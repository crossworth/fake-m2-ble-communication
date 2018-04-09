package com.droi.sdk.feedback;

import android.content.Context;
import com.droi.sdk.feedback.p018a.C0949a;

public class DroiFeedback {
    public static void initialize(Context context) {
        C0958h.m2832a(context);
    }

    public static void setUserId(String str) {
        C0951a.f3113a = str;
    }

    public static void setTitleBarColor(int i) {
        C0951a.f3115c = i;
    }

    public static void setSendButtonColor(int i, int i2) {
        C0951a.f3114b = C0949a.m2813a(i, i2);
    }

    public static void submitFeedback(String str, String str2, DroiFeedbackSendListener droiFeedbackSendListener) {
        if (C0958h.f3120a != null) {
            C0958h.m2834a(str, str2, droiFeedbackSendListener);
        }
    }

    protected static void getReply(DroiFeedbackReplyListener droiFeedbackReplyListener) {
        if (C0958h.f3120a != null) {
            C0958h.m2833a(droiFeedbackReplyListener);
        }
    }

    public static void callFeedback(Context context) {
        if (C0958h.f3120a != null) {
            C0958h.m2836b(context);
        }
    }

    public static String getUserId() {
        return C0958h.m2831a();
    }
}
