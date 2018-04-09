package twitter4j;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.Authenticator.RequestorType;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import twitter4j.conf.ConfigurationContext;

class HttpClientImpl extends HttpClientBase implements HttpResponseCode, Serializable {
    private static final Map<HttpClientConfiguration, HttpClient> instanceMap = new HashMap(1);
    private static final long serialVersionUID = -403500272719330534L;

    class C21801 extends Authenticator {
        C21801() {
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            if (getRequestorType().equals(RequestorType.PROXY)) {
                return new PasswordAuthentication(HttpClientImpl.this.CONF.getHttpProxyUser(), HttpClientImpl.this.CONF.getHttpProxyPassword().toCharArray());
            }
            return null;
        }
    }

    static {
        try {
            if (Integer.parseInt((String) Class.forName("android.os.Build$VERSION").getField("SDK").get(null)) < 8) {
                System.setProperty("http.keepAlive", "false");
            }
        } catch (Exception e) {
        }
    }

    public HttpClientImpl() {
        super(ConfigurationContext.getInstance().getHttpClientConfiguration());
    }

    public HttpClientImpl(HttpClientConfiguration conf) {
        super(conf);
    }

    public static HttpClient getInstance(HttpClientConfiguration conf) {
        HttpClient client = (HttpClient) instanceMap.get(conf);
        if (client != null) {
            return client;
        }
        client = new HttpClientImpl(conf);
        instanceMap.put(conf, client);
        return client;
    }

    public HttpResponse get(String url) throws TwitterException {
        return request(new HttpRequest(RequestMethod.GET, url, null, null, null));
    }

    public HttpResponse post(String url, HttpParameter[] params) throws TwitterException {
        return request(new HttpRequest(RequestMethod.POST, url, params, null, null));
    }

    public HttpResponse handleRequest(HttpRequest req) throws TwitterException {
        Throwable th;
        int retry = this.CONF.getHttpRetryCount() + 1;
        int retriedCount = 0;
        HttpResponse res = null;
        while (retriedCount < retry) {
            OutputStream os = null;
            HttpResponse res2;
            try {
                HttpURLConnection con = getConnection(req.getURL());
                con.setDoInput(true);
                setHeaders(req, con);
                con.setRequestMethod(req.getMethod().name());
                if (req.getMethod() == RequestMethod.POST) {
                    if (HttpParameter.containsFile(req.getParameters())) {
                        String boundary = "----Twitter4J-upload" + System.currentTimeMillis();
                        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                        boundary = "--" + boundary;
                        con.setDoOutput(true);
                        os = con.getOutputStream();
                        DataOutputStream out = new DataOutputStream(os);
                        for (HttpParameter param : req.getParameters()) {
                            if (param.isFile()) {
                                InputStream fileBody;
                                write(out, boundary + "\r\n");
                                write(out, "Content-Disposition: form-data; name=\"" + param.getName() + "\"; filename=\"" + param.getFile().getName() + "\"\r\n");
                                write(out, "Content-Type: " + param.getContentType() + "\r\n\r\n");
                                if (param.hasFileBody()) {
                                    fileBody = param.getFileBody();
                                } else {
                                    InputStream fileInputStream = new FileInputStream(param.getFile());
                                }
                                BufferedInputStream in = new BufferedInputStream(fileBody);
                                byte[] buff = new byte[1024];
                                while (true) {
                                    int length = in.read(buff);
                                    if (length == -1) {
                                        break;
                                    }
                                    out.write(buff, 0, length);
                                }
                                write(out, "\r\n");
                                in.close();
                            } else {
                                write(out, boundary + "\r\n");
                                write(out, "Content-Disposition: form-data; name=\"" + param.getName() + "\"\r\n");
                                write(out, "Content-Type: text/plain; charset=UTF-8\r\n\r\n");
                                out.write(param.getValue().getBytes("UTF-8"));
                                write(out, "\r\n");
                            }
                        }
                        write(out, boundary + "--\r\n");
                        write(out, "\r\n");
                    } else {
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        byte[] bytes = HttpParameter.encodeParameters(req.getParameters()).getBytes("UTF-8");
                        con.setRequestProperty("Content-Length", Integer.toString(bytes.length));
                        con.setDoOutput(true);
                        os = con.getOutputStream();
                        os.write(bytes);
                    }
                    os.flush();
                    os.close();
                }
                res2 = new HttpResponseImpl(con, this.CONF);
                try {
                    int responseCode = con.getResponseCode();
                    if (responseCode >= 200 && (responseCode == 302 || 300 > responseCode)) {
                        try {
                            os.close();
                            return res2;
                        } catch (Exception e) {
                            return res2;
                        }
                    } else if (responseCode == 420 || responseCode == 400 || responseCode < 500 || retriedCount == this.CONF.getHttpRetryCount()) {
                        throw new TwitterException(res2.asString(), res2);
                    } else {
                        try {
                            os.close();
                        } catch (Exception e2) {
                        }
                        try {
                            Thread.sleep((long) (this.CONF.getHttpRetryIntervalSeconds() * 1000));
                        } catch (InterruptedException e3) {
                        }
                        retriedCount++;
                        res = res2;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        os.close();
                    } catch (Exception e4) {
                    }
                    try {
                        throw th;
                    } catch (IOException ioe) {
                        if (retriedCount == this.CONF.getHttpRetryCount()) {
                            throw new TwitterException(ioe.getMessage(), ioe, -1);
                        }
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                res2 = res;
            }
        }
        return res;
    }

    private void setHeaders(HttpRequest req, HttpURLConnection connection) {
        if (req.getAuthorization() != null) {
            String authorizationHeader = req.getAuthorization().getAuthorizationHeader(req);
            if (authorizationHeader != null) {
                connection.addRequestProperty("Authorization", authorizationHeader);
            }
        }
        if (req.getRequestHeaders() != null) {
            for (String key : req.getRequestHeaders().keySet()) {
                connection.addRequestProperty(key, (String) req.getRequestHeaders().get(key));
            }
        }
    }

    HttpURLConnection getConnection(String url) throws IOException {
        HttpURLConnection con;
        if (isProxyConfigured()) {
            if (!(this.CONF.getHttpProxyUser() == null || this.CONF.getHttpProxyUser().equals(""))) {
                Authenticator.setDefault(new C21801());
            }
            con = (HttpURLConnection) new URL(url).openConnection(new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(this.CONF.getHttpProxyHost(), this.CONF.getHttpProxyPort())));
        } else {
            con = (HttpURLConnection) new URL(url).openConnection();
        }
        if (this.CONF.getHttpConnectionTimeout() > 0) {
            con.setConnectTimeout(this.CONF.getHttpConnectionTimeout());
        }
        if (this.CONF.getHttpReadTimeout() > 0) {
            con.setReadTimeout(this.CONF.getHttpReadTimeout());
        }
        con.setInstanceFollowRedirects(false);
        return con;
    }
}
