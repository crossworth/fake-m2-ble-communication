package com.zhuoyi.system.download.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.baidu.location.p000a.C0496b;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyi.system.download.model.DownloadInfo;
import com.zhuoyi.system.download.thread.impl.DownloadApkThread;
import com.zhuoyi.system.download.thread.impl.DownloadHtml5Thread;
import com.zhuoyi.system.network.object.Html5Info;
import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.network.util.NetworkUtils;
import com.zhuoyi.system.promotion.util.NotiUitl;
import com.zhuoyi.system.promotion.util.PromConstants;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.AppInfoUtils;
import com.zhuoyi.system.util.FileUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.ResourceIdUtils;
import com.zhuoyi.system.util.Smith;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.FileConstants;
import com.zhuoyi.system.util.model.MyPackageInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;
import p031u.aly.au;

public class DownloadUtils {
    public static final int MAX_THREAD_COUNT = 2;
    public static final String TAG = "DownloadUtils";
    private static ArrayList<Handler> extraHandlerList = new ArrayList();
    private static Context mContext = null;
    private static DownloadUtils mInstance = null;
    private HashMap<MyPackageInfo, DownloadApkThread> downloadApkThreadMap = new HashMap();
    private HashMap<Integer, Object> downloadNotificationMap = new HashMap();
    private int downloadNotifyId = 90000;
    private HashMap<String, DownloadHtml5Thread> downloadZipThreadMap = new HashMap();
    private byte[] lock = new byte[0];
    private HashMap<MyPackageInfo, DownloadApkThread> selfUpdateThreadMap = new HashMap();
    private LinkedList<DownloadInfo> waitDownloadList = new LinkedList();

    public static class MyNotifyDownloadHandler extends Handler {
        private String apkPath;
        private String appName;
        private Intent clickIntent;
        private int defIconId;
        private String downloadUrl;
        private int iconId;
        private String iconUrl;
        private String md5;
        private int notificationId;
        private String packageName;
        private int position;
        private int progress;
        private int source;
        private String tickerText = "";
        private int version;

        class C10541 extends Thread {
            C10541() {
            }

            public void run() {
                PromUtils.getInstance(DownloadUtils.mContext).install(PromUtils.getRootFlag(DownloadUtils.mContext), MyNotifyDownloadHandler.this.apkPath, new MyPackageInfo(MyNotifyDownloadHandler.this.packageName, MyNotifyDownloadHandler.this.version, MyNotifyDownloadHandler.this.position, MyNotifyDownloadHandler.this.source));
            }
        }

        public MyNotifyDownloadHandler(String appName, String apkPath, String packageName, String iconUrl, int notificationId, int defIconId, int iconId, int version, int position, int source, String downloadUrl, String md5, Intent clickIntent) {
            this.appName = appName;
            this.apkPath = apkPath;
            this.packageName = packageName;
            this.iconUrl = iconUrl;
            this.notificationId = notificationId;
            this.defIconId = defIconId;
            this.iconId = iconId;
            this.version = version;
            this.position = position;
            this.source = source;
            this.clickIntent = clickIntent;
            this.downloadUrl = downloadUrl;
            this.md5 = md5;
            this.tickerText = new StringBuilder(String.valueOf(appName)).append(DownloadUtils.mContext.getString(ResourceIdUtils.getResourceId(DownloadUtils.mContext, "R.string.zy_optimizing"))).toString();
            Logger.m3373e(DownloadUtils.TAG, "notificationId=" + notificationId);
        }

