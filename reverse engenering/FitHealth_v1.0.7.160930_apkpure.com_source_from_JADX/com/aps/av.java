package com.aps;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Process;
import android.support.v4.media.TransportMediator;
import com.zhuoyi.system.network.util.NetworkConstants;
import com.zhuoyi.system.promotion.util.PromConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.json.JSONObject;

public final class av {
    private Context f1771a = null;
    private boolean f1772b = true;
    private int f1773c = 1270;
    private int f1774d = 310;
    private int f1775e = 4;
    private int f1776f = 200;
    private int f1777g = 1;
    private int f1778h = 0;
    private int f1779i = 0;
    private long f1780j = 0;
    private au f1781k = null;

    private av(Context context) {
        this.f1771a = context;
    }

    private static int m1815a(byte[] bArr, int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < 4) {
            i3 += (bArr[i2 + i] & 255) << (i2 << 3);
            i2++;
        }
        return i3;
    }

    protected static av m1816a(Context context) {
        FileInputStream fileInputStream;
        Throwable th;
        av avVar = new av(context);
        avVar.f1778h = 0;
        avVar.f1779i = 0;
        avVar.f1780j = ((System.currentTimeMillis() + PromConstants.PROM_SHOW_NOTIFY_INTERVAL) / 86400000) * 86400000;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(new File(m1819b(context) + File.separator + "data_carrier_status"));
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[32];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.flush();
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                if (toByteArray != null && toByteArray.length >= 22) {
                    avVar.f1772b = toByteArray[0] != (byte) 0;
                    avVar.f1773c = (toByteArray[1] * 10) << 10;
                    avVar.f1774d = (toByteArray[2] * 10) << 10;
                    avVar.f1775e = toByteArray[3];
                    avVar.f1776f = toByteArray[4] * 10;
                    avVar.f1777g = toByteArray[5];
                    long b = m1818b(toByteArray, 14);
                    if (avVar.f1780j - b < 86400000) {
                        avVar.f1780j = b;
                        avVar.f1778h = m1815a(toByteArray, 6);
                        avVar.f1779i = m1815a(toByteArray, 10);
                    }
                }
                byteArrayOutputStream.close();
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e3) {
                    }
                }
                return avVar;
            } catch (Throwable th2) {
                Throwable th3 = th2;
                fileInputStream2 = fileInputStream;
                th = th3;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            fileInputStream = null;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return avVar;
        } catch (Throwable th4) {
            th = th4;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            throw th;
        }
        return avVar;
    }

    private static byte[] m1817a(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < 8; i++) {
            bArr[i] = (byte) ((int) ((j >> (i << 3)) & 255));
        }
        return bArr;
    }

    private static long m1818b(byte[] bArr, int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < 8) {
            i3 += (bArr[i2 + 14] & 255) << (i2 << 3);
            i2++;
        }
        return (long) i3;
    }

    private static String m1819b(Context context) {
        boolean z = false;
        File file = null;
        if (Process.myUid() != 1000) {
            file = af.m1735a(context);
        }
        try {
            z = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
        }
        return ((z || !af.m1742c()) && file != null) ? file.getPath() : context.getFilesDir().getPath();
    }

    private static byte[] m1820c(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[i2] = (byte) (i >> (i2 << 3));
        }
        return bArr;
    }

    private void m1821g() {
        long currentTimeMillis = System.currentTimeMillis() + PromConstants.PROM_SHOW_NOTIFY_INTERVAL;
        if (currentTimeMillis - this.f1780j > 86400000) {
            this.f1780j = (currentTimeMillis / 86400000) * 86400000;
            this.f1778h = 0;
            this.f1779i = 0;
        }
    }

    protected final void m1822a(int i) {
        m1821g();
        if (i < 0) {
            i = 0;
        }
        this.f1778h = i;
    }

    protected final void m1823a(au auVar) {
        this.f1781k = auVar;
    }

    protected final boolean m1824a() {
        m1821g();
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.f1771a.getSystemService("connectivity")).getActiveNetworkInfo();
        return (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) ? this.f1772b : activeNetworkInfo.getType() == 1 ? this.f1772b && this.f1778h < this.f1773c : this.f1772b && this.f1779i < this.f1774d;
    }

    protected final boolean m1825a(String str) {
        boolean z;
        FileOutputStream fileOutputStream;
        Throwable th;
        int i = 1;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("e")) {
                this.f1772b = jSONObject.getInt("e") != 0;
            }
            if (jSONObject.has("d")) {
                int i2 = jSONObject.getInt("d");
                this.f1773c = ((i2 & TransportMediator.KEYCODE_MEDIA_PAUSE) * 10) << 10;
                this.f1774d = (((i2 & 3968) >> 7) * 10) << 10;
                this.f1775e = (520192 & i2) >> 12;
                this.f1776f = ((66584576 & i2) >> 19) * 10;
                this.f1777g = (i2 & 2080374784) >> 26;
                if (this.f1777g == 31) {
                    this.f1777g = 1500;
                }
                if (this.f1781k != null) {
                    this.f1781k.m1814a();
                }
            }
            z = jSONObject.has("u") ? jSONObject.getInt("u") != 0 : false;
        } catch (Exception e) {
            z = false;
        }
        FileOutputStream fileOutputStream2;
        try {
            m1821g();
            fileOutputStream2 = new FileOutputStream(new File(m1819b(this.f1771a) + File.separator + "data_carrier_status"));
            try {
                byte[] c = m1820c(this.f1778h);
                byte[] c2 = m1820c(this.f1779i);
                byte[] a = m1817a(this.f1780j);
                byte[] bArr = new byte[22];
                if (!this.f1772b) {
                    i = 0;
                }
                bArr[0] = (byte) i;
                bArr[1] = (byte) (this.f1773c / NetworkConstants.DOWNLOAD_BUFFER_SIZE);
                bArr[2] = (byte) (this.f1774d / NetworkConstants.DOWNLOAD_BUFFER_SIZE);
                bArr[3] = (byte) this.f1775e;
                bArr[4] = (byte) (this.f1776f / 10);
                bArr[5] = (byte) this.f1777g;
                bArr[6] = c[0];
                bArr[7] = c[1];
                bArr[8] = c[2];
                bArr[9] = c[3];
                bArr[10] = c2[0];
                bArr[11] = c2[1];
                bArr[12] = c2[2];
                bArr[13] = c2[3];
                bArr[14] = a[0];
                bArr[15] = a[1];
                bArr[16] = a[2];
                bArr[17] = a[3];
                bArr[18] = a[4];
                bArr[19] = a[5];
                bArr[20] = a[6];
                bArr[21] = a[7];
                fileOutputStream2.write(bArr);
                try {
                    fileOutputStream2.close();
                } catch (Exception e2) {
                }
            } catch (Exception e3) {
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e4) {
                    }
                }
                return z;
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (Exception e5) {
                    }
                }
                throw th;
            }
        } catch (Exception e6) {
            fileOutputStream = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return z;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream2 = null;
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            throw th;
        }
        return z;
    }

    protected final int m1826b() {
        return this.f1775e;
    }

    protected final void m1827b(int i) {
        m1821g();
        if (i < 0) {
            i = 0;
        }
        this.f1779i = i;
    }

    protected final int m1828c() {
        return this.f1776f;
    }

    protected final int m1829d() {
        return this.f1777g;
    }

    protected final int m1830e() {
        m1821g();
        return this.f1778h;
    }

    protected final int m1831f() {
        m1821g();
        return this.f1779i;
    }
}
