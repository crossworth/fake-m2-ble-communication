package com.xiaosu;

import java.util.ArrayList;
import java.util.List;

public abstract class DataSetAdapter<T> {
    List<T> data = new ArrayList();

    protected abstract String text(T t);

    public DataSetAdapter(List<T> data) {
        this.data = data;
    }

    public final String getText(int index) {
        return text(this.data.get(index));
    }

    public int getItemCount() {
        return (this.data == null || this.data.isEmpty()) ? 0 : this.data.size();
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return this.data == null || this.data.isEmpty();
    }
}
