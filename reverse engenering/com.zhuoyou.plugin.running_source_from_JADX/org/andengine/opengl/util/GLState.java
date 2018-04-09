package org.andengine.opengl.util;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.Matrix;
import java.nio.ByteOrder;
import java.util.Arrays;
import javax.microedition.khronos.egl.EGLConfig;
import org.andengine.engine.options.RenderOptions;
import org.andengine.opengl.exception.GLException;
import org.andengine.opengl.exception.GLFrameBufferException;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.view.ConfigChooser;

public class GLState {
    public static final int GL_UNPACK_ALIGNMENT_DEFAULT = 4;
    private boolean mBlendEnabled = false;
    private boolean mCullingEnabled = false;
    private int mCurrentActiveTextureIndex = 0;
    private int mCurrentArrayBufferID = -1;
    private final int[] mCurrentBoundTextureIDs = new int[31];
    private int mCurrentDestinationBlendMode = -1;
    private int mCurrentFramebufferID = -1;
    private int mCurrentIndexBufferID = -1;
    private int mCurrentShaderProgramID = -1;
    private int mCurrentSourceBlendMode = -1;
    private boolean mDepthTestEnabled = true;
    private boolean mDitherEnabled = true;
    private String mExtensions;
    private final int[] mHardwareIDContainer = new int[1];
    private float mLineWidth = 1.0f;
    private int mMaximumFragmentShaderUniformVectorCount;
    private int mMaximumTextureSize;
    private int mMaximumTextureUnits;
    private int mMaximumVertexAttributeCount;
    private int mMaximumVertexShaderUniformVectorCount;
    private final float[] mModelViewGLMatrix = new float[16];
    private final GLMatrixStack mModelViewGLMatrixStack = new GLMatrixStack();
    private final float[] mModelViewProjectionGLMatrix = new float[16];
    private final float[] mProjectionGLMatrix = new float[16];
    private final GLMatrixStack mProjectionGLMatrixStack = new GLMatrixStack();
    private String mRenderer;
    private boolean mScissorTestEnabled = false;
    private String mVersion;

    public String getVersion() {
        return this.mVersion;
    }

    public String getRenderer() {
        return this.mRenderer;
    }

    public String getExtensions() {
        return this.mExtensions;
    }

    public int getMaximumVertexAttributeCount() {
        return this.mMaximumVertexAttributeCount;
    }

    public int getMaximumVertexShaderUniformVectorCount() {
        return this.mMaximumVertexShaderUniformVectorCount;
    }

    public int getMaximumFragmentShaderUniformVectorCount() {
        return this.mMaximumFragmentShaderUniformVectorCount;
    }

    public int getMaximumTextureUnits() {
        return this.mMaximumTextureUnits;
    }

    public int getMaximumTextureSize() {
        return this.mMaximumTextureSize;
    }

    public void reset(RenderOptions pRenderOptions, ConfigChooser pConfigChooser, EGLConfig pEGLConfig) {
        this.mVersion = GLES20.glGetString(7938);
        this.mRenderer = GLES20.glGetString(7937);
        this.mExtensions = GLES20.glGetString(7939);
        this.mMaximumVertexAttributeCount = getInteger(34921);
        this.mMaximumVertexShaderUniformVectorCount = getInteger(36347);
        this.mMaximumFragmentShaderUniformVectorCount = getInteger(36349);
        this.mMaximumTextureUnits = getInteger(34930);
        this.mMaximumTextureSize = getInteger(3379);
        this.mModelViewGLMatrixStack.reset();
        this.mProjectionGLMatrixStack.reset();
        this.mCurrentArrayBufferID = -1;
        this.mCurrentIndexBufferID = -1;
        this.mCurrentShaderProgramID = -1;
        Arrays.fill(this.mCurrentBoundTextureIDs, -1);
        this.mCurrentFramebufferID = -1;
        this.mCurrentActiveTextureIndex = 0;
        this.mCurrentSourceBlendMode = -1;
        this.mCurrentDestinationBlendMode = -1;
        enableDither();
        enableDepthTest();
        disableBlend();
        disableCulling();
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glEnableVertexAttribArray(3);
        this.mLineWidth = 1.0f;
    }

