package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Build;
import com.amap.api.services.proguard.cm.C0382b;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* compiled from: StatisticsManager */
public class bc {
    private static boolean f1394a = true;

    private static byte[] m1334b(Context context) {
        Object c = m1335c(context);
        Object e = m1337e(context);
        byte[] bArr = new byte[(c.length + e.length)];
        System.arraycopy(c, 0, bArr, 0, c.length);
        System.arraycopy(e, 0, bArr, c.length, e.length);
        return m1332a(context, bArr);
    }

    public static void m1330a(Context context) {
        try {
            if (m1339g(context)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date()));
                stringBuffer.append(" ");
                stringBuffer.append(UUID.randomUUID().toString());
                stringBuffer.append(" ");
                if (stringBuffer.length() == 53) {
                    Object a = bb.m1321a(stringBuffer.toString());
                    Object b = m1334b(context);
                    byte[] bArr = new byte[(a.length + b.length)];
                    System.arraycopy(a, 0, bArr, 0, a.length);
                    System.arraycopy(b, 0, bArr, a.length, b.length);
                    cq.m1545a().mo1777b(new bg(bb.m1325c(bArr), "2"));
                }
            }
        } catch (Throwable e) {
            be.m1340a(e, "StatisticsManager", "updateStaticsData");
        } catch (Throwable e2) {
            be.m1340a(e2, "StatisticsManager", "updateStaticsData");
        }
    }

    private static byte[] m1332a(Context context, byte[] bArr) {
        try {
            return av.m1232a(context, bArr);
        } catch (CertificateException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeySpecException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            return null;
        } catch (IOException e4) {
            e4.printStackTrace();
            return null;
        } catch (InvalidKeyException e5) {
            e5.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e6) {
            e6.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e7) {
            e7.printStackTrace();
            return null;
        } catch (BadPaddingException e8) {
            e8.printStackTrace();
            return null;
        }
    }

    private static byte[] m1335c(Context context) {
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[0];
        try {
            bb.m1318a(byteArrayOutputStream, "1.2.12.5");
            bb.m1318a(byteArrayOutputStream, aw.m1261q(context));
            bb.m1318a(byteArrayOutputStream, aw.m1253i(context));
            bb.m1318a(byteArrayOutputStream, aw.m1250f(context));
            bb.m1318a(byteArrayOutputStream, Build.MANUFACTURER);
            bb.m1318a(byteArrayOutputStream, Build.MODEL);
            bb.m1318a(byteArrayOutputStream, Build.DEVICE);
            bb.m1318a(byteArrayOutputStream, aw.m1262r(context));
            bb.m1318a(byteArrayOutputStream, as.m1212c(context));
            bb.m1318a(byteArrayOutputStream, as.m1213d(context));
            bb.m1318a(byteArrayOutputStream, as.m1215f(context));
            byteArrayOutputStream.write(new byte[]{(byte) 0});
            bArr = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th = th2;
                th.printStackTrace();
                return bArr;
            }
        } catch (Throwable th3) {
            th = th3;
            th.printStackTrace();
        }
        return bArr;
    }

    private static int m1336d(Context context) {
        int i = 0;
        try {
            File file = new File(bf.m1345a(context, bf.f1410e));
            if (file.exists()) {
                i = file.list().length;
            }
        } catch (Throwable th) {
            be.m1340a(th, "StatisticsManager", "getFileNum");
        }
        return i;
    }

    private static byte[] m1337e(Context context) {
        Throwable th;
        int i = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[0];
        String a = bf.m1345a(context, bf.f1410e);
        cm cmVar = null;
        try {
            cmVar = cm.m1512a(new File(a), 1, 1, 10240);
            File file = new File(a);
            if (file != null && file.exists()) {
                String[] list = file.list();
                int length = list.length;
                while (i < length) {
                    String str = list[i];
                    if (str.contains(".0")) {
                        byteArrayOutputStream.write(m1333a(cmVar, str.split("\\.")[0]));
                    }
                    i++;
                }
            }
            bArr = byteArrayOutputStream.toByteArray();
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (cmVar != null) {
                try {
                    cmVar.close();
                } catch (Throwable th2) {
                    th = th2;
                    th.printStackTrace();
                    return bArr;
                }
            }
        } catch (Throwable th3) {
            be.m1340a(th3, "StatisticsManager", "getContent");
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (cmVar != null) {
                cmVar.close();
            }
        } catch (Throwable th4) {
            th3 = th4;
            th3.printStackTrace();
        }
        return bArr;
    }

    private static byte[] m1333a(cm cmVar, String str) {
        C0382b a;
        byte[] bArr;
        Throwable th;
        Throwable th2;
        InputStream inputStream = null;
        byte[] bArr2 = new byte[0];
        try {
            a = cmVar.m1532a(str);
            try {
                inputStream = a.m1495a(0);
                bArr = new byte[inputStream.available()];
            } catch (Throwable th3) {
                Throwable th4 = th3;
                bArr = bArr2;
                th2 = th4;
                try {
                    th2.printStackTrace();
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th22) {
                            th22.printStackTrace();
                        }
                    }
                    if (a != null) {
                        try {
                            a.close();
                        } catch (Throwable th5) {
                            th22 = th5;
                            th22.printStackTrace();
                            return bArr;
                        }
                    }
                    return bArr;
                } catch (Throwable th6) {
                    th3 = th6;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th222) {
                            th222.printStackTrace();
                        }
                    }
                    if (a != null) {
                        try {
                            a.close();
                        } catch (Throwable th2222) {
                            th2222.printStackTrace();
                        }
                    }
                    throw th3;
                }
            }
            try {
                inputStream.read(bArr);
                cmVar.m1538c(str);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th22222) {
                        th22222.printStackTrace();
                    }
                }
                if (a != null) {
                    try {
                        a.close();
                    } catch (Throwable th7) {
                        th22222 = th7;
                        th22222.printStackTrace();
                        return bArr;
                    }
                }
            } catch (Throwable th8) {
                th22222 = th8;
                th22222.printStackTrace();
                if (inputStream != null) {
                    inputStream.close();
                }
                if (a != null) {
                    a.close();
                }
                return bArr;
            }
        } catch (Throwable th9) {
            th3 = th9;
            a = null;
            if (inputStream != null) {
                inputStream.close();
            }
            if (a != null) {
                a.close();
            }
            throw th3;
        }
        return bArr;
    }

    private static void m1331a(Context context, long j) {
        FileOutputStream fileOutputStream;
        Throwable th;
        FileNotFoundException e;
        IOException e2;
        File file = new File(bf.m1345a(context, "c.log"));
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(bb.m1321a(String.valueOf(j)));
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            } catch (FileNotFoundException e3) {
                e = e3;
                try {
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th3) {
                            th = th3;
                            th.printStackTrace();
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th5) {
                            th5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e2 = e4;
                e2.printStackTrace();
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th6) {
                        th = th6;
                        th.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            fileOutputStream = null;
            e.printStackTrace();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException e6) {
            e2 = e6;
            fileOutputStream = null;
            e2.printStackTrace();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Throwable th7) {
            th = th7;
            fileOutputStream = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }

    private static long m1338f(android.content.Context r7) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.amap.api.services.proguard.bc.f(android.content.Context):long. bs: [B:17:0x0038, B:40:0x0063]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = 0;
        r2 = "c.log";
        r2 = com.amap.api.services.proguard.bf.m1345a(r7, r2);
        r5 = new java.io.File;
        r5.<init>(r2);
        r2 = r5.exists();
        if (r2 != 0) goto L_0x0014;
    L_0x0013:
        return r0;
    L_0x0014:
        r4 = 0;
        r3 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x0036, IOException -> 0x0047, Throwable -> 0x0058, all -> 0x0085 }
        r3.<init>(r5);	 Catch:{ FileNotFoundException -> 0x0036, IOException -> 0x0047, Throwable -> 0x0058, all -> 0x0085 }
        r2 = r3.available();	 Catch:{ FileNotFoundException -> 0x008c, IOException -> 0x008a, Throwable -> 0x0088 }
        r2 = new byte[r2];	 Catch:{ FileNotFoundException -> 0x008c, IOException -> 0x008a, Throwable -> 0x0088 }
        r3.read(r2);	 Catch:{ FileNotFoundException -> 0x008c, IOException -> 0x008a, Throwable -> 0x0088 }
        r2 = com.amap.api.services.proguard.bb.m1315a(r2);	 Catch:{ FileNotFoundException -> 0x008c, IOException -> 0x008a, Throwable -> 0x0088 }
        r0 = java.lang.Long.parseLong(r2);	 Catch:{ FileNotFoundException -> 0x008c, IOException -> 0x008a, Throwable -> 0x0088 }
        if (r3 == 0) goto L_0x0013;
    L_0x002d:
        r3.close();	 Catch:{ Throwable -> 0x0031 }
        goto L_0x0013;
    L_0x0031:
        r2 = move-exception;
    L_0x0032:
        r2.printStackTrace();
        goto L_0x0013;
    L_0x0036:
        r2 = move-exception;
        r3 = r4;
    L_0x0038:
        r4 = "StatisticsManager";	 Catch:{ all -> 0x0079 }
        r5 = "getUpdateTime";	 Catch:{ all -> 0x0079 }
        com.amap.api.services.proguard.be.m1340a(r2, r4, r5);	 Catch:{ all -> 0x0079 }
        if (r3 == 0) goto L_0x0013;
    L_0x0041:
        r3.close();	 Catch:{ Throwable -> 0x0045 }
        goto L_0x0013;
    L_0x0045:
        r2 = move-exception;
        goto L_0x0032;
    L_0x0047:
        r2 = move-exception;
        r3 = r4;
    L_0x0049:
        r4 = "StatisticsManager";	 Catch:{ all -> 0x0079 }
        r5 = "getUpdateTime";	 Catch:{ all -> 0x0079 }
        com.amap.api.services.proguard.be.m1340a(r2, r4, r5);	 Catch:{ all -> 0x0079 }
        if (r3 == 0) goto L_0x0013;
    L_0x0052:
        r3.close();	 Catch:{ Throwable -> 0x0056 }
        goto L_0x0013;
    L_0x0056:
        r2 = move-exception;
        goto L_0x0032;
    L_0x0058:
        r2 = move-exception;
        r3 = r4;
    L_0x005a:
        r4 = "StatisticsManager";	 Catch:{ all -> 0x0079 }
        r6 = "getUpdateTime";	 Catch:{ all -> 0x0079 }
        com.amap.api.services.proguard.be.m1340a(r2, r4, r6);	 Catch:{ all -> 0x0079 }
        if (r5 == 0) goto L_0x006c;
    L_0x0063:
        r2 = r5.exists();	 Catch:{ Throwable -> 0x0074 }
        if (r2 == 0) goto L_0x006c;	 Catch:{ Throwable -> 0x0074 }
    L_0x0069:
        r5.delete();	 Catch:{ Throwable -> 0x0074 }
    L_0x006c:
        if (r3 == 0) goto L_0x0013;
    L_0x006e:
        r3.close();	 Catch:{ Throwable -> 0x0072 }
        goto L_0x0013;
    L_0x0072:
        r2 = move-exception;
        goto L_0x0032;
    L_0x0074:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x0079 }
        goto L_0x006c;
    L_0x0079:
        r0 = move-exception;
    L_0x007a:
        if (r3 == 0) goto L_0x007f;
    L_0x007c:
        r3.close();	 Catch:{ Throwable -> 0x0080 }
    L_0x007f:
        throw r0;
    L_0x0080:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x007f;
    L_0x0085:
        r0 = move-exception;
        r3 = r4;
        goto L_0x007a;
    L_0x0088:
        r2 = move-exception;
        goto L_0x005a;
    L_0x008a:
        r2 = move-exception;
        goto L_0x0049;
    L_0x008c:
        r2 = move-exception;
        goto L_0x0038;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.services.proguard.bc.f(android.content.Context):long");
    }

    private static boolean m1339g(Context context) {
        try {
            if (aw.m1257m(context) != 1 || !f1394a || m1336d(context) < 100) {
                return false;
            }
            long f = m1338f(context);
            long time = new Date().getTime();
            if (time - f < 3600000) {
                return false;
            }
            m1331a(context, time);
            f1394a = false;
            return true;
        } catch (Throwable th) {
            be.m1340a(th, "StatisticsManager", "isUpdate");
            return false;
        }
    }
}
