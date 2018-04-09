package org.andengine.opengl.texture.atlas.bitmap.source;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Picture;
import org.andengine.opengl.texture.atlas.source.BaseTextureAtlasSource;
import org.andengine.util.debug.Debug;

public abstract class PictureBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
    protected final Picture mPicture;

    public abstract PictureBitmapTextureAtlasSource deepCopy();

    public PictureBitmapTextureAtlasSource(Picture pPicture) {
        this(pPicture, 0, 0);
    }

    public PictureBitmapTextureAtlasSource(Picture pPicture, int pTextureX, int pTextureY) {
        this(pPicture, pTextureX, pTextureY, pPicture.getWidth(), pPicture.getHeight());
    }

    public PictureBitmapTextureAtlasSource(Picture pPicture, int pTextureX, int pTextureY, float pScale) {
        this(pPicture, pTextureX, pTextureY, Math.round(((float) pPicture.getWidth()) * pScale), Math.round(((float) pPicture.getHeight()) * pScale));
    }

    public PictureBitmapTextureAtlasSource(Picture pPicture, int pTextureX, int pTextureY, int pTextureWidth, int pTextureHeight) {
        super(pTextureX, pTextureY, pTextureWidth, pTextureHeight);
        this.mPicture = pPicture;
    }

    public Bitmap onLoadBitmap(Config pBitmapConfig) {
        Picture picture = this.mPicture;
        if (picture == null) {
            Debug.m4588e("Failed loading Bitmap in " + getClass().getSimpleName() + ".");
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(this.mTextureWidth, this.mTextureHeight, pBitmapConfig);
        Canvas canvas = new Canvas(bitmap);
        canvas.scale(((float) this.mTextureWidth) / ((float) this.mPicture.getWidth()), ((float) this.mTextureHeight) / ((float) this.mPicture.getHeight()), 0.0f, 0.0f);
        picture.draw(canvas);
        return bitmap;
    }
}
