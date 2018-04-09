package com.baidu.location.p005a;

import android.location.Location;
import com.baidu.location.BDLocation;
import com.baidu.location.C0455f;
import com.baidu.location.Jni;
import com.baidu.location.p006h.C0335e;
import com.baidu.location.p006h.C0458a;
import com.baidu.location.p006h.C0467i;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p007b.C0372e;
import com.baidu.location.p011e.C0426h;
import com.baidu.location.p011e.C0426h.C0423a;
import com.baidu.location.p011e.C0426h.C0424b;
import com.baidu.location.p012f.C0441a;
import com.baidu.location.p012f.C0443b;
import com.baidu.location.p012f.C0448d;
import com.baidu.location.p012f.C0451g;
import com.baidu.location.p012f.C0454h;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class C0362s {
    private static C0362s f306A = null;
    private static ArrayList<String> f307b = new ArrayList();
    private static ArrayList<String> f308c = new ArrayList();
    private static ArrayList<String> f309d = new ArrayList();
    private static String f310e = (C0467i.f869a + "/yo.dat");
    private static final String f311f = (C0467i.f869a + "/yoh.dat");
    private static final String f312g = (C0467i.f869a + "/yom.dat");
    private static final String f313h = (C0467i.f869a + "/yol.dat");
    private static final String f314i = (C0467i.f869a + "/yor.dat");
    private static File f315j = null;
    private static int f316k = 8;
    private static int f317l = 8;
    private static int f318m = 16;
    private static int f319n = 1024;
    private static double f320o = 0.0d;
    private static double f321p = 0.1d;
    private static double f322q = 30.0d;
    private static double f323r = 100.0d;
    private static int f324s = 0;
    private static int f325t = 64;
    private static int f326u = 128;
    private static Location f327v = null;
    private static Location f328w = null;
    private static Location f329x = null;
    private static C0451g f330y = null;
    private int f331B;
    long f332a;
    private C0361a f333z;

    private class C0361a extends C0335e {
        boolean f301a;
        int f302b;
        int f303c;
        final /* synthetic */ C0362s f304d;
        private ArrayList<String> f305e;

        public C0361a(C0362s c0362s) {
            this.f304d = c0362s;
            this.f301a = false;
            this.f302b = 0;
            this.f303c = 0;
            this.f305e = null;
            this.k = new HashMap();
        }

        public void mo1741a() {
            this.h = C0468j.m1023c();
            this.i = 2;
            if (this.f305e != null) {
                for (int i = 0; i < this.f305e.size(); i++) {
                    if (this.f302b == 1) {
                        this.k.put("cldc[" + i + "]", this.f305e.get(i));
                    } else {
                        this.k.put("cltr[" + i + "]", this.f305e.get(i));
                    }
                }
                this.k.put("trtm", String.format(Locale.CHINA, "%d", new Object[]{Long.valueOf(System.currentTimeMillis())}));
            }
        }

        public void mo1742a(boolean z) {
            if (!(!z || this.j == null || this.f305e == null)) {
                this.f305e.clear();
            }
            if (this.k != null) {
                this.k.clear();
            }
            this.f301a = false;
        }

        public void mo1747b() {
            if (!this.f301a) {
                if (o <= 4 || this.f303c >= o) {
                    this.f303c = 0;
                    this.f301a = true;
                    this.f302b = 0;
                    if (this.f305e == null || this.f305e.size() < 1) {
                        if (this.f305e == null) {
                            this.f305e = new ArrayList();
                        }
                        this.f302b = 0;
                        int i = 0;
                        while (true) {
                            String b = this.f302b < 2 ? C0362s.m360b() : null;
                            if (b != null || this.f302b == 1) {
                                this.f302b = 1;
                            } else {
                                this.f302b = 2;
                                try {
                                    b = C0343f.m256b();
                                } catch (Exception e) {
                                    b = null;
                                }
                            }
                            if (b == null) {
                                break;
                            } else if (!b.contains("err!")) {
                                this.f305e.add(b);
                                i += b.length();
                                if (i >= C0458a.f834i) {
                                    break;
                                }
                            }
                        }
                    }
                    if (this.f305e == null || this.f305e.size() < 1) {
                        this.f305e = null;
                        this.f301a = false;
                        return;
                    }
                    m204e();
                    return;
                }
                this.f303c++;
            }
        }
    }

    private C0362s() {
        this.f333z = null;
        this.f331B = 0;
        this.f332a = 0;
        this.f333z = new C0361a(this);
        this.f331B = 0;
    }

    private static synchronized int m349a(List<String> list, int i) {
        int i2;
        synchronized (C0362s.class) {
            if (list == null || i > 256 || i < 0) {
                i2 = -1;
            } else {
                try {
                    if (f315j == null) {
                        f315j = new File(f310e);
                        if (!f315j.exists()) {
                            f315j = null;
                            i2 = -2;
                        }
                    }
                    RandomAccessFile randomAccessFile = new RandomAccessFile(f315j, "rw");
                    if (randomAccessFile.length() < 1) {
                        randomAccessFile.close();
                        i2 = -3;
                    } else {
                        randomAccessFile.seek((long) i);
                        i2 = randomAccessFile.readInt();
                        int readInt = randomAccessFile.readInt();
                        int readInt2 = randomAccessFile.readInt();
                        int readInt3 = randomAccessFile.readInt();
                        long readLong = randomAccessFile.readLong();
                        if (!C0362s.m355a(i2, readInt, readInt2, readInt3, readLong) || readInt < 1) {
                            randomAccessFile.close();
                            i2 = -4;
                        } else {
                            byte[] bArr = new byte[f319n];
                            int i3 = readInt;
                            readInt = f316k;
                            while (readInt > 0 && i3 > 0) {
                                randomAccessFile.seek(((long) ((((i2 + i3) - 1) % readInt2) * readInt3)) + readLong);
                                int readInt4 = randomAccessFile.readInt();
                                if (readInt4 > 0 && readInt4 < readInt3) {
                                    randomAccessFile.read(bArr, 0, readInt4);
                                    if (bArr[readInt4 - 1] == (byte) 0) {
                                        list.add(new String(bArr, 0, readInt4 - 1));
                                    }
                                }
                                readInt--;
                                i3--;
                            }
                            randomAccessFile.seek((long) i);
                            randomAccessFile.writeInt(i2);
                            randomAccessFile.writeInt(i3);
                            randomAccessFile.writeInt(readInt2);
                            randomAccessFile.writeInt(readInt3);
                            randomAccessFile.writeLong(readLong);
                            randomAccessFile.close();
                            i2 = f316k - readInt;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    i2 = -5;
                }
            }
        }
        return i2;
    }

    public static synchronized C0362s m350a() {
        C0362s c0362s;
        synchronized (C0362s.class) {
            if (f306A == null) {
                f306A = new C0362s();
            }
            c0362s = f306A;
        }
        return c0362s;
    }

    public static String m351a(int i) {
        String str;
        String str2 = null;
        String str3;
        if (i == 1) {
            str3 = f311f;
            str = str3;
            List list = f307b;
        } else if (i == 2) {
            str3 = f312g;
            str = str3;
            r2 = f308c;
        } else if (i == 3) {
            str3 = f313h;
            str = str3;
            r2 = f309d;
        } else if (i != 4) {
            return null;
        } else {
            str3 = f314i;
            str = str3;
            r2 = f309d;
        }
        if (list == null) {
            return null;
        }
        if (list.size() < 1) {
            C0362s.m359a(str, list);
        }
        synchronized (C0362s.class) {
            int size = list.size();
            if (size > 0) {
                try {
                    str = (String) list.get(size - 1);
                    try {
                        list.remove(size - 1);
                    } catch (Exception e) {
                        str2 = str;
                        str = str2;
                        return str;
                    }
                } catch (Exception e2) {
                    str = str2;
                    return str;
                }
            }
            str = null;
        }
        return str;
    }

    public static void m352a(int i, boolean z) {
        String str;
        Object obj;
        String str2;
        if (i == 1) {
            str2 = f311f;
            if (!z) {
                str = str2;
                List list = f307b;
            } else {
                return;
            }
        } else if (i == 2) {
            str2 = f312g;
            if (z) {
                str = str2;
                obj = f307b;
            } else {
                str = str2;
                obj = f308c;
            }
        } else if (i == 3) {
            str2 = f313h;
            if (z) {
                str = str2;
                obj = f308c;
            } else {
                str = str2;
                obj = f309d;
            }
        } else if (i == 4) {
            str2 = f314i;
            if (z) {
                str = str2;
                obj = f309d;
            } else {
                return;
            }
        } else {
            return;
        }
        File file = new File(str);
        if (!file.exists()) {
            C0362s.m354a(str);
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(4);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            int readInt3 = randomAccessFile.readInt();
            int readInt4 = randomAccessFile.readInt();
            int readInt5 = randomAccessFile.readInt();
            int size = list.size();
            int i2 = readInt5;
            while (size > f317l) {
                readInt5 = z ? i2 + 1 : i2;
                byte[] bytes;
                if (readInt3 >= readInt) {
                    if (!z) {
                        obj = 1;
                        i2 = readInt5;
                        break;
                    }
                    randomAccessFile.seek((long) ((readInt4 * readInt2) + 128));
                    bytes = (((String) list.get(0)) + '\u0000').getBytes();
                    randomAccessFile.writeInt(bytes.length);
                    randomAccessFile.write(bytes, 0, bytes.length);
                    list.remove(0);
                    i2 = readInt4 + 1;
                    if (i2 > readInt3) {
                        i2 = 0;
                    }
                    readInt4 = readInt3;
                } else {
                    randomAccessFile.seek((long) ((readInt2 * readInt3) + 128));
                    bytes = (((String) list.get(0)) + '\u0000').getBytes();
                    randomAccessFile.writeInt(bytes.length);
                    randomAccessFile.write(bytes, 0, bytes.length);
                    list.remove(0);
                    int i3 = readInt4;
                    readInt4 = readInt3 + 1;
                    i2 = i3;
                }
                size--;
                readInt3 = readInt4;
                readInt4 = i2;
                i2 = readInt5;
            }
            obj = null;
            randomAccessFile.seek(12);
            randomAccessFile.writeInt(readInt3);
            randomAccessFile.writeInt(readInt4);
            randomAccessFile.writeInt(i2);
            randomAccessFile.close();
            if (obj != null && i < 4) {
                C0362s.m352a(i + 1, true);
            }
        } catch (Exception e) {
        }
    }

    public static void m353a(C0441a c0441a, C0451g c0451g, Location location, String str) {
        if (!C0372e.m404a().f369a) {
            return;
        }
        if (C0468j.f917s != 3 || C0362s.m357a(location, c0451g) || C0362s.m358a(location, false)) {
            BDLocation a;
            String str2;
            if (C0468j.m1015a(C0455f.getServiceContext())) {
                a = C0426h.m767a().m776a(c0441a, c0451g, null, C0424b.IS_MIX_MODE, C0423a.NO_NEED_TO_LOG);
            } else {
                a = C0426h.m767a().m776a(c0441a, c0451g, null, C0424b.IS_NOT_MIX_MODE, C0423a.NO_NEED_TO_LOG);
            }
            if (a == null || a.getLocType() == 67) {
                str2 = str + String.format(Locale.CHINA, "&ofl=%s|0", new Object[]{"1"});
            } else {
                int i = 0;
                if (a.getNetworkLocationType().equals("cl")) {
                    i = 1;
                } else if (a.getNetworkLocationType().equals("wf")) {
                    i = 2;
                }
                str2 = str + String.format(Locale.CHINA, "&ofl=%s|%d|%f|%f|%d", new Object[]{"1", Integer.valueOf(i), Double.valueOf(a.getLongitude()), Double.valueOf(a.getLatitude()), Integer.valueOf((int) a.getRadius())});
            }
            if (c0441a != null && c0441a.m841a()) {
                if (!C0362s.m357a(location, c0451g)) {
                    c0451g = null;
                }
                str2 = C0468j.m1013a(c0441a, c0451g, location, str2, 1);
                if (str2 != null) {
                    C0362s.m362c(Jni.encode(str2));
                    f328w = location;
                    f327v = location;
                    if (c0451g != null) {
                        f330y = c0451g;
                    }
                }
            } else if (c0451g != null && c0451g.m947h() && C0362s.m357a(location, c0451g)) {
                if (!C0362s.m356a(location) && !C0443b.m855a().m871d()) {
                    str2 = "&cfr=1" + str2;
                } else if (!C0362s.m356a(location) && C0443b.m855a().m871d()) {
                    str2 = "&cfr=3" + str2;
                } else if (C0443b.m855a().m871d()) {
                    str2 = "&cfr=2" + str2;
                }
                str2 = C0468j.m1013a(c0441a, c0451g, location, str2, 2);
                if (str2 != null) {
                    C0362s.m364d(Jni.encode(str2));
                    f329x = location;
                    f327v = location;
                    if (c0451g != null) {
                        f330y = c0451g;
                    }
                }
            } else {
                if (!C0362s.m356a(location) && !C0443b.m855a().m871d()) {
                    str2 = "&cfr=1" + str2;
                } else if (!C0362s.m356a(location) && C0443b.m855a().m871d()) {
                    str2 = "&cfr=3" + str2;
                } else if (C0443b.m855a().m871d()) {
                    str2 = "&cfr=2" + str2;
                }
                if (!C0362s.m357a(location, c0451g)) {
                    c0451g = null;
                }
                if (c0441a != null || c0451g != null) {
                    str2 = C0468j.m1013a(c0441a, c0451g, location, str2, 3);
                    if (str2 != null) {
                        C0362s.m366e(Jni.encode(str2));
                        f327v = location;
                        if (c0451g != null) {
                            f330y = c0451g;
                        }
                    }
                }
            }
        }
    }

    public static void m354a(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                File file2 = new File(C0467i.f869a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (!file.createNewFile()) {
                    file = null;
                }
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(0);
                randomAccessFile.writeInt(32);
                randomAccessFile.writeInt(2048);
                randomAccessFile.writeInt(1040);
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(0);
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    private static boolean m355a(int i, int i2, int i3, int i4, long j) {
        return i >= 0 && i < i3 && i2 >= 0 && i2 <= i3 && i3 >= 0 && i3 <= 1024 && i4 >= 128 && i4 <= 1024;
    }

    private static boolean m356a(Location location) {
        if (location == null) {
            return false;
        }
        if (f328w == null || f327v == null) {
            f328w = location;
            return true;
        }
        double distanceTo = (double) location.distanceTo(f328w);
        return ((double) location.distanceTo(f327v)) > ((distanceTo * ((double) C0468j.f889Q)) + ((((double) C0468j.f888P) * distanceTo) * distanceTo)) + ((double) C0468j.f890R);
    }

    private static boolean m357a(Location location, C0451g c0451g) {
        if (location == null || c0451g == null || c0451g.f802a == null || c0451g.f802a.isEmpty() || c0451g.m939b(f330y)) {
            return false;
        }
        if (f329x != null) {
            return true;
        }
        f329x = location;
        return true;
    }

    public static boolean m358a(Location location, boolean z) {
        return C0448d.m897a(f327v, location, z);
    }

    public static boolean m359a(String str, List<String> list) {
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(8);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            int readInt3 = randomAccessFile.readInt();
            byte[] bArr = new byte[f319n];
            int i = readInt2;
            readInt2 = f317l + 1;
            boolean z = false;
            while (readInt2 > 0 && i > 0) {
                if (i < readInt3) {
                    readInt3 = 0;
                }
                try {
                    randomAccessFile.seek((long) (((i - 1) * readInt) + 128));
                    int readInt4 = randomAccessFile.readInt();
                    if (readInt4 > 0 && readInt4 < readInt) {
                        randomAccessFile.read(bArr, 0, readInt4);
                        if (bArr[readInt4 - 1] == (byte) 0) {
                            list.add(0, new String(bArr, 0, readInt4 - 1));
                            z = true;
                        }
                    }
                    readInt2--;
                    i--;
                } catch (Exception e) {
                    return z;
                }
            }
            randomAccessFile.seek(12);
            randomAccessFile.writeInt(i);
            randomAccessFile.writeInt(readInt3);
            randomAccessFile.close();
            return z;
        } catch (Exception e2) {
            return false;
        }
    }

    public static String m360b() {
        return C0362s.m363d();
    }

    public static synchronized void m361b(String str) {
        synchronized (C0362s.class) {
            if (!str.contains("err!")) {
                List list;
                int i = C0468j.f912n;
                if (i == 1) {
                    list = f307b;
                } else if (i == 2) {
                    list = f308c;
                } else if (i == 3) {
                    list = f309d;
                }
                if (list != null) {
                    if (list.size() <= f318m) {
                        list.add(str);
                    }
                    if (list.size() >= f318m) {
                        C0362s.m352a(i, false);
                    }
                    while (list.size() > f318m) {
                        list.remove(0);
                    }
                }
            }
        }
    }

    private static void m362c(String str) {
        C0362s.m361b(str);
    }

    public static String m363d() {
        String str = null;
        for (int i = 1; i < 5; i++) {
            str = C0362s.m351a(i);
            if (str != null) {
                return str;
            }
        }
        C0362s.m349a(f309d, f325t);
        if (f309d.size() > 0) {
            str = (String) f309d.get(0);
            f309d.remove(0);
        }
        if (str != null) {
            return str;
        }
        C0362s.m349a(f309d, f324s);
        if (f309d.size() > 0) {
            str = (String) f309d.get(0);
            f309d.remove(0);
        }
        if (str != null) {
            return str;
        }
        C0362s.m349a(f309d, f326u);
        if (f309d.size() <= 0) {
            return str;
        }
        str = (String) f309d.get(0);
        f309d.remove(0);
        return str;
    }

    private static void m364d(String str) {
        C0362s.m361b(str);
    }

    public static void m365e() {
        f317l = 0;
        C0362s.m352a(1, false);
        C0362s.m352a(2, false);
        C0362s.m352a(3, false);
        f317l = 8;
    }

    private static void m366e(String str) {
        C0362s.m361b(str);
    }

    public static String m367f() {
        RandomAccessFile randomAccessFile;
        int readInt;
        File file = new File(f312g);
        if (file.exists()) {
            try {
                randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(20);
                readInt = randomAccessFile.readInt();
                if (readInt > 128) {
                    String str = "&p1=" + readInt;
                    randomAccessFile.seek(20);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.close();
                    return str;
                }
                randomAccessFile.close();
            } catch (Exception e) {
            }
        }
        file = new File(f313h);
        if (file.exists()) {
            try {
                randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(20);
                readInt = randomAccessFile.readInt();
                if (readInt > 256) {
                    str = "&p2=" + readInt;
                    randomAccessFile.seek(20);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.close();
                    return str;
                }
                randomAccessFile.close();
            } catch (Exception e2) {
            }
        }
        file = new File(f314i);
        if (!file.exists()) {
            return null;
        }
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(20);
            readInt = randomAccessFile.readInt();
            if (readInt > 512) {
                str = "&p3=" + readInt;
                randomAccessFile.seek(20);
                randomAccessFile.writeInt(0);
                randomAccessFile.close();
                return str;
            }
            randomAccessFile.close();
            return null;
        } catch (Exception e3) {
            return null;
        }
    }

    public void m368c() {
        if (C0454h.m952h()) {
            this.f333z.mo1747b();
        }
    }
}
