package org.andengine.opengl.texture.atlas;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.ITextureStateListener;
import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;
import org.andengine.util.debug.Debug;

public interface ITextureAtlas<T extends ITextureAtlasSource> extends ITexture {

    public interface ITextureAtlasStateListener<T extends ITextureAtlasSource> extends ITextureStateListener {

        public static class DebugTextureAtlasStateListener<T extends ITextureAtlasSource> implements ITextureAtlasStateListener<T> {
            public void onLoadedToHardware(ITexture pTexture) {
            }

            public void onTextureAtlasSourceLoaded(ITextureAtlas<T> pTextureAtlas, T pTextureAtlasSource) {
                Debug.m4588e("Loaded TextureAtlasSource. TextureAtlas: " + pTextureAtlas.toString() + " TextureAtlasSource: " + pTextureAtlasSource.toString());
            }

            public void onTextureAtlasSourceLoadExeption(ITextureAtlas<T> pTextureAtlas, T pTextureAtlasSource, Throwable pThrowable) {
                Debug.m4591e("Exception loading TextureAtlasSource. TextureAtlas: " + pTextureAtlas.toString() + " TextureAtlasSource: " + pTextureAtlasSource.toString(), pThrowable);
            }

            public void onUnloadedFromHardware(ITexture pTexture) {
            }
        }

        public static class TextureAtlasStateAdapter<T extends ITextureAtlasSource> implements ITextureAtlasStateListener<T> {
            public void onLoadedToHardware(ITexture pTexture) {
            }

            public void onTextureAtlasSourceLoaded(ITextureAtlas<T> iTextureAtlas, T t) {
            }

            public void onTextureAtlasSourceLoadExeption(ITextureAtlas<T> iTextureAtlas, T t, Throwable pThrowable) {
            }

            public void onUnloadedFromHardware(ITexture pTexture) {
            }
        }

        void onTextureAtlasSourceLoadExeption(ITextureAtlas<T> iTextureAtlas, T t, Throwable th);

        void onTextureAtlasSourceLoaded(ITextureAtlas<T> iTextureAtlas, T t);
    }

    void addEmptyTextureAtlasSource(int i, int i2, int i3, int i4);

    void addTextureAtlasSource(T t, int i, int i2) throws IllegalArgumentException;

    void addTextureAtlasSource(T t, int i, int i2, int i3) throws IllegalArgumentException;

    void clearTextureAtlasSources();

    ITextureAtlasStateListener<T> getTextureAtlasStateListener();

    @Deprecated
    ITextureAtlasStateListener<T> getTextureStateListener();

    boolean hasTextureAtlasStateListener();

    @Deprecated
    boolean hasTextureStateListener();

    void removeTextureAtlasSource(T t, int i, int i2);

    void setTextureAtlasStateListener(ITextureAtlasStateListener<T> iTextureAtlasStateListener);

    @Deprecated
    void setTextureStateListener(ITextureStateListener iTextureStateListener);
}
