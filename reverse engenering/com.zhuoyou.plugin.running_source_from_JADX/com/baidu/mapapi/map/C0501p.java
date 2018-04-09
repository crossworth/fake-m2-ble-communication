package com.baidu.mapapi.map;

import android.view.View;
import com.baidu.mapapi.map.SwipeDismissTouchListener.DismissCallbacks;

class C0501p implements DismissCallbacks {
    final /* synthetic */ SwipeDismissView f1442a;

    C0501p(SwipeDismissView swipeDismissView) {
        this.f1442a = swipeDismissView;
    }

    public boolean canDismiss(Object obj) {
        return true;
    }

    public void onDismiss(View view, Object obj) {
        if (this.f1442a.f1299a != null) {
            this.f1442a.f1299a.onDismiss();
        }
    }

    public void onNotify() {
        if (this.f1442a.f1299a != null) {
            this.f1442a.f1299a.onNotify();
        }
    }
}
