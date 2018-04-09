package com.umeng.socialize.net.utils;

import android.net.Uri.Builder;
import android.text.TextUtils;
import com.tyd.aidlservice.internal.Constants;
import com.umeng.socialize.Config;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.utils.URequest.FilePair;
import com.umeng.socialize.utils.Log;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.net.ssl.HttpsURLConnection;
import no.nordicsemi.android.dfu.DfuBaseService;
import org.json.JSONObject;

public class UClient {
    private static final String END = "\r\n";
    private static final String TAG = "UClient";
    private Map<String, String> mHeaders;
    private StringBuilder mRequestInfo;

    protected static class ResponseObj {
        public int httpResponseCode;
        public JSONObject jsonObject;

        protected ResponseObj() {
        }
    }

    protected java.lang.String convertStreamToString(java.io.InputStream r6) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:17:? in {5, 10, 12, 15, 16, 19, 20} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.rerun(BlockProcessor.java:44)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:57)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r5 = this;
        r1 = new java.io.InputStreamReader;
        r1.<init>(r6);
        r2 = new java.io.BufferedReader;
        r0 = 512; // 0x200 float:7.175E-43 double:2.53E-321;
        r2.<init>(r1, r0);
        r0 = new java.lang.StringBuilder;
        r0.<init>();
    L_0x0011:
        r3 = r2.readLine();	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
        if (r3 == 0) goto L_0x003e;	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
    L_0x0017:
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
        r4.<init>();	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
        r3 = r4.append(r3);	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
        r4 = "\n";	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
        r3 = r3.toString();	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
        r0.append(r3);	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
        goto L_0x0011;
    L_0x002e:
        r0 = move-exception;
        r3 = "UClient";	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
        r4 = "Caught IOException in convertStreamToString()";	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
        com.umeng.socialize.utils.Log.m4550e(r3, r4, r0);	 Catch:{ IOException -> 0x002e, all -> 0x0049 }
        r0 = 0;
        r5.closeQuietly(r1);
        r5.closeQuietly(r2);
    L_0x003d:
        return r0;
    L_0x003e:
        r5.closeQuietly(r1);
        r5.closeQuietly(r2);
        r0 = r0.toString();
        goto L_0x003d;
    L_0x0049:
        r0 = move-exception;
        r5.closeQuietly(r1);
        r5.closeQuietly(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.net.utils.UClient.convertStreamToString(java.io.InputStream):java.lang.String");
    }

    public <T extends UResponse> T execute(URequest uRequest, Class<T> cls) {
        uRequest.onPrepareRequest();
        String trim = uRequest.getHttpMethod().trim();
        verifyMethod(trim);
        this.mRequestInfo = new StringBuilder();
        ResponseObj responseObj = null;
        if (URequest.GET.equals(trim)) {
            responseObj = httpGetRequest(uRequest);
        } else if (URequest.POST.equals(trim)) {
            responseObj = httpPostRequest(uRequest.mBaseUrl, uRequest);
        }
        return createResponse(responseObj, cls);
    }

    protected <T extends UResponse> T createResponse(ResponseObj responseObj, Class<T> cls) {
        if (responseObj == null || responseObj.jsonObject == null) {
            return null;
        }
        try {
            return (UResponse) cls.getConstructor(new Class[]{JSONObject.class}).newInstance(new Object[]{responseObj.jsonObject});
        } catch (Exception e) {
            Log.m4550e(TAG, "SecurityException", e);
            return null;
        } catch (Exception e2) {
            Log.m4550e(TAG, "NoSuchMethodException", e2);
            return null;
        } catch (Exception e22) {
            Log.m4550e(TAG, "IllegalArgumentException", e22);
            return null;
        } catch (Exception e222) {
            Log.m4550e(TAG, "InstantiationException", e222);
            return null;
        } catch (Exception e2222) {
            Log.m4550e(TAG, "IllegalAccessException", e2222);
            return null;
        } catch (Exception e22222) {
            Log.m4550e(TAG, "InvocationTargetException", e22222);
            return null;
        }
    }

    private ResponseObj httpPostRequest(String str, URequest uRequest) {
        String str2;
        Closeable closeable;
        Exception e;
        Closeable closeable2;
        Throwable th;
        Closeable closeable3 = null;
        if (uRequest.toJson() == null) {
            str2 = "";
        } else {
            str2 = uRequest.toJson().toString();
        }
        int nextInt = new Random().nextInt(1000);
        Log.m4552i(TAG, nextInt + "post request:" + str + System.getProperty("line.separator") + str2);
        String uuid = UUID.randomUUID().toString();
        HttpURLConnection openUrlConnection;
        try {
            openUrlConnection = openUrlConnection(uRequest);
            if (openUrlConnection == null) {
                closeQuietly(null);
                closeQuietly(null);
                if (openUrlConnection != null) {
                    openUrlConnection.disconnect();
                }
                return null;
            }
            try {
                Map bodyPair = uRequest.getBodyPair();
                OutputStream outputStream;
                if (uRequest.mMimeType != null) {
                    str2 = (String) bodyPair.get("data");
                    openUrlConnection.setRequestProperty("Content-Type", uRequest.mMimeType.toString());
                    outputStream = openUrlConnection.getOutputStream();
                    try {
                        outputStream.write(str2.getBytes());
                        closeable = outputStream;
                    } catch (IOException e2) {
                        e = e2;
                        closeable = closeable2;
                        closeable2 = null;
                        try {
                            Log.m4550e(TAG, "Caught Exception in httpPostRequest()", e);
                            closeQuietly(closeable2);
                            closeQuietly(closeable);
                            if (openUrlConnection != null) {
                                openUrlConnection.disconnect();
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            closeable3 = closeable2;
                            closeQuietly(closeable3);
                            closeQuietly(closeable);
                            if (openUrlConnection != null) {
                                openUrlConnection.disconnect();
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        closeable = closeable2;
                        closeQuietly(closeable3);
                        closeQuietly(closeable);
                        if (openUrlConnection != null) {
                            openUrlConnection.disconnect();
                        }
                        throw th;
                    }
                }
                Object obj;
                if (bodyPair != null) {
                    if (bodyPair.size() > 0) {
                        openUrlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + uuid);
                        outputStream = openUrlConnection.getOutputStream();
                        addBodyParams(uRequest, outputStream, uuid);
                        obj = outputStream;
                    }
                }
                openUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                Builder builder = new Builder();
                builder.appendQueryParameter("content", str2);
                str2 = builder.build().getEncodedQuery();
                outputStream = new DataOutputStream(openUrlConnection.getOutputStream());
                try {
                    outputStream.write(str2.getBytes());
                    obj = outputStream;
                } catch (IOException e3) {
                    e = e3;
                    obj = outputStream;
                    closeable2 = null;
                    Log.m4550e(TAG, "Caught Exception in httpPostRequest()", e);
                    closeQuietly(closeable2);
                    closeQuietly(closeable);
                    if (openUrlConnection != null) {
                        openUrlConnection.disconnect();
                    }
                    return null;
                } catch (Throwable th4) {
                    th = th4;
                    obj = outputStream;
                    closeQuietly(closeable3);
                    closeQuietly(closeable);
                    if (openUrlConnection != null) {
                        openUrlConnection.disconnect();
                    }
                    throw th;
                }
                try {
                    closeable.flush();
                    nextInt = openUrlConnection.getResponseCode();
                    ResponseObj responseObj = new ResponseObj();
                    responseObj.httpResponseCode = nextInt;
                    if (nextInt == 200) {
                        closeable2 = openUrlConnection.getInputStream();
                        try {
                            JSONObject parseResult = parseResult(openUrlConnection.getRequestMethod(), openUrlConnection.getContentEncoding(), closeable2);
                            Log.m4546d(TAG, "requestMethod:POST;json data:" + parseResult);
                            responseObj.jsonObject = parseResult;
                            closeQuietly(closeable2);
                            closeQuietly(closeable);
                            if (openUrlConnection == null) {
                                return responseObj;
                            }
                            openUrlConnection.disconnect();
                            return responseObj;
                        } catch (IOException e4) {
                            e = e4;
                            Log.m4550e(TAG, "Caught Exception in httpPostRequest()", e);
                            closeQuietly(closeable2);
                            closeQuietly(closeable);
                            if (openUrlConnection != null) {
                                openUrlConnection.disconnect();
                            }
                            return null;
                        }
                    }
                    closeQuietly(null);
                    closeQuietly(closeable);
                    if (openUrlConnection != null) {
                        openUrlConnection.disconnect();
                    }
                    return null;
                } catch (IOException e5) {
                    e = e5;
                    closeable2 = null;
                    Log.m4550e(TAG, "Caught Exception in httpPostRequest()", e);
                    closeQuietly(closeable2);
                    closeQuietly(closeable);
                    if (openUrlConnection != null) {
                        openUrlConnection.disconnect();
                    }
                    return null;
                } catch (Throwable th5) {
                    th = th5;
                    closeQuietly(closeable3);
                    closeQuietly(closeable);
                    if (openUrlConnection != null) {
                        openUrlConnection.disconnect();
                    }
                    throw th;
                }
            } catch (IOException e6) {
                e = e6;
                closeable2 = null;
                closeable = null;
                Log.m4550e(TAG, "Caught Exception in httpPostRequest()", e);
                closeQuietly(closeable2);
                closeQuietly(closeable);
                if (openUrlConnection != null) {
                    openUrlConnection.disconnect();
                }
                return null;
            } catch (Throwable th6) {
                th = th6;
                closeable = null;
                closeQuietly(closeable3);
                closeQuietly(closeable);
                if (openUrlConnection != null) {
                    openUrlConnection.disconnect();
                }
                throw th;
            }
        } catch (IOException e7) {
            e = e7;
            closeable2 = null;
            closeable = null;
            openUrlConnection = null;
            Log.m4550e(TAG, "Caught Exception in httpPostRequest()", e);
            closeQuietly(closeable2);
            closeQuietly(closeable);
            if (openUrlConnection != null) {
                openUrlConnection.disconnect();
            }
            return null;
        } catch (Throwable th7) {
            th = th7;
            closeable = null;
            openUrlConnection = null;
            closeQuietly(closeable3);
            closeQuietly(closeable);
            if (openUrlConnection != null) {
                openUrlConnection.disconnect();
            }
            throw th;
        }
    }

    private ResponseObj httpGetRequest(URequest uRequest) {
        Closeable inputStream;
        Exception e;
        Throwable th;
        Object obj;
        ResponseObj responseObj = null;
        HttpURLConnection openUrlConnection;
        try {
            if (uRequest.toGetUrl().length() <= 1) {
                Log.m4549e(TAG, new Random().nextInt(1000) + "get request:Invalid baseUrl.");
                closeQuietly(responseObj);
                if (responseObj == null) {
                    return responseObj;
                }
                responseObj.disconnect();
                return responseObj;
            }
            openUrlConnection = openUrlConnection(uRequest);
            if (openUrlConnection == null) {
                closeQuietly(responseObj);
                if (openUrlConnection == null) {
                    return responseObj;
                }
                openUrlConnection.disconnect();
                return responseObj;
            }
            try {
                int responseCode = openUrlConnection.getResponseCode();
                ResponseObj responseObj2 = new ResponseObj();
                responseObj2.httpResponseCode = responseCode;
                if (responseCode == 200) {
                    inputStream = openUrlConnection.getInputStream();
                    try {
                        JSONObject parseResult = parseResult(openUrlConnection.getRequestMethod(), openUrlConnection.getContentEncoding(), inputStream);
                        Log.m4546d(TAG, "requestMethod:GET;json data:" + parseResult);
                        responseObj2.jsonObject = parseResult;
                        closeQuietly(inputStream);
                        if (openUrlConnection != null) {
                            openUrlConnection.disconnect();
                        }
                        return responseObj2;
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            Log.m4550e(TAG, "Caught Exception in httpGetRequest()", e);
                            closeQuietly(inputStream);
                            if (openUrlConnection != null) {
                                return responseObj;
                            }
                            openUrlConnection.disconnect();
                            return responseObj;
                        } catch (Throwable th2) {
                            th = th2;
                            closeQuietly(inputStream);
                            if (openUrlConnection != null) {
                                openUrlConnection.disconnect();
                            }
                            throw th;
                        }
                    }
                }
                closeQuietly(responseObj);
                if (openUrlConnection == null) {
                    return responseObj;
                }
                openUrlConnection.disconnect();
                return responseObj;
            } catch (Exception e3) {
                e = e3;
                obj = responseObj;
                Log.m4550e(TAG, "Caught Exception in httpGetRequest()", e);
                closeQuietly(inputStream);
                if (openUrlConnection != null) {
                    return responseObj;
                }
                openUrlConnection.disconnect();
                return responseObj;
            } catch (Throwable th3) {
                obj = responseObj;
                th = th3;
                closeQuietly(inputStream);
                if (openUrlConnection != null) {
                    openUrlConnection.disconnect();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            openUrlConnection = responseObj;
            inputStream = responseObj;
            Log.m4550e(TAG, "Caught Exception in httpGetRequest()", e);
            closeQuietly(inputStream);
            if (openUrlConnection != null) {
                return responseObj;
            }
            openUrlConnection.disconnect();
            return responseObj;
        } catch (Throwable th32) {
            openUrlConnection = responseObj;
            inputStream = responseObj;
            th = th32;
            closeQuietly(inputStream);
            if (openUrlConnection != null) {
                openUrlConnection.disconnect();
            }
            throw th;
        }
    }

    private HttpURLConnection openUrlConnection(URequest uRequest) throws IOException {
        Object toGetUrl;
        String trim = uRequest.getHttpMethod().trim();
        if (URequest.GET.equals(trim)) {
            toGetUrl = uRequest.toGetUrl();
        } else if (URequest.POST.equals(trim)) {
            toGetUrl = uRequest.mBaseUrl;
        } else {
            toGetUrl = null;
        }
        if (TextUtils.isEmpty(toGetUrl)) {
            return null;
        }
        HttpURLConnection httpURLConnection;
        URL url = new URL(toGetUrl);
        boolean z = false;
        if ("https".equals(url.getProtocol())) {
            z = true;
        }
        if (z) {
            httpURLConnection = (HttpsURLConnection) url.openConnection();
        } else {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        httpURLConnection.setConnectTimeout(Config.connectionTimeOut);
        httpURLConnection.setReadTimeout(Config.readSocketTimeOut);
        httpURLConnection.setRequestMethod(trim);
        if (URequest.GET.equals(trim)) {
            httpURLConnection.setRequestProperty("Accept-Encoding", Constants.GZIP_TAG);
            if (this.mHeaders != null && this.mHeaders.size() > 0) {
                for (String str : this.mHeaders.keySet()) {
                    httpURLConnection.setRequestProperty(str, (String) this.mHeaders.get(str));
                }
            }
        } else if (URequest.POST.equals(trim)) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
        }
        return httpURLConnection;
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

    private void addBodyParams(URequest uRequest, OutputStream outputStream, String str) throws IOException {
        Object obj;
        StringBuilder stringBuilder = new StringBuilder();
        Map bodyPair = uRequest.getBodyPair();
        for (String str2 : bodyPair.keySet()) {
            if (bodyPair.get(str2) != null) {
                addFormField(stringBuilder, str2, bodyPair.get(str2).toString(), str);
            }
        }
        if (stringBuilder.length() > 0) {
            OutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.write(stringBuilder.toString().getBytes());
            outputStream = dataOutputStream;
            obj = 1;
        } else {
            obj = null;
        }
        Map filePair = uRequest.getFilePair();
        if (filePair != null && filePair.size() > 0) {
            Object obj2 = obj;
            for (String str22 : filePair.keySet()) {
                FilePair filePair2 = (FilePair) filePair.get(str22);
                byte[] bArr = filePair2.mBinaryData;
                if (bArr != null && bArr.length >= 1) {
                    addFilePart(filePair2.mFileName, bArr, str, outputStream);
                    obj2 = 1;
                }
            }
            obj = obj2;
        }
        if (obj != null) {
            finishWrite(outputStream, str);
        }
    }

    private void addFormField(StringBuilder stringBuilder, String str, String str2, String str3) {
        stringBuilder.append("--").append(str3).append(END).append("Content-Disposition: form-data; name=\"").append(str).append("\"").append(END).append("Content-Type: text/plain; charset=").append("UTF-8").append(END).append(END).append(str2).append(END);
    }

    private void addFilePart(String str, byte[] bArr, String str2, OutputStream outputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--").append(str2).append(END).append("Content-Disposition: form-data; name=\"").append(SocializeConstants.KEY_PIC).append("\"; filename=\"").append(str).append("\"").append(END).append("Content-Type: ").append(DfuBaseService.MIME_TYPE_OCTET_STREAM).append(END).append("Content-Transfer-Encoding: binary").append(END).append(END);
        outputStream.write(stringBuilder.toString().getBytes());
        outputStream.write(bArr);
        outputStream.write(END.getBytes());
    }

    private void finishWrite(OutputStream outputStream, String str) throws IOException {
        outputStream.write(END.getBytes());
        outputStream.write(("--" + str + "--").getBytes());
        outputStream.write(END.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    protected JSONObject parseResult(String str, String str2, InputStream inputStream) {
        JSONObject jSONObject;
        Exception e;
        Throwable th;
        Closeable wrapStream;
        try {
            wrapStream = wrapStream(str2, inputStream);
            try {
                String convertStreamToString = convertStreamToString(wrapStream);
                Log.m4546d(TAG, "requestMethod:" + str + ";origin data:" + convertStreamToString);
                if (com.tencent.connect.common.Constants.HTTP_POST.equals(str)) {
                    try {
                        jSONObject = new JSONObject(convertStreamToString);
                        closeQuietly(wrapStream);
                        return jSONObject;
                    } catch (Exception e2) {
                        jSONObject = decryptData(convertStreamToString);
                        closeQuietly(wrapStream);
                        return jSONObject;
                    }
                } else if (com.tencent.connect.common.Constants.HTTP_GET.equals(str)) {
                    jSONObject = decryptData(convertStreamToString);
                    closeQuietly(wrapStream);
                    return jSONObject;
                } else {
                    closeQuietly(wrapStream);
                    return null;
                }
            } catch (IOException e3) {
                e = e3;
                try {
                    Log.m4550e(TAG, "Caught IOException in parseResult()", e);
                    closeQuietly(wrapStream);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    closeQuietly(wrapStream);
                    throw th;
                }
            }
        } catch (IOException e4) {
            e = e4;
            wrapStream = null;
            Log.m4550e(TAG, "Caught IOException in parseResult()", e);
            closeQuietly(wrapStream);
            return null;
        } catch (Throwable th3) {
            th = th3;
            wrapStream = null;
            closeQuietly(wrapStream);
            throw th;
        }
    }

    protected InputStream wrapStream(String str, InputStream inputStream) throws IOException {
        if (str == null || "identity".equalsIgnoreCase(str)) {
            return inputStream;
        }
        if (Constants.GZIP_TAG.equalsIgnoreCase(str)) {
            return new GZIPInputStream(inputStream);
        }
        if ("deflate".equalsIgnoreCase(str)) {
            return new InflaterInputStream(inputStream, new Inflater(false), 512);
        }
        throw new RuntimeException("unsupported content-encoding: " + str);
    }

    private JSONObject decryptData(String str) {
        try {
            return new JSONObject(AesHelper.decryptNoPadding(str, "UTF-8").trim());
        } catch (Exception e) {
            Log.m4550e(TAG, "Caught Exception in decryptData()", e);
            return null;
        }
    }

    protected void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                Log.m4550e(TAG, "Caught IOException in closeQuietly()", e);
            }
        }
    }
}
