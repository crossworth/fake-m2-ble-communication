package com.baidu.location.p007b;

import android.content.SharedPreferences.Editor;
import android.support.v4.media.session.PlaybackStateCompat;
import com.baidu.location.C0455f;
import com.baidu.location.Jni;
import com.baidu.location.p006h.C0335e;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0460c;
import com.baidu.location.p006h.C0467i;
import com.baidu.location.p006h.C0468j;
import com.tencent.stat.DeviceInfo;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONObject;

public class C0372e {
    private static C0372e f364i = null;
    private static final String f365k = (C0467i.f869a + "/conlts.dat");
    private static int f366l = -1;
    private static int f367m = -1;
    private static int f368n = 0;
    public boolean f369a = true;
    public boolean f370b = true;
    public boolean f371c = false;
    public boolean f372d = true;
    public boolean f373e = true;
    public boolean f374f = true;
    public boolean f375g = true;
    public boolean f376h = false;
    private C0371a f377j = null;

    class C0371a extends C0335e {
        String f360a;
        boolean f361b;
        boolean f362c;
        final /* synthetic */ C0372e f363d;

        public C0371a(C0372e c0372e) {
            this.f363d = c0372e;
            this.f360a = null;
            this.f361b = false;
            this.f362c = false;
            this.k = new HashMap();
        }

        public void mo1741a() {
            this.h = C0468j.m1023c();
            this.i = 2;
            String encode = Jni.encode(this.f360a);
            this.f360a = null;
            if (this.f361b) {
                this.k.put("qt", "grid");
            } else {
                this.k.put("qt", "conf");
            }
            this.k.put("req", encode);
        }

        public void m402a(String str, boolean z) {
            if (!this.f362c) {
                this.f362c = true;
                this.f360a = str;
                this.f361b = z;
                if (z) {
                    m202b(true);
                } else {
                    m204e();
                }
            }
        }

        public void mo1742a(boolean z) {
            if (!z || this.j == null) {
                this.f363d.m412c(null);
            } else if (this.f361b) {
                this.f363d.m409a(this.m);
            } else {
                this.f363d.m412c(this.j);
            }
            if (this.k != null) {
                this.k.clear();
            }
            this.f362c = false;
        }
    }

    private C0372e() {
    }

    public static C0372e m404a() {
        if (f364i == null) {
            f364i = new C0372e();
        }
        return f364i;
    }

    private void m405a(int i) {
        boolean z = true;
        this.f369a = (i & 1) == 1;
        this.f370b = (i & 2) == 2;
        this.f371c = (i & 4) == 4;
        this.f372d = (i & 8) == 8;
        this.f374f = (i & 65536) == 65536;
        if ((i & 131072) != 131072) {
            z = false;
        }
        this.f375g = z;
        if ((i & 16) == 16) {
            this.f373e = false;
        }
    }

    private void m408a(JSONObject jSONObject) {
        boolean z = false;
        if (jSONObject != null) {
            int i = 14400000;
            int i2 = 10;
            try {
                if (!(jSONObject.has("ipen") && jSONObject.getInt("ipen") == 0)) {
                    z = true;
                }
                if (jSONObject.has("ipvt")) {
                    i = jSONObject.getInt("ipvt");
                }
                if (jSONObject.has("ipvn")) {
                    i2 = jSONObject.getInt("ipvn");
                }
                Editor edit = C0455f.getServiceContext().getSharedPreferences("MapCoreServicePre", 0).edit();
                edit.putBoolean("ipLocInfoUpload", z);
                edit.putInt("ipValidTime", i);
                edit.putInt("ipLocInfoUploadTimesPerDay", i2);
                edit.commit();
            } catch (Exception e) {
            }
        }
    }

