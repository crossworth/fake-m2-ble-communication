package com.tencent.stat;

import android.database.Cursor;
import com.zhuoyou.plugin.database.DataBaseContants;
import org.json.JSONObject;

class C0856t implements Runnable {
    final /* synthetic */ C0850n f2951a;

    C0856t(C0850n c0850n) {
        this.f2951a = c0850n;
    }

    public void run() {
        Object th;
        Throwable th2;
        Cursor query;
        try {
            query = this.f2951a.f2938d.getReadableDatabase().query(DataBaseContants.TABLE_CONFIG_NAME, null, null, null, null, null, null);
            while (query.moveToNext()) {
                try {
                    int i = query.getInt(0);
                    String string = query.getString(1);
                    String string2 = query.getString(2);
                    int i2 = query.getInt(3);
                    C0827b c0827b = new C0827b(i);
                    c0827b.f2867a = i;
                    c0827b.f2868b = new JSONObject(string);
                    c0827b.f2869c = string2;
                    c0827b.f2870d = i2;
                    StatConfig.m2624a(c0827b);
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
