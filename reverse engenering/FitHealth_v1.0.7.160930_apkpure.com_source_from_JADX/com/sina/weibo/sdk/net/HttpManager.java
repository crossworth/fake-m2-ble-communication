package com.sina.weibo.sdk.net;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.exception.WeiboHttpException;
import com.zhuoyou.plugin.bluetooth.data.BMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

class HttpManager {
    private static final String BOUNDARY = getBoundry();
    private static final int BUFFER_SIZE = 8192;
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final String END_MP_BOUNDARY = ("--" + BOUNDARY + "--");
    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";
    private static final String MP_BOUNDARY = ("--" + BOUNDARY);
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final int SOCKET_TIMEOUT = 20000;
    private static SSLSocketFactory sSSLSocketFactory;

    HttpManager() {
    }

    public static String openUrl(String url, String method, WeiboParameters params) throws WeiboException {
        return readRsponse(requestHttpExecute(url, method, params));
    }

    private static HttpResponse requestHttpExecute(String url, String method, WeiboParameters params) {
        try {
            HttpClient client = getNewHttpClient();
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, NetStateManager.getAPN());
            HttpUriRequest request = null;
            if (method.equals("GET")) {
                request = new HttpGet(new StringBuilder(String.valueOf(url)).append("?").append(params.encodeUrl()).toString());
            } else {
                if (method.equals("POST")) {
                    HttpPost post = new HttpPost(url);
                    Object request2 = post;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    if (params.hasBinaryData()) {
                        post.setHeader("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
                        buildParams(baos, params);
                    } else {
                        Object value = params.get("content-type");
                        if (value == null || !(value instanceof String)) {
                            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
                        } else {
                            params.remove("content-type");
                            post.setHeader("Content-Type", (String) value);
                        }
                        baos.write(params.encodeUrl().getBytes("UTF-8"));
                    }
                    post.setEntity(new ByteArrayEntity(baos.toByteArray()));
                    baos.close();
                } else {
                    if (method.equals("DELETE")) {
                        request = new HttpDelete(url);
                    }
                }
            }
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return response;
            }
            throw new WeiboHttpException(readRsponse(response), statusCode);
        } catch (Throwable e) {
            throw new WeiboException(e);
        }
    }

    private static HttpClient getNewHttpClient() {
        try {
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "UTF-8");
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", getSSLSocketFactory(), 443));
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
            HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);
            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    private static void buildParams(OutputStream baos, WeiboParameters params) throws WeiboException {
        try {
            StringBuilder sb;
            Set<String> keys = params.keySet();
            for (String key : keys) {
                if (params.get(key) instanceof String) {
                    sb = new StringBuilder(100);
                    sb.setLength(0);
                    sb.append(MP_BOUNDARY).append(BMessage.CRLF);
                    sb.append("content-disposition: form-data; name=\"").append(key).append("\"\r\n\r\n");
                    sb.append(params.get(key)).append(BMessage.CRLF);
                    baos.write(sb.toString().getBytes());
                }
            }
            for (String key2 : keys) {
                Object value = params.get(key2);
                ByteArrayOutputStream stream;
                if (value instanceof Bitmap) {
                    sb = new StringBuilder();
                    sb.append(MP_BOUNDARY).append(BMessage.CRLF);
                    sb.append("content-disposition: form-data; name=\"").append(key2).append("\"; filename=\"file\"\r\n");
                    sb.append("Content-Type: application/octet-stream; charset=utf-8\r\n\r\n");
                    baos.write(sb.toString().getBytes());
                    Bitmap bmp = (Bitmap) value;
                    stream = new ByteArrayOutputStream();
                    bmp.compress(CompressFormat.PNG, 100, stream);
                    baos.write(stream.toByteArray());
                    baos.write(BMessage.CRLF.getBytes());
                } else if (value instanceof ByteArrayOutputStream) {
                    sb = new StringBuilder();
                    sb.append(MP_BOUNDARY).append(BMessage.CRLF);
                    sb.append("content-disposition: form-data; name=\"").append(key2).append("\"; filename=\"file\"\r\n");
                    sb.append("Content-Type: application/octet-stream; charset=utf-8\r\n\r\n");
                    baos.write(sb.toString().getBytes());
                    stream = (ByteArrayOutputStream) value;
                    baos.write(stream.toByteArray());
                    baos.write(BMessage.CRLF.getBytes());
                    stream.close();
                }
            }
            baos.write(new StringBuilder(BMessage.CRLF).append(END_MP_BOUNDARY).toString().getBytes());
        } catch (Throwable e) {
            throw new WeiboException(e);
        }
    }

    private static String readRsponse(HttpResponse response) throws WeiboException {
        if (response == null) {
            return null;
        }
        HttpEntity entity = response.getEntity();
        InputStream inputStream = null;
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        try {
            inputStream = entity.getContent();
            Header header = response.getFirstHeader("Content-Encoding");
            if (header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
                inputStream = new GZIPInputStream(inputStream);
            }
            byte[] buffer = new byte[8192];
            while (true) {
                int readBytes = inputStream.read(buffer);
                if (readBytes == -1) {
                    break;
                }
                content.write(buffer, 0, readBytes);
            }
            String str = new String(content.toByteArray(), "UTF-8");
            if (inputStream == null) {
                return str;
            }
            try {
                inputStream.close();
                return str;
            } catch (IOException e) {
                e.printStackTrace();
                return str;
            }
        } catch (Throwable e2) {
            throw new WeiboException(e2);
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    private static String getBoundry() {
        StringBuffer sb = new StringBuffer();
        for (int t = 1; t < 12; t++) {
            long time = System.currentTimeMillis() + ((long) t);
            if (time % 3 == 0) {
                sb.append(((char) ((int) time)) % 9);
            } else if (time % 3 == 1) {
                sb.append((char) ((int) (65 + (time % 26))));
            } else {
                sb.append((char) ((int) (97 + (time % 26))));
            }
        }
        return sb.toString();
    }

    private static SSLSocketFactory getSSLSocketFactory() {
        if (sSSLSocketFactory == null) {
            InputStream caInput;
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                caInput = HttpManager.class.getResourceAsStream("cacert.cer");
                Certificate ca = cf.generateCertificate(caInput);
                caInput.close();
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", ca);
                sSSLSocketFactory = new SSLSocketFactory(keyStore);
            } catch (Exception e) {
                e.printStackTrace();
                sSSLSocketFactory = SSLSocketFactory.getSocketFactory();
            } catch (Throwable th) {
                caInput.close();
            }
        }
        return sSSLSocketFactory;
    }
}
