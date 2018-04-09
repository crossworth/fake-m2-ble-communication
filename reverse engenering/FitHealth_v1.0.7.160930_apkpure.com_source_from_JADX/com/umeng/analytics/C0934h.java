package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.facebook.GraphResponse;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;
import p031u.aly.C1513m;
import p031u.aly.C1950f;
import p031u.aly.ap;
import p031u.aly.au;
import p031u.aly.av;
import p031u.aly.av.C1465e;
import p031u.aly.av.C1466f;
import p031u.aly.av.C1468h;
import p031u.aly.av.C1470l;
import p031u.aly.av.C1921i;
import p031u.aly.av.C1922j;
import p031u.aly.av.C1923o;
import p031u.aly.bj;
import p031u.aly.bk;
import p031u.aly.bl;

/* compiled from: StoreHelper */
public final class C0934h {
    private static C0934h f3161a = null;
    private static Context f3162b = null;
    private static String f3163c = null;
    private static long f3164e = 1209600000;
    private static long f3165f = 2097152;
    private static final String f3166g = "mobclick_agent_user_";
    private static final String f3167h = "mobclick_agent_header_";
    private static final String f3168i = "mobclick_agent_update_";
    private static final String f3169j = "mobclick_agent_state_";
    private static final String f3170k = "mobclick_agent_cached_";
    private C0932a f3171d;

    /* compiled from: StoreHelper */
    public static class C0932a {
        private final int f3158a;
        private File f3159b;
        private FilenameFilter f3160c;

        /* compiled from: StoreHelper */
        class C09312 implements FilenameFilter {
            final /* synthetic */ C0932a f3157a;

            C09312(C0932a c0932a) {
                this.f3157a = c0932a;
            }

            public boolean accept(File file, String str) {
                return str.startsWith("um");
            }
        }

        public C0932a(Context context) {
            this(context, ".um");
        }

        public C0932a(Context context, String str) {
            this.f3158a = 10;
            this.f3160c = new C09312(this);
            this.f3159b = new File(context.getFilesDir(), str);
            if (!this.f3159b.exists() || !this.f3159b.isDirectory()) {
                this.f3159b.mkdir();
            }
        }

        public boolean m3094a() {
            File[] listFiles = this.f3159b.listFiles();
            if (listFiles == null || listFiles.length <= 0) {
                return false;
            }
            return true;
        }

        public void m3092a(C0933b c0933b) {
            int i;
            int i2 = 0;
            File[] listFiles = this.f3159b.listFiles(this.f3160c);
            if (listFiles != null && listFiles.length >= 10) {
                Arrays.sort(listFiles);
                final int length = listFiles.length - 10;
                C0923f.m3078b(new Runnable(this) {
                    final /* synthetic */ C0932a f3156b;

                    public void run() {
                        if (length > 0) {
                            C1513m.m3810a(C0934h.f3162b).m3835a((long) length, System.currentTimeMillis(), C0919a.f3123t);
                        }
                    }
                });
                for (i = 0; i < length; i++) {
                    listFiles[i].delete();
                }
            }
            if (listFiles != null && listFiles.length > 0) {
                c0933b.mo2753a(this.f3159b);
                i = listFiles.length;
                while (i2 < i) {
                    try {
                        if (c0933b.mo2754b(listFiles[i2])) {
                            listFiles[i2].delete();
                        }
                    } catch (Throwable th) {
                        listFiles[i2].delete();
                    }
                    i2++;
                }
                c0933b.mo2755c(this.f3159b);
            }
        }

        public void m3093a(byte[] bArr) {
            if (bArr != null && bArr.length != 0) {
                try {
                    bk.m3561a(new File(this.f3159b, String.format(Locale.US, "um_cache_%d.env", new Object[]{Long.valueOf(System.currentTimeMillis())})), bArr);
                } catch (Exception e) {
                }
            }
        }

        public void m3095b() {
            File[] listFiles = this.f3159b.listFiles(this.f3160c);
            if (listFiles != null && listFiles.length > 0) {
                for (File delete : listFiles) {
                    delete.delete();
                }
            }
        }

