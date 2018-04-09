package org.andengine.opengl.texture.atlas;

import java.util.ArrayList;
import org.andengine.opengl.texture.ITextureStateListener;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.ITextureAtlas.ITextureAtlasStateListener;
import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;

public abstract class TextureAtlas<T extends ITextureAtlasSource> extends Texture implements ITextureAtlas<T> {
    protected final int mHeight;
    protected final ArrayList<T> mTextureAtlasSources = new ArrayList();
    protected final int mWidth;

    public TextureAtlas(TextureManager pTextureManager, int pWidth, int pHeight, PixelFormat pPixelFormat, TextureOptions pTextureOptions, ITextureAtlasStateListener<T> pTextureAtlasStateListener) {
        super(pTextureManager, pPixelFormat, pTextureOptions, pTextureAtlasStateListener);
        this.mWidth = pWidth;
        this.mHeight = pHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    @Deprecated
    public boolean hasTextureStateListener() {
        return super.hasTextureStateListener();
    }

    public boolean hasTextureAtlasStateListener() {
        return super.hasTextureStateListener();
    }

    @Deprecated
    public ITextureAtlasStateListener<T> getTextureStateListener() {
        return getTextureAtlasStateListener();
    }

    public ITextureAtlasStateListener<T> getTextureAtlasStateListener() {
        return (ITextureAtlasStateListener) super.getTextureStateListener();
    }

    @Deprecated
    public void setTextureStateListener(ITextureStateListener pTextureStateListener) {
        super.setTextureStateListener(pTextureStateListener);
    }

    public void setTextureAtlasStateListener(ITextureAtlasStateListener<T> pTextureAtlasStateListener) {
        super.setTextureStateListener(pTextureAtlasStateListener);
    }

    public void addTextureAtlasSource(T pTextureAtlasSource, int pTextureX, int pTextureY) throws IllegalArgumentException {
        checkTextureAtlasSourcePosition(pTextureAtlasSource, pTextureX, pTextureY);
        pTextureAtlasSource.setTextureX(pTextureX);
        pTextureAtlasSource.setTextureY(pTextureY);
        this.mTextureAtlasSources.add(pTextureAtlasSource);
        this.mUpdateOnHardwareNeeded = true;
    }

    public void addTextureAtlasSource(T pTextureAtlasSource, int pTextureX, int pTextureY, int pTextureAtlasSourcePadding) throws IllegalArgumentException {
        addTextureAtlasSource(pTextureAtlasSource, pTextureX, pTextureY);
        if (pTextureAtlasSourcePadding > 0) {
            if (pTextureX >= pTextureAtlasSourcePadding) {
                addEmptyTextureAtlasSource(pTextureX - pTextureAtlasSourcePadding, pTextureY, pTextureAtlasSourcePadding, pTextureAtlasSource.getTextureHeight());
            }
            if (pTextureY >= pTextureAtlasSourcePadding) {
                addEmptyTextureAtlasSource(pTextureX, pTextureY - pTextureAtlasSourcePadding, pTextureAtlasSource.getTextureWidth(), pTextureAtlasSourcePadding);
            }
            if (((pTextureAtlasSource.getTextureWidth() + pTextureX) - 1) + pTextureAtlasSourcePadding <= getWidth()) {
                addEmptyTextureAtlasSource(pTextureAtlasSource.getTextureWidth() + pTextureX, pTextureY, pTextureAtlasSourcePadding, pTextureAtlasSource.getTextureHeight());
            }
            if (((pTextureAtlasSource.getTextureHeight() + pTextureY) - 1) + pTextureAtlasSourcePadding <= getHeight()) {
                addEmptyTextureAtlasSource(pTextureX, pTextureAtlasSource.getTextureHeight() + pTextureY, pTextureAtlasSource.getTextureWidth(), pTextureAtlasSourcePadding);
            }
        }
    }

    public void removeTextureAtlasSource(T pTextureAtlasSource, int pTextureX, int pTextureY) {
        ArrayList<T> textureSources = this.mTextureAtlasSources;
        for (int i = textureSources.size() - 1; i >= 0; i--) {
            T textureSource = (ITextureAtlasSource) textureSources.get(i);
            if (textureSource == pTextureAtlasSource && textureSource.getTextureX() == pTextureX && textureSource.getTextureY() == pTextureY) {
                textureSources.remove(i);
                this.mUpdateOnHardwareNeeded = true;
                return;
            }
        }
    }

    public void clearTextureAtlasSources() {
        this.mTextureAtlasSources.clear();
        this.mUpdateOnHardwareNeeded = true;
    }

    private void checkTextureAtlasSourcePosition(T pTextureAtlasSource, int pTextureX, int pTextureY) throws IllegalArgumentException {
        if (pTextureX < 0) {
            throw new IllegalArgumentException("Illegal negative pTextureX supplied: '" + pTextureX + "'");
        } else if (pTextureY < 0) {
            throw new IllegalArgumentException("Illegal negative pTextureY supplied: '" + pTextureY + "'");
        } else if (pTextureAtlasSource.getTextureWidth() + pTextureX > getWidth() || pTextureAtlasSource.getTextureHeight() + pTextureY > getHeight()) {
            throw new IllegalArgumentException("Supplied pTextureAtlasSource must not exceed bounds of Texture.");
        }
    }
}
