package org.andengine.opengl.font;

import java.util.ArrayList;
import org.andengine.opengl.util.GLState;

public class FontManager {
    private final ArrayList<Font> mFontsManaged = new ArrayList();

    public void onCreate() {
    }

    public synchronized void onDestroy() {
        ArrayList<Font> managedFonts = this.mFontsManaged;
        for (int i = managedFonts.size() - 1; i >= 0; i--) {
            ((Font) managedFonts.get(i)).invalidateLetters();
        }
        this.mFontsManaged.clear();
    }

    public synchronized void loadFont(Font pFont) {
        if (pFont == null) {
            throw new IllegalArgumentException("pFont must not be null!");
        }
        this.mFontsManaged.add(pFont);
    }

    public synchronized void loadFonts(Font... pFonts) {
        for (Font loadFont : pFonts) {
            loadFont(loadFont);
        }
    }

    public synchronized void unloadFont(Font pFont) {
        if (pFont == null) {
            throw new IllegalArgumentException("pFont must not be null!");
        }
        this.mFontsManaged.remove(pFont);
    }

    public synchronized void unloadFonts(Font... pFonts) {
        for (Font unloadFont : pFonts) {
            unloadFont(unloadFont);
        }
    }

    public synchronized void updateFonts(GLState pGLState) {
        ArrayList<Font> fonts = this.mFontsManaged;
        int fontCount = fonts.size();
        if (fontCount > 0) {
            for (int i = fontCount - 1; i >= 0; i--) {
                ((Font) fonts.get(i)).update(pGLState);
            }
        }
    }

    public synchronized void onReload() {
        ArrayList<Font> managedFonts = this.mFontsManaged;
        for (int i = managedFonts.size() - 1; i >= 0; i--) {
            ((Font) managedFonts.get(i)).invalidateLetters();
        }
    }
}
