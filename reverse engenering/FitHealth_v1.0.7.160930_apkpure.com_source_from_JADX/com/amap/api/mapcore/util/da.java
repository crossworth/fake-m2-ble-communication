package com.amap.api.mapcore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.os.StatFs;
import com.amap.api.mapcore.util.fk.C0260b;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.HashMap;

/* compiled from: ImageCache */
public class da {
    private static final CompressFormat f427a = CompressFormat.PNG;
    private fk f428b;
    private df<String, Bitmap> f429c;
    private C0240a f430d;
    private final Object f431e = new Object();
    private boolean f432f = true;
    private HashMap<String, WeakReference<Bitmap>> f433g;

    /* compiled from: ImageCache */
    public static class C0240a {
        public int f419a = 5242880;
        public int f420b = 10485760;
        public File f421c;
        public CompressFormat f422d = da.f427a;
        public int f423e = 100;
        public boolean f424f = true;
        public boolean f425g = true;
        public boolean f426h = false;

        public C0240a(Context context, String str) {
            this.f421c = da.m498a(context, str);
        }

        public void m489a(int i) {
            this.f419a = i;
        }

        public void m492b(int i) {
            if (i <= 0) {
                this.f425g = false;
            }
            this.f420b = i;
        }

        public void m490a(String str) {
            this.f421c = new File(str);
        }

        public void m491a(boolean z) {
            this.f424f = z;
        }

        public void m493b(boolean z) {
            this.f425g = z;
        }
    }

    private da(C0240a c0240a) {
        m501b(c0240a);
    }

    public static da m496a(C0240a c0240a) {
        return new da(c0240a);
    }

    private void m501b(C0240a c0240a) {
        this.f430d = c0240a;
        if (this.f430d.f424f) {
            if (dj.m594c()) {
                this.f433g = new HashMap();
            }
            this.f429c = new df<String, Bitmap>(this, this.f430d.f419a) {
                final /* synthetic */ da f4162a;

                protected void m4199a(boolean z, String str, Bitmap bitmap, Bitmap bitmap2) {
                    if (dj.m594c() && this.f4162a.f433g != null && bitmap != null && !bitmap.isRecycled()) {
                        this.f4162a.f433g.put(str, new WeakReference(bitmap));
                    }
                }

                protected int m4197a(String str, Bitmap bitmap) {
                    int a = da.m494a(bitmap);
                    return a == 0 ? 1 : a;
                }
            };
        }
        if (c0240a.f426h) {
            m507a();
        }
    }

    public void m507a() {
        synchronized (this.f431e) {
            if (this.f428b == null || this.f428b.m936a()) {
                File file = this.f430d.f421c;
                if (this.f430d.f425g && file != null) {
                    try {
                        if (file.exists()) {
                            m502b(file);
                        }
                        file.mkdir();
                    } catch (Throwable th) {
                    }
                    if (m495a(file) > ((long) this.f430d.f420b)) {
                        try {
                            this.f428b = fk.m914a(file, 1, 1, (long) this.f430d.f420b);
                        } catch (Throwable th2) {
                            this.f430d.f421c = null;
                        }
                    }
                }
            }
            this.f432f = false;
            this.f431e.notifyAll();
        }
    }