        private String getDownloadInfoString() {
            JSONObject jo = new JSONObject();
            try {
                jo.put("an", this.appName);
                jo.put(C0496b.f2135for, this.apkPath);
                jo.put("pn", this.packageName);
                jo.put("iu", this.iconUrl);
                jo.put("ni", this.notificationId);
                jo.put("ii", this.iconId);
                jo.put("vc", this.version);
                jo.put(SocializeProtocolConstants.PROTOCOL_KEY_SNSACCOUNT_ICON, this.position);
                jo.put("sc", this.source);
                jo.put(au.aI, this.downloadUrl);
                jo.put("m5", this.md5);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jo.toString();
        }

        public void handleMessage(Message msg) {
            Iterator it = DownloadUtils.extraHandlerList.iterator();
            while (it.hasNext()) {
                ((Handler) it.next()).sendMessage(obtainMessage(msg.what, msg.obj));
            }
            switch (msg.what) {
                case 1:
                    Bundle b = msg.obj;
                    if (((int) b.getFloat(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_PROGRESS)) > this.progress) {
                        if (((int) b.getFloat(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_PROGRESS)) != 100) {
                            this.progress = (int) b.getFloat(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_PROGRESS);
                            DownloadUtils.getInstance(DownloadUtils.mContext).showDownloadNotify(this.packageName, this.version, this.notificationId, this.tickerText, this.appName, null, this.defIconId, this.iconId, this.progress, null, this.iconUrl, this.downloadUrl, this.md5, this.position, this.source, this.clickIntent, false);
                            return;
                        }
                        return;
                    }
                    return;
                case 2:
                    DownloadUtils.getInstance(DownloadUtils.mContext).showDownloadNotify(this.packageName, this.version, this.notificationId, null, this.appName, null, this.defIconId, this.iconId, -1, null, this.iconUrl, this.downloadUrl, this.md5, this.position, this.source, null, false);
                    return;
                case 3:
                    DownloadUtils.mInstance.removeDownloadInfo(this.apkPath);
                    if (AppInfoUtils.isOpenInstallRightNow(this.position, this.source)) {
                        Logger.m3373e(DownloadUtils.TAG, "isOpenInstallRightNow");
                        DownloadUtils.getInstance(DownloadUtils.mContext).showDownloadNotify(this.packageName, this.version, this.notificationId, null, null, null, this.defIconId, this.iconId, -2, null, this.iconUrl, this.downloadUrl, this.md5, this.position, this.source, null, false);
                        new C10541().start();
                        return;
                    }
                    DownloadUtils.getInstance(DownloadUtils.mContext).showDownloadNotify(this.packageName, this.version, this.notificationId, this.appName + ResourceIdUtils.getStringByResId(DownloadUtils.mContext, "R.string.zy_download_finish"), this.appName, ResourceIdUtils.getStringByResId(DownloadUtils.mContext, "R.string.zy_download_finish"), this.defIconId, this.iconId, 100, this.apkPath, this.iconUrl, this.downloadUrl, this.md5, this.position, this.source, null, true);
                    return;
                default:
                    return;
            }
        }
    }

    public int generateDownladNotifyId() {
        int i;
        synchronized (this.lock) {
            if (((long) this.downloadNotifyId) == Long.MAX_VALUE) {
                this.downloadNotifyId = 90000;
            } else {
                this.downloadNotifyId++;
            }
            i = this.downloadNotifyId;
        }
        return i;
    }

    public static DownloadUtils getInstance(Context context) {
        mContext = context;
        if (mInstance == null) {
            mInstance = new DownloadUtils();
        }
        return mInstance;
    }

    private DownloadUtils() {
    }

    private void saveDownloadInfo(String saveKey, String infoStr) {
        Logger.m3374i(TAG, "yphuang push, saveDownFailedInfo(): key: " + saveKey + ", info: " + infoStr);
        Editor editor = mContext.getSharedPreferences("dfInfoSp", 0).edit();
        editor.putString(saveKey, infoStr);
        editor.commit();
    }

    private void removeDownloadInfo(String saveKey) {
        Editor editor = mContext.getSharedPreferences("dfInfoSp", 0).edit();
        editor.remove(saveKey);
        editor.commit();
    }

