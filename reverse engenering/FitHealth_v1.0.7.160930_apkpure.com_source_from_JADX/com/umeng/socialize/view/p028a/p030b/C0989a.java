package com.umeng.socialize.view.p028a.p030b;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.List;

/* compiled from: CustomView */
public class C0989a extends View {
    private int f3387a;
    private List<Bitmap> f3388b;
    private RectF f3389c;
    private int f3390d = 0;
    private Handler f3391e = new C0988a(this);

    /* compiled from: CustomView */
    private static class C0988a extends Handler {
        WeakReference<C0989a> f3386a;

        public C0988a(C0989a c0989a) {
            this.f3386a = new WeakReference(c0989a);
        }

        public void handleMessage(Message message) {
            C0989a c0989a = (C0989a) this.f3386a.get();
            if (c0989a != null) {
                c0989a.invalidate();
            }
        }
    }

    public C0989a(Context context, int i, List<Bitmap> list) {
        super(context);
        this.f3387a = i;
        this.f3389c = new RectF(0.0f, 0.0f, (float) i, (float) i);
        this.f3388b = list;
    }

    public void m3286a(int i) {
        this.f3390d = i;
        this.f3391e.sendEmptyMessage(0);
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(this.f3387a, this.f3387a);
    }

    protected void onDraw(Canvas canvas) {
        Log.d("23232", "draw");
        canvas.drawBitmap((Bitmap) this.f3388b.get(this.f3390d), null, this.f3389c, null);
    }
}
