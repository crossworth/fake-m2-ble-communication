package com.sina.weibo.sdk.statistic;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.sina.weibo.sdk.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class LogBuilder {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$sina$weibo$sdk$statistic$LogType = null;
    private static final String APPKEY = "WEIBO_APPKEY";
    private static final String CHANNEL = "WEIBO_CHANNEL";
    public static final String KEY_AID = "aid";
    public static final String KEY_APPKEY = "appkey";
    public static final String KEY_CHANNEL = "channel";
    private static final String KEY_DURATION = "duration";
    public static final String KEY_END_TIME = "endtime";
    private static final String KEY_EVENT_ID = "event_id";
    private static final String KEY_EXTEND = "extend";
    public static final String KEY_HASH = "key_hash";
    public static final String KEY_PACKAGE_NAME = "packagename";
    private static final String KEY_PAGE_ID = "page_id";
    public static final String KEY_PLATFORM = "platform";
    public static final String KEY_START_TIME = "starttime";
    private static final String KEY_TIME = "time";
    public static final String KEY_TYPE = "type";
    public static final String KEY_VERSION = "version";
    private static final int MAX_COUNT = 500;
    public static final long MAX_INTERVAL = 86400000;

    static /* synthetic */ int[] $SWITCH_TABLE$com$sina$weibo$sdk$statistic$LogType() {
        int[] iArr = $SWITCH_TABLE$com$sina$weibo$sdk$statistic$LogType;
        if (iArr == null) {
            iArr = new int[LogType.values().length];
            try {
                iArr[LogType.ACTIVITY.ordinal()] = 5;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[LogType.EVENT.ordinal()] = 4;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[LogType.FRAGMENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[LogType.SESSION_END.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[LogType.SESSION_START.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            $SWITCH_TABLE$com$sina$weibo$sdk$statistic$LogType = iArr;
        }
        return iArr;
    }

    LogBuilder() {
    }

    public static String getAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (appInfo != null) {
                Object appkey = appInfo.metaData.get(APPKEY);
                if (appkey != null) {
                    LogUtil.m3309i(WBAgent.TAG, "APPKEY: " + String.valueOf(appkey));
                    return String.valueOf(appkey);
                }
                LogUtil.m3308e(WBAgent.TAG, "Could not read WEIBO_APPKEY meta-data from AndroidManifest.xml.");
            }
        } catch (Exception ex) {
            LogUtil.m3308e(WBAgent.TAG, "Could not read WEIBO_APPKEY meta-data from AndroidManifest.xml." + ex);
        }
        return null;
    }

    public static String getChannel(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (appInfo != null) {
                String str = appInfo.metaData.getString(CHANNEL);
                if (str != null) {
                    LogUtil.m3309i(WBAgent.TAG, "CHANNEL: " + str.trim());
                    return str.trim();
                }
                LogUtil.m3308e(WBAgent.TAG, "Could not read WEIBO_CHANNEL meta-data from AndroidManifest.xml.");
            }
        } catch (Exception ex) {
            LogUtil.m3308e(WBAgent.TAG, "Could not read WEIBO_CHANNEL meta-data from AndroidManifest.xml." + ex);
        }
        return null;
    }

    public static String getVersion(Context context) {
        try {
            PackageInfo pkg = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            LogUtil.m3309i(WBAgent.TAG, "versionName: " + pkg.versionName);
            return pkg.versionName;
        } catch (NameNotFoundException ex) {
            LogUtil.m3308e(WBAgent.TAG, "Could not read versionName from AndroidManifest.xml." + ex);
            return null;
        }
    }

    public static String getPageLogs(List<PageLog> pages) {
        StringBuilder logs = new StringBuilder();
        for (PageLog page : pages) {
            logs.append(getLogInfo(page).toString()).append(",");
        }
        return logs.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static org.json.JSONObject getLogInfo(com.sina.weibo.sdk.statistic.PageLog r8) {
        /*
        r1 = new org.json.JSONObject;
        r1.<init>();
        r2 = $SWITCH_TABLE$com$sina$weibo$sdk$statistic$LogType();	 Catch:{ Exception -> 0x002a }
        r3 = r8.getType();	 Catch:{ Exception -> 0x002a }
        r3 = r3.ordinal();	 Catch:{ Exception -> 0x002a }
        r2 = r2[r3];	 Catch:{ Exception -> 0x002a }
        switch(r2) {
            case 1: goto L_0x0017;
            case 2: goto L_0x0040;
            case 3: goto L_0x005f;
            case 4: goto L_0x0087;
            case 5: goto L_0x00a9;
            default: goto L_0x0016;
        };	 Catch:{ Exception -> 0x002a }
    L_0x0016:
        return r1;
    L_0x0017:
        r2 = "type";
        r3 = 0;
        r1.put(r2, r3);	 Catch:{ Exception -> 0x002a }
        r2 = "time";
        r4 = r8.getStartTime();	 Catch:{ Exception -> 0x002a }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r6;
        r1.put(r2, r4);	 Catch:{ Exception -> 0x002a }
        goto L_0x0016;
    L_0x002a:
        r0 = move-exception;
        r2 = "WBAgent";
        r3 = new java.lang.StringBuilder;
        r4 = "get page log error.";
        r3.<init>(r4);
        r3 = r3.append(r0);
        r3 = r3.toString();
        com.sina.weibo.sdk.utils.LogUtil.m3308e(r2, r3);
        goto L_0x0016;
    L_0x0040:
        r2 = "type";
        r3 = 1;
        r1.put(r2, r3);	 Catch:{ Exception -> 0x002a }
        r2 = "time";
        r4 = r8.getEndTime();	 Catch:{ Exception -> 0x002a }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r6;
        r1.put(r2, r4);	 Catch:{ Exception -> 0x002a }
        r2 = "duration";
        r4 = r8.getDuration();	 Catch:{ Exception -> 0x002a }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r6;
        r1.put(r2, r4);	 Catch:{ Exception -> 0x002a }
        goto L_0x0016;
    L_0x005f:
        r2 = "type";
        r3 = 2;
        r1.put(r2, r3);	 Catch:{ Exception -> 0x002a }
        r2 = "page_id";
        r3 = r8.getPage_id();	 Catch:{ Exception -> 0x002a }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x002a }
        r2 = "time";
        r4 = r8.getStartTime();	 Catch:{ Exception -> 0x002a }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r6;
        r1.put(r2, r4);	 Catch:{ Exception -> 0x002a }
        r2 = "duration";
        r4 = r8.getDuration();	 Catch:{ Exception -> 0x002a }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r6;
        r1.put(r2, r4);	 Catch:{ Exception -> 0x002a }
        goto L_0x0016;
    L_0x0087:
        r2 = "type";
        r3 = 3;
        r1.put(r2, r3);	 Catch:{ Exception -> 0x002a }
        r2 = "page_id";
        r3 = r8.getPage_id();	 Catch:{ Exception -> 0x002a }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x002a }
        r2 = "time";
        r4 = r8.getStartTime();	 Catch:{ Exception -> 0x002a }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r6;
        r1.put(r2, r4);	 Catch:{ Exception -> 0x002a }
        r8 = (com.sina.weibo.sdk.statistic.EventLog) r8;	 Catch:{ Exception -> 0x002a }
        addEventData(r1, r8);	 Catch:{ Exception -> 0x002a }
        goto L_0x0016;
    L_0x00a9:
        r2 = "type";
        r3 = 4;
        r1.put(r2, r3);	 Catch:{ Exception -> 0x002a }
        r2 = "page_id";
        r3 = r8.getPage_id();	 Catch:{ Exception -> 0x002a }
        r1.put(r2, r3);	 Catch:{ Exception -> 0x002a }
        r2 = "time";
        r4 = r8.getStartTime();	 Catch:{ Exception -> 0x002a }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r6;
        r1.put(r2, r4);	 Catch:{ Exception -> 0x002a }
        r2 = "duration";
        r4 = r8.getDuration();	 Catch:{ Exception -> 0x002a }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r6;
        r1.put(r2, r4);	 Catch:{ Exception -> 0x002a }
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.statistic.LogBuilder.getLogInfo(com.sina.weibo.sdk.statistic.PageLog):org.json.JSONObject");
    }

    private static JSONObject addEventData(JSONObject json, EventLog event) {
        try {
            json.put(KEY_EVENT_ID, event.getEvent_id());
            if (event.getExtend() != null) {
                Map<String, String> extend = event.getExtend();
                StringBuilder sb = new StringBuilder();
                int count = 0;
                for (String key : extend.keySet()) {
                    if (count >= 10) {
                        break;
                    } else if (!TextUtils.isEmpty((CharSequence) extend.get(key))) {
                        if (sb.length() > 0) {
                            sb.append("|");
                        }
                        sb.append(key).append(":").append((String) extend.get(key));
                        count++;
                    }
                }
                json.put("extend", sb.toString());
            }
        } catch (Exception ex) {
            LogUtil.m3308e(WBAgent.TAG, "add event log error." + ex);
        }
        return json;
    }

    public static List<JSONArray> getValidUploadLogs(String memoryLogs) {
        JSONException e;
        String applogs = buildUploadLogs(memoryLogs);
        if (TextUtils.isEmpty(applogs)) {
            return null;
        }
        List<JSONArray> listValidlogs = new ArrayList();
        JSONArray validlogs = new JSONArray();
        int count = 0;
        long curTime = System.currentTimeMillis();
        try {
            JSONArray jsonLogs = new JSONObject(applogs).getJSONArray("applogs");
            int i = 0;
            JSONArray validlogs2 = validlogs;
            while (i < jsonLogs.length()) {
                try {
                    JSONObject log = jsonLogs.getJSONObject(i);
                    if (!isDataValid(curTime, log.getLong("time") * 1000)) {
                        validlogs = validlogs2;
                    } else if (count < 500) {
                        validlogs2.put(log);
                        count++;
                        validlogs = validlogs2;
                    } else {
                        listValidlogs.add(validlogs2);
                        validlogs = new JSONArray();
                        count = 0;
                    }
                    i++;
                    validlogs2 = validlogs;
                } catch (JSONException e2) {
                    e = e2;
                    validlogs = validlogs2;
                }
            }
            if (validlogs2.length() > 0) {
                listValidlogs.add(validlogs2);
                validlogs = validlogs2;
                return listValidlogs;
            }
            return listValidlogs;
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
            return listValidlogs;
        }
    }

    private static String buildUploadLogs(String memoryLogs) {
        String localLogs = LogFileUtil.getAppLogs(LogFileUtil.getAppLogPath(LogFileUtil.ANALYTICS_FILE_NAME));
        if (TextUtils.isEmpty(localLogs) && TextUtils.isEmpty(memoryLogs)) {
            return null;
        }
        StringBuilder applogs = new StringBuilder();
        applogs.append("{applogs:[");
        if (!TextUtils.isEmpty(localLogs)) {
            applogs.append(localLogs);
        }
        if (!TextUtils.isEmpty(memoryLogs)) {
            applogs.append(memoryLogs);
        }
        if (applogs.charAt(applogs.length() - 1) == ',') {
            applogs.replace(applogs.length() - 1, applogs.length(), "");
        }
        applogs.append("]}");
        return applogs.toString();
    }

    private static boolean isDataValid(long curTime, long actTime) {
        if (curTime - actTime < MAX_INTERVAL) {
            return true;
        }
        return false;
    }
}
