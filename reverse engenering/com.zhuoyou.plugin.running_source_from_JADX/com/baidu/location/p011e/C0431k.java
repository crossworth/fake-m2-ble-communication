package com.baidu.location.p011e;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.baidu.location.Jni;
import com.baidu.location.p006h.C0335e;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class C0431k {
    private static final String f703d = String.format(Locale.US, "DELETE FROM LOG WHERE timestamp NOT IN (SELECT timestamp FROM LOG ORDER BY timestamp DESC LIMIT %d);", new Object[]{Integer.valueOf(MessageHandler.WHAT_ITEM_SELECTED)});
    private static final String f704e = String.format(Locale.US, "SELECT * FROM LOG ORDER BY timestamp DESC LIMIT %d;", new Object[]{Integer.valueOf(3)});
    private String f705a = null;
    private final SQLiteDatabase f706b;
    private final C0430a f707c;

    private class C0430a extends C0335e {
        final /* synthetic */ C0431k f696a;
        private int f697b;
        private long f698c;
        private String f699d;
        private boolean f700e;
        private boolean f701f;
        private C0431k f702p;

        C0430a(C0431k c0431k, C0431k c0431k2) {
            this.f696a = c0431k;
            this.f702p = c0431k2;
            this.f699d = null;
            this.f700e = false;
            this.f701f = false;
            this.k = new HashMap();
            this.f697b = 0;
            this.f698c = -1;
        }

        private void m798b() {
            if (!this.f700e) {
                this.f699d = this.f702p.m806b();
                if (this.f698c != -1 && this.f698c + LogBuilder.MAX_INTERVAL <= System.currentTimeMillis()) {
                    this.f697b = 0;
                    this.f698c = -1;
                }
                if (this.f699d != null && this.f697b < 2) {
                    this.f700e = true;
                    m204e();
                }
            }
        }

        public void mo1741a() {
            this.k.clear();
            this.k.put("qt", "ofbh");
            this.k.put("req", this.f699d);
            this.h = C0426h.f675a;
        }

        public void mo1742a(boolean z) {
            this.f701f = false;
            if (z && this.j != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.j);
                    if (jSONObject != null && jSONObject.has("error") && jSONObject.getInt("error") == 161) {
                        this.f701f = true;
                    }
                } catch (Exception e) {
                }
            }
            this.f700e = false;
            if (!this.f701f) {
                this.f697b++;
                this.f698c = System.currentTimeMillis();
            }
            new C0432l(this).start();
        }
    }

    C0431k(SQLiteDatabase sQLiteDatabase) {
        this.f706b = sQLiteDatabase;
        this.f707c = new C0430a(this, this);
        if (this.f706b != null && this.f706b.isOpen()) {
            try {
                this.f706b.execSQL("CREATE TABLE IF NOT EXISTS LOG(timestamp LONG PRIMARY KEY, log VARCHAR(4000))");
            } catch (Exception e) {
            }
        }
    }

    private void m805a(boolean z) {
        if (z && this.f705a != null) {
            String format = String.format("DELETE FROM LOG WHERE timestamp in (%s);", new Object[]{this.f705a});
            try {
                if (this.f705a.length() > 0) {
                    this.f706b.execSQL(format);
                }
            } catch (Exception e) {
            }
        }
        this.f705a = null;
    }

    private String m806b() {
        Throwable th;
        String str = null;
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        Cursor rawQuery;
        try {
            rawQuery = this.f706b.rawQuery(f704e, null);
            if (rawQuery != null) {
                try {
                    if (rawQuery.getCount() > 0) {
                        StringBuffer stringBuffer = new StringBuffer();
                        rawQuery.moveToFirst();
                        while (!rawQuery.isAfterLast()) {
                            jSONArray.put(rawQuery.getString(1));
                            if (stringBuffer.length() != 0) {
                                stringBuffer.append(",");
                            }
                            stringBuffer.append(rawQuery.getLong(0));
                            rawQuery.moveToNext();
                        }
                        try {
                            jSONObject.put("ofloc", jSONArray);
                            str = jSONObject.toString();
                        } catch (JSONException e) {
                        }
                        this.f705a = stringBuffer.toString();
                    }
                } catch (Exception e2) {
                    if (rawQuery != null) {
                        try {
                            rawQuery.close();
                        } catch (Exception e3) {
                        }
                    }
                    return str;
                } catch (Throwable th2) {
                    th = th2;
                    if (rawQuery != null) {
                        try {
                            rawQuery.close();
                        } catch (Exception e4) {
                        }
                    }
                    throw th;
                }
            }
            if (rawQuery != null) {
                try {
                    rawQuery.close();
                } catch (Exception e5) {
                }
            }
        } catch (Exception e6) {
            Object obj = str;
            if (rawQuery != null) {
                rawQuery.close();
            }
            return str;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            rawQuery = str;
            th = th4;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
        return str;
    }

    void m807a() {
        this.f707c.m798b();
    }

    void m808a(String str) {
        String encodeOfflineLocationUpdateRequest = Jni.encodeOfflineLocationUpdateRequest(str);
        try {
            this.f706b.execSQL(String.format(Locale.US, "INSERT OR IGNORE INTO LOG VALUES (%d,\"%s\");", new Object[]{Long.valueOf(System.currentTimeMillis()), encodeOfflineLocationUpdateRequest}));
            this.f706b.execSQL(f703d);
        } catch (Exception e) {
        }
    }
}
