package com.umeng.socialize.shareboard.wigets;

import android.view.View;
import android.view.ViewGroup;

public abstract class ActionFrameAdapter {
    public abstract int getCount();

    public abstract Object getItem(int i);

    public abstract View getView(int i, ViewGroup viewGroup);
}
