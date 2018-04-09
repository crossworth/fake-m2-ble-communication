package org.andengine.entity.scene.background;

import android.opengl.GLES20;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ModifierList;

public class Background implements IBackground {
    private static final int BACKGROUNDMODIFIERS_CAPACITY_DEFAULT = 4;
    private ModifierList<IBackground> mBackgroundModifiers = null;
    private final Color mColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    private boolean mColorEnabled = true;

    protected Background() {
    }

    public Background(float pRed, float pGreen, float pBlue) {
        this.mColor.set(pRed, pGreen, pBlue);
    }

    public Background(float pRed, float pGreen, float pBlue, float pAlpha) {
        this.mColor.set(pRed, pGreen, pBlue, pAlpha);
    }

    public Background(Color pColor) {
        this.mColor.set(pColor);
    }

    public void setColor(float pRed, float pGreen, float pBlue) {
        this.mColor.set(pRed, pGreen, pBlue);
    }

    public void setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
        this.mColor.set(pRed, pGreen, pBlue, pAlpha);
    }

    public void setColor(Color pColor) {
        this.mColor.set(pColor);
    }

    public boolean isColorEnabled() {
        return this.mColorEnabled;
    }

    public void setColorEnabled(boolean pColorEnabled) {
        this.mColorEnabled = pColorEnabled;
    }

    public void registerBackgroundModifier(IModifier<IBackground> pBackgroundModifier) {
        if (this.mBackgroundModifiers == null) {
            allocateBackgroundModifiers();
        }
        this.mBackgroundModifiers.add((IModifier) pBackgroundModifier);
    }

    public boolean unregisterBackgroundModifier(IModifier<IBackground> pBackgroundModifier) {
        if (this.mBackgroundModifiers != null) {
            return this.mBackgroundModifiers.remove(pBackgroundModifier);
        }
        return false;
    }

    public void clearBackgroundModifiers() {
        if (this.mBackgroundModifiers != null) {
            this.mBackgroundModifiers.clear();
        }
    }

    public void onUpdate(float pSecondsElapsed) {
        if (this.mBackgroundModifiers != null) {
            this.mBackgroundModifiers.onUpdate(pSecondsElapsed);
        }
    }

    public void onDraw(GLState pGLState, Camera pCamera) {
        if (this.mColorEnabled) {
            GLES20.glClearColor(this.mColor.getRed(), this.mColor.getGreen(), this.mColor.getBlue(), this.mColor.getAlpha());
            GLES20.glClear(16384);
        }
    }

    public void reset() {
        this.mBackgroundModifiers.reset();
    }

    private void allocateBackgroundModifiers() {
        this.mBackgroundModifiers = new ModifierList(this, 4);
    }
}
