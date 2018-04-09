package org.andengine.opengl.texture;

import android.opengl.GLES20;

public class TextureOptions {
    public static final TextureOptions BILINEAR = new TextureOptions(9729, 9729, 33071, 33071, false);
    public static final TextureOptions BILINEAR_PREMULTIPLYALPHA = new TextureOptions(9729, 9729, 33071, 33071, true);
    public static final TextureOptions DEFAULT = NEAREST;
    public static final TextureOptions NEAREST = new TextureOptions(9728, 9728, 33071, 33071, false);
    public static final TextureOptions NEAREST_PREMULTIPLYALPHA = new TextureOptions(9728, 9728, 33071, 33071, true);
    public static final TextureOptions REPEATING_BILINEAR = new TextureOptions(9729, 9729, 10497, 10497, false);
    public static final TextureOptions REPEATING_BILINEAR_PREMULTIPLYALPHA = new TextureOptions(9729, 9729, 10497, 10497, true);
    public static final TextureOptions REPEATING_NEAREST = new TextureOptions(9728, 9728, 10497, 10497, false);
    public static final TextureOptions REPEATING_NEAREST_PREMULTIPLYALPHA = new TextureOptions(9728, 9728, 10497, 10497, true);
    public final int mMagFilter;
    public final int mMinFilter;
    public final boolean mPreMultiplyAlpha;
    public final float mWrapS;
    public final float mWrapT;

    public TextureOptions(int pMinFilter, int pMagFilter, int pWrapT, int pWrapS, boolean pPreMultiplyAlpha) {
        this.mMinFilter = pMinFilter;
        this.mMagFilter = pMagFilter;
        this.mWrapT = (float) pWrapT;
        this.mWrapS = (float) pWrapS;
        this.mPreMultiplyAlpha = pPreMultiplyAlpha;
    }

    public void apply() {
        GLES20.glTexParameterf(3553, 10241, (float) this.mMinFilter);
        GLES20.glTexParameterf(3553, 10240, (float) this.mMagFilter);
        GLES20.glTexParameterf(3553, 10242, this.mWrapS);
        GLES20.glTexParameterf(3553, 10243, this.mWrapT);
    }
}
