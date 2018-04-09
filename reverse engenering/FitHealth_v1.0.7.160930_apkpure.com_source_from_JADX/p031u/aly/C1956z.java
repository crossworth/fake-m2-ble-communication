package p031u.aly;

import android.os.Build;
import android.os.Build.VERSION;

/* compiled from: SerialTracker */
public class C1956z extends C1522r {
    private static final String f5217a = "serial";

    public C1956z() {
        super(f5217a);
    }

    public String mo2746f() {
        if (VERSION.SDK_INT >= 9) {
            return Build.SERIAL;
        }
        return null;
    }
}
