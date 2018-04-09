package com.baidu.location.p005a;

import android.location.Location;
import com.baidu.location.Jni;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0467i;
import com.baidu.location.p006h.C0468j;
import com.baidu.mapapi.UIMsg.m_AppUI;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;
import org.andengine.util.time.TimeConstants;

public class C0343f {
    private static C0343f f205a = null;
    private static String f206b = "Temp_in.dat";
    private static File f207c = new File(C0467i.f869a, f206b);
    private static StringBuffer f208d = null;
    private static boolean f209e = true;
    private static int f210f = 0;
    private static int f211g = 0;
    private static long f212h = 0;
    private static long f213i = 0;
    private static long f214j = 0;
    private static double f215k = 0.0d;
    private static double f216l = 0.0d;
    private static int f217m = 0;
    private static int f218n = 0;
    private static int f219o = 0;
    private String f220p = null;
    private boolean f221q = true;

    private C0343f(String str) {
        if (str == null) {
            str = "";
        } else if (str.length() > 100) {
            str = str.substring(0, 100);
        }
        this.f220p = str;
    }

    public static C0343f m251a() {
        if (f205a == null) {
            f205a = new C0343f(C0459b.m980a().m986c());
        }
        return f205a;
    }

    private String m252a(int i) {
        if (!f207c.exists()) {
            return null;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(f207c, "rw");
            randomAccessFile.seek(0);
            int readInt = randomAccessFile.readInt();
            if (!C0343f.m253a(readInt, randomAccessFile.readInt(), randomAccessFile.readInt())) {
                randomAccessFile.close();
                C0343f.m258d();
                return null;
            } else if (i == 0 || i == readInt + 1) {
                randomAccessFile.close();
                return null;
            } else {
                long j = (12 + 0) + ((long) ((i - 1) * 1024));
                randomAccessFile.seek(j);
                int readInt2 = randomAccessFile.readInt();
                byte[] bArr = new byte[readInt2];
                randomAccessFile.seek(j + 4);
                for (readInt = 0; readInt < readInt2; readInt++) {
                    bArr[readInt] = randomAccessFile.readByte();
                }
                randomAccessFile.close();
                return new String(bArr);
            }
        } catch (IOException e) {
            return null;
        }
    }

    private static boolean m253a(int i, int i2, int i3) {
        return (i < 0 || i > C0468j.ab) ? false : (i2 < 0 || i2 > i + 1) ? false : i3 >= 1 && i3 <= i + 1 && i3 <= C0468j.ab;
    }

    private boolean m254a(Location location, int i, int i2) {
        if (location == null || !C0468j.f896X || !this.f221q) {
            return false;
        }
        if (C0468j.f898Z < 5) {
            C0468j.f898Z = 5;
        } else if (C0468j.f898Z > 1000) {
            C0468j.f898Z = 1000;
        }
        if (C0468j.aa < 5) {
            C0468j.aa = 5;
        } else if (C0468j.aa > TimeConstants.SECONDS_PER_HOUR) {
            C0468j.aa = TimeConstants.SECONDS_PER_HOUR;
        }
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        long time = location.getTime() / 1000;
        if (f209e) {
            f210f = 1;
            f208d = new StringBuffer("");
            f208d.append(String.format(Locale.CHINA, "&nr=%s&traj=%d,%.5f,%.5f|", new Object[]{this.f220p, Long.valueOf(time), Double.valueOf(longitude), Double.valueOf(latitude)}));
            f211g = f208d.length();
            f212h = time;
            f215k = longitude;
            f216l = latitude;
            f213i = (long) Math.floor((longitude * 100000.0d) + 0.5d);
            f214j = (long) Math.floor((latitude * 100000.0d) + 0.5d);
            f209e = false;
            return true;
        }
        float[] fArr = new float[1];
        Location.distanceBetween(latitude, longitude, f216l, f215k, fArr);
        long j = time - f212h;
        if (fArr[0] < ((float) C0468j.f898Z) && j < ((long) C0468j.aa)) {
            return false;
        }
        if (f208d == null) {
            f210f++;
            f211g = 0;
            f208d = new StringBuffer("");
            f208d.append(String.format(Locale.CHINA, "&nr=%s&traj=%d,%.5f,%.5f|", new Object[]{this.f220p, Long.valueOf(time), Double.valueOf(longitude), Double.valueOf(latitude)}));
            f211g = f208d.length();
            f212h = time;
            f215k = longitude;
            f216l = latitude;
            f213i = (long) Math.floor((longitude * 100000.0d) + 0.5d);
            f214j = (long) Math.floor((latitude * 100000.0d) + 0.5d);
        } else {
            f215k = longitude;
            f216l = latitude;
            long floor = (long) Math.floor((longitude * 100000.0d) + 0.5d);
            long floor2 = (long) Math.floor((latitude * 100000.0d) + 0.5d);
            f217m = (int) (time - f212h);
            f218n = (int) (floor - f213i);
            f219o = (int) (floor2 - f214j);
            f208d.append(String.format(Locale.CHINA, "%d,%d,%d|", new Object[]{Integer.valueOf(f217m), Integer.valueOf(f218n), Integer.valueOf(f219o)}));
            f211g = f208d.length();
            f212h = time;
            f213i = floor;
            f214j = floor2;
        }
        if (f211g + 15 > 750) {
            m255a(f208d.toString());
            f208d = null;
        }
        if (f210f >= C0468j.ab) {
            this.f221q = false;
        }
        return true;
    }

