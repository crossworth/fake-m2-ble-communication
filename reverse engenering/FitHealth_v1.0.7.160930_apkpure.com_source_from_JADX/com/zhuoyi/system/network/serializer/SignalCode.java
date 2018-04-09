package com.zhuoyi.system.network.serializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SignalCode {
    boolean autoRetry() default true;

    boolean encrypt() default false;

    int messageCode() default 0;
}
