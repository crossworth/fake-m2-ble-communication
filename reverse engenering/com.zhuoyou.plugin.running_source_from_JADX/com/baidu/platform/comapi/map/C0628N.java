package com.baidu.platform.comapi.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.platform.comapi.AssetsLoadUtil;

public class C0628N extends LinearLayout implements OnTouchListener {
    private ImageView f2005a;
    private ImageView f2006b;
    private Context f2007c;
    private Bitmap f2008d;
    private Bitmap f2009e;
    private Bitmap f2010f;
    private Bitmap f2011g;
    private Bitmap f2012h;
    private Bitmap f2013i;
    private Bitmap f2014j;
    private Bitmap f2015k;
    private int f2016l;
    private boolean f2017m = false;
    private boolean f2018n = false;

    @Deprecated
    public C0628N(Context context) {
        super(context);
        this.f2007c = context;
        m1934c();
        if (this.f2008d != null && this.f2009e != null && this.f2010f != null && this.f2011g != null) {
            this.f2005a = new ImageView(this.f2007c);
            this.f2006b = new ImageView(this.f2007c);
            this.f2005a.setImageBitmap(this.f2008d);
            this.f2006b.setImageBitmap(this.f2010f);
            this.f2016l = m1931a(this.f2010f.getHeight() / 6);
            m1933a(this.f2005a, "main_topbtn_up.9.png");
            m1933a(this.f2006b, "main_bottombtn_up.9.png");
            this.f2005a.setId(0);
            this.f2006b.setId(1);
            this.f2005a.setClickable(true);
            this.f2006b.setClickable(true);
            this.f2005a.setOnTouchListener(this);
            this.f2006b.setOnTouchListener(this);
            setOrientation(1);
            setLayoutParams(new LayoutParams(-2, -2));
            addView(this.f2005a);
            addView(this.f2006b);
            this.f2018n = true;
        }
    }

    public C0628N(Context context, boolean z) {
        super(context);
        this.f2007c = context;
        this.f2017m = z;
        this.f2005a = new ImageView(this.f2007c);
        this.f2006b = new ImageView(this.f2007c);
        if (z) {
            m1935d();
            if (this.f2012h != null && this.f2013i != null && this.f2014j != null && this.f2015k != null) {
                this.f2005a.setLayoutParams(new LayoutParams(-2, -2));
                this.f2006b.setLayoutParams(new LayoutParams(-2, -2));
                this.f2005a.setImageBitmap(this.f2012h);
                this.f2006b.setImageBitmap(this.f2014j);
                setLayoutParams(new LayoutParams(-2, -2));
                setOrientation(0);
            } else {
                return;
            }
        }
        m1934c();
        if (this.f2008d != null && this.f2009e != null && this.f2010f != null && this.f2011g != null) {
            this.f2005a.setImageBitmap(this.f2008d);
            this.f2006b.setImageBitmap(this.f2010f);
            this.f2016l = m1931a(this.f2010f.getHeight() / 6);
            m1933a(this.f2005a, "main_topbtn_up.9.png");
            m1933a(this.f2006b, "main_bottombtn_up.9.png");
            setLayoutParams(new LayoutParams(-2, -2));
            setOrientation(1);
        } else {
            return;
        }
        this.f2005a.setId(0);
        this.f2006b.setId(1);
        this.f2005a.setClickable(true);
        this.f2006b.setClickable(true);
        this.f2005a.setOnTouchListener(this);
        this.f2006b.setOnTouchListener(this);
        addView(this.f2005a);
        addView(this.f2006b);
        this.f2018n = true;
    }

    private int m1931a(int i) {
        return (int) ((this.f2007c.getResources().getDisplayMetrics().density * ((float) i)) + 0.5f);
    }

    private Bitmap m1932a(String str) {
        Matrix matrix = new Matrix();
        int densityDpi = SysOSUtil.getDensityDpi();
        if (densityDpi > 480) {
            matrix.postScale(1.8f, 1.8f);
        } else if (densityDpi <= 320 || densityDpi > 480) {
            matrix.postScale(1.2f, 1.2f);
        } else {
            matrix.postScale(1.5f, 1.5f);
        }
        Bitmap loadAssetsFile = AssetsLoadUtil.loadAssetsFile(str, this.f2007c);
        return Bitmap.createBitmap(loadAssetsFile, 0, 0, loadAssetsFile.getWidth(), loadAssetsFile.getHeight(), matrix, true);
    }

