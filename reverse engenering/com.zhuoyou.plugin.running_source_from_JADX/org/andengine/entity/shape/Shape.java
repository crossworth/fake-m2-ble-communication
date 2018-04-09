package org.andengine.entity.shape;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.IVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class Shape extends Entity implements IShape {
    protected int mBlendFunctionDestination = 771;
    protected int mBlendFunctionSource = IShape.BLENDFUNCTION_SOURCE_DEFAULT;
    protected boolean mBlendingEnabled = false;
    protected ShaderProgram mShaderProgram;

    protected abstract void onUpdateVertices();

    public Shape(float pX, float pY, ShaderProgram pShaderProgram) {
        super(pX, pY);
        this.mShaderProgram = pShaderProgram;
    }

    public boolean isBlendingEnabled() {
        return this.mBlendingEnabled;
    }

    public void setBlendingEnabled(boolean pBlendingEnabled) {
        this.mBlendingEnabled = pBlendingEnabled;
    }

    public int getBlendFunctionSource() {
        return this.mBlendFunctionSource;
    }

    public void setBlendFunctionSource(int pBlendFunctionSource) {
        this.mBlendFunctionSource = pBlendFunctionSource;
    }

    public int getBlendFunctionDestination() {
        return this.mBlendFunctionDestination;
    }

    public void setBlendFunctionDestination(int pBlendFunctionDestination) {
        this.mBlendFunctionDestination = pBlendFunctionDestination;
    }

    public void setBlendFunction(int pBlendFunctionSource, int pBlendFunctionDestination) {
        this.mBlendFunctionSource = pBlendFunctionSource;
        this.mBlendFunctionDestination = pBlendFunctionDestination;
    }

    public ShaderProgram getShaderProgram() {
        return this.mShaderProgram;
    }

    public void setShaderProgram(ShaderProgram pShaderProgram) {
        this.mShaderProgram = pShaderProgram;
    }

    public VertexBufferObjectManager getVertexBufferObjectManager() {
        return getVertexBufferObject().getVertexBufferObjectManager();
    }

    protected void preDraw(GLState pGLState, Camera pCamera) {
        if (this.mBlendingEnabled) {
            pGLState.enableBlend();
            pGLState.blendFunction(this.mBlendFunctionSource, this.mBlendFunctionDestination);
        }
    }

    protected void postDraw(GLState pGLState, Camera pCamera) {
        if (this.mBlendingEnabled) {
            pGLState.disableBlend();
        }
    }

    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return false;
    }

    public void reset() {
        super.reset();
        this.mBlendFunctionSource = IShape.BLENDFUNCTION_SOURCE_DEFAULT;
        this.mBlendFunctionDestination = 771;
    }

    public void dispose() {
        super.dispose();
        IVertexBufferObject vertexBufferObject = getVertexBufferObject();
        if (vertexBufferObject != null && vertexBufferObject.isAutoDispose() && !vertexBufferObject.isDisposed()) {
            vertexBufferObject.dispose();
        }
    }

    protected void initBlendFunction(ITextureRegion pTextureRegion) {
        initBlendFunction(pTextureRegion.getTexture());
    }

    protected void initBlendFunction(ITexture pTexture) {
        initBlendFunction(pTexture.getTextureOptions());
    }

    protected void initBlendFunction(TextureOptions pTextureOptions) {
        if (pTextureOptions.mPreMultiplyAlpha) {
            setBlendFunction(1, 771);
        }
    }
}
