package com.baidu.mapapi.map;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.ViewGroup.LayoutParams;

class C0497n extends AnimatorListenerAdapter {
    final /* synthetic */ LayoutParams f1433a;
    final /* synthetic */ int f1434b;
    final /* synthetic */ SwipeDismissTouchListener f1435c;

    C0497n(SwipeDismissTouchListener swipeDismissTouchListener, LayoutParams layoutParams, int i) {
        this.f1435c = swipeDismissTouchListener;
        this.f1433a = layoutParams;
        this.f1434b = i;
    }

    public void onAnimationEnd(Animator animator) {
        this.f1435c.f1288f.onDismiss(this.f1435c.f1287e, this.f1435c.f1294l);
        this.f1435c.f1287e.setTranslationX(0.0f);
        this.f1433a.height = this.f1434b;
        this.f1435c.f1287e.setLayoutParams(this.f1433a);
    }
}
