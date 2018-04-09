package org.andengine.opengl.font;

import org.andengine.opengl.font.exception.LetterNotFoundException;
import org.andengine.opengl.texture.ITexture;

public interface IFont {
    Letter getLetter(char c) throws LetterNotFoundException;

    float getLineHeight();

    ITexture getTexture();

    void load();

    void unload();
}
