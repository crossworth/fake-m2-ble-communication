package com.zhuoyou.plugin.download;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;
import com.facebook.internal.AnalyticsEvents;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Util_update {

    public static final class AppInfo {
        public Drawable appicon = null;
        public String appname = "";
        public String packagename = "";
        public int versionCode = 0;
        public String versionName = "";
    }

    public static final class AppInfoManager {
        public static final int type_all = 2;
        public static final int type_download = 1;
        public static final int type_system = 0;
        ArrayList<AppInfo> appList = new ArrayList();
        ContextWrapper contextwrapper;
        List<PackageInfo> packages;

        public AppInfoManager(ContextWrapper contextwrapper) {
            this.contextwrapper = contextwrapper;
        }

        public ArrayList<AppInfo> getAppInfo(int type) {
            if (this.packages == null) {
                this.packages = this.contextwrapper.getPackageManager().getInstalledPackages(0);
            }
            for (int i = 0; i < this.packages.size(); i++) {
                PackageInfo packageInfo = (PackageInfo) this.packages.get(i);
                AppInfo tmpInfo = new AppInfo();
                tmpInfo.appname = packageInfo.applicationInfo.loadLabel(this.contextwrapper.getPackageManager()).toString();
                tmpInfo.packagename = packageInfo.packageName;
                tmpInfo.versionName = packageInfo.versionName;
                tmpInfo.versionCode = packageInfo.versionCode;
                tmpInfo.appicon = packageInfo.applicationInfo.loadIcon(this.contextwrapper.getPackageManager());
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                switch (type) {
                    case 0:
                        if ((applicationInfo.flags & 1) <= 0) {
                            break;
                        }
                        this.appList.add(tmpInfo);
                        break;
                    case 1:
                        if ((applicationInfo.flags & 1) != 0) {
                            break;
                        }
                        this.appList.add(tmpInfo);
                        break;
                    case 2:
                        this.appList.add(tmpInfo);
                        break;
                    default:
                        break;
                }
            }
            return this.appList;
        }

        public static void AppInstall(String filePath, Context act) {
            File f = new File(filePath);
            Intent i = new Intent();
            i.setFlags(268435456);
            i.setAction("android.intent.action.VIEW");
            i.setDataAndType(Uri.fromFile(f), "application/vnd.android.package-archive");
            act.startActivity(i);
        }

        public static void AppUnInstall(String packagePath, Activity act) {
            act.startActivity(new Intent("android.intent.action.DELETE", Uri.parse("package:" + packagePath)));
        }
    }

    public static final class FileManage {
        public static final String[] MusicEndWith = new String[]{".mp3", ".midi", ".mid", ".rm", ".wma"};
        Folder root;

        public static class FileHolder {
            public long availSpace;
            public long totalSpace;

            public FileHolder(long totalSpace, long availSpace) {
                this.totalSpace = totalSpace;
                this.availSpace = availSpace;
            }
        }

        public Folder getRoot() {
            return this.root;
        }

        public void setRoot(Folder root) {
            this.root = root;
        }

        public void initRoot() {
            this.root = new Folder();
            this.root.name = "sdcard";
            this.root.path = getSDPath();
        }

        public static String getSDPath() {
            if (Environment.getExternalStorageState().equals("mounted")) {
                return Environment.getExternalStorageDirectory().toString();
            }
            return null;
        }

        public static FileHolder readSDCardSpace() {
            if (!"mounted".equals(Environment.getExternalStorageState())) {
                return null;
            }
            StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long blockSize = (long) sf.getBlockSize();
            return new FileHolder(blockSize * ((long) sf.getBlockCount()), blockSize * ((long) sf.getAvailableBlocks()));
        }

        void readSystem() {
            StatFs sf = new StatFs(Environment.getRootDirectory().getPath());
            long blockSize = (long) sf.getBlockSize();
            long blockCount = (long) sf.getBlockCount();
            long availCount = (long) sf.getAvailableBlocks();
        }

        public static int getFileSize(String path) {
            File f = new File(path);
            if (f.exists()) {
                return ((int) f.length()) / 1024;
            }
            return 0;
        }

        public void traversal(Folder root) {
            File[] files = new File(root.path).listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    Folder root1 = new Folder();
                    root1.path = files[i].getAbsolutePath();
                    root1.name = files[i].getName();
                    root1.prefolder = root;
                    if (files[i].isDirectory()) {
                        root1.isfolder = true;
                        traversal(root1);
                        root.list.add(root1);
                    } else if (isMusicFile(root1.path)) {
                        root1.isfolder = false;
                        root1.size = getFileSize(root1.path);
                        root.list.add(root1);
                    }
                }
            }
        }

        public static Folder searchPath(Folder root, String path) {
            if (root.getPath().equals(path)) {
                return root;
            }
            ArrayList<Folder> folderlist = root.getList();
            int i = 0;
            while (i < folderlist.size() && searchPath((Folder) folderlist.get(i), path) == null) {
                i++;
            }
            return null;
        }

        public static void newPathFolder(String folderPath) {
            int start1 = 0;
            int start2 = folderPath.indexOf("/");
            while (start2 > -1) {
                newFolder(folderPath.substring(0, start2));
                start1 = start2;
                start2 = folderPath.indexOf("/", start1 + 1);
            }
            if (start1 < folderPath.length() - 1) {
                newFolder(folderPath);
            }
        }

        public static void newFolder(String folderPath) {
            if (folderPath != null && !folderPath.equals("")) {
                try {
                    File myFilePath = new File(folderPath);
                    if (myFilePath.exists()) {
                        Log.v("mytag", "folderPath is exists " + folderPath);
                    } else if (!myFilePath.mkdirs()) {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static boolean delFolder(String path) {
            delAllFileInFolder(path);
            return delFile(path);
        }

        public static boolean delFile(String path) {
            File delPath = new File(path);
            if (delPath.isFile()) {
                return delPath.delete();
            }
            return false;
        }

        public static void delAllFileInFolder(String path) {
            File file = new File(path);
            if (file.exists() && file.isDirectory()) {
                String[] tempList = file.list();
                for (int i = 0; i < tempList.length; i++) {
                    File temp;
                    if (path.endsWith(File.separator)) {
                        temp = new File(path + tempList[i]);
                    } else {
                        temp = new File(path + File.separator + tempList[i]);
                    }
                    if (temp.isFile()) {
                        temp.delete();
                    }
                    if (temp.isDirectory()) {
                        delAllFileInFolder(path + "/" + tempList[i]);
                        delFile(path + "/" + tempList[i]);
                    }
                }
            }
        }

        public boolean isMusicFile(String path) {
            for (String endsWith : MusicEndWith) {
                if (path.endsWith(endsWith)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static final class Folder {
        boolean isfolder = true;
        ArrayList<Folder> list = new ArrayList();
        String name;
        String path;
        Folder prefolder;
        int size;

        public Folder(String path) {
            this.path = path;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isIsfolder() {
            return this.isfolder;
        }

        public void setIsfolder(boolean isfolder) {
            this.isfolder = isfolder;
        }

        public ArrayList<Folder> getList() {
            return this.list;
        }

        public void setList(ArrayList<Folder> list) {
            this.list = list;
        }

        public Folder getPrefolder() {
            return this.prefolder;
        }

        public void setPrefolder(Folder prefolder) {
            this.prefolder = prefolder;
        }

        public int getSize() {
            return this.size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static final class TimeManager {
        public static final long UNIT_DAY = 86400000;
        public static final long UNIT_HOUR = 3600000;
        public static final long UNIT_MILLISECOND = 1;
        public static final long UNIT_MINUTE = 60000;
        public static final long UNIT_SECOND = 1000;

        public static long TimeConvert(long time, long unitFrom, long unitTo) {
            return (time * unitFrom) / unitTo;
        }

        public static long TimeConvertToMillisecond(long time, long unitFrom) {
            return (time * unitFrom) / 1;
        }

        public static long getTimeMillis(int hour, int minute) {
            return getTimeMillis(null, -1, -1, -1, hour, minute);
        }

        public static long getTimeMillis(Calendar c, int hour, int minute) {
            return getTimeMillis(c, -1, -1, -1, hour, minute);
        }

        public static long getTimeMillis(Calendar c, int year, int month, int day, int hour, int minute) {
            if (c == null) {
                c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
            }
            if (year >= 0) {
                c.set(1, year);
            }
            if (month >= 0) {
                c.set(2, month);
            }
            if (day >= 0) {
                c.set(5, day);
            }
            if (hour >= 0) {
                c.set(11, hour);
            }
            if (minute >= 0) {
                c.set(12, minute);
            }
            c.set(13, 0);
            return c.getTimeInMillis();
        }

        public static String getDataFormat(long timeMillis) {
            if (timeMillis == 0) {
                return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
            }
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeMillis));
        }
    }

    public static final class ToastManager {
        public static void show(Context context, String text) {
            try {
                Toast.makeText(context, text, 0).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static InputStream getImageUrl(String oldUrl) {
        InputStream is = null;
        String url = oldUrl;
        try {
            String[] arr = url.split("/");
            String url1 = arr[arr.length - 1].replace(".jpg", "");
            url = url.replace(url1, URLEncoder.encode(url1, "gbk"));
            Log.e("url", "image=" + url);
            is = new URL(url).openStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (IndexOutOfBoundsException e3) {
            e3.printStackTrace();
        }
        return is;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getTD(android.content.Context r6, int r7) {
        /*
        r5 = r6.getResources();
        r3 = r5.openRawResource(r7);
        r1 = new java.io.DataInputStream;
        r1.<init>(r3);
        r0 = 0;
        r5 = r3.available();	 Catch:{ IOException -> 0x0028 }
        r0 = new byte[r5];	 Catch:{ IOException -> 0x0028 }
        r1.readFully(r0);	 Catch:{ IOException -> 0x0028 }
        r1.close();	 Catch:{ IOException -> 0x0023 }
        r3.close();	 Catch:{ IOException -> 0x0023 }
    L_0x001d:
        r4 = new java.lang.String;
        r4.<init>(r0);
        return r4;
    L_0x0023:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x001d;
    L_0x0028:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x0038 }
        r1.close();	 Catch:{ IOException -> 0x0033 }
        r3.close();	 Catch:{ IOException -> 0x0033 }
        goto L_0x001d;
    L_0x0033:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x001d;
    L_0x0038:
        r5 = move-exception;
        r1.close();	 Catch:{ IOException -> 0x0040 }
        r3.close();	 Catch:{ IOException -> 0x0040 }
    L_0x003f:
        throw r5;
    L_0x0040:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.download.Util_update.getTD(android.content.Context, int):java.lang.String");
    }
}
