package com.tencent.open.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.tencent.open.p036a.C1314f;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* compiled from: ProGuard */
public class AsynLoadImg {
    private static String f4189c;
    private String f4190a;
    private AsynLoadImgBack f4191b;
    private long f4192d;
    private Handler f4193e;
    private Runnable f4194f = new C13402(this);

    /* compiled from: ProGuard */
    class C13402 implements Runnable {
        final /* synthetic */ AsynLoadImg f4188a;

        C13402(AsynLoadImg asynLoadImg) {
            this.f4188a = asynLoadImg;
        }

        public void run() {
            C1314f.m3864a("AsynLoadImg", "saveFileRunnable:");
            String str = "share_qq_" + Util.encrypt(this.f4188a.f4190a) + ".jpg";
            String str2 = AsynLoadImg.f4189c + str;
            File file = new File(str2);
            Message obtainMessage = this.f4188a.f4193e.obtainMessage();
            if (file.exists()) {
                obtainMessage.arg1 = 0;
                obtainMessage.obj = str2;
                C1314f.m3864a("AsynLoadImg", "file exists: time:" + (System.currentTimeMillis() - this.f4188a.f4192d));
            } else {
                boolean saveFile;
                Bitmap bitmap = AsynLoadImg.getbitmap(this.f4188a.f4190a);
                if (bitmap != null) {
                    saveFile = this.f4188a.saveFile(bitmap, str);
                } else {
                    C1314f.m3864a("AsynLoadImg", "saveFileRunnable:get bmp fail---");
                    saveFile = false;
                }
                if (saveFile) {
                    obtainMessage.arg1 = 0;
                    obtainMessage.obj = str2;
                } else {
                    obtainMessage.arg1 = 1;
                }
                C1314f.m3864a("AsynLoadImg", "file not exists: download time:" + (System.currentTimeMillis() - this.f4188a.f4192d));
            }
            this.f4188a.f4193e.sendMessage(obtainMessage);
        }
    }

    public AsynLoadImg(Activity activity) {
        this.f4193e = new Handler(this, activity.getMainLooper()) {
            final /* synthetic */ AsynLoadImg f4187a;

            public void handleMessage(Message message) {
                C1314f.m3864a("AsynLoadImg", "handleMessage:" + message.arg1);
                if (message.arg1 == 0) {
                    this.f4187a.f4191b.saved(message.arg1, (String) message.obj);
                } else {
                    this.f4187a.f4191b.saved(message.arg1, null);
                }
            }
        };
    }

    public void save(String str, AsynLoadImgBack asynLoadImgBack) {
        C1314f.m3864a("AsynLoadImg", "--save---");
        if (str == null || str.equals("")) {
            asynLoadImgBack.saved(1, null);
        } else if (Util.hasSDCard()) {
            f4189c = Environment.getExternalStorageDirectory() + "/tmp/";
            this.f4192d = System.currentTimeMillis();
            this.f4190a = str;
            this.f4191b = asynLoadImgBack;
            new Thread(this.f4194f).start();
        } else {
            asynLoadImgBack.saved(2, null);
        }
    }

    public boolean saveFile(Bitmap bitmap, String str) {
        Throwable e;
        OutputStream outputStream;
        String str2 = f4189c;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdir();
            }
            str2 = str2 + str;
            C1314f.m3864a("AsynLoadImg", "saveFile:" + str);
            OutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(new File(str2)));
            try {
                bitmap.compress(CompressFormat.JPEG, 80, bufferedOutputStream2);
                bufferedOutputStream2.flush();
                if (bufferedOutputStream2 != null) {
                    try {
                        bufferedOutputStream2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                return true;
            } catch (IOException e3) {
                e = e3;
                outputStream = bufferedOutputStream2;
                try {
                    e.printStackTrace();
                    C1314f.m3868b("AsynLoadImg", "saveFile bmp fail---", e);
                    if (bufferedOutputStream != null) {
                        return false;
                    }
                    try {
                        bufferedOutputStream.close();
                        return false;
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        return false;
                    }
                } catch (Throwable th) {
                    e = th;
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e42) {
                            e42.printStackTrace();
                        }
                    }
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                outputStream = bufferedOutputStream2;
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                throw e;
            }
        } catch (IOException e5) {
            e = e5;
            e.printStackTrace();
            C1314f.m3868b("AsynLoadImg", "saveFile bmp fail---", e);
            if (bufferedOutputStream != null) {
                return false;
            }
            bufferedOutputStream.close();
            return false;
        }
    }

    public static Bitmap getbitmap(String str) {
        C1314f.m3864a("AsynLoadImg", "getbitmap:" + str);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            C1314f.m3864a("AsynLoadImg", "image download finished." + str);
            return decodeStream;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            C1314f.m3864a("AsynLoadImg", "getbitmap bmp fail---");
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            C1314f.m3864a("AsynLoadImg", "getbitmap bmp fail---");
            return null;
        }
    }
}