    public boolean isScissorTestEnabled() {
        return this.mScissorTestEnabled;
    }

    public boolean enableScissorTest() {
        if (this.mScissorTestEnabled) {
            return true;
        }
        this.mScissorTestEnabled = true;
        GLES20.glEnable(3089);
        return false;
    }

    public boolean disableScissorTest() {
        if (!this.mScissorTestEnabled) {
            return false;
        }
        this.mScissorTestEnabled = false;
        GLES20.glDisable(3089);
        return true;
    }

    public boolean setScissorTestEnabled(boolean pEnabled) {
        if (pEnabled) {
            return enableScissorTest();
        }
        return disableScissorTest();
    }

    public boolean isBlendEnabled() {
        return this.mBlendEnabled;
    }

    public boolean enableBlend() {
        if (this.mBlendEnabled) {
            return true;
        }
        this.mBlendEnabled = true;
        GLES20.glEnable(3042);
        return false;
    }

    public boolean disableBlend() {
        if (!this.mBlendEnabled) {
            return false;
        }
        this.mBlendEnabled = false;
        GLES20.glDisable(3042);
        return true;
    }

    public boolean setBlendEnabled(boolean pEnabled) {
        if (pEnabled) {
            return enableBlend();
        }
        return disableBlend();
    }

    public boolean isCullingEnabled() {
        return this.mCullingEnabled;
    }

    public boolean enableCulling() {
        if (this.mCullingEnabled) {
            return true;
        }
        this.mCullingEnabled = true;
        GLES20.glEnable(2884);
        return false;
    }

    public boolean disableCulling() {
        if (!this.mCullingEnabled) {
            return false;
        }
        this.mCullingEnabled = false;
        GLES20.glDisable(2884);
        return true;
    }

    public boolean setCullingEnabled(boolean pEnabled) {
        if (pEnabled) {
            return enableCulling();
        }
        return disableCulling();
    }

    public boolean isDitherEnabled() {
        return this.mDitherEnabled;
    }

    public boolean enableDither() {
        if (this.mDitherEnabled) {
            return true;
        }
        this.mDitherEnabled = true;
        GLES20.glEnable(3024);
        return false;
    }

    public boolean disableDither() {
        if (!this.mDitherEnabled) {
            return false;
        }
        this.mDitherEnabled = false;
        GLES20.glDisable(3024);
        return true;
    }

    public boolean setDitherEnabled(boolean pEnabled) {
        if (pEnabled) {
            return enableDither();
        }
        return disableDither();
    }

    public boolean isDepthTestEnabled() {
        return this.mDepthTestEnabled;
    }

    public boolean enableDepthTest() {
        if (this.mDepthTestEnabled) {
            return true;
        }
        this.mDepthTestEnabled = true;
        GLES20.glEnable(2929);
        return false;
    }

    public boolean disableDepthTest() {
        if (!this.mDepthTestEnabled) {
            return false;
        }
        this.mDepthTestEnabled = false;
        GLES20.glDisable(2929);
        return true;
    }

    public boolean setDepthTestEnabled(boolean pEnabled) {
        if (pEnabled) {
            return enableDepthTest();
        }
        return disableDepthTest();
    }

    public int generateBuffer() {
        GLES20.glGenBuffers(1, this.mHardwareIDContainer, 0);
        return this.mHardwareIDContainer[0];
    }

    public int generateArrayBuffer(int pSize, int pUsage) {
        GLES20.glGenBuffers(1, this.mHardwareIDContainer, 0);
        int hardwareBufferID = this.mHardwareIDContainer[0];
        bindArrayBuffer(hardwareBufferID);
        GLES20.glBufferData(34962, pSize, null, pUsage);
        bindArrayBuffer(0);
        return hardwareBufferID;
    }

