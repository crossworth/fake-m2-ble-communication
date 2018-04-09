package com.droi.sdk.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.droi.btlib.connection.MapConstants;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.priv.C0944p;
import com.droi.sdk.core.priv.CorePriv;
import com.droi.sdk.internal.DroiLog;
import org.json.JSONObject;

public class C0877b {
    private static final String f2792m = "SaveEventuallyQueue";
    private static Context f2793n = null;
    protected Object f2794a = new Object();
    private final String f2795b = "EventuallyQueue";
    private final String f2796c = "evenDB";
    private final String f2797d = "EvenQueue";
    private final String f2798e = MapConstants._ID;
    private final String f2799f = "_json";
    private final String f2800g = "_issaving";
    private final int f2801h = 10;
    private C0875a f2802i;
    private C0876b f2803j;
    private boolean f2804k = false;
    private boolean f2805l = false;
    private final DroiCallback<Integer> f2806o = new C08743(this);

    class C08743 implements DroiCallback<Integer> {
        final /* synthetic */ C0877b f2788a;

        C08743(C0877b c0877b) {
            this.f2788a = c0877b;
        }

        public void m2610a(Integer num, DroiError droiError) {
            if (!droiError.isOk()) {
                DroiLog.m2870e("EventuallyQueue", droiError.toString());
            } else if (NetworkUtils.isWifiOrMobileAvailable(num.intValue())) {
                this.f2788a.m2618d();
            }
        }

        public /* synthetic */ void result(Object obj, DroiError droiError) {
            m2610a((Integer) obj, droiError);
        }
    }

    private class C0875a extends SQLiteOpenHelper {
        final /* synthetic */ C0877b f2789a;

        private C0875a(C0877b c0877b, Context context) {
            this.f2789a = c0877b;
            super(context, "evenDB", null, 1);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    private class C0876b implements Runnable {
        final /* synthetic */ C0877b f2790a;
        private C0877b f2791b;

        public C0876b(C0877b c0877b, C0877b c0877b2) {
            this.f2790a = c0877b;
            this.f2791b = c0877b2;
        }

        public void run() {
            synchronized (this.f2791b) {
                this.f2791b.f2805l = true;
            }
            if (C0944p.m2792b(C0877b.f2793n)) {
                boolean c = this.f2791b.m2622f();
                if (this.f2790a.m2623g() || !c) {
                    synchronized (this.f2791b.f2794a) {
                        this.f2791b.f2794a.notifyAll();
                    }
                } else {
                    TaskDispatcher.getDispatcher(C0877b.f2792m).enqueueTask(this);
                }
                synchronized (this.f2791b) {
                    this.f2791b.f2805l = false;
                }
                return;
            }
            synchronized (this.f2791b) {
                this.f2791b.f2805l = false;
                this.f2791b.f2804k = false;
            }
            synchronized (this.f2791b.f2794a) {
                this.f2791b.f2794a.notifyAll();
            }
        }
    }

    public C0877b(Context context) {
        f2793n = context;
        this.f2802i = new C0875a(f2793n);
        this.f2803j = new C0876b(this, this);
        m2621e();
        m2618d();
    }

    private void m2612a(int i) {
        this.f2802i.getWritableDatabase().delete("EvenQueue", "_id=" + Integer.toString(i), null);
    }

    private void m2618d() {
        TaskDispatcher.getDispatcher(f2792m).enqueueTask(this.f2803j);
    }

    private void m2621e() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS ").append("EvenQueue");
        stringBuilder.append(" (").append(MapConstants._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT").append(", ").append("_json").append(" TEXT").append(", ").append("_issaving").append(" TEXT);");
        final String stringBuilder2 = stringBuilder.toString();
        final TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(f2792m);
        dispatcher.enqueueTask(new Runnable(this) {
            final /* synthetic */ C0877b f2787c;

            public void run() {
                try {
                    this.f2787c.f2802i.getWritableDatabase().execSQL(stringBuilder2);
                } catch (Exception e) {
                    DroiLog.m2873w("EventuallyQueue", e);
                }
                dispatcher.enqueueTask(this.f2787c.f2803j);
            }
        });
    }

