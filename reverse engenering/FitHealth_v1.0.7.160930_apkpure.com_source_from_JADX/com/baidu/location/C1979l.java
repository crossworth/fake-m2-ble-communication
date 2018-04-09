package com.baidu.location;

import android.location.Location;
import com.baidu.location.C1981n.C0529a;
import com.baidu.location.ai.C0503b;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.message.BasicNameValuePair;

class C1979l implements an, C1619j {
    private static int b0 = 8;
    private static int b1 = 0;
    private static final int b2 = 1040;
    private static Location b3 = null;
    private static final int b4 = 32;
    private static C0503b b5 = null;
    private static ArrayList b6 = new ArrayList();
    private static int b7 = 5;
    private static ArrayList b8 = new ArrayList();
    private static final String b9 = (C1976f.L + "/yol.dat");
    private static Location bN = null;
    private static final int bO = 2048;
    private static final int bP = 2048;
    private static double bQ = 100.0d;
    private static double bR = 0.0d;
    private static int bS = 16;
    private static int bT = 8;
    private static int bU = 1024;
    private static int bV = 64;
    private static File bW = null;
    private static final int bX = 128;
    private static ArrayList bY = new ArrayList();
    private static double bZ = 30.0d;
    private static int ca = 256;
    private static ArrayList cc = new ArrayList();
    private static double cd = 0.1d;
    private static int ce = 1024;
    private static final String cf = (C1976f.L + "/yor.dat");
    private static Location cg = null;
    private static ArrayList ch = new ArrayList();
    private static final int ci = 2048;
    private static final String cj = (C1976f.L + "/yom.dat");
    private static final String ck = (C1976f.L + "/yoh.dat");
    private static int cl = 128;
    private static int cm = 512;
    private static ArrayList cn = new ArrayList();
    private static C1979l co = null;
    private static String cp = (C1976f.L + "/yo.dat");
    private C2061a cb;

    private class C2061a extends C1982o {
        final /* synthetic */ C1979l cR;
        boolean cS;
        private ArrayList cT;
        int cU;

        public C2061a(C1979l c1979l) {
            this.cR = c1979l;
            this.cS = false;
            this.cU = 0;
            this.cT = null;
            this.cP = new ArrayList();
        }

        void mo3704V() {
            this.cL = C1974b.m5924int();
            this.cO = 2;
            if (this.cT != null) {
                for (int i = 0; i < this.cT.size(); i++) {
                    if (this.cU == 1) {
                        this.cP.add(new BasicNameValuePair("cldc[" + i + "]", (String) this.cT.get(i)));
                    } else {
                        this.cP.add(new BasicNameValuePair("cltr[" + i + "]", (String) this.cT.get(i)));
                    }
                }
                if (this.cU == 2) {
                    this.cP.add(new BasicNameValuePair("trtm", String.format(Locale.CHINA, "%d", new Object[]{Long.valueOf(System.currentTimeMillis())})));
                }
            }
        }

        public void m6263W() {
            if (!this.cS) {
                this.cS = true;
                this.cU = 0;
                if (this.cT == null) {
                    this.cU = 0;
                    this.cT = new ArrayList();
                    int i = 0;
                    do {
                        String w = this.cU < 2 ? C1979l.m5996w() : null;
                        if (w != null || this.cU == 1) {
                            this.cU = 1;
                        } else {
                            this.cU = 2;
                            try {
                                if (C1974b.aO == 0) {
                                    w = C1978h.m5962l();
                                    if (w == null) {
                                        w = C1983r.at();
                                    }
                                } else if (C1974b.aO == 1) {
                                    w = C1983r.at();
                                    if (w == null) {
                                        w = C1978h.m5962l();
                                    }
                                }
                            } catch (Exception e) {
                                w = null;
                            }
                        }
                        if (w == null) {
                            break;
                        }
                        this.cT.add(w);
                        i += w.length();
                    } while (i < 2048);
                }
                if (this.cT == null || this.cT.size() < 1) {
                    this.cT = null;
                    this.cS = false;
                    return;
                }
                m6032R();
            }
        }

        void mo3705if(boolean z) {
            if (!(!z || this.cM == null || this.cT == null)) {
                this.cT.clear();
            }
            if (this.cP != null) {
                this.cP.clear();
            }
            this.cS = false;
        }
    }

    private C1979l() {
        this.cb = null;
        this.cb = new C2061a(this);
    }

