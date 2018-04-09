package org.andengine.entity.scene.background.modifier;

import org.andengine.entity.scene.background.IBackground;
import org.andengine.entity.scene.background.modifier.IBackgroundModifier.IBackgroundModifierListener;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.BaseTripleValueSpanModifier;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class ColorBackgroundModifier extends BaseTripleValueSpanModifier<IBackground> implements IBackgroundModifier {
    public ColorBackgroundModifier(float pDuration, Color pFromColor, Color pToColor) {
        this(pDuration, pFromColor.getRed(), pToColor.getRed(), pFromColor.getGreen(), pToColor.getGreen(), pFromColor.getBlue(), pToColor.getBlue(), null, EaseLinear.getInstance());
    }

    public ColorBackgroundModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue) {
        this(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, null, EaseLinear.getInstance());
    }

    public ColorBackgroundModifier(float pDuration, Color pFromColor, Color pToColor, IEaseFunction pEaseFunction) {
        this(pDuration, pFromColor.getRed(), pToColor.getRed(), pFromColor.getGreen(), pToColor.getGreen(), pFromColor.getBlue(), pToColor.getBlue(), null, pEaseFunction);
    }

    public ColorBackgroundModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue, IEaseFunction pEaseFunction) {
        this(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, null, pEaseFunction);
    }

    public ColorBackgroundModifier(float pDuration, Color pFromColor, Color pToColor, IBackgroundModifierListener pBackgroundModifierListener) {
        super(pDuration, pFromColor.getRed(), pToColor.getRed(), pFromColor.getGreen(), pToColor.getGreen(), pFromColor.getBlue(), pToColor.getBlue(), pBackgroundModifierListener, EaseLinear.getInstance());
    }

    public ColorBackgroundModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue, IBackgroundModifierListener pBackgroundModifierListener) {
        super(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, pBackgroundModifierListener, EaseLinear.getInstance());
    }

    public ColorBackgroundModifier(float pDuration, Color pFromColor, Color pToColor, IBackgroundModifierListener pBackgroundModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromColor.getRed(), pToColor.getRed(), pFromColor.getGreen(), pToColor.getGreen(), pFromColor.getBlue(), pToColor.getBlue(), pBackgroundModifierListener, pEaseFunction);
    }

    public ColorBackgroundModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue, IBackgroundModifierListener pBackgroundModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, pBackgroundModifierListener, pEaseFunction);
    }

    protected ColorBackgroundModifier(ColorBackgroundModifier pColorBackgroundModifier) {
        super(pColorBackgroundModifier);
    }

    public ColorBackgroundModifier deepCopy() {
        return new ColorBackgroundModifier(this);
    }

    protected void onSetInitialValues(IBackground pBackground, float pRed, float pGreen, float pBlue) {
        pBackground.setColor(pRed, pGreen, pBlue);
    }

    protected void onSetValues(IBackground pBackground, float pPerctentageDone, float pRed, float pGreen, float pBlue) {
        pBackground.setColor(pRed, pGreen, pBlue);
    }
}
