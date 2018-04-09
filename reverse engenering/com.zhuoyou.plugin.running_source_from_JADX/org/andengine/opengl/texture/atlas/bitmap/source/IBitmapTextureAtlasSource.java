package org.andengine.opengl.texture.atlas.bitmap.source;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;

public interface IBitmapTextureAtlasSource extends ITextureAtlasSource {
    IBitmapTextureAtlasSource deepCopy();

    Bitmap onLoadBitmap(Config config);
}
