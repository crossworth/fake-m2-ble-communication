package com.baidu.mapapi.map;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.MapViewLayoutParams.ELayoutMode;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.platform.comapi.AssetsLoadUtil;
import com.baidu.platform.comapi.map.C0482k;
import com.baidu.platform.comapi.map.C0628N;
import com.baidu.platform.comapi.map.C0638i;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.zhuoyou.plugin.running.baas.Rank;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import org.andengine.util.adt.DataConstants;
import org.andengine.util.time.TimeConstants;

@TargetApi(20)
public class WearMapView extends ViewGroup implements OnApplyWindowInsetsListener {
    public static final int BT_INVIEW = 1;
    private static final String f1379b = MapView.class.getSimpleName();
    private static String f1380c;
    private static int f1381q = 0;
    private static int f1382r = 0;
    private static int f1383s = 10;
    private static final SparseArray<Integer> f1384u = new SparseArray();
    private int f1385A;
    private int f1386B;
    private int f1387C;
    private int f1388D;
    private int f1389E;
    C0478a f1390a = C0478a.ROUND;
    private C0638i f1391d;
    private BaiduMap f1392e;
    private ImageView f1393f;
    private Bitmap f1394g;
    private C0628N f1395h;
    private boolean f1396i = true;
    private Point f1397j;
    private Point f1398k;
    private RelativeLayout f1399l;
    private SwipeDismissView f1400m;
    public AnimationTask mTask;
    public Timer mTimer;
    public C0479b mTimerHandler;
    private TextView f1401n;
    private TextView f1402o;
    private ImageView f1403p;
    private boolean f1404t = true;
    private boolean f1405v = true;
    private boolean f1406w = true;
    private float f1407x;
    private C0482k f1408y;
    private int f1409z;

    public class AnimationTask extends TimerTask {
        final /* synthetic */ WearMapView f1372a;

        public AnimationTask(WearMapView wearMapView) {
            this.f1372a = wearMapView;
        }

        public void run() {
            Message message = new Message();
            message.what = 1;
            this.f1372a.mTimerHandler.sendMessage(message);
        }
    }

    public interface OnDismissCallback {
        void onDismiss();

        void onNotify();
    }

    enum C0478a {
        ROUND,
        RECTANGLE,
        UNDETECTED
    }

    private class C0479b extends Handler {
        final /* synthetic */ WearMapView f1377a;
        private final WeakReference<Context> f1378b;

        public C0479b(WearMapView wearMapView, Context context) {
            this.f1377a = wearMapView;
            this.f1378b = new WeakReference(context);
        }

