package org.andengine.opengl.texture;

public enum PixelFormat {
    UNDEFINED(-1, -1, -1, -1),
    RGBA_4444(6408, 6408, 32819, 16),
    RGBA_5551(6407, 6408, 32820, 16),
    RGBA_8888(6408, 6408, 5121, 32),
    RGB_565(6407, 6407, 33635, 16),
    A_8(6406, 6406, 5121, 8),
    I_8(6409, 6409, 5121, 8),
    AI_88(6410, 6410, 5121, 16);
    
    private final int mBitsPerPixel;
    private final int mGLFormat;
    private final int mGLInternalFormat;
    private final int mGLType;

    private PixelFormat(int pGLInternalFormat, int pGLFormat, int pGLType, int pBitsPerPixel) {
        this.mGLInternalFormat = pGLInternalFormat;
        this.mGLFormat = pGLFormat;
        this.mGLType = pGLType;
        this.mBitsPerPixel = pBitsPerPixel;
    }

    public int getGLInternalFormat() {
        return this.mGLInternalFormat;
    }

    public int getGLFormat() {
        return this.mGLFormat;
    }

    public int getGLType() {
        return this.mGLType;
    }

    public int getBitsPerPixel() {
        return this.mBitsPerPixel;
    }
}
