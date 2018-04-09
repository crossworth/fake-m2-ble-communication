package org.andengine.opengl.texture.atlas.bitmap;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import java.io.IOException;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.ResourceBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.BuildableTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.exception.AndEngineRuntimeException;

public class BitmapTextureAtlasTextureRegionFactory {
    private static String sAssetBasePath = "";

    public static void setAssetBasePath(String pAssetBasePath) {
        if (pAssetBasePath.endsWith("/") || pAssetBasePath.length() == 0) {
            sAssetBasePath = pAssetBasePath;
            return;
        }
        throw new IllegalArgumentException("pAssetBasePath must end with '/' or be lenght zero.");
    }

    public static String getAssetBasePath() {
        return sAssetBasePath;
    }

    public static void reset() {
        setAssetBasePath("");
    }

    public static TextureRegion createFromAsset(BitmapTextureAtlas pBitmapTextureAtlas, Context pContext, String pAssetPath, int pTextureX, int pTextureY) {
        return createFromAsset(pBitmapTextureAtlas, pContext.getAssets(), pAssetPath, pTextureX, pTextureY);
    }

    public static TextureRegion createFromAsset(BitmapTextureAtlas pBitmapTextureAtlas, AssetManager pAssetManager, String pAssetPath, int pTextureX, int pTextureY) {
        return createFromSource(pBitmapTextureAtlas, AssetBitmapTextureAtlasSource.create(pAssetManager, sAssetBasePath + pAssetPath), pTextureX, pTextureY);
    }

    public static TiledTextureRegion createTiledFromAsset(BitmapTextureAtlas pBitmapTextureAtlas, Context pContext, String pAssetPath, int pTextureX, int pTextureY, int pTileColumns, int pTileRows) {
        return createTiledFromAsset(pBitmapTextureAtlas, pContext.getAssets(), pAssetPath, pTextureX, pTextureY, pTileColumns, pTileRows);
    }

    public static TiledTextureRegion createTiledFromAsset(BitmapTextureAtlas pBitmapTextureAtlas, AssetManager pAssetManager, String pAssetPath, int pTextureX, int pTextureY, int pTileColumns, int pTileRows) {
        return createTiledFromSource(pBitmapTextureAtlas, AssetBitmapTextureAtlasSource.create(pAssetManager, sAssetBasePath + pAssetPath), pTextureX, pTextureY, pTileColumns, pTileRows);
    }

    public static TextureRegion createFromResource(BitmapTextureAtlas pBitmapTextureAtlas, Context pContext, int pDrawableResourceID, int pTextureX, int pTextureY) {
        return createFromResource(pBitmapTextureAtlas, pContext.getResources(), pDrawableResourceID, pTextureX, pTextureY);
    }

    public static TextureRegion createFromResource(BitmapTextureAtlas pBitmapTextureAtlas, Resources pResources, int pDrawableResourceID, int pTextureX, int pTextureY) {
        return createFromSource(pBitmapTextureAtlas, ResourceBitmapTextureAtlasSource.create(pResources, pDrawableResourceID), pTextureX, pTextureY);
    }

    public static TiledTextureRegion createTiledFromResource(BitmapTextureAtlas pBitmapTextureAtlas, Context pContext, int pDrawableResourceID, int pTextureX, int pTextureY, int pTileColumns, int pTileRows) {
        return createTiledFromResource(pBitmapTextureAtlas, pContext.getResources(), pDrawableResourceID, pTextureX, pTextureY, pTileColumns, pTileRows);
    }

    public static TiledTextureRegion createTiledFromResource(BitmapTextureAtlas pBitmapTextureAtlas, Resources pResources, int pDrawableResourceID, int pTextureX, int pTextureY, int pTileColumns, int pTileRows) {
        return createTiledFromSource(pBitmapTextureAtlas, ResourceBitmapTextureAtlasSource.create(pResources, pDrawableResourceID), pTextureX, pTextureY, pTileColumns, pTileRows);
    }

    public static TextureRegion createFromSource(BitmapTextureAtlas pBitmapTextureAtlas, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, int pTextureX, int pTextureY) {
        return TextureRegionFactory.createFromSource(pBitmapTextureAtlas, pBitmapTextureAtlasSource, pTextureX, pTextureY);
    }

    public static TiledTextureRegion createTiledFromSource(BitmapTextureAtlas pBitmapTextureAtlas, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, int pTextureX, int pTextureY, int pTileColumns, int pTileRows) {
        return TextureRegionFactory.createTiledFromSource(pBitmapTextureAtlas, pBitmapTextureAtlasSource, pTextureX, pTextureY, pTileColumns, pTileRows);
    }

    public static TextureRegion createFromAsset(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Context pContext, String pAssetPath) {
        return createFromAsset(pBuildableBitmapTextureAtlas, pContext.getAssets(), pAssetPath);
    }

