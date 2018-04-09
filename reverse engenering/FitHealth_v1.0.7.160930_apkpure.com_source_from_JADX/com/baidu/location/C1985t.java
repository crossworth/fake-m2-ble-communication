package com.baidu.location;

import android.support.v4.media.session.PlaybackStateCompat;
import com.tencent.stat.DeviceInfo;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

class C1985t implements an, C1619j {
    public static boolean e0 = true;
    public static boolean e1 = true;
    private static final String e2 = (C1976f.L + "/con.dat");
    public static int e4 = -1;
    public static boolean e5 = true;
    public static boolean e6 = true;
    private static final int e8 = 128;
    public static boolean e9 = true;
    private static C1985t fa = null;
    public static boolean fb = false;
    public static int fc = -1;
    public static int fd = 0;
    private C2063a e3;
    private boolean e7;

    class C2063a extends C1982o {
        final /* synthetic */ C1985t cV;
        boolean cW;
        String cX;
        boolean cY;

        public C2063a(C1985t c1985t) {
            this.cV = c1985t;
            this.cX = null;
            this.cW = false;
            this.cY = false;
            this.cP = new ArrayList();
        }

        void mo3704V() {
            this.cL = C1974b.m5924int();
            this.cO = 2;
            String f = Jni.m5811f(this.cX);
            this.cX = null;
            if (this.cW) {
                this.cP.add(new BasicNameValuePair("qt", "grid"));
            } else {
                this.cP.add(new BasicNameValuePair("qt", "conf"));
            }
            this.cP.add(new BasicNameValuePair("req", f));
        }

        public void m6269if(String str, boolean z) {
            if (!this.cY) {
                this.cY = true;
                this.cX = str;
                this.cW = z;
                m6032R();
            }
        }

        void mo3705if(boolean z) {
            if (z && this.cM != null) {
                if (this.cW) {
                    this.cV.m6070if(this.cM);
                } else {
                    this.cV.m6068do(this.cM);
                }
            }
            if (this.cP != null) {
                this.cP.clear();
            }
            this.cY = false;
        }
    }

    private C1985t() {
        this.e3 = null;
        this.e7 = true;
        this.e3 = new C2063a(this);
        fb = true;
    }

