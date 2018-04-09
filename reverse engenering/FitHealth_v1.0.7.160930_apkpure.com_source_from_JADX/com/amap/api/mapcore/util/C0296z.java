package com.amap.api.mapcore.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* compiled from: IndoorFloorSwitchView */
public class C0296z extends ScrollView {
    public static final String f764a = C0296z.class.getSimpleName();
    int f765b = 1;
    private Context f766c;
    private LinearLayout f767d;
    private int f768e = 0;
    private List<String> f769f;
    private int f770g = -1;
    private int f771h;
    private Bitmap f772i = null;
    private int f773j = Color.parseColor("#eeffffff");
    private int f774k = Color.parseColor("#44383838");
    private int f775l = 4;
    private int f776m = 1;
    private int f777n;
    private int f778o;
    private Runnable f779p;
    private int f780q = 50;
    private C0295a f781r;

    /* compiled from: IndoorFloorSwitchView */
    class C02921 implements Runnable {
        final /* synthetic */ C0296z f760a;

        C02921(C0296z c0296z) {
            this.f760a = c0296z;
        }

        public void run() {
            if (this.f760a.f778o - this.f760a.getScrollY() == 0) {
                final int a = this.f760a.f778o % this.f760a.f768e;
                final int a2 = this.f760a.f778o / this.f760a.f768e;
                if (a == 0) {
                    this.f760a.f765b = a2 + this.f760a.f776m;
                    this.f760a.m1057g();
                    return;
                } else if (a > this.f760a.f768e / 2) {
                    this.f760a.post(new Runnable(this) {
                        final /* synthetic */ C02921 f756c;

                        public void run() {
                            this.f756c.f760a.smoothScrollTo(0, (this.f756c.f760a.f778o - a) + this.f756c.f760a.f768e);
                            this.f756c.f760a.f765b = (a2 + this.f756c.f760a.f776m) + 1;
                            this.f756c.f760a.m1057g();
                        }
                    });
                    return;
                } else {
                    this.f760a.post(new Runnable(this) {
                        final /* synthetic */ C02921 f759c;

                        public void run() {
                            this.f759c.f760a.smoothScrollTo(0, this.f759c.f760a.f778o - a);
                            this.f759c.f760a.f765b = a2 + this.f759c.f760a.f776m;
                            this.f759c.f760a.m1057g();
                        }
                    });
                    return;
                }
            }
            this.f760a.f778o = this.f760a.getScrollY();
            this.f760a.postDelayed(this.f760a.f779p, (long) this.f760a.f780q);
        }
    }

    /* compiled from: IndoorFloorSwitchView */
    class C02932 extends Drawable {
        final /* synthetic */ C0296z f761a;

        C02932(C0296z c0296z) {
            this.f761a = c0296z;
        }

        public void draw(Canvas canvas) {
            try {
                m1037a(canvas);
                m1038b(canvas);
                m1039c(canvas);
            } catch (Throwable th) {
            }
        }

        private void m1037a(Canvas canvas) {
            canvas.drawColor(this.f761a.f773j);
        }

        private void m1038b(Canvas canvas) {
            Paint paint = new Paint();
            Rect rect = new Rect();
            Rect rect2 = new Rect();
            rect.left = 0;
            rect.top = 0;
            rect.right = this.f761a.f772i.getWidth() + 0;
            rect.bottom = this.f761a.f772i.getHeight() + 0;
            rect2.left = 0;
            rect2.top = this.f761a.m1055f()[0];
            rect2.right = this.f761a.f771h + 0;
            rect2.bottom = this.f761a.m1055f()[1];
            canvas.drawBitmap(this.f761a.f772i, rect, rect2, paint);
        }

        private void m1039c(Canvas canvas) {
            Paint paint = new Paint();
            Rect clipBounds = canvas.getClipBounds();
            paint.setColor(this.f761a.f774k);
            paint.setStyle(Style.STROKE);
            paint.setStrokeWidth((float) this.f761a.f775l);
            canvas.drawRect(clipBounds, paint);
        }

        public void setAlpha(int i) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        public int getOpacity() {
            return 0;
        }
    }

    /* compiled from: IndoorFloorSwitchView */
    public interface C0295a {
        void mo1487a(int i);
    }

    public C0296z(Context context) {
        super(context);
        m1046a(context);
    }

    public C0296z(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m1046a(context);
    }

    public C0296z(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m1046a(context);
    }

    private void m1046a(Context context) {
        this.f766c = context;
        setVerticalScrollBarEnabled(false);
        try {
            if (this.f772i == null) {
                InputStream open = dh.m547a(context).open("map_indoor_select.png");
                this.f772i = BitmapFactory.decodeStream(open);
                open.close();
            }
        } catch (Throwable th) {
        }
        this.f767d = new LinearLayout(context);
        this.f767d.setOrientation(1);
        addView(this.f767d);
        this.f779p = new C02921(this);
    }

