package org.andengine.engine;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Process;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import com.umeng.socialize.common.SocializeConstants;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.DrawHandlerList;
import org.andengine.engine.handler.IDrawHandler;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.UpdateHandlerList;
import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.input.sensor.SensorDelay;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.AccelerationSensorOptions;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.sensor.location.ILocationListener;
import org.andengine.input.sensor.location.LocationProviderStatus;
import org.andengine.input.sensor.location.LocationSensorOptions;
import org.andengine.input.sensor.orientation.IOrientationListener;
import org.andengine.input.sensor.orientation.OrientationData;
import org.andengine.input.sensor.orientation.OrientationSensorOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.controller.ITouchController;
import org.andengine.input.touch.controller.ITouchEventCallback;
import org.andengine.input.touch.controller.MultiTouchController;
import org.andengine.input.touch.controller.SingleTouchController;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.shader.ShaderProgramManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;
import org.andengine.util.time.TimeConstants;

public class Engine implements SensorEventListener, OnTouchListener, ITouchEventCallback, LocationListener {
    private static final int DRAWHANDLERS_CAPACITY_DEFAULT = 4;
    private static final SensorDelay SENSORDELAY_DEFAULT = SensorDelay.GAME;
    private static final int UPDATEHANDLERS_CAPACITY_DEFAULT = 8;
    private AccelerationData mAccelerationData;
    private IAccelerationListener mAccelerationListener;
    protected final Camera mCamera;
    private boolean mDestroyed;
    private final DrawHandlerList mDrawHandlers = new DrawHandlerList(4);
    private final EngineLock mEngineLock;
    private final EngineOptions mEngineOptions;
    private final FontManager mFontManager = new FontManager();
    private long mLastTick;
    private Location mLocation;
    private ILocationListener mLocationListener;
    private final MusicManager mMusicManager;
    private OrientationData mOrientationData;
    private IOrientationListener mOrientationListener;
    private boolean mRunning;
    protected Scene mScene;
    private float mSecondsElapsedTotal;
    private final ShaderProgramManager mShaderProgramManager = new ShaderProgramManager();
    private final SoundManager mSoundManager;
    protected int mSurfaceHeight = 1;
    protected int mSurfaceWidth = 1;
    private final TextureManager mTextureManager = new TextureManager();
    private ITouchController mTouchController;
    private final UpdateHandlerList mUpdateHandlers = new UpdateHandlerList(8);
    private final UpdateThread mUpdateThread;
    private final RunnableHandler mUpdateThreadRunnableHandler = new RunnableHandler();
    private final VertexBufferObjectManager mVertexBufferObjectManager = new VertexBufferObjectManager();
    private Vibrator mVibrator;

    public class EngineDestroyedException extends InterruptedException {
        private static final long serialVersionUID = -4691263961728972560L;
    }

    public static class EngineLock extends ReentrantLock {
        private static final long serialVersionUID = 671220941302523934L;
        final AtomicBoolean mDrawing = new AtomicBoolean(false);
        final Condition mDrawingCondition = newCondition();

        public EngineLock(boolean pFair) {
            super(pFair);
        }

        void notifyCanDraw() {
            this.mDrawing.set(true);
            this.mDrawingCondition.signalAll();
        }

        void notifyCanUpdate() {
            this.mDrawing.set(false);
            this.mDrawingCondition.signalAll();
        }

        void waitUntilCanDraw() throws InterruptedException {
            while (!this.mDrawing.get()) {
                this.mDrawingCondition.await();
            }
        }

        void waitUntilCanUpdate() throws InterruptedException {
            while (this.mDrawing.get()) {
                this.mDrawingCondition.await();
            }
        }
    }

    public static class UpdateThread extends Thread {
        private Engine mEngine;
        private final RunnableHandler mRunnableHandler = new RunnableHandler();

        public UpdateThread() {
            super(UpdateThread.class.getSimpleName());
        }

        public void setEngine(Engine pEngine) {
            this.mEngine = pEngine;
        }

