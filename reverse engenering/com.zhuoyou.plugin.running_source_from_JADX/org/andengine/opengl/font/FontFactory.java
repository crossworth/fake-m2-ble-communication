package org.andengine.opengl.font;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.util.color.Color;

public class FontFactory {
    private static final boolean ANTIALIAS_DEFAULT = true;
    private static final int COLOR_DEFAULT = Color.BLACK_ARGB_PACKED_INT;
    private static String sAssetBasePath = "";

    public static void setAssetBasePath(String pAssetBasePath) {
        if (pAssetBasePath.endsWith("/") || pAssetBasePath.length() == 0) {
            sAssetBasePath = pAssetBasePath;
            return;
        }
        throw new IllegalStateException("pAssetBasePath must end with '/' or be lenght zero.");
    }

    public static String getAssetBasePath() {
        return sAssetBasePath;
    }

    public static void onCreate() {
        setAssetBasePath("");
    }

    public static Font create(FontManager pFontManager, ITexture pTexture, float pSize) {
        return create(pFontManager, pTexture, pSize, true, COLOR_DEFAULT);
    }

    public static Font create(FontManager pFontManager, ITexture pTexture, float pSize, boolean pAntiAlias) {
        return create(pFontManager, pTexture, pSize, pAntiAlias, COLOR_DEFAULT);
    }

    public static Font create(FontManager pFontManager, ITexture pTexture, float pSize, int pColor) {
        return create(pFontManager, pTexture, pSize, true, pColor);
    }

