package org.andengine.ui.activity;

import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.FrameLayout.LayoutParams;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.input.sensor.acceleration.AccelerationSensorOptions;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.sensor.location.ILocationListener;
import org.andengine.input.sensor.location.LocationSensorOptions;
import org.andengine.input.sensor.orientation.IOrientationListener;
import org.andengine.input.sensor.orientation.OrientationSensorOptions;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.shader.ShaderProgramManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.view.IRendererListener;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.IGameInterface.OnCreateResourcesCallback;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;
import org.andengine.util.ActivityUtils;
import org.andengine.util.Constants;
import org.andengine.util.debug.Debug;
import org.andengine.util.system.SystemUtils;

public abstract class BaseGameActivity extends BaseActivity implements IGameInterface, IRendererListener {
    private boolean mCreateGameCalled;
    protected Engine mEngine;
    private boolean mGameCreated;
    private boolean mGamePaused;
    private boolean mOnReloadResourcesScheduled;
    protected RenderSurfaceView mRenderSurfaceView;
    private WakeLock mWakeLock;

    class C20831 implements OnPopulateSceneCallback {
        C20831() {
        }

        public void onPopulateSceneFinished() {
            try {
                BaseGameActivity.this.onGameCreated();
            } catch (Throwable pThrowable) {
                Debug.m4591e(BaseGameActivity.this.getClass().getSimpleName() + ".onGameCreated failed." + " @(Thread: '" + Thread.currentThread().getName() + "')", pThrowable);
            }
            BaseGameActivity.this.callGameResumedOnUIThread();
        }
    }

    class C20864 implements Runnable {
        C20864() {
        }

