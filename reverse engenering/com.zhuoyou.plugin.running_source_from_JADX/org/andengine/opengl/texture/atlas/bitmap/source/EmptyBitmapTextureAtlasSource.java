package org.andengine.opengl.texture.atlas.bitmap.source;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import org.andengine.opengl.texture.atlas.source.BaseTextureAtlasSource;

public class EmptyBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
    public EmptyBitmapTextureAtlasSource(int pTextureWidth, int pTextureHeight) {
        this(0, 0, pTextureWidth, pTextureHeight);
    }

    public EmptyBitmapTextureAtlasSource(int pTextureX, int pTextureY, int pTextureWidth, int pTextureHeight) {
        super(pTextureX, pTextureY, pTextureWidth, pTextureHeight);
    }

    public EmptyBitmapTextureAtlasSource deepCopy() {
        return new EmptyBitmapTextureAtlasSource(this.mTextureX, this.mTextureY, this.mTextureWidth, this.mTextureHeight);
    }

    public Bitmap onLoadBitmap(Config pBitmapConfig) {
        return Bitmap.createBitmap(this.mTextureWidth, this.mTextureHeight, pBitmapConfig);
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.mTextureWidth + " x " + this.mTextureHeight + ")";
    }
}
