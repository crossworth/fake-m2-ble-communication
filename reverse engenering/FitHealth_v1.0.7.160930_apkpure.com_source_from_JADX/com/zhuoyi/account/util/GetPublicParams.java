package com.zhuoyi.account.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyi.system.promotion.util.PromConstants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class GetPublicParams {
    public static HashMap<String, String> getPublicParaForPush(Context context, String pName, int id) {
        String NTstr;
        String trim;
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        String imei = tm.getDeviceId();
        WindowManager winManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics outMetrics = new DisplayMetrics();
        winManager.getDefaultDisplay().getMetrics(outMetrics);
        String lcdResolution = Integer.toString(outMetrics.widthPixels) + "x" + Integer.toString(outMetrics.heightPixels);
        String lcdResolution_wh = "&w=" + Integer.toString(outMetrics.widthPixels) + "&h=" + Integer.toString(outMetrics.heightPixels);
        String sdcardStatus = Environment.getExternalStorageState();
        if (sdcardStatus.equals("mounted") || sdcardStatus.equals("shared")) {
            sdcardStatus = "1";
        } else {
            sdcardStatus = "0";
        }
        if (getAvailableNetWorkType(context) != 0) {
            NTstr = "wifi";
        } else if (tm.getNetworkType() == 3) {
            NTstr = "3g";
        } else {
            NTstr = "2g";
        }
        String imsiStr = tm.getSubscriberId();
        String googleVersion = VERSION.RELEASE;
        String gSensorStatus = "0";
        if (((SensorManager) context.getSystemService("sensor")).getSensorList(4).size() > 0) {
            gSensorStatus = "1";
        }
        String batch = Build.HARDWARE;
        String softVersion = Build.DISPLAY;
        String androidVersion = VERSION.RELEASE;
        String mem = getTotalMemory(context);
        String versionCode = getVersionCode(context, pName) + "";
        String td = getTD(context, id);
        HashMap<String, String> hm = new HashMap();
        hm.put("iccid", tm.getSimSerialNumber());
        hm.put("uuid", getMyUUID(context));
        hm.put(SocializeProtocolConstants.PROTOCOL_KEY_IMEI, imei);
        hm.put("imsi", imsiStr);
        hm.put("lcd", lcdResolution);
        hm.put("sdcardStatus", sdcardStatus);
        hm.put("NTstr", NTstr);
        hm.put("gSensorStatus", gSensorStatus);
        hm.put("batch", batch);
        hm.put("softVersion", softVersion);
        hm.put("androidVersion", androidVersion);
        hm.put(PromConstants.PROM_HTML5_INFO_VERSION_CODE, versionCode);
        hm.put("lcdResolution_wh", lcdResolution_wh);
        String str = "td";
        if (td != null) {
            trim = td.trim();
        } else {
            trim = null;
        }
        hm.put(str, trim);
        return hm;
    }

    public static int getAvailableNetWorkType(Context context) {
        int netWorkType = -1;
        Log.i("getAva", "getAvailableNetWorkType enter");
        try {
            ConnectivityManager connetManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connetManager == null) {
                Log.e("getAva", "getAvailableNetWorkType, connetManager == null");
                return -1;
            }
            NetworkInfo[] infos = connetManager.getAllNetworkInfo();
            if (infos == null) {
                return -1;
            }
            int i = 0;
            while (i < infos.length && infos[i] != null) {
                if (infos[i].isConnected() && infos[i].isAvailable()) {
                    netWorkType = infos[i].getType();
                    Log.i("getAva", "getAvailableNetWorkType, netWorkType = " + netWorkType);
                    break;
                }
                i++;
            }
            return netWorkType;
        } catch (Exception e) {
            Log.e("getAva", "getAvailableNetWorkType exception");
            e.printStackTrace();
        }
    }

    public static String getTotalMemory(Context context) {
        long initial_memory = 0;
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            String str2 = localBufferedReader.readLine();
            String[] arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            initial_memory = (long) (Integer.valueOf(arrayOfString[1]).intValue() * 1024);
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return Formatter.formatFileSize(context, initial_memory);
    }

    public static int getVersionCode(Context context, String pName) {
        int versionCode = 0;
        try {
            return context.getPackageManager().getPackageInfo(pName, 16384).versionCode;
        } catch (NameNotFoundException e) {
            return versionCode;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getTD(android.content.Context r6, int r7) {
        /*
        r5 = r6.getResources();
        r3 = r5.openRawResource(r7);
        r1 = new java.io.DataInputStream;
        r1.<init>(r3);
        r0 = 0;
        r5 = r3.available();	 Catch:{ IOException -> 0x0028 }
        r0 = new byte[r5];	 Catch:{ IOException -> 0x0028 }
        r1.readFully(r0);	 Catch:{ IOException -> 0x0028 }
        r1.close();	 Catch:{ IOException -> 0x0023 }
        r3.close();	 Catch:{ IOException -> 0x0023 }
    L_0x001d:
        r4 = new java.lang.String;
        r4.<init>(r0);
        return r4;
    L_0x0023:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x001d;
    L_0x0028:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x0038 }
        r1.close();	 Catch:{ IOException -> 0x0033 }
        r3.close();	 Catch:{ IOException -> 0x0033 }
        goto L_0x001d;
    L_0x0033:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x001d;
    L_0x0038:
        r5 = move-exception;
        r1.close();	 Catch:{ IOException -> 0x0040 }
        r3.close();	 Catch:{ IOException -> 0x0040 }
    L_0x003f:
        throw r5;
    L_0x0040:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyi.account.util.GetPublicParams.getTD(android.content.Context, int):java.lang.String");
    }

    private static String getMyUUID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        String uniqueId = new UUID((long) ("" + Secure.getString(context.getContentResolver(), "android_id")).hashCode(), (((long) ("" + tm.getDeviceId()).hashCode()) << 32) | ((long) ("" + tm.getSimSerialNumber()).hashCode())).toString();
        Log.d("debug", "uuid=" + uniqueId);
        return uniqueId;
    }
}
