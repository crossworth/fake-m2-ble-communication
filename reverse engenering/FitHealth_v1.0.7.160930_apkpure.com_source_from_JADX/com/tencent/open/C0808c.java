package com.tencent.open;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: ProGuard */
public class C0808c extends AsyncTask<Bitmap, Void, HashMap<String, Object>> {
    private static final SimpleDateFormat f2746a = new SimpleDateFormat("yyyy-MM-dd-HHmmss", Locale.CHINA);
    private C0807a f2747b;

    /* compiled from: ProGuard */
    public interface C0807a {
        void mo2135a(String str);

        void mo2136b(String str);
    }

    protected /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
        return m2589a((Bitmap[]) objArr);
    }

    protected /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
        m2590a((HashMap) obj);
    }

    public C0808c(C0807a c0807a) {
        this.f2747b = c0807a;
    }

    protected HashMap<String, Object> m2589a(Bitmap... bitmapArr) {
        HashMap<String, Object> hashMap = new HashMap();
        try {
            Bitmap bitmap = bitmapArr[0];
            if (bitmap != null) {
                Object b;
                String str = "";
                if (bitmap.getWidth() > 320 || bitmap.getHeight() > 320) {
                    Bitmap a = m2583a(bitmap);
                    b = m2588b(a);
                    a.recycle();
                } else {
                    b = m2588b(bitmap);
                }
                bitmap.recycle();
                hashMap.put("ResultType", Integer.valueOf(1));
                hashMap.put("ResultValue", b);
            }
        } catch (Exception e) {
            hashMap.put("ResultType", Integer.valueOf(0));
            hashMap.put("ResultValue", e.getMessage());
        }
        return hashMap;
    }

    protected void m2590a(HashMap<String, Object> hashMap) {
        if (((Integer) hashMap.get("ResultType")).intValue() == 1) {
            this.f2747b.mo2135a((String) hashMap.get("ResultValue"));
        } else {
            this.f2747b.mo2136b((String) hashMap.get("ResultValue"));
        }
        super.onPostExecute(hashMap);
    }

    private Bitmap m2583a(Bitmap bitmap) {
        int i = 1;
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
        if (byteArrayOutputStream.toByteArray().length / 1024 > 1024) {
            byteArrayOutputStream.reset();
            bitmap.compress(CompressFormat.JPEG, 50, byteArrayOutputStream);
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(byteArrayInputStream, null, options);
        options.inJustDecodeBounds = false;
        int a = m2582a(options, 320, 320);
        if (a > 0) {
            i = a;
        }
        Log.i("comp", "comp be=" + i);
        options.inSampleSize = i;
        return BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), null, options);
    }

    private int m2582a(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int round = Math.round(((float) i3) / ((float) i2));
        i3 = Math.round(((float) i4) / ((float) i));
        return round < i3 ? round : i3;
    }

    public static void m2585a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                File file = new File(str);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
            }
        }
    }

    private String m2588b(Bitmap bitmap) {
        OutputStream outputStream;
        Throwable th;
        FileOutputStream fileOutputStream = null;
        String str = "";
        try {
            str = m2584a(System.currentTimeMillis()) + ".png";
            String str2 = m2587b() + File.separator + ".AppCenterWebBuffer";
            str = str2 + File.separator + str;
            File file = new File(str2);
            if (file.exists() || !file.mkdirs()) {
                file = new File(str);
            } else {
                file = new File(str);
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            OutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                bitmap.compress(CompressFormat.PNG, 100, fileOutputStream2);
                fileOutputStream2.flush();
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e) {
                    }
                }
            } catch (Exception e2) {
                outputStream = fileOutputStream2;
                try {
                    str = "";
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                        }
                    }
                    return str;
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                outputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            str = "";
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return str;
        }
        return str;
    }

    private String m2587b() {
        String str = ".";
        if (Environment.getExternalStorageState().equals("mounted")) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        if (new File("/mnt/sdcard-ext").isDirectory()) {
            return "/mnt/sdcard-ext";
        }
        return str;
    }

    private String m2584a(long j) {
        return f2746a.format(new Date(j));
    }

    public static boolean m2586a() {
        if (Environment.getExternalStorageState().equals("mounted") || new File("/mnt/sdcard-ext").isDirectory()) {
            return true;
        }
        return false;
    }
}
