package org.andengine.opengl.texture.atlas.bitmap.source;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import java.io.InputStream;
import org.andengine.opengl.texture.atlas.source.BaseTextureAtlasSource;
import org.andengine.util.StreamUtils;
import org.andengine.util.debug.Debug;

public class AssetBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
    private final AssetManager mAssetManager;
    private final String mAssetPath;

    public static AssetBitmapTextureAtlasSource create(AssetManager pAssetManager, String pAssetPath) {
        return create(pAssetManager, pAssetPath, 0, 0);
    }

    public static AssetBitmapTextureAtlasSource create(AssetManager pAssetManager, String pAssetPath, int pTextureX, int pTextureY) {
        Options decodeOptions = new Options();
        decodeOptions.inJustDecodeBounds = true;
        InputStream inputStream = null;
        try {
            inputStream = pAssetManager.open(pAssetPath);
            BitmapFactory.decodeStream(inputStream, null, decodeOptions);
        } catch (Throwable e) {
            Debug.m4591e("Failed loading Bitmap in AssetBitmapTextureAtlasSource. AssetPath: " + pAssetPath, e);
        } finally {
            StreamUtils.close(inputStream);
        }
        return new AssetBitmapTextureAtlasSource(pAssetManager, pAssetPath, pTextureX, pTextureY, decodeOptions.outWidth, decodeOptions.outHeight);
    }

    AssetBitmapTextureAtlasSource(AssetManager pAssetManager, String pAssetPath, int pTextureX, int pTextureY, int pTextureWidth, int pTextureHeight) {
        super(pTextureX, pTextureY, pTextureWidth, pTextureHeight);
        this.mAssetManager = pAssetManager;
        this.mAssetPath = pAssetPath;
    }

    public AssetBitmapTextureAtlasSource deepCopy() {
        return new AssetBitmapTextureAtlasSource(this.mAssetManager, this.mAssetPath, this.mTextureX, this.mTextureY, this.mTextureWidth, this.mTextureHeight);
    }

    public Bitmap onLoadBitmap(Config pBitmapConfig) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            Options decodeOptions = new Options();
            decodeOptions.inPreferredConfig = pBitmapConfig;
            in = this.mAssetManager.open(this.mAssetPath);
            bitmap = BitmapFactory.decodeStream(in, null, decodeOptions);
        } catch (Throwable e) {
            Debug.m4591e("Failed loading Bitmap in " + getClass().getSimpleName() + ". AssetPath: " + this.mAssetPath, e);
        } finally {
            StreamUtils.close(in);
        }
        return bitmap;
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.mAssetPath + ")";
    }
}