        public void run() {
            BaseGameActivity.this.onResumeGame();
        }
    }

    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.mGamePaused = true;
        this.mEngine = onCreateEngine(onCreateEngineOptions());
        this.mEngine.startUpdateThread();
        applyEngineOptions();
        onSetContentView();
    }

    public Engine onCreateEngine(EngineOptions pEngineOptions) {
        return new Engine(pEngineOptions);
    }

    public synchronized void onSurfaceCreated(GLState pGLState) {
        if (this.mGameCreated) {
            onReloadResources();
            if (this.mGamePaused && this.mGameCreated) {
                onResumeGame();
            }
        } else if (this.mCreateGameCalled) {
            this.mOnReloadResourcesScheduled = true;
        } else {
            this.mCreateGameCalled = true;
            onCreateGame();
        }
    }

    public synchronized void onSurfaceChanged(GLState pGLState, int pWidth, int pHeight) {
    }

    protected synchronized void onCreateGame() {
        final OnPopulateSceneCallback onPopulateSceneCallback = new C20831();
        final OnCreateSceneCallback onCreateSceneCallback = new OnCreateSceneCallback() {
            public void onCreateSceneFinished(Scene pScene) {
                BaseGameActivity.this.mEngine.setScene(pScene);
                try {
                    BaseGameActivity.this.onPopulateScene(pScene, onPopulateSceneCallback);
                } catch (Throwable pThrowable) {
                    Debug.m4591e(BaseGameActivity.this.getClass().getSimpleName() + ".onPopulateScene failed." + " @(Thread: '" + Thread.currentThread().getName() + "')", pThrowable);
                }
            }
        };
        try {
            onCreateResources(new OnCreateResourcesCallback() {
                public void onCreateResourcesFinished() {
                    try {
                        BaseGameActivity.this.onCreateScene(onCreateSceneCallback);
                    } catch (Throwable pThrowable) {
                        Debug.m4591e(BaseGameActivity.this.getClass().getSimpleName() + ".onCreateScene failed." + " @(Thread: '" + Thread.currentThread().getName() + "')", pThrowable);
                    }
                }
            });
        } catch (Throwable pThrowable) {
            Debug.m4591e(getClass().getSimpleName() + ".onCreateGame failed." + " @(Thread: '" + Thread.currentThread().getName() + "')", pThrowable);
        }
    }

    public synchronized void onGameCreated() {
        this.mGameCreated = true;
        if (this.mOnReloadResourcesScheduled) {
            this.mOnReloadResourcesScheduled = false;
            try {
                onReloadResources();
            } catch (Throwable pThrowable) {
                Debug.m4591e(getClass().getSimpleName() + ".onReloadResources failed." + " @(Thread: '" + Thread.currentThread().getName() + "')", pThrowable);
            }
        }
    }

    protected synchronized void onResume() {
        super.onResume();
        acquireWakeLock();
        this.mRenderSurfaceView.onResume();
    }

    public synchronized void onResumeGame() {
        this.mEngine.start();
        this.mGamePaused = false;
    }

    public synchronized void onWindowFocusChanged(boolean pHasWindowFocus) {
        super.onWindowFocusChanged(pHasWindowFocus);
        if (pHasWindowFocus && this.mGamePaused && this.mGameCreated) {
            onResumeGame();
        }
    }

    public void onReloadResources() {
        this.mEngine.onReloadResources();
    }

    protected void onPause() {
        super.onPause();
        this.mRenderSurfaceView.onPause();
        releaseWakeLock();
        if (!this.mGamePaused) {
            onPauseGame();
        }
    }

    public synchronized void onPauseGame() {
        this.mGamePaused = true;
        this.mEngine.stop();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mEngine.onDestroy();
        try {
            onDestroyResources();
        } catch (Throwable pThrowable) {
            Debug.m4591e(getClass().getSimpleName() + ".onDestroyResources failed." + " @(Thread: '" + Thread.currentThread().getName() + "')", pThrowable);
        }
        onGameDestroyed();
        this.mEngine = null;
    }

    public void onDestroyResources() throws Exception {
        if (this.mEngine.getEngineOptions().getAudioOptions().needsMusic()) {
            getMusicManager().releaseAll();
        }
        if (this.mEngine.getEngineOptions().getAudioOptions().needsSound()) {
            getSoundManager().releaseAll();
        }
    }

    public synchronized void onGameDestroyed() {
        this.mGameCreated = false;
    }

    public Engine getEngine() {
        return this.mEngine;
    }

    public boolean isGamePaused() {
        return this.mGamePaused;
    }

    public boolean isGameRunning() {
        return !this.mGamePaused;
    }

    public boolean isGameLoaded() {
        return this.mGameCreated;
    }

    public VertexBufferObjectManager getVertexBufferObjectManager() {
        return this.mEngine.getVertexBufferObjectManager();
    }

    public TextureManager getTextureManager() {
        return this.mEngine.getTextureManager();
    }

    public FontManager getFontManager() {
        return this.mEngine.getFontManager();
    }

    public ShaderProgramManager getShaderProgramManager() {
        return this.mEngine.getShaderProgramManager();
    }

    public SoundManager getSoundManager() {
        return this.mEngine.getSoundManager();
    }

    public MusicManager getMusicManager() {
        return this.mEngine.getMusicManager();
    }

    private void callGameResumedOnUIThread() {
        runOnUiThread(new C20864());
    }

    protected void onSetContentView() {
        this.mRenderSurfaceView = new RenderSurfaceView(this);
        this.mRenderSurfaceView.setRenderer(this.mEngine, this);
        setContentView(this.mRenderSurfaceView, createSurfaceViewLayoutParams());
    }

    public void runOnUpdateThread(Runnable pRunnable) {
        this.mEngine.runOnUpdateThread(pRunnable);
    }

    public void runOnUpdateThread(Runnable pRunnable, boolean pOnlyWhenEngineRunning) {
        this.mEngine.runOnUpdateThread(pRunnable, pOnlyWhenEngineRunning);
    }

    private void acquireWakeLock() {
        acquireWakeLock(this.mEngine.getEngineOptions().getWakeLockOptions());
    }

    private void acquireWakeLock(WakeLockOptions pWakeLockOptions) {
        if (pWakeLockOptions == WakeLockOptions.SCREEN_ON) {
            ActivityUtils.keepScreenOn(this);
            return;
        }
        this.mWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(pWakeLockOptions.getFlag() | 536870912, Constants.DEBUGTAG);
        try {
            this.mWakeLock.acquire();
        } catch (Throwable pSecurityException) {
            Debug.m4591e("You have to add\n\t<uses-permission android:name=\"android.permission.WAKE_LOCK\"/>\nto your AndroidManifest.xml !", pSecurityException);
        }
    }

    private void releaseWakeLock() {
        if (this.mWakeLock != null && this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
    }

    private void applyEngineOptions() {
        EngineOptions engineOptions = this.mEngine.getEngineOptions();
        if (engineOptions.isFullscreen()) {
            ActivityUtils.requestFullscreen(this);
        }
        if (engineOptions.getAudioOptions().needsMusic() || engineOptions.getAudioOptions().needsSound()) {
            setVolumeControlStream(3);
        }
        switch (engineOptions.getScreenOrientation()) {
            case LANDSCAPE_FIXED:
                setRequestedOrientation(0);
                return;
            case LANDSCAPE_SENSOR:
                if (SystemUtils.SDK_VERSION_GINGERBREAD_OR_LATER) {
                    setRequestedOrientation(6);
                    return;
                }
                Debug.m4601w(ScreenOrientation.class.getSimpleName() + "." + ScreenOrientation.LANDSCAPE_SENSOR + " is not supported on this device. Falling back to " + ScreenOrientation.class.getSimpleName() + "." + ScreenOrientation.LANDSCAPE_FIXED);
                setRequestedOrientation(0);
                return;
            case PORTRAIT_FIXED:
                setRequestedOrientation(1);
                return;
            case PORTRAIT_SENSOR:
                if (SystemUtils.SDK_VERSION_GINGERBREAD_OR_LATER) {
                    setRequestedOrientation(7);
                    return;
                }
                Debug.m4601w(ScreenOrientation.class.getSimpleName() + "." + ScreenOrientation.PORTRAIT_SENSOR + " is not supported on this device. Falling back to " + ScreenOrientation.class.getSimpleName() + "." + ScreenOrientation.PORTRAIT_FIXED);
                setRequestedOrientation(1);
                return;
            default:
                return;
        }
    }

    protected static LayoutParams createSurfaceViewLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        return layoutParams;
    }

    protected void enableVibrator() {
        this.mEngine.enableVibrator(this);
    }

    protected void enableLocationSensor(ILocationListener pLocationListener, LocationSensorOptions pLocationSensorOptions) {
        this.mEngine.enableLocationSensor(this, pLocationListener, pLocationSensorOptions);
    }

    protected void disableLocationSensor() {
        this.mEngine.disableLocationSensor(this);
    }

    protected boolean enableAccelerationSensor(IAccelerationListener pAccelerationListener) {
        return this.mEngine.enableAccelerationSensor(this, pAccelerationListener);
    }

    protected boolean enableAccelerationSensor(IAccelerationListener pAccelerationListener, AccelerationSensorOptions pAccelerationSensorOptions) {
        return this.mEngine.enableAccelerationSensor(this, pAccelerationListener, pAccelerationSensorOptions);
    }

    protected boolean disableAccelerationSensor() {
        return this.mEngine.disableAccelerationSensor(this);
    }

    protected boolean enableOrientationSensor(IOrientationListener pOrientationListener) {
        return this.mEngine.enableOrientationSensor(this, pOrientationListener);
    }

    protected boolean enableOrientationSensor(IOrientationListener pOrientationListener, OrientationSensorOptions pLocationSensorOptions) {
        return this.mEngine.enableOrientationSensor(this, pOrientationListener, pLocationSensorOptions);
    }

    protected boolean disableOrientationSensor() {
        return this.mEngine.disableOrientationSensor(this);
    }
}
