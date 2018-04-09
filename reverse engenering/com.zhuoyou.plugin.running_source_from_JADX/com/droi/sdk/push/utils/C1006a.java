package com.droi.sdk.push.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import com.droi.sdk.push.C0983e;
import org.andengine.util.adt.DataConstants;

public class C1006a extends RelativeLayout {
    private Context f3350a;
    private ImageView f3351b;
    private Button f3352c;
    private Bitmap f3353d;
    private int f3354e;
    private int f3355f;
    private int f3356g;
    private int f3357h;
    private C0983e f3358i;

    @TargetApi(16)
    public C1006a(Context context, Bitmap bitmap) {
        super(context);
        this.f3350a = context;
        this.f3353d = bitmap;
        if (this.f3353d != null) {
            this.f3356g = this.f3353d.getWidth();
            this.f3357h = this.f3353d.getHeight();
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.f3354e = displayMetrics.widthPixels;
        this.f3355f = displayMetrics.heightPixels;
        this.f3351b = new ImageView(this.f3350a.getApplicationContext());
        this.f3351b.setScaleType(ScaleType.CENTER_CROP);
        this.f3351b.setId(10011);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14, -1);
        layoutParams.addRule(10, -1);
        this.f3351b.setLayoutParams(layoutParams);
        this.f3351b.setImageBitmap(this.f3353d);
        addView(this.f3351b);
        this.f3352c = new Button(this.f3350a.getApplicationContext());
        this.f3352c.setTextColor(-1);
        if (VERSION.SDK_INT >= 16) {
            this.f3352c.setBackground(getBackgroundDrawable());
        } else {
            this.f3352c.setBackgroundDrawable(getBackgroundDrawable());
        }
        this.f3352c.setOnClickListener(new C1007b(this));
        layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(14, -1);
        layoutParams.addRule(3, 10011);
        int b = C1013h.m3144b(this.f3350a, "dp_download_install_text");
        CharSequence charSequence = "Download and install";
        if (b > 0) {
            charSequence = this.f3350a.getString(b);
        }
        this.f3352c.setLayoutParams(layoutParams);
        this.f3352c.setText(charSequence);
        addView(this.f3352c);
    }

    private StateListDrawable getBackgroundDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable colorDrawable = new ColorDrawable(-865486601);
        Drawable colorDrawable2 = new ColorDrawable(-9848585);
        stateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, colorDrawable);
        stateListDrawable.addState(View.ENABLED_FOCUSED_STATE_SET, colorDrawable);
        stateListDrawable.addState(View.FOCUSED_STATE_SET, colorDrawable);
        stateListDrawable.addState(View.ENABLED_STATE_SET, colorDrawable2);
        stateListDrawable.addState(View.EMPTY_STATE_SET, colorDrawable2);
        return stateListDrawable;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.f3353d != null) {
            this.f3353d.recycle();
            this.f3353d = null;
        }
    }

    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        super.onMeasure(i, i2);
        int i5 = this.f3356g;
        i5 = this.f3357h;
        if (((float) this.f3356g) / ((float) this.f3357h) >= ((float) this.f3354e) / ((float) this.f3355f)) {
            i3 = (int) (((float) this.f3354e) * 0.8f);
            i4 = i3;
            i3 = (this.f3357h * i3) / this.f3356g;
        } else {
            i5 = (int) (((float) this.f3355f) * 0.8f);
            i4 = (this.f3354e * i5) / this.f3355f;
            i3 = i5;
        }
        this.f3351b.measure(MeasureSpec.makeMeasureSpec(i4, DataConstants.BYTES_PER_GIGABYTE), MeasureSpec.makeMeasureSpec(i3, DataConstants.BYTES_PER_GIGABYTE));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.f3351b.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = i4;
            layoutParams.height = i3;
        }
        this.f3352c.measure(MeasureSpec.makeMeasureSpec(i4, DataConstants.BYTES_PER_GIGABYTE), MeasureSpec.makeMeasureSpec(this.f3355f - i3, Integer.MIN_VALUE));
        int measuredHeight = this.f3352c.getMeasuredHeight();
        layoutParams = (RelativeLayout.LayoutParams) this.f3352c.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = i4;
            layoutParams.height = measuredHeight;
        }
        setMeasuredDimension(i4, i3 + measuredHeight);
    }

    public void setDownloadListener(C0983e c0983e) {
        this.f3358i = c0983e;
    }
}
