package com.droi.sdk.feedback;

import android.app.ProgressDialog;
import com.droi.sdk.feedback.p018a.C0950b;

class C0970q implements DroiFeedbackSendListener {
    final /* synthetic */ ProgressDialog f3157a;
    final /* synthetic */ C0968o f3158b;

    C0970q(C0968o c0968o, ProgressDialog progressDialog) {
        this.f3158b = c0968o;
        this.f3157a = progressDialog;
    }

    public void onReturned(int i) {
        this.f3157a.cancel();
        switch (i) {
            case 0:
                this.f3158b.m2848a(this.f3158b.f3155h.getString(C0950b.m2819a(this.f3158b.f3155h).m2822c("droi_feedback_commit_failed")));
                return;
            case 1:
                this.f3158b.m2848a(this.f3158b.f3155h.getString(C0950b.m2819a(this.f3158b.f3155h).m2822c("droi_feedback_commit_content")));
                return;
            case 2:
                this.f3158b.m2848a(this.f3158b.f3155h.getString(C0950b.m2819a(this.f3158b.f3155h).m2822c("droi_feedback_commit_contact")));
                return;
            case 3:
                this.f3158b.m2848a(this.f3158b.f3155h.getString(C0950b.m2819a(this.f3158b.f3155h).m2822c("droi_feedback_commit_feedback")));
                return;
            case 4:
                this.f3158b.m2848a(this.f3158b.f3155h.getString(C0950b.m2819a(this.f3158b.f3155h).m2822c("droi_feedback_commit_contact_invalid")));
                return;
            case 6:
                DroiFeedbackActivity.m2811b();
                this.f3158b.m2848a(this.f3158b.f3155h.getString(C0950b.m2819a(this.f3158b.f3155h).m2822c("droi_feedback_commit_successful")));
                return;
            default:
                return;
        }
    }
}
