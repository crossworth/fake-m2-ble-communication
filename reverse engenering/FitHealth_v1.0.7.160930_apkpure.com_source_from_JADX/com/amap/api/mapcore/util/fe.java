package com.amap.api.mapcore.util;

import android.content.Context;
import java.lang.reflect.Constructor;

/* compiled from: InstanceFactory */
public class fe {
    public static <T> T m885a(Context context, dv dvVar, String str, Class cls, Class[] clsArr, Object[] objArr) throws dk {
        fg a;
        T t = null;
        try {
            a = ex.m840a().m842a(context, dvVar);
        } catch (Throwable th) {
            eb.m742a(th, "InstanceFactory", "getInstance");
            a = null;
        }
        if (a != null) {
            try {
                if (a.m889a() && a.f602d) {
                    Class loadClass = a.loadClass(str);
                    if (loadClass != null) {
                        Constructor declaredConstructor = loadClass.getDeclaredConstructor(clsArr);
                        declaredConstructor.setAccessible(true);
                        t = declaredConstructor.newInstance(objArr);
                        return t;
                    }
                }
            } catch (Throwable th2) {
                eb.m742a(th2, "InstanceFactory", "getInstance()");
            } catch (Throwable th22) {
                eb.m742a(th22, "InstanceFactory", "getInstance()");
            } catch (Throwable th222) {
                eb.m742a(th222, "InstanceFactory", "getInstance()");
            } catch (Throwable th2222) {
                eb.m742a(th2222, "InstanceFactory", "getInstance()");
            } catch (Throwable th22222) {
                eb.m742a(th22222, "InstanceFactory", "getInstance()");
            } catch (Throwable th222222) {
                eb.m742a(th222222, "InstanceFactory", "getInstance()");
            } catch (Throwable th2222222) {
                eb.m742a(th2222222, "InstanceFactory", "getInstance()");
            }
        }
        if (cls != null) {
            try {
                Constructor declaredConstructor2 = cls.getDeclaredConstructor(clsArr);
                declaredConstructor2.setAccessible(true);
                t = declaredConstructor2.newInstance(objArr);
            } catch (Throwable e) {
                eb.m742a(e, "InstanceFactory", "getInstance()");
                throw new dk("获取对象错误");
            } catch (Throwable e2) {
                eb.m742a(e2, "InstanceFactory", "getInstance()");
                throw new dk("获取对象错误");
            } catch (Throwable e22) {
                eb.m742a(e22, "InstanceFactory", "getInstance()");
                throw new dk("获取对象错误");
            } catch (Throwable e222) {
                eb.m742a(e222, "InstanceFactory", "getInstance()");
                throw new dk("获取对象错误");
            } catch (Throwable e2222) {
                eb.m742a(e2222, "InstanceFactory", "getInstance()");
                throw new dk("获取对象错误");
            } catch (Throwable e22222) {
                eb.m742a(e22222, "InstanceFactory", "getInstance()");
                throw new dk("获取对象错误");
            }
        }
        return t;
    }
}