    private boolean m2622f() {
        Cursor rawQuery;
        Cursor cursor = null;
        boolean z = false;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ").append("EvenQueue");
        stringBuilder.append(" ORDER BY ").append(MapConstants._ID).append(" ");
        stringBuilder.append(" LIMIT ").append(10);
        try {
            rawQuery = this.f2802i.getReadableDatabase().rawQuery(stringBuilder.toString(), null);
        } catch (Exception e) {
            DroiLog.m2873w("EventuallyQueue", e);
            rawQuery = cursor;
        }
        if (rawQuery != null && rawQuery.getCount() != 0) {
            rawQuery.getCount();
            int columnIndex = rawQuery.getColumnIndex(MapConstants._ID);
            int columnIndex2 = rawQuery.getColumnIndex("_json");
            int columnIndex3 = rawQuery.getColumnIndex("_issaving");
            if (rawQuery.moveToFirst()) {
                String string = rawQuery.getString(columnIndex2);
                boolean booleanValue = Boolean.valueOf(rawQuery.getString(columnIndex3)).booleanValue();
                try {
                    DroiObject fromJson = DroiObject.fromJson(new JSONObject(string));
                    fromJson.setLocalStorage(false);
                    fromJson.setDirtyFlag();
                    z = booleanValue ? fromJson.save().isOk() : fromJson.delete().isOk();
                    if (z) {
                        m2612a(rawQuery.getInt(columnIndex));
                    }
                } catch (Exception e2) {
                    DroiLog.m2869e("EventuallyQueue", e2);
                }
            }
            rawQuery.close();
        } else if (rawQuery != null) {
            rawQuery.close();
        }
        return z;
    }

    private boolean m2623g() {
        Cursor rawQuery;
        Cursor cursor = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ").append("EvenQueue");
        stringBuilder.append(" ORDER BY ").append(MapConstants._ID).append(" ");
        stringBuilder.append(" LIMIT ").append(10);
        try {
            rawQuery = this.f2802i.getReadableDatabase().rawQuery(stringBuilder.toString(), null);
        } catch (Exception e) {
            DroiLog.m2873w("EventuallyQueue", e);
            rawQuery = cursor;
        }
        boolean z = rawQuery == null || rawQuery.getCount() == 0;
        if (rawQuery != null) {
            rawQuery.close();
        }
        return z;
    }

    public void m2624a(DroiObject droiObject, boolean z) {
        final ContentValues contentValues = new ContentValues();
        droiObject.setLocalStorage(true);
        DroiError droiError = new DroiError();
        JSONObject toJson = droiObject.toJson(droiError);
        if (!droiError.isOk()) {
            DroiLog.m2870e("EventuallyQueue", droiError.toString());
        }
        contentValues.put("_json", toJson.toString());
        contentValues.put("_issaving", String.valueOf(z));
        droiObject.setLocalStorage(false);
        TaskDispatcher.getDispatcher(f2792m).enqueueTask(new Runnable(this) {
            final /* synthetic */ C0877b f2784b;

            public void run() {
                try {
                    this.f2784b.f2802i.getWritableDatabase().insertWithOnConflict("EvenQueue", null, contentValues, 4);
                } catch (Exception e) {
                    DroiLog.m2873w("EventuallyQueue", e);
                }
                this.f2784b.m2618d();
            }
        });
    }

    public boolean m2625a() {
        boolean z = true;
        synchronized (this) {
            if (this.f2804k || this.f2805l) {
            } else {
                this.f2804k = true;
                synchronized (this.f2794a) {
                    if (!m2623g()) {
                        TaskDispatcher.getDispatcher(f2792m).enqueueTask(this.f2803j);
                        try {
                            this.f2794a.wait();
                        } catch (InterruptedException e) {
                            z = false;
                        }
                    }
                }
                synchronized (this) {
                    if (!this.f2804k) {
                        z = false;
                    }
                    this.f2804k = false;
                }
            }
        }
        return z;
    }

    protected void m2626b() {
        NetworkUtils.registerNetworkStateListener(CorePriv.getContext(), this.f2806o);
        DroiLog.m2871i("EventuallyQueue", "Registered network monitor.");
    }
}
