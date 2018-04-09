package com.baidu.location;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import com.baidu.location.C1981n.C0529a;
import com.baidu.location.ai.C0503b;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Calendar;
import p031u.aly.C1507j;

class C1978h implements an, C1619j {
    private static String bB = (L + "/glb.dat");
    private static final int bD = 200;
    private static File bE = null;
    private static File bI = null;
    private static final int bK = 800;
    private static final int bL = 24;
    public static final String br = "com.baidu.locTest.LocationServer";
    private Context bA = null;
    private String bC = null;
    private long bF = 0;
    private C0529a bG = null;
    private String bH = null;
    private PendingIntent bJ = null;
    private String bM = (L + "/vm.dat");
    private long[] bn = new long[20];
    private boolean bo = false;
    private boolean bp = false;
    private final int bq = 1;
    private int bs = 0;
    private final long bt = 86100000;
    private AlarmManager bu = null;
    private int bv = 1;
    private C0525a bw = null;
    private final int bx = 200;
    private Handler by = null;
    private boolean bz = false;

    class C05241 extends Handler {
        final /* synthetic */ C1978h f2263a;

        C05241(C1978h c1978h) {
            this.f2263a = c1978h;
        }

        public void handleMessage(Message message) {
            if (C1976f.isServing) {
                switch (message.what) {
                    case 1:
                        this.f2263a.m5966m();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public class C0525a extends BroadcastReceiver {
        final /* synthetic */ C1978h f2264a;

        public C0525a(C1978h c1978h) {
            this.f2264a = c1978h;
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(C1978h.br)) {
                this.f2264a.by.sendEmptyMessage(1);
                return;
            }
            try {
                if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                    int intExtra = intent.getIntExtra("status", 0);
                    int intExtra2 = intent.getIntExtra("plugged", 0);
                    switch (intExtra) {
                        case 2:
                            this.f2264a.bH = "4";
                            break;
                        case 3:
                        case 4:
                            this.f2264a.bH = "3";
                            break;
                        default:
                            this.f2264a.bH = null;
                            break;
                    }
                    switch (intExtra2) {
                        case 1:
                            this.f2264a.bH = "6";
                            return;
                        case 2:
                            this.f2264a.bH = "5";
                            return;
                        default:
                            return;
                    }
                }
            } catch (Exception e) {
                this.f2264a.bH = null;
            }
        }
    }

    public C1978h(Context context) {
        this.bA = context;
        this.by = new C05241(this);
        this.bF = System.currentTimeMillis();
        this.bu = (AlarmManager) context.getSystemService("alarm");
        this.bw = new C0525a(this);
        context.registerReceiver(this.bw, new IntentFilter(br));
        this.bJ = PendingIntent.getBroadcast(context, 0, new Intent(br), 134217728);
        this.bu.setRepeating(2, C1974b.aJ, C1974b.aJ, this.bJ);
        context.registerReceiver(this.bw, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    }

    public static void m5961j() {
        try {
            if (bB != null) {
                bE = new File(bB);
                if (!bE.exists()) {
                    File file = new File(L);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    bE.createNewFile();
                    RandomAccessFile randomAccessFile = new RandomAccessFile(bE, "rw");
                    randomAccessFile.seek(0);
                    randomAccessFile.writeInt(-1);
                    randomAccessFile.writeInt(-1);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.writeLong(0);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.close();
                    return;
                }
                return;
            }
            bE = null;
        } catch (Exception e) {
            bE = null;
        }
    }

    public static String m5962l() {
        if (!C1974b.aY) {
            return null;
        }
        C1978h.m5961j();
        try {
            if (bE == null) {
                return null;
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(bE, "rw");
            int readInt = randomAccessFile.readInt();
            randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            if (readInt > 0) {
                randomAccessFile.seek((long) ((readInt2 * bK) + 24));
                int readInt3 = randomAccessFile.readInt();
                byte[] bArr = new byte[bK];
                randomAccessFile.read(bArr, 0, readInt3);
                int readInt4 = randomAccessFile.readInt();
                readInt--;
                readInt2 = (readInt2 + 1) % 200;
                randomAccessFile.seek(0);
                randomAccessFile.writeInt(readInt);
                randomAccessFile.seek(8);
                randomAccessFile.writeInt(readInt2);
                if (readInt4 == readInt3) {
                    for (int i = 0; i < bArr.length; i++) {
                        bArr[i] = (byte) (bArr[i] ^ 90);
                    }
                    String f = Jni.m5811f(new String(bArr, 0, readInt3));
                    randomAccessFile.close();
                    return f;
                }
                randomAccessFile.close();
                return null;
            }
            randomAccessFile.close();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public void m5963h() {
        m5964i();
        if (bI != null) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(bI, "rw");
                if (randomAccessFile.length() < 1) {
                    randomAccessFile.close();
                    return;
                }
                randomAccessFile.seek(0);
                int readInt = randomAccessFile.readInt();
                int i = (readInt * 200) + 4;
                readInt++;
                randomAccessFile.seek(0);
                randomAccessFile.writeInt(readInt);
                randomAccessFile.seek((long) i);
                randomAccessFile.writeLong(System.currentTimeMillis());
                randomAccessFile.writeInt(this.bv);
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(this.bs);
                randomAccessFile.writeInt(this.bG.f2271do);
                randomAccessFile.writeInt(this.bG.f2273if);
                randomAccessFile.writeInt(this.bG.f2272for);
                randomAccessFile.writeInt(this.bG.f2276try);
                byte[] bArr = new byte[C1507j.f3829b];
                for (int i2 = 0; i2 < this.bs; i2++) {
                    bArr[(i2 * 8) + 7] = (byte) ((int) this.bn[i2]);
                    bArr[(i2 * 8) + 6] = (byte) ((int) (this.bn[i2] >> 8));
                    bArr[(i2 * 8) + 5] = (byte) ((int) (this.bn[i2] >> 16));
                    bArr[(i2 * 8) + 4] = (byte) ((int) (this.bn[i2] >> 24));
                    bArr[(i2 * 8) + 3] = (byte) ((int) (this.bn[i2] >> 32));
                    bArr[(i2 * 8) + 2] = (byte) ((int) (this.bn[i2] >> 40));
                    bArr[(i2 * 8) + 1] = (byte) ((int) (this.bn[i2] >> 48));
                    bArr[(i2 * 8) + 0] = (byte) ((int) (this.bn[i2] >> 56));
                }
                if (this.bs > 0) {
                    randomAccessFile.write(bArr, 0, this.bs * 8);
                }
                randomAccessFile.writeInt(this.bs);
                randomAccessFile.close();
            } catch (Exception e) {
            }
        }
    }

    public void m5964i() {
        try {
            if (this.bM != null) {
                bI = new File(this.bM);
                if (!bI.exists()) {
                    File file = new File(L);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    bI.createNewFile();
                    RandomAccessFile randomAccessFile = new RandomAccessFile(bI, "rw");
                    randomAccessFile.seek(0);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.close();
                    return;
                }
                return;
            }
            bI = null;
        } catch (Exception e) {
            bI = null;
        }
    }

    public boolean m5965k() {
        return ((KeyguardManager) this.bA.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    public void m5966m() {
        int i = 0;
        try {
            m5969p();
            if (this.bz) {
                this.bz = false;
                return;
            }
            m5971r();
            this.bs = 0;
            this.bG = null;
            ai.bb().a8();
            C0503b ba = ai.bb().ba();
            if (!(ba == null || ba.f2165for == null)) {
                int size = ba.f2165for.size();
                if (size > 20) {
                    size = 20;
                }
                int i2 = 0;
                while (i2 < size) {
                    int i3;
                    String replace = ((ScanResult) ba.f2165for.get(i2)).BSSID.replace(":", "");
                    try {
                        i3 = i + 1;
                        try {
                            this.bn[i] = Long.parseLong(replace, 16);
                        } catch (Exception e) {
                        }
                    } catch (Exception e2) {
                        i3 = i;
                    }
                    i2++;
                    i = i3;
                }
                this.bs = i;
            }
            this.bG = C1981n.m6008K().m6018H();
            if (this.bG != null) {
                m5967n();
            }
        } catch (Exception e3) {
        }
    }

    public void m5967n() {
        String str;
        Object obj;
        m5964i();
        try {
            str = m5965k() ? "y2" : "y1";
        } catch (Exception e) {
            str = "y";
        }
        if (this.bp) {
            obj = null;
        } else {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(bI, "rw");
                if (randomAccessFile.length() < 1) {
                    randomAccessFile.close();
                    return;
                }
                int readInt = randomAccessFile.readInt();
                for (int i = 0; i < readInt; i++) {
                    randomAccessFile.seek((long) ((i * 200) + 4));
                    randomAccessFile.readLong();
                    int readInt2 = randomAccessFile.readInt();
                    int readInt3 = randomAccessFile.readInt();
                    int readInt4 = randomAccessFile.readInt();
                    byte[] bArr = new byte[200];
                    randomAccessFile.read(bArr, 0, (readInt4 * 8) + 16);
                    int i2 = (((bArr[7] & 255) | ((bArr[6] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | ((bArr[5] << 16) & 16711680)) | ((bArr[4] << 24) & ViewCompat.MEASURED_STATE_MASK);
                    int i3 = (((bArr[11] & 255) | ((bArr[10] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | ((bArr[9] << 16) & 16711680)) | ((bArr[8] << 24) & ViewCompat.MEASURED_STATE_MASK);
                    int i4 = (((bArr[15] & 255) | ((bArr[14] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | ((bArr[13] << 16) & 16711680)) | ((bArr[12] << 24) & ViewCompat.MEASURED_STATE_MASK);
                    if (this.bG.f2271do == ((((bArr[3] & 255) | ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | ((bArr[1] << 16) & 16711680)) | ((bArr[0] << 24) & ViewCompat.MEASURED_STATE_MASK)) && this.bG.f2273if == i2 && this.bG.f2272for == i3 && this.bG.f2276try == i4) {
                        int i5;
                        long[] jArr = new long[readInt4];
                        for (i5 = 0; i5 < readInt4; i5++) {
                            jArr[i5] = ((((((((((long) bArr[(i5 * 8) + 16]) & 255) << 56) | ((((long) bArr[((i5 * 8) + 16) + 1]) & 255) << 48)) | ((((long) bArr[((i5 * 8) + 16) + 2]) & 255) << 40)) | ((((long) bArr[((i5 * 8) + 16) + 3]) & 255) << 32)) | ((((long) bArr[((i5 * 8) + 16) + 4]) & 255) << 24)) | ((((long) bArr[((i5 * 8) + 16) + 5]) & 255) << 16)) | ((((long) bArr[((i5 * 8) + 16) + 6]) & 255) << 8)) | (((long) bArr[((i5 * 8) + 16) + 7]) & 255);
                        }
                        int i6 = 0;
                        i2 = 0;
                        while (i2 < this.bs) {
                            i5 = i6;
                            for (i6 = 0; i6 < readInt4; i6++) {
                                if (this.bn[i2] == jArr[i6]) {
                                    i5++;
                                }
                            }
                            i2++;
                            i6 = i5;
                        }
                        if (i6 > 5 || i6 * 8 > this.bs + readInt4 || ((readInt4 == 0 && this.bs == 0) || ((readInt4 == 1 && this.bs == 1 && this.bn[0] == jArr[0]) || (readInt4 > 1 && this.bs > 1 && this.bn[0] == jArr[0] && this.bn[1] == jArr[1])))) {
                            obj = 1;
                            int i7 = readInt3 + 1;
                            randomAccessFile.seek((long) ((i * 200) + 16));
                            randomAccessFile.writeInt(i7);
                            if (this.bC != null) {
                                this.bC += "|" + readInt2 + str;
                                if (this.bH != null) {
                                    this.bC += this.bH;
                                }
                            }
                            randomAccessFile.close();
                        }
                    }
                }
                obj = null;
                randomAccessFile.close();
            } catch (Exception e2) {
                return;
            }
        }
        if (obj == null) {
            String str2 = (this.bG.f2271do == 460 ? "|x," : "|x460,") + this.bG.f2273if + SeparatorConstants.SEPARATOR_ADS_ID + this.bG.f2272for + SeparatorConstants.SEPARATOR_ADS_ID + this.bG.f2276try;
            long j = 0;
            String bd = ai.bb().bd();
            if (bd != null) {
                try {
                    j = Long.parseLong(bd, 16);
                } catch (Exception e3) {
                }
            }
            if (this.bs == 1) {
                str2 = str2 + "w" + Long.toHexString(this.bn[0]) + "k";
                if (this.bn[0] == j) {
                    str2 = str2 + "k";
                }
            } else if (this.bs > 1) {
                str2 = str2 + "w" + Long.toHexString(this.bn[0]);
                if (this.bn[0] == j) {
                    str2 = str2 + "k";
                    j = 0;
                }
                str2 = j > 0 ? str2 + SeparatorConstants.SEPARATOR_ADS_ID + Long.toHexString(j) + "k" : str2 + SeparatorConstants.SEPARATOR_ADS_ID + Long.toHexString(this.bn[1]);
            }
            this.bC += str2 + str;
            if (this.bH != null) {
                this.bC += this.bH;
            }
            m5963h();
        }
        m5972s();
        this.bC = null;
    }

    public void m5968o() {
    }

    public void m5969p() {
        int i = 0;
        this.bp = false;
        this.bo = false;
        m5964i();
        C1978h.m5961j();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(bE, "rw");
            randomAccessFile.seek(0);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            randomAccessFile.readInt();
            long readLong = randomAccessFile.readLong();
            int readInt3 = randomAccessFile.readInt();
            if (readInt < 0) {
                this.bp = true;
                this.bo = true;
                randomAccessFile.close();
                return;
            }
            randomAccessFile.seek((long) ((readInt2 * bK) + 24));
            readInt = randomAccessFile.readInt();
            if (readInt > 680) {
                this.bp = true;
                this.bo = true;
                randomAccessFile.close();
                return;
            }
            byte[] bArr = new byte[bK];
            randomAccessFile.read(bArr, 0, readInt);
            if (readInt != randomAccessFile.readInt()) {
                this.bp = true;
                this.bo = true;
                randomAccessFile.close();
                return;
            }
            while (i < bArr.length) {
                bArr[i] = (byte) (bArr[i] ^ 90);
                i++;
            }
            this.bC = new String(bArr, 0, readInt);
            if (this.bC.contains("&tr=")) {
                long currentTimeMillis = System.currentTimeMillis();
                readLong = currentTimeMillis - readLong;
                if (readLong > (C1974b.aJ * 3) - C1974b.ay) {
                    this.bp = true;
                } else if (readLong > (C1974b.aJ * 2) - C1974b.ay) {
                    this.bC += "|" + readInt3;
                    this.bv = readInt3 + 2;
                } else if (readLong > C1974b.aJ - C1974b.ay) {
                    this.bv = readInt3 + 1;
                } else {
                    this.bz = true;
                    randomAccessFile.close();
                    return;
                }
                randomAccessFile.seek(12);
                randomAccessFile.writeLong(currentTimeMillis);
                randomAccessFile.writeInt(this.bv);
                randomAccessFile.close();
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(bI, "rw");
                randomAccessFile2.seek(0);
                if (randomAccessFile2.readInt() == 0) {
                    this.bp = true;
                    randomAccessFile2.close();
                    return;
                }
                randomAccessFile2.close();
                return;
            }
            this.bp = true;
            this.bo = true;
            randomAccessFile.close();
        } catch (Exception e) {
            this.bp = true;
            this.bo = true;
        }
    }

    public void m5970q() {
        this.bA.unregisterReceiver(this.bw);
        this.bu.cancel(this.bJ);
        bI = null;
    }

    public void m5971r() {
        int i = 0;
        if (this.bp) {
            this.bv = 1;
            C1974b.aJ = (C1974b.aL * 1000) * 60;
            C1974b.ay = C1974b.aJ >> 2;
            Calendar instance = Calendar.getInstance();
            int i2 = instance.get(5);
            int i3 = instance.get(1);
            if (i3 > 2000) {
                i = i3 - 2000;
            }
            i3 = instance.get(2) + 1;
            int i4 = instance.get(11);
            String str = i + SeparatorConstants.SEPARATOR_ADS_ID + i3 + SeparatorConstants.SEPARATOR_ADS_ID + i2 + SeparatorConstants.SEPARATOR_ADS_ID + i4 + SeparatorConstants.SEPARATOR_ADS_ID + instance.get(12) + SeparatorConstants.SEPARATOR_ADS_ID + C1974b.aL;
            if (this.bo) {
                this.bC = "&tr=" + ap.bD().bE() + SeparatorConstants.SEPARATOR_ADS_ID + str;
            } else {
                this.bC += "|T" + str;
            }
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(bE, "rw");
                randomAccessFile.seek(12);
                randomAccessFile.writeLong(System.currentTimeMillis());
                randomAccessFile.writeInt(this.bv);
                randomAccessFile.close();
                randomAccessFile = new RandomAccessFile(bI, "rw");
                randomAccessFile.seek(0);
                randomAccessFile.writeInt(0);
                randomAccessFile.close();
            } catch (Exception e) {
            }
        }
    }

    public void m5972s() {
        C1978h.m5961j();
        if (bE != null) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(bE, "rw");
                if (randomAccessFile.length() < 1) {
                    randomAccessFile.close();
                    return;
                }
                randomAccessFile.seek(0);
                int readInt = randomAccessFile.readInt();
                int readInt2 = randomAccessFile.readInt();
                int readInt3 = randomAccessFile.readInt();
                if (this.bp && this.bo) {
                    readInt2 = (readInt2 + 1) % 200;
                    randomAccessFile.seek(4);
                    randomAccessFile.writeInt(readInt2);
                    readInt++;
                    if (readInt >= 200) {
                        readInt = 199;
                    }
                    if (readInt2 == readInt3 && r2 > 0) {
                        randomAccessFile.writeInt((readInt3 + 1) % 200);
                    }
                    readInt2 = (readInt2 * bK) + 24;
                } else {
                    readInt2 = (readInt2 * bK) + 24;
                }
                randomAccessFile.seek((long) (readInt2 + 4));
                byte[] bytes = this.bC.getBytes();
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte) (bytes[i] ^ 90);
                }
                randomAccessFile.write(bytes, 0, bytes.length);
                randomAccessFile.writeInt(bytes.length);
                randomAccessFile.seek((long) readInt2);
                randomAccessFile.writeInt(bytes.length);
                if (this.bp && this.bo) {
                    randomAccessFile.seek(0);
                    randomAccessFile.writeInt(readInt);
                }
                randomAccessFile.close();
            } catch (Exception e) {
            }
        }
    }
}
