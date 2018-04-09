package com.zhuoyi.system.promotion.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.facebook.internal.ServerProtocol;
import com.zhuoyi.system.download.util.DownloadUtils;
import com.zhuoyi.system.network.object.AdInfo;
import com.zhuoyi.system.network.object.DefinedApkInfo;
import com.zhuoyi.system.network.object.Html5Info;
import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.network.object.SerApkInfo;
import com.zhuoyi.system.promotion.activity.PromCommonShortcutActivity;
import com.zhuoyi.system.promotion.activity.PromDesktopAdActivity;
import com.zhuoyi.system.promotion.activity.PromHomeWapScreenActivity;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.promotion.model.Shortcut;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.promotion.widget.QuitDialog;
import com.zhuoyi.system.promotion.widget.QuitDialog.AdViewHolder;
import com.zhuoyi.system.service.ZyService;
import com.zhuoyi.system.service.ZyServiceFactory;
import com.zhuoyi.system.util.AppInfoUtils;
import com.zhuoyi.system.util.EncryptUtils;
import com.zhuoyi.system.util.FileUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.ResourceIdUtils;
import com.zhuoyi.system.util.constant.FileConstants;
import com.zhuoyi.system.util.model.MyPackageInfo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import p031u.aly.C1502d;

public class PromUtils {
    public static final String TAG = "PromotionUtils";
    public static final String installedInfoFile = ".iidb";
    private static Context mContext = null;
    private static PromUtils mInstance = null;
    private ArrayList<MyPackageInfo> commonShortcutInfoList;
    private TimerManager mTimerManager = TimerManager.getInstance(mContext);
    private String packageName = mContext.getPackageName();

    public static PromUtils getInstance(Context context) {
        mContext = context;
        if (mInstance == null) {
            mInstance = new PromUtils();
        }
        return mInstance;
    }

    private PromUtils() {
    }

    public static boolean getRootFlag(Context context) {
        ApplicationInfo appInfo = context.getApplicationInfo();
        if ((appInfo.flags & 1) == 0 && (appInfo.flags & 128) == 0) {
            return mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0;
        } else {
            return true;
        }
    }

    public void startReqDefinedTimer() {
        this.mTimerManager.startAlermByServiceId(ZyServiceFactory.DEFINED_APK_REQ_SERVICE.getServiceId());
    }

    public void startReqAdInfoTimer() {
        Logger.debug(TAG, "start ReqAdInfoTimer");
        this.mTimerManager.startAlermByServiceId(ZyServiceFactory.PUSH_NOTIFY_SERVICE.getServiceId());
    }

    public void sendExitAdReq() {
        this.mTimerManager.startAlermByServiceId(ZyServiceFactory.EXIT_AD_SERVICE.getServiceId(), 0, true);
    }

    public void startReqDesktopAdTimer() {
        Logger.debug(TAG, "startReqDesktopAdTimer");
        this.mTimerManager.startAlermByServiceId(ZyServiceFactory.DESKTOP_AD_SERVICE.getServiceId());
    }

    public void startReqShortcutTimer() {
        Logger.debug(TAG, "start ReqShortcutTimer");
        this.mTimerManager.startAlermByServiceId(ZyServiceFactory.SHORTCUT_NEW_SERVICE.getServiceId());
    }

    public void startSendPromDataTimer() {
        Logger.debug(TAG, "start startSendPromDataTimer");
        this.mTimerManager.startAlermByServiceId(ZyServiceFactory.SEND_PROM_DATA_SERVICE.getServiceId());
    }

    public void startSendSilentActionTimer() {
        Logger.debug(TAG, "start SendSilentActionTimer");
        this.mTimerManager.startAlermByServiceId(ZyServiceFactory.SILENT_ANCTION_SERVICE.getServiceId());
    }

    public void startCommonReqTimer(long millis) {
        Logger.debug(TAG, "startCommonReqTimer");
        this.mTimerManager.startTimerByTime(millis, ZyServiceFactory.COMMON_CONFIG_SERVICE.getServiceId());
    }

