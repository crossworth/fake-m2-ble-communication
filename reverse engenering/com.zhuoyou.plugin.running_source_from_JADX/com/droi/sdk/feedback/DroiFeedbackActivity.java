package com.droi.sdk.feedback;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.droi.sdk.feedback.p018a.C0950b;

public class DroiFeedbackActivity extends FragmentActivity {
    static FragmentManager f3096a;
    static TextView f3097b;
    static ImageView f3098d;
    static Context f3099e;
    static Activity f3100f;
    static boolean f3101h = true;
    ImageView f3102c;
    View f3103g;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        f3099e = this;
        f3100f = this;
        setContentView(C0950b.m2819a((Context) this).m2821b("droi_feedback_activity_layout"));
        f3096a = getSupportFragmentManager();
        this.f3103g = findViewById(C0950b.m2819a((Context) this).m2820a("droi_feedback_title_bar"));
        if (C0951a.f3115c != 0) {
            this.f3103g.setBackgroundColor(C0951a.f3115c);
        }
        f3097b = (TextView) findViewById(C0950b.m2819a((Context) this).m2820a("droi_feedback_topbar_title"));
        this.f3102c = (ImageView) findViewById(C0950b.m2819a((Context) this).m2820a("droi_feedback_back_btn"));
        this.f3102c.setOnClickListener(new C0952b(this));
        f3098d = (ImageView) findViewById(C0950b.m2819a((Context) this).m2820a("droi_feedback_submit_btn"));
        f3098d.setOnClickListener(new C0953c(this));
        m2811b();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        if (f3101h) {
            finish();
        } else {
            m2811b();
        }
        return true;
    }

    static void m2810a() {
        FragmentTransaction beginTransaction = f3096a.beginTransaction();
        beginTransaction.replace(C0950b.m2819a(f3099e).m2820a("droi_feedback_container"), new C0968o());
        beginTransaction.commit();
        f3097b.setText(C0950b.m2819a(f3099e).m2822c("droi_feedback_topbar_submit_text"));
        f3098d.setVisibility(8);
        f3101h = false;
    }

    static void m2811b() {
        new Handler(Looper.getMainLooper()).post(new C0954d());
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }
}