        public void handleMessage(Message message) {
            if (((Context) this.f1378b.get()) != null) {
                super.handleMessage(message);
                switch (message.what) {
                    case 1:
                        if (this.f1377a.f1395h != null) {
                            this.f1377a.m1231a(true);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    static {
        f1384u.append(3, Integer.valueOf(2000000));
        f1384u.append(4, Integer.valueOf(TimeConstants.MICROSECONDS_PER_SECOND));
        f1384u.append(5, Integer.valueOf(500000));
        f1384u.append(6, Integer.valueOf(200000));
        f1384u.append(7, Integer.valueOf(Rank.MIN));
        f1384u.append(8, Integer.valueOf(50000));
        f1384u.append(9, Integer.valueOf(25000));
        f1384u.append(10, Integer.valueOf(BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT));
        f1384u.append(11, Integer.valueOf(10000));
        f1384u.append(12, Integer.valueOf(5000));
        f1384u.append(13, Integer.valueOf(2000));
        f1384u.append(14, Integer.valueOf(1000));
        f1384u.append(15, Integer.valueOf(500));
        f1384u.append(16, Integer.valueOf(200));
        f1384u.append(17, Integer.valueOf(100));
        f1384u.append(18, Integer.valueOf(50));
        f1384u.append(19, Integer.valueOf(20));
        f1384u.append(20, Integer.valueOf(10));
        f1384u.append(21, Integer.valueOf(5));
        f1384u.append(22, Integer.valueOf(2));
    }

    public WearMapView(Context context) {
        super(context);
        m1226a(context, null);
    }

    public WearMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m1226a(context, null);
    }

    public WearMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m1226a(context, null);
    }

    public WearMapView(Context context, BaiduMapOptions baiduMapOptions) {
        super(context);
        m1226a(context, baiduMapOptions);
    }

    private int m1221a(int i, int i2) {
        return i - ((int) Math.sqrt(Math.pow((double) i, 2.0d) - Math.pow((double) i2, 2.0d)));
    }

    private void m1224a(int i) {
        if (this.f1391d != null) {
            switch (i) {
                case 0:
                    this.f1391d.onPause();
                    m1233b();
                    return;
                case 1:
                    this.f1391d.onResume();
                    m1236c();
                    return;
                default:
                    return;
            }
        }
    }

    private static void m1225a(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        f1381q = point.x;
        f1382r = point.y;
    }

    private void m1226a(Context context, BaiduMapOptions baiduMapOptions) {
        m1225a(context);
        setOnApplyWindowInsetsListener(this);
        this.mTimerHandler = new C0479b(this, context);
        this.mTimer = new Timer();
        if (!(this.mTimer == null || this.mTask == null)) {
            this.mTask.cancel();
        }
        this.mTask = new AnimationTask(this);
        this.mTimer.schedule(this.mTask, 5000);
        BMapManager.init();
        m1227a(context, baiduMapOptions, f1380c);
        this.f1392e = new BaiduMap(this.f1391d);
        this.f1391d.m2046a().m2025p(false);
        this.f1391d.m2046a().m2024o(false);
        m1237c(context);
        m1240d(context);
        m1234b(context);
        if (!(baiduMapOptions == null || baiduMapOptions.f1048h)) {
            this.f1395h.setVisibility(4);
        }
        m1243e(context);
        if (!(baiduMapOptions == null || baiduMapOptions.f1049i)) {
            this.f1399l.setVisibility(4);
        }
        if (!(baiduMapOptions == null || baiduMapOptions.f1052l == null)) {
            this.f1398k = baiduMapOptions.f1052l;
        }
        if (baiduMapOptions != null && baiduMapOptions.f1051k != null) {
            this.f1397j = baiduMapOptions.f1051k;
        }
    }

    private void m1227a(Context context, BaiduMapOptions baiduMapOptions, String str) {
        if (baiduMapOptions == null) {
            this.f1391d = new C0638i(context, null, str);
        } else {
            this.f1391d = new C0638i(context, baiduMapOptions.m1102a(), str);
        }
        addView(this.f1391d);
        this.f1408y = new C0506u(this);
        this.f1391d.m2046a().m1978a(this.f1408y);
    }

    private void m1228a(View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-2, -2);
        }
        int i = layoutParams.width;
        i = i > 0 ? MeasureSpec.makeMeasureSpec(i, DataConstants.BYTES_PER_GIGABYTE) : MeasureSpec.makeMeasureSpec(0, 0);
        int i2 = layoutParams.height;
        view.measure(i, i2 > 0 ? MeasureSpec.makeMeasureSpec(i2, DataConstants.BYTES_PER_GIGABYTE) : MeasureSpec.makeMeasureSpec(0, 0));
    }

    private void m1229a(View view, boolean z) {
        if (z) {
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "TranslationY", new float[]{0.0f, -50.0f});
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", new float[]{1.0f, 0.0f});
            animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
            animatorSet.addListener(new C0509x(this, view));
            animatorSet.setDuration(1200);
            animatorSet.start();
            return;
        }
        view.setVisibility(0);
        animatorSet = new AnimatorSet();
        ofFloat = ObjectAnimator.ofFloat(view, "TranslationY", new float[]{-50.0f, 0.0f});
        ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", new float[]{0.0f, 1.0f});
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        animatorSet.setDuration(1200);
        animatorSet.start();
    }

    private void m1231a(boolean z) {
        if (this.f1396i) {
            m1229a(this.f1395h, z);
        }
    }

    private void m1233b() {
        if (this.f1391d != null && !this.f1404t) {
            m1239d();
            this.f1404t = true;
        }
    }

    private void m1234b(Context context) {
        this.f1400m = new SwipeDismissView(context, this);
        LayoutParams layoutParams = new LayoutParams((int) ((context.getResources().getDisplayMetrics().density * 34.0f) + 0.5f), f1382r);
        this.f1400m.setBackgroundColor(Color.argb(0, 0, 0, 0));
        this.f1400m.setLayoutParams(layoutParams);
        addView(this.f1400m);
    }

    private void m1236c() {
        if (this.f1391d != null && this.f1404t) {
            m1242e();
            this.f1404t = false;
        }
    }

