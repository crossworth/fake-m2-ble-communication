package org.andengine.opengl.shader;

import android.opengl.GLES20;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.shader.exception.ShaderProgramLinkException;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class PositionTextureCoordinatesTextureSelectShaderProgram extends ShaderProgram {
    public static final String FRAGMENTSHADER = "precision lowp float;\nuniform sampler2D u_texture_0;\nuniform sampler2D u_texture_1;\nuniform bool u_textureselect_texture_0;\nvarying mediump vec2 v_textureCoordinates;\nvoid main() {\n\tif(u_textureselect_texture_0) {\n\t\tgl_FragColor = texture2D(u_texture_0, v_textureCoordinates);\n\t} else {\n\t\tgl_FragColor = texture2D(u_texture_1, v_textureCoordinates);\n\t}\n}";
    private static PositionTextureCoordinatesTextureSelectShaderProgram INSTANCE = null;
    public static final String VERTEXSHADER = "uniform mat4 u_modelViewProjectionMatrix;\nattribute vec4 a_position;\nattribute vec2 a_textureCoordinates;\nvarying vec2 v_textureCoordinates;\nvoid main() {\n\tv_textureCoordinates = a_textureCoordinates;\n\tgl_Position = u_modelViewProjectionMatrix * a_position;\n}";
    public static int sUniformModelViewPositionMatrixLocation = -1;
    public static int sUniformTexture0Location = -1;
    public static int sUniformTexture1Location = -1;
    public static int sUniformTextureSelectTexture0Location = -1;

    private PositionTextureCoordinatesTextureSelectShaderProgram() {
        super("uniform mat4 u_modelViewProjectionMatrix;\nattribute vec4 a_position;\nattribute vec2 a_textureCoordinates;\nvarying vec2 v_textureCoordinates;\nvoid main() {\n\tv_textureCoordinates = a_textureCoordinates;\n\tgl_Position = u_modelViewProjectionMatrix * a_position;\n}", "precision lowp float;\nuniform sampler2D u_texture_0;\nuniform sampler2D u_texture_1;\nuniform bool u_textureselect_texture_0;\nvarying mediump vec2 v_textureCoordinates;\nvoid main() {\n\tif(u_textureselect_texture_0) {\n\t\tgl_FragColor = texture2D(u_texture_0, v_textureCoordinates);\n\t} else {\n\t\tgl_FragColor = texture2D(u_texture_1, v_textureCoordinates);\n\t}\n}");
    }

    public static PositionTextureCoordinatesTextureSelectShaderProgram getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PositionTextureCoordinatesTextureSelectShaderProgram();
        }
        return INSTANCE;
    }

    protected void link(GLState pGLState) throws ShaderProgramLinkException {
        GLES20.glBindAttribLocation(this.mProgramID, 0, ShaderProgramConstants.ATTRIBUTE_POSITION);
        GLES20.glBindAttribLocation(this.mProgramID, 3, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES);
        super.link(pGLState);
        sUniformModelViewPositionMatrixLocation = getUniformLocation(ShaderProgramConstants.UNIFORM_MODELVIEWPROJECTIONMATRIX);
        sUniformTexture0Location = getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURE_0);
        sUniformTexture1Location = getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURE_1);
        sUniformTextureSelectTexture0Location = getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURESELECT_TEXTURE_0);
    }

    public void bind(GLState pGLState, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        GLES20.glDisableVertexAttribArray(1);
        super.bind(pGLState, pVertexBufferObjectAttributes);
        GLES20.glUniformMatrix4fv(sUniformModelViewPositionMatrixLocation, 1, false, pGLState.getModelViewProjectionGLMatrix(), 0);
        GLES20.glUniform1i(sUniformTexture0Location, 0);
        GLES20.glUniform1i(sUniformTexture1Location, 1);
    }

    public void unbind(GLState pGLState) {
        GLES20.glEnableVertexAttribArray(1);
        super.unbind(pGLState);
    }
}