package org.andengine.opengl.shader;

import android.opengl.GLES20;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.shader.exception.ShaderProgramLinkException;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class PositionColorTextureCoordinatesShaderProgram extends ShaderProgram {
    public static final String FRAGMENTSHADER = "precision lowp float;\nuniform sampler2D u_texture_0;\nvarying lowp vec4 v_color;\nvarying mediump vec2 v_textureCoordinates;\nvoid main() {\n\tgl_FragColor = v_color * texture2D(u_texture_0, v_textureCoordinates);\n}";
    private static PositionColorTextureCoordinatesShaderProgram INSTANCE = null;
    public static final String VERTEXSHADER = "uniform mat4 u_modelViewProjectionMatrix;\nattribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_textureCoordinates;\nvarying vec4 v_color;\nvarying vec2 v_textureCoordinates;\nvoid main() {\n\tv_color = a_color;\n\tv_textureCoordinates = a_textureCoordinates;\n\tgl_Position = u_modelViewProjectionMatrix * a_position;\n}";
    public static int sUniformModelViewPositionMatrixLocation = -1;
    public static int sUniformTexture0Location = -1;

    private PositionColorTextureCoordinatesShaderProgram() {
        super(VERTEXSHADER, FRAGMENTSHADER);
    }

    public static PositionColorTextureCoordinatesShaderProgram getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PositionColorTextureCoordinatesShaderProgram();
        }
        return INSTANCE;
    }

    protected void link(GLState pGLState) throws ShaderProgramLinkException {
        GLES20.glBindAttribLocation(this.mProgramID, 0, ShaderProgramConstants.ATTRIBUTE_POSITION);
        GLES20.glBindAttribLocation(this.mProgramID, 1, ShaderProgramConstants.ATTRIBUTE_COLOR);
        GLES20.glBindAttribLocation(this.mProgramID, 3, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES);
        super.link(pGLState);
        sUniformModelViewPositionMatrixLocation = getUniformLocation(ShaderProgramConstants.UNIFORM_MODELVIEWPROJECTIONMATRIX);
        sUniformTexture0Location = getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURE_0);
    }

    public void bind(GLState pGLState, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super.bind(pGLState, pVertexBufferObjectAttributes);
        GLES20.glUniformMatrix4fv(sUniformModelViewPositionMatrixLocation, 1, false, pGLState.getModelViewProjectionGLMatrix(), 0);
        GLES20.glUniform1i(sUniformTexture0Location, 0);
    }
}
