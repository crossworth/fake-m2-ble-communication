package org.andengine.opengl.shader;

import android.opengl.GLES20;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.shader.exception.ShaderProgramLinkException;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class PositionTextureCoordinatesUniformColorShaderProgram extends ShaderProgram {
    public static final String FRAGMENTSHADER = "precision lowp float;\nuniform sampler2D u_texture_0;\nuniform vec4 u_color;\nvarying mediump vec2 v_textureCoordinates;\nvoid main() {\n\tgl_FragColor = u_color * texture2D(u_texture_0, v_textureCoordinates);\n}";
    private static PositionTextureCoordinatesUniformColorShaderProgram INSTANCE = null;
    public static final String VERTEXSHADER = "uniform mat4 u_modelViewProjectionMatrix;\nattribute vec4 a_position;\nattribute vec2 a_textureCoordinates;\nvarying vec2 v_textureCoordinates;\nvoid main() {\n\tv_textureCoordinates = a_textureCoordinates;\n\tgl_Position = u_modelViewProjectionMatrix * a_position;\n}";
    public static int sUniformColorLocation = -1;
    public static int sUniformModelViewPositionMatrixLocation = -1;
    public static int sUniformTexture0Location = -1;

    private PositionTextureCoordinatesUniformColorShaderProgram() {
        super("uniform mat4 u_modelViewProjectionMatrix;\nattribute vec4 a_position;\nattribute vec2 a_textureCoordinates;\nvarying vec2 v_textureCoordinates;\nvoid main() {\n\tv_textureCoordinates = a_textureCoordinates;\n\tgl_Position = u_modelViewProjectionMatrix * a_position;\n}", FRAGMENTSHADER);
    }

    public static PositionTextureCoordinatesUniformColorShaderProgram getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PositionTextureCoordinatesUniformColorShaderProgram();
        }
        return INSTANCE;
    }

    protected void link(GLState pGLState) throws ShaderProgramLinkException {
        GLES20.glBindAttribLocation(this.mProgramID, 0, ShaderProgramConstants.ATTRIBUTE_POSITION);
        GLES20.glBindAttribLocation(this.mProgramID, 3, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES);
        super.link(pGLState);
        sUniformModelViewPositionMatrixLocation = getUniformLocation(ShaderProgramConstants.UNIFORM_MODELVIEWPROJECTIONMATRIX);
        sUniformTexture0Location = getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURE_0);
        sUniformColorLocation = getUniformLocation(ShaderProgramConstants.UNIFORM_COLOR);
    }

    public void bind(GLState pGLState, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        GLES20.glDisableVertexAttribArray(1);
        super.bind(pGLState, pVertexBufferObjectAttributes);
        GLES20.glUniformMatrix4fv(sUniformModelViewPositionMatrixLocation, 1, false, pGLState.getModelViewProjectionGLMatrix(), 0);
        GLES20.glUniform1i(sUniformTexture0Location, 0);
    }

    public void unbind(GLState pGLState) {
        GLES20.glEnableVertexAttribArray(1);
        super.unbind(pGLState);
    }
}
