package org.andengine.engine.handler.timer;

import org.andengine.engine.handler.IUpdateHandler;

public class TimerHandler implements IUpdateHandler {
    private boolean mAutoReset;
    protected final ITimerCallback mTimerCallback;
    private boolean mTimerCallbackTriggered;
    private float mTimerSeconds;
    private float mTimerSecondsElapsed;

    public TimerHandler(float pTimerSeconds, ITimerCallback pTimerCallback) {
        this(pTimerSeconds, false, pTimerCallback);
    }

    public TimerHandler(float pTimerSeconds, boolean pAutoReset, ITimerCallback pTimerCallback) {
        if (pTimerSeconds <= 0.0f) {
            throw new IllegalStateException("pTimerSeconds must be > 0!");
        }
        this.mTimerSeconds = pTimerSeconds;
        this.mAutoReset = pAutoReset;
        this.mTimerCallback = pTimerCallback;
    }

    public boolean isAutoReset() {
        return this.mAutoReset;
    }

    public void setAutoReset(boolean pAutoReset) {
        this.mAutoReset = pAutoReset;
    }

    public void setTimerSeconds(float pTimerSeconds) {
        if (pTimerSeconds <= 0.0f) {
            throw new IllegalStateException("pTimerSeconds must be > 0!");
        }
        this.mTimerSeconds = pTimerSeconds;
    }

    public float getTimerSeconds() {
        return this.mTimerSeconds;
    }

    public float getTimerSecondsElapsed() {
        return this.mTimerSecondsElapsed;
    }

    public boolean isTimerCallbackTriggered() {
        return this.mTimerCallbackTriggered;
    }

    public void setTimerCallbackTriggered(boolean pTimerCallbackTriggered) {
        this.mTimerCallbackTriggered = pTimerCallbackTriggered;
    }

    public void onUpdate(float pSecondsElapsed) {
        if (this.mAutoReset) {
            this.mTimerSecondsElapsed += pSecondsElapsed;
            while (this.mTimerSecondsElapsed >= this.mTimerSeconds) {
                this.mTimerSecondsElapsed -= this.mTimerSeconds;
                this.mTimerCallback.onTimePassed(this);
            }
        } else if (!this.mTimerCallbackTriggered) {
            this.mTimerSecondsElapsed += pSecondsElapsed;
            if (this.mTimerSecondsElapsed >= this.mTimerSeconds) {
                this.mTimerCallbackTriggered = true;
                this.mTimerCallback.onTimePassed(this);
            }
        }
    }

    public void reset() {
        this.mTimerCallbackTriggered = false;
        this.mTimerSecondsElapsed = 0.0f;
    }
}
