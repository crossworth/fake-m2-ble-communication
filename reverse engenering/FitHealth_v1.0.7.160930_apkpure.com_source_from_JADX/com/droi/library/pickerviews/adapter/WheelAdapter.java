package com.droi.library.pickerviews.adapter;

public interface WheelAdapter<T> {
    T getItem(int i);

    int getItemsCount();

    int indexOf(T t);
}
