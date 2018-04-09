package com.droi.sdk.feedback;

import android.widget.Toast;

class C0971s implements Runnable {
    final /* synthetic */ String f3159a;
    final /* synthetic */ C0968o f3160b;

    C0971s(C0968o c0968o, String str) {
        this.f3160b = c0968o;
        this.f3159a = str;
    }

    public void run() {
        if (this.f3160b.f3154g == null) {
            this.f3160b.f3154g = Toast.makeText(this.f3160b.f3155h.getApplicationContext(), this.f3159a, 0);
        } else {
            this.f3160b.f3154g.setText(this.f3159a);
        }
        this.f3160b.f3154g.show();
    }
}
