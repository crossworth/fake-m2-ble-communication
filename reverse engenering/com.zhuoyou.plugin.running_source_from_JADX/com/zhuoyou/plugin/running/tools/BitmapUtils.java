package com.zhuoyou.plugin.running.tools;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;
import com.zhuoyou.plugin.running.app.TheApp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, baos);
        return baos.toByteArray();
    }

    public static Bitmap getBitmapFromBytes(byte[] data) {
        if (data == null) {
            return null;
        }
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    public static String bytesToBase64(byte[] data) {
        if (data == null) {
            return null;
        }
        return Base64.encodeToString(data, 0);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return Base64.encodeToString(getBytesFromBitmap(bitmap), 0);
    }

    public static String imageToBase64(String imagePath) {
        if (imagePath == null) {
            return null;
        }
        return bitmapToBase64(readBitmap(imagePath));
    }

    private static Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean saveBitmapToFile(String bitName, Bitmap bitmap) {
        if (bitmap == null) {
            return false;
        }
        File file = new File(bitName);
        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Bitmap compressImageFromFile(String srcPath, int width) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(srcPath, options);
        int be = options.outWidth / width;
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeFile(srcPath, options);
    }

    public static Bitmap getBitmapFromUri(Uri uri) {
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(TheApp.getContext().getContentResolver().openInputStream(uri), null, options);
            int picWidth = options.outWidth;
            int picHeight = options.outHeight;
            Point point = DisplayUtils.getScreenMetrics(TheApp.getContext());
            options.inSampleSize = 1;
            if (picWidth > picHeight) {
                if (picWidth > point.x) {
                    options.inSampleSize = picWidth / point.x;
                }
            } else if (picHeight > point.y) {
                options.inSampleSize = picHeight / point.y;
            }
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(TheApp.getContext().getContentResolver().openInputStream(uri), null, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bmp, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    public static Bitmap resizeBitmap(Bitmap bm, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
    }

    public static Bitmap resizeBitmap(Bitmap bm, int w, int h) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) w) / ((float) width);
        float scaleHeight = ((float) h) / ((float) height);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    public static Bitmap reverseBitmap(Bitmap bmp, int flag) {
        float[] floats = null;
        switch (flag) {
            case 0:
                floats = new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
                break;
            case 1:
                floats = new float[]{1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
                break;
        }
        if (floats == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setValues(floats);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    public static void rotateImageView(ImageView view, int degree) {
        view.setImageBitmap(rotateBitmap(((BitmapDrawable) view.getDrawable()).getBitmap(), (float) degree));
    }
}
