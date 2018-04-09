package p031u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import p031u.aly.av.C1470l;
import p031u.aly.av.C1923o;

/* compiled from: ViewPageTracker */
public class at {
    private static final String f3543a = "activities";
    private final Map<String, Long> f3544b = new HashMap();
    private final ArrayList<C1470l> f3545c = new ArrayList();

    public void m3479a(String str) {
        if (!TextUtils.isEmpty(str)) {
            synchronized (this.f3544b) {
                this.f3544b.put(str, Long.valueOf(System.currentTimeMillis()));
            }
        }
    }

    public void m3480b(String str) {
        if (!TextUtils.isEmpty(str)) {
            Long l;
            synchronized (this.f3544b) {
                l = (Long) this.f3544b.remove(str);
            }
            if (l == null) {
                bl.m3597e("please call 'onPageStart(%s)' before onPageEnd", str);
                return;
            }
            long currentTimeMillis = System.currentTimeMillis() - l.longValue();
            synchronized (this.f3545c) {
                C1470l c1470l = new C1470l();
                c1470l.f3631a = str;
                c1470l.f3632b = currentTimeMillis;
                this.f3545c.add(c1470l);
            }
        }
    }

    public void m3477a() {
        String str = null;
        long j = 0;
        synchronized (this.f3544b) {
            for (Entry entry : this.f3544b.entrySet()) {
                String str2;
                long j2;
                if (((Long) entry.getValue()).longValue() > j) {
                    long longValue = ((Long) entry.getValue()).longValue();
                    str2 = (String) entry.getKey();
                    j2 = longValue;
                } else {
                    j2 = j;
                    str2 = str;
                }
                str = str2;
                j = j2;
            }
        }
        if (str != null) {
            m3480b(str);
        }
    }

    public void m3478a(Context context) {
        SharedPreferences a = ap.m3451a(context);
        Editor edit = a.edit();
        if (this.f3545c.size() > 0) {
            Object string = a.getString(f3543a, "");
            StringBuilder stringBuilder = new StringBuilder();
            if (!TextUtils.isEmpty(string)) {
                stringBuilder.append(string);
                stringBuilder.append(";");
            }
            synchronized (this.f3545c) {
                Iterator it = this.f3545c.iterator();
                while (it.hasNext()) {
                    C1470l c1470l = (C1470l) it.next();
                    stringBuilder.append(String.format("[\"%s\",%d]", new Object[]{c1470l.f3631a, Long.valueOf(c1470l.f3632b)}));
                    stringBuilder.append(";");
                }
                this.f3545c.clear();
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            edit.remove(f3543a);
            edit.putString(f3543a, stringBuilder.toString());
        }
        edit.commit();
    }

    public static void m3476a(SharedPreferences sharedPreferences, C1923o c1923o) {
        Object string = sharedPreferences.getString(f3543a, "");
        if (!TextUtils.isEmpty(string)) {
            try {
                String[] split = string.split(";");
                for (String jSONArray : split) {
                    JSONArray jSONArray2 = new JSONArray(jSONArray);
                    C1470l c1470l = new C1470l();
                    c1470l.f3631a = jSONArray2.getString(0);
                    c1470l.f3632b = (long) jSONArray2.getInt(1);
                    c1923o.f4933h.add(c1470l);
                }
            } catch (Throwable e) {
                bl.m3598e(e);
            }
        }
    }
}
