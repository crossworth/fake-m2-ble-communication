package com.droi.sdk.selfupdate;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

class C1029f implements OnCheckedChangeListener {
    final /* synthetic */ DroiUpdateDialogActivity f3417a;

    C1029f(DroiUpdateDialogActivity droiUpdateDialogActivity) {
        this.f3417a = droiUpdateDialogActivity;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.f3417a.f3392j = z;
    }
}
