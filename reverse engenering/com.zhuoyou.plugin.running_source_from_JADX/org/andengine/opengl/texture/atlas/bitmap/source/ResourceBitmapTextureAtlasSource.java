package org.andengine.opengl.texture.atlas.bitmap.source;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import org.andengine.opengl.texture.atlas.source.BaseTextureAtlasSource;

public class ResourceBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
    private final int mDrawableResourceID;
    private final Resources mResources;

    public static ResourceBitmapTextureAtlasSource create(Resources pResources, int pDrawableResourceID) {
        return create(pResources, pDrawableResourceID, 0, 0);
    }

    public static ResourceBitmapTextureAtlasSource create(Resources pResources, int pDrawableResourceID, int pTextureX, int pTextureY) {
        Options decodeOptions = new Options();
        decodeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(pResources, pDrawableResourceID, decodeOptions);
        return new ResourceBitmapTextureAtlasSource(pResources, pDrawableResourceID, pTextureX, pTextureY, decodeOptions.outWidth, decodeOptions.outHeight);
    }

    public ResourceBitmapTextureAtlasSource(Resources pResources, int pDrawableResourceID, int pTextureX, int pTextureY, int pTextureWidth, int pTextureHeight) {
        super(pTextureX, pTextureY, pTextureWidth, pTextureHeight);
        this.mResources = pResources;
        this.mDrawableResourceID = pDrawableResourceID;
    }

    public ResourceBitmapTextureAtlasSource deepCopy() {
        return new ResourceBitmapTextureAtlasSource(this.mResources, this.mDrawableResourceID, this.mTextureX, this.mTextureY, this.mTextureWidth, this.mTextureHeight);
    }

    public Bitmap onLoadBitmap(Config pBitmapConfig) {
        Options decodeOptions = new Options();
        decodeOptions.inPreferredConfig = pBitmapConfig;
        return BitmapFactory.decodeResource(this.mResources, this.mDrawableResourceID, decodeOptions);
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.mDrawableResourceID + ")";
    }
}
