package com.zhuoyi.system.promotion.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.zhuoyi.system.promotion.listener.ZyPromSDK;
import com.zhuoyi.system.promotion.util.PromConstants;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.AppInfoUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.model.MyPackageInfo;
import java.util.List;

public class PackageInstallationReceiver extends BroadcastReceiver {
    public static final String TAG = "PackageInstallationReciever";

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String packageName = intent.getData().getSchemeSpecificPart();
        AppInfoUtils.packageInfoList = null;
        boolean init = true;
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            Logger.debug(TAG, "add package: " + packageName);
            PromUtils.getInstance(context).removeDefinedApk(packageName);
            List<MyPackageInfo> installedList = AppInfoUtils.installedPackageInfoList;
            if (installedList != null) {
                for (MyPackageInfo pInfo : installedList) {
                    Logger.debug(TAG, "add MyPackageInfo: " + pInfo.toString());
                    if (!TextUtils.isEmpty(pInfo.getPackageName()) && pInfo.getPackageName().equals(packageName) && pInfo.getVersionCode() >= 0) {
                        Intent i;
                        Intent intent1 = new Intent(PromConstants.PROM_RECEIVER_FILTER_PACKAGE_INSTALL);
                        intent1.putExtra(BundleConstants.BUNDLE_PACKAGE_NAME, packageName);
                        intent1.putExtra(BundleConstants.BUNDLE_VERSION_CODE, pInfo.getVersionCode());
                        context.sendBroadcast(intent1);
                        AppInfoUtils.removeApk(pInfo);
                        AppInfoUtils.installedPackageInfoList.remove(pInfo);
                        StatsPromUtils.getInstance(context).addAppInstallSuccessAction(packageName, pInfo.getVersionCode(), 0, pInfo.getPosition(), pInfo.getSource());
                        PromUtils.getInstance(context).saveInstalledInfo(pInfo);
                        if (pInfo.isImeOpen() && pInfo.getPosition() != 20) {
                            i = context.getPackageManager().getLaunchIntentForPackage(packageName);
                            if (i == null) {
                                i = new Intent(packageName);
                                i.setFlags(268435456);
                            }
                            try {
                                context.startActivity(i);
                                StatsPromUtils.getInstance(context).addAppLaunchAction(packageName, pInfo.getVersionCode(), 0, pInfo.getPosition(), pInfo.getSource());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (pInfo.getPosition() == 20) {
                            try {
                                Bundle b = new Bundle();
                                b.putString("zy_appid", TerminalInfoUtil.getAppId(context) + "apk");
                                b.putString(BundleConstants.BUNDLE_CPID_METADATA_KEY, TerminalInfoUtil.getCpId(context));
                                b.putString("zy_channel_id", TerminalInfoUtil.getChannelId(context));
                                b.putString(BundleConstants.BUNDLE_APPKEY_METADATA_KEY, TerminalInfoUtil.getAppKey(context));
                                i = new Intent();
                                i.putExtras(b);
                                i.setComponent(new ComponentName(pInfo.getPackageName(), pInfo.getActivityName()));
                                i.setFlags(268435456);
                                context.startActivity(i);
                                Logger.debug(TAG, "start plug-in apk success");
                            } catch (Exception e2) {
                                Logger.m3375p(e2);
                            }
                        }
                        init = false;
                    }
                }
            }
        }
        if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            Log.e(TAG, "remove package: " + packageName);
            if (AppInfoUtils.removedPackageInfoList != null) {
                for (MyPackageInfo pInfo2 : AppInfoUtils.removedPackageInfoList) {
                    if (pInfo2 != null && !TextUtils.isEmpty(pInfo2.getPackageName()) && pInfo2.getPackageName().equals(packageName)) {
                        intent1 = new Intent(PromConstants.PROM_RECEIVER_FILTER_PACKAGE_REMOVE);
                        intent1.putExtra(BundleConstants.BUNDLE_PACKAGE_NAME, packageName);
                        context.sendBroadcast(intent1);
                        AppInfoUtils.removedPackageInfoList.remove(pInfo2);
                        StatsPromUtils.getInstance(context).addAppUninstallSuccessAction(packageName, pInfo2.getVersionCode(), 0, pInfo2.getPosition(), pInfo2.getSource());
                        init = false;
                        break;
                    }
                }
            }
        }
        if (init) {
            ZyPromSDK.getInstance().initWithPlugIn(context, false);
        }
    }
}
