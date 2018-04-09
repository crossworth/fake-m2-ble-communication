package com.tencent.mm.sdk.p030a.p031a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.p032b.C1242b;
import com.tencent.mm.sdk.p032b.C1250h;

public final class C1237a {

    public static class C1236a {
        public String f3935Y;
        public Bundle f3936Z;
        public String aa;
        public String ab;
    }

    public static boolean m3657a(Context context, C1236a c1236a) {
        if (context == null) {
            C1242b.m3667b("MicroMsg.SDK.MMessage", "send fail, invalid argument");
            return false;
        } else if (C1250h.m3682h(c1236a.ab)) {
            C1242b.m3667b("MicroMsg.SDK.MMessage", "send fail, action is null");
            return false;
        } else {
            String str = null;
            if (!C1250h.m3682h(c1236a.aa)) {
                str = c1236a.aa + ".permission.MM_MESSAGE";
            }
            Intent intent = new Intent(c1236a.ab);
            if (c1236a.f3936Z != null) {
                intent.putExtras(c1236a.f3936Z);
            }
            String packageName = context.getPackageName();
            intent.putExtra(ConstantsAPI.SDK_VERSION, 587268097);
            intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
            intent.putExtra(ConstantsAPI.CONTENT, c1236a.f3935Y);
            intent.putExtra(ConstantsAPI.CHECK_SUM, C1238b.m3658a(c1236a.f3935Y, 587268097, packageName));
            context.sendBroadcast(intent, str);
            C1242b.m3670e("MicroMsg.SDK.MMessage", "send mm message, intent=" + intent + ", perm=" + str);
            return true;
        }
    }
}
