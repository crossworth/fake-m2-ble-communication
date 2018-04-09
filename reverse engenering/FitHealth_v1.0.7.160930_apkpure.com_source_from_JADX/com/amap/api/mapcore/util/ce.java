package com.amap.api.mapcore.util;

import android.text.TextUtils;
import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* compiled from: UnZipFile */
public class ce {
    private C0229b f370a;

    /* compiled from: UnZipFile */
    public static class C0228a {
        public boolean f363a = false;
    }

    /* compiled from: UnZipFile */
    private class C0229b {
        final /* synthetic */ ce f364a;
        private String f365b;
        private String f366c;
        private ca f367d = null;
        private C0228a f368e = new C0228a();
        private String f369f;

        public C0229b(ce ceVar, cb cbVar, ca caVar) {
            this.f364a = ceVar;
            this.f365b = cbVar.mo2986A();
            this.f366c = cbVar.mo2987B();
            this.f367d = caVar;
        }

        public void m404a(String str) {
            if (str.length() > 1) {
                this.f369f = str;
            }
        }

        public String m403a() {
            return this.f365b;
        }

        public String m405b() {
            return this.f366c;
        }

        public String m406c() {
            return this.f369f;
        }

        public ca m407d() {
            return this.f367d;
        }

        public C0228a m408e() {
            return this.f368e;
        }

        public void m409f() {
            this.f368e.f363a = true;
        }
    }

    /* compiled from: UnZipFile */
    public interface C0230c {
        void mo1628a();

        void mo1629a(long j);
    }

    public ce(cb cbVar, ca caVar) {
        this.f370a = new C0229b(this, cbVar, caVar);
    }

    public void m418a() {
        if (this.f370a != null) {
            this.f370a.m409f();
        }
    }

    public void m419b() {
        if (this.f370a != null) {
            m413a(this.f370a);
        }
    }

