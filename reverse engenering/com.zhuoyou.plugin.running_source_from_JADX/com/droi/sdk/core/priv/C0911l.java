package com.droi.sdk.core.priv;

import android.util.SparseArray;
import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiObjectName;
import com.droi.sdk.core.DroiReference;
import com.droi.sdk.core.DroiReferenceObject;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.internal.DroiLog;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class C0911l {
    private static HashMap<String, ArrayList<Field>> f2955a = new HashMap();
    private static HashMap<String, HashMap<String, Type>> f2956b = new HashMap();
    private static SparseArray<HashMap<String, Field>> f2957c = new SparseArray();
    private static HashMap<String, Number> f2958d = new HashMap();
    private static SparseArray<String> f2959e = new SparseArray();
    private static SparseArray<String> f2960f = new SparseArray();
    private static SparseArray<String> f2961g = new SparseArray();

    public static int m2698a(String str) {
        return f2958d.containsKey(str) ? ((Number) f2958d.get(str)).intValue() : 0;
    }

    public static <T extends DroiObject> ArrayList<Field> m2699a(Class<T> cls) {
        ArrayList<Field> arrayList = (ArrayList) f2955a.get(C0911l.m2708c((Class) cls));
        if (arrayList != null) {
            return arrayList;
        }
        C0911l.m2712f(cls);
        return (ArrayList) f2955a.get(C0911l.m2708c((Class) cls));
    }

    public static <T extends DroiObject> HashMap<String, Field> m2700a(Class<T> cls, String str) {
        if (cls == null || str == null) {
            return null;
        }
        int hashCode = cls != null ? C0911l.m2708c((Class) cls).hashCode() : str.hashCode();
        HashMap<String, Field> hashMap = (HashMap) f2957c.get(hashCode);
        if (hashMap != null) {
            return hashMap;
        }
        C0911l.m2712f(cls);
        return (HashMap) f2957c.get(hashCode);
    }

    public static boolean m2701a(Class cls, Class cls2) {
        while (cls != null) {
            cls = cls.getSuperclass();
            if (cls2.equals(cls)) {
                return true;
            }
        }
        return false;
    }

    public static boolean m2702a(Object obj) {
        return (obj instanceof String) || (obj instanceof Number) || (obj instanceof Boolean);
    }

    public static boolean m2703a(Type type) {
        if (C0911l.m2707b(type)) {
            return true;
        }
        Class cls = (Class) type;
        return cls != null && (Number.class.isAssignableFrom(cls) || DroiObject.class.isAssignableFrom(cls) || List.class.isAssignableFrom(cls) || DroiReferenceObject.class.isAssignableFrom(cls) || Map.class.isAssignableFrom(cls) || byte[].class.isAssignableFrom(cls) || String.class.isAssignableFrom(cls) || Date.class.isAssignableFrom(cls));
    }

    public static String m2704b(String str) {
        return (String) f2961g.get(str.hashCode(), null);
    }

    public static Field m2705b(Class cls, String str) {
        Field field = null;
        if (cls != null) {
            do {
                try {
                    field = cls.getDeclaredField(str);
                } catch (NoSuchFieldException e) {
                }
                cls = cls.getSuperclass();
                if (field != null || cls == null) {
                    break;
                }
            } while (cls != DroiObject.class);
        }
        return field;
    }

    public static <T extends DroiObject> HashMap<String, Type> m2706b(Class<T> cls) {
        HashMap<String, Type> hashMap = (HashMap) f2956b.get(C0911l.m2708c((Class) cls));
        if (hashMap != null) {
            return hashMap;
        }
        C0911l.m2712f(cls);
        return (HashMap) f2956b.get(C0911l.m2708c((Class) cls));
    }

    public static boolean m2707b(Type type) {
        return type == String.class || type == Number.class || type == Boolean.class || type == Boolean.TYPE || type == byte[].class || type == Integer.TYPE || type == Float.TYPE || type == Long.TYPE || type == Short.TYPE || type == Byte.TYPE || type == Double.TYPE;
    }

    public static String m2708c(Class cls) {
        int hashCode = cls.hashCode();
        if (f2959e.indexOfKey(hashCode) >= 0) {
            return (String) f2959e.get(hashCode);
        }
        String canonicalName = cls.getCanonicalName();
        f2959e.put(hashCode, canonicalName);
        return canonicalName;
    }

    public static boolean m2709c(Type type) {
        Class cls = (Class) type;
        return cls != null && (List.class.isAssignableFrom(cls) || Map.class.isAssignableFrom(cls) || byte[].class.isAssignableFrom(cls));
    }

    public static String m2710d(Class cls) {
        String canonicalName = cls.getCanonicalName();
        int hashCode = canonicalName.hashCode();
        if (f2960f.indexOfKey(hashCode) >= 0) {
            return (String) f2960f.get(hashCode);
        }
        String value;
        if (cls.equals(DroiUser.class) || !C0911l.m2701a(cls, DroiUser.class)) {
            DroiObjectName droiObjectName = (DroiObjectName) cls.getAnnotation(DroiObjectName.class);
            if (droiObjectName != null) {
                value = droiObjectName.value();
            } else {
                int lastIndexOf = canonicalName.lastIndexOf(".");
                value = lastIndexOf >= 0 ? canonicalName.substring(lastIndexOf + 1) : null;
            }
            f2960f.put(hashCode, value);
            f2961g.put(hashCode, value);
            return value;
        }
        lastIndexOf = DroiUser.class.getCanonicalName().hashCode();
        value = f2960f.indexOfKey(lastIndexOf) >= 0 ? (String) f2960f.get(lastIndexOf) : "_User";
        f2960f.put(hashCode, value);
        f2961g.put(hashCode, value);
        return value;
    }

    public static <T> T m2711e(Class<? extends T> cls) {
        T t = null;
        if (cls != null) {
            try {
                Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
                if (declaredConstructor != null) {
                    declaredConstructor.setAccessible(true);
                    t = declaredConstructor.newInstance(new Object[0]);
                }
            } catch (Exception e) {
            }
        }
        return t;
    }

    private static <T extends DroiObject> void m2712f(Class<T> cls) {
        String c = C0911l.m2708c((Class) cls);
        int hashCode = c.hashCode();
        if (((ArrayList) f2955a.get(c)) == null) {
            StringBuilder stringBuilder;
            Object[] toArray;
            ArrayList arrayList = new ArrayList();
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            Class cls2 = cls;
            while (true) {
                for (Field field : cls2.getDeclaredFields()) {
                    DroiReference droiReference = (DroiReference) field.getAnnotation(DroiReference.class);
                    if (((DroiExpose) field.getAnnotation(DroiExpose.class)) != null || droiReference != null) {
                        if (droiReference == null || !field.getType().isAssignableFrom(DroiObject.class)) {
                            Type genericType = field.getGenericType();
                            if (C0911l.m2703a(field.getType())) {
                                if (genericType instanceof ParameterizedType) {
                                    for (Type genericType2 : ((ParameterizedType) genericType2).getActualTypeArguments()) {
                                        if (!C0911l.m2703a(genericType2)) {
                                            if ((genericType2 instanceof Class) && (genericType2 instanceof DroiObject)) {
                                                C0911l.m2712f((Class) genericType2);
                                            } else {
                                                DroiLog.m2870e("CoreService", "isNotValidType - " + genericType2.toString() + ", Name is " + field.getName());
                                            }
                                        }
                                    }
                                } else if (!(genericType2 instanceof Class)) {
                                    DroiLog.m2870e("CoreService", "isNotValidType" + hashMap.toString() + ", Name is " + field.getName());
                                } else if (!(C0911l.m2709c(genericType2) || C0911l.m2703a(genericType2))) {
                                    DroiLog.m2870e("CoreService", "isNotValidType" + hashMap.toString() + ", Name is " + field.getName());
                                }
                                String name = field.getName();
                                arrayList.add(field);
                                hashMap.put(name, field.getType());
                                hashMap2.put(name, field);
                            } else {
                                DroiLog.m2870e("CoreService", "isNotValidType - " + cls.toString() + ", Name is " + field.getName());
                            }
                        } else {
                            throw new IllegalArgumentException("Reference Type should extend from DroiObject or is DroiObject");
                        }
                    }
                }
                Class superclass = cls2.getSuperclass();
                if (superclass == null || superclass == DroiObject.class) {
                    f2955a.put(c, arrayList);
                    f2956b.put(c, hashMap);
                    f2957c.put(hashCode, hashMap2);
                    stringBuilder = new StringBuilder();
                    toArray = hashMap.keySet().toArray();
                    Arrays.sort(toArray);
                } else {
                    cls2 = superclass;
                }
            }
            f2955a.put(c, arrayList);
            f2956b.put(c, hashMap);
            f2957c.put(hashCode, hashMap2);
            stringBuilder = new StringBuilder();
            toArray = hashMap.keySet().toArray();
            Arrays.sort(toArray);
            for (Object obj : toArray) {
                stringBuilder.append(obj.toString()).append(((Type) hashMap.get(obj)).toString());
            }
            f2958d.put(c, Integer.valueOf(stringBuilder.toString().hashCode()));
        }
    }
}