    public void m1063a() {
        this.f778o = getScrollY();
        postDelayed(this.f779p, (long) this.f780q);
    }

    private void m1053e() {
        if (this.f769f != null && this.f769f.size() != 0) {
            this.f767d.removeAllViews();
            this.f777n = (this.f776m * 2) + 1;
            for (int size = this.f769f.size() - 1; size >= 0; size--) {
                this.f767d.addView(m1048b((String) this.f769f.get(size)));
            }
            m1045a(0);
        }
    }

    private TextView m1048b(String str) {
        View textView = new TextView(this.f766c);
        textView.setLayoutParams(new LayoutParams(-1, -2));
        textView.setSingleLine(true);
        textView.setTextSize(2, 16.0f);
        textView.setText(str);
        textView.setGravity(17);
        textView.getPaint().setFakeBoldText(true);
        int a = C0296z.m1041a(this.f766c, 8.0f);
        int a2 = C0296z.m1041a(this.f766c, 6.0f);
        textView.setPadding(a, a2, a, a2);
        if (this.f768e == 0) {
            this.f768e = C0296z.m1042a(textView);
            this.f767d.setLayoutParams(new LayoutParams(-2, this.f768e * this.f777n));
            setLayoutParams(new LinearLayout.LayoutParams(-2, this.f768e * this.f777n));
        }
        return textView;
    }

    private void m1045a(int i) {
        int i2 = (i / this.f768e) + this.f776m;
        int i3 = i % this.f768e;
        int i4 = i / this.f768e;
        if (i3 == 0) {
            i3 = this.f776m + i4;
        } else if (i3 > this.f768e / 2) {
            i3 = (this.f776m + i4) + 1;
        } else {
            i3 = i2;
        }
        int childCount = this.f767d.getChildCount();
        i4 = 0;
        while (i4 < childCount) {
            TextView textView = (TextView) this.f767d.getChildAt(i4);
            if (textView != null) {
                if (i3 == i4) {
                    textView.setTextColor(Color.parseColor("#0288ce"));
                } else {
                    textView.setTextColor(Color.parseColor("#bbbbbb"));
                }
                i4++;
            } else {
                return;
            }
        }
    }

    public void m1067a(String[] strArr) {
        int i;
        if (this.f769f == null) {
            this.f769f = new ArrayList();
        }
        this.f769f.clear();
        for (Object add : strArr) {
            this.f769f.add(add);
        }
        for (i = 0; i < this.f776m; i++) {
            this.f769f.add(0, "");
            this.f769f.add("");
        }
        m1053e();
    }

    public void setBackgroundColor(int i) {
        this.f773j = i;
    }

    public void m1068b() {
        if (this.f772i != null && !this.f772i.isRecycled()) {
            this.f772i.recycle();
            this.f772i = null;
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        if (this.f771h == 0) {
            this.f771h = ((Activity) this.f766c).getWindowManager().getDefaultDisplay().getWidth();
        }
        super.setBackgroundDrawable(new C02932(this));
    }

    private int[] m1055f() {
        if (null != null) {
            return null;
        }
        return new int[]{this.f768e * this.f776m, this.f768e * (this.f776m + 1)};
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.f771h = i;
        setBackgroundDrawable(null);
    }

    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        m1045a(i2);
        if (i2 > i4) {
            this.f770g = 1;
        } else {
            this.f770g = 0;
        }
    }

    private void m1057g() {
        if (this.f781r != null) {
            try {
                this.f781r.mo1487a(m1069c());
            } catch (Throwable th) {
            }
        }
    }

    public void m1065a(String str) {
        if (this.f769f != null && this.f769f.size() != 0) {
            final int size = ((this.f769f.size() - this.f776m) - 1) - this.f769f.indexOf(str);
            this.f765b = this.f776m + size;
            post(new Runnable(this) {
                final /* synthetic */ C0296z f763b;

                public void run() {
                    this.f763b.smoothScrollTo(0, size * this.f763b.f768e);
                }
            });
        }
    }

    public int m1069c() {
        if (this.f769f == null || this.f769f.size() == 0) {
            return 0;
        }
        return Math.min(this.f769f.size() - (this.f776m * 2), Math.max(0, ((this.f769f.size() - 1) - this.f765b) - this.f776m));
    }

    public void fling(int i) {
        super.fling(i / 3);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            m1063a();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void m1064a(C0295a c0295a) {
        this.f781r = c0295a;
    }

    public static int m1041a(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static int m1042a(View view) {
        C0296z.m1049b(view);
        return view.getMeasuredHeight();
    }

    public static void m1049b(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }

    public void m1066a(boolean z) {
        if (z) {
            if (!m1070d()) {
                setVisibility(0);
            }
        } else if (m1070d()) {
            setVisibility(8);
        }
    }

    public boolean m1070d() {
        return getVisibility() == 0;
    }
}