        public void run() {
            Process.setThreadPriority(this.mEngine.getEngineOptions().getUpdateThreadPriority());
            while (true) {
                try {
                    this.mRunnableHandler.onUpdate(0.0f);
                    this.mEngine.onTickUpdate();
                } catch (InterruptedException e) {
                    interrupt();
                    return;
                }
            }
        }

        public void postRunnable(Runnable pRunnable) {
            this.mRunnableHandler.postRunnable(pRunnable);
        }
    }

    public Engine(EngineOptions pEngineOptions) {
        BitmapTextureAtlasTextureRegionFactory.reset();
        SoundFactory.onCreate();
        MusicFactory.onCreate();
        FontFactory.onCreate();
        this.mVertexBufferObjectManager.onCreate();
        this.mTextureManager.onCreate();
        this.mFontManager.onCreate();
        this.mShaderProgramManager.onCreate();
        this.mEngineOptions = pEngineOptions;
        if (this.mEngineOptions.hasEngineLock()) {
            this.mEngineLock = pEngineOptions.getEngineLock();
        } else {
            this.mEngineLock = new EngineLock(false);
        }
        this.mCamera = pEngineOptions.getCamera();
        if (this.mEngineOptions.getTouchOptions().needsMultiTouch()) {
            setTouchController(new MultiTouchController());
        } else {
            setTouchController(new SingleTouchController());
        }
        if (this.mEngineOptions.getAudioOptions().needsSound()) {
            this.mSoundManager = new SoundManager(this.mEngineOptions.getAudioOptions().getSoundOptions().getMaxSimultaneousStreams());
        } else {
            this.mSoundManager = null;
        }
        if (this.mEngineOptions.getAudioOptions().needsMusic()) {
            this.mMusicManager = new MusicManager();
        } else {
            this.mMusicManager = null;
        }
        if (this.mEngineOptions.hasUpdateThread()) {
            this.mUpdateThread = this.mEngineOptions.getUpdateThread();
        } else {
            this.mUpdateThread = new UpdateThread();
        }
        this.mUpdateThread.setEngine(this);
    }

    public void startUpdateThread() throws IllegalThreadStateException {
        this.mUpdateThread.start();
    }

    public synchronized boolean isRunning() {
        return this.mRunning;
    }

    public synchronized void start() {
        if (!this.mRunning) {
            this.mLastTick = System.nanoTime();
            this.mRunning = true;
        }
    }

    public synchronized void stop() {
        if (this.mRunning) {
            this.mRunning = false;
        }
    }

    public EngineLock getEngineLock() {
        return this.mEngineLock;
    }

    public Scene getScene() {
        return this.mScene;
    }

    public void setScene(Scene pScene) {
        this.mScene = pScene;
    }

    public EngineOptions getEngineOptions() {
        return this.mEngineOptions;
    }

    public Camera getCamera() {
        return this.mCamera;
    }

    public float getSecondsElapsedTotal() {
        return this.mSecondsElapsedTotal;
    }

    public void setSurfaceSize(int pSurfaceWidth, int pSurfaceHeight) {
        this.mSurfaceWidth = pSurfaceWidth;
        this.mSurfaceHeight = pSurfaceHeight;
        onUpdateCameraSurface();
    }

    protected void onUpdateCameraSurface() {
        this.mCamera.setSurfaceSize(0, 0, this.mSurfaceWidth, this.mSurfaceHeight);
    }

    public int getSurfaceWidth() {
        return this.mSurfaceWidth;
    }

    public int getSurfaceHeight() {
        return this.mSurfaceHeight;
    }

    public ITouchController getTouchController() {
        return this.mTouchController;
    }

    public void setTouchController(ITouchController pTouchController) {
        this.mTouchController = pTouchController;
        this.mTouchController.setTouchEventCallback(this);
    }

    public AccelerationData getAccelerationData() {
        return this.mAccelerationData;
    }

    public OrientationData getOrientationData() {
        return this.mOrientationData;
    }

    public VertexBufferObjectManager getVertexBufferObjectManager() {
        return this.mVertexBufferObjectManager;
    }

