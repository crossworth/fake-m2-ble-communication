package com.droi.sdk.push;

import android.view.View;
import android.widget.TextView;

class C1021x implements C1020y {
    final /* synthetic */ Integer[] f3366a;
    final /* synthetic */ C1005u f3367b;

    C1021x(C1005u c1005u, Integer[] numArr) {
        this.f3367b = c1005u;
        this.f3366a = numArr;
    }

    public void mo1935a(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Object obj = null;
            CharSequence text = textView.getText();
            if (text != null) {
                obj = text.toString();
            }
            if (this.f3367b.f3348i.equals(obj)) {
                this.f3366a[0] = Integer.valueOf(textView.getCurrentTextColor());
            } else if (this.f3367b.f3349j.equals(obj)) {
                this.f3366a[1] = Integer.valueOf(textView.getCurrentTextColor());
            }
        }
    }
}
