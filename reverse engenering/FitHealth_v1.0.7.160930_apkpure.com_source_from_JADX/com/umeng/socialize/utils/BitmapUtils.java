package com.umeng.socialize.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.amap.api.maps.model.WeightedLatLng;
import com.umeng.socialize.Config;
import com.umeng.socialize.net.utils.AesHelper;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;

public class BitmapUtils {
    private static final int CACHE_SIZE = 10;
    public static final int COMPRESS_FLAG = 3145728;
    public static final String FOLDER = "umeng_cache";
    private static final int FREE_SD_SPACE = 40;
    public static int MAX_HEIGHT = 1024;
    public static int MAX_WIDTH = 768;
    private static final int MB = 1048576;
    public static String PATH = "/mnt/sdcard/";
    private static final String TAG = "BitmapUtils";

    private static class FileLastModifSort implements Comparator<File> {
        private FileLastModifSort() {
        }

        public int compare(File file, File file2) {
            if (file.lastModified() > file2.lastModified()) {
                return 1;
            }
            if (file.lastModified() == file2.lastModified()) {
                return 0;
            }
            return -1;
        }
    }

    static {
        init();
    }

    public static void init() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + FOLDER + File.separator;
        } else {
            PATH = Environment.getDataDirectory().getPath() + File.separator + FOLDER + File.separator;
        }
        File file = new File(PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            remove40PercentCache(PATH);
        } catch (Exception e) {
            Log.m3248d(TAG, "清除缓存抛出异常 ： " + e.toString());
        }
        System.gc();
    }

    private static Options getScaleBitmapOptions(String str, int i, int i2) {
        Options options = null;
        Log.m3248d("bitmapOptions", str);
        InputStream bitmapStream = getBitmapStream(str);
        if (bitmapStream != null) {
            options = new Options();
            options.inJustDecodeBounds = true;
            try {
                BitmapFactory.decodeStream(bitmapStream, null, options);
                int ceil = (int) Math.ceil((double) (options.outHeight / i2));
                int ceil2 = (int) Math.ceil((double) (options.outWidth / i));
                if (ceil > 1 && ceil2 > 1) {
                    if (ceil > ceil2) {
                        options.inSampleSize = ceil;
                    } else {
                        options.inSampleSize = ceil2;
                    }
                }
                options.inJustDecodeBounds = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            closeInputStream(bitmapStream);
        }
        return options;
    }

    public static InputStream getBitmapStream(String str) {
        InputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(new File(getFileName(str)));
        } catch (Exception e) {
            try {
                e.printStackTrace();
                fileInputStream = null;
            } catch (Exception e2) {
                Exception exception = e2;
                fileInputStream = null;
                Exception exception2 = exception;
                Log.m3251e("BitmapUtil", "读取图片流出错" + exception2.toString());
                return fileInputStream;
            }
        }
        if (fileInputStream != null) {
            try {
                if (fileInputStream.available() > 0) {
                    return fileInputStream;
                }
            } catch (Exception e3) {
                exception2 = e3;
                Log.m3251e("BitmapUtil", "读取图片流出错" + exception2.toString());
                return fileInputStream;
            }
        }
        InputStream openStream = new URL(str).openStream();
        try {
            saveInputStream(getFileName(str), openStream);
            return new FileInputStream(new File(getFileName(str)));
        } catch (Exception e22) {
            exception = e22;
            fileInputStream = openStream;
            exception2 = exception;
        }
    }

    private static void saveInputStream(String str, InputStream inputStream) {
        FileOutputStream fileOutputStream;
        FileNotFoundException e;
        Throwable th;
        IOException e2;
        try {
            fileOutputStream = new FileOutputStream(new File(str));
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.flush();
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e3) {
                    }
                }
            } catch (FileNotFoundException e4) {
                e = e4;
                try {
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e5) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e6) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e7) {
                e2 = e7;
                e2.printStackTrace();
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e8) {
                    }
                }
            }
        } catch (FileNotFoundException e9) {
            e = e9;
            fileOutputStream = null;
            e.printStackTrace();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException e10) {
            e2 = e10;
            fileOutputStream = null;
            e2.printStackTrace();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }

    public static Bitmap loadImage(String str, int i, int i2) {
        InputStream bitmapStream;
        Exception e;
        Throwable th;
        Bitmap bitmap = null;
        Log.m3248d("loadImageUrl", str);
        if (!TextUtils.isEmpty(str)) {
            try {
                bitmapStream = getBitmapStream(str);
                try {
                    bitmap = BitmapFactory.decodeStream(bitmapStream, null, getScaleBitmapOptions(str, i, i2));
                    closeInputStream(bitmapStream);
                } catch (Exception e2) {
                    e = e2;
                    try {
                        e.printStackTrace();
                        closeInputStream(bitmapStream);
                        return bitmap;
                    } catch (Throwable th2) {
                        th = th2;
                        closeInputStream(bitmapStream);
                        throw th;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                bitmapStream = bitmap;
                e.printStackTrace();
                closeInputStream(bitmapStream);
                return bitmap;
            } catch (Throwable th3) {
                bitmapStream = bitmap;
                th = th3;
                closeInputStream(bitmapStream);
                throw th;
            }
        }
        return bitmap;
    }

    public static boolean isFileExist(String str) {
        if (!TextUtils.isEmpty(str) && new File(getFileName(str)).getAbsoluteFile().exists()) {
            return true;
        }
        return false;
    }

    public static boolean isNeedScale(String str, int i) {
        File file = new File(getFileName(str));
        if (!file.exists() || file.length() < ((long) i)) {
            return false;
        }
        return true;
    }

    public static Bitmap getBitmapFromFile(String str) {
        InputStream bitmapStream = getBitmapStream(str);
        if (bitmapStream == null) {
            return null;
        }
        Bitmap decodeStream = BitmapFactory.decodeStream(bitmapStream, null, null);
        closeInputStream(bitmapStream);
        return decodeStream;
    }

    public static Bitmap getBitmapFromFile(String str, int i, int i2) {
        InputStream bitmapStream = getBitmapStream(str);
        if (bitmapStream == null) {
            return null;
        }
        Bitmap decodeStream = BitmapFactory.decodeStream(bitmapStream, null, getScaleBitmapOptions(str, i, i2));
        closeInputStream(bitmapStream);
        return decodeStream;
    }

    public static void saveBitmap(String str, Bitmap bitmap) {
        try {
            OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(getFileName(str)));
            int i = 100;
            if (bitmap.getRowBytes() * bitmap.getHeight() > COMPRESS_FLAG) {
                i = 80;
            }
            bitmap.compress(CompressFormat.PNG, i, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception e) {
                Log.m3248d(TAG, e.toString());
            }
        }
    }

    public static String getFileName(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.startsWith("http://") || str.startsWith("https://")) {
            return PATH + AesHelper.md5(str);
        }
        return str;
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        Exception e;
        Throwable th;
        byte[] bArr = null;
        if (bitmap == null || bitmap.isRecycled()) {
            Log.m3248d(TAG, "bitmap2Bytes  ==> bitmap == null or bitmap.isRecycled()");
        } else {
            ByteArrayOutputStream byteArrayOutputStream;
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    int rowBytes = (bitmap.getRowBytes() * bitmap.getHeight()) / 1024;
                    int i = 100;
                    if (((float) rowBytes) > Config.imageSize) {
                        i = (int) (((float) 100) * (Config.imageSize / ((float) rowBytes)));
                    }
                    Log.m3248d("BitmapUtil", "compress quality:" + i);
                    bitmap.compress(CompressFormat.JPEG, i, byteArrayOutputStream);
                    bArr = byteArrayOutputStream.toByteArray();
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e2) {
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    try {
                        Log.m3251e(TAG, e.toString());
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (IOException e4) {
                            }
                        }
                        return bArr;
                    } catch (Throwable th2) {
                        th = th2;
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (IOException e5) {
                            }
                        }
                        throw th;
                    }
                }
            } catch (Exception e6) {
                e = e6;
                byteArrayOutputStream = null;
                Log.m3251e(TAG, e.toString());
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                return bArr;
            } catch (Throwable th3) {
                byteArrayOutputStream = null;
                th = th3;
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                throw th;
            }
        }
        return bArr;
    }

    public static int calculateInSampleSize(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            i3 /= 2;
            i4 /= 2;
            while (i3 / i5 > i2 && i4 / i5 > i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    public static Options getBitmapOptions(byte[] bArr) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        int ceil = (int) Math.ceil((double) (options.outWidth / MAX_WIDTH));
        int ceil2 = (int) Math.ceil((double) (options.outHeight / MAX_HEIGHT));
        if (ceil2 <= 1 || ceil <= 1) {
            if (ceil2 > 2) {
                options.inSampleSize = ceil2;
            } else if (ceil > 2) {
                options.inSampleSize = ceil;
            }
        } else if (ceil2 > ceil) {
            options.inSampleSize = ceil2;
        } else {
            options.inSampleSize = ceil;
        }
        options.inJustDecodeBounds = false;
        return options;
    }

    private static int freeSpaceOnSd() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return (int) ((((double) statFs.getBlockSize()) * ((double) statFs.getAvailableBlocks())) / 1048576.0d);
    }

    private static void remove40PercentCache(String str) {
        int i = 0;
        File[] listFiles = new File(str).listFiles();
        if (listFiles.length != 0) {
            int length;
            int i2 = 0;
            for (File length2 : listFiles) {
                i2 = (int) (((long) i2) + length2.length());
            }
            if (i2 > 10485760 || 40 > freeSpaceOnSd()) {
                length = (int) ((0.4d * ((double) listFiles.length)) + WeightedLatLng.DEFAULT_INTENSITY);
                Arrays.sort(listFiles, new FileLastModifSort());
                while (i < length) {
                    listFiles[i].delete();
                    i++;
                }
            }
        }
    }

    public static void cleanCache() {
        init();
    }

    public static byte[] compressBitmap(byte[] bArr, int i) {
        int i2 = 0;
        if (bArr != null && bArr.length >= i) {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
            int i3 = 1;
            while (i2 == 0 && i3 <= 10) {
                int pow = (int) (Math.pow(0.8d, (double) i3) * 100.0d);
                Log.m3248d(TAG, "quality = " + pow);
                decodeByteArray.compress(CompressFormat.JPEG, pow, byteArrayOutputStream);
                Log.m3248d(TAG, "WeiXin Thumb Size = " + (byteArrayOutputStream.toByteArray().length / 1024) + " KB");
                if (byteArrayOutputStream == null || byteArrayOutputStream.size() >= i) {
                    byteArrayOutputStream.reset();
                    i3++;
                } else {
                    i2 = 1;
                }
            }
            if (byteArrayOutputStream != null) {
                bArr = byteArrayOutputStream.toByteArray();
                if (!decodeByteArray.isRecycled()) {
                    decodeByteArray.recycle();
                }
                if (bArr != null && bArr.length <= 0) {
                    Log.m3251e(TAG, "### 您的原始图片太大,导致缩略图压缩过后还大于32KB,请将分享到微信的图片进行适当缩小.");
                }
            }
        }
        return bArr;
    }

    public static Bitmap createThumb(Bitmap bitmap, float f) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f2 = 1.0f;
        if (width < 200 || height < 200) {
            if (width < height) {
                f2 = f / ((float) width);
            } else {
                f2 = f / ((float) height);
            }
        }
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (((float) width) * f2), (int) (f2 * ((float) height)), true);
        if (createScaledBitmap == null) {
            return bitmap;
        }
        return createScaledBitmap;
    }
}
