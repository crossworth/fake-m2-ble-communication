package com.droi.sdk.core.priv;

import com.droi.sdk.DroiError;
import java.util.List;

public abstract class C0782a {
    public abstract DroiError mo1881a(C0903g<String, Object> c0903g);

    public abstract DroiError mo1882a(Object obj, String str);

    protected String m2422a(Class cls) {
        return (cls == Number.class || Number.class.isAssignableFrom(cls) || cls.isPrimitive()) ? "NUMERIC" : "TEXT";
    }

    protected String m2423a(Object obj) {
        return ((obj instanceof Number) || Number.class.isAssignableFrom(obj.getClass())) ? "NUMERIC" : "TEXT";
    }

    public abstract List mo1883a(C0903g<String, Object> c0903g, DroiError droiError);

    public abstract DroiError mo1884b(C0903g<String, Object> c0903g);

    protected boolean m2426b(Object obj) {
        return (obj instanceof Number) || Number.class.isAssignableFrom(obj.getClass());
    }

    public abstract DroiError mo1885c(C0903g<String, Object> c0903g);

    public abstract DroiError mo1886d(C0903g<String, Object> c0903g);
}
