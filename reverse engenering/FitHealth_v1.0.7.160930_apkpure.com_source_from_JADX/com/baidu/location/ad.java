package com.baidu.location;

import android.net.wifi.ScanResult;
import android.os.Environment;
import android.text.TextUtils;
import com.baidu.location.C1981n.C0529a;
import com.baidu.location.ai.C0503b;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ad implements C1619j {
    private static ad im = null;
    private static String[] in = null;
    private static final String io = "loc_cache.dat";
    private static final String ip = ";";
    private static final String ir = ",";
    private static final int is = 5;
    private static final double it = 121.314d;
    private String[] iq = null;

    public class C0500a {
        final /* synthetic */ ad f2154a;
        public int f2155do;
        public boolean f2156for;
        public double f2157if;
        public double f2158int;
        public long f2159new;
        public double f2160try;

        public C0500a(ad adVar) {
            this.f2154a = adVar;
        }
    }

    private double bN() {
        return (this.iq == null || this.iq.length <= 2) ? 0.0d : Double.valueOf(this.iq[2]).doubleValue();
    }

    private double bO() {
        return (this.iq == null || this.iq.length <= 1) ? 0.0d : Double.valueOf(this.iq[1]).doubleValue() - it;
    }

    private long bP() {
        return (this.iq == null || this.iq.length < 3) ? 0 : Long.valueOf(this.iq[3]).longValue();
    }

    private boolean bQ() {
        C0529a H = C1981n.m6008K().m6018H();
        return !TextUtils.isEmpty(in[1]) && in[1].equals(String.format("%s|%s|%s|%s", new Object[]{Integer.valueOf(H.f2271do), Integer.valueOf(H.f2273if), Integer.valueOf(H.f2272for), Integer.valueOf(H.f2276try)}));
    }

    private void bS() {
        if (this.iq == null && in != null) {
            Object obj = in[0];
            if (!TextUtils.isEmpty(obj)) {
                this.iq = obj.split(",");
            }
        }
    }

    private double bT() {
        return (this.iq == null || this.iq.length <= 0) ? 0.0d : Double.valueOf(this.iq[0]).doubleValue() - it;
    }

    public static ad bU() {
        if (im == null) {
            im = new ad();
        }
        return im;
    }

    public C0500a bR() {
        byte[] bArr = null;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            File file = new File(C1976f.L + File.separator + io);
            if (file.exists()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    byte[] bArr2 = new byte[128];
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    while (true) {
                        int read = fileInputStream.read(bArr2);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr2, 0, read);
                    }
                    bArr = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    fileInputStream.close();
                } catch (Exception e) {
                }
            }
        }
        in = new String(bArr).split(ip);
        bS();
        C0500a c0500a = new C0500a(this);
        c0500a.f2158int = bT();
        c0500a.f2160try = bO();
        c0500a.f2157if = bN();
        c0500a.f2156for = bQ();
        c0500a.f2155do = bV();
        c0500a.f2159new = bP();
        return c0500a;
    }

    public int bV() {
        String[] split = in[2] != null ? in[2].split(",") : null;
        C0503b a7 = ai.bb().a7();
        if (a7 == null) {
            return 0;
        }
        List list = a7.f2165for;
        if (list == null) {
            return 0;
        }
        int i = 0;
        int i2 = 0;
        while (i < 5) {
            int i3;
            ScanResult scanResult = (ScanResult) list.get(i);
            if (scanResult != null) {
                String replace = scanResult.BSSID.replace(":", "");
                for (Object equals : split) {
                    if (replace.equals(equals)) {
                        i3 = i2 + 1;
                        break;
                    }
                }
            }
            i3 = i2;
            i++;
            i2 = i3;
        }
        return i2;
    }

    public void m5859try(BDLocation bDLocation) {
        int i = 0;
        if (bDLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
            String format;
            String format2 = String.format("%s,%s,%s,%d", new Object[]{Double.valueOf(bDLocation.getLongitude() + it), Double.valueOf(bDLocation.getLatitude() + it), Float.valueOf(bDLocation.getRadius()), Long.valueOf(System.currentTimeMillis())});
            if (C1981n.m6008K().m6018H().m2202for()) {
                format = String.format("%s|%s|%s|%s", new Object[]{Integer.valueOf(C1981n.m6008K().m6018H().f2271do), Integer.valueOf(C1981n.m6008K().m6018H().f2273if), Integer.valueOf(C1981n.m6008K().m6018H().f2272for), Integer.valueOf(C1981n.m6008K().m6018H().f2276try)});
            } else {
                format = null;
            }
            String str = null;
            C0503b a7 = ai.bb().a7();
            if (a7 != null) {
                List list = a7.f2165for;
                if (list != null) {
                    Iterable arrayList = new ArrayList();
                    while (i < 5) {
                        ScanResult scanResult = (ScanResult) list.get(i);
                        if (scanResult != null) {
                            arrayList.add(scanResult.BSSID.replace(":", ""));
                        }
                        i++;
                    }
                    str = TextUtils.join(",", arrayList);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(format2).append(ip).append(format).append(ip).append(str);
            str = stringBuilder.toString();
            if ("mounted".equals(Environment.getExternalStorageState())) {
                File file = new File(C1976f.L + File.separator + io);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.close();
                } catch (Exception e) {
                }
            }
        }
    }
}
