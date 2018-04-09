package org.andengine.engine.camera.hud.controls;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

public abstract class BaseOnScreenControl extends HUD implements IOnSceneTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerID = -1;
    private final Sprite mControlBase;
    private final Sprite mControlKnob;
    private float mControlValueX;
    private float mControlValueY;
    private final IOnScreenControlListener mOnScreenControlListener;

    public interface IOnScreenControlListener {
        void onControlChange(BaseOnScreenControl baseOnScreenControl, float f, float f2);
    }

    class C20472 implements ITimerCallback {
        C20472() {
        }

        public void onTimePassed(TimerHandler pTimerHandler) {
            BaseOnScreenControl.this.mOnScreenControlListener.onControlChange(BaseOnScreenControl.this, BaseOnScreenControl.this.mControlValueX, BaseOnScreenControl.this.mControlValueY);
        }
    }

    public BaseOnScreenControl(float pX, float pY, Camera pCamera, ITextureRegion pControlBaseTextureRegion, ITextureRegion pControlKnobTextureRegion, float pTimeBetweenUpdates, VertexBufferObjectManager pVertexBufferObjectManager, IOnScreenControlListener pOnScreenControlListener) {
        setCamera(pCamera);
        this.mOnScreenControlListener = pOnScreenControlListener;
        this.mControlBase = new Sprite(pX, pY, pControlBaseTextureRegion, pVertexBufferObjectManager) {
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                return BaseOnScreenControl.this.onHandleControlBaseTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        this.mControlKnob = new Sprite(0.0f, 0.0f, pControlKnobTextureRegion, pVertexBufferObjectManager);
        onHandleControlKnobReleased();
        setOnSceneTouchListener(this);
        registerTouchArea(this.mControlBase);
        registerUpdateHandler(new TimerHandler(pTimeBetweenUpdates, true, new C20472()));
        attachChild(this.mControlBase);
        attachChild(this.mControlKnob);
        setTouchAreaBindingOnActionDownEnabled(true);
    }

    public Sprite getControlBase() {
        return this.mControlBase;
    }

    public Sprite getControlKnob() {
        return this.mControlKnob;
    }

    public IOnScreenControlListener getOnScreenControlListener() {
        return this.mOnScreenControlListener;
    }

    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.getPointerID() == this.mActivePointerID) {
            onHandleControlBaseLeft();
            switch (pSceneTouchEvent.getAction()) {
                case 1:
                case 3:
                    this.mActivePointerID = -1;
                    break;
            }
        }
        return false;
    }

    public void refreshControlKnobPosition() {
        onUpdateControlKnob(this.mControlValueX * 0.5f, this.mControlValueY * 0.5f);
    }

    protected void onHandleControlBaseLeft() {
        onUpdateControlKnob(0.0f, 0.0f);
    }

    protected void onHandleControlKnobReleased() {
        onUpdateControlKnob(0.0f, 0.0f);
    }

    protected boolean onHandleControlBaseTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        int pointerID = pSceneTouchEvent.getPointerID();
        switch (pSceneTouchEvent.getAction()) {
            case 0:
                if (this.mActivePointerID == -1) {
                    this.mActivePointerID = pointerID;
                    updateControlKnob(pTouchAreaLocalX, pTouchAreaLocalY);
                    break;
                }
                break;
            case 1:
            case 3:
                if (this.mActivePointerID == pointerID) {
                    this.mActivePointerID = -1;
                    onHandleControlKnobReleased();
                    break;
                }
                break;
            default:
                if (this.mActivePointerID == pointerID) {
                    updateControlKnob(pTouchAreaLocalX, pTouchAreaLocalY);
                    break;
                }
                break;
        }
        return true;
    }

    private void updateControlKnob(float pTouchAreaLocalX, float pTouchAreaLocalY) {
        Sprite controlBase = this.mControlBase;
        onUpdateControlKnob((MathUtils.bringToBounds(0.0f, controlBase.getWidth(), pTouchAreaLocalX) / controlBase.getWidth()) - 0.5f, (MathUtils.bringToBounds(0.0f, controlBase.getHeight(), pTouchAreaLocalY) / controlBase.getHeight()) - 0.5f);
    }

    protected void onUpdateControlKnob(float pRelativeX, float pRelativeY) {
        Sprite controlBase = this.mControlBase;
        Sprite controlKnob = this.mControlKnob;
        this.mControlValueX = 2.0f * pRelativeX;
        this.mControlValueY = 2.0f * pRelativeY;
        float[] controlBaseSceneCenterCoordinates = controlBase.getSceneCenterCoordinates();
        controlKnob.setPosition((controlBaseSceneCenterCoordinates[0] - (controlKnob.getWidth() * 0.5f)) + (controlBase.getWidthScaled() * pRelativeX), (controlBaseSceneCenterCoordinates[1] - (controlKnob.getHeight() * 0.5f)) + (controlBase.getHeightScaled() * pRelativeY));
    }
}