    public static Font create(FontManager pFontManager, ITexture pTexture, float pSize, boolean pAntiAlias, int pColor) {
        return create(pFontManager, pTexture, Typeface.create(Typeface.DEFAULT, 0), pSize, pAntiAlias, pColor);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, float pSize, boolean pAntiAlias, int pColor) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, TextureOptions.DEFAULT, pSize, pAntiAlias, pColor);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, TextureOptions pTextureOptions, float pSize, boolean pAntiAlias, int pColor) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, pTextureOptions, Typeface.create(Typeface.DEFAULT, 0), pSize, pAntiAlias, pColor);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, Typeface pTypeface, float pSize) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, TextureOptions.DEFAULT, pTypeface, pSize, true, COLOR_DEFAULT);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, Typeface pTypeface, float pSize, boolean pAntiAlias) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, TextureOptions.DEFAULT, pTypeface, pSize, pAntiAlias, COLOR_DEFAULT);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, Typeface pTypeface, float pSize, int pColor) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, TextureOptions.DEFAULT, pTypeface, pSize, true, pColor);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, TextureOptions.DEFAULT, pTypeface, pSize, pAntiAlias, pColor);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, TextureOptions pTextureOptions, Typeface pTypeface, float pSize) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, pTextureOptions, pTypeface, pSize, true, COLOR_DEFAULT);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, TextureOptions pTextureOptions, Typeface pTypeface, float pSize, boolean pAntiAlias) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, pTextureOptions, pTypeface, pSize, pAntiAlias, COLOR_DEFAULT);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, TextureOptions pTextureOptions, Typeface pTypeface, float pSize, int pColor) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, pTextureOptions, pTypeface, pSize, true, pColor);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, TextureOptions pTextureOptions, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, BitmapTextureFormat.RGBA_8888, pTextureOptions, pTypeface, pSize, pAntiAlias, pColor);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, Typeface pTypeface, float pSize) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, pBitmapTextureFormat, pTextureOptions, pTypeface, pSize, true, COLOR_DEFAULT);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, Typeface pTypeface, float pSize, boolean pAntiAlias) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, pBitmapTextureFormat, pTextureOptions, pTypeface, pSize, pAntiAlias, COLOR_DEFAULT);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, Typeface pTypeface, float pSize, int pColor) {
        return create(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, pBitmapTextureFormat, pTextureOptions, pTypeface, pSize, true, pColor);
    }

    public static Font create(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor) {
        return create(pFontManager, (ITexture) new BitmapTextureAtlas(pTextureManager, pTextureWidth, pTextureHeight, pBitmapTextureFormat, pTextureOptions), pTypeface, pSize, pAntiAlias, pColor);
    }

    public static Font create(FontManager pFontManager, ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor) {
        return new Font(pFontManager, pTexture, pTypeface, pSize, pAntiAlias, pColor);
    }

    public static Font createFromAsset(FontManager pFontManager, ITexture pTexture, AssetManager pAssetManager, String pAssetPath, float pSize, boolean pAntiAlias, int pColor) {
        return new Font(pFontManager, pTexture, Typeface.createFromAsset(pAssetManager, sAssetBasePath + pAssetPath), pSize, pAntiAlias, pColor);
    }

    public static Font createFromAsset(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, AssetManager pAssetManager, String pAssetPath, float pSize, boolean pAntiAlias, int pColor) {
        return createFromAsset(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, TextureOptions.DEFAULT, pAssetManager, pAssetPath, pSize, pAntiAlias, pColor);
    }

    public static Font createFromAsset(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, TextureOptions pTextureOptions, AssetManager pAssetManager, String pAssetPath, float pSize, boolean pAntiAlias, int pColor) {
        return createFromAsset(pFontManager, pTextureManager, pTextureWidth, pTextureHeight, BitmapTextureFormat.RGBA_8888, pTextureOptions, pAssetManager, pAssetPath, pSize, pAntiAlias, pColor);
    }

    public static Font createFromAsset(FontManager pFontManager, TextureManager pTextureManager, int pTextureWidth, int pTextureHeight, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, AssetManager pAssetManager, String pAssetPath, float pSize, boolean pAntiAlias, int pColor) {
        AssetManager assetManager = pAssetManager;
        return new Font(pFontManager, new BitmapTextureAtlas(pTextureManager, pTextureWidth, pTextureHeight, pBitmapTextureFormat, pTextureOptions), Typeface.createFromAsset(assetManager, sAssetBasePath + pAssetPath), pSize, pAntiAlias, pColor);
    }

    public static StrokeFont createStroke(FontManager pFontManager, ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor, float pStrokeWidth, int pStrokeColor) {
        return new StrokeFont(pFontManager, pTexture, pTypeface, pSize, pAntiAlias, pColor, pStrokeWidth, pStrokeColor);
    }

    public static StrokeFont createStroke(FontManager pFontManager, ITexture pTexture, Typeface pTypeface, float pSize, boolean pAntiAlias, int pColor, float pStrokeWidth, int pStrokeColor, boolean pStrokeOnly) {
        return new StrokeFont(pFontManager, pTexture, pTypeface, pSize, pAntiAlias, pColor, pStrokeWidth, pStrokeColor, pStrokeOnly);
    }

    public static StrokeFont createStrokeFromAsset(FontManager pFontManager, ITexture pTexture, AssetManager pAssetManager, String pAssetPath, float pSize, boolean pAntiAlias, int pColor, float pStrokeWidth, int pStrokeColor) {
        return new StrokeFont(pFontManager, pTexture, Typeface.createFromAsset(pAssetManager, sAssetBasePath + pAssetPath), pSize, pAntiAlias, pColor, pStrokeWidth, pStrokeColor);
    }

    public static StrokeFont createStrokeFromAsset(FontManager pFontManager, ITexture pTexture, AssetManager pAssetManager, String pAssetPath, float pSize, boolean pAntiAlias, int pColor, float pStrokeWidth, int pStrokeColor, boolean pStrokeOnly) {
        return new StrokeFont(pFontManager, pTexture, Typeface.createFromAsset(pAssetManager, sAssetBasePath + pAssetPath), pSize, pAntiAlias, pColor, pStrokeWidth, pStrokeColor, pStrokeOnly);
    }
}