        public int m3096c() {
            File[] listFiles = this.f3159b.listFiles(this.f3160c);
            if (listFiles == null || listFiles.length <= 0) {
                return 0;
            }
            return listFiles.length;
        }
    }

    /* compiled from: StoreHelper */
    public interface C0933b {
        void mo2753a(File file);

        boolean mo2754b(File file);

        void mo2755c(File file);
    }

    /* compiled from: StoreHelper */
    class C20091 extends C1950f {
        final /* synthetic */ C0934h f5437a;

        C20091(C0934h c0934h) {
            this.f5437a = c0934h;
        }

        public void mo2823a(Object obj, boolean z) {
            if (!obj.equals(GraphResponse.SUCCESS_KEY)) {
            }
        }
    }

    public C0934h(Context context) {
        this.f3171d = new C0932a(context);
    }

    public static synchronized C0934h m3100a(Context context) {
        C0934h c0934h;
        synchronized (C0934h.class) {
            f3162b = context.getApplicationContext();
            f3163c = context.getPackageName();
            if (f3161a == null) {
                f3161a = new C0934h(context);
            }
            c0934h = f3161a;
        }
        return c0934h;
    }

    private static boolean m3103a(File file) {
        long length = file.length();
        if (!file.exists() || length <= f3165f) {
            return false;
        }
        C1513m.m3810a(f3162b).m3835a(length, System.currentTimeMillis(), C0919a.f3122s);
        return true;
    }

