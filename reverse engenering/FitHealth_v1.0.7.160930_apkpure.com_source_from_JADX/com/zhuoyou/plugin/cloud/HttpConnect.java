package com.zhuoyou.plugin.cloud;

import android.net.Uri;
import com.umeng.socialize.common.SocializeConstants;
import com.weibo.net.Utility;
import com.zhuoyou.plugin.bluetooth.data.BMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

public class HttpConnect {
    public static final int DOWN_RESULT_HTTP_TIMEOUT_OR_ERROR = 2;
    public static final int DOWN_RESULT_SDCARD_LOST = 0;
    public static final int DOWN_RESULT_SUCCESS = 3;
    public static final int DOWN_RESULT_USER_PAUSED = 1;
    private static final long FAULT_TOLERANT_BEFFER = 1024;
    public static final int UPLOAD_RESULT_FILE_MD5_NULL = 2;
    public static final int UPLOAD_RESULT_HTTP_ERROR = 0;
    public static final int UPLOAD_RESULT_SUCCESS = 1;
    private DefaultHttpClient mHttpClient = null;

    private class FormFile {
        private String contentType = Utility.MULTIPART_FORM_DATA;
        private byte[] data;
        private File file;
        private String filname;
        private FileInputStream inStream;
        private String parameterName;
        private long uploadedSize;

