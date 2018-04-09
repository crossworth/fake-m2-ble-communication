package org.andengine.entity.sprite;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

public class ButtonSprite extends TiledSprite {
    private boolean mEnabled;
    private OnClickListener mOnClickListener;
    private State mState;
    private final int mStateCount;

    public interface OnClickListener {
        void onClick(ButtonSprite buttonSprite, float f, float f2);
    }

    public enum State {
        NORMAL(0),
        PRESSED(1),
        DISABLED(2);
        
        private final int mTiledTextureRegionIndex;

        private State(int pTiledTextureRegionIndex) {
            this.mTiledTextureRegionIndex = pTiledTextureRegionIndex;
        }

        public int getTiledTextureRegionIndex() {
            return this.mTiledTextureRegionIndex;
        }
    }

    public ButtonSprite(float pX, float pY, ITextureRegion pNormalTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager, (OnClickListener) null);
    }

    public ButtonSprite(float pX, float pY, ITextureRegion pNormalTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, OnClickListener pOnClickListener) {
        this(pX, pY, new TiledTextureRegion(pNormalTextureRegion.getTexture(), pNormalTextureRegion), pVertexBufferObjectManager, pOnClickListener);
    }

    public ButtonSprite(float pX, float pY, ITextureRegion pNormalTextureRegion, ITextureRegion pPressedTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pNormalTextureRegion, pPressedTextureRegion, pVertexBufferObjectManager, (OnClickListener) null);
    }

    public ButtonSprite(float pX, float pY, ITextureRegion pNormalTextureRegion, ITextureRegion pPressedTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, OnClickListener pOnClickListener) {
        this(pX, pY, new TiledTextureRegion(pNormalTextureRegion.getTexture(), pNormalTextureRegion, pPressedTextureRegion), pVertexBufferObjectManager, pOnClickListener);
    }

    public ButtonSprite(float pX, float pY, ITextureRegion pNormalTextureRegion, ITextureRegion pPressedTextureRegion, ITextureRegion pDisabledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pNormalTextureRegion, pPressedTextureRegion, pDisabledTextureRegion, pVertexBufferObjectManager, (OnClickListener) null);
    }

    public ButtonSprite(float pX, float pY, ITextureRegion pNormalTextureRegion, ITextureRegion pPressedTextureRegion, ITextureRegion pDisabledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, OnClickListener pOnClickListener) {
        this(pX, pY, new TiledTextureRegion(pNormalTextureRegion.getTexture(), pNormalTextureRegion, pPressedTextureRegion, pDisabledTextureRegion), pVertexBufferObjectManager, pOnClickListener);
    }

    public ButtonSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, (OnClickListener) null);
    }

    public ButtonSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, OnClickListener pOnClickListener) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
        this.mEnabled = true;
        this.mOnClickListener = pOnClickListener;
        this.mStateCount = pTiledTextureRegion.getTileCount();
        switch (this.mStateCount) {
            case 1:
                Debug.m4601w("No " + ITextureRegion.class.getSimpleName() + " supplied for " + State.class.getSimpleName() + "." + State.PRESSED + ".");
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                throw new IllegalArgumentException("The supplied " + ITiledTextureRegion.class.getSimpleName() + " has an unexpected amount of states: '" + this.mStateCount + "'.");
        }
        Debug.m4601w("No " + ITextureRegion.class.getSimpleName() + " supplied for " + State.class.getSimpleName() + "." + State.DISABLED + ".");
        changeState(State.NORMAL);
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean pEnabled) {
        this.mEnabled = pEnabled;
        if (this.mEnabled && this.mState == State.DISABLED) {
            changeState(State.NORMAL);
        } else if (!this.mEnabled) {
            changeState(State.DISABLED);
        }
    }

    public boolean isPressed() {
        return this.mState == State.PRESSED;
    }

    public State getState() {
        return this.mState;
    }

    public void setOnClickListener(OnClickListener pOnClickListener) {
        this.mOnClickListener = pOnClickListener;
    }

    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if (!isEnabled()) {
            changeState(State.DISABLED);
        } else if (pSceneTouchEvent.isActionDown()) {
            changeState(State.PRESSED);
        } else if (pSceneTouchEvent.isActionCancel() || !contains(pSceneTouchEvent.getX(), pSceneTouchEvent.getY())) {
            changeState(State.NORMAL);
        } else if (pSceneTouchEvent.isActionUp() && this.mState == State.PRESSED) {
            changeState(State.NORMAL);
            if (this.mOnClickListener != null) {
                this.mOnClickListener.onClick(this, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        }
        return true;
    }

    public boolean contains(float pX, float pY) {
        if (isVisible()) {
            return super.contains(pX, pY);
        }
        return false;
    }

    private void changeState(State pState) {
        if (pState != this.mState) {
            this.mState = pState;
            int stateTiledTextureRegionIndex = this.mState.getTiledTextureRegionIndex();
            if (stateTiledTextureRegionIndex >= this.mStateCount) {
                setCurrentTileIndex(0);
                Debug.m4601w(getClass().getSimpleName() + " changed its " + State.class.getSimpleName() + " to " + pState.toString() + ", which doesn't have a " + ITextureRegion.class.getSimpleName() + " supplied. Applying default " + ITextureRegion.class.getSimpleName() + ".");
                return;
            }
            setCurrentTileIndex(stateTiledTextureRegionIndex);
        }
    }
}