    public void autoContinueDownload() {
        if (NetworkUtils.getNetworkType(mContext) != (byte) 3) {
            Logger.m3373e(TAG, "current network is not wifi, don't do auto continue download");
            return;
        }
        Logger.m3374i(TAG, "yphuang push, autoContinueDownload()");
        for (Entry entry : mContext.getSharedPreferences("dfInfoSp", 0).getAll().entrySet()) {
            String infoStr = (String) entry.getValue();
            Logger.m3374i(TAG, "yphuang push, autoContinueDownload(): one to continue, info: " + infoStr);
            String appName = null;
            String apkPath = null;
            String pkgName = null;
            String iconUrl = null;
            int notifyId = 0;
            int iconId = 0;
            int verCode = 0;
            int position = 0;
            int source = 0;
            String downUrl = null;
            String md5 = null;
            try {
                JSONObject jSONObject = new JSONObject(infoStr);
                appName = jSONObject.getString("an");
                apkPath = jSONObject.getString(C0496b.f2135for);
                pkgName = jSONObject.getString("pn");
                if (jSONObject.has("iu")) {
                    iconUrl = jSONObject.getString("iu");
                }
                notifyId = jSONObject.getInt("ni");
                iconId = jSONObject.getInt("ii");
                verCode = jSONObject.getInt("vc");
                position = jSONObject.getInt(SocializeProtocolConstants.PROTOCOL_KEY_SNSACCOUNT_ICON);
                source = jSONObject.getInt("sc");
                downUrl = jSONObject.getString(au.aI);
                if (jSONObject.has("m5")) {
                    md5 = jSONObject.getString("m5");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Logger.m3374i(TAG, "yphuang push, autoContinueDownload(): add download thread result: " + addDownloadApkThread(new DownloadInfo(new MyNotifyDownloadHandler(appName, apkPath, pkgName, iconUrl, notifyId, 0, iconId, verCode, position, source, downUrl, md5, null), pkgName, verCode, position, source, downUrl, md5, true)));
        }
    }

    public void showDownloadNotify(String packageName, int versionCode, int notifictionId, String tickerText, String title, String msg, int defIconId, int iconId, int progress, String apkPath, String iconUrl, String downloadUrl, String md5, int position, int source, Intent pauseIntent, boolean hasSound) {
        if (progress == -2) {
            NotiUitl.getInstance(mContext).cancelOldNoti(notifictionId);
            this.downloadNotificationMap.remove(Integer.valueOf(notifictionId));
            return;
        }
        RemoteViews rv;
        PendingIntent pendingIntent;
        boolean cancelFlag = true;
        Object downloadNotification = this.downloadNotificationMap.get(Integer.valueOf(notifictionId));
        if (downloadNotification == null) {
            downloadNotification = NotiUitl.getInstance(mContext).getNotiObject(17301633, title);
            rv = new RemoteViews(mContext.getPackageName(), ResourceIdUtils.getResourceId(mContext, "R.layout.zy_download_notify_layout"));
            NotiUitl.getInstance(mContext).setParams(downloadNotification, "flags", Integer.valueOf(24));
            NotiUitl.getInstance(mContext).setParams(downloadNotification, "vibrate", null);
            Bitmap bitmap = null;
            if (!TextUtils.isEmpty(iconUrl)) {
                File imagePathFile = new File(PromConstants.PROM_APP_ICONS_PATH);
                if (!imagePathFile.exists()) {
                    imagePathFile.mkdirs();
                }
                File f = new File(imagePathFile, new StringBuilder(String.valueOf(iconId)).toString());
                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                if (bitmap == null) {
                    try {
                        FileUtils.copyStream(new URL(iconUrl).openStream(), new FileOutputStream(f));
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        bitmap = null;
                    }
                }
            }
            if (bitmap == null) {
                rv.setImageViewResource(ResourceIdUtils.getResourceId(mContext, "R.id.zy_iv_notify_icon"), defIconId);
            } else {
                rv.setImageViewBitmap(ResourceIdUtils.getResourceId(mContext, "R.id.zy_iv_notify_icon"), bitmap);
            }
            this.downloadNotificationMap.put(Integer.valueOf(notifictionId), downloadNotification);
        } else {
            rv = (RemoteViews) new Smith(downloadNotification, "contentView").get();
        }
        rv.setTextViewText(ResourceIdUtils.getResourceId(mContext, "R.id.zy_tv_notify_title"), title);
        if (progress >= 0 && progress < 100) {
            rv.setViewVisibility(ResourceIdUtils.getResourceId(mContext, "R.id.zy_rl_download_notify_pb"), 0);
            rv.setProgressBar(ResourceIdUtils.getResourceId(mContext, "R.id.zy_pb_download"), 100, progress, false);
            cancelFlag = false;
        } else if (progress < 0) {
            rv.setTextViewText(ResourceIdUtils.getResourceId(mContext, "R.id.zy_tv_notify_title"), new StringBuilder(String.valueOf(title)).append(SocializeConstants.OP_DIVIDER_MINUS).append(ResourceIdUtils.getStringByResId(mContext, "R.string.zy_pause")).toString());
            rv.setViewVisibility(ResourceIdUtils.getResourceId(mContext, "R.id.zy_rl_download_notify_pb"), 8);
        } else if (progress == 100) {
            NotiUitl.getInstance(mContext).setParams(downloadNotification, "icon", Integer.valueOf(17301634));
            rv.setViewVisibility(ResourceIdUtils.getResourceId(mContext, "R.id.zy_rl_download_notify_pb"), 8);
            rv.setViewVisibility(ResourceIdUtils.getResourceId(mContext, "R.id.zy_tv_notify_msg"), 0);
        }
        if (pauseIntent == null) {
            pauseIntent = new Intent();
        }
        PromAppInfo appInfo = new PromAppInfo();
        appInfo.setAction(downloadUrl);
        appInfo.setAppName(title);
        appInfo.setFileVerifyCode(md5);
        appInfo.setIconId(iconId);
        appInfo.setPackageName(packageName);
        appInfo.setUrl(iconUrl);
        appInfo.setVer(versionCode);
        appInfo.setType((byte) 1);
        appInfo.setActionType((byte) 1);
        appInfo.setId(notifictionId);
        if (progress == 100) {
            Intent intent = PromUtils.getInstance(mContext).clickPromAppInfoListener(appInfo, position);
            intent.putExtra(BundleConstants.BUNDLE_APP_INFO_SOURCE, source);
            pendingIntent = PendingIntent.getService(mContext, appInfo.getId(), intent, 1073741824);
        } else {
            pendingIntent = PendingIntent.getActivity(mContext, notifictionId, pauseIntent, 0);
        }
        NotiUitl.getInstance(mContext).setParams(downloadNotification, "defaults", Integer.valueOf(0));
        NotiUitl.getInstance(mContext).createReflectNoti(notifictionId, rv, downloadNotification, pendingIntent, cancelFlag);
    }

    public String getApkDownloadPath(String packageName) {
        return "/sdcard/" + FileConstants.FILE_ROOT + "/" + packageName + "/" + TerminalInfoUtil.getApkChannelId(mContext) + "/apks";
    }

    public String getApkDownloadFilePath(String packageName, int versionCode) {
        return new StringBuilder(String.valueOf(getInstance(mContext).getApkDownloadPath(packageName))).append("/").append(packageName).append("_r").append(versionCode).append(".apk").toString();
    }

    public void addExtraHandler(Handler handler) {
        extraHandlerList.add(handler);
    }

    public void removeExtraHandler(Handler handler) {
        extraHandlerList.remove(handler);
    }

    public ArrayList<Handler> getExtraHandlerList() {
        return extraHandlerList;
    }

    public HashMap<MyPackageInfo, DownloadApkThread> getDownloadApkThreadMap() {
        return this.downloadApkThreadMap;
    }

    public HashMap<MyPackageInfo, DownloadApkThread> getSelfUpdateThreadMap() {
        return this.selfUpdateThreadMap;
    }

    public boolean addDownloadApkThread(DownloadInfo downloadInfo) {
        boolean ret = true;
        boolean addWaiting = false;
        MyPackageInfo packageInfo = new MyPackageInfo(downloadInfo.getPackageName(), downloadInfo.getVersionCode(), downloadInfo.getPosition(), downloadInfo.getSource());
        if (this.downloadApkThreadMap.size() < 2) {
            DownloadApkThread thread = (DownloadApkThread) this.downloadApkThreadMap.get(packageInfo);
            if (thread != null && thread.isAlive() && thread.isDownloading()) {
                ret = false;
                downloadInfo = null;
            } else {
                StatsPromUtils.getInstance(mContext).addAppDownloadAction(downloadInfo.getPackageName(), downloadInfo.getVersionCode(), 0, downloadInfo.getPosition(), downloadInfo.getSource());
                if (downloadInfo.getHandler() instanceof MyNotifyDownloadHandler) {
                    MyNotifyDownloadHandler mNDownHandler = (MyNotifyDownloadHandler) downloadInfo.getHandler();
                    saveDownloadInfo(mNDownHandler.apkPath, mNDownHandler.getDownloadInfoString());
                }
                this.downloadApkThreadMap.remove(packageInfo);
                thread = new DownloadApkThread(mContext, downloadInfo);
                this.downloadApkThreadMap.put(packageInfo, thread);
                thread.start();
            }
        } else {
            ret = false;
            addWaiting = true;
        }
        if (!(!addWaiting || downloadInfo == null || this.waitDownloadList.contains(downloadInfo))) {
            this.waitDownloadList.add(downloadInfo);
            if (downloadInfo.getHandler() instanceof MyNotifyDownloadHandler) {
                mNDownHandler = (MyNotifyDownloadHandler) downloadInfo.getHandler();
                saveDownloadInfo(mNDownHandler.apkPath, mNDownHandler.getDownloadInfoString());
            }
        }
        return ret;
    }

    public boolean downloadNextApk() {
        if (this.waitDownloadList != null) {
            DownloadInfo downloadInfo = (DownloadInfo) this.waitDownloadList.poll();
            if (downloadInfo != null) {
                return addDownloadApkThread(downloadInfo);
            }
        }
        return false;
    }

    public boolean isDownloadWaiting(DownloadInfo downloadInfo) {
        return this.waitDownloadList.contains(downloadInfo);
    }

    public void addSelfUpdateThread(DownloadInfo downloadInfo) {
        MyPackageInfo packageInfo = new MyPackageInfo(downloadInfo.getPackageName(), downloadInfo.getVersionCode(), downloadInfo.getPosition(), downloadInfo.getSource());
        DownloadApkThread thread = (DownloadApkThread) this.selfUpdateThreadMap.get(packageInfo);
        if (thread == null || !thread.isAlive() || !thread.isDownloading()) {
            StatsPromUtils.getInstance(mContext).addAppDownloadAction(downloadInfo.getPackageName(), downloadInfo.getVersionCode(), 0, 0, 0);
            this.selfUpdateThreadMap.remove(packageInfo);
            thread = new DownloadApkThread(mContext, downloadInfo);
            this.selfUpdateThreadMap.put(packageInfo, thread);
            thread.start();
        }
    }

    public void addDownloadZipThread(Handler downloadHandler, Html5Info html5Info, String filePath) {
        DownloadHtml5Thread thread = (DownloadHtml5Thread) this.downloadZipThreadMap.get(Integer.valueOf(html5Info.getId()));
        if (thread == null || !thread.isAlive() || !thread.isDownloading()) {
            this.downloadZipThreadMap.remove(Integer.valueOf(html5Info.getId()));
            thread = new DownloadHtml5Thread(mContext, downloadHandler, html5Info, filePath);
            this.downloadZipThreadMap.put(html5Info.getId(), thread);
            thread.start();
        }
    }

    public void removeDownloadZipThread(int id) {
        if (this.downloadZipThreadMap != null) {
            DownloadHtml5Thread thread = (DownloadHtml5Thread) this.downloadZipThreadMap.remove(Integer.valueOf(id));
            if (thread != null && thread.isAlive()) {
                thread.onPause();
            }
        }
    }

    public void removeDownloadApkThread(MyPackageInfo key) {
        if (this.downloadApkThreadMap != null) {
            DownloadApkThread thread = (DownloadApkThread) this.downloadApkThreadMap.remove(key);
            if (thread != null && thread.isAlive()) {
                thread.onPause();
            }
        }
    }

    public void removeSelfUpdateThread(MyPackageInfo key) {
        if (this.selfUpdateThreadMap != null) {
            DownloadApkThread thread = (DownloadApkThread) this.selfUpdateThreadMap.remove(key);
            if (thread != null && thread.isAlive()) {
                thread.onPause();
            }
        }
    }

    public void removeAllThread() {
        if (this.downloadApkThreadMap != null) {
            MyPackageInfo[] keyArray = new MyPackageInfo[this.downloadApkThreadMap.size()];
            int i = 0;
            for (MyPackageInfo key : this.downloadApkThreadMap.keySet()) {
                int i2 = i + 1;
                keyArray[i] = key;
                i = i2;
            }
            for (MyPackageInfo key2 : keyArray) {
                removeDownloadApkThread(key2);
            }
        }
    }

    public void clearWaitingAppList() {
        if (this.waitDownloadList != null) {
            this.waitDownloadList.clear();
        }
    }

    public int getAppStatus(String packageName, int versionCode) {
        if (this.downloadApkThreadMap.containsKey(new MyPackageInfo(packageName, versionCode))) {
            return 5;
        }
        if (this.waitDownloadList.contains(new DownloadInfo(packageName, versionCode))) {
            return 6;
        }
        PackageInfo pInfo = AppInfoUtils.getPackageInfoByPackageName(mContext, packageName);
        if (TextUtils.isEmpty(packageName) || versionCode < 0) {
            return -1;
        }
        int status = 0;
        if (pInfo != null) {
            if (pInfo.versionCode >= versionCode) {
                return 3;
            }
            status = 7;
        }
        File path = new File(getInstance(mContext).getApkDownloadPath(packageName));
        if (path.exists()) {
            if (new File(path, new StringBuilder(String.valueOf(packageName)).append("_r").append(versionCode).append(".apk").toString()).exists()) {
                return 2;
            }
            if (new File(path, new StringBuilder(String.valueOf(packageName)).append("_r").append(versionCode).append(".tmp").toString()).exists()) {
                return 4;
            }
        }
        return status == 0 ? 1 : status;
    }

    public void onDestroy() {
        clearWaitingAppList();
        removeAllThread();
    }
}