    public static TextureRegion createFromAsset(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, AssetManager pAssetManager, String pAssetPath) {
        return createFromAsset(pBuildableBitmapTextureAtlas, pAssetManager, pAssetPath, false);
    }

    public static TextureRegion createFromAsset(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Context pContext, String pAssetPath, boolean pRotated) {
        return createFromAsset(pBuildableBitmapTextureAtlas, pContext.getAssets(), pAssetPath, pRotated);
    }

    public static TextureRegion createFromAsset(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, AssetManager pAssetManager, String pAssetPath, boolean pRotated) {
        return createFromSource(pBuildableBitmapTextureAtlas, AssetBitmapTextureAtlasSource.create(pAssetManager, sAssetBasePath + pAssetPath), pRotated);
    }

    public static TiledTextureRegion createTiledFromAsset(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Context pContext, String pAssetPath, int pTileColumns, int pTileRows) {
        return createTiledFromAsset(pBuildableBitmapTextureAtlas, pContext.getAssets(), pAssetPath, pTileColumns, pTileRows);
    }

    public static TiledTextureRegion createTiledFromAsset(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, AssetManager pAssetManager, String pAssetPath, int pTileColumns, int pTileRows) {
        return createTiledFromSource(pBuildableBitmapTextureAtlas, AssetBitmapTextureAtlasSource.create(pAssetManager, sAssetBasePath + pAssetPath), pTileColumns, pTileRows);
    }

    public static TextureRegion createFromResource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Context pContext, int pDrawableResourceID) {
        return createFromResource(pBuildableBitmapTextureAtlas, pContext.getResources(), pDrawableResourceID);
    }

    public static TextureRegion createFromResource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Resources pResources, int pDrawableResourceID) {
        return createFromResource(pBuildableBitmapTextureAtlas, pResources, pDrawableResourceID, false);
    }

    public static TextureRegion createFromResource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Context pContext, int pDrawableResourceID, boolean pRotated) {
        return createFromResource(pBuildableBitmapTextureAtlas, pContext.getResources(), pDrawableResourceID, pRotated);
    }

    public static TextureRegion createFromResource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Resources pResources, int pDrawableResourceID, boolean pRotated) {
        return createFromSource(pBuildableBitmapTextureAtlas, ResourceBitmapTextureAtlasSource.create(pResources, pDrawableResourceID), pRotated);
    }

    public static TiledTextureRegion createTiledFromResource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Context pContext, int pDrawableResourceID, int pTileColumns, int pTileRows) {
        return createTiledFromResource(pBuildableBitmapTextureAtlas, pContext.getResources(), pDrawableResourceID, pTileColumns, pTileRows);
    }

    public static TiledTextureRegion createTiledFromResource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, Resources pResources, int pDrawableResourceID, int pTileColumns, int pTileRows) {
        return createTiledFromSource(pBuildableBitmapTextureAtlas, ResourceBitmapTextureAtlasSource.create(pResources, pDrawableResourceID), pTileColumns, pTileRows);
    }

    public static TextureRegion createFromSource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, IBitmapTextureAtlasSource pBitmapTextureAtlasSource) {
        return createFromSource(pBuildableBitmapTextureAtlas, pBitmapTextureAtlasSource, false);
    }

    public static TextureRegion createFromSource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, boolean pRotated) {
        return BuildableTextureAtlasTextureRegionFactory.createFromSource(pBuildableBitmapTextureAtlas, pBitmapTextureAtlasSource, pRotated);
    }

    public static TiledTextureRegion createTiledFromSource(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, IBitmapTextureAtlasSource pBitmapTextureAtlasSource, int pTileColumns, int pTileRows) {
        return BuildableTextureAtlasTextureRegionFactory.createTiledFromSource(pBuildableBitmapTextureAtlas, pBitmapTextureAtlasSource, pTileColumns, pTileRows);
    }

    public static TiledTextureRegion createTiledFromAssetDirectory(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, AssetManager pAssetManager, String pAssetSubdirectory) {
        try {
            String[] files = pAssetManager.list(sAssetBasePath + pAssetSubdirectory);
            int fileCount = files.length;
            TextureRegion[] textures = new TextureRegion[fileCount];
            for (int i = 0; i < fileCount; i++) {
                textures[i] = createFromAsset(pBuildableBitmapTextureAtlas, pAssetManager, pAssetSubdirectory + "/" + files[i]);
            }
            return new TiledTextureRegion(pBuildableBitmapTextureAtlas, textures);
        } catch (IOException e) {
            throw new AndEngineRuntimeException("Listing assets subdirectory: '" + sAssetBasePath + pAssetSubdirectory + "' failed. Does it exist?", e);
        }
    }
}
