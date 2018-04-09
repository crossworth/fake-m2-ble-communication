package com.droi.sdk.feedback;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.feedback.p018a.C0949a;
import com.droi.sdk.internal.DroiLog;
import java.io.File;
import java.util.List;

public class C0958h {
    public static Context f3120a;

    public static void m2832a(Context context) {
        Log.i("DroiFeedbackImpl", "DroiFeedback initializing:1.0.004");
        if (context == null) {
            Log.e("DroiFeedbackImpl", "unexpected null context");
        } else {
            f3120a = context;
        }
    }

    protected static void m2836b(Context context) {
        Log.i("DroiFeedbackImpl", "openFeedbackActivity");
        Intent intent = new Intent(context, DroiFeedbackActivity.class);
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    protected static String m2831a() {
        String str = C0951a.f3113a;
        if (str == null) {
            return DroiUser.getCurrentUser().getObjectId();
        }
        return str;
    }

    protected static void m2834a(String str, String str2, DroiFeedbackSendListener droiFeedbackSendListener) {
        C0958h.m2835a(str, str2, null, droiFeedbackSendListener);
    }

    protected static void m2835a(String str, String str2, List<File> list, DroiFeedbackSendListener droiFeedbackSendListener) {
        if (!str2.equals("") && !str.equals("")) {
            DroiLog.m2871i("DroiFeedbackImpl", "isEmailVaild" + C0949a.m2815a(str));
            DroiLog.m2871i("DroiFeedbackImpl", "isMobileNO" + C0949a.m2816b(str));
            if (C0949a.m2815a(str) || C0949a.m2816b(str)) {
                new C0959i(list, droiFeedbackSendListener, str, str2).start();
            } else if (droiFeedbackSendListener != null) {
                droiFeedbackSendListener.onReturned(4);
            }
        } else if (!str2.equals("") || str.equals("")) {
            if (!str.equals("") || str2.equals("")) {
                if (droiFeedbackSendListener != null) {
                    droiFeedbackSendListener.onReturned(3);
                }
            } else if (droiFeedbackSendListener != null) {
                droiFeedbackSendListener.onReturned(2);
            }
        } else if (droiFeedbackSendListener != null) {
            droiFeedbackSendListener.onReturned(1);
        }
    }

    protected static void m2833a(DroiFeedbackReplyListener droiFeedbackReplyListener) {
        new C0960j(droiFeedbackReplyListener).start();
    }
}
