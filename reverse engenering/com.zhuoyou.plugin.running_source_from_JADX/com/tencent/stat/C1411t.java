package com.tencent.stat;

import android.database.Cursor;
import com.droi.sdk.core.priv.PersistSettings;
import org.json.JSONObject;

class C1411t implements Runnable {
    final /* synthetic */ C1405n f4489a;

    C1411t(C1405n c1405n) {
        this.f4489a = c1405n;
    }

    public void run() {
        Cursor query;
        Object th;
        Throwable th2;
        try {
            query = this.f4489a.f4476d.getReadableDatabase().query(PersistSettings.CONFIG, null, null, null, null, null, null);
            while (query.moveToNext()) {
                try {
                    int i = query.getInt(0);
                    String string = query.getString(1);
                    String string2 = query.getString(2);
                    int i2 = query.getInt(3);
                    C1377b c1377b = new C1377b(i);
                    c1377b.f4387a = i;
                    c1377b.f4388b = new JSONObject(string);
                    c1377b.f4389c = string2;
                    c1377b.f4390d = i2;
                    StatConfig.m4008a(c1377b);
                } catch (Throwable th3) {
                    th = th3;
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Throwable th4) {
            th2 = th4;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th2;
        }
    }
}
