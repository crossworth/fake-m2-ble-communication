package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import com.sina.weibo.sdk.api.share.ApiUtils;
import com.sina.weibo.sdk.api.share.ApiUtils.WeiboInfo;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.constant.WBConstants.Base;

public class SecurityHelper {
    private static final String WEIBO_MD5_SIGNATURE = "18da2bf10352443a00a5e046d9fca6bd";

    public static boolean validateAppSignatureForIntent(Context context, Intent intent) {
        boolean z = false;
        PackageManager pkgMgr = context.getPackageManager();
        if (pkgMgr != null) {
            ResolveInfo resolveInfo = pkgMgr.resolveActivity(intent, z);
            if (resolveInfo != null) {
                try {
                    z = containSign(pkgMgr.getPackageInfo(resolveInfo.activityInfo.packageName, 64).signatures, WEIBO_MD5_SIGNATURE);
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return z;
    }

    public static boolean checkResponseAppLegal(Context context, Intent intent) {
        WeiboInfo winfo = ApiUtils.queryWeiboInfo(context);
        if ((winfo != null && winfo.supportApi <= ApiUtils.BUILD_INT_VER_2_3) || winfo == null) {
            return true;
        }
        String appPackage = intent != null ? intent.getStringExtra(Base.APP_PKG) : null;
        if (appPackage == null || intent.getStringExtra(WBConstants.TRAN) == null || !ApiUtils.validateWeiboSign(context, appPackage)) {
            return false;
        }
        return true;
    }

    public static boolean containSign(Signature[] signatures, String destSign) {
        if (signatures == null || destSign == null) {
            return false;
        }
        for (Signature signature : signatures) {
            if (destSign.equals(MD5.hexdigest(signature.toByteArray()))) {
                return true;
            }
        }
        return false;
    }
}
