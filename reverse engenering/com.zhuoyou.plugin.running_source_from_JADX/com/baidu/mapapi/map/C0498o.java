package com.baidu.mapapi.map;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.ViewGroup.LayoutParams;

class C0498o implements AnimatorUpdateListener {
    final /* synthetic */ LayoutParams f1436a;
    final /* synthetic */ SwipeDismissTouchListener f1437b;

    C0498o(SwipeDismissTouchListener swipeDismissTouchListener, LayoutParams layoutParams) {
        this.f1437b = swipeDismissTouchListener;
        this.f1436a = layoutParams;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f1436a.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.f1437b.f1287e.setLayoutParams(this.f1436a);
    }
}