    public static void aI() {
        String str = C1976f.L + "/config.dat";
        int i = C1974b.au ? 1 : 0;
        int i2 = C1974b.aR ? 1 : 0;
        byte[] bytes = String.format(Locale.CHINA, "{\"ver\":\"%d\",\"gps\":\"%.1f|%.1f|%.1f|%.1f|%d|%d|%d|%d|%d|%d|%d\",\"up\":\"%.1f|%.1f|%.1f|%.1f\",\"wf\":\"%d|%.1f|%d|%.1f\",\"ab\":\"%.2f|%.2f|%d|%d\",\"gpc\":\"%d|%d|%d|%d|%d|%d\",\"zxd\":\"%.1f|%.1f|%d|%d|%d\",\"shak\":\"%d|%d|%.1f\"}", new Object[]{Integer.valueOf(C1974b.af), Float.valueOf(C1974b.ab), Float.valueOf(C1974b.aK), Float.valueOf(C1974b.ad), Float.valueOf(C1974b.aM), Integer.valueOf(C1974b.aC), Integer.valueOf(C1974b.f5424U), Integer.valueOf(C1974b.aD), Integer.valueOf(C1974b.f5425V), Integer.valueOf(C1974b.f5428Y), Integer.valueOf(C1974b.as), Integer.valueOf(C1974b.aW), Float.valueOf(C1974b.a5), Float.valueOf(C1974b.a2), Float.valueOf(C1974b.ak), Float.valueOf(C1974b.aS), Integer.valueOf(C1974b.aQ), Float.valueOf(C1974b.f5429Z), Integer.valueOf(C1974b.ao), Float.valueOf(C1974b.aN), Float.valueOf(C1974b.a4), Float.valueOf(C1974b.a1), Integer.valueOf(C1974b.aZ), Integer.valueOf(C1974b.aX), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(C1974b.aq), Integer.valueOf(C1974b.at), Long.valueOf(C1974b.aL), Integer.valueOf(C1974b.aO), Float.valueOf(C1974b.ac), Float.valueOf(C1974b.f5427X), Integer.valueOf(C1974b.al), Integer.valueOf(C1974b.az), Integer.valueOf(C1974b.ap), Integer.valueOf(C1974b.aP), Integer.valueOf(C1974b.aH), Float.valueOf(C1974b.aU)}).getBytes();
        try {
            RandomAccessFile randomAccessFile;
            File file = new File(str);
            if (!file.exists()) {
                File file2 = new File(C1976f.L);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (file.createNewFile()) {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(0);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.close();
                } else {
                    return;
                }
            }
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(0);
            randomAccessFile.writeBoolean(true);
            randomAccessFile.seek(2);
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes);
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    public static void aJ() {
        try {
            File file = new File(e2);
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
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(128);
                randomAccessFile.writeInt(0);
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    public static C1985t aK() {
        if (fa == null) {
            fa = new C1985t();
        }
        return fa;
    }

    public static void aN() {
        try {
            RandomAccessFile randomAccessFile;
            File file = new File(C1976f.L + "/config.dat");
            if (!file.exists()) {
                File file2 = new File(C1976f.L);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (file.createNewFile()) {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(0);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.close();
                } else {
                    return;
                }
            }
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(1);
            randomAccessFile.writeBoolean(true);
            randomAccessFile.seek(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            randomAccessFile.writeDouble(C1974b.aB);
            randomAccessFile.writeDouble(C1974b.aa);
            randomAccessFile.writeBoolean(C1974b.am);
            if (C1974b.am && C1974b.aw != null) {
                randomAccessFile.write(C1974b.aw);
            }
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    public static void aO() {
        int i = 0;
        try {
            File file = new File(e2);
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(4);
                int readInt = randomAccessFile.readInt();
                int readInt2 = randomAccessFile.readInt();
                randomAccessFile.seek(128);
                byte[] bArr = new byte[readInt];
                while (i < readInt2) {
                    randomAccessFile.seek((long) ((readInt * i) + 128));
                    int readInt3 = randomAccessFile.readInt();
                    if (readInt3 > 0 && readInt3 < readInt) {
                        randomAccessFile.read(bArr, 0, readInt3);
                        if (bArr[readInt3 - 1] == (byte) 0 && new String(bArr, 0, readInt3 - 1).equals(ap.hb)) {
                            fc = randomAccessFile.readInt();
                            fd = i;
                            break;
                        }
                    }
                    i++;
                }
                if (i == readInt2) {
                    fd = readInt2;
                }
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    private void m6068do(HttpEntity httpEntity) {
        String str = null;
        try {
            str = EntityUtils.toString(httpEntity, "utf-8");
            if (m6074i(str)) {
                C1985t.aI();
            }
        } catch (Exception e) {
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("ctr")) {
                e4 = Integer.parseInt(jSONObject.getString("ctr"));
            }
        } catch (Exception e2) {
        }
        try {
            int i;
            C1985t.aO();
            if (e4 != -1) {
                i = e4;
                C1985t.m6071int(e4);
            } else {
                i = fc != -1 ? fc : -1;
            }
            if (i != -1) {
                C1985t.m6072new(i);
            }
            C1980m.m6000D().m6005E().obtainMessage(92).sendToTarget();
        } catch (Exception e3) {
        }
    }

    private void m6070if(HttpEntity httpEntity) {
        int i = 0;
        try {
            byte[] toByteArray = EntityUtils.toByteArray(httpEntity);
            if (toByteArray != null) {
                if (toByteArray.length < 640) {
                    C1974b.am = false;
                    C1974b.aa = C1974b.aj + 0.025d;
                    C1974b.aB = C1974b.a0 - 0.025d;
                    i = 1;
                } else {
                    C1974b.am = true;
                    C1974b.aB = Double.longBitsToDouble(Long.valueOf(((((((((((long) toByteArray[7]) & 255) << 56) | ((((long) toByteArray[6]) & 255) << 48)) | ((((long) toByteArray[5]) & 255) << 40)) | ((((long) toByteArray[4]) & 255) << 32)) | ((((long) toByteArray[3]) & 255) << 24)) | ((((long) toByteArray[2]) & 255) << 16)) | ((((long) toByteArray[1]) & 255) << 8)) | (((long) toByteArray[0]) & 255)).longValue());
                    C1974b.aa = Double.longBitsToDouble(Long.valueOf(((((((((((long) toByteArray[15]) & 255) << 56) | ((((long) toByteArray[14]) & 255) << 48)) | ((((long) toByteArray[13]) & 255) << 40)) | ((((long) toByteArray[12]) & 255) << 32)) | ((((long) toByteArray[11]) & 255) << 24)) | ((((long) toByteArray[10]) & 255) << 16)) | ((((long) toByteArray[9]) & 255) << 8)) | (((long) toByteArray[8]) & 255)).longValue());
                    C1974b.aw = new byte[625];
                    while (i < 625) {
                        C1974b.aw[i] = toByteArray[i + 16];
                        i++;
                    }
                    i = 1;
                }
            }
            if (i != 0) {
                C1985t.aN();
            }
        } catch (Exception e) {
        }
    }

    public static void m6071int(int i) {
        File file = new File(e2);
        if (!file.exists()) {
            C1985t.aJ();
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(4);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            randomAccessFile.seek((long) ((readInt * fd) + 128));
            byte[] bytes = (ap.hb + '\u0000').getBytes();
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes, 0, bytes.length);
            randomAccessFile.writeInt(i);
            if (readInt2 == fd) {
                randomAccessFile.seek(8);
                randomAccessFile.writeInt(readInt2 + 1);
            }
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    public static void m6072new(int i) {
        boolean z = true;
        e6 = (i & 1) == 1;
        e9 = (i & 2) == 2;
        fb = (i & 4) == 4;
        e0 = (i & 8) == 8;
        e1 = (i & 65536) == 65536;
        if ((i & 131072) != 131072) {
            z = false;
        }
        e5 = z;
    }

    public void aL() {
        try {
            File file = new File(C1976f.L + "/config.dat");
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                if (randomAccessFile.readBoolean()) {
                    randomAccessFile.seek(2);
                    int readInt = randomAccessFile.readInt();
                    byte[] bArr = new byte[readInt];
                    randomAccessFile.read(bArr, 0, readInt);
                    m6074i(new String(bArr));
                }
                randomAccessFile.seek(1);
                if (randomAccessFile.readBoolean()) {
                    randomAccessFile.seek(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
                    C1974b.aB = randomAccessFile.readDouble();
                    C1974b.aa = randomAccessFile.readDouble();
                    C1974b.am = randomAccessFile.readBoolean();
                    if (C1974b.am) {
                        C1974b.aw = new byte[625];
                        randomAccessFile.read(C1974b.aw, 0, 625);
                    }
                }
                randomAccessFile.close();
            }
            this.e3.m6269if("&ver=" + C1974b.af + "&usr=" + ap.bD().bC() + "&app=" + ap.hb + "&prod=" + ap.g7, false);
        } catch (Exception e) {
        }
    }

    public void aM() {
        if (this.e7) {
            aL();
            this.e7 = false;
        }
    }

    public void m6073h(String str) {
        this.e3.m6269if(str, true);
    }

    public boolean m6074i(String str) {
        boolean z = true;
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int parseInt = Integer.parseInt(jSONObject.getString(DeviceInfo.TAG_VERSION));
                if (parseInt > C1974b.af) {
                    String[] split;
                    C1974b.af = parseInt;
                    if (jSONObject.has("gps")) {
                        split = jSONObject.getString("gps").split("\\|");
                        if (split.length > 10) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                C1974b.ab = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                C1974b.aK = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C1974b.ad = Float.parseFloat(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C1974b.aM = Float.parseFloat(split[3]);
                            }
                            if (!(split[4] == null || split[4].equals(""))) {
                                C1974b.aC = Integer.parseInt(split[4]);
                            }
                            if (!(split[5] == null || split[5].equals(""))) {
                                C1974b.f5424U = Integer.parseInt(split[5]);
                            }
                            if (!(split[6] == null || split[6].equals(""))) {
                                C1974b.aD = Integer.parseInt(split[6]);
                            }
                            if (!(split[7] == null || split[7].equals(""))) {
                                C1974b.f5425V = Integer.parseInt(split[7]);
                            }
                            if (!(split[8] == null || split[8].equals(""))) {
                                C1974b.f5428Y = Integer.parseInt(split[8]);
                            }
                            if (!(split[9] == null || split[9].equals(""))) {
                                C1974b.as = Integer.parseInt(split[9]);
                            }
                            if (!(split[10] == null || split[10].equals(""))) {
                                C1974b.aW = Integer.parseInt(split[10]);
                            }
                        }
                    }
                    if (jSONObject.has("up")) {
                        split = jSONObject.getString("up").split("\\|");
                        if (split.length > 3) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                C1974b.a5 = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                C1974b.a2 = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C1974b.ak = Float.parseFloat(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C1974b.aS = Float.parseFloat(split[3]);
                            }
                        }
                    }
                    if (jSONObject.has("wf")) {
                        split = jSONObject.getString("wf").split("\\|");
                        if (split.length > 3) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                C1974b.aQ = Integer.parseInt(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                C1974b.f5429Z = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C1974b.ao = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C1974b.aN = Float.parseFloat(split[3]);
                            }
                        }
                    }
                    if (jSONObject.has("ab")) {
                        split = jSONObject.getString("ab").split("\\|");
                        if (split.length > 3) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                C1974b.a4 = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                C1974b.a1 = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C1974b.aZ = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C1974b.aX = Integer.parseInt(split[3]);
                            }
                        }
                    }
                    if (jSONObject.has("zxd")) {
                        split = jSONObject.getString("zxd").split("\\|");
                        if (split.length > 4) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                C1974b.ac = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                C1974b.f5427X = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C1974b.al = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C1974b.az = Integer.parseInt(split[3]);
                            }
                            if (!(split[4] == null || split[4].equals(""))) {
                                C1974b.ap = Integer.parseInt(split[4]);
                            }
                        }
                    }
                    if (jSONObject.has("gpc")) {
                        split = jSONObject.getString("gpc").split("\\|");
                        if (split.length > 5) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                if (Integer.parseInt(split[0]) > 0) {
                                    C1974b.au = true;
                                } else {
                                    C1974b.au = false;
                                }
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                if (Integer.parseInt(split[1]) > 0) {
                                    C1974b.aR = true;
                                } else {
                                    C1974b.aR = false;
                                }
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C1974b.aq = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C1974b.at = Integer.parseInt(split[3]);
                            }
                            if (!(split[4] == null || split[4].equals(""))) {
                                int parseInt2 = Integer.parseInt(split[4]);
                                if (parseInt2 > 0) {
                                    C1974b.aL = (long) parseInt2;
                                    C1974b.aJ = (C1974b.aL * 1000) * 60;
                                    C1974b.ay = C1974b.aJ >> 2;
                                } else {
                                    C1974b.aY = false;
                                }
                            }
                            if (!(split[5] == null || split[5].equals(""))) {
                                C1974b.aO = Integer.parseInt(split[5]);
                            }
                        }
                    }
                    if (jSONObject.has("shak")) {
                        String[] split2 = jSONObject.getString("shak").split("\\|");
                        if (split2.length > 2) {
                            if (!(split2[0] == null || split2[0].equals(""))) {
                                C1974b.aP = Integer.parseInt(split2[0]);
                            }
                            if (!(split2[1] == null || split2[1].equals(""))) {
                                C1974b.aH = Integer.parseInt(split2[1]);
                            }
                            if (!(split2[2] == null || split2[2].equals(""))) {
                                C1974b.aU = Float.parseFloat(split2[2]);
                            }
                        }
                    }
                    return z;
                }
            } catch (Exception e) {
                return false;
            }
        }
        z = false;
        return z;
    }
}
