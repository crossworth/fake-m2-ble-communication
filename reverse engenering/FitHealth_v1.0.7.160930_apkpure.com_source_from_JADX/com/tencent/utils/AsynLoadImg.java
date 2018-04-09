package com.tencent.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
    private static String f2964d;
    Activity f2965a;
    private String f2966b;
    private AsynLoadImgBack f2967c;
    private long f2968e;
    private Handler f2969f;
    private Runnable f2970g = new C08622(this);

    /* compiled from: ProGuard */
    class C08622 implements Runnable {
        final /* synthetic */ AsynLoadImg f2963a;

        C08622(AsynLoadImg asynLoadImg) {
            this.f2963a = asynLoadImg;
        }

        public void run() {
            Log.v("AsynLoadImg", "saveFileRunnable:");
            String str = "share_qq_" + Util.encrypt(this.f2963a.f2966b) + ".jpg";
            String str2 = AsynLoadImg.f2964d + str;
            File file = new File(str2);
            Message obtainMessage = this.f2963a.f2969f.obtainMessage();
            if (file.exists()) {
                obtainMessage.arg1 = 0;
                obtainMessage.obj = str2;
                Log.v("AsynLoadImg", "file exists: time:" + (System.currentTimeMillis() - this.f2963a.f2968e));
            } else {
                boolean saveFile;
                Bitmap bitmap = AsynLoadImg.getbitmap(this.f2963a.f2966b);
                if (bitmap != null) {
                    saveFile = this.f2963a.saveFile(bitmap, str);
                } else {
                    Log.v("AsynLoadImg", "saveFileRunnable:get bmp fail---");
                    saveFile = false;
                }
                if (saveFile) {
                    obtainMessage.arg1 = 0;
                    obtainMessage.obj = str2;
                } else {
                    obtainMessage.arg1 = 1;
                }
                Log.v("AsynLoadImg", "file not exists: download time:" + (System.currentTimeMillis() - this.f2963a.f2968e));
            }
            this.f2963a.f2969f.sendMessage(obtainMessage);
        }
    }

    public AsynLoadImg(Activity activity) {
        this.f2965a = activity;
        this.f2969f = new Handler(this, activity.getMainLooper()) {
            final /* synthetic */ AsynLoadImg f2962a;

            public void handleMessage(Message message) {
                Log.v("AsynLoadImg", "handleMessage:" + message.arg1);
                if (message.arg1 == 0) {
                    this.f2962a.f2967c.saved(message.arg1, (String) message.obj);
                } else {
                    this.f2962a.f2967c.saved(message.arg1, null);
                }
            }
        };
    }

    public void save(String str, AsynLoadImgBack asynLoadImgBack) {
        Log.v("AsynLoadImg", "--save---");
        if (str == null || str.equals("")) {
            asynLoadImgBack.saved(1, null);
        } else if (Util.hasSDCard()) {
            f2964d = Environment.getExternalStorageDirectory() + "/tmp/";
            this.f2968e = System.currentTimeMillis();
            this.f2966b = str;
            this.f2967c = asynLoadImgBack;
            new Thread(this.f2970g).start();
        } else {
            asynLoadImgBack.saved(2, null);
        }
    }

    public boolean saveFile(Bitmap bitmap, String str) {
        String str2 = f2964d;
        try {
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdir();
            }
            str2 = str2 + str;
            Log.v("AsynLoadImg", "saveFile:" + str);
            OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(str2)));
            bitmap.compress(CompressFormat.JPEG, 80, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("AsynLoadImg", "saveFile bmp fail---");
            return false;
        }
    }

    public static Bitmap getbitmap(String str) {
        Log.v("AsynLoadImg", "getbitmap:" + str);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            Log.v("AsynLoadImg", "image download finished." + str);
            return decodeStream;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("AsynLoadImg", "getbitmap bmp fail---");
            return null;
        }
    }
}
