package com.baidu.mapapi.map;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

class C0496m extends AnimatorListenerAdapter {
    final /* synthetic */ SwipeDismissTouchListener f1432a;

    C0496m(SwipeDismissTouchListener swipeDismissTouchListener) {
        this.f1432a = swipeDismissTouchListener;
    }

    public void onAnimationEnd(Animator animator) {
        this.f1432a.m1182a();
    }
}
