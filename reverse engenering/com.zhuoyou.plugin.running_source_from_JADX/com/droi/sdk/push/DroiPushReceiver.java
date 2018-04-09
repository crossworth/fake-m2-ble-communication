package com.droi.sdk.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.droi.sdk.push.utils.C1015j;
import com.tencent.open.GameAppOperation;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class DroiPushReceiver extends BroadcastReceiver {
    private void m2882a(Intent intent, String str) {
        if (intent != null && !TextUtils.isEmpty(str)) {
            JSONObject jSONObject;
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
                jSONObject = null;
            }
            if (jSONObject != null) {
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String obj = keys.next().toString();
                    try {
                        intent.putExtra(obj, jSONObject.getString(obj));
                    } catch (JSONException e2) {
                    }
                }
            }
        }
    }

    public void m2883a(Context context, long j, int i, String str, String str2, String str3) {
        Intent intent = new Intent(context, DroiPushActivity.class);
        intent.putExtra("msg_id", j);
        intent.putExtra("request_id", i);
        intent.putExtra(GameAppOperation.QQFAV_DATALINE_IMAGEURL, str3);
        intent.putExtra("download_url", str2);
        intent.putExtra("pkg_name", str);
        intent.setAction("com.droi.sdk.push.action.SHOW_DETAIL");
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public void m2884a(Context context, String str) {
        int i = 0;
        if (!TextUtils.isEmpty(str)) {
            if (!(str.startsWith("http://") || str.startsWith("https://"))) {
                str = "http://" + str;
            }
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            String[] strArr = new String[]{"com.tencent.mtt", "com.UCMobile", "com.uc.browser", "com.qihoo.browser", "com.baidu.browser.apps", "com.oupeng.browser", "com.oupeng.mini.android", "com.android.browser"};
            int length = strArr.length;
            while (i < length) {
                String str2 = strArr[i];
                if (C1015j.m3165c(context, str2) && context.getApplicationContext().getPackageManager().getLaunchIntentForPackage(str2) != null) {
                    intent.setPackage(str2);
                    break;
                }
                i++;
            }
            intent.addFlags(268435456);
            context.startActivity(intent);
        }
    }

    public void m2885a(Context context, String str, String str2) {
        Intent intent = new Intent();
        if (C1015j.m3168d(str)) {
            if (C1015j.m3168d(str2)) {
                m2882a(intent, str2);
            }
            intent.setClassName(context, str);
            intent.addFlags(268435456);
            context.startActivity(intent);
            return;
        }
        intent = context.getPackageManager().getLaunchIntentForPackage(context.getApplicationContext().getPackageName());
        if (C1015j.m3168d(str2)) {
            m2882a(intent, str2);
        }
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r14, android.content.Intent r15) {
        /*
        r13 = this;
        r12 = 3;
        r11 = 2;
        r6 = 0;
        r9 = 1;
        r4 = -1;
        r0 = r15.getAction();
        r1 = "android.intent.action.PACKAGE_ADDED";
        r1 = r1.equals(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r1 != 0) goto L_0x001a;
    L_0x0012:
        r1 = "android.intent.action.PACKAGE_REPLACED";
        r1 = r1.equals(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r1 == 0) goto L_0x003b;
    L_0x001a:
        r0 = r15.getData();	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0 = r0.getSchemeSpecificPart();	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = com.droi.sdk.push.p020b.C0979a.m3009a(r14);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r2 = r1.m3020a(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r0 <= 0) goto L_0x003a;
    L_0x002e:
        r4 = "m01";
        r5 = 10;
        r6 = 1;
        r7 = -1;
        r8 = "DROIPUSH";
        r1 = r14;
        com.droi.sdk.push.ag.m3007a(r1, r2, r4, r5, r6, r7, r8);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
    L_0x003a:
        return;
    L_0x003b:
        r1 = "com.droi.sdk.push.action.DATA";
        r1 = r1.equals(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r1 == 0) goto L_0x004d;
    L_0x0043:
        r0 = com.droi.sdk.push.C1005u.m3108a(r14);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0.m3122a(r15, r14);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        goto L_0x003a;
    L_0x004b:
        r0 = move-exception;
        goto L_0x003a;
    L_0x004d:
        r1 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1.<init>();	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r2 = r14.getPackageName();	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = r1.append(r2);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r2 = ".Action.START";
        r1 = r1.append(r2);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = r1.toString();	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = r1.equals(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r1 == 0) goto L_0x014f;
    L_0x006a:
        r0 = "type";
        r1 = -1;
        r0 = r15.getIntExtra(r0, r1);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = "id";
        r2 = -1;
        r10 = r15.getIntExtra(r1, r2);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = "msgId";
        r2 = -1;
        r2 = r15.getLongExtra(r1, r2);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r0 == r4) goto L_0x003a;
    L_0x0082:
        r1 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r1 <= 0) goto L_0x0091;
    L_0x0086:
        r4 = "m01";
        r5 = 2;
        r6 = 1;
        r7 = -1;
        r8 = "DROIPUSH";
        r1 = r14;
        com.droi.sdk.push.ag.m3007a(r1, r2, r4, r5, r6, r7, r8);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
    L_0x0091:
        if (r0 != r9) goto L_0x00ac;
    L_0x0093:
        r0 = com.droi.sdk.push.C1005u.m3108a(r14);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0.m3121a(r2);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0 = "activity";
        r0 = r15.getStringExtra(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = "data";
        r1 = r15.getStringExtra(r1);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r13.m2885a(r14, r0, r1);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        goto L_0x003a;
    L_0x00aa:
        r0 = move-exception;
        goto L_0x003a;
    L_0x00ac:
        if (r0 != r11) goto L_0x00c0;
    L_0x00ae:
        r0 = com.droi.sdk.push.C1005u.m3108a(r14);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0.m3121a(r2);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0 = "url";
        r0 = r15.getStringExtra(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r13.m2884a(r14, r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        goto L_0x003a;
    L_0x00c0:
        if (r0 != r12) goto L_0x011d;
    L_0x00c2:
        r0 = com.droi.sdk.push.C1005u.m3108a(r14);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0.m3121a(r2);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0 = "dinfo";
        r0 = r15.getBundleExtra(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = "durl";
        r6 = r0.getString(r1);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = "package";
        r5 = r0.getString(r1);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = "";
        r1 = "iurl";
        r7 = r0.getString(r1);	 Catch:{ Exception -> 0x010e, RuntimeException -> 0x004b }
    L_0x00e3:
        r0 = com.droi.sdk.push.utils.C1015j.m3168d(r6);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r0 == 0) goto L_0x003a;
    L_0x00e9:
        r0 = com.droi.sdk.push.utils.C1015j.m3168d(r5);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r0 == 0) goto L_0x003a;
    L_0x00ef:
        r0 = com.droi.sdk.push.utils.C1015j.m3168d(r7);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r0 == 0) goto L_0x0111;
    L_0x00f5:
        r0 = new com.droi.sdk.DroiError;	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0.<init>();	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = com.droi.sdk.utility.Utility.isBitmapCached(r7, r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0 = r0.isOk();	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r0 == 0) goto L_0x0111;
    L_0x0104:
        if (r1 == 0) goto L_0x0111;
    L_0x0106:
        r0 = r13;
        r1 = r14;
        r4 = r10;
        r0.m2883a(r1, r2, r4, r5, r6, r7);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        goto L_0x003a;
    L_0x010e:
        r0 = move-exception;
        r7 = 0;
        goto L_0x00e3;
    L_0x0111:
        r7 = com.droi.sdk.push.p020b.C0979a.m3009a(r14);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r8 = r2;
        r11 = r6;
        r12 = r5;
        r7.m3019a(r8, r10, r11, r12);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        goto L_0x003a;
    L_0x011d:
        r1 = 4;
        if (r0 != r1) goto L_0x003a;
    L_0x0120:
        r0 = com.droi.sdk.push.C1005u.m3108a(r14);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0.m3121a(r2);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0 = "dinfo";
        r0 = r15.getBundleExtra(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = "durl";
        r5 = r0.getString(r1);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = "package";
        r6 = r0.getString(r1);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0 = com.droi.sdk.push.utils.C1015j.m3168d(r5);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r0 == 0) goto L_0x003a;
    L_0x013f:
        r0 = com.droi.sdk.push.utils.C1015j.m3168d(r6);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r0 == 0) goto L_0x003a;
    L_0x0145:
        r1 = com.droi.sdk.push.p020b.C0979a.m3009a(r14);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r4 = r10;
        r1.m3019a(r2, r4, r5, r6);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        goto L_0x003a;
    L_0x014f:
        r1 = "android.intent.action.USER_PRESENT";
        r1 = r1.equals(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r1 != 0) goto L_0x016f;
    L_0x0157:
        r1 = "android.net.conn.CONNECTIVITY_CHANGE";
        r1 = r1.equals(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r1 != 0) goto L_0x016f;
    L_0x015f:
        r1 = "android.intent.action.PHONE_STATE";
        r1 = r1.equals(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r1 != 0) goto L_0x016f;
    L_0x0167:
        r1 = "android.intent.action.BOOT_COMPLETED";
        r1 = r1.equals(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r1 == 0) goto L_0x0195;
    L_0x016f:
        com.droi.sdk.push.DroiPush.m2876a(r14);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0 = new android.content.Intent;	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = "com.droi.sdk.push.action.START";
        r0.<init>(r1);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r1 = r15.getAction();	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r2 = "android.net.conn.CONNECTIVITY_CHANGE";
        r1 = r1.equals(r2);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r1 == 0) goto L_0x018c;
    L_0x0185:
        r1 = "CMD";
        r2 = "NETWORK_CHANGE";
        r0.putExtra(r1, r2);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
    L_0x018c:
        r0 = com.droi.sdk.push.aa.m2958a(r14);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0.m2988k();	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        goto L_0x003a;
    L_0x0195:
        r1 = "com.droi.sdk.push.action.REMOVE_NOTIFICATION";
        r0 = r1.equals(r0);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        if (r0 == 0) goto L_0x003a;
    L_0x019d:
        r0 = "msgId";
        r2 = -1;
        r2 = r15.getLongExtra(r0, r2);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        r0 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r0 <= 0) goto L_0x003a;
    L_0x01a9:
        r4 = "m01";
        r5 = 3;
        r6 = 1;
        r7 = -1;
        r8 = "DROIPUSH";
        r1 = r14;
        com.droi.sdk.push.ag.m3007a(r1, r2, r4, r5, r6, r7, r8);	 Catch:{ RuntimeException -> 0x004b, Exception -> 0x00aa }
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.push.DroiPushReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }
}
