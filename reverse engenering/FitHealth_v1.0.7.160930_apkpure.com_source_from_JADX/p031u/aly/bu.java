package p031u.aly;

import java.lang.reflect.InvocationTargetException;

/* compiled from: TEnumHelper */
public class bu {
    public static bt m3665a(Class<? extends bt> cls, int i) {
        try {
            return (bt) cls.getMethod("findByValue", new Class[]{Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(i)});
        } catch (NoSuchMethodException e) {
            return null;
        } catch (IllegalAccessException e2) {
            return null;
        } catch (InvocationTargetException e3) {
            return null;
        }
    }
}
