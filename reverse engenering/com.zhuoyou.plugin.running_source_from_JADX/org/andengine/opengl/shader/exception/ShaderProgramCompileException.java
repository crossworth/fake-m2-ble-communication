package org.andengine.opengl.shader.exception;

public class ShaderProgramCompileException extends ShaderProgramException {
    private static final long serialVersionUID = 8284346688949370359L;

    public ShaderProgramCompileException(String pMessage, String pSource) {
        super("Reason: " + pMessage + "\nSource:\n##########################\n" + pSource + "\n##########################");
    }
}
