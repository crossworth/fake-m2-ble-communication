package twitter4j;

import java.util.HashMap;
import java.util.Map;

public final class TwitterObjectFactory {
    private static final ThreadLocal<Map> rawJsonMap = new C21821();
    private static boolean registeredAtleastOnce = false;

    static class C21821 extends ThreadLocal<Map> {
        C21821() {
        }

        protected Map initialValue() {
            return new HashMap();
        }
    }

    private TwitterObjectFactory() {
        throw new AssertionError("not intended to be instantiated.");
    }

    public static String getRawJSON(Object obj) {
        if (registeredAtleastOnce) {
            Object json = ((Map) rawJsonMap.get()).get(obj);
            if (json instanceof String) {
                return (String) json;
            }
            if (json != null) {
                return json.toString();
            }
            return null;
        }
        throw new IllegalStateException("Apparently jsonStoreEnabled is not set to true.");
    }

    static void clearThreadLocalMap() {
        ((Map) rawJsonMap.get()).clear();
    }

    static <T> T registerJSONObject(T key, Object json) {
        registeredAtleastOnce = true;
        ((Map) rawJsonMap.get()).put(key, json);
        return key;
    }
}
