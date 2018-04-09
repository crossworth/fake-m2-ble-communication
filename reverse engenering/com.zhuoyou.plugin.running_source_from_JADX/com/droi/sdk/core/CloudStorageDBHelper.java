package com.droi.sdk.core;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiQuery.Builder;
import com.droi.sdk.core.priv.C0782a;
import com.droi.sdk.core.priv.C0895b;
import com.droi.sdk.core.priv.C0899e;
import com.droi.sdk.core.priv.C0903g;
import com.droi.sdk.core.priv.C0911l;
import com.droi.sdk.core.priv.C0939m;
import com.droi.sdk.internal.DroiLog;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudStorageDBHelper extends C0782a {
    public static final int f2373a = 1040006;
    private static final String f2374b = "CloudStore";
    private static CloudStorageDBHelper f2375c = null;
    private static final int f2376d = 1000;
    private static final String f2377e = "_Id";
    private static final String f2378f = "_Value";
    private static final String f2379g = "Token";

    public static class RestfulObject extends DroiObject {
        @DroiExpose
        public int Code;
        @DroiExpose
        public String Ticket;
    }

    private DroiError m2429a(DroiObject droiObject) {
        DroiError c;
        Iterator it = C0911l.m2699a(droiObject.getClass()).iterator();
        while (it.hasNext()) {
            Object obj;
            DroiError c2;
            Field field = (Field) it.next();
            field.setAccessible(true);
            try {
                field.getType();
                DroiReference droiReference = (DroiReference) field.getAnnotation(DroiReference.class);
                obj = field.get(droiObject);
                if (obj != null && (droiReference != null || (obj instanceof DroiReferenceObject) || (obj instanceof DroiFile) || (obj instanceof List) || obj.getClass().isArray())) {
                    if ((obj instanceof DroiObject) || (obj instanceof DroiReferenceObject)) {
                        c = m2439c(obj);
                        if (!c.isOk()) {
                            return c;
                        }
                    } else if (obj instanceof List) {
                        for (Object obj2 : (List) obj2) {
                            if (obj2 instanceof DroiReferenceObject) {
                                c = m2439c(obj2);
                                if (!c.isOk()) {
                                    return c;
                                }
                            }
                        }
                        continue;
                    } else if (obj2.getClass().isArray()) {
                        for (Object obj3 : (Object[]) obj2) {
                            if (obj3 instanceof DroiReferenceObject) {
                                c2 = m2439c(obj3);
                                if (!c2.isOk()) {
                                    return c2;
                                }
                            }
                        }
                        continue;
                    } else {
                        continue;
                    }
                }
            } catch (IllegalAccessException e) {
            }
        }
        ArrayList keys = droiObject.getKeys();
        if (keys != null) {
            it = keys.iterator();
            while (it.hasNext()) {
                obj2 = droiObject.get((String) it.next());
                if ((obj2 instanceof DroiReferenceObject) || (obj2 instanceof DroiFile)) {
                    c = m2439c(obj2);
                    if (!c.isOk()) {
                        return c;
                    }
                } else if (obj2 instanceof List) {
                    for (Object obj22 : (List) obj22) {
                        if ((obj22 instanceof DroiReferenceObject) || (obj22 instanceof DroiFile)) {
                            c = m2439c(obj22);
                            if (!c.isOk()) {
                                return c;
                            }
                        }
                    }
                    continue;
                } else if (obj22.getClass().isArray()) {
                    for (Object obj32 : (Object[]) obj22) {
                        if ((obj32 instanceof DroiReferenceObject) || (obj32 instanceof DroiFile)) {
                            c2 = m2439c(obj32);
                            if (!c2.isOk()) {
                                return c2;
                            }
                        }
                    }
                    continue;
                } else {
                    continue;
                }
            }
        }
        return new DroiError();
    }

    private DroiError m2430a(DroiObject droiObject, String str) {
        DroiError droiError = new DroiError();
        DroiUser currentUser = DroiUser.getCurrentUser();
        if (!(currentUser == null || !DroiUser.isAutoAnonymousUserEnabled() || currentUser.isAuthorized())) {
            currentUser = DroiUser.loginWithAnonymous(droiError);
            if (!droiError.isOk()) {
                return droiError;
            }
        }
        try {
            Collection arrayList = new ArrayList();
            arrayList.add(new JSONObject().put("delete", str));
            if (droiObject != null) {
                arrayList.add(new JSONObject().put("_Id", droiObject.getObjectId()));
            }
            if (currentUser != null) {
                arrayList.add(new JSONObject().put(f2379g, currentUser.getSessionToken()));
            }
            String b = C0939m.m2772b(m2434a("delete"), new JSONArray(arrayList).toString(), droiError);
            if (!droiError.isOk()) {
                return droiError;
            }
            RestfulObject restfulObject = (RestfulObject) DroiObject.fromJson(new JSONObject(b), RestfulObject.class);
            droiError.setCode(restfulObject.Code);
            if (restfulObject.Code != 0) {
                if (restfulObject.Code == f2373a) {
                    DroiUserHelper.cleanUp();
                }
                droiError.setAppendedMessage("Ticket: " + restfulObject.Ticket);
            }
            return droiError;
        } catch (Exception e) {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage(e.toString());
        }
    }

    private DroiError m2431a(DroiObject droiObject, String str, String str2) {
        DroiError droiError = new DroiError();
        try {
            Collection arrayList = new ArrayList();
            arrayList.add(new JSONObject().put(str2, str));
            arrayList.add(new JSONObject().put("_Id", droiObject.getObjectId()));
            arrayList.add(new JSONObject().put(f2378f, droiObject.toJson(droiError)));
            if (!droiError.isOk()) {
                return droiError;
            }
            DroiUser currentUser = DroiUser.getCurrentUser();
            if (!droiError.isOk()) {
                return droiError;
            }
            String sessionToken = currentUser.getSessionToken();
            if (!(currentUser == null || sessionToken == null || sessionToken.isEmpty())) {
                arrayList.add(new JSONObject().put(f2379g, sessionToken));
            }
            String b = C0939m.m2772b(m2434a(str2), new JSONArray(arrayList).toString(), droiError);
            if (!droiError.isOk()) {
                return droiError;
            }
            RestfulObject restfulObject = (RestfulObject) DroiObject.fromJson(new JSONObject(b), RestfulObject.class);
            droiError.setCode(restfulObject.Code);
            if (restfulObject.Code != 0) {
                if (restfulObject.Code == f2373a) {
                    DroiUserHelper.cleanUp();
                }
                droiError.setAppendedMessage("Ticket: " + restfulObject.Ticket);
            }
            return droiError;
        } catch (Exception e) {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage(e.toString());
        }
    }

    public static synchronized CloudStorageDBHelper m2432a() {
        CloudStorageDBHelper cloudStorageDBHelper;
        synchronized (CloudStorageDBHelper.class) {
            if (f2375c == null) {
                f2375c = new CloudStorageDBHelper();
            }
            cloudStorageDBHelper = f2375c;
        }
        return cloudStorageDBHelper;
    }

    private Object m2433a(String str, Object obj) {
        Object obj2 = null;
        Class cls = obj.getClass();
        if (cls == DroiObject.class) {
            return ((DroiObject) obj).get(str);
        }
        if (!(obj instanceof DroiObject)) {
            return obj2;
        }
        Field b = C0911l.m2705b(cls, str);
        if (b == null) {
            return ((DroiObject) obj).get(str);
        }
        b.setAccessible(true);
        try {
            return b.get(obj);
        } catch (IllegalAccessException e) {
            return obj2;
        }
    }

    private String m2434a(String str) {
        Object obj = -1;
        switch (str.hashCode()) {
            case -1335458389:
                if (str.equals("delete")) {
                    obj = 3;
                    break;
                }
                break;
            case -1183792455:
                if (str.equals(Builder.f2640e)) {
                    obj = null;
                    break;
                }
                break;
            case -906021636:
                if (str.equals(Builder.f2639d)) {
                    obj = 1;
                    break;
                }
                break;
            case -838846263:
                if (str.equals("update")) {
                    obj = 2;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
            case 1:
            case 2:
            case 3:
                return C0899e.f2897b;
            default:
                return null;
        }
    }

    private ArrayList m2435a(ArrayList arrayList) {
        if (arrayList.size() > 2 && (arrayList.get(2) instanceof Date)) {
            arrayList.set(2, C0895b.m2661a((Date) arrayList.get(2)));
        }
        return arrayList;
    }

    private JSONObject m2436a(DroiCondition droiCondition) {
        JSONObject jSONObject = new JSONObject();
        try {
            String a = C0871a.m2608a(droiCondition);
            ArrayList b = C0871a.m2609b(droiCondition);
            if (a.equals(Builder.f2646k)) {
                jSONObject.put(a, new JSONArray(m2435a((ArrayList) b.get(0))));
            } else {
                JSONArray jSONArray = new JSONArray();
                Iterator it = b.iterator();
                while (it.hasNext()) {
                    Object next = it.next();
                    if (next instanceof ArrayList) {
                        jSONArray.put(new JSONArray((ArrayList) next));
                    } else {
                        jSONArray.put(m2436a((DroiCondition) next));
                    }
                }
                jSONObject.put(a, jSONArray);
            }
        } catch (Exception e) {
            DroiLog.m2873w(f2374b, e);
        }
        return jSONObject;
    }

    private boolean m2437a(String str, Object obj, Object obj2) {
        Class cls = obj.getClass();
        if (cls == DroiObject.class) {
            ((DroiObject) obj).put(str, obj2);
            return true;
        } else if (!(obj instanceof DroiObject)) {
            return false;
        } else {
            Field b = C0911l.m2705b(cls, str);
            if (b != null) {
                b.setAccessible(true);
                try {
                    b.set(obj, obj2);
                    return true;
                } catch (IllegalAccessException e) {
                    return false;
                }
            }
            ((DroiObject) obj).put(str, obj2);
            return true;
        }
    }

    private Object m2438b(C0903g<String, Object> c0903g, String str) {
        List c = c0903g.m2675c(str);
        return c.size() < 1 ? null : c.get(0);
    }

    private DroiError m2439c(Object obj) {
        DroiObject droiObject = obj instanceof DroiReferenceObject ? (DroiObject) ((DroiReferenceObject) obj).droiObject() : (DroiObject) obj;
        if (droiObject == null || !droiObject.isDirty()) {
            DroiLog.m2868d(f2374b, "itemValue empty or not dirty. " + droiObject);
            return new DroiError();
        }
        DroiError a;
        if (droiObject.isReferenceDataDirty()) {
            a = m2429a(droiObject);
            if (!a.isOk()) {
                return a;
            }
        }
        if (droiObject.isBodyDirty()) {
            droiObject.setLocalStorage(false);
            a = droiObject.save();
            if (!a.isOk()) {
                return a;
            }
        }
        return new DroiError();
    }

    public DroiError mo1881a(C0903g<String, Object> c0903g) {
        throw new RuntimeException("Not allowed to call insert");
    }

    public DroiError mo1882a(Object obj, String str) {
        throw new RuntimeException("Not allowed to call createTable");
    }

    protected String m2442a(C0903g<String, Object> c0903g, String str) {
        if (c0903g == null) {
            throw new RuntimeException("There is no query command in list");
        }
        Collection arrayList = new ArrayList();
        for (Entry entry : c0903g.m2674c()) {
            JSONObject jSONObject = new JSONObject();
            try {
                Object value = entry.getValue();
                String str2 = (String) entry.getKey();
                if (!(value.toString().equals(Builder.f2638c) || ParamKey.COUNT.equals(str2))) {
                    Object obj = ((String) entry.getKey()).toString().equals(Builder.f2639d) ? str : value;
                    if (obj instanceof ArrayList) {
                        jSONObject.put((String) entry.getKey(), new JSONArray((ArrayList) obj));
                    } else if (obj instanceof DroiCondition) {
                        DroiCondition droiCondition = (DroiCondition) obj;
                        if (C0871a.m2608a(droiCondition).equals(Builder.f2646k)) {
                            jSONObject.put((String) entry.getKey(), new JSONArray(m2435a((ArrayList) C0871a.m2609b(droiCondition).get(0))));
                        } else {
                            jSONObject.put((String) entry.getKey(), m2436a(droiCondition));
                        }
                    } else {
                        jSONObject.put((String) entry.getKey(), obj);
                    }
                    arrayList.add(jSONObject);
                }
            } catch (JSONException e) {
            }
        }
        if (c0903g.m2673b(ParamKey.COUNT)) {
            try {
                arrayList.add(new JSONObject().put("Count", true));
            } catch (Exception e2) {
                DroiLog.m2869e(f2374b, e2);
            }
        }
        DroiUser currentUser = DroiUser.getCurrentUser();
        if (currentUser != null) {
            try {
                arrayList.add(new JSONObject().put(f2379g, currentUser.getSessionToken()));
            } catch (Exception e22) {
                DroiLog.m2869e(f2374b, e22);
            }
        }
        return new JSONArray(arrayList).toString();
    }

    public List mo1883a(C0903g<String, Object> c0903g, DroiError droiError) {
        Class customClassWithClassName;
        String str = null;
        int i = 0;
        ArrayList arrayList = new ArrayList();
        if (droiError == null) {
            droiError = new DroiError();
        }
        DroiUser currentUser = DroiUser.getCurrentUser();
        if (!(currentUser == null || !DroiUser.isAutoAnonymousUserEnabled() || currentUser.isAuthorized())) {
            DroiUser.loginWithAnonymous(droiError);
            if (!droiError.isOk()) {
                return arrayList;
            }
        }
        if (c0903g.m2673b(Builder.f2639d)) {
            List c = c0903g.m2675c(Builder.f2639d);
            if (c.size() != 1) {
                return arrayList;
            }
            str = (String) c.get(0);
            customClassWithClassName = DroiObject.getCustomClassWithClassName(str);
            if (customClassWithClassName != null) {
                str = C0911l.m2710d(customClassWithClassName);
            }
        } else {
            customClassWithClassName = null;
        }
        try {
            str = C0939m.m2772b(m2434a(Builder.f2639d), m2442a((C0903g) c0903g, str), droiError);
            if (!droiError.isOk()) {
                return arrayList;
            }
            JSONObject jSONObject = new JSONObject(str);
            int i2 = jSONObject.getInt("Code");
            if (i2 != 0) {
                if (i2 == f2373a) {
                    DroiUserHelper.cleanUp();
                }
                droiError.setCode(i2);
                droiError.setAppendedMessage("Ticket: " + jSONObject.getString("Ticket"));
                return arrayList;
            } else if (c0903g.m2673b(ParamKey.COUNT)) {
                arrayList.add(Integer.valueOf(jSONObject.getInt("Count")));
                return arrayList;
            } else {
                JSONArray jSONArray = jSONObject.getJSONArray("Result");
                i2 = jSONArray.length() > 1000 ? 1000 : jSONArray.length();
                while (i < i2) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    Object fromJson = customClassWithClassName != null ? DroiObject.fromJson(jSONObject2, customClassWithClassName) : DroiObject.fromJson(jSONObject2);
                    if (fromJson != null) {
                        arrayList.add(fromJson);
                        i++;
                    } else {
                        str = "Form DroiObject fail. class: " + (customClassWithClassName == null ? "DroiObject" : customClassWithClassName.getSimpleName());
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage(str);
                        DroiLog.m2870e(f2374b, str);
                        return arrayList;
                    }
                }
                return arrayList;
            }
        } catch (Exception e) {
            DroiLog.m2873w(f2374b, e);
            if (droiError != null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e.toString());
            }
        }
    }

    public DroiError mo1884b(C0903g<String, Object> c0903g) {
        DroiError droiError = new DroiError();
        DroiUser currentUser = DroiUser.getCurrentUser();
        if (!(currentUser == null || !DroiUser.isAutoAnonymousUserEnabled() || currentUser.isAuthorized())) {
            DroiUser.loginWithAnonymous(droiError);
            if (!droiError.isOk()) {
                return droiError;
            }
        }
        ArrayList arrayList = (ArrayList) m2438b(c0903g, "update");
        if (arrayList == null || arrayList.size() != 2) {
            droiError.setCode(DroiError.INVALID_PARAMETER);
            return droiError;
        }
        DroiObject droiObject = (DroiObject) arrayList.get(0);
        if (droiObject.isReferenceDataDirty()) {
            DroiError a = m2429a(droiObject);
            if (!a.isOk()) {
                return a == null ? new DroiError(DroiError.ERROR, "obj is not dirty. " + droiObject) : a;
            }
        }
        if (droiObject.isBodyDirty()) {
            String str = (String) arrayList.get(1);
            Class customClassWithClassName = DroiObject.getCustomClassWithClassName(str);
            if (customClassWithClassName != null) {
                str = C0911l.m2710d(customClassWithClassName);
            }
            droiObject.setLocalStorage(false);
            return m2431a(droiObject, str, "update");
        }
        droiObject.setLocalStorage(false);
        return droiError;
    }

    public DroiError mo1885c(C0903g<String, Object> c0903g) {
        DroiError droiError = new DroiError();
        DroiUser currentUser = DroiUser.getCurrentUser();
        if (!(currentUser == null || !DroiUser.isAutoAnonymousUserEnabled() || currentUser.isAuthorized())) {
            DroiUser.loginWithAnonymous(droiError);
            if (!droiError.isOk()) {
                return droiError;
            }
        }
        ArrayList arrayList = (ArrayList) m2438b(c0903g, "delete");
        if (arrayList == null || arrayList.size() <= 0 || arrayList.size() > 2) {
            droiError.setCode(DroiError.INVALID_PARAMETER);
            return droiError;
        }
        String str;
        DroiObject droiObject = null;
        Class customClassWithClassName;
        if (arrayList.size() == 1) {
            str = (String) arrayList.get(0);
            customClassWithClassName = DroiObject.getCustomClassWithClassName(str);
            if (customClassWithClassName != null) {
                str = C0911l.m2710d(customClassWithClassName);
            }
        } else {
            droiObject = (DroiObject) arrayList.get(0);
            str = (String) arrayList.get(1);
            customClassWithClassName = DroiObject.getCustomClassWithClassName(str);
            if (customClassWithClassName != null) {
                str = C0911l.m2710d(customClassWithClassName);
            }
        }
        return m2430a(droiObject, str);
    }

    public DroiError mo1886d(C0903g<String, Object> c0903g) {
        C0903g a = C0903g.m2669a((C0903g) c0903g);
        List c = a.m2675c(Builder.f2644i);
        if (c.size() != 1) {
            return new DroiError(DroiError.INVALID_PARAMETER, null);
        }
        List c2 = a.m2675c(Builder.f2650o);
        List c3 = a.m2675c(Builder.f2651p);
        List c4 = a.m2675c(Builder.f2652q);
        a.m2670a(Builder.f2650o);
        a.m2670a(Builder.f2651p);
        a.m2670a(Builder.f2652q);
        Object obj = (String) ((List) c.get(0)).get(0);
        Class customClassWithClassName = DroiObject.getCustomClassWithClassName(obj);
        if (customClassWithClassName != null) {
            obj = C0911l.m2710d(customClassWithClassName);
        }
        a.m2670a(Builder.f2644i);
        a.m2671a(Builder.f2639d, obj);
        DroiError droiError = new DroiError(0, null);
        List a2 = mo1883a(a, droiError);
        ArrayList arrayList = new ArrayList();
        if (!droiError.isOk()) {
            return droiError;
        }
        DroiError droiError2;
        String str;
        Object a3;
        if (c2 != null) {
            str = (String) c2.get(0);
            droiError2 = droiError;
            for (Object next : a2) {
                a3 = m2433a(str, next);
                DroiError save = (a3 != null && (a3 instanceof Number) && m2437a(str, next, Integer.valueOf(((Number) a3).intValue() + 1))) ? ((DroiObject) next).save() : droiError2;
                droiError2 = save;
            }
        } else if (c3 != null) {
            str = (String) c3.get(0);
            droiError2 = droiError;
            for (Object next2 : a2) {
                a3 = m2433a(str, next2);
                droiError = (a3 != null && (a3 instanceof Number) && m2437a(str, next2, Integer.valueOf(((Number) a3).intValue() - 1))) ? ((DroiObject) next2).save() : droiError2;
                droiError2 = droiError;
            }
        } else if (c4 != null) {
            c = (List) c4.get(0);
            String str2 = (String) c.get(0);
            Object obj2 = c.get(1);
            for (Object obj3 : a2) {
                if (m2437a(str2, obj3, obj2)) {
                    droiError = ((DroiObject) obj3).save();
                }
            }
            droiError2 = droiError;
        } else {
            droiError2 = droiError;
        }
        return droiError2;
    }
}