    private void m1237c(Context context) {
        String str = "logo_h.png";
        int densityDpi = SysOSUtil.getDensityDpi();
        if (densityDpi < 180) {
            str = "logo_l.png";
        }
        Bitmap loadAssetsFile = AssetsLoadUtil.loadAssetsFile(str, context);
        Matrix matrix;
        if (densityDpi > 480) {
            matrix = new Matrix();
            matrix.postScale(2.0f, 2.0f);
            this.f1394g = Bitmap.createBitmap(loadAssetsFile, 0, 0, loadAssetsFile.getWidth(), loadAssetsFile.getHeight(), matrix, true);
        } else if (densityDpi <= 320 || densityDpi > 480) {
            this.f1394g = loadAssetsFile;
        } else {
            matrix = new Matrix();
            matrix.postScale(1.5f, 1.5f);
            this.f1394g = Bitmap.createBitmap(loadAssetsFile, 0, 0, loadAssetsFile.getWidth(), loadAssetsFile.getHeight(), matrix, true);
        }
        if (this.f1394g != null) {
            this.f1393f = new ImageView(context);
            this.f1393f.setImageBitmap(this.f1394g);
            addView(this.f1393f);
        }
    }

    private void m1239d() {
        if (this.f1391d != null) {
            this.f1391d.m2050c();
        }
    }

    private void m1240d(Context context) {
        this.f1395h = new C0628N(context, true);
        if (this.f1395h.m1938a()) {
            this.f1395h.m1940b(new C0507v(this));
            this.f1395h.m1936a(new C0508w(this));
            addView(this.f1395h);
        }
    }

    private void m1242e() {
        if (this.f1391d != null) {
            this.f1391d.m2051d();
        }
    }

    private void m1243e(Context context) {
        this.f1399l = new RelativeLayout(context);
        this.f1399l.setLayoutParams(new LayoutParams(-2, -2));
        this.f1401n = new TextView(context);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        this.f1401n.setTextColor(Color.parseColor("#FFFFFF"));
        this.f1401n.setTextSize(2, 11.0f);
        this.f1401n.setTypeface(this.f1401n.getTypeface(), 1);
        this.f1401n.setLayoutParams(layoutParams);
        this.f1401n.setId(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        this.f1399l.addView(this.f1401n);
        this.f1402o = new TextView(context);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.addRule(14);
        this.f1402o.setTextColor(Color.parseColor("#000000"));
        this.f1402o.setTextSize(2, 11.0f);
        this.f1402o.setLayoutParams(layoutParams);
        this.f1399l.addView(this.f1402o);
        this.f1403p = new ImageView(context);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.addRule(14);
        layoutParams.addRule(3, this.f1401n.getId());
        this.f1403p.setLayoutParams(layoutParams);
        Bitmap loadAssetsFile = AssetsLoadUtil.loadAssetsFile("icon_scale.9.png", context);
        byte[] ninePatchChunk = loadAssetsFile.getNinePatchChunk();
        NinePatch.isNinePatchChunk(ninePatchChunk);
        this.f1403p.setBackgroundDrawable(new NinePatchDrawable(loadAssetsFile, ninePatchChunk, new Rect(), null));
        this.f1399l.addView(this.f1403p);
        addView(this.f1399l);
    }

    public static void setCustomMapStylePath(String str) {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("customMapStylePath String is illegal");
        } else if (new File(str).exists()) {
            f1380c = str;
        } else {
            throw new RuntimeException("please check whether the customMapStylePath file exits");
        }
    }

    public static void setMapCustomEnable(boolean z) {
        C0638i.m2040a(z);
    }

    public void addView(View view, LayoutParams layoutParams) {
        if (layoutParams instanceof MapViewLayoutParams) {
            super.addView(view, layoutParams);
        }
    }

    public final BaiduMap getMap() {
        this.f1392e.f1018c = this;
        return this.f1392e;
    }

    public final int getMapLevel() {
        return ((Integer) f1384u.get((int) this.f1391d.m2046a().m1953D().f1963a)).intValue();
    }

    public int getScaleControlViewHeight() {
        return this.f1388D;
    }

