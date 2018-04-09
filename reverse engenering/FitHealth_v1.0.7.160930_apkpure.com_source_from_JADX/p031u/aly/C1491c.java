package p031u.aly;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import p031u.aly.C1502d.C1495a;
import p031u.aly.C1502d.C1498b;
import p031u.aly.C1502d.C1501c;

/* compiled from: UMCCDBHelper */
class C1491c extends SQLiteOpenHelper {
    private static Context f3729b;
    private String f3730a;

    /* compiled from: UMCCDBHelper */
    private static class C1490a {
        private static final C1491c f3728a = new C1491c(C1491c.f3729b, C1502d.m3740a(C1491c.f3729b), C1502d.f3813c, null, 1);

        private C1490a() {
        }
    }

    public static synchronized C1491c m3673a(Context context) {
        C1491c a;
        synchronized (C1491c.class) {
            f3729b = context;
            a = C1490a.f3728a;
        }
        return a;
    }

    private C1491c(Context context, String str, String str2, CursorFactory cursorFactory, int i) {
        this(new C1503e(context, str), str2, cursorFactory, i);
    }

    private C1491c(Context context, String str, CursorFactory cursorFactory, int i) {
        if (str == null || str.equals("")) {
            str = C1502d.f3813c;
        }
        super(context, str, cursorFactory, i);
        m3675b();
    }

    private void m3675b() {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (!(m3678a(C1495a.f3797a, writableDatabase) && m3678a(C1495a.f3798b, writableDatabase))) {
                m3677c(writableDatabase);
            }
            if (!m3678a(C1501c.f3810a, writableDatabase)) {
                m3676b(writableDatabase);
            }
            if (!m3678a(C1498b.f3801a, writableDatabase)) {
                m3674a(writableDatabase);
            }
        } catch (Exception e) {
        }
    }

    public boolean m3678a(String str, SQLiteDatabase sQLiteDatabase) {
        Cursor cursor = null;
        boolean z = false;
        if (str != null) {
            try {
                cursor = sQLiteDatabase.rawQuery("select count(*) as c from sqlite_master where type ='table' and name ='" + str.trim() + "' ", null);
                if (cursor.moveToNext() && cursor.getInt(0) > 0) {
                    z = true;
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return z;
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.beginTransaction();
            m3677c(sQLiteDatabase);
            m3676b(sQLiteDatabase);
            m3674a(sQLiteDatabase);
            sQLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    private boolean m3674a(SQLiteDatabase sQLiteDatabase) {
        try {
            this.f3730a = "create table if not exists limitedck(Id INTEGER primary key autoincrement, ck TEXT unique)";
            sQLiteDatabase.execSQL(this.f3730a);
            return true;
        } catch (SQLException e) {
            bl.m3594e("create reference table error!");
            return false;
        }
    }

    private boolean m3676b(SQLiteDatabase sQLiteDatabase) {
        try {
            this.f3730a = "create table if not exists system(Id INTEGER primary key autoincrement, key TEXT, timeStamp INTEGER, count INTEGER)";
            sQLiteDatabase.execSQL(this.f3730a);
            return true;
        } catch (SQLException e) {
            bl.m3594e("create system table error!");
            return false;
        }
    }

    private boolean m3677c(SQLiteDatabase sQLiteDatabase) {
        try {
            this.f3730a = "create table if not exists aggregated_cache(Id INTEGER primary key autoincrement, key TEXT, totalTimestamp TEXT, value INTEGER, count INTEGER, label TEXT, timeWindowNum TEXT)";
            sQLiteDatabase.execSQL(this.f3730a);
            this.f3730a = "create table if not exists aggregated(Id INTEGER primary key autoincrement, key TEXT, totalTimestamp TEXT, value INTEGER, count INTEGER, label TEXT, timeWindowNum TEXT)";
            sQLiteDatabase.execSQL(this.f3730a);
            return true;
        } catch (SQLException e) {
            bl.m3594e("create aggregated table error!");
            return false;
        }
    }
}