    private void m409a(byte[] bArr) {
        int i = 0;
        if (bArr != null) {
            if (bArr.length < 640) {
                C0468j.f919u = false;
                C0468j.f916r = C0468j.f914p + 0.025d;
                C0468j.f915q = C0468j.f913o - 0.025d;
                i = 1;
            } else {
                C0468j.f919u = true;
                C0468j.f915q = Double.longBitsToDouble(((((((((((long) bArr[7]) & 255) << 56) | ((((long) bArr[6]) & 255) << 48)) | ((((long) bArr[5]) & 255) << 40)) | ((((long) bArr[4]) & 255) << 32)) | ((((long) bArr[3]) & 255) << 24)) | ((((long) bArr[2]) & 255) << 16)) | ((((long) bArr[1]) & 255) << 8)) | (((long) bArr[0]) & 255));
                C0468j.f916r = Double.longBitsToDouble(((((((((((long) bArr[15]) & 255) << 56) | ((((long) bArr[14]) & 255) << 48)) | ((((long) bArr[13]) & 255) << 40)) | ((((long) bArr[12]) & 255) << 32)) | ((((long) bArr[11]) & 255) << 24)) | ((((long) bArr[10]) & 255) << 16)) | ((((long) bArr[9]) & 255) << 8)) | (((long) bArr[8]) & 255));
                C0468j.f918t = new byte[625];
                while (i < 625) {
                    C0468j.f918t[i] = bArr[i + 16];
                    i++;
                }
                i = 1;
            }
        }
        if (i != 0) {
            try {
                m415g();
            } catch (Exception e) {
            }
        }
    }

