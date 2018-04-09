package com.baidu.mapapi.map;

import android.annotation.SuppressLint;
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
import com.baidu.platform.comapi.map.C0618E;
import com.baidu.platform.comapi.map.C0628N;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.zhuoyou.plugin.running.baas.Rank;
import java.io.File;
import org.andengine.util.adt.DataConstants;
import org.andengine.util.time.TimeConstants;

public final class TextureMapView extends ViewGroup {
    private static final String f1326a = TextureMapView.class.getSimpleName();
    private static String f1327i;
    private static final SparseArray<Integer> f1328n = new SparseArray();
    private C0618E f1329b;
    private BaiduMap f1330c;
    private ImageView f1331d;
    private Bitmap f1332e;
    private C0628N f1333f;
    private Point f1334g;
    private Point f1335h;
    private RelativeLayout f1336j;
    private TextView f1337k;
    private TextView f1338l;
    private ImageView f1339m;
    private float f1340o;
    private C0482k f1341p;
    private int f1342q = LogoPosition.logoPostionleftBottom.ordinal();
    private boolean f1343r = true;
    private boolean f1344s = true;
    private int f1345t;
    private int f1346u;
    private int f1347v;
    private int f1348w;
    private int f1349x;
    private int f1350y;

    static {
        f1328n.append(3, Integer.valueOf(2000000));
        f1328n.append(4, Integer.valueOf(TimeConstants.MICROSECONDS_PER_SECOND));
        f1328n.append(5, Integer.valueOf(500000));
        f1328n.append(6, Integer.valueOf(200000));
        f1328n.append(7, Integer.valueOf(Rank.MIN));
        f1328n.append(8, Integer.valueOf(50000));
        f1328n.append(9, Integer.valueOf(25000));
        f1328n.append(10, Integer.valueOf(BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT));
        f1328n.append(11, Integer.valueOf(10000));
        f1328n.append(12, Integer.valueOf(5000));
        f1328n.append(13, Integer.valueOf(2000));
        f1328n.append(14, Integer.valueOf(1000));
        f1328n.append(15, Integer.valueOf(500));
        f1328n.append(16, Integer.valueOf(200));
        f1328n.append(17, Integer.valueOf(100));
        f1328n.append(18, Integer.valueOf(50));
        f1328n.append(19, Integer.valueOf(20));
        f1328n.append(20, Integer.valueOf(10));
        f1328n.append(21, Integer.valueOf(5));
        f1328n.append(22, Integer.valueOf(2));
    }

    public TextureMapView(Context context) {
        super(context);
        m1195a(context, null);
    }

