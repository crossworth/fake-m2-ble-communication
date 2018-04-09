package com.google.gson;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

abstract class UnsafeAllocator {

    static class C17054 extends UnsafeAllocator {
        C17054() {
        }

        public <T> T newInstance(Class<T> c) {
            throw new UnsupportedOperationException("Cannot allocate " + c);
        }
    }

    public abstract <T> T newInstance(Class<T> cls) throws Exception;

    UnsafeAllocator() {
    }

    public static UnsafeAllocator create() {
        final Method newInstance;
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field f = unsafeClass.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            final Object unsafe = f.get(null);
            final Method allocateInstance = unsafeClass.getMethod("allocateInstance", new Class[]{Class.class});
            return new UnsafeAllocator() {
                public <T> T newInstance(Class<T> c) throws Exception {
                    return allocateInstance.invoke(unsafe, new Object[]{c});
                }
            };
        } catch (Exception e) {
            try {
                newInstance = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                newInstance.setAccessible(true);
                return new UnsafeAllocator() {
                    public <T> T newInstance(Class<T> c) throws Exception {
                        return newInstance.invoke(null, new Object[]{c, Object.class});
                    }
                };
            } catch (Exception e2) {
                try {
                    Method getConstructorId = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                    getConstructorId.setAccessible(true);
                    final int constructorId = ((Integer) getConstructorId.invoke(null, new Object[]{Object.class})).intValue();
                    newInstance = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                    newInstance.setAccessible(true);
                    return new UnsafeAllocator() {
                        public <T> T newInstance(Class<T> c) throws Exception {
                            return newInstance.invoke(null, new Object[]{c, Integer.valueOf(constructorId)});
                        }
                    };
                } catch (Exception e3) {
                    return new C17054();
                }
            }
        }
    }
}