    private void m410b(int i) {
        File file = new File(f365k);
        if (!file.exists()) {
            m417i();
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(4);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            randomAccessFile.seek((long) ((readInt * f368n) + 128));
            byte[] bytes = (C0459b.f835d + '\u0000').getBytes();
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes, 0, bytes.length);
            randomAccessFile.writeInt(i);
            if (readInt2 == f368n) {
                randomAccessFile.seek(8);
                randomAccessFile.writeInt(readInt2 + 1);
            }
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    private boolean m411b(String str) {
        boolean z = true;
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("ipconf")) {
                    try {
                        m408a(jSONObject.getJSONObject("ipconf"));
                    } catch (Exception e) {
                    }
                }
                int parseInt = Integer.parseInt(jSONObject.getString(DeviceInfo.TAG_VERSION));
                if (parseInt > C0468j.f920v) {
                    String[] split;
                    C0468j.f920v = parseInt;
                    if (jSONObject.has("gps")) {
                        split = jSONObject.getString("gps").split("\\|");
                        if (split.length > 10) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                C0468j.f921w = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                C0468j.f922x = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C0468j.f923y = Float.parseFloat(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C0468j.f924z = Float.parseFloat(split[3]);
                            }
                            if (!(split[4] == null || split[4].equals(""))) {
                                C0468j.f873A = Integer.parseInt(split[4]);
                            }
                            if (!(split[5] == null || split[5].equals(""))) {
                                C0468j.f874B = Integer.parseInt(split[5]);
                            }
                            if (!(split[6] == null || split[6].equals(""))) {
                                C0468j.f875C = Integer.parseInt(split[6]);
                            }
                            if (!(split[7] == null || split[7].equals(""))) {
                                C0468j.f876D = Integer.parseInt(split[7]);
                            }
                            if (!(split[8] == null || split[8].equals(""))) {
                                C0468j.f877E = Integer.parseInt(split[8]);
                            }
                            if (!(split[9] == null || split[9].equals(""))) {
                                C0468j.f878F = Integer.parseInt(split[9]);
                            }
                            if (!(split[10] == null || split[10].equals(""))) {
                                C0468j.f879G = Integer.parseInt(split[10]);
                            }
                        }
                    }
                    if (jSONObject.has("up")) {
                        split = jSONObject.getString("up").split("\\|");
                        if (split.length > 3) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                C0468j.f880H = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                C0468j.f881I = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C0468j.f882J = Float.parseFloat(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C0468j.f883K = Float.parseFloat(split[3]);
                            }
                        }
                    }
                    if (jSONObject.has("wf")) {
                        split = jSONObject.getString("wf").split("\\|");
                        if (split.length > 3) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                C0468j.f884L = Integer.parseInt(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                C0468j.f885M = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C0468j.f886N = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C0468j.f887O = Float.parseFloat(split[3]);
                            }
                        }
                    }
                    if (jSONObject.has("ab")) {
                        split = jSONObject.getString("ab").split("\\|");
                        if (split.length > 3) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                C0468j.f888P = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                C0468j.f889Q = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C0468j.f890R = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C0468j.f891S = Integer.parseInt(split[3]);
                            }
                        }
                    }
                    if (jSONObject.has("zxd")) {
                        split = jSONObject.getString("zxd").split("\\|");
                        if (split.length > 4) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                C0468j.an = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                C0468j.ao = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C0468j.ap = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C0468j.aq = Integer.parseInt(split[3]);
                            }
                            if (!(split[4] == null || split[4].equals(""))) {
                                C0468j.ar = Integer.parseInt(split[4]);
                            }
                        }
                    }
                    if (jSONObject.has("gpc")) {
                        split = jSONObject.getString("gpc").split("\\|");
                        if (split.length > 5) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                if (Integer.parseInt(split[0]) > 0) {
                                    C0468j.f896X = true;
                                } else {
                                    C0468j.f896X = false;
                                }
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                if (Integer.parseInt(split[1]) > 0) {
                                    C0468j.f897Y = true;
                                } else {
                                    C0468j.f897Y = false;
                                }
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C0468j.f898Z = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                C0468j.ab = Integer.parseInt(split[3]);
                            }
                            if (!(split[4] == null || split[4].equals(""))) {
                                int parseInt2 = Integer.parseInt(split[4]);
                                if (parseInt2 > 0) {
                                    C0468j.ag = (long) parseInt2;
                                    C0468j.ac = (C0468j.ag * 1000) * 60;
                                    C0468j.ah = C0468j.ac >> 2;
                                } else {
                                    C0468j.f911m = false;
                                }
                            }
                            if (!(split[5] == null || split[5].equals(""))) {
                                C0468j.aj = Integer.parseInt(split[5]);
                            }
                        }
                    }
                    if (jSONObject.has("shak")) {
                        split = jSONObject.getString("shak").split("\\|");
                        if (split.length > 2) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                C0468j.ak = Integer.parseInt(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                C0468j.al = Integer.parseInt(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                C0468j.am = Float.parseFloat(split[2]);
                            }
                        }
                    }
                    if (jSONObject.has("dmx")) {
                        C0468j.ai = jSONObject.getInt("dmx");
                    }
                    return z;
                }
            } catch (Exception e2) {
                return false;
            }
        }
        z = false;
        return z;
    }

    private void m412c(String str) {
        f367m = -1;
        if (str != null) {
            try {
                if (m411b(str)) {
                    m414f();
                }
            } catch (Exception e) {
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("ctr")) {
                    f367m = Integer.parseInt(jSONObject.getString("ctr"));
                }
            } catch (Exception e2) {
            }
            try {
                int i;
                m418j();
                if (f367m != -1) {
                    i = f367m;
                    m410b(f367m);
                } else {
                    i = f366l != -1 ? f366l : -1;
                }
                if (i != -1) {
                    m405a(i);
                }
                if (!C0455f.isServing) {
                }
            } catch (Exception e3) {
            }
        }
    }

    private void m413e() {
        String str = "&ver=" + C0468j.f920v + "&usr=" + C0459b.m980a().m985b() + "&app=" + C0459b.f835d + "&prod=" + C0459b.f836e;
        if (this.f377j == null) {
            this.f377j = new C0371a(this);
        }
        this.f377j.m402a(str, false);
    }

    private void m414f() {
        String str = C0467i.f869a + "/config.dat";
        int i = C0468j.f896X ? 1 : 0;
        int i2 = C0468j.f897Y ? 1 : 0;
        byte[] bytes = String.format(Locale.CHINA, "{\"ver\":\"%d\",\"gps\":\"%.1f|%.1f|%.1f|%.1f|%d|%d|%d|%d|%d|%d|%d\",\"up\":\"%.1f|%.1f|%.1f|%.1f\",\"wf\":\"%d|%.1f|%d|%.1f\",\"ab\":\"%.2f|%.2f|%d|%d\",\"gpc\":\"%d|%d|%d|%d|%d|%d\",\"zxd\":\"%.1f|%.1f|%d|%d|%d\",\"shak\":\"%d|%d|%.1f\",\"dmx\":%d}", new Object[]{Integer.valueOf(C0468j.f920v), Float.valueOf(C0468j.f921w), Float.valueOf(C0468j.f922x), Float.valueOf(C0468j.f923y), Float.valueOf(C0468j.f924z), Integer.valueOf(C0468j.f873A), Integer.valueOf(C0468j.f874B), Integer.valueOf(C0468j.f875C), Integer.valueOf(C0468j.f876D), Integer.valueOf(C0468j.f877E), Integer.valueOf(C0468j.f878F), Integer.valueOf(C0468j.f879G), Float.valueOf(C0468j.f880H), Float.valueOf(C0468j.f881I), Float.valueOf(C0468j.f882J), Float.valueOf(C0468j.f883K), Integer.valueOf(C0468j.f884L), Float.valueOf(C0468j.f885M), Integer.valueOf(C0468j.f886N), Float.valueOf(C0468j.f887O), Float.valueOf(C0468j.f888P), Float.valueOf(C0468j.f889Q), Integer.valueOf(C0468j.f890R), Integer.valueOf(C0468j.f891S), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(C0468j.f898Z), Integer.valueOf(C0468j.ab), Long.valueOf(C0468j.ag), Integer.valueOf(C0468j.aj), Float.valueOf(C0468j.an), Float.valueOf(C0468j.ao), Integer.valueOf(C0468j.ap), Integer.valueOf(C0468j.aq), Integer.valueOf(C0468j.ar), Integer.valueOf(C0468j.ak), Integer.valueOf(C0468j.al), Float.valueOf(C0468j.am), Integer.valueOf(C0468j.ai)}).getBytes();
        try {
            RandomAccessFile randomAccessFile;
            File file = new File(str);
            if (!file.exists()) {
                File file2 = new File(C0467i.f869a);
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

    private void m415g() {
        try {
            RandomAccessFile randomAccessFile;
            File file = new File(C0467i.f869a + "/config.dat");
            if (!file.exists()) {
                File file2 = new File(C0467i.f869a);
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
            randomAccessFile.writeDouble(C0468j.f915q);
            randomAccessFile.writeDouble(C0468j.f916r);
            randomAccessFile.writeBoolean(C0468j.f919u);
            if (C0468j.f919u && C0468j.f918t != null) {
                randomAccessFile.write(C0468j.f918t);
            }
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    private void m416h() {
        try {
            File file = new File(C0467i.f869a + "/config.dat");
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                if (randomAccessFile.readBoolean()) {
                    randomAccessFile.seek(2);
                    int readInt = randomAccessFile.readInt();
                    byte[] bArr = new byte[readInt];
                    randomAccessFile.read(bArr, 0, readInt);
                    m411b(new String(bArr));
                }
                randomAccessFile.seek(1);
                if (randomAccessFile.readBoolean()) {
                    randomAccessFile.seek(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
                    C0468j.f915q = randomAccessFile.readDouble();
                    C0468j.f916r = randomAccessFile.readDouble();
                    C0468j.f919u = randomAccessFile.readBoolean();
                    if (C0468j.f919u) {
                        C0468j.f918t = new byte[625];
                        randomAccessFile.read(C0468j.f918t, 0, 625);
                    }
                }
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
        m412c(null);
    }

    private void m417i() {
        try {
            File file = new File(f365k);
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
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(128);
                randomAccessFile.writeInt(0);
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    private void m418j() {
        int i = 0;
        try {
            File file = new File(f365k);
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(4);
                int readInt = randomAccessFile.readInt();
                if (readInt > MessageHandler.WHAT_ITEM_SELECTED) {
                    randomAccessFile.close();
                    f368n = 0;
                    m417i();
                    return;
                }
                int readInt2 = randomAccessFile.readInt();
                randomAccessFile.seek(128);
                byte[] bArr = new byte[readInt];
                while (i < readInt2) {
                    randomAccessFile.seek((long) ((readInt * i) + 128));
                    int readInt3 = randomAccessFile.readInt();
                    if (readInt3 > 0 && readInt3 < readInt) {
                        randomAccessFile.read(bArr, 0, readInt3);
                        if (bArr[readInt3 - 1] == (byte) 0) {
                            String str = new String(bArr, 0, readInt3 - 1);
                            C0459b.m980a();
                            if (str.equals(C0459b.f835d)) {
                                f366l = randomAccessFile.readInt();
                                f368n = i;
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                    i++;
                }
                if (i == readInt2) {
                    f368n = readInt2;
                }
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    public void m419a(String str) {
        if (this.f377j == null) {
            this.f377j = new C0371a(this);
        }
        this.f377j.m402a(str, true);
    }

    public void m420b() {
        m416h();
    }

    public void m421c() {
    }

    public void m422d() {
        if (System.currentTimeMillis() - C0460c.m989a().m996d() > LogBuilder.MAX_INTERVAL) {
            C0460c.m989a().m995c(System.currentTimeMillis());
            m413e();
        }
    }
}
