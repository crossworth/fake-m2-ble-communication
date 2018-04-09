package org.andengine.opengl.texture;

import java.io.IOException;
import org.andengine.opengl.util.GLState;

public abstract class Texture implements ITexture {
    public static final int HARDWARE_TEXTURE_ID_INVALID = -1;
    protected int mHardwareTextureID = -1;
    protected final PixelFormat mPixelFormat;
    protected final TextureManager mTextureManager;
    protected final TextureOptions mTextureOptions;
    protected ITextureStateListener mTextureStateListener;
    protected boolean mUpdateOnHardwareNeeded = false;

    protected abstract void writeTextureToHardware(GLState gLState) throws IOException;

    public Texture(TextureManager pTextureManager, PixelFormat pPixelFormat, TextureOptions pTextureOptions, ITextureStateListener pTextureStateListener) throws IllegalArgumentException {
        this.mTextureManager = pTextureManager;
        this.mPixelFormat = pPixelFormat;
        this.mTextureOptions = pTextureOptions;
        this.mTextureStateListener = pTextureStateListener;
    }

    public int getHardwareTextureID() {
        return this.mHardwareTextureID;
    }

    public boolean isLoadedToHardware() {
        return this.mHardwareTextureID != -1;
    }

    public void setNotLoadedToHardware() {
        this.mHardwareTextureID = -1;
    }

    public boolean isUpdateOnHardwareNeeded() {
        return this.mUpdateOnHardwareNeeded;
    }

    public void setUpdateOnHardwareNeeded(boolean pUpdateOnHardwareNeeded) {
        this.mUpdateOnHardwareNeeded = pUpdateOnHardwareNeeded;
    }

    public PixelFormat getPixelFormat() {
        return this.mPixelFormat;
    }

    public TextureOptions getTextureOptions() {
        return this.mTextureOptions;
    }

    public ITextureStateListener getTextureStateListener() {
        return this.mTextureStateListener;
    }

    public void setTextureStateListener(ITextureStateListener pTextureStateListener) {
        this.mTextureStateListener = pTextureStateListener;
    }

    public boolean hasTextureStateListener() {
        return this.mTextureStateListener != null;
    }

    public void load() {
        this.mTextureManager.loadTexture(this);
    }

    public void load(GLState pGLState) throws IOException {
        this.mTextureManager.loadTexture(pGLState, this);
    }

    public void unload() {
        this.mTextureManager.unloadTexture(this);
    }

    public void unload(GLState pGLState) {
        this.mTextureManager.unloadTexture(pGLState, this);
    }

    public void loadToHardware(GLState pGLState) throws IOException {
        this.mHardwareTextureID = pGLState.generateTexture();
        pGLState.bindTexture(this.mHardwareTextureID);
        writeTextureToHardware(pGLState);
        this.mTextureOptions.apply();
        this.mUpdateOnHardwareNeeded = false;
        if (this.mTextureStateListener != null) {
            this.mTextureStateListener.onLoadedToHardware(this);
        }
    }

    public void unloadFromHardware(GLState pGLState) {
        pGLState.deleteTexture(this.mHardwareTextureID);
        this.mHardwareTextureID = -1;
        if (this.mTextureStateListener != null) {
            this.mTextureStateListener.onUnloadedFromHardware(this);
        }
    }

    public void reloadToHardware(GLState pGLState) throws IOException {
        unloadFromHardware(pGLState);
        loadToHardware(pGLState);
    }

    public void bind(GLState pGLState) {
        pGLState.bindTexture(this.mHardwareTextureID);
    }

    public void bind(GLState pGLState, int pGLActiveTexture) {
        pGLState.activeTexture(pGLActiveTexture);
        pGLState.bindTexture(this.mHardwareTextureID);
    }
}
