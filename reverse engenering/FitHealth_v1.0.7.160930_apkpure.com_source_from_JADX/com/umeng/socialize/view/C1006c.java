package com.umeng.socialize.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.umeng.socialize.utils.SocializeUtils;

/* compiled from: OauthDialog */
class C1006c extends FrameLayout {
    final /* synthetic */ View f3474a;
    final /* synthetic */ View f3475b;
    final /* synthetic */ int f3476c;
    final /* synthetic */ OauthDialog f3477d;

    C1006c(OauthDialog oauthDialog, Context context, View view, View view2, int i) {
        this.f3477d = oauthDialog;
        this.f3474a = view;
        this.f3475b = view2;
        this.f3476c = i;
        super(context);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (!SocializeUtils.isFloatWindowStyle(this.f3477d.f3374j)) {
            m3371a(this.f3474a, this.f3475b, this.f3476c, i2);
        }
    }

    private void m3371a(View view, View view2, int i, int i2) {
        if (view2.getVisibility() == 0 && i2 < i) {
            this.f3477d.f3379o.post(new C1007d(this, view2, view));
        } else if (view2.getVisibility() != 0 && i2 >= i) {
            this.f3477d.f3379o.post(new C1008e(this, view2, view));
        }
    }
}
