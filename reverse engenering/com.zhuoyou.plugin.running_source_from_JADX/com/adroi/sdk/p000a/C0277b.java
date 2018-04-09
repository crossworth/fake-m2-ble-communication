package com.adroi.sdk.p000a;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import dalvik.system.DexClassLoader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.jar.JarFile;

public final class C0277b {
    public static ClassLoader f31a = null;
    private static boolean f32b = false;

    public static boolean m28a(boolean z, Context context, URL url, String str) {
        BufferedInputStream bufferedInputStream;
        Throwable e;
        boolean z2;
        BufferedOutputStream bufferedOutputStream = null;
        Object[] objArr = new Object[2];
        objArr[0] = "AdUtil.save";
        objArr[1] = String.format("[%s] %s", new Object[]{str, url.toString()});
        C0278c.m33a(objArr);
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
            jarURLConnection.connect();
            bufferedInputStream = new BufferedInputStream(jarURLConnection.getInputStream());
            if (z) {
                try {
                    if ("mounted".equals(Environment.getExternalStorageState())) {
                        bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + str));
                    } else {
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (Throwable e2) {
                                C0278c.m40b("AdUtil.saveJar", e2);
                                return false;
                            }
                        }
                        if (bufferedOutputStream == null) {
                            return false;
                        }
                        bufferedOutputStream2.close();
                        return false;
                    }
                } catch (IOException e3) {
                    e2 = e3;
                    try {
                        C0278c.m40b("AdUtil.saveJar", e2);
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (Throwable e22) {
                                C0278c.m40b("AdUtil.saveJar", e22);
                                z2 = false;
                            }
                        }
                        if (bufferedOutputStream != null) {
                            bufferedOutputStream.close();
                        }
                        z2 = true;
                        return z2;
                    } catch (Throwable th) {
                        e22 = th;
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (Throwable e4) {
                                C0278c.m40b("AdUtil.saveJar", e4);
                                throw e22;
                            }
                        }
                        if (bufferedOutputStream != null) {
                            bufferedOutputStream.close();
                        }
                        throw e22;
                    }
                }
            }
            bufferedOutputStream = new BufferedOutputStream(context.openFileOutput(str, 0));
            byte[] bArr = new byte[5120];
            while (true) {
                int read = bufferedInputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                bufferedOutputStream.write(bArr, 0, read);
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (Throwable e222) {
                    C0278c.m40b("AdUtil.saveJar", e222);
                    z2 = false;
                }
            }
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            z2 = true;
        } catch (IOException e5) {
            e222 = e5;
            Object obj = bufferedOutputStream;
            C0278c.m40b("AdUtil.saveJar", e222);
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            z2 = true;
            return z2;
        } catch (Throwable th2) {
            e222 = th2;
            bufferedInputStream = bufferedOutputStream;
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            throw e222;
        }
        return z2;
    }

    public static synchronized ClassLoader m26a(Context context) {
        ClassLoader classLoader;
        boolean z = false;
        double d = 0.0d;
        synchronized (C0277b.class) {
            StringBuilder append = new StringBuilder().append("AdUtil.getRemoteClassLoader  ");
            if (f31a == null) {
                z = true;
            }
            C0278c.m30a(append.append(z).append(" thread: ").append(Thread.currentThread()).toString());
            if (f31a != null) {
                classLoader = f31a;
            } else {
                double parseDouble;
                File file = new File(context.getFilesDir().getAbsolutePath() + "/" + C0276a.f28b);
                File file2 = new File(context.getFilesDir().getAbsolutePath() + "/" + C0276a.f29c);
                File file3 = new File(context.getFilesDir().getAbsolutePath() + "/" + C0276a.f27a + ".tmp.jar");
                try {
                    C0277b.m28a(false, context, C0277b.class.getResource("/extra/" + C0276a.f28b), file3.getName());
                    parseDouble = Double.parseDouble(new JarFile(file3).getManifest().getMainAttributes().getValue("Implementation-Version"));
                } catch (Exception e) {
                    C0278c.m39b("Adroi-SDK packed is not ok! this will affect the first few shows");
                    parseDouble = 0.0d;
                }
                try {
                    if (file.exists()) {
                        d = Double.parseDouble(new JarFile(file).getManifest().getMainAttributes().getValue("Implementation-Version"));
                    }
                    if (parseDouble > d) {
                        file.delete();
                        file3.renameTo(file);
                    }
                    C0278c.m39b("begin load jar");
                    file2.delete();
                    f31a = new DexClassLoader(file.getAbsolutePath(), context.getFilesDir().getAbsolutePath(), null, ClassLoader.getSystemClassLoader());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                classLoader = f31a;
            }
        }
        return classLoader;
    }

    public static Class<?> m25a(Context context, String str) {
        C0278c.m33a("zhouzhongbo", "getRemoteClass !!!");
        return C0277b.m26a(context).loadClass(str);
    }

    public static String m27a() {
        String str;
        IOException iOException;
        try {
            Properties properties = new Properties();
            InputStream resourceAsStream = C0277b.class.getResourceAsStream("/assets/adroi.config.properties");
            properties.load(resourceAsStream);
            String property = properties.getProperty("channel.id");
            if (property != null) {
                try {
                    if ("adroi.sdk".equals(property)) {
                        str = null;
                        resourceAsStream.close();
                        return str;
                    }
                } catch (IOException e) {
                    IOException iOException2 = e;
                    str = property;
                    iOException = iOException2;
                    iOException.printStackTrace();
                    return str;
                }
            }
            str = property;
            try {
                resourceAsStream.close();
            } catch (IOException e2) {
                iOException = e2;
                iOException.printStackTrace();
                return str;
            }
        } catch (IOException e3) {
            iOException = e3;
            str = null;
            iOException.printStackTrace();
            return str;
        }
        return str;
    }

    public static Bitmap m29b() {
        Bitmap decodeStream;
        IOException e;
        try {
            InputStream resourceAsStream = C0277b.class.getResourceAsStream("/assets/adroi_icon.png");
            decodeStream = BitmapFactory.decodeStream(resourceAsStream);
            try {
                resourceAsStream.close();
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                return decodeStream;
            }
        } catch (IOException e3) {
            IOException iOException = e3;
            decodeStream = null;
            e = iOException;
            e.printStackTrace();
            return decodeStream;
        }
        return decodeStream;
    }
}
