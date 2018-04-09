package org.andengine.opengl.texture.atlas.buildable;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.ITextureAtlas;
import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.call.Callback;

public class BuildableTextureAtlasTextureRegionFactory {
    public static <T extends ITextureAtlasSource, A extends ITextureAtlas<T>> TextureRegion createFromSource(BuildableTextureAtlas<T, A> pBuildableTextureAtlas, T pTextureAtlasSource, boolean pRotated) {
        final TextureRegion textureRegion = new TextureRegion((ITexture) pBuildableTextureAtlas, 0.0f, 0.0f, (float) pTextureAtlasSource.getTextureWidth(), (float) pTextureAtlasSource.getTextureHeight(), pRotated);
        pBuildableTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, new Callback<T>() {
            public void onCallback(T pCallbackValue) {
                textureRegion.setTexturePosition((float) pCallbackValue.getTextureX(), (float) pCallbackValue.getTextureY());
            }
        });
        return textureRegion;
    }

    public static <T extends ITextureAtlasSource, A extends ITextureAtlas<T>> TiledTextureRegion createTiledFromSource(BuildableTextureAtlas<T, A> pBuildableTextureAtlas, final T pTextureAtlasSource, final int pTileColumns, final int pTileRows) {
        final TiledTextureRegion tiledTextureRegion = TiledTextureRegion.create(pBuildableTextureAtlas, 0, 0, pTextureAtlasSource.getTextureWidth(), pTextureAtlasSource.getTextureHeight(), pTileColumns, pTileRows);
        pBuildableTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, new Callback<T>() {
            public void onCallback(T pCallbackValue) {
                int tileWidth = pTextureAtlasSource.getTextureWidth() / pTileColumns;
                int tileHeight = pTextureAtlasSource.getTextureHeight() / pTileRows;
                for (int tileColumn = 0; tileColumn < pTileColumns; tileColumn++) {
                    for (int tileRow = 0; tileRow < pTileRows; tileRow++) {
                        tiledTextureRegion.setTexturePosition((pTileColumns * tileRow) + tileColumn, (float) (pCallbackValue.getTextureX() + (tileColumn * tileWidth)), (float) (pCallbackValue.getTextureY() + (tileRow * tileHeight)));
                    }
                }
            }
        });
        return tiledTextureRegion;
    }
}
