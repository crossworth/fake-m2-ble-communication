package com.umeng.socialize.net.utils;

import android.net.Uri.Builder;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.net.utils.URequest.FilePair;
import com.umeng.socialize.utils.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import org.apache.http.HttpHeaders;
import org.json.JSONObject;

public class UClient {
    private static final String END = "\r\n";
    private static final String TAG = "UClient";
    private Map<String, String> mHeaders;
    private StringBuilder mRequestInfo;

    public <T extends UResponse> T execute(URequest uRequest, Class<T> cls) {
        JSONObject httpGetRequest;
        uRequest.onPrepareRequest();
        String trim = uRequest.getHttpMethod().trim();
        verifyMethod(trim);
        this.mRequestInfo = new StringBuilder();
        if (URequest.GET.equals(trim)) {
            httpGetRequest = httpGetRequest(uRequest);
        } else if (URequest.POST.equals(trim)) {
            httpGetRequest = httpPostRequest(uRequest.mBaseUrl, uRequest);
        } else {
            httpGetRequest = null;
        }
        if (httpGetRequest == null) {
            return null;
        }
        try {
            return (UResponse) cls.getConstructor(new Class[]{JSONObject.class}).newInstance(new Object[]{httpGetRequest});
        } catch (Exception e) {
            Log.m3252e(TAG, "SecurityException", e);
            return null;
        } catch (Exception e2) {
            Log.m3252e(TAG, "NoSuchMethodException", e2);
            return null;
        } catch (Exception e22) {
            Log.m3252e(TAG, "IllegalArgumentException", e22);
            return null;
        } catch (Exception e222) {
            Log.m3252e(TAG, "InstantiationException", e222);
            return null;
        } catch (Exception e2222) {
            Log.m3252e(TAG, "IllegalAccessException", e2222);
            return null;
        } catch (Exception e22222) {
            Log.m3252e(TAG, "InvocationTargetException", e22222);
            return null;
        }
    }

