package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.share.internal.ShareConstants;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.p010a.C0687a;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.HttpUtils.HttpStatusException;
import com.tencent.utils.HttpUtils.NetworkUnavailableException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class TaskGuide extends BaseApi {
    private static int f4638K = 3000;
    static long f4639b = 5000;
    private static Drawable f4640k;
    private static Drawable f4641l;
    private static Drawable f4642m;
    private static int f4643n = 75;
    private static int f4644o = 284;
    private static int f4645p = 75;
    private static int f4646q = 30;
    private static int f4647r = 29;
    private static int f4648s = 5;
    private static int f4649t = 74;
    private static int f4650u = 0;
    private static int f4651v = 6;
    private static int f4652w = 153;
    private static int f4653x = 30;
    private static int f4654y = 6;
    private static int f4655z = 3;
    private int f4656A = 0;
    private int f4657B = 0;
    private float f4658C = 0.0f;
    private Interpolator f4659D = new AccelerateInterpolator();
    private boolean f4660E = false;
    private boolean f4661F = false;
    private boolean f4662G = false;
    private long f4663H;
    private int f4664I;
    private int f4665J;
    private Runnable f4666L = null;
    private Runnable f4667M = null;
    boolean f4668a = false;
    IUiListener f4669c;
    private LayoutParams f4670d = null;
    private ViewGroup f4671e = null;
    private WindowManager f4672f;
    private Handler f4673g = new Handler(Looper.getMainLooper());
    private C0792i f4674h;
    private C0788d f4675i = C0788d.INIT;
    private C0788d f4676j = C0788d.INIT;

    /* compiled from: ProGuard */
    class C07833 implements Runnable {
        final /* synthetic */ TaskGuide f2673a;

        C07833(TaskGuide taskGuide) {
            this.f2673a = taskGuide;
        }

        public void run() {
            this.f2673a.f4671e = this.f2673a.m4799b(this.f2673a.mContext);
            this.f2673a.f4670d = this.f2673a.m4787a(this.f2673a.mContext);
            this.f2673a.m4810d();
            WindowManager windowManager = (WindowManager) this.f2673a.mContext.getSystemService("window");
            if (!((Activity) this.f2673a.mContext).isFinishing()) {
                if (!this.f2673a.f4660E) {
                    windowManager.addView(this.f2673a.f4671e, this.f2673a.f4670d);
                }
                this.f2673a.f4660E = true;
                this.f2673a.m4802b(2);
                this.f2673a.m4827k();
            }
        }
    }

    /* compiled from: ProGuard */
    class C0786b implements Runnable {
        boolean f2678a = false;
        float f2679b = 0.0f;
        final /* synthetic */ TaskGuide f2680c;

        public C0786b(TaskGuide taskGuide, boolean z) {
            this.f2680c = taskGuide;
            this.f2678a = z;
        }

        public void run() {
            Object obj = 1;
            SystemClock.currentThreadTimeMillis();
            this.f2679b = (float) (((double) this.f2679b) + 0.1d);
            float f = this.f2679b;
            if (f > 1.0f) {
                f = 1.0f;
            }
            Object obj2 = f >= 1.0f ? 1 : null;
            int interpolation = (int) (this.f2680c.f4659D.getInterpolation(f) * ((float) this.f2680c.f4664I));
            if (this.f2678a) {
                this.f2680c.f4670d.y = this.f2680c.f4665J + interpolation;
            } else {
                this.f2680c.f4670d.y = this.f2680c.f4665J - interpolation;
            }
            Log.d("TAG", "mWinParams.y = " + this.f2680c.f4670d.y + "deltaDistence = " + interpolation);
            if (this.f2680c.f4660E) {
                this.f2680c.f4672f.updateViewLayout(this.f2680c.f4671e, this.f2680c.f4670d);
                obj = obj2;
            }
            if (obj != null) {
                this.f2680c.m4823i();
            } else {
                this.f2680c.f4673g.postDelayed(this.f2680c.f4666L, 5);
            }
        }
    }

    /* compiled from: ProGuard */
    private enum C0788d {
        INIT,
        WAITTING_BACK_TASKINFO,
        WAITTING_BACK_REWARD,
        NORAML,
        REWARD_SUCCESS,
        REWARD_FAIL;

        public static C0788d[] m2525a() {
            return (C0788d[]) f2688g.clone();
        }
    }

    /* compiled from: ProGuard */
    private static class C0789e {
        int f2689a;
        String f2690b;
        String f2691c;
        long f2692d;
        int f2693e;

        public C0789e(int i, String str, String str2, long j, int i2) {
            this.f2689a = i;
            this.f2690b = str;
            this.f2691c = str2;
            this.f2692d = j;
            this.f2693e = i2;
        }
    }

    /* compiled from: ProGuard */
    class C0790f implements OnClickListener {
        int f2694a;
        final /* synthetic */ TaskGuide f2695b;

        public C0790f(TaskGuide taskGuide, int i) {
            this.f2695b = taskGuide;
            this.f2694a = i;
        }

        public void onClick(View view) {
            Button button = (Button) view;
            if (this.f2695b.m4804c(this.f2694a) == C0788d.NORAML) {
                this.f2695b.m4813e(this.f2694a);
                this.f2695b.m4802b(this.f2694a);
            }
            this.f2695b.m4820h();
        }
    }

    /* compiled from: ProGuard */
    private class C0791h extends LinearLayout {
        final /* synthetic */ TaskGuide f2696a;
        private TextView f2697b;
        private Button f2698c;
        private C0789e f2699d;

        public C0791h(TaskGuide taskGuide, Context context, C0789e c0789e) {
            this.f2696a = taskGuide;
            super(context);
            this.f2699d = c0789e;
            setOrientation(0);
            m2526a();
        }

        private void m2526a() {
            this.f2697b = new TextView(this.f2696a.mContext);
            this.f2697b.setTextColor(Color.rgb(255, 255, 255));
            this.f2697b.setTextSize(15.0f);
            this.f2697b.setShadowLayer(1.0f, 1.0f, 1.0f, Color.rgb(242, 211, 199));
            this.f2697b.setGravity(3);
            this.f2697b.setEllipsize(TruncateAt.END);
            this.f2697b.setIncludeFontPadding(false);
            this.f2697b.setSingleLine(true);
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
            layoutParams.weight = 1.0f;
            layoutParams.leftMargin = this.f2696a.m4783a(4);
            addView(this.f2697b, layoutParams);
            this.f2698c = new Button(this.f2696a.mContext);
            this.f2698c.setPadding(0, 0, 0, 0);
            this.f2698c.setTextSize(16.0f);
            this.f2698c.setTextColor(Color.rgb(255, 255, 255));
            this.f2698c.setShadowLayer(1.0f, 1.0f, 1.0f, Color.rgb(242, 211, 199));
            this.f2698c.setIncludeFontPadding(false);
            this.f2698c.setOnClickListener(new C0790f(this.f2696a, this.f2699d.f2689a));
            layoutParams = new LinearLayout.LayoutParams(this.f2696a.m4783a(TaskGuide.f4645p), this.f2696a.m4783a(TaskGuide.f4646q));
            layoutParams.leftMargin = this.f2696a.m4783a(2);
            layoutParams.rightMargin = this.f2696a.m4783a(8);
            addView(this.f2698c, layoutParams);
        }

        public void m2527a(C0788d c0788d) {
            if (!TextUtils.isEmpty(this.f2699d.f2690b)) {
                this.f2697b.setText(this.f2699d.f2690b);
            }
            switch (c0788d) {
                case INIT:
                    this.f2698c.setEnabled(false);
                    return;
                case NORAML:
                    if (this.f2699d.f2693e == 1) {
                        this.f2698c.setText(this.f2699d.f2691c);
                        this.f2698c.setBackgroundDrawable(null);
                        this.f2698c.setTextColor(Color.rgb(255, 246, 0));
                        this.f2698c.setEnabled(false);
                        return;
                    } else if (this.f2699d.f2693e == 2) {
                        this.f2698c.setText("领取奖励");
                        this.f2698c.setTextColor(Color.rgb(255, 255, 255));
                        this.f2698c.setBackgroundDrawable(this.f2696a.m4817f());
                        this.f2698c.setEnabled(true);
                        return;
                    } else {
                        return;
                    }
                case WAITTING_BACK_REWARD:
                    this.f2698c.setText("领取中...");
                    this.f2698c.setEnabled(false);
                    return;
                case REWARD_SUCCESS:
                    this.f2698c.setText("已领取");
                    this.f2698c.setBackgroundDrawable(this.f2696a.m4819g());
                    this.f2698c.setEnabled(false);
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: ProGuard */
    private static class C0792i {
        String f2700a;
        String f2701b;
        C0789e[] f2702c;

        private C0792i() {
        }

        public boolean m2529a() {
            if (TextUtils.isEmpty(this.f2700a) || this.f2702c == null || this.f2702c.length <= 0) {
                return false;
            }
            return true;
        }

        static C0792i m2528a(JSONObject jSONObject) throws JSONException {
            if (jSONObject == null) {
                return null;
            }
            C0792i c0792i = new C0792i();
            JSONObject jSONObject2 = jSONObject.getJSONObject("task_info");
            c0792i.f2700a = jSONObject2.getString("task_id");
            c0792i.f2701b = jSONObject2.getString("task_desc");
            JSONArray jSONArray = jSONObject2.getJSONArray("step_info");
            int length = jSONArray.length();
            if (length > 0) {
                c0792i.f2702c = new C0789e[length];
            }
            for (int i = 0; i < length; i++) {
                jSONObject2 = jSONArray.getJSONObject(i);
                c0792i.f2702c[i] = new C0789e(jSONObject2.getInt("step_no"), jSONObject2.getString("step_desc"), jSONObject2.getString("step_gift"), jSONObject2.getLong("end_time"), jSONObject2.getInt("status"));
            }
            return c0792i;
        }
    }

    /* compiled from: ProGuard */
    private class C0793j extends RelativeLayout {
        int f2703a = 0;
        final /* synthetic */ TaskGuide f2704b;

        public C0793j(TaskGuide taskGuide, Context context) {
            this.f2704b = taskGuide;
            super(context);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            int y = (int) motionEvent.getY();
            Log.d("XXXX", "onInterceptTouchEvent-- action = " + motionEvent.getAction() + "currentY = " + y);
            this.f2704b.m4811d(3000);
            switch (motionEvent.getAction()) {
                case 0:
                    this.f2703a = y;
                    return false;
                case 1:
                    if (this.f2703a - y > ViewConfiguration.getTouchSlop() * 2) {
                        this.f2704b.m4828l();
                        return true;
                    }
                    break;
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            super.onTouchEvent(motionEvent);
            int y = (int) motionEvent.getY();
            Log.d("XXXX", " onTouchEvent-----startY = " + this.f2703a + "currentY = " + y);
            switch (motionEvent.getAction()) {
                case 0:
                    this.f2703a = y;
                    break;
                case 1:
                    if (this.f2703a - y > ViewConfiguration.getTouchSlop() * 2) {
                        this.f2704b.m4828l();
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    /* compiled from: ProGuard */
    private class C0794k implements Runnable {
        final /* synthetic */ TaskGuide f2705a;

        private C0794k(TaskGuide taskGuide) {
            this.f2705a = taskGuide;
        }

        public void run() {
            this.f2705a.m4828l();
        }
    }

    /* compiled from: ProGuard */
    private abstract class C1728g implements IRequestListener {
        final /* synthetic */ TaskGuide f4637c;

        protected abstract void mo3084a(Exception exception);

        private C1728g(TaskGuide taskGuide) {
            this.f4637c = taskGuide;
        }

        public void onIOException(IOException iOException) {
            mo3084a(iOException);
        }

        public void onMalformedURLException(MalformedURLException malformedURLException) {
            mo3084a(malformedURLException);
        }

        public void onJSONException(JSONException jSONException) {
            mo3084a(jSONException);
        }

        public void onConnectTimeoutException(ConnectTimeoutException connectTimeoutException) {
            mo3084a(connectTimeoutException);
        }

        public void onSocketTimeoutException(SocketTimeoutException socketTimeoutException) {
            mo3084a(socketTimeoutException);
        }

        public void onNetworkUnavailableException(NetworkUnavailableException networkUnavailableException) {
            mo3084a(networkUnavailableException);
        }

        public void onHttpStatusException(HttpStatusException httpStatusException) {
            mo3084a(httpStatusException);
        }

        public void onUnknowException(Exception exception) {
            mo3084a(exception);
        }
    }

    /* compiled from: ProGuard */
    private class C2005a extends C1728g {
        int f5432a = -1;
        final /* synthetic */ TaskGuide f5433b;

        public C2005a(TaskGuide taskGuide, int i) {
            this.f5433b = taskGuide;
            super();
            this.f5432a = i;
        }

        public void onComplete(JSONObject jSONObject) {
            String str = null;
            try {
                int i = jSONObject.getInt("code");
                str = jSONObject.getString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
                JSONObject jSONObject2;
                if (i == 0) {
                    this.f5433b.m4791a(this.f5432a, C0788d.REWARD_SUCCESS);
                    jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("result", "金券领取成功");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    this.f5433b.f4669c.onComplete(jSONObject2);
                    this.f5433b.m4802b(this.f5432a);
                    this.f5433b.m4811d(2000);
                }
                this.f5433b.m4791a(this.f5432a, C0788d.NORAML);
                this.f5433b.m4794a(str);
                jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("result", "金券领取失败");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                this.f5433b.f4669c.onComplete(jSONObject2);
                this.f5433b.m4802b(this.f5432a);
                this.f5433b.m4811d(2000);
            } catch (JSONException e22) {
                this.f5433b.m4791a(this.f5432a, C0788d.NORAML);
                this.f5433b.m4794a(str);
                e22.printStackTrace();
            }
        }

        protected void mo3084a(final Exception exception) {
            if (exception != null) {
                exception.printStackTrace();
            }
            this.f5433b.f4669c.onError(new UiError(101, "error ", "金券领取时出现异常"));
            if (this.f5433b.f4673g != null) {
                this.f5433b.f4673g.post(new Runnable(this) {
                    final /* synthetic */ C2005a f2677b;

                    public void run() {
                        C0788d c0788d = C0788d.INIT;
                        if (this.f2677b.f5432a == 0) {
                            c0788d = this.f2677b.f5433b.f4675i;
                        } else {
                            c0788d = this.f2677b.f5433b.f4676j;
                        }
                        if (c0788d == C0788d.WAITTING_BACK_REWARD) {
                            this.f2677b.f5433b.m4791a(this.f2677b.f5432a, C0788d.NORAML);
                            this.f2677b.f5433b.m4794a("领取失败 :" + exception.getClass().getName());
                        }
                        this.f2677b.f5433b.m4802b(this.f2677b.f5432a);
                        this.f2677b.f5433b.m4811d(2000);
                    }
                });
            }
        }
    }

    /* compiled from: ProGuard */
    private class C2006c extends C1728g {
        final /* synthetic */ TaskGuide f5434a;

        /* compiled from: ProGuard */
        class C07871 implements Runnable {
            final /* synthetic */ C2006c f2681a;

            C07871(C2006c c2006c) {
                this.f2681a = c2006c;
            }

            public void run() {
                this.f2681a.f5434a.m4791a(2, C0788d.INIT);
            }
        }

        private C2006c(TaskGuide taskGuide) {
            this.f5434a = taskGuide;
            super();
        }

        public void onComplete(JSONObject jSONObject) {
            try {
                this.f5434a.f4674h = C0792i.m2528a(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (this.f5434a.f4674h == null || !this.f5434a.f4674h.m2529a()) {
                mo3084a(null);
                return;
            }
            this.f5434a.showWindow();
            this.f5434a.m4791a(2, C0788d.NORAML);
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("result", "获取成功");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            this.f5434a.f4669c.onComplete(jSONObject2);
        }

        protected void mo3084a(Exception exception) {
            if (exception != null) {
                exception.printStackTrace();
            }
            if (exception == null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("result", "暂无任务");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.f5434a.f4669c.onComplete(jSONObject);
            } else {
                this.f5434a.f4669c.onError(new UiError(100, "error ", "获取任务失败"));
            }
            this.f5434a.f4673g.post(new C07871(this));
        }
    }

    public TaskGuide(Context context, QQToken qQToken) {
        super(context, qQToken);
        this.f4672f = (WindowManager) context.getSystemService("window");
        m4806c();
    }

    public TaskGuide(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(context, qQAuth, qQToken);
        this.f4672f = (WindowManager) context.getSystemService("window");
        m4806c();
    }

    private void m4806c() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.f4672f.getDefaultDisplay().getMetrics(displayMetrics);
        this.f4656A = displayMetrics.widthPixels;
        this.f4657B = displayMetrics.heightPixels;
        this.f4658C = displayMetrics.density;
    }

    private LayoutParams m4787a(Context context) {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.gravity = 49;
        this.f4672f.getDefaultDisplay().getWidth();
        this.f4672f.getDefaultDisplay().getHeight();
        layoutParams.width = m4783a(f4644o);
        layoutParams.height = m4783a(f4643n);
        layoutParams.windowAnimations = 16973826;
        layoutParams.format = 1;
        layoutParams.flags |= 520;
        layoutParams.type = 2;
        this.f4670d = layoutParams;
        return layoutParams;
    }

    private void m4810d() {
        if (this.f4670d != null) {
            this.f4670d.y = -this.f4670d.height;
        }
    }

    private int m4783a(int i) {
        return (int) (((float) i) * this.f4658C);
    }

    private ViewGroup m4799b(Context context) {
        ViewGroup c0793j = new C0793j(this, context);
        C0789e[] c0789eArr = this.f4674h.f2702c;
        View c0791h;
        ViewGroup.LayoutParams layoutParams;
        if (c0789eArr.length == 1) {
            c0791h = new C0791h(this, context, c0789eArr[0]);
            c0791h.setId(1);
            layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(15);
            c0793j.addView(c0791h, layoutParams);
        } else {
            c0791h = new C0791h(this, context, c0789eArr[0]);
            c0791h.setId(1);
            View c0791h2 = new C0791h(this, context, c0789eArr[1]);
            c0791h2.setId(2);
            layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(14);
            layoutParams.setMargins(0, m4783a(6), 0, 0);
            ViewGroup.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams2.addRule(14);
            layoutParams2.setMargins(0, m4783a(4), 0, 0);
            layoutParams2.addRule(3, 1);
            layoutParams2.addRule(5, 1);
            c0793j.addView(c0791h, layoutParams);
            c0793j.addView(c0791h2, layoutParams2);
        }
        c0793j.setBackgroundDrawable(m4812e());
        return c0793j;
    }

    private Drawable m4812e() {
        if (f4640k == null) {
            f4640k = m4784a("background.9.png", this.mContext);
        }
        return f4640k;
    }

    private Drawable m4817f() {
        if (f4641l == null) {
            f4641l = m4784a("button_green.9.png", this.mContext);
        }
        return f4641l;
    }

    private Drawable m4819g() {
        if (f4642m == null) {
            f4642m = m4784a("button_red.9.png", this.mContext);
        }
        return f4642m;
    }

    private void m4802b(final int i) {
        if (this.f4673g != null) {
            this.f4673g.post(new Runnable(this) {
                final /* synthetic */ TaskGuide f2675b;

                public void run() {
                    if (!this.f2675b.f4660E) {
                        return;
                    }
                    if (i == 0) {
                        ((C0791h) this.f2675b.f4671e.findViewById(1)).m2527a(this.f2675b.f4675i);
                    } else if (i == 1) {
                        ((C0791h) this.f2675b.f4671e.findViewById(2)).m2527a(this.f2675b.f4676j);
                    } else if (i == 2) {
                        ((C0791h) this.f2675b.f4671e.findViewById(1)).m2527a(this.f2675b.f4675i);
                        if (this.f2675b.f4671e.getChildCount() > 1) {
                            ((C0791h) this.f2675b.f4671e.findViewById(2)).m2527a(this.f2675b.f4676j);
                        }
                    }
                }
            });
        }
    }

    private void m4791a(int i, C0788d c0788d) {
        if (i == 0) {
            this.f4675i = c0788d;
        } else if (i == 1) {
            this.f4676j = c0788d;
        } else {
            this.f4675i = c0788d;
            this.f4676j = c0788d;
        }
    }

    private C0788d m4804c(int i) {
        if (i == 0) {
            return this.f4675i;
        }
        if (i == 1) {
            return this.f4676j;
        }
        return C0788d.INIT;
    }

    @SuppressLint({"ResourceAsColor"})
    public void showWindow() {
        new Handler(Looper.getMainLooper()).post(new C07833(this));
        C0687a.m2306a(this.mContext, this.mToken, "TaskApi", "showTaskWindow");
    }

    private void m4811d(int i) {
        m4820h();
        this.f4667M = new C0794k();
        this.f4673g.postDelayed(this.f4667M, (long) i);
    }

    private void m4820h() {
        this.f4673g.removeCallbacks(this.f4667M);
        if (!m4825j()) {
            this.f4673g.removeCallbacks(this.f4666L);
        }
    }

    private void m4823i() {
        if (this.f4661F) {
            m4811d(3000);
        } else {
            removeWindow();
        }
        if (this.f4661F) {
            LayoutParams layoutParams = this.f4670d;
            layoutParams.flags &= -17;
            this.f4672f.updateViewLayout(this.f4671e, this.f4670d);
        }
        this.f4661F = false;
        this.f4662G = false;
    }

    private void m4795a(boolean z) {
        this.f4663H = SystemClock.currentThreadTimeMillis();
        if (z) {
            this.f4661F = true;
        } else {
            this.f4662G = true;
        }
        this.f4664I = this.f4670d.height;
        this.f4665J = this.f4670d.y;
        LayoutParams layoutParams = this.f4670d;
        layoutParams.flags |= 16;
        this.f4672f.updateViewLayout(this.f4671e, this.f4670d);
    }

    private boolean m4825j() {
        return this.f4661F || this.f4662G;
    }

    private void m4827k() {
        if (!m4825j()) {
            this.f4673g.removeCallbacks(this.f4667M);
            this.f4673g.removeCallbacks(this.f4666L);
            this.f4666L = new C0786b(this, true);
            m4795a(true);
            this.f4673g.post(this.f4666L);
        }
    }

    private void m4828l() {
        if (!m4825j()) {
            this.f4673g.removeCallbacks(this.f4667M);
            this.f4673g.removeCallbacks(this.f4666L);
            this.f4666L = new C0786b(this, false);
            m4795a(false);
            this.f4673g.post(this.f4666L);
        }
    }

    public void removeWindow() {
        if (this.f4660E) {
            this.f4672f.removeView(this.f4671e);
            this.f4660E = false;
        }
    }

    private Drawable m4784a(String str, Context context) {
        Drawable createFromStream;
        IOException e;
        try {
            InputStream open = context.getApplicationContext().getAssets().open(str);
            if (open == null) {
                return null;
            }
            if (str.endsWith(".9.png")) {
                Bitmap decodeStream = BitmapFactory.decodeStream(open);
                if (decodeStream == null) {
                    return null;
                }
                byte[] ninePatchChunk = decodeStream.getNinePatchChunk();
                NinePatch.isNinePatchChunk(ninePatchChunk);
                return new NinePatchDrawable(decodeStream, ninePatchChunk, new Rect(), null);
            }
            createFromStream = Drawable.createFromStream(open, str);
            try {
                open.close();
                return createFromStream;
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                return createFromStream;
            }
        } catch (IOException e3) {
            IOException iOException = e3;
            createFromStream = null;
            e = iOException;
            e.printStackTrace();
            return createFromStream;
        }
    }

    private void m4794a(final String str) {
        this.f4673g.post(new Runnable(this) {
            final /* synthetic */ TaskGuide f2672b;

            public void run() {
                Toast.makeText(this.f2672b.mContext, "失败：" + str, 1).show();
            }
        });
    }

    public void showTaskGuideWindow(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.mContext = activity;
        this.f4669c = iUiListener;
        if (this.f4675i != C0788d.WAITTING_BACK_TASKINFO && this.f4676j != C0788d.WAITTING_BACK_TASKINFO && !this.f4660E) {
            Bundle bundle2;
            this.f4674h = null;
            if (bundle != null) {
                bundle2 = new Bundle(bundle);
                bundle2.putAll(composeCGIParams());
            } else {
                bundle2 = composeCGIParams();
            }
            IRequestListener c2006c = new C2006c();
            bundle2.putString("action", "task_list");
            bundle2.putString("auth", "mobile");
            bundle2.putString("appid", this.mToken.getAppId());
            HttpUtils.requestAsync(this.mToken, this.mContext, "http://appact.qzone.qq.com/appstore_activity_task_pcpush_sdk", bundle2, "GET", c2006c);
            m4791a(2, C0788d.WAITTING_BACK_TASKINFO);
        }
    }

    private void m4813e(int i) {
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("action", "get_gift");
        composeCGIParams.putString("task_id", this.f4674h.f2700a);
        composeCGIParams.putString("step_no", new Integer(i).toString());
        composeCGIParams.putString("appid", this.mToken.getAppId());
        HttpUtils.requestAsync(this.mToken, this.mContext, "http://appact.qzone.qq.com/appstore_activity_task_pcpush_sdk", composeCGIParams, "GET", new C2005a(this, i));
        m4791a(i, C0788d.WAITTING_BACK_REWARD);
        C0687a.m2306a(this.mContext, this.mToken, "TaskApi", "getGift");
    }
}
