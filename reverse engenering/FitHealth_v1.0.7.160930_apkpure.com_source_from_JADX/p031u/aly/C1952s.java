package p031u.aly;

import android.content.Context;
import android.provider.Settings.Secure;

/* compiled from: AndroidIdTracker */
public class C1952s extends C1522r {
    private static final String f5209a = "android_id";
    private Context f5210b;

    public C1952s(Context context) {
        super(f5209a);
        this.f5210b = context;
    }

    public String mo2746f() {
        String str = null;
        try {
            str = Secure.getString(this.f5210b.getContentResolver(), f5209a);
        } catch (Exception e) {
        }
        return str;
    }
}
