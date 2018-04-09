package com.baidu.platform.comapi;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class NativeLoader {
    private static Context f1877a;
    private static final Set<String> f1878b = new HashSet();
    private static final Set<String> f1879c = new HashSet();
    private static NativeLoader f1880d;
    private static C0595a f1881e = C0595a.ARMEABI;

    private enum C0595a {
        ARMEABI("armeabi"),
        ARMV7("armeabi-v7a"),
        ARM64("arm64-v8a"),
        X86("x86"),
        X86_64("x86_64");
        
        private String f1876f;

        private C0595a(String str) {
            this.f1876f = str;
        }

        public String m1832a() {
            return this.f1876f;
        }
    }

    private NativeLoader() {
    }

    @TargetApi(21)
    private static C0595a m1833a() {
        String str = VERSION.SDK_INT < 21 ? Build.CPU_ABI : Build.SUPPORTED_ABIS[0];
        if (str == null) {
            return C0595a.ARMEABI;
        }
        if (str.contains("arm") && str.contains("v7")) {
            f1881e = C0595a.ARMV7;
        }
        if (str.contains("arm") && str.contains("64")) {
            f1881e = C0595a.ARM64;
        }
        if (str.contains("x86")) {
            if (str.contains("64")) {
                f1881e = C0595a.X86_64;
            } else {
                f1881e = C0595a.X86;
            }
        }
        return f1881e;
    }

    private String m1834a(C0595a c0595a) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lib/").append(c0595a.m1832a()).append("/");
        return stringBuilder.toString();
    }

    private void m1835a(Throwable th) {
        Log.e(NativeLoader.class.getSimpleName(), "loadException", th);
        for (String str : f1879c) {
            Log.e(NativeLoader.class.getSimpleName(), str + " Failed to load.");
        }
    }

    private boolean m1836a(String str, String str2) {
        return !copyNativeLibrary(str2, C0595a.ARMV7) ? m1837b(str, str2) : m1841f(str2, str);
    }

    private boolean m1837b(String str, String str2) {
        if (copyNativeLibrary(str2, C0595a.ARMEABI)) {
            return m1841f(str2, str);
        }
        Log.e(NativeLoader.class.getSimpleName(), "found lib" + str + ".so error");
        return false;
    }

    private boolean m1838c(String str, String str2) {
        return !copyNativeLibrary(str2, C0595a.ARM64) ? m1836a(str, str2) : m1841f(str2, str);
    }

    private boolean m1839d(String str, String str2) {
        return !copyNativeLibrary(str2, C0595a.X86) ? m1836a(str, str2) : m1841f(str2, str);
    }

    private boolean m1840e(String str, String str2) {
        return !copyNativeLibrary(str2, C0595a.X86_64) ? m1839d(str, str2) : m1841f(str2, str);
    }

    private boolean m1841f(String str, String str2) {
        try {
            System.load(new File(getCustomizeNativePath(), str).getAbsolutePath());
            synchronized (f1878b) {
                f1878b.add(str2);
            }
            return true;
        } catch (Throwable th) {
            synchronized (f1879c) {
                f1879c.add(str2);
                m1835a(th);
                return false;
            }
        }
    }

    public static synchronized NativeLoader getInstance() {
        NativeLoader nativeLoader;
        synchronized (NativeLoader.class) {
            if (f1880d == null) {
                f1880d = new NativeLoader();
                f1881e = m1833a();
            }
            nativeLoader = f1880d;
        }
        return nativeLoader;
    }

    public static void setContext(Context context) {
        f1877a = context;
    }

    protected boolean copyNativeLibrary(String str, C0595a c0595a) {
        Throwable e;
        String str2 = m1834a(c0595a) + str;
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(getCodePath());
            try {
                File file = new File(getCustomizeNativePath(), str);
                ZipEntry entry = zipFile.getEntry(str2);
                if (entry != null) {
                    copyStream(zipFile.getInputStream(entry), new FileOutputStream(file));
                    if (zipFile != null) {
                        try {
                            zipFile.close();
                        } catch (IOException e2) {
                            return false;
                        }
                    }
                    return true;
                } else if (zipFile == null) {
                    return false;
                } else {
                    try {
                        zipFile.close();
                        return false;
                    } catch (IOException e3) {
                        return false;
                    }
                }
            } catch (Exception e4) {
                e = e4;
                try {
                    Log.e(NativeLoader.class.getSimpleName(), "copyError", e);
                    if (zipFile != null) {
                        return false;
                    }
                    try {
                        zipFile.close();
                        return false;
                    } catch (IOException e5) {
                        return false;
                    }
                } catch (Throwable th) {
                    e = th;
                    if (zipFile != null) {
                        try {
                            zipFile.close();
                        } catch (IOException e6) {
                            return false;
                        }
                    }
                    throw e;
                }
            }
        } catch (Exception e7) {
            e = e7;
            zipFile = null;
            Log.e(NativeLoader.class.getSimpleName(), "copyError", e);
            if (zipFile != null) {
                return false;
            }
            zipFile.close();
            return false;
        } catch (Throwable th2) {
            e = th2;
            zipFile = null;
            if (zipFile != null) {
                zipFile.close();
            }
            throw e;
        }
    }

    protected final void copyStream(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        byte[] bArr = new byte[4096];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            } finally {
                try {
                    inputStream.close();
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        return;
                    }
                } catch (IOException e2) {
                    return;
                }
            }
        }
        fileOutputStream.flush();
        try {
            fileOutputStream.close();
        } catch (IOException e3) {
        }
    }

    @TargetApi(8)
    protected String getCodePath() {
        return 8 <= VERSION.SDK_INT ? f1877a.getPackageCodePath() : "";
    }

    protected String getCustomizeNativePath() {
        File file = new File(f1877a.getFilesDir(), "libs");
        file.mkdirs();
        return file.getAbsolutePath();
    }

    protected boolean loadCustomizeNativeLibrary(String str) {
        String mapLibraryName = System.mapLibraryName(str);
        switch (f1881e) {
            case ARM64:
                return m1838c(str, mapLibraryName);
            case ARMV7:
                return m1836a(str, mapLibraryName);
            case ARMEABI:
                return m1837b(str, mapLibraryName);
            case X86_64:
                return m1840e(str, mapLibraryName);
            case X86:
                return m1839d(str, mapLibraryName);
            default:
                return false;
        }
    }

    public synchronized boolean loadLibrary(String str) {
        boolean z = true;
        synchronized (this) {
            try {
                synchronized (f1878b) {
                    if (f1878b.contains(str)) {
                    } else {
                        System.loadLibrary(str);
                        synchronized (f1878b) {
                            f1878b.add(str);
                        }
                    }
                }
            } catch (Throwable th) {
                z = loadCustomizeNativeLibrary(str);
            }
        }
        return z;
    }
}
