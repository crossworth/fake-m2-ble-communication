package com.zhuoyi.system.util;

import android.text.TextUtils;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyi.system.config.ZySDKConfig;
import java.security.MessageDigest;

public class EncryptUtils {
    private static final byte[] ZY_DEBUG_FILE_NAME = new byte[]{(byte) 53, (byte) 53, (byte) 49, (byte) 51, (byte) 100, (byte) 52, (byte) 102, (byte) 54, (byte) 53, (byte) 51, (byte) 57, (byte) 53, (byte) 50, (byte) 51, (byte) 97, (byte) 55, (byte) 50, (byte) 54, (byte) 52, (byte) 55, (byte) 101, (byte) 50, (byte) 48, (byte) 55, (byte) 50, (byte) 55, (byte) 102, (byte) 54, (byte) 48, (byte) 55, (byte) 53, (byte) 54, (byte) 50, (byte) 55, (byte) 52, (byte) 55, (byte) 57, (byte) 54, (byte) 53, (byte) 54, (byte) 51, (byte) 55, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52};
    private static final byte[] ZY_DEVELOP_CRTPT_KEY = new byte[]{(byte) 102, (byte) 53, (byte) 102, (byte) 53, (byte) 97, (byte) 54, (byte) 52, (byte) 52, (byte) 99, (byte) 54, (byte) 102, (byte) 54, (byte) 55, (byte) 54, (byte) 102, (byte) 53, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52};
    private static final byte[] ZY_DEVELOP_NETWORK_ADDR = new byte[]{(byte) 49, (byte) 51, (byte) 48, (byte) 51, (byte) 49, (byte) 51, (byte) 101, (byte) 50, (byte) 57, (byte) 51, (byte) 53, (byte) 51, (byte) 101, (byte) 50, (byte) 57, (byte) 51, (byte) 55, (byte) 51, (byte) 101, (byte) 50, (byte) 49, (byte) 51, (byte) 55, (byte) 51, (byte) 56, (byte) 51, (byte) 97, (byte) 51, (byte) 56, (byte) 51, (byte) 48, (byte) 51, (byte) 49, (byte) 51, (byte) 48, (byte) 51, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52};
    private static final byte[] ZY_FILE_NAME = new byte[]{(byte) 101, (byte) 50, (byte) 51, (byte) 54, (byte) 102, (byte) 54, (byte) 100, (byte) 54, (byte) 101, (byte) 50, (byte) 49, (byte) 54, (byte) 101, (byte) 54, (byte) 52, (byte) 54, (byte) 50, (byte) 55, (byte) 102, (byte) 54, (byte) 57, (byte) 54, (byte) 52, (byte) 54, (byte) 101, (byte) 50, (byte) 49, (byte) 54, (byte) 48, (byte) 55, (byte) 48, (byte) 55, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52};
    private static final byte[] ZY_PLUG_IN_ACTIVITY_NAME = new byte[]{(byte) 51, (byte) 54, (byte) 102, (byte) 54, (byte) 100, (byte) 54, (byte) 101, (byte) 50, (byte) 55, (byte) 54, (byte) 101, (byte) 50, (byte) 53, (byte) 54, (byte) 101, (byte) 54, (byte) 55, (byte) 54, (byte) 57, (byte) 54, (byte) 101, (byte) 54, (byte) 53, (byte) 54, (byte) 101, (byte) 50, (byte) 100, (byte) 52, (byte) 49, (byte) 54, (byte) 57, (byte) 54, (byte) 101, (byte) 54, (byte) 49, (byte) 52, (byte) 51, (byte) 54, (byte) 52, (byte) 55, (byte) 57, (byte) 54, (byte) 54, (byte) 55, (byte) 57, (byte) 54, (byte) 52, (byte) 55, (byte) 57, (byte) 55, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52};
    private static final byte[] ZY_PLUG_IN_PACKAGE_NAME = new byte[]{(byte) 51, (byte) 54, (byte) 102, (byte) 54, (byte) 100, (byte) 54, (byte) 101, (byte) 50, (byte) 55, (byte) 54, (byte) 101, (byte) 50, (byte) 53, (byte) 54, (byte) 101, (byte) 54, (byte) 55, (byte) 54, (byte) 57, (byte) 54, (byte) 101, (byte) 54, (byte) 53, (byte) 54, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52};
    private static final byte[] ZY_RELEASE_CRTPT_KEY = new byte[]{(byte) 102, (byte) 53, (byte) 50, (byte) 51, (byte) 56, (byte) 55, (byte) 102, (byte) 53, (byte) 50, (byte) 51, (byte) 99, (byte) 54, (byte) 50, (byte) 53, (byte) 102, (byte) 53, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52};
    private static final byte[] ZY_RELEASE_NETWORK_ADDR = new byte[]{(byte) 48, (byte) 55, (byte) 53, (byte) 55, (byte) 51, (byte) 55, (byte) 56, (byte) 54, (byte) 101, (byte) 50, (byte) 97, (byte) 55, (byte) 56, (byte) 54, (byte) 53, (byte) 55, (byte) 102, (byte) 54, (byte) 57, (byte) 55, (byte) 57, (byte) 54, (byte) 52, (byte) 55, (byte) 53, (byte) 54, (byte) 51, (byte) 54, (byte) 56, (byte) 54, (byte) 101, (byte) 50, (byte) 51, (byte) 54, (byte) 102, (byte) 54, (byte) 100, (byte) 54, (byte) 101, (byte) 50, (byte) 51, (byte) 54, (byte) 101, (byte) 54, (byte) 97, (byte) 51, (byte) 56, (byte) 51, (byte) 48, (byte) 51, (byte) 49, (byte) 51, (byte) 48, (byte) 51, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52, (byte) 54, (byte) 52};

