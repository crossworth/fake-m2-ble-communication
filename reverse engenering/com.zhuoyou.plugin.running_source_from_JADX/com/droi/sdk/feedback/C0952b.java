package com.droi.sdk.feedback;

import android.view.View;
import android.view.View.OnClickListener;

class C0952b implements OnClickListener {
    final /* synthetic */ DroiFeedbackActivity f3116a;

    C0952b(DroiFeedbackActivity droiFeedbackActivity) {
        this.f3116a = droiFeedbackActivity;
    }

    public void onClick(View view) {
        if (DroiFeedbackActivity.f3101h) {
            this.f3116a.finish();
        } else {
            DroiFeedbackActivity.m2811b();
        }
    }
}
