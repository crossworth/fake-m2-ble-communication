package com.zhuoyou.plugin.info;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.zhuoyou.plugin.album.BitmapUtils;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.selfupdate.Constants;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageAsynTask extends AsyncTask<String, Void, Bitmap> {
    String fileName = "";

    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        String url = params[0];
        this.fileName = params[1];
        try {
            bitmap = BitmapFactory.decodeStream(getImageStream(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap resultBitmap) {
        Exception e;
        Throwable th;
        if (resultBitmap != null) {
            String file = Tools.getSDPath() + Constants.DownloadApkPath;
            File dirFile = new File(file);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            BufferedOutputStream bos = null;
            try {
                BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(new File(file + this.fileName)));
                try {
                    BitmapUtils.getCroppedRoundBitmap(resultBitmap, 100).compress(CompressFormat.PNG, 100, bos2);
                    if (!(resultBitmap == null || resultBitmap.isRecycled())) {
                        resultBitmap.recycle();
                        resultBitmap = null;
                        System.gc();
                    }
                    try {
                        bos2.flush();
                        bos2.close();
                        bos = bos2;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        bos = bos2;
                    }
                } catch (Exception e3) {
                    e2 = e3;
                    bos = bos2;
                    try {
                        e2.printStackTrace();
                        if (!(resultBitmap == null || resultBitmap.isRecycled())) {
                            resultBitmap.recycle();
                            resultBitmap = null;
                            System.gc();
                        }
                        try {
                            bos.flush();
                            bos.close();
                        } catch (Exception e22) {
                            e22.printStackTrace();
                        }
                        super.onPostExecute(resultBitmap);
                    } catch (Throwable th2) {
                        th = th2;
                        if (!(resultBitmap == null || resultBitmap.isRecycled())) {
                            resultBitmap.recycle();
                            System.gc();
                        }
                        try {
                            bos.flush();
                            bos.close();
                        } catch (Exception e222) {
                            e222.printStackTrace();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bos = bos2;
                    resultBitmap.recycle();
                    System.gc();
                    bos.flush();
                    bos.close();
                    throw th;
                }
            } catch (Exception e4) {
                e222 = e4;
                e222.printStackTrace();
                resultBitmap.recycle();
                resultBitmap = null;
                System.gc();
                bos.flush();
                bos.close();
                super.onPostExecute(resultBitmap);
            }
            super.onPostExecute(resultBitmap);
        }
    }

    private InputStream getImageStream(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            return conn.getInputStream();
        }
        return null;
    }
}
