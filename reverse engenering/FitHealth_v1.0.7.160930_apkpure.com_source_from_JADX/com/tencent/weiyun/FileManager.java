package com.tencent.weiyun;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.DataConvert;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.HttpUtils.HttpStatusException;
import com.tencent.utils.HttpUtils.NetworkUnavailableException;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyi.account.constant.UrlConstant;
import com.zhuoyi.system.promotion.util.PromConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.SM;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class FileManager extends BaseApi {
    private static final String[] f4725a = new String[]{"https://graph.qq.com/weiyun/get_photo_list", "https://graph.qq.com/weiyun/get_music_list", "https://graph.qq.com/weiyun/get_video_list"};
    private static final String[] f4726b = new String[]{"https://graph.qq.com/weiyun/delete_photo", "https://graph.qq.com/weiyun/delete_music", "https://graph.qq.com/weiyun/delete_video"};

    /* compiled from: ProGuard */
    private class DownLoadImp {
        private static final String DOWNLOAD_COOKIE_NAME = "dl_cookie_name";
        private static final String DOWNLOAD_COOKIE_VALUE = "dl_cookie_value";
        private static final String DOWNLOAD_ENCRYPT_URL = "dl_encrypt_url";
        private static final String DOWNLOAD_MUSIC_URL = "https://graph.qq.com/weiyun/download_music";
        private static final String DOWNLOAD_PIC_URL = "https://graph.qq.com/weiyun/download_photo";
        private static final int DOWNLOAD_PROGRESS = 1;
        private static final int DOWNLOAD_PROGRESS_DONE = 2;
        private static final String DOWNLOAD_SERVER_HOST = "dl_svr_host";
        private static final String DOWNLOAD_SERVER_PORT = "dl_svr_port";
        private static final String DOWNLOAD_THUMB_SIZE = "dl_thumb_size";
        private static final String DOWNLOAD_THUMB_URL = "https://graph.qq.com/weiyun/get_photo_thumb";
        private static final String DOWNLOAD_VIDEO_URL = "https://graph.qq.com/weiyun/download_video";
        private static final int GET_PERMISSON_DOWN = 0;
        private static final int MAX_ERROR_TIMES = 10;
        private IDownLoadFileCallBack mCallback;
        private Context mContext;
        private String mDownloadCookieName;
        private String mDownloadCookieValue;
        private String mDownloadEncryptUrl;
        private String mDownloadServerHost;
        private int mDownloadServerPort;
        private String mDownloadThumbSize;
        private File mFile;
        private WeiyunFileType mFileType;
        private Handler mHandler;
        private String mSavePath;
        private String mThumbSize;
        private WeiyunFile mWeiyunFile;

        /* compiled from: ProGuard */
        class C08672 extends Thread {
            C08672() {
            }

            public void run() {
                Message obtainMessage;
                Bundle c = FileManager.this.composeCGIParams();
                c.putString("file_id", DownLoadImp.this.mWeiyunFile.getFileId());
                if (!TextUtils.isEmpty(DownLoadImp.this.mThumbSize)) {
                    c.putString("thumb", DownLoadImp.this.mThumbSize);
                }
                try {
                    JSONObject request = HttpUtils.request(FileManager.this.mToken, DownLoadImp.this.mContext, DownLoadImp.this.getDownloadUrl(DownLoadImp.this.mFileType), c, "GET");
                    obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                    obtainMessage.what = 0;
                    obtainMessage.obj = request;
                    DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                } catch (MalformedURLException e) {
                    obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                    obtainMessage.what = -3;
                    obtainMessage.obj = e.getMessage();
                    DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                } catch (IOException e2) {
                    obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                    obtainMessage.obj = e2.getMessage();
                    obtainMessage.what = -2;
                    DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                } catch (JSONException e3) {
                    obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                    obtainMessage.obj = e3.getMessage();
                    obtainMessage.what = -4;
                    DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                } catch (NetworkUnavailableException e4) {
                    obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                    obtainMessage.obj = e4.getMessage();
                    obtainMessage.what = -10;
                    DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                } catch (HttpStatusException e5) {
                    obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                    obtainMessage.obj = e5.getMessage();
                    obtainMessage.what = -9;
                    DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                }
            }
        }

        /* compiled from: ProGuard */
        class C08683 extends Thread {
            C08683() {
            }

            public void run() {
                Message obtainMessage;
                HttpParams basicHttpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(basicHttpParams, UrlConstant.SEDN_MMS_DELAY);
                HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
                HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(basicHttpParams, "UTF-8");
                HttpProtocolParams.setUserAgent(basicHttpParams, "TX_QQF_ANDROID");
                String str = "http://" + DownLoadImp.this.mDownloadServerHost + ":" + DownLoadImp.this.mDownloadServerPort + "/ftn_handler/" + DownLoadImp.this.mDownloadEncryptUrl + "/";
                if (!TextUtils.isEmpty(DownLoadImp.this.mDownloadThumbSize)) {
                    str = str + "?size=" + DownLoadImp.this.mDownloadThumbSize;
                }
                HttpClient defaultHttpClient = new DefaultHttpClient(basicHttpParams);
                InputStream inputStream = null;
                try {
                    InputStream inputStream2;
                    Message obtainMessage2;
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(DownLoadImp.this.mSavePath));
                    byte[] bArr = new byte[262144];
                    int i = 0;
                    if (TextUtils.isEmpty(DownLoadImp.this.mThumbSize)) {
                        long j;
                        if (DownLoadImp.this.mWeiyunFile.getFileSize() > 262144) {
                            j = 262144;
                        } else {
                            j = DownLoadImp.this.mWeiyunFile.getFileSize();
                        }
                        long j2 = j + 0;
                        j = 0;
                        inputStream2 = inputStream;
                        long j3 = j2;
                        while (j3 <= DownLoadImp.this.mWeiyunFile.getFileSize()) {
                            HttpUriRequest httpGet = new HttpGet(str);
                            httpGet.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");
                            httpGet.addHeader("Host", DownLoadImp.this.mDownloadServerHost);
                            httpGet.addHeader("Connection", HTTP.CONN_KEEP_ALIVE);
                            httpGet.addHeader(SM.COOKIE, DownLoadImp.this.mDownloadCookieName + "=" + DownLoadImp.this.mDownloadCookieValue);
                            httpGet.addHeader(HttpHeaders.PRAGMA, "no-cache");
                            httpGet.addHeader("RANGE", "bytes=" + j + SocializeConstants.OP_DIVIDER_MINUS + j3 + "");
                            try {
                                HttpResponse execute = defaultHttpClient.execute(httpGet);
                                Log.i("weiyun_test", "uploadFileToWeiyun doDownloadPic response:" + execute.toString());
                                int statusCode = execute.getStatusLine().getStatusCode();
                                if (statusCode == 200 || statusCode == 206) {
                                    inputStream2 = execute.getEntity().getContent();
                                    while (true) {
                                        int read = inputStream2.read(bArr);
                                        if (read <= 0) {
                                            break;
                                        }
                                        fileOutputStream.write(bArr, 0, read);
                                        j += (long) read;
                                    }
                                    if (DownLoadImp.this.mWeiyunFile.getFileSize() - j3 > 0) {
                                        j3 = (DownLoadImp.this.mWeiyunFile.getFileSize() - j > 262144 ? 262144 : DownLoadImp.this.mWeiyunFile.getFileSize() - j) + j;
                                        Message obtainMessage3 = DownLoadImp.this.mHandler.obtainMessage();
                                        obtainMessage3.what = 1;
                                        obtainMessage3.obj = ((100 * j3) / DownLoadImp.this.mWeiyunFile.getFileSize()) + "";
                                        DownLoadImp.this.mHandler.sendMessage(obtainMessage3);
                                    }
                                }
                            } catch (Exception e) {
                                i++;
                                if (i > 10) {
                                    e.printStackTrace();
                                    Log.e("weiyun_test", "uploadFileToWeiyun doDownloadPic error:" + e.getMessage() + "");
                                    obtainMessage2 = DownLoadImp.this.mHandler.obtainMessage();
                                    obtainMessage2.what = -2;
                                    obtainMessage2.obj = e.getMessage();
                                    DownLoadImp.this.mHandler.sendMessage(obtainMessage2);
                                    break;
                                }
                            }
                        }
                    } else {
                        HttpUriRequest httpGet2 = new HttpGet(str);
                        httpGet2.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");
                        httpGet2.addHeader("Host", DownLoadImp.this.mDownloadServerHost);
                        httpGet2.addHeader("Connection", HTTP.CONN_KEEP_ALIVE);
                        httpGet2.addHeader(SM.COOKIE, DownLoadImp.this.mDownloadCookieName + "=" + DownLoadImp.this.mDownloadCookieValue + "");
                        httpGet2.addHeader(HttpHeaders.PRAGMA, "no-cache");
                        try {
                            HttpResponse execute2 = defaultHttpClient.execute(httpGet2);
                            Log.i("weiyun_test", "uploadFileToWeiyun doDownloadPic response:" + execute2.toString());
                            i = execute2.getStatusLine().getStatusCode();
                            if (i == 200 || i == 206) {
                                inputStream = execute2.getEntity().getContent();
                                while (true) {
                                    int read2 = inputStream.read(bArr);
                                    if (read2 <= 0) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, read2);
                                }
                            }
                            inputStream2 = inputStream;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            Log.e("weiyun_test", "uploadFileToWeiyun doDownloadPic error:" + e2.getMessage() + "");
                            obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                            obtainMessage.what = -2;
                            obtainMessage.obj = e2.getMessage();
                            DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                            try {
                                fileOutputStream.close();
                                inputStream.close();
                                return;
                            } catch (IOException e3) {
                                return;
                            }
                        }
                    }
                    try {
                        fileOutputStream.close();
                        inputStream2.close();
                        obtainMessage2 = DownLoadImp.this.mHandler.obtainMessage();
                        obtainMessage2.what = 2;
                        DownLoadImp.this.mHandler.sendMessage(obtainMessage2);
                    } catch (IOException e4) {
                        obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                        obtainMessage.what = -2;
                        obtainMessage.obj = e4.getMessage();
                        DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                    }
                } catch (FileNotFoundException e5) {
                    obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                    obtainMessage.what = -2;
                    obtainMessage.obj = e5.getMessage();
                    DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                }
            }
        }

        public DownLoadImp(Context context, WeiyunFileType weiyunFileType, WeiyunFile weiyunFile, String str, IDownLoadFileCallBack iDownLoadFileCallBack) {
            this.mContext = context;
            this.mFileType = weiyunFileType;
            this.mWeiyunFile = weiyunFile;
            this.mSavePath = str;
            this.mCallback = iDownLoadFileCallBack;
            this.mHandler = new Handler(this.mContext.getMainLooper(), FileManager.this) {
                public void handleMessage(Message message) {
                    switch (message.what) {
                        case 0:
                            JSONObject jSONObject = (JSONObject) message.obj;
                            try {
                                int i = jSONObject.getInt("ret");
                                if (i != 0) {
                                    DownLoadImp.this.mCallback.onError(new UiError(i, jSONObject.toString(), null));
                                    return;
                                }
                                jSONObject = jSONObject.getJSONObject("data");
                                DownLoadImp.this.mDownloadEncryptUrl = jSONObject.getString(DownLoadImp.DOWNLOAD_ENCRYPT_URL);
                                DownLoadImp.this.mDownloadCookieName = jSONObject.getString(DownLoadImp.DOWNLOAD_COOKIE_NAME);
                                DownLoadImp.this.mDownloadCookieValue = jSONObject.getString(DownLoadImp.DOWNLOAD_COOKIE_VALUE);
                                DownLoadImp.this.mDownloadServerPort = jSONObject.getInt(DownLoadImp.DOWNLOAD_SERVER_PORT);
                                DownLoadImp.this.mDownloadServerHost = jSONObject.getString(DownLoadImp.DOWNLOAD_SERVER_HOST);
                                if (jSONObject.has(DownLoadImp.DOWNLOAD_THUMB_SIZE)) {
                                    DownLoadImp.this.mDownloadThumbSize = jSONObject.getString(DownLoadImp.DOWNLOAD_THUMB_SIZE);
                                }
                                DownLoadImp.this.mCallback.onDownloadStart();
                                DownLoadImp.this.doDownload();
                                return;
                            } catch (JSONException e) {
                                DownLoadImp.this.mCallback.onError(new UiError(-4, e.getMessage(), null));
                                return;
                            }
                        case 1:
                            DownLoadImp.this.mCallback.onDownloadProgress(Integer.parseInt((String) message.obj));
                            return;
                        case 2:
                            DownLoadImp.this.mCallback.onDownloadSuccess(DownLoadImp.this.mSavePath);
                            return;
                        default:
                            DownLoadImp.this.mCallback.onError(new UiError(message.what, (String) message.obj, null));
                            return;
                    }
                }
            };
        }

        public void setThumbSize(String str) {
            this.mThumbSize = str;
        }

        public void start() {
            if (this.mSavePath == null || this.mFileType == null || this.mWeiyunFile == null || this.mWeiyunFile.getFileId() == null) {
                Message obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = -5;
                obtainMessage.obj = new String("");
                this.mHandler.sendMessage(obtainMessage);
                return;
            }
            this.mFile = new File(this.mSavePath);
            if (this.mFile.exists()) {
                obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = -11;
                obtainMessage.obj = new String("");
                this.mHandler.sendMessage(obtainMessage);
                return;
            }
            this.mCallback.onPrepareStart();
            getDownloadPermission();
        }

        private String getDownloadUrl(WeiyunFileType weiyunFileType) {
            if (weiyunFileType == WeiyunFileType.ImageFile) {
                if (this.mThumbSize != null) {
                    return DOWNLOAD_THUMB_URL;
                }
                return DOWNLOAD_PIC_URL;
            } else if (weiyunFileType == WeiyunFileType.MusicFile) {
                return DOWNLOAD_MUSIC_URL;
            } else {
                if (weiyunFileType == WeiyunFileType.VideoFile) {
                    return DOWNLOAD_VIDEO_URL;
                }
                return DOWNLOAD_PIC_URL;
            }
        }

        private void getDownloadPermission() {
            new C08672().start();
        }

        private void doDownload() {
            new C08683().start();
        }
    }

    /* compiled from: ProGuard */
    private class UploadFileImp {
        private static final int GET_PERMISSON_DONE = 0;
        private static final String UPLOAD_IMAGE_URL = "https://graph.qq.com/weiyun/upload_photo";
        private static final String UPLOAD_MUSIC_URL = "https://graph.qq.com/weiyun/upload_music";
        private static final int UPLOAD_PROGRESS = 1;
        private static final int UPLOAD_PROGRESS_DONE = 2;
        private static final String UPLOAD_VIDEO_URL = "https://graph.qq.com/weiyun/upload_video";
        private final IUploadFileCallBack mCallback;
        private final Context mContext;
        private String mFileKey;
        private final String mFilePath;
        private long mFileSize;
        private final WeiyunFileType mFileType;
        private final Handler mHandler;
        private String mHost;
        private String mMD5Hash;
        private byte[] mUKey;

        /* compiled from: ProGuard */
        class C08702 extends Thread {
            C08702() {
            }

            public void run() {
                Message obtainMessage;
                String str = SystemClock.elapsedRealtime() + "__" + Uri.parse(UploadFileImp.this.mFilePath).getLastPathSegment();
                Bundle a = FileManager.this.composeCGIParams();
                a.putString("sha", UploadFileImp.this.mFileKey);
                a.putString(PromConstants.PROM_HTML5_INFO_MD5, UploadFileImp.this.mMD5Hash);
                a.putString("size", UploadFileImp.this.mFileSize + "");
                a.putString("name", str);
                a.putString("upload_type", "control");
                try {
                    JSONObject request = HttpUtils.request(FileManager.this.mToken, UploadFileImp.this.mContext, UploadFileImp.this.getRequestUrl(UploadFileImp.this.mFileType), a, "GET");
                    obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                    obtainMessage.what = 0;
                    obtainMessage.obj = request;
                    UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                } catch (MalformedURLException e) {
                    obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                    obtainMessage.what = -3;
                    obtainMessage.obj = e.getMessage();
                    UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                } catch (IOException e2) {
                    obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                    obtainMessage.obj = e2.getMessage();
                    obtainMessage.what = -2;
                    UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                } catch (JSONException e3) {
                    obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                    obtainMessage.obj = e3.getMessage();
                    obtainMessage.what = -4;
                    UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                } catch (NetworkUnavailableException e4) {
                    obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                    obtainMessage.obj = e4.getMessage();
                    obtainMessage.what = -10;
                    UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                } catch (HttpStatusException e5) {
                    obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                    obtainMessage.obj = e5.getMessage();
                    obtainMessage.what = -9;
                    UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                }
            }
        }

        /* compiled from: ProGuard */
        class C08713 extends Thread {
            C08713() {
            }

            public void run() {
                Message obtainMessage;
                Message obtainMessage2;
                HttpParams basicHttpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(basicHttpParams, UrlConstant.SEDN_MMS_DELAY);
                HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
                HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(basicHttpParams, "UTF-8");
                HttpProtocolParams.setUserAgent(basicHttpParams, "TX_QQF_ANDROID");
                HttpClient defaultHttpClient = new DefaultHttpClient(basicHttpParams);
                byte[] bArr = new byte[131072];
                try {
                    FileInputStream fileInputStream = new FileInputStream(UploadFileImp.this.mFilePath);
                    int i = 0;
                    while (((long) i) < UploadFileImp.this.mFileSize) {
                        try {
                            int read = fileInputStream.read(bArr);
                            byte[] access$1400 = UploadFileImp.this.packPostBody(bArr, read, i);
                            i += read;
                            if (access$1400 != null) {
                                HttpUriRequest httpPost = new HttpPost("http://" + UploadFileImp.this.mHost + "/ftn_handler/?bmd5=" + UploadFileImp.this.mMD5Hash);
                                httpPost.addHeader(HttpHeaders.ACCEPT_ENCODING, "*/*");
                                httpPost.setHeader("Connection", HTTP.CONN_KEEP_ALIVE);
                                httpPost.setHeader(HttpHeaders.PRAGMA, "no-cache");
                                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                                httpPost.setEntity(new ByteArrayEntity(access$1400));
                                try {
                                    read = defaultHttpClient.execute(httpPost).getStatusLine().getStatusCode();
                                } catch (IOException e) {
                                    obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                                    obtainMessage.what = -2;
                                    obtainMessage.obj = "";
                                    UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                                    read = 0;
                                }
                                if (read != 200) {
                                    obtainMessage2 = UploadFileImp.this.mHandler.obtainMessage();
                                    obtainMessage2.what = -9;
                                    obtainMessage2.obj = "";
                                    UploadFileImp.this.mHandler.sendMessage(obtainMessage2);
                                    break;
                                } else if (((long) i) < UploadFileImp.this.mFileSize) {
                                    read = (int) ((((long) i) * 100) / UploadFileImp.this.mFileSize);
                                    Message obtainMessage3 = UploadFileImp.this.mHandler.obtainMessage();
                                    obtainMessage3.what = 1;
                                    obtainMessage3.obj = read + "";
                                    UploadFileImp.this.mHandler.sendMessage(obtainMessage3);
                                } else {
                                    obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                                    obtainMessage.what = 2;
                                    obtainMessage.obj = "";
                                    UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                                }
                            }
                        } catch (IOException e2) {
                            obtainMessage2 = UploadFileImp.this.mHandler.obtainMessage();
                            obtainMessage2.what = -2;
                            obtainMessage2.obj = "";
                            UploadFileImp.this.mHandler.sendMessage(obtainMessage2);
                        }
                    }
                    try {
                        fileInputStream.close();
                    } catch (IOException e3) {
                        Message obtainMessage4 = UploadFileImp.this.mHandler.obtainMessage();
                        obtainMessage4.what = -2;
                        obtainMessage4.obj = e3.getMessage();
                        UploadFileImp.this.mHandler.sendMessage(obtainMessage4);
                    }
                } catch (FileNotFoundException e4) {
                    obtainMessage2 = UploadFileImp.this.mHandler.obtainMessage();
                    obtainMessage2.what = -2;
                    obtainMessage2.obj = "";
                    UploadFileImp.this.mHandler.sendMessage(obtainMessage2);
                }
            }
        }

        public UploadFileImp(Context context, WeiyunFileType weiyunFileType, String str, IUploadFileCallBack iUploadFileCallBack) {
            this.mContext = context;
            this.mFileType = weiyunFileType;
            this.mFilePath = str;
            this.mCallback = iUploadFileCallBack;
            this.mHandler = new Handler(this.mContext.getMainLooper(), FileManager.this) {
                public void handleMessage(Message message) {
                    switch (message.what) {
                        case 0:
                            JSONObject jSONObject = (JSONObject) message.obj;
                            try {
                                int i = jSONObject.getInt("ret");
                                if (i != 0) {
                                    UploadFileImp.this.mCallback.onError(new UiError(i, jSONObject.toString(), null));
                                    return;
                                }
                                jSONObject = jSONObject.getJSONObject("data");
                                UploadFileImp.this.mUKey = DataConvert.string2bytes(jSONObject.getString("csum"));
                                UploadFileImp.this.mHost = jSONObject.getString("host");
                                UploadFileImp.this.mCallback.onUploadStart();
                                UploadFileImp.this.doUpload();
                                return;
                            } catch (Exception e) {
                                UploadFileImp.this.mCallback.onError(new UiError(-4, e.getMessage(), null));
                                return;
                            }
                        case 1:
                            UploadFileImp.this.mCallback.onUploadProgress(Integer.parseInt((String) message.obj));
                            return;
                        case 2:
                            UploadFileImp.this.mCallback.onUploadSuccess();
                            return;
                        default:
                            UploadFileImp.this.mCallback.onError(new UiError(message.what, (String) message.obj, null));
                            return;
                    }
                }
            };
        }

        public void start() {
            if (this.mFilePath == null || !new File(this.mFilePath).exists()) {
                Message obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = -5;
                obtainMessage.obj = new String("");
                this.mHandler.sendMessage(obtainMessage);
                return;
            }
            this.mCallback.onPrepareStart();
            File file = new File(this.mFilePath);
            this.mFileSize = file.length();
            try {
                MessageDigest instance = MessageDigest.getInstance("SHA-1");
                InputStream fileInputStream = new FileInputStream(file);
                DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, instance);
                do {
                } while (digestInputStream.read(new byte[524288]) > 0);
                instance = digestInputStream.getMessageDigest();
                this.mFileKey = DataConvert.toHexString(instance.digest());
                instance.reset();
                fileInputStream.close();
                digestInputStream.close();
                try {
                    instance = MessageDigest.getInstance("MD5");
                    fileInputStream = new FileInputStream(file);
                    DigestInputStream digestInputStream2 = new DigestInputStream(fileInputStream, instance);
                    do {
                    } while (digestInputStream2.read(new byte[524288]) > 0);
                    MessageDigest messageDigest = digestInputStream2.getMessageDigest();
                    this.mMD5Hash = DataConvert.toHexString(messageDigest.digest());
                    messageDigest.reset();
                    fileInputStream.close();
                    digestInputStream2.close();
                    getUploadPermission();
                } catch (Exception e) {
                    obtainMessage = this.mHandler.obtainMessage();
                    obtainMessage.what = -2;
                    obtainMessage.obj = new String("");
                    this.mHandler.sendMessage(obtainMessage);
                }
            } catch (Exception e2) {
                obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = -2;
                obtainMessage.obj = new String("");
                this.mHandler.sendMessage(obtainMessage);
            }
        }

        private String getRequestUrl(WeiyunFileType weiyunFileType) {
            if (weiyunFileType == WeiyunFileType.ImageFile) {
                return UPLOAD_IMAGE_URL;
            }
            if (weiyunFileType == WeiyunFileType.MusicFile) {
                return UPLOAD_MUSIC_URL;
            }
            return UPLOAD_VIDEO_URL;
        }

        private void getUploadPermission() {
            new C08702().start();
        }

        private void doUpload() {
            new C08713().start();
        }

        private byte[] packPostBody(byte[] bArr, int i, int i2) {
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                instance.update(bArr, 0, i);
                this.mMD5Hash = DataConvert.toHexString(instance.digest());
                instance.reset();
                int i3 = i + 340;
                byte[] bArr2 = new byte[((((i3 + 4) + 4) + 4) + 4)];
                int putInt2Bytes = DataConvert.putInt2Bytes(-1412589450, bArr2, 0) + 0;
                putInt2Bytes += DataConvert.putInt2Bytes(1000, bArr2, putInt2Bytes);
                putInt2Bytes += DataConvert.putInt2Bytes(0, bArr2, putInt2Bytes);
                i3 = DataConvert.putInt2Bytes(i3, bArr2, putInt2Bytes) + putInt2Bytes;
                i3 += DataConvert.putShort2Bytes(304, bArr2, i3);
                i3 += DataConvert.putBytes2Bytes(this.mUKey, bArr2, i3);
                i3 += DataConvert.putShort2Bytes(20, bArr2, i3);
                i3 += DataConvert.putString2Bytes(this.mFileKey, bArr2, i3);
                i3 += DataConvert.putInt2Bytes((int) this.mFileSize, bArr2, i3);
                i3 += DataConvert.putInt2Bytes(i2, bArr2, i3);
                i3 += DataConvert.putInt2Bytes(i, bArr2, i3);
                i3 += DataConvert.putBytes2Bytes(bArr, i, bArr2, i3);
                return bArr2;
            } catch (NoSuchAlgorithmException e) {
                Message obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = -2;
                obtainMessage.obj = e.getMessage();
                this.mHandler.sendMessage(obtainMessage);
                return null;
            }
        }
    }

    /* compiled from: ProGuard */
    public enum WeiyunFileType {
        ImageFile(0),
        MusicFile(1),
        VideoFile(2);
        
        private final int mType;

        private WeiyunFileType(int i) {
            this.mType = i;
        }

        public int value() {
            return this.mType;
        }
    }

    /* compiled from: ProGuard */
    private class GetFileListListener implements IUiListener {
        private IGetFileListListener mListener;

        public GetFileListListener(IGetFileListListener iGetFileListListener) {
            this.mListener = iGetFileListListener;
        }

        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            try {
                List arrayList = new ArrayList();
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                if (!jSONObject2.isNull("content")) {
                    JSONArray jSONArray = jSONObject2.getJSONArray("content");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                        arrayList.add(new WeiyunFile(jSONObject3.getString("file_id"), jSONObject3.getString("file_name"), jSONObject3.getString("file_ctime"), (long) jSONObject3.getInt("file_size")));
                    }
                }
                this.mListener.onComplete(arrayList);
            } catch (JSONException e) {
                this.mListener.onError(new UiError(-4, Constants.MSG_JSON_ERROR, jSONObject.toString()));
            }
        }

        public void onError(UiError uiError) {
            this.mListener.onError(uiError);
        }

        public void onCancel() {
        }
    }

    public FileManager(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(context, qQAuth, qQToken);
    }

    public FileManager(Context context, QQToken qQToken) {
        super(context, qQToken);
    }

    public void getFileList(WeiyunFileType weiyunFileType, IGetFileListListener iGetFileListListener) {
        String str = f4725a[weiyunFileType.value()];
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("offset", "0");
        composeCGIParams.putString("number", "100");
        HttpUtils.requestAsync(this.mToken, this.mContext, str, composeCGIParams, "GET", new TempRequestListener(new GetFileListListener(iGetFileListListener)));
    }

    public void deleteFile(WeiyunFileType weiyunFileType, String str, IUiListener iUiListener) {
        String str2 = f4726b[weiyunFileType.value()];
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("file_id", str);
        HttpUtils.requestAsync(this.mToken, this.mContext, str2, composeCGIParams, "GET", new TempRequestListener(iUiListener));
    }

    public void downLoadFile(WeiyunFileType weiyunFileType, WeiyunFile weiyunFile, String str, IDownLoadFileCallBack iDownLoadFileCallBack) {
        new DownLoadImp(this.mContext, weiyunFileType, weiyunFile, str, iDownLoadFileCallBack).start();
    }

    public void downLoadThumb(WeiyunFile weiyunFile, String str, String str2, IDownLoadFileCallBack iDownLoadFileCallBack) {
        DownLoadImp downLoadImp = new DownLoadImp(this.mContext, WeiyunFileType.ImageFile, weiyunFile, str, iDownLoadFileCallBack);
        downLoadImp.setThumbSize(str2);
        downLoadImp.start();
    }

    public void uploadFile(WeiyunFileType weiyunFileType, String str, IUploadFileCallBack iUploadFileCallBack) {
        new UploadFileImp(this.mContext, weiyunFileType, str, iUploadFileCallBack).start();
    }
}
