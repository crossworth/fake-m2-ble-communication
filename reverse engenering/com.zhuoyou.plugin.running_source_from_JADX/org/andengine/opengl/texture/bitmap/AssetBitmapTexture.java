package org.andengine.opengl.texture.bitmap;

import android.content.res.AssetManager;
import java.io.IOException;
import org.andengine.opengl.texture.ITextureStateListener;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.adt.io.in.AssetInputStreamOpener;

public class AssetBitmapTexture extends BitmapTexture {
    public AssetBitmapTexture(TextureManager pTextureManager, AssetManager pAssetManager, String pAssetPath) throws IOException {
        super(pTextureManager, new AssetInputStreamOpener(pAssetManager, pAssetPath));
    }

    public AssetBitmapTexture(TextureManager pTextureManager, AssetManager pAssetManager, String pAssetPath, BitmapTextureFormat pBitmapTextureFormat) throws IOException {
        super(pTextureManager, new AssetInputStreamOpener(pAssetManager, pAssetPath), pBitmapTextureFormat);
    }

    public AssetBitmapTexture(TextureManager pTextureManager, AssetManager pAssetManager, String pAssetPath, TextureOptions pTextureOptions) throws IOException {
        super(pTextureManager, new AssetInputStreamOpener(pAssetManager, pAssetPath), pTextureOptions);
    }

    public AssetBitmapTexture(TextureManager pTextureManager, AssetManager pAssetManager, String pAssetPath, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions) throws IOException {
        super(pTextureManager, new AssetInputStreamOpener(pAssetManager, pAssetPath), pBitmapTextureFormat, pTextureOptions);
    }

    public AssetBitmapTexture(TextureManager pTextureManager, AssetManager pAssetManager, String pAssetPath, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, ITextureStateListener pTextureStateListener) throws IOException {
        super(pTextureManager, new AssetInputStreamOpener(pAssetManager, pAssetPath), pBitmapTextureFormat, pTextureOptions, pTextureStateListener);
    }
}
