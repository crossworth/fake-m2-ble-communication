package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Handler;
import com.zhuoyi.system.download.model.DownloadInfo;
import com.zhuoyi.system.download.util.DownloadUtils;
import com.zhuoyi.system.download.util.DownloadUtils.MyNotifyDownloadHandler;
import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.AppInfoUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.constant.Session;
import com.zhuoyi.system.util.model.MyPackageInfo;
import java.io.File;

public class PromHandleAppService extends IZyService {
    public static final String TAG = "PromShowDownloadNotifyService";
    private PromAppInfo appInfo;
    private int position;
    private int source;

    public PromHandleAppService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        String str;
        Logger.m3373e(TAG, "onStartCommand");
        if (intent != null) {
            this.appInfo = (PromAppInfo) intent.getSerializableExtra(BundleConstants.BUNDLE_APP_INFO);
            this.position = intent.getIntExtra(BundleConstants.BUNDLE_APP_INFO_POSITION, 0);
            this.source = intent.getIntExtra(BundleConstants.BUNDLE_APP_INFO_SOURCE, 0);
            if (this.position == 3) {
                try {
                    StatsPromUtils.getInstance(this.mContext).addAdClickAction(this.appInfo.getPackageName(), this.appInfo.getIconId(), this.position, this.source);
                } catch (Exception e) {
                }
            }
        }
        String str2 = TAG;
        StringBuilder stringBuilder = new StringBuilder("appInfo = ");
        if (this.appInfo == null) {
            str = "null";
        } else {
            str = this.appInfo.toString();
        }
        Logger.m3373e(str2, stringBuilder.append(str).toString());
        if (this.appInfo != null) {
            PackageInfo pInfo = AppInfoUtils.getPackageInfoByPackageName(this.mContext, this.appInfo.getPackageName());
            String apkPath = DownloadUtils.getInstance(this.mContext).getApkDownloadFilePath(this.appInfo.getPackageName(), this.appInfo.getVer());
            if (pInfo != null) {
                if (PromUtils.getInstance(this.mContext).hasInstalled(new MyPackageInfo(this.appInfo.getPackageName(), this.appInfo.getVer()))) {
                    try {
                        intent = this.mContext.getPackageManager().getLaunchIntentForPackage(this.appInfo.getPackageName());
                        intent.addFlags(268435456);
                        this.mContext.startActivity(intent);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else if (new File(apkPath).exists()) {
                    AppInfoUtils.installApp(this.mContext, apkPath, new MyPackageInfo(this.appInfo.getPackageName(), this.appInfo.getVer(), this.position, this.source, true));
                }
            } else if (new File(apkPath).exists()) {
                AppInfoUtils.installApp(this.mContext, apkPath, new MyPackageInfo(this.appInfo.getPackageName(), this.appInfo.getVer(), this.position, this.source, true));
            } else {
                int notifyId = DownloadUtils.getInstance(this.mContext).generateDownladNotifyId();
                Logger.m3373e(TAG, "notifyId = " + notifyId);
                DownloadUtils.getInstance(this.mContext).addDownloadApkThread(new DownloadInfo(new MyNotifyDownloadHandler(this.appInfo.getAppName(), apkPath, this.appInfo.getPackageName(), this.appInfo.getUrl(), notifyId, 0, this.appInfo.getIconId(), this.appInfo.getVer(), this.position, this.source, this.appInfo.getAction(), this.appInfo.getFileVerifyCode(), null), this.appInfo.getPackageName(), this.appInfo.getVer(), this.position, this.source, this.appInfo.getAction(), this.appInfo.getFileVerifyCode(), true));
            }
        }
        stopSelf();
    }

    public void onServerAddressReponse(Session session) {
    }
}
