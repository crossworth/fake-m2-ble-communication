package org.andengine.entity.modifier;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

public class JumpModifier extends MoveModifier {
    private static final int JUMPCOUNT_DEFAULT = 1;
    protected final int mJumpCount;
    protected final float mJumpHeight;

    public JumpModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, float pJumpHeight) {
        this(pDuration, pFromX, pToX, pFromY, pToY, pJumpHeight, 1, EaseLinear.getInstance());
    }

    public JumpModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, float pJumpHeight, IEaseFunction pEaseFunction) {
        this(pDuration, pFromX, pToX, pFromY, pToY, pJumpHeight, 1, pEaseFunction);
    }

    public JumpModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, float pJumpHeight, IEntityModifierListener pEntityModifierListener) {
        this(pDuration, pFromX, pToX, pFromY, pToY, pJumpHeight, 1, pEntityModifierListener, EaseLinear.getInstance());
    }

    public JumpModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, float pJumpHeight, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        this(pDuration, pFromX, pToX, pFromY, pToY, pJumpHeight, 1, pEntityModifierListener, pEaseFunction);
    }

    public JumpModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, float pJumpHeight, int pJumpCount) {
        this(pDuration, pFromX, pToX, pFromY, pToY, pJumpHeight, pJumpCount, EaseLinear.getInstance());
    }

    public JumpModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, float pJumpHeight, int pJumpCount, IEaseFunction pEaseFunction) {
        this(pDuration, pFromX, pToX, pFromY, pToY, pJumpHeight, pJumpCount, null, pEaseFunction);
    }

    public JumpModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, float pJumpHeight, int pJumpCount, IEntityModifierListener pEntityModifierListener) {
        this(pDuration, pFromX, pToX, pFromY, pToY, pJumpHeight, pJumpCount, pEntityModifierListener, EaseLinear.getInstance());
    }

    public JumpModifier(float pDuration, float pFromX, float pToX, float pFromY, float pToY, float pJumpHeight, int pJumpCount, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pFromX, pToX, pFromY, pToY, pEntityModifierListener, pEaseFunction);
        this.mJumpHeight = pJumpHeight;
        this.mJumpCount = pJumpCount;
    }

    public JumpModifier(JumpModifier pJumpModifier) {
        super(pJumpModifier);
        this.mJumpHeight = pJumpModifier.mJumpHeight;
        this.mJumpCount = pJumpModifier.mJumpCount;
    }

    public JumpModifier deepCopy() throws DeepCopyNotSupportedException {
        return new JumpModifier(this);
    }

    protected void onSetValues(IEntity pEntity, float pPercentageDone, float pX, float pY) {
        float fraction = (((float) this.mJumpCount) * pPercentageDone) % 1.0f;
        super.onSetValues(pEntity, pPercentageDone, pX, pY - (((this.mJumpHeight * 4.0f) * fraction) * (1.0f - fraction)));
    }
}
