package org.andengine.opengl.shader;

import android.opengl.GLES20;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.shader.exception.ShaderProgramLinkException;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class C2068xbf9afb3a extends ShaderProgram {
    public static final String FRAGMENTSHADER = "precision lowp float;\nuniform sampler2D u_texture_0;\nuniform sampler2D u_texture_1;\nuniform bool u_textureselect_texture_0;\nvarying mediump vec2 v_textureCoordinates;\nvoid main() {\n\tif(u_textureselect_texture_0) {\n\t\tgl_FragColor = texture2D(u_texture_0, v_textureCoordinates);\n\t} else {\n\t\tgl_FragColor = texture2D(u_texture_1, v_textureCoordinates);\n\t}\n}";
    private static C2068xbf9afb3a INSTANCE = null;
    public static final String VERTEXSHADER = "uniform mat4 u_modelViewProjectionMatrix;\nuniform float u_position_interpolation_mix_0;\nattribute vec4 a_position_0;\nattribute vec4 a_position_1;\nattribute vec2 a_textureCoordinates;\nvarying vec2 v_textureCoordinates;\nvoid main() {\n\tvec4 position = vec4(0, 0, 0, 1);\n\tposition.xy = mix(a_position_0.xy,a_position_1.xy,u_position_interpolation_mix_0);\n\tv_textureCoordinates = a_textureCoordinates;\n\tgl_Position = u_modelViewProjectionMatrix * position;\n}";
    public static int sUniformModelViewPositionMatrixLocation = -1;
    public static int sUniformPositionInterpolationMix0Location = -1;
    public static int sUniformTexture0Location = -1;
    public static int sUniformTexture1Location = -1;
    public static int sUniformTextureSelectTexture0Location = -1;

    private C2068xbf9afb3a() {
        super(VERTEXSHADER, "precision lowp float;\nuniform sampler2D u_texture_0;\nuniform sampler2D u_texture_1;\nuniform bool u_textureselect_texture_0;\nvarying mediump vec2 v_textureCoordinates;\nvoid main() {\n\tif(u_textureselect_texture_0) {\n\t\tgl_FragColor = texture2D(u_texture_0, v_textureCoordinates);\n\t} else {\n\t\tgl_FragColor = texture2D(u_texture_1, v_textureCoordinates);\n\t}\n}");
    }

    public static C2068xbf9afb3a getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new C2068xbf9afb3a();
        }
        return INSTANCE;
    }

    protected void link(GLState pGLState) throws ShaderProgramLinkException {
        GLES20.glBindAttribLocation(this.mProgramID, 4, ShaderProgramConstants.ATTRIBUTE_POSITION_0);
        GLES20.glBindAttribLocation(this.mProgramID, 5, ShaderProgramConstants.ATTRIBUTE_POSITION_1);
        GLES20.glBindAttribLocation(this.mProgramID, 3, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES);
        super.link(pGLState);
        sUniformModelViewPositionMatrixLocation = getUniformLocation(ShaderProgramConstants.UNIFORM_MODELVIEWPROJECTIONMATRIX);
        sUniformTexture0Location = getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURE_0);
        sUniformTexture1Location = getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURE_1);
        sUniformTextureSelectTexture0Location = getUniformLocation(ShaderProgramConstants.UNIFORM_TEXTURESELECT_TEXTURE_0);
        sUniformPositionInterpolationMix0Location = getUniformLocation(ShaderProgramConstants.UNIFORM_POSITION_INTERPOLATION_MIX_0);
    }

    public void bind(GLState pGLState, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        GLES20.glDisableVertexAttribArray(1);
        GLES20.glDisableVertexAttribArray(0);
        GLES20.glEnableVertexAttribArray(4);
        GLES20.glEnableVertexAttribArray(5);
        super.bind(pGLState, pVertexBufferObjectAttributes);
        GLES20.glUniformMatrix4fv(sUniformModelViewPositionMatrixLocation, 1, false, pGLState.getModelViewProjectionGLMatrix(), 0);
        GLES20.glUniform1i(sUniformTexture0Location, 0);
        GLES20.glUniform1i(sUniformTexture1Location, 1);
    }

    public void unbind(GLState pGLState) {
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glDisableVertexAttribArray(4);
        GLES20.glDisableVertexAttribArray(5);
        super.unbind(pGLState);
    }
}
