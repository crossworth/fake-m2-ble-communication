package com.zhuoyou.plugin.running.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.util.EncodingUtils;

@SuppressLint({"ParserError"})
public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler INSTANCE = new CrashHandler();
    public static final String LOG_DIR = "/Running/crash";
    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().getPath();
    public static final String TAG = "CrashHandler";
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    private Map<String, String> infos = new HashMap();
    private Context mContext;
    private UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        if (handleException(ex) || this.mDefaultHandler == null) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            reStartProcess();
            return;
        }
        this.mDefaultHandler.uncaughtException(thread, ex);
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        collectDeviceInfo(this.mContext);
        saveCrashInfo2File(ex, LOG_DIR);
        return true;
    }

    public static boolean isApkDebugable(Context context) {
        try {
            if ((context.getApplicationInfo().flags & 2) != 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void collectDeviceInfo(Context ctx) {
        try {
            PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 1);
            if (pi != null) {
                String versionCode = pi.versionCode + "";
                this.infos.put("versionName", pi.versionName == null ? "null" : pi.versionName);
                this.infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                this.infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e2) {
                Log.e(TAG, "an error occured when collect crash info", e2);
            }
        }
    }

    private int getPackageVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 1).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String saveCrashInfo2File(Throwable ex, String logPath) {
        StringBuffer sb = new StringBuffer();
        for (Entry<String, String> entry : this.infos.entrySet()) {
            String value = (String) entry.getValue();
            sb.append(((String) entry.getKey()) + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        sb.append(writer.toString());
        try {
            String fileName = this.mContext.getPackageName() + "_crash-" + this.formatter.format(new Date()) + "-" + System.currentTimeMillis() + "-" + ((String) this.infos.get("versionCode")) + ".log";
            if (!Environment.getExternalStorageState().equals("mounted")) {
                return fileName;
            }
            String path = SD_CARD_PATH + logPath;
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(path + File.separator + fileName);
            fos.write(EncodingUtils.getBytes(sb.toString(), "UTF-8"));
            fos.close();
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file", e);
            return null;
        }
    }

    public String[] getCrashReportFileNames(final Context context) {
        return new File(SD_CARD_PATH + LOG_DIR).list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (CrashHandler.this.deleteFileByVersionCode(new File(dir, name), CrashHandler.this.getPackageVersionCode(context))) {
                    return false;
                }
                return true;
            }
        });
    }

    public void sendCrashReportsToServer(Context ctx) {
        if (!isApkDebugable(ctx)) {
            String[] reportFilesNames = getCrashReportFileNames(ctx);
            if (reportFilesNames != null && reportFilesNames.length > 0) {
                File[] files = new File[reportFilesNames.length];
                for (int i = 0; i < reportFilesNames.length; i++) {
                    File file = new File(SD_CARD_PATH + LOG_DIR, reportFilesNames[i]);
                    if (file.exists()) {
                        files[i] = file;
                    }
                }
            }
        }
    }

    public void deleteFiles(File[] files, String fileIndexs) {
        if (!TextUtils.isEmpty(fileIndexs)) {
            try {
                for (String str : fileIndexs.split(",")) {
                    int i = Integer.parseInt(str);
                    if (i <= files.length - 1) {
                        files[i].delete();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void reStartProcess() {
        Intent launchIntent = this.mContext.getPackageManager().getLaunchIntentForPackage(this.mContext.getPackageName());
        if (launchIntent != null) {
            launchIntent.addFlags(67108864);
            this.mContext.startActivity(launchIntent);
        }
        Process.killProcess(Process.myPid());
    }

    public String getResolution(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels + "x" + dm.widthPixels;
    }

    private boolean deleteFileByVersionCode(File file, int code) {
        if (file == null || file.getName().endsWith("-" + code + ".log")) {
            return false;
        }
        file.delete();
        return true;
    }
}
