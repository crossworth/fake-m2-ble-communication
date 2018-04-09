package com.baidu.location;

import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.ArrayList;
import java.util.List;

class ak implements an {
    public int gh = 0;
    private int gi = 1;
    private List gj = null;
    private double gk = 0.0d;
    private String gl = "";
    private boolean gm = false;
    private double gn = 0.0d;
    private char go = 'N';
    private String gp = "";
    private List gq = null;
    private boolean gr = false;
    private int gs = 0;
    private boolean gt = false;
    private int gu = 0;
    private int gv = 0;
    private String gw = "";
    private String gx = "";
    private boolean gy = false;

    private class C0504a {
        final /* synthetic */ ak f2168a;
        private int f2169do = 0;
        private int f2170for = 0;
        private int f2171if = 0;
        private int f2172int = 0;

        public C0504a(ak akVar, int i, int i2, int i3, int i4) {
            this.f2168a = akVar;
            this.f2172int = i;
            this.f2171if = i2;
            this.f2169do = i3;
            this.f2170for = i4;
        }

        public int m2163a() {
            return this.f2171if;
        }

        public int m2164do() {
            return this.f2170for;
        }

        public int m2165if() {
            return this.f2169do;
        }
    }

    public ak(List list, String str, String str2, String str3) {
        this.gj = list;
        this.gp = str;
        this.gw = str2;
        this.gx = str3;
        this.gq = new ArrayList();
        bl();
    }

    private void bl() {
        String[] split;
        String substring;
        int i;
        int i2;
        boolean z = true;
        if (m4598j(this.gp)) {
            split = this.gp.split(SeparatorConstants.SEPARATOR_ADS_ID);
            if (split.length == 15) {
                if (!(split[6].equals("") || split[7].equals(""))) {
                    this.gs = Integer.valueOf(split[6]).intValue();
                    this.gu = Integer.valueOf(split[7]).intValue();
                    this.gy = true;
                }
            } else {
                return;
            }
        }
        if (m4598j(this.gx)) {
            substring = this.gx.substring(0, this.gx.length() - 3);
            i = 0;
            for (i2 = 0; i2 < substring.length(); i2++) {
                if (substring.charAt(i2) == ',') {
                    i++;
                }
            }
            split = substring.split(SeparatorConstants.SEPARATOR_ADS_ID, i + 1);
            if (split.length < 6) {
                return;
            }
            if (!(split[2].equals("") || split[split.length - 3].equals("") || split[split.length - 2].equals("") || split[split.length - 1].equals(""))) {
                this.gi = Integer.valueOf(split[2]).intValue();
                this.gn = Double.valueOf(split[split.length - 3]).doubleValue();
                this.gk = Double.valueOf(split[split.length - 2]).doubleValue();
                this.gt = true;
            }
        }
        if (this.gj != null && this.gj.size() > 0) {
            for (String str : this.gj) {
                if (!m4598j(str)) {
                    this.gm = false;
                    break;
                }
                substring = str.substring(0, str.length() - 3);
                i = 0;
                for (i2 = 0; i2 < substring.length(); i2++) {
                    if (substring.charAt(i2) == ',') {
                        i++;
                    }
                }
                String[] split2 = substring.split(SeparatorConstants.SEPARATOR_ADS_ID, i + 1);
                boolean z2 = Integer.valueOf(split2[1]).intValue() == this.gj.size() && split2.length > 8;
                this.gm = z2;
                if (!this.gm) {
                    break;
                }
                int i3 = 4;
                while (i3 < split2.length) {
                    if (!(split2[i3].equals("") || split2[i3 + 1].equals("") || split2[i3 + 2].equals(""))) {
                        this.gv++;
                        this.gq.add(new C0504a(this, Integer.valueOf(split2[i3]).intValue(), Integer.valueOf(split2[i3 + 2]).intValue(), Integer.valueOf(split2[i3 + 1]).intValue(), split2[i3 + 3].equals("") ? 0 : Integer.valueOf(split2[i3 + 3]).intValue()));
                    }
                    i3 += 4;
                }
            }
        } else {
            this.gm = false;
        }
        if (!(this.gy && this.gt)) {
            z = false;
        }
        this.gr = z;
    }

    private double[] m4594do(double d, double d2) {
        return new double[]{Math.sin(Math.toRadians(d2)) * d, Math.cos(Math.toRadians(d2)) * d};
    }

