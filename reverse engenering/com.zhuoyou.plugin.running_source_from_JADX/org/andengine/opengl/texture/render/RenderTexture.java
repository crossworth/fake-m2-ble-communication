package org.andengine.opengl.texture.render;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.opengl.GLES20;
import java.io.IOException;
import java.nio.IntBuffer;
import org.andengine.opengl.exception.GLException;
import org.andengine.opengl.exception.GLFrameBufferException;
import org.andengine.opengl.exception.RenderTextureInitializationException;
import org.andengine.opengl.texture.ITextureStateListener;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.util.GLHelper;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;

public class RenderTexture extends Texture {
    private static final float[] CLEARCOLOR_CONTAINER = new float[4];
    private static final int CLEARCOLOR_CONTAINER_ALPHA_INDEX = 3;
    private static final int CLEARCOLOR_CONTAINER_BLUE_INDEX = 2;
    private static final int CLEARCOLOR_CONTAINER_GREEN_INDEX = 1;
    private static final int CLEARCOLOR_CONTAINER_RED_INDEX = 0;
    private static final int[] VIEWPORT_CONTAINER = new int[4];
    private static final int VIEWPORT_CONTAINER_HEIGHT_INDEX = 3;
    private static final int VIEWPORT_CONTAINER_WIDTH_INDEX = 2;
    private static final int VIEWPORT_CONTAINER_X_INDEX = 0;
    private static final int VIEWPORT_CONTAINER_Y_INDEX = 1;
    protected int mFramebufferObjectID;
    protected final int mHeight;
    private boolean mInitialized;
    protected final PixelFormat mPixelFormat;
    private int mPreviousFramebufferObjectID;
    private int mPreviousViewPortHeight;
    private int mPreviousViewPortWidth;
    private int mPreviousViewPortX;
    private int mPreviousViewPortY;
    protected final int mWidth;

    public RenderTexture(TextureManager pTextureManager, int pWidth, int pHeight) {
        this(pTextureManager, pWidth, pHeight, PixelFormat.RGBA_8888, TextureOptions.NEAREST);
    }

    public RenderTexture(TextureManager pTextureManager, int pWidth, int pHeight, PixelFormat pPixelFormat) {
        this(pTextureManager, pWidth, pHeight, pPixelFormat, TextureOptions.NEAREST);
    }

    public RenderTexture(TextureManager pTextureManager, int pWidth, int pHeight, TextureOptions pTextureOptions) {
        this(pTextureManager, pWidth, pHeight, PixelFormat.RGBA_8888, pTextureOptions);
    }

    public RenderTexture(TextureManager pTextureManager, int pWidth, int pHeight, PixelFormat pPixelFormat, TextureOptions pTextureOptions) {
        this(pTextureManager, pWidth, pHeight, pPixelFormat, pTextureOptions, null);
    }

    public RenderTexture(TextureManager pTextureManager, int pWidth, int pHeight, PixelFormat pPixelFormat, TextureOptions pTextureOptions, ITextureStateListener pTextureStateListener) {
        super(pTextureManager, pPixelFormat, pTextureOptions, pTextureStateListener);
        this.mWidth = pWidth;
        this.mHeight = pHeight;
        this.mPixelFormat = pPixelFormat;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean isInitialized() {
        return this.mInitialized;
    }

    protected void writeTextureToHardware(GLState pGLState) {
        GLES20.glTexImage2D(3553, 0, this.mPixelFormat.getGLInternalFormat(), this.mWidth, this.mHeight, 0, this.mPixelFormat.getGLFormat(), this.mPixelFormat.getGLType(), null);
    }

    public void init(GLState pGLState) throws GLFrameBufferException, GLException {
        savePreviousFramebufferObjectID(pGLState);
        try {
            loadToHardware(pGLState);
        } catch (IOException e) {
        }
        pGLState.bindTexture(0);
        this.mFramebufferObjectID = pGLState.generateFramebuffer();
        pGLState.bindFramebuffer(this.mFramebufferObjectID);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.mHardwareTextureID, 0);
        try {
            pGLState.checkFramebufferStatus();
            restorePreviousFramebufferObjectID(pGLState);
            this.mInitialized = true;
        } catch (Throwable e2) {
            destroy(pGLState);
            throw new RenderTextureInitializationException(e2);
        } catch (Throwable th) {
            restorePreviousFramebufferObjectID(pGLState);
        }
    }

    public void begin(GLState pGLState) {
        begin(pGLState, false, false);
    }

    public void begin(GLState pGLState, Color pColor) {
        begin(pGLState, pColor.getRed(), pColor.getGreen(), pColor.getBlue(), pColor.getAlpha());
    }

    public void begin(GLState pGLState, float pRed, float pGreen, float pBlue, float pAlpha) {
        begin(pGLState, false, false, pRed, pGreen, pBlue, pAlpha);
    }

    public void begin(GLState pGLState, boolean pFlipX, boolean pFlipY, Color pColor) {
        begin(pGLState, pFlipX, pFlipY, pColor.getRed(), pColor.getGreen(), pColor.getBlue(), pColor.getAlpha());
    }

