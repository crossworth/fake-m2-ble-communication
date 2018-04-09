package com.droi.sdk.core;

import com.droi.sdk.core.DroiQuery.Builder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DroiCondition {
    String f2454a = Builder.f2646k;
    ArrayList f2455b = new ArrayList();

    public static class Type {
        public static final String CONTAINS = "CONTAINS";
        public static final String ENDSWITH = "ENDSWITH";
        public static final String EQ = "EQ";
        public static final String GT = "GT";
        public static final String GT_OR_EQ = "GT_OR_EQ";
        public static final String IN = "IN";
        public static final String ISNOTNULL = "ISNOTNULL";
        public static final String ISNULL = "ISNULL";
        public static final String LT = "LT";
        public static final String LT_OR_EQ = "LT_OR_EQ";
        public static final String NEQ = "NEQ";
        public static final String NOTCONTAINS = "NOTCONTAINS";
        public static final String NOTENDSWITH = "NOTENDSWITH";
        public static final String NOTIN = "NIN";
        public static final String NOTSTARTSWITH = "NOTSTARTSWITH";
        public static final String STARTSWITH = "STARTSWITH";
    }

    protected DroiCondition() {
    }

    private DroiCondition m2466a(String str, DroiCondition droiCondition) {
        Iterator it;
        DroiCondition droiCondition2 = new DroiCondition();
        droiCondition2.f2454a = str;
        if (this.f2454a.equals(str)) {
            it = this.f2455b.iterator();
            while (it.hasNext()) {
                droiCondition2.f2455b.add(it.next());
            }
        } else {
            droiCondition2.f2455b.add(this);
        }
        if (droiCondition.f2454a.equals(str)) {
            it = droiCondition.f2455b.iterator();
            while (it.hasNext()) {
                droiCondition2.f2455b.add(it.next());
            }
        } else {
            droiCondition2.f2455b.add(droiCondition);
        }
        return droiCondition2;
    }

    public static DroiCondition cond(String str, String str2, Object obj) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(str2);
        if (obj != null) {
            arrayList.add(obj);
        }
        DroiCondition droiCondition = new DroiCondition();
        droiCondition.f2455b.add(arrayList);
        return droiCondition;
    }

    public static DroiCondition contains(String str, Object obj) {
        return cond(str, Type.CONTAINS, obj);
    }

    public static DroiCondition endsWith(String str, String str2) {
        return cond(str, Type.ENDSWITH, str2);
    }

    public static DroiCondition eq(String str, Object obj) {
        return cond(str, Type.EQ, obj);
    }

    public static DroiCondition gt(String str, Object obj) {
        return cond(str, Type.GT, obj);
    }

    public static DroiCondition gtOrEq(String str, Object obj) {
        return cond(str, Type.GT_OR_EQ, obj);
    }

    public static DroiCondition isNotNull(String str) {
        return cond(str, Type.ISNOTNULL, null);
    }

    public static DroiCondition isNull(String str) {
        return cond(str, Type.ISNULL, null);
    }

    public static DroiCondition lt(String str, Object obj) {
        return cond(str, Type.LT, obj);
    }

    public static DroiCondition ltOrEq(String str, Object obj) {
        return cond(str, Type.LT_OR_EQ, obj);
    }

    public static DroiCondition neq(String str, Object obj) {
        return cond(str, Type.NEQ, obj);
    }

    public static DroiCondition notContains(String str, Object obj) {
        return cond(str, Type.NOTCONTAINS, obj);
    }

    public static DroiCondition notEndsWith(String str, String str2) {
        return cond(str, Type.NOTENDSWITH, str2);
    }

    public static DroiCondition notSelectIn(String str, List list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(Type.NOTIN);
        arrayList.add(list);
        DroiCondition droiCondition = new DroiCondition();
        droiCondition.f2455b.add(arrayList);
        return droiCondition;
    }

    public static DroiCondition notStartsWith(String str, String str2) {
        return cond(str, Type.NOTSTARTSWITH, str2);
    }

    public static DroiCondition selectIn(String str, List list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(Type.IN);
        arrayList.add(list);
        DroiCondition droiCondition = new DroiCondition();
        droiCondition.f2455b.add(arrayList);
        return droiCondition;
    }

    public static DroiCondition startsWith(String str, String str2) {
        return cond(str, Type.STARTSWITH, str2);
    }

    public DroiCondition and(DroiCondition droiCondition) {
        return m2466a(Builder.f2649n, droiCondition);
    }

    public DroiCondition or(DroiCondition droiCondition) {
        return m2466a(Builder.f2648m, droiCondition);
    }
}
