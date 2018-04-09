package com.amap.api.mapcore.util;

import android.content.Context;
import com.amap.api.mapcore.util.fd.C0256a;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* compiled from: DexFileManager */
public class fa {

    /* compiled from: DexFileManager */
    public static class C0255a {
        public static void m849a(ek ekVar, fd fdVar, String str) {
            ekVar.m802a((Object) fdVar, str);
        }

        public static fd m847a(ek ekVar, String str) {
            List b = ekVar.m808b(fd.m877b(str), fd.class);
            if (b == null || b.size() <= 0) {
                return null;
            }
            return (fd) b.get(0);
        }

        public static List<fd> m848a(ek ekVar, String str, String str2) {
            return ekVar.m808b(fd.m878b(str, str2), fd.class);
        }
    }

    static String m854a(String str) {
        return str + ".dex";
    }

    static String m853a(Context context, String str, String str2) {
        return ds.m681b(str + str2 + dq.m660q(context)) + ".jar";
    }

    static String m862b(Context context, String str, String str2) {
        return m852a(context, m853a(context, str, str2));
    }

    static String m852a(Context context, String str) {
        return m850a(context) + File.separator + str;
    }

    static String m850a(Context context) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "dex";
    }

    static boolean m861a(String str, String str2) {
        String a = ds.m678a(str);
        if (a == null || !a.equalsIgnoreCase(str2)) {
            return false;
        }
        return true;
    }

    static void m856a(Context context, ek ekVar, String str) {
        m863b(context, ekVar, str);
        m863b(context, ekVar, m854a(str));
    }

    static void m863b(Context context, ek ekVar, String str) {
        File file = new File(m852a(context, str));
        if (file.exists()) {
            file.delete();
        }
        ekVar.m804a(fd.m877b(str), fd.class);
    }

    static void m864c(final Context context, final String str, final String str2) {
        new Thread() {
            public void run() {
                try {
                    ek ekVar = new ek(context, fc.m4276a());
                    List<fd> b = ekVar.m808b(fd.m874a(str), fd.class);
                    if (b != null && b.size() > 0) {
                        for (fd fdVar : b) {
                            if (!str2.equalsIgnoreCase(fdVar.m881c())) {
                                fa.m863b(context, ekVar, fdVar.m879a());
                            }
                        }
                    }
                } catch (Throwable th) {
                    eb.m742a(th, "DexFileManager", "clearUnSuitableVersion");
                }
            }
        }.start();
    }

    public static void m857a(ek ekVar, Context context, dv dvVar) {
        String a = dvVar.m706a();
        String a2 = m853a(context, a, dvVar.m708b());
        fd a3 = C0255a.m847a(ekVar, a2);
        if (a3 != null) {
            m856a(context, ekVar, a2);
            List b = ekVar.m808b(fd.m875a(a, a3.m883d()), fd.class);
            if (b != null && b.size() > 0) {
                fd fdVar = (fd) b.get(0);
                fdVar.m882c("errorstatus");
                C0255a.m849a(ekVar, fdVar, fd.m877b(fdVar.m879a()));
                File file = new File(m852a(context, fdVar.m879a()));
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    static void m855a(Context context, ek ekVar, dv dvVar, fd fdVar, String str) throws Throwable {
        InputStream fileInputStream;
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        FileOutputStream fileOutputStream = null;
        FileOutputStream fileOutputStream2;
        try {
            String a = dvVar.m706a();
            m856a(context, ekVar, fdVar.m879a());
            fileInputStream = new FileInputStream(new File(str));
            try {
                fileOutputStream2 = new FileOutputStream(new File(m862b(context, a, dvVar.m708b())), true);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        fileOutputStream2.write(bArr, 0, read);
                    }
                    C0255a.m849a(ekVar, fdVar, fd.m877b(fdVar.m879a()));
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e4) {
                    e2 = e4;
                    fileOutputStream = fileInputStream;
                    try {
                        throw e2;
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = fileOutputStream;
                        fileOutputStream = fileOutputStream2;
                    }
                } catch (IOException e5) {
                    e32 = e5;
                    fileOutputStream = fileOutputStream2;
                    throw e32;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream2;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e62) {
                            e62.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e7) {
                e2 = e7;
                fileOutputStream2 = null;
                Object obj = fileInputStream;
                throw e2;
            } catch (IOException e8) {
                e32 = e8;
                throw e32;
            } catch (Throwable th4) {
                th = th4;
                throw th;
            }
        } catch (FileNotFoundException e9) {
            e2 = e9;
            fileOutputStream2 = null;
            throw e2;
        } catch (IOException e10) {
            e32 = e10;
            fileInputStream = null;
            throw e32;
        } catch (Throwable th5) {
            th = th5;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }

    static boolean m859a(Context context, ek ekVar, String str, dv dvVar) {
        return m860a(ekVar, str, m852a(context, str), dvVar);
    }

    static boolean m860a(ek ekVar, String str, String str2, dv dvVar) {
        fd a = C0255a.m847a(ekVar, str);
        if (a != null && dvVar.m708b().equals(a.m881c()) && m861a(str2, a.m880b())) {
            return true;
        }
        return false;
    }

    static String m851a(Context context, ek ekVar, dv dvVar) {
        List b = ekVar.m808b(fd.m878b(dvVar.m706a(), "copy"), fd.class);
        if (b == null || b.size() == 0) {
            return null;
        }
        m858a(b);
        int i = 0;
        while (i < b.size()) {
            fd fdVar = (fd) b.get(i);
            if (m859a(context, ekVar, fdVar.m879a(), dvVar)) {
                try {
                    m855a(context, ekVar, dvVar, new C0256a(m853a(context, dvVar.m706a(), dvVar.m708b()), fdVar.m880b(), dvVar.m706a(), dvVar.m708b(), fdVar.m883d()).m872a("usedex").m873a(), m852a(context, fdVar.m879a()));
                    return fdVar.m883d();
                } catch (Throwable th) {
                    eb.m742a(th, "DexFileManager", "loadAvailableDynamicSDKFile");
                }
            } else {
                m863b(context, ekVar, fdVar.m879a());
                i++;
            }
        }
        return null;
    }

    static void m858a(List<fd> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int i2 = i + 1; i2 < list.size(); i2++) {
                fd fdVar = (fd) list.get(i);
                fd fdVar2 = (fd) list.get(i2);
                if (ff.m886a(fdVar2.m883d(), fdVar.m883d()) > 0) {
                    list.set(i, fdVar2);
                    list.set(i2, fdVar);
                }
            }
        }
    }
}