        public FormFile(String filname, File file, String parameterName, String contentType, long uploadedSize) {
            this.filname = filname;
            this.parameterName = parameterName;
            this.file = file;
            try {
                this.inStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (contentType != null) {
                this.contentType = contentType;
            }
        }

        public long getUploadedSize() {
            return this.uploadedSize;
        }

        public File getFile() {
            return this.file;
        }

        public FileInputStream getInStream() {
            return this.inStream;
        }

        public byte[] getData() {
            return this.data;
        }

        public String getFilename() {
            return this.filname;
        }

        public void setFilename(String filname) {
            this.filname = filname;
        }

        public String getParameterName() {
            return this.parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getContentType() {
            return this.contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }
    }

    public int uploadFile(String url, HashMap<String, String> params, String filePath) {
        try {
            File file = new File(filePath);
            String md5 = getFileMd5(file.getAbsolutePath());
            if (md5 == null) {
                return 2;
            }
            params.put("uploadFileMd5", md5);
            params.put("uploadFileSize", Long.toString(file.length()));
            params.put("action", Integer.toString(1));
            String responseStr = postExternalFile(url, params, null);
            if (responseStr != null) {
                JSONObject jSONObject = new JSONObject(responseStr);
                if (jSONObject.getInt("result") == 0) {
                    FormFile formFile;
                    if (jSONObject.getInt("exist") == 1) {
                        long uploadedSize = jSONObject.getLong("fileSize") - 1024;
                        if (uploadedSize < 0) {
                            uploadedSize = 0;
                        }
                        formFile = new FormFile(file.getName(), file, "datafile", null, uploadedSize);
                    } else {
                        FormFile formFile2 = new FormFile(file.getName(), file, "datafile", null, 0);
                    }
                    params.put("action", Integer.toString(2));
                    responseStr = postExternalFile(url, params, formFile);
                    if (responseStr != null) {
                        if (new JSONObject(responseStr).getInt("result") == 0) {
                            return 1;
                        }
                    }
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int downloadFile(String url, HashMap<String, String> headers, HashMap<String, String> params, String filePath) {
        long currSize = new File(filePath).length() - 1024;
        if (currSize < 0) {
            currSize = 0;
        }
        ArrayList<BasicNameValuePair> bnvp = new ArrayList();
        for (Entry<String, String> entry : params.entrySet()) {
            bnvp.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
        }
        HttpResponse response = doPost(url, headers, bnvp);
        if (response == null) {
            onShutdownConn();
            return 2;
        }
        StatusLine status = response.getStatusLine();
        if (status.getStatusCode() == 200 || status.getStatusCode() == 206) {
            long fileSize = getDownloadFileSize(response);
            if (headers == null) {
                headers = new HashMap();
            }
            headers.put(HttpHeaders.RANGE, "bytes=" + Long.toString(currSize) + SocializeConstants.OP_DIVIDER_MINUS + Long.toString(fileSize));
            response = doPost(url, headers, bnvp);
            if (response == null) {
                onShutdownConn();
                return 2;
            }
            status = response.getStatusLine();
            if (status.getStatusCode() == 200 || status.getStatusCode() == 206) {
                int ret = writeFile(response, currSize, fileSize, filePath);
                onShutdownConn();
                return ret;
            }
            onShutdownConn();
            return 2;
        }
        onShutdownConn();
        return 2;
    }

    private void onShutdownConn() {
        if (this.mHttpClient != null) {
            this.mHttpClient.getConnectionManager().shutdown();
        }
        this.mHttpClient = null;
    }

    private HttpResponse doPost(String url, HashMap<String, String> headers, ArrayList<BasicNameValuePair> bnvp) {
        Exception e;
        HttpParams httpParam = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParam, 30000);
        HttpConnectionParams.setSoTimeout(httpParam, 30000);
        this.mHttpClient = new DefaultHttpClient(httpParam);
        HttpContext localcontext = new BasicHttpContext();
        HttpHost host = null;
        try {
            HttpPost httpPost;
            if (url.contains("https")) {
                Uri u = Uri.parse(url);
                HttpHost host2 = new HttpHost(u.getHost(), 443, u.getScheme());
                try {
                    httpPost = new HttpPost(u.getPath());
                    host = host2;
                } catch (Exception e2) {
                    e = e2;
                    host = host2;
                    e.printStackTrace();
                    return null;
                }
            }
            httpPost = new HttpPost(url);
            if (headers != null) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    httpPost.addHeader((String) entry.getKey(), (String) entry.getValue());
                }
            }
            if (bnvp != null) {
                httpPost.setEntity(new UrlEncodedFormEntity(bnvp));
            }
            if (url.contains("https")) {
                return this.mHttpClient.execute(host, (HttpRequest) httpPost);
            }
            return this.mHttpClient.execute((HttpUriRequest) httpPost, localcontext);
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return null;
        }
    }

    private long getDownloadFileSize(HttpResponse response) {
        try {
            return (long) response.getEntity().getContent().available();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int writeFile(org.apache.http.HttpResponse r19, long r20, long r22, java.lang.String r24) {
        /*
        r18 = this;
        r9 = 0;
        r12 = r19.getEntity();	 Catch:{ Exception -> 0x00b0 }
        r7 = r12.getContent();	 Catch:{ Exception -> 0x00b0 }
        r10 = new java.io.RandomAccessFile;	 Catch:{ Exception -> 0x0034 }
        r12 = "rws";
        r0 = r24;
        r10.<init>(r0, r12);	 Catch:{ Exception -> 0x0034 }
        r0 = r20;
        r10.seek(r0);	 Catch:{ Exception -> 0x00d3, all -> 0x00cd }
        r12 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r2 = new byte[r12];	 Catch:{ Exception -> 0x00d0, all -> 0x00cd }
        r11 = 0;
        r3 = 0;
    L_0x001d:
        r12 = 0;
        r13 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r11 = r7.read(r2, r12, r13);	 Catch:{ Exception -> 0x00d0, all -> 0x00cd }
        if (r11 <= 0) goto L_0x008b;
    L_0x0026:
        r12 = java.lang.Thread.interrupted();	 Catch:{ Exception -> 0x007a, all -> 0x00cd }
        if (r12 == 0) goto L_0x0049;
    L_0x002c:
        r12 = 1;
        if (r10 == 0) goto L_0x0032;
    L_0x002f:
        r10.close();	 Catch:{ Exception -> 0x0044 }
    L_0x0032:
        r9 = r10;
    L_0x0033:
        return r12;
    L_0x0034:
        r6 = move-exception;
    L_0x0035:
        r6.printStackTrace();	 Catch:{ Exception -> 0x00b0 }
        r12 = 0;
        if (r9 == 0) goto L_0x0033;
    L_0x003b:
        r9.close();	 Catch:{ Exception -> 0x003f }
        goto L_0x0033;
    L_0x003f:
        r6 = move-exception;
        r6.printStackTrace();
        goto L_0x0033;
    L_0x0044:
        r6 = move-exception;
        r6.printStackTrace();
        goto L_0x0032;
    L_0x0049:
        r12 = 0;
        r10.write(r2, r12, r11);	 Catch:{ Exception -> 0x007a, all -> 0x00cd }
        r3 = r3 + r11;
        r12 = (long) r3;	 Catch:{ Exception -> 0x007a, all -> 0x00cd }
        r14 = 2;
        r14 = r22 / r14;
        r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r12 < 0) goto L_0x001d;
    L_0x0057:
        r12 = (long) r3;	 Catch:{ Exception -> 0x007a, all -> 0x00cd }
        r14 = 2;
        r14 = r22 / r14;
        r16 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r14 = r14 + r16;
        r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r12 >= 0) goto L_0x001d;
    L_0x0064:
        r12 = com.zhuoyou.plugin.running.HomePageFragment.mHandler;	 Catch:{ Exception -> 0x007a, all -> 0x00cd }
        if (r12 == 0) goto L_0x001d;
    L_0x0068:
        r8 = new android.os.Message;	 Catch:{ Exception -> 0x007a, all -> 0x00cd }
        r8.<init>();	 Catch:{ Exception -> 0x007a, all -> 0x00cd }
        r12 = 4;
        r8.what = r12;	 Catch:{ Exception -> 0x007a, all -> 0x00cd }
        r12 = "80%";
        r8.obj = r12;	 Catch:{ Exception -> 0x007a, all -> 0x00cd }
        r12 = com.zhuoyou.plugin.running.HomePageFragment.mHandler;	 Catch:{ Exception -> 0x007a, all -> 0x00cd }
        r12.sendMessage(r8);	 Catch:{ Exception -> 0x007a, all -> 0x00cd }
        goto L_0x001d;
    L_0x007a:
        r6 = move-exception;
        r6.printStackTrace();	 Catch:{ Exception -> 0x00d0, all -> 0x00cd }
        r12 = 0;
        if (r10 == 0) goto L_0x0084;
    L_0x0081:
        r10.close();	 Catch:{ Exception -> 0x0086 }
    L_0x0084:
        r9 = r10;
        goto L_0x0033;
    L_0x0086:
        r6 = move-exception;
        r6.printStackTrace();
        goto L_0x0084;
    L_0x008b:
        r4 = r10.length();	 Catch:{ Exception -> 0x00d0, all -> 0x00cd }
        r10.close();	 Catch:{ Exception -> 0x00d0, all -> 0x00cd }
        r12 = (r4 > r22 ? 1 : (r4 == r22 ? 0 : -1));
        if (r12 >= 0) goto L_0x00a3;
    L_0x0096:
        r12 = 2;
        if (r10 == 0) goto L_0x009c;
    L_0x0099:
        r10.close();	 Catch:{ Exception -> 0x009e }
    L_0x009c:
        r9 = r10;
        goto L_0x0033;
    L_0x009e:
        r6 = move-exception;
        r6.printStackTrace();
        goto L_0x009c;
    L_0x00a3:
        r12 = 3;
        if (r10 == 0) goto L_0x00a9;
    L_0x00a6:
        r10.close();	 Catch:{ Exception -> 0x00ab }
    L_0x00a9:
        r9 = r10;
        goto L_0x0033;
    L_0x00ab:
        r6 = move-exception;
        r6.printStackTrace();
        goto L_0x00a9;
    L_0x00b0:
        r6 = move-exception;
    L_0x00b1:
        r6.printStackTrace();	 Catch:{ all -> 0x00c1 }
        if (r9 == 0) goto L_0x00b9;
    L_0x00b6:
        r9.close();	 Catch:{ Exception -> 0x00bc }
    L_0x00b9:
        r12 = 2;
        goto L_0x0033;
    L_0x00bc:
        r6 = move-exception;
        r6.printStackTrace();
        goto L_0x00b9;
    L_0x00c1:
        r12 = move-exception;
    L_0x00c2:
        if (r9 == 0) goto L_0x00c7;
    L_0x00c4:
        r9.close();	 Catch:{ Exception -> 0x00c8 }
    L_0x00c7:
        throw r12;
    L_0x00c8:
        r6 = move-exception;
        r6.printStackTrace();
        goto L_0x00c7;
    L_0x00cd:
        r12 = move-exception;
        r9 = r10;
        goto L_0x00c2;
    L_0x00d0:
        r6 = move-exception;
        r9 = r10;
        goto L_0x00b1;
    L_0x00d3:
        r6 = move-exception;
        r9 = r10;
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.cloud.HttpConnect.writeFile(org.apache.http.HttpResponse, long, long, java.lang.String):int");
    }

    private String postExternalFile(String address, HashMap<String, String> params, FormFile file) throws Exception {
        String BOUNDARY = "----yphPostBoundaryMakeIn20141023";
        String endline = "------yphPostBoundaryMakeIn20141023--\r\n";
        int fileDataLength = 0;
        if (file != null) {
            StringBuilder fileExplain = new StringBuilder();
            fileExplain.append("--");
            fileExplain.append("----yphPostBoundaryMakeIn20141023");
            fileExplain.append(BMessage.CRLF);
            fileExplain.append("Content-Disposition: form-data;name=\"" + file.getParameterName() + "\";filename=\"" + file.getFilename() + "\"\r\n");
            fileExplain.append("Content-Type: " + file.getContentType() + "\r\n\r\n");
            fileExplain.append(BMessage.CRLF);
            fileDataLength = 0 + fileExplain.length();
            if (file.getInStream() != null) {
                fileDataLength = (int) (((long) fileDataLength) + file.getFile().length());
            } else {
                fileDataLength += file.getData().length;
            }
        }
        StringBuilder textEntity = new StringBuilder();
        for (Entry<String, String> entry : params.entrySet()) {
            textEntity.append("--");
            textEntity.append("----yphPostBoundaryMakeIn20141023");
            textEntity.append(BMessage.CRLF);
            textEntity.append("Content-Disposition: form-data; name=\"" + ((String) entry.getKey()) + "\"\r\n\r\n");
            textEntity.append((String) entry.getValue());
            textEntity.append(BMessage.CRLF);
        }
        int dataLength = (textEntity.toString().getBytes().length + fileDataLength) + "------yphPostBoundaryMakeIn20141023--\r\n".getBytes().length;
        URL url = new URL(address);
        int port = url.getPort() == -1 ? 80 : url.getPort();
        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
        OutputStream outStream = socket.getOutputStream();
        outStream.write(("POST " + url.getPath() + " HTTP/1.1\r\n").getBytes());
        outStream.write("Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n".getBytes());
        outStream.write("Accept-Language: zh-CN\r\n".getBytes());
        outStream.write("Content-Type: multipart/form-data; boundary=----yphPostBoundaryMakeIn20141023\r\n".getBytes());
        outStream.write(("Content-Length: " + dataLength + BMessage.CRLF).getBytes());
        outStream.write("Connection: Keep-Alive\r\n".getBytes());
        outStream.write(("Host: " + url.getHost() + ":" + port + BMessage.CRLF).getBytes());
        outStream.write(BMessage.CRLF.getBytes());
        outStream.write(textEntity.toString().getBytes());
        StringBuffer strBuf = new StringBuffer();
        if (file != null) {
            StringBuilder fileEntity = new StringBuilder();
            fileEntity.append("--");
            fileEntity.append("----yphPostBoundaryMakeIn20141023");
            fileEntity.append(BMessage.CRLF);
            fileEntity.append("Content-Disposition: form-data;name=\"" + file.getParameterName() + "\";filename=\"" + file.getFilename() + "\"\r\n");
            fileEntity.append("Content-Type: " + file.getContentType() + "\r\n\r\n");
            outStream.write(fileEntity.toString().getBytes());
            FileInputStream fis = file.getInStream();
            if (fis != null) {
                fis.skip(file.getUploadedSize());
                byte[] buffer = new byte[1024];
                while (true) {
                    int len = fis.read(buffer, 0, 1024);
                    if (len == -1) {
                        break;
                    }
                    outStream.write(buffer, 0, len);
                }
                fis.close();
            } else {
                outStream.write(file.getData(), 0, file.getData().length);
            }
            outStream.write(BMessage.CRLF.getBytes());
        }
        outStream.write("------yphPostBoundaryMakeIn20141023--\r\n".getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        if (bufferedReader != null && bufferedReader.readLine().indexOf("200") == -1) {
            return null;
        }
        String contLenLable = "Content-Length: ";
        int contLength = 0;
        boolean readBody = false;
        while (bufferedReader != null) {
            String readStr = bufferedReader.readLine();
            if (readStr == null) {
                break;
            }
            if (readStr.startsWith(contLenLable)) {
                contLength = Integer.parseInt(readStr.substring(contLenLable.length()));
            }
            if (readStr.equals("")) {
                readBody = true;
            }
            if (contLength != 0 && readBody && readStr.length() == contLength) {
                strBuf.append(readStr);
            }
        }
        outStream.flush();
        outStream.close();
        bufferedReader.close();
        socket.close();
        return strBuf.toString();
    }

    private String getFileMd5(String fileName) {
        Exception e;
        Throwable th;
        String str = null;
        FileInputStream fileInputStream = null;
        StringBuffer strBuff = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(fileName);
            try {
                byte[] buffer = new byte[1048576];
                while (true) {
                    int length = fis.read(buffer);
                    if (length == -1) {
                        break;
                    }
                    md.update(buffer, 0, length);
                }
                byte[] bytes = md.digest();
                if (bytes == null) {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    fileInputStream = fis;
                } else {
                    for (byte b : bytes) {
                        String md5s = Integer.toHexString(b & 255);
                        if (md5s.length() == 1) {
                            strBuff.append("0");
                        }
                        strBuff.append(md5s);
                    }
                    str = strBuff.toString();
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (Exception e22) {
                            e22.printStackTrace();
                        }
                    }
                    fileInputStream = fis;
                }
            } catch (Exception e3) {
                e22 = e3;
                fileInputStream = fis;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = fis;
            }
        } catch (Exception e4) {
            e22 = e4;
            try {
                e22.printStackTrace();
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e222) {
                        e222.printStackTrace();
                    }
                }
                return str;
            } catch (Throwable th3) {
                th = th3;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e2222) {
                        e2222.printStackTrace();
                    }
                }
                throw th;
            }
        }
        return str;
    }
}
