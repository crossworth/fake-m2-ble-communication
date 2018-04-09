package org.andengine.engine.options;

import org.andengine.engine.Engine.EngineLock;
import org.andengine.engine.Engine.UpdateThread;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;

public class EngineOptions {
    private final AudioOptions mAudioOptions = new AudioOptions();
    private final Camera mCamera;
    private EngineLock mEngineLock;
    private final boolean mFullscreen;
    private final RenderOptions mRenderOptions = new RenderOptions();
    private final IResolutionPolicy mResolutionPolicy;
    private final ScreenOrientation mScreenOrientation;
    private final TouchOptions mTouchOptions = new TouchOptions();
    private UpdateThread mUpdateThread;
    private int mUpdateThreadPriority = 0;
    private WakeLockOptions mWakeLockOptions = WakeLockOptions.SCREEN_ON;

    public EngineOptions(boolean pFullscreen, ScreenOrientation pScreenOrientation, IResolutionPolicy pResolutionPolicy, Camera pCamera) {
        this.mFullscreen = pFullscreen;
        this.mScreenOrientation = pScreenOrientation;
        this.mResolutionPolicy = pResolutionPolicy;
        this.mCamera = pCamera;
    }

    public boolean hasEngineLock() {
        return this.mEngineLock != null;
    }

    public EngineLock getEngineLock() {
        return this.mEngineLock;
    }

    public void setEngineLock(EngineLock pEngineLock) {
        this.mEngineLock = pEngineLock;
    }

    public TouchOptions getTouchOptions() {
        return this.mTouchOptions;
    }

    public AudioOptions getAudioOptions() {
        return this.mAudioOptions;
    }

    public RenderOptions getRenderOptions() {
        return this.mRenderOptions;
    }

    public boolean isFullscreen() {
        return this.mFullscreen;
    }

    public ScreenOrientation getScreenOrientation() {
        return this.mScreenOrientation;
    }

    public IResolutionPolicy getResolutionPolicy() {
        return this.mResolutionPolicy;
    }

    public Camera getCamera() {
        return this.mCamera;
    }

    public boolean hasUpdateThread() {
        return this.mUpdateThread != null;
    }

    public UpdateThread getUpdateThread() {
        return this.mUpdateThread;
    }

    public void setUpdateThread(UpdateThread pUpdateThread) {
        this.mUpdateThread = pUpdateThread;
    }

    public int getUpdateThreadPriority() {
        return this.mUpdateThreadPriority;
    }

    public void setUpdateThreadPriority(int pUpdateThreadPriority) {
        this.mUpdateThreadPriority = pUpdateThreadPriority;
    }

    public WakeLockOptions getWakeLockOptions() {
        return this.mWakeLockOptions;
    }

    public EngineOptions setWakeLockOptions(WakeLockOptions pWakeLockOptions) {
        this.mWakeLockOptions = pWakeLockOptions;
        return this;
    }
}
