package com.umeng.socialize.net.stats.cache;

import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.umeng.socialize.net.utils.AesHelper;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/* compiled from: StatsLogCache */
public class C1665c {
    private static final String f4997a = C1665c.class.getSimpleName();
    private static final String f4998b = ".log";
    private final int f4999c = 32;
    private final int f5000d = 512;
    private final int f5001e = 50;
    private final int f5002f = 8;
    private String f5003g = null;

    /* compiled from: StatsLogCache */
    class C16631 implements Comparator<File> {
        final /* synthetic */ C1665c f4994a;

        C16631(C1665c c1665c) {
            this.f4994a = c1665c;
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m4523a((File) obj, (File) obj2);
        }

        public int m4523a(File file, File file2) {
            return Long.valueOf(file.length()).compareTo(Long.valueOf(file2.length()));
        }
    }

    /* compiled from: StatsLogCache */
    public static class C1664a {
        private String f4995a;
        private List<String> f4996b = new ArrayList();

        public String m4524a() {
            return this.f4995a;
        }

        public void m4525a(String str) {
            this.f4995a = str;
        }

        public List<String> m4527b() {
            return this.f4996b;
        }

        public void m4526a(List<String> list) {
            this.f4996b = list;
        }
    }

    public C1665c(String str) {
        this.f5003g = str;
        Log.m4546d(f4997a, "dir path" + this.f5003g);
    }

    public boolean m4543a(String str) {
        Closeable a;
        IOException e;
        Closeable closeable;
        Closeable outputStreamWriter;
        Throwable th;
        Closeable closeable2 = null;
        File e2 = m4538e();
        File a2 = m4529a(e2);
        if (a2 == null) {
            return false;
        }
        double b = C1665c.m4531b();
        if (b < 50.0d) {
            Log.m4549e(f4997a, "InternalMemory beyond the min limit, current size is:" + b);
            return false;
        } else if (m4541g(e2)) {
            Log.m4549e(f4997a, "dir size beyond the max limit");
            return false;
        } else {
            C1657a c1657a = new C1657a(a2);
            try {
                a = c1657a.m4510a(true);
            } catch (IOException e3) {
                e3.printStackTrace();
                m4544b(a2.getName());
                a = null;
            }
            if (a == null) {
                return false;
            }
            Object encryptNoPadding;
            try {
                encryptNoPadding = AesHelper.encryptNoPadding(str, "UTF-8");
                try {
                    boolean z;
                    if (TextUtils.isEmpty(encryptNoPadding)) {
                        closeable = null;
                        z = false;
                    } else {
                        outputStreamWriter = new OutputStreamWriter(a);
                        try {
                            closeable = new BufferedWriter(outputStreamWriter);
                        } catch (IOException e4) {
                            e3 = e4;
                            try {
                                c1657a.m4513b(a);
                                e3.printStackTrace();
                                m4530a(closeable2);
                                m4530a(outputStreamWriter);
                                m4530a(a);
                                return false;
                            } catch (Throwable th2) {
                                th = th2;
                                m4530a(closeable2);
                                m4530a(outputStreamWriter);
                                m4530a(a);
                                throw th;
                            }
                        }
                        try {
                            closeable.write(encryptNoPadding);
                            closeable.newLine();
                            closeable.flush();
                            c1657a.m4511a((FileOutputStream) a);
                            closeable2 = outputStreamWriter;
                            z = true;
                        } catch (IOException e5) {
                            IOException iOException = e5;
                            closeable2 = closeable;
                            e3 = iOException;
                            c1657a.m4513b(a);
                            e3.printStackTrace();
                            m4530a(closeable2);
                            m4530a(outputStreamWriter);
                            m4530a(a);
                            return false;
                        } catch (Throwable th3) {
                            Throwable th4 = th3;
                            closeable2 = closeable;
                            th = th4;
                            m4530a(closeable2);
                            m4530a(outputStreamWriter);
                            m4530a(a);
                            throw th;
                        }
                    }
                    m4530a(closeable);
                    m4530a(closeable2);
                    m4530a(a);
                    return z;
                } catch (IOException e6) {
                    e3 = e6;
                    outputStreamWriter = null;
                    c1657a.m4513b(a);
                    e3.printStackTrace();
                    m4530a(closeable2);
                    m4530a(outputStreamWriter);
                    m4530a(a);
                    return false;
                } catch (Throwable th5) {
                    th = th5;
                    outputStreamWriter = null;
                    m4530a(closeable2);
                    m4530a(outputStreamWriter);
                    m4530a(a);
                    throw th;
                }
            } catch (Exception e7) {
                e7.printStackTrace();
                Log.m4549e(f4997a, "save log encrypt error");
                encryptNoPadding = null;
            }
        }
    }

