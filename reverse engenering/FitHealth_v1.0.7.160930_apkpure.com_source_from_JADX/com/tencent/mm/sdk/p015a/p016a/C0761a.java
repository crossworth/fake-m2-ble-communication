package com.tencent.mm.sdk.p015a.p016a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.p017b.C0765a;
import com.tencent.mm.sdk.p017b.C0768e;

public final class C0761a {

    public static class C0760a {
        public String f2639m;
        public Bundle f2640n;
        public String f2641o;
        public String f2642p;
    }

    public static boolean m2506a(Context context, C0760a c0760a) {
        if (context == null || c0760a == null) {
            C0765a.m2514a("MicroMsg.SDK.MMessage", "send fail, invalid argument");
            return false;
        } else if (C0768e.m2523j(c0760a.f2642p)) {
            C0765a.m2514a("MicroMsg.SDK.MMessage", "send fail, action is null");
            return false;
        } else {
            String str = null;
            if (!C0768e.m2523j(c0760a.f2641o)) {
                str = c0760a.f2641o + ".permission.MM_MESSAGE";
            }
            Intent intent = new Intent(c0760a.f2642p);
            if (c0760a.f2640n != null) {
                intent.putExtras(c0760a.f2640n);
            }
            String packageName = context.getPackageName();
            intent.putExtra(ConstantsAPI.SDK_VERSION, 570490883);
            intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
            intent.putExtra(ConstantsAPI.CONTENT, c0760a.f2639m);
            intent.putExtra(ConstantsAPI.CHECK_SUM, C0762b.m2507a(c0760a.f2639m, 570490883, packageName));
            context.sendBroadcast(intent, str);
            C0765a.m2518d("MicroMsg.SDK.MMessage", "send mm message, intent=" + intent + ", perm=" + str);
            return true;
        }
    }
}