    private static void m413a(C0229b c0229b) {
        if (c0229b != null) {
            final ca d = c0229b.m407d();
            if (d != null) {
                d.mo2996p();
            }
            Object a = c0229b.m403a();
            Object b = c0229b.m405b();
            if (!TextUtils.isEmpty(a) && !TextUtils.isEmpty(b)) {
                File file = new File(a);
                if (file.exists()) {
                    C0230c c15931;
                    File file2 = new File(b);
                    if (file2.exists() || !file2.mkdirs()) {
                        c15931 = new C0230c() {
                            public void mo1629a(long j) {
                                try {
                                    if (d != null) {
                                        d.mo2988a(j);
                                    }
                                } catch (Exception e) {
                                }
                            }

                            public void mo1628a() {
                                if (d != null) {
                                    d.mo2997q();
                                }
                            }
                        };
                    } else {
                        c15931 = /* anonymous class already generated */;
                    }
                    try {
                        if (c0229b.m408e().f363a && d != null) {
                            d.mo2998r();
                        }
                        m415a(file, file2, c15931, c0229b);
                        if (c0229b.m408e().f363a) {
                            if (d != null) {
                                d.mo2998r();
                            }
                        } else if (d != null) {
                            d.mo2992b(c0229b.m406c());
                        }
                    } catch (Exception e) {
                        if (c0229b.m408e().f363a) {
                            if (d != null) {
                                d.mo2998r();
                            }
                        } else if (d != null) {
                            d.mo2997q();
                        }
                    }
                } else if (c0229b.m408e().f363a) {
                    if (d != null) {
                        d.mo2998r();
                    }
                } else if (d != null) {
                    d.mo2997q();
                }
            } else if (c0229b.m408e().f363a) {
                if (d != null) {
                    d.mo2998r();
                }
            } else if (d != null) {
                d.mo2997q();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void m415a(java.io.File r10, java.io.File r11, com.amap.api.mapcore.util.ce.C0230c r12, com.amap.api.mapcore.util.ce.C0229b r13) throws java.lang.Exception {
        /*
        r0 = new java.lang.StringBuffer;
        r0.<init>();
        r5 = r13.m408e();
        r2 = 0;
        if (r12 == 0) goto L_0x0049;
    L_0x000d:
        r1 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x0080 }
        r1.<init>(r10);	 Catch:{ Exception -> 0x0080 }
        r4 = new java.util.zip.CheckedInputStream;	 Catch:{ Exception -> 0x0080 }
        r6 = new java.util.zip.CRC32;	 Catch:{ Exception -> 0x0080 }
        r6.<init>();	 Catch:{ Exception -> 0x0080 }
        r4.<init>(r1, r6);	 Catch:{ Exception -> 0x0080 }
        r6 = new java.util.zip.ZipInputStream;	 Catch:{ Exception -> 0x0080 }
        r6.<init>(r4);	 Catch:{ Exception -> 0x0080 }
    L_0x0021:
        r7 = r6.getNextEntry();	 Catch:{ Exception -> 0x0080 }
        if (r7 == 0) goto L_0x0039;
    L_0x0027:
        if (r5 == 0) goto L_0x006c;
    L_0x0029:
        r8 = r5.f363a;	 Catch:{ Exception -> 0x0080 }
        if (r8 == 0) goto L_0x006c;
    L_0x002d:
        r6.closeEntry();	 Catch:{ Exception -> 0x0080 }
        r6.close();	 Catch:{ Exception -> 0x0080 }
        r4.close();	 Catch:{ Exception -> 0x0080 }
        r1.close();	 Catch:{ Exception -> 0x0080 }
    L_0x0039:
        r0 = r0.toString();	 Catch:{ Exception -> 0x0080 }
        r13.m404a(r0);	 Catch:{ Exception -> 0x0080 }
        r6.close();	 Catch:{ Exception -> 0x0080 }
        r4.close();	 Catch:{ Exception -> 0x0080 }
        r1.close();	 Catch:{ Exception -> 0x0080 }
    L_0x0049:
        r6 = new java.io.FileInputStream;
        r6.<init>(r10);
        r7 = new java.util.zip.CheckedInputStream;
        r0 = new java.util.zip.CRC32;
        r0.<init>();
        r7.<init>(r6, r0);
        r1 = new java.util.zip.ZipInputStream;
        r1.<init>(r7);
        r0 = r11;
        r4 = r12;
        m416a(r0, r1, r2, r4, r5);
        r1.close();
        r7.close();
        r6.close();
        return;
    L_0x006c:
        r8 = r7.isDirectory();	 Catch:{ Exception -> 0x0080 }
        if (r8 != 0) goto L_0x0092;
    L_0x0072:
        r8 = r7.getName();	 Catch:{ Exception -> 0x0080 }
        r8 = m417a(r8);	 Catch:{ Exception -> 0x0080 }
        if (r8 != 0) goto L_0x0085;
    L_0x007c:
        r12.mo1628a();	 Catch:{ Exception -> 0x0080 }
        goto L_0x0039;
    L_0x0080:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0049;
    L_0x0085:
        r8 = r7.getName();	 Catch:{ Exception -> 0x0080 }
        r8 = r0.append(r8);	 Catch:{ Exception -> 0x0080 }
        r9 = ";";
        r8.append(r9);	 Catch:{ Exception -> 0x0080 }
    L_0x0092:
        r8 = r7.getSize();	 Catch:{ Exception -> 0x0080 }
        r2 = r2 + r8;
        r6.closeEntry();	 Catch:{ Exception -> 0x0080 }
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.mapcore.util.ce.a(java.io.File, java.io.File, com.amap.api.mapcore.util.ce$c, com.amap.api.mapcore.util.ce$b):void");
    }

    private static void m416a(File file, ZipInputStream zipInputStream, long j, C0230c c0230c, C0228a c0228a) throws Exception {
        int i = 0;
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                return;
            }
            if (c0228a == null || !c0228a.f363a) {
                String str = file.getPath() + File.separator + nextEntry.getName();
                if (!m417a(str)) {
                    break;
                }
                File file2 = new File(str);
                m414a(file2);
                int a = nextEntry.isDirectory() ? !file2.mkdirs() ? i : i : m412a(file2, zipInputStream, (long) i, j, c0230c, c0228a) + i;
                zipInputStream.closeEntry();
                i = a;
            } else {
                zipInputStream.closeEntry();
                return;
            }
        }
        if (c0230c != null) {
            c0230c.mo1628a();
        }
    }

    private static boolean m417a(String str) {
        if (str.contains("../")) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int m412a(java.io.File r8, java.util.zip.ZipInputStream r9, long r10, long r12, com.amap.api.mapcore.util.ce.C0230c r14, com.amap.api.mapcore.util.ce.C0228a r15) throws java.lang.Exception {
        /*
        r0 = 0;
        r1 = new java.io.BufferedOutputStream;
        r2 = new java.io.FileOutputStream;
        r2.<init>(r8);
        r1.<init>(r2);
        r2 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r2 = new byte[r2];
    L_0x000f:
        r3 = 0;
        r4 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r3 = r9.read(r2, r3, r4);
        r4 = -1;
        if (r3 == r4) goto L_0x0040;
    L_0x0019:
        if (r15 == 0) goto L_0x0023;
    L_0x001b:
        r4 = r15.f363a;
        if (r4 == 0) goto L_0x0023;
    L_0x001f:
        r1.close();
    L_0x0022:
        return r0;
    L_0x0023:
        r4 = 0;
        r1.write(r2, r4, r3);
        r0 = r0 + r3;
        r4 = 0;
        r3 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1));
        if (r3 <= 0) goto L_0x000f;
    L_0x002e:
        if (r14 == 0) goto L_0x000f;
    L_0x0030:
        r4 = (long) r0;
        r4 = r4 + r10;
        r6 = 100;
        r4 = r4 * r6;
        r4 = r4 / r12;
        if (r15 == 0) goto L_0x003c;
    L_0x0038:
        r3 = r15.f363a;
        if (r3 != 0) goto L_0x000f;
    L_0x003c:
        r14.mo1629a(r4);
        goto L_0x000f;
    L_0x0040:
        r1.close();
        goto L_0x0022;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.mapcore.util.ce.a(java.io.File, java.util.zip.ZipInputStream, long, long, com.amap.api.mapcore.util.ce$c, com.amap.api.mapcore.util.ce$a):int");
    }

    private static void m414a(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            m414a(parentFile);
            if (!parentFile.mkdir()) {
            }
        }
    }
}
