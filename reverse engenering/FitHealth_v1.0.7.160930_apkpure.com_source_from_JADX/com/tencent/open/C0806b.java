package com.tencent.open;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PaintDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.tencent.open.yyb.TitleBar;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ProGuard */
public class C0806b extends Dialog {
    private TextView f2742a;
    private TextView f2743b;
    private Button f2744c;
    private Button f2745d;

    public C0806b(Context context) {
        super(context);
        Drawable colorDrawable = new ColorDrawable();
        colorDrawable.setAlpha(0);
        getWindow().setBackgroundDrawable(colorDrawable);
        setContentView(m2573a(context));
    }

    public C0806b m2575a(String str) {
        this.f2742a.setText(str);
        return this;
    }

    public C0806b m2577b(String str) {
        this.f2743b.setText(str);
        return this;
    }

    public C0806b m2578c(String str) {
        this.f2744c.setText(str);
        return this;
    }

    public C0806b m2579d(String str) {
        this.f2745d.setText(str);
        return this;
    }

    public C0806b m2574a(OnClickListener onClickListener) {
        this.f2745d.setOnClickListener(onClickListener);
        return this;
    }

    public C0806b m2576b(OnClickListener onClickListener) {
        this.f2744c.setOnClickListener(onClickListener);
        return this;
    }

    private View m2573a(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        float f = displayMetrics.density;
        int i = (int) (BitmapDescriptorFactory.HUE_YELLOW * f);
        i = (int) (BitmapDescriptorFactory.HUE_YELLOW * f);
        i = (int) (14.0f * f);
        i = (int) (18.0f * f);
        i = (int) (6.0f * f);
        i = (int) (18.0f * f);
        View relativeLayout = new RelativeLayout(context);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        this.f2742a = new TextView(context);
        this.f2742a.setTextSize(17.0f);
        this.f2742a.setId(10);
        this.f2742a.getPaint().setFakeBoldText(true);
        layoutParams.addRule(14);
        layoutParams.setMargins(0, 20, 0, 0);
        relativeLayout.addView(this.f2742a, layoutParams);
        this.f2743b = new TextView(context);
        this.f2743b.setTextSize(16.0f);
        this.f2743b.setIncludeFontPadding(false);
        layoutParams.setMargins(0, 20, 0, 0);
        this.f2743b.setLines(2);
        this.f2743b.setId(11);
        this.f2743b.setMinWidth((int) (185.0f * f));
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(3, 10);
        relativeLayout.addView(this.f2743b, layoutParams);
        View view = new View(context);
        view.setBackgroundColor(Color.rgb(214, 214, 214));
        view.setId(12);
        LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, 2);
        layoutParams2.addRule(3, 11);
        layoutParams2.setMargins(0, 10, 0, (int) (12.0f * f));
        relativeLayout.addView(view, layoutParams2);
        view = new LinearLayout(context);
        layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams2.addRule(5, 12);
        layoutParams2.addRule(7, 12);
        layoutParams2.addRule(3, 12);
        this.f2744c = new Button(context);
        this.f2744c.setBackgroundDrawable(m2572a("buttonNegt.png", context));
        this.f2744c.setTextColor(Color.rgb(36, 97, 131));
        this.f2744c.setTextSize(18.0f);
        this.f2744c.setId(14);
        LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams3.weight = 1.0f;
        layoutParams3.rightMargin = (int) (14.0f * f);
        layoutParams3.leftMargin = (int) (4.0f * f);
        view.addView(this.f2744c, layoutParams3);
        this.f2745d = new Button(context);
        this.f2745d.setTextSize(18.0f);
        this.f2745d.setTextColor(Color.rgb(255, 255, 255));
        this.f2745d.setBackgroundDrawable(m2572a("buttonPost.png", context));
        layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams3.weight = 1.0f;
        layoutParams3.rightMargin = (int) (4.0f * f);
        view.addView(this.f2745d, layoutParams3);
        relativeLayout.addView(view, layoutParams2);
        layoutParams3 = new FrameLayout.LayoutParams((int) (279.0f * f), (int) (163.0f * f));
        relativeLayout.setPadding((int) (TitleBar.SHAREBTN_RIGHT_MARGIN * f), 0, (int) (TitleBar.SHAREBTN_RIGHT_MARGIN * f), (int) (12.0f * f));
        relativeLayout.setLayoutParams(layoutParams3);
        relativeLayout.setBackgroundColor(Color.rgb(247, 251, 247));
        Drawable paintDrawable = new PaintDrawable(Color.rgb(247, 251, 247));
        paintDrawable.setCornerRadius(f * 5.0f);
        relativeLayout.setBackgroundDrawable(paintDrawable);
        return relativeLayout;
    }

    private Drawable m2572a(String str, Context context) {
        Drawable createFromStream;
        IOException e;
        try {
            InputStream open = context.getApplicationContext().getAssets().open(str);
            if (open == null) {
                return null;
            }
            if (str.endsWith(".9.png")) {
                Bitmap decodeStream = BitmapFactory.decodeStream(open);
                if (decodeStream != null) {
                    return new NinePatchDrawable(decodeStream, decodeStream.getNinePatchChunk(), new Rect(), null);
                }
                return null;
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
}
