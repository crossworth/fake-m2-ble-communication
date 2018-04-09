package p031u.aly;

import android.content.Context;
import com.facebook.GraphResponse;
import com.umeng.analytics.C0923f;
import com.umeng.analytics.C0924g;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import p031u.aly.av.C1922j;

/* compiled from: EventTracker */
public class ag {
    private final int f3508a = 128;
    private final int f3509b = 256;
    private final int f3510c = 10;
    private Context f3511d;
    private ae f3512e;

    public ag(Context context) {
        if (context == null) {
            throw new RuntimeException("Context is null, can't track event");
        }
        this.f3511d = context.getApplicationContext();
        this.f3512e = ae.m5075a(this.f3511d);
    }

    public void m3420a(String str, Map<String, Object> map, long j) {
        try {
            if (m3415a(str) && m3416a((Map) map)) {
                ai c1922j = new C1922j();
                c1922j.f4921c = str;
                c1922j.f4922d = System.currentTimeMillis();
                if (j > 0) {
                    c1922j.f4923e = j;
                }
                c1922j.f4919a = 1;
                Iterator it = map.entrySet().iterator();
                for (int i = 0; i < 10 && it.hasNext(); i++) {
                    Entry entry = (Entry) it.next();
                    c1922j.f4924f.put(entry.getKey(), entry.getValue());
                }
                if (c1922j.f4920b == null) {
                    c1922j.f4920b = ar.m3469g(this.f3511d);
                }
                this.f3512e.mo2748a(c1922j);
            }
        } catch (Throwable e) {
            bl.m3596e("Exception occurred in Mobclick.onEvent(). ", e);
        }
    }

    public void m3418a(String str, String str2, long j, int i) {
        if (m3415a(str) && m3417b(str2)) {
            Object obj;
            Map hashMap = new HashMap();
            if (str2 == null) {
                obj = "";
            } else {
                String str3 = str2;
            }
            hashMap.put(str, obj);
            ai c1922j = new C1922j();
            c1922j.f4921c = str;
            c1922j.f4922d = System.currentTimeMillis();
            if (j > 0) {
                c1922j.f4923e = j;
            }
            c1922j.f4919a = 1;
            hashMap = c1922j.f4924f;
            if (str2 == null) {
                str2 = "";
            }
            hashMap.put(str, str2);
            if (c1922j.f4920b == null) {
                c1922j.f4920b = ar.m3469g(this.f3511d);
            }
            this.f3512e.mo2748a(c1922j);
        }
    }

    public void m3419a(String str, Map<String, Object> map) {
        try {
            if (m3415a(str)) {
                ai c1922j = new C1922j();
                c1922j.f4921c = str;
                c1922j.f4922d = System.currentTimeMillis();
                c1922j.f4923e = 0;
                c1922j.f4919a = 2;
                Iterator it = map.entrySet().iterator();
                for (int i = 0; i < 10 && it.hasNext(); i++) {
                    Entry entry = (Entry) it.next();
                    c1922j.f4924f.put(entry.getKey(), entry.getValue());
                }
                if (c1922j.f4920b == null) {
                    c1922j.f4920b = ar.m3469g(this.f3511d);
                }
                this.f3512e.mo2748a(c1922j);
            }
        } catch (Throwable e) {
            bl.m3596e("Exception occurred in Mobclick.onEvent(). ", e);
        }
    }

    private boolean m3415a(String str) {
        if (str != null) {
            int length = str.trim().getBytes().length;
            if (length > 0 && length <= 128) {
                return true;
            }
        }
        bl.m3594e("Event id is empty or too long in tracking Event");
        return false;
    }

    private boolean m3417b(String str) {
        if (str == null || str.trim().getBytes().length <= 256) {
            return true;
        }
        bl.m3594e("Event label or value is empty or too long in tracking Event");
        return false;
    }

    private boolean m3416a(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            bl.m3594e("map is null or empty in onEvent");
            return false;
        }
        for (Entry entry : map.entrySet()) {
            if (!m3415a((String) entry.getKey())) {
                return false;
            }
            if (entry.getValue() == null) {
                return false;
            }
            if ((entry.getValue() instanceof String) && !m3417b(entry.getValue().toString())) {
                return false;
            }
        }
        return true;
    }

    public boolean m3421a(List<String> list, int i, String str) {
        C1516n a = C1516n.m3847a();
        if (list == null) {
            bl.m3594e("cklist is null!");
        } else if (list.size() <= 0) {
            bl.m3594e("the KeyList is null!");
            return false;
        } else {
            List arrayList = new ArrayList(list);
            if (a.m3852b((String) arrayList.get(0))) {
                String str2;
                String str3;
                if (arrayList.size() > 8) {
                    str3 = (String) arrayList.get(0);
                    arrayList.clear();
                    arrayList.add(str3);
                    arrayList.add("__cc");
                    arrayList.add("illegal");
                } else if (!a.m3850a(arrayList)) {
                    str3 = (String) arrayList.get(0);
                    arrayList.clear();
                    arrayList.add(str3);
                    arrayList.add("__cc");
                    arrayList.add("illegal");
                } else if (a.m3853b(arrayList)) {
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        str3 = (String) arrayList.get(i2);
                        if (str3.length() > 16) {
                            arrayList.remove(i2);
                            arrayList.add(i2, str3.substring(0, 16));
                        }
                    }
                } else {
                    str3 = (String) arrayList.get(0);
                    arrayList.clear();
                    arrayList.add(str3);
                    arrayList.add("__cc");
                    arrayList.add("illegal");
                }
                if (a.m3849a(str)) {
                    str2 = str;
                } else {
                    bl.m3594e("label  Invalid!");
                    str2 = "__illegal";
                }
                final Map hashMap = new HashMap();
                hashMap.put(arrayList, new C1509l(arrayList, (long) i, str2, System.currentTimeMillis()));
                C0923f.m3078b(new C0924g(this) {
                    final /* synthetic */ ag f4898b;

                    /* compiled from: EventTracker */
                    class C20191 extends C1950f {
                        final /* synthetic */ C19191 f5545a;

                        C20191(C19191 c19191) {
                            this.f5545a = c19191;
                        }

                        public void mo2823a(Object obj, boolean z) {
                            if (!obj.equals(GraphResponse.SUCCESS_KEY)) {
                            }
                        }
                    }

                    public void mo2152a() {
                        C1513m.m3810a(this.f4898b.f3511d).m3838a(new C20191(this), hashMap);
                    }
                });
            } else {
                bl.m3594e("Primary key Invalid!");
                return false;
            }
        }
        return true;
    }
}
