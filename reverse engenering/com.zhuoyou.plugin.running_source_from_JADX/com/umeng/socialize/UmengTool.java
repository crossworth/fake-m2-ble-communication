package com.umeng.socialize;

import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UmengTool {
    private static final char[] HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static PackageManager manager;
    private static PackageInfo packageInfo;

    public static void getSignature(Context context) {
        String pkName = context.getPackageName();
        manager = context.getPackageManager();
        if (TextUtils.isEmpty(pkName)) {
            Toast.makeText(context, "应用程序的包名不能为空！", 0);
            return;
        }
        try {
            packageInfo = manager.getPackageInfo(pkName, 64);
            showDialog(context, "包名：" + packageInfo.packageName + "\n" + "签名:" + getSignatureDigest(packageInfo).toLowerCase() + "\n" + "facebook keyhash:" + facebookHashKey(packageInfo));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String facebookHashKey(PackageInfo info) {
        try {
            Signature[] signatureArr = info.signatures;
            if (0 < signatureArr.length) {
                Signature signature = signatureArr[0];
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), 0);
            }
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    private static void showDialog(Context context, String result) {
        new Builder(context).setTitle("umengtool").setMessage(result).setPositiveButton("确定", null).show();
    }

    public static void getREDICRECT_URL(Context context) {
        showDialog(context, Config.REDIRECT_URL);
    }

    public static String getSignatureDigest(PackageInfo pkgInfo) {
        if (pkgInfo.signatures.length <= 0) {
            return "";
        }
        Signature signature = pkgInfo.signatures[0];
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return toHexString(md5.digest(signature.toByteArray()));
    }

    private static String toHexString(byte[] rawByteArray) {
        char[] chars = new char[(rawByteArray.length * 2)];
        for (int i = 0; i < rawByteArray.length; i++) {
            byte b = rawByteArray[i];
            chars[i * 2] = HEX_CHAR[(b >>> 4) & 15];
            chars[(i * 2) + 1] = HEX_CHAR[b & 15];
        }
        return new String(chars);
    }

    public static void checkWx(Context context) {
        String pkName = context.getPackageName();
        String classPath = pkName + ".wxapi.WXEntryActivity";
        try {
            manager = context.getPackageManager();
            packageInfo = manager.getPackageInfo(pkName, 64);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        String result = getSignatureDigest(packageInfo);
        try {
            if (Class.forName(classPath) == null) {
                new Builder(context).setTitle("umengtool").setMessage("请检查微信后台注册签名：" + result.toLowerCase() + "\n" + "没有配置微信回调activity或配置不正确").setPositiveButton("确定", null).show();
            } else {
                new Builder(context).setTitle("umengtool").setMessage("请检查微信后台注册签名：" + result.toLowerCase() + "\n" + "Activity微信配置正确").setPositiveButton("确定", null).show();
            }
        } catch (ClassNotFoundException e2) {
            new Builder(context).setTitle("umengtool").setMessage("请检查微信后台注册签名：" + result.toLowerCase() + "\n" + "没有配置微信回调activity或配置不正确").setPositiveButton("确定", null).show();
            e2.printStackTrace();
        }
    }

    public static void checkSina(Context context) {
        String pkName = context.getPackageName();
        String classPath = pkName + ".WBShareActivity";
        try {
            manager = context.getPackageManager();
            packageInfo = manager.getPackageInfo(pkName, 64);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        String result = getSignatureDigest(packageInfo);
        try {
            if (Class.forName(classPath) == null) {
                new Builder(context).setTitle("umengtool").setMessage("请检查sina后台注册签名：" + result.toLowerCase() + "\n" + "没有配置新浪回调activity或配置不正确").setPositiveButton("确定", null).show();
            } else {
                new Builder(context).setTitle("umengtool").setMessage("请检查sina后台注册签名：" + result.toLowerCase() + "\n" + "Activity新浪配置正确").setPositiveButton("确定", null).show();
            }
        } catch (ClassNotFoundException e2) {
            new Builder(context).setTitle("umengtool").setMessage("请检查sina后台注册签名：" + result.toLowerCase() + "\n" + "没有配置新浪回调activity或配置不正确").setPositiveButton("确定", null).show();
            e2.printStackTrace();
        }
    }

    public static void checkAlipay(Context context) {
        try {
            if (Class.forName(context.getPackageName() + ".apshare.ShareEntryActivity") == null) {
                new Builder(context).setTitle("umengtool").setMessage("没有配置支付宝回调activity或配置不正确").setPositiveButton("确定", null).show();
            } else {
                new Builder(context).setTitle("umengtool").setMessage("支付宝配置正确").setPositiveButton("确定", null).show();
            }
        } catch (ClassNotFoundException e) {
            new Builder(context).setTitle("umengtool").setMessage("没有配置支付宝回调activity或配置不正确").setPositiveButton("确定", null).show();
            e.printStackTrace();
        }
    }

    public static void checkQQ(Context context) {
        try {
            context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.tauth.AuthActivity"), 0);
        } catch (NameNotFoundException e) {
            new Builder(context).setTitle("umengtool").setMessage("没有在AndroidManifest.xml中检测到com.tencent.tauth.AuthActivity,请加上com.tencent.tauth.AuthActivity,并配置<data android:scheme=\"tencentappid\" />,详细信息请查看官网文档.").setPositiveButton("确定", null).show();
        }
        try {
            context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.connect.common.AssistActivity"), 0);
            new Builder(context).setTitle("umengtool").setMessage("qq配置正确").setPositiveButton("确定", null).show();
        } catch (NameNotFoundException e2) {
            new Builder(context).setTitle("umengtool").setMessage("没有在AndroidManifest.xml中检测到com.tencent.connect.common.AssistActivity,请加上com.tencent.connect.common.AssistActivity,详细信息请查看官网文档.").setPositiveButton("确定", null).show();
        }
    }
}