    private JSONObject httpPostRequest(String str, URequest uRequest) {
        String str2;
        Throwable th;
        if (uRequest.toJson() == null) {
            str2 = "";
        } else {
            str2 = uRequest.toJson().toString();
        }
        int nextInt = new Random().nextInt(1000);
        Log.m3254i(TAG, nextInt + ":\trequest: " + str + System.getProperty("line.separator") + str2);
        String uuid = UUID.randomUUID().toString();
        Log.m3251e("xxxxx", "url=" + str);
        HttpURLConnection httpURLConnection;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setConnectTimeout(Config.connectionTimeOut);
                httpURLConnection.setReadTimeout(Config.readSocketTimeOut);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + uuid);
                Map bodyPair = uRequest.getBodyPair();
                String encodedQuery;
                JSONObject parseResult;
                if (bodyPair == null || bodyPair.size() <= 0) {
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    Builder builder = new Builder();
                    builder.appendQueryParameter("content", str2);
                    encodedQuery = builder.build().getEncodedQuery();
                    OutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    dataOutputStream.write(encodedQuery.getBytes());
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    parseResult = parseResult(httpURLConnection);
                    if (httpURLConnection == null) {
                        return parseResult;
                    }
                    httpURLConnection.disconnect();
                    return parseResult;
                }
                OutputStream dataOutputStream2;
                Object obj;
                StringBuilder stringBuilder = new StringBuilder();
                for (String encodedQuery2 : bodyPair.keySet()) {
                    if (bodyPair.get(encodedQuery2) != null) {
                        addFormField(stringBuilder, encodedQuery2, bodyPair.get(encodedQuery2).toString(), uuid);
                    }
                }
                if (stringBuilder.length() > 0) {
                    dataOutputStream2 = new DataOutputStream(httpURLConnection.getOutputStream());
                    dataOutputStream2.write(stringBuilder.toString().getBytes());
                    obj = 1;
                } else {
                    obj = null;
                    dataOutputStream2 = null;
                }
                Map filePair = uRequest.getFilePair();
                if (filePair != null && filePair.size() > 0) {
                    stringBuilder = new StringBuilder();
                    for (String str3 : filePair.keySet()) {
                        byte[] bArr = ((FilePair) filePair.get(str3)).mBinaryData;
                        if (bArr != null && bArr.length >= 1) {
                            addFilePart(stringBuilder, str3, bArr, uuid, dataOutputStream2);
                        }
                    }
                }
                if (obj != null) {
                    finishWrite(dataOutputStream2, uuid);
                }
                parseResult = parseResult(httpURLConnection);
                if (httpURLConnection == null) {
                    return parseResult;
                }
                httpURLConnection.disconnect();
                return parseResult;
            } catch (IOException e) {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th;
            }
        } catch (IOException e2) {
            httpURLConnection = null;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            httpURLConnection = null;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
    }

    private JSONObject parseResult(HttpURLConnection httpURLConnection) throws IOException {
        if (httpURLConnection.getResponseCode() != 200) {
            return null;
        }
        String convertStreamToString = convertStreamToString(new BufferedInputStream(httpURLConnection.getInputStream()));
        try {
            return new JSONObject(convertStreamToString);
        } catch (Exception e) {
            try {
                return new JSONObject(AesHelper.decryptNoPadding(convertStreamToString, "UTF-8").trim());
            } catch (Exception e2) {
                return null;
            }
        }
    }

    private JSONObject httpGetRequest(URequest uRequest) {
        HttpURLConnection httpURLConnection;
        Exception exception;
        Throwable th;
        HttpURLConnection httpURLConnection2 = null;
        int nextInt = new Random().nextInt(1000);
        try {
            if (uRequest.toGetUrl().length() <= 1) {
                Log.m3251e(TAG, nextInt + ":\tInvalid baseUrl.");
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                return httpURLConnection2;
            }
            HttpURLConnection httpURLConnection3 = (HttpURLConnection) new URL(uRequest.toGetUrl()).openConnection();
            try {
                String contentEncoding;
                httpURLConnection3.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, "gzip");
                httpURLConnection3.setConnectTimeout(Config.connectionTimeOut);
                httpURLConnection3.setReadTimeout(Config.readSocketTimeOut);
                if (this.mHeaders != null && this.mHeaders.size() > 0) {
                    for (String contentEncoding2 : this.mHeaders.keySet()) {
                        httpURLConnection3.setRequestProperty(contentEncoding2, (String) this.mHeaders.get(contentEncoding2));
                    }
                }
                httpURLConnection3.connect();
                if (httpURLConnection3.getResponseCode() == 200) {
                    InputStream gZIPInputStream;
                    contentEncoding2 = httpURLConnection3.getContentEncoding();
                    if (contentEncoding2.equalsIgnoreCase("gzip")) {
                        gZIPInputStream = new GZIPInputStream(httpURLConnection3.getInputStream());
                    } else if (contentEncoding2.equalsIgnoreCase("deflate")) {
                        gZIPInputStream = new InflaterInputStream(httpURLConnection3.getInputStream());
                    } else {
                        Object obj = httpURLConnection2;
                    }
                    String trim = AesHelper.decryptNoPadding(convertStreamToString(gZIPInputStream), "UTF-8").trim();
                    if (trim == null) {
                        if (httpURLConnection3 != null) {
                            httpURLConnection3.disconnect();
                        }
                        return httpURLConnection2;
                    }
                    JSONObject jSONObject = new JSONObject(trim);
                    if (httpURLConnection3 != null) {
                        httpURLConnection3.disconnect();
                    }
                    return jSONObject;
                }
                if (httpURLConnection3 != null) {
                    httpURLConnection3.disconnect();
                }
                return httpURLConnection2;
            } catch (Exception e) {
                Exception exception2 = e;
                httpURLConnection = httpURLConnection3;
                exception = exception2;
                try {
                    Log.m3251e("UMhttprequest", "error:" + exception.getMessage());
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return httpURLConnection2;
                } catch (Throwable th2) {
                    th = th2;
                    httpURLConnection2 = httpURLConnection;
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                httpURLConnection2 = httpURLConnection3;
                th = th3;
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                throw th;
            }
        } catch (Exception e2) {
            exception = e2;
            httpURLConnection = httpURLConnection2;
            Log.m3251e("UMhttprequest", "error:" + exception.getMessage());
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return httpURLConnection2;
        } catch (Throwable th4) {
            th = th4;
            if (httpURLConnection2 != null) {
                httpURLConnection2.disconnect();
            }
            throw th;
        }
    }

    public UClient setHeader(Map<String, String> map) {
        this.mHeaders = map;
        return this;
    }

    private void verifyMethod(String str) {
        if (TextUtils.isEmpty(str) || (URequest.GET.equals(str.trim()) ^ URequest.POST.equals(str.trim())) == 0) {
            throw new RuntimeException("验证请求方式失败[" + str + "]");
        }
    }

    private void outprint(String str) {
        this.mRequestInfo.append(str);
    }

    private static String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8192);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(readLine + "\n");
            } catch (Exception e) {
                stringBuilder = TAG;
                Log.m3252e(stringBuilder, "Caught IOException in convertStreamToString()", e);
                return null;
            } finally {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    Log.m3252e(TAG, "Caught IOException in convertStreamToString()", e2);
                    return null;
                }
            }
        }
        return stringBuilder.toString();
    }

    private void addFormField(StringBuilder stringBuilder, String str, String str2, String str3) {
        stringBuilder.append("--").append(str3).append("\r\n").append("Content-Disposition: form-data; name=\"").append(str).append("\"").append("\r\n").append("Content-Type: text/plain; charset=").append("UTF-8").append("\r\n").append("\r\n").append(str2).append("\r\n");
    }

    private void addFilePart(StringBuilder stringBuilder, String str, byte[] bArr, String str2, OutputStream outputStream) throws IOException {
        stringBuilder.append("--").append(str2).append("\r\n").append("Content-Disposition: form-data; name=\"").append(str).append("\"; filename=\"").append(str).append("\"").append("\r\n").append("Content-Type: ").append("application/octet-stream").append("\r\n").append("Content-Transfer-Encoding: binary").append("\r\n").append("\r\n");
        outputStream.write(stringBuilder.toString().getBytes());
        outputStream.write(bArr);
        outputStream.write("\r\n".getBytes());
    }

    private void finishWrite(OutputStream outputStream, String str) throws IOException {
        outputStream.write("\r\n".getBytes());
        outputStream.write(("--" + str + "--").getBytes());
        outputStream.write("\r\n".getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
