package com.zhuoyou.plugin.selfupdate;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import org.json.JSONException;
import org.json.JSONObject;
import p031u.aly.au;

public class TerminalInfo {
    private String apkVerName;
    private int apkVersion;
    private String appId;
    private String channelId;
    private String cpu;
    private String hsman;
    private String hstype;
    private String imei;
    private String imsi;
    private String ip;
    private short lac;
    private byte networkType;
    private String osVer;
    private String packageName;
    private long ramSize;
    private short screenHeight;
    private short screenWidth;

    public String getCpu() {
        return this.cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getHsman() {
        return this.hsman;
    }

    public void setHsman(String hsman) {
        this.hsman = hsman;
    }

    public String getHstype() {
        return this.hstype;
    }

    public void setHstype(String hstype) {
        this.hstype = hstype;
    }

    public String getOsVer() {
        return this.osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public short getScreenWidth() {
        return this.screenWidth;
    }

    public void setScreenWidth(short screenWidth) {
        this.screenWidth = screenWidth;
    }

    public short getScreenHeight() {
        return this.screenHeight;
    }

    public void setScreenHeight(short screenHeight) {
        this.screenHeight = screenHeight;
    }

    public long getRamSize() {
        return this.ramSize;
    }

    public void setRamSize(long ramSize) {
        this.ramSize = ramSize;
    }

    public String getImsi() {
        return this.imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public short getLac() {
        return this.lac;
    }

    public void setLac(short lac) {
        this.lac = lac;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public byte getNetworkType() {
        return this.networkType;
    }

    public void setNetworkType(byte networkType) {
        this.networkType = networkType;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getApkVersion() {
        return this.apkVersion;
    }

    public void setApkVersion(int apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApkVerName() {
        return this.apkVerName;
    }

    public void setApkVerName(String apkVerName) {
        this.apkVerName = apkVerName;
    }

    public String toString() {
        JSONObject jsonTerminalInfo = new JSONObject();
        JSONObject jsonObjBody = new JSONObject();
        try {
            jsonTerminalInfo.put("hman", this.hsman);
            jsonTerminalInfo.put("htype", this.hstype);
            jsonTerminalInfo.put("sWidth", this.screenWidth);
            jsonTerminalInfo.put("sHeight", this.screenHeight);
            jsonTerminalInfo.put("ramSize", this.ramSize);
            jsonTerminalInfo.put("lac", this.lac);
            jsonTerminalInfo.put("netType", this.networkType);
            jsonTerminalInfo.put("chId", this.channelId);
            jsonTerminalInfo.put("osVer", this.osVer);
            jsonTerminalInfo.put(MessageObj.APPID, this.appId);
            jsonTerminalInfo.put("apkVer", this.apkVersion);
            jsonTerminalInfo.put("pName", this.packageName);
            jsonTerminalInfo.put("apkVerName", this.apkVerName);
            jsonTerminalInfo.put("imsi", this.imsi);
            jsonTerminalInfo.put(SocializeProtocolConstants.PROTOCOL_KEY_IMEI, this.imei);
            jsonTerminalInfo.put(au.f3586o, this.cpu);
            jsonObjBody.put("tInfo", jsonTerminalInfo);
            Log.i("msg", "jsonObjBody:" + jsonObjBody.toString());
            return jsonObjBody.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static TerminalInfo generateTerminalInfo(Context context) {
        return generateTerminalInfo(context, "", "");
    }

    public static TerminalInfo generateTerminalInfo(Context context, String appId, String chId) {
        int versionCode = 0;
        String pName = "";
        String apkVersionName = "";
        TelephonyManager tManager = (TelephonyManager) context.getSystemService("phone");
        TerminalInfo mTerminalInfo = new TerminalInfo();
        DisplayMetrics outMetrics = context.getResources().getDisplayMetrics();
        mTerminalInfo.setHsman(Build.MANUFACTURER);
        mTerminalInfo.setHstype(Build.MODEL);
        mTerminalInfo.setOsVer(VERSION.RELEASE);
        mTerminalInfo.setScreenHeight((short) outMetrics.heightPixels);
        mTerminalInfo.setScreenWidth((short) outMetrics.widthPixels);
        mTerminalInfo.setAppId(appId);
        mTerminalInfo.setChannelId(chId);
        PackageInfo pInfo = getPackageInfo(context, context.getPackageName());
        if (pInfo != null) {
            versionCode = pInfo.versionCode;
            apkVersionName = pInfo.versionName;
        }
        String imei = "123456789012345";
        mTerminalInfo.setApkVersion(versionCode);
        mTerminalInfo.setPackageName(context.getPackageName());
        mTerminalInfo.setApkVerName(apkVersionName);
        if (tManager.getDeviceId() != null) {
            imei = tManager.getDeviceId();
        }
        mTerminalInfo.setImei(imei);
        mTerminalInfo.setImsi(tManager.getSubscriberId());
        mTerminalInfo.setNetworkType(getNetworkType(context));
        mTerminalInfo.setRamSize(getAndroidRamSize());
        mTerminalInfo.setCpu(Build.HARDWARE);
        return mTerminalInfo;
    }

    private static PackageInfo getPackageInfo(Context context, String pName) {
        PackageInfo pinfo = null;
        try {
            pinfo = context.getPackageManager().getPackageInfo(pName, 16384);
        } catch (NameNotFoundException e) {
        }
        return pinfo;
    }

    private static byte getNetworkType(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (info == null) {
            return (byte) 0;
        }
        if (info.getType() == 1) {
            return (byte) 3;
        }
        if (info.getType() != 0) {
            return (byte) 0;
        }
        int subType = info.getSubtype();
        if (subType == 3 || subType == 8 || subType == 5 || subType == 6) {
            return (byte) 2;
        }
        return (byte) 1;
    }

    private static long getAndroidRamSize() {
        String[] meminfoLabels = new String[]{"MemTotal:"};
        long[] meminfoValues = new long[]{-1};
        Class<?> proc = null;
        try {
            proc = Class.forName("android.os.Process");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            proc.getMethod("readProcLines", new Class[]{String.class, String[].class, long[].class}).invoke(proc.newInstance(), new Object[]{"/proc/meminfo", meminfoLabels, meminfoValues});
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        String RAM = "null";
        if (meminfoValues[0] != -1) {
            RAM = Long.toString(meminfoValues[0] / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + "M";
        }
        return meminfoValues[0];
    }
}
