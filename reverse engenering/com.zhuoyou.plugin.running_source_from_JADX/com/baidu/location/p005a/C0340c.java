package com.baidu.location.p005a;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.location.Location;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import com.baidu.location.C0455f;
import com.baidu.location.Jni;
import com.baidu.location.p006h.C0335e;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0467i;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p007b.C0370d;
import com.baidu.location.p012f.C0443b;
import com.baidu.location.p012f.C0444c;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import com.tencent.connect.common.Constants;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.HttpResponseCode;

public class C0340c {
    public static String f162f = "0";
    private static C0340c f163j = null;
    private C0339a f164A;
    private boolean f165B;
    private boolean f166C;
    private int f167D;
    private float f168E;
    private float f169F;
    private long f170G;
    private int f171H;
    private Handler f172I;
    private byte[] f173J;
    private byte[] f174K;
    private int f175L;
    private List<Byte> f176M;
    private boolean f177N;
    long f178a;
    Location f179b;
    Location f180c;
    StringBuilder f181d;
    long f182e;
    int f183g;
    double f184h;
    double f185i;
    private int f186k;
    private double f187l;
    private String f188m;
    private int f189n;
    private int f190o;
    private int f191p;
    private int f192q;
    private double f193r;
    private double f194s;
    private double f195t;
    private int f196u;
    private int f197v;
    private int f198w;
    private int f199x;
    private int f200y;
    private long f201z;

    class C0339a extends C0335e {
        String f160a;
        final /* synthetic */ C0340c f161b;

        public C0339a(C0340c c0340c) {
            this.f161b = c0340c;
            this.f160a = null;
            this.k = new HashMap();
        }

        public void mo1741a() {
            this.h = "http://loc.map.baidu.com/cc.php";
            String encode = Jni.encode(this.f160a);
            this.f160a = null;
            this.k.put(WidgetRequestParam.REQ_PARAM_COMMENT_TOPIC, encode);
        }

        public void m220a(String str) {
            this.f160a = str;
            m204e();
        }

        public void mo1742a(boolean z) {
            if (z && this.j != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.j);
                    jSONObject.put("prod", C0459b.f835d);
                    jSONObject.put("uptime", System.currentTimeMillis());
                    this.f161b.m241e(jSONObject.toString());
                } catch (Exception e) {
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
        }
    }

    private C0340c() {
        this.f186k = 1;
        this.f187l = 0.699999988079071d;
        this.f188m = "3G|4G";
        this.f189n = 1;
        this.f190o = 307200;
        this.f191p = 15;
        this.f192q = 1;
        this.f193r = 3.5d;
        this.f194s = 3.0d;
        this.f195t = 0.5d;
        this.f196u = 300;
        this.f197v = 60;
        this.f198w = 0;
        this.f199x = 60;
        this.f200y = 0;
        this.f201z = 0;
        this.f164A = null;
        this.f165B = false;
        this.f166C = false;
        this.f167D = 0;
        this.f168E = 0.0f;
        this.f169F = 0.0f;
        this.f170G = 0;
        this.f171H = 500;
        this.f178a = 0;
        this.f179b = null;
        this.f180c = null;
        this.f181d = null;
        this.f182e = 0;
        this.f172I = null;
        this.f173J = new byte[4];
        this.f174K = null;
        this.f175L = 0;
        this.f176M = null;
        this.f177N = false;
        this.f183g = 0;
        this.f184h = 116.22345545d;
        this.f185i = 40.245667323d;
        this.f172I = new Handler();
    }

    public static C0340c m222a() {
        if (f163j == null) {
            f163j = new C0340c();
        }
        return f163j;
    }

