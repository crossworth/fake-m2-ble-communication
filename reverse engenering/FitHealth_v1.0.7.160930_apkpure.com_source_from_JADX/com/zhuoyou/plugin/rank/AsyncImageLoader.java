package com.zhuoyou.plugin.rank;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.zhuoyou.plugin.running.Tools;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;

public class AsyncImageLoader {
    private ImageUtils mImageUtils = new ImageUtils();
    private Random mRandom = new Random();

    public interface ImageCallback {
        void imageLoaded(Drawable drawable, String str);
    }

    public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback) {
        Drawable drawable = this.mImageUtils.getDrawable(imageUrl);
        if (drawable != null) {
            return drawable;
        }
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
            }
        };
        new Thread() {
            public void run() {
                Drawable drawable = AsyncImageLoader.loadImageFromUrl(imageUrl);
                AsyncImageLoader.this.mImageUtils.putDrawable(imageUrl, drawable);
                handler.sendMessage(handler.obtainMessage(0, drawable));
            }
        }.start();
        return null;
    }

    public static String GetFileName(String url) {
        String filename = "";
        if (url == null) {
            return filename;
        }
        String tmp = url;
        String file_tmp = url;
        for (int i = 0; i < 5; i++) {
            tmp = tmp.substring(0, tmp.lastIndexOf("/"));
        }
        for (String aa : file_tmp.substring(tmp.length() + 1).split("/")) {
            filename = filename + aa;
        }
        return filename.substring(0, filename.lastIndexOf("."));
    }

    public static Drawable loadImageFromUrl(String url) {
        OutOfMemoryError e;
        Exception e2;
        Throwable th;
        InputStream i = null;
        Drawable d = null;
        if (url != null) {
            if (!url.equals("")) {
                String fileName = GetFileName(url);
                Log.i("caixinxin", "filaName = " + fileName);
                String filePath = Tools.getSDPath() + "/Running/download/cache";
                File dirs = new File(filePath.toString());
                if (!dirs.exists()) {
                    dirs.mkdirs();
                }
                File f = new File(filePath, fileName);
                FileInputStream fis;
                FileInputStream fileInputStream;
                if (f.exists()) {
                    try {
                        fis = new FileInputStream(f);
                        try {
                            d = Drawable.createFromStream(fis, "src");
                            fis.close();
                            fileInputStream = fis;
                            return d;
                        } catch (OutOfMemoryError e3) {
                            e = e3;
                            fileInputStream = fis;
                            e.printStackTrace();
                            System.gc();
                            return null;
                        } catch (Exception e4) {
                            e2 = e4;
                            fileInputStream = fis;
                            e2.printStackTrace();
                            return null;
                        }
                    } catch (OutOfMemoryError e5) {
                        e = e5;
                        e.printStackTrace();
                        System.gc();
                        return null;
                    } catch (Exception e6) {
                        e2 = e6;
                        e2.printStackTrace();
                        return null;
                    }
                }
                try {
                    i = (InputStream) new URL(url).getContent();
                    DataInputStream in = new DataInputStream(i);
                    FileOutputStream out = new FileOutputStream(f);
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int byteread = in.read(buffer);
                        if (byteread == -1) {
                            break;
                        }
                        out.write(buffer, 0, byteread);
                    }
                    in.close();
                    out.close();
                    fis = new FileInputStream(f);
                    try {
                        d = Drawable.createFromStream(i, "src");
                        fis.close();
                        i.close();
                        if (i != null) {
                            try {
                                i.close();
                            } catch (IOException e7) {
                                e7.printStackTrace();
                                fileInputStream = fis;
                            }
                        }
                        fileInputStream = fis;
                    } catch (OutOfMemoryError e8) {
                        e = e8;
                        fileInputStream = fis;
                        try {
                            e.printStackTrace();
                            System.gc();
                            if (i != null) {
                                return null;
                            }
                            try {
                                i.close();
                                return null;
                            } catch (IOException e72) {
                                e72.printStackTrace();
                                return null;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (i != null) {
                                try {
                                    i.close();
                                } catch (IOException e722) {
                                    e722.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Exception e9) {
                        e2 = e9;
                        fileInputStream = fis;
                        e2.printStackTrace();
                        if (i != null) {
                            try {
                                i.close();
                            } catch (IOException e7222) {
                                e7222.printStackTrace();
                            }
                        }
                        return d;
                    } catch (Throwable th3) {
                        th = th3;
                        fileInputStream = fis;
                        if (i != null) {
                            i.close();
                        }
                        throw th;
                    }
                } catch (OutOfMemoryError e10) {
                    e = e10;
                    e.printStackTrace();
                    System.gc();
                    if (i != null) {
                        return null;
                    }
                    i.close();
                    return null;
                } catch (Exception e11) {
                    e2 = e11;
                    e2.printStackTrace();
                    if (i != null) {
                        i.close();
                    }
                    return d;
                }
            }
        }
        return d;
    }
}
