package org.andengine.opengl.shader;

import android.opengl.GLES20;
import java.util.HashMap;
import org.andengine.opengl.shader.exception.ShaderProgramCompileException;
import org.andengine.opengl.shader.exception.ShaderProgramException;
import org.andengine.opengl.shader.exception.ShaderProgramLinkException;
import org.andengine.opengl.shader.source.IShaderSource;
import org.andengine.opengl.shader.source.StringShaderSource;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class ShaderProgram {
    private static final int[] HARDWAREID_CONTAINER = new int[1];
    private static final int[] LENGTH_CONTAINER = new int[1];
    private static final byte[] NAME_CONTAINER = new byte[64];
    private static final int NAME_CONTAINER_SIZE = 64;
    private static final int[] PARAMETERS_CONTAINER = new int[1];
    private static final int[] SIZE_CONTAINER = new int[1];
    private static final int[] TYPE_CONTAINER = new int[1];
    protected final HashMap<String, Integer> mAttributeLocations;
    protected boolean mCompiled;
    protected final IShaderSource mFragmentShaderSource;
    protected int mProgramID;
    protected final HashMap<String, Integer> mUniformLocations;
    protected final IShaderSource mVertexShaderSource;

    public ShaderProgram(String pVertexShaderSource, String pFragmentShaderSource) {
        this(new StringShaderSource(pVertexShaderSource), new StringShaderSource(pFragmentShaderSource));
    }

    public ShaderProgram(IShaderSource pVertexShaderSource, IShaderSource pFragmentShaderSource) {
        this.mProgramID = -1;
        this.mUniformLocations = new HashMap();
        this.mAttributeLocations = new HashMap();
        this.mVertexShaderSource = pVertexShaderSource;
        this.mFragmentShaderSource = pFragmentShaderSource;
    }

    public boolean isCompiled() {
        return this.mCompiled;
    }

    public void setCompiled(boolean pCompiled) {
        this.mCompiled = pCompiled;
    }

    public int getAttributeLocation(String pAttributeName) {
        Integer location = (Integer) this.mAttributeLocations.get(pAttributeName);
        if (location != null) {
            return location.intValue();
        }
        throw new ShaderProgramException("Unexpected attribute: '" + pAttributeName + "'. Existing attributes: " + this.mAttributeLocations.toString());
    }

    public int getAttributeLocationOptional(String pAttributeName) {
        Integer location = (Integer) this.mAttributeLocations.get(pAttributeName);
        if (location != null) {
            return location.intValue();
        }
        return -1;
    }

    public int getUniformLocation(String pUniformName) {
        Integer location = (Integer) this.mUniformLocations.get(pUniformName);
        if (location != null) {
            return location.intValue();
        }
        throw new ShaderProgramException("Unexpected uniform: '" + pUniformName + "'. Existing uniforms: " + this.mUniformLocations.toString());
    }

    public int getUniformLocationOptional(String pUniformName) {
        Integer location = (Integer) this.mUniformLocations.get(pUniformName);
        if (location != null) {
            return location.intValue();
        }
        return -1;
    }

    public void bind(GLState pGLState, VertexBufferObjectAttributes pVertexBufferObjectAttributes) throws ShaderProgramException {
        if (!this.mCompiled) {
            compile(pGLState);
        }
        pGLState.useProgram(this.mProgramID);
        pVertexBufferObjectAttributes.glVertexAttribPointers();
    }

    public void unbind(GLState pGLState) throws ShaderProgramException {
    }

    public void delete(GLState pGLState) {
        if (this.mCompiled) {
            this.mCompiled = false;
            pGLState.deleteProgram(this.mProgramID);
            this.mProgramID = -1;
        }
    }

    protected void compile(GLState pGLState) throws ShaderProgramException {
        String vertexShaderSource = this.mVertexShaderSource.getShaderSource(pGLState);
        int vertexShaderID = compileShader(vertexShaderSource, 35633);
        String fragmentShaderSource = this.mFragmentShaderSource.getShaderSource(pGLState);
        int fragmentShaderID = compileShader(fragmentShaderSource, 35632);
        this.mProgramID = GLES20.glCreateProgram();
        GLES20.glAttachShader(this.mProgramID, vertexShaderID);
        GLES20.glAttachShader(this.mProgramID, fragmentShaderID);
        try {
            link(pGLState);
            GLES20.glDeleteShader(vertexShaderID);
            GLES20.glDeleteShader(fragmentShaderID);
        } catch (ShaderProgramLinkException e) {
            throw new ShaderProgramLinkException("VertexShaderSource:\n##########################\n" + vertexShaderSource + "\n##########################" + "\n\nFragmentShaderSource:\n##########################\n" + fragmentShaderSource + "\n##########################", e);
        }
    }

    protected void link(GLState pGLState) throws ShaderProgramLinkException {
        GLES20.glLinkProgram(this.mProgramID);
        GLES20.glGetProgramiv(this.mProgramID, 35714, HARDWAREID_CONTAINER, 0);
        if (HARDWAREID_CONTAINER[0] == 0) {
            throw new ShaderProgramLinkException(GLES20.glGetProgramInfoLog(this.mProgramID));
        }
        initAttributeLocations();
        initUniformLocations();
        this.mCompiled = true;
    }

    private static int compileShader(String pSource, int pType) throws ShaderProgramException {
        int shaderID = GLES20.glCreateShader(pType);
        if (shaderID == 0) {
            throw new ShaderProgramException("Could not create Shader of type: '" + pType + '\"');
        }
        GLES20.glShaderSource(shaderID, pSource);
        GLES20.glCompileShader(shaderID);
        GLES20.glGetShaderiv(shaderID, 35713, HARDWAREID_CONTAINER, 0);
        if (HARDWAREID_CONTAINER[0] != 0) {
            return shaderID;
        }
        throw new ShaderProgramCompileException(GLES20.glGetShaderInfoLog(shaderID), pSource);
    }

    private void initUniformLocations() throws ShaderProgramLinkException {
        this.mUniformLocations.clear();
        PARAMETERS_CONTAINER[0] = 0;
        GLES20.glGetProgramiv(this.mProgramID, 35718, PARAMETERS_CONTAINER, 0);
        int numUniforms = PARAMETERS_CONTAINER[0];
        for (int i = 0; i < numUniforms; i++) {
            GLES20.glGetActiveUniform(this.mProgramID, i, 64, LENGTH_CONTAINER, 0, SIZE_CONTAINER, 0, TYPE_CONTAINER, 0, NAME_CONTAINER, 0);
            int length = LENGTH_CONTAINER[0];
            if (length == 0) {
                while (length < 64 && NAME_CONTAINER[length] != (byte) 0) {
                    length++;
                }
            }
            String name = new String(NAME_CONTAINER, 0, length);
            int location = GLES20.glGetUniformLocation(this.mProgramID, name);
            if (location == -1) {
                length = 0;
                while (length < 64 && NAME_CONTAINER[length] != (byte) 0) {
                    length++;
                }
                name = new String(NAME_CONTAINER, 0, length);
                location = GLES20.glGetUniformLocation(this.mProgramID, name);
                if (location == -1) {
                    throw new ShaderProgramLinkException("Invalid location for uniform: '" + name + "'.");
                }
            }
            this.mUniformLocations.put(name, Integer.valueOf(location));
        }
    }

    @Deprecated
    private void initAttributeLocations() {
        this.mAttributeLocations.clear();
        PARAMETERS_CONTAINER[0] = 0;
        GLES20.glGetProgramiv(this.mProgramID, 35721, PARAMETERS_CONTAINER, 0);
        int numAttributes = PARAMETERS_CONTAINER[0];
        for (int i = 0; i < numAttributes; i++) {
            GLES20.glGetActiveAttrib(this.mProgramID, i, 64, LENGTH_CONTAINER, 0, SIZE_CONTAINER, 0, TYPE_CONTAINER, 0, NAME_CONTAINER, 0);
            int length = LENGTH_CONTAINER[0];
            if (length == 0) {
                while (length < 64 && NAME_CONTAINER[length] != (byte) 0) {
                    length++;
                }
            }
            String name = new String(NAME_CONTAINER, 0, length);
            int location = GLES20.glGetAttribLocation(this.mProgramID, name);
            if (location == -1) {
                length = 0;
                while (length < 64 && NAME_CONTAINER[length] != (byte) 0) {
                    length++;
                }
                name = new String(NAME_CONTAINER, 0, length);
                location = GLES20.glGetAttribLocation(this.mProgramID, name);
                if (location == -1) {
                    throw new ShaderProgramLinkException("Invalid location for attribute: '" + name + "'.");
                }
            }
            this.mAttributeLocations.put(name, Integer.valueOf(location));
        }
    }

    public void setUniform(String pUniformName, float[] pGLMatrix) {
        GLES20.glUniformMatrix4fv(getUniformLocation(pUniformName), 1, false, pGLMatrix, 0);
    }

    public void setUniformOptional(String pUniformName, float[] pGLMatrix) {
        if (getUniformLocationOptional(pUniformName) != -1) {
            GLES20.glUniformMatrix4fv(getUniformLocationOptional(pUniformName), 1, false, pGLMatrix, 0);
        }
    }

    public void setUniform(String pUniformName, float pX) {
        GLES20.glUniform1f(getUniformLocation(pUniformName), pX);
    }

    public void setUniformOptional(String pUniformName, float pX) {
        if (getUniformLocationOptional(pUniformName) != -1) {
            GLES20.glUniform1f(getUniformLocationOptional(pUniformName), pX);
        }
    }

    public void setUniform(String pUniformName, float pX, float pY) {
        GLES20.glUniform2f(getUniformLocation(pUniformName), pX, pY);
    }

    public void setUniformOptional(String pUniformName, float pX, float pY) {
        if (getUniformLocationOptional(pUniformName) != -1) {
            GLES20.glUniform2f(getUniformLocationOptional(pUniformName), pX, pY);
        }
    }

    public void setUniform(String pUniformName, float pX, float pY, float pZ) {
        GLES20.glUniform3f(getUniformLocation(pUniformName), pX, pY, pZ);
    }

    public void setUniformOptional(String pUniformName, float pX, float pY, float pZ) {
        if (getUniformLocationOptional(pUniformName) != -1) {
            GLES20.glUniform3f(getUniformLocationOptional(pUniformName), pX, pY, pZ);
        }
    }

    public void setUniform(String pUniformName, float pX, float pY, float pZ, float pW) {
        GLES20.glUniform4f(getUniformLocation(pUniformName), pX, pY, pZ, pW);
    }

    public void setUniformOptional(String pUniformName, float pX, float pY, float pZ, float pW) {
        if (getUniformLocationOptional(pUniformName) != -1) {
            GLES20.glUniform4f(getUniformLocationOptional(pUniformName), pX, pY, pZ, pW);
        }
    }

    public void setTexture(String pUniformName, int pTexture) {
        GLES20.glUniform1i(getUniformLocation(pUniformName), pTexture);
    }

    public void setTextureOptional(String pUniformName, int pTexture) {
        int location = getUniformLocationOptional(pUniformName);
        if (location != -1) {
            GLES20.glUniform1i(location, pTexture);
        }
    }
}
