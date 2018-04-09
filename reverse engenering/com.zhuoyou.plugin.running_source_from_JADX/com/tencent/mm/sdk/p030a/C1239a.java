package com.tencent.mm.sdk.p030a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.p030a.p031a.C1238b;
import com.tencent.mm.sdk.p032b.C1242b;
import com.tencent.mm.sdk.p032b.C1250h;

public final class C1239a {

    public static class C1235a {
        public String f3931W;
        public String f3932X;
        public String f3933Y;
        public Bundle f3934Z;
        public int flags = -1;

        public final String toString() {
            return "targetPkgName:" + this.f3931W + ", targetClassName:" + this.f3932X + ", content:" + this.f3933Y + ", flags:" + this.flags + ", bundle:" + this.f3934Z;
        }
    }

    public static boolean m3659a(Context context, C1235a c1235a) {
        if (context == null) {
            C1242b.m3667b("MicroMsg.SDK.MMessageAct", "send fail, invalid argument");
            return false;
        } else if (C1250h.m3682h(c1235a.f3931W)) {
            C1242b.m3667b("MicroMsg.SDK.MMessageAct", "send fail, invalid targetPkgName, targetPkgName = " + c1235a.f3931W);
            return false;
        } else {
            if (C1250h.m3682h(c1235a.f3932X)) {
                c1235a.f3932X = c1235a.f3931W + ".wxapi.WXEntryActivity";
            }
            C1242b.m3670e("MicroMsg.SDK.MMessageAct", "send, targetPkgName = " + c1235a.f3931W + ", targetClassName = " + c1235a.f3932X);
            Intent intent = new Intent();
            intent.setClassName(c1235a.f3931W, c1235a.f3932X);
            if (c1235a.f3934Z != null) {
                intent.putExtras(c1235a.f3934Z);
            }
            String packageName = context.getPackageName();
            intent.putExtra(ConstantsAPI.SDK_VERSION, 587268097);
            intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
            intent.putExtra(ConstantsAPI.CONTENT, c1235a.f3933Y);
            intent.putExtra(ConstantsAPI.CHECK_SUM, C1238b.m3658a(c1235a.f3933Y, 587268097, packageName));
            if (c1235a.flags == -1) {
                intent.addFlags(268435456).addFlags(134217728);
            } else {
                intent.setFlags(c1235a.flags);
            }
            try {
                context.startActivity(intent);
                C1242b.m3670e("MicroMsg.SDK.MMessageAct", "send mm message, intent=" + intent);
                return true;
            } catch (Exception e) {
                C1242b.m3666a("MicroMsg.SDK.MMessageAct", "send fail, ex = %s", e.getMessage());
                return false;
            }
        }
    }
}