    public static void m5973A() {
        BufferedWriter bufferedWriter;
        String str;
        String w = C1979l.m5996w();
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(C1976f.L + "/out.txt"), true));
            str = w;
        } catch (Exception e) {
            bufferedWriter = null;
            str = w;
        }
        while (str != null) {
            try {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
                str = C1979l.m5996w();
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        bufferedWriter.close();
    }

    public static void m5974byte(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                File file2 = new File(C1976f.L);
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
                randomAccessFile.writeInt(b2);
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(0);
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    public static void m5975case(String str) {
        List list;
        int i = C1974b.aF;
        if (i == 1) {
            list = cn;
        } else if (i == 2) {
            list = b8;
        } else if (i == 3) {
            list = ch;
        } else {
            return;
        }
        if (list != null) {
            if (list.size() <= bS) {
                list.add(str);
            }
            if (list.size() >= bS) {
                C1979l.m5984if(i, false);
            }
            while (list.size() > bS) {
                list.remove(0);
            }
        }
    }

    private static void m5976char(String str) {
        C1979l.m5975case(str);
    }

    private static void m5977else(String str) {
        C1979l.m5975case(str);
    }

    private static void m5978goto(String str) {
        C1979l.m5975case(str);
    }

    private static int m5979if(List list, int i) {
        if (list == null || i > 256 || i < 0) {
            return -1;
        }
        try {
            if (bW == null) {
                bW = new File(cp);
                if (!bW.exists()) {
                    bW = null;
                    return -2;
                }
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(bW, "rw");
            if (randomAccessFile.length() < 1) {
                randomAccessFile.close();
                return -3;
            }
            randomAccessFile.seek((long) i);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            int readInt3 = randomAccessFile.readInt();
            int readInt4 = randomAccessFile.readInt();
            long readLong = randomAccessFile.readLong();
            if (!C1979l.m5988if(readInt, readInt2, readInt3, readInt4, readLong) || readInt2 < 1) {
                randomAccessFile.close();
                return -4;
            }
            byte[] bArr = new byte[ce];
            int i2 = readInt2;
            readInt2 = bT;
            while (readInt2 > 0 && i2 > 0) {
                randomAccessFile.seek(((long) ((((readInt + i2) - 1) % readInt3) * readInt4)) + readLong);
                int readInt5 = randomAccessFile.readInt();
                if (readInt5 > 0 && readInt5 < readInt4) {
                    randomAccessFile.read(bArr, 0, readInt5);
                    if (bArr[readInt5 - 1] == (byte) 0) {
                        list.add(new String(bArr, 0, readInt5 - 1));
                    }
                }
                readInt2--;
                i2--;
            }
            randomAccessFile.seek((long) i);
            randomAccessFile.writeInt(readInt);
            randomAccessFile.writeInt(i2);
            randomAccessFile.writeInt(readInt3);
            randomAccessFile.writeInt(readInt4);
            randomAccessFile.writeLong(readLong);
            randomAccessFile.close();
            return bT - readInt2;
        } catch (Exception e) {
            e.printStackTrace();
            return -5;
        }
    }

    public static String m5980if(int i) {
        String str;
        List list;
        if (i == 1) {
            str = ck;
            list = cn;
        } else if (i == 2) {
            str = cj;
            list = b8;
        } else if (i == 3) {
            str = b9;
            list = ch;
        } else if (i != 4) {
            return null;
        } else {
            str = cf;
            list = ch;
        }
        if (list == null) {
            return null;
        }
        if (list.size() < 1) {
            C1979l.m5992if(str, list);
        }
        if (list.size() <= 0) {
            return null;
        }
        String str2 = (String) list.get(0);
        list.remove(0);
        return str2;
    }

    public static void m5981if(double d, double d2, double d3, double d4) {
        if (d <= 0.0d) {
            d = bR;
        }
        bR = d;
        cd = d2;
        if (d3 <= 20.0d) {
            d3 = bZ;
        }
        bZ = d3;
        bQ = d4;
    }

    public static void m5982if(int i, int i2) {
        String str = "I'm test string!!I'm test string!!I'm test string!!I'm test sting!!I'm test sting!!I'm test sting!!I'm test sting!!I'm test sting!!I'm test sting!!";
        for (int i3 = 0; i3 < i; i3++) {
            C1979l.m5986if("&p=" + i2 + "&number=" + i3 + str, i2);
        }
    }

    public static void m5983if(int i, int i2, boolean z) {
        String str = "I'm test string!!I'm test string!!I'm test string!!I'm test sting!!I'm test sting!!I'm test sting!!I'm test sting!!I'm test sting!!I'm test sting!!";
        for (int i3 = 0; i3 < i; i3++) {
            C1979l.m5987if("&p=" + i2 + "&number=" + i3 + str, i2, z);
        }
    }

    public static void m5984if(int i, boolean z) {
        String str;
        Object obj;
        String str2;
        if (i == 1) {
            str2 = ck;
            if (!z) {
                str = str2;
                List list = cn;
            } else {
                return;
            }
        } else if (i == 2) {
            str2 = cj;
            if (z) {
                str = str2;
                obj = cn;
            } else {
                str = str2;
                obj = b8;
            }
        } else if (i == 3) {
            str2 = b9;
            if (z) {
                str = str2;
                obj = b8;
            } else {
                str = str2;
                obj = ch;
            }
        } else if (i == 4) {
            str2 = cf;
            if (z) {
                str = str2;
                obj = ch;
            } else {
                return;
            }
        } else {
            return;
        }
        File file = new File(str);
        if (!file.exists()) {
            C1979l.m5974byte(str);
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
            while (size > b0) {
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
                C1979l.m5984if(i + 1, true);
            }
        } catch (Exception e) {
        }
    }

    public static void m5985if(C0529a c0529a, C0503b c0503b, Location location, String str) {
        C0503b c0503b2 = null;
        if (!C1985t.e6) {
            return;
        }
        if (C1974b.ag == 3 && !C1979l.m5990if(location, c0503b) && !C1979l.m5991if(location, false)) {
            return;
        }
        String str2;
        if (c0529a != null && c0529a.m2201do()) {
            if (!C1979l.m5990if(location, c0503b)) {
                c0503b = null;
            }
            str2 = C1974b.m5919if(c0529a, c0503b, location, str, 1);
            if (str2 != null) {
                C1979l.m5977else(Jni.m5811f(str2));
                cg = location;
                b3 = location;
                if (c0503b != null) {
                    b5 = c0503b;
                }
            }
        } else if (c0503b != null && c0503b.m2158if() && C1979l.m5990if(location, c0503b)) {
            C0529a c0529a2;
            if (C1979l.m5989if(location)) {
                c0529a2 = c0529a;
            }
            str2 = C1974b.m5919if(c0529a2, c0503b, location, str, 2);
            if (str2 != null) {
                C1979l.m5976char(Jni.m5811f(str2));
                bN = location;
                b3 = location;
                if (c0503b != null) {
                    b5 = c0503b;
                }
            }
        } else {
            if (!C1979l.m5989if(location)) {
                c0529a = null;
            }
            if (C1979l.m5990if(location, c0503b)) {
                c0503b2 = c0503b;
            }
            if (c0529a != null || c0503b2 != null) {
                String str3 = C1974b.m5919if(c0529a, c0503b2, location, str, 3);
                if (str3 != null) {
                    C1979l.m5978goto(Jni.m5811f(str3));
                    b3 = location;
                    if (c0503b2 != null) {
                        b5 = c0503b2;
                    }
                }
            }
        }
    }

    public static void m5986if(String str, int i) {
        C1974b.aF = i;
        C1979l.m5975case(str);
    }

    public static void m5987if(String str, int i, boolean z) {
        C1974b.aF = i;
        String str2 = null;
        if (z) {
            str2 = Jni.m5811f(str);
        }
        C1979l.m5975case(str2);
    }

    private static boolean m5988if(int i, int i2, int i3, int i4, long j) {
        return i >= 0 && i < i3 && i2 >= 0 && i2 <= i3 && i3 >= 0 && i3 <= 1024 && i4 >= 128 && i4 <= 1024;
    }

    private static boolean m5989if(Location location) {
        if (location == null) {
            return false;
        }
        if (cg == null || b3 == null) {
            cg = location;
            return true;
        }
        double distanceTo = (double) location.distanceTo(cg);
        return ((double) location.distanceTo(b3)) > ((distanceTo * ((double) C1974b.a1)) + ((((double) C1974b.a4) * distanceTo) * distanceTo)) + ((double) C1974b.aZ);
    }

    private static boolean m5990if(Location location, C0503b c0503b) {
        if (location == null || c0503b == null || c0503b.f2165for == null || c0503b.f2165for.isEmpty() || c0503b.m2154do(b5)) {
            return false;
        }
        if (bN != null) {
            return true;
        }
        bN = location;
        return true;
    }

    public static boolean m5991if(Location location, boolean z) {
        return C1984s.m6059if(b3, location, z);
    }

    public static boolean m5992if(String str, List list) {
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
            byte[] bArr = new byte[ce];
            int i = readInt2;
            readInt2 = b0 + 1;
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
                            list.add(new String(bArr, 0, readInt4 - 1));
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

    public static void m5993t() {
        b0 = 0;
        C1979l.m5984if(1, false);
        C1979l.m5984if(2, false);
        C1979l.m5984if(3, false);
        b0 = 8;
    }

    public static C1979l m5994u() {
        if (co == null) {
            co = new C1979l();
        }
        return co;
    }

    public static String m5995v() {
        RandomAccessFile randomAccessFile;
        int readInt;
        File file = new File(cj);
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
        file = new File(b9);
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
        file = new File(cf);
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

    public static String m5996w() {
        return C1979l.m5998y();
    }

    public static void m5997x() {
        C1979l.m5982if(22, 1);
    }

    public static String m5998y() {
        String str = null;
        for (int i = 1; i < 5; i++) {
            str = C1979l.m5980if(i);
            if (str != null) {
                return str;
            }
        }
        C1979l.m5979if(ch, bV);
        if (ch.size() > 0) {
            str = (String) ch.get(0);
            ch.remove(0);
        }
        if (str != null) {
            return str;
        }
        C1979l.m5979if(ch, b1);
        if (ch.size() > 0) {
            str = (String) ch.get(0);
            ch.remove(0);
        }
        if (str != null) {
            return str;
        }
        C1979l.m5979if(ch, cl);
        if (ch.size() <= 0) {
            return str;
        }
        str = (String) ch.get(0);
        ch.remove(0);
        return str;
    }

    public void m5999z() {
        if (ai.bf()) {
            this.cb.m6263W();
        }
    }
}
