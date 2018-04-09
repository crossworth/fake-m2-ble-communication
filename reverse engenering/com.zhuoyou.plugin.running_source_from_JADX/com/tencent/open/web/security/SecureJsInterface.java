package com.tencent.open.web.security;

import com.tencent.open.C1317a.C1277b;
import com.tencent.open.p036a.C1314f;

/* compiled from: ProGuard */
public class SecureJsInterface extends C1277b {
    public static boolean isPWDEdit = false;
    private String f4237a;

    public boolean customCallback() {
        return true;
    }

    public void curPosFromJS(String str) {
        C1314f.m3867b("openSDK_LOG.SecureJsInterface", "-->curPosFromJS: " + str);
        int i = -1;
        try {
            i = Integer.parseInt(str);
        } catch (Throwable e) {
            C1314f.m3868b("openSDK_LOG.SecureJsInterface", "-->curPosFromJS number format exception.", e);
        }
        if (i < 0) {
            throw new RuntimeException("position is illegal.");
        }
        if (C1346a.f4240c) {
        }
        if (!C1346a.f4239b) {
            this.f4237a = C1346a.f4238a;
            JniInterface.insetTextToArray(i, this.f4237a, this.f4237a.length());
            C1314f.m3864a("openSDK_LOG.SecureJsInterface", "curPosFromJS mKey: " + this.f4237a);
        } else if (Boolean.valueOf(JniInterface.BackSpaceChar(C1346a.f4239b, i)).booleanValue()) {
            C1346a.f4239b = false;
        }
    }

    public void isPasswordEdit(String str) {
        C1314f.m3870c("openSDK_LOG.SecureJsInterface", "-->is pswd edit, flag: " + str);
        int i = -1;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            C1314f.m3872e("openSDK_LOG.SecureJsInterface", "-->is pswd edit exception: " + e.getMessage());
        }
        if (i != 0 && i != 1) {
            throw new RuntimeException("is pswd edit flag is illegal.");
        } else if (i == 0) {
            isPWDEdit = false;
        } else if (i == 1) {
            isPWDEdit = true;
        }
    }

    public void clearAllEdit() {
        C1314f.m3870c("openSDK_LOG.SecureJsInterface", "-->clear all edit.");
        try {
            JniInterface.clearAllPWD();
        } catch (Throwable e) {
            C1314f.m3872e("openSDK_LOG.SecureJsInterface", "-->clear all edit exception: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getMD5FromNative() {
        C1314f.m3870c("openSDK_LOG.SecureJsInterface", "-->get md5 form native");
        String str = "";
        try {
            str = JniInterface.getPWDKeyToMD5(null);
            C1314f.m3864a("openSDK_LOG.SecureJsInterface", "-->getMD5FromNative, MD5= " + str);
            return str;
        } catch (Throwable e) {
            C1314f.m3872e("openSDK_LOG.SecureJsInterface", "-->get md5 form native exception: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
