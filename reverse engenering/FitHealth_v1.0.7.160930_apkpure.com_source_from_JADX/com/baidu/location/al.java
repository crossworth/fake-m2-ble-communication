package com.baidu.location;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.baidu.location.GeofenceClient.OnAddBDGeofencesResultListener;
import com.baidu.location.GeofenceClient.OnRemoveBDGeofencesResultListener;
import com.baidu.location.p000a.C0495a;
import com.baidu.location.p000a.C0496b;
import com.baidu.location.p001b.p002a.C0512a;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class al implements an, C1619j {
    private static final String gA = "GeofenceMan";
    private static final int gB = 3;
    public static final int gC = 10;
    private static final String gD = "http://loc.map.baidu.com/fence";
    private static final String gE = "geofence_id";
    private static final String gF = ";";
    private static final String gH = "status_code";
    private static al gI = null;
    private static final int gK = 5;
    private static final int gL = 2;
    private static final int gM = 1;
    private static final String gO = "geofence_ids";
    private static final String f5423l = "GeofenceMan";
    private Object gG = new Object();
    private HandlerThread gJ;
    private C0507a gN;
    private Context gz;

    class C05062 implements Runnable {
        final /* synthetic */ al f2175a;

        C05062(al alVar) {
            this.f2175a = alVar;
        }

        public void run() {
            this.f2175a.bs();
        }
    }

    private class C0507a extends Handler {
        public static final int f2176do = 2;
        public static final int f2177for = 3;
        public static final int f2178if = 0;
        final /* synthetic */ al f2179a;

        public C0507a(al alVar, Looper looper) {
            this.f2179a = alVar;
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            Bundle data = message.getData();
            switch (i) {
                case 0:
                    String string;
                    if (data != null) {
                        i = data.getInt(al.gH, 1);
                        string = data.getString("geofence_id");
                    } else {
                        i = 1;
                        string = null;
                    }
                    this.f2179a.m5872if(i, string, (OnAddBDGeofencesResultListener) message.obj);
                    return;
                case 2:
                    String[] stringArray;
                    if (data != null) {
                        i = data.getInt(al.gH, 1);
                        stringArray = data.getStringArray(al.gO);
                    } else {
                        i = 1;
                        stringArray = null;
                    }
                    this.f2179a.m5873if(i, stringArray, (OnRemoveBDGeofencesResultListener) message.obj);
                    return;
                case 3:
                    this.f2179a.bq();
                    return;
                default:
                    return;
            }
        }
    }

    private class C0508c implements Runnable {
        final /* synthetic */ al f2180a;
        private final ah f2181do;
        private final OnAddBDGeofencesResultListener f2182for;
        private final String f2183if;

        public C0508c(al alVar, ah ahVar, String str, OnAddBDGeofencesResultListener onAddBDGeofencesResultListener) {
            this.f2180a = alVar;
            this.f2181do = ahVar;
            this.f2183if = str;
            this.f2182for = onAddBDGeofencesResultListener;
        }

        public void run() {
            this.f2180a.m5874if(this.f2182for, this.f2180a.m5868if(this.f2181do, this.f2183if), this.f2181do.getGeofenceId());
        }
    }

    private class C0509d implements Runnable {
        final /* synthetic */ al f2184a;
        private final OnRemoveBDGeofencesResultListener f2185do;
        private final List f2186if;

        public C0509d(al alVar, List list, OnRemoveBDGeofencesResultListener onRemoveBDGeofencesResultListener) {
            this.f2184a = alVar;
            this.f2186if = list;
            this.f2185do = onRemoveBDGeofencesResultListener;
        }

        public void run() {
            int i = this.f2184a.m5864do(this.f2186if);
            Message obtain = Message.obtain(this.f2184a.gN);
            obtain.what = 2;
            obtain.obj = this.f2185do;
            Bundle bundle = new Bundle();
            bundle.putInt(al.gH, i);
            bundle.putStringArray(al.gO, (String[]) this.f2186if.toArray(new String[this.f2186if.size()]));
            obtain.setData(bundle);
            this.f2184a.gN.sendMessage(obtain);
        }
    }

    class C2058b extends C1982o {
        private static final String dc = "error";
        private static final int dd = -3;
        private static final String de = "ext";
        private static final String df = "cl";
        private static final String dg = "fence";
        private static final String dh = "lac";
        private static final String dj = "wf";
        private static final String dl = "radius";
        private OnAddBDGeofencesResultListener db;
        final /* synthetic */ al di;
        private int dk;
        private final ah dm;

        public C2058b(al alVar, ah ahVar, OnAddBDGeofencesResultListener onAddBDGeofencesResultListener) {
            this.di = alVar;
            this.dm = ahVar;
            this.db = onAddBDGeofencesResultListener;
            this.cP = new ArrayList();
        }

        public void mo3704V() {
            this.cL = "http://loc.map.baidu.com/fence";
            DecimalFormat decimalFormat = new DecimalFormat("0.00000");
            this.cP.add(new BasicNameValuePair(dg, Jni.m5811f(String.format("&x=%s&y=%s&r=%s&coord=%s&type=%s&cu=%s", new Object[]{decimalFormat.format(this.dm.m4578a()), decimalFormat.format(this.dm.m4582byte()), String.valueOf(this.dm.m4592new()), String.valueOf(this.dm.m4591int()), Integer.valueOf(al.m5863do(this.di.gz)), C0512a.m2169if(this.di.gz)}))));
            this.cP.add(new BasicNameValuePair("ext", Jni.m5811f(String.format("&ki=%s&sn=%s", new Object[]{this.di.bp(), C0533q.m2206a(this.di.gz)}))));
        }

        public void m6257Z() {
            m6032R();
        }

        public void mo3705if(boolean z) {
            if (!z || this.cM == null) {
                this.di.m5874if(this.db, 1, this.dm.getGeofenceId());
                return;
            }
            Object obj = null;
            try {
                JSONObject jSONObject = new JSONObject(EntityUtils.toString(this.cM, "UTF-8"));
                if (jSONObject != null) {
                    Object string;
                    StringBuilder stringBuilder = new StringBuilder();
                    if (jSONObject.has(dh)) {
                        string = jSONObject.getString(dh);
                        if (!TextUtils.isEmpty(string)) {
                            stringBuilder.append(string);
                            this.dm.m4586do(true);
                        }
                    }
                    if (jSONObject.has(df)) {
                        string = jSONObject.getString(df);
                        if (!TextUtils.isEmpty(string)) {
                            stringBuilder.append(string);
                            this.dm.m4581a(true);
                        }
                    }
                    if (jSONObject.has(dj)) {
                        string = jSONObject.getString(dj);
                        if (!TextUtils.isEmpty(string)) {
                            stringBuilder.append(string);
                            this.dm.m4589if(true);
                        }
                    }
                    obj = stringBuilder.toString();
                    if (jSONObject.has("radius")) {
                        this.dm.m4579a(Float.valueOf(jSONObject.getString("radius")).floatValue());
                    }
                    if (jSONObject.has("error")) {
                        this.dk = Integer.valueOf(jSONObject.getString("error")).intValue();
                    }
                }
                if (!TextUtils.isEmpty(obj)) {
                    this.di.gN.post(new C0508c(this.di, this.dm, obj, this.db));
                } else if (this.dk == -3) {
                    this.di.m5874if(this.db, 1002, this.dm.getGeofenceId());
                } else {
                    this.di.m5874if(this.db, 1, this.dm.getGeofenceId());
                }
            } catch (Exception e) {
                this.di.m5874if(this.db, 1, this.dm.getGeofenceId());
            }
        }
    }

    private String bp() {
        Context context = this.gz;
        String str = LocationClient.PREF_FILE_NAME;
        Context context2 = this.gz;
        return context.getSharedPreferences(str, 0).getString(LocationClient.PREF_KEY_NAME, null);
    }

    private void br() {
        this.gJ = new HandlerThread("GeofenceMan", 10);
        this.gJ.start();
        this.gN = new C0507a(this, this.gJ.getLooper());
    }

    private synchronized void bs() {
        SQLiteDatabase writableDatabase = C0526i.m2195a(this.gz).getWritableDatabase();
        if (writableDatabase != null) {
            writableDatabase.beginTransaction();
            try {
                long currentTimeMillis = System.currentTimeMillis();
                writableDatabase.execSQL(String.format("DELETE FROM %s WHERE EXISTS (SELECT * FROM %s WHERE (%s + %s) < %d)", new Object[]{C0496b.f2133a, C0495a.f2124else, C0495a.f2119a, C0495a.f2132void, Long.valueOf(currentTimeMillis)}));
                writableDatabase.execSQL(String.format("DELETE FROM %s WHERE (%s + %s) < %d", new Object[]{C0495a.f2124else, C0495a.f2119a, C0495a.f2132void, Long.valueOf(currentTimeMillis)}));
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
                writableDatabase.close();
            } catch (Exception e) {
                writableDatabase.endTransaction();
                writableDatabase.close();
            } catch (Throwable th) {
                writableDatabase.endTransaction();
                writableDatabase.close();
            }
        }
    }

    private void bt() {
        this.gN.sendEmptyMessage(3);
    }

    private final void bu() {
        if (!C1982o.m6030if(this.gz)) {
            throw new IllegalStateException("Not net connection");
        }
    }

    private synchronized long bv() {
        long j;
        j = 0;
        try {
            SQLiteDatabase readableDatabase = C0526i.m2195a(this.gz).getReadableDatabase();
            if (readableDatabase != null) {
                j = DatabaseUtils.queryNumEntries(readableDatabase, C0495a.f2124else);
                readableDatabase.close();
            }
        } catch (Exception e) {
        }
        return j;
    }

    public static int m5863do(Context context) {
        String subscriberId = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        if (subscriberId != null) {
            if (subscriberId.startsWith("46000") || subscriberId.startsWith("46002") || subscriberId.startsWith("46007")) {
                return 1;
            }
            if (subscriberId.startsWith("46001")) {
                return 2;
            }
            if (subscriberId.startsWith("46003")) {
                return 3;
            }
        }
        return 5;
    }

    private synchronized int m5864do(List list) {
        int i;
        SQLiteDatabase writableDatabase = C0526i.m2195a(this.gz).getWritableDatabase();
        if (writableDatabase != null) {
            writableDatabase.beginTransaction();
            try {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String[] strArr = new String[]{(String) it.next()};
                    writableDatabase.delete(C0495a.f2124else, String.format("%s=?", new Object[]{"geofence_id"}), strArr);
                    writableDatabase.delete(C0496b.f2133a, String.format("%s=?", new Object[]{"geofence_id"}), strArr);
                }
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
                i = 0;
            } catch (Exception e) {
                writableDatabase.endTransaction();
                i = 1;
            } catch (Throwable th) {
                writableDatabase.endTransaction();
            }
            writableDatabase.close();
        } else {
            i = 0;
        }
        return i;
    }

    public static al m5867for(Context context) {
        if (gI == null) {
            gI = new al();
            gI.br();
            gI.gz = context;
        }
        return gI;
    }

    private synchronized int m5868if(ah ahVar, String str) {
        int i = 0;
        synchronized (this) {
            SQLiteDatabase writableDatabase = C0526i.m2195a(this.gz).getWritableDatabase();
            if (writableDatabase != null) {
                writableDatabase.beginTransaction();
                long currentTimeMillis = System.currentTimeMillis();
                try {
                    ContentValues contentValues = new ContentValues();
                    String geofenceId = ahVar.getGeofenceId();
                    contentValues.put("geofence_id", geofenceId);
                    contentValues.put("longitude", Double.valueOf(ahVar.m4578a()));
                    contentValues.put("latitude", Double.valueOf(ahVar.m4582byte()));
                    contentValues.put(C0495a.f2122char, Float.valueOf(ahVar.m4585do()));
                    contentValues.put(C0495a.f2131try, Integer.valueOf(ahVar.m4592new()));
                    contentValues.put(C0495a.f2119a, Long.valueOf(currentTimeMillis));
                    contentValues.put(C0495a.f2132void, Long.valueOf(ahVar.m4587else()));
                    contentValues.put(C0495a.f2128int, ahVar.m4591int());
                    contentValues.put(C0495a.f2120byte, Integer.valueOf(ahVar.m4593try() ? 1 : 0));
                    contentValues.put(C0495a.f2129long, Integer.valueOf(ahVar.m4590if() ? 1 : 0));
                    contentValues.put(C0495a.f2126goto, Integer.valueOf(ahVar.m4588for() ? 1 : 0));
                    contentValues.put(C0495a.f2130new, Integer.valueOf(0));
                    writableDatabase.insert(C0495a.f2124else, null, contentValues);
                    for (String str2 : str.split(gF)) {
                        String str22;
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("geofence_id", geofenceId);
                        contentValues2.put(C0496b.f2137int, str22);
                        int lastIndexOf = str22.lastIndexOf("|");
                        if (lastIndexOf != -1) {
                            str22 = str22.substring(0, lastIndexOf);
                        }
                        contentValues2.put(C0496b.f2135for, str22);
                        writableDatabase.insert(C0496b.f2133a, null, contentValues2);
                    }
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                } catch (Exception e) {
                    writableDatabase.endTransaction();
                    i = 1;
                } catch (Throwable th) {
                    writableDatabase.endTransaction();
                }
                writableDatabase.close();
            }
        }
        return i;
    }

    private void m5872if(int i, String str, OnAddBDGeofencesResultListener onAddBDGeofencesResultListener) {
        if (i == 1) {
            onAddBDGeofencesResultListener.onAddBDGeofencesResult(i, str);
        } else {
            onAddBDGeofencesResultListener.onAddBDGeofencesResult(i, str);
        }
    }

    private void m5873if(int i, String[] strArr, OnRemoveBDGeofencesResultListener onRemoveBDGeofencesResultListener) {
        onRemoveBDGeofencesResultListener.onRemoveBDGeofencesByRequestIdsResult(i, strArr);
    }

    private void m5874if(OnAddBDGeofencesResultListener onAddBDGeofencesResultListener, int i, String str) {
        Message obtain = Message.obtain(this.gN);
        obtain.what = 0;
        obtain.obj = onAddBDGeofencesResultListener;
        Bundle bundle = new Bundle();
        bundle.putInt(gH, i);
        bundle.putString("geofence_id", str);
        obtain.setData(bundle);
        this.gN.sendMessage(obtain);
    }

    public static void m5878int(Context context) {
        aq.b2().m5905byte(C1976f.getServiceContext());
    }

    public void bq() {
        synchronized (this.gG) {
            this.gN.post(new C05062(this));
        }
    }

    public void m5880if(final ah ahVar) {
        this.gN.post(new Runnable(this) {
            final /* synthetic */ al f2173a;

            public void run() {
                this.f2173a.m5883k(ahVar.getGeofenceId());
            }
        });
    }

    public void m5881if(ah ahVar, OnAddBDGeofencesResultListener onAddBDGeofencesResultListener) {
        bu();
        ae.m2136a((Object) onAddBDGeofencesResultListener, (Object) "OnAddBDGeofenceRecesResultListener not provided.");
        if (bv() >= 10) {
            onAddBDGeofencesResultListener.onAddBDGeofencesResult(1001, ahVar.getGeofenceId());
            return;
        }
        new C2058b(this, ahVar, onAddBDGeofencesResultListener).m6257Z();
        bt();
    }

    public void m5882if(List list, OnRemoveBDGeofencesResultListener onRemoveBDGeofencesResultListener) {
        boolean z = list != null && list.size() > 0;
        ae.m2141if(z, "geofenceRequestIds can't be null nor empty.");
        ae.m2136a((Object) onRemoveBDGeofencesResultListener, (Object) "onRemoveBDGeofencesResultListener not provided.");
        this.gN.post(new C0509d(this, list, onRemoveBDGeofencesResultListener));
    }

    public synchronized void m5883k(String str) {
        if (!TextUtils.isEmpty(str)) {
            SQLiteDatabase writableDatabase = C0526i.m2195a(this.gz).getWritableDatabase();
            if (writableDatabase != null) {
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(C0495a.f2130new, Long.valueOf(System.currentTimeMillis() + aq.iE));
                    writableDatabase.update(C0495a.f2124else, contentValues, "geofence_id= ?", new String[]{str});
                } catch (Exception e) {
                } finally {
                    writableDatabase.close();
                }
            }
        }
    }
}
