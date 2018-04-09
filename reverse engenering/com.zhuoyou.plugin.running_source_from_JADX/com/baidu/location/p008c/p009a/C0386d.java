package com.baidu.location.p008c.p009a;

import android.support.v4.widget.AutoScrollHelper;
import com.baidu.location.BDLocation;
import com.baidu.location.p006h.C0468j;
import com.baidu.mapapi.map.WeightedLatLng;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

public final class C0386d {
    private static volatile C0386d f414q = null;
    private HashMap<String, HashMap<Integer, C0387e>> f415a;
    private HashMap<String, HashMap<Integer, Vector<Integer>>> f416b;
    private HashMap<String, ArrayList<ArrayList<Integer>>> f417c;
    private HashMap<String, ArrayList<ArrayList<Integer>>> f418d;
    private ArrayList<BDLocation> f419e;
    private ArrayList<BDLocation> f420f;
    private C0385c f421g;
    private String f422h;
    private String f423i;
    private boolean f424j;
    private boolean f425k;
    private boolean f426l;
    private boolean f427m;
    private C0384b f428n;
    private boolean f429o;
    private String f430p;

    private static class C0383a {
        private final C0383a f401a;
        private final int f402b;
        private final int f403c;

        private C0383a(C0383a c0383a, int i, int i2) {
            this.f401a = c0383a;
            this.f402b = i;
            this.f403c = i2;
        }
    }

    private static class C0384b {
        private double f404a;
        private double f405b;
        private HashMap<String, C0389g> f406c;
        private double f407d;
        private double f408e;
        private boolean f409f;
        private C0387e f410g;
        private C0387e f411h;
        private boolean f412i;

        private C0384b(double d, double d2) {
            this.f406c = null;
            this.f409f = false;
            this.f412i = false;
            this.f404a = d;
            this.f405b = d2;
        }

        private HashMap<String, C0389g> m455a() {
            return this.f406c;
        }

        private void m457a(HashMap<String, C0389g> hashMap) {
            this.f406c = hashMap;
        }
    }

    private static class C0385c {
        private final ArrayDeque<C0384b> f413a;

        private C0385c() {
            this.f413a = new ArrayDeque();
        }

        private void m472a() {
            if (this.f413a.size() > 0) {
                this.f413a.removeFirst();
            }
        }

        private void m473a(C0384b c0384b) {
            if (this.f413a.size() >= 3) {
                this.f413a.removeFirst();
            }
            this.f413a.addLast(c0384b);
        }

        private void m476b() {
            this.f413a.clear();
        }

        private boolean m479c() {
            return this.f413a.size() == 3;
        }

        private int m480d() {
            return this.f413a.size();
        }
    }

    private C0386d() {
        this.f415a = null;
        this.f416b = null;
        this.f417c = null;
        this.f418d = null;
        this.f419e = null;
        this.f420f = null;
        this.f421g = null;
        this.f422h = null;
        this.f423i = null;
        this.f424j = false;
        this.f425k = true;
        this.f426l = false;
        this.f427m = false;
        this.f428n = null;
        this.f429o = false;
        this.f430p = null;
        this.f415a = new HashMap();
        this.f416b = new HashMap();
        this.f417c = new HashMap();
        this.f418d = new HashMap();
        this.f421g = new C0385c();
    }

    private double m483a(BDLocation bDLocation, ArrayList<C0387e> arrayList) {
        C0387e c0387e = (C0387e) arrayList.get(arrayList.size() - 1);
        C0387e c0387e2 = new C0387e();
        c0387e2.f431a = bDLocation.getLatitude();
        c0387e2.f432b = bDLocation.getLongitude();
        double a = c0387e.m505a(c0387e2);
        Iterator it = this.f421g.f413a.iterator();
        double d = a;
        while (it.hasNext()) {
            d = m484a((C0384b) it.next(), (ArrayList) arrayList) + d;
        }
        return d;
    }

