package com.baidu.mapapi.map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.baidu.mapapi.map.WearMapView.OnDismissCallback;

public class SwipeDismissView extends RelativeLayout {
    OnDismissCallback f1299a = null;

    public SwipeDismissView(Context context, AttributeSet attributeSet, int i, View view) {
        super(context, attributeSet, i);
        m1187a(context, view);
    }

    public SwipeDismissView(Context context, AttributeSet attributeSet, View view) {
        super(context, attributeSet);
        m1187a(context, view);
    }

    public SwipeDismissView(Context context, View view) {
        super(context);
        m1187a(context, view);
    }

    void m1187a(Context context, View view) {
        setOnTouchListener(new SwipeDismissTouchListener(view, new Object(), new C0501p(this)));
    }

    public void setCallback(OnDismissCallback onDismissCallback) {
        this.f1299a = onDismissCallback;
    }
}
