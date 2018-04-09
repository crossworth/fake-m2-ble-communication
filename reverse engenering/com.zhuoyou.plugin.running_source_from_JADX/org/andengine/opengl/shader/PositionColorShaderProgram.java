package org.andengine.opengl.shader;

import android.opengl.GLES20;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.shader.exception.ShaderProgramLinkException;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class PositionColorShaderProgram extends ShaderProgram {
    public static final String FRAGMENTSHADER = "precision lowp float;\nvarying vec4 v_color;\nvoid main() {\n\tgl_FragColor = v_color;\n}";
    private static PositionColorShaderProgram INSTANCE = null;
    public static final String VERTEXSHADER = "uniform mat4 u_modelViewProjectionMatrix;\nattribute vec4 a_position;\nattribute vec4 a_color;\nvarying vec4 v_color;\nvoid main() {\n\tgl_Position = u_modelViewProjectionMatrix * a_position;\n\tv_color = a_color;\n}";
    public static int sUniformModelViewPositionMatrixLocation = -1;

    private PositionColorShaderProgram() {
        super(VERTEXSHADER, FRAGMENTSHADER);
    }

    public static PositionColorShaderProgram getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PositionColorShaderProgram();
        }
        return INSTANCE;
    }

    protected void link(GLState pGLState) throws ShaderProgramLinkException {
        GLES20.glBindAttribLocation(this.mProgramID, 0, ShaderProgramConstants.ATTRIBUTE_POSITION);
        GLES20.glBindAttribLocation(this.mProgramID, 1, ShaderProgramConstants.ATTRIBUTE_COLOR);
        super.link(pGLState);
        sUniformModelViewPositionMatrixLocation = getUniformLocation(ShaderProgramConstants.UNIFORM_MODELVIEWPROJECTIONMATRIX);
    }

    public void bind(GLState pGLState, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        GLES20.glDisableVertexAttribArray(3);
        super.bind(pGLState, pVertexBufferObjectAttributes);
        GLES20.glUniformMatrix4fv(sUniformModelViewPositionMatrixLocation, 1, false, pGLState.getModelViewProjectionGLMatrix(), 0);
    }

    public void unbind(GLState pGLState) {
        GLES20.glEnableVertexAttribArray(3);
        super.unbind(pGLState);
    }
}
