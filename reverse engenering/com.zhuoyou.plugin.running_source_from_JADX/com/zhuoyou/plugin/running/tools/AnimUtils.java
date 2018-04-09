package com.zhuoyou.plugin.running.tools;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.app.TheApp;

public class AnimUtils {
    public static void playAnimList(Drawable drawable) {
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).start();
        }
    }

    public static void playTranstionAnime(View view, float trans, int duration, TimeInterpolator interpolator) {
        ObjectAnimator objAnime = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, new float[]{trans});
        objAnime.setInterpolator(interpolator);
        objAnime.setDuration((long) duration);
        objAnime.start();
    }

    public static void playTranstionAnime(View view, float trans, int duration) {
        ObjectAnimator objAnime = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, new float[]{trans});
        objAnime.setDuration((long) duration);
        objAnime.start();
    }

    public static void playZanButtonAnim(View view) {
        Animation animation = AnimationUtils.loadAnimation(TheApp.getContext(), C1680R.anim.zan_button_anim);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        view.setAnimation(animation);
    }
}
