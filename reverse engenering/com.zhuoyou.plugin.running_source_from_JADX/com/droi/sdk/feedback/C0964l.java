package com.droi.sdk.feedback;

import android.os.Handler;
import android.os.Message;
import com.droi.sdk.feedback.p018a.C0950b;
import java.util.List;

class C0964l extends Handler {
    final /* synthetic */ C0963k f3142a;

    C0964l(C0963k c0963k) {
        this.f3142a = c0963k;
    }

    public void handleMessage(Message message) {
        if (this.f3142a.isAdded()) {
            this.f3142a.f3141f.setRefreshing(false);
            switch (message.what) {
                case 0:
                    this.f3142a.f3140e.setVisibility(0);
                    this.f3142a.f3140e.setText(this.f3142a.getString(C0950b.m2819a(this.f3142a.f3139d).m2822c("droi_feedback_error_text")));
                    this.f3142a.f3137b.setVisibility(8);
                    return;
                case 1:
                    this.f3142a.f3140e.setVisibility(8);
                    this.f3142a.f3137b.setVisibility(0);
                    this.f3142a.m2843a((List) message.obj);
                    return;
                case 2:
                    this.f3142a.f3140e.setVisibility(0);
                    this.f3142a.f3140e.setText(this.f3142a.getString(C0950b.m2819a(this.f3142a.f3139d).m2822c("droi_feedback_no_feedback_text")));
                    this.f3142a.f3137b.setVisibility(8);
                    return;
                default:
                    return;
            }
        }
    }
}
