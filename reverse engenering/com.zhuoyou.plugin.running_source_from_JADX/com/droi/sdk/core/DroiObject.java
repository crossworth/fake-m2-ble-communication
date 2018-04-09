package com.droi.sdk.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Base64;
import android.util.SparseArray;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiQuery.Builder;
import com.droi.sdk.core.priv.C0895b;
import com.droi.sdk.core.priv.C0896c;
import com.droi.sdk.core.priv.C0911l;
import com.droi.sdk.internal.DroiLog;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DroiObject implements Parcelable {
    static final /* synthetic */ boolean $assertionsDisabled = (!DroiObject.class.desiredAssertionStatus());
    public static final Creator<DroiObject> CREATOR = new C08407();
    private static final int DIRTY_FLAG_BODY = 1;
    private static final int DIRTY_FLAG_REFERENCE = 2;
    private static final String DroiObjectClassName = C0911l.m2708c(DroiObject.class);
    private static final String DroiObjectObjectName = C0911l.m2710d(DroiObject.class);
    private static final String LOG_TAG = "DROI_OBJECT";
    private static final int REF_LIMIT = 3;
    private static final SparseArray<Class<? extends DroiObject>> classTypeTable = new SparseArray();
    private static final SparseArray<Class<? extends DroiObject>> objectTypeTable = new SparseArray();
    private Date creationTime;
    private int dirtyFlags;
    private StringBuilder hashCodeBuffer;
    private int hashCodeForDroiObject;
    private boolean localStorage;
    private Date modifiedTime;
    private String objectId;
    private final String objectName;
    private ConcurrentHashMap<String, Object> opDroiObjectValueSet;
    private DroiPermission permission;

    static class C08407 implements Creator<DroiObject> {
        C08407() {
        }

        public DroiObject createFromParcel(Parcel parcel) {
            String readString = parcel.readString();
            if (readString == null) {
                return null;
            }
            JSONObject jSONObject;
            try {
                jSONObject = new JSONObject(readString);
            } catch (JSONException e) {
                DroiLog.m2870e(DroiObject.LOG_TAG, e.toString());
                jSONObject = null;
            }
            return jSONObject == null ? null : DroiObject.fromJson(jSONObject);
        }

        public DroiObject[] newArray(int i) {
            return new DroiObject[i];
        }
    }

    protected DroiObject() {
        long time = new Date().getTime();
        this.objectName = "";
        this.creationTime = new Date(time + DroiHttpRequest.f2568a);
        this.modifiedTime = this.creationTime;
        this.dirtyFlags = 1;
        this.hashCodeForDroiObject = 0;
        assignObjectId();
    }

    protected DroiObject(String str) {
        long time = new Date().getTime();
        this.objectName = str;
        this.creationTime = new Date(time + DroiHttpRequest.f2568a);
        this.modifiedTime = this.creationTime;
        this.dirtyFlags = 1;
        this.hashCodeForDroiObject = 0;
        assignObjectId();
    }

    private static void assignField(Object obj, Object obj2, Field field) {
        try {
            if ((obj2 instanceof String) && (field.getType() == Boolean.class || field.getType() == Boolean.TYPE)) {
                field.set(obj, Boolean.valueOf(new Boolean((String) obj2).booleanValue()));
            } else if ((obj2 instanceof String) && field.getType() == byte[].class) {
                Object decode;
                String str = (String) obj2;
                try {
                    decode = Base64.decode(str, 0);
                } catch (IllegalArgumentException e) {
                    decode = str.getBytes();
                }
                field.set(obj, decode);
            } else if ((obj2 instanceof String) && field.getType() == Date.class) {
                field.set(obj, C0895b.m2662a((String) obj2));
            } else if ((obj2 instanceof String) && field.getType() == JSONObject.class) {
                field.set(obj, new JSONObject((String) obj2));
            } else if ((obj2 instanceof DroiObject) && field.getType() == DroiReferenceObject.class) {
                DroiReferenceObject droiReferenceObject = new DroiReferenceObject();
                droiReferenceObject.setDroiObject(obj2);
                field.set(obj, droiReferenceObject);
            } else if ((field.getType() == Float.class || field.getType() == Float.TYPE) && (obj2.getClass() == Double.class || obj2.getClass() == Double.TYPE)) {
                field.set(obj, Float.valueOf(((Double) obj2).floatValue()));
            } else {
                field.set(obj, obj2);
            }
        } catch (Exception e2) {
            DroiLog.m2873w(LOG_TAG, e2);
        } catch (Exception e22) {
            DroiLog.m2874w(LOG_TAG, e22.toString());
        }
    }

    private void assignObjectId() {
        this.objectId = UUID.randomUUID().toString().substring(8).replace("-", "");
    }

    public static void cancelBackgroundTask(String str) {
        TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread").killTask(str);
    }

    private void checkDirtyFlag() {
        if (this.dirtyFlags != 3) {
            int i = this.hashCodeForDroiObject;
            toJson(null);
            this.hashCodeForDroiObject = this.hashCodeBuffer.toString().hashCode();
            if (i != this.hashCodeForDroiObject) {
                this.dirtyFlags |= 1;
            }
        }
    }

    public static <T extends DroiObject> T create(Class<T> cls) {
        return (cls == DroiObject.class || objectTypeTable.indexOfKey(C0911l.m2710d(cls).hashCode()) >= 0) ? (DroiObject) C0911l.m2711e(cls) : null;
    }

    public static DroiObject create(String str) {
        if (objectTypeTable.indexOfKey(str.hashCode()) < 0) {
            return new DroiObject(str);
        }
        Class cls = (Class) objectTypeTable.get(str.hashCode());
        if (cls != null) {
            DroiObject droiObject = (DroiObject) C0911l.m2711e(cls);
            if (droiObject != null) {
                return droiObject;
            }
        }
        throw new RuntimeException("Unable to invoke no-args constructor for " + str + ". ");
    }

    public static <T extends DroiObject> DroiError deleteAll(List<T> list) {
        if (list == null || list.size() == 0) {
            return new DroiError(0, null);
        }
        for (T t : list) {
            DroiError delete = t.delete();
            if (!delete.isOk()) {
                if (t != null) {
                    delete.setAppendedMessage("ObjId: " + t.getObjectId());
                }
                return delete;
            }
        }
        return new DroiError();
    }

    public static <T extends DroiObject> String deleteAllInBackground(final List<T> list, final DroiCallback<Boolean> droiCallback) {
        String str = null;
        if (list != null && list.size() != 0) {
            str = UUID.randomUUID().toString();
            TaskDispatcher dispatcher = TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread");
            if ($assertionsDisabled || dispatcher != null) {
                final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
                dispatcher.enqueueTask(new Runnable() {
                    public void run() {
                        DroiError deleteAll;
                        try {
                            deleteAll = DroiObject.deleteAll(list);
                        } catch (Exception e) {
                            deleteAll = new DroiError(DroiError.ERROR, e.toString());
                        }
                        if (droiCallback != null) {
                            currentTaskDispatcher.enqueueTask(new Runnable(this) {
                                final /* synthetic */ C08396 f2595b;

                                public void run() {
                                    droiCallback.result(Boolean.valueOf(deleteAll.isOk()), deleteAll);
                                }
                            });
                        }
                    }
                }, str);
            } else {
                throw new AssertionError();
            }
        } else if (droiCallback != null) {
            droiCallback.result(Boolean.valueOf(true), new DroiError(0, null));
        }
        return str;
    }

    private DroiError deleteFromStorage() {
        Class cls = getClass();
        String simpleName = cls.getSimpleName();
        if (cls.equals(DroiObject.class)) {
            simpleName = this.objectName;
        }
        Builder b = Builder.newBuilder().m2570b(this, simpleName);
        return (this.localStorage ? b.localStorage().build() : b.cloudStorage().build()).m2577a();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.droi.sdk.core.DroiObject fromJson(org.json.JSONObject r2) {
        /*
        r0 = 0;
        r1 = "_ClassName";
        r0 = r2.getString(r1);	 Catch:{ JSONException -> 0x0016 }
    L_0x0007:
        r1 = com.droi.sdk.core.DroiObject.class;
        if (r0 == 0) goto L_0x0018;
    L_0x000b:
        r0 = getCustomClassWithClassName(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x0011:
        r0 = fromJson(r2, r0);
        return r0;
    L_0x0016:
        r1 = move-exception;
        goto L_0x0007;
    L_0x0018:
        r0 = r1;
        goto L_0x0011;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.DroiObject.fromJson(org.json.JSONObject):com.droi.sdk.core.DroiObject");
    }

    static <T extends DroiObject> T fromJson(JSONObject jSONObject, Class<T> cls) {
        String string;
        String d;
        String str;
        T create;
        try {
            string = jSONObject.getString(C0896c.f2866a);
        } catch (JSONException e) {
            string = null;
        }
        if (string == null || string.length() == 0) {
            string = C0911l.m2708c((Class) cls);
            d = C0911l.m2710d(cls);
            str = string;
        } else {
            Class customClassWithClassName = getCustomClassWithClassName(string);
            if (customClassWithClassName != null) {
                if (cls == DroiUser.class && cls != customClassWithClassName) {
                    Class cls2 = customClassWithClassName;
                }
                string = C0911l.m2708c(customClassWithClassName);
                d = C0911l.m2710d(customClassWithClassName);
                str = string;
            } else {
                d = string;
                str = string;
            }
        }
        if (isExtendedClass(d)) {
            create = create(cls2);
            if (create == null) {
                DroiLog.m2870e(LOG_TAG, "Can not constructor object " + cls2.getSimpleName() + ". Using DroiObject instead.");
            }
            int i = 1;
        } else {
            create = create(str);
            Object obj = null;
        }
        if (create == null) {
            return null;
        }
        Iterator keys = jSONObject.keys();
        HashMap hashMap = null;
        while (keys.hasNext()) {
            string = (String) keys.next();
            if (C0896c.f2866a.hashCode() != string.hashCode()) {
                HashMap hashMap2;
                try {
                    Object fromJsonByClass = fromJsonByClass(string, jSONObject.get(string), create.getClass());
                    if (fromJsonByClass != null) {
                        if (!fromJson(string, fromJsonByClass, create)) {
                            Object obj2;
                            if (obj == null) {
                                if (fromJsonByClass != null) {
                                    obj2 = jSONObject.get(string);
                                    if ((obj2 instanceof JSONObject) && ((JSONObject) obj2).has(C0896c.f2871f)) {
                                        DroiReferenceObject droiReferenceObject = new DroiReferenceObject();
                                        droiReferenceObject.setDroiObject(fromJsonByClass);
                                        fromJsonByClass = droiReferenceObject;
                                    }
                                    create.put(string, fromJsonByClass);
                                    hashMap2 = hashMap;
                                    hashMap = hashMap2;
                                }
                            } else if (C0896c.f2872g.hashCode() == string.hashCode()) {
                                for (Entry entry : ((Map) fromJsonByClass).entrySet()) {
                                    create.put((String) entry.getKey(), entry.getValue());
                                }
                                hashMap2 = hashMap;
                                hashMap = hashMap2;
                            } else {
                                if (hashMap == null) {
                                    hashMap = C0911l.m2700a(cls2, str);
                                }
                                Field field = (hashMap == null || !hashMap.containsKey(string)) ? null : (Field) hashMap.get(string);
                                if (field == null) {
                                    field = C0911l.m2705b(cls2, string);
                                }
                                if (field != null) {
                                    field.setAccessible(true);
                                    assignField(create, fromJsonByClass, field);
                                    obj2 = 1;
                                } else {
                                    obj2 = null;
                                }
                                if (obj2 == null) {
                                    create.put(string, fromJsonByClass);
                                }
                            }
                        }
                        hashMap2 = hashMap;
                        hashMap = hashMap2;
                    }
                } catch (JSONException e2) {
                    hashMap2 = hashMap;
                }
            }
        }
        if (create != null) {
            create.checkDirtyFlag();
            create.resetDirtyFlags();
        }
        return create;
    }

    private static boolean fromJson(String str, Object obj, DroiObject droiObject) {
        int hashCode = str.hashCode();
        if (C0896c.f2869d.hashCode() == hashCode) {
            droiObject.creationTime = C0895b.m2662a((String) obj);
            return true;
        } else if (C0896c.f2870e.hashCode() == hashCode) {
            droiObject.modifiedTime = C0895b.m2662a((String) obj);
            return true;
        } else if (C0896c.f2868c.hashCode() == hashCode) {
            droiObject.objectId = obj.toString();
            return true;
        } else if (C0896c.f2877l.hashCode() == hashCode) {
            droiObject.permission = DroiPermission.fromJson(new JSONObject((HashMap) obj));
            return true;
        } else if (C0896c.f2880o.hashCode() == hashCode) {
            droiObject.hashCodeForDroiObject = Integer.valueOf(obj.toString()).intValue();
            return true;
        } else if (C0896c.f2881p.hashCode() != hashCode) {
            return false;
        } else {
            droiObject.dirtyFlags = Integer.valueOf(obj.toString()).intValue();
            return true;
        }
    }

    private static Object fromJsonByClass(String str, Object obj, Class cls) {
        String obj2;
        Object fromJson;
        Exception e;
        Map map;
        Map map2;
        Iterator keys;
        Exception e2;
        Field b;
        Class type;
        Object obj3;
        Field b2;
        Class type2;
        Object obj4;
        if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            if (jSONObject.has(C0896c.f2871f)) {
                try {
                    obj2 = jSONObject.get(C0896c.f2871f).toString();
                    if (C0896c.f2873h.hashCode() == obj2.hashCode() || C0896c.f2874i.equals(obj2)) {
                        if (!jSONObject.has(C0896c.f2876k)) {
                            obj2 = jSONObject.getString(C0896c.f2868c);
                            String string = jSONObject.getString(C0896c.f2867b);
                            DroiLog.m2874w(LOG_TAG, String.format("Ref id: %s, cloud class: %s", new Object[]{obj2, string}));
                        }
                        JSONObject jSONObject2 = (JSONObject) jSONObject.get(C0896c.f2876k);
                        b2 = C0911l.m2705b(cls, str);
                        if (b2 != null) {
                            type2 = b2.getType();
                            if (!isExtendedClass(type2)) {
                                type2 = null;
                            } else if (type2 == DroiUser.class && jSONObject2.has(C0896c.f2866a)) {
                                Class customClassWithClassName = getCustomClassWithClassName(jSONObject2.getString(C0896c.f2866a));
                                if (customClassWithClassName != type2 && C0911l.m2701a(customClassWithClassName, DroiUser.class)) {
                                    type2 = null;
                                }
                            }
                        } else {
                            type2 = null;
                        }
                        fromJson = type2 != null ? fromJson(jSONObject2, type2) : fromJson(jSONObject2);
                        try {
                            if (jSONObject.has(C0896c.f2875j)) {
                                ((DroiObject) fromJson).dirtyFlags = jSONObject.getInt(C0896c.f2875j);
                            }
                        } catch (JSONException e3) {
                            e = e3;
                            DroiLog.m2873w(LOG_TAG, e);
                            if (fromJson == null) {
                                if (str == null) {
                                    map = cls != null ? (Map) C0911l.m2711e(cls) : null;
                                    if (map == null) {
                                        fromJson = new HashMap();
                                    } else {
                                        map2 = map;
                                    }
                                    keys = jSONObject.keys();
                                    while (keys.hasNext()) {
                                        obj2 = (String) keys.next();
                                        try {
                                            obj4 = jSONObject.get(obj2);
                                            try {
                                                if (!(obj4 instanceof JSONObject)) {
                                                    obj4 = fromJsonByClass(obj2, obj4, null);
                                                }
                                            } catch (JSONException e4) {
                                                e2 = e4;
                                                DroiLog.m2873w(LOG_TAG, e2);
                                                fromJson.put(obj2, obj4);
                                            }
                                        } catch (Exception e5) {
                                            e2 = e5;
                                            obj4 = null;
                                            DroiLog.m2873w(LOG_TAG, e2);
                                            fromJson.put(obj2, obj4);
                                        }
                                        fromJson.put(obj2, obj4);
                                    }
                                } else {
                                    b = C0911l.m2705b(cls, str);
                                    if (b != null) {
                                        type = b.getType();
                                        fromJson = isExtendedClass(C0911l.m2710d(type)) ? fromJson(jSONObject, type) : fromJsonByClass(null, jSONObject, type);
                                    } else {
                                        fromJson = fromJsonByClass(null, jSONObject, null);
                                    }
                                }
                            }
                            obj3 = fromJson;
                            return obj3 == null ? obj : obj3;
                        }
                    } else {
                        fromJson = fromJsonByClass(null, jSONObject, null);
                    }
                } catch (JSONException e6) {
                    e = e6;
                    fromJson = null;
                    DroiLog.m2873w(LOG_TAG, e);
                    if (fromJson == null) {
                        if (str == null) {
                            b = C0911l.m2705b(cls, str);
                            if (b != null) {
                                fromJson = fromJsonByClass(null, jSONObject, null);
                            } else {
                                type = b.getType();
                                if (isExtendedClass(C0911l.m2710d(type))) {
                                }
                                fromJson = isExtendedClass(C0911l.m2710d(type)) ? fromJson(jSONObject, type) : fromJsonByClass(null, jSONObject, type);
                            }
                        } else {
                            if (cls != null) {
                            }
                            if (map == null) {
                                map2 = map;
                            } else {
                                fromJson = new HashMap();
                            }
                            keys = jSONObject.keys();
                            while (keys.hasNext()) {
                                obj2 = (String) keys.next();
                                obj4 = jSONObject.get(obj2);
                                if (!(obj4 instanceof JSONObject)) {
                                    obj4 = fromJsonByClass(obj2, obj4, null);
                                }
                                fromJson.put(obj2, obj4);
                            }
                        }
                    }
                    obj3 = fromJson;
                    if (obj3 == null) {
                    }
                }
            } else if (jSONObject.has(C0896c.f2866a)) {
                b = C0911l.m2705b(cls, str);
                if (b != null) {
                    type = b.getType();
                    if (!isExtendedClass(type)) {
                        type = null;
                    } else if (type == DroiUser.class) {
                        try {
                            type2 = getCustomClassWithClassName(jSONObject.getString(C0896c.f2866a));
                            if (type2 != type && C0911l.m2701a(type2, DroiUser.class)) {
                                type = null;
                            }
                        } catch (JSONException e7) {
                            DroiLog.m2874w(LOG_TAG, e7.toString());
                        }
                    }
                } else {
                    type = null;
                }
                fromJson = type != null ? fromJson(jSONObject, type) : fromJson(jSONObject);
            } else {
                fromJson = null;
            }
            if (fromJson == null) {
                if (str == null) {
                    if (cls != null) {
                    }
                    if (map == null) {
                        fromJson = new HashMap();
                    } else {
                        map2 = map;
                    }
                    keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        obj2 = (String) keys.next();
                        obj4 = jSONObject.get(obj2);
                        if (!(obj4 instanceof JSONObject)) {
                            obj4 = fromJsonByClass(obj2, obj4, null);
                        }
                        fromJson.put(obj2, obj4);
                    }
                } else {
                    b = C0911l.m2705b(cls, str);
                    if (b != null) {
                        type = b.getType();
                        if (isExtendedClass(C0911l.m2710d(type))) {
                        }
                        fromJson = isExtendedClass(C0911l.m2710d(type)) ? fromJson(jSONObject, type) : fromJsonByClass(null, jSONObject, type);
                    } else {
                        fromJson = fromJsonByClass(null, jSONObject, null);
                    }
                }
            }
            obj3 = fromJson;
        } else if (obj instanceof JSONArray) {
            List list;
            Class cls2;
            JSONArray jSONArray = (JSONArray) obj;
            b2 = C0911l.m2705b(cls, str);
            if (b2 != null) {
                list = (List) C0911l.m2711e(b2.getType());
                Type genericType = b2.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
                    if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                        type2 = (Class) actualTypeArguments[0];
                        cls2 = !isExtendedClass(C0911l.m2710d(type2)) ? null : type2;
                    }
                }
                cls2 = null;
            } else {
                cls2 = null;
                list = null;
            }
            List arrayList = list == null ? new ArrayList() : list;
            int length = jSONArray.length();
            int i = 0;
            while (i < length) {
                try {
                    Object obj5 = jSONArray.get(i);
                    if (cls2 == null || !(obj5 instanceof JSONObject)) {
                        obj4 = fromJsonByClass(null, obj5, null);
                        if ((obj5 instanceof JSONObject) && ((JSONObject) obj5).has(C0896c.f2871f) && ((JSONObject) obj5).get(C0896c.f2871f).equals(C0896c.f2873h)) {
                            obj5 = new DroiReferenceObject();
                            obj5.setDroiObject(obj4);
                        } else {
                            obj5 = obj4;
                        }
                        arrayList.add(obj5);
                        i++;
                    } else {
                        arrayList.add(fromJson((JSONObject) obj5, cls2));
                        i++;
                    }
                } catch (Exception e8) {
                    DroiLog.m2873w(LOG_TAG, e8);
                }
            }
            List list2 = arrayList;
        } else {
            obj3 = null;
        }
        if (obj3 == null) {
        }
    }

    public static Class<?> getCustomClass(String str) {
        return (objectTypeTable == null || str == null) ? null : (Class) objectTypeTable.get(str.hashCode());
    }

    public static Class<?> getCustomClassWithClassName(String str) {
        if (objectTypeTable == null || str == null) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf >= 0) {
            DroiLog.m2874w(LOG_TAG, "Not class simple name, will strip it.");
            str = str.substring(lastIndexOf + 1);
        }
        return (Class) classTypeTable.get(str.hashCode());
    }

    private int getDepth() {
        Iterator it;
        Object obj;
        Exception exception;
        int i = 1;
        int i2 = 0;
        String str = this.objectName;
        Class cls = getClass();
        if (str != null && str.length() != 0) {
            i = 0;
        } else if (!isExtendedClass(this)) {
            return 0;
        }
        if (i != 0) {
            try {
                ArrayList a = C0911l.m2699a(cls);
                if (a != null) {
                    it = a.iterator();
                    while (it.hasNext()) {
                        Field field = (Field) it.next();
                        field.setAccessible(true);
                        obj = field.get(this);
                        if (obj != null && (obj instanceof DroiObject)) {
                            i2 = Math.max(i2, ((DroiObject) obj).getDepth() + 1);
                        }
                    }
                }
            } catch (Exception e) {
                Exception exception2 = e;
                i = i2;
                exception = exception2;
            }
        }
        i = i2;
        try {
            if (this.opDroiObjectValueSet != null && this.opDroiObjectValueSet.size() > 0) {
                i2 = i;
                for (String str2 : this.opDroiObjectValueSet.keySet()) {
                    obj = this.opDroiObjectValueSet.get(str2);
                    if (obj != null && (obj instanceof DroiObject)) {
                        i2 = Math.max(i2, ((DroiObject) obj).getDepth() + 1);
                    }
                }
                i = i2;
            }
        } catch (IllegalAccessException e2) {
            exception = e2;
            DroiLog.m2873w(LOG_TAG, exception);
            return i;
        }
        return i;
    }

    private <T> T getGeneric(String str, Class<T> cls) {
        Object obj = get(str);
        return (obj != null && cls.isInstance(obj)) ? cls.cast(obj) : null;
    }

    private JSONObject getPermissionWithDefault() {
        if (this.permission != null) {
            return this.permission.toJson();
        }
        DroiPermission defaultPermission = DroiPermission.getDefaultPermission();
        return defaultPermission != null ? defaultPermission.toJson() : new JSONObject();
    }

    private static boolean isDroiObjectClass(String str) {
        return str != null && DroiObjectClassName.hashCode() == str.hashCode();
    }

    private static <T extends DroiObject> boolean isExtendedClass(T t) {
        return isExtendedClass(C0911l.m2710d(t.getClass()));
    }

    public static boolean isExtendedClass(Class cls) {
        return cls == DroiObject.class || getCustomClassWithClassName(cls.getSimpleName()) != null;
    }

    public static boolean isExtendedClass(String str) {
        return str == null ? false : DroiObjectObjectName.equals(str) || objectTypeTable.indexOfKey(str.hashCode()) >= 0;
    }

    private void processReferenceObject(DroiObject droiObject) {
        Iterator it = C0911l.m2699a(droiObject.getClass()).iterator();
        while (it.hasNext()) {
            Field field = (Field) it.next();
            field.setAccessible(true);
            try {
                field.getType();
                DroiReference droiReference = (DroiReference) field.getAnnotation(DroiReference.class);
                DroiObject droiObject2 = (DroiObject) field.get(droiObject);
                if (droiObject2 != null && droiObject2.isDirty()) {
                    if (droiReference != null || (droiObject2 instanceof DroiFile)) {
                        if (droiObject2.isReferenceDataDirty()) {
                            processReferenceObject(droiObject2);
                        }
                        droiObject2.setLocalStorage(false);
                        droiObject2.resetDirtyFlags();
                    }
                }
            } catch (IllegalAccessException e) {
            }
        }
    }

    public static synchronized void registerCustomClass(Class<? extends DroiObject> cls) {
        synchronized (DroiObject.class) {
            if (cls == null) {
                DroiLog.m2870e(LOG_TAG, "input argument null.");
            } else {
                String d = C0911l.m2710d(cls);
                String simpleName = cls.getSimpleName();
                if (simpleName == null) {
                    DroiLog.m2870e(LOG_TAG, "Can not register class " + cls + ". can not getSimpleName");
                } else if (d == null) {
                    DroiLog.m2870e(LOG_TAG, "Can not register class " + simpleName + ". can not getObjectName");
                } else {
                    objectTypeTable.put(d.hashCode(), cls);
                    classTypeTable.put(simpleName.hashCode(), cls);
                    C0911l.m2699a((Class) cls);
                }
            }
        }
    }

    public static <T extends DroiObject> DroiError saveAll(List<T> list) {
        if (list == null || list.size() == 0) {
            return new DroiError(0, null);
        }
        for (T t : list) {
            DroiError save = t.save();
            if (!save.isOk()) {
                if (t != null) {
                    save.setAppendedMessage("ObjId: " + t.getObjectId());
                }
                return save;
            }
        }
        return new DroiError();
    }

    public static <T extends DroiObject> String saveAllInBackground(final List<T> list, final DroiCallback<Boolean> droiCallback) {
        String str = null;
        if (list != null && list.size() != 0) {
            str = UUID.randomUUID().toString();
            TaskDispatcher dispatcher = TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread");
            if ($assertionsDisabled || dispatcher != null) {
                final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
                dispatcher.enqueueTask(new Runnable() {
                    public void run() {
                        DroiError saveAll;
                        try {
                            saveAll = DroiObject.saveAll(list);
                        } catch (Exception e) {
                            saveAll = new DroiError(DroiError.ERROR, e.toString());
                        }
                        if (droiCallback != null) {
                            currentTaskDispatcher.enqueueTask(new Runnable(this) {
                                final /* synthetic */ C08332 f2580b;

                                public void run() {
                                    droiCallback.result(Boolean.valueOf(saveAll.isOk()), saveAll);
                                }
                            });
                        }
                    }
                }, str);
            } else {
                throw new AssertionError();
            }
        } else if (droiCallback != null) {
            droiCallback.result(Boolean.valueOf(true), new DroiError(0, null));
        }
        return str;
    }

    private DroiError saveToStorage() {
        DroiError droiError = new DroiError();
        if (getDepth() > 3) {
            DroiLog.m2870e(LOG_TAG, "Cannot reference more than 3");
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage("Cannot reference more than 3");
        } else {
            checkDirtyFlag();
            if (this.dirtyFlags != 0) {
                this.modifiedTime = new Date(new Date().getTime() + DroiHttpRequest.f2568a);
                Class cls = getClass();
                String simpleName = cls.getSimpleName();
                if (cls.equals(DroiObject.class)) {
                    simpleName = this.objectName;
                }
                Builder update = Builder.newBuilder().update(this, simpleName);
                droiError = (this.localStorage ? update.localStorage().build() : update.cloudStorage().build()).m2577a();
                if (droiError.isOk()) {
                    resetDirtyFlags();
                }
            } else {
                DroiLog.m2868d(LOG_TAG, "No need to save:" + this.objectId);
            }
        }
        return droiError;
    }

    private Object toJson(Object obj, Class<?> cls, DroiError droiError, boolean z, boolean z2) {
        if (obj == null) {
            if (droiError != null) {
                try {
                    droiError.setCode(DroiError.INVALID_PARAMETER);
                    droiError.setAppendedMessage("Empty value");
                } catch (Exception e) {
                    DroiLog.m2873w(LOG_TAG, e);
                    if (droiError != null) {
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage(e.toString());
                    }
                    return null;
                }
            }
            return null;
        } else if ((obj instanceof DroiReferenceObject) && !z) {
            return obj;
        } else {
            String c = C0911l.m2708c((Class) cls);
            String d = C0911l.m2710d(cls);
            if ((obj instanceof DroiReferenceObject) || (obj instanceof DroiFile)) {
                DroiObject droiObject = obj instanceof DroiReferenceObject ? (DroiObject) ((DroiReferenceObject) obj).droiObject() : (DroiObject) obj;
                if (droiObject == null) {
                    return null;
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(C0896c.f2871f, C0896c.f2873h);
                if (this.localStorage) {
                    jSONObject.put(C0896c.f2876k, null);
                }
                jSONObject.put(C0896c.f2867b, droiObject.getObjectName());
                jSONObject.put(C0896c.f2868c, droiObject.getObjectId());
                if (z2) {
                    jSONObject.put(C0896c.f2876k, droiObject.toJson(z2, null));
                }
                droiObject.checkDirtyFlag();
                if (droiObject.dirtyFlags != 0) {
                    this.dirtyFlags |= 2;
                }
                if (!this.localStorage) {
                    return jSONObject;
                }
                jSONObject.put(C0896c.f2875j, droiObject.dirtyFlags);
                return jSONObject;
            } else if (C0911l.m2702a(obj)) {
                if (!(obj instanceof Long)) {
                    return obj;
                }
                DroiLog.m2870e(LOG_TAG, "For now, only support int(32bit) values.");
                Long l = (Long) obj;
                return l.longValue() > 2147483647L ? Long.valueOf(2147483647L) : l.longValue() < -2147483648L ? Long.valueOf(-2147483648L) : l;
            } else if (isDroiObjectClass(c) || isExtendedClass(d)) {
                DroiObject droiObject2 = (DroiObject) obj;
                droiObject2.setLocalStorage(this.localStorage);
                return droiObject2.toJson(z2, droiError);
            } else if (obj instanceof List) {
                JSONArray jSONArray = new JSONArray();
                List list = (List) obj;
                for (int i = 0; i < list.size(); i++) {
                    r1 = list.get(i);
                    Object toJson = toJson(r1, r1.getClass(), droiError, true, z2);
                    if (toJson != null) {
                        jSONArray.put(toJson);
                    }
                }
                return jSONArray;
            } else if (obj instanceof Map) {
                JSONObject jSONObject2 = new JSONObject();
                Map map = (Map) obj;
                for (Object next : map.keySet()) {
                    r1 = map.get(next);
                    jSONObject2.put(next.toString(), toJson(r1, r1.getClass(), droiError, true, z2));
                }
                return jSONObject2;
            } else if ((obj instanceof JSONObject) || (obj instanceof JSONArray)) {
                return obj;
            } else {
                if (obj instanceof byte[]) {
                    return Base64.encodeToString((byte[]) obj, 0);
                }
                if (obj instanceof Date) {
                    return C0895b.m2661a((Date) obj);
                }
                throw new IllegalArgumentException("Droi Core SDK doesn't support class type : " + C0911l.m2708c((Class) cls));
            }
        }
    }

    private void toJson(String str, Object obj, JSONObject jSONObject, Class<?> cls, boolean z, boolean z2, StringBuilder stringBuilder, DroiError droiError) {
        Object toJson = toJson(obj, cls, droiError, false, z2);
        if (toJson != null) {
            if (!z) {
                try {
                    if (!((obj instanceof DroiReferenceObject) || (obj instanceof DroiFile))) {
                        jSONObject.put(str, toJson);
                        stringBuilder.append(toJson);
                        return;
                    }
                } catch (Exception e) {
                    DroiLog.m2873w(LOG_TAG, e);
                    if (droiError != null) {
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage(e.toString());
                        return;
                    }
                    return;
                }
            }
            DroiObject droiObject = obj instanceof DroiReferenceObject ? (DroiObject) ((DroiReferenceObject) obj).droiObject() : (DroiObject) obj;
            if (droiObject != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(C0896c.f2871f, C0896c.f2873h);
                if (this.localStorage) {
                    jSONObject2.put(C0896c.f2876k, toJson);
                }
                jSONObject2.put(C0896c.f2867b, droiObject.getObjectName());
                jSONObject2.put(C0896c.f2868c, droiObject.getObjectId());
                if (z2) {
                    jSONObject2.put(C0896c.f2876k, droiObject.toJson(z2, null));
                }
                droiObject.checkDirtyFlag();
                if (droiObject.dirtyFlags != 0) {
                    this.dirtyFlags |= 2;
                }
                if (this.localStorage) {
                    jSONObject2.put(C0896c.f2875j, droiObject.dirtyFlags);
                }
                stringBuilder.append(C0896c.f2873h);
                stringBuilder.append(C0911l.m2710d(droiObject.getClass()));
                stringBuilder.append(droiObject.getCreationTime());
                jSONObject.put(str, jSONObject2);
            }
        }
    }

    protected void copyFrom(DroiObject droiObject) {
        this.objectId = droiObject.objectId;
        this.creationTime = droiObject.creationTime;
        this.modifiedTime = droiObject.modifiedTime;
        this.permission = droiObject.permission;
        this.localStorage = droiObject.localStorage;
        this.dirtyFlags = droiObject.dirtyFlags;
        this.hashCodeForDroiObject = droiObject.hashCodeForDroiObject;
        this.opDroiObjectValueSet = droiObject.opDroiObjectValueSet;
    }

    public DroiError delete() {
        C0877b a = Core.m2451a();
        return a != null ? a.m2625a() : true ? deleteFromStorage() : new DroiError(DroiError.ERROR, null);
    }

    public void deleteEventually() {
        if (isLocalStorage()) {
            delete();
            return;
        }
        this.modifiedTime = new Date();
        Core.m2451a().m2624a(this, false);
        resetDirtyFlags();
        processReferenceObject(this);
    }

    public String deleteInBackground(final DroiCallback<Boolean> droiCallback) {
        String uuid = UUID.randomUUID().toString();
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread");
        if ($assertionsDisabled || dispatcher != null) {
            final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
            dispatcher.enqueueTask(new Runnable(this) {
                final /* synthetic */ DroiObject f2593c;

                public void run() {
                    DroiError delete;
                    try {
                        delete = this.f2593c.delete();
                    } catch (Exception e) {
                        delete = new DroiError(DroiError.ERROR, e.toString());
                    }
                    if (droiCallback != null) {
                        currentTaskDispatcher.enqueueTask(new Runnable(this) {
                            final /* synthetic */ C08375 f2590b;

                            public void run() {
                                droiCallback.result(Boolean.valueOf(delete.isOk()), delete);
                            }
                        });
                    }
                }
            }, uuid);
            return uuid;
        }
        throw new AssertionError();
    }

    public int describeContents() {
        return 0;
    }

    public DroiError fetch() {
        DroiQuery build = Builder.newBuilder().query(getClass()).where(C0896c.f2868c, DroiCondition.Type.EQ, getObjectId()).build();
        DroiError droiError = new DroiError();
        List runQuery = build.runQuery(droiError);
        if (!droiError.isOk()) {
            return droiError;
        }
        if (runQuery.size() < 1) {
            return new DroiError(DroiError.ERROR, "objectId not found.");
        }
        Object obj = runQuery.get(0);
        for (Field field : C0911l.m2699a(getClass())) {
            try {
                field.setAccessible(true);
                field.set(this, field.get(obj));
            } catch (Exception e) {
                String str = "assign fail in key " + field.getName();
                DroiLog.m2870e(LOG_TAG, str);
                return new DroiError(DroiError.ERROR, str);
            }
        }
        String[] strArr = new String[]{"modifiedTime", "opDroiObjectValueSet", "permission"};
        Class cls = DroiObject.class;
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            str = strArr[i];
            try {
                Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                declaredField.set(this, declaredField.get(obj));
                i++;
            } catch (Exception e2) {
                str = "assign fail in key " + str;
                DroiLog.m2870e(LOG_TAG, str);
                return new DroiError(DroiError.ERROR, str);
            }
        }
        if (this == DroiUser.getCurrentUser()) {
            droiError = DroiUser.m2583a((DroiUser) this);
            if (!droiError.isOk()) {
                return droiError;
            }
        }
        return new DroiError();
    }

    public boolean fetchInBackground(final DroiCallback<Boolean> droiCallback) {
        final AtomicReference atomicReference = new AtomicReference(new DroiError(DroiError.UNKNOWN_ERROR, "May caused by Exception"));
        return DroiTask.create(new DroiRunnable(this) {
            final /* synthetic */ DroiObject f2588b;

            public void run() {
                atomicReference.set(this.f2588b.fetch());
            }
        }).callback(new DroiRunnable(this) {
            final /* synthetic */ DroiObject f2586c;

            public void run() {
                DroiError droiError = (DroiError) atomicReference.get();
                if (droiCallback != null) {
                    droiCallback.result(Boolean.valueOf(droiError.isOk()), droiError);
                }
            }
        }, TaskDispatcher.currentTaskDispatcher().name()).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    public Object get(String str) {
        return this.opDroiObjectValueSet == null ? null : this.opDroiObjectValueSet.get(str);
    }

    public boolean getBoolean(String str) {
        Boolean bool = (Boolean) getGeneric(str, Boolean.class);
        return bool == null ? false : bool.booleanValue();
    }

    public byte[] getBytes(String str) {
        Object obj = get(str);
        return (obj == null || !(obj instanceof byte[])) ? null : (byte[]) obj;
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    public double getDouble(String str) {
        Number number = getNumber(str);
        return number == null ? 0.0d : number.doubleValue();
    }

    public DroiObject getDroiObject(String str) {
        DroiObject droiObject = (DroiObject) getGeneric(str, DroiObject.class);
        return droiObject == null ? null : droiObject;
    }

    public int getInt(String str) {
        Number number = getNumber(str);
        return number == null ? 0 : number.intValue();
    }

    public ArrayList<String> getKeys() {
        return this.opDroiObjectValueSet == null ? null : Collections.list(this.opDroiObjectValueSet.keys());
    }

    public long getLong(String str) {
        Number number = getNumber(str);
        return number == null ? 0 : number.longValue();
    }

    public Date getModifiedTime() {
        return this.modifiedTime;
    }

    public Number getNumber(String str) {
        Number number = (Number) getGeneric(str, Number.class);
        return number == null ? null : Integer.valueOf(number.intValue());
    }

    public String getObjectId() {
        return this.objectId;
    }

    public String getObjectName() {
        Class cls = getClass();
        return isDroiObjectClass(C0911l.m2708c(cls)) ? this.objectName : C0911l.m2710d(cls);
    }

    public DroiPermission getPermission() {
        return this.permission;
    }

    public String getString(String str) {
        Object obj = get(str);
        return (obj == null || !(obj instanceof String)) ? null : (String) obj;
    }

    public boolean isBodyDirty() {
        return (this.dirtyFlags & 1) != 0;
    }

    public boolean isDirty() {
        return this.dirtyFlags != 0;
    }

    public boolean isLocalStorage() {
        return this.localStorage;
    }

    public boolean isReferenceDataDirty() {
        return (this.dirtyFlags & 2) != 0;
    }

    public void put(String str, Object obj) {
        if (!C0911l.m2703a(obj.getClass())) {
            throw new IllegalArgumentException("Value may be numerical, JSONObject, String, DroiObject .... the value type is " + obj.getClass().getName());
        } else if (str == null || obj == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("value should not be null or key should not be empty");
        } else if (str.contains("$") || str.contains(".") || str.contains("@")) {
            throw new IllegalArgumentException("The key cannot include '$', '.' or '@' character.");
        } else if (C0896c.f2866a.hashCode() == str.hashCode()) {
            throw new IllegalArgumentException("Keyname cannot be _ClassName");
        } else {
            if (this.opDroiObjectValueSet == null) {
                this.opDroiObjectValueSet = new ConcurrentHashMap();
            }
            if (DroiObjectClassName.hashCode() != C0911l.m2708c(getClass()).hashCode()) {
                Iterator it = C0911l.m2699a(getClass()).iterator();
                while (it.hasNext()) {
                    if (((Field) it.next()).getName().equals(str)) {
                        DroiLog.m2874w(LOG_TAG, String.format("There is a name of member value same to key (%s). Will drop.", new Object[]{str}));
                        return;
                    }
                }
            }
            this.opDroiObjectValueSet.put(str, obj);
        }
    }

    void resetDirtyFlags() {
        this.dirtyFlags = 0;
    }

    public DroiError save() {
        C0877b a = Core.m2451a();
        if (!(a != null ? a.m2625a() : true)) {
            return new DroiError(DroiError.ERROR, "Flush object fail.");
        }
        DroiError saveToStorage = saveToStorage();
        if (!saveToStorage.isOk() || getPermission() != null) {
            return saveToStorage;
        }
        DroiUser currentUser = DroiUser.getCurrentUser();
        if (currentUser == null || !currentUser.isAuthorized()) {
            return saveToStorage;
        }
        DroiPermission droiPermission = new DroiPermission();
        droiPermission.m2559a(currentUser.getObjectId());
        setPermission(droiPermission);
        return saveToStorage;
    }

    public DroiError saveEventually() {
        if (isLocalStorage()) {
            return save();
        }
        checkDirtyFlag();
        if (this.dirtyFlags != 0) {
            this.modifiedTime = new Date();
            Core.m2451a().m2624a(this, true);
            resetDirtyFlags();
            processReferenceObject(this);
        }
        return new DroiError();
    }

    public String saveInBackground(final DroiCallback<Boolean> droiCallback) {
        String uuid = UUID.randomUUID().toString();
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher("TaskDispatcher_DroiBackgroundThread");
        if ($assertionsDisabled || dispatcher != null) {
            final TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
            dispatcher.enqueueTask(new Runnable(this) {
                final /* synthetic */ DroiObject f2578c;

                public void run() {
                    DroiError save;
                    try {
                        save = this.f2578c.save();
                    } catch (Exception e) {
                        save = new DroiError(DroiError.ERROR, e.toString());
                    }
                    if (droiCallback != null) {
                        currentTaskDispatcher.enqueueTask(new Runnable(this) {
                            final /* synthetic */ C08311 f2575b;

                            public void run() {
                                droiCallback.result(Boolean.valueOf(save.isOk()), save);
                            }
                        });
                    }
                }
            }, uuid);
            return uuid;
        }
        throw new AssertionError();
    }

    void setDirtyFlag() {
        this.dirtyFlags = 1;
    }

    public void setLocalStorage(boolean z) {
        if (this.localStorage != z) {
            setDirtyFlag();
        }
        this.localStorage = z;
        if (getDepth() > 3) {
            DroiLog.m2870e(LOG_TAG, "Cannot reference more than 3");
            return;
        }
        List<Field> a = C0911l.m2699a(getClass());
        if (a != null) {
            for (Field field : a) {
                try {
                    Object obj = field.get(this);
                    DroiObject droiObject;
                    if (obj instanceof List) {
                        for (Object next : (List) obj) {
                            droiObject = next instanceof DroiReferenceObject ? (DroiObject) ((DroiReferenceObject) next).droiObject() : next instanceof DroiObject ? (DroiObject) next : null;
                            if (droiObject != null) {
                                droiObject.setLocalStorage(z);
                            }
                        }
                    } else if (obj instanceof Map) {
                        for (Object next2 : ((Map) obj).values()) {
                            droiObject = next2 instanceof DroiReferenceObject ? (DroiObject) ((DroiReferenceObject) next2).droiObject() : next2 instanceof DroiObject ? (DroiObject) next2 : null;
                            if (droiObject != null) {
                                droiObject.setLocalStorage(z);
                            }
                        }
                    } else {
                        String d = C0911l.m2710d(field.getType());
                        if (d != null && isExtendedClass(d)) {
                            field.setAccessible(true);
                            DroiObject droiObject2 = (DroiObject) obj;
                            if (droiObject2 != null) {
                                droiObject2.setLocalStorage(z);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    DroiLog.m2870e(LOG_TAG, "setLocalStorage fail. " + e);
                }
            }
        }
    }

    public void setPermission(DroiPermission droiPermission) {
        this.permission = droiPermission;
    }

    JSONObject toJson(DroiError droiError) {
        return toJson(false, droiError);
    }

    JSONObject toJson(boolean z, DroiError droiError) {
        JSONObject jSONObject = new JSONObject();
        StringBuilder stringBuilder = new StringBuilder();
        String str = this.objectName;
        Class cls = getClass();
        if (this.objectName == null || this.objectName.length() == 0) {
            str = cls.getSimpleName();
            if (isExtendedClass(this)) {
                Object obj = 1;
                Object obj2 = str;
            } else {
                throw new IllegalArgumentException("Droi Core SDK doesn't support class type : " + cls.getCanonicalName());
            }
        }
        obj = null;
        String str2 = str;
        try {
            jSONObject.put(C0896c.f2866a, obj2);
            jSONObject.put(C0896c.f2869d, C0895b.m2661a(this.creationTime));
            jSONObject.put(C0896c.f2870e, C0895b.m2661a(this.modifiedTime));
            jSONObject.put(C0896c.f2868c, this.objectId);
            JSONObject permissionWithDefault = getPermissionWithDefault();
            if (permissionWithDefault != null) {
                jSONObject.put(C0896c.f2877l, permissionWithDefault);
            }
            if (isLocalStorage()) {
                jSONObject.put(C0896c.f2880o, this.hashCodeForDroiObject);
                jSONObject.put(C0896c.f2881p, this.dirtyFlags);
            }
            stringBuilder.append(this.objectName);
            stringBuilder.append(this.creationTime.getTime());
            stringBuilder.append(this.objectId);
            stringBuilder.append(permissionWithDefault);
            if (obj != null) {
                ArrayList a = C0911l.m2699a(cls);
                if (a != null) {
                    Iterator it = a.iterator();
                    while (it.hasNext()) {
                        Field field = (Field) it.next();
                        field.setAccessible(true);
                        Object obj3 = field.get(this);
                        if (obj3 != null) {
                            Class type = field.getType();
                            boolean z2 = false;
                            if (((DroiReference) field.getAnnotation(DroiReference.class)) != null) {
                                z2 = true;
                            }
                            if (obj3 instanceof DroiReferenceObject) {
                                obj3 = ((DroiReferenceObject) obj3).droiObject();
                                if (obj3 != null) {
                                    z2 = true;
                                }
                            }
                            if (obj3 instanceof DroiFile) {
                                z2 = true;
                            }
                            toJson(field.getName(), obj3, jSONObject, type, z2, z, stringBuilder, droiError);
                        }
                    }
                }
            }
            if (this.opDroiObjectValueSet != null && this.opDroiObjectValueSet.size() > 0) {
                JSONObject jSONObject2 = obj != null ? new JSONObject() : jSONObject;
                for (String str3 : this.opDroiObjectValueSet.keySet()) {
                    Object obj4 = this.opDroiObjectValueSet.get(str3);
                    if (obj4 != null) {
                        toJson(str3, obj4, jSONObject2, obj4.getClass(), false, z, stringBuilder, droiError);
                    }
                }
                if (obj != null) {
                    jSONObject.put(C0896c.f2872g, jSONObject2);
                }
            }
        } catch (Exception e) {
            DroiLog.m2873w(LOG_TAG, e);
            if (droiError != null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e.toString());
            }
        } catch (Exception e2) {
            DroiLog.m2873w(LOG_TAG, e2);
            if (droiError != null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e2.toString());
            }
        }
        this.hashCodeBuffer = stringBuilder;
        return jSONObject;
    }

    public String toString() {
        JSONObject toJson = toJson(true, null);
        return toJson != null ? toJson.toString() : super.toString() + " [NO DATA]";
    }

    public void writeToParcel(Parcel parcel, int i) {
        JSONObject toJson = toJson(true, null);
        if (toJson != null) {
            parcel.writeString(toJson.toString());
        }
    }
}