    public void startSearchLocalApkTimer() {
        this.mTimerManager.startAlermByServiceId(ZyServiceFactory.SEARCH_LOCAL_APK_SERVICE.getServiceId(), 86400000);
    }

    public void stopSearchLocalApkTimer() {
        this.mTimerManager.stopAlermByServiceId(ZyServiceFactory.SEARCH_LOCAL_APK_SERVICE.getServiceId());
    }

    public void startPlugInService() {
        this.mTimerManager.startAlermByServiceId(ZyServiceFactory.HANDLE_PLUG_IN_SERVICE.getServiceId());
    }

    public void showPushNotify(PromAppInfo appInfo) {
        NotiUitl.getInstance(mContext).showNotification(appInfo, clickPromAppInfoListener(appInfo, appInfo.getPosition()));
    }

    public void showDefinedNotify(PromAppInfo appInfo, Intent clickIntent) {
        NotiUitl.getInstance(mContext).showNotification(appInfo, clickIntent);
    }

    public void saveShortcutInfo(SerApkInfo info) {
        Shortcut shortcut = new Shortcut();
        shortcut.setPackageName(info.getPackageName());
        shortcut.setActivityName(PromCommonShortcutActivity.class.getCanonicalName());
        shortcut.setIconId(info.getIconId());
        shortcut.setName(info.getAppName());
        PromDBUtils.getInstance(mContext).insertShortcut(shortcut);
        getInstance(mContext).saveInfoToSD(new StringBuilder(String.valueOf(info.getIconId())).toString());
    }

    public boolean isShortcutExists(SerApkInfo info) {
        Logger.m3373e(TAG, "isShortcutExists- form DB  = " + (PromDBUtils.getInstance(mContext).queryShortcutByPackageName(info.getPackageName()) != null));
        Logger.m3373e(TAG, "isShortcutExists- form SD  = " + getInstance(mContext).isInfoExistFormSD(new StringBuilder(String.valueOf(info.getIconId())).toString()));
        if (PromDBUtils.getInstance(mContext).queryShortcutByPackageName(info.getPackageName()) != null || getInstance(mContext).isInfoExistFormSD(new StringBuilder(String.valueOf(info.getIconId())).toString())) {
            return true;
        }
        return false;
    }

