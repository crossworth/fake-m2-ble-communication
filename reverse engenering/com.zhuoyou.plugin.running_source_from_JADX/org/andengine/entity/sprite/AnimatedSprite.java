package org.andengine.entity.sprite;

import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.shader.PositionColorTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class AnimatedSprite extends TiledSprite {
    private static final int FRAMEINDEX_INVALID = -1;
    private final IAnimationData mAnimationData = new AnimationData();
    private IAnimationListener mAnimationListener;
    private long mAnimationProgress;
    private boolean mAnimationRunning;
    private boolean mAnimationStartedFired;
    private int mCurrentFrameIndex;
    private int mRemainingLoopCount;

    public interface IAnimationListener {
        void onAnimationFinished(AnimatedSprite animatedSprite);

        void onAnimationFrameChanged(AnimatedSprite animatedSprite, int i, int i2);

        void onAnimationLoopFinished(AnimatedSprite animatedSprite, int i, int i2);

        void onAnimationStarted(AnimatedSprite animatedSprite, int i);
    }

    public AnimatedSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, DrawType.DYNAMIC);
    }

    public AnimatedSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, DrawType.DYNAMIC, pShaderProgram);
    }

    public AnimatedSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, pDrawType);
    }

    public AnimatedSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public AnimatedSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
        super(pX, pY, pTiledTextureRegion, pTiledSpriteVertexBufferObject);
    }

    public AnimatedSprite(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pX, pY, pTiledTextureRegion, pTiledSpriteVertexBufferObject, pShaderProgram);
    }

    public AnimatedSprite(float pX, float pY, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager, DrawType.DYNAMIC);
    }

    public AnimatedSprite(float pX, float pY, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager, DrawType.DYNAMIC, pShaderProgram);
    }

    public AnimatedSprite(float pX, float pY, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager, pDrawType);
    }

    public AnimatedSprite(float pX, float pY, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public AnimatedSprite(float pX, float pY, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
        super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pTiledSpriteVertexBufferObject, PositionColorTextureCoordinatesShaderProgram.getInstance());
    }

    public AnimatedSprite(float pX, float pY, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pTiledSpriteVertexBufferObject, pShaderProgram);
    }

    public boolean isAnimationRunning() {
        return this.mAnimationRunning;
    }

    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        if (this.mAnimationRunning) {
            int loopCount = this.mAnimationData.getLoopCount();
            int[] frames = this.mAnimationData.getFrames();
            long animationDuration = this.mAnimationData.getAnimationDuration();
            if (!this.mAnimationStartedFired && this.mAnimationProgress == 0) {
                this.mAnimationStartedFired = true;
                if (frames == null) {
                    setCurrentTileIndex(this.mAnimationData.getFirstFrameIndex());
                } else {
                    setCurrentTileIndex(frames[0]);
                }
                this.mCurrentFrameIndex = 0;
                if (this.mAnimationListener != null) {
                    this.mAnimationListener.onAnimationStarted(this, loopCount);
                    this.mAnimationListener.onAnimationFrameChanged(this, -1, 0);
                }
            }
            this.mAnimationProgress += (long) (1.0E9f * pSecondsElapsed);
            if (loopCount == -1) {
                while (this.mAnimationProgress > animationDuration) {
                    this.mAnimationProgress -= animationDuration;
                    if (this.mAnimationListener != null) {
                        this.mAnimationListener.onAnimationLoopFinished(this, this.mRemainingLoopCount, loopCount);
                    }
                }
            } else {
                while (this.mAnimationProgress > animationDuration) {
                    this.mAnimationProgress -= animationDuration;
                    this.mRemainingLoopCount--;
                    if (this.mRemainingLoopCount < 0) {
                        break;
                    } else if (this.mAnimationListener != null) {
                        this.mAnimationListener.onAnimationLoopFinished(this, this.mRemainingLoopCount, loopCount);
                    }
                }
            }
            if (loopCount == -1 || this.mRemainingLoopCount >= 0) {
                int newFrameIndex = this.mAnimationData.calculateCurrentFrameIndex(this.mAnimationProgress);
                if (this.mCurrentFrameIndex != newFrameIndex) {
                    if (frames == null) {
                        setCurrentTileIndex(this.mAnimationData.getFirstFrameIndex() + newFrameIndex);
                    } else {
                        setCurrentTileIndex(frames[newFrameIndex]);
                    }
                    if (this.mAnimationListener != null) {
                        this.mAnimationListener.onAnimationFrameChanged(this, this.mCurrentFrameIndex, newFrameIndex);
                    }
                }
                this.mCurrentFrameIndex = newFrameIndex;
                return;
            }
            this.mAnimationRunning = false;
            if (this.mAnimationListener != null) {
                this.mAnimationListener.onAnimationFinished(this);
            }
        }
    }

    public void stopAnimation() {
        this.mAnimationRunning = false;
    }

    public void stopAnimation(int pTileIndex) {
        this.mAnimationRunning = false;
        setCurrentTileIndex(pTileIndex);
    }

    public void animate(long pFrameDurationEach) {
        animate(pFrameDurationEach, null);
    }

    public void animate(long pFrameDurationEach, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pFrameDurationEach, getTileCount());
        initAnimation(pAnimationListener);
    }

    public void animate(long pFrameDurationEach, boolean pLoop) {
        animate(pFrameDurationEach, pLoop, null);
    }

    public void animate(long pFrameDurationEach, boolean pLoop, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pFrameDurationEach, getTileCount(), pLoop);
        initAnimation(pAnimationListener);
    }

    public void animate(long pFrameDurationEach, int pLoopCount) {
        animate(pFrameDurationEach, pLoopCount, null);
    }

    public void animate(long pFrameDurationEach, int pLoopCount, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pFrameDurationEach, getTileCount(), pLoopCount);
        initAnimation(pAnimationListener);
    }

    public void animate(long[] pFrameDurations) {
        animate(pFrameDurations, (IAnimationListener) null);
    }

    public void animate(long[] pFrameDurations, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pFrameDurations);
        initAnimation(pAnimationListener);
    }

    public void animate(long[] pFrameDurations, boolean pLoop) {
        animate(pFrameDurations, pLoop, null);
    }

    public void animate(long[] pFrameDurations, boolean pLoop, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pFrameDurations, pLoop);
        initAnimation(pAnimationListener);
    }

    public void animate(long[] pFrameDurations, int pLoopCount) {
        animate(pFrameDurations, pLoopCount, null);
    }

    public void animate(long[] pFrameDurations, int pLoopCount, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pFrameDurations, pLoopCount);
        initAnimation(pAnimationListener);
    }

    public void animate(long[] pFrameDurations, int pFirstTileIndex, int pLastTileIndex, boolean pLoop) {
        animate(pFrameDurations, pFirstTileIndex, pLastTileIndex, pLoop, null);
    }

    public void animate(long[] pFrameDurations, int pFirstTileIndex, int pLastTileIndex, boolean pLoop, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pFrameDurations, pFirstTileIndex, pLastTileIndex, pLoop);
        initAnimation(pAnimationListener);
    }

    public void animate(long[] pFrameDurations, int pFirstTileIndex, int pLastTileIndex, int pLoopCount) {
        animate(pFrameDurations, pFirstTileIndex, pLastTileIndex, pLoopCount, null);
    }

    public void animate(long[] pFrameDurations, int pFirstTileIndex, int pLastTileIndex, int pLoopCount, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pFrameDurations, pFirstTileIndex, pLastTileIndex, pLoopCount);
        initAnimation(pAnimationListener);
    }

    public void animate(long[] pFrameDurations, int[] pFrames) {
        animate(pFrameDurations, pFrames, null);
    }

    public void animate(long[] pFrameDurations, int[] pFrames, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pFrameDurations, pFrames);
        initAnimation(pAnimationListener);
    }

    public void animate(long[] pFrameDurations, int[] pFrames, boolean pLoop) {
        animate(pFrameDurations, pFrames, pLoop, null);
    }

    public void animate(long[] pFrameDurations, int[] pFrames, boolean pLoop, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pFrameDurations, pFrames, pLoop);
        initAnimation(pAnimationListener);
    }

    public void animate(long[] pFrameDurations, int[] pFrames, int pLoopCount) {
        animate(pFrameDurations, pFrames, pLoopCount, null);
    }

    public void animate(long[] pFrameDurations, int[] pFrames, int pLoopCount, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pFrameDurations, pFrames, pLoopCount);
        initAnimation(pAnimationListener);
    }

    public void animate(IAnimationData pAnimationData) {
        animate(pAnimationData, null);
    }

    public void animate(IAnimationData pAnimationData, IAnimationListener pAnimationListener) {
        this.mAnimationData.set(pAnimationData);
        initAnimation(pAnimationListener);
    }

    private void initAnimation(IAnimationListener pAnimationListener) {
        this.mAnimationStartedFired = false;
        this.mAnimationListener = pAnimationListener;
        this.mRemainingLoopCount = this.mAnimationData.getLoopCount();
        this.mAnimationProgress = 0;
        this.mAnimationRunning = true;
    }
}
