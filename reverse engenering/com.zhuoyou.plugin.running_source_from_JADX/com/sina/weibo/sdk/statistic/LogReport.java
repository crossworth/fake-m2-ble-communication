package com.sina.weibo.sdk.statistic;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.sina.weibo.sdk.net.HttpManager;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.MD5;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.common.SocializeConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class LogReport {
    private static final int CONNECTION_TIMEOUT = 25000;
    private static final String PRIVATE_CODE = "dqwef1864il4c9m6";
    private static final int SOCKET_TIMEOUT = 20000;
    private static String UPLOADTIME = "uploadtime";
    private static String mAid;
    private static String mAppkey;
    private static String mBaseUrl = "https://api.weibo.com/2/proxy/sdk/statistic.json";
    private static String mChannel;
    private static String mKeyHash;
    public static LogReport mLogReport;
    private static String mPackageName;
    private static JSONObject mParams;
    private static String mVersionName;

    public LogReport(Context context) {
        try {
            if (mPackageName == null) {
                mPackageName = context.getPackageName();
            }
            mAppkey = StatisticConfig.getAppkey(context);
            checkAid(context);
            mKeyHash = Utility.getSign(context, mPackageName);
            mVersionName = LogBuilder.getVersion(context);
            mChannel = StatisticConfig.getChannel(context);
        } catch (Exception ex) {
            LogUtil.m3308e(WBAgent.TAG, ex.toString());
        }
        initCommonParams();
    }

    private static JSONObject initCommonParams() {
        if (mParams == null) {
            mParams = new JSONObject();
        }
        try {
            mParams.put(LogBuilder.KEY_APPKEY, mAppkey);
            mParams.put("platform", SocializeConstants.OS);
            mParams.put("packagename", mPackageName);
            mParams.put("key_hash", mKeyHash);
            mParams.put("version", mVersionName);
            mParams.put(LogBuilder.KEY_CHANNEL, mChannel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mParams;
    }

    private static void checkAid(Context context) {
        if (TextUtils.isEmpty(mAid)) {
            mAid = Utility.getAid(context, mAppkey);
        }
        if (mParams == null) {
            mParams = new JSONObject();
        }
        try {
            mParams.put("aid", mAid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setPackageName(String mPackageName) {
        mPackageName = mPackageName;
    }

    public static String getPackageName() {
        return mPackageName;
    }

    public static synchronized void uploadAppLogs(Context context, String memoryLogs) {
        synchronized (LogReport.class) {
            if (mLogReport == null) {
                mLogReport = new LogReport(context);
            }
            if (isNetworkConnected(context)) {
                List<JSONArray> applogs = LogBuilder.getValidUploadLogs(memoryLogs);
                if (applogs == null) {
                    LogUtil.m3309i(WBAgent.TAG, "applogs is null");
                } else {
                    List<JSONArray> failed_logs = new ArrayList();
                    checkAid(context);
                    for (JSONArray applog : applogs) {
                        HttpResponse response = requestHttpExecute(mBaseUrl, Constants.HTTP_POST, mParams, applog);
                        if (response != null && response.getStatusLine().getStatusCode() == 200) {
                            updateTime(context, Long.valueOf(System.currentTimeMillis()));
                        } else {
                            failed_logs.add(applog);
                            LogUtil.m3308e(WBAgent.TAG, "upload applogs error");
                        }
                    }
                    LogFileUtil.delete(LogFileUtil.getAppLogPath(LogFileUtil.ANALYTICS_FILE_NAME));
                    if (failed_logs.size() > 0) {
                        for (JSONArray failed_log : failed_logs) {
                            LogFileUtil.writeToFile(LogFileUtil.getAppLogPath(LogFileUtil.ANALYTICS_FILE_NAME), failed_log.toString(), true);
                            LogUtil.m3307d(WBAgent.TAG, "save failed_log");
                        }
                    }
                }
            } else {
                LogUtil.m3309i(WBAgent.TAG, "network is not connected");
                LogFileUtil.writeToFile(LogFileUtil.getAppLogPath(LogFileUtil.ANALYTICS_FILE_NAME), memoryLogs, true);
            }
        }
    }

    private static HttpResponse requestHttpExecute(String url, String method, JSONObject params, JSONArray applog) {
        UnsupportedEncodingException e;
        Throwable th;
        ClientProtocolException e2;
        IOException e3;
        HttpClient client = null;
        HttpResponse response = null;
        ByteArrayOutputStream baos = null;
        HttpUriRequest request = null;
        try {
            client = HttpManager.getNewHttpClient();
            if (params == null) {
                params = initCommonParams();
            }
            try {
                params.put(LogColumns.TIME, System.currentTimeMillis() / 1000);
                params.put("length", applog.length());
                JSONObject jSONObject = params;
                jSONObject.put("sign", getSign(params.getString("aid"), params.getString(LogBuilder.KEY_APPKEY), params.getLong(LogColumns.TIME)));
                params.put("content", applog);
                LogUtil.m3307d(WBAgent.TAG, "post content--- " + params.toString());
            } catch (JSONException e4) {
                e4.printStackTrace();
            }
            if (method.equals(Constants.HTTP_GET)) {
                request = new HttpGet(new StringBuilder(String.valueOf(url)).append("?").append(params.toString()).toString());
            } else {
                if (method.equals(Constants.HTTP_POST)) {
                    if (TextUtils.isEmpty(mAppkey)) {
                        LogUtil.m3308e(WBAgent.TAG, "unexpected null AppKey");
                        if (baos != null) {
                            try {
                                baos.close();
                            } catch (IOException e5) {
                            }
                        }
                        shutdownHttpClient(client);
                        return null;
                    }
                    HttpPost post = getNewHttpPost(new StringBuilder(String.valueOf(url)).append("?").append("source=").append(mAppkey).toString(), params);
                    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                    try {
                        if (StatisticConfig.isNeedGizp()) {
                            baos2.write(gzipLogs(params.toString()));
                        } else {
                            baos2.write(params.toString().getBytes());
                        }
                        post.setEntity(new ByteArrayEntity(baos2.toByteArray()));
                        Object request2 = post;
                        baos = baos2;
                    } catch (UnsupportedEncodingException e6) {
                        e = e6;
                        baos = baos2;
                        try {
                            e.printStackTrace();
                            if (baos != null) {
                                try {
                                    baos.close();
                                } catch (IOException e7) {
                                }
                            }
                            shutdownHttpClient(client);
                            return response;
                        } catch (Throwable th2) {
                            th = th2;
                            if (baos != null) {
                                try {
                                    baos.close();
                                } catch (IOException e8) {
                                }
                            }
                            shutdownHttpClient(client);
                            throw th;
                        }
                    } catch (ClientProtocolException e9) {
                        e2 = e9;
                        baos = baos2;
                        e2.printStackTrace();
                        if (baos != null) {
                            try {
                                baos.close();
                            } catch (IOException e10) {
                            }
                        }
                        shutdownHttpClient(client);
                        return response;
                    } catch (IOException e11) {
                        e3 = e11;
                        baos = baos2;
                        e3.printStackTrace();
                        if (baos != null) {
                            try {
                                baos.close();
                            } catch (IOException e12) {
                            }
                        }
                        shutdownHttpClient(client);
                        return response;
                    } catch (Throwable th3) {
                        th = th3;
                        baos = baos2;
                        if (baos != null) {
                            baos.close();
                        }
                        shutdownHttpClient(client);
                        throw th;
                    }
                }
            }
            response = client.execute(request);
            LogUtil.m3309i(WBAgent.TAG, "status code = " + response.getStatusLine().getStatusCode());
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e13) {
                }
            }
            shutdownHttpClient(client);
        } catch (UnsupportedEncodingException e14) {
            e = e14;
            e.printStackTrace();
            if (baos != null) {
                baos.close();
            }
            shutdownHttpClient(client);
            return response;
        } catch (ClientProtocolException e15) {
            e2 = e15;
            e2.printStackTrace();
            if (baos != null) {
                baos.close();
            }
            shutdownHttpClient(client);
            return response;
        } catch (IOException e16) {
            e3 = e16;
            e3.printStackTrace();
            if (baos != null) {
                baos.close();
            }
            shutdownHttpClient(client);
            return response;
        }
        return response;
    }

    private static boolean isNetworkConnected(Context context) {
        if (context == null) {
            LogUtil.m3308e(WBAgent.TAG, "unexpected null context in isNetworkConnected");
            return false;
        } else if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
            return false;
        } else {
            NetworkInfo info = null;
            try {
                info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            } catch (NullPointerException e) {
            }
            if (info == null || !info.isAvailable()) {
                return false;
            }
            return true;
        }
    }

    private static synchronized HttpPost getNewHttpPost(String url, JSONObject params) {
        HttpPost httpPost;
        synchronized (LogReport.class) {
            httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Connection", "Keep-Alive");
            httpPost.addHeader(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_CONTENT_ENCODING, StatisticConfig.isNeedGizp() ? com.tyd.aidlservice.internal.Constants.GZIP_TAG : "charset=UTF-8");
            httpPost.addHeader("Accept", "*/*");
            httpPost.addHeader("Accept-Language", "en-us");
            httpPost.addHeader("Accept-Encoding", com.tyd.aidlservice.internal.Constants.GZIP_TAG);
        }
        return httpPost;
    }

    private static String getSign(String aid, String appkey, long time) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(aid)) {
            sb.append(aid);
        }
        sb.append(appkey).append(PRIVATE_CODE).append(time);
        String oriData = MD5.hexdigest(sb.toString());
        String md5_key = oriData.substring(oriData.length() - 6);
        String md5_sign = MD5.hexdigest(new StringBuilder(String.valueOf(md5_key)).append(md5_key.substring(0, 4)).toString());
        return new StringBuilder(String.valueOf(md5_key)).append(md5_sign.substring(md5_sign.length() - 1)).toString();
    }

    private static byte[] gzipLogs(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte[] logs = str.getBytes("utf-8");
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(logs);
            gzip.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return out.toByteArray();
    }

    public static long getTime(Context context) {
        return context.getSharedPreferences(UPLOADTIME, 0).getLong("lasttime", 0);
    }

    private static void updateTime(Context context, Long time) {
        Editor editor = context.getSharedPreferences(UPLOADTIME, 0).edit();
        editor.putLong("lasttime", time.longValue());
        editor.commit();
    }

    private static void shutdownHttpClient(HttpClient client) {
        if (client != null) {
            try {
                client.getConnectionManager().closeExpiredConnections();
            } catch (Exception e) {
            }
        }
    }
}
