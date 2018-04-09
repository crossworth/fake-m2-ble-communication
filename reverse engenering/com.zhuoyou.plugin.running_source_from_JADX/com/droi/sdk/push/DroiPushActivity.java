package com.droi.sdk.push;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.droi.sdk.DroiError;
import com.droi.sdk.push.p020b.C0979a;
import com.droi.sdk.push.utils.C1006a;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.droi.sdk.utility.Utility;
import com.tencent.open.GameAppOperation;

public class DroiPushActivity extends Activity {
    private C1006a f3181a;
    private ImageView f3182b;
    private long f3183c = -1;
    private Bitmap f3184d = null;

    private boolean m2881a(Activity activity, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int scaledWindowTouchSlop = ViewConfiguration.get(activity).getScaledWindowTouchSlop();
        View decorView = activity.getWindow().getDecorView();
        return x < (-scaledWindowTouchSlop) || y < (-scaledWindowTouchSlop) || x > decorView.getWidth() + scaledWindowTouchSlop || y > decorView.getHeight() + scaledWindowTouchSlop;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.f3183c = intent.getLongExtra("msg_id", -1);
        int intExtra = intent.getIntExtra("request_id", -1);
        String stringExtra = intent.getStringExtra(GameAppOperation.QQFAV_DATALINE_IMAGEURL);
        String stringExtra2 = intent.getStringExtra("pkg_name");
        String stringExtra3 = intent.getStringExtra("download_url");
        if (C1015j.m3168d(stringExtra2) && C1015j.m3168d(stringExtra3)) {
            if (C1015j.m3168d(stringExtra)) {
                C1012g.m3141c("DroiPushActivity: main image path - " + stringExtra);
                DroiError droiError = new DroiError();
                boolean isBitmapCached = Utility.isBitmapCached(stringExtra, droiError);
                C1012g.m3141c("DroiPushActivity: check bitmap isok(" + droiError.isOk() + ") - isCached(" + isBitmapCached + ") ");
                if (droiError.isOk() && isBitmapCached) {
                    DroiError droiError2 = new DroiError();
                    Bitmap bitmap = Utility.getBitmap(stringExtra, 0, 0, droiError2);
                    C1012g.m3141c("DroiPushActivity: getBitmap isok(" + droiError2.isOk() + ") - isNull(" + (bitmap == null) + ") ");
                    if (droiError2.isOk() && bitmap != null) {
                        View frameLayout = new FrameLayout(getApplicationContext());
                        this.f3181a = new C1006a(this, bitmap);
                        this.f3181a.setDownloadListener(new C0984c(this, intExtra, stringExtra3, stringExtra2));
                        LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
                        layoutParams.gravity = 17;
                        this.f3181a.setLayoutParams(layoutParams);
                        frameLayout.addView(this.f3181a);
                        this.f3182b = new ImageView(this);
                        this.f3184d = C1015j.m3166d(getApplicationContext(), "dp_close_dialog_btn.png");
                        if (this.f3184d != null) {
                            this.f3182b.setImageBitmap(this.f3184d);
                        }
                        layoutParams = new FrameLayout.LayoutParams(-2, -2);
                        layoutParams.gravity = 53;
                        layoutParams.setMargins(0, 10, 10, 0);
                        this.f3182b.setLayoutParams(layoutParams);
                        frameLayout.addView(this.f3182b);
                        this.f3182b.setOnClickListener(new C0985d(this));
                        if (this.f3183c > 0) {
                            ag.m3007a(this, this.f3183c, "m01", 4, 1, -1, "DROIPUSH");
                        }
                        setContentView(frameLayout);
                        return;
                    }
                }
            }
            C0979a.m3009a((Context) this).m3019a(this.f3183c, intExtra, stringExtra3, stringExtra2);
            finish();
            return;
        }
        C1012g.m3140b("DroiPushActivity: package name or download url is invalid!");
        finish();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.f3184d != null && !this.f3184d.isRecycled()) {
            this.f3184d.recycle();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return (motionEvent.getAction() == 0 && m2881a(this, motionEvent)) ? true : super.onTouchEvent(motionEvent);
    }
}
