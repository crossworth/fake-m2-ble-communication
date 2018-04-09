package org.andengine.entity.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.opengl.GLES20;
import java.nio.IntBuffer;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.opengl.util.GLState;

public class ScreenGrabber extends Entity {
    private int mGrabHeight;
    private int mGrabWidth;
    private int mGrabX;
    private int mGrabY;
    private IScreenGrabberCallback mScreenGrabCallback;
    private boolean mScreenGrabPending = false;

    public interface IScreenGrabberCallback {
        void onScreenGrabFailed(Exception exception);

        void onScreenGrabbed(Bitmap bitmap);
    }

    protected void onManagedDraw(GLState pGLState, Camera pCamera) {
        if (this.mScreenGrabPending) {
            try {
                this.mScreenGrabCallback.onScreenGrabbed(grab(this.mGrabX, this.mGrabY, this.mGrabWidth, this.mGrabHeight));
            } catch (Exception e) {
                this.mScreenGrabCallback.onScreenGrabFailed(e);
            }
            this.mScreenGrabPending = false;
        }
    }

    protected void onManagedUpdate(float pSecondsElapsed) {
    }

    public void reset() {
    }

    public void grab(int pGrabWidth, int pGrabHeight, IScreenGrabberCallback pScreenGrabCallback) {
        grab(0, 0, pGrabWidth, pGrabHeight, pScreenGrabCallback);
    }

    public void grab(int pGrabX, int pGrabY, int pGrabWidth, int pGrabHeight, IScreenGrabberCallback pScreenGrabCallback) {
        this.mGrabX = pGrabX;
        this.mGrabY = pGrabY;
        this.mGrabWidth = pGrabWidth;
        this.mGrabHeight = pGrabHeight;
        this.mScreenGrabCallback = pScreenGrabCallback;
        this.mScreenGrabPending = true;
    }

    private static Bitmap grab(int pGrabX, int pGrabY, int pGrabWidth, int pGrabHeight) {
        int[] source = new int[((pGrabY + pGrabHeight) * pGrabWidth)];
        IntBuffer sourceBuffer = IntBuffer.wrap(source);
        sourceBuffer.position(0);
        GLES20.glReadPixels(pGrabX, 0, pGrabWidth, pGrabY + pGrabHeight, 6408, 5121, sourceBuffer);
        int[] pixels = new int[(pGrabWidth * pGrabHeight)];
        for (int y = 0; y < pGrabHeight; y++) {
            for (int x = 0; x < pGrabWidth; x++) {
                int pixel = source[((pGrabY + y) * pGrabWidth) + x];
                pixels[(((pGrabHeight - y) - 1) * pGrabWidth) + x] = ((pixel & -16711936) | ((pixel & 255) << 16)) | ((16711680 & pixel) >> 16);
            }
        }
        return Bitmap.createBitmap(pixels, pGrabWidth, pGrabHeight, Config.ARGB_8888);
    }
}
