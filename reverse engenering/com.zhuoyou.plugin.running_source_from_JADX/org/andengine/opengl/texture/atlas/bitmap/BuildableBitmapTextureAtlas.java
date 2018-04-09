package org.andengine.opengl.texture.atlas.bitmap;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.ITextureAtlas.ITextureAtlasStateListener;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.BuildableTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;

public class BuildableBitmapTextureAtlas extends BuildableTextureAtlas<IBitmapTextureAtlasSource, BitmapTextureAtlas> {
    public BuildableBitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight) {
        this(pTextureManager, pWidth, pHeight, BitmapTextureFormat.RGBA_8888);
    }

    public BuildableBitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, BitmapTextureFormat pBitmapTextureFormat) {
        this(pTextureManager, pWidth, pHeight, pBitmapTextureFormat, TextureOptions.DEFAULT, null);
    }

    public BuildableBitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureStateListener) {
        this(pTextureManager, pWidth, pHeight, BitmapTextureFormat.RGBA_8888, TextureOptions.DEFAULT, pTextureStateListener);
    }

    public BuildableBitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, BitmapTextureFormat pBitmapTextureFormat, ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureStateListener) {
        this(pTextureManager, pWidth, pHeight, pBitmapTextureFormat, TextureOptions.DEFAULT, pTextureStateListener);
    }

    public BuildableBitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, TextureOptions pTextureOptions) throws IllegalArgumentException {
        this(pTextureManager, pWidth, pHeight, BitmapTextureFormat.RGBA_8888, pTextureOptions, null);
    }

    public BuildableBitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions) throws IllegalArgumentException {
        this(pTextureManager, pWidth, pHeight, pBitmapTextureFormat, pTextureOptions, null);
    }

    public BuildableBitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, TextureOptions pTextureOptions, ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureStateListener) throws IllegalArgumentException {
        this(pTextureManager, pWidth, pHeight, BitmapTextureFormat.RGBA_8888, pTextureOptions, pTextureStateListener);
    }

    public BuildableBitmapTextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, ITextureAtlasStateListener<IBitmapTextureAtlasSource> pTextureStateListener) throws IllegalArgumentException {
        super(new BitmapTextureAtlas(pTextureManager, pWidth, pHeight, pBitmapTextureFormat, pTextureOptions, pTextureStateListener));
    }
}
