package org.andengine.opengl.texture.bitmap;

import android.graphics.Bitmap.Config;
import org.andengine.opengl.texture.PixelFormat;

public enum BitmapTextureFormat {
    RGBA_8888(Config.ARGB_8888, PixelFormat.RGBA_8888),
    RGB_565(Config.RGB_565, PixelFormat.RGB_565),
    RGBA_4444(Config.ARGB_4444, PixelFormat.RGBA_4444),
    A_8(Config.ALPHA_8, PixelFormat.A_8);
    
    private final Config mBitmapConfig;
    private final PixelFormat mPixelFormat;

    private BitmapTextureFormat(Config pBitmapConfig, PixelFormat pPixelFormat) {
        this.mBitmapConfig = pBitmapConfig;
        this.mPixelFormat = pPixelFormat;
    }

    public static BitmapTextureFormat fromPixelFormat(PixelFormat pPixelFormat) {
        switch (pPixelFormat) {
            case RGBA_8888:
                return RGBA_8888;
            case RGBA_4444:
                return RGBA_4444;
            case RGB_565:
                return RGB_565;
            case A_8:
                return A_8;
            default:
                throw new IllegalArgumentException("Unsupported " + PixelFormat.class.getName() + ": '" + pPixelFormat + "'.");
        }
    }

    public Config getBitmapConfig() {
        return this.mBitmapConfig;
    }

    public PixelFormat getPixelFormat() {
        return this.mPixelFormat;
    }
}
