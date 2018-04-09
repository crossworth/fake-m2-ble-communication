package com.lemon.cx.histogra;

public interface OnHistogramClickListener<T> {
    void getData(T t);

    void onTouchUp();
}
