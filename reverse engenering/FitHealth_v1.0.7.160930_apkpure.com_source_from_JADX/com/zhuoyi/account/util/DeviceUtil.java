package com.zhuoyi.account.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceUtil {
    private static int IDENTIFY_LEN = 14;
    private static SharedPreferences userPreferences;

    public static String getDeviceInfo(Context context) {
        String info = "{}";
        if (context == null) {
            return info;
        }
        String packageName = context.getPackageName();
        MyResource myResource = new MyResource(context);
        HashMap<String, String> publicParams = GetPublicParams.getPublicParaForPush(context, packageName, MyResource.getRaw("td"));
        String IMSI = (String) publicParams.get("imsi");
        String IMEI = (String) publicParams.get(SocializeProtocolConstants.PROTOCOL_KEY_IMEI);
        String MAC = getMacAddress(context);
        String TD = (String) publicParams.get("td");
        String packName = context.getPackageName();
        if (MAC == null) {
            MAC = "";
        }
        if (IMSI == null) {
            IMSI = "123456789012345";
        }
        if (IMEI == null) {
            IMEI = "";
        }
        if (TD == null) {
            TD = "";
        }
        if (packName == null) {
            packName = "";
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("imsi", IMSI);
            jsonObject.put(SocializeProtocolConstants.PROTOCOL_KEY_IMEI, IMEI);
            jsonObject.put(SocializeProtocolConstants.PROTOCOL_KEY_MAC, MAC);
            jsonObject.put("td", TD);
            jsonObject.put("packName", packName);
            info = EncoderAndDecoder.encrypt(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static String getMacAddress(Context context) {
        if (userPreferences == null) {
            userPreferences = context.getSharedPreferences("userCenterPreferences", 0);
        }
        String mac = userPreferences.getString("mac_address", null);
        if (!TextUtils.isEmpty(mac)) {
            return mac;
        }
        WifiInfo wifiInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo == null || TextUtils.isEmpty(wifiInfo.getMacAddress())) {
            return null;
        }
        mac = wifiInfo.getMacAddress();
        Editor editor = userPreferences.edit();
        editor.putString("mac_address", mac);
        editor.commit();
        return mac;
    }

    private static String formatIdentify(String identify) {
        if (TextUtils.isEmpty(identify)) {
            return identify;
        }
        identify = identify.trim();
        int len = identify.length();
        if (len == IDENTIFY_LEN) {
            return identify;
        }
        if (len > IDENTIFY_LEN) {
            return identify.substring(0, IDENTIFY_LEN);
        }
        while (len < IDENTIFY_LEN) {
            identify = identify + "0";
            len++;
        }
        return identify;
    }
}
