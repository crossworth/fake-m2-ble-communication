package org.andengine.util.adt.transformation;

import org.andengine.util.math.MathConstants;

public class Transformation {
    private float f5019a = 1.0f;
    private float f5020b = 0.0f;
    private float f5021c = 0.0f;
    private float f5022d = 1.0f;
    private float tx = 0.0f;
    private float ty = 0.0f;

    public String toString() {
        return "Transformation{[" + this.f5019a + ", " + this.f5021c + ", " + this.tx + "][" + this.f5020b + ", " + this.f5022d + ", " + this.ty + "][0.0, 0.0, 1.0]}";
    }

    public final void reset() {
        setToIdentity();
    }

    public final void setToIdentity() {
        this.f5019a = 1.0f;
        this.f5022d = 1.0f;
        this.f5020b = 0.0f;
        this.f5021c = 0.0f;
        this.tx = 0.0f;
        this.ty = 0.0f;
    }

    public final void setTo(Transformation pTransformation) {
        this.f5019a = pTransformation.f5019a;
        this.f5022d = pTransformation.f5022d;
        this.f5020b = pTransformation.f5020b;
        this.f5021c = pTransformation.f5021c;
        this.tx = pTransformation.tx;
        this.ty = pTransformation.ty;
    }

    public final void preTranslate(float pX, float pY) {
        this.tx += (this.f5019a * pX) + (this.f5021c * pY);
        this.ty += (this.f5020b * pX) + (this.f5022d * pY);
    }

    public final void postTranslate(float pX, float pY) {
        this.tx += pX;
        this.ty += pY;
    }

    public final Transformation setToTranslate(float pX, float pY) {
        this.f5019a = 1.0f;
        this.f5020b = 0.0f;
        this.f5021c = 0.0f;
        this.f5022d = 1.0f;
        this.tx = pX;
        this.ty = pY;
        return this;
    }

    public final void preRotate(float pAngle) {
        float angleRad = MathConstants.DEG_TO_RAD * pAngle;
        float sin = (float) Math.sin((double) angleRad);
        float cos = (float) Math.cos((double) angleRad);
        float a = this.f5019a;
        float b = this.f5020b;
        float c = this.f5021c;
        float d = this.f5022d;
        this.f5019a = (cos * a) + (sin * c);
        this.f5020b = (cos * b) + (sin * d);
        this.f5021c = (cos * c) - (sin * a);
        this.f5022d = (cos * d) - (sin * b);
    }

    public final void postRotate(float pAngle) {
        float angleRad = MathConstants.DEG_TO_RAD * pAngle;
        float sin = (float) Math.sin((double) angleRad);
        float cos = (float) Math.cos((double) angleRad);
        float a = this.f5019a;
        float b = this.f5020b;
        float c = this.f5021c;
        float d = this.f5022d;
        float tx = this.tx;
        float ty = this.ty;
        this.f5019a = (a * cos) - (b * sin);
        this.f5020b = (a * sin) + (b * cos);
        this.f5021c = (c * cos) - (d * sin);
        this.f5022d = (c * sin) + (d * cos);
        this.tx = (tx * cos) - (ty * sin);
        this.ty = (tx * sin) + (ty * cos);
    }

    public final Transformation setToRotate(float pAngle) {
        float angleRad = MathConstants.DEG_TO_RAD * pAngle;
        float sin = (float) Math.sin((double) angleRad);
        float cos = (float) Math.cos((double) angleRad);
        this.f5019a = cos;
        this.f5020b = sin;
        this.f5021c = -sin;
        this.f5022d = cos;
        this.tx = 0.0f;
        this.ty = 0.0f;
        return this;
    }

    public final void preScale(float pScaleX, float pScaleY) {
        this.f5019a *= pScaleX;
        this.f5020b *= pScaleX;
        this.f5021c *= pScaleY;
        this.f5022d *= pScaleY;
    }

    public final void postScale(float pScaleX, float pScaleY) {
        this.f5019a *= pScaleX;
        this.f5020b *= pScaleY;
        this.f5021c *= pScaleX;
        this.f5022d *= pScaleY;
        this.tx *= pScaleX;
        this.ty *= pScaleY;
    }

