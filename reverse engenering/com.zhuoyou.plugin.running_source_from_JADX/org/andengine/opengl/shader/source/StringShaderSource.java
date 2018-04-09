package org.andengine.opengl.shader.source;

import org.andengine.opengl.util.GLState;

public class StringShaderSource implements IShaderSource {
    private final String mShaderSource;

    public StringShaderSource(String pShaderSource) {
        this.mShaderSource = pShaderSource;
    }

    public String getShaderSource(GLState pGLState) {
        return this.mShaderSource;
    }
}
