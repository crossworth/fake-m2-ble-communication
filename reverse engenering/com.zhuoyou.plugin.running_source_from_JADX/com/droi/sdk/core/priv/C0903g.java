package com.droi.sdk.core.priv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class C0903g<K, V> {
    private LinkedList<Entry<K, V>> f2930a = new LinkedList();
    private HashMap<K, LinkedList<V>> f2931b = new HashMap();

    class C0902a<K, V> implements Entry<K, V> {
        final /* synthetic */ C0903g f2927a;
        private final K f2928b;
        private V f2929c;

        public C0902a(C0903g c0903g, K k, V v) {
            this.f2927a = c0903g;
            this.f2928b = k;
            this.f2929c = v;
        }

        public K getKey() {
            return this.f2928b;
        }

        public V getValue() {
            return this.f2929c;
        }

        public V setValue(V v) {
            V v2 = this.f2929c;
            this.f2929c = v;
            return v2;
        }
    }

    protected C0903g() {
    }

    public static <K, V> C0903g<K, V> m2668a() {
        return new C0903g();
    }

    public static <K, V> C0903g<K, V> m2669a(C0903g<K, V> c0903g) {
        C0903g<K, V> a = C0903g.m2668a();
        a.f2930a = new LinkedList(c0903g.f2930a);
        a.f2931b = new HashMap(c0903g.f2931b);
        return a;
    }

    public void m2670a(K k) {
        this.f2931b.remove(k);
        Collection arrayList = new ArrayList();
        Iterator it = this.f2930a.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (entry.getKey().equals(k)) {
                arrayList.add(entry);
            }
        }
        this.f2930a.removeAll(arrayList);
    }

    public void m2671a(K k, V v) {
        C0902a c0902a = new C0902a(this, k, v);
        LinkedList linkedList = (LinkedList) this.f2931b.get(k);
        if (linkedList == null) {
            linkedList = new LinkedList();
            this.f2931b.put(k, linkedList);
        }
        linkedList.add(v);
        this.f2930a.add(c0902a);
    }

    public void m2672b() {
        this.f2931b.clear();
        this.f2930a.clear();
    }

    public boolean m2673b(K k) {
        return this.f2931b.containsKey(k);
    }

    public List<Entry<K, V>> m2674c() {
        return this.f2930a;
    }

    public List<V> m2675c(K k) {
        return (List) this.f2931b.get(k);
    }
}
