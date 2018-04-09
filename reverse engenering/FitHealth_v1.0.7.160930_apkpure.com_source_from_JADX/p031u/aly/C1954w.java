package p031u.aly;

import android.content.Context;
import android.telephony.TelephonyManager;

/* compiled from: ImeiTracker */
public class C1954w extends C1522r {
    private static final String f5213a = "imei";
    private Context f5214b;

    public C1954w(Context context) {
        super("imei");
        this.f5214b = context;
    }

    public String mo2746f() {
        TelephonyManager telephonyManager = (TelephonyManager) this.f5214b.getSystemService("phone");
        if (telephonyManager == null) {
        }
        try {
            if (bj.m3518a(this.f5214b, "android.permission.READ_PHONE_STATE")) {
                return telephonyManager.getDeviceId();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
