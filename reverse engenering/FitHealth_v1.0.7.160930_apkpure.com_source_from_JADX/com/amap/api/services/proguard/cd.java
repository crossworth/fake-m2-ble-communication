package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.proguard.cg.C0375a;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* compiled from: DexFileManager */
public class cd {

    /* compiled from: DexFileManager */
    public static class C0374a {
        public static void m1446a(bn bnVar, cg cgVar, String str) {
            bnVar.m1400a((Object) cgVar, str);
        }

        public static cg m1444a(bn bnVar, String str) {
            List b = bnVar.m1405b(cg.m1474b(str), cg.class);
            if (b == null || b.size() <= 0) {
                return null;
            }
            return (cg) b.get(0);
        }

        public static List<cg> m1445a(bn bnVar, String str, String str2) {
            return bnVar.m1405b(cg.m1475b(str, str2), cg.class);
        }
    }

    static String m1451a(String str) {
        return str + ".dex";
    }

    static String m1450a(Context context, String str, String str2) {
        return ay.m1282b(str + str2 + aw.m1261q(context)) + ".jar";
    }

    static String m1459b(Context context, String str, String str2) {
        return m1449a(context, m1450a(context, str, str2));
    }

    static String m1449a(Context context, String str) {
        return m1447a(context) + File.separator + str;
    }

    static String m1447a(Context context) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "dex";
    }

    static boolean m1458a(String str, String str2) {
        String a = ay.m1279a(str);
        if (a == null || !a.equalsIgnoreCase(str2)) {
            return false;
        }
        return true;
    }

    static void m1453a(Context context, bn bnVar, String str) {
        m1460b(context, bnVar, str);
        m1460b(context, bnVar, m1451a(str));
    }

    static void m1460b(Context context, bn bnVar, String str) {
        File file = new File(m1449a(context, str));
        if (file.exists()) {
            file.delete();
        }
        bnVar.m1402a(cg.m1474b(str), cg.class);
    }

    static void m1461c(final Context context, final String str, final String str2) {
        new Thread() {
            public void run() {
                try {
                    bn bnVar = new bn(context, cf.m4469c());
                    List<cg> b = bnVar.m1405b(cg.m1471a(str), cg.class);
                    if (b != null && b.size() > 0) {
                        for (cg cgVar : b) {
                            if (!str2.equalsIgnoreCase(cgVar.m1478c())) {
                                cd.m1460b(context, bnVar, cgVar.m1476a());
                            }
                        }
                    }
                } catch (Throwable th) {
                    be.m1340a(th, "DexFileManager", "clearUnSuitableVersion");
                }
            }
        }.start();
    }

    public static void m1454a(bn bnVar, Context context, ba baVar) {
        String a = baVar.m1308a();
        String a2 = m1450a(context, a, baVar.m1309b());
        cg a3 = C0374a.m1444a(bnVar, a2);
        if (a3 != null) {
            m1453a(context, bnVar, a2);
            List b = bnVar.m1405b(cg.m1472a(a, a3.m1480d()), cg.class);
            if (b != null && b.size() > 0) {
                cg cgVar = (cg) b.get(0);
                cgVar.m1479c("errorstatus");
                C0374a.m1446a(bnVar, cgVar, cg.m1474b(cgVar.m1476a()));
                File file = new File(m1449a(context, cgVar.m1476a()));
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    static void m1452a(Context context, bn bnVar, ba baVar, cg cgVar, String str) throws Throwable {
        InputStream fileInputStream;
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        FileOutputStream fileOutputStream = null;
        FileOutputStream fileOutputStream2;
        try {
            String a = baVar.m1308a();
            m1453a(context, bnVar, cgVar.m1476a());
            fileInputStream = new FileInputStream(new File(str));
            try {
                fileOutputStream2 = new FileOutputStream(new File(m1459b(context, a, baVar.m1309b())), true);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        fileOutputStream2.write(bArr, 0, read);
                    }
                    C0374a.m1446a(bnVar, cgVar, cg.m1474b(cgVar.m1476a()));
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

    static boolean m1456a(Context context, bn bnVar, String str, ba baVar) {
        return m1457a(bnVar, str, m1449a(context, str), baVar);
    }

    static boolean m1457a(bn bnVar, String str, String str2, ba baVar) {
        cg a = C0374a.m1444a(bnVar, str);
        if (a != null && baVar.m1309b().equals(a.m1478c()) && m1458a(str2, a.m1477b())) {
            return true;
        }
        return false;
    }

    static String m1448a(Context context, bn bnVar, ba baVar) {
        List b = bnVar.m1405b(cg.m1475b(baVar.m1308a(), "copy"), cg.class);
        if (b == null || b.size() == 0) {
            return null;
        }
        m1455a(b);
        int i = 0;
        while (i < b.size()) {
            cg cgVar = (cg) b.get(i);
            if (m1456a(context, bnVar, cgVar.m1476a(), baVar)) {
                try {
                    m1452a(context, bnVar, baVar, new C0375a(m1450a(context, baVar.m1308a(), baVar.m1309b()), cgVar.m1477b(), baVar.m1308a(), baVar.m1309b(), cgVar.m1480d()).m1469a("usedex").m1470a(), m1449a(context, cgVar.m1476a()));
                    return cgVar.m1480d();
                } catch (Throwable th) {
                    be.m1340a(th, "DexFileManager", "loadAvailableDynamicSDKFile");
                }
            } else {
                m1460b(context, bnVar, cgVar.m1476a());
                i++;
            }
        }
        return null;
    }

    static void m1455a(List<cg> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int i2 = i + 1; i2 < list.size(); i2++) {
                cg cgVar = (cg) list.get(i);
                cg cgVar2 = (cg) list.get(i2);
                if (ci.m1483a(cgVar2.m1480d(), cgVar.m1480d()) > 0) {
                    list.set(i, cgVar2);
                    list.set(i2, cgVar);
                }
            }
        }
    }
}
