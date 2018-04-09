package org.andengine.entity.particle;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.opengl.util.GLState;

public class Particle<T extends IEntity> {
    private static final int EXPIRETIME_NEVER = -1;
    private T mEntity;
    private float mExpireTime = -1.0f;
    boolean mExpired;
    private float mLifeTime;
    private final PhysicsHandler mPhysicsHandler = new PhysicsHandler(null);

    public T getEntity() {
        return this.mEntity;
    }

    public void setEntity(T pEntity) {
        this.mEntity = pEntity;
        this.mPhysicsHandler.setEntity(pEntity);
    }

    public float getLifeTime() {
        return this.mLifeTime;
    }

    public float getExpireTime() {
        return this.mExpireTime;
    }

    public void setExpireTime(float pExpireTime) {
        this.mExpireTime = pExpireTime;
    }

    public boolean isExpired() {
        return this.mExpired;
    }

    public void setExpired(boolean pExpired) {
        this.mExpired = pExpired;
    }

    public PhysicsHandler getPhysicsHandler() {
        return this.mPhysicsHandler;
    }

    protected void onUpdate(float pSecondsElapsed) {
        if (!this.mExpired) {
            if (this.mExpireTime == -1.0f || this.mLifeTime + pSecondsElapsed < this.mExpireTime) {
                this.mLifeTime += pSecondsElapsed;
                this.mEntity.onUpdate(pSecondsElapsed);
                this.mPhysicsHandler.onUpdate(pSecondsElapsed);
                return;
            }
            float secondsElapsedUsed = this.mExpireTime - this.mLifeTime;
            this.mLifeTime = this.mExpireTime;
            this.mEntity.onUpdate(secondsElapsedUsed);
            this.mPhysicsHandler.onUpdate(secondsElapsedUsed);
            setExpired(true);
        }
    }

    public void onDraw(GLState pGLState, Camera pCamera) {
        if (!this.mExpired) {
            this.mEntity.onDraw(pGLState, pCamera);
        }
    }

    public void reset() {
        this.mEntity.reset();
        this.mPhysicsHandler.reset();
        this.mExpired = false;
        this.mExpireTime = -1.0f;
        this.mLifeTime = 0.0f;
    }
}