    private boolean m255a(String str) {
        if (str == null || !str.startsWith("&nr")) {
            return false;
        }
        if (!f207c.exists() && !C0343f.m258d()) {
            return false;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(f207c, "rw");
            randomAccessFile.seek(0);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            int readInt3 = randomAccessFile.readInt();
            if (C0343f.m253a(readInt, readInt2, readInt3)) {
                if (C0468j.f897Y) {
                    if (readInt == C0468j.ab) {
                        if (str.equals(m252a(readInt3 == 1 ? C0468j.ab : readInt3 - 1))) {
                            randomAccessFile.close();
                            return false;
                        }
                    } else if (readInt3 > 1 && str.equals(m252a(readInt3 - 1))) {
                        randomAccessFile.close();
                        return false;
                    }
                }
                randomAccessFile.seek(((long) (((readInt3 - 1) * 1024) + 12)) + 0);
                if (str.length() > 750) {
                    randomAccessFile.close();
                    return false;
                }
                String encode = Jni.encode(str);
                int length = encode.length();
                if (length > m_AppUI.MSG_GET_GL_OK) {
                    randomAccessFile.close();
                    return false;
                }
                randomAccessFile.writeInt(length);
                randomAccessFile.writeBytes(encode);
                if (readInt == 0) {
                    randomAccessFile.seek(0);
                    randomAccessFile.writeInt(1);
                    randomAccessFile.writeInt(1);
                    randomAccessFile.writeInt(2);
                } else if (readInt < C0468j.ab - 1) {
                    randomAccessFile.seek(0);
                    randomAccessFile.writeInt(readInt + 1);
                    randomAccessFile.seek(8);
                    randomAccessFile.writeInt(readInt + 2);
                } else if (readInt == C0468j.ab - 1) {
                    randomAccessFile.seek(0);
                    randomAccessFile.writeInt(C0468j.ab);
                    if (readInt2 == 0 || readInt2 == 1) {
                        randomAccessFile.writeInt(2);
                    }
                    randomAccessFile.seek(8);
                    randomAccessFile.writeInt(1);
                } else if (readInt3 == readInt2) {
                    readInt = readInt3 == C0468j.ab ? 1 : readInt3 + 1;
                    r2 = readInt == C0468j.ab ? 1 : readInt + 1;
                    randomAccessFile.seek(4);
                    randomAccessFile.writeInt(r2);
                    randomAccessFile.writeInt(readInt);
                } else {
                    readInt = readInt3 == C0468j.ab ? 1 : readInt3 + 1;
                    if (readInt == readInt2) {
                        r2 = readInt == C0468j.ab ? 1 : readInt + 1;
                        randomAccessFile.seek(4);
                        randomAccessFile.writeInt(r2);
                    }
                    randomAccessFile.seek(8);
                    randomAccessFile.writeInt(readInt);
                }
                randomAccessFile.close();
                return true;
            }
            randomAccessFile.close();
            C0343f.m258d();
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static String m256b() {
        if (f207c == null) {
            return null;
        }
        if (!f207c.exists()) {
            return null;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(f207c, "rw");
            randomAccessFile.seek(0);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            int readInt3 = randomAccessFile.readInt();
            if (!C0343f.m253a(readInt, readInt2, readInt3)) {
                randomAccessFile.close();
                C0343f.m258d();
                return null;
            } else if (readInt2 == 0 || readInt2 == readInt3) {
                randomAccessFile.close();
                return null;
            } else {
                long j = 0 + ((long) (((readInt2 - 1) * 1024) + 12));
                randomAccessFile.seek(j);
                int readInt4 = randomAccessFile.readInt();
                byte[] bArr = new byte[readInt4];
                randomAccessFile.seek(j + 4);
                for (readInt3 = 0; readInt3 < readInt4; readInt3++) {
                    bArr[readInt3] = randomAccessFile.readByte();
                }
                String str = new String(bArr);
                readInt3 = readInt < C0468j.ab ? readInt2 + 1 : readInt2 == C0468j.ab ? 1 : readInt2 + 1;
                randomAccessFile.seek(4);
                randomAccessFile.writeInt(readInt3);
                randomAccessFile.close();
                return str;
            }
        } catch (IOException e) {
            return null;
        }
    }

    private static void m257c() {
        f209e = true;
        f208d = null;
        f210f = 0;
        f211g = 0;
        f212h = 0;
        f213i = 0;
        f214j = 0;
        f215k = 0.0d;
        f216l = 0.0d;
        f217m = 0;
        f218n = 0;
        f219o = 0;
    }

    private static boolean m258d() {
        if (f207c.exists()) {
            f207c.delete();
        }
        if (!f207c.getParentFile().exists()) {
            f207c.getParentFile().mkdirs();
        }
        try {
            f207c.createNewFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile(f207c, "rw");
            randomAccessFile.seek(0);
            randomAccessFile.writeInt(0);
            randomAccessFile.writeInt(0);
            randomAccessFile.writeInt(1);
            randomAccessFile.close();
            C0343f.m257c();
            return f207c.exists();
        } catch (IOException e) {
            return false;
        }
    }

    public boolean m259a(Location location) {
        return m254a(location, C0468j.f898Z, C0468j.aa);
    }
}
