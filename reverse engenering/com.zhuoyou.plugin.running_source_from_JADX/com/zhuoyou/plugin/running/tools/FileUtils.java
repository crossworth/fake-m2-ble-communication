package com.zhuoyou.plugin.running.tools;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.util.ByteArrayBuffer;

public class FileUtils {
    private static final int BUF_SIZE = 32768;
    public static final int FAILED = -1;
    static final String LOG_TAG = "FileUtils";
    private static final Class<?>[] SIG_SET_PERMISSION = new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE};
    public static final int SUCCESS = 0;
    public static final int S_IRGRP = 32;
    public static final int S_IROTH = 4;
    public static final int S_IRUSR = 256;
    public static final int S_IRWXG = 56;
    public static final int S_IRWXO = 7;
    public static final int S_IRWXU = 448;
    public static final int S_IWGRP = 16;
    public static final int S_IWOTH = 2;
    public static final int S_IWUSR = 128;
    public static final int S_IXGRP = 8;
    public static final int S_IXOTH = 1;
    public static final int S_IXUSR = 64;
    private static WeakReference<Exception> exReference;

    public enum FileState {
        FState_Dir("I am director!"),
        FState_File("I am file!"),
        FState_None("I am a ghost!"),
        FState_Other("I am not human!");
        
        private String tag;

        private FileState(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return this.tag;
        }

        public String toString() {
            return this.tag;
        }
    }

    private FileUtils() {
    }

    public static FileState fileState(String path) {
        return fileState(new File(path));
    }

    public static FileState fileState(File file) {
        if (!file.exists()) {
            return FileState.FState_None;
        }
        if (file.isFile()) {
            return FileState.FState_File;
        }
        if (file.isDirectory()) {
            return FileState.FState_Dir;
        }
        return FileState.FState_Other;
    }

    public static int createDir(String path) {
        return createDir(new File(path));
    }

    public static int createDir(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                return 0;
            }
            file.delete();
        }
        if (file.mkdirs()) {
            return 0;
        }
        return -1;
    }

    public static int removeDir(String path) {
        return removeDir(new File(path));
    }

    public static int removeDir(File dir) {
        if (!dir.exists()) {
            return 0;
        }
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        removeDir(f);
                    } else {
                        f.delete();
                    }
                }
            }
        }
        if (dir.delete()) {
            return 0;
        }
        return -1;
    }

    public static void checkParentPath(String path) {
        checkParentPath(new File(path));
    }

    public static void checkParentPath(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.isDirectory()) {
            createDir(parent);
        }
    }

    public static int streamToFile(String path, InputStream is, boolean isAppend) {
        return streamToFile(new File(path), is, isAppend);
    }

    public static int streamToFile(File file, InputStream is, boolean isAppend) {
        Exception e;
        Throwable th;
        checkParentPath(file);
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fos = new FileOutputStream(file, isAppend);
            try {
                byte[] buf = new byte[32768];
                while (true) {
                    int readSize = is.read(buf);
                    if (readSize == -1) {
                        break;
                    }
                    fos.write(buf, 0, readSize);
                }
                fos.flush();
                try {
                    fos.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                fileOutputStream = fos;
                return 0;
            } catch (Exception e3) {
                e2 = e3;
                fileOutputStream = fos;
                try {
                    e2.printStackTrace();
                    try {
                        fileOutputStream.close();
                    } catch (Exception e22) {
                        e22.printStackTrace();
                    }
                    return -1;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        fileOutputStream.close();
                    } catch (Exception e222) {
                        e222.printStackTrace();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = fos;
                fileOutputStream.close();
                throw th;
            }
        } catch (Exception e4) {
            e222 = e4;
            e222.printStackTrace();
            fileOutputStream.close();
            return -1;
        }
    }

    public static int bytesToFile(File file, byte[] data, int offset, int length, boolean isAppend) {
        Throwable th;
        int i = -1;
        checkParentPath(file);
        if (data != null) {
            if (length <= 0) {
                length = data.length;
            }
            FileOutputStream fos = null;
            try {
                FileOutputStream fos2 = new FileOutputStream(file, isAppend);
                try {
                    fos2.write(data, offset, length);
                    fos2.flush();
                    i = 0;
                    try {
                        fos2.close();
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                    fos = fos2;
                    try {
                        fos.close();
                    } catch (Exception e3) {
                    }
                    return i;
                } catch (Throwable th2) {
                    th = th2;
                    fos = fos2;
                    try {
                        fos.close();
                    } catch (Exception e4) {
                    }
                    throw th;
                }
            } catch (Exception e5) {
                fos.close();
                return i;
            } catch (Throwable th3) {
                th = th3;
                fos.close();
                throw th;
            }
        }
        return i;
    }

    public static int bytesToFile(File file, byte[] data, boolean isAppend) {
        return bytesToFile(file, data, 0, data.length, isAppend);
    }

    public static int bytesToFile(File file, byte[] data) {
        return bytesToFile(file, data, 0, data.length, false);
    }

    public static int stringToFile(File file, String string) {
        return bytesToFile(file, string.getBytes());
    }

    public static int bytesToFile(String path, byte[] data, int offset, int length, boolean isAppend) {
        return bytesToFile(new File(path), data, offset, length, isAppend);
    }

    public static byte[] fileToBytes(String path, int offset, int length) {
        return fileToBytes(new File(path), offset, length);
    }

    public static byte[] fileToBytes(File file) {
        return fileToBytes(file, 0, 0);
    }

    public static String fileToString(File file) {
        byte[] data = fileToBytes(file);
        return data != null ? new String(data) : null;
    }

    public static byte[] fileToBytes(File file, int offset, int length) {
        Throwable th;
        if (length < 0 || !file.exists()) {
            return null;
        }
        InputStream is = null;
        try {
            InputStream is2 = new FileInputStream(file);
            if (length == 0) {
                try {
                    length = is2.available();
                } catch (Exception e) {
                    is = is2;
                    try {
                        is.close();
                    } catch (Exception e2) {
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    is = is2;
                    try {
                        is.close();
                    } catch (Exception e3) {
                    }
                    throw th;
                }
            }
            byte[] outBuf = new byte[length];
            is2.read(outBuf, offset, length);
            try {
                is2.close();
                return outBuf;
            } catch (Exception e4) {
                return outBuf;
            }
        } catch (Exception e5) {
            is.close();
            return null;
        } catch (Throwable th3) {
            th = th3;
            is.close();
            throw th;
        }
    }

    public static int copyTo(String dstPath, String srcPath) {
        return copyTo(new File(dstPath), new File(srcPath));
    }

    public static int copyTo(File dstFile, File srcFile) {
        Exception e;
        InputStream fis;
        Throwable th;
        int i = -1;
        if (fileState(srcFile) == FileState.FState_File) {
            FileInputStream fis2 = null;
            try {
                InputStream fis3 = new FileInputStream(srcFile);
                try {
                    i = streamToFile(dstFile, fis3, false);
                    try {
                        fis3.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } catch (Exception e3) {
                    e2 = e3;
                    fis = fis3;
                    try {
                        e2.printStackTrace();
                        try {
                            fis2.close();
                        } catch (Exception e22) {
                            e22.printStackTrace();
                        }
                        return i;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            fis2.close();
                        } catch (Exception e222) {
                            e222.printStackTrace();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fis = fis3;
                    fis2.close();
                    throw th;
                }
            } catch (Exception e4) {
                e222 = e4;
                e222.printStackTrace();
                fis2.close();
                return i;
            }
        }
        return i;
    }

    public static int assetToFile(Context context, String assetName, String path) {
        return assetToFile(context, assetName, new File(path));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int assetToFile(android.content.Context r3, java.lang.String r4, java.io.File r5) {
        /*
        r0 = 0;
        r1 = r3.getAssets();	 Catch:{ Exception -> 0x0012, all -> 0x0018 }
        r0 = r1.open(r4);	 Catch:{ Exception -> 0x0012, all -> 0x0018 }
        r1 = 0;
        r1 = streamToFile(r5, r0, r1);	 Catch:{ Exception -> 0x0012, all -> 0x0018 }
        r0.close();	 Catch:{ Exception -> 0x001d }
    L_0x0011:
        return r1;
    L_0x0012:
        r1 = move-exception;
        r0.close();	 Catch:{ Exception -> 0x001f }
    L_0x0016:
        r1 = -1;
        goto L_0x0011;
    L_0x0018:
        r1 = move-exception;
        r0.close();	 Catch:{ Exception -> 0x0021 }
    L_0x001c:
        throw r1;
    L_0x001d:
        r2 = move-exception;
        goto L_0x0011;
    L_0x001f:
        r1 = move-exception;
        goto L_0x0016;
    L_0x0021:
        r2 = move-exception;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.running.tools.FileUtils.assetToFile(android.content.Context, java.lang.String, java.io.File):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int assetToFileIfNotExist(android.content.Context r4, java.lang.String r5, java.io.File r6) {
        /*
        r1 = 0;
        r0 = 0;
        r2 = r4.getAssets();	 Catch:{ Exception -> 0x0024, all -> 0x002a }
        r0 = r2.open(r5);	 Catch:{ Exception -> 0x0024, all -> 0x002a }
        r2 = r0.available();	 Catch:{ Exception -> 0x0024, all -> 0x002a }
        r2 = (long) r2;	 Catch:{ Exception -> 0x0024, all -> 0x002a }
        r2 = checkExistBySize(r6, r2);	 Catch:{ Exception -> 0x0024, all -> 0x002a }
        if (r2 != 0) goto L_0x001e;
    L_0x0015:
        r1 = 0;
        r1 = streamToFile(r6, r0, r1);	 Catch:{ Exception -> 0x0024, all -> 0x002a }
        r0.close();	 Catch:{ Exception -> 0x002f }
    L_0x001d:
        return r1;
    L_0x001e:
        r0.close();	 Catch:{ Exception -> 0x0022 }
        goto L_0x001d;
    L_0x0022:
        r2 = move-exception;
        goto L_0x001d;
    L_0x0024:
        r1 = move-exception;
        r0.close();	 Catch:{ Exception -> 0x0031 }
    L_0x0028:
        r1 = -1;
        goto L_0x001d;
    L_0x002a:
        r1 = move-exception;
        r0.close();	 Catch:{ Exception -> 0x0033 }
    L_0x002e:
        throw r1;
    L_0x002f:
        r2 = move-exception;
        goto L_0x001d;
    L_0x0031:
        r1 = move-exception;
        goto L_0x0028;
    L_0x0033:
        r2 = move-exception;
        goto L_0x002e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.running.tools.FileUtils.assetToFileIfNotExist(android.content.Context, java.lang.String, java.io.File):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] assetToBytes(android.content.Context r5, java.lang.String r6) {
        /*
        r2 = 0;
        r3 = r5.getAssets();	 Catch:{ Exception -> 0x0016 }
        r2 = r3.open(r6);	 Catch:{ Exception -> 0x0016 }
        r3 = r2.available();	 Catch:{ Exception -> 0x0016 }
        r0 = new byte[r3];	 Catch:{ Exception -> 0x0016 }
        r2.read(r0);	 Catch:{ Exception -> 0x0016 }
        r2.close();	 Catch:{ Exception -> 0x0024 }
    L_0x0015:
        return r0;
    L_0x0016:
        r1 = move-exception;
        setLastException(r1);	 Catch:{ all -> 0x001f }
        r2.close();	 Catch:{ Exception -> 0x0026 }
    L_0x001d:
        r0 = 0;
        goto L_0x0015;
    L_0x001f:
        r3 = move-exception;
        r2.close();	 Catch:{ Exception -> 0x0028 }
    L_0x0023:
        throw r3;
    L_0x0024:
        r3 = move-exception;
        goto L_0x0015;
    L_0x0026:
        r3 = move-exception;
        goto L_0x001d;
    L_0x0028:
        r4 = move-exception;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.running.tools.FileUtils.assetToBytes(android.content.Context, java.lang.String):byte[]");
    }

    public static String assetToString(Context context, String name) {
        byte[] data = assetToBytes(context, name);
        return data != null ? new String(data) : null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean assetExist(android.content.res.AssetManager r3, java.lang.String r4) {
        /*
        r0 = 0;
        r0 = r3.open(r4);	 Catch:{ IOException -> 0x000a, all -> 0x0010 }
        r1 = 1;
        r0.close();	 Catch:{ Exception -> 0x0015 }
    L_0x0009:
        return r1;
    L_0x000a:
        r1 = move-exception;
        r0.close();	 Catch:{ Exception -> 0x0017 }
    L_0x000e:
        r1 = 0;
        goto L_0x0009;
    L_0x0010:
        r1 = move-exception;
        r0.close();	 Catch:{ Exception -> 0x0019 }
    L_0x0014:
        throw r1;
    L_0x0015:
        r2 = move-exception;
        goto L_0x0009;
    L_0x0017:
        r1 = move-exception;
        goto L_0x000e;
    L_0x0019:
        r2 = move-exception;
        goto L_0x0014;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.running.tools.FileUtils.assetExist(android.content.res.AssetManager, java.lang.String):boolean");
    }

    public static boolean isSDMounted() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isSDAvailable(int minRemainMB) {
        return isSDAvailable() && getSDLeftSpace() >= (((long) minRemainMB) * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }

    public static boolean isSDAvailable() {
        if (!isSDMounted()) {
            return false;
        }
        File file = Environment.getExternalStorageDirectory();
        if (file.canRead() && file.canWrite()) {
            return true;
        }
        return false;
    }

    public static long getSDLeftSpace() {
        if (!isSDMounted()) {
            return 0;
        }
        StatFs statfs = new StatFs(Environment.getExternalStorageDirectory() + File.separator);
        return ((long) statfs.getAvailableBlocks()) * ((long) statfs.getBlockSize());
    }

    public static String coverSize(long size) {
        String s = "";
        if (size < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return s + size + "b";
        }
        if (size < 1048576) {
            return String.format(Locale.getDefault(), "%.1fK", new Object[]{Float.valueOf(((float) size) / 1024.0f)});
        } else if (size < 1073741824) {
            return String.format(Locale.getDefault(), "%.1fM", new Object[]{Float.valueOf(((float) (size / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) / 1024.0f)});
        } else {
            return String.format(Locale.getDefault(), "%.1fG", new Object[]{Float.valueOf(((float) ((size / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) / 1024.0f)});
        }
    }

    public static long getROMLeft() {
        StatFs sf = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        long blockSize = (long) sf.getBlockSize();
        long availCount = (long) sf.getAvailableBlocks();
        Log.i("", "ROM Total:" + coverSize(blockSize * ((long) sf.getBlockCount())) + ", Left:" + coverSize(availCount * blockSize));
        return availCount * blockSize;
    }

    public static String getDirPathInPrivate(Context context, String name) {
        return context.getDir(name, 0).getAbsolutePath() + File.separator;
    }

    public static String getSoPath(Context context) {
        return context.getApplicationInfo().dataDir + "/lib/";
    }

    public static FileLock tryFileLock(String path) {
        return tryFileLock(new File(path));
    }

    public static FileLock tryFileLock(File file) {
        try {
            checkParentPath(file);
            FileLock fl = new FileOutputStream(file).getChannel().tryLock();
            if (fl.isValid()) {
                Log.i(LOG_TAG, "tryFileLock " + file + " SUC!");
                return fl;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "tryFileLock " + file + " FAIL! " + e.getMessage());
        }
        return null;
    }

    public static void freeFileLock(FileLock fl, File file) {
        if (file != null) {
            file.delete();
        }
        if (fl != null && fl.isValid()) {
            try {
                fl.release();
                Log.i(LOG_TAG, "freeFileLock " + file + " SUC!");
            } catch (IOException e) {
            }
        }
    }

    public static String getPathName(String absolutePath) {
        return absolutePath.substring(absolutePath.lastIndexOf(File.separator) + 1, absolutePath.length());
    }

    public static boolean reNamePath(String oldName, String newName) {
        return new File(oldName).renameTo(new File(newName));
    }

    public static List<String> listPath(String root) {
        List<String> allDir = new ArrayList();
        SecurityManager checker = new SecurityManager();
        File path = new File(root);
        checker.checkRead(root);
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory()) {
                    allDir.add(f.getAbsolutePath());
                }
            }
        }
        return allDir;
    }

    public static int deleteBlankPath(String path) {
        File f = new File(path);
        if (!f.canWrite()) {
            return 1;
        }
        if (f.list() != null && f.list().length > 0) {
            return 2;
        }
        if (f.delete()) {
            return 0;
        }
        return 3;
    }

    public static String getSDRoot() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    public static boolean checkExistBySize(File file, long size) {
        return file.exists() && file.isFile() && file.length() == size;
    }

    public static int setPermissions(String file, int mode) {
        return setPermissions(file, mode, -1, -1);
    }

    public static int setPermissions(String file, int mode, int uid, int gid) {
        try {
            Method method = Class.forName("android.os.FileUtils").getDeclaredMethod("setPermissions", SIG_SET_PERMISSION);
            method.setAccessible(true);
            return ((Integer) method.invoke(null, new Object[]{file, Integer.valueOf(mode), Integer.valueOf(uid), Integer.valueOf(gid)})).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean createLink(String src, String dst) {
        try {
            Process ps = Runtime.getRuntime().exec(String.format("ln -s %s %s", new Object[]{src, dst}));
            InputStream in = ps.getInputStream();
            while (true) {
                int c = in.read();
                if (c != -1) {
                    System.out.print(c);
                } else {
                    in.close();
                    ps.waitFor();
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static ByteArrayBuffer streamToByteArray(InputStream is) {
        try {
            byte[] buf = new byte[256];
            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(1024);
            while (true) {
                int read = is.read(buf);
                if (read == -1) {
                    return byteArrayBuffer;
                }
                byteArrayBuffer.append(buf, 0, read);
            }
        } catch (Exception e) {
            setLastException(e);
            return null;
        }
    }

    public static String streamToString(InputStream is) {
        ByteArrayBuffer buffer = streamToByteArray(is);
        if (buffer != null) {
            return new String(buffer.buffer(), 0, buffer.length());
        }
        return null;
    }

    public static void printLastException() {
        Exception e = getLastException();
        if (e != null) {
            e.printStackTrace();
        }
    }

    private static void setLastException(Exception e) {
        exReference = new WeakReference(e);
    }

    public static Exception getLastException() {
        return exReference != null ? (Exception) exReference.get() : null;
    }
}