    public TextureManager getTextureManager() {
        return this.mTextureManager;
    }

    public FontManager getFontManager() {
        return this.mFontManager;
    }

    public ShaderProgramManager getShaderProgramManager() {
        return this.mShaderProgramManager;
    }

    public SoundManager getSoundManager() throws IllegalStateException {
        if (this.mSoundManager != null) {
            return this.mSoundManager;
        }
        throw new IllegalStateException("To enable the SoundManager, check the EngineOptions!");
    }

    public MusicManager getMusicManager() throws IllegalStateException {
        if (this.mMusicManager != null) {
            return this.mMusicManager;
        }
        throw new IllegalStateException("To enable the MusicManager, check the EngineOptions!");
    }

    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
        this.mUpdateHandlers.add(pUpdateHandler);
    }

    public void unregisterUpdateHandler(IUpdateHandler pUpdateHandler) {
        this.mUpdateHandlers.remove(pUpdateHandler);
    }

    public void clearUpdateHandlers() {
        this.mUpdateHandlers.clear();
    }

    public void registerDrawHandler(IDrawHandler pDrawHandler) {
        this.mDrawHandlers.add(pDrawHandler);
    }

    public void unregisterDrawHandler(IDrawHandler pDrawHandler) {
        this.mDrawHandlers.remove(pDrawHandler);
    }

    public void clearDrawHandlers() {
        this.mDrawHandlers.clear();
    }

    public void onAccuracyChanged(Sensor pSensor, int pAccuracy) {
        if (this.mRunning) {
            switch (pSensor.getType()) {
                case 1:
                    if (this.mAccelerationData != null) {
                        this.mAccelerationData.setAccuracy(pAccuracy);
                        this.mAccelerationListener.onAccelerationAccuracyChanged(this.mAccelerationData);
                        return;
                    } else if (this.mOrientationData != null) {
                        this.mOrientationData.setAccelerationAccuracy(pAccuracy);
                        this.mOrientationListener.onOrientationAccuracyChanged(this.mOrientationData);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    this.mOrientationData.setMagneticFieldAccuracy(pAccuracy);
                    this.mOrientationListener.onOrientationAccuracyChanged(this.mOrientationData);
                    return;
                default:
                    return;
            }
        }
    }

    public void onSensorChanged(SensorEvent pEvent) {
        if (this.mRunning) {
            switch (pEvent.sensor.getType()) {
                case 1:
                    if (this.mAccelerationData != null) {
                        this.mAccelerationData.setValues(pEvent.values);
                        this.mAccelerationListener.onAccelerationChanged(this.mAccelerationData);
                        return;
                    } else if (this.mOrientationData != null) {
                        this.mOrientationData.setAccelerationValues(pEvent.values);
                        this.mOrientationListener.onOrientationChanged(this.mOrientationData);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    this.mOrientationData.setMagneticFieldValues(pEvent.values);
                    this.mOrientationListener.onOrientationChanged(this.mOrientationData);
                    return;
                default:
                    return;
            }
        }
    }

    public void onLocationChanged(Location pLocation) {
        if (this.mLocation == null) {
            this.mLocation = pLocation;
        } else if (pLocation == null) {
            this.mLocationListener.onLocationLost();
        } else {
            this.mLocation = pLocation;
            this.mLocationListener.onLocationChanged(pLocation);
        }
    }

    public void onProviderDisabled(String pProvider) {
        this.mLocationListener.onLocationProviderDisabled();
    }

    public void onProviderEnabled(String pProvider) {
        this.mLocationListener.onLocationProviderEnabled();
    }

    public void onStatusChanged(String pProvider, int pStatus, Bundle pExtras) {
        switch (pStatus) {
            case 0:
                this.mLocationListener.onLocationProviderStatusChanged(LocationProviderStatus.OUT_OF_SERVICE, pExtras);
                return;
            case 1:
                this.mLocationListener.onLocationProviderStatusChanged(LocationProviderStatus.TEMPORARILY_UNAVAILABLE, pExtras);
                return;
            case 2:
                this.mLocationListener.onLocationProviderStatusChanged(LocationProviderStatus.AVAILABLE, pExtras);
                return;
            default:
                return;
        }
    }

    public boolean onTouch(View pView, MotionEvent pSurfaceMotionEvent) {
        if (!this.mRunning) {
            return false;
        }
        this.mTouchController.onHandleMotionEvent(pSurfaceMotionEvent);
        try {
            Thread.sleep(this.mEngineOptions.getTouchOptions().getTouchEventIntervalMilliseconds());
        } catch (Throwable e) {
            Debug.m4592e(e);
        }
        return true;
    }

    public boolean onTouchEvent(TouchEvent pSurfaceTouchEvent) {
        Scene scene = getSceneFromSurfaceTouchEvent(pSurfaceTouchEvent);
        Camera camera = getCameraFromSurfaceTouchEvent(pSurfaceTouchEvent);
        convertSurfaceToSceneTouchEvent(camera, pSurfaceTouchEvent);
        if (onTouchHUD(camera, pSurfaceTouchEvent)) {
            return true;
        }
        return onTouchScene(scene, pSurfaceTouchEvent);
    }

    protected boolean onTouchHUD(Camera pCamera, TouchEvent pSceneTouchEvent) {
        if (pCamera.hasHUD()) {
            return pCamera.getHUD().onSceneTouchEvent(pSceneTouchEvent);
        }
        return false;
    }

    protected boolean onTouchScene(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pScene != null) {
            return pScene.onSceneTouchEvent(pSceneTouchEvent);
        }
        return false;
    }

    public void runOnUpdateThread(Runnable pRunnable) {
        runOnUpdateThread(pRunnable, true);
    }

    public void runOnUpdateThread(Runnable pRunnable, boolean pOnlyWhenEngineRunning) {
        if (pOnlyWhenEngineRunning) {
            this.mUpdateThreadRunnableHandler.postRunnable(pRunnable);
        } else {
            this.mUpdateThread.postRunnable(pRunnable);
        }
    }

    public void runSafely(Runnable pRunnable) {
        this.mEngineLock.lock();
        try {
            pRunnable.run();
        } finally {
            this.mEngineLock.unlock();
        }
    }

    public void onDestroy() {
        this.mEngineLock.lock();
        try {
            this.mDestroyed = true;
            this.mEngineLock.notifyCanUpdate();
            try {
                this.mUpdateThread.join();
            } catch (Throwable e) {
                Debug.m4591e("Could not join UpdateThread.", e);
                Debug.m4601w("Trying to manually interrupt UpdateThread.");
                this.mUpdateThread.interrupt();
            }
            this.mVertexBufferObjectManager.onDestroy();
            this.mTextureManager.onDestroy();
            this.mFontManager.onDestroy();
            this.mShaderProgramManager.onDestroy();
        } finally {
            this.mEngineLock.unlock();
        }
    }

    public void onReloadResources() {
        this.mVertexBufferObjectManager.onReload();
        this.mTextureManager.onReload();
        this.mFontManager.onReload();
        this.mShaderProgramManager.onReload();
    }

    protected Camera getCameraFromSurfaceTouchEvent(TouchEvent pTouchEvent) {
        return getCamera();
    }

    protected Scene getSceneFromSurfaceTouchEvent(TouchEvent pTouchEvent) {
        return this.mScene;
    }

    protected void convertSurfaceToSceneTouchEvent(Camera pCamera, TouchEvent pSurfaceTouchEvent) {
        pCamera.convertSurfaceToSceneTouchEvent(pSurfaceTouchEvent, this.mSurfaceWidth, this.mSurfaceHeight);
    }

    protected void convertSceneToSurfaceTouchEvent(Camera pCamera, TouchEvent pSurfaceTouchEvent) {
        pCamera.convertSceneToSurfaceTouchEvent(pSurfaceTouchEvent, this.mSurfaceWidth, this.mSurfaceHeight);
    }

    void onTickUpdate() throws InterruptedException {
        if (this.mRunning) {
            long secondsElapsed = getNanosecondsElapsed();
            this.mEngineLock.lock();
            try {
                throwOnDestroyed();
                onUpdate(secondsElapsed);
                throwOnDestroyed();
                this.mEngineLock.notifyCanDraw();
                this.mEngineLock.waitUntilCanUpdate();
            } finally {
                this.mEngineLock.unlock();
            }
        } else {
            this.mEngineLock.lock();
            try {
                throwOnDestroyed();
                this.mEngineLock.notifyCanDraw();
                this.mEngineLock.waitUntilCanUpdate();
                Thread.sleep(16);
            } finally {
                this.mEngineLock.unlock();
            }
        }
    }

    private void throwOnDestroyed() throws EngineDestroyedException {
        if (this.mDestroyed) {
            throw new EngineDestroyedException();
        }
    }

    public void onUpdate(long pNanosecondsElapsed) throws InterruptedException {
        float pSecondsElapsed = ((float) pNanosecondsElapsed) * TimeConstants.SECONDS_PER_NANOSECOND;
        this.mSecondsElapsedTotal += pSecondsElapsed;
        this.mLastTick += pNanosecondsElapsed;
        this.mTouchController.onUpdate(pSecondsElapsed);
        onUpdateUpdateHandlers(pSecondsElapsed);
        onUpdateScene(pSecondsElapsed);
    }

    protected void onUpdateScene(float pSecondsElapsed) {
        if (this.mScene != null) {
            this.mScene.onUpdate(pSecondsElapsed);
        }
    }

    protected void onUpdateUpdateHandlers(float pSecondsElapsed) {
        this.mUpdateThreadRunnableHandler.onUpdate(pSecondsElapsed);
        this.mUpdateHandlers.onUpdate(pSecondsElapsed);
        getCamera().onUpdate(pSecondsElapsed);
    }

    protected void onUpdateDrawHandlers(GLState pGLState, Camera pCamera) {
        this.mDrawHandlers.onDraw(pGLState, pCamera);
    }

    public void onDrawFrame(GLState pGLState) throws InterruptedException {
        EngineLock engineLock = this.mEngineLock;
        engineLock.lock();
        try {
            engineLock.waitUntilCanDraw();
            this.mVertexBufferObjectManager.updateVertexBufferObjects(pGLState);
            this.mTextureManager.updateTextures(pGLState);
            this.mFontManager.updateFonts(pGLState);
            onUpdateDrawHandlers(pGLState, this.mCamera);
            onDrawScene(pGLState, this.mCamera);
            engineLock.notifyCanUpdate();
        } finally {
            engineLock.unlock();
        }
    }

    protected void onDrawScene(GLState pGLState, Camera pCamera) {
        if (this.mScene != null) {
            this.mScene.onDraw(pGLState, pCamera);
        }
        pCamera.onDrawHUD(pGLState);
    }

    private long getNanosecondsElapsed() {
        return System.nanoTime() - this.mLastTick;
    }

    public boolean enableVibrator(Context pContext) {
        this.mVibrator = (Vibrator) pContext.getSystemService("vibrator");
        return this.mVibrator != null;
    }

    public void vibrate(long pMilliseconds) throws IllegalStateException {
        if (this.mVibrator != null) {
            this.mVibrator.vibrate(pMilliseconds);
            return;
        }
        throw new IllegalStateException("You need to enable the Vibrator before you can use it!");
    }

    public void vibrate(long[] pPattern, int pRepeat) throws IllegalStateException {
        if (this.mVibrator != null) {
            this.mVibrator.vibrate(pPattern, pRepeat);
            return;
        }
        throw new IllegalStateException("You need to enable the Vibrator before you can use it!");
    }

    public void enableLocationSensor(Context pContext, ILocationListener pLocationListener, LocationSensorOptions pLocationSensorOptions) {
        this.mLocationListener = pLocationListener;
        LocationManager locationManager = (LocationManager) pContext.getSystemService(SocializeConstants.KEY_LOCATION);
        String locationProvider = locationManager.getBestProvider(pLocationSensorOptions, pLocationSensorOptions.isEnabledOnly());
        locationManager.requestLocationUpdates(locationProvider, pLocationSensorOptions.getMinimumTriggerTime(), (float) pLocationSensorOptions.getMinimumTriggerDistance(), this);
        onLocationChanged(locationManager.getLastKnownLocation(locationProvider));
    }

    public void disableLocationSensor(Context pContext) {
        ((LocationManager) pContext.getSystemService(SocializeConstants.KEY_LOCATION)).removeUpdates(this);
    }

    public boolean enableAccelerationSensor(Context pContext, IAccelerationListener pAccelerationListener) {
        return enableAccelerationSensor(pContext, pAccelerationListener, new AccelerationSensorOptions(SENSORDELAY_DEFAULT));
    }

    public boolean enableAccelerationSensor(Context pContext, IAccelerationListener pAccelerationListener, AccelerationSensorOptions pAccelerationSensorOptions) {
        SensorManager sensorManager = (SensorManager) pContext.getSystemService("sensor");
        if (!isSensorSupported(sensorManager, 1)) {
            return false;
        }
        this.mAccelerationListener = pAccelerationListener;
        if (this.mAccelerationData == null) {
            this.mAccelerationData = new AccelerationData(((WindowManager) pContext.getSystemService("window")).getDefaultDisplay().getOrientation());
        }
        registerSelfAsSensorListener(sensorManager, 1, pAccelerationSensorOptions.getSensorDelay());
        return true;
    }

    public boolean disableAccelerationSensor(Context pContext) {
        SensorManager sensorManager = (SensorManager) pContext.getSystemService("sensor");
        if (!isSensorSupported(sensorManager, 1)) {
            return false;
        }
        unregisterSelfAsSensorListener(sensorManager, 1);
        return true;
    }

    public boolean enableOrientationSensor(Context pContext, IOrientationListener pOrientationListener) {
        return enableOrientationSensor(pContext, pOrientationListener, new OrientationSensorOptions(SENSORDELAY_DEFAULT));
    }

    public boolean enableOrientationSensor(Context pContext, IOrientationListener pOrientationListener, OrientationSensorOptions pOrientationSensorOptions) {
        SensorManager sensorManager = (SensorManager) pContext.getSystemService("sensor");
        if (!isSensorSupported(sensorManager, 1) || !isSensorSupported(sensorManager, 2)) {
            return false;
        }
        this.mOrientationListener = pOrientationListener;
        if (this.mOrientationData == null) {
            this.mOrientationData = new OrientationData(((WindowManager) pContext.getSystemService("window")).getDefaultDisplay().getOrientation());
        }
        registerSelfAsSensorListener(sensorManager, 1, pOrientationSensorOptions.getSensorDelay());
        registerSelfAsSensorListener(sensorManager, 2, pOrientationSensorOptions.getSensorDelay());
        return true;
    }

    public boolean disableOrientationSensor(Context pContext) {
        SensorManager sensorManager = (SensorManager) pContext.getSystemService("sensor");
        if (!isSensorSupported(sensorManager, 1) || !isSensorSupported(sensorManager, 2)) {
            return false;
        }
        unregisterSelfAsSensorListener(sensorManager, 1);
        unregisterSelfAsSensorListener(sensorManager, 2);
        return true;
    }

    private static boolean isSensorSupported(SensorManager pSensorManager, int pType) {
        return pSensorManager.getSensorList(pType).size() > 0;
    }

    private void registerSelfAsSensorListener(SensorManager pSensorManager, int pType, SensorDelay pSensorDelay) {
        pSensorManager.registerListener(this, (Sensor) pSensorManager.getSensorList(pType).get(0), pSensorDelay.getDelay());
    }

    private void unregisterSelfAsSensorListener(SensorManager pSensorManager, int pType) {
        pSensorManager.unregisterListener(this, (Sensor) pSensorManager.getSensorList(pType).get(0));
    }
}
