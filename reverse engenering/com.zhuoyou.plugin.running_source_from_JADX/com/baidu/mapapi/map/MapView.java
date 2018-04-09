package com.baidu.mapapi.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
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
import org.andengine.util.adt.DataConstants;
import org.andengine.util.time.TimeConstants;

public final class MapView extends ViewGroup {
    private static final String f1168a = MapView.class.getSimpleName();
    private static String f1169b;
    private static final SparseArray<Integer> f1170n = new SparseArray();
    private C0638i f1171c;
    private BaiduMap f1172d;
    private ImageView f1173e;
    private Bitmap f1174f;
    private C0628N f1175g;
    private Point f1176h;
    private Point f1177i;
    private RelativeLayout f1178j;
    private TextView f1179k;
    private TextView f1180l;
    private ImageView f1181m;
    private int f1182o = LogoPosition.logoPostionleftBottom.ordinal();
    private boolean f1183p = true;
    private boolean f1184q = true;
    private float f1185r;
    private C0482k f1186s;
    private int f1187t;
    private int f1188u;
    private int f1189v;
    private int f1190w;
    private int f1191x;
    private int f1192y;

    static {
        f1170n.append(3, Integer.valueOf(2000000));
        f1170n.append(4, Integer.valueOf(TimeConstants.MICROSECONDS_PER_SECOND));
        f1170n.append(5, Integer.valueOf(500000));
        f1170n.append(6, Integer.valueOf(200000));
        f1170n.append(7, Integer.valueOf(Rank.MIN));
        f1170n.append(8, Integer.valueOf(50000));
        f1170n.append(9, Integer.valueOf(25000));
        f1170n.append(10, Integer.valueOf(BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT));
        f1170n.append(11, Integer.valueOf(10000));
        f1170n.append(12, Integer.valueOf(5000));
        f1170n.append(13, Integer.valueOf(2000));
        f1170n.append(14, Integer.valueOf(1000));
        f1170n.append(15, Integer.valueOf(500));
        f1170n.append(16, Integer.valueOf(200));
        f1170n.append(17, Integer.valueOf(100));
        f1170n.append(18, Integer.valueOf(50));
        f1170n.append(19, Integer.valueOf(20));
        f1170n.append(20, Integer.valueOf(10));
        f1170n.append(21, Integer.valueOf(5));
        f1170n.append(22, Integer.valueOf(2));
    }

    public MapView(Context context) {
        super(context);
        m1155a(context, null);
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m1155a(context, null);
    }

