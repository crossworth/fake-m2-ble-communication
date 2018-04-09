package com.zhuoyi.encoder;

public class BaseUtils {
    public static String encodeByBase64(String inputStr) {
        if (inputStr != null) {
            return Base64.encodeToString(inputStr.getBytes(), 2);
        }
        return null;
    }

    public static String decodeByBase64(String inputStr) {
        if (inputStr != null) {
            return new String(Base64.decode(inputStr, 2));
        }
        return null;
    }
}
