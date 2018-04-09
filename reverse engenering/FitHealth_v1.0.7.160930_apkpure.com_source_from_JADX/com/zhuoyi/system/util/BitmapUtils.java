package com.zhuoyi.system.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import java.io.BufferedInputStream;
import java.io.File;
import java.net.URL;

public class BitmapUtils {

    class C10801 implements Runnable {
        private final /* synthetic */ Callback val$callback;
        private final /* synthetic */ ImageView val$iv;
        private final /* synthetic */ String val$showPicUrl;

        C10801(String str, Callback callback, ImageView imageView) {
            this.val$showPicUrl = str;
            this.val$callback = callback;
            this.val$iv = imageView;
        }

        public void run() {
            Bitmap bitmap = BitmapUtils.getBitmapByUrl(this.val$showPicUrl);
            if (bitmap != null) {
                this.val$callback.onImageLoaded(this.val$iv, bitmap);
            }
        }
    }

    public interface Callback {
        void onImageLoaded(ImageView imageView, Bitmap bitmap);
    }

    public static Bitmap getBitmapByUrl(String urlStr) {
        Bitmap bitmap = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(new URL(urlStr).openConnection().getInputStream());
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(((float) w) / ((float) bitmap.getWidth()), ((float) h) / ((float) bitmap.getHeight()));
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        if (drawable == null) {
            return null;
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        matrix.postScale(((float) w) / ((float) width), ((float) h) / ((float) height));
        return new BitmapDrawable(null, Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true));
    }

    public static Drawable zoomDrawable(Drawable drawable, float scale) {
        if (drawable == null) {
            return null;
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return new BitmapDrawable(null, Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true));
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    public static void bind(ImageView iv, String showPicUrl, String promAdImagesPath, String showPicId, Callback callback) {
        File imageFile = new File(new StringBuilder(String.valueOf(promAdImagesPath)).append("/").append(showPicId).toString());
        File imageDirectory = new File(promAdImagesPath);
        if (imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(new StringBuilder(String.valueOf(promAdImagesPath)).append("/").append(showPicId).toString());
            if (bitmap != null) {
                callback.onImageLoaded(iv, bitmap);
                return;
            }
            return;
        }
        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs();
        }
        new Thread(new C10801(showPicUrl, callback, iv)).start();
    }
}