    public int getScaleControlViewWidth() {
        return this.f1389E;
    }

    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        if (windowInsets.isRound()) {
            this.f1390a = C0478a.ROUND;
        } else {
            this.f1390a = C0478a.RECTANGLE;
        }
        return windowInsets;
    }

    public void onCreate(Context context, Bundle bundle) {
        if (bundle != null) {
            f1380c = bundle.getString("customMapPath");
            if (bundle == null) {
                m1226a(context, new BaiduMapOptions());
                return;
            }
            MapStatus mapStatus = (MapStatus) bundle.getParcelable("mapstatus");
            if (this.f1397j != null) {
                this.f1397j = (Point) bundle.getParcelable("scalePosition");
            }
            if (this.f1398k != null) {
                this.f1398k = (Point) bundle.getParcelable("zoomPosition");
            }
            this.f1405v = bundle.getBoolean("mZoomControlEnabled");
            this.f1406w = bundle.getBoolean("mScaleControlEnabled");
            setPadding(bundle.getInt("paddingLeft"), bundle.getInt("paddingTop"), bundle.getInt("paddingRight"), bundle.getInt("paddingBottom"));
            m1226a(context, new BaiduMapOptions().mapStatus(mapStatus));
        }
    }

    public final void onDestroy() {
        this.f1391d.m2049b();
        if (!(this.f1394g == null || this.f1394g.isRecycled())) {
            this.f1394g.recycle();
            this.f1394g = null;
        }
        this.f1395h.m1939b();
        BMapManager.destroy();
        if (this.mTask != null) {
            this.mTask.cancel();
        }
    }

    public final void onDismiss() {
        removeAllViews();
    }

    public final void onEnterAmbient(Bundle bundle) {
        m1224a(0);
    }

    public void onExitAmbient() {
        m1224a(1);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                if (this.f1395h.getVisibility() != 0) {
                    if (this.f1395h.getVisibility() == 4) {
                        if (this.mTimer != null) {
                            if (this.mTask != null) {
                                this.mTask.cancel();
                            }
                            this.mTimer.cancel();
                            this.mTask = null;
                            this.mTimer = null;
                        }
                        m1231a(false);
                        break;
                    }
                } else if (this.mTimer != null) {
                    if (this.mTask != null) {
                        this.mTimer.cancel();
                        this.mTask.cancel();
                    }
                    this.mTimer = null;
                    this.mTask = null;
                    break;
                }
                break;
            case 1:
                this.mTimer = new Timer();
                if (!(this.mTimer == null || this.mTask == null)) {
                    this.mTask.cancel();
                }
                this.mTask = new AnimationTask(this);
                this.mTimer.schedule(this.mTask, 5000);
                break;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @TargetApi(20)
    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        float f2;
        int childCount = getChildCount();
        m1228a(this.f1393f);
        if (((getWidth() - this.f1409z) - this.f1385A) - this.f1393f.getMeasuredWidth() <= 0 || ((getHeight() - this.f1386B) - this.f1387C) - this.f1393f.getMeasuredHeight() <= 0) {
            this.f1409z = 0;
            this.f1385A = 0;
            this.f1387C = 0;
            this.f1386B = 0;
            f = 1.0f;
            f2 = 1.0f;
        } else {
            f = ((float) ((getWidth() - this.f1409z) - this.f1385A)) / ((float) getWidth());
            f2 = ((float) ((getHeight() - this.f1386B) - this.f1387C)) / ((float) getHeight());
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt == this.f1391d) {
                this.f1391d.layout(0, 0, getWidth(), getHeight());
            } else if (childAt == this.f1393f) {
                r6 = (int) (((float) this.f1387C) + (12.0f * f2));
                r3 = 0;
                r0 = 0;
                if (this.f1390a == C0478a.ROUND) {
                    m1228a(this.f1395h);
                    r3 = f1381q / 2;
                    r0 = m1221a(r3, this.f1395h.getMeasuredWidth() / 2);
                    r3 = ((f1381q / 2) - m1221a(r3, r3 - r0)) + f1383s;
                }
                r0 = (f1382r - r0) - r6;
                r3 = f1381q - r3;
                r7 = r3 - this.f1393f.getMeasuredWidth();
                this.f1393f.layout(r7, r0 - this.f1393f.getMeasuredHeight(), r3, r0);
            } else if (childAt == this.f1395h) {
                if (this.f1395h.m1938a()) {
                    m1228a(this.f1395h);
                    if (this.f1398k == null) {
                        r0 = 0;
                        if (this.f1390a == C0478a.ROUND) {
                            r0 = m1221a(f1382r / 2, this.f1395h.getMeasuredWidth() / 2);
                        }
                        r0 = (int) (((float) r0) + ((12.0f * f2) + ((float) this.f1386B)));
                        r3 = (f1381q - this.f1395h.getMeasuredWidth()) / 2;
                        this.f1395h.layout(r3, r0, this.f1395h.getMeasuredWidth() + r3, this.f1395h.getMeasuredHeight() + r0);
                    } else {
                        this.f1395h.layout(this.f1398k.x, this.f1398k.y, this.f1398k.x + this.f1395h.getMeasuredWidth(), this.f1398k.y + this.f1395h.getMeasuredHeight());
                    }
                }
            } else if (childAt == this.f1399l) {
                r3 = 0;
                r0 = 0;
                if (this.f1390a == C0478a.ROUND) {
                    m1228a(this.f1395h);
                    r3 = f1381q / 2;
                    r0 = m1221a(r3, this.f1395h.getMeasuredWidth() / 2);
                    r3 = ((f1381q / 2) - m1221a(r3, r3 - r0)) + f1383s;
                }
                m1228a(this.f1399l);
                if (this.f1397j == null) {
                    r6 = (int) (((float) this.f1387C) + (12.0f * f2));
                    this.f1389E = this.f1399l.getMeasuredWidth();
                    this.f1388D = this.f1399l.getMeasuredHeight();
                    r3 = (int) (((float) r3) + (((float) this.f1409z) + (5.0f * f)));
                    r0 = (f1382r - r6) - r0;
                    r7 = r0 - this.f1399l.getMeasuredHeight();
                    this.f1399l.layout(r3, r7, this.f1389E + r3, r0);
                } else {
                    this.f1399l.layout(this.f1397j.x, this.f1397j.y, this.f1397j.x + this.f1399l.getMeasuredWidth(), this.f1397j.y + this.f1399l.getMeasuredHeight());
                }
            } else if (childAt == this.f1400m) {
                m1228a(this.f1400m);
                this.f1400m.layout(0, 0, this.f1400m.getMeasuredWidth(), f1382r);
            } else {
                LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof MapViewLayoutParams) {
                    Point point;
                    MapViewLayoutParams mapViewLayoutParams = (MapViewLayoutParams) layoutParams;
                    if (mapViewLayoutParams.f1203c == ELayoutMode.absoluteMode) {
                        point = mapViewLayoutParams.f1202b;
                    } else {
                        point = this.f1391d.m2046a().m1966a(CoordUtil.ll2mc(mapViewLayoutParams.f1201a));
                    }
                    m1228a(childAt);
                    r7 = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    float f3 = mapViewLayoutParams.f1204d;
                    int i6 = (int) (((float) point.x) - (f3 * ((float) r7)));
                    r0 = mapViewLayoutParams.f1206f + ((int) (((float) point.y) - (mapViewLayoutParams.f1205e * ((float) measuredHeight))));
                    childAt.layout(i6, r0, i6 + r7, r0 + measuredHeight);
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null && this.f1392e != null) {
            bundle.putParcelable("mapstatus", this.f1392e.getMapStatus());
            if (this.f1397j != null) {
                bundle.putParcelable("scalePosition", this.f1397j);
            }
            if (this.f1398k != null) {
                bundle.putParcelable("zoomPosition", this.f1398k);
            }
            bundle.putBoolean("mZoomControlEnabled", this.f1405v);
            bundle.putBoolean("mScaleControlEnabled", this.f1406w);
            bundle.putInt("paddingLeft", this.f1409z);
            bundle.putInt("paddingTop", this.f1386B);
            bundle.putInt("paddingRight", this.f1385A);
            bundle.putInt("paddingBottom", this.f1387C);
            bundle.putString("customMapPath", f1380c);
        }
    }

    public void removeView(View view) {
        if (view != this.f1393f) {
            super.removeView(view);
        }
    }

    public void setOnDismissCallbackListener(OnDismissCallback onDismissCallback) {
        if (this.f1400m != null) {
            this.f1400m.setCallback(onDismissCallback);
        }
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.f1409z = i;
        this.f1386B = i2;
        this.f1385A = i3;
        this.f1387C = i4;
    }

    public void setScaleControlPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.f1397j = point;
            requestLayout();
        }
    }

    public void setShape(C0478a c0478a) {
        this.f1390a = c0478a;
    }

    public void setViewAnimitionEnable(boolean z) {
        this.f1396i = z;
    }

    public void setZoomControlsPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.f1398k = point;
            requestLayout();
        }
    }

    public void showScaleControl(boolean z) {
        this.f1399l.setVisibility(z ? 0 : 8);
        this.f1406w = z;
    }

    public void showZoomControls(boolean z) {
        if (this.f1395h.m1938a()) {
            this.f1395h.setVisibility(z ? 0 : 8);
            this.f1405v = z;
        }
    }
}
