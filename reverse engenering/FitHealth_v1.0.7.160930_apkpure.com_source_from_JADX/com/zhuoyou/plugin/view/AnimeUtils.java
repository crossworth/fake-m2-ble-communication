package com.zhuoyou.plugin.view;

import android.animation.ObjectAnimator;
import android.view.View;

public class AnimeUtils {
    public static void playTranstionAnime(View view, float trans, int duration) {
        ObjectAnimator objAnime = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, new float[]{trans});
        objAnime.setDuration((long) duration);
        objAnime.start();
    }
}