    public MapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m1155a(context, null);
    }

    public MapView(Context context, BaiduMapOptions baiduMapOptions) {
        super(context);
        m1155a(context, baiduMapOptions);
    }

    private void m1154a(Context context) {
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
            this.f1174f = Bitmap.createBitmap(loadAssetsFile, 0, 0, loadAssetsFile.getWidth(), loadAssetsFile.getHeight(), matrix, true);
        } else if (densityDpi <= 320 || densityDpi > 480) {
            this.f1174f = loadAssetsFile;
        } else {
            matrix = new Matrix();
            matrix.postScale(1.5f, 1.5f);
            this.f1174f = Bitmap.createBitmap(loadAssetsFile, 0, 0, loadAssetsFile.getWidth(), loadAssetsFile.getHeight(), matrix, true);
        }
        if (this.f1174f != null) {
            this.f1173e = new ImageView(context);
            this.f1173e.setImageBitmap(this.f1174f);
            addView(this.f1173e);
        }
    }

    private void m1155a(Context context, BaiduMapOptions baiduMapOptions) {
        BMapManager.init();
        m1156a(context, baiduMapOptions, f1169b);
        this.f1172d = new BaiduMap(this.f1171c);
        m1154a(context);
        m1160b(context);
        if (!(baiduMapOptions == null || baiduMapOptions.f1048h)) {
            this.f1175g.setVisibility(4);
        }
        m1162c(context);
        if (!(baiduMapOptions == null || baiduMapOptions.f1049i)) {
            this.f1178j.setVisibility(4);
        }
        if (!(baiduMapOptions == null || baiduMapOptions.f1050j == null)) {
            this.f1182o = baiduMapOptions.f1050j.ordinal();
        }
        if (!(baiduMapOptions == null || baiduMapOptions.f1052l == null)) {
            this.f1177i = baiduMapOptions.f1052l;
        }
        if (baiduMapOptions != null && baiduMapOptions.f1051k != null) {
            this.f1176h = baiduMapOptions.f1051k;
        }
    }

    private void m1156a(Context context, BaiduMapOptions baiduMapOptions, String str) {
        if (baiduMapOptions == null) {
            this.f1171c = new C0638i(context, null, str);
        } else {
            this.f1171c = new C0638i(context, baiduMapOptions.m1102a(), str);
        }
        addView(this.f1171c);
        this.f1186s = new C0492i(this);
        this.f1171c.m2046a().m1978a(this.f1186s);
    }

    private void m1157a(View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-2, -2);
        }
        int i = layoutParams.width;
        i = i > 0 ? MeasureSpec.makeMeasureSpec(i, DataConstants.BYTES_PER_GIGABYTE) : MeasureSpec.makeMeasureSpec(0, 0);
        int i2 = layoutParams.height;
        view.measure(i, i2 > 0 ? MeasureSpec.makeMeasureSpec(i2, DataConstants.BYTES_PER_GIGABYTE) : MeasureSpec.makeMeasureSpec(0, 0));
    }

    private void m1159b() {
        boolean z = false;
        float f = this.f1171c.m2046a().m1953D().f1963a;
        if (this.f1175g.m1938a()) {
            this.f1175g.m1941b(f > this.f1171c.m2046a().f2048b);
            C0628N c0628n = this.f1175g;
            if (f < this.f1171c.m2046a().f2047a) {
                z = true;
            }
            c0628n.m1937a(z);
        }
    }

    private void m1160b(Context context) {
        this.f1175g = new C0628N(context, false);
        if (this.f1175g.m1938a()) {
            this.f1175g.m1940b(new C0493j(this));
            this.f1175g.m1936a(new C0494k(this));
            addView(this.f1175g);
        }
    }

    private void m1162c(Context context) {
        this.f1178j = new RelativeLayout(context);
        this.f1178j.setLayoutParams(new LayoutParams(-2, -2));
        this.f1179k = new TextView(context);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        this.f1179k.setTextColor(Color.parseColor("#FFFFFF"));
        this.f1179k.setTextSize(2, 11.0f);
        this.f1179k.setTypeface(this.f1179k.getTypeface(), 1);
        this.f1179k.setLayoutParams(layoutParams);
        this.f1179k.setId(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        this.f1178j.addView(this.f1179k);
        this.f1180l = new TextView(context);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.addRule(14);
        this.f1180l.setTextColor(Color.parseColor("#000000"));
        this.f1180l.setTextSize(2, 11.0f);
        this.f1180l.setLayoutParams(layoutParams);
        this.f1178j.addView(this.f1180l);
        this.f1181m = new ImageView(context);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.addRule(14);
        layoutParams.addRule(3, this.f1179k.getId());
        this.f1181m.setLayoutParams(layoutParams);
        Bitmap loadAssetsFile = AssetsLoadUtil.loadAssetsFile("icon_scale.9.png", context);
        byte[] ninePatchChunk = loadAssetsFile.getNinePatchChunk();
        NinePatch.isNinePatchChunk(ninePatchChunk);
        this.f1181m.setBackgroundDrawable(new NinePatchDrawable(loadAssetsFile, ninePatchChunk, new Rect(), null));
        this.f1178j.addView(this.f1181m);
        addView(this.f1178j);
    }

    public static void setCustomMapStylePath(String str) {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("customMapStylePath String is illegal");
        } else if (new File(str).exists()) {
            f1169b = str;
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

    public final LogoPosition getLogoPosition() {
        switch (this.f1182o) {
            case 1:
                return LogoPosition.logoPostionleftTop;
            case 2:
                return LogoPosition.logoPostionCenterBottom;
            case 3:
                return LogoPosition.logoPostionCenterTop;
            case 4:
                return LogoPosition.logoPostionRightBottom;
            case 5:
                return LogoPosition.logoPostionRightTop;
            default:
                return LogoPosition.logoPostionleftBottom;
        }
    }

    public final BaiduMap getMap() {
        this.f1172d.f1016a = this;
        return this.f1172d;
    }

    public final int getMapLevel() {
        return ((Integer) f1170n.get((int) this.f1171c.m2046a().m1953D().f1963a)).intValue();
    }

    public int getScaleControlViewHeight() {
        return this.f1191x;
    }

    public int getScaleControlViewWidth() {
        return this.f1192y;
    }

    public void onCreate(Context context, Bundle bundle) {
        if (bundle != null) {
            f1169b = bundle.getString("customMapPath");
            if (bundle == null) {
                m1155a(context, new BaiduMapOptions());
                return;
            }
            MapStatus mapStatus = (MapStatus) bundle.getParcelable("mapstatus");
            if (this.f1176h != null) {
                this.f1176h = (Point) bundle.getParcelable("scalePosition");
            }
            if (this.f1177i != null) {
                this.f1177i = (Point) bundle.getParcelable("zoomPosition");
            }
            this.f1183p = bundle.getBoolean("mZoomControlEnabled");
            this.f1184q = bundle.getBoolean("mScaleControlEnabled");
            this.f1182o = bundle.getInt("logoPosition");
            setPadding(bundle.getInt("paddingLeft"), bundle.getInt("paddingTop"), bundle.getInt("paddingRight"), bundle.getInt("paddingBottom"));
            m1155a(context, new BaiduMapOptions().mapStatus(mapStatus));
        }
    }

    public final void onDestroy() {
        this.f1171c.m2049b();
        if (!(this.f1174f == null || this.f1174f.isRecycled())) {
            this.f1174f.recycle();
            this.f1174f = null;
        }
        this.f1175g.m1939b();
        BMapManager.destroy();
    }

    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        float f2;
        int childCount = getChildCount();
        m1157a(this.f1173e);
        if (((getWidth() - this.f1187t) - this.f1188u) - this.f1173e.getMeasuredWidth() <= 0 || ((getHeight() - this.f1189v) - this.f1190w) - this.f1173e.getMeasuredHeight() <= 0) {
            this.f1187t = 0;
            this.f1188u = 0;
            this.f1190w = 0;
            this.f1189v = 0;
            f = 1.0f;
            f2 = 1.0f;
        } else {
            f = ((float) ((getWidth() - this.f1187t) - this.f1188u)) / ((float) getWidth());
            f2 = ((float) ((getHeight() - this.f1189v) - this.f1190w)) / ((float) getHeight());
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt != null) {
                if (childAt == this.f1171c) {
                    this.f1171c.layout(0, 0, getWidth(), getHeight());
                } else if (childAt == this.f1173e) {
                    r3 = (int) (((float) this.f1187t) + (5.0f * f));
                    r0 = (int) (((float) this.f1188u) + (5.0f * f));
                    r4 = (int) (((float) this.f1189v) + (5.0f * f2));
                    r5 = (int) (((float) this.f1190w) + (5.0f * f2));
                    switch (this.f1182o) {
                        case 1:
                            r5 = r4 + this.f1173e.getMeasuredHeight();
                            r0 = this.f1173e.getMeasuredWidth() + r3;
                            break;
                        case 2:
                            r5 = getHeight() - r5;
                            r4 = r5 - this.f1173e.getMeasuredHeight();
                            r3 = (((getWidth() - this.f1173e.getMeasuredWidth()) + this.f1187t) - this.f1188u) / 2;
                            r0 = (((getWidth() + this.f1173e.getMeasuredWidth()) + this.f1187t) - this.f1188u) / 2;
                            break;
                        case 3:
                            r5 = r4 + this.f1173e.getMeasuredHeight();
                            r3 = (((getWidth() - this.f1173e.getMeasuredWidth()) + this.f1187t) - this.f1188u) / 2;
                            r0 = (((getWidth() + this.f1173e.getMeasuredWidth()) + this.f1187t) - this.f1188u) / 2;
                            break;
                        case 4:
                            r5 = getHeight() - r5;
                            r4 = r5 - this.f1173e.getMeasuredHeight();
                            r0 = getWidth() - r0;
                            r3 = r0 - this.f1173e.getMeasuredWidth();
                            break;
                        case 5:
                            r5 = r4 + this.f1173e.getMeasuredHeight();
                            r0 = getWidth() - r0;
                            r3 = r0 - this.f1173e.getMeasuredWidth();
                            break;
                        default:
                            r5 = getHeight() - r5;
                            r0 = this.f1173e.getMeasuredWidth() + r3;
                            r4 = r5 - this.f1173e.getMeasuredHeight();
                            break;
                    }
                    this.f1173e.layout(r3, r4, r0, r5);
                } else if (childAt == this.f1175g) {
                    if (this.f1175g.m1938a()) {
                        m1157a(this.f1175g);
                        if (this.f1177i == null) {
                            r3 = (int) ((((float) (getHeight() - 15)) * f2) + ((float) this.f1189v));
                            r4 = (int) ((((float) (getWidth() - 15)) * f) + ((float) this.f1187t));
                            r5 = r4 - this.f1175g.getMeasuredWidth();
                            r0 = r3 - this.f1175g.getMeasuredHeight();
                            if (this.f1182o == 4) {
                                r3 -= this.f1173e.getMeasuredHeight();
                                r0 -= this.f1173e.getMeasuredHeight();
                            }
                            this.f1175g.layout(r5, r0, r4, r3);
                        } else {
                            this.f1175g.layout(this.f1177i.x, this.f1177i.y, this.f1177i.x + this.f1175g.getMeasuredWidth(), this.f1177i.y + this.f1175g.getMeasuredHeight());
                        }
                    }
                } else if (childAt == this.f1178j) {
                    m1157a(this.f1178j);
                    if (this.f1176h == null) {
                        r0 = (int) ((((float) this.f1190w) + (5.0f * f2)) + 56.0f);
                        this.f1192y = this.f1178j.getMeasuredWidth();
                        this.f1191x = this.f1178j.getMeasuredHeight();
                        r3 = (int) (((float) this.f1187t) + (5.0f * f));
                        r0 = (getHeight() - r0) - this.f1173e.getMeasuredHeight();
                        this.f1178j.layout(r3, r0, this.f1192y + r3, this.f1191x + r0);
                    } else {
                        this.f1178j.layout(this.f1176h.x, this.f1176h.y, this.f1176h.x + this.f1178j.getMeasuredWidth(), this.f1176h.y + this.f1178j.getMeasuredHeight());
                    }
                } else {
                    LayoutParams layoutParams = childAt.getLayoutParams();
                    if (layoutParams == null) {
                        Log.e("test", "lp == null");
                    }
                    if (layoutParams instanceof MapViewLayoutParams) {
                        Point point;
                        MapViewLayoutParams mapViewLayoutParams = (MapViewLayoutParams) layoutParams;
                        if (mapViewLayoutParams.f1203c == ELayoutMode.absoluteMode) {
                            point = mapViewLayoutParams.f1202b;
                        } else {
                            point = this.f1171c.m2046a().m1966a(CoordUtil.ll2mc(mapViewLayoutParams.f1201a));
                        }
                        m1157a(childAt);
                        r5 = childAt.getMeasuredWidth();
                        int measuredHeight = childAt.getMeasuredHeight();
                        float f3 = mapViewLayoutParams.f1204d;
                        int i6 = (int) (((float) point.x) - (f3 * ((float) r5)));
                        r0 = mapViewLayoutParams.f1206f + ((int) (((float) point.y) - (mapViewLayoutParams.f1205e * ((float) measuredHeight))));
                        childAt.layout(i6, r0, i6 + r5, r0 + measuredHeight);
                    }
                }
            }
        }
    }

    public final void onPause() {
        this.f1171c.onPause();
    }

    public final void onResume() {
        this.f1171c.onResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null && this.f1172d != null) {
            bundle.putParcelable("mapstatus", this.f1172d.getMapStatus());
            if (this.f1176h != null) {
                bundle.putParcelable("scalePosition", this.f1176h);
            }
            if (this.f1177i != null) {
                bundle.putParcelable("zoomPosition", this.f1177i);
            }
            bundle.putBoolean("mZoomControlEnabled", this.f1183p);
            bundle.putBoolean("mScaleControlEnabled", this.f1184q);
            bundle.putInt("logoPosition", this.f1182o);
            bundle.putInt("paddingLeft", this.f1187t);
            bundle.putInt("paddingTop", this.f1189v);
            bundle.putInt("paddingRight", this.f1188u);
            bundle.putInt("paddingBottom", this.f1190w);
            bundle.putString("customMapPath", f1169b);
        }
    }

    public void removeView(View view) {
        if (view != this.f1173e) {
            super.removeView(view);
        }
    }

    public final void setLogoPosition(LogoPosition logoPosition) {
        if (logoPosition == null) {
            this.f1182o = LogoPosition.logoPostionleftBottom.ordinal();
        }
        this.f1182o = logoPosition.ordinal();
        requestLayout();
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.f1187t = i;
        this.f1189v = i2;
        this.f1188u = i3;
        this.f1190w = i4;
    }

    public void setScaleControlPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.f1176h = point;
            requestLayout();
        }
    }

    public final void setZOrderMediaOverlay(boolean z) {
        if (this.f1171c != null) {
            this.f1171c.setZOrderMediaOverlay(z);
        }
    }

    public void setZoomControlsPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.f1177i = point;
            requestLayout();
        }
    }

    public void showScaleControl(boolean z) {
        this.f1178j.setVisibility(z ? 0 : 8);
        this.f1184q = z;
    }

    public void showZoomControls(boolean z) {
        if (this.f1175g.m1938a()) {
            this.f1175g.setVisibility(z ? 0 : 8);
            this.f1183p = z;
        }
    }
}
