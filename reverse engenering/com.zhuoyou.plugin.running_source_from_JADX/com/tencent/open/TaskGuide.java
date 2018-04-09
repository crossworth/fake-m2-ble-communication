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
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.droi.btlib.service.BluetoothManager;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.p025a.C1148a;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.HttpUtils.HttpStatusException;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.umeng.facebook.share.internal.ShareConstants;
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
    private static int f4039L = MessageHandler.WHAT_ITEM_SELECTED;
    static long f4040b = 5000;
    private static Drawable f4041k;
    private static Drawable f4042l;
    private static Drawable f4043m;
    private static int f4044n = 75;
    private static int f4045o = 284;
    private static int f4046p = 75;
    private static int f4047q = 30;
    private static int f4048r = 29;
    private static int f4049s = 5;
    private static int f4050t = 74;
    private static int f4051u = 0;
    private static int f4052v = 6;
    private static int f4053w = 153;
    private static int f4054x = 30;
    private static int f4055y = 6;
    private static int f4056z = 3;
    private int f4057A = 0;
    private int f4058B = 0;
    private float f4059C = 0.0f;
    private Interpolator f4060D = new AccelerateInterpolator();
    private boolean f4061E = false;
    private Context f4062F;
    private boolean f4063G = false;
    private boolean f4064H = false;
    private long f4065I;
    private int f4066J;
    private int f4067K;
    private Runnable f4068M = null;
    private Runnable f4069N = null;
    boolean f4070a = false;
    IUiListener f4071c;
    private LayoutParams f4072d = null;
    private ViewGroup f4073e = null;
    private WindowManager f4074f;
    private Handler f4075g = new Handler(Looper.getMainLooper());
    private C1298h f4076h;
    private C1302k f4077i = C1302k.INIT;
    private C1302k f4078j = C1302k.INIT;

    /* compiled from: ProGuard */
    class C12872 implements Runnable {
        final /* synthetic */ TaskGuide f4001a;

        C12872(TaskGuide taskGuide) {
            this.f4001a = taskGuide;
        }

        public void run() {
            this.f4001a.f4073e = this.f4001a.m3774b(this.f4001a.f4062F);
            this.f4001a.f4072d = this.f4001a.m3762a(this.f4001a.f4062F);
            this.f4001a.m3785d();
            WindowManager windowManager = (WindowManager) this.f4001a.f4062F.getSystemService("window");
            if (!((Activity) this.f4001a.f4062F).isFinishing()) {
                if (!this.f4001a.f4061E) {
                    windowManager.addView(this.f4001a.f4073e, this.f4001a.f4072d);
                }
                this.f4001a.f4061E = true;
                this.f4001a.m3777b(2);
                this.f4001a.m3802k();
            }
        }
    }

    /* compiled from: ProGuard */
    private abstract class C1290a implements IRequestListener {
        final /* synthetic */ TaskGuide f4005a;

        protected abstract void mo2207a(Exception exception);

        private C1290a(TaskGuide taskGuide) {
            this.f4005a = taskGuide;
        }

        public void onIOException(IOException iOException) {
            mo2207a(iOException);
        }

        public void onMalformedURLException(MalformedURLException malformedURLException) {
            mo2207a(malformedURLException);
        }

        public void onJSONException(JSONException jSONException) {
            mo2207a(jSONException);
        }

        public void onConnectTimeoutException(ConnectTimeoutException connectTimeoutException) {
            mo2207a(connectTimeoutException);
        }

        public void onSocketTimeoutException(SocketTimeoutException socketTimeoutException) {
            mo2207a(socketTimeoutException);
        }

        public void onNetworkUnavailableException(NetworkUnavailableException networkUnavailableException) {
            mo2207a(networkUnavailableException);
        }

        public void onHttpStatusException(HttpStatusException httpStatusException) {
            mo2207a(httpStatusException);
        }

        public void onUnknowException(Exception exception) {
            mo2207a(exception);
        }
    }

    /* compiled from: ProGuard */
    private class C1291b implements Runnable {
        final /* synthetic */ TaskGuide f4006a;

        private C1291b(TaskGuide taskGuide) {
            this.f4006a = taskGuide;
        }

        public void run() {
            this.f4006a.m3803l();
        }
    }

    /* compiled from: ProGuard */
    class C1292c implements Runnable {
        boolean f4007a = false;
        float f4008b = 0.0f;
        final /* synthetic */ TaskGuide f4009c;

        public C1292c(TaskGuide taskGuide, boolean z) {
            this.f4009c = taskGuide;
            this.f4007a = z;
        }

        public void run() {
            Object obj = 1;
            SystemClock.currentThreadTimeMillis();
            this.f4008b = (float) (((double) this.f4008b) + 0.1d);
            float f = this.f4008b;
            if (f > 1.0f) {
                f = 1.0f;
            }
            Object obj2 = f >= 1.0f ? 1 : null;
            int interpolation = (int) (this.f4009c.f4060D.getInterpolation(f) * ((float) this.f4009c.f4066J));
            if (this.f4007a) {
                this.f4009c.f4072d.y = this.f4009c.f4067K + interpolation;
            } else {
                this.f4009c.f4072d.y = this.f4009c.f4067K - interpolation;
            }
            C1314f.m3867b("openSDK_LOG.TaskGuide", "mWinParams.y = " + this.f4009c.f4072d.y + "deltaDistence = " + interpolation);
            if (this.f4009c.f4061E) {
                this.f4009c.f4074f.updateViewLayout(this.f4009c.f4073e, this.f4009c.f4072d);
                obj = obj2;
            }
            if (obj != null) {
                this.f4009c.m3797i();
            } else {
                this.f4009c.f4075g.postDelayed(this.f4009c.f4068M, 5);
            }
        }
    }

    /* compiled from: ProGuard */
    private class C1294d extends C1290a {
        int f4012b = -1;
        final /* synthetic */ TaskGuide f4013c;

        public C1294d(TaskGuide taskGuide, int i) {
            this.f4013c = taskGuide;
            super();
            this.f4012b = i;
        }

        public void onComplete(JSONObject jSONObject) {
            String str = null;
            try {
                int i = jSONObject.getInt("code");
                str = jSONObject.getString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
                JSONObject jSONObject2;
                if (i == 0) {
                    this.f4013c.m3766a(this.f4012b, C1302k.REWARD_SUCCESS);
                    jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("result", "金券领取成功");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    this.f4013c.f4071c.onComplete(jSONObject2);
                    this.f4013c.m3777b(this.f4012b);
                    this.f4013c.m3786d(2000);
                }
                this.f4013c.m3766a(this.f4012b, C1302k.NORAML);
                this.f4013c.m3769a(str);
                jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("result", "金券领取失败");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                this.f4013c.f4071c.onComplete(jSONObject2);
                this.f4013c.m3777b(this.f4012b);
                this.f4013c.m3786d(2000);
            } catch (JSONException e22) {
                this.f4013c.m3766a(this.f4012b, C1302k.NORAML);
                this.f4013c.m3769a(str);
                e22.printStackTrace();
            }
        }

        protected void mo2207a(final Exception exception) {
            if (exception != null) {
                exception.printStackTrace();
            }
            this.f4013c.f4071c.onError(new UiError(101, "error ", "金券领取时出现异常"));
            if (this.f4013c.f4075g != null) {
                this.f4013c.f4075g.post(new Runnable(this) {
                    final /* synthetic */ C1294d f4011b;

                    public void run() {
                        C1302k c1302k = C1302k.INIT;
                        if (this.f4011b.f4012b == 0) {
                            c1302k = this.f4011b.f4013c.f4077i;
                        } else {
                            c1302k = this.f4011b.f4013c.f4078j;
                        }
                        if (c1302k == C1302k.WAITTING_BACK_REWARD) {
                            this.f4011b.f4013c.m3766a(this.f4011b.f4012b, C1302k.NORAML);
                            this.f4011b.f4013c.m3769a("领取失败 :" + exception.getClass().getName());
                        }
                        this.f4011b.f4013c.m3777b(this.f4011b.f4012b);
                        this.f4011b.f4013c.m3786d(2000);
                    }
                });
            }
        }
    }

    /* compiled from: ProGuard */
    private class C1295e extends RelativeLayout {
        int f4014a = 0;
        final /* synthetic */ TaskGuide f4015b;

        public C1295e(TaskGuide taskGuide, Context context) {
            this.f4015b = taskGuide;
            super(context);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            int y = (int) motionEvent.getY();
            C1314f.m3864a("openSDK_LOG.TaskGuide", "onInterceptTouchEvent-- action = " + motionEvent.getAction() + "currentY = " + y);
            this.f4015b.m3786d((int) MessageHandler.WHAT_ITEM_SELECTED);
            switch (motionEvent.getAction()) {
                case 0:
                    this.f4014a = y;
                    return false;
                case 1:
                    if (this.f4014a - y > ViewConfiguration.getTouchSlop() * 2) {
                        this.f4015b.m3803l();
                        return true;
                    }
                    break;
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            super.onTouchEvent(motionEvent);
            int y = (int) motionEvent.getY();
            C1314f.m3867b("openSDK_LOG.TaskGuide", " onTouchEvent-----startY = " + this.f4014a + "currentY = " + y);
            switch (motionEvent.getAction()) {
                case 0:
                    this.f4014a = y;
                    break;
                case 1:
                    if (this.f4014a - y > ViewConfiguration.getTouchSlop() * 2) {
                        this.f4015b.m3803l();
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    /* compiled from: ProGuard */
    class C1296f implements OnClickListener {
        int f4016a;
        final /* synthetic */ TaskGuide f4017b;

        public C1296f(TaskGuide taskGuide, int i) {
            this.f4017b = taskGuide;
            this.f4016a = i;
        }

        public void onClick(View view) {
            Button button = (Button) view;
            if (this.f4017b.m3779c(this.f4016a) == C1302k.NORAML) {
                this.f4017b.m3788e(this.f4016a);
                this.f4017b.m3777b(this.f4016a);
            }
            this.f4017b.m3796h();
        }
    }

    /* compiled from: ProGuard */
    private static class C1297g {
        int f4018a;
        String f4019b;
        String f4020c;
        long f4021d;
        int f4022e;

        public C1297g(int i, String str, String str2, long j, int i2) {
            this.f4018a = i;
            this.f4019b = str;
            this.f4020c = str2;
            this.f4021d = j;
            this.f4022e = i2;
        }
    }

    /* compiled from: ProGuard */
    private static class C1298h {
        String f4023a;
        String f4024b;
        C1297g[] f4025c;

        private C1298h() {
        }

        public boolean m3752a() {
            if (TextUtils.isEmpty(this.f4023a) || this.f4025c == null || this.f4025c.length <= 0) {
                return false;
            }
            return true;
        }

        static C1298h m3751a(JSONObject jSONObject) throws JSONException {
            if (jSONObject == null) {
                return null;
            }
            C1298h c1298h = new C1298h();
            JSONObject jSONObject2 = jSONObject.getJSONObject("task_info");
            c1298h.f4023a = jSONObject2.getString("task_id");
            c1298h.f4024b = jSONObject2.getString("task_desc");
            JSONArray jSONArray = jSONObject2.getJSONArray("step_info");
            int length = jSONArray.length();
            if (length > 0) {
                c1298h.f4025c = new C1297g[length];
            }
            for (int i = 0; i < length; i++) {
                jSONObject2 = jSONArray.getJSONObject(i);
                c1298h.f4025c[i] = new C1297g(jSONObject2.getInt("step_no"), jSONObject2.getString("step_desc"), jSONObject2.getString("step_gift"), jSONObject2.getLong("end_time"), jSONObject2.getInt("status"));
            }
            return c1298h;
        }
    }

    /* compiled from: ProGuard */
    private class C1299i extends LinearLayout {
        final /* synthetic */ TaskGuide f4026a;
        private TextView f4027b;
        private Button f4028c;
        private C1297g f4029d;

        public C1299i(TaskGuide taskGuide, Context context, C1297g c1297g) {
            this.f4026a = taskGuide;
            super(context);
            this.f4029d = c1297g;
            setOrientation(0);
            m3753a();
        }

        private void m3753a() {
            this.f4027b = new TextView(this.f4026a.f4062F);
            this.f4027b.setTextColor(Color.rgb(255, 255, 255));
            this.f4027b.setTextSize(15.0f);
            this.f4027b.setShadowLayer(1.0f, 1.0f, 1.0f, Color.rgb(BluetoothManager.TYPE_BT_CONNECTION_LOST, 211, 199));
            this.f4027b.setGravity(3);
            this.f4027b.setEllipsize(TruncateAt.END);
            this.f4027b.setIncludeFontPadding(false);
            this.f4027b.setSingleLine(true);
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
            layoutParams.weight = 1.0f;
            layoutParams.leftMargin = this.f4026a.m3758a(4);
            addView(this.f4027b, layoutParams);
            this.f4028c = new Button(this.f4026a.f4062F);
            this.f4028c.setPadding(0, 0, 0, 0);
            this.f4028c.setTextSize(16.0f);
            this.f4028c.setTextColor(Color.rgb(255, 255, 255));
            this.f4028c.setShadowLayer(1.0f, 1.0f, 1.0f, Color.rgb(BluetoothManager.TYPE_BT_CONNECTION_LOST, 211, 199));
            this.f4028c.setIncludeFontPadding(false);
            this.f4028c.setOnClickListener(new C1296f(this.f4026a, this.f4029d.f4018a));
            layoutParams = new LinearLayout.LayoutParams(this.f4026a.m3758a(TaskGuide.f4046p), this.f4026a.m3758a(TaskGuide.f4047q));
            layoutParams.leftMargin = this.f4026a.m3758a(2);
            layoutParams.rightMargin = this.f4026a.m3758a(8);
            addView(this.f4028c, layoutParams);
        }

        public void m3754a(C1302k c1302k) {
            if (!TextUtils.isEmpty(this.f4029d.f4019b)) {
                this.f4027b.setText(this.f4029d.f4019b);
            }
            switch (c1302k) {
                case INIT:
                    this.f4028c.setEnabled(false);
                    return;
                case NORAML:
                    if (this.f4029d.f4022e == 1) {
                        this.f4028c.setText(this.f4029d.f4020c);
                        this.f4028c.setBackgroundDrawable(null);
                        this.f4028c.setTextColor(Color.rgb(255, BluetoothManager.TYPE_BT_CONNECTION_FAIL, 0));
                        this.f4028c.setEnabled(false);
                        return;
                    } else if (this.f4029d.f4022e == 2) {
                        this.f4028c.setText("领取奖励");
                        this.f4028c.setTextColor(Color.rgb(255, 255, 255));
                        this.f4028c.setBackgroundDrawable(this.f4026a.m3792f());
                        this.f4028c.setEnabled(true);
                        return;
                    } else {
                        return;
                    }
                case WAITTING_BACK_REWARD:
                    this.f4028c.setText("领取中...");
                    this.f4028c.setEnabled(false);
                    return;
                case REWARD_SUCCESS:
                    this.f4028c.setText("已领取");
                    this.f4028c.setBackgroundDrawable(this.f4026a.m3793g());
                    this.f4028c.setEnabled(false);
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: ProGuard */
    private class C1301j extends C1290a {
        final /* synthetic */ TaskGuide f4031b;

        /* compiled from: ProGuard */
        class C13001 implements Runnable {
            final /* synthetic */ C1301j f4030a;

            C13001(C1301j c1301j) {
                this.f4030a = c1301j;
            }

            public void run() {
                this.f4030a.f4031b.m3766a(2, C1302k.INIT);
            }
        }

        private C1301j(TaskGuide taskGuide) {
            this.f4031b = taskGuide;
            super();
        }

        public void onComplete(JSONObject jSONObject) {
            try {
                this.f4031b.f4076h = C1298h.m3751a(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (this.f4031b.f4076h == null || !this.f4031b.f4076h.m3752a()) {
                mo2207a(null);
                return;
            }
            this.f4031b.showWindow();
            this.f4031b.m3766a(2, C1302k.NORAML);
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("result", "获取成功");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            this.f4031b.f4071c.onComplete(jSONObject2);
        }

        protected void mo2207a(Exception exception) {
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
                this.f4031b.f4071c.onComplete(jSONObject);
            } else {
                this.f4031b.f4071c.onError(new UiError(100, "error ", "获取任务失败"));
            }
            this.f4031b.f4075g.post(new C13001(this));
        }
    }

    /* compiled from: ProGuard */
    private enum C1302k {
        INIT,
        WAITTING_BACK_TASKINFO,
        WAITTING_BACK_REWARD,
        NORAML,
        REWARD_SUCCESS,
        REWARD_FAIL;

        public static C1302k[] m3756a() {
            return (C1302k[]) f4038g.clone();
        }
    }

    public TaskGuide(Context context, QQToken qQToken) {
        super(qQToken);
        this.f4062F = context;
        this.f4074f = (WindowManager) context.getSystemService("window");
        m3781c();
    }

    public TaskGuide(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(qQAuth, qQToken);
        this.f4062F = context;
        this.f4074f = (WindowManager) context.getSystemService("window");
        m3781c();
    }

    private void m3781c() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.f4074f.getDefaultDisplay().getMetrics(displayMetrics);
        this.f4057A = displayMetrics.widthPixels;
        this.f4058B = displayMetrics.heightPixels;
        this.f4059C = displayMetrics.density;
    }

    private LayoutParams m3762a(Context context) {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.gravity = 49;
        this.f4074f.getDefaultDisplay().getWidth();
        this.f4074f.getDefaultDisplay().getHeight();
        layoutParams.width = m3758a(f4045o);
        layoutParams.height = m3758a(f4044n);
        layoutParams.windowAnimations = 16973826;
        layoutParams.format = 1;
        layoutParams.flags |= m_AppUI.MSG_PLACEFIELD_RELOAD;
        layoutParams.type = 2;
        this.f4072d = layoutParams;
        return layoutParams;
    }

    private void m3785d() {
        if (this.f4072d != null) {
            this.f4072d.y = -this.f4072d.height;
        }
    }

    private int m3758a(int i) {
        return (int) (((float) i) * this.f4059C);
    }

    private ViewGroup m3774b(Context context) {
        ViewGroup c1295e = new C1295e(this, context);
        C1297g[] c1297gArr = this.f4076h.f4025c;
        View c1299i;
        ViewGroup.LayoutParams layoutParams;
        if (c1297gArr.length == 1) {
            c1299i = new C1299i(this, context, c1297gArr[0]);
            c1299i.setId(1);
            layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(15);
            c1295e.addView(c1299i, layoutParams);
        } else {
            c1299i = new C1299i(this, context, c1297gArr[0]);
            c1299i.setId(1);
            View c1299i2 = new C1299i(this, context, c1297gArr[1]);
            c1299i2.setId(2);
            layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(14);
            layoutParams.setMargins(0, m3758a(6), 0, 0);
            ViewGroup.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams2.addRule(14);
            layoutParams2.setMargins(0, m3758a(4), 0, 0);
            layoutParams2.addRule(3, 1);
            layoutParams2.addRule(5, 1);
            c1295e.addView(c1299i, layoutParams);
            c1295e.addView(c1299i2, layoutParams2);
        }
        c1295e.setBackgroundDrawable(m3787e());
        return c1295e;
    }

    private Drawable m3787e() {
        if (f4041k == null) {
            f4041k = m3759a("background.9.png", this.f4062F);
        }
        return f4041k;
    }

    private Drawable m3792f() {
        if (f4042l == null) {
            f4042l = m3759a("button_green.9.png", this.f4062F);
        }
        return f4042l;
    }

    private Drawable m3793g() {
        if (f4043m == null) {
            f4043m = m3759a("button_red.9.png", this.f4062F);
        }
        return f4043m;
    }

    private void m3777b(final int i) {
        if (this.f4075g != null) {
            this.f4075g.post(new Runnable(this) {
                final /* synthetic */ TaskGuide f4000b;

                public void run() {
                    if (!this.f4000b.f4061E) {
                        return;
                    }
                    if (i == 0) {
                        ((C1299i) this.f4000b.f4073e.findViewById(1)).m3754a(this.f4000b.f4077i);
                    } else if (i == 1) {
                        ((C1299i) this.f4000b.f4073e.findViewById(2)).m3754a(this.f4000b.f4078j);
                    } else if (i == 2) {
                        ((C1299i) this.f4000b.f4073e.findViewById(1)).m3754a(this.f4000b.f4077i);
                        if (this.f4000b.f4073e.getChildCount() > 1) {
                            ((C1299i) this.f4000b.f4073e.findViewById(2)).m3754a(this.f4000b.f4078j);
                        }
                    }
                }
            });
        }
    }

    private void m3766a(int i, C1302k c1302k) {
        if (i == 0) {
            this.f4077i = c1302k;
        } else if (i == 1) {
            this.f4078j = c1302k;
        } else {
            this.f4077i = c1302k;
            this.f4078j = c1302k;
        }
    }

    private C1302k m3779c(int i) {
        if (i == 0) {
            return this.f4077i;
        }
        if (i == 1) {
            return this.f4078j;
        }
        return C1302k.INIT;
    }

    @SuppressLint({"ResourceAsColor"})
    public void showWindow() {
        new Handler(Looper.getMainLooper()).post(new C12872(this));
        C1148a.m3344a(this.f4062F, this.mToken, "TaskApi", "showTaskWindow");
    }

    private void m3786d(int i) {
        m3796h();
        this.f4069N = new C1291b();
        this.f4075g.postDelayed(this.f4069N, (long) i);
    }

    private void m3796h() {
        this.f4075g.removeCallbacks(this.f4069N);
        if (!m3800j()) {
            this.f4075g.removeCallbacks(this.f4068M);
        }
    }

    private void m3797i() {
        if (this.f4063G) {
            m3786d((int) MessageHandler.WHAT_ITEM_SELECTED);
        } else {
            removeWindow();
        }
        if (this.f4063G) {
            LayoutParams layoutParams = this.f4072d;
            layoutParams.flags &= -17;
            this.f4074f.updateViewLayout(this.f4073e, this.f4072d);
        }
        this.f4063G = false;
        this.f4064H = false;
    }

    private void m3770a(boolean z) {
        this.f4065I = SystemClock.currentThreadTimeMillis();
        if (z) {
            this.f4063G = true;
        } else {
            this.f4064H = true;
        }
        this.f4066J = this.f4072d.height;
        this.f4067K = this.f4072d.y;
        LayoutParams layoutParams = this.f4072d;
        layoutParams.flags |= 16;
        this.f4074f.updateViewLayout(this.f4073e, this.f4072d);
    }

    private boolean m3800j() {
        return this.f4063G || this.f4064H;
    }

    private void m3802k() {
        if (!m3800j()) {
            this.f4075g.removeCallbacks(this.f4069N);
            this.f4075g.removeCallbacks(this.f4068M);
            this.f4068M = new C1292c(this, true);
            m3770a(true);
            this.f4075g.post(this.f4068M);
        }
    }

    private void m3803l() {
        if (!m3800j()) {
            this.f4075g.removeCallbacks(this.f4069N);
            this.f4075g.removeCallbacks(this.f4068M);
            this.f4068M = new C1292c(this, false);
            m3770a(false);
            this.f4075g.post(this.f4068M);
        }
    }

    public void removeWindow() {
        if (this.f4061E) {
            this.f4074f.removeView(this.f4073e);
            this.f4061E = false;
        }
    }

    private Drawable m3759a(String str, Context context) {
        Drawable createFromStream;
        IOException e;
        try {
            InputStream open = context.getApplicationContext().getAssets().open(str);
            if (open == null) {
                return null;
            }
            if (str.endsWith(".9.png")) {
                Bitmap decodeStream;
                try {
                    decodeStream = BitmapFactory.decodeStream(open);
                } catch (OutOfMemoryError e2) {
                    e2.printStackTrace();
                    decodeStream = null;
                }
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
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                return createFromStream;
            }
        } catch (IOException e4) {
            IOException iOException = e4;
            createFromStream = null;
            e = iOException;
            e.printStackTrace();
            return createFromStream;
        }
    }

    private void m3769a(final String str) {
        this.f4075g.post(new Runnable(this) {
            final /* synthetic */ TaskGuide f4003b;

            public void run() {
                Toast.makeText(this.f4003b.f4062F, "失败：" + str, 1).show();
            }
        });
    }

    public void showTaskGuideWindow(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f4062F = activity;
        this.f4071c = iUiListener;
        if (this.f4077i == C1302k.WAITTING_BACK_TASKINFO || this.f4078j == C1302k.WAITTING_BACK_TASKINFO || this.f4061E) {
            C1314f.m3870c("openSDK_LOG.TaskGuide", "showTaskGuideWindow, mState1 ==" + this.f4077i + ", mState2" + this.f4078j);
            return;
        }
        Bundle bundle2;
        this.f4076h = null;
        if (bundle != null) {
            bundle2 = new Bundle(bundle);
            bundle2.putAll(composeCGIParams());
        } else {
            bundle2 = composeCGIParams();
        }
        IRequestListener c1301j = new C1301j();
        bundle2.putString("action", "task_list");
        bundle2.putString("auth", "mobile");
        bundle2.putString("appid", this.mToken.getAppId());
        HttpUtils.requestAsync(this.mToken, this.f4062F, "http://appact.qzone.qq.com/appstore_activity_task_pcpush_sdk", bundle2, Constants.HTTP_GET, c1301j);
        m3766a(2, C1302k.WAITTING_BACK_TASKINFO);
    }

    private void m3788e(int i) {
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("action", "get_gift");
        composeCGIParams.putString("task_id", this.f4076h.f4023a);
        composeCGIParams.putString("step_no", new Integer(i).toString());
        composeCGIParams.putString("appid", this.mToken.getAppId());
        HttpUtils.requestAsync(this.mToken, this.f4062F, "http://appact.qzone.qq.com/appstore_activity_task_pcpush_sdk", composeCGIParams, Constants.HTTP_GET, new C1294d(this, i));
        m3766a(i, C1302k.WAITTING_BACK_REWARD);
        C1148a.m3344a(this.f4062F, this.mToken, "TaskApi", "getGift");
    }
}
