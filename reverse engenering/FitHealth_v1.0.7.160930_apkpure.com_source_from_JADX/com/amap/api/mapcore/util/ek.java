package com.amap.api.mapcore.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: DBOperation */
public class ek {
    private static Map<Class<? extends ej>, ej> f557d = new HashMap();
    private ep f558a;
    private SQLiteDatabase f559b;
    private ej f560c;

    public static synchronized ej m790a(Class<? extends ej> cls) throws IllegalAccessException, InstantiationException {
        ej ejVar;
        synchronized (ek.class) {
            if (f557d.get(cls) == null) {
                f557d.put(cls, cls.newInstance());
            }
            ejVar = (ej) f557d.get(cls);
        }
        return ejVar;
    }

    public ek(Context context, ej ejVar) {
        try {
            this.f558a = new ep(context.getApplicationContext(), ejVar.mo1477b(), null, ejVar.mo1478c(), ejVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        this.f560c = ejVar;
    }

    public static String m793a(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (String str : map.keySet()) {
            Object obj2;
            if (obj != null) {
                stringBuilder.append(str).append(" = '").append((String) map.get(str)).append("'");
                obj2 = null;
            } else {
                stringBuilder.append(" and ").append(str).append(" = '").append((String) map.get(str)).append("'");
                obj2 = obj;
            }
            obj = obj2;
        }
        return stringBuilder.toString();
    }

    public <T> void m804a(String str, Class<T> cls) {
        synchronized (this.f560c) {
            Object a = m792a(m799b((Class) cls));
            if (TextUtils.isEmpty(a)) {
                return;
            }
            this.f559b = m798b(false);
            if (this.f559b == null) {
                return;
            }
            try {
                this.f559b.delete(a, str, null);
                if (this.f559b != null) {
                    this.f559b.close();
                    this.f559b = null;
                }
            } catch (Throwable th) {
                if (this.f559b != null) {
                    this.f559b.close();
                    this.f559b = null;
                }
            }
        }
    }

    public <T> void m806a(String str, Object obj, boolean z) {
        synchronized (this.f560c) {
            if (obj == null) {
                return;
            }
            el b = m799b(obj.getClass());
            Object a = m792a(b);
            if (TextUtils.isEmpty(a)) {
                return;
            }
            ContentValues a2 = m788a(obj, b);
            if (a2 == null) {
                return;
            }
            this.f559b = m798b(z);
            if (this.f559b == null) {
                return;
            }
            try {
                this.f559b.update(a, a2, str, null);
                if (this.f559b != null) {
                    this.f559b.close();
                    this.f559b = null;
                }
            } catch (Throwable th) {
                if (this.f559b != null) {
                    this.f559b.close();
                    this.f559b = null;
                }
            }
        }
    }

    public <T> void m805a(String str, Object obj) {
        m806a(str, obj, false);
    }

    public void m802a(Object obj, String str) {
        synchronized (this.f560c) {
            List b = m808b(str, obj.getClass());
            if (b == null || b.size() == 0) {
                m801a(obj);
            } else {
                m805a(str, obj);
            }
        }
    }

    public <T> void m801a(T t) {
        m803a((Object) t, false);
    }

    public <T> void m803a(T t, boolean z) {
        synchronized (this.f560c) {
            this.f559b = m798b(z);
            if (this.f559b == null) {
                return;
            }
            try {
                m794a(this.f559b, (Object) t);
                if (this.f559b != null) {
                    this.f559b.close();
                    this.f559b = null;
                }
            } catch (Throwable th) {
                if (this.f559b != null) {
                    this.f559b.close();
                    this.f559b = null;
                }
            }
        }
    }

    private <T> void m794a(SQLiteDatabase sQLiteDatabase, T t) {
        el b = m799b(t.getClass());
        Object a = m792a(b);
        if (!TextUtils.isEmpty(a) && t != null && sQLiteDatabase != null) {
            ContentValues a2 = m788a((Object) t, b);
            if (a2 != null) {
                sQLiteDatabase.insert(a, null, a2);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> void m807a(java.util.List<T> r5) {
        /*
        r4 = this;
        r1 = r4.f560c;
        monitor-enter(r1);
        if (r5 == 0) goto L_0x000b;
    L_0x0005:
        r0 = r5.size();	 Catch:{ all -> 0x001a }
        if (r0 != 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r1);	 Catch:{ all -> 0x001a }
    L_0x000c:
        return;
    L_0x000d:
        r0 = 0;
        r0 = r4.m798b(r0);	 Catch:{ all -> 0x001a }
        r4.f559b = r0;	 Catch:{ all -> 0x001a }
        r0 = r4.f559b;	 Catch:{ all -> 0x001a }
        if (r0 != 0) goto L_0x001d;
    L_0x0018:
        monitor-exit(r1);	 Catch:{ all -> 0x001a }
        goto L_0x000c;
    L_0x001a:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x001a }
        throw r0;
    L_0x001d:
        r0 = r4.f559b;	 Catch:{ Throwable -> 0x0036 }
        r0.beginTransaction();	 Catch:{ Throwable -> 0x0036 }
        r0 = r5.iterator();	 Catch:{ Throwable -> 0x0036 }
    L_0x0026:
        r2 = r0.hasNext();	 Catch:{ Throwable -> 0x0036 }
        if (r2 == 0) goto L_0x004d;
    L_0x002c:
        r2 = r0.next();	 Catch:{ Throwable -> 0x0036 }
        r3 = r4.f559b;	 Catch:{ Throwable -> 0x0036 }
        r4.m794a(r3, r2);	 Catch:{ Throwable -> 0x0036 }
        goto L_0x0026;
    L_0x0036:
        r0 = move-exception;
        r2 = "DataBase";
        r3 = "insertListData";
        com.amap.api.mapcore.util.eb.m742a(r0, r2, r3);	 Catch:{ all -> 0x0060 }
        r0 = r4.f559b;	 Catch:{ all -> 0x001a }
        r0.endTransaction();	 Catch:{ all -> 0x001a }
        r0 = r4.f559b;	 Catch:{ all -> 0x001a }
        r0.close();	 Catch:{ all -> 0x001a }
        r0 = 0;
        r4.f559b = r0;	 Catch:{ all -> 0x001a }
    L_0x004b:
        monitor-exit(r1);	 Catch:{ all -> 0x001a }
        goto L_0x000c;
    L_0x004d:
        r0 = r4.f559b;	 Catch:{ Throwable -> 0x0036 }
        r0.setTransactionSuccessful();	 Catch:{ Throwable -> 0x0036 }
        r0 = r4.f559b;	 Catch:{ all -> 0x001a }
        r0.endTransaction();	 Catch:{ all -> 0x001a }
        r0 = r4.f559b;	 Catch:{ all -> 0x001a }
        r0.close();	 Catch:{ all -> 0x001a }
        r0 = 0;
        r4.f559b = r0;	 Catch:{ all -> 0x001a }
        goto L_0x004b;
    L_0x0060:
        r0 = move-exception;
        r2 = r4.f559b;	 Catch:{ all -> 0x001a }
        r2.endTransaction();	 Catch:{ all -> 0x001a }
        r2 = r4.f559b;	 Catch:{ all -> 0x001a }
        r2.close();	 Catch:{ all -> 0x001a }
        r2 = 0;
        r4.f559b = r2;	 Catch:{ all -> 0x001a }
        throw r0;	 Catch:{ all -> 0x001a }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.mapcore.util.ek.a(java.util.List):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.util.List<T> m800a(java.lang.String r13, java.lang.Class<T> r14, boolean r15) {
        /*
        r12 = this;
        r9 = 0;
        r10 = r12.f560c;
        monitor-enter(r10);
        r8 = new java.util.ArrayList;	 Catch:{ all -> 0x0094 }
        r8.<init>();	 Catch:{ all -> 0x0094 }
        r11 = r12.m799b(r14);	 Catch:{ all -> 0x0094 }
        r1 = r12.m792a(r11);	 Catch:{ all -> 0x0094 }
        r0 = r12.f559b;	 Catch:{ all -> 0x0094 }
        if (r0 != 0) goto L_0x001b;
    L_0x0015:
        r0 = r12.m789a(r15);	 Catch:{ all -> 0x0094 }
        r12.f559b = r0;	 Catch:{ all -> 0x0094 }
    L_0x001b:
        r0 = r12.f559b;	 Catch:{ all -> 0x0094 }
        if (r0 == 0) goto L_0x0027;
    L_0x001f:
        r0 = android.text.TextUtils.isEmpty(r1);	 Catch:{ all -> 0x0094 }
        if (r0 != 0) goto L_0x0027;
    L_0x0025:
        if (r13 != 0) goto L_0x002a;
    L_0x0027:
        monitor-exit(r10);	 Catch:{ all -> 0x0094 }
        r0 = r8;
    L_0x0029:
        return r0;
    L_0x002a:
        r0 = r12.f559b;	 Catch:{ Throwable -> 0x0106, all -> 0x0080 }
        r2 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r3 = r13;
        r1 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x0106, all -> 0x0080 }
        if (r1 != 0) goto L_0x0054;
    L_0x0038:
        r0 = r12.f559b;	 Catch:{ Throwable -> 0x0062 }
        r0.close();	 Catch:{ Throwable -> 0x0062 }
        r0 = 0;
        r12.f559b = r0;	 Catch:{ Throwable -> 0x0062 }
        if (r1 == 0) goto L_0x0045;
    L_0x0042:
        r1.close();	 Catch:{ Throwable -> 0x00c3 }
    L_0x0045:
        r0 = r12.f559b;	 Catch:{ Throwable -> 0x00cf }
        if (r0 == 0) goto L_0x0051;
    L_0x0049:
        r0 = r12.f559b;	 Catch:{ Throwable -> 0x00cf }
        r0.close();	 Catch:{ Throwable -> 0x00cf }
        r0 = 0;
        r12.f559b = r0;	 Catch:{ Throwable -> 0x00cf }
    L_0x0051:
        monitor-exit(r10);	 Catch:{ all -> 0x0094 }
        r0 = r8;
        goto L_0x0029;
    L_0x0054:
        r0 = r1.moveToNext();	 Catch:{ Throwable -> 0x0062 }
        if (r0 == 0) goto L_0x00db;
    L_0x005a:
        r0 = r12.m791a(r1, r14, r11);	 Catch:{ Throwable -> 0x0062 }
        r8.add(r0);	 Catch:{ Throwable -> 0x0062 }
        goto L_0x0054;
    L_0x0062:
        r0 = move-exception;
    L_0x0063:
        if (r15 != 0) goto L_0x006c;
    L_0x0065:
        r2 = "DataBase";
        r3 = "searchListData";
        com.amap.api.mapcore.util.eb.m742a(r0, r2, r3);	 Catch:{ all -> 0x0103 }
    L_0x006c:
        if (r1 == 0) goto L_0x0071;
    L_0x006e:
        r1.close();	 Catch:{ Throwable -> 0x00ad }
    L_0x0071:
        r0 = r12.f559b;	 Catch:{ Throwable -> 0x00b8 }
        if (r0 == 0) goto L_0x007d;
    L_0x0075:
        r0 = r12.f559b;	 Catch:{ Throwable -> 0x00b8 }
        r0.close();	 Catch:{ Throwable -> 0x00b8 }
        r0 = 0;
        r12.f559b = r0;	 Catch:{ Throwable -> 0x00b8 }
    L_0x007d:
        monitor-exit(r10);	 Catch:{ all -> 0x0094 }
        r0 = r8;
        goto L_0x0029;
    L_0x0080:
        r0 = move-exception;
        r1 = r9;
    L_0x0082:
        if (r1 == 0) goto L_0x0087;
    L_0x0084:
        r1.close();	 Catch:{ Throwable -> 0x0097 }
    L_0x0087:
        r1 = r12.f559b;	 Catch:{ Throwable -> 0x00a2 }
        if (r1 == 0) goto L_0x0093;
    L_0x008b:
        r1 = r12.f559b;	 Catch:{ Throwable -> 0x00a2 }
        r1.close();	 Catch:{ Throwable -> 0x00a2 }
        r1 = 0;
        r12.f559b = r1;	 Catch:{ Throwable -> 0x00a2 }
    L_0x0093:
        throw r0;	 Catch:{ all -> 0x0094 }
    L_0x0094:
        r0 = move-exception;
        monitor-exit(r10);	 Catch:{ all -> 0x0094 }
        throw r0;
    L_0x0097:
        r1 = move-exception;
        if (r15 != 0) goto L_0x0087;
    L_0x009a:
        r2 = "DataBase";
        r3 = "searchListData";
        com.amap.api.mapcore.util.eb.m742a(r1, r2, r3);	 Catch:{ all -> 0x0094 }
        goto L_0x0087;
    L_0x00a2:
        r1 = move-exception;
        if (r15 != 0) goto L_0x0093;
    L_0x00a5:
        r2 = "DataBase";
        r3 = "searchListData";
        com.amap.api.mapcore.util.eb.m742a(r1, r2, r3);	 Catch:{ all -> 0x0094 }
        goto L_0x0093;
    L_0x00ad:
        r0 = move-exception;
        if (r15 != 0) goto L_0x0071;
    L_0x00b0:
        r1 = "DataBase";
        r2 = "searchListData";
        com.amap.api.mapcore.util.eb.m742a(r0, r1, r2);	 Catch:{ all -> 0x0094 }
        goto L_0x0071;
    L_0x00b8:
        r0 = move-exception;
        if (r15 != 0) goto L_0x007d;
    L_0x00bb:
        r1 = "DataBase";
        r2 = "searchListData";
        com.amap.api.mapcore.util.eb.m742a(r0, r1, r2);	 Catch:{ all -> 0x0094 }
        goto L_0x007d;
    L_0x00c3:
        r0 = move-exception;
        if (r15 != 0) goto L_0x0045;
    L_0x00c6:
        r1 = "DataBase";
        r2 = "searchListData";
        com.amap.api.mapcore.util.eb.m742a(r0, r1, r2);	 Catch:{ all -> 0x0094 }
        goto L_0x0045;
    L_0x00cf:
        r0 = move-exception;
        if (r15 != 0) goto L_0x0051;
    L_0x00d2:
        r1 = "DataBase";
        r2 = "searchListData";
        com.amap.api.mapcore.util.eb.m742a(r0, r1, r2);	 Catch:{ all -> 0x0094 }
        goto L_0x0051;
    L_0x00db:
        if (r1 == 0) goto L_0x00e0;
    L_0x00dd:
        r1.close();	 Catch:{ Throwable -> 0x00f8 }
    L_0x00e0:
        r0 = r12.f559b;	 Catch:{ Throwable -> 0x00ed }
        if (r0 == 0) goto L_0x007d;
    L_0x00e4:
        r0 = r12.f559b;	 Catch:{ Throwable -> 0x00ed }
        r0.close();	 Catch:{ Throwable -> 0x00ed }
        r0 = 0;
        r12.f559b = r0;	 Catch:{ Throwable -> 0x00ed }
        goto L_0x007d;
    L_0x00ed:
        r0 = move-exception;
        if (r15 != 0) goto L_0x007d;
    L_0x00f0:
        r1 = "DataBase";
        r2 = "searchListData";
        com.amap.api.mapcore.util.eb.m742a(r0, r1, r2);	 Catch:{ all -> 0x0094 }
        goto L_0x007d;
    L_0x00f8:
        r0 = move-exception;
        if (r15 != 0) goto L_0x00e0;
    L_0x00fb:
        r1 = "DataBase";
        r2 = "searchListData";
        com.amap.api.mapcore.util.eb.m742a(r0, r1, r2);	 Catch:{ all -> 0x0094 }
        goto L_0x00e0;
    L_0x0103:
        r0 = move-exception;
        goto L_0x0082;
    L_0x0106:
        r0 = move-exception;
        r1 = r9;
        goto L_0x0063;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.mapcore.util.ek.a(java.lang.String, java.lang.Class, boolean):java.util.List<T>");
    }

    public <T> List<T> m808b(String str, Class<T> cls) {
        return m800a(str, (Class) cls, false);
    }

    private <T> T m791a(Cursor cursor, Class<T> cls, el elVar) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Field[] a = m797a((Class) cls, elVar.m810b());
        Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
        declaredConstructor.setAccessible(true);
        T newInstance = declaredConstructor.newInstance(new Object[0]);
        for (Field field : a) {
            field.setAccessible(true);
            Annotation annotation = field.getAnnotation(em.class);
            if (annotation != null) {
                em emVar = (em) annotation;
                int b = emVar.m812b();
                int columnIndex = cursor.getColumnIndex(emVar.m811a());
                switch (b) {
                    case 1:
                        field.set(newInstance, Short.valueOf(cursor.getShort(columnIndex)));
                        break;
                    case 2:
                        field.set(newInstance, Integer.valueOf(cursor.getInt(columnIndex)));
                        break;
                    case 3:
                        field.set(newInstance, Float.valueOf(cursor.getFloat(columnIndex)));
                        break;
                    case 4:
                        field.set(newInstance, Double.valueOf(cursor.getDouble(columnIndex)));
                        break;
                    case 5:
                        field.set(newInstance, Long.valueOf(cursor.getLong(columnIndex)));
                        break;
                    case 6:
                        field.set(newInstance, cursor.getString(columnIndex));
                        break;
                    case 7:
                        field.set(newInstance, cursor.getBlob(columnIndex));
                        break;
                    default:
                        break;
                }
            }
        }
        return newInstance;
    }

    private void m795a(Object obj, Field field, ContentValues contentValues) {
        Annotation annotation = field.getAnnotation(em.class);
        if (annotation != null) {
            em emVar = (em) annotation;
            switch (emVar.m812b()) {
                case 1:
                    try {
                        contentValues.put(emVar.m811a(), Short.valueOf(field.getShort(obj)));
                        return;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                case 2:
                    contentValues.put(emVar.m811a(), Integer.valueOf(field.getInt(obj)));
                    return;
                case 3:
                    contentValues.put(emVar.m811a(), Float.valueOf(field.getFloat(obj)));
                    return;
                case 4:
                    contentValues.put(emVar.m811a(), Double.valueOf(field.getDouble(obj)));
                    return;
                case 5:
                    contentValues.put(emVar.m811a(), Long.valueOf(field.getLong(obj)));
                    return;
                case 6:
                    String str = "";
                    contentValues.put(emVar.m811a(), (String) field.get(obj));
                    return;
                case 7:
                    contentValues.put(emVar.m811a(), (byte[]) field.get(obj));
                    return;
                default:
                    return;
            }
            e.printStackTrace();
        }
    }

    private ContentValues m788a(Object obj, el elVar) {
        ContentValues contentValues = new ContentValues();
        for (Field field : m797a(obj.getClass(), elVar.m810b())) {
            field.setAccessible(true);
            m795a(obj, field, contentValues);
        }
        return contentValues;
    }

    private Field[] m797a(Class<?> cls, boolean z) {
        if (cls == null) {
            return null;
        }
        if (z) {
            return cls.getSuperclass().getDeclaredFields();
        }
        return cls.getDeclaredFields();
    }

    private SQLiteDatabase m789a(boolean z) {
        try {
            if (this.f559b == null) {
                this.f559b = this.f558a.getReadableDatabase();
            }
        } catch (Throwable th) {
            if (z) {
                th.printStackTrace();
            } else {
                eb.m742a(th, "DBOperation", "getReadAbleDataBase");
            }
        }
        return this.f559b;
    }

    private SQLiteDatabase m798b(boolean z) {
        try {
            if (this.f559b == null || this.f559b.isReadOnly()) {
                if (this.f559b != null) {
                    this.f559b.close();
                }
                this.f559b = this.f558a.getWritableDatabase();
            }
        } catch (Throwable th) {
            eb.m742a(th, "DBOperation", "getWriteDatabase");
        }
        return this.f559b;
    }

    private boolean m796a(Annotation annotation) {
        return annotation != null;
    }

    private <T> String m792a(el elVar) {
        if (elVar == null) {
            return null;
        }
        return elVar.m809a();
    }

    private <T> el m799b(Class<T> cls) {
        Annotation annotation = cls.getAnnotation(el.class);
        if (m796a(annotation)) {
            return (el) annotation;
        }
        return null;
    }
}
