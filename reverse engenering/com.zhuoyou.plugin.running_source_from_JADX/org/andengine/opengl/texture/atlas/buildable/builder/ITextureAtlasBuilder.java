package org.andengine.opengl.texture.atlas.buildable.builder;

import java.util.ArrayList;
import org.andengine.opengl.texture.atlas.ITextureAtlas;
import org.andengine.opengl.texture.atlas.buildable.BuildableTextureAtlas.TextureAtlasSourceWithWithLocationCallback;
import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;

public interface ITextureAtlasBuilder<T extends ITextureAtlasSource, A extends ITextureAtlas<T>> {

    public static class TextureAtlasBuilderException extends Exception {
        private static final long serialVersionUID = 4700734424214372671L;

        public TextureAtlasBuilderException(String pMessage) {
            super(pMessage);
        }
    }

    void build(A a, ArrayList<TextureAtlasSourceWithWithLocationCallback<T>> arrayList) throws TextureAtlasBuilderException;
}