    public void bindArrayBuffer(int pHardwareBufferID) {
        if (this.mCurrentArrayBufferID != pHardwareBufferID) {
            this.mCurrentArrayBufferID = pHardwareBufferID;
            GLES20.glBindBuffer(34962, pHardwareBufferID);
        }
    }

    public void deleteArrayBuffer(int pHardwareBufferID) {
        if (this.mCurrentArrayBufferID == pHardwareBufferID) {
            this.mCurrentArrayBufferID = -1;
        }
        this.mHardwareIDContainer[0] = pHardwareBufferID;
        GLES20.glDeleteBuffers(1, this.mHardwareIDContainer, 0);
    }

    public int generateIndexBuffer(int pSize, int pUsage) {
        GLES20.glGenBuffers(1, this.mHardwareIDContainer, 0);
        int hardwareBufferID = this.mHardwareIDContainer[0];
        bindIndexBuffer(hardwareBufferID);
        GLES20.glBufferData(34963, pSize, null, pUsage);
        bindIndexBuffer(0);
        return hardwareBufferID;
    }

    public void bindIndexBuffer(int pHardwareBufferID) {
        if (this.mCurrentIndexBufferID != pHardwareBufferID) {
            this.mCurrentIndexBufferID = pHardwareBufferID;
            GLES20.glBindBuffer(34963, pHardwareBufferID);
        }
    }

    public void deleteIndexBuffer(int pHardwareBufferID) {
        if (this.mCurrentIndexBufferID == pHardwareBufferID) {
            this.mCurrentIndexBufferID = -1;
        }
        this.mHardwareIDContainer[0] = pHardwareBufferID;
        GLES20.glDeleteBuffers(1, this.mHardwareIDContainer, 0);
    }

    public int generateFramebuffer() {
        GLES20.glGenFramebuffers(1, this.mHardwareIDContainer, 0);
        return this.mHardwareIDContainer[0];
    }

    public void bindFramebuffer(int pFramebufferID) {
        GLES20.glBindFramebuffer(36160, pFramebufferID);
    }

    public int getFramebufferStatus() {
        return GLES20.glCheckFramebufferStatus(36160);
    }