    public final Transformation setToScale(float pScaleX, float pScaleY) {
        this.f5019a = pScaleX;
        this.f5020b = 0.0f;
        this.f5021c = 0.0f;
        this.f5022d = pScaleY;
        this.tx = 0.0f;
        this.ty = 0.0f;
        return this;
    }

    public final void preSkew(float pSkewX, float pSkewY) {
        float tanX = (float) Math.tan((double) (-0.017453292f * pSkewX));
        float tanY = (float) Math.tan((double) (-0.017453292f * pSkewY));
        float a = this.f5019a;
        float b = this.f5020b;
        float c = this.f5021c;
        float d = this.f5022d;
        float tx = this.tx;
        float ty = this.ty;
        this.f5019a = (tanY * c) + a;
        this.f5020b = (tanY * d) + b;
        this.f5021c = (tanX * a) + c;
        this.f5022d = (tanX * b) + d;
        this.tx = tx;
        this.ty = ty;
    }

    public final void postSkew(float pSkewX, float pSkewY) {
        float tanX = (float) Math.tan((double) (-0.017453292f * pSkewX));
        float tanY = (float) Math.tan((double) (-0.017453292f * pSkewY));
        float a = this.f5019a;
        float b = this.f5020b;
        float c = this.f5021c;
        float d = this.f5022d;
        float tx = this.tx;
        float ty = this.ty;
        this.f5019a = (b * tanX) + a;
        this.f5020b = (a * tanY) + b;
        this.f5021c = (d * tanX) + c;
        this.f5022d = (c * tanY) + d;
        this.tx = (ty * tanX) + tx;
        this.ty = (tx * tanY) + ty;
    }

    public final Transformation setToSkew(float pSkewX, float pSkewY) {
        this.f5019a = 1.0f;
        this.f5020b = (float) Math.tan((double) (-0.017453292f * pSkewY));
        this.f5021c = (float) Math.tan((double) (-0.017453292f * pSkewX));
        this.f5022d = 1.0f;
        this.tx = 0.0f;
        this.ty = 0.0f;
        return this;
    }

    public final void postConcat(Transformation pTransformation) {
        postConcat(pTransformation.f5019a, pTransformation.f5020b, pTransformation.f5021c, pTransformation.f5022d, pTransformation.tx, pTransformation.ty);
    }

    private void postConcat(float pA, float pB, float pC, float pD, float pTX, float pTY) {
        float a = this.f5019a;
        float b = this.f5020b;
        float c = this.f5021c;
        float d = this.f5022d;
        float tx = this.tx;
        float ty = this.ty;
        this.f5019a = (a * pA) + (b * pC);
        this.f5020b = (a * pB) + (b * pD);
        this.f5021c = (c * pA) + (d * pC);
        this.f5022d = (c * pB) + (d * pD);
        this.tx = ((tx * pA) + (ty * pC)) + pTX;
        this.ty = ((tx * pB) + (ty * pD)) + pTY;
    }

    public final void preConcat(Transformation pTransformation) {
        preConcat(pTransformation.f5019a, pTransformation.f5020b, pTransformation.f5021c, pTransformation.f5022d, pTransformation.tx, pTransformation.ty);
    }

    private void preConcat(float pA, float pB, float pC, float pD, float pTX, float pTY) {
        float a = this.f5019a;
        float b = this.f5020b;
        float c = this.f5021c;
        float d = this.f5022d;
        float tx = this.tx;
        float ty = this.ty;
        this.f5019a = (pA * a) + (pB * c);
        this.f5020b = (pA * b) + (pB * d);
        this.f5021c = (pC * a) + (pD * c);
        this.f5022d = (pC * b) + (pD * d);
        this.tx = ((pTX * a) + (pTY * c)) + tx;
        this.ty = ((pTX * b) + (pTY * d)) + ty;
    }

    public final void transform(float[] pVertices) {
        int count = pVertices.length >> 1;
        int j = 0;
        int i = 0;
        while (true) {
            count--;
            if (count >= 0) {
                int i2 = i + 1;
                float x = pVertices[i];
                i = i2 + 1;
                float y = pVertices[i2];
                int i3 = j + 1;
                pVertices[j] = ((this.f5019a * x) + (this.f5021c * y)) + this.tx;
                j = i3 + 1;
                pVertices[i3] = ((this.f5020b * x) + (this.f5022d * y)) + this.ty;
            } else {
                return;
            }
        }
    }
}
