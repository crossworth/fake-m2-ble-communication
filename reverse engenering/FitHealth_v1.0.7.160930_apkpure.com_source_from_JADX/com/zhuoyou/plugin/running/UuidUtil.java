package com.zhuoyou.plugin.running;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.security.MessageDigest;
import java.util.UUID;

public class UuidUtil {
    private static String uuid = "";

    public static String getDeviceId(Context context) {
        String result = Tools.getUuid(context);
        if (!TextUtils.isEmpty(result)) {
            return result;
        }
        StringBuilder deviceId = new StringBuilder();
        deviceId.append("a");
        try {
            String wifiMac = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (TextUtils.isEmpty(wifiMac)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
                String imei = tm.getDeviceId();
                if (TextUtils.isEmpty(imei)) {
                    String sn = tm.getSimSerialNumber();
                    if (TextUtils.isEmpty(sn)) {
                        String uuid = getUUID(context);
                        if (!TextUtils.isEmpty(uuid)) {
                            deviceId.append("id");
                            deviceId.append(uuid);
                            Log.e("getDeviceId : ", deviceId.toString());
                            Tools.setUuid(context, MD5(deviceId.toString()));
                            return MD5(deviceId.toString());
                        }
                        Log.e("getDeviceId : ", deviceId.toString());
                        Tools.setUuid(context, MD5(deviceId.toString()));
                        return MD5(deviceId.toString());
                    }
                    deviceId.append(SocializeProtocolConstants.PROTOCOL_KEY_SHARE_NUM);
                    deviceId.append(sn);
                    Log.e("getDeviceId : ", deviceId.toString());
                    Tools.setUuid(context, MD5(deviceId.toString()));
                    return MD5(deviceId.toString());
                }
                deviceId.append(SocializeProtocolConstants.PROTOCOL_KEY_IMEI);
                deviceId.append(imei);
                Log.e("getDeviceId : ", deviceId.toString());
                Tools.setUuid(context, MD5(deviceId.toString()));
                return MD5(deviceId.toString());
            }
            deviceId.append("wifi");
            deviceId.append(wifiMac);
            Log.e("getDeviceId : ", deviceId.toString());
            Tools.setUuid(context, MD5(deviceId.toString()));
            return MD5(deviceId.toString());
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
            Tools.setUuid(context, MD5(deviceId.toString()));
        }
    }

    public static String getUUID(Context context) {
        uuid = Tools.getUuid(context);
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            Tools.setUuid(context, uuid);
        }
        Log.e("chenxin", "getUUID : " + uuid);
        return uuid;
    }

    public static final String MD5(String s) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            char[] str = new char[(j * 2)];
            int k = 0;
            for (byte byte0 : mdInst.digest()) {
                int i = k + 1;
                str[k] = hexDigits[(byte0 >>> 4) & 15];
                k = i + 1;
                str[i] = hexDigits[byte0 & 15];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
