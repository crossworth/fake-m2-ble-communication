package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class ColorModifier extends TripleValueSpanEntityModifier {
    public ColorModifier(float pDuration, Color pFromColor, Color pToColor) {
        this(pDuration, pFromColor.getRed(), pToColor.getRed(), pFromColor.getGreen(), pToColor.getGreen(), pFromColor.getBlue(), pToColor.getBlue(), null, EaseLinear.getInstance());
    }

    public ColorModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue) {
        this(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, null, EaseLinear.getInstance());
    }

    public ColorModifier(float pDuration, Color pFromColor, Color pToColor, IEaseFunction pEaseFunction) {
        this(pDuration, pFromColor.getRed(), pToColor.getRed(), pFromColor.getGreen(), pToColor.getGreen(), pFromColor.getBlue(), pToColor.getBlue(), null, pEaseFunction);
    }

    public ColorModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue, IEaseFunction pEaseFunction) {
        this(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, null, pEaseFunction);
    }

    public ColorModifier(float pDuration, Color pFromColor, Color pToColor, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromColor.getRed(), pToColor.getRed(), pFromColor.getGreen(), pToColor.getGreen(), pFromColor.getBlue(), pToColor.getBlue(), pEntityModifierListener, EaseLinear.getInstance());
    }

    public ColorModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, pEntityModifierListener, EaseLinear.getInstance());
    }

    public ColorModifier(float pDuration, Color pFromColor, Color pToColor, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromColor.getRed(), pToColor.getRed(), pFromColor.getGreen(), pToColor.getGreen(), pFromColor.getBlue(), pToColor.getBlue(), pEntityModifierListener, pEaseFunction);
    }

    public ColorModifier(float pDuration, float pFromRed, float pToRed, float pFromGreen, float pToGreen, float pFromBlue, float pToBlue, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, pEntityModifierListener, pEaseFunction);
    }

    protected ColorModifier(ColorModifier pColorModifier) {
        super(pColorModifier);
    }

    public ColorModifier deepCopy() {
        return new ColorModifier(this);
    }

    protected void onSetInitialValues(IEntity pEntity, float pRed, float pGreen, float pBlue) {
        pEntity.setColor(pRed, pGreen, pBlue);
    }

    protected void onSetValues(IEntity pEntity, float pPerctentageDone, float pRed, float pGreen, float pBlue) {
        pEntity.setColor(pRed, pGreen, pBlue);
    }
}
