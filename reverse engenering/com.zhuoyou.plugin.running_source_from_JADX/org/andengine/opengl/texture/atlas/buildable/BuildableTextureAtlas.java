package org.andengine.opengl.texture.atlas.buildable;

import java.io.IOException;
import java.util.ArrayList;
import org.andengine.opengl.texture.ITextureStateListener;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.ITextureAtlas;
import org.andengine.opengl.texture.atlas.ITextureAtlas.ITextureAtlasStateListener;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;
import org.andengine.opengl.util.GLState;
import org.andengine.util.call.Callback;

public class BuildableTextureAtlas<S extends ITextureAtlasSource, T extends ITextureAtlas<S>> implements IBuildableTextureAtlas<S, T> {
    private final T mTextureAtlas;
    private final ArrayList<TextureAtlasSourceWithWithLocationCallback<S>> mTextureAtlasSourcesToPlace = new ArrayList();

    public static class TextureAtlasSourceWithWithLocationCallback<T extends ITextureAtlasSource> {
        private final Callback<T> mCallback;
        private final T mTextureAtlasSource;

        public TextureAtlasSourceWithWithLocationCallback(T pTextureAtlasSource, Callback<T> pCallback) {
            this.mTextureAtlasSource = pTextureAtlasSource;
            this.mCallback = pCallback;
        }

        public T getTextureAtlasSource() {
            return this.mTextureAtlasSource;
        }

        public Callback<T> getCallback() {
            return this.mCallback;
        }
    }

    public BuildableTextureAtlas(T pTextureAtlas) {
        this.mTextureAtlas = pTextureAtlas;
    }

    public int getWidth() {
        return this.mTextureAtlas.getWidth();
    }

    public int getHeight() {
        return this.mTextureAtlas.getHeight();
    }

    public int getHardwareTextureID() {
        return this.mTextureAtlas.getHardwareTextureID();
    }

    public boolean isLoadedToHardware() {
        return this.mTextureAtlas.isLoadedToHardware();
    }

    public void setNotLoadedToHardware() {
        this.mTextureAtlas.setNotLoadedToHardware();
    }

    public boolean isUpdateOnHardwareNeeded() {
        return this.mTextureAtlas.isUpdateOnHardwareNeeded();
    }

    public void setUpdateOnHardwareNeeded(boolean pUpdateOnHardwareNeeded) {
        this.mTextureAtlas.setUpdateOnHardwareNeeded(pUpdateOnHardwareNeeded);
    }

    public void load() {
        this.mTextureAtlas.load();
    }

    public void load(GLState pGLState) throws IOException {
        this.mTextureAtlas.load(pGLState);
    }

    public void unload() {
        this.mTextureAtlas.unload();
    }

    public void unload(GLState pGLState) {
        this.mTextureAtlas.unload(pGLState);
    }

    public void loadToHardware(GLState pGLState) throws IOException {
        this.mTextureAtlas.loadToHardware(pGLState);
    }

    public void unloadFromHardware(GLState pGLState) {
        this.mTextureAtlas.unloadFromHardware(pGLState);
    }

    public void reloadToHardware(GLState pGLState) throws IOException {
        this.mTextureAtlas.reloadToHardware(pGLState);
    }

    public void bind(GLState pGLState) {
        this.mTextureAtlas.bind(pGLState);
    }

    public void bind(GLState pGLState, int pGLActiveTexture) {
        this.mTextureAtlas.bind(pGLState, pGLActiveTexture);
    }

    public PixelFormat getPixelFormat() {
        return this.mTextureAtlas.getPixelFormat();
    }

    public TextureOptions getTextureOptions() {
        return this.mTextureAtlas.getTextureOptions();
    }

    @Deprecated
    public void addTextureAtlasSource(S pTextureAtlasSource, int pTextureX, int pTextureY) {
        this.mTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, pTextureX, pTextureY);
    }

    @Deprecated
    public void addTextureAtlasSource(S pTextureAtlasSource, int pTextureX, int pTextureY, int pTextureAtlasSourcePadding) {
        this.mTextureAtlas.addTextureAtlasSource(pTextureAtlasSource, pTextureX, pTextureY, pTextureAtlasSourcePadding);
    }

    public void removeTextureAtlasSource(S pTextureAtlasSource, int pTextureX, int pTextureY) {
        this.mTextureAtlas.removeTextureAtlasSource(pTextureAtlasSource, pTextureX, pTextureY);
    }

    public void clearTextureAtlasSources() {
        this.mTextureAtlas.clearTextureAtlasSources();
        this.mTextureAtlasSourcesToPlace.clear();
    }

    @Deprecated
    public boolean hasTextureStateListener() {
        return this.mTextureAtlas.hasTextureStateListener();
    }

    public boolean hasTextureAtlasStateListener() {
        return this.mTextureAtlas.hasTextureAtlasStateListener();
    }

    @Deprecated
    public ITextureAtlasStateListener<S> getTextureStateListener() {
        return this.mTextureAtlas.getTextureStateListener();
    }

    public ITextureAtlasStateListener<S> getTextureAtlasStateListener() {
        return this.mTextureAtlas.getTextureAtlasStateListener();
    }

    @Deprecated
    public void setTextureStateListener(ITextureStateListener pTextureStateListener) {
        this.mTextureAtlas.setTextureStateListener(pTextureStateListener);
    }

    public void setTextureAtlasStateListener(ITextureAtlasStateListener<S> pTextureAtlasStateListener) {
        this.mTextureAtlas.setTextureAtlasStateListener(pTextureAtlasStateListener);
    }

    public void addEmptyTextureAtlasSource(int pTextureX, int pTextureY, int pWidth, int pHeight) {
        this.mTextureAtlas.addEmptyTextureAtlasSource(pTextureX, pTextureY, pWidth, pHeight);
    }

    public void addTextureAtlasSource(S pTextureAtlasSource, Callback<S> pCallback) {
        this.mTextureAtlasSourcesToPlace.add(new TextureAtlasSourceWithWithLocationCallback(pTextureAtlasSource, pCallback));
    }

    public void removeTextureAtlasSource(ITextureAtlasSource pTextureAtlasSource) {
        ArrayList<TextureAtlasSourceWithWithLocationCallback<S>> textureSources = this.mTextureAtlasSourcesToPlace;
        for (int i = textureSources.size() - 1; i >= 0; i--) {
            if (((TextureAtlasSourceWithWithLocationCallback) textureSources.get(i)).mTextureAtlasSource == pTextureAtlasSource) {
                textureSources.remove(i);
                this.mTextureAtlas.setUpdateOnHardwareNeeded(true);
                return;
            }
        }
    }

    public IBuildableTextureAtlas<S, T> build(ITextureAtlasBuilder<S, T> pTextureAtlasBuilder) throws TextureAtlasBuilderException {
        pTextureAtlasBuilder.build(this.mTextureAtlas, this.mTextureAtlasSourcesToPlace);
        this.mTextureAtlasSourcesToPlace.clear();
        this.mTextureAtlas.setUpdateOnHardwareNeeded(true);
        return this;
    }
}
