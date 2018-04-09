package com.droi.sdk.feedback;

import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;
import com.droi.sdk.feedback.p018a.C0950b;

final class C0954d implements Runnable {
    C0954d() {
    }

    public void run() {
        FragmentTransaction beginTransaction = DroiFeedbackActivity.f3096a.beginTransaction();
        beginTransaction.replace(C0950b.m2819a(DroiFeedbackActivity.f3099e).m2820a("droi_feedback_container"), new C0963k());
        beginTransaction.commit();
        DroiFeedbackActivity.f3097b.setText(C0950b.m2819a(DroiFeedbackActivity.f3099e).m2822c("droi_feedback_topbar_title_text"));
        DroiFeedbackActivity.f3098d.setVisibility(0);
        DroiFeedbackActivity.f3101h = true;
        InputMethodManager inputMethodManager = (InputMethodManager) DroiFeedbackActivity.f3099e.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(DroiFeedbackActivity.f3100f.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