    public static String getUnKey(byte[] allXBytes) {
        int i;
        byte[] allBytes = new byte[(allXBytes.length / 2)];
        for (i = 0; i < allXBytes.length / 2; i++) {
            allBytes[i] = (byte) ((Char2int((char) allXBytes[i * 2]) << 4) + Char2int((char) allXBytes[(i * 2) + 1]));
        }
        int textLength = allBytes.length / 2;
        byte[] noModifyKey = new byte[textLength];
        byte[] noModify = new byte[textLength];
        byte[] bytes6 = new byte[textLength];
        System.arraycopy(allBytes, 0, noModify, 0, textLength);
        System.arraycopy(allBytes, textLength, noModifyKey, 0, textLength);
        for (i = 0; i < textLength; i++) {
            bytes6[i] = circleLeft(noModify[i], noModifyKey[i]);
        }
        return new String(bytes6);
    }

    public static byte circleRight(byte b, int i) {
        int a = b & 255;
        return (byte) (((a >> i) | (a << (8 - i))) & 255);
    }

    public static byte circleLeft(byte b, int i) {
        int a = b & 255;
        return (byte) (((a << i) | (a >> (8 - i))) & 255);
    }

    public static int Char2int(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c < 'a' || c > 'f') {
            return -1;
        }
        return (c - 97) + 10;
    }

    public static String getEncryptKey() {
        return getUnKey(ZY_RELEASE_CRTPT_KEY);
    }

    public static String getNetworkAddr() {
        String address = ZySDKConfig.getInstance().getLocAddress();
        if (!TextUtils.isEmpty(address)) {
            return address;
        }
        if (ZySDKConfig.getInstance().isDebugMode()) {
            return getUnKey(ZY_DEVELOP_NETWORK_ADDR);
        }
        return getUnKey(ZY_RELEASE_NETWORK_ADDR);
    }

    public static String getZyFile() {
        return getUnKey(ZY_FILE_NAME);
    }

    public static String getDebugFileName() {
        return getUnKey(ZY_DEBUG_FILE_NAME);
    }

    public static String getEncrypt(String content) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes());
            byte[] buffer = md.digest();
            StringBuffer sb = new StringBuffer(buffer.length * 2);
            for (int i = 0; i < buffer.length; i++) {
                sb.append(Character.forDigit((buffer[i] & SocializeConstants.MASK_USER_CENTER_HIDE_AREA) >> 4, 16));
                sb.append(Character.forDigit(buffer[i] & 15, 16));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static String getPlugInPackageName() {
        return getUnKey(ZY_PLUG_IN_PACKAGE_NAME);
    }

    public static String getPlugInActivityName() {
        return getUnKey(ZY_PLUG_IN_ACTIVITY_NAME);
    }
}
