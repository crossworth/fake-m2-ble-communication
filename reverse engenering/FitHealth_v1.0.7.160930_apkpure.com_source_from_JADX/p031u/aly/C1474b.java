package p031u.aly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: DatabaseManager */
public class C1474b {
    private static C1474b f3695c;
    private static SQLiteOpenHelper f3696d;
    private AtomicInteger f3697a = new AtomicInteger();
    private AtomicInteger f3698b = new AtomicInteger();
    private SQLiteDatabase f3699e;

    private static synchronized void m3484b(Context context) {
        synchronized (C1474b.class) {
            if (f3695c == null) {
                f3695c = new C1474b();
                f3696d = C1491c.m3673a(context);
            }
        }
    }

    public static synchronized C1474b m3483a(Context context) {
        C1474b c1474b;
        synchronized (C1474b.class) {
            if (f3695c == null) {
                C1474b.m3484b(context);
            }
            c1474b = f3695c;
        }
        return c1474b;
    }

    public synchronized SQLiteDatabase m3485a() {
        if (this.f3697a.incrementAndGet() == 1) {
            this.f3699e = f3696d.getReadableDatabase();
        }
        return this.f3699e;
    }

    public synchronized SQLiteDatabase m3486b() {
        if (this.f3697a.incrementAndGet() == 1) {
            this.f3699e = f3696d.getWritableDatabase();
        }
        return this.f3699e;
    }

    public synchronized void m3487c() {
        if (this.f3697a.decrementAndGet() == 0) {
            this.f3699e.close();
        }
        if (this.f3698b.decrementAndGet() == 0) {
            this.f3699e.close();
        }
    }
}
