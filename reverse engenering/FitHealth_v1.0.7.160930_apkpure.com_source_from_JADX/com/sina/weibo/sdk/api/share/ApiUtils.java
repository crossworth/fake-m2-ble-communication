package com.sina.weibo.sdk.api.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.text.TextUtils;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.MD5;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiUtils {
    public static final int BUILD_INT = 10350;
    public static final int BUILD_INT_VER_2_2 = 10351;
    public static final int BUILD_INT_VER_2_3 = 10352;
    public static final int BUILD_INT_VER_2_5 = 10353;
    private static final String TAG = "ApiUtils";
    private static final String WEIBO_IDENTITY_ACTION = "com.sina.weibo.action.sdkidentity";
    private static final Uri WEIBO_NAME_URI = Uri.parse("content://com.sina.weibo.sdkProvider/query/package");
    private static final String WEIBO_SIGN = "18da2bf10352443a00a5e046d9fca6bd";

    public static class WeiboInfo {
        public String packageName;
        public int supportApi;

        public String toString() {
            return "WeiboInfo: PackageName = " + this.packageName + ", supportApi = " + this.supportApi;
        }
    }

    private static com.sina.weibo.sdk.api.share.ApiUtils.WeiboInfo queryWeiboInfoByProvider(android.content.Context r15) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x006d in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r14 = 0;
        r0 = r15.getContentResolver();
        r6 = 0;
        r1 = WEIBO_NAME_URI;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r2 = 0;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r3 = 0;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r4 = 0;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r5 = 0;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r6 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        if (r6 != 0) goto L_0x001a;
    L_0x0012:
        if (r6 == 0) goto L_0x0018;
    L_0x0014:
        r6.close();
        r6 = 0;
    L_0x0018:
        r13 = r14;
    L_0x0019:
        return r13;
    L_0x001a:
        r1 = "support_api";	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r11 = r6.getColumnIndex(r1);	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r1 = "package";	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r8 = r6.getColumnIndex(r1);	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r1 = r6.moveToFirst();	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        if (r1 == 0) goto L_0x0077;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
    L_0x002c:
        r12 = -1;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r10 = r6.getString(r11);	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r12 = java.lang.Integer.parseInt(r10);	 Catch:{ NumberFormatException -> 0x0055 }
    L_0x0035:
        r9 = r6.getString(r8);	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r1 = android.text.TextUtils.isEmpty(r9);	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        if (r1 != 0) goto L_0x0077;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
    L_0x003f:
        r1 = validateWeiboSign(r15, r9);	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        if (r1 == 0) goto L_0x0077;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
    L_0x0045:
        r13 = new com.sina.weibo.sdk.api.share.ApiUtils$WeiboInfo;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r13.<init>();	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r13.packageName = r9;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r13.supportApi = r12;	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        if (r6 == 0) goto L_0x0019;
    L_0x0050:
        r6.close();
        r6 = 0;
        goto L_0x0019;
    L_0x0055:
        r7 = move-exception;
        r7.printStackTrace();	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        goto L_0x0035;
    L_0x005a:
        r7 = move-exception;
        r7.printStackTrace();	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r1 = "ApiUtils";	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        r2 = r7.getMessage();	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        com.sina.weibo.sdk.utils.LogUtil.m2215e(r1, r2);	 Catch:{ Exception -> 0x005a, all -> 0x006f }
        if (r6 == 0) goto L_0x006d;
    L_0x0069:
        r6.close();
        r6 = 0;
    L_0x006d:
        r13 = r14;
        goto L_0x0019;
    L_0x006f:
        r1 = move-exception;
        if (r6 == 0) goto L_0x0076;
    L_0x0072:
        r6.close();
        r6 = 0;
    L_0x0076:
        throw r1;
    L_0x0077:
        if (r6 == 0) goto L_0x006d;
    L_0x0079:
        r6.close();
        r6 = 0;
        goto L_0x006d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.api.share.ApiUtils.queryWeiboInfoByProvider(android.content.Context):com.sina.weibo.sdk.api.share.ApiUtils$WeiboInfo");
    }

    public static boolean isWeiboAppSupportAPI(int supportApi) {
        return supportApi >= BUILD_INT;
    }

    public static WeiboInfo queryWeiboInfoByPackage(Context context, String packageName) {
        if (context == null || packageName == null) {
            return null;
        }
        WeiboInfo info = queryWeiboInfoByAsset(context, packageName);
        if (info != null) {
            return info;
        }
        info = queryWeiboInfoByProvider(context);
        if (info == null || !packageName.equals(info.packageName)) {
            return null;
        }
        return info;
    }

    public static WeiboInfo queryWeiboInfo(Context context) {
        WeiboInfo winfo = queryWeiboInfoByProvider(context);
        if (winfo == null) {
            return queryWeiboInfoByFile(context);
        }
        return winfo;
    }

    public static boolean validateWeiboSign(Context context, String pkgName) {
        try {
            return containSign(context.getPackageManager().getPackageInfo(pkgName, 64).signatures, WEIBO_SIGN);
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean containSign(Signature[] signatures, String destSign) {
        if (signatures == null || destSign == null) {
            return false;
        }
        for (Signature signature : signatures) {
            if (destSign.equals(MD5.hexdigest(signature.toByteArray()))) {
                LogUtil.m2214d(TAG, "check pass");
                return true;
            }
        }
        return false;
    }

    private static WeiboInfo queryWeiboInfoByFile(Context context) {
        Intent intent = new Intent(WEIBO_IDENTITY_ACTION);
        intent.addCategory("android.intent.category.DEFAULT");
        List<ResolveInfo> list = context.getPackageManager().queryIntentServices(intent, 0);
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (ResolveInfo ri : list) {
            if (!(ri.serviceInfo == null || ri.serviceInfo.applicationInfo == null || ri.serviceInfo.applicationInfo.packageName == null || ri.serviceInfo.applicationInfo.packageName.length() == 0)) {
                WeiboInfo winfo = queryWeiboInfoByAsset(context, ri.serviceInfo.applicationInfo.packageName);
                if (winfo != null) {
                    return winfo;
                }
            }
        }
        return null;
    }

    private static WeiboInfo queryWeiboInfoByAsset(Context context, String packageName) {
        if (context == null || packageName == null) {
            return null;
        }
        InputStream is = null;
        try {
            byte[] buf = new byte[1024];
            is = context.createPackageContext(packageName, 2).getAssets().open("weibo_for_sdk.json");
            StringBuilder sbContent = new StringBuilder();
            while (true) {
                int readNum = is.read(buf, 0, 1024);
                if (readNum == -1) {
                    break;
                }
                sbContent.append(new String(buf, 0, readNum));
            }
            if (TextUtils.isEmpty(sbContent.toString()) || !validateWeiboSign(context, packageName)) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                }
                return null;
            }
            int supportApi = new JSONObject(sbContent.toString()).optInt("support_api", -1);
            WeiboInfo winfo = new WeiboInfo();
            winfo.packageName = packageName;
            winfo.supportApi = supportApi;
            if (is == null) {
                return winfo;
            }
            try {
                is.close();
                return winfo;
            } catch (IOException e2) {
                return winfo;
            }
        } catch (NameNotFoundException e3) {
            e3.printStackTrace();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e4) {
                }
            }
        } catch (IOException e5) {
            e5.printStackTrace();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e6) {
                }
            }
        } catch (JSONException e7) {
            e7.printStackTrace();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e8) {
                }
            }
        } catch (NameNotFoundException e32) {
            LogUtil.m2215e(TAG, e32.getMessage());
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e9) {
                }
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e10) {
                }
            }
        }
        return null;
    }
}
