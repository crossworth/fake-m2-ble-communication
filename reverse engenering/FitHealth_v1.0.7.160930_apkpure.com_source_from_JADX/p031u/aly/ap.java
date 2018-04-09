package p031u.aly;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: PreferenceWrapper */
public class ap {
    private static final String f3520a = "umeng_general_config";

    private ap() {
    }

    public static SharedPreferences m3452a(Context context, String str) {
        return context.getSharedPreferences(str, 0);
    }

    public static SharedPreferences m3451a(Context context) {
        return context.getSharedPreferences(f3520a, 0);
    }
}
