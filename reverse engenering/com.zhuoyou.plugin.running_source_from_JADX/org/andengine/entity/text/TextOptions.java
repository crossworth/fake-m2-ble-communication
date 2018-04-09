package org.andengine.entity.text;

import org.andengine.util.HorizontalAlign;

public class TextOptions {
    AutoWrap mAutoWrap;
    float mAutoWrapWidth;
    HorizontalAlign mHorizontalAlign;
    float mLeading;

    public TextOptions() {
        this(AutoWrap.NONE, 0.0f, HorizontalAlign.LEFT, 0.0f);
    }

    public TextOptions(HorizontalAlign pHorizontalAlign) {
        this(AutoWrap.NONE, 0.0f, pHorizontalAlign, 0.0f);
    }

    public TextOptions(AutoWrap pAutoWrap, float pAutoWrapWidth) {
        this(pAutoWrap, pAutoWrapWidth, HorizontalAlign.LEFT, 0.0f);
    }

    public TextOptions(AutoWrap pAutoWrap, float pAutoWrapWidth, HorizontalAlign pHorizontalAlign) {
        this(pAutoWrap, pAutoWrapWidth, pHorizontalAlign, 0.0f);
    }

    public TextOptions(AutoWrap pAutoWrap, float pAutoWrapWidth, HorizontalAlign pHorizontalAlign, float pLeading) {
        this.mAutoWrap = pAutoWrap;
        this.mAutoWrapWidth = pAutoWrapWidth;
        this.mHorizontalAlign = pHorizontalAlign;
        this.mLeading = pLeading;
    }

    public AutoWrap getAutoWrap() {
        return this.mAutoWrap;
    }

    public void setAutoWrap(AutoWrap pAutoWrap) {
        this.mAutoWrap = pAutoWrap;
    }

    public float getAutoWrapWidth() {
        return this.mAutoWrapWidth;
    }

    public void setAutoWrapWidth(float pAutoWrapWidth) {
        this.mAutoWrapWidth = pAutoWrapWidth;
    }

    public float getLeading() {
        return this.mLeading;
    }

    public void setLeading(float pLeading) {
        this.mLeading = pLeading;
    }

    public HorizontalAlign getHorizontalAlign() {
        return this.mHorizontalAlign;
    }

    public void setHorizontalAlign(HorizontalAlign pHorizontalAlign) {
        this.mHorizontalAlign = pHorizontalAlign;
    }
}
