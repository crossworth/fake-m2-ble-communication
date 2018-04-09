package com.zhuoyou.plugin.album;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import com.fithealth.running.R;

public class AlbumPopupWindow extends PopupWindow {
    public Bitmap bmp = null;
    private ImageView img;
    private View view;

    class C11381 implements OnClickListener {
        C11381() {
        }

        public void onClick(View v) {
            AlbumPopupWindow.this.dismiss();
        }
    }

    public AlbumPopupWindow(Context context, String url) {
        LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.0f;
        ((Activity) context).getWindow().setAttributes(lp);
        this.view = LayoutInflater.from(context).inflate(R.layout.sports_album_item_display, null);
        this.img = (ImageView) this.view.findViewById(R.id.picture);
        this.bmp = BitmapUtils.getBitmapFromUrl(url);
        this.img.setImageBitmap(this.bmp);
        this.img.setOnClickListener(new C11381());
        setContentView(this.view);
        setWidth(-2);
        setHeight(-2);
        setFocusable(true);
        setBackgroundDrawable(new PaintDrawable());
    }

    public void dismiss() {
        super.dismiss();
        if (this.bmp != null) {
            this.bmp.recycle();
            System.gc();
            this.bmp = null;
        }
    }
}