    private String m224a(File file, String str) {
        String uuid = UUID.randomUUID().toString();
        String str2 = "--";
        String str3 = "\r\n";
        String str4 = "multipart/form-data";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod(Constants.HTTP_POST);
            httpURLConnection.setRequestProperty("Charset", "utf-8");
            httpURLConnection.setRequestProperty("connection", "close");
            httpURLConnection.setRequestProperty("Content-Type", str4 + ";boundary=" + uuid);
            if (file != null && file.exists()) {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(str2);
                stringBuffer.append(uuid);
                stringBuffer.append(str3);
                stringBuffer.append("Content-Disposition: form-data; name=\"location_dat\"; filename=\"" + file.getName() + "\"" + str3);
                stringBuffer.append("Content-Type: application/octet-stream; charset=utf-8" + str3);
                stringBuffer.append(str3);
                dataOutputStream.write(stringBuffer.toString().getBytes());
                InputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    dataOutputStream.write(bArr, 0, read);
                }
                fileInputStream.close();
                dataOutputStream.write(str3.getBytes());
                dataOutputStream.write((str2 + uuid + str2 + str3).getBytes());
                dataOutputStream.flush();
                int responseCode = httpURLConnection.getResponseCode();
                outputStream.close();
                httpURLConnection.disconnect();
                this.f200y += HttpResponseCode.BAD_REQUEST;
                m234c(this.f200y);
                if (responseCode == 200) {
                    return "1";
                }
            }
        } catch (MalformedURLException e) {
        } catch (IOException e2) {
        }
        return "0";
    }

    private boolean m227a(String str, Context context) {
        boolean z = false;
        try {
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses != null) {
                for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                    boolean z2;
                    if (runningAppProcessInfo.processName.equals(str)) {
                        int i = runningAppProcessInfo.importance;
                        if (i == 200 || i == 100) {
                            z2 = true;
                            z = z2;
                        }
                    }
                    z2 = z;
                    z = z2;
                }
            }
        } catch (Exception e) {
        }
        return z;
    }

    private byte[] m228a(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & i) >> 8), (byte) ((16711680 & i) >> 16), (byte) ((-16777216 & i) >> 24)};
    }

    private byte[] m229a(String str) {
        int i = 0;
        if (str == null) {
            return null;
        }
        byte[] bytes = str.getBytes();
        byte nextInt = (byte) new Random().nextInt(255);
        byte nextInt2 = (byte) new Random().nextInt(255);
        byte[] bArr = new byte[(bytes.length + 2)];
        int length = bytes.length;
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 1;
            bArr[i2] = (byte) (bytes[i] ^ nextInt);
            i++;
            i2 = i3;
        }
        i = i2 + 1;
        bArr[i2] = nextInt;
        i2 = i + 1;
        bArr[i] = nextInt2;
        return bArr;
    }

    private String m230b(String str) {
        Calendar instance = Calendar.getInstance();
        return String.format(str, new Object[]{Integer.valueOf(instance.get(1)), Integer.valueOf(instance.get(2) + 1), Integer.valueOf(instance.get(5))});
    }

    private void m231b(int i) {
        byte[] a = m228a(i);
        for (int i2 = 0; i2 < 4; i2++) {
            this.f176M.add(Byte.valueOf(a[i2]));
        }
    }

    private void m232b(Location location) {
        m235c(location);
        m245h();
    }

    private void m233c() {
        if (!this.f177N) {
            this.f177N = true;
            m239d(C0459b.f835d);
            m247j();
            m237d();
        }
    }

    private void m234c(int i) {
        if (i != 0) {
            try {
                RandomAccessFile randomAccessFile;
                File file = new File(C0467i.f869a + "/grtcf.dat");
                if (!file.exists()) {
                    File file2 = new File(C0467i.f869a);
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                    if (file.createNewFile()) {
                        randomAccessFile = new RandomAccessFile(file, "rw");
                        randomAccessFile.seek(2);
                        randomAccessFile.writeInt(0);
                        randomAccessFile.seek(8);
                        byte[] bytes = "1980_01_01:0".getBytes();
                        randomAccessFile.writeInt(bytes.length);
                        randomAccessFile.write(bytes);
                        randomAccessFile.seek(200);
                        randomAccessFile.writeBoolean(false);
                        randomAccessFile.seek(800);
                        randomAccessFile.writeBoolean(false);
                        randomAccessFile.close();
                    } else {
                        return;
                    }
                }
                randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(8);
                byte[] bytes2 = (m230b("%d_%02d_%02d") + ":" + i).getBytes();
                randomAccessFile.writeInt(bytes2.length);
                randomAccessFile.write(bytes2);
                randomAccessFile.close();
            } catch (Exception e) {
            }
        }
    }

    private void m235c(Location location) {
        if (System.currentTimeMillis() - this.f178a >= ((long) this.f171H) && location != null) {
            if (location != null && location.hasSpeed() && location.getSpeed() > this.f168E) {
                this.f168E = location.getSpeed();
            }
            try {
                if (this.f176M == null) {
                    this.f176M = new ArrayList();
                    m246i();
                    m238d(location);
                } else {
                    m240e(location);
                }
            } catch (Exception e) {
            }
            this.f175L++;
        }
    }

    private void m236c(String str) {
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("on")) {
                    this.f186k = jSONObject.getInt("on");
                }
                if (jSONObject.has("bash")) {
                    this.f187l = jSONObject.getDouble("bash");
                }
                if (jSONObject.has("net")) {
                    this.f188m = jSONObject.getString("net");
                }
                if (jSONObject.has("tcon")) {
                    this.f189n = jSONObject.getInt("tcon");
                }
                if (jSONObject.has("tcsh")) {
                    this.f190o = jSONObject.getInt("tcsh");
                }
                if (jSONObject.has("per")) {
                    this.f191p = jSONObject.getInt("per");
                }
                if (jSONObject.has("chdron")) {
                    this.f192q = jSONObject.getInt("chdron");
                }
                if (jSONObject.has("spsh")) {
                    this.f193r = jSONObject.getDouble("spsh");
                }
                if (jSONObject.has("acsh")) {
                    this.f194s = jSONObject.getDouble("acsh");
                }
                if (jSONObject.has("stspsh")) {
                    this.f195t = jSONObject.getDouble("stspsh");
                }
                if (jSONObject.has("drstsh")) {
                    this.f196u = jSONObject.getInt("drstsh");
                }
                if (jSONObject.has("stper")) {
                    this.f197v = jSONObject.getInt("stper");
                }
                if (jSONObject.has("nondron")) {
                    this.f198w = jSONObject.getInt("nondron");
                }
                if (jSONObject.has("nondrper")) {
                    this.f199x = jSONObject.getInt("nondrper");
                }
                if (jSONObject.has("uptime")) {
                    this.f201z = jSONObject.getLong("uptime");
                }
                m248k();
            } catch (JSONException e) {
            }
        }
    }

    private void m237d() {
        String str = null;
        if (null == null) {
            str = "7.0.1";
        }
        String[] split = str.split("\\.");
        int length = split.length;
        this.f173J[0] = (byte) 0;
        this.f173J[1] = (byte) 0;
        this.f173J[2] = (byte) 0;
        this.f173J[3] = (byte) 0;
        if (length >= 4) {
            length = 4;
        }
        int i = 0;
        while (i < length) {
            try {
                this.f173J[i] = (byte) (Integer.valueOf(split[i]).intValue() & 255);
                i++;
            } catch (Exception e) {
            }
        }
        this.f174K = m229a(C0459b.f835d + ":" + C0459b.m980a().f841b);
    }

    private void m238d(Location location) {
        Object obj = null;
        this.f182e = System.currentTimeMillis();
        m231b((int) (this.f182e / 1000));
        m231b((int) (location.getLongitude() * 1000000.0d));
        m231b((int) (location.getLatitude() * 1000000.0d));
        if (location.hasBearing()) {
            Object obj2 = null;
        } else {
            int i = 1;
        }
        if (!location.hasSpeed()) {
            int i2 = 1;
        }
        if (obj2 > null) {
            this.f176M.add(Byte.valueOf((byte) 32));
        } else {
            this.f176M.add(Byte.valueOf((byte) (((byte) (((int) (location.getBearing() / 15.0f)) & 255)) & -33)));
        }
        if (obj > null) {
            this.f176M.add(Byte.valueOf(Byte.MIN_VALUE));
        } else {
            this.f176M.add(Byte.valueOf((byte) (((byte) (((int) ((((double) location.getSpeed()) * 3.6d) / 4.0d)) & 255)) & TransportMediator.KEYCODE_MEDIA_PAUSE)));
        }
        this.f179b = location;
    }

    private void m239d(String str) {
        int i = 1;
        try {
            File file = new File(C0467i.f869a + "/grtcf.dat");
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(2);
                int readInt = randomAccessFile.readInt();
                randomAccessFile.seek(8);
                int readInt2 = randomAccessFile.readInt();
                byte[] bArr = new byte[readInt2];
                randomAccessFile.read(bArr, 0, readInt2);
                String str2 = new String(bArr);
                if (str2.contains(m230b("%d_%02d_%02d")) && str2.contains(":")) {
                    try {
                        String[] split = str2.split(":");
                        if (split.length > 1) {
                            this.f200y = Integer.valueOf(split[1]).intValue();
                        }
                    } catch (Exception e) {
                    }
                }
                while (i <= readInt) {
                    randomAccessFile.seek((long) (i * 2048));
                    readInt2 = randomAccessFile.readInt();
                    bArr = new byte[readInt2];
                    randomAccessFile.read(bArr, 0, readInt2);
                    str2 = new String(bArr);
                    if (str != null && str2.contains(str)) {
                        m236c(str2);
                        break;
                    }
                    i++;
                }
                randomAccessFile.close();
            }
        } catch (Exception e2) {
        }
    }

    private void m240e(Location location) {
        if (location != null) {
            Object obj;
            Object obj2;
            Object obj3;
            Object obj4;
            int longitude = (int) ((location.getLongitude() - this.f179b.getLongitude()) * 100000.0d);
            int latitude = (int) ((location.getLatitude() - this.f179b.getLatitude()) * 100000.0d);
            if (location.hasBearing()) {
                obj = null;
            } else {
                int i = 1;
            }
            if (location.hasSpeed()) {
                obj2 = null;
            } else {
                int i2 = 1;
            }
            if (longitude > 0) {
                obj3 = null;
            } else {
                int i3 = 1;
            }
            int abs = Math.abs(longitude);
            if (latitude > 0) {
                obj4 = null;
            } else {
                int i4 = 1;
            }
            int abs2 = Math.abs(latitude);
            if (this.f175L > 1) {
                this.f180c = null;
                this.f180c = this.f179b;
            }
            this.f179b = location;
            if (this.f179b != null && this.f180c != null && this.f179b.getTime() > this.f180c.getTime() && this.f179b.getTime() - this.f180c.getTime() < 5000) {
                long time = this.f179b.getTime() - this.f180c.getTime();
                float[] fArr = new float[2];
                Location.distanceBetween(this.f179b.getAltitude(), this.f179b.getLongitude(), this.f180c.getLatitude(), this.f180c.getLongitude(), fArr);
                double speed = (double) ((2.0f * (fArr[0] - (this.f180c.getSpeed() * ((float) time)))) / ((float) (time * time)));
                if (speed > ((double) this.f169F)) {
                    this.f169F = (float) speed;
                }
            }
            this.f176M.add(Byte.valueOf((byte) (abs & 255)));
            this.f176M.add(Byte.valueOf((byte) (abs2 & 255)));
            byte b;
            if (obj > null) {
                b = (byte) 32;
                if (obj4 > null) {
                    b = (byte) 96;
                }
                if (obj3 > null) {
                    b = (byte) (b | -128);
                }
                this.f176M.add(Byte.valueOf(b));
            } else {
                b = (byte) (((byte) (((int) (location.getBearing() / 15.0f)) & 255)) & 31);
                if (obj4 > null) {
                    b = (byte) (b | 64);
                }
                if (obj3 > null) {
                    b = (byte) (b | -128);
                }
                this.f176M.add(Byte.valueOf(b));
            }
            if (obj2 > null) {
                this.f176M.add(Byte.valueOf(Byte.MIN_VALUE));
                return;
            }
            this.f176M.add(Byte.valueOf((byte) (((byte) (((int) ((((double) location.getSpeed()) * 3.6d) / 4.0d)) & 255)) & TransportMediator.KEYCODE_MEDIA_PAUSE)));
        }
    }

    private void m241e(String str) {
        try {
            File file = new File(C0467i.f869a + "/grtcf.dat");
            if (!file.exists()) {
                File file2 = new File(C0467i.f869a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (file.createNewFile()) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(2);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.seek(8);
                    byte[] bytes = "1980_01_01:0".getBytes();
                    randomAccessFile.writeInt(bytes.length);
                    randomAccessFile.write(bytes);
                    randomAccessFile.seek(200);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.seek(800);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.close();
                } else {
                    return;
                }
            }
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rw");
            randomAccessFile2.seek(2);
            int readInt = randomAccessFile2.readInt();
            int i = 1;
            while (i <= readInt) {
                randomAccessFile2.seek((long) (i * 2048));
                int readInt2 = randomAccessFile2.readInt();
                byte[] bArr = new byte[readInt2];
                randomAccessFile2.read(bArr, 0, readInt2);
                if (new String(bArr).contains(C0459b.f835d)) {
                    break;
                }
                i++;
            }
            if (i >= readInt) {
                randomAccessFile2.seek(2);
                randomAccessFile2.writeInt(i);
            }
            randomAccessFile2.seek((long) (i * 2048));
            byte[] bytes2 = str.getBytes();
            randomAccessFile2.writeInt(bytes2.length);
            randomAccessFile2.write(bytes2);
            randomAccessFile2.close();
        } catch (Exception e) {
        }
    }

    private boolean m242e() {
        Throwable th;
        Object obj;
        FileLock fileLock = null;
        FileChannel fileChannel = null;
        FileLock fileLock2 = null;
        boolean z = false;
        RandomAccessFile randomAccessFile;
        try {
            File file = new File(C0468j.m1028f() + File.separator + "gflk.dat");
            if (!file.exists()) {
                file.createNewFile();
            }
            if (fileLock == null) {
                randomAccessFile = new RandomAccessFile(file, "rw");
                try {
                    fileChannel = randomAccessFile.getChannel();
                    try {
                        fileLock = fileChannel.tryLock();
                    } catch (Exception e) {
                        z = true;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileLock != null) {
                            try {
                                fileLock2.release();
                            } catch (Exception e2) {
                                throw th;
                            }
                        }
                        if (fileChannel != null) {
                            fileChannel.close();
                        }
                        if (randomAccessFile != null) {
                            randomAccessFile.close();
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    if (fileLock != null) {
                        try {
                            fileLock2.release();
                        } catch (Exception e4) {
                        }
                    }
                    if (fileLock != null) {
                        fileChannel.close();
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                    return z;
                } catch (Throwable th3) {
                    th = th3;
                    obj = fileLock;
                    if (fileLock != null) {
                        fileLock2.release();
                    }
                    if (fileChannel != null) {
                        fileChannel.close();
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                    throw th;
                }
            }
            Object obj2 = fileLock;
            obj = fileLock;
            if (fileLock != null) {
                try {
                    fileLock.release();
                } catch (Exception e5) {
                }
            }
            if (fileChannel != null) {
                fileChannel.close();
            }
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        } catch (Exception e6) {
            randomAccessFile = fileLock;
            if (fileLock != null) {
                fileLock2.release();
            }
            if (fileLock != null) {
                fileChannel.close();
            }
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            return z;
        } catch (Throwable th4) {
            th = th4;
            randomAccessFile = fileLock;
            fileChannel = fileLock;
            if (fileLock != null) {
                fileLock2.release();
            }
            if (fileChannel != null) {
                fileChannel.close();
            }
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            throw th;
        }
        return z;
    }

    private boolean m243f() {
        if (this.f165B) {
            if (this.f166C) {
                if (((double) this.f168E) < this.f195t) {
                    this.f167D += this.f191p;
                    if (this.f167D <= this.f196u || System.currentTimeMillis() - this.f170G > ((long) (this.f197v * 1000))) {
                        return true;
                    }
                }
                this.f167D = 0;
                this.f166C = false;
                return true;
            } else if (((double) this.f168E) >= this.f195t) {
                return true;
            } else {
                this.f166C = true;
                this.f167D = 0;
                this.f167D += this.f191p;
                return true;
            }
        } else if (((double) this.f168E) >= this.f193r || ((double) this.f169F) >= this.f194s) {
            this.f165B = true;
            return true;
        } else if (this.f198w == 1 && System.currentTimeMillis() - this.f170G > ((long) (this.f199x * 1000))) {
            return true;
        }
        return false;
    }

    private void m244g() {
        this.f176M = null;
        this.f182e = 0;
        this.f175L = 0;
        this.f179b = null;
        this.f180c = null;
        this.f168E = 0.0f;
        this.f169F = 0.0f;
    }

    private void m245h() {
        if (this.f182e != 0 && System.currentTimeMillis() - this.f182e >= ((long) (this.f191p * 1000))) {
            if (C0455f.getServiceContext().getSharedPreferences("loc_navi_mode", 4).getBoolean("is_navi_on", false)) {
                m244g();
            } else if (this.f189n != 1 || m243f()) {
                if (C0459b.f835d.equals("com.ubercab.driver")) {
                    if (m242e()) {
                        m244g();
                        return;
                    }
                } else if (!m227a(C0459b.f835d, C0455f.getServiceContext())) {
                    m244g();
                    return;
                }
                if (this.f176M != null) {
                    int size = this.f176M.size();
                    this.f176M.set(0, Byte.valueOf((byte) (size & 255)));
                    this.f176M.set(1, Byte.valueOf((byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & size) >> 8)));
                    this.f176M.set(3, Byte.valueOf((byte) (this.f175L & 255)));
                    byte[] bArr = new byte[size];
                    for (int i = 0; i < size; i++) {
                        bArr[i] = ((Byte) this.f176M.get(i)).byteValue();
                    }
                    if (Environment.getExternalStorageState().equals("mounted")) {
                        File file = new File(Environment.getExternalStorageDirectory(), "baidu/tempdata");
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        if (file.exists()) {
                            File file2 = new File(file, "intime.dat");
                            if (file2.exists()) {
                                file2.delete();
                            }
                            try {
                                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
                                bufferedOutputStream.write(bArr);
                                bufferedOutputStream.flush();
                                bufferedOutputStream.close();
                                new C0342e(this).start();
                            } catch (Exception e) {
                            }
                        }
                    }
                    m244g();
                    this.f170G = System.currentTimeMillis();
                }
            } else {
                m244g();
            }
        }
    }

    private void m246i() {
        int i = 0;
        this.f176M.add(Byte.valueOf((byte) 0));
        this.f176M.add(Byte.valueOf((byte) 0));
        if (f162f.equals("0")) {
            this.f176M.add(Byte.valueOf((byte) 110));
        } else {
            this.f176M.add(Byte.valueOf((byte) 126));
        }
        this.f176M.add(Byte.valueOf((byte) 0));
        this.f176M.add(Byte.valueOf(this.f173J[0]));
        this.f176M.add(Byte.valueOf(this.f173J[1]));
        this.f176M.add(Byte.valueOf(this.f173J[2]));
        this.f176M.add(Byte.valueOf(this.f173J[3]));
        int length = this.f174K.length;
        this.f176M.add(Byte.valueOf((byte) ((length + 1) & 255)));
        while (i < length) {
            this.f176M.add(Byte.valueOf(this.f174K[i]));
            i++;
        }
    }

    private void m247j() {
        if (System.currentTimeMillis() - this.f201z > LogBuilder.MAX_INTERVAL) {
            if (this.f164A == null) {
                this.f164A = new C0339a(this);
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(C0459b.m980a().m981a(false));
            stringBuffer.append(C0332a.m176a().m188d());
            this.f164A.m220a(stringBuffer.toString());
        }
        m248k();
    }

    private void m248k() {
    }

    public void m249a(Location location) {
        if (!this.f177N) {
            m233c();
        }
        Object obj = ((double) C0370d.m393a().m400f()) < this.f187l * 100.0d ? 1 : null;
        if (this.f186k != 1 || obj == null || !this.f188m.contains(C0444c.m876a(C0443b.m855a().m872e()))) {
            return;
        }
        if (this.f189n != 1 || this.f200y <= this.f190o) {
            this.f172I.post(new C0341d(this, location));
        }
    }

    public void m250b() {
        if (this.f177N) {
            this.f177N = false;
            m244g();
        }
    }
}
