package com.amap.api.services.proguard;

import android.content.Context;
import java.lang.reflect.Constructor;

/* compiled from: InstanceFactory */
public class ch {
    public static <T> T m1482a(Context context, ba baVar, String str, Class cls, Class[] clsArr, Object[] objArr) throws ar {
        cj a;
        T t = null;
        try {
            a = ca.m1437a().m1439a(context, baVar);
        } catch (Throwable th) {
            be.m1340a(th, "InstanceFactory", "getInstance");
            a = null;
        }
        if (a != null) {
            try {
                if (a.m1486a() && a.f1463d) {
                    Class loadClass = a.loadClass(str);
                    if (loadClass != null) {
                        Constructor declaredConstructor = loadClass.getDeclaredConstructor(clsArr);
                        declaredConstructor.setAccessible(true);
                        t = declaredConstructor.newInstance(objArr);
                        return t;
                    }
                }
            } catch (Throwable th2) {
                be.m1340a(th2, "InstanceFactory", "getInstance()");
            } catch (Throwable th22) {
                be.m1340a(th22, "InstanceFactory", "getInstance()");
            } catch (Throwable th222) {
                be.m1340a(th222, "InstanceFactory", "getInstance()");
            } catch (Throwable th2222) {
                be.m1340a(th2222, "InstanceFactory", "getInstance()");
            } catch (Throwable th22222) {
                be.m1340a(th22222, "InstanceFactory", "getInstance()");
            } catch (Throwable th222222) {
                be.m1340a(th222222, "InstanceFactory", "getInstance()");
            } catch (Throwable th2222222) {
                be.m1340a(th2222222, "InstanceFactory", "getInstance()");
            }
        }
        if (cls != null) {
            try {
                Constructor declaredConstructor2 = cls.getDeclaredConstructor(clsArr);
                declaredConstructor2.setAccessible(true);
                t = declaredConstructor2.newInstance(objArr);
            } catch (Throwable e) {
                be.m1340a(e, "InstanceFactory", "getInstance()");
                throw new ar("获取对象错误");
            } catch (Throwable e2) {
                be.m1340a(e2, "InstanceFactory", "getInstance()");
                throw new ar("获取对象错误");
            } catch (Throwable e22) {
                be.m1340a(e22, "InstanceFactory", "getInstance()");
                throw new ar("获取对象错误");
            } catch (Throwable e222) {
                be.m1340a(e222, "InstanceFactory", "getInstance()");
                throw new ar("获取对象错误");
            } catch (Throwable e2222) {
                be.m1340a(e2222, "InstanceFactory", "getInstance()");
                throw new ar("获取对象错误");
            } catch (Throwable e22222) {
                be.m1340a(e22222, "InstanceFactory", "getInstance()");
                throw new ar("获取对象错误");
            }
        }
        return t;
    }
}
