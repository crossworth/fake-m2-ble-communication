package com.zhuoyou.plugin.bluetooth.attach;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PlugUtils {
    public static void uninstallUseIntent(String packageName, Activity a) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent("android.intent.action.DELETE");
        intent.setData(packageURI);
        a.startActivity(intent);
    }

    private static Class<?>[] getParamTypes(Class<?> cls, String mName) {
        Class<?>[] cs = null;
        Method[] mtd = cls.getMethods();
        for (int i = 0; i < mtd.length; i++) {
            if (mtd[i].getName().equals(mName)) {
                cs = mtd[i].getParameterTypes();
            }
        }
        return cs;
    }

    public static Object invoke(PlugBean bean, String method_name) {
        try {
            return bean.mClasses.getMethod(method_name, new Class[0]).invoke(bean.mInstance, new Object[0]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static Object invoke_method(PlugBean bean, String method_name, char[] tag, String content) {
        try {
            Class<?>[] params = getParamTypes(bean.mClasses, method_name);
            Method target = bean.mClasses.getMethod(method_name, params);
            if (params == null || params.length == 0) {
                return target.invoke(bean.mInstance, new Object[0]);
            }
            return target.invoke(bean.mInstance, new Object[]{tag, content});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static boolean writeStreamToFile(InputStream stream, File file) {
        boolean ret = false;
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (Throwable th) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            byte[] buffer = new byte[1024];
            while (true) {
                int read = stream.read(buffer);
                if (read == -1) {
                    break;
                }
                output.write(buffer, 0, read);
            }
            output.flush();
            ret = true;
            output.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th2) {
            output.close();
        }
        try {
            stream.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return ret;
    }

    public static boolean canCopy(String sdcard) {
        StatFs statFs = new StatFs(new File(sdcard).getPath());
        if (((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID > 20) {
            return true;
        }
        return false;
    }
}
