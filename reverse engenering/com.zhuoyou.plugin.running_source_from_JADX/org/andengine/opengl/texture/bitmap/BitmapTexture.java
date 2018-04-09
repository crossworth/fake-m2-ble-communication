package org.andengine.opengl.texture.bitmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import java.io.IOException;
import org.andengine.opengl.texture.ITextureStateListener;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.StreamUtils;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.exception.NullBitmapException;
import org.andengine.util.math.MathUtils;

public class BitmapTexture extends Texture {
    private final BitmapTextureFormat mBitmapTextureFormat;
    private final int mHeight;
    private final IInputStreamOpener mInputStreamOpener;
    private final int mWidth;

    public BitmapTexture(TextureManager pTextureManager, IInputStreamOpener pInputStreamOpener) throws IOException {
        this(pTextureManager, pInputStreamOpener, BitmapTextureFormat.RGBA_8888, TextureOptions.DEFAULT, null);
    }

    public BitmapTexture(TextureManager pTextureManager, IInputStreamOpener pInputStreamOpener, BitmapTextureFormat pBitmapTextureFormat) throws IOException {
        this(pTextureManager, pInputStreamOpener, pBitmapTextureFormat, TextureOptions.DEFAULT, null);
    }

    public BitmapTexture(TextureManager pTextureManager, IInputStreamOpener pInputStreamOpener, TextureOptions pTextureOptions) throws IOException {
        this(pTextureManager, pInputStreamOpener, BitmapTextureFormat.RGBA_8888, pTextureOptions, null);
    }

    public BitmapTexture(TextureManager pTextureManager, IInputStreamOpener pInputStreamOpener, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions) throws IOException {
        this(pTextureManager, pInputStreamOpener, pBitmapTextureFormat, pTextureOptions, null);
    }

    public BitmapTexture(TextureManager pTextureManager, IInputStreamOpener pInputStreamOpener, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, ITextureStateListener pTextureStateListener) throws IOException {
        super(pTextureManager, pBitmapTextureFormat.getPixelFormat(), pTextureOptions, pTextureStateListener);
        this.mInputStreamOpener = pInputStreamOpener;
        this.mBitmapTextureFormat = pBitmapTextureFormat;
        Options decodeOptions = new Options();
        decodeOptions.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(pInputStreamOpener.open(), null, decodeOptions);
            this.mWidth = decodeOptions.outWidth;
            this.mHeight = decodeOptions.outHeight;
        } finally {
            StreamUtils.close(null);
        }
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    protected void writeTextureToHardware(GLState pGLState) throws IOException {
        Bitmap bitmap = onGetBitmap(this.mBitmapTextureFormat.getBitmapConfig());
        if (bitmap == null) {
            throw new NullBitmapException("Caused by: '" + toString() + "'.");
        }
        boolean useDefaultAlignment;
        if (MathUtils.isPowerOfTwo(bitmap.getWidth()) && MathUtils.isPowerOfTwo(bitmap.getHeight()) && this.mPixelFormat == PixelFormat.RGBA_8888) {
            useDefaultAlignment = true;
        } else {
            useDefaultAlignment = false;
        }
        if (!useDefaultAlignment) {
            GLES20.glPixelStorei(3317, 1);
        }
        if (this.mTextureOptions.mPreMultiplyAlpha) {
            GLUtils.texImage2D(3553, 0, bitmap, 0);
        } else {
            pGLState.glTexImage2D(3553, 0, bitmap, 0, this.mPixelFormat);
        }
        if (!useDefaultAlignment) {
            GLES20.glPixelStorei(3317, 4);
        }
        bitmap.recycle();
    }

    protected Bitmap onGetBitmap(Config pBitmapConfig) throws IOException {
        Options decodeOptions = new Options();
        decodeOptions.inPreferredConfig = pBitmapConfig;
        return BitmapFactory.decodeStream(this.mInputStreamOpener.open(), null, decodeOptions);
    }
}
