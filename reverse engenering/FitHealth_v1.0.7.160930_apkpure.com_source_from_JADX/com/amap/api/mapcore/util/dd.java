package com.amap.api.mapcore.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import com.amap.api.mapcore.util.av.C0199a;
import com.amap.api.mapcore.util.da.C0240a;
import com.umeng.socialize.common.SocializeConstants;
import java.lang.ref.WeakReference;

/* compiled from: ImageWorker */
public abstract class dd {
    private da f434a;
    private C0240a f435b;
    protected boolean f436c = false;
    protected Resources f437d;
    private boolean f438e = false;
    private final Object f439f = new Object();

    /* compiled from: ImageWorker */
    public class C1596a extends cv<Boolean, Void, Bitmap> {
        final /* synthetic */ dd f4165a;
        private final WeakReference<C0199a> f4166e;

        public C1596a(dd ddVar, C0199a c0199a) {
            this.f4165a = ddVar;
            this.f4166e = new WeakReference(c0199a);
        }

        protected Bitmap m4207a(Boolean... boolArr) {
            try {
                boolean booleanValue = boolArr[0].booleanValue();
                Object obj = (C0199a) this.f4166e.get();
                if (obj == null) {
                    return null;
                }
                Bitmap bitmap;
                Bitmap bitmap2;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(obj.f188a);
                stringBuilder.append(SocializeConstants.OP_DIVIDER_MINUS);
                stringBuilder.append(obj.f189b);
                stringBuilder.append(SocializeConstants.OP_DIVIDER_MINUS);
                stringBuilder.append(obj.f190c);
                String stringBuilder2 = stringBuilder.toString();
                synchronized (this.f4165a.f439f) {
                    while (this.f4165a.f436c && !m472d()) {
                        this.f4165a.f439f.wait();
                    }
                }
                if (this.f4165a.f434a == null || m472d() || m4206e() == null || this.f4165a.f438e) {
                    bitmap = null;
                } else {
                    bitmap = this.f4165a.f434a.m509b(stringBuilder2);
                }
                if (!booleanValue || bitmap != null || m472d() || m4206e() == null || this.f4165a.f438e) {
                    bitmap2 = bitmap;
                } else {
                    bitmap2 = this.f4165a.mo1640a(obj);
                }
                if (bitmap2 == null || this.f4165a.f434a == null) {
                    return bitmap2;
                }
                this.f4165a.f434a.m508a(stringBuilder2, bitmap2);
                return bitmap2;
            } catch (Throwable th) {
                th.printStackTrace();
                return null;
            }
        }