    private void m502b(File file) throws IOException {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            throw new IOException("not a readable directory: " + file);
        }
        int length = listFiles.length;
        int i = 0;
        while (i < length) {
            File file2 = listFiles[i];
            if (file2.isDirectory()) {
                m502b(file2);
            }
            if (file2.delete()) {
                i++;
            } else {
                throw new IOException("failed to delete file: " + file2);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void m508a(java.lang.String r7, android.graphics.Bitmap r8) {
        /*
        r6 = this;
        if (r7 == 0) goto L_0x000a;
    L_0x0002:
        if (r8 == 0) goto L_0x000a;
    L_0x0004:
        r0 = r8.isRecycled();
        if (r0 == 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r0 = r6.f429c;
        if (r0 == 0) goto L_0x0014;
    L_0x000f:
        r0 = r6.f429c;
        r0.m544b(r7, r8);
    L_0x0014:
        r2 = r6.f431e;
        monitor-enter(r2);
        r0 = r6.f428b;	 Catch:{ all -> 0x004d }
        if (r0 == 0) goto L_0x004b;
    L_0x001b:
        r1 = m503c(r7);	 Catch:{ all -> 0x004d }
        r0 = 0;
        r3 = r6.f428b;	 Catch:{ Throwable -> 0x0059, all -> 0x0062 }
        r3 = r3.m934a(r1);	 Catch:{ Throwable -> 0x0059, all -> 0x0062 }
        if (r3 != 0) goto L_0x0050;
    L_0x0028:
        r3 = r6.f428b;	 Catch:{ Throwable -> 0x0059, all -> 0x0062 }
        r1 = r3.m937b(r1);	 Catch:{ Throwable -> 0x0059, all -> 0x0062 }
        if (r1 == 0) goto L_0x0046;
    L_0x0030:
        r3 = 0;
        r0 = r1.m894a(r3);	 Catch:{ Throwable -> 0x0059, all -> 0x0062 }
        r3 = r6.f430d;	 Catch:{ Throwable -> 0x0059, all -> 0x0070 }
        r3 = r3.f422d;	 Catch:{ Throwable -> 0x0059, all -> 0x0070 }
        r4 = r6.f430d;	 Catch:{ Throwable -> 0x0059, all -> 0x0070 }
        r4 = r4.f423e;	 Catch:{ Throwable -> 0x0059, all -> 0x0070 }
        r8.compress(r3, r4, r0);	 Catch:{ Throwable -> 0x0059, all -> 0x0070 }
        r1.m895a();	 Catch:{ Throwable -> 0x0059, all -> 0x0070 }
        r0.close();	 Catch:{ Throwable -> 0x0059, all -> 0x0070 }
    L_0x0046:
        if (r0 == 0) goto L_0x004b;
    L_0x0048:
        r0.close();	 Catch:{ Throwable -> 0x006c }
    L_0x004b:
        monitor-exit(r2);	 Catch:{ all -> 0x004d }
        goto L_0x000a;
    L_0x004d:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x004d }
        throw r0;
    L_0x0050:
        r1 = 0;
        r1 = r3.m897a(r1);	 Catch:{ Throwable -> 0x0059, all -> 0x0062 }
        r1.close();	 Catch:{ Throwable -> 0x0059, all -> 0x0062 }
        goto L_0x0046;
    L_0x0059:
        r1 = move-exception;
        if (r0 == 0) goto L_0x004b;
    L_0x005c:
        r0.close();	 Catch:{ Throwable -> 0x0060 }
        goto L_0x004b;
    L_0x0060:
        r0 = move-exception;
        goto L_0x004b;
    L_0x0062:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0066:
        if (r1 == 0) goto L_0x006b;
    L_0x0068:
        r1.close();	 Catch:{ Throwable -> 0x006e }
    L_0x006b:
        throw r0;	 Catch:{ all -> 0x004d }
    L_0x006c:
        r0 = move-exception;
        goto L_0x004b;
    L_0x006e:
        r1 = move-exception;
        goto L_0x006b;
    L_0x0070:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0066;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.mapcore.util.da.a(java.lang.String, android.graphics.Bitmap):void");
    }

    public Bitmap m506a(String str) {
        Bitmap bitmap;
        if (dj.m594c() && this.f433g != null) {
            WeakReference weakReference = (WeakReference) this.f433g.get(str);
            if (weakReference != null) {
                bitmap = (Bitmap) weakReference.get();
                if (bitmap == null || bitmap.isRecycled()) {
                    bitmap = null;
                }
                this.f433g.remove(str);
                if (bitmap == null && this.f429c != null) {
                    bitmap = (Bitmap) this.f429c.m540a((Object) str);
                }
                if (bitmap == null && !bitmap.isRecycled()) {
                    return bitmap;
                }
            }
        }
        bitmap = null;
        bitmap = (Bitmap) this.f429c.m540a((Object) str);
        return bitmap == null ? null : null;
    }

    public Bitmap m509b(String str) {
        InputStream a;
        Throwable th;
        Bitmap bitmap = null;
        String c = m503c(str);
        synchronized (this.f431e) {
            while (this.f432f) {
                try {
                    this.f431e.wait();
                } catch (Throwable th2) {
                }
            }
            if (this.f428b != null) {
                try {
                    C0260b a2 = this.f428b.m934a(c);
                    if (a2 != null) {
                        a = a2.m897a(0);
                        if (a != null) {
                            try {
                                bitmap = dc.m4203a(((FileInputStream) a).getFD(), ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, this);
                            } catch (Throwable th3) {
                                th = th3;
                                if (a != null) {
                                    try {
                                        a.close();
                                    } catch (Throwable th4) {
                                    }
                                }
                                throw th;
                            }
                        }
                    }
                    Object obj = bitmap;
                    if (a != null) {
                        try {
                            a.close();
                        } catch (Throwable th5) {
                        }
                    }
                } catch (Throwable th6) {
                    th = th6;
                    a = bitmap;
                    if (a != null) {
                        a.close();
                    }
                    throw th;
                }
            }
        }
        return bitmap;
    }

    public void m510b() {
        if (dj.m594c() && this.f433g != null) {
            this.f433g.clear();
        }
        if (this.f429c != null) {
            this.f429c.m541a();
        }
        synchronized (this.f431e) {
            this.f432f = true;
            if (!(this.f428b == null || this.f428b.m936a())) {
                try {
                    this.f428b.m939c();
                } catch (Throwable th) {
                }
                this.f428b = null;
                m507a();
            }
        }
    }

    public void m511c() {
        synchronized (this.f431e) {
            if (this.f428b != null) {
                try {
                    this.f428b.m938b();
                } catch (Throwable th) {
                }
            }
        }
    }

    public void m512d() {
        if (dj.m594c() && this.f433g != null) {
            this.f433g.clear();
        }
        if (this.f429c != null) {
            this.f429c.m541a();
        }
        synchronized (this.f431e) {
            if (this.f428b != null) {
                try {
                    if (!this.f428b.m936a()) {
                        this.f428b.m939c();
                        this.f428b = null;
                    }
                } catch (Throwable th) {
                }
            }
        }
    }

    public static File m498a(Context context, String str) {
        String path;
        File a = m497a(context);
        if (("mounted".equals(Environment.getExternalStorageState()) || !m504e()) && a != null) {
            path = a.getPath();
        } else {
            path = context.getCacheDir().getPath();
        }
        return new File(path + File.separator + str);
    }

    public static String m503c(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes("utf-8"));
            return m499a(instance.digest());
        } catch (Throwable th) {
            return String.valueOf(str.hashCode());
        }
    }

    private static String m499a(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & 255);
            if (toHexString.length() == 1) {
                stringBuilder.append('0');
            }
            stringBuilder.append(toHexString);
        }
        return stringBuilder.toString();
    }

    public static int m494a(Bitmap bitmap) {
        if (dj.m596d()) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static boolean m504e() {
        if (dj.m591b()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    public static File m497a(Context context) {
        if (dj.m581a()) {
            return context.getExternalCacheDir();
        }
        return new File(Environment.getExternalStorageDirectory().getPath() + ("/Android/data/" + context.getPackageName() + "/cache/"));
    }

    public static long m495a(File file) {
        if (dj.m591b()) {
            return file.getUsableSpace();
        }
        StatFs statFs = new StatFs(file.getPath());
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }
}
