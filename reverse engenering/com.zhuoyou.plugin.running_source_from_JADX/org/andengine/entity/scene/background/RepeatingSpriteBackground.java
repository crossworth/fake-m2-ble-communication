package org.andengine.entity.scene.background;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class RepeatingSpriteBackground extends SpriteBackground {
    private BitmapTextureAtlas mBitmapTextureAtlas;
    private final float mScale;

    public RepeatingSpriteBackground(float pCameraWidth, float pCameraHeight, TextureManager pTextureManager, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, VertexBufferObjectManager pVertexBufferObjectManager) throws IllegalArgumentException {
        this(pCameraWidth, pCameraHeight, pTextureManager, pBitmapTextureAtlasSource, 1.0f, pVertexBufferObjectManager);
    }

    public RepeatingSpriteBackground(float pCameraWidth, float pCameraHeight, TextureManager pTextureManager, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, float pScale, VertexBufferObjectManager pVertexBufferObjectManager) throws IllegalArgumentException {
        super(null);
        this.mScale = pScale;
        this.mEntity = loadSprite(pCameraWidth, pCameraHeight, pTextureManager, pBitmapTextureAtlasSource, pVertexBufferObjectManager);
    }

    public BitmapTextureAtlas getBitmapTextureAtlas() {
        return this.mBitmapTextureAtlas;
    }

    private Sprite loadSprite(float pCameraWidth, float pCameraHeight, TextureManager pTextureManager, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, VertexBufferObjectManager pVertexBufferObjectManager) throws IllegalArgumentException {
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(pTextureManager, pBitmapTextureAtlasSource.getTextureWidth(), pBitmapTextureAtlasSource.getTextureHeight(), BitmapTextureFormat.RGBA_8888, TextureOptions.REPEATING_NEAREST);
        ITextureRegion textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource(this.mBitmapTextureAtlas, pBitmapTextureAtlasSource, 0, 0);
        int width = Math.round(pCameraWidth / this.mScale);
        int height = Math.round(pCameraHeight / this.mScale);
        textureRegion.setTextureWidth((float) width);
        textureRegion.setTextureHeight((float) height);
        this.mBitmapTextureAtlas.load();
        Sprite sprite = new Sprite(0.0f, 0.0f, (float) width, (float) height, textureRegion, pVertexBufferObjectManager);
        sprite.setScaleCenter(0.0f, 0.0f);
        sprite.setScale(this.mScale);
        return sprite;
    }
}
