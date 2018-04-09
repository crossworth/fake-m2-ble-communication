package com.tencent.sample.activitys;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.amap.api.maps.model.WeightedLatLng;
import com.amap.api.services.core.AMapException;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import junit.framework.Assert;

public class Util {
    private static final int MAX_DECODE_PICTURE_SIZE = 2764800;
    private static final String TAG = "SDK_Sample.Util";
    private static String hexString = "0123456789ABCDEF";
    private static Dialog mProgressDialog;
    private static Toast mToast;

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            String hv = Integer.toHexString(b & 255);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String toHexString(String str) {
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & SocializeConstants.MASK_USER_CENTER_HIDE_AREA) >> 4));
            sb.append(hexString.charAt((bytes[i] & 15) >> 0));
        }
        return sb.toString();
    }

    public static String hexToString(String s) {
        if ("0x".equals(s.substring(0, 2))) {
            s = s.substring(2);
        }
        byte[] baKeyword = new byte[(s.length() / 2)];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (Integer.parseInt(s.substring(i * 2, (i * 2) + 2), 16) & 255);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            return new String(baKeyword, "utf-8");
        } catch (Exception e1) {
            e1.printStackTrace();
            return s;
        }
    }

    public static byte[] bmpToByteArray(Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] getHtmlByteArray(String url) {
        URL url2;
        MalformedURLException e;
        IOException e2;
        InputStream inStream = null;
        try {
            URL htmlUrl = new URL(url);
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) htmlUrl.openConnection();
                if (httpConnection.getResponseCode() == 200) {
                    inStream = httpConnection.getInputStream();
                }
                url2 = htmlUrl;
            } catch (MalformedURLException e3) {
                e = e3;
                url2 = htmlUrl;
                e.printStackTrace();
                return inputStreamToByte(inStream);
            } catch (IOException e4) {
                e2 = e4;
                url2 = htmlUrl;
                e2.printStackTrace();
                return inputStreamToByte(inStream);
            }
        } catch (MalformedURLException e5) {
            e = e5;
            e.printStackTrace();
            return inputStreamToByte(inStream);
        } catch (IOException e6) {
            e2 = e6;
            e2.printStackTrace();
            return inputStreamToByte(inStream);
        }
        return inputStreamToByte(inStream);
    }

    public static byte[] inputStreamToByte(InputStream is) {
        try {
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            while (true) {
                int ch = is.read();
                if (ch != -1) {
                    bytestream.write(ch);
                } else {
                    byte[] imgdata = bytestream.toByteArray();
                    bytestream.close();
                    return imgdata;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readFromFile(String fileName, int offset, int len) {
        byte[] bArr = null;
        if (fileName != null) {
            File file = new File(fileName);
            if (file.exists()) {
                if (len == -1) {
                    len = (int) file.length();
                }
                Log.d(TAG, "readFromFile : offset = " + offset + " len = " + len + " offset + len = " + (offset + len));
                if (offset < 0) {
                    Log.e(TAG, "readFromFile invalid offset:" + offset);
                } else if (len <= 0) {
                    Log.e(TAG, "readFromFile invalid len:" + len);
                } else if (offset + len > ((int) file.length())) {
                    Log.e(TAG, "readFromFile invalid file len:" + file.length());
                } else {
                    bArr = null;
                    try {
                        RandomAccessFile in = new RandomAccessFile(fileName, "r");
                        bArr = new byte[len];
                        in.seek((long) offset);
                        in.readFully(bArr);
                        in.close();
                    } catch (Exception e) {
                        Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            } else {
                Log.i(TAG, "readFromFile: file not found");
            }
        }
        return bArr;
    }

    public static int computeSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        if (initialSize > 8) {
            return ((initialSize + 7) / 8) * 8;
        }
        int roundedSize = 1;
        while (roundedSize < initialSize) {
            roundedSize <<= 1;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        int upperBound;
        double w = (double) options.outWidth;
        double h = (double) options.outHeight;
        int lowerBound = maxNumOfPixels == -1 ? 1 : (int) Math.ceil(Math.sqrt((w * h) / ((double) maxNumOfPixels)));
        if (minSideLength == -1) {
            upperBound = 128;
        } else {
            upperBound = (int) Math.min(Math.floor(w / ((double) minSideLength)), Math.floor(h / ((double) minSideLength)));
        }
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if (maxNumOfPixels == -1 && minSideLength == -1) {
            return 1;
        }
        if (minSideLength != -1) {
            return upperBound;
        }
        return lowerBound;
    }

    public static Bitmap readBitmap(String path) {
        Bitmap bitmap = null;
        try {
            FileInputStream stream = new FileInputStream(new File(path + "test.jpg"));
            Options opts = new Options();
            opts.inSampleSize = 8;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            bitmap = BitmapFactory.decodeStream(stream, null, opts);
        } catch (Exception e) {
        }
        return bitmap;
    }

    public static Bitmap extractThumbNail(String path, int height, int width, boolean crop) {
        boolean z;
        Options options;
        Bitmap tmp;
        double beY;
        double beX;
        int newHeight;
        int newWidth;
        Bitmap bm;
        Bitmap scale;
        Bitmap cropped;
        if (path != null) {
            if (!path.equals("") && height > 0 && width > 0) {
                z = true;
                Assert.assertTrue(z);
                options = new Options();
                options.inJustDecodeBounds = true;
                tmp = BitmapFactory.decodeFile(path, options);
                if (tmp != null) {
                    tmp.recycle();
                }
                Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
                beY = (((double) options.outHeight) * WeightedLatLng.DEFAULT_INTENSITY) / ((double) height);
                beX = (((double) options.outWidth) * WeightedLatLng.DEFAULT_INTENSITY) / ((double) width);
                Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
                double d = crop ? beY <= beX ? beX : beY : beY >= beX ? beX : beY;
                options.inSampleSize = (int) d;
                if (options.inSampleSize <= 1) {
                    options.inSampleSize = 1;
                }
                while ((options.outHeight * options.outWidth) / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                    options.inSampleSize++;
                }
                newHeight = height;
                newWidth = width;
                if (crop) {
                    if (beY >= beX) {
                        newHeight = (int) (((((double) newWidth) * WeightedLatLng.DEFAULT_INTENSITY) * ((double) options.outHeight)) / ((double) options.outWidth));
                    } else {
                        newWidth = (int) (((((double) newHeight) * WeightedLatLng.DEFAULT_INTENSITY) * ((double) options.outWidth)) / ((double) options.outHeight));
                    }
                } else if (beY <= beX) {
                    newHeight = (int) (((((double) newWidth) * WeightedLatLng.DEFAULT_INTENSITY) * ((double) options.outHeight)) / ((double) options.outWidth));
                } else {
                    newWidth = (int) (((((double) newHeight) * WeightedLatLng.DEFAULT_INTENSITY) * ((double) options.outWidth)) / ((double) options.outHeight));
                }
                options.inJustDecodeBounds = false;
                Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
                bm = BitmapFactory.decodeFile(path, options);
                if (bm != null) {
                    Log.e(TAG, "bitmap decode failed");
                    return null;
                }
                Log.i(TAG, "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
                scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
                if (scale != null) {
                    bm.recycle();
                    bm = scale;
                }
                if (crop) {
                    return bm;
                }
                cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
                if (cropped != null) {
                    return bm;
                }
                bm.recycle();
                bm = cropped;
                Log.i(TAG, "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
                return bm;
            }
        }
        z = false;
        Assert.assertTrue(z);
        options = new Options();
        try {
            options.inJustDecodeBounds = true;
            tmp = BitmapFactory.decodeFile(path, options);
            if (tmp != null) {
                tmp.recycle();
            }
            Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
            beY = (((double) options.outHeight) * WeightedLatLng.DEFAULT_INTENSITY) / ((double) height);
            beX = (((double) options.outWidth) * WeightedLatLng.DEFAULT_INTENSITY) / ((double) width);
            Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
            if (crop) {
                if (beY <= beX) {
                }
            }
            options.inSampleSize = (int) d;
            if (options.inSampleSize <= 1) {
                options.inSampleSize = 1;
            }
            while ((options.outHeight * options.outWidth) / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                options.inSampleSize++;
            }
            newHeight = height;
            newWidth = width;
            if (crop) {
                if (beY >= beX) {
                    newWidth = (int) (((((double) newHeight) * WeightedLatLng.DEFAULT_INTENSITY) * ((double) options.outWidth)) / ((double) options.outHeight));
                } else {
                    newHeight = (int) (((((double) newWidth) * WeightedLatLng.DEFAULT_INTENSITY) * ((double) options.outHeight)) / ((double) options.outWidth));
                }
            } else if (beY <= beX) {
                newWidth = (int) (((((double) newHeight) * WeightedLatLng.DEFAULT_INTENSITY) * ((double) options.outWidth)) / ((double) options.outHeight));
            } else {
                newHeight = (int) (((((double) newWidth) * WeightedLatLng.DEFAULT_INTENSITY) * ((double) options.outHeight)) / ((double) options.outWidth));
            }
            options.inJustDecodeBounds = false;
            Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
            bm = BitmapFactory.decodeFile(path, options);
            if (bm != null) {
                Log.i(TAG, "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
                scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
                if (scale != null) {
                    bm.recycle();
                    bm = scale;
                }
                if (crop) {
                    return bm;
                }
                cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
                if (cropped != null) {
                    return bm;
                }
                bm.recycle();
                bm = cropped;
                Log.i(TAG, "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
                return bm;
            }
            Log.e(TAG, "bitmap decode failed");
            return null;
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "decode bitmap failed: " + e.getMessage());
            return null;
        }
    }

    public static final void showResultDialog(Context context, String msg, String title) {
        if (msg != null) {
            String rmsg = msg.replace(SeparatorConstants.SEPARATOR_ADS_ID, "\n");
            Log.d("Util", rmsg);
            new Builder(context).setTitle(title).setMessage(rmsg).setNegativeButton("知道了", null).create().show();
        }
    }

    public static final void showProgressDialog(Context context, String title, String message) {
        dismissDialog();
        if (TextUtils.isEmpty(message)) {
            message = "正在加载...";
        }
        mProgressDialog = ProgressDialog.show(context, title, message);
    }

    public static final void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public static boolean isDialogShowing() {
        if (mProgressDialog != null) {
            return mProgressDialog.isShowing();
        }
        return false;
    }

    public static final void toastMessage(final Activity activity, final String message, String logLevel) {
        if ("w".equals(logLevel)) {
            Log.w("sdkDemo", message);
        } else if ("e".equals(logLevel)) {
            Log.e("sdkDemo", message);
        } else {
            Log.d("sdkDemo", message);
        }
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (Util.mToast != null) {
                    Util.mToast.cancel();
                    Util.mToast = null;
                }
                Util.mToast = Toast.makeText(activity, message, 0);
                Util.mToast.show();
            }
        });
    }

    public static final void toastMessage(Activity activity, String message) {
        toastMessage(activity, message, null);
    }

    public static Bitmap getbitmap(String imageUri) {
        Log.v(TAG, "getbitmap:" + imageUri);
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(imageUri).openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(AMapException.CODE_AMAP_SHARE_LICENSE_IS_EXPIRED);
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            Log.v(TAG, "image download finished." + imageUri);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "getbitmap bmp fail---");
            return null;
        }
    }

    public static void writeBitmap(Bitmap bitmap) {
    }
}
