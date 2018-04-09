package com.zhuoyi.system.util;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import com.zhuoyi.system.util.constant.FileConstants;
import java.io.BufferedReader;
import java.io.FileReader;

public class PhoneInfoUtils {
    public static final String CHANNEL_ID_KEY = "channelid";
    public static String DIR_NAME = "phoneInfo";
    public static String PATH = ("/sdcard/" + FileConstants.FILE_ROOT);
    public static String UUID_FILE = "uuidInfo";

    public static int getTotalMemory() {
        int initial_memory = 0;
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            initial_memory = Integer.valueOf(localBufferedReader.readLine().split("\\s+")[1]).intValue() / 1024;
            localBufferedReader.close();
            return initial_memory;
        } catch (Exception e) {
            return initial_memory;
        }
    }

    public static int getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        return (int) (mi.availMem / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
    }

    public static boolean isSDExists() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static long getSDcardRoom() {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return 0;
        }
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) sf.getBlockSize()) * ((long) sf.getBlockCount());
    }

    public static long getAvailableSDcardRoom() {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return 0;
        }
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) sf.getBlockSize()) * ((long) sf.getAvailableBlocks());
    }

    public static long getMobileRomRoom() {
        StatFs sf = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) sf.getBlockSize()) * ((long) sf.getBlockCount());
    }

    public static long getAvailableMobileRoom() {
        StatFs sf = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) sf.getBlockSize()) * ((long) sf.getAvailableBlocks());
    }

    public long getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static String getNativePhoneNumber(Context context) {
        String nativePhoneNumber = null;
        try {
            nativePhoneNumber = ((TelephonyManager) context.getSystemService("phone")).getLine1Number();
        } catch (Exception e) {
        }
        return nativePhoneNumber;
    }
}
