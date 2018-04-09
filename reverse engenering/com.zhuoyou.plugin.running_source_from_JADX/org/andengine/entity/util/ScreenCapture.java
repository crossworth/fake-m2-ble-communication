package org.andengine.entity.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.util.ScreenGrabber.IScreenGrabberCallback;
import org.andengine.opengl.util.GLState;
import org.andengine.util.StreamUtils;
import org.andengine.util.debug.Debug;

public class ScreenCapture extends Entity implements IScreenGrabberCallback {
    private String mFilePath;
    private IScreenCaptureCallback mScreenCaptureCallback;
    private final ScreenGrabber mScreenGrabber = new ScreenGrabber();

    public interface IScreenCaptureCallback {
        void onScreenCaptureFailed(String str, Exception exception);

        void onScreenCaptured(String str);
    }

    protected void onManagedDraw(GLState pGLState, Camera pCamera) {
        this.mScreenGrabber.onManagedDraw(pGLState, pCamera);
    }

    protected void onManagedUpdate(float pSecondsElapsed) {
    }

    public void reset() {
    }

    public void onScreenGrabbed(Bitmap pBitmap) {
        try {
            saveCapture(pBitmap, this.mFilePath);
            this.mScreenCaptureCallback.onScreenCaptured(this.mFilePath);
        } catch (FileNotFoundException e) {
            this.mScreenCaptureCallback.onScreenCaptureFailed(this.mFilePath, e);
        }
    }

    public void onScreenGrabFailed(Exception pException) {
        this.mScreenCaptureCallback.onScreenCaptureFailed(this.mFilePath, pException);
    }

    public void capture(int pCaptureWidth, int pCaptureHeight, String pFilePath, IScreenCaptureCallback pScreenCaptureCallback) {
        capture(0, 0, pCaptureWidth, pCaptureHeight, pFilePath, pScreenCaptureCallback);
    }

    public void capture(int pCaptureX, int pCaptureY, int pCaptureWidth, int pCaptureHeight, String pFilePath, IScreenCaptureCallback pScreencaptureCallback) {
        this.mFilePath = pFilePath;
        this.mScreenCaptureCallback = pScreencaptureCallback;
        this.mScreenGrabber.grab(pCaptureX, pCaptureY, pCaptureWidth, pCaptureHeight, this);
    }

    private static void saveCapture(Bitmap pBitmap, String pFilePath) throws FileNotFoundException {
        Throwable e;
        FileOutputStream out = null;
        try {
            FileOutputStream out2 = new FileOutputStream(pFilePath);
            try {
                pBitmap.compress(CompressFormat.PNG, 100, out2);
            } catch (FileNotFoundException e2) {
                e = e2;
                out = out2;
                StreamUtils.flushCloseStream(out);
                Debug.m4591e("Error saving file to: " + pFilePath, e);
                throw e;
            }
        } catch (FileNotFoundException e3) {
            e = e3;
            StreamUtils.flushCloseStream(out);
            Debug.m4591e("Error saving file to: " + pFilePath, e);
            throw e;
        }
    }
}