        protected void m4209a(Bitmap bitmap) {
            try {
                if (m472d() || this.f4165a.f438e) {
                    bitmap = null;
                }
                C0199a e = m4206e();
                if (bitmap != null && !bitmap.isRecycled() && e != null) {
                    e.m213a(bitmap);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        protected void m4211b(Bitmap bitmap) {
            super.mo1641b((Object) bitmap);
            synchronized (this.f4165a.f439f) {
                try {
                    this.f4165a.f439f.notifyAll();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }

        private C0199a m4206e() {
            C0199a c0199a = (C0199a) this.f4166e.get();
            return this == dd.m517c(c0199a) ? c0199a : null;
        }
    }

    /* compiled from: ImageWorker */
    protected class C1597b extends cv<Object, Void, Void> {
        final /* synthetic */ dd f4167a;

        protected C1597b(dd ddVar) {
            this.f4167a = ddVar;
        }

        protected /* synthetic */ Object mo1428a(Object[] objArr) {
            return m4214d(objArr);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected java.lang.Void m4214d(java.lang.Object... r2) {
            /*
            r1 = this;
            r0 = 0;
            r0 = r2[r0];	 Catch:{ Throwable -> 0x0014 }
            r0 = (java.lang.Integer) r0;	 Catch:{ Throwable -> 0x0014 }
            r0 = r0.intValue();	 Catch:{ Throwable -> 0x0014 }
            switch(r0) {
                case 0: goto L_0x000e;
                case 1: goto L_0x0019;
                case 2: goto L_0x001f;
                case 3: goto L_0x0025;
                default: goto L_0x000c;
            };	 Catch:{ Throwable -> 0x0014 }
        L_0x000c:
            r0 = 0;
            return r0;
        L_0x000e:
            r0 = r1.f4167a;	 Catch:{ Throwable -> 0x0014 }
            r0.m526c();	 Catch:{ Throwable -> 0x0014 }
            goto L_0x000c;
        L_0x0014:
            r0 = move-exception;
            r0.printStackTrace();
            goto L_0x000c;
        L_0x0019:
            r0 = r1.f4167a;	 Catch:{ Throwable -> 0x0014 }
            r0.m524b();	 Catch:{ Throwable -> 0x0014 }
            goto L_0x000c;
        L_0x001f:
            r0 = r1.f4167a;	 Catch:{ Throwable -> 0x0014 }
            r0.m527d();	 Catch:{ Throwable -> 0x0014 }
            goto L_0x000c;
        L_0x0025:
            r0 = r1.f4167a;	 Catch:{ Throwable -> 0x0014 }
            r0.m528e();	 Catch:{ Throwable -> 0x0014 }
            goto L_0x000c;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.api.mapcore.util.dd.b.d(java.lang.Object[]):java.lang.Void");
        }
    }

    protected abstract Bitmap mo1640a(Object obj);

    protected dd(Context context) {
        this.f437d = context.getResources();
    }

    public void m523a(boolean z, C0199a c0199a) {
        if (c0199a != null) {
            Bitmap bitmap = null;
            try {
                if (this.f434a != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(c0199a.f188a);
                    stringBuilder.append(SocializeConstants.OP_DIVIDER_MINUS);
                    stringBuilder.append(c0199a.f189b);
                    stringBuilder.append(SocializeConstants.OP_DIVIDER_MINUS);
                    stringBuilder.append(c0199a.f190c);
                    bitmap = this.f434a.m506a(stringBuilder.toString());
                }
                if (bitmap != null) {
                    c0199a.m213a(bitmap);
                    return;
                }
                C1596a c1596a = new C1596a(this, c0199a);
                c0199a.f197j = c1596a;
                c1596a.m463a(cv.f395d, (Object[]) new Boolean[]{Boolean.valueOf(z)});
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public void m521a(C0240a c0240a) {
        this.f435b = c0240a;
        this.f434a = da.m496a(this.f435b);
        new C1597b(this).m470c(Integer.valueOf(1));
    }

    public void m522a(boolean z) {
        this.f438e = z;
        m525b(false);
    }

    protected da m520a() {
        return this.f434a;
    }

    public static void m514a(C0199a c0199a) {
        C1596a c = m517c(c0199a);
        if (c != null) {
            c.m466a(true);
        }
    }

    private static C1596a m517c(C0199a c0199a) {
        if (c0199a != null) {
            return c0199a.f197j;
        }
        return null;
    }

    public void m525b(boolean z) {
        synchronized (this.f439f) {
            this.f436c = z;
            if (!this.f436c) {
                try {
                    this.f439f.notifyAll();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
    }

    protected void m524b() {
        if (this.f434a != null) {
            this.f434a.m507a();
        }
    }

    protected void m526c() {
        if (this.f434a != null) {
            this.f434a.m510b();
        }
    }

    protected void m527d() {
        if (this.f434a != null) {
            this.f434a.m511c();
        }
    }

    protected void m528e() {
        if (this.f434a != null) {
            this.f434a.m512d();
            this.f434a = null;
        }
    }

    public void m529f() {
        new C1597b(this).m470c(Integer.valueOf(0));
    }

    public void m530g() {
        new C1597b(this).m470c(Integer.valueOf(2));
    }

    public void m531h() {
        new C1597b(this).m470c(Integer.valueOf(3));
    }
}
