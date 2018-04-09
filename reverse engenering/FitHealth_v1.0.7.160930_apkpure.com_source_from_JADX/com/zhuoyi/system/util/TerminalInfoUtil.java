package com.zhuoyi.system.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.zhuoyi.system.config.ZySDKConfig;
import com.zhuoyi.system.network.object.TerminalInfo;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.promotion.listener.ZyPromSDK;
import com.zhuoyi.system.util.constant.CommConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import org.apache.http.conn.util.InetAddressUtils;

public class TerminalInfoUtil {
    private static String isInitWithKeyStr;
    private static TerminalInfo mTerminalInfo;
    private static TerminalInfo m_TerminalInfoForZone;

    private static void initTerminalInfo(Context c) {
        String verName;
        m_TerminalInfoForZone = new TerminalInfo();
        try {
            m_TerminalInfoForZone.setHsman(Build.PRODUCT);
            m_TerminalInfoForZone.setHstype(Build.MODEL);
            m_TerminalInfoForZone.setOsVer("android_" + VERSION.RELEASE);
        } catch (Exception e) {
            m_TerminalInfoForZone.setHsman("");
            m_TerminalInfoForZone.setHstype("");
            m_TerminalInfoForZone.setOsVer("android");
        }
        try {
            DisplayMetrics dm = new DisplayMetrics();
            dm = c.getResources().getDisplayMetrics();
            m_TerminalInfoForZone.setScreenWidth((short) dm.widthPixels);
            m_TerminalInfoForZone.setScreenHeight((short) dm.heightPixels);
        } catch (Exception e2) {
            m_TerminalInfoForZone.setScreenWidth((short) 0);
            m_TerminalInfoForZone.setScreenHeight((short) 0);
        }
        try {
            m_TerminalInfoForZone.setRamSize((short) getTotalMemory());
        } catch (Exception e3) {
            m_TerminalInfoForZone.setRamSize((short) 0);
        }
        try {
            m_TerminalInfoForZone.setImei(((TelephonyManager) c.getSystemService("phone")).getDeviceId());
        } catch (Exception e4) {
            m_TerminalInfoForZone.setImei("");
        }
        try {
            List<NeighboringCellInfo> infos = ((TelephonyManager) c.getSystemService("phone")).getNeighboringCellInfo();
            if (infos.size() > 0) {
                m_TerminalInfoForZone.setLac((short) ((NeighboringCellInfo) infos.get(0)).getLac());
            }
        } catch (Exception e5) {
            m_TerminalInfoForZone.setLac((short) 0);
        }
        String IMSI = getPhoneImsi(c);
        if (IMSI == null) {
            IMSI = "";
        }
        m_TerminalInfoForZone.setImsi(IMSI);
        m_TerminalInfoForZone.setProvidersName(getProvidersName(IMSI));
        m_TerminalInfoForZone.setSmsCenter("");
        m_TerminalInfoForZone.setNetworkType(getNetworkType(c));
        m_TerminalInfoForZone.setIp(getLocalIpAddress());
        int verCode = 0;
        try {
            verCode = AppInfoUtils.getPackageVersionCode(c);
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        String sdkVerName = ZySDKConfig.SDK_VERSION_NAME;
        try {
            verName = AppInfoUtils.getPackageVersionName(c);
        } catch (Exception e62) {
            verName = "android";
            e62.printStackTrace();
        }
        m_TerminalInfoForZone.setVersionCode("apkVCode=" + verCode + ";apkVName=" + verName + ";sdkVName=" + sdkVerName);
        try {
            m_TerminalInfoForZone.setChannelId(getApkChannelId(c));
        } catch (Exception e7) {
            m_TerminalInfoForZone.setChannelId("notfound");
        }
        m_TerminalInfoForZone.setAppId("Joy0001HZXT0001");
        try {
            mTerminalInfo = (TerminalInfo) m_TerminalInfoForZone.clone();
        } catch (Exception e8) {
            mTerminalInfo = m_TerminalInfoForZone;
        }
        try {
            mTerminalInfo.setAppId(getAppId(c));
        } catch (Exception e9) {
            mTerminalInfo.setAppId("notfound");
        }
    }

    public static TerminalInfo getTerminalInfo(Context c) {
        if (mTerminalInfo == null) {
            initTerminalInfo(c);
        }
        try {
            mTerminalInfo.setAppId(getAppId(c));
        } catch (Exception e) {
            mTerminalInfo.setAppId("notfound");
        }
        return mTerminalInfo;
    }

    public static TerminalInfo getTerminalInfoForZone(Context c) {
        if (m_TerminalInfoForZone == null) {
            initTerminalInfo(c);
        }
        m_TerminalInfoForZone.setAppId("Joy0001HZXT0001");
        return m_TerminalInfoForZone;
    }

    public static int getAppVersionCode(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            return i;
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getPackageName(Context context) {
        try {
            return context.getPackageName();
        } catch (Exception e) {
            Logger.error("get package name error.");
            return "";
        }
    }

    public static String getAppId(Context context) {
        String appId = "";
        if (isInitWithKey(context)) {
            appId = PromDBUtils.getInstance(context).queryCfgValueByKey("zy_appid");
        } else {
            try {
                appId = getMetaData(context).getString("zy_appid");
            } catch (Exception e) {
                Logger.m3375p(e);
            }
        }
        if (TextUtils.isEmpty(appId)) {
            Log.e(ZyPromSDK.TAG, "Get appId error.");
        }
        return appId;
    }

    private static int getTotalMemory() {
        int initial_memory = 0;
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            initial_memory = Integer.valueOf(localBufferedReader.readLine().split("\\s+")[1]).intValue() / 1024;
            localBufferedReader.close();
            return initial_memory;
        } catch (Exception e) {
            return initial_memory;
        }
    }

    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = ((NetworkInterface) en.nextElement()).getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipv4 = inetAddress.getHostAddress();
                        if (InetAddressUtils.isIPv4Address(ipv4)) {
                            return ipv4;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static String getIMSI(Context mContext) {
        String imsi = getPhoneImsi(mContext);
        if (imsi == null) {
            return getUUIDfromSD();
        }
        return imsi;
    }

    public static String getLocalMacAddress(Context context) {
        String mac = "";
        try {
            WifiInfo info = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            if (info == null) {
                return "";
            }
            mac = info.getMacAddress();
            return mac;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getUUIDfromSD() {
        String uuid = null;
        File mFile = new File(PhoneInfoUtils.PATH + "/" + PhoneInfoUtils.DIR_NAME + "/" + PhoneInfoUtils.UUID_FILE);
        if (AppInfoUtils.isSDCardAvailable() && mFile.exists()) {
            try {
                uuid = FileUtils.readFile(mFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (uuid != null) {
            return uuid;
        }
        uuid = UUID.randomUUID().toString();
        saveUUIDtoSD(uuid);
        return uuid;
    }

    private static void saveUUIDtoSD(String uuid) {
        String strDir = PhoneInfoUtils.PATH + "/" + PhoneInfoUtils.DIR_NAME;
        String strFile = new StringBuilder(String.valueOf(strDir)).append("/").append(PhoneInfoUtils.UUID_FILE).toString();
        File dir = new File(strDir);
        File mFile = new File(strFile);
        if (dir.exists() || dir.mkdirs()) {
            if (!mFile.exists()) {
                try {
                    if (!mFile.createNewFile()) {
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileUtils.writeFile(mFile, uuid, false);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String getApkChannelId(Context context) {
        return getCpId(context) + "@" + getChannelId(context);
    }

    private static Bundle getMetaData(Context context) throws Exception {
        Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
        if (bundle == null) {
            return new Bundle();
        }
        return bundle;
    }

    public static String getCpId(Context context) {
        String cpId = "";
        if (isInitWithKey(context)) {
            cpId = PromDBUtils.getInstance(context).queryCfgValueByKey(CommConstants.CPID_METADATA_KEY);
        } else {
            try {
                cpId = getMetaData(context).getString(CommConstants.CPID_METADATA_KEY);
            } catch (Exception e) {
                Logger.m3375p(e);
            }
        }
        if (TextUtils.isEmpty(cpId)) {
            Log.e(ZyPromSDK.TAG, "get cpId key error");
        }
        return cpId;
    }

    private static boolean isInitWithKey(Context context) {
        if (TextUtils.isEmpty(isInitWithKeyStr)) {
            isInitWithKeyStr = PromDBUtils.getInstance(context).queryCfgValueByKey(CommConstants.ZY_GET_DATA_PRIORITY);
        }
        if (TextUtils.isEmpty(isInitWithKeyStr)) {
            return false;
        }
        return "0".equals(isInitWithKeyStr);
    }

    public static String getAppKey(Context context) {
        String getAppKey = "";
        if (isInitWithKey(context)) {
            getAppKey = PromDBUtils.getInstance(context).queryCfgValueByKey(CommConstants.LOTUSSED_ZY_METADATA_KEY);
        } else {
            try {
                getAppKey = getMetaData(context).getString(CommConstants.LOTUSSED_ZY_METADATA_KEY);
            } catch (Exception e) {
                Logger.m3375p(e);
            }
        }
        if (TextUtils.isEmpty(getAppKey)) {
            Log.e(ZyPromSDK.TAG, "get app key error");
        }
        return getAppKey;
    }

    public static String getChannelId(Context context) {
        String channelId = "";
        if (isInitWithKey(context)) {
            channelId = PromDBUtils.getInstance(context).queryCfgValueByKey("zy_channel_id");
        } else {
            try {
                channelId = getMetaData(context).getString("zy_channel_id");
            } catch (Exception e) {
                Logger.m3375p(e);
            }
        }
        if (TextUtils.isEmpty(channelId)) {
            Log.e("ZyPromSdk", "get channel id error");
        }
        return channelId;
    }

    private static String getProvidersName(String IMSI) {
        if (IMSI == null) {
            return "4";
        }
        String ProvidersName;
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            ProvidersName = "1";
        } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
            ProvidersName = "2";
        } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005")) {
            ProvidersName = "3";
        } else {
            ProvidersName = "0";
        }
        return ProvidersName;
    }

    public static String getPhoneImsi(Context context) {
        String imsi = null;
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        try {
            imsi = mTelephonyManager.getSubscriberId();
        } catch (Exception e) {
        }
        if (imsi != null) {
            return imsi;
        }
        try {
            Method getImsiMethod = mTelephonyManager.getClass().getMethod("getSubscriberIdGemini", new Class[]{Integer.TYPE});
            if (getImsiMethod == null) {
                return imsi;
            }
            try {
                imsi = (String) getImsiMethod.invoke(mTelephonyManager, new Object[]{Integer.valueOf(1)});
                if (imsi != null) {
                    return imsi;
                }
                return (String) getImsiMethod.invoke(mTelephonyManager, new Object[]{Integer.valueOf(0)});
            } catch (IllegalArgumentException e2) {
                return imsi;
            } catch (IllegalAccessException e3) {
                return imsi;
            } catch (InvocationTargetException e4) {
                return imsi;
            }
        } catch (SecurityException e5) {
            return imsi;
        } catch (Exception e6) {
            return imsi;
        }
    }

    private static byte getNetworkType(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivity == null) {
            return (byte) 0;
        }
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (info == null) {
            return (byte) 0;
        }
        if (info.getType() == 0) {
            switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
                case 0:
                    return (byte) 4;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    return (byte) 1;
                case 8:
                case 9:
                case 10:
                    return (byte) 2;
                default:
                    return (byte) 0;
            }
        } else if (info.getType() == 1) {
            return (byte) 3;
        } else {
            return (byte) 0;
        }
    }
}
