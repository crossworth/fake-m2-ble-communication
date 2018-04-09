package org.andengine.opengl.texture.atlas.buildable;

import org.andengine.opengl.texture.atlas.ITextureAtlas;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;
import org.andengine.util.call.Callback;

public interface IBuildableTextureAtlas<S extends ITextureAtlasSource, T extends ITextureAtlas<S>> extends ITextureAtlas<S> {
    @Deprecated
    void addTextureAtlasSource(S s, int i, int i2);

    @Deprecated
    void addTextureAtlasSource(S s, int i, int i2, int i3);

    void addTextureAtlasSource(S s, Callback<S> callback);

    IBuildableTextureAtlas<S, T> build(ITextureAtlasBuilder<S, T> iTextureAtlasBuilder) throws TextureAtlasBuilderException;

    void removeTextureAtlasSource(ITextureAtlasSource iTextureAtlasSource);
}