    private int m4595if(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (!this.gr) {
            return 0;
        }
        if (z && this.gy) {
            this.gh = 1;
            if (this.gu >= C1974b.f5424U) {
                return 1;
            }
            if (this.gu <= C1974b.aC) {
                return 4;
            }
        }
        if (z2 && this.gt) {
            this.gh = 2;
            if (this.gk <= ((double) C1974b.ab)) {
                return 1;
            }
            if (this.gk >= ((double) C1974b.aK)) {
                return 4;
            }
        }
        if (z3 && this.gt) {
            this.gh = 3;
            if (this.gn <= ((double) C1974b.ad)) {
                return 1;
            }
            if (this.gn >= ((double) C1974b.aM)) {
                return 4;
            }
        }
        if (!this.gm) {
            return 0;
        }
        int i;
        if (z4) {
            this.gh = 4;
            i = 0;
            for (C0504a c0504a : this.gq) {
                i = c0504a.m2164do() >= C1974b.f5428Y ? i + 1 : i;
            }
            if (i >= C1974b.f5425V) {
                return 1;
            }
            if (i <= C1974b.aD) {
                return 4;
            }
        }
        if (z5) {
            int i2;
            this.gh = 5;
            List arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            List arrayList3 = new ArrayList();
            for (i2 = 0; i2 < 10; i2++) {
                arrayList.add(new ArrayList());
            }
            int i3 = 0;
            for (C0504a c0504a2 : this.gq) {
                if (c0504a2.m2164do() < 10 || c0504a2.m2165if() < 1) {
                    i2 = i3;
                } else {
                    ((List) arrayList.get((c0504a2.m2164do() - 10) / 5)).add(c0504a2);
                    i2 = i3 + 1;
                }
                i3 = i2;
            }
            if (i3 < 4) {
                return 4;
            }
            for (i = 0; i < arrayList.size(); i++) {
                if (((List) arrayList.get(i)).size() != 0) {
                    Object obj = m4597if((List) arrayList.get(i));
                    if (obj != null) {
                        arrayList2.add(obj);
                        arrayList3.add(Integer.valueOf(i));
                    }
                }
            }
            if (arrayList2 == null || arrayList2.size() <= 0) {
                return 4;
            }
            double[] dArr = (double[]) arrayList2.get(0);
            dArr[0] = dArr[0] * ((double) ((Integer) arrayList3.get(0)).intValue());
            dArr[1] = dArr[1] * ((double) ((Integer) arrayList3.get(0)).intValue());
            if (arrayList2.size() > 1) {
                for (int i4 = 1; i4 < arrayList2.size(); i4++) {
                    double[] dArr2 = (double[]) arrayList2.get(i4);
                    dArr2[0] = dArr2[0] * ((double) ((Integer) arrayList3.get(i4)).intValue());
                    dArr2[1] = dArr2[1] * ((double) ((Integer) arrayList3.get(i4)).intValue());
                    dArr[0] = dArr[0] + dArr2[0];
                    dArr[1] = dArr[1] + dArr2[1];
                }
                dArr[0] = dArr[0] / ((double) arrayList2.size());
                dArr[1] = dArr[1] / ((double) arrayList2.size());
            }
            dArr = m4596if(dArr[0], dArr[1]);
            if (dArr[0] <= ((double) C1974b.as)) {
                return 1;
            }
            if (dArr[0] >= ((double) C1974b.aW)) {
                return 4;
            }
        }
        this.gh = 0;
        return 3;
    }

    private double[] m4596if(double d, double d2) {
        double d3 = 0.0d;
        if (d2 != 0.0d) {
            d3 = Math.toDegrees(Math.atan(d / d2));
        } else if (d > 0.0d) {
            d3 = 90.0d;
        } else if (d < 0.0d) {
            d3 = 270.0d;
        }
        return new double[]{Math.sqrt((d * d) + (d2 * d2)), d3};
    }

    private double[] m4597if(List list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        double[] dArr = m4594do((double) (90 - ((C0504a) list.get(0)).m2165if()), (double) ((C0504a) list.get(0)).m2163a());
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                double[] dArr2 = m4594do((double) (90 - ((C0504a) list.get(i)).m2165if()), (double) ((C0504a) list.get(i)).m2163a());
                dArr[0] = dArr[0] + dArr2[0];
                dArr[1] = dArr[1] + dArr2[1];
            }
            dArr[0] = dArr[0] / ((double) list.size());
            dArr[1] = dArr[1] / ((double) list.size());
        }
        return dArr;
    }

    private boolean m4598j(String str) {
        if (str == null || str.length() <= 8) {
            return false;
        }
        int i = 0;
        for (int i2 = 1; i2 < str.length() - 3; i2++) {
            i ^= str.charAt(i2);
        }
        return Integer.toHexString(i).equalsIgnoreCase(str.substring(str.length() + -2, str.length()));
    }

    public int bi() {
        return m4595if(true, true, true, true, true);
    }

    public boolean bj() {
        return this.gr;
    }

    public double bk() {
        return this.gk;
    }

    public String bm() {
        return this.gl;
    }

    public int bn() {
        return this.gs;
    }

    public double bo() {
        return this.gn;
    }
}
