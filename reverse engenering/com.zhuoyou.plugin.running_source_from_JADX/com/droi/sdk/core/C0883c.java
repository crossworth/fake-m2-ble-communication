package com.droi.sdk.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition.Type;
import com.droi.sdk.core.DroiQuery.Builder;
import com.droi.sdk.core.priv.C0782a;
import com.droi.sdk.core.priv.C0895b;
import com.droi.sdk.core.priv.C0896c;
import com.droi.sdk.core.priv.C0903g;
import com.droi.sdk.core.priv.C0911l;
import com.droi.sdk.internal.DroiLog;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class C0883c extends C0782a {
    private static final String f2823a = "LOCAL_STORAGE_DB_HELPER";
    private static final String f2824c = "LocalStorageDBHelperDBWriter";
    private static C0883c f2825d = null;
    private C0882a f2826b = null;

    private class C0882a extends SQLiteOpenHelper {
        final /* synthetic */ C0883c f2822a;

        private C0882a(C0883c c0883c, Context context) {
            this.f2822a = c0883c;
            super(context, "opdb.db", null, 1);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    private C0883c(Context context) {
        this.f2826b = new C0882a(context);
    }

    private DroiError m2627a(DroiObject droiObject, String str) {
        String c;
        String str2;
        Object obj;
        DroiObject droiObject2;
        mo1882a((Object) droiObject, str);
        Class customClassWithClassName = DroiObject.getCustomClassWithClassName(str);
        if (customClassWithClassName != null) {
            String d = C0911l.m2710d(customClassWithClassName);
            c = C0911l.m2708c(customClassWithClassName);
            str2 = "T" + Integer.toHexString(d.hashCode()) + Integer.toHexString(C0911l.m2698a(c));
        } else {
            c = str;
            str2 = str;
        }
        final ContentValues contentValues = new ContentValues();
        contentValues.put(C0896c.f2868c, droiObject.getObjectId());
        contentValues.put(C0896c.f2866a, str);
        contentValues.put(C0896c.f2869d, C0895b.m2661a(droiObject.getCreationTime()));
        contentValues.put(C0896c.f2870e, C0895b.m2661a(droiObject.getModifiedTime()));
        if (droiObject.getPermission() != null) {
            contentValues.put(C0896c.f2877l, droiObject.getPermission().toJson().toString());
        }
        Class cls = droiObject.getClass();
        if (!c.equals(C0911l.m2708c(DroiObject.class))) {
            for (Entry entry : C0911l.m2706b(cls).entrySet()) {
                Field b = C0911l.m2705b(cls, (String) entry.getKey());
                if (b != null) {
                    boolean z = ((DroiReference) b.getAnnotation(DroiReference.class)) != null;
                    b.setAccessible(true);
                    obj = null;
                    try {
                        obj = b.get(droiObject);
                    } catch (Exception e) {
                        DroiLog.m2873w(f2823a, e);
                    }
                    if (obj != null && ((obj instanceof DroiReferenceObject) || (obj instanceof DroiFile))) {
                        z = true;
                    }
                    if (z) {
                        droiObject2 = obj instanceof DroiReferenceObject ? (DroiObject) ((DroiReferenceObject) obj).droiObject() : (DroiObject) obj;
                        if (droiObject2 != null) {
                            if (droiObject2.isDirty()) {
                                droiObject2.setLocalStorage(true);
                                droiObject2.save();
                            }
                            JSONObject jSONObject = new JSONObject();
                            try {
                                jSONObject.put(C0896c.f2871f, droiObject2.getClass().getSimpleName());
                                jSONObject.put(C0896c.f2868c, droiObject2.getObjectId());
                            } catch (Exception e2) {
                                DroiLog.m2873w(f2823a, e2);
                            }
                            contentValues.put((String) entry.getKey(), jSONObject.toString());
                        }
                    } else if (obj != null) {
                        if (obj instanceof DroiObject) {
                            m2639a((DroiObject) obj, contentValues, "/" + ((String) entry.getKey()) + "/");
                        } else {
                            contentValues.put((String) entry.getKey(), m2635a(obj, false));
                        }
                    }
                }
            }
        }
        ArrayList keys = droiObject.getKeys();
        if (keys != null) {
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                c = (String) it.next();
                obj = droiObject.get(c);
                if (obj instanceof DroiObject) {
                    m2639a((DroiObject) obj, contentValues, "/" + c + "/");
                } else if (obj instanceof DroiReferenceObject) {
                    droiObject2 = (DroiObject) ((DroiReferenceObject) obj).droiObject();
                    if (droiObject2 != null) {
                        if (droiObject2.isDirty()) {
                            droiObject2.setLocalStorage(true);
                            droiObject2.save();
                        }
                        JSONObject jSONObject2 = new JSONObject();
                        try {
                            jSONObject2.put(C0896c.f2871f, droiObject2.getClass().getSimpleName());
                            jSONObject2.put(C0896c.f2868c, droiObject2.getObjectId());
                        } catch (Exception e22) {
                            DroiLog.m2873w(f2823a, e22);
                        }
                        contentValues.put(c, jSONObject2.toString());
                    }
                } else {
                    contentValues.put(c, m2635a(obj, false));
                }
            }
        }
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(f2824c);
        final DroiError droiError = new DroiError();
        synchronized (this) {
            dispatcher.enqueueTask(new Runnable(this) {
                final /* synthetic */ C0883c f2817d;

                public void run() {
                    try {
                        this.f2817d.f2826b.getWritableDatabase().insertWithOnConflict(str2, null, contentValues, 5);
                    } catch (Exception e) {
                        DroiLog.m2873w(C0883c.f2823a, e);
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage("Save to local fail.");
                    }
                    synchronized (this.f2817d) {
                        this.f2817d.notifyAll();
                    }
                }
            });
            try {
                wait();
            } catch (Exception e3) {
                DroiLog.m2873w(f2823a, e3);
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e3.toString());
            }
        }
        return droiError;
    }

    private DroiError m2628a(DroiObject droiObject, String str, C0903g<String, Object> c0903g) {
        Class customClassWithClassName = DroiObject.getCustomClassWithClassName(str);
        if (customClassWithClassName != null) {
            str = "T" + Integer.toHexString(C0911l.m2710d(customClassWithClassName).hashCode()) + Integer.toHexString(C0911l.m2698a(C0911l.m2708c(customClassWithClassName)));
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (droiObject != null) {
            stringBuilder.append(C0896c.f2868c).append(" = '").append(droiObject.getObjectId()).append("'");
        } else {
            stringBuilder.append(m2634a((C0903g) c0903g, false));
        }
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(f2824c);
        final String stringBuilder2 = stringBuilder.toString();
        final DroiError droiError = new DroiError();
        synchronized (this) {
            dispatcher.enqueueTask(new Runnable(this) {
                final /* synthetic */ C0883c f2821d;

                public void run() {
                    try {
                        this.f2821d.f2826b.getWritableDatabase().delete(str, stringBuilder2, null);
                    } catch (Exception e) {
                        DroiLog.m2873w(C0883c.f2823a, e);
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage(e.toString());
                    }
                    synchronized (this.f2821d) {
                        this.f2821d.notifyAll();
                    }
                }
            });
            try {
                wait();
            } catch (Exception e) {
                DroiLog.m2873w(f2823a, e);
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e.toString());
            }
        }
        return droiError;
    }

    public static C0883c m2630a(Context context) {
        if (f2825d == null) {
            f2825d = new C0883c(context);
        }
        return f2825d;
    }

    private Object m2631a(C0903g<String, Object> c0903g, String str) {
        List c = c0903g.m2675c(str);
        return c.size() < 1 ? null : c.get(0);
    }

    private Object m2632a(String str, Object obj) {
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

    private String m2633a(DroiObject droiObject, Class cls, String str) {
        Object obj;
        Exception exception;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(", [").append(str).append(C0896c.f2868c).append("] String").append(", [").append(str).append(C0896c.f2869d).append("] STRING").append(", [").append(str).append(C0896c.f2870e).append("] STRING").append(", [").append(str).append(C0896c.f2877l).append("] STRING");
        if (!C0911l.m2708c(cls).equals(C0911l.m2708c(DroiObject.class))) {
            for (Entry entry : C0911l.m2706b(cls).entrySet()) {
                Field b = C0911l.m2705b(cls, (String) entry.getKey());
                if (b != null) {
                    boolean z = ((DroiReference) b.getAnnotation(DroiReference.class)) != null;
                    Class type = b.getType();
                    b.setAccessible(true);
                    try {
                        obj = b.get(droiObject);
                        try {
                            if (type == DroiReferenceObject.class || type == DroiFile.class) {
                                if (obj != null) {
                                    try {
                                        if (type == DroiReferenceObject.class) {
                                            obj = ((DroiReferenceObject) obj).droiObject();
                                            z = true;
                                        }
                                    } catch (Exception e) {
                                        exception = e;
                                        z = true;
                                        DroiLog.m2873w(f2823a, exception);
                                        if (!!DroiObject.isExtendedClass(type)) {
                                        }
                                        if (!z) {
                                            stringBuilder.append(", [").append(str).append((String) entry.getKey()).append("] TEXT");
                                        } else if (obj == null) {
                                            stringBuilder.append(", [").append(str).append((String) entry.getKey()).append("] TEXT");
                                        } else {
                                            stringBuilder.append(m2633a((DroiObject) obj, obj.getClass(), str + ((String) entry.getKey()) + "/"));
                                        }
                                    }
                                }
                                z = true;
                            }
                        } catch (IllegalAccessException e2) {
                            exception = e2;
                            DroiLog.m2873w(f2823a, exception);
                            if (!DroiObject.isExtendedClass(type)) {
                            }
                            if (!z) {
                                stringBuilder.append(", [").append(str).append((String) entry.getKey()).append("] TEXT");
                            } else if (obj == null) {
                                stringBuilder.append(", [").append(str).append((String) entry.getKey()).append("] TEXT");
                            } else {
                                stringBuilder.append(m2633a((DroiObject) obj, obj.getClass(), str + ((String) entry.getKey()) + "/"));
                            }
                        }
                    } catch (Exception e3) {
                        Exception exception2 = e3;
                        obj = null;
                        exception = exception2;
                        DroiLog.m2873w(f2823a, exception);
                        if (!DroiObject.isExtendedClass(type)) {
                        }
                        if (!z) {
                            stringBuilder.append(", [").append(str).append((String) entry.getKey()).append("] TEXT");
                        } else if (obj == null) {
                            stringBuilder.append(m2633a((DroiObject) obj, obj.getClass(), str + ((String) entry.getKey()) + "/"));
                        } else {
                            stringBuilder.append(", [").append(str).append((String) entry.getKey()).append("] TEXT");
                        }
                    }
                    if (!DroiObject.isExtendedClass(type) && !z) {
                        stringBuilder.append(", [").append(str).append((String) entry.getKey()).append("] ").append(m2422a(type));
                    } else if (!z) {
                        stringBuilder.append(", [").append(str).append((String) entry.getKey()).append("] TEXT");
                    } else if (obj == null) {
                        stringBuilder.append(m2633a((DroiObject) obj, obj.getClass(), str + ((String) entry.getKey()) + "/"));
                    } else {
                        stringBuilder.append(", [").append(str).append((String) entry.getKey()).append("] TEXT");
                    }
                }
            }
        }
        ArrayList keys = droiObject.getKeys();
        if (keys != null) {
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                obj = droiObject.get(str2);
                if ((obj instanceof DroiFile) || (obj instanceof DroiReferenceObject)) {
                    stringBuilder.append(", [").append(str2).append("] TEXT");
                } else if (obj instanceof DroiObject) {
                    stringBuilder.append(m2633a((DroiObject) obj, obj.getClass(), str + str2 + "/"));
                } else {
                    stringBuilder.append(", [").append(str2).append("] ").append(m2423a(obj));
                }
            }
        }
        return stringBuilder.toString();
    }

    private String m2634a(C0903g<String, Object> c0903g, boolean z) {
        StringBuilder stringBuilder = new StringBuilder();
        if (c0903g.m2673b(Builder.f2645j)) {
            if (z) {
                stringBuilder.append(" WHERE ");
            }
            DroiCondition droiCondition = (DroiCondition) m2631a((C0903g) c0903g, Builder.f2645j);
            if (C0871a.m2608a(droiCondition).equals(Builder.f2646k)) {
                stringBuilder.append(" ").append(m2637a((ArrayList) C0871a.m2609b(droiCondition).get(0))).append(" ");
            } else {
                m2638a(droiCondition, stringBuilder);
            }
        }
        return stringBuilder.toString();
    }

    private String m2635a(Object obj, boolean z) {
        String str = "";
        if ((obj instanceof DroiReferenceObject) || z || DroiObject.isExtendedClass(obj.getClass())) {
            DroiObject droiObject;
            if (obj instanceof DroiReferenceObject) {
                droiObject = (DroiObject) ((DroiReferenceObject) obj).droiObject();
                z = true;
            } else {
                droiObject = (DroiObject) obj;
            }
            if (droiObject == null) {
                return str;
            }
            JSONObject jSONObject;
            if (z) {
                if (droiObject.isDirty()) {
                    droiObject.setLocalStorage(true);
                    droiObject.save();
                }
                jSONObject = new JSONObject();
                try {
                    jSONObject.put(C0896c.f2871f, droiObject.getClass().getSimpleName());
                    jSONObject.put(C0896c.f2868c, droiObject.getObjectId());
                } catch (Exception e) {
                    DroiLog.m2873w(f2823a, e);
                }
            } else {
                jSONObject = droiObject.toJson(null);
            }
            return jSONObject.toString();
        } else if (obj instanceof Date) {
            return C0895b.m2661a((Date) obj);
        } else {
            if (obj instanceof List) {
                JSONArray jSONArray = new JSONArray();
                for (Object a : (List) obj) {
                    jSONArray.put(m2635a(a, false));
                }
                return jSONArray.toString();
            } else if (!(obj instanceof Map)) {
                return obj.toString();
            } else {
                JSONObject jSONObject2 = new JSONObject();
                Map map = (Map) obj;
                for (Object next : map.keySet()) {
                    try {
                        jSONObject2.put((String) next, m2635a(map.get(next), false));
                    } catch (JSONException e2) {
                    }
                }
                return jSONObject2.toString();
            }
        }
    }

    private String m2636a(String str) {
        String[] split = str.split("\\.");
        if (split.length <= 1) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (String str2 : split) {
            stringBuilder.append("/");
            stringBuilder.append(str2);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String m2637a(java.util.ArrayList r9) {
        /*
        r8 = this;
        r2 = 0;
        r6 = 2;
        r3 = 1;
        r0 = r9.size();
        if (r0 >= r6) goto L_0x000c;
    L_0x0009:
        r0 = "";
    L_0x000b:
        return r0;
    L_0x000c:
        r0 = r9.get(r2);
        r0 = (java.lang.String) r0;
        r0 = r8.m2636a(r0);
        r1 = r9.size();
        if (r1 <= r6) goto L_0x007c;
    L_0x001c:
        r1 = r9.get(r6);
        r1 = r1 instanceof java.util.Date;
        if (r1 == 0) goto L_0x007c;
    L_0x0024:
        r1 = new java.lang.StringBuilder;
        r4 = "datetime(";
        r1.<init>(r4);
        r0 = r1.append(r0);
        r1 = ")";
        r0 = r0.append(r1);
        r1 = r0;
    L_0x0036:
        r0 = r9.get(r3);
        r0 = (java.lang.String) r0;
        r4 = -1;
        r5 = r0.hashCode();
        switch(r5) {
            case -2125979215: goto L_0x0109;
            case -1768228930: goto L_0x00ce;
            case -1623290288: goto L_0x0115;
            case -1492549750: goto L_0x00e5;
            case -1069493097: goto L_0x00c3;
            case -778356042: goto L_0x00af;
            case -654238959: goto L_0x0091;
            case -43531407: goto L_0x00f1;
            case 2220: goto L_0x009b;
            case 2285: goto L_0x00b9;
            case 2341: goto L_0x0121;
            case 2440: goto L_0x0087;
            case 77178: goto L_0x00a5;
            case 77299: goto L_0x012d;
            case 215180831: goto L_0x00d9;
            case 1939878354: goto L_0x00fd;
            default: goto L_0x0044;
        };
    L_0x0044:
        r0 = r4;
    L_0x0045:
        switch(r0) {
            case 0: goto L_0x0139;
            case 1: goto L_0x0143;
            case 2: goto L_0x014d;
            case 3: goto L_0x0157;
            case 4: goto L_0x0161;
            case 5: goto L_0x016b;
            case 6: goto L_0x0175;
            case 7: goto L_0x017f;
            case 8: goto L_0x0189;
            case 9: goto L_0x0193;
            case 10: goto L_0x019d;
            case 11: goto L_0x01a7;
            case 12: goto L_0x01b1;
            case 13: goto L_0x01bb;
            case 14: goto L_0x01c5;
            case 15: goto L_0x01cf;
            default: goto L_0x0048;
        };
    L_0x0048:
        r0 = r2;
        r4 = r2;
        r5 = r2;
    L_0x004b:
        r7 = r9.size();
        if (r7 <= r6) goto L_0x022c;
    L_0x0051:
        if (r0 == 0) goto L_0x0232;
    L_0x0053:
        r0 = r9.get(r6);
        r0 = (java.util.ArrayList) r0;
        r5 = r0.iterator();
        r4 = r3;
    L_0x005e:
        r0 = r5.hasNext();
        if (r0 == 0) goto L_0x0227;
    L_0x0064:
        r0 = r5.next();
        if (r4 != 0) goto L_0x006f;
    L_0x006a:
        r4 = ", ";
        r1.append(r4);
    L_0x006f:
        r4 = r0 instanceof java.lang.Number;
        if (r4 == 0) goto L_0x01d9;
    L_0x0073:
        r0 = r0.toString();
        r1.append(r0);
    L_0x007a:
        r4 = r2;
        goto L_0x005e;
    L_0x007c:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r0 = r1.append(r0);
        r1 = r0;
        goto L_0x0036;
    L_0x0087:
        r5 = "LT";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x008f:
        r0 = r2;
        goto L_0x0045;
    L_0x0091:
        r5 = "LT_OR_EQ";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x0099:
        r0 = r3;
        goto L_0x0045;
    L_0x009b:
        r5 = "EQ";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x00a3:
        r0 = r6;
        goto L_0x0045;
    L_0x00a5:
        r5 = "NEQ";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x00ad:
        r0 = 3;
        goto L_0x0045;
    L_0x00af:
        r5 = "GT_OR_EQ";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x00b7:
        r0 = 4;
        goto L_0x0045;
    L_0x00b9:
        r5 = "GT";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x00c1:
        r0 = 5;
        goto L_0x0045;
    L_0x00c3:
        r5 = "STARTSWITH";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x00cb:
        r0 = 6;
        goto L_0x0045;
    L_0x00ce:
        r5 = "ENDSWITH";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x00d6:
        r0 = 7;
        goto L_0x0045;
    L_0x00d9:
        r5 = "CONTAINS";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x00e1:
        r0 = 8;
        goto L_0x0045;
    L_0x00e5:
        r5 = "NOTSTARTSWITH";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x00ed:
        r0 = 9;
        goto L_0x0045;
    L_0x00f1:
        r5 = "NOTENDSWITH";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x00f9:
        r0 = 10;
        goto L_0x0045;
    L_0x00fd:
        r5 = "NOTCONTAINS";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x0105:
        r0 = 11;
        goto L_0x0045;
    L_0x0109:
        r5 = "ISNULL";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x0111:
        r0 = 12;
        goto L_0x0045;
    L_0x0115:
        r5 = "ISNOTNULL";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x011d:
        r0 = 13;
        goto L_0x0045;
    L_0x0121:
        r5 = "IN";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x0129:
        r0 = 14;
        goto L_0x0045;
    L_0x012d:
        r5 = "NIN";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0044;
    L_0x0135:
        r0 = 15;
        goto L_0x0045;
    L_0x0139:
        r0 = " < ";
        r1.append(r0);
        r0 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x004b;
    L_0x0143:
        r0 = " <= ";
        r1.append(r0);
        r0 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x004b;
    L_0x014d:
        r0 = " = ";
        r1.append(r0);
        r0 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x004b;
    L_0x0157:
        r0 = " <> ";
        r1.append(r0);
        r0 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x004b;
    L_0x0161:
        r0 = " >= ";
        r1.append(r0);
        r0 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x004b;
    L_0x016b:
        r0 = " > ";
        r1.append(r0);
        r0 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x004b;
    L_0x0175:
        r0 = " LIKE ";
        r1.append(r0);
        r0 = r2;
        r4 = r3;
        r5 = r2;
        goto L_0x004b;
    L_0x017f:
        r0 = " LIKE ";
        r1.append(r0);
        r0 = r2;
        r4 = r2;
        r5 = r3;
        goto L_0x004b;
    L_0x0189:
        r0 = " LIKE ";
        r1.append(r0);
        r0 = r2;
        r4 = r3;
        r5 = r3;
        goto L_0x004b;
    L_0x0193:
        r0 = " NOT LIKE ";
        r1.append(r0);
        r0 = r2;
        r4 = r3;
        r5 = r2;
        goto L_0x004b;
    L_0x019d:
        r0 = " NOT LIKE ";
        r1.append(r0);
        r0 = r2;
        r4 = r2;
        r5 = r3;
        goto L_0x004b;
    L_0x01a7:
        r0 = " NOT LIKE ";
        r1.append(r0);
        r0 = r2;
        r4 = r3;
        r5 = r3;
        goto L_0x004b;
    L_0x01b1:
        r0 = " ISNULL ";
        r1.append(r0);
        r0 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x004b;
    L_0x01bb:
        r0 = " NOTNULL ";
        r1.append(r0);
        r0 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x004b;
    L_0x01c5:
        r0 = " IN (";
        r1.append(r0);
        r0 = r3;
        r4 = r2;
        r5 = r2;
        goto L_0x004b;
    L_0x01cf:
        r0 = " NOT IN (";
        r1.append(r0);
        r0 = r3;
        r4 = r2;
        r5 = r2;
        goto L_0x004b;
    L_0x01d9:
        r4 = r0 instanceof java.util.Date;
        if (r4 == 0) goto L_0x01f4;
    L_0x01dd:
        r4 = "datetime('";
        r4 = r1.append(r4);
        r0 = (java.util.Date) r0;
        r0 = com.droi.sdk.core.priv.C0895b.m2661a(r0);
        r0 = r4.append(r0);
        r4 = "')";
        r0.append(r4);
        goto L_0x007a;
    L_0x01f4:
        r0 = (java.lang.String) r0;
        r4 = "'";
        r4 = r0.startsWith(r4);
        if (r4 == 0) goto L_0x0216;
    L_0x01fe:
        r4 = "'";
        r4 = r0.endsWith(r4);
        if (r4 == 0) goto L_0x0216;
    L_0x0206:
        r4 = r0.length();
        if (r4 < r6) goto L_0x0216;
    L_0x020c:
        r4 = r0.length();
        r4 = r4 + -2;
        r0 = r0.substring(r3, r4);
    L_0x0216:
        r4 = "'";
        r4 = r1.append(r4);
        r0 = r4.append(r0);
        r4 = "'";
        r0.append(r4);
        goto L_0x007a;
    L_0x0227:
        r0 = ")";
        r1.append(r0);
    L_0x022c:
        r0 = r1.toString();
        goto L_0x000b;
    L_0x0232:
        r0 = r9.get(r6);
        r2 = r0 instanceof java.lang.Number;
        if (r2 == 0) goto L_0x0250;
    L_0x023a:
        if (r5 == 0) goto L_0x0241;
    L_0x023c:
        r2 = "%";
        r1.append(r2);
    L_0x0241:
        r0 = r0.toString();
        r1.append(r0);
        if (r4 == 0) goto L_0x022c;
    L_0x024a:
        r0 = "%";
        r1.append(r0);
        goto L_0x022c;
    L_0x0250:
        r2 = r0 instanceof java.util.Date;
        if (r2 == 0) goto L_0x026a;
    L_0x0254:
        r2 = "datetime('";
        r2 = r1.append(r2);
        r0 = (java.util.Date) r0;
        r0 = com.droi.sdk.core.priv.C0895b.m2661a(r0);
        r0 = r2.append(r0);
        r2 = "')";
        r0.append(r2);
        goto L_0x022c;
    L_0x026a:
        r2 = r0 instanceof java.lang.Boolean;
        if (r2 == 0) goto L_0x0280;
    L_0x026e:
        r2 = "'";
        r1.append(r2);
        r0 = r0.toString();
        r1.append(r0);
        r0 = "'";
        r1.append(r0);
        goto L_0x022c;
    L_0x0280:
        r0 = (java.lang.String) r0;
        r2 = "'";
        r2 = r0.startsWith(r2);
        if (r2 == 0) goto L_0x02a2;
    L_0x028a:
        r2 = "'";
        r2 = r0.endsWith(r2);
        if (r2 == 0) goto L_0x02a2;
    L_0x0292:
        r2 = r0.length();
        if (r2 < r6) goto L_0x02a2;
    L_0x0298:
        r2 = r0.length();
        r2 = r2 + -2;
        r0 = r0.substring(r3, r2);
    L_0x02a2:
        r2 = "'";
        r1.append(r2);
        if (r5 == 0) goto L_0x02ae;
    L_0x02a9:
        r2 = "%";
        r1.append(r2);
    L_0x02ae:
        r1.append(r0);
        if (r4 == 0) goto L_0x02b8;
    L_0x02b3:
        r0 = "%";
        r1.append(r0);
    L_0x02b8:
        r0 = "'";
        r1.append(r0);
        goto L_0x022c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.core.c.a(java.util.ArrayList):java.lang.String");
    }

    private void m2638a(DroiCondition droiCondition, StringBuilder stringBuilder) {
        String a = C0871a.m2608a(droiCondition);
        if (a.equals(Builder.f2646k)) {
            stringBuilder.append(" ").append(m2637a((ArrayList) C0871a.m2609b(droiCondition).get(0))).append(" ");
            return;
        }
        String toUpperCase = a.toUpperCase();
        Iterator it = C0871a.m2609b(droiCondition).iterator();
        int i = 1;
        while (it.hasNext()) {
            Object next = it.next();
            if (i == 0) {
                stringBuilder.append(toUpperCase);
            }
            if (next instanceof ArrayList) {
                stringBuilder.append(" ").append(m2637a((ArrayList) ((ArrayList) next).get(0))).append(" ");
            } else {
                DroiCondition droiCondition2 = (DroiCondition) next;
                if (C0871a.m2608a(droiCondition2).equals(Builder.f2646k)) {
                    stringBuilder.append(" ").append(m2637a((ArrayList) C0871a.m2609b(droiCondition2).get(0))).append(" ");
                } else {
                    stringBuilder.append(" (");
                    m2638a((DroiCondition) next, stringBuilder);
                    stringBuilder.append(") ");
                }
            }
            i = 0;
        }
    }

    private void m2639a(DroiObject droiObject, ContentValues contentValues, String str) {
        Object obj;
        contentValues.put("[" + str + C0896c.f2868c + "]", droiObject.getObjectId());
        contentValues.put("[" + str + C0896c.f2869d + "]", C0895b.m2661a(droiObject.getCreationTime()));
        contentValues.put("[" + str + C0896c.f2870e + "]", C0895b.m2661a(droiObject.getModifiedTime()));
        if (droiObject.getPermission() != null) {
            contentValues.put("[" + str + C0896c.f2877l + "]", droiObject.getPermission().toJson().toString());
        }
        Class cls = droiObject.getClass();
        if (!C0911l.m2708c(cls).equals(C0911l.m2708c(DroiObject.class))) {
            for (Entry entry : C0911l.m2706b(cls).entrySet()) {
                Field b = C0911l.m2705b(cls, (String) entry.getKey());
                if (b != null) {
                    boolean z;
                    b.setAccessible(true);
                    boolean z2 = ((DroiReference) b.getAnnotation(DroiReference.class)) != null;
                    Class type = b.getType();
                    try {
                        Object obj2 = b.get(droiObject);
                        if (type == DroiReferenceObject.class || type == DroiFile.class) {
                            z2 = true;
                        }
                        z = z2;
                        obj = obj2;
                    } catch (Exception e) {
                        DroiLog.m2873w(f2823a, e);
                        z = z2;
                        obj = null;
                    }
                    if (obj != null) {
                        if (z) {
                            DroiObject droiObject2 = obj instanceof DroiReferenceObject ? (DroiObject) ((DroiReferenceObject) obj).droiObject() : (DroiObject) obj;
                            if (droiObject2 != null) {
                                if (droiObject2.isDirty()) {
                                    droiObject2.setLocalStorage(true);
                                    droiObject2.save();
                                }
                                contentValues.put("[" + str + ((String) entry.getKey()) + "]", droiObject2.getObjectId());
                            }
                        } else if (obj != null) {
                            if (obj instanceof DroiObject) {
                                m2639a((DroiObject) obj, contentValues, str + ((String) entry.getKey()) + "/");
                            } else {
                                contentValues.put("[" + str + ((String) entry.getKey()) + "]", m2635a(obj, false));
                            }
                        }
                    }
                }
            }
        }
        ArrayList keys = droiObject.getKeys();
        if (keys != null) {
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                obj = droiObject.get(str2);
                if ((obj instanceof DroiFile) || (obj instanceof DroiReferenceObject)) {
                    contentValues.put("[" + str + str2 + "]", (obj instanceof DroiReferenceObject ? (DroiObject) ((DroiReferenceObject) obj).droiObject() : (DroiObject) obj).getObjectId());
                } else if (obj instanceof DroiObject) {
                    m2639a((DroiObject) obj, contentValues, str + str2 + "/");
                } else {
                    contentValues.put("[" + str + str2 + "]", m2635a(obj, false));
                }
            }
        }
    }

    private boolean m2640a(String str, Object obj, Object obj2) {
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

    private Object m2641c(Object obj) {
        if ((obj instanceof JSONObject) || (obj instanceof JSONArray)) {
            return obj;
        }
        if (!(obj instanceof String)) {
            return null;
        }
        try {
            obj = new JSONTokener((String) obj).nextValue();
            if ((obj instanceof JSONObject) || (obj instanceof JSONArray)) {
                return obj;
            }
        } catch (JSONException e) {
            DroiLog.m2870e(f2823a, "Exception is " + e.toString());
        }
        return null;
    }

    private Object m2642d(Object obj) {
        String str;
        String str2;
        Builder localStorage;
        DroiQuery build;
        DroiError droiError;
        if (obj == null) {
            return null;
        }
        if (obj instanceof JSONArray) {
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                Object obj2;
                try {
                    obj2 = ((JSONArray) obj).get(i);
                } catch (JSONException e) {
                    obj2 = null;
                }
                if (obj2 != null) {
                    Object c = m2641c(obj2);
                    if (c != null) {
                        obj2 = m2642d(c);
                    }
                    jSONArray.put(obj2);
                }
            }
            return jSONArray;
        } else if (!(obj instanceof JSONObject)) {
            return obj;
        } else {
            JSONObject jSONObject = new JSONObject();
            Object obj3;
            if (((JSONObject) obj).has(C0896c.f2871f)) {
                Class customClassWithClassName;
                List runQuery;
                JSONObject jSONObject2 = (JSONObject) obj;
                try {
                    str = (String) jSONObject2.get(C0896c.f2871f);
                    try {
                        obj3 = (String) jSONObject2.get(C0896c.f2868c);
                        str2 = str;
                    } catch (JSONException e2) {
                        obj3 = null;
                        str2 = str;
                        customClassWithClassName = DroiObject.getCustomClassWithClassName(str2);
                        localStorage = Builder.newBuilder().localStorage();
                        if (customClassWithClassName != null) {
                            localStorage.query(str2);
                        } else {
                            localStorage.query(customClassWithClassName);
                        }
                        build = localStorage.where(C0896c.f2868c, Type.EQ, obj3).build();
                        droiError = new DroiError(0, null);
                        runQuery = build.runQuery(droiError);
                        if (!droiError.isOk()) {
                            DroiLog.m2870e(f2823a, droiError.toString());
                        }
                        return runQuery != null ? null : null;
                    }
                } catch (JSONException e3) {
                    str = null;
                    obj3 = null;
                    str2 = str;
                    customClassWithClassName = DroiObject.getCustomClassWithClassName(str2);
                    localStorage = Builder.newBuilder().localStorage();
                    if (customClassWithClassName != null) {
                        localStorage.query(customClassWithClassName);
                    } else {
                        localStorage.query(str2);
                    }
                    build = localStorage.where(C0896c.f2868c, Type.EQ, obj3).build();
                    droiError = new DroiError(0, null);
                    runQuery = build.runQuery(droiError);
                    if (droiError.isOk()) {
                        DroiLog.m2870e(f2823a, droiError.toString());
                    }
                    if (runQuery != null) {
                    }
                }
                customClassWithClassName = DroiObject.getCustomClassWithClassName(str2);
                if (!(customClassWithClassName == null && str2 == null)) {
                    localStorage = Builder.newBuilder().localStorage();
                    if (customClassWithClassName != null) {
                        localStorage.query(customClassWithClassName);
                    } else {
                        localStorage.query(str2);
                    }
                    build = localStorage.where(C0896c.f2868c, Type.EQ, obj3).build();
                    droiError = new DroiError(0, null);
                    runQuery = build.runQuery(droiError);
                    if (droiError.isOk()) {
                        DroiLog.m2870e(f2823a, droiError.toString());
                    }
                    if (runQuery != null && runQuery.size() > 0) {
                        JSONObject toJson = ((DroiObject) runQuery.get(0)).toJson(null);
                        try {
                            jSONObject.put(C0896c.f2871f, C0896c.f2873h);
                            jSONObject.put(C0896c.f2866a, str2);
                            jSONObject.put(C0896c.f2876k, toJson);
                        } catch (JSONException e4) {
                        }
                        return jSONObject;
                    }
                }
            }
            Iterator keys = ((JSONObject) obj).keys();
            while (keys.hasNext()) {
                str = (String) keys.next();
                try {
                    obj3 = ((JSONObject) obj).get(str);
                } catch (JSONException e5) {
                    obj3 = null;
                }
                if (obj3 != null) {
                    Object c2 = m2641c(obj3);
                    if (c2 != null) {
                        obj3 = m2642d(c2);
                    }
                    try {
                        jSONObject.put(str, obj3);
                    } catch (JSONException e6) {
                    }
                }
            }
            return jSONObject;
        }
    }

    public DroiError mo1881a(C0903g<String, Object> c0903g) {
        ArrayList arrayList = (ArrayList) m2631a((C0903g) c0903g, Builder.f2640e);
        return (arrayList == null || arrayList.size() != 2) ? new DroiError(DroiError.INVALID_PARAMETER, null) : m2627a((DroiObject) arrayList.get(0), (String) arrayList.get(1));
    }

    public DroiError mo1882a(Object obj, String str) {
        Object obj2;
        Exception exception;
        DroiObject droiObject = (DroiObject) obj;
        StringBuilder stringBuilder = new StringBuilder();
        Class customClassWithClassName = DroiObject.getCustomClassWithClassName(str);
        if (customClassWithClassName != null) {
            str = C0911l.m2710d(customClassWithClassName);
        }
        if (customClassWithClassName != null) {
            stringBuilder.append("CREATE TABLE IF NOT EXISTS T").append(Integer.toHexString(str.hashCode())).append(Integer.toHexString(C0911l.m2698a(C0911l.m2708c(customClassWithClassName))));
        } else {
            stringBuilder.append("CREATE TABLE IF NOT EXISTS ").append(str);
        }
        stringBuilder.append(" (").append(C0896c.f2868c).append(" String PRIMARY KEY").append(", ").append(C0896c.f2866a).append(" STRING").append(", ").append(C0896c.f2869d).append(" STRING").append(", ").append(C0896c.f2870e).append(" STRING").append(", ").append(C0896c.f2877l).append(" STRING");
        Class cls = droiObject.getClass();
        if (!C0911l.m2708c(cls).equals(C0911l.m2708c(DroiObject.class))) {
            for (Entry entry : C0911l.m2706b(cls).entrySet()) {
                Field b = C0911l.m2705b(cls, (String) entry.getKey());
                if (b != null) {
                    boolean z = ((DroiReference) b.getAnnotation(DroiReference.class)) != null;
                    b.setAccessible(true);
                    Class type = b.getType();
                    try {
                        obj2 = b.get(droiObject);
                        try {
                            if (type == DroiReferenceObject.class || type == DroiFile.class) {
                                if (obj2 != null) {
                                    try {
                                        if (type == DroiReferenceObject.class) {
                                            obj2 = ((DroiReferenceObject) obj2).droiObject();
                                            z = true;
                                        }
                                    } catch (Exception e) {
                                        exception = e;
                                        z = true;
                                        DroiLog.m2873w(f2823a, exception);
                                        if (!!DroiObject.isExtendedClass(type)) {
                                        }
                                        if (!z) {
                                            stringBuilder.append(", " + ((String) entry.getKey()) + " TEXT");
                                        } else if (obj2 == null) {
                                            stringBuilder.append(", " + ((String) entry.getKey()) + " TEXT");
                                        } else {
                                            stringBuilder.append(m2633a((DroiObject) obj2, obj2.getClass(), "/" + ((String) entry.getKey()) + "/"));
                                        }
                                    }
                                }
                                z = true;
                            }
                        } catch (IllegalAccessException e2) {
                            exception = e2;
                            DroiLog.m2873w(f2823a, exception);
                            if (!DroiObject.isExtendedClass(type)) {
                            }
                            if (!z) {
                                stringBuilder.append(", " + ((String) entry.getKey()) + " TEXT");
                            } else if (obj2 == null) {
                                stringBuilder.append(", " + ((String) entry.getKey()) + " TEXT");
                            } else {
                                stringBuilder.append(m2633a((DroiObject) obj2, obj2.getClass(), "/" + ((String) entry.getKey()) + "/"));
                            }
                        }
                    } catch (Exception e3) {
                        Exception exception2 = e3;
                        obj2 = null;
                        exception = exception2;
                        DroiLog.m2873w(f2823a, exception);
                        if (!DroiObject.isExtendedClass(type)) {
                        }
                        if (!z) {
                            stringBuilder.append(", " + ((String) entry.getKey()) + " TEXT");
                        } else if (obj2 == null) {
                            stringBuilder.append(m2633a((DroiObject) obj2, obj2.getClass(), "/" + ((String) entry.getKey()) + "/"));
                        } else {
                            stringBuilder.append(", " + ((String) entry.getKey()) + " TEXT");
                        }
                    }
                    if (!DroiObject.isExtendedClass(type) && !z) {
                        stringBuilder.append(", " + ((String) entry.getKey()) + " " + m2422a(type));
                    } else if (!z) {
                        stringBuilder.append(", " + ((String) entry.getKey()) + " TEXT");
                    } else if (obj2 == null) {
                        stringBuilder.append(m2633a((DroiObject) obj2, obj2.getClass(), "/" + ((String) entry.getKey()) + "/"));
                    } else {
                        stringBuilder.append(", " + ((String) entry.getKey()) + " TEXT");
                    }
                }
            }
        }
        ArrayList keys = droiObject.getKeys();
        if (keys != null) {
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                obj2 = droiObject.get(str2);
                if ((obj2 instanceof DroiFile) || (obj2 instanceof DroiReferenceObject)) {
                    stringBuilder.append(", " + str2 + " TEXT");
                } else if (obj2 instanceof DroiObject) {
                    stringBuilder.append(m2633a((DroiObject) obj2, obj2.getClass(), str2 + "/"));
                } else {
                    stringBuilder.append(", " + str2 + " " + m2423a(obj2));
                }
            }
        }
        stringBuilder.append(");");
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(f2824c);
        final String stringBuilder2 = stringBuilder.toString();
        final DroiError droiError = new DroiError();
        synchronized (this) {
            dispatcher.enqueueTask(new Runnable(this) {
                final /* synthetic */ C0883c f2809c;

                public void run() {
                    try {
                        this.f2809c.f2826b.getWritableDatabase().execSQL(stringBuilder2);
                    } catch (Exception e) {
                        droiError.setCode(DroiError.ERROR);
                        droiError.setAppendedMessage("Save to local fail.");
                        DroiLog.m2873w(C0883c.f2823a, e);
                    }
                    synchronized (this.f2809c) {
                        this.f2809c.notifyAll();
                    }
                }
            });
            try {
                wait();
            } catch (Exception e4) {
                DroiLog.m2873w(f2823a, e4);
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage(e4.toString());
            }
        }
        return droiError;
    }

    public List mo1883a(C0903g<String, Object> c0903g, DroiError droiError) {
        if (droiError == null) {
            droiError = new DroiError();
        }
        if (c0903g.m2673b(Builder.f2639d)) {
            List c = c0903g.m2675c(Builder.f2639d);
            if (c.size() != 1) {
                if (droiError != null) {
                    droiError.setCode(DroiError.INVALID_PARAMETER);
                }
                return null;
            }
            String str = (String) c.get(0);
            boolean b = c0903g.m2673b(ParamKey.COUNT);
            Class customClassWithClassName = DroiObject.getCustomClassWithClassName(str);
            StringBuilder stringBuilder = new StringBuilder();
            if (b) {
                stringBuilder.append("SELECT COUNT(_Id)");
            } else {
                stringBuilder.append("SELECT *");
            }
            if (customClassWithClassName != null) {
                stringBuilder.append(" FROM T").append(Integer.toHexString(C0911l.m2710d(customClassWithClassName).hashCode())).append(Integer.toHexString(C0911l.m2698a(C0911l.m2708c(customClassWithClassName))));
            } else {
                stringBuilder.append(" FROM ").append(str);
            }
            stringBuilder.append(m2634a((C0903g) c0903g, true));
            if (c0903g.m2673b(Builder.f2653r)) {
                stringBuilder.append(" ORDER BY ");
                List c2 = c0903g.m2675c(Builder.f2653r);
                if (c2 != null) {
                    for (int i = 0; i < c2.size(); i++) {
                        if (i != 0) {
                            stringBuilder.append(", ");
                        }
                        ArrayList arrayList = (ArrayList) c2.get(i);
                        stringBuilder.append(m2636a((String) arrayList.get(0))).append(" ").append(arrayList.get(1)).append(" ");
                    }
                }
            }
            if (c0903g.m2673b(Builder.f2654s)) {
                stringBuilder.append(" LIMIT ");
                stringBuilder.append(((Number) m2631a((C0903g) c0903g, Builder.f2654s)).toString());
            }
            if (c0903g.m2673b("offset")) {
                stringBuilder.append(" OFFSET ");
                stringBuilder.append(((Number) m2631a((C0903g) c0903g, "offset")).toString());
            }
            stringBuilder.append(";");
            TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(f2824c);
            final String stringBuilder2 = stringBuilder.toString();
            final AtomicReference atomicReference = new AtomicReference();
            final DroiError droiError2 = new DroiError();
            synchronized (this) {
                dispatcher.enqueueTask(new Runnable(this) {
                    final /* synthetic */ C0883c f2813d;

                    public void run() {
                        try {
                            atomicReference.set(this.f2813d.f2826b.getReadableDatabase().rawQuery(stringBuilder2, null));
                        } catch (Exception e) {
                            DroiLog.m2873w(C0883c.f2823a, e);
                            droiError2.setCode(DroiError.ERROR);
                            droiError2.setAppendedMessage(e.toString());
                        }
                        synchronized (this.f2813d) {
                            this.f2813d.notifyAll();
                        }
                    }
                });
                try {
                    wait();
                } catch (Exception e) {
                    DroiLog.m2873w(f2823a, e);
                    droiError2.setCode(DroiError.ERROR);
                    droiError2.setAppendedMessage(e.toString());
                }
            }
            if (droiError != null) {
                droiError.copy(droiError2);
            }
            Cursor cursor = (Cursor) atomicReference.get();
            ArrayList arrayList2 = new ArrayList();
            if (cursor == null) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("null cursor.");
                return arrayList2;
            } else if (b) {
                if (cursor.moveToNext()) {
                    arrayList2.add(Integer.valueOf(cursor.getInt(0)));
                } else {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage("empty cursor.");
                }
                return arrayList2;
            } else {
                HashMap b2 = customClassWithClassName != null ? C0911l.m2706b(customClassWithClassName) : null;
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        JSONObject jSONObject = new JSONObject();
                        for (String str2 : cursor.getColumnNames()) {
                            JSONObject jSONObject2;
                            String str3;
                            JSONObject jSONObject3;
                            int columnIndex = cursor.getColumnIndex(str2);
                            if (str2.startsWith("/")) {
                                String[] split = str2.split("/");
                                String str4 = str2;
                                jSONObject2 = jSONObject;
                                for (int i2 = 0; i2 < split.length; i2++) {
                                    String str5 = split[i2];
                                    if (str5.length() != 0) {
                                        if (i2 + 1 >= split.length) {
                                            str4 = str5;
                                        } else {
                                            try {
                                                if (jSONObject2.has(str5)) {
                                                    jSONObject2 = jSONObject2.getJSONObject(str5);
                                                } else {
                                                    JSONObject jSONObject4 = new JSONObject();
                                                    jSONObject2.put(str5, jSONObject4);
                                                    jSONObject2 = jSONObject4;
                                                }
                                            } catch (Exception e2) {
                                                DroiLog.m2873w(f2823a, e2);
                                            }
                                        }
                                    }
                                }
                                str3 = str4;
                                jSONObject3 = jSONObject2;
                            } else {
                                jSONObject3 = jSONObject;
                                str3 = str2;
                            }
                            switch (cursor.getType(columnIndex)) {
                                case 1:
                                    try {
                                        long j = cursor.getLong(columnIndex);
                                        if (j < 2147483647L && j > -2147483648L) {
                                            jSONObject3.put(str3, new Integer((int) j));
                                            break;
                                        }
                                        jSONObject3.put(str3, new Long(j));
                                        break;
                                    } catch (Exception e3) {
                                        DroiLog.m2873w(f2823a, e3);
                                        droiError.setCode(DroiError.ERROR);
                                        droiError.setAppendedMessage(e3.toString());
                                        break;
                                    }
                                    break;
                                case 2:
                                    jSONObject3.put(str3, new Float(cursor.getFloat(columnIndex)));
                                    break;
                                case 3:
                                    Object obj;
                                    DroiReferenceObject string = cursor.getString(columnIndex);
                                    Object obj2 = null;
                                    Object c3 = m2641c((Object) string);
                                    if (c3 != null) {
                                        obj2 = m2642d(c3);
                                    }
                                    if (b2 != null && b2.containsKey(str3)) {
                                        Field b3 = C0911l.m2705b(customClassWithClassName, str3);
                                        if (b3 != null) {
                                            b3.setAccessible(true);
                                            Object obj3 = ((DroiReference) b3.getAnnotation(DroiReference.class)) != null ? 1 : null;
                                            if (b3.getType() == DroiReferenceObject.class || b3.getType() == DroiFile.class) {
                                                obj3 = 1;
                                            }
                                            c3 = (obj2 != null && (obj2 instanceof JSONObject) && ((JSONObject) obj2).has(C0896c.f2871f)) ? 1 : obj3;
                                            if (c3 != null && obj2 != null) {
                                                DroiReferenceObject droiReferenceObject;
                                                jSONObject2 = (JSONObject) obj2;
                                                if (jSONObject2.has(C0896c.f2876k)) {
                                                    DroiObject fromJson = DroiObject.fromJson(jSONObject2.getJSONObject(C0896c.f2876k));
                                                    if (b3.getType() == DroiReferenceObject.class) {
                                                        droiReferenceObject = new DroiReferenceObject();
                                                        droiReferenceObject.setDroiObject(fromJson);
                                                    } else {
                                                        c3 = fromJson;
                                                    }
                                                } else {
                                                    droiReferenceObject = string;
                                                }
                                                obj = droiReferenceObject;
                                            } else if (obj2 != null) {
                                                obj = obj2;
                                            }
                                        }
                                    } else if (obj2 != null) {
                                        obj = obj2;
                                    }
                                    if (obj == null) {
                                        break;
                                    }
                                    jSONObject3.put(str3, obj);
                                    break;
                                    break;
                                default:
                                    break;
                            }
                        }
                        DroiObject fromJson2 = customClassWithClassName == null ? DroiObject.fromJson(jSONObject) : DroiObject.fromJson(jSONObject, customClassWithClassName);
                        if (fromJson2 != null) {
                            fromJson2.setLocalStorage(true);
                            arrayList2.add(fromJson2);
                        } else {
                            DroiLog.m2870e(f2823a, "Gen obj fail in query. class: " + (customClassWithClassName == null ? "DroiObject" : customClassWithClassName.getSimpleName()));
                        }
                    }
                }
                cursor.close();
                return arrayList2;
            }
        }
        if (droiError != null) {
            droiError.setCode(DroiError.INVALID_PARAMETER);
        }
        return null;
    }

    public DroiError mo1884b(C0903g<String, Object> c0903g) {
        ArrayList arrayList = (ArrayList) m2631a((C0903g) c0903g, "update");
        return (arrayList == null || arrayList.size() != 2) ? new DroiError(DroiError.INVALID_PARAMETER, null) : m2627a((DroiObject) arrayList.get(0), (String) arrayList.get(1));
    }

    public DroiError mo1885c(C0903g<String, Object> c0903g) {
        DroiObject droiObject = null;
        ArrayList arrayList = (ArrayList) m2631a((C0903g) c0903g, "delete");
        if (arrayList == null || arrayList.size() <= 0 || arrayList.size() > 2) {
            return new DroiError(DroiError.INVALID_PARAMETER, null);
        }
        String str;
        if (arrayList.size() == 2) {
            droiObject = (DroiObject) arrayList.get(0);
            str = (String) arrayList.get(1);
        } else {
            str = (String) arrayList.get(0);
        }
        return m2628a(droiObject, str, (C0903g) c0903g);
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
        String str;
        Object a3;
        Object obj2;
        if (c2 != null) {
            str = (String) c2.get(0);
            for (Object obj22 : a2) {
                a3 = m2632a(str, obj22);
                if (a3 != null && (a3 instanceof Number) && m2640a(str, obj22, Integer.valueOf(((Number) a3).intValue() + 1))) {
                    ((DroiObject) obj22).save();
                }
            }
        } else if (c3 != null) {
            str = (String) c3.get(0);
            for (Object obj222 : a2) {
                a3 = m2632a(str, obj222);
                if (a3 != null && (a3 instanceof Number) && m2640a(str, obj222, Integer.valueOf(((Number) a3).intValue() - 1))) {
                    ((DroiObject) obj222).save();
                }
            }
        } else if (c4 != null) {
            c = (List) c4.get(0);
            String str2 = (String) c.get(0);
            obj222 = c.get(1);
            for (Object obj3 : a2) {
                if (m2640a(str2, obj3, obj222)) {
                    ((DroiObject) obj3).save();
                }
            }
        }
        return null;
    }
}
