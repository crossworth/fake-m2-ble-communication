package com.zhuoyou.plugin.album;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {
    private static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (width > height) {
            if (width / height > 3) {
                return 1;
            }
        } else if (height / width > 3) {
            return 1;
        }
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = (int) Math.ceil((double) (height / reqHeight));
            if (height % reqHeight > 0) {
                heightRatio++;
            }
            inSampleSize = heightRatio;
        }
        return inSampleSize;
    }

    private static int scaleSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        int widthRatio = (int) Math.ceil((double) (width / reqWidth));
        int heightRatio = (int) Math.ceil((double) (height / reqHeight));
        if (widthRatio <= heightRatio) {
            return widthRatio;
        }
        return heightRatio;
    }

    private static int scaleSampleSize3(Options options, int reqWidth) {
        int width = options.outWidth;
        if (width > reqWidth) {
            return (int) Math.ceil((double) (width / reqWidth));
        }
        return 1;
    }

    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight) {
        Bitmap dst = null;
        if (src != null) {
            dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
            if (src != dst) {
                src.recycle();
                System.gc();
            }
        }
        return dst;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return createScaleBitmap(BitmapFactory.decodeResource(res, resId, options), reqWidth, reqHeight);
    }

    public static Bitmap decodeSampledBitmapFromFd(String pathName, int reqWidth, int type) {
        Options options = new Options();
        options.inPreferredConfig = Config.RGB_565;
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        BitmapFactory.decodeFile(pathName, options);
        if (options.outHeight <= 0 || options.outWidth <= 0 || options.outHeight <= 0 || options.outWidth <= 0) {
            return null;
        }
        options.inSampleSize = scaleSampleSize3(options, reqWidth);
        options.inJustDecodeBounds = false;
        return compressImage(BitmapFactory.decodeFile(pathName, options), 30, Tools.getSDPath() + "/Running/.albumthumbnail/", pathName);
    }

    public static Bitmap decodeSampledBitmapFromFd2(String pathName, int reqWidth, int reqHeight, int type) {
        Options options = new Options();
        options.inPreferredConfig = Config.RGB_565;
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        BitmapFactory.decodeFile(pathName, options);
        if (options.outHeight <= 0 || options.outWidth <= 0) {
            return null;
        }
        options.inSampleSize = scaleSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);
        if (bitmap.getWidth() < reqWidth || bitmap.getHeight() < reqHeight) {
            float width_scal = ((float) reqWidth) / ((float) bitmap.getWidth());
            float height_scal = ((float) reqHeight) / ((float) bitmap.getHeight());
            if (width_scal >= height_scal) {
                bitmap = createScaleBitmap(bitmap, reqWidth, (int) (((float) bitmap.getHeight()) * width_scal));
            } else {
                bitmap = createScaleBitmap(bitmap, (int) (((float) bitmap.getWidth()) * height_scal), reqHeight);
            }
        }
        Matrix matrix = new Matrix();
        matrix.postRotate((float) readPictureDegree(pathName));
        bitmap = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - reqWidth) / 2, (bitmap.getHeight() - reqHeight) / 2, reqWidth, reqHeight, matrix, true);
        if (type == 1) {
            return compressImage(bitmap, 100, Tools.getSDPath() + "/Running/.thumbnailnew/", pathName);
        }
        if (type == 3) {
            return compressImage(bitmap, 30, Tools.getSDPath() + "/Running/.albumthumbnail/", pathName);
        }
        return compressImage(bitmap, 40, pathName);
    }

    public static Bitmap getBitmapFromUrl(String url) {
        Options options = new Options();
        options.inPreferredConfig = Config.RGB_565;
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        BitmapFactory.decodeFile(url, options);
        int wRatio = (int) Math.ceil((double) (options.outWidth / ScreenHelper.getScreenWidth(RunningApp.getInstance().getApplicationContext())));
        int hRatio = (int) Math.ceil((double) (options.outHeight / ScreenHelper.getScreenHeight(RunningApp.getInstance().getApplicationContext())));
        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                options.inSampleSize = wRatio;
            } else {
                options.inSampleSize = hRatio;
            }
        }
        options.inJustDecodeBounds = false;
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(url, options);
            Matrix matrix = new Matrix();
            matrix.postRotate((float) readPictureDegree(url));
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            System.gc();
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getScaleBitmap(String url) {
        Bitmap bitmap = null;
        try {
            FileInputStream fs = new FileInputStream(url);
            byte[] buffer = new byte[fs.available()];
            fs.read(buffer);
            ByteArrayInputStream isBm = new ByteArrayInputStream(buffer);
            Options options2 = new Options();
            options2.inPreferredConfig = Config.RGB_565;
            bitmap = BitmapFactory.decodeStream(isBm, null, options2);
            fs.close();
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return bitmap;
        } catch (IOException e2) {
            e2.printStackTrace();
            return bitmap;
        }
    }

    private static Bitmap compressImage(Bitmap image, int pSize, String flieUrl, String url) {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 90;
        image.compress(CompressFormat.JPEG, 90, baos);
        while (baos.toByteArray().length / 1024 > pSize && options > 0) {
            baos.reset();
            image.compress(CompressFormat.JPEG, options, baos);
            options -= 20;
        }
        if (image != null) {
            image.recycle();
            System.gc();
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Options options2 = new Options();
        options2.inPreferredConfig = Config.RGB_565;
        image = BitmapFactory.decodeStream(isBm, null, options2);
        saveBitmap(baos, flieUrl, url);
        return image;
    }

    public static Bitmap compressImage(Bitmap image, int pSize, String url) {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 90;
        image.compress(CompressFormat.JPEG, 90, baos);
        while (baos.toByteArray().length / 1024 > pSize && options > 0) {
            baos.reset();
            image.compress(CompressFormat.JPEG, options, baos);
            options -= 20;
        }
        if (image != null) {
            image.recycle();
            System.gc();
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Options options2 = new Options();
        options2.inPreferredConfig = Config.RGB_565;
        return BitmapFactory.decodeStream(isBm, null, options2);
    }

    public static void saveBitmap(ByteArrayOutputStream baos, String flieUrl, String url) {
        File fd = new File(flieUrl);
        if (!fd.exists()) {
            fd.mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(new File(flieUrl, url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."))));
            out.write(baos.toByteArray());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
        Bitmap squareBitmap;
        Bitmap scaledSrcBmp;
        int diameter = radius;
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        Bitmap bitmap;
        int i;
        if (bmpHeight > bmpWidth) {
            bitmap = bmp;
            i = (bmpHeight - bmpWidth) / 2;
            squareBitmap = Bitmap.createBitmap(bitmap, 0, i, bmpWidth, bmpWidth);
        } else if (bmpHeight < bmpWidth) {
            int x = (bmpWidth - bmpHeight) / 2;
            bitmap = bmp;
            i = 0;
            squareBitmap = Bitmap.createBitmap(bitmap, x, i, bmpHeight, bmpHeight);
        } else {
            squareBitmap = bmp;
        }
        if (squareBitmap.getWidth() == diameter && squareBitmap.getHeight() == diameter) {
            scaledSrcBmp = squareBitmap;
        } else {
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);
        }
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(1, 1, scaledSrcBmp.getWidth() - 1, scaledSrcBmp.getHeight() - 1);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle((float) (scaledSrcBmp.getWidth() / 2), (float) (scaledSrcBmp.getHeight() / 2), (float) ((scaledSrcBmp.getWidth() / 2) - 1), paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
        return output;
    }

    public static int readPictureDegree(String path) {
        try {
            int orientation = new ExifInterface(path).getAttributeInt("Orientation", 1);
            System.out.println("orientation=" + orientation);
            switch (orientation) {
                case 3:
                    return 180;
                case 6:
                    return 90;
                case 8:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
