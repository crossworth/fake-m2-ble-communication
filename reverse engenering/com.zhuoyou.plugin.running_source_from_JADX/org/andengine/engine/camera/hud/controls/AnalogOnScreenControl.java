package org.andengine.engine.camera.hud.controls;

import android.util.FloatMath;
import lecho.lib.hellocharts.gesture.ChartZoomer;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

public class AnalogOnScreenControl extends BaseOnScreenControl implements IClickDetectorListener {
    private final ClickDetector mClickDetector = new ClickDetector(this);

    public interface IAnalogOnScreenControlListener extends IOnScreenControlListener {
        void onControlClick(AnalogOnScreenControl analogOnScreenControl);
    }

    public AnalogOnScreenControl(float pX, float pY, Camera pCamera, ITextureRegion pControlBaseTextureRegion, ITextureRegion pControlKnobTextureRegion, float pTimeBetweenUpdates, VertexBufferObjectManager pVertexBufferObjectManager, IAnalogOnScreenControlListener pAnalogOnScreenControlListener) {
        super(pX, pY, pCamera, pControlBaseTextureRegion, pControlKnobTextureRegion, pTimeBetweenUpdates, pVertexBufferObjectManager, pAnalogOnScreenControlListener);
        this.mClickDetector.setEnabled(false);
    }

    public AnalogOnScreenControl(float pX, float pY, Camera pCamera, ITextureRegion pControlBaseTextureRegion, ITextureRegion pControlKnobTextureRegion, float pTimeBetweenUpdates, long pOnControlClickMaximumMilliseconds, VertexBufferObjectManager pVertexBufferObjectManager, IAnalogOnScreenControlListener pAnalogOnScreenControlListener) {
        super(pX, pY, pCamera, pControlBaseTextureRegion, pControlKnobTextureRegion, pTimeBetweenUpdates, pVertexBufferObjectManager, pAnalogOnScreenControlListener);
        this.mClickDetector.setTriggerClickMaximumMilliseconds(pOnControlClickMaximumMilliseconds);
    }

    public IAnalogOnScreenControlListener getOnScreenControlListener() {
        return (IAnalogOnScreenControlListener) super.getOnScreenControlListener();
    }

    public void setOnControlClickEnabled(boolean pOnControlClickEnabled) {
        this.mClickDetector.setEnabled(pOnControlClickEnabled);
    }

    public void setOnControlClickMaximumMilliseconds(long pOnControlClickMaximumMilliseconds) {
        this.mClickDetector.setTriggerClickMaximumMilliseconds(pOnControlClickMaximumMilliseconds);
    }

    public void onClick(ClickDetector pClickDetector, int pPointerID, float pSceneX, float pSceneY) {
        getOnScreenControlListener().onControlClick(this);
    }

    protected boolean onHandleControlBaseTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        this.mClickDetector.onSceneTouchEvent(null, pSceneTouchEvent);
        return super.onHandleControlBaseTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }

    protected void onUpdateControlKnob(float pRelativeX, float pRelativeY) {
        if ((pRelativeX * pRelativeX) + (pRelativeY * pRelativeY) <= ChartZoomer.ZOOM_AMOUNT) {
            super.onUpdateControlKnob(pRelativeX, pRelativeY);
            return;
        }
        float angleRad = MathUtils.atan2(pRelativeY, pRelativeX);
        super.onUpdateControlKnob(FloatMath.cos(angleRad) * 0.5f, FloatMath.sin(angleRad) * 0.5f);
    }
}
