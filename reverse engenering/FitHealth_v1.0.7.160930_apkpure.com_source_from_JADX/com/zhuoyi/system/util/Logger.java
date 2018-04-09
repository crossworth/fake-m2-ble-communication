package com.zhuoyi.system.util;

import android.util.Log;
import com.zhuoyi.system.config.ZySDKConfig;
import com.zhuoyi.system.util.constant.FileConstants;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static final String OpenSns = "ZySDKLog";

    public static void error(String TAG, String msg, Throwable e) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            Log.e(TAG, msg, e);
        }
    }

    public static void error(String TAG, String msg) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            Log.e(TAG, msg);
        }
    }

    public static void error(String msg) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            Log.e(OpenSns, msg);
        }
    }

    public static void debug(String TAG, String msg) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            Log.d(TAG, msg);
        }
    }

    public static void debug(String msg) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            Log.d(OpenSns, msg);
        }
    }

    public static void logD(String TAG, String str) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            Log.d(TAG, str);
        }
    }

    public static void m3374i(String TAG, String str) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            Log.i(TAG, str);
        }
    }

    public static void m3372d(String TAG, String str) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            Log.d(TAG, str);
        }
    }

    public static void m3376w(String TAG, String str) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            Log.w(TAG, str);
        }
    }

    public static void m3373e(String TAG, String str) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            Log.e(TAG, str);
        }
    }

    public static void printLogToFile(String tag, String str) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            File dir = new File("/sdcard/" + FileConstants.FILE_ROOT + "/log");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, tag);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            long time = System.currentTimeMillis();
            try {
                FileUtils.writeFile(file, new StringBuilder(String.valueOf(str)).append(" -- ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(time))).append("\n").toString(), true);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void deleteLogFile(String tag) {
        File f = new File("/sdcard/" + FileConstants.FILE_ROOT + "/log/" + tag);
        if (f.exists()) {
            f.delete();
        }
    }

    public static void m3375p(Throwable e) {
        if (ZySDKConfig.getInstance().isOpenLog()) {
            e.printStackTrace();
        }
    }
}
