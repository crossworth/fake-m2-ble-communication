package com.baidu.location;

import android.location.Location;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;

public class C1983r implements an, C1619j {
    private static File d0 = new File(C1976f.L, ea);
    private static final int d1 = 3600;
    private static int d2 = 0;
    private static long d3 = 0;
    private static long d4 = 0;
    private static boolean d5 = true;
    private static final int d6 = 1024;
    private static int d7 = 0;
    private static double d8 = 0.0d;
    private static double d9 = 0.0d;
    private static final int dX = 12;
    private static StringBuffer dY = null;
    private static final int dZ = 5;
    private static String ea = "Temp_in.dat";
    private static int eb = 0;
    private static int ec = 0;
    private static final int ed = 5;
    private static final int ee = 750;
    private static final int ef = 1000;
    private static final int eh = 100;
    private static int ej = 0;
    private static C1983r ek = null;
    private static long el = 0;
    private String eg = null;
    private boolean ei = true;

    private C1983r(String str) {
        if (str == null) {
            str = "";
        } else if (str.length() > 100) {
            str = str.substring(0, 100);
        }
        this.eg = str;
    }

    private static boolean ao() {
        if (d0.exists()) {
            d0.delete();
        }
        if (!d0.getParentFile().exists()) {
            d0.getParentFile().mkdirs();
        }
        try {
            d0.createNewFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile(d0, "rw");
            randomAccessFile.seek(0);
            randomAccessFile.writeInt(0);
            randomAccessFile.writeInt(0);
            randomAccessFile.writeInt(1);
            randomAccessFile.close();
            C1983r.aq();
            return d0.exists();
        } catch (IOException e) {
            return false;
        }
    }

    private void ap() {
        if (dY != null && dY.length() >= 100) {
            m6035d(dY.toString());
        }
        C1983r.aq();
    }

    private static void aq() {
        d5 = true;
        dY = null;
        eb = 0;
        ej = 0;
        d4 = 0;
        d3 = 0;
        el = 0;
        d8 = 0.0d;
        d9 = 0.0d;
        d2 = 0;
        d7 = 0;
        ec = 0;
    }

    public static C1983r ar() {
        if (ek == null) {
            ek = new C1983r(ap.bD().bE());
        }
        return ek;
    }

    private void as() {
    }

    public static String at() {
        if (d0 == null) {
            return null;
        }
        if (!d0.exists()) {
            return null;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(d0, "rw");
            randomAccessFile.seek(0);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            int readInt3 = randomAccessFile.readInt();
            if (!C1983r.m6037if(readInt, readInt2, readInt3)) {
                randomAccessFile.close();
                C1983r.ao();
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
                readInt3 = readInt < C1974b.at ? readInt2 + 1 : readInt2 == C1974b.at ? 1 : readInt2 + 1;
                randomAccessFile.seek(4);
                randomAccessFile.writeInt(readInt3);
                randomAccessFile.close();
                return str;
            }
        } catch (IOException e) {
            return null;
        }
    }

    private boolean au() {
        if (d0.exists()) {
            d0.delete();
        }
        C1983r.aq();
        return !d0.exists();
    }

