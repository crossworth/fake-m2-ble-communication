package org.andengine.entity.primitive;

public enum DrawMode {
    POINTS(0),
    LINE_STRIP(3),
    LINE_LOOP(2),
    LINES(1),
    TRIANGLE_STRIP(5),
    TRIANGLE_FAN(6),
    TRIANGLES(4);
    
    public final int mDrawMode;

    private DrawMode(int pDrawMode) {
        this.mDrawMode = pDrawMode;
    }

    public int getDrawMode() {
        return this.mDrawMode;
    }
}
