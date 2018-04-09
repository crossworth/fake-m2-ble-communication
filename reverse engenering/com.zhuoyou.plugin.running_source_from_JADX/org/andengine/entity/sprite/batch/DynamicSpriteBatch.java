package org.andengine.entity.sprite.batch;

import org.andengine.entity.sprite.batch.vbo.ISpriteBatchVertexBufferObject;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class DynamicSpriteBatch extends SpriteBatch {
    protected abstract boolean onUpdateSpriteBatch();

    public DynamicSpriteBatch(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pTexture, pCapacity, pVertexBufferObjectManager, DrawType.DYNAMIC);
    }

    public DynamicSpriteBatch(float pX, float pY, ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTexture, pCapacity, pVertexBufferObjectManager, DrawType.DYNAMIC);
    }

    public DynamicSpriteBatch(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(pTexture, pCapacity, pVertexBufferObjectManager, pDrawType);
    }

    public DynamicSpriteBatch(float pX, float pY, ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(pX, pY, pTexture, pCapacity, pVertexBufferObjectManager, pDrawType);
    }

    public DynamicSpriteBatch(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(pTexture, pCapacity, pVertexBufferObjectManager, DrawType.DYNAMIC, pShaderProgram);
    }

    public DynamicSpriteBatch(float pX, float pY, ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(pX, pY, pTexture, pCapacity, pVertexBufferObjectManager, DrawType.DYNAMIC, pShaderProgram);
    }

    public DynamicSpriteBatch(ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject) {
        super(pTexture, pCapacity, pSpriteBatchVertexBufferObject);
    }

    public DynamicSpriteBatch(float pX, float pY, ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject) {
        super(pX, pY, pTexture, pCapacity, pSpriteBatchVertexBufferObject);
    }

    public DynamicSpriteBatch(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(pTexture, pCapacity, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public DynamicSpriteBatch(float pX, float pY, ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(pX, pY, pTexture, pCapacity, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public DynamicSpriteBatch(ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pTexture, pCapacity, pSpriteBatchVertexBufferObject, pShaderProgram);
    }

    public DynamicSpriteBatch(float pX, float pY, ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pX, pY, pTexture, pCapacity, pSpriteBatchVertexBufferObject, pShaderProgram);
    }

    protected void begin() {
        super.begin();
        if (onUpdateSpriteBatch()) {
            submit();
        }
    }
}
