package com.tencent.mm.sdk.p015a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.p015a.p016a.C0762b;
import com.tencent.mm.sdk.p017b.C0765a;
import com.tencent.mm.sdk.p017b.C0768e;

public final class C0763a {

    public static class C0759a {
        public int flags = -1;
        public String f2635k;
        public String f2636l;
        public String f2637m;
        public Bundle f2638n;
    }

    public static boolean m2508a(Context context, C0759a c0759a) {
        if (context == null || c0759a == null) {
            C0765a.m2514a("MicroMsg.SDK.MMessageAct", "send fail, invalid argument");
            return false;
        } else if (C0768e.m2523j(c0759a.f2635k)) {
            C0765a.m2514a("MicroMsg.SDK.MMessageAct", "send fail, invalid targetPkgName, targetPkgName = " + c0759a.f2635k);
            return false;
        } else {
            if (C0768e.m2523j(c0759a.f2636l)) {
                c0759a.f2636l = c0759a.f2635k + ".wxapi.WXEntryActivity";
            }
            C0765a.m2518d("MicroMsg.SDK.MMessageAct", "send, targetPkgName = " + c0759a.f2635k + ", targetClassName = " + c0759a.f2636l);
            Intent intent = new Intent();
            intent.setClassName(c0759a.f2635k, c0759a.f2636l);
            if (c0759a.f2638n != null) {
                intent.putExtras(c0759a.f2638n);
            }
            String packageName = context.getPackageName();
            intent.putExtra(ConstantsAPI.SDK_VERSION, 570490883);
            intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
            intent.putExtra(ConstantsAPI.CONTENT, c0759a.f2637m);
            intent.putExtra(ConstantsAPI.CHECK_SUM, C0762b.m2507a(c0759a.f2637m, 570490883, packageName));
            if (c0759a.flags == -1) {
                intent.addFlags(268435456).addFlags(134217728);
            } else {
                intent.setFlags(c0759a.flags);
            }
            try {
                context.startActivity(intent);
                C0765a.m2518d("MicroMsg.SDK.MMessageAct", "send mm message, intent=" + intent);
                return true;
            } catch (Exception e) {
                C0765a.m2515a("MicroMsg.SDK.MMessageAct", "send fail, ex = %s", e.getMessage());
                return false;
            }
        }
    }
}