    private void m4530a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private File m4529a(File file) {
        if (file == null || !file.isDirectory()) {
            return null;
        }
        if (file.list().length <= 0) {
            return m4534c(file);
        }
        File b = m4533b(file);
        if (b == null) {
            return m4534c(file);
        }
        return b;
    }

    private File m4533b(File file) {
        File[] d = m4537d(file);
        if (d == null || d.length <= 0) {
            return null;
        }
        int i = 0;
        while (i < d.length) {
            File file2 = d[i];
            if (!file2.getName().endsWith(f4998b)) {
                m4544b(file2.getName());
                i++;
            } else if (m4532b(file2.length()) > 32.0d) {
                return null;
            } else {
                return file2;
            }
        }
        return null;
    }

    private double m4532b(long j) {
        return j <= 0 ? 0.0d : ((double) j) / 1024.0d;
    }

    private File m4534c(File file) {
        if (file == null || !file.isDirectory()) {
            return null;
        }
        return new File(file, m4535c());
    }

    private String m4535c() {
        return String.valueOf(System.currentTimeMillis()) + f4998b;
    }

    private File[] m4537d(File file) {
        if (file == null || !file.isDirectory()) {
            return null;
        }
        File[] listFiles = file.listFiles();
        Arrays.sort(listFiles, m4536d());
        return listFiles;
    }

    private Comparator<File> m4536d() {
        return new C16631(this);
    }

    private File m4538e() {
        if (TextUtils.isEmpty(this.f5003g)) {
            Log.m4546d(f4997a, "Couldn't create directory mDirPath is null");
            return null;
        }
        File file = new File(this.f5003g);
        if (file.exists() || file.mkdirs()) {
            return file;
        }
        Log.m4546d(f4997a, "Couldn't create directory" + this.f5003g);
        return null;
    }

    public boolean m4544b(String str) {
        File e = m4538e();
        if (e == null) {
            return false;
        }
        boolean delete = new File(e, str).delete();
        Log.m4546d(f4997a, "deleteFile:" + str + ";result:" + delete);
        return delete;
    }

