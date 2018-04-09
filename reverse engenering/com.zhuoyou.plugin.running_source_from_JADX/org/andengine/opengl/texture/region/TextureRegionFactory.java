package org.andengine.opengl.texture.region;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.ITextureAtlas;
import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;

public class TextureRegionFactory {
    public static TextureRegion extractFromTexture(ITexture pTexture) {
        return extractFromTexture(pTexture, false);
    }

    public static TextureRegion extractFromTexture(ITexture pTexture, int pTextureX, int pTextureY, int pWidth, int pHeight) {
        return extractFromTexture(pTexture, pTextureX, pTextureY, pWidth, pHeight, false);
    }

    public static TextureRegion extractFromTexture(ITexture pTexture, boolean pRotated) {
        return new TextureRegion(pTexture, 0.0f, 0.0f, (float) pTexture.getWidth(), (float) pTexture.getHeight(), pRotated);
    }

    public static TextureRegion extractFromTexture(ITexture pTexture, int pTextureX, int pTextureY, int pWidth, int pHeight, boolean pRotated) {
        return new TextureRegion(pTexture, (float) pTextureX, (float) pTextureY, (float) pWidth, (float) pHeight, pRotated);
    }

    public static TiledTextureRegion extractTiledFromTexture(ITexture pTexture, int pTileColumns, int pTileRows) {
        return TiledTextureRegion.create(pTexture, 0, 0, pTexture.getWidth(), pTexture.getHeight(), pTileColumns, pTileRows);
    }

    public static TiledTextureRegion extractTiledFromTexture(ITexture pTexture, int pTextureX, int pTextureY, int pWidth, int pHeight, int pTileColumns, int pTileRows) {
        return TiledTextureRegion.create(pTexture, pTextureX, pTextureY, pWidth, pHeight, pTileColumns, pTileRows);
    }

    public static <T extends ITextureAtlasSource> TextureRegion createFromSource(ITextureAtlas<T> pTextureAtlas, T pTextureAtlasSource, int pTextureX, int pTextureY) {
        return createFromSource(pTextureAtlas, pTextureAtlasSource, pTextureX, pTextureY, false);
    }

    public static <T extends ITextureAtlasSource> TextureRegion createFromSource(ITextureAtlas<T> pTextureAtlas, T pTextureAtlasSource, int pTextureX, int pTextureY, boolean pRotated) {
        TextureRegion textureRegion = new TextureRegion((ITexture) pTextureAtlas, (float) pTextureX, (float) pTextureY, (float) pTextureAtlasSource.getTextureWidth(), (float) pTextureAtlasSource.getTextureHeight(), pRotated);
        pTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, pTextureX, pTextureY);
        return textureRegion;
    }

    public static <T extends ITextureAtlasSource> TiledTextureRegion createTiledFromSource(ITextureAtlas<T> pTextureAtlas, T pTextureAtlasSource, int pTextureX, int pTextureY, int pTileColumns, int pTileRows) {
        return createTiledFromSource(pTextureAtlas, pTextureAtlasSource, pTextureX, pTextureY, pTileColumns, pTileRows, false);
    }

    public static <T extends ITextureAtlasSource> TiledTextureRegion createTiledFromSource(ITextureAtlas<T> pTextureAtlas, T pTextureAtlasSource, int pTextureX, int pTextureY, int pTileColumns, int pTileRows, boolean pRotated) {
        TiledTextureRegion tiledTextureRegion = TiledTextureRegion.create(pTextureAtlas, pTextureX, pTextureY, pTextureAtlasSource.getTextureWidth(), pTextureAtlasSource.getTextureHeight(), pTileColumns, pTileRows, pRotated);
        pTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, pTextureX, pTextureY);
        return tiledTextureRegion;
    }
}
