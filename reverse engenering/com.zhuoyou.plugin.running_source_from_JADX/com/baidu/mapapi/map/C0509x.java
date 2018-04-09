package com.baidu.mapapi.map;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

class C0509x extends AnimatorListenerAdapter {
    final /* synthetic */ View f1454a;
    final /* synthetic */ WearMapView f1455b;

    C0509x(WearMapView wearMapView, View view) {
        this.f1455b = wearMapView;
        this.f1454a = view;
    }

    public void onAnimationEnd(Animator animator) {
        this.f1454a.setVisibility(4);
        super.onAnimationEnd(animator);
    }
}
