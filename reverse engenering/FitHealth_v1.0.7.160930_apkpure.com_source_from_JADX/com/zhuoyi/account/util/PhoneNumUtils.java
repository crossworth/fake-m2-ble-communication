package com.zhuoyi.account.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumUtils {
    public static boolean isPhoneNumberValid(String mobiles) {
        return Pattern.compile("^1[34578]{1}[0-9]{1}[0-9]{8}$").matcher(mobiles).matches();
    }

    public static Long checkPhoneNum(String phoneNum) throws Exception {
        if (Pattern.compile("^((\\+{0,1}86){0,1})1[0-9]{10}").matcher(phoneNum).matches()) {
            Matcher m2 = Pattern.compile("^((\\+{0,1}86){0,1})").matcher(phoneNum);
            StringBuffer sb = new StringBuffer();
            while (m2.find()) {
                m2.appendReplacement(sb, "");
            }
            m2.appendTail(sb);
            return Long.valueOf(Long.parseLong(sb.toString()));
        }
        throw new Exception("The format of phoneNum " + phoneNum + "  is not correct!Please correct it");
    }
}
