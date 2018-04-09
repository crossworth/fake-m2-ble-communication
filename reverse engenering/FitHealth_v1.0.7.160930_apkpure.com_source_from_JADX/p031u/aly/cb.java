package p031u.aly;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* compiled from: FieldMetaData */
public class cb implements Serializable {
    private static Map<Class<? extends bp>, Map<? extends bw, cb>> f3731d = new HashMap();
    public final String f3732a;
    public final byte f3733b;
    public final cc f3734c;

    public cb(String str, byte b, cc ccVar) {
        this.f3732a = str;
        this.f3733b = b;
        this.f3734c = ccVar;
    }

    public static void m3680a(Class<? extends bp> cls, Map<? extends bw, cb> map) {
        f3731d.put(cls, map);
    }

    public static Map<? extends bw, cb> m3679a(Class<? extends bp> cls) {
        if (!f3731d.containsKey(cls)) {
            try {
                cls.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("InstantiationException for TBase class: " + cls.getName() + ", message: " + e.getMessage());
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("IllegalAccessException for TBase class: " + cls.getName() + ", message: " + e2.getMessage());
            }
        }
        return (Map) f3731d.get(cls);
    }
}
