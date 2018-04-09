package org.andengine.entity.scene.menu.item;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class AnimatedSpriteMenuItem extends AnimatedSprite implements IMenuItem {
    private final int mID;

    public AnimatedSpriteMenuItem(int pID, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(0.0f, 0.0f, pTiledTextureRegion, pVertexBufferObjectManager);
        this.mID = pID;
    }

    public AnimatedSpriteMenuItem(int pID, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pTiledTextureRegion, pVertexBufferObjectManager, pShaderProgram);
        this.mID = pID;
    }

    public AnimatedSpriteMenuItem(int pID, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(0.0f, 0.0f, pTiledTextureRegion, pVertexBufferObjectManager, pDrawType);
        this.mID = pID;
    }

    public AnimatedSpriteMenuItem(int pID, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pTiledTextureRegion, pVertexBufferObjectManager, pDrawType, pShaderProgram);
        this.mID = pID;
    }

    public AnimatedSpriteMenuItem(int pID, ITiledTextureRegion pTiledTextureRegion, ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
        super(0.0f, 0.0f, pTiledTextureRegion, pTiledSpriteVertexBufferObject);
        this.mID = pID;
    }

    public AnimatedSpriteMenuItem(int pID, ITiledTextureRegion pTiledTextureRegion, ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pTiledTextureRegion, pTiledSpriteVertexBufferObject, pShaderProgram);
        this.mID = pID;
    }

    public AnimatedSpriteMenuItem(int pID, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(0.0f, 0.0f, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager);
        this.mID = pID;
    }

    public AnimatedSpriteMenuItem(int pID, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager, pShaderProgram);
        this.mID = pID;
    }

    public AnimatedSpriteMenuItem(int pID, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(0.0f, 0.0f, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager, pDrawType);
        this.mID = pID;
    }

    public AnimatedSpriteMenuItem(int pID, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager, pDrawType, pShaderProgram);
        this.mID = pID;
    }

    public AnimatedSpriteMenuItem(int pID, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
        super(0.0f, 0.0f, pWidth, pHeight, pTiledTextureRegion, pTiledSpriteVertexBufferObject);
        this.mID = pID;
    }

    public AnimatedSpriteMenuItem(int pID, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion, ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pWidth, pHeight, pTiledTextureRegion, pTiledSpriteVertexBufferObject, pShaderProgram);
        this.mID = pID;
    }

    public int getID() {
        return this.mID;
    }

    public void onSelected() {
    }

    public void onUnselected() {
    }
}