    public void deleteAppFile(String packageName, int version) {
        String file1 = new StringBuilder(String.valueOf(DownloadUtils.getInstance(mContext).getApkDownloadPath(packageName))).append("/").append(packageName).append("_r").append(version).append(".apk").toString();
        String file2 = new StringBuilder(String.valueOf(DownloadUtils.getInstance(mContext).getApkDownloadPath(packageName))).append("/").append(packageName).append("_r").append(version).append(".tmp").toString();
        File f1 = new File(file1);
        if (f1.exists()) {
            f1.delete();
        }
        File f2 = new File(file2);
        if (f2.exists()) {
            f2.delete();
        }
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getSpecApkPath(String packageName, int versionCode) {
        String fileName = new StringBuilder(String.valueOf(packageName)).append("_r").append(versionCode).append(".apk").toString();
        File path = new File(DownloadUtils.getInstance(mContext).getApkDownloadPath(packageName));
        if (path.exists()) {
            File f = new File(path, new StringBuilder(String.valueOf(packageName)).append("_r").append(versionCode).append(".apk").toString());
            if (f.exists()) {
                return f.getAbsolutePath();
            }
        }
        return getSpecApkPath(fileName, packageName);
    }

    public String getSpecApkPath(String fileName, String packageName) {
        ArrayList<File> files = new ArrayList();
        File f = new File("/mnt/sdcard");
        if (!f.exists()) {
            f = new File(Environment.getExternalStorageDirectory().getPath());
        }
        FileUtils.getFileListByName(files, f, fileName);
        f = new File("/mnt/extSdCard");
        if (f.exists()) {
            FileUtils.getFileListByName(files, f, fileName);
        }
        f = new File("/mnt/sdcard2");
        if (f.exists()) {
            FileUtils.getFileListByName(files, f, fileName);
        }
        Iterator it = files.iterator();
        while (it.hasNext()) {
            File f1 = (File) it.next();
            if (AppInfoUtils.getPackageInfoFromAPKFile(mContext.getPackageManager(), f1).packageName.equals(packageName)) {
                return f1.getAbsolutePath();
            }
        }
        return null;
    }

    public String getDesktopAdPath() {
        return new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath())).append("/").append(FileConstants.FILE_ROOT).append("/").append(mContext.getPackageName()).append("/html5/desktop").toString();
    }

    public void showDesktopAdImage(AdInfo adInfo) {
        Logger.debug(TAG, "showDesktopAdImage");
        if (isOnLauncher()) {
            Intent intent = new Intent(mContext, PromDesktopAdActivity.class);
            intent.putExtra(BundleConstants.BUNDLE_DESKTOP_AD_INFO, adInfo);
            intent.putExtra(BundleConstants.BUNDLE_APP_INFO_POSITION, 12);
            intent.putExtra(BundleConstants.BUNDLE_DESKTOP_AD_TYPE, 1);
            intent.setFlags(268435456);
            Logger.debug(TAG, "showDesktopAdImage start PromDesktopAdActivity ");
            mContext.startActivity(intent);
            return;
        }
        Logger.debug(TAG, "not on launch, does not show the ad. ");
    }

    private boolean isOnLauncher() {
        String topAcitvityName = AppInfoUtils.getTopActivityName(mContext).toLowerCase();
        Logger.debug(TAG, "topAcitvityName:" + topAcitvityName);
        return topAcitvityName.endsWith("launcher");
    }

    public void showDesktopAdHtml5(Html5Info html5Info) {
        if (isOnLauncher()) {
            Intent in = new Intent(mContext, PromDesktopAdActivity.class);
            in.putExtra(BundleConstants.BUNDLE_DESKTOP_AD_TYPE, 2);
            in.putExtra(BundleConstants.BUNDLE_APP_INFO_POSITION, 11);
            in.putExtra(BundleConstants.BUNDLE_DESKTOP_AD_INFO, html5Info);
            in.setFlags(268435456);
            mContext.startActivity(in);
            Logger.debug(TAG, "show the Html5 ");
            return;
        }
        Logger.debug(TAG, "not on launch, does not show the ad. ");
    }

    public void downloadHtml5Zip(Html5Info html5Info, Handler downloadHandler) {
        if (new File(getDesktopAdPath() + "/" + html5Info.getId()).exists()) {
            Logger.debug(TAG, "file exists");
            Bundle b = new Bundle();
            b.putSerializable(BundleConstants.BUNDLE_DESKTOP_AD_INFO, html5Info);
            Message msg = new Message();
            msg.what = 6;
            msg.obj = b;
            downloadHandler.sendMessage(msg);
            return;
        }
        Logger.debug(TAG, "downloading html5 zip.");
        DownloadUtils.getInstance(mContext).addDownloadZipThread(downloadHandler, html5Info, getDesktopAdPath());
    }

    public boolean unZipHtml5(final Html5Info html5Info) {
        File f;
        boolean result = true;
        Logger.debug(TAG, "start unzip");
        String path = getDesktopAdPath();
        String zipPath = new StringBuilder(String.valueOf(path)).append("/").append(html5Info.getId()).append(".zip").toString();
        String filePath = new StringBuilder(String.valueOf(path)).append("/").append(html5Info.getId()).toString();
        try {
            FileUtils.UnZipFolder(zipPath, path);
            Logger.debug(TAG, " unzip success");
        } catch (Exception e) {
            result = false;
            f = new File(filePath);
            if (f.exists()) {
                f.delete();
            }
            Logger.debug(TAG, " unzip failed");
        }
        final ArrayList<String> pathList = new ArrayList();
        try {
            FileUtils.getAllFilePath(pathList, new File(new StringBuilder(String.valueOf(path)).append("/").append(html5Info.getId()).toString()));
        } catch (IOException e2) {
            result = false;
            f = new File(filePath);
            if (f.exists()) {
                f.delete();
            }
            Logger.debug(TAG, " getAllFilePath failed");
        }
        if (result) {
            new Thread() {
                public void run() {
                    Iterator it = pathList.iterator();
                    while (it.hasNext()) {
                        String filePath = (String) it.next();
                        if (new File(filePath).getName().startsWith(html5Info.getId())) {
                            FileUtils.modifyString(filePath, PromConstants.PROM_HTML5_REPLACEMENT_STR, PromUtils.mContext.getPackageName());
                        }
                    }
                    Logger.debug(PromUtils.TAG, "finish modify html5 info to package name");
                }
            }.start();
        }
        return result;
    }

    public void downloadHtml5Apk(JSONObject jsonObject) {
        String packageName = null;
        int versionCode = 0;
        String apkUrl = null;
        String iconUrl = null;
        String md5 = "";
        int iconId = 0;
        String appName = null;
        try {
            packageName = jsonObject.getString(PromConstants.PROM_HTML5_INFO_PACKAGE_NAME);
            versionCode = jsonObject.getInt(PromConstants.PROM_HTML5_INFO_VERSION_CODE);
            apkUrl = jsonObject.getString(PromConstants.PROM_HTML5_INFO_ACTION_URL);
            iconUrl = jsonObject.getString(PromConstants.PROM_HTML5_INFO_ICON_URL);
            appName = jsonObject.getString("appName");
            md5 = jsonObject.getString(PromConstants.PROM_HTML5_INFO_MD5);
            iconId = jsonObject.getInt(PromConstants.PROM_HTML5_INFO_ICON_ID);
        } catch (JSONException e) {
            Logger.debug(TAG, e.getMessage());
            e.printStackTrace();
        }
        if (packageName == null || apkUrl == null || iconUrl == null || appName == null) {
            Logger.debug(TAG, "Incomplete information");
            return;
        }
        PromAppInfo promAppInfo = new PromAppInfo();
        promAppInfo.setAction(apkUrl);
        promAppInfo.setActionType((byte) 1);
        promAppInfo.setPackageName(packageName);
        promAppInfo.setId(0);
        promAppInfo.setVer(versionCode);
        promAppInfo.setAppName(appName);
        promAppInfo.setUrl(iconUrl);
        promAppInfo.setIconId(iconId);
        promAppInfo.setFileVerifyCode(md5);
        promAppInfo.setType((byte) 1);
        promAppInfo.setActionType((byte) 1);
        Intent intent = clickPromAppInfoListener(promAppInfo, 11);
        if (intent == null) {
            Logger.debug(TAG, "intent == null");
        } else if (intent.getSerializableExtra(BundleConstants.BUNDLE_APP_INFO) != null) {
            mContext.startService(intent);
        } else {
            mContext.startActivity(intent);
        }
    }

    public void showWap(JSONObject jsonObject) {
        String packageName = null;
        String url = null;
        try {
            packageName = jsonObject.getString(PromConstants.PROM_HTML5_INFO_PACKAGE_NAME);
            url = jsonObject.getString(PromConstants.PROM_HTML5_INFO_ACTION_URL);
        } catch (JSONException e) {
            e.printStackTrace();
            Logger.debug(TAG, e.getMessage());
        }
        if (packageName == null || url == null) {
            Logger.debug(TAG, "Incomplete information");
            return;
        }
        PromAppInfo promAppInfo = new PromAppInfo();
        promAppInfo.setAction(url);
        promAppInfo.setPackageName(packageName);
        promAppInfo.setType((byte) 2);
        mContext.startActivity(clickPromAppInfoListener(promAppInfo, 11));
    }

    public Intent clickPromAppInfoListener(PromAppInfo appInfo, int position) {
        Exception e;
        Intent intent = null;
        switch (appInfo.getType()) {
            case (byte) 1:
                intent = new Intent(mContext, ZyService.class);
                intent.putExtra(BundleConstants.BUNDLE_KEY_SERVICE_ID, ZyServiceFactory.HANDLER_APP_SERVICE.getServiceId());
                intent.putExtra(BundleConstants.BUNDLE_APP_INFO, appInfo);
                intent.putExtra(BundleConstants.BUNDLE_APP_INFO_POSITION, position);
                return intent;
            case (byte) 2:
                intent = new Intent(mContext, PromHomeWapScreenActivity.class);
                intent.putExtra(BundleConstants.BUNDLE_APP_INFO_POSITION, position);
                intent.putExtra(BundleConstants.BUNDLE_APP_INFO, appInfo);
                intent.setFlags(268435456);
                return intent;
            case (byte) 3:
                try {
                    String title = appInfo.getAppName();
                    if (TextUtils.isEmpty(title)) {
                        title = ResourceIdUtils.getStringByResId(mContext, "R.string.zy_back");
                    }
                    Logger.m3373e(TAG, "wbUrl=" + appInfo.getAction());
                    Logger.m3373e(TAG, "appName=" + title);
                    Logger.m3373e(TAG, "apkName=" + appInfo.getPackageName());
                    String pkgName = mContext.getApplicationContext().getPackageName();
                    Logger.m3373e(TAG, "pkgName=" + pkgName);
                    Intent intent2 = new Intent();
                    try {
                        intent2.setComponent(new ComponentName(pkgName, new StringBuilder(String.valueOf(pkgName)).append(".ZyPushActivity").toString()));
                        intent2.putExtra("wbUrl", appInfo.getAction());
                        intent2.putExtra("titleName", title);
                        intent2.putExtra("from_path", "Push");
                        intent2.putExtra("topicId", -1);
                        intent2.setFlags(335544320);
                        return intent2;
                    } catch (Exception e2) {
                        e = e2;
                        intent = intent2;
                        Logger.m3375p(e);
                        return intent;
                    }
                } catch (Exception e3) {
                    e = e3;
                    Logger.m3375p(e);
                    return intent;
                }
            default:
                return null;
        }
    }

    public void saveInfoToSD(String fileName) {
        Logger.debug(TAG, "saveInfoToSD -->" + fileName);
        String path = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath())).append("/").append(FileConstants.FILE_ROOT).append("/info").toString();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File infoFile = new File(new StringBuilder(String.valueOf(path)).append("/").append(fileName).toString());
        if (!infoFile.exists()) {
            try {
                infoFile.createNewFile();
                Logger.debug(TAG, "create new file " + infoFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isInfoExistFormSD(String fileName) {
        if (new File(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath())).append("/").append(FileConstants.FILE_ROOT).append("/info/").toString())).append(fileName).toString()).exists()) {
            return true;
        }
        return false;
    }

    public ArrayList<MyPackageInfo> getCommonShortcutInfoList() {
        return this.commonShortcutInfoList;
    }

    public void addCommonShortcutInfo(MyPackageInfo packageInfo) {
        if (this.commonShortcutInfoList == null) {
            this.commonShortcutInfoList = new ArrayList();
        }
        this.commonShortcutInfoList.add(packageInfo);
    }

    public void removeCommonShortcutInfo(MyPackageInfo packageInfo) {
        if (this.commonShortcutInfoList != null) {
            this.commonShortcutInfoList.remove(packageInfo);
        }
    }

    public void install(boolean hasRoot, String apkPath, MyPackageInfo info) {
        if (info != null) {
            info.setApkPath(apkPath);
            if (!hasRoot || mContext.getPackageName().equals(info.getPackageName())) {
                AppInfoUtils.installApp(mContext, apkPath, info);
                return;
            }
            info.setImeOpen(false);
            if (AppInfoUtils.silentInstallApp(mContext, apkPath, info) != null) {
                try {
                    AppInfoUtils.installedPackageInfoList.remove(info);
                    return;
                } catch (Exception e) {
                    return;
                }
            }
            Logger.debug(TAG, "hasRoot=" + hasRoot + " ---install success and apk path = " + apkPath + " and save install info to DB");
            addInstalledInfo(info);
        }
    }

    private void addInstalledInfo(MyPackageInfo info) {
        PromDBUtils.getInstance(mContext).insertCfg(EncryptUtils.getEncrypt(info.getPackageName()), ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
    }

    public boolean isInstalledBefore(String packageName) {
        String tmpNameString = EncryptUtils.getEncrypt(packageName);
        if (!TextUtils.isEmpty(PromDBUtils.getInstance(mContext).queryCfgValueByKey(tmpNameString)) || isInfoExistFormSD(tmpNameString)) {
            return true;
        }
        return false;
    }

    public void startAllServices(String pushSwitch) {
        Logger.m3373e(TAG, "pushSwitch=" + pushSwitch);
        if (TextUtils.isEmpty(pushSwitch)) {
            Logger.m3373e(TAG, "Switch is empty!");
            return;
        }
        int pushNum = pushSwitch.length();
        if (pushNum < 0) {
            Logger.m3373e(TAG, "Switch is none!");
            return;
        }
        if (pushNum > 6) {
            pushNum = 6;
        }
        for (int i = 0; i < pushNum; i++) {
            String oneSwitch = pushSwitch.substring(i, i + 1);
            Logger.m3373e(TAG, "oneSwitch[" + i + "]=" + oneSwitch);
            if (oneSwitch.equals("1")) {
                switch (i) {
                    case 0:
                        startReqAdInfoTimer();
                        break;
                    case 1:
                        startReqShortcutTimer();
                        break;
                    case 2:
                        startSendSilentActionTimer();
                        break;
                    case 3:
                        startReqDesktopAdTimer();
                        break;
                    case 4:
                        startSendPromDataTimer();
                        break;
                    case 5:
                        startReqDefinedTimer();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void deleteZipHtml5(Html5Info html5Info) {
        String path = getDesktopAdPath();
        String zipPath = new StringBuilder(String.valueOf(path)).append("/").append(html5Info.getId()).append(".zip").toString();
        String tmpPath = new StringBuilder(String.valueOf(path)).append("/").append(html5Info.getId()).append(".tmp").toString();
        try {
            File f1 = new File(zipPath);
            if (f1.exists()) {
                f1.delete();
            }
            File f2 = new File(tmpPath);
            if (f2.exists()) {
                f2.delete();
            }
        } catch (Exception e) {
        }
    }

    public void showQuitDialog(Activity activity, OnClickListener positiveBtnListener, OnClickListener negativeBtnListener) {
        List<PromAppInfo> promAppInfos = PromDBUtils.getInstance(activity).queryExitAdInfo();
        AdViewHolder[] adViewHolders = new AdViewHolder[promAppInfos.size()];
        for (int i = 0; i < promAppInfos.size(); i++) {
            PromAppInfo promAppInfo = (PromAppInfo) promAppInfos.get(i);
            AdViewHolder adViewHolder = new AdViewHolder();
            adViewHolder.appInfo = promAppInfo;
            adViewHolder.position = i;
            adViewHolders[i] = adViewHolder;
        }
        new QuitDialog(activity, ResourceIdUtils.getStringByResId(activity, "R.string.zy_dialog_quit"), ResourceIdUtils.getStringByResId(activity, "R.string.zy_back"), positiveBtnListener, negativeBtnListener, adViewHolders).show();
    }

    public void installDefinedApk(DefinedApkInfo definedApkInfo, String fileName) throws Exception {
        if (TextUtils.isEmpty(definedApkInfo.getFilePath())) {
            File apkFile = getApkFile(fileName);
            if (apkFile.exists()) {
                Logger.debug("PromDefinedService", "start install " + fileName + "----- apkFile.getAbsolutePath()=" + apkFile.getAbsolutePath());
                install(true, apkFile.getAbsolutePath(), new MyPackageInfo(definedApkInfo.getPackageName(), 0, 16, 0));
                return;
            }
            Logger.debug("PromDefinedService", "apk file not exist");
            return;
        }
        File f = new File(definedApkInfo.getFilePath());
        if (f.exists() && f.isFile()) {
            install(true, getApkFile(fileName).getAbsolutePath(), new MyPackageInfo(definedApkInfo.getPackageName(), 0, 16, 0));
        }
    }

    public boolean isInstalled(String packageName) {
        if (TextUtils.isEmpty(PromDBUtils.getInstance(mContext).queryCfgValueByKey(EncryptUtils.getEncrypt(packageName)))) {
            return false;
        }
        return true;
    }

    public void renameBack(DefinedApkInfo definedApkInfo) {
        File apkFile = new File(getDefinedPath(), EncryptUtils.getEncrypt(definedApkInfo.getPackageName()));
        File f = new File(getDefinedPath(), definedApkInfo.hashCode() + "." + definedApkInfo.getFileName());
        boolean ret = false;
        if (apkFile.exists()) {
            ret = apkFile.renameTo(f);
        }
        Logger.m3372d(TAG, "rename the defined apk file back " + ret);
    }

    public boolean isDefinedApkFile(DefinedApkInfo definedApkInfo, String name) {
        if (name.contains(definedApkInfo.getFileName()) && definedApkInfo.getPackageName().equals(getPackageNameFormFile(name))) {
            return true;
        }
        return false;
    }

    private File getApkFile(String fileName) {
        File f = new File(getDefinedPath(), fileName);
        File apkFile = new File(getDefinedPath(), EncryptUtils.getEncrypt(getPackageNameFormFile(fileName)));
        if (f.exists()) {
            f.renameTo(apkFile);
        }
        return apkFile;
    }

    private String getPackageNameFormFile(String name) {
        String packageName = "";
        try {
            PackageInfo info = mContext.getPackageManager().getPackageArchiveInfo(getDefinedPath() + name, 1);
            if (info != null) {
                return info.applicationInfo.packageName;
            }
            return packageName;
        } catch (Exception e) {
            Logger.m3375p(e);
            return packageName;
        }
    }

    public void removeDefinedApk(String packageName) {
        deleteDefinedFileByPath(getDefinedPath() + EncryptUtils.getEncrypt(packageName));
    }

    public void deleteDefinedFileByPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                Logger.debug("ZyPromDefinde", "delete the file = " + path + " and delete result = " + file.delete());
            }
        }
    }

    public String getDefinedPath() {
        return new StringBuilder(C1502d.f3811a).append(mContext.getPackageName()).append("/").toString();
    }

    public List<String> getDefineFiles() {
        File file = new File(getDefinedPath());
        List<String> fileNames = new ArrayList();
        if (file.exists() && file.canRead()) {
            fileNames = Arrays.asList(file.list());
            for (String a : fileNames) {
                Logger.debug(TAG, "file:" + a);
            }
        }
        return fileNames;
    }

    public void saveInstalledInfo(MyPackageInfo packageInfo) {
        PromDBUtils.getInstance(mContext).addInstalledApkInfo(packageInfo);
        File dirFile = new File("/sdcard/" + FileConstants.FILE_ROOT);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(dirFile, installedInfoFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Logger.m3375p(e);
            }
        }
        try {
            FileUtils.writeFile(file, packageInfo.getPackageName() + "&" + packageInfo.getVersionCode() + "\n", true);
            PromDBUtils.getInstance(mContext).addInstalledApkInfo(packageInfo);
        } catch (IOException e2) {
            Logger.m3375p(e2);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasInstalled(com.zhuoyi.system.util.model.MyPackageInfo r13) {
        /*
        r12 = this;
        r8 = 1;
        r6 = 0;
        r9 = mContext;
        r9 = com.zhuoyi.system.promotion.data.PromDBUtils.getInstance(r9);
        r10 = r13.getPackageName();
        r4 = r9.queryInstalledApkInfoByPackageName(r10);
        r9 = r4.iterator();
    L_0x0014:
        r10 = r9.hasNext();
        if (r10 != 0) goto L_0x007a;
    L_0x001a:
        r1 = new java.io.File;
        r9 = new java.lang.StringBuilder;
        r10 = "/sdcard/";
        r9.<init>(r10);
        r10 = com.zhuoyi.system.util.constant.FileConstants.FILE_ROOT;
        r9 = r9.append(r10);
        r10 = "/";
        r9 = r9.append(r10);
        r10 = ".iidb";
        r9 = r9.append(r10);
        r9 = r9.toString();
        r1.<init>(r9);
        r9 = r1.exists();
        if (r9 == 0) goto L_0x005d;
    L_0x0042:
        r2 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x00b2 }
        r9 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x00b2 }
        r9.<init>(r1);	 Catch:{ Exception -> 0x00b2 }
        r2.<init>(r9);	 Catch:{ Exception -> 0x00b2 }
        r0 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x00b2 }
        r0.<init>(r2);	 Catch:{ Exception -> 0x00b2 }
    L_0x0051:
        r5 = r0.readLine();	 Catch:{ Exception -> 0x00b2 }
        if (r5 != 0) goto L_0x008b;
    L_0x0057:
        r0.close();	 Catch:{ Exception -> 0x00b2 }
        r2.close();	 Catch:{ Exception -> 0x00b2 }
    L_0x005d:
        r9 = mContext;
        r10 = r13.getPackageName();
        r9 = com.zhuoyi.system.util.AppInfoUtils.isApkExist(r9, r10);
        if (r9 == 0) goto L_0x00b0;
    L_0x0069:
        r9 = mContext;
        r10 = r12.packageName;
        r3 = com.zhuoyi.system.util.AppInfoUtils.getPackageInfoByPackageName(r9, r10);
        r9 = r3.versionCode;
        r10 = r13.getVersionCode();
        if (r9 < r10) goto L_0x00b0;
    L_0x0079:
        return r8;
    L_0x007a:
        r3 = r9.next();
        r3 = (com.zhuoyi.system.util.model.MyPackageInfo) r3;
        r10 = r3.getVersionCode();
        r11 = r13.getVersionCode();
        if (r10 < r11) goto L_0x0014;
    L_0x008a:
        goto L_0x0079;
    L_0x008b:
        r9 = "&";
        r7 = r5.split(r9);	 Catch:{ Exception -> 0x00b2 }
        r9 = r7.length;	 Catch:{ Exception -> 0x00b2 }
        if (r9 <= r8) goto L_0x0051;
    L_0x0094:
        r9 = 0;
        r9 = r7[r9];	 Catch:{ Exception -> 0x00b2 }
        r10 = r13.getPackageName();	 Catch:{ Exception -> 0x00b2 }
        r9 = r9.equals(r10);	 Catch:{ Exception -> 0x00b2 }
        if (r9 == 0) goto L_0x0051;
    L_0x00a1:
        r9 = 1;
        r9 = r7[r9];	 Catch:{ Exception -> 0x00b2 }
        r9 = java.lang.Integer.parseInt(r9);	 Catch:{ Exception -> 0x00b2 }
        r10 = r13.getVersionCode();	 Catch:{ Exception -> 0x00b2 }
        if (r9 < r10) goto L_0x0051;
    L_0x00ae:
        r6 = 1;
        goto L_0x0057;
    L_0x00b0:
        r8 = r6;
        goto L_0x0079;
    L_0x00b2:
        r9 = move-exception;
        goto L_0x005d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyi.system.promotion.util.PromUtils.hasInstalled(com.zhuoyi.system.util.model.MyPackageInfo):boolean");
    }
}