    public C1664a m4542a() {
        IOException e;
        Closeable closeable;
        Throwable th;
        File e2 = m4539e(m4538e());
        if (e2 == null) {
            return null;
        }
        Closeable c;
        try {
            c = new C1657a(e2).m4514c();
        } catch (IOException e3) {
            e3.printStackTrace();
            m4544b(e2.getName());
            c = null;
        }
        if (c == null) {
            return null;
        }
        Closeable inputStreamReader;
        Closeable bufferedReader;
        try {
            C1664a c1664a = new C1664a();
            c1664a.m4525a(e2.getName());
            inputStreamReader = new InputStreamReader(c);
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                try {
                    List arrayList = new ArrayList();
                    int i = 0;
                    while (true) {
                        Object readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        int i2 = i + 1;
                        Log.m4546d(f4997a, "read file:" + i2 + readLine);
                        if (TextUtils.isEmpty(readLine)) {
                            i = i2;
                        } else {
                            Object replaceAll;
                            try {
                                replaceAll = AesHelper.decryptNoPadding(readLine, "UTF-8").replaceAll("\u0000", "");
                            } catch (Exception e4) {
                                e4.printStackTrace();
                                Log.m4549e(f4997a, "read log decrypt error");
                                replaceAll = null;
                            }
                            if (TextUtils.isEmpty(replaceAll) || !replaceAll.contains(SocializeProtocolConstants.PROTOCOL_KEY_VERSION)) {
                                Log.m4549e(f4997a, "read log content error");
                            } else {
                                arrayList.add(replaceAll);
                            }
                            i = i2;
                        }
                    }
                    if (arrayList.isEmpty()) {
                        m4544b(c1664a.m4524a());
                        m4530a(c);
                        m4530a(inputStreamReader);
                        m4530a(bufferedReader);
                        return null;
                    }
                    c1664a.m4526a(arrayList);
                    m4530a(c);
                    m4530a(inputStreamReader);
                    m4530a(bufferedReader);
                    return c1664a;
                } catch (IOException e5) {
                    e3 = e5;
                    closeable = bufferedReader;
                    bufferedReader = inputStreamReader;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e6) {
                e3 = e6;
                closeable = null;
                bufferedReader = inputStreamReader;
                try {
                    e3.printStackTrace();
                    m4530a(c);
                    m4530a(bufferedReader);
                    m4530a(closeable);
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    inputStreamReader = bufferedReader;
                    bufferedReader = closeable;
                    m4530a(c);
                    m4530a(inputStreamReader);
                    m4530a(bufferedReader);
                    throw th;
                }
            } catch (Throwable th4) {
                bufferedReader = null;
                th = th4;
                m4530a(c);
                m4530a(inputStreamReader);
                m4530a(bufferedReader);
                throw th;
            }
        } catch (IOException e7) {
            e3 = e7;
            closeable = null;
            bufferedReader = null;
            e3.printStackTrace();
            m4530a(c);
            m4530a(bufferedReader);
            m4530a(closeable);
            return null;
        } catch (Throwable th42) {
            bufferedReader = null;
            inputStreamReader = null;
            th = th42;
            m4530a(c);
            m4530a(inputStreamReader);
            m4530a(bufferedReader);
            throw th;
        }
    }

    private File m4539e(File file) {
        if (file == null || !file.isDirectory() || file.list().length <= 0) {
            return null;
        }
        return m4540f(file);
    }

    private File m4540f(File file) {
        File[] d = m4537d(file);
        if (d == null || d.length <= 0) {
            return null;
        }
        for (File file2 : d) {
            if (m4532b(file2.length()) <= ((double) 40) && file2.getName().endsWith(f4998b)) {
                return file2;
            }
            Log.m4549e(f4997a, "getReadableFileFromFiles:file length don't legal" + file2.length());
            m4544b(file2.getName());
        }
        return null;
    }

    private boolean m4541g(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            return false;
        }
        long j = 0;
        for (File file2 : listFiles) {
            if (file2.getName().endsWith(f4998b)) {
                j += file2.length();
            } else if (!m4544b(file2.getName())) {
                j += file2.length();
            }
        }
        double d = ((double) j) / 1024.0d;
        Log.m4549e(f4997a, "dir size is:" + d + ";length:" + file.length());
        if (d > 512.0d) {
            return true;
        }
        return false;
    }

    public static double m4531b() {
        long blockSizeLong;
        long availableBlocksLong;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        if (VERSION.SDK_INT >= 18) {
            blockSizeLong = statFs.getBlockSizeLong();
            availableBlocksLong = statFs.getAvailableBlocksLong();
        } else {
            blockSizeLong = (long) statFs.getBlockSize();
            availableBlocksLong = (long) statFs.getAvailableBlocks();
        }
        return C1665c.m4528a(availableBlocksLong * blockSizeLong);
    }

    public static double m4528a(long j) {
        return (((double) j) / 1024.0d) / 1024.0d;
    }
}