    public void checkFramebufferStatus() throws GLFrameBufferException, GLException {
        int framebufferStatus = getFramebufferStatus();
        switch (framebufferStatus) {
            case 0:
                checkError();
                break;
            case 36053:
                return;
            case 36054:
                throw new GLFrameBufferException(framebufferStatus, "GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
            case 36055:
                throw new GLFrameBufferException(framebufferStatus, "GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
            case 36057:
                throw new GLFrameBufferException(framebufferStatus, "GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS");
            case 36061:
                throw new GLFrameBufferException(framebufferStatus, "GL_FRAMEBUFFER_UNSUPPORTED");
        }
        throw new GLFrameBufferException(framebufferStatus);
    }

    public int getActiveFramebuffer() {
        return getInteger(36006);
    }

    public void deleteFramebuffer(int pHardwareFramebufferID) {
        if (this.mCurrentFramebufferID == pHardwareFramebufferID) {
            this.mCurrentFramebufferID = -1;
        }
        this.mHardwareIDContainer[0] = pHardwareFramebufferID;
        GLES20.glDeleteFramebuffers(1, this.mHardwareIDContainer, 0);
    }

    public void useProgram(int pShaderProgramID) {
        if (this.mCurrentShaderProgramID != pShaderProgramID) {
            this.mCurrentShaderProgramID = pShaderProgramID;
            GLES20.glUseProgram(pShaderProgramID);
        }
    }

    public void deleteProgram(int pShaderProgramID) {
        if (this.mCurrentShaderProgramID == pShaderProgramID) {
            this.mCurrentShaderProgramID = -1;
        }
        GLES20.glDeleteProgram(pShaderProgramID);
    }

    public int generateTexture() {
        GLES20.glGenTextures(1, this.mHardwareIDContainer, 0);
        return this.mHardwareIDContainer[0];
    }

    public boolean isTexture(int pHardwareTextureID) {
        return GLES20.glIsTexture(pHardwareTextureID);
    }

    public int getActiveTexture() {
        return this.mCurrentActiveTextureIndex + 33984;
    }

    public void activeTexture(int pGLActiveTexture) {
        int activeTextureIndex = pGLActiveTexture - 33984;
        if (pGLActiveTexture != this.mCurrentActiveTextureIndex) {
            this.mCurrentActiveTextureIndex = activeTextureIndex;
            GLES20.glActiveTexture(pGLActiveTexture);
        }
    }

    public void bindTexture(int pHardwareTextureID) {
        if (this.mCurrentBoundTextureIDs[this.mCurrentActiveTextureIndex] != pHardwareTextureID) {
            this.mCurrentBoundTextureIDs[this.mCurrentActiveTextureIndex] = pHardwareTextureID;
            GLES20.glBindTexture(3553, pHardwareTextureID);
        }
    }

    public void deleteTexture(int pHardwareTextureID) {
        if (this.mCurrentBoundTextureIDs[this.mCurrentActiveTextureIndex] == pHardwareTextureID) {
            this.mCurrentBoundTextureIDs[this.mCurrentActiveTextureIndex] = -1;
        }
        this.mHardwareIDContainer[0] = pHardwareTextureID;
        GLES20.glDeleteTextures(1, this.mHardwareIDContainer, 0);
    }

    public void blendFunction(int pSourceBlendMode, int pDestinationBlendMode) {
        if (this.mCurrentSourceBlendMode != pSourceBlendMode || this.mCurrentDestinationBlendMode != pDestinationBlendMode) {
            this.mCurrentSourceBlendMode = pSourceBlendMode;
            this.mCurrentDestinationBlendMode = pDestinationBlendMode;
            GLES20.glBlendFunc(pSourceBlendMode, pDestinationBlendMode);
        }
    }

    public void lineWidth(float pLineWidth) {
        if (this.mLineWidth != pLineWidth) {
            this.mLineWidth = pLineWidth;
            GLES20.glLineWidth(pLineWidth);
        }
    }

    public void pushModelViewGLMatrix() {
        this.mModelViewGLMatrixStack.glPushMatrix();
    }

    public void popModelViewGLMatrix() {
        this.mModelViewGLMatrixStack.glPopMatrix();
    }

    public void loadModelViewGLMatrixIdentity() {
        this.mModelViewGLMatrixStack.glLoadIdentity();
    }

    public void translateModelViewGLMatrixf(float pX, float pY, float pZ) {
        this.mModelViewGLMatrixStack.glTranslatef(pX, pY, pZ);
    }

    public void rotateModelViewGLMatrixf(float pAngle, float pX, float pY, float pZ) {
        this.mModelViewGLMatrixStack.glRotatef(pAngle, pX, pY, pZ);
    }

    public void scaleModelViewGLMatrixf(float pScaleX, float pScaleY, int pScaleZ) {
        this.mModelViewGLMatrixStack.glScalef(pScaleX, pScaleY, (float) pScaleZ);
    }

    public void skewModelViewGLMatrixf(float pSkewX, float pSkewY) {
        this.mModelViewGLMatrixStack.glSkewf(pSkewX, pSkewY);
    }

    public void orthoModelViewGLMatrixf(float pLeft, float pRight, float pBottom, float pTop, float pZNear, float pZFar) {
        this.mModelViewGLMatrixStack.glOrthof(pLeft, pRight, pBottom, pTop, pZNear, pZFar);
    }

    public void pushProjectionGLMatrix() {
        this.mProjectionGLMatrixStack.glPushMatrix();
    }

    public void popProjectionGLMatrix() {
        this.mProjectionGLMatrixStack.glPopMatrix();
    }

    public void loadProjectionGLMatrixIdentity() {
        this.mProjectionGLMatrixStack.glLoadIdentity();
    }

    public void translateProjectionGLMatrixf(float pX, float pY, float pZ) {
        this.mProjectionGLMatrixStack.glTranslatef(pX, pY, pZ);
    }

    public void rotateProjectionGLMatrixf(float pAngle, float pX, float pY, float pZ) {
        this.mProjectionGLMatrixStack.glRotatef(pAngle, pX, pY, pZ);
    }

    public void scaleProjectionGLMatrixf(float pScaleX, float pScaleY, float pScaleZ) {
        this.mProjectionGLMatrixStack.glScalef(pScaleX, pScaleY, pScaleZ);
    }

    public void skewProjectionGLMatrixf(float pSkewX, float pSkewY) {
        this.mProjectionGLMatrixStack.glSkewf(pSkewX, pSkewY);
    }

    public void orthoProjectionGLMatrixf(float pLeft, float pRight, float pBottom, float pTop, float pZNear, float pZFar) {
        this.mProjectionGLMatrixStack.glOrthof(pLeft, pRight, pBottom, pTop, pZNear, pZFar);
    }

    public float[] getModelViewGLMatrix() {
        this.mModelViewGLMatrixStack.getMatrix(this.mModelViewGLMatrix);
        return this.mModelViewGLMatrix;
    }

    public float[] getProjectionGLMatrix() {
        this.mProjectionGLMatrixStack.getMatrix(this.mProjectionGLMatrix);
        return this.mProjectionGLMatrix;
    }

    public float[] getModelViewProjectionGLMatrix() {
        Matrix.multiplyMM(this.mModelViewProjectionGLMatrix, 0, this.mProjectionGLMatrixStack.mMatrixStack, this.mProjectionGLMatrixStack.mMatrixStackOffset, this.mModelViewGLMatrixStack.mMatrixStack, this.mModelViewGLMatrixStack.mMatrixStackOffset);
        return this.mModelViewProjectionGLMatrix;
    }

    public void resetModelViewGLMatrixStack() {
        this.mModelViewGLMatrixStack.reset();
    }

    public void resetProjectionGLMatrixStack() {
        this.mProjectionGLMatrixStack.reset();
    }

    public void resetGLMatrixStacks() {
        this.mModelViewGLMatrixStack.reset();
        this.mProjectionGLMatrixStack.reset();
    }

    public void glTexImage2D(int pTarget, int pLevel, Bitmap pBitmap, int pBorder, PixelFormat pPixelFormat) {
        int i = pTarget;
        int i2 = pLevel;
        int i3 = pBorder;
        GLES20.glTexImage2D(i, i2, pPixelFormat.getGLInternalFormat(), pBitmap.getWidth(), pBitmap.getHeight(), i3, pPixelFormat.getGLFormat(), pPixelFormat.getGLType(), GLHelper.getPixels(pBitmap, pPixelFormat, ByteOrder.BIG_ENDIAN));
    }

    public void glTexSubImage2D(int pTarget, int pLevel, int pX, int pY, Bitmap pBitmap, PixelFormat pPixelFormat) {
        int i = pTarget;
        int i2 = pLevel;
        int i3 = pX;
        int i4 = pY;
        GLES20.glTexSubImage2D(i, i2, i3, i4, pBitmap.getWidth(), pBitmap.getHeight(), pPixelFormat.getGLFormat(), pPixelFormat.getGLType(), GLHelper.getPixels(pBitmap, pPixelFormat, ByteOrder.BIG_ENDIAN));
    }

    public void flush() {
        GLES20.glFlush();
    }

    public void finish() {
        GLES20.glFinish();
    }

    public int getInteger(int pAttribute) {
        GLES20.glGetIntegerv(pAttribute, this.mHardwareIDContainer, 0);
        return this.mHardwareIDContainer[0];
    }

    public int getError() {
        return GLES20.glGetError();
    }

    public void checkError() throws GLException {
        int error = GLES20.glGetError();
        if (error != 0) {
            throw new GLException(error);
        }
    }

    public void clearError() {
        GLES20.glGetError();
    }
}