    private double m484a(C0384b c0384b, ArrayList<C0387e> arrayList) {
        int i = 0;
        C0387e c0387e = new C0387e();
        c0387e.f431a = c0384b.f405b;
        c0387e.f432b = c0384b.f404a;
        if (arrayList.size() < 2) {
            return Double.MAX_VALUE;
        }
        C0387e c0387e2;
        C0387e c0387e3 = (C0387e) arrayList.get(0);
        C0387e c0387e4 = (C0387e) arrayList.get(arrayList.size() - 1);
        if (!(c0387e3.f434d == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED || c0387e3.f435e == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)) {
            c0387e2 = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(c0387e3.f434d));
            c0387e3 = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(c0387e3.f435e));
            C0387e c = m494c(c0387e, c0387e2, c0387e3);
            if (m497d(c, c0387e2, c0387e3)) {
                return c0387e.m505a(c);
            }
        }
        if (!(c0387e4.f434d == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED || c0387e4.f435e == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)) {
            c0387e3 = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(c0387e4.f434d));
            c0387e4 = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(c0387e4.f435e));
            c0387e2 = m494c(c0387e, c0387e3, c0387e4);
            if (m497d(c0387e2, c0387e3, c0387e4)) {
                return c0387e.m505a(c0387e2);
            }
        }
        if (c0384b.f412i) {
            c0387e3 = new C0387e();
            c0387e3.f431a = c0384b.f408e;
            c0387e3.f432b = c0384b.f407d;
            return c0387e.m505a(c0387e3);
        }
        double d = Double.MAX_VALUE;
        while (i < arrayList.size() - 1) {
            double a;
            c0387e3 = (C0387e) arrayList.get(i);
            c0387e4 = (C0387e) arrayList.get(i + 1);
            C0387e c2 = m494c(c0387e, c0387e3, c0387e4);
            if (m497d(c2, c0387e3, c0387e4)) {
                a = c0387e.m505a(c2);
                if (a < d) {
                    i++;
                    d = a;
                }
            }
            a = d;
            i++;
            d = a;
        }
        return d;
    }

    private double m485a(C0387e c0387e, C0387e c0387e2, ArrayList<C0387e> arrayList) {
        int i = 0;
        double d = 0.0d;
        while (arrayList.size() >= 2 && i < arrayList.size() - 1) {
            d += ((C0387e) arrayList.get(i)).m505a((C0387e) arrayList.get(i + 1));
            i++;
        }
        return c0387e.m505a((C0387e) arrayList.get(arrayList.size() - 1)) + (d + c0387e.m505a((C0387e) arrayList.get(0)));
    }

    private double m486a(List<C0387e> list) {
        if (list.size() < 2) {
            return Double.MAX_VALUE;
        }
        double d = 0.0d;
        for (int i = 0; i < list.size() - 1; i++) {
            d += ((C0387e) list.get(i)).m505a((C0387e) list.get(i + 1));
        }
        return d;
    }

    public static C0386d m487a() {
        if (f414q == null) {
            synchronized (C0386d.class) {
                if (f414q == null) {
                    f414q = new C0386d();
                }
            }
        }
        return f414q;
    }

    private C0387e m488a(C0387e c0387e, C0387e c0387e2, C0387e c0387e3) {
        C0387e c0387e4 = new C0387e();
        double d = c0387e2.f431a;
        double d2 = c0387e2.f432b;
        double d3 = c0387e3.f431a;
        double d4 = c0387e3.f432b;
        double d5 = c0387e.f431a;
        double d6 = c0387e.f432b;
        d6 = Math.sqrt(((d5 - d3) * (d5 - d3)) + ((d6 - d4) * (d6 - d4)));
        if (Math.abs((d2 - d4) / (d - d3)) > 10.0d) {
            d5 = d4 + d6;
            d6 = d4 - d6;
            if (((d3 - d3) * (d3 - d)) + ((d5 - d4) * (d4 - d2)) <= 0.0d) {
                d5 = d6;
            }
            d6 = d3;
        } else {
            double d7 = (d4 - d2) / (d3 - d);
            double d8 = ((d * d4) - (d3 * d2)) / (d - d3);
            double d9 = (d7 * d7) + WeightedLatLng.DEFAULT_INTENSITY;
            double d10 = ((2.0d * d7) * (d8 - d4)) - (2.0d * d3);
            d6 = ((d3 * d3) + ((d8 - d4) * (d8 - d4))) - (d6 * d6);
            if ((d10 * d10) - ((4.0d * d9) * d6) < 0.0d) {
                c0387e4.f431a = Double.MAX_VALUE;
                c0387e4.f432b = Double.MAX_VALUE;
                return c0387e4;
            }
            double sqrt = ((-1.0d * d10) + Math.sqrt((d10 * d10) - ((4.0d * d9) * d6))) / (2.0d * d9);
            d5 = (d7 * sqrt) + d8;
            d9 = ((-1.0d * d10) - Math.sqrt((d10 * d10) - (d6 * (4.0d * d9)))) / (d9 * 2.0d);
            d6 = (d7 * d9) + d8;
            if (((d3 - d) * (sqrt - d3)) + ((d5 - d4) * (d4 - d2)) > 0.0d) {
                d6 = sqrt;
            } else {
                d5 = d6;
                d6 = d9;
            }
        }
        c0387e4.f431a = d6;
        c0387e4.f432b = d5;
        return c0387e4;
    }

    private ArrayList<C0387e> m489a(C0389g c0389g, C0389g c0389g2) {
        c0389g.f440c.f434d = c0389g.m507a();
        c0389g.f440c.f435e = c0389g.m508b();
        c0389g2.f440c.f434d = c0389g2.m507a();
        c0389g2.f440c.f435e = c0389g2.m508b();
        double d = Double.MAX_VALUE;
        ArrayList<C0387e> arrayList = new ArrayList();
        if (c0389g.m507a() == c0389g2.m507a() && c0389g.m508b() == c0389g2.m508b()) {
            arrayList.add(c0389g.f440c);
            arrayList.add(c0389g2.f440c);
            return arrayList;
        }
        Set hashSet = new HashSet();
        hashSet.add(Integer.valueOf(c0389g.m508b()));
        hashSet.add(Integer.valueOf(c0389g2.m508b()));
        List a = m490a(this.f422h, c0389g.m507a(), c0389g2.m507a(), c0389g.f440c, c0389g2.f440c, hashSet);
        double a2 = m486a(a);
        if (a2 < Double.MAX_VALUE) {
            arrayList = a;
            d = a2;
        }
        hashSet.clear();
        hashSet.add(Integer.valueOf(c0389g.m507a()));
        hashSet.add(Integer.valueOf(c0389g2.m508b()));
        a = m490a(this.f422h, c0389g.m508b(), c0389g2.m507a(), c0389g.f440c, c0389g2.f440c, hashSet);
        a2 = m486a(a);
        if (a2 < d) {
            arrayList = a;
            d = a2;
        }
        hashSet.clear();
        hashSet.add(Integer.valueOf(c0389g.m508b()));
        hashSet.add(Integer.valueOf(c0389g2.m507a()));
        a = m490a(this.f422h, c0389g.m507a(), c0389g2.m508b(), c0389g.f440c, c0389g2.f440c, hashSet);
        a2 = m486a(a);
        if (a2 < d) {
            arrayList = a;
            d = a2;
        }
        hashSet.clear();
        hashSet.add(Integer.valueOf(c0389g.m507a()));
        hashSet.add(Integer.valueOf(c0389g2.m507a()));
        a = m490a(this.f422h, c0389g.m508b(), c0389g2.m508b(), c0389g.f440c, c0389g2.f440c, hashSet);
        return m486a(a) < d ? a : arrayList;
    }

    private ArrayList<C0387e> m490a(String str, int i, int i2, C0387e c0387e, C0387e c0387e2, Set<Integer> set) {
        HashMap hashMap = (HashMap) this.f416b.get(str);
        ArrayList arrayList = new ArrayList();
        Queue linkedList = new LinkedList();
        linkedList.add(new C0383a(null, i, 0));
        while (!linkedList.isEmpty()) {
            C0383a c0383a = (C0383a) linkedList.poll();
            if (!set.contains(Integer.valueOf(c0383a.f402b)) && c0383a.f403c <= 4) {
                if (c0383a.f402b != i2) {
                    set.add(Integer.valueOf(c0383a.f402b));
                    if (c0383a.f403c < 4) {
                        Vector vector = (Vector) hashMap.get(Integer.valueOf(c0383a.f402b));
                        int i3 = 0;
                        while (vector != null && i3 < vector.size()) {
                            linkedList.offer(new C0383a(c0383a, ((Integer) vector.get(i3)).intValue(), c0383a.f403c + 1));
                            i3++;
                        }
                    }
                } else {
                    arrayList.add(c0383a);
                }
            }
        }
        ArrayList<C0387e> arrayList2 = new ArrayList();
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            ArrayList arrayList3 = new ArrayList();
            for (C0383a c0383a2 = (C0383a) arrayList.get(i4); c0383a2 != null; c0383a2 = c0383a2.f401a) {
                arrayList3.add((C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(c0383a2.f402b)));
            }
            Collection arrayList4 = new ArrayList();
            for (int size = arrayList3.size() - 1; size >= 0; size--) {
                arrayList4.add(arrayList3.get(size));
            }
            if (m485a(c0387e, c0387e2, arrayList3) < ((double) AutoScrollHelper.NO_MAX)) {
                arrayList2.clear();
                arrayList2.add(c0387e);
                arrayList2.addAll(arrayList4);
                arrayList2.add(c0387e2);
            }
        }
        return arrayList2;
    }

    private boolean m491a(String str) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(str)));
            ArrayList arrayList = null;
            ArrayList arrayList2 = null;
            HashMap hashMap = null;
            HashMap hashMap2 = null;
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    HashMap hashMap3 = (HashMap) this.f415a.get(this.f422h);
                    hashMap3 = (HashMap) this.f416b.get(this.f422h);
                    return true;
                } else if (readLine.contains("Floor")) {
                    HashMap hashMap4 = new HashMap();
                    HashMap hashMap5 = new HashMap();
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    Object obj = readLine.split(":")[1];
                    this.f415a.put(obj, hashMap4);
                    this.f416b.put(obj, hashMap5);
                    this.f417c.put(obj, arrayList3);
                    this.f418d.put(obj, arrayList4);
                    hashMap = hashMap5;
                    hashMap2 = hashMap4;
                    arrayList = arrayList4;
                    arrayList2 = arrayList3;
                } else {
                    int intValue;
                    String[] split;
                    String[] split2 = readLine.split(",");
                    if (split2[0].equals("0")) {
                        intValue = Integer.valueOf(split2[2]).intValue();
                        C0387e c0387e = new C0387e();
                        c0387e.f431a = Double.valueOf(split2[4]).doubleValue();
                        c0387e.f432b = Double.valueOf(split2[3]).doubleValue();
                        c0387e.f433c = intValue;
                        hashMap2.put(Integer.valueOf(intValue), c0387e);
                    }
                    if (split2[0].equals("1")) {
                        for (int i = 1; i < split2.length; i++) {
                            Vector vector;
                            int intValue2;
                            String[] split3 = split2[i].split("-");
                            if (hashMap.keySet().contains(Integer.valueOf(split3[0]))) {
                                vector = (Vector) hashMap.get(Integer.valueOf(split3[0]));
                                if (!vector.contains(Integer.valueOf(split3[1]))) {
                                    vector.add(Integer.valueOf(split3[1]));
                                    Collections.sort(vector);
                                }
                            } else {
                                vector = new Vector();
                                intValue2 = Integer.valueOf(split3[0]).intValue();
                                vector.add(Integer.valueOf(split3[1]));
                                hashMap.put(Integer.valueOf(intValue2), vector);
                            }
                            if (hashMap.keySet().contains(Integer.valueOf(split3[1]))) {
                                vector = (Vector) hashMap.get(Integer.valueOf(split3[1]));
                                if (!vector.contains(Integer.valueOf(split3[0]))) {
                                    vector.add(Integer.valueOf(split3[0]));
                                    Collections.sort(vector);
                                }
                            } else {
                                vector = new Vector();
                                intValue2 = Integer.valueOf(split3[1]).intValue();
                                vector.add(Integer.valueOf(split3[0]));
                                hashMap.put(Integer.valueOf(intValue2), vector);
                            }
                        }
                    }
                    if (split2[0].equals("2")) {
                        split = split2[1].split("-");
                        ArrayList arrayList5 = new ArrayList();
                        for (String valueOf : split) {
                            arrayList5.add(Integer.valueOf(valueOf));
                        }
                        arrayList2.add(arrayList5);
                    }
                    if (split2[0].equals("3")) {
                        split = split2[1].split("-");
                        ArrayList arrayList6 = new ArrayList();
                        for (String valueOf2 : split) {
                            arrayList6.add(Integer.valueOf(valueOf2));
                        }
                        arrayList.add(arrayList6);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return false;
    }

    private C0387e m492b(C0387e c0387e, C0387e c0387e2, C0387e c0387e3) {
        C0387e c0387e4 = new C0387e();
        double d = c0387e2.f431a;
        double d2 = c0387e2.f432b;
        double d3 = c0387e3.f431a;
        double d4 = c0387e3.f432b;
        double d5 = c0387e.f431a;
        double d6 = c0387e.f432b;
        d6 = Math.sqrt(((d5 - d) * (d5 - d)) + ((d6 - d2) * (d6 - d2)));
        if (Math.abs((d2 - d4) / (d - d3)) > 200.0d) {
            d5 = d2 + d6;
            d6 = d2 - d6;
            if (((d - d) * (d3 - d)) + ((d5 - d2) * (d4 - d2)) <= 0.0d) {
                d5 = d6;
            }
            d6 = d;
        } else {
            double d7 = (d4 - d2) / (d3 - d);
            double d8 = ((d * d4) - (d3 * d2)) / (d - d3);
            double d9 = (d7 * d7) + WeightedLatLng.DEFAULT_INTENSITY;
            double d10 = ((2.0d * d7) * (d8 - d2)) - (2.0d * d);
            d6 = ((d * d) + ((d8 - d2) * (d8 - d2))) - (d6 * d6);
            if ((d10 * d10) - ((4.0d * d9) * d6) < 0.0d) {
                c0387e4.f431a = Double.MAX_VALUE;
                c0387e4.f432b = Double.MAX_VALUE;
                return c0387e4;
            }
            double sqrt = ((-1.0d * d10) + Math.sqrt((d10 * d10) - ((4.0d * d9) * d6))) / (2.0d * d9);
            d5 = (d7 * sqrt) + d8;
            d9 = ((-1.0d * d10) - Math.sqrt((d10 * d10) - (d6 * (4.0d * d9)))) / (d9 * 2.0d);
            d6 = (d7 * d9) + d8;
            if (((d3 - d) * (sqrt - d)) + ((d4 - d2) * (d5 - d2)) > 0.0d) {
                d6 = sqrt;
            } else {
                d5 = d6;
                d6 = d9;
            }
        }
        c0387e4.f431a = d6;
        c0387e4.f432b = d5;
        return c0387e4;
    }

    private boolean m493b(C0384b c0384b, ArrayList<C0387e> arrayList) {
        boolean z = false;
        C0387e c0387e = new C0387e();
        c0387e.f431a = c0384b.f405b;
        c0387e.f432b = c0384b.f404a;
        if (arrayList.size() < 2) {
            return false;
        }
        C0387e c0387e2 = (C0387e) arrayList.get(0);
        C0387e c0387e3 = (C0387e) arrayList.get(arrayList.size() - 1);
        if (!(c0387e2.f434d == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED || c0387e2.f435e == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)) {
            C0387e c0387e4 = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(c0387e2.f434d));
            c0387e2 = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(c0387e2.f435e));
            if (m497d(m494c(c0387e, c0387e4, c0387e2), c0387e4, c0387e2)) {
                return true;
            }
        }
        if (!(c0387e3.f434d == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED || c0387e3.f435e == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)) {
            c0387e2 = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(c0387e3.f434d));
            c0387e3 = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(c0387e3.f435e));
            if (m497d(m494c(c0387e, c0387e2, c0387e3), c0387e2, c0387e3)) {
                return true;
            }
        }
        for (int i = 0; i < arrayList.size() - 1; i++) {
            c0387e2 = (C0387e) arrayList.get(i);
            c0387e3 = (C0387e) arrayList.get(i + 1);
            if (m497d(m494c(c0387e, c0387e2, c0387e3), c0387e2, c0387e3)) {
                z = true;
                break;
            }
        }
        return !c0384b.f412i ? z : true;
    }

    private C0387e m494c(C0387e c0387e, C0387e c0387e2, C0387e c0387e3) {
        C0387e c0387e4 = new C0387e();
        double d = c0387e.f431a;
        double d2 = c0387e.f432b;
        double d3 = c0387e2.f431a;
        double d4 = c0387e2.f432b;
        double d5 = c0387e3.f431a;
        double d6 = c0387e3.f432b;
        if (Math.abs((d4 - d6) / (d3 - d5)) > 20000.0d) {
            c0387e4.f431a = d3;
            c0387e4.f432b = d2;
        } else {
            d5 = (d4 - d6) / (d3 - d5);
            c0387e4.f431a = ((d / d5) + ((d2 - d4) + (d5 * d3))) * (d5 / ((d5 * d5) + WeightedLatLng.DEFAULT_INTENSITY));
            c0387e4.f432b = ((c0387e4.f431a - d3) * d5) + d4;
        }
        return c0387e4;
    }

    private boolean m495c(BDLocation bDLocation) {
        this.f429o = false;
        String buildingName = bDLocation.getBuildingName();
        if (!this.f425k) {
            if (!buildingName.equals(this.f430p)) {
                this.f426l = false;
                this.f427m = false;
                C0379a.m442b().m446a(buildingName);
            }
            this.f430p = buildingName;
        }
        this.f422h = bDLocation.getFloor();
        if (bDLocation.getNetworkLocationType().equals("wf")) {
            if (this.f425k) {
                this.f430p = buildingName;
                C0379a.m442b().m446a(buildingName);
            }
            this.f425k = false;
        }
        if (!this.f427m) {
            if (!this.f426l) {
                return false;
            }
            if (m491a(C0468j.m1029g() + File.separator + "indoorinfo" + File.separator + buildingName + File.separator + buildingName + ".txt")) {
                this.f427m = true;
            } else {
                this.f427m = false;
                this.f426l = false;
                C0379a.m442b().mo1746c();
                return false;
            }
        }
        if (this.f415a.get(this.f422h) == null) {
            return false;
        }
        if (bDLocation.getNetworkLocationType().equals("wf")) {
            this.f423i = this.f422h;
            this.f428n = new C0384b(bDLocation.getLongitude(), bDLocation.getLatitude());
            this.f428n.f408e = this.f428n.f405b;
            this.f428n.f407d = this.f428n.f404a;
            this.f419e = new ArrayList();
            this.f420f = new ArrayList();
            if (m502b(bDLocation)) {
                this.f421g.m476b();
                this.f428n = null;
                this.f424j = false;
                return true;
            } else if (this.f422h.equals(this.f423i)) {
                HashMap e = m499e(bDLocation);
                if (e.size() <= 0) {
                    return true;
                }
                if (this.f421g.m479c()) {
                    this.f428n.m457a(e);
                    if (!this.f429o) {
                        m496d(bDLocation);
                        this.f424j = true;
                        this.f421g.m473a(this.f428n);
                    }
                    return true;
                } else if (this.f429o) {
                    return true;
                } else {
                    double d = -1.0d;
                    C0389g c0389g = null;
                    for (Entry value : e.entrySet()) {
                        C0389g c0389g2 = (C0389g) value.getValue();
                        if (c0389g2.f442e > d) {
                            d = c0389g2.f442e;
                        } else {
                            c0389g2 = c0389g;
                        }
                        c0389g = c0389g2;
                    }
                    if (c0389g == null || c0389g.f442e <= 0.5d) {
                        this.f421g.m476b();
                        this.f424j = false;
                        return true;
                    }
                    bDLocation.setLatitude(c0389g.f440c.f431a);
                    bDLocation.setLongitude(c0389g.f440c.f432b);
                    this.f428n.f408e = c0389g.f440c.f431a;
                    this.f428n.f407d = c0389g.f440c.f432b;
                    bDLocation.setNetworkLocationType("wf2");
                    this.f424j = true;
                    this.f428n.f409f = false;
                    this.f428n.f410g = new C0387e();
                    this.f428n.f410g.f431a = c0389g.f438a.f431a;
                    this.f428n.f410g.f432b = c0389g.f438a.f432b;
                    this.f428n.f411h = new C0387e();
                    this.f428n.f411h.f431a = c0389g.f439b.f431a;
                    this.f428n.f411h.f432b = c0389g.f439b.f432b;
                    if (!this.f421g.m479c()) {
                        this.f428n.m457a(e);
                        this.f421g.m473a(this.f428n);
                    }
                    return true;
                }
            } else {
                this.f421g.m476b();
                if (this.f424j) {
                    this.f424j = false;
                }
                this.f423i = this.f422h;
                return true;
            }
        } else if (!bDLocation.getNetworkLocationType().equals("dr")) {
            return true;
        } else {
            this.f419e.add(new BDLocation(bDLocation));
            if (this.f424j) {
                m496d(bDLocation);
                this.f420f.add(bDLocation);
            }
            return true;
        }
    }

    private void m496d(BDLocation bDLocation) {
        C0387e c;
        C0387e c0387e;
        int i;
        if (bDLocation.getNetworkLocationType().equals("dr")) {
            double e = this.f428n.f407d;
            double f = this.f428n.f408e;
            double b = this.f428n.f404a;
            double a = this.f428n.f405b;
            bDLocation.setLongitude((e + bDLocation.getLongitude()) - b);
            bDLocation.setLatitude((bDLocation.getLatitude() + f) - a);
            C0387e c0387e2 = new C0387e();
            c0387e2.f431a = bDLocation.getLatitude();
            c0387e2.f432b = bDLocation.getLongitude();
            if (!(this.f428n.f410g == null || this.f428n.f411h == null)) {
                if (!this.f428n.f409f) {
                    c = m494c(c0387e2, this.f428n.f410g, this.f428n.f411h);
                } else if (this.f428n.f412i) {
                    c = m492b(c0387e2, this.f428n.f410g, this.f428n.f411h);
                    if (c.f431a == Double.MAX_VALUE && c.f432b == Double.MAX_VALUE) {
                        c = m494c(c0387e2, this.f428n.f410g, this.f428n.f411h);
                        bDLocation.setLongitude(c.f432b);
                        bDLocation.setLatitude(c.f431a);
                        bDLocation.setNetworkLocationType("dr2");
                        return;
                    }
                    bDLocation.setLongitude(c.f432b);
                    bDLocation.setLatitude(c.f431a);
                    bDLocation.setNetworkLocationType("dr2");
                    return;
                } else {
                    C0387e a2 = m488a(c0387e2, this.f428n.f410g, this.f428n.f411h);
                    if (a2.f431a == Double.MAX_VALUE && a2.f432b == Double.MAX_VALUE) {
                        c = m494c(c0387e2, this.f428n.f410g, this.f428n.f411h);
                        bDLocation.setLongitude(c.f432b);
                        bDLocation.setLatitude(c.f431a);
                        bDLocation.setNetworkLocationType("dr2");
                        return;
                    }
                    if (this.f428n.f411h.f434d != ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
                        int i2 = this.f428n.f411h.f434d;
                        c = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(i2));
                        c0387e = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(this.f428n.f411h.f435e));
                        if (!m497d(a2, c, c0387e)) {
                            C0387e c0387e3;
                            C0387e c0387e4 = new C0387e();
                            i = 0;
                            if (this.f428n.f410g.f433c == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
                                c0387e3 = new C0387e();
                                c0387e3.f431a = this.f428n.f410g.f431a;
                                c0387e3.f432b = this.f428n.f410g.f432b;
                                C0387e c0387e5 = new C0387e();
                                c0387e5.f431a = this.f428n.f411h.f431a;
                                c0387e5.f432b = this.f428n.f411h.f432b;
                                if (c.m505a(c0387e3) > c.m505a(c0387e5)) {
                                    c0387e4.f433c = c.f433c;
                                    c0387e4.f431a = c.f431a;
                                    c0387e4.f432b = c.f432b;
                                    i = c0387e.f433c;
                                }
                                if (c.m505a(c0387e3) < c.m505a(c0387e5)) {
                                    c0387e4.f433c = c0387e.f433c;
                                    c0387e4.f431a = c0387e.f431a;
                                    c0387e4.f432b = c0387e.f432b;
                                    i = c.f433c;
                                }
                                if (c.m505a(c0387e3) == c.m505a(c0387e5)) {
                                    bDLocation.setLongitude(a2.f432b);
                                    bDLocation.setLatitude(a2.f431a);
                                    bDLocation.setNetworkLocationType("dr2");
                                    return;
                                }
                            } else if (c.f433c == this.f428n.f410g.f433c) {
                                c0387e4.f431a = c0387e.f431a;
                                c0387e4.f432b = c0387e.f432b;
                                c0387e4.f433c = c0387e.f433c;
                                i = c.f433c;
                            } else {
                                c0387e4.f431a = c.f431a;
                                c0387e4.f432b = c.f432b;
                                c0387e4.f433c = c.f433c;
                                i = c0387e.f433c;
                            }
                            Vector vector = (Vector) ((HashMap) this.f416b.get(this.f422h)).get(Integer.valueOf(c0387e4.f433c));
                            c0387e3 = new C0387e();
                            if (vector.size() == 2) {
                                if (((Integer) vector.get(0)).intValue() != i) {
                                    c0387e3.f431a = ((C0387e) ((HashMap) this.f415a.get(this.f422h)).get(vector.get(0))).f431a;
                                    c0387e3.f432b = ((C0387e) ((HashMap) this.f415a.get(this.f422h)).get(vector.get(0))).f432b;
                                } else {
                                    c0387e3.f431a = ((C0387e) ((HashMap) this.f415a.get(this.f422h)).get(vector.get(1))).f431a;
                                    c0387e3.f432b = ((C0387e) ((HashMap) this.f415a.get(this.f422h)).get(vector.get(1))).f432b;
                                }
                                c = m492b(c0387e2, c0387e4, c0387e3);
                                if (c.f431a == Double.MAX_VALUE && c.f432b == Double.MAX_VALUE) {
                                    c = m494c(c0387e2, this.f428n.f410g, this.f428n.f411h);
                                    bDLocation.setLongitude(c.f432b);
                                    bDLocation.setLatitude(c.f431a);
                                    bDLocation.setNetworkLocationType("dr2");
                                    return;
                                }
                            }
                        }
                    }
                    c = a2;
                }
                bDLocation.setLongitude(c.f432b);
                bDLocation.setLatitude(c.f431a);
            }
            bDLocation.setNetworkLocationType("dr2");
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (bDLocation.getNetworkLocationType().equals("wf")) {
            C0384b c0384b = (C0384b) this.f421g.f413a.getFirst();
            HashMap i3 = this.f428n.m455a();
            HashMap i4 = c0384b.m455a();
            for (Entry value : i3.entrySet()) {
                C0389g c0389g = (C0389g) value.getValue();
                for (Entry value2 : i4.entrySet()) {
                    ArrayList a3 = m489a((C0389g) value2.getValue(), c0389g);
                    if (a3 != null && a3.size() > 0) {
                        arrayList.add(a3);
                    }
                }
            }
            if (arrayList.size() == 0) {
                this.f428n.f408e = bDLocation.getLatitude();
                this.f428n.f407d = bDLocation.getLongitude();
                bDLocation.setNetworkLocationType("wf2");
                return;
            }
            ArrayList arrayList2;
            int i5;
            for (i = 0; i < arrayList.size(); i++) {
                arrayList2 = (ArrayList) arrayList.get(i);
                Iterator it = this.f421g.f413a.iterator();
                i5 = 0;
                int i6 = 0;
                while (it.hasNext()) {
                    C0384b c0384b2 = (C0384b) it.next();
                    if (i6 != 0) {
                        if (!m493b(c0384b2, arrayList2)) {
                            break;
                        }
                        i5++;
                    } else {
                        i6++;
                    }
                }
                if (i5 < this.f421g.m480d() - 1) {
                    arrayList.remove(i);
                }
            }
            if (arrayList.size() == 0) {
                this.f428n.f408e = bDLocation.getLatitude();
                this.f428n.f407d = bDLocation.getLongitude();
                bDLocation.setNetworkLocationType("wf2");
                return;
            }
            c = new C0387e();
            if (arrayList.size() == 1) {
                c = (C0387e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1);
                bDLocation.setLatitude(c.f431a);
                bDLocation.setLongitude(c.f432b);
                this.f428n.f408e = c.f431a;
                this.f428n.f407d = c.f432b;
                bDLocation.setNetworkLocationType("wf2");
                this.f428n.f409f = true;
                this.f428n.f410g = new C0387e();
                this.f428n.f410g.f431a = ((C0387e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 2)).f431a;
                this.f428n.f410g.f432b = ((C0387e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 2)).f432b;
                this.f428n.f410g.f433c = ((C0387e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 2)).f433c;
                this.f428n.f411h = new C0387e();
                this.f428n.f411h.f431a = ((C0387e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1)).f431a;
                this.f428n.f411h.f432b = ((C0387e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1)).f432b;
                this.f428n.f411h.f434d = ((C0387e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1)).f434d;
                this.f428n.f411h.f435e = ((C0387e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1)).f435e;
                this.f428n.f411h.f433c = ((C0387e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1)).f433c;
                return;
            }
            i5 = 0;
            if (arrayList.size() >= 2) {
                ArrayList arrayList3 = new ArrayList();
                for (i2 = 0; i2 < arrayList.size(); i2++) {
                    arrayList3.add(Double.valueOf(m483a(bDLocation, (ArrayList) arrayList.get(i2))));
                }
                f = Double.MAX_VALUE;
                for (i2 = 0; i2 < arrayList3.size(); i2++) {
                    if (((Double) arrayList3.get(i2)).doubleValue() < f) {
                        f = ((Double) arrayList3.get(i2)).doubleValue();
                        i5 = i2;
                    }
                }
                arrayList2 = (ArrayList) arrayList.get(i5);
                c0387e = (C0387e) arrayList2.get(arrayList2.size() - 1);
                bDLocation.setLatitude(c0387e.f431a);
                bDLocation.setLongitude(c0387e.f432b);
                this.f428n.f408e = c0387e.f431a;
                this.f428n.f407d = c0387e.f432b;
                this.f428n.f409f = true;
                this.f428n.f410g = new C0387e();
                this.f428n.f410g.f431a = ((C0387e) arrayList2.get(arrayList2.size() - 2)).f431a;
                this.f428n.f410g.f432b = ((C0387e) arrayList2.get(arrayList2.size() - 2)).f432b;
                this.f428n.f410g.f433c = ((C0387e) arrayList2.get(arrayList2.size() - 2)).f433c;
                this.f428n.f411h = new C0387e();
                this.f428n.f411h.f431a = ((C0387e) arrayList2.get(arrayList2.size() - 1)).f431a;
                this.f428n.f411h.f432b = ((C0387e) arrayList2.get(arrayList2.size() - 1)).f432b;
                this.f428n.f411h.f434d = ((C0387e) arrayList2.get(arrayList2.size() - 1)).f434d;
                this.f428n.f411h.f435e = ((C0387e) arrayList2.get(arrayList2.size() - 1)).f435e;
                this.f428n.f411h.f433c = ((C0387e) arrayList2.get(arrayList2.size() - 1)).f433c;
                bDLocation.setNetworkLocationType("wf2");
            }
        }
    }

    private boolean m497d(C0387e c0387e, C0387e c0387e2, C0387e c0387e3) {
        double d = c0387e.f431a;
        double d2 = c0387e.f432b;
        double d3 = c0387e2.f431a;
        return ((c0387e3.f431a - d) * (d3 - d)) + ((c0387e3.f432b - d2) * (c0387e2.f432b - d2)) <= 0.0d;
    }

    private double m498e(C0387e c0387e, C0387e c0387e2, C0387e c0387e3) {
        C0387e c0387e4 = new C0387e();
        C0387e c0387e5 = new C0387e();
        c0387e4.f431a = c0387e2.f431a - c0387e.f431a;
        c0387e4.f432b = c0387e2.f432b - c0387e.f432b;
        c0387e5.f431a = c0387e3.f431a - c0387e.f431a;
        c0387e5.f432b = c0387e3.f432b - c0387e.f432b;
        return Math.acos(((c0387e4.f431a * c0387e5.f431a) + (c0387e4.f432b * c0387e5.f432b)) / (c0387e5.m504a() * c0387e4.m504a()));
    }

    private HashMap<String, C0389g> m499e(BDLocation bDLocation) {
        C0387e c0387e;
        Vector vector;
        int intValue;
        C0387e c0387e2;
        int i;
        HashMap<String, C0389g> hashMap = new HashMap();
        double latitude = bDLocation.getLatitude();
        double longitude = bDLocation.getLongitude();
        C0387e c0387e3 = new C0387e();
        c0387e3.f431a = latitude;
        c0387e3.f432b = longitude;
        HashMap hashMap2 = (HashMap) this.f416b.get(this.f422h);
        if (hashMap2 != null) {
            HashMap hashMap3 = new HashMap();
            for (Entry entry : hashMap2.entrySet()) {
                int intValue2 = ((Integer) entry.getKey()).intValue();
                c0387e = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(intValue2));
                vector = (Vector) entry.getValue();
                for (int i2 = 0; i2 < vector.size(); i2++) {
                    intValue = ((Integer) vector.get(i2)).intValue();
                    String str = String.valueOf(intValue2 > intValue ? intValue : intValue2) + "_" + String.valueOf(intValue2 < intValue ? intValue : intValue2);
                    if (!hashMap3.containsKey(str)) {
                        hashMap3.put(str, Integer.valueOf(1));
                        c0387e2 = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(intValue));
                        C0387e c = m494c(c0387e3, c0387e, c0387e2);
                        if (m497d(c, c0387e, c0387e2)) {
                            C0389g c0389g = new C0389g();
                            c0389g.f440c = c;
                            c0389g.f441d = c0387e3.m505a(c);
                            c0389g.f438a = c0387e;
                            c0389g.f439b = c0387e2;
                            if (c0389g.f441d < 2.0E-4d) {
                                hashMap.put(str, c0389g);
                            }
                        }
                    }
                }
            }
        }
        if (hashMap.size() > 0) {
            longitude = 0.0d;
            for (Entry entry2 : hashMap.entrySet()) {
                longitude = (WeightedLatLng.DEFAULT_INTENSITY / (((C0389g) entry2.getValue()).f441d + 1.0E-6d)) + longitude;
            }
            ArrayList arrayList = new ArrayList();
            for (Entry entry22 : hashMap.entrySet()) {
                C0389g c0389g2 = (C0389g) entry22.getValue();
                String str2 = (String) entry22.getKey();
                if (hashMap.size() == 1) {
                    c0389g2.f442e = WeightedLatLng.DEFAULT_INTENSITY;
                } else {
                    c0389g2.f442e = (WeightedLatLng.DEFAULT_INTENSITY / (1.0E-6d + c0389g2.f441d)) / longitude;
                }
                if (c0389g2.f442e < 0.1d) {
                    arrayList.add(str2);
                }
            }
            for (i = 0; i < arrayList.size(); i++) {
                hashMap.remove((String) arrayList.get(i));
            }
        }
        if (hashMap.size() >= 0) {
            C0387e c0387e4 = null;
            double d = 999999.0d;
            int i3 = 0;
            for (Entry entry222 : ((HashMap) this.f415a.get(this.f422h)).entrySet()) {
                i = ((Integer) entry222.getKey()).intValue();
                C0387e c0387e5 = (C0387e) entry222.getValue();
                if (Math.abs(c0387e5.f431a - c0387e3.f431a) <= 5.0E-4d && Math.abs(c0387e5.f432b - c0387e3.f432b) <= 5.0E-4d) {
                    int i4;
                    double a = c0387e5.m505a(c0387e3);
                    if (d > a) {
                        longitude = a;
                        int i5 = i;
                        c0387e = c0387e5;
                        i4 = i5;
                    } else {
                        i4 = i3;
                        c0387e = c0387e4;
                        longitude = d;
                    }
                    d = longitude;
                    i3 = i4;
                    c0387e4 = c0387e;
                }
            }
            Object obj = 1;
            for (Entry entry2222 : hashMap.entrySet()) {
                obj = ((C0389g) entry2222.getValue()).f441d <= d ? null : obj;
            }
            if (obj == null) {
                return hashMap;
            }
            hashMap.clear();
            vector = (Vector) ((HashMap) this.f416b.get(this.f422h)).get(Integer.valueOf(i3));
            if (vector == null) {
                return hashMap;
            }
            i = ((Integer) vector.get(0)).intValue();
            C0389g c0389g3 = new C0389g();
            c0389g3.f440c = c0387e4;
            c0389g3.f441d = 0.0d;
            c0389g3.f438a = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(i3));
            c0389g3.f439b = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(i));
            hashMap.put(String.valueOf(i3) + "_" + String.valueOf(i), c0389g3);
            this.f428n.f405b = bDLocation.getLatitude();
            this.f428n.f404a = bDLocation.getLongitude();
            bDLocation.setLatitude(c0387e4.f431a);
            bDLocation.setLongitude(c0387e4.f432b);
            bDLocation.setNetworkLocationType("wf2");
            this.f429o = true;
            this.f428n.f410g = new C0387e();
            this.f428n.f410g.f431a = c0389g3.f439b.f431a;
            this.f428n.f410g.f432b = c0389g3.f439b.f432b;
            this.f428n.f411h = new C0387e();
            this.f428n.f411h.f431a = c0387e4.f431a;
            this.f428n.f411h.f432b = c0387e4.f432b;
            this.f428n.f408e = c0387e4.f431a;
            this.f428n.f407d = c0387e4.f432b;
            this.f428n.m457a((HashMap) hashMap);
            this.f428n.f412i = true;
            this.f428n.f409f = false;
            if (this.f421g.m479c()) {
                C0384b c0384b = (C0384b) this.f421g.f413a.getLast();
                if (c0384b.f411h != null && (c0384b.f411h.f434d == i3 || c0384b.f411h.f435e == i3)) {
                    c0389g3.f438a = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(i3));
                    this.f428n.f409f = true;
                    intValue = c0384b.f411h.f435e == i3 ? c0384b.f411h.f434d : c0384b.f411h.f434d == i3 ? c0384b.f411h.f435e : 0;
                    c0389g3.f439b = (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(Integer.valueOf(intValue));
                    vector = (Vector) ((HashMap) this.f416b.get(this.f422h)).get(Integer.valueOf(i3));
                    c0387e2 = new C0387e();
                    if (vector.size() == 2) {
                        if (((Integer) vector.get(0)).intValue() != intValue) {
                            c0387e2.f431a = ((C0387e) ((HashMap) this.f415a.get(this.f422h)).get(vector.get(0))).f431a;
                            c0387e2.f432b = ((C0387e) ((HashMap) this.f415a.get(this.f422h)).get(vector.get(0))).f432b;
                        } else {
                            c0387e2.f431a = ((C0387e) ((HashMap) this.f415a.get(this.f422h)).get(vector.get(1))).f431a;
                            c0387e2.f432b = ((C0387e) ((HashMap) this.f415a.get(this.f422h)).get(vector.get(1))).f432b;
                        }
                    }
                    this.f428n.f411h = c0387e2;
                    this.f428n.f410g = c0387e4;
                    this.f428n.f411h.f434d = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                    this.f428n.f412i = true;
                }
                this.f421g.m472a();
                this.f421g.m473a(this.f428n);
            } else {
                this.f421g.m473a(this.f428n);
            }
        }
        return hashMap;
    }

    public boolean m500a(BDLocation bDLocation) {
        try {
            boolean c = m495c(bDLocation);
            if (!c) {
                return c;
            }
            if (bDLocation.getNetworkLocationType().equals("wf")) {
                bDLocation.setNetworkLocationType("wf2");
            }
            if (!bDLocation.getNetworkLocationType().equals("dr")) {
                return c;
            }
            bDLocation.setNetworkLocationType("dr2");
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    void m501b() {
        this.f426l = true;
    }

    boolean m502b(BDLocation bDLocation) {
        boolean z = false;
        ArrayList arrayList = (ArrayList) this.f417c.get(this.f422h);
        ArrayList arrayList2 = (ArrayList) this.f418d.get(this.f422h);
        C0387e c0387e = new C0387e();
        c0387e.f431a = bDLocation.getLatitude();
        c0387e.f432b = bDLocation.getLongitude();
        int i = 0;
        while (arrayList != null && i < arrayList.size()) {
            ArrayList arrayList3 = (ArrayList) arrayList.get(i);
            double d = 0.0d;
            for (int i2 = 0; i2 < arrayList3.size() - 1; i2++) {
                d += m498e(c0387e, (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(arrayList3.get(i2)), (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(arrayList3.get(i2 + 1)));
            }
            if (Math.abs((m498e(c0387e, (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(arrayList3.get(0)), (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(arrayList3.get(arrayList3.size() - 1))) + d) - 360.0d) < 0.1d) {
                z = true;
                break;
            }
            i++;
        }
        if (!z) {
            i = 0;
            while (arrayList2 != null && i < arrayList2.size()) {
                arrayList = (ArrayList) arrayList2.get(i);
                d = 0.0d;
                for (int i3 = 0; i3 < arrayList.size() - 1; i3++) {
                    d += m498e(c0387e, (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(arrayList.get(i3)), (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(arrayList.get(i3 + 1)));
                }
                if (Math.abs((m498e(c0387e, (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(arrayList.get(0)), (C0387e) ((HashMap) this.f415a.get(this.f422h)).get(arrayList.get(arrayList.size() - 1))) + d) - 360.0d) < 0.1d) {
                    return true;
                }
                i++;
            }
        }
        return z;
    }
}