    public void begin(GLState pGLState, boolean pFlipX, boolean pFlipY, float pRed, float pGreen, float pBlue, float pAlpha) {
        begin(pGLState, pFlipX, pFlipY);
        GLES20.glGetFloatv(3106, CLEARCOLOR_CONTAINER, 0);
        GLES20.glClearColor(pRed, pGreen, pBlue, pAlpha);
        GLES20.glClear(16384);
        GLES20.glClearColor(CLEARCOLOR_CONTAINER[0], CLEARCOLOR_CONTAINER[1], CLEARCOLOR_CONTAINER[2], CLEARCOLOR_CONTAINER[3]);
    }

    public void begin(GLState pGLState, boolean pFlipX, boolean pFlipY) {
        float left;
        float right;
        float top;
        float bottom;
        savePreviousViewport();
        GLES20.glViewport(0, 0, this.mWidth, this.mHeight);
        pGLState.pushProjectionGLMatrix();
        if (pFlipX) {
            left = (float) this.mWidth;
            right = 0.0f;
        } else {
            left = 0.0f;
            right = (float) this.mWidth;
        }
        if (pFlipY) {
            top = (float) this.mHeight;
            bottom = 0.0f;
        } else {
            top = 0.0f;
            bottom = (float) this.mHeight;
        }
        pGLState.orthoProjectionGLMatrixf(left, right, bottom, top, -1.0f, 1.0f);
        savePreviousFramebufferObjectID(pGLState);
        pGLState.bindFramebuffer(this.mFramebufferObjectID);
        pGLState.pushModelViewGLMatrix();
        pGLState.loadModelViewGLMatrixIdentity();
    }

    public void flush(GLState pGLState) {
        pGLState.flush();
    }

    public void finish(GLState pGLState) {
        pGLState.finish();
    }

    public void end(GLState pGLState) {
        end(pGLState, false, false);
    }

    public void end(GLState pGLState, boolean pFlush, boolean pFinish) {
        if (pFinish) {
            finish(pGLState);
        } else if (pFlush) {
            flush(pGLState);
        }
        pGLState.popModelViewGLMatrix();
        restorePreviousFramebufferObjectID(pGLState);
        pGLState.popProjectionGLMatrix();
        resotorePreviousViewport();
    }

    public void destroy(GLState pGLState) {
        unloadFromHardware(pGLState);
        pGLState.deleteFramebuffer(this.mFramebufferObjectID);
        this.mInitialized = false;
    }

    protected void savePreviousFramebufferObjectID(GLState pGLState) {
        this.mPreviousFramebufferObjectID = pGLState.getActiveFramebuffer();
    }

    protected void restorePreviousFramebufferObjectID(GLState pGLState) {
        pGLState.bindFramebuffer(this.mPreviousFramebufferObjectID);
    }

    protected void savePreviousViewport() {
        GLES20.glGetIntegerv(2978, VIEWPORT_CONTAINER, 0);
        this.mPreviousViewPortX = VIEWPORT_CONTAINER[0];
        this.mPreviousViewPortY = VIEWPORT_CONTAINER[1];
        this.mPreviousViewPortWidth = VIEWPORT_CONTAINER[2];
        this.mPreviousViewPortHeight = VIEWPORT_CONTAINER[3];
    }

    protected void resotorePreviousViewport() {
        GLES20.glViewport(this.mPreviousViewPortX, this.mPreviousViewPortY, this.mPreviousViewPortWidth, this.mPreviousViewPortHeight);
    }

    public int[] getPixelsARGB_8888(GLState pGLState) {
        return getPixelsARGB_8888(pGLState, 0, 0, this.mWidth, this.mHeight);
    }

    public int[] getPixelsARGB_8888(GLState pGLState, int pX, int pY, int pWidth, int pHeight) {
        int[] pixelsRGBA_8888 = new int[(pWidth * pHeight)];
        IntBuffer glPixelBuffer = IntBuffer.wrap(pixelsRGBA_8888);
        glPixelBuffer.position(0);
        begin(pGLState);
        GLES20.glReadPixels(pX, pY, pWidth, pHeight, this.mPixelFormat.getGLFormat(), this.mPixelFormat.getGLType(), glPixelBuffer);
        end(pGLState);
        return GLHelper.convertRGBA_8888toARGB_8888(pixelsRGBA_8888);
    }

    public Bitmap getBitmap(GLState pGLState) {
        return getBitmap(pGLState, 0, 0, this.mWidth, this.mHeight);
    }

    public Bitmap getBitmap(GLState pGLState, int pX, int pY, int pWidth, int pHeight) {
        if (this.mPixelFormat == PixelFormat.RGBA_8888) {
            return Bitmap.createBitmap(getPixelsARGB_8888(pGLState, pX, pY, pWidth, pHeight), pWidth, pHeight, Config.ARGB_8888);
        }
        throw new IllegalStateException("Currently only 'PixelFormat." + PixelFormat.RGBA_8888 + "' is supported to be retrieved as a Bitmap.");
    }
}