    public void m3112a(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            Editor edit = m3107o().edit();
            edit.putString("au_p", str);
            edit.putString("au_u", str2);
            edit.commit();
        }
    }

    public String[] m3115a() {
        SharedPreferences o = m3107o();
        String string = o.getString("au_p", null);
        String string2 = o.getString("au_u", null);
        if (string == null || string2 == null) {
            return null;
        }
        return new String[]{string, string2};
    }

    public void m3116b() {
        m3107o().edit().remove("au_p").remove("au_u").commit();
    }

    String m3119c() {
        SharedPreferences a = ap.m3451a(f3162b);
        if (a != null) {
            return a.getString("appkey", null);
        }
        return null;
    }

    void m3111a(String str) {
        SharedPreferences a = ap.m3451a(f3162b);
        if (a != null) {
            a.edit().putString("appkey", str).commit();
        }
    }

    String m3121d() {
        SharedPreferences a = ap.m3451a(f3162b);
        if (a != null) {
            return a.getString(au.f3573b, null);
        }
        return null;
    }

    void m3117b(String str) {
        SharedPreferences a = ap.m3451a(f3162b);
        if (a != null) {
            a.edit().putString(au.f3573b, str).commit();
        }
    }

    String m3122e() {
        SharedPreferences a = ap.m3451a(f3162b);
        if (a != null) {
            return a.getString(SocializeProtocolConstants.PROTOCOL_KEY_ST, null);
        }
        return null;
    }

    void m3120c(String str) {
        SharedPreferences a = ap.m3451a(f3162b);
        if (a != null) {
            a.edit().putString(SocializeProtocolConstants.PROTOCOL_KEY_ST, str).commit();
        }
    }

    void m3110a(int i) {
        SharedPreferences a = ap.m3451a(f3162b);
        if (a != null) {
            a.edit().putInt("vt", i).commit();
        }
    }

    int m3123f() {
        SharedPreferences a = ap.m3451a(f3162b);
        if (a != null) {
            return a.getInt("vt", 0);
        }
        return 0;
    }

    public av m3124g() {
        ObjectInputStream objectInputStream;
        Exception e;
        av avVar;
        Throwable th;
        Throwable e2;
        try {
            File file = new File(f3162b.getApplicationContext().getFilesDir().getAbsolutePath(), m3109q());
            if (C0934h.m3103a(file)) {
                file.delete();
                return null;
            } else if (!file.exists()) {
                return null;
            } else {
                FileInputStream fileInputStream;
                try {
                    fileInputStream = new FileInputStream(file);
                    try {
                        objectInputStream = new ObjectInputStream(fileInputStream);
                    } catch (Exception e3) {
                        e = e3;
                        objectInputStream = null;
                        try {
                            e.printStackTrace();
                            if (objectInputStream != null) {
                                try {
                                    objectInputStream.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                    avVar = null;
                                } catch (IOException e42) {
                                    e42.printStackTrace();
                                    avVar = null;
                                }
                            } else {
                                avVar = null;
                            }
                            return avVar;
                        } catch (Throwable th2) {
                            th = th2;
                            if (objectInputStream != null) {
                                try {
                                    objectInputStream.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e52) {
                                    e52.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        objectInputStream = null;
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        throw th;
                    }
                    try {
                        avVar = (av) objectInputStream.readObject();
                        if (objectInputStream != null) {
                            try {
                                objectInputStream.close();
                            } catch (IOException e6) {
                                try {
                                    e6.printStackTrace();
                                } catch (Exception e7) {
                                    e2 = e7;
                                    if (bl.f3714a) {
                                        bl.m3598e(e2);
                                    }
                                    return avVar;
                                }
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e62) {
                                e62.printStackTrace();
                            }
                        }
                    } catch (Exception e8) {
                        e = e8;
                        e.printStackTrace();
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        if (fileInputStream != null) {
                            avVar = null;
                        } else {
                            fileInputStream.close();
                            avVar = null;
                        }
                        return avVar;
                    }
                } catch (Exception e9) {
                    e = e9;
                    objectInputStream = null;
                    fileInputStream = null;
                    e.printStackTrace();
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                        avVar = null;
                    } else {
                        avVar = null;
                    }
                    return avVar;
                } catch (Throwable th4) {
                    th = th4;
                    objectInputStream = null;
                    fileInputStream = null;
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
                return avVar;
            }
        } catch (Throwable th5) {
            Throwable th6 = th5;
            avVar = null;
            e2 = th6;
            if (bl.f3714a) {
                bl.m3598e(e2);
            }
            return avVar;
        }
    }

    public void m3113a(av avVar) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        Throwable e;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(new File(f3162b.getApplicationContext().getFilesDir().getAbsolutePath(), m3109q()));
            try {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
            } catch (Exception e2) {
                e = e2;
                objectOutputStream = null;
                fileOutputStream2 = fileOutputStream;
                try {
                    bl.m3598e(e);
                    e.printStackTrace();
                    if (objectOutputStream != null) {
                        try {
                            objectOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (fileOutputStream2 == null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                            return;
                        }
                    }
                } catch (Throwable th) {
                    e = th;
                    fileOutputStream = fileOutputStream2;
                    if (objectOutputStream != null) {
                        try {
                            objectOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e42) {
                            e42.printStackTrace();
                        }
                    }
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                objectOutputStream = null;
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw e;
            }
            try {
                objectOutputStream.writeObject(avVar);
                objectOutputStream.flush();
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e322) {
                        e322.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e3222) {
                        e3222.printStackTrace();
                    }
                }
            } catch (Exception e5) {
                e = e5;
                fileOutputStream2 = fileOutputStream;
                bl.m3598e(e);
                e.printStackTrace();
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (fileOutputStream2 == null) {
                    fileOutputStream2.close();
                }
            } catch (Throwable th3) {
                e = th3;
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw e;
            }
        } catch (Exception e6) {
            e = e6;
            objectOutputStream = null;
            bl.m3598e(e);
            e.printStackTrace();
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (fileOutputStream2 == null) {
                fileOutputStream2.close();
            }
        } catch (Throwable th4) {
            e = th4;
            objectOutputStream = null;
            fileOutputStream = null;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw e;
        }
    }

    public void m3125h() {
        f3162b.deleteFile(m3108p());
        f3162b.deleteFile(m3109q());
        C1513m.m3810a(f3162b).m3845d(new C20091(this));
    }

    public void m3114a(byte[] bArr) {
        this.f3171d.m3093a(bArr);
    }

    public boolean m3126i() {
        return this.f3171d.m3094a();
    }

    public C0932a m3127j() {
        return this.f3171d;
    }

    private SharedPreferences m3107o() {
        return f3162b.getSharedPreferences(f3166g + f3163c, 0);
    }

    public SharedPreferences m3128k() {
        return f3162b.getSharedPreferences(f3167h + f3163c, 0);
    }

    public SharedPreferences m3129l() {
        return f3162b.getSharedPreferences(f3168i + f3163c, 0);
    }

    public SharedPreferences m3130m() {
        return f3162b.getSharedPreferences(f3169j + f3163c, 0);
    }

    private String m3108p() {
        return f3167h + f3163c;
    }

    private String m3109q() {
        SharedPreferences a = ap.m3451a(f3162b);
        if (a == null) {
            return f3170k + f3163c + bj.m3526c(f3162b);
        }
        int i = a.getInt(C0919a.f3128y, 0);
        int parseInt = Integer.parseInt(bj.m3526c(f3162b));
        if (i == 0 || parseInt == i) {
            return f3170k + f3163c + bj.m3526c(f3162b);
        }
        return f3170k + f3163c + i;
    }

    public byte[] m3118b(final av avVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            final StringBuilder stringBuilder = new StringBuilder();
            jSONObject.put("header", new JSONObject(this) {
                final /* synthetic */ C0934h f3151c;
            });
            JSONObject c09293 = new JSONObject(this) {
                final /* synthetic */ C0934h f3154c;
            };
            if (c09293.length() > 0) {
                jSONObject.put("body", c09293);
            }
            bl.m3576b("serialize entry:" + String.valueOf(stringBuilder));
            return String.valueOf(jSONObject).getBytes();
        } catch (Throwable e) {
            bl.m3596e("Fail to serialize log ...", e);
            return null;
        }
    }

    private void m3102a(av avVar, JSONObject jSONObject, StringBuilder stringBuilder) throws Exception {
        jSONObject.put("appkey", avVar.f3693a.f3662a);
        jSONObject.put(au.f3573b, avVar.f3693a.f3663b);
        if (avVar.f3693a.f3664c != null) {
            jSONObject.put(au.f3574c, avVar.f3693a.f3664c);
        }
        jSONObject.put(au.f3575d, avVar.f3693a.f3665d);
        jSONObject.put(au.f3578g, avVar.f3693a.f3668g);
        jSONObject.put("package_name", avVar.f3693a.f3666e);
        jSONObject.put(au.f3577f, avVar.f3693a.f3667f);
        jSONObject.put("version_code", avVar.f3693a.f3669h);
        jSONObject.put(au.f3580i, avVar.f3693a.f3670i);
        jSONObject.put(au.f3581j, avVar.f3693a.f3671j);
        jSONObject.put(au.f3582k, avVar.f3693a.f3672k);
        jSONObject.put("sdk_version", avVar.f3693a.f3673l);
        jSONObject.put(au.f3584m, avVar.f3693a.f3674m);
        jSONObject.put(au.f3585n, avVar.f3693a.f3675n);
        jSONObject.put(au.f3586o, avVar.f3693a.f3676o);
        jSONObject.put("os", avVar.f3693a.f3677p);
        jSONObject.put(au.f3588q, avVar.f3693a.f3678q);
        jSONObject.put(au.f3589r, avVar.f3693a.f3679r);
        jSONObject.put("mc", avVar.f3693a.f3680s);
        jSONObject.put(au.f3592u, avVar.f3693a.f3681t);
        jSONObject.put(au.f3593v, avVar.f3693a.f3682u);
        jSONObject.put(au.f3594w, avVar.f3693a.f3683v);
        jSONObject.put(au.f3595x, avVar.f3693a.f3684w);
        jSONObject.put(au.f3596y, avVar.f3693a.f3685x);
        jSONObject.put(au.f3597z, avVar.f3693a.f3686y);
        jSONObject.put(au.f3546A, avVar.f3693a.f3687z);
        jSONObject.put("device_name", avVar.f3693a.f3647A);
        if (avVar.f3693a.f3648B != null) {
            jSONObject.put(au.f3548C, avVar.f3693a.f3648B);
        }
        if (avVar.f3693a.f3649C != null) {
            jSONObject.put(au.f3549D, avVar.f3693a.f3649C);
        }
        jSONObject.put(au.f3550E, avVar.f3693a.f3650D);
        jSONObject.put(au.f3551F, avVar.f3693a.f3651E);
        jSONObject.put("country", avVar.f3693a.f3652F);
        jSONObject.put(au.f3553H, avVar.f3693a.f3653G);
        jSONObject.put(au.f3554I, avVar.f3693a.f3654H);
        jSONObject.put(au.f3555J, avVar.f3693a.f3655I);
        jSONObject.put(au.f3591t, avVar.f3693a.f3656J == null ? "" : avVar.f3693a.f3656J);
        jSONObject.put(au.f3556K, avVar.f3693a.f3657K);
        jSONObject.put(au.f3557L, avVar.f3693a.f3658L);
        jSONObject.put(au.f3558M, avVar.f3693a.f3659M);
        jSONObject.put(au.f3559N, avVar.f3693a.f3660N);
        jSONObject.put(au.f3560O, avVar.f3693a.f3661O);
        stringBuilder.append("sdk_version:").append(avVar.f3693a.f3673l).append(";device_id:").append(avVar.f3693a.f3681t).append(";device_manufacturer:").append(avVar.f3693a.f3686y).append(";device_board:").append(avVar.f3693a.f3683v).append(";device_brand:").append(avVar.f3693a.f3684w).append(";os_version:").append(avVar.f3693a.f3678q);
    }

    private void m3105b(av avVar, JSONObject jSONObject, StringBuilder stringBuilder) throws Exception {
        JSONObject jSONObject2;
        List list;
        JSONArray jSONArray;
        int i;
        JSONArray jSONArray2;
        C1468h c1468h;
        JSONArray jSONArray3;
        int i2;
        JSONObject jSONObject3;
        Object value;
        JSONObject jSONObject4;
        JSONObject jSONObject5 = new JSONObject();
        if (!(avVar.f3694b.f3643h == null || avVar.f3694b.f3643h.f3607a == null || avVar.f3694b.f3643h.f3607a.size() <= 0)) {
            jSONObject2 = new JSONObject();
            for (Entry entry : avVar.f3694b.f3643h.f3607a.entrySet()) {
                String str = (String) entry.getKey();
                list = (List) entry.getValue();
                jSONArray = new JSONArray();
                for (i = 0; i < list.size(); i++) {
                    C1465e c1465e = (C1465e) list.get(i);
                    JSONObject jSONObject6 = new JSONObject();
                    jSONObject6.put(au.ar, c1465e.f3610a);
                    jSONObject6.put(au.as, c1465e.f3611b);
                    jSONObject6.put(au.at, c1465e.f3612c);
                    jSONObject6.put("count", c1465e.f3613d);
                    jSONObject6.put(au.av, new JSONArray(c1465e.f3614e));
                    jSONArray.put(jSONObject6);
                }
                jSONObject2.put(str, jSONArray);
            }
            jSONObject5.put(au.aq, jSONObject2);
        }
        if (!(avVar.f3694b.f3643h == null || avVar.f3694b.f3643h.f3608b == null || avVar.f3694b.f3643h.f3608b.size() <= 0)) {
            jSONObject2 = new JSONObject();
            for (Entry entry2 : avVar.f3694b.f3643h.f3608b.entrySet()) {
                str = (String) entry2.getKey();
                list = (List) entry2.getValue();
                jSONArray = new JSONArray();
                for (i = 0; i < list.size(); i++) {
                    C1466f c1466f = (C1466f) list.get(i);
                    jSONObject6 = new JSONObject();
                    jSONObject6.put("value", c1466f.f3616a);
                    jSONObject6.put("ts", c1466f.f3617b);
                    jSONObject6.put("label", c1466f.f3618c);
                    jSONArray.put(jSONObject6);
                }
                jSONObject2.put(str, jSONArray);
            }
            jSONObject5.put(au.aw, jSONObject2);
        }
        if (jSONObject5 != null && jSONObject5.length() > 0) {
            jSONObject.put(au.ap, jSONObject5);
            stringBuilder.append("; cc: ").append(jSONObject5.toString());
        }
        if (avVar.f3694b.f3636a != null && avVar.f3694b.f3636a.size() > 0) {
            jSONArray2 = new JSONArray();
            for (i = 0; i < avVar.f3694b.f3636a.size(); i++) {
                c1468h = (C1468h) avVar.f3694b.f3636a.get(i);
                jSONArray3 = new JSONArray();
                for (i2 = 0; i2 < c1468h.f3625b.size(); i2++) {
                    jSONObject3 = new JSONObject();
                    C1922j c1922j = (C1922j) c1468h.f3625b.get(i2);
                    jSONObject3.put("id", c1922j.f4921c);
                    jSONObject3.put("ts", c1922j.f4922d);
                    jSONObject3.put(au.aI, c1922j.f4923e);
                    for (Entry entry3 : c1922j.f4924f.entrySet()) {
                        value = entry3.getValue();
                        if ((value instanceof String) || (value instanceof Integer) || (value instanceof Long)) {
                            jSONObject3.put((String) entry3.getKey(), entry3.getValue());
                        }
                    }
                    jSONArray3.put(jSONObject3);
                }
                if (!(c1468h.f3624a == null || jSONArray3 == null || jSONArray3.length() <= 0)) {
                    JSONObject jSONObject7 = new JSONObject();
                    jSONObject7.put(c1468h.f3624a, jSONArray3);
                    jSONArray2.put(jSONObject7);
                }
            }
            if (jSONArray2 != null && jSONArray2.length() > 0) {
                jSONObject.put(au.aE, jSONArray2);
                stringBuilder.append(";ekv:").append(jSONArray2.toString());
            }
        }
        if (avVar.f3694b.f3637b != null && avVar.f3694b.f3637b.size() > 0) {
            jSONArray2 = new JSONArray();
            for (i = 0; i < avVar.f3694b.f3637b.size(); i++) {
                c1468h = (C1468h) avVar.f3694b.f3637b.get(i);
                jSONArray3 = new JSONArray();
                for (i2 = 0; i2 < c1468h.f3625b.size(); i2++) {
                    c1922j = (C1922j) c1468h.f3625b.get(i2);
                    jSONObject3 = new JSONObject();
                    jSONObject3.put("id", c1922j.f4921c);
                    jSONObject3.put("ts", c1922j.f4922d);
                    jSONObject3.put(au.aI, c1922j.f4923e);
                    for (Entry entry32 : c1922j.f4924f.entrySet()) {
                        value = entry32.getValue();
                        if ((value instanceof String) || (value instanceof Integer) || (value instanceof Long)) {
                            jSONObject3.put((String) entry32.getKey(), entry32.getValue());
                        }
                    }
                    jSONArray3.put(jSONObject3);
                }
                if (!(c1468h.f3624a == null || jSONArray3 == null || jSONArray3.length() <= 0)) {
                    jSONObject7 = new JSONObject();
                    jSONObject7.put(c1468h.f3624a, jSONArray3);
                    jSONArray2.put(jSONObject7);
                }
            }
            if (jSONArray2 != null && jSONArray2.length() > 0) {
                jSONObject.put(au.aF, jSONArray2);
                stringBuilder.append("; gkv:").append(jSONArray2.toString());
            }
        }
        if (avVar.f3694b.f3644i != null && avVar.f3694b.f3644i.size() > 0) {
            JSONArray jSONArray4 = new JSONArray();
            for (int i3 = 0; i3 < avVar.f3694b.f3644i.size(); i3++) {
                C1921i c1921i = (C1921i) avVar.f3694b.f3644i.get(i3);
                JSONObject jSONObject8 = new JSONObject();
                jSONObject8.put("ts", c1921i.f4915a);
                jSONObject8.put(au.aC, c1921i.f4916b);
                jSONObject8.put(au.aD, c1921i.f4917c);
                jSONArray4.put(jSONObject8);
            }
            jSONObject.put("error", jSONArray4);
        }
        if (avVar.f3694b.f3638c != null && avVar.f3694b.f3638c.size() > 0) {
            JSONArray jSONArray5 = new JSONArray();
            for (int i4 = 0; i4 < avVar.f3694b.f3638c.size(); i4++) {
                C1923o c1923o = (C1923o) avVar.f3694b.f3638c.get(i4);
                jSONObject5 = new JSONObject();
                jSONObject5.put("id", c1923o.f4928b);
                jSONObject5.put("start_time", c1923o.f4929c);
                jSONObject5.put("end_time", c1923o.f4930d);
                jSONObject5.put("duration", c1923o.f4931e);
                if (!(c1923o.f4934i.f3689a == 0 && c1923o.f4934i.f3690b == 0)) {
                    jSONObject7 = new JSONObject();
                    jSONObject7.put(au.ad, c1923o.f4934i.f3689a);
                    jSONObject7.put(au.ac, c1923o.f4934i.f3690b);
                    jSONObject5.put(au.ab, jSONObject7);
                }
                if (c1923o.f4933h.size() > 0) {
                    jSONArray2 = new JSONArray();
                    for (C1470l c1470l : c1923o.f4933h) {
                        jSONObject3 = new JSONObject();
                        jSONObject3.put(au.f3567V, c1470l.f3631a);
                        jSONObject3.put("duration", c1470l.f3632b);
                        jSONArray2.put(jSONObject3);
                    }
                    jSONObject5.put(au.f3566U, jSONArray2);
                }
                if (c1923o.f4935j.f3629c != 0) {
                    JSONArray jSONArray6 = new JSONArray();
                    jSONObject2 = new JSONObject();
                    jSONObject2.put(au.f3570Y, c1923o.f4935j.f3627a);
                    jSONObject2.put(au.f3571Z, c1923o.f4935j.f3628b);
                    jSONObject2.put("ts", c1923o.f4935j.f3629c);
                    jSONArray6.put(jSONObject2);
                    jSONObject5.put(au.f3569X, jSONArray6);
                }
                jSONArray5.put(jSONObject5);
            }
            if (jSONArray5 != null && jSONArray5.length() > 0) {
                jSONObject.put("sessions", jSONArray5);
                stringBuilder.append("; sessions:").append(jSONArray5.toString());
            }
        }
        if (avVar.f3694b.f3639d.f3602a != 0) {
            jSONObject4 = new JSONObject();
            jSONObject4.put("ts", avVar.f3694b.f3639d.f3602a);
            if (jSONObject4.length() > 0) {
                jSONObject.put(au.ae, jSONObject4);
                stringBuilder.append("; active_msg: ").append(jSONObject4.toString());
            }
        }
        if (avVar.f3694b.f3640e.f3622c) {
            jSONObject4 = new JSONObject();
            jSONObject7 = new JSONObject();
            jSONObject7.put(au.aj, avVar.f3694b.f3640e.f3621b);
            jSONObject7.put(au.ai, avVar.f3694b.f3640e.f3620a);
            jSONObject4.put(au.ah, jSONObject7);
            if (jSONObject4.length() > 0) {
                jSONObject.put(au.ag, jSONObject4);
                stringBuilder.append("; control_policy: ").append(jSONObject4.toString());
            }
        }
        if (avVar.f3694b.f3641f.size() > 0) {
            JSONObject jSONObject9 = new JSONObject();
            for (Entry entry22 : avVar.f3694b.f3641f.entrySet()) {
                jSONObject9.put((String) entry22.getKey(), entry22.getValue());
            }
            jSONObject.put(au.ak, jSONObject9);
        }
        if (!(avVar.f3694b.f3642g.f3604a == null && avVar.f3694b.f3642g.f3605b == null)) {
            jSONObject4 = new JSONObject();
            jSONObject4.put("provider", avVar.f3694b.f3642g.f3604a);
            jSONObject4.put(au.ao, avVar.f3694b.f3642g.f3605b);
            if (jSONObject4.length() > 0) {
                jSONObject.put(au.am, jSONObject4);
                stringBuilder.append("; active_user: ").append(jSONObject4.toString());
            }
        }
        if (avVar.f3694b.f3645j != null) {
            jSONObject.put("userlevel", avVar.f3694b.f3645j);
        }
    }
}
