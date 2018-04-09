package org.andengine.opengl.vbo;

public enum DrawType {
    STATIC(35044),
    DYNAMIC(35048),
    STREAM(35040);
    
    private final int mUsage;

    private DrawType(int pUsage) {
        this.mUsage = pUsage;
    }

    public int getUsage() {
        return this.mUsage;
    }
}
