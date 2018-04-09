package com.umeng.socialize.p024a;

import android.content.Context;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.base.SocializeClient;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.Log;

/* compiled from: SocialAnalytics */
public class C0944c {
    private static SocializeClient f3234a = new SocializeClient();

    public static void m3174a(Context context, String str, String str2, UMediaObject uMediaObject) {
        Log.m3256v("xxxxxx log=" + str2);
        SocializeRequest c2010a = new C2010a(context, str, str2);
        c2010a.m6104c(SocializeProtocolConstants.PROTOCOL_NORMAL_SHARE);
        c2010a.m6101a(uMediaObject);
        C2011b c2011b = (C2011b) f3234a.execute(c2010a);
        if (c2011b == null || !c2011b.isOk()) {
            Log.m3247d("xxxxxx fail to send log");
        } else {
            Log.m3247d("xxxxxx send log succeed");
        }
    }

    public static void m3175b(Context context, String str, String str2, UMediaObject uMediaObject) {
        SocializeRequest c2010a = new C2010a(context, str, str2);
        c2010a.m6104c("shake");
        c2010a.m6101a(uMediaObject);
        C2011b c2011b = (C2011b) f3234a.execute(c2010a);
        if (c2011b == null || !c2011b.isOk()) {
            Log.m3247d("fail to send log");
        } else {
            Log.m3247d("send log succeed");
        }
    }
}
