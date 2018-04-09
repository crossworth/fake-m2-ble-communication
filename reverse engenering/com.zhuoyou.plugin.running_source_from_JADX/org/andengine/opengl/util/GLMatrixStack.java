package org.andengine.opengl.util;

import android.opengl.Matrix;
import org.andengine.util.exception.AndEngineRuntimeException;

public class GLMatrixStack {
    private static final int GLMATRIXSTACKOFFSET_OVERFLOW = 512;
    private static final int GLMATRIXSTACKOFFSET_UNDERFLOW = -16;
    public static final int GLMATRIXSTACK_DEPTH_MAX = 32;
    public static final int GLMATRIX_SIZE = 16;
    final float[] mMatrixStack = new float[512];
    int mMatrixStackOffset;
    private final float[] mTemp = new float[32];

    public static class GLMatrixStackOverflowException extends AndEngineRuntimeException {
        private static final long serialVersionUID = -800847781599300100L;
    }

    public static class GLMatrixStackUnderflowException extends AndEngineRuntimeException {
        private static final long serialVersionUID = -3268021423136372954L;
    }

    public GLMatrixStack() {
        glLoadIdentity();
    }

    public void getMatrix(float[] pMatrix) {
        System.arraycopy(this.mMatrixStack, this.mMatrixStackOffset, pMatrix, 0, 16);
    }

    public void glLoadIdentity() {
        Matrix.setIdentityM(this.mMatrixStack, this.mMatrixStackOffset);
    }

    public void glTranslatef(float pX, float pY, float pZ) {
        Matrix.translateM(this.mMatrixStack, this.mMatrixStackOffset, pX, pY, pZ);
    }

    public void glRotatef(float pAngle, float pX, float pY, float pZ) {
        Matrix.setRotateM(this.mTemp, 0, pAngle, pX, pY, pZ);
        System.arraycopy(this.mMatrixStack, this.mMatrixStackOffset, this.mTemp, 16, 16);
        Matrix.multiplyMM(this.mMatrixStack, this.mMatrixStackOffset, this.mTemp, 16, this.mTemp, 0);
    }

    public void glScalef(float pScaleX, float pScaleY, float pScaleZ) {
        Matrix.scaleM(this.mMatrixStack, this.mMatrixStackOffset, pScaleX, pScaleY, pScaleZ);
    }

    public void glSkewf(float pSkewX, float pSkewY) {
        setSkewM(this.mTemp, 0, pSkewX, pSkewY);
        System.arraycopy(this.mMatrixStack, this.mMatrixStackOffset, this.mTemp, 16, 16);
        Matrix.multiplyMM(this.mMatrixStack, this.mMatrixStackOffset, this.mTemp, 16, this.mTemp, 0);
    }

    public void glOrthof(float pLeft, float pRight, float pBottom, float pTop, float pZNear, float pZFar) {
        Matrix.orthoM(this.mMatrixStack, this.mMatrixStackOffset, pLeft, pRight, pBottom, pTop, pZNear, pZFar);
    }

    public void glPushMatrix() throws GLMatrixStackOverflowException {
        if (this.mMatrixStackOffset + 16 >= 512) {
            throw new GLMatrixStackOverflowException();
        }
        System.arraycopy(this.mMatrixStack, this.mMatrixStackOffset, this.mMatrixStack, this.mMatrixStackOffset + 16, 16);
        this.mMatrixStackOffset += 16;
    }

    public void glPopMatrix() {
        if (this.mMatrixStackOffset + GLMATRIXSTACKOFFSET_UNDERFLOW <= GLMATRIXSTACKOFFSET_UNDERFLOW) {
            throw new GLMatrixStackUnderflowException();
        }
        this.mMatrixStackOffset += GLMATRIXSTACKOFFSET_UNDERFLOW;
    }

    public void reset() {
        this.mMatrixStackOffset = 0;
        glLoadIdentity();
    }

    private static void setSkewM(float[] pMatrixStack, int pOffset, float pSkewX, float pSkewY) {
        pMatrixStack[pOffset + 0] = 1.0f;
        pMatrixStack[pOffset + 1] = (float) Math.tan((double) (-0.017453292f * pSkewY));
        pMatrixStack[pOffset + 2] = 0.0f;
        pMatrixStack[pOffset + 3] = 0.0f;
        pMatrixStack[pOffset + 4] = (float) Math.tan((double) (-0.017453292f * pSkewX));
        pMatrixStack[pOffset + 5] = 1.0f;
        pMatrixStack[pOffset + 6] = 0.0f;
        pMatrixStack[pOffset + 7] = 0.0f;
        pMatrixStack[pOffset + 8] = 0.0f;
        pMatrixStack[pOffset + 9] = 0.0f;
        pMatrixStack[pOffset + 10] = 1.0f;
        pMatrixStack[pOffset + 11] = 0.0f;
        pMatrixStack[pOffset + 12] = 0.0f;
        pMatrixStack[pOffset + 13] = 0.0f;
        pMatrixStack[pOffset + 14] = 0.0f;
        pMatrixStack[pOffset + 15] = 1.0f;
    }
}
