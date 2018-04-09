package org.andengine.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public final class ViewUtils {
    public static final View inflate(Context pContext, int pLayoutID) {
        return LayoutInflater.from(pContext).inflate(pLayoutID, null);
    }

    public static final View inflate(Context pContext, int pLayoutID, ViewGroup pViewGroup) {
        return LayoutInflater.from(pContext).inflate(pLayoutID, pViewGroup, true);
    }
}
