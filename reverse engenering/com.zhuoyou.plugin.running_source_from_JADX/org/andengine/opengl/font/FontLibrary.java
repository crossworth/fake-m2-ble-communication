package org.andengine.opengl.font;

import android.util.SparseArray;
import org.andengine.util.adt.map.Library;

public class FontLibrary extends Library<Font> {
    public FontLibrary(int pInitialCapacity) {
        super(pInitialCapacity);
    }

    public void loadFonts(FontManager pFontManager) {
        SparseArray<Font> items = this.mItems;
        for (int i = items.size() - 1; i >= 0; i--) {
            Font font = (Font) items.valueAt(i);
            if (font != null) {
                pFontManager.loadFont(font);
            }
        }
    }
}
