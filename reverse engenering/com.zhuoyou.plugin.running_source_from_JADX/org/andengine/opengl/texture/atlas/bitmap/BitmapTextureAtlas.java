package org.andengine.opengl.texture.atlas.bitmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import java.util.ArrayList;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.ITextureAtlas.ITextureAtlasStateListener;
import org.andengine.opengl.texture.atlas.TextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.util.GLState;
import org.andengine.util.exception.NullBitmapException;
import org.andengine.util.math.MathUtils;

public class BitmapTextureAtlas extends TextureAtlas<IBitmapTextureAtlasSource> {
    private final BitmapTextureFormat mBitmapTextureFormat;

    public BitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight) {
        this(pTextureManager, pWidth, pHeight, BitmapTextureFormat.RGBA_8888);
    }

    public BitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, BitmapTextureFormat pBitmapTextureFormat) {
        this(pTextureManager, pWidth, pHeight, pBitmapTextureFormat, TextureOptions.DEFAULT, null);
    }

    public BitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) {
        this(pTextureManager, pWidth, pHeight, BitmapTextureFormat.RGBA_8888, TextureOptions.DEFAULT, pTextureAtlasStateListener);
    }

    public BitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, BitmapTextureFormat pBitmapTextureFormat, ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) {
        this(pTextureManager, pWidth, pHeight, pBitmapTextureFormat, TextureOptions.DEFAULT, pTextureAtlasStateListener);
    }

    public BitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, TextureOptions pTextureOptions) throws IllegalArgumentException {
        this(pTextureManager, pWidth, pHeight, BitmapTextureFormat.RGBA_8888, pTextureOptions, null);
    }

    public BitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions) throws IllegalArgumentException {
        this(pTextureManager, pWidth, pHeight, pBitmapTextureFormat, pTextureOptions, null);
    }

    public BitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, TextureOptions pTextureOptions, ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) throws IllegalArgumentException {
        this(pTextureManager, pWidth, pHeight, BitmapTextureFormat.RGBA_8888, pTextureOptions, pTextureAtlasStateListener);
    }

    public BitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureAtlasStateListener) throws IllegalArgumentException {
        super(pTextureManager, pWidth, pHeight, pBitmapTextureFormat.getPixelFormat(), pTextureOptions, pTextureAtlasStateListener);
        this.mBitmapTextureFormat = pBitmapTextureFormat;
    }

    public BitmapTextureFormat getBitmapTextureFormat() {
        return this.mBitmapTextureFormat;
    }

    public void addEmptyTextureAtlasSource(int pTextureX, int pTextureY, int pWidth, int pHeight) {
        addTextureAtlasSource(new EmptyBitmapTextureAtlasSource(pWidth, pHeight), pTextureX, pTextureY);
    }

    protected void writeTextureToHardware(GLState pGLState) {
        PixelFormat pixelFormat = this.mBitmapTextureFormat.getPixelFormat();
        int glInternalFormat = pixelFormat.getGLInternalFormat();
        int glFormat = pixelFormat.getGLFormat();
        int glType = pixelFormat.getGLType();
        GLES20.glTexImage2D(3553, 0, glInternalFormat, this.mWidth, this.mHeight, 0, glFormat, glType, null);
        boolean preMultipyAlpha = this.mTextureOptions.mPreMultiplyAlpha;
        Config bitmapConfig = preMultipyAlpha ? this.mBitmapTextureFormat.getBitmapConfig() : Config.ARGB_8888;
        ArrayList<IBitmapTextureAtlasSource> textureSources = this.mTextureAtlasSources;
        int textureSourceCount = textureSources.size();
        ITextureAtlasStateListener<IBitmapTextureAtlasSource> textureStateListener = getTextureAtlasStateListener();
        int i = 0;
        while (i < textureSourceCount) {
            IBitmapTextureAtlasSource bitmapTextureAtlasSource = (IBitmapTextureAtlasSource) textureSources.get(i);
            try {
                Bitmap bitmap = bitmapTextureAtlasSource.onLoadBitmap(bitmapConfig);
                if (bitmap == null) {
                    throw new NullBitmapException("Caused by: " + bitmapTextureAtlasSource.getClass().toString() + " --> " + bitmapTextureAtlasSource.toString() + " returned a null Bitmap.");
                }
                boolean useDefaultAlignment = MathUtils.isPowerOfTwo(bitmap.getWidth()) && MathUtils.isPowerOfTwo(bitmap.getHeight()) && pixelFormat == PixelFormat.RGBA_8888;
                if (!useDefaultAlignment) {
                    GLES20.glPixelStorei(3317, 1);
                }
                if (preMultipyAlpha) {
                    GLUtils.texSubImage2D(3553, 0, bitmapTextureAtlasSource.getTextureX(), bitmapTextureAtlasSource.getTextureY(), bitmap, glFormat, glType);
                } else {
                    pGLState.glTexSubImage2D(3553, 0, bitmapTextureAtlasSource.getTextureX(), bitmapTextureAtlasSource.getTextureY(), bitmap, this.mPixelFormat);
                }
                if (!useDefaultAlignment) {
                    GLES20.glPixelStorei(3317, 4);
                }
                bitmap.recycle();
                if (textureStateListener != null) {
                    textureStateListener.onTextureAtlasSourceLoaded(this, bitmapTextureAtlasSource);
                }
                i++;
            } catch (Throwable e) {
                if (textureStateListener != null) {
                    textureStateListener.onTextureAtlasSourceLoadExeption(this, bitmapTextureAtlasSource, e);
                } else {
                    throw e;
                }
            }
        }
    }
}
