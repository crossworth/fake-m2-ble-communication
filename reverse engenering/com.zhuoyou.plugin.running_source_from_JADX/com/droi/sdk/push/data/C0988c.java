package com.droi.sdk.push.data;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import com.droi.sdk.push.utils.C1011f;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import java.io.File;

public class C0988c extends ContextWrapper {
    public C0988c(Context context) {
        super(context);
    }

    public File m3043a() {
        File file = null;
        if (C1015j.m3172g(this)) {
            String b = C1011f.m3136b();
            File file2 = new File(b);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            file = new File(b + "/" + "push.db");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    C1012g.m3139b(e);
                }
            }
        }
        return file;
    }

    public SQLiteDatabase openOrCreateDatabase(String str, int i, CursorFactory cursorFactory) {
        return m3043a() != null ? SQLiteDatabase.openOrCreateDatabase(m3043a(), null) : null;
    }

    public SQLiteDatabase openOrCreateDatabase(String str, int i, CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        return m3043a() != null ? SQLiteDatabase.openOrCreateDatabase(m3043a(), null) : null;
    }
}