    private void m1933a(View view, String str) {
        Bitmap loadAssetsFile = AssetsLoadUtil.loadAssetsFile(str, this.f2007c);
        byte[] ninePatchChunk = loadAssetsFile.getNinePatchChunk();
        NinePatch.isNinePatchChunk(ninePatchChunk);
        view.setBackgroundDrawable(new NinePatchDrawable(loadAssetsFile, ninePatchChunk, new Rect(), null));
        view.setPadding(this.f2016l, this.f2016l, this.f2016l, this.f2016l);
    }

    private void m1934c() {
        this.f2008d = m1932a("main_icon_zoomin.png");
        this.f2009e = m1932a("main_icon_zoomin_dis.png");
        this.f2010f = m1932a("main_icon_zoomout.png");
        this.f2011g = m1932a("main_icon_zoomout_dis.png");
    }

    private void m1935d() {
        this.f2012h = m1932a("wear_zoom_in.png");
        this.f2013i = m1932a("wear_zoom_in_pressed.png");
        this.f2014j = m1932a("wear_zoon_out.png");
        this.f2015k = m1932a("wear_zoom_out_pressed.png");
    }

    public void m1936a(OnClickListener onClickListener) {
        this.f2005a.setOnClickListener(onClickListener);
    }

    public void m1937a(boolean z) {
        this.f2005a.setEnabled(z);
        if (z) {
            this.f2005a.setImageBitmap(this.f2008d);
        } else {
            this.f2005a.setImageBitmap(this.f2009e);
        }
    }

    public boolean m1938a() {
        return this.f2018n;
    }

    public void m1939b() {
        if (!(this.f2008d == null || this.f2008d.isRecycled())) {
            this.f2008d.recycle();
            this.f2008d = null;
        }
        if (!(this.f2009e == null || this.f2009e.isRecycled())) {
            this.f2009e.recycle();
            this.f2009e = null;
        }
        if (!(this.f2010f == null || this.f2010f.isRecycled())) {
            this.f2010f.recycle();
            this.f2010f = null;
        }
        if (!(this.f2011g == null || this.f2011g.isRecycled())) {
            this.f2011g.recycle();
            this.f2011g = null;
        }
        if (!(this.f2012h == null || this.f2012h.isRecycled())) {
            this.f2012h.recycle();
            this.f2012h = null;
        }
        if (!(this.f2013i == null || this.f2013i.isRecycled())) {
            this.f2013i.recycle();
            this.f2013i = null;
        }
        if (!(this.f2014j == null || this.f2014j.isRecycled())) {
            this.f2014j.recycle();
            this.f2014j = null;
        }
        if (this.f2015k != null && !this.f2015k.isRecycled()) {
            this.f2015k.recycle();
            this.f2015k = null;
        }
    }

    public void m1940b(OnClickListener onClickListener) {
        this.f2006b.setOnClickListener(onClickListener);
    }

    public void m1941b(boolean z) {
        this.f2006b.setEnabled(z);
        if (z) {
            this.f2006b.setImageBitmap(this.f2010f);
        } else {
            this.f2006b.setImageBitmap(this.f2011g);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view instanceof ImageView) {
            switch (((ImageView) view).getId()) {
                case 0:
                    if (motionEvent.getAction() != 0) {
                        if (motionEvent.getAction() == 1) {
                            if (!this.f2017m) {
                                m1933a(this.f2005a, "main_topbtn_up.9.png");
                                break;
                            }
                            this.f2005a.setImageBitmap(this.f2012h);
                            break;
                        }
                    } else if (!this.f2017m) {
                        m1933a(this.f2005a, "main_topbtn_down.9.png");
                        break;
                    } else {
                        this.f2005a.setImageBitmap(this.f2013i);
                        break;
                    }
                    break;
                case 1:
                    if (motionEvent.getAction() != 0) {
                        if (motionEvent.getAction() == 1) {
                            if (!this.f2017m) {
                                m1933a(this.f2006b, "main_bottombtn_up.9.png");
                                break;
                            }
                            this.f2006b.setImageBitmap(this.f2014j);
                            break;
                        }
                    } else if (!this.f2017m) {
                        m1933a(this.f2006b, "main_bottombtn_down.9.png");
                        break;
                    } else {
                        this.f2006b.setImageBitmap(this.f2015k);
                        break;
                    }
                    break;
            }
        }
        return false;
    }
}
