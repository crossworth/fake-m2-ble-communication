package org.andengine.opengl.shader.exception;

import org.andengine.util.exception.AndEngineRuntimeException;

public class ShaderProgramException extends AndEngineRuntimeException {
    private static final long serialVersionUID = 2377158902169370516L;

    public ShaderProgramException(String pMessage) {
        super(pMessage);
    }

    public ShaderProgramException(String pMessage, ShaderProgramException pShaderProgramException) {
        super(pMessage, pShaderProgramException);
    }
}
