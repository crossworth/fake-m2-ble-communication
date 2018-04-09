package com.aps;

import android.content.Context;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.NeighboringCellInfo;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class af {
    private Context f1710a;
    private int f1711b = 0;
    private int f1712c = 0;
    private int f1713d = 0;

    protected af(Context context) {
        this.f1710a = context;
        m1749a(768);
    }

    private static int m1733a(int i, int i2) {
        return i < i2 ? i : i2;
    }

    protected static aa m1734a(Location location, ai aiVar, int i, byte b, long j) {
        aa aaVar = new aa();
        if (i <= 0 || i > 3 || aiVar == null) {
            return null;
        }
        Object obj = (i == 1 || i == 3) ? 1 : null;
        Object obj2 = (i == 2 || i == 3) ? 1 : null;
        Object bytes = aiVar.m1803p().getBytes();
        System.arraycopy(bytes, 0, aaVar.f1690c, 0, m1733a(bytes.length, aaVar.f1690c.length));
        bytes = aiVar.m1793f().getBytes();
        System.arraycopy(bytes, 0, aaVar.f1694g, 0, m1733a(bytes.length, aaVar.f1694g.length));
        bytes = aiVar.m1794g().getBytes();
        System.arraycopy(bytes, 0, aaVar.f1688a, 0, m1733a(bytes.length, aaVar.f1688a.length));
        bytes = aiVar.m1795h().getBytes();
        System.arraycopy(bytes, 0, aaVar.f1689b, 0, m1733a(bytes.length, aaVar.f1689b.length));
        aaVar.f1691d = (short) aiVar.m1804q();
        aaVar.f1692e = (short) aiVar.m1805r();
        aaVar.f1693f = (byte) aiVar.m1806s();
        bytes = aiVar.m1807t().getBytes();
        System.arraycopy(bytes, 0, aaVar.f1695h, 0, m1733a(bytes.length, aaVar.f1695h.length));
        long j2 = j / 1000;
        bytes = (location == null || !aiVar.m1792e()) ? null : 1;
        if (bytes == null) {
            return null;
        }
        int i2;
        C0474x c0474x = new C0474x();
        c0474x.f1958b = (int) j2;
        C0476z c0476z = new C0476z();
        c0476z.f2015a = (int) (location.getLongitude() * 1000000.0d);
        c0476z.f2016b = (int) (location.getLatitude() * 1000000.0d);
        c0476z.f2017c = (int) location.getAltitude();
        c0476z.f2018d = (int) location.getAccuracy();
        c0476z.f2019e = (int) location.getSpeed();
        c0476z.f2020f = (short) ((int) location.getBearing());
        if (ai.m1773b(aiVar.m1811x()) && C0475y.f1975b) {
            c0476z.f2021g = (byte) 1;
        } else {
            c0476z.f2021g = (byte) 0;
        }
        c0476z.f2022h = b;
        c0476z.f2023i = System.currentTimeMillis();
        c0476z.f2024j = aiVar.m1802o();
        c0474x.f1959c = c0476z;
        int i3 = 1;
        aaVar.f1697j.add(c0474x);
        if (!(!aiVar.m1790c() || aiVar.m1796i() || obj == null)) {
            C0474x c0474x2 = new C0474x();
            c0474x2.f1958b = (int) j2;
            C0472v c0472v = new C0472v();
            List a = aiVar.m1786a(location.getSpeed());
            if (a != null && a.size() >= 3) {
                c0472v.f1946a = (short) ((Integer) a.get(0)).intValue();
                c0472v.f1947b = ((Integer) a.get(1)).intValue();
            }
            c0472v.f1948c = aiVar.m1799l();
            List m = aiVar.m1800m();
            c0472v.f1949d = (byte) m.size();
            for (i2 = 0; i2 < m.size(); i2++) {
                ah ahVar = new ah();
                ahVar.f1721a = (short) ((NeighboringCellInfo) m.get(i2)).getLac();
                ahVar.f1722b = ((NeighboringCellInfo) m.get(i2)).getCid();
                ahVar.f1723c = (byte) ((NeighboringCellInfo) m.get(i2)).getRssi();
                c0472v.f1950e.add(ahVar);
            }
            c0474x2.f1960d = c0472v;
            i3 = 2;
            aaVar.f1697j.add(c0474x2);
        }
        i2 = i3;
        if (aiVar.m1790c() && aiVar.m1796i() && obj != null) {
            C0474x c0474x3 = new C0474x();
            c0474x3.f1958b = (int) j2;
            ag agVar = new ag();
            List b2 = aiVar.m1788b(location.getSpeed());
            if (b2 != null && b2.size() >= 6) {
                agVar.f1714a = ((Integer) b2.get(3)).intValue();
                agVar.f1715b = ((Integer) b2.get(4)).intValue();
                agVar.f1716c = (short) ((Integer) b2.get(0)).intValue();
                agVar.f1717d = (short) ((Integer) b2.get(1)).intValue();
                agVar.f1718e = ((Integer) b2.get(2)).intValue();
                agVar.f1719f = aiVar.m1799l();
            }
            c0474x3.f1961e = agVar;
            i2++;
            aaVar.f1697j.add(c0474x3);
        }
        if (aiVar.m1791d() && obj2 != null) {
            c0474x2 = new C0474x();
            ac acVar = new ac();
            List u = aiVar.m1808u();
            c0474x2.f1958b = (int) (((Long) u.get(0)).longValue() / 1000);
            acVar.f1700a = (byte) (u.size() - 1);
            for (int i4 = 1; i4 < u.size(); i4++) {
                List list = (List) u.get(i4);
                if (list != null && list.size() >= 3) {
                    ad adVar = new ad();
                    bytes = ((String) list.get(0)).getBytes();
                    System.arraycopy(bytes, 0, adVar.f1703a, 0, m1733a(bytes.length, adVar.f1703a.length));
                    adVar.f1704b = (short) ((Integer) list.get(1)).intValue();
                    bytes = ((String) list.get(2)).getBytes();
                    System.arraycopy(bytes, 0, adVar.f1705c, 0, m1733a(bytes.length, adVar.f1705c.length));
                    acVar.f1701b.add(adVar);
                }
            }
            c0474x2.f1962f = acVar;
            i2++;
            aaVar.f1697j.add(c0474x2);
        }
        aaVar.f1696i = (short) i2;
        return i2 < 2 ? null : aaVar;
    }

    protected static File m1735a(Context context) {
        return new File(Environment.getExternalStorageDirectory().getPath() + ("/Android/data/" + context.getPackageName() + "/files/"));
    }

    private static ArrayList m1736a(File[] fileArr) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < fileArr.length) {
            if (fileArr[i].isFile() && fileArr[i].getName().length() == 10 && TextUtils.isDigitsOnly(fileArr[i].getName())) {
                arrayList.add(fileArr[i]);
            }
            i++;
        }
        return arrayList;
    }

    protected static byte[] m1737a(BitSet bitSet) {
        byte[] bArr = new byte[(bitSet.size() / 8)];
        for (int i = 0; i < bitSet.size(); i++) {
            int i2 = i / 8;
            bArr[i2] = (byte) (((bitSet.get(i) ? 1 : 0) << (7 - (i % 8))) | bArr[i2]);
        }
        return bArr;
    }

    protected static byte[] m1738a(byte[] bArr) {
        byte[] bArr2 = null;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.finish();
            gZIPOutputStream.close();
            bArr2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bArr2;
        } catch (Exception e) {
            return bArr2;
        }
    }

    protected static byte[] m1739a(byte[] bArr, int i) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        int indexOf = new String(bArr).indexOf(0);
        if (indexOf <= 0) {
            i = 1;
        } else if (indexOf + 1 <= i) {
            i = indexOf + 1;
        }
        Object obj = new byte[i];
        System.arraycopy(bArr, 0, obj, 0, i);
        obj[i - 1] = null;
        return obj;
    }

    protected static BitSet m1740b(byte[] bArr) {
        BitSet bitSet = new BitSet(bArr.length << 3);
        int i = 0;
        for (byte b : bArr) {
            int i2 = 7;
            while (i2 >= 0) {
                int i3 = i + 1;
                bitSet.set(i, ((b & (1 << i2)) >> i2) == 1);
                i2--;
                i = i3;
            }
        }
        return bitSet;
    }

    private File m1741c(long j) {
        boolean z = false;
        if (Process.myUid() == 1000) {
            return null;
        }
        File file;
        boolean equals;
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = z;
        }
        if (!m1742c() || r0) {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            if (((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize()) <= ((long) (this.f1712c / 2))) {
                return null;
            }
            File file2 = new File(m1735a(this.f1710a).getPath() + File.separator + "carrierdata");
            if (!(file2.exists() && file2.isDirectory())) {
                file2.mkdirs();
            }
            file = new File(file2.getPath() + File.separator + j);
            try {
                z = file.createNewFile();
            } catch (IOException e2) {
            }
        } else {
            file = null;
        }
        return !z ? null : file;
    }

    protected static boolean m1742c() {
        if (VERSION.SDK_INT >= 9) {
            try {
                return ((Boolean) Environment.class.getMethod("isExternalStorageRemovable", null).invoke(null, null)).booleanValue();
            } catch (Exception e) {
            }
        }
        return true;
    }

    private File m1743d() {
        if (Process.myUid() == 1000) {
            return null;
        }
        File file;
        boolean equals;
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = false;
        }
        if (!m1742c() || r0) {
            File file2 = new File(m1735a(this.f1710a).getPath() + File.separator + "carrierdata");
            if (file2.exists() && file2.isDirectory()) {
                File[] listFiles = file2.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    ArrayList a = m1736a(listFiles);
                    if (a.size() == 1) {
                        if (((File) a.get(0)).length() < ((long) this.f1713d)) {
                            file = (File) a.get(0);
                            return file;
                        }
                    } else if (a.size() >= 2) {
                        file = (File) a.get(0);
                        File file3 = (File) a.get(1);
                        if (file.getName().compareTo(file3.getName()) <= 0) {
                            file = file3;
                        }
                        return file;
                    }
                }
            }
        }
        file = null;
        return file;
    }

    private File m1744d(long j) {
        boolean z = false;
        File file = new File(this.f1710a.getFilesDir().getPath() + File.separator + "carrierdata");
        if (!(file.exists() && file.isDirectory())) {
            file.mkdirs();
        }
        File file2 = new File(file.getPath() + File.separator + j);
        try {
            z = file2.createNewFile();
        } catch (IOException e) {
        }
        return z ? file2 : null;
    }

    private int m1745e() {
        boolean equals;
        if (Process.myUid() == 1000) {
            return 0;
        }
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = false;
        }
        if (m1742c() && !r0) {
            return 0;
        }
        File file = new File(m1735a(this.f1710a).getPath() + File.separator + "carrierdata");
        if (!file.exists() || !file.isDirectory()) {
            return 0;
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            return 0;
        }
        ArrayList a = m1736a(listFiles);
        return a.size() == 1 ? ((File) a.get(0)).length() <= 0 ? 10 : 1 : a.size() >= 2 ? 2 : 0;
    }

    private File m1746f() {
        boolean equals;
        if (Process.myUid() == 1000) {
            return null;
        }
        File file;
        try {
            equals = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            equals = false;
        }
        if (!m1742c() || r0) {
            File a = m1735a(this.f1710a);
            if (a != null) {
                File file2 = new File(a.getPath() + File.separator + "carrierdata");
                if (file2.exists() && file2.isDirectory()) {
                    File[] listFiles = file2.listFiles();
                    if (listFiles != null && listFiles.length > 0) {
                        ArrayList a2 = m1736a(listFiles);
                        if (a2.size() >= 2) {
                            a = (File) a2.get(0);
                            file = (File) a2.get(1);
                            if (a.getName().compareTo(file.getName()) <= 0) {
                                file = a;
                            }
                            return file;
                        }
                    }
                }
            }
        }
        file = null;
        return file;
    }

    protected int m1747a() {
        return this.f1711b;
    }

    protected File m1748a(long j) {
        File d = m1743d();
        if (d == null) {
            d = m1741c(j);
        }
        if (d == null) {
            File file = new File(this.f1710a.getFilesDir().getPath() + File.separator + "carrierdata");
            if (file.exists() && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    ArrayList a = m1736a(listFiles);
                    if (a.size() == 1) {
                        if (((File) a.get(0)).length() < ((long) this.f1713d)) {
                            d = (File) a.get(0);
                        }
                    } else if (a.size() >= 2) {
                        d = (File) a.get(0);
                        File file2 = (File) a.get(1);
                        if (d.getName().compareTo(file2.getName()) <= 0) {
                            d = file2;
                        }
                    }
                }
            }
            d = null;
        }
        return d == null ? m1744d(j) : d;
    }

    protected void m1749a(int i) {
        this.f1711b = i;
        this.f1712c = (((this.f1711b << 3) * 1500) + this.f1711b) + 4;
        if (this.f1711b == 256 || this.f1711b == 768) {
            this.f1713d = this.f1712c / 100;
        } else if (this.f1711b == 8736) {
            this.f1713d = this.f1712c - 5000;
        }
    }

    protected File m1750b() {
        File f = m1746f();
        if (f != null) {
            return f;
        }
        File file = new File(this.f1710a.getFilesDir().getPath() + File.separator + "carrierdata");
        if (!file.exists() || !file.isDirectory()) {
            return null;
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            return null;
        }
        ArrayList a = m1736a(listFiles);
        if (a.size() < 2) {
            return null;
        }
        File file2 = (File) a.get(0);
        f = (File) a.get(1);
        return file2.getName().compareTo(f.getName()) > 0 ? f : file2;
    }

    protected boolean m1751b(long j) {
        int e = m1745e();
        if (e != 0) {
            return e == 1 ? m1741c(j) != null : e == 2;
        } else {
            File file = new File(this.f1710a.getFilesDir().getPath() + File.separator + "carrierdata");
            if (file.exists() && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    ArrayList a = m1736a(listFiles);
                    if (a.size() == 1) {
                        e = ((File) a.get(0)).length() <= 0 ? 10 : 1;
                    } else if (a.size() >= 2) {
                        e = 2;
                    }
                    return e != 0 ? false : e != 1 ? m1744d(j) == null : e != 2;
                }
            }
            e = 0;
            if (e != 0) {
                if (e != 1) {
                    if (e != 2) {
                    }
                }
            }
        }
    }
}