    public TextureMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m1195a(context, null);
    }

    public TextureMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m1195a(context, null);
    }

    public TextureMapView(Context context, BaiduMapOptions baiduMapOptions) {
        super(context);
        m1195a(context, baiduMapOptions);
    }

    private void m1194a(Context context) {
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
            this.f1332e = Bitmap.createBitmap(loadAssetsFile, 0, 0, loadAssetsFile.getWidth(), loadAssetsFile.getHeight(), matrix, true);
        } else if (densityDpi <= 320 || densityDpi > 480) {
            this.f1332e = loadAssetsFile;
        } else {
            matrix = new Matrix();
            matrix.postScale(1.5f, 1.5f);
            this.f1332e = Bitmap.createBitmap(loadAssetsFile, 0, 0, loadAssetsFile.getWidth(), loadAssetsFile.getHeight(), matrix, true);
        }
        if (this.f1332e != null) {
            this.f1331d = new ImageView(context);
            this.f1331d.setImageBitmap(this.f1332e);
            addView(this.f1331d);
        }
    }

    private void m1195a(Context context, BaiduMapOptions baiduMapOptions) {
        setBackgroundColor(-1);
        BMapManager.init();
        m1196a(context, baiduMapOptions, f1327i);
        this.f1330c = new BaiduMap(this.f1329b);
        m1194a(context);
        m1200b(context);
        if (!(baiduMapOptions == null || baiduMapOptions.f1048h)) {
            this.f1333f.setVisibility(4);
        }
        m1202c(context);
        if (!(baiduMapOptions == null || baiduMapOptions.f1049i)) {
            this.f1336j.setVisibility(4);
        }
        if (!(baiduMapOptions == null || baiduMapOptions.f1050j == null)) {
            this.f1342q = baiduMapOptions.f1050j.ordinal();
        }
        if (!(baiduMapOptions == null || baiduMapOptions.f1052l == null)) {
            this.f1335h = baiduMapOptions.f1052l;
        }
        if (baiduMapOptions != null && baiduMapOptions.f1051k != null) {
            this.f1334g = baiduMapOptions.f1051k;
        }
    }

    private void m1196a(Context context, BaiduMapOptions baiduMapOptions, String str) {
        f1327i = str;
        if (baiduMapOptions == null) {
            this.f1329b = new C0618E(context, null, str);
        } else {
            this.f1329b = new C0618E(context, baiduMapOptions.m1102a(), str);
        }
        addView(this.f1329b);
        this.f1341p = new C0502q(this);
        this.f1329b.m1915b().m1978a(this.f1341p);
    }

    private void m1197a(View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-2, -2);
        }
        int i = layoutParams.width;
        i = i > 0 ? MeasureSpec.makeMeasureSpec(i, DataConstants.BYTES_PER_GIGABYTE) : MeasureSpec.makeMeasureSpec(0, 0);
        int i2 = layoutParams.height;
        view.measure(i, i2 > 0 ? MeasureSpec.makeMeasureSpec(i2, DataConstants.BYTES_PER_GIGABYTE) : MeasureSpec.makeMeasureSpec(0, 0));
    }

    private void m1199b() {
        boolean z = false;
        float f = this.f1329b.m1915b().m1953D().f1963a;
        if (this.f1333f.m1938a()) {
            this.f1333f.m1941b(f > this.f1329b.m1915b().f2048b);
            C0628N c0628n = this.f1333f;
            if (f < this.f1329b.m1915b().f2047a) {
                z = true;
            }
            c0628n.m1937a(z);
        }
    }

    private void m1200b(Context context) {
        this.f1333f = new C0628N(context);
        if (this.f1333f.m1938a()) {
            this.f1333f.m1940b(new C0503r(this));
            this.f1333f.m1936a(new C0504s(this));
            addView(this.f1333f);
        }
    }

    private void m1202c(Context context) {
        this.f1336j = new RelativeLayout(context);
        this.f1336j.setLayoutParams(new LayoutParams(-2, -2));
        this.f1337k = new TextView(context);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        this.f1337k.setTextColor(Color.parseColor("#FFFFFF"));
        this.f1337k.setTextSize(2, 11.0f);
        this.f1337k.setTypeface(this.f1337k.getTypeface(), 1);
        this.f1337k.setLayoutParams(layoutParams);
        this.f1337k.setId(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        this.f1336j.addView(this.f1337k);
        this.f1338l = new TextView(context);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.addRule(14);
        this.f1338l.setTextColor(Color.parseColor("#000000"));
        this.f1338l.setTextSize(2, 11.0f);
        this.f1338l.setLayoutParams(layoutParams);
        this.f1336j.addView(this.f1338l);
        this.f1339m = new ImageView(context);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.addRule(14);
        layoutParams.addRule(3, this.f1337k.getId());
        this.f1339m.setLayoutParams(layoutParams);
        Bitmap loadAssetsFile = AssetsLoadUtil.loadAssetsFile("icon_scale.9.png", context);
        byte[] ninePatchChunk = loadAssetsFile.getNinePatchChunk();
        NinePatch.isNinePatchChunk(ninePatchChunk);
        this.f1339m.setBackgroundDrawable(new NinePatchDrawable(loadAssetsFile, ninePatchChunk, new Rect(), null));
        this.f1336j.addView(this.f1339m);
        addView(this.f1336j);
    }

    public static void setCustomMapStylePath(String str) {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("customMapStylePath String is illegal");
        } else if (new File(str).exists()) {
            f1327i = str;
        } else {
            throw new RuntimeException("please check whether the customMapStylePath file exits");
        }
    }

    public static void setMapCustomEnable(boolean z) {
        C0618E.m1910a(z);
    }

    public void addView(View view, LayoutParams layoutParams) {
        if (layoutParams instanceof MapViewLayoutParams) {
            super.addView(view, layoutParams);
        }
    }

    public final LogoPosition getLogoPosition() {
        switch (this.f1342q) {
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
        this.f1330c.f1017b = this;
        return this.f1330c;
    }

    public final int getMapLevel() {
        return ((Integer) f1328n.get((int) this.f1329b.m1915b().m1953D().f1963a)).intValue();
    }

    public int getScaleControlViewHeight() {
        return this.f1350y;
    }

    public int getScaleControlViewWidth() {
        return this.f1350y;
    }

    public void onCreate(Context context, Bundle bundle) {
        if (bundle != null) {
            f1327i = bundle.getString("customMapPath");
            if (bundle == null) {
                m1195a(context, new BaiduMapOptions());
                return;
            }
            MapStatus mapStatus = (MapStatus) bundle.getParcelable("mapstatus");
            if (this.f1334g != null) {
                this.f1334g = (Point) bundle.getParcelable("scalePosition");
            }
            if (this.f1335h != null) {
                this.f1335h = (Point) bundle.getParcelable("zoomPosition");
            }
            this.f1343r = bundle.getBoolean("mZoomControlEnabled");
            this.f1344s = bundle.getBoolean("mScaleControlEnabled");
            this.f1342q = bundle.getInt("logoPosition");
            setPadding(bundle.getInt("paddingLeft"), bundle.getInt("paddingTop"), bundle.getInt("paddingRight"), bundle.getInt("paddingBottom"));
            m1195a(context, new BaiduMapOptions().mapStatus(mapStatus));
        }
    }

    public final void onDestroy() {
        this.f1329b.m1918e();
        if (!(this.f1332e == null || this.f1332e.isRecycled())) {
            this.f1332e.recycle();
        }
        this.f1333f.m1939b();
        BMapManager.destroy();
    }

    @SuppressLint({"NewApi"})
    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        float f2;
        int childCount = getChildCount();
        m1197a(this.f1331d);
        if (((getWidth() - this.f1345t) - this.f1346u) - this.f1331d.getMeasuredWidth() <= 0 || ((getHeight() - this.f1347v) - this.f1348w) - this.f1331d.getMeasuredHeight() <= 0) {
            this.f1345t = 0;
            this.f1346u = 0;
            this.f1348w = 0;
            this.f1347v = 0;
            f = 1.0f;
            f2 = 1.0f;
        } else {
            f = ((float) ((getWidth() - this.f1345t) - this.f1346u)) / ((float) getWidth());
            f2 = ((float) ((getHeight() - this.f1347v) - this.f1348w)) / ((float) getHeight());
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt == this.f1329b) {
                this.f1329b.layout(0, 0, getWidth(), getHeight());
            } else if (childAt == this.f1331d) {
                r3 = (int) (((float) this.f1345t) + (5.0f * f));
                r0 = (int) (((float) this.f1346u) + (5.0f * f));
                r4 = (int) (((float) this.f1347v) + (5.0f * f2));
                r5 = (int) (((float) this.f1348w) + (5.0f * f2));
                switch (this.f1342q) {
                    case 1:
                        r5 = r4 + this.f1331d.getMeasuredHeight();
                        r0 = this.f1331d.getMeasuredWidth() + r3;
                        break;
                    case 2:
                        r5 = getHeight() - r5;
                        r4 = r5 - this.f1331d.getMeasuredHeight();
                        r3 = (((getWidth() - this.f1331d.getMeasuredWidth()) + this.f1345t) - this.f1346u) / 2;
                        r0 = (((getWidth() + this.f1331d.getMeasuredWidth()) + this.f1345t) - this.f1346u) / 2;
                        break;
                    case 3:
                        r5 = r4 + this.f1331d.getMeasuredHeight();
                        r3 = (((getWidth() - this.f1331d.getMeasuredWidth()) + this.f1345t) - this.f1346u) / 2;
                        r0 = (((getWidth() + this.f1331d.getMeasuredWidth()) + this.f1345t) - this.f1346u) / 2;
                        break;
                    case 4:
                        r5 = getHeight() - r5;
                        r4 = r5 - this.f1331d.getMeasuredHeight();
                        r0 = getWidth() - r0;
                        r3 = r0 - this.f1331d.getMeasuredWidth();
                        break;
                    case 5:
                        r5 = r4 + this.f1331d.getMeasuredHeight();
                        r0 = getWidth() - r0;
                        r3 = r0 - this.f1331d.getMeasuredWidth();
                        break;
                    default:
                        r5 = getHeight() - r5;
                        r0 = this.f1331d.getMeasuredWidth() + r3;
                        r4 = r5 - this.f1331d.getMeasuredHeight();
                        break;
                }
                this.f1331d.layout(r3, r4, r0, r5);
            } else if (childAt == this.f1333f) {
                if (this.f1333f.m1938a()) {
                    m1197a(this.f1333f);
                    if (this.f1335h == null) {
                        r3 = (int) ((((float) (getHeight() - 15)) * f2) + ((float) this.f1347v));
                        r4 = (int) ((((float) (getWidth() - 15)) * f) + ((float) this.f1345t));
                        r5 = r4 - this.f1333f.getMeasuredWidth();
                        r0 = r3 - this.f1333f.getMeasuredHeight();
                        if (this.f1342q == 4) {
                            r3 -= this.f1331d.getMeasuredHeight();
                            r0 -= this.f1331d.getMeasuredHeight();
                        }
                        this.f1333f.layout(r5, r0, r4, r3);
                    } else {
                        this.f1333f.layout(this.f1335h.x, this.f1335h.y, this.f1335h.x + this.f1333f.getMeasuredWidth(), this.f1335h.y + this.f1333f.getMeasuredHeight());
                    }
                }
            } else if (childAt == this.f1336j) {
                m1197a(this.f1336j);
                if (this.f1334g == null) {
                    r0 = (int) ((((float) this.f1348w) + (5.0f * f2)) + 56.0f);
                    this.f1350y = this.f1336j.getMeasuredWidth();
                    this.f1349x = this.f1336j.getMeasuredHeight();
                    r3 = (int) (((float) this.f1345t) + (5.0f * f));
                    r0 = (getHeight() - r0) - this.f1331d.getMeasuredHeight();
                    this.f1336j.layout(r3, r0, this.f1350y + r3, this.f1349x + r0);
                } else {
                    this.f1336j.layout(this.f1334g.x, this.f1334g.y, this.f1334g.x + this.f1336j.getMeasuredWidth(), this.f1334g.y + this.f1336j.getMeasuredHeight());
                }
            } else {
                LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof MapViewLayoutParams) {
                    Point point;
                    MapViewLayoutParams mapViewLayoutParams = (MapViewLayoutParams) layoutParams;
                    if (mapViewLayoutParams.f1203c == ELayoutMode.absoluteMode) {
                        point = mapViewLayoutParams.f1202b;
                    } else {
                        point = this.f1329b.m1915b().m1966a(CoordUtil.ll2mc(mapViewLayoutParams.f1201a));
                    }
                    m1197a(childAt);
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

    public final void onPause() {
        this.f1329b.m1917d();
    }

    public final void onResume() {
        this.f1329b.m1916c();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null && this.f1330c != null) {
            bundle.putParcelable("mapstatus", this.f1330c.getMapStatus());
            if (this.f1334g != null) {
                bundle.putParcelable("scalePosition", this.f1334g);
            }
            if (this.f1335h != null) {
                bundle.putParcelable("zoomPosition", this.f1335h);
            }
            bundle.putBoolean("mZoomControlEnabled", this.f1343r);
            bundle.putBoolean("mScaleControlEnabled", this.f1344s);
            bundle.putInt("logoPosition", this.f1342q);
            bundle.putInt("paddingLeft", this.f1345t);
            bundle.putInt("paddingTop", this.f1347v);
            bundle.putInt("paddingRight", this.f1346u);
            bundle.putInt("paddingBottom", this.f1348w);
            bundle.putString("customMapPath", f1327i);
        }
    }

    public void removeView(View view) {
        if (view != this.f1331d) {
            super.removeView(view);
        }
    }

    public final void setLogoPosition(LogoPosition logoPosition) {
        if (logoPosition == null) {
            this.f1342q = LogoPosition.logoPostionleftBottom.ordinal();
        }
        this.f1342q = logoPosition.ordinal();
        requestLayout();
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.f1345t = i;
        this.f1347v = i2;
        this.f1346u = i3;
        this.f1348w = i4;
    }

    public void setScaleControlPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.f1334g = point;
            requestLayout();
        }
    }

    public void setZoomControlsPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.f1335h = point;
            requestLayout();
        }
    }

    public void showScaleControl(boolean z) {
        this.f1336j.setVisibility(z ? 0 : 8);
        this.f1344s = z;
    }

    public void showZoomControls(boolean z) {
        if (this.f1333f.m1938a()) {
            this.f1333f.setVisibility(z ? 0 : 8);
            this.f1343r = z;
        }
    }
}
