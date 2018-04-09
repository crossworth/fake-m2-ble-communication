package org.andengine.opengl.shader.exception;

public class ShaderProgramLinkException extends ShaderProgramException {
    private static final long serialVersionUID = 5418521931032742504L;

    public ShaderProgramLinkException(String pMessage) {
        super(pMessage);
    }

    public ShaderProgramLinkException(String pMessage, ShaderProgramException pShaderProgramException) {
        super(pMessage, pShaderProgramException);
    }
}
