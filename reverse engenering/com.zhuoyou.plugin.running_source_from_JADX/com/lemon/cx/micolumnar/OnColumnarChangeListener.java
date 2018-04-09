package com.lemon.cx.micolumnar;

public interface OnColumnarChangeListener<T extends MiColumnarData> {
    void onChange(T t);
}