    private boolean m6035d(String str) {
        if (str == null || !str.startsWith("&nr")) {
            return false;
        }
        if (!d0.exists() && !C1983r.ao()) {
            return false;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(d0, "rw");
            randomAccessFile.seek(0);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            int readInt3 = randomAccessFile.readInt();
            if (C1983r.m6037if(readInt, readInt2, readInt3)) {
                if (C1974b.aR) {
                    if (readInt == C1974b.at) {
                        if (str.equals(m6036do(readInt3 == 1 ? C1974b.at : readInt3 - 1))) {
                            randomAccessFile.close();
                            return false;
                        }
                    } else if (readInt3 > 1 && str.equals(m6036do(readInt3 - 1))) {
                        randomAccessFile.close();
                        return false;
                    }
                }
                randomAccessFile.seek(((long) (((readInt3 - 1) * 1024) + 12)) + 0);
                if (str.length() > ee) {
                    randomAccessFile.close();
                    return false;
                }
                String f = Jni.m5811f(str);
                int length = f.length();
                if (length > 1020) {
                    randomAccessFile.close();
                    return false;
                }
                randomAccessFile.writeInt(length);
                randomAccessFile.writeBytes(f);
                if (readInt == 0) {
                    randomAccessFile.seek(0);
                    randomAccessFile.writeInt(1);
                    randomAccessFile.writeInt(1);
                    randomAccessFile.writeInt(2);
                } else if (readInt < C1974b.at - 1) {
                    randomAccessFile.seek(0);
                    randomAccessFile.writeInt(readInt + 1);
                    randomAccessFile.seek(8);
                    randomAccessFile.writeInt(readInt + 2);
                } else if (readInt == C1974b.at - 1) {
                    randomAccessFile.seek(0);
                    randomAccessFile.writeInt(C1974b.at);
                    if (readInt2 == 0 || readInt2 == 1) {
                        randomAccessFile.writeInt(2);
                    }
                    randomAccessFile.seek(8);
                    randomAccessFile.writeInt(1);
                } else if (readInt3 == readInt2) {
                    readInt = readInt3 == C1974b.at ? 1 : readInt3 + 1;
                    r2 = readInt == C1974b.at ? 1 : readInt + 1;
                    randomAccessFile.seek(4);
                    randomAccessFile.writeInt(r2);
                    randomAccessFile.writeInt(readInt);
                } else {
                    readInt = readInt3 == C1974b.at ? 1 : readInt3 + 1;
                    if (readInt == readInt2) {
                        r2 = readInt == C1974b.at ? 1 : readInt + 1;
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
            C1983r.ao();
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private String m6036do(int i) {
        if (!d0.exists()) {
            return null;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(d0, "rw");
            randomAccessFile.seek(0);
            int readInt = randomAccessFile.readInt();
            if (!C1983r.m6037if(readInt, randomAccessFile.readInt(), randomAccessFile.readInt())) {
                randomAccessFile.close();
                C1983r.ao();
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

    private static boolean m6037if(int i, int i2, int i3) {
        return (i < 0 || i > C1974b.at) ? false : (i2 < 0 || i2 > i + 1) ? false : i3 >= 1 && i3 <= i + 1 && i3 <= C1974b.at;
    }

    private boolean m6038if(Location location, int i, int i2) {
        if (location == null || !C1974b.au || !this.ei || !C1985t.e9) {
            return false;
        }
        if (C1974b.aq < 5) {
            C1974b.aq = 5;
        } else if (C1974b.aq > 1000) {
            C1974b.aq = 1000;
        }
        if (C1974b.an < 5) {
            C1974b.an = 5;
        } else if (C1974b.an > d1) {
            C1974b.an = d1;
        }
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        long time = location.getTime() / 1000;
        if (d5) {
            eb = 1;
            dY = new StringBuffer("");
            dY.append(String.format(Locale.CHINA, "&nr=%s&traj=%d,%.5f,%.5f|", new Object[]{this.eg, Long.valueOf(time), Double.valueOf(longitude), Double.valueOf(latitude)}));
            ej = dY.length();
            d4 = time;
            d8 = longitude;
            d9 = latitude;
            d3 = (long) Math.floor((longitude * 100000.0d) + 0.5d);
            el = (long) Math.floor((latitude * 100000.0d) + 0.5d);
            d5 = false;
            return true;
        }
        float[] fArr = new float[1];
        Location.distanceBetween(latitude, longitude, d9, d8, fArr);
        long j = time - d4;
        if (fArr[0] < ((float) C1974b.aq) && j < ((long) C1974b.an)) {
            return false;
        }
        if (dY == null) {
            eb++;
            ej = 0;
            dY = new StringBuffer("");
            dY.append(String.format(Locale.CHINA, "&nr=%s&traj=%d,%.5f,%.5f|", new Object[]{this.eg, Long.valueOf(time), Double.valueOf(longitude), Double.valueOf(latitude)}));
            ej = dY.length();
            d4 = time;
            d8 = longitude;
            d9 = latitude;
            d3 = (long) Math.floor((longitude * 100000.0d) + 0.5d);
            el = (long) Math.floor((latitude * 100000.0d) + 0.5d);
        } else {
            d8 = longitude;
            d9 = latitude;
            long floor = (long) Math.floor((longitude * 100000.0d) + 0.5d);
            long floor2 = (long) Math.floor((latitude * 100000.0d) + 0.5d);
            d2 = (int) (time - d4);
            d7 = (int) (floor - d3);
            ec = (int) (floor2 - el);
            dY.append(String.format(Locale.CHINA, "%d,%d,%d|", new Object[]{Integer.valueOf(d2), Integer.valueOf(d7), Integer.valueOf(ec)}));
            ej = dY.length();
            d4 = time;
            d3 = floor;
            el = floor2;
        }
        if (ej + 15 > ee) {
            m6035d(dY.toString());
            dY = null;
        }
        if (eb >= C1974b.at) {
            this.ei = false;
        }
        return true;
    }

    public void av() {
        ap();
    }

    public boolean m6039do(Location location) {
        return m6038if(location, C1974b.aq, C1974b.an);
    }
}
