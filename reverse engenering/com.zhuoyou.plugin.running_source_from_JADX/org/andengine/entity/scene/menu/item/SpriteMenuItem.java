package org.andengine.entity.scene.menu.item;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class SpriteMenuItem extends Sprite implements IMenuItem {
    private final int mID;

    public SpriteMenuItem(int pID, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(0.0f, 0.0f, pTextureRegion, pVertexBufferObjectManager);
        this.mID = pID;
    }

    public SpriteMenuItem(int pID, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pTextureRegion, pVertexBufferObjectManager, pShaderProgram);
        this.mID = pID;
    }

    public SpriteMenuItem(int pID, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(0.0f, 0.0f, pTextureRegion, pVertexBufferObjectManager, pDrawType);
        this.mID = pID;
    }

    public SpriteMenuItem(int pID, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pTextureRegion, pVertexBufferObjectManager, pDrawType, pShaderProgram);
        this.mID = pID;
    }

    public SpriteMenuItem(int pID, ITextureRegion pTextureRegion, ISpriteVertexBufferObject pVertexBufferObject) {
        super(0.0f, 0.0f, pTextureRegion, pVertexBufferObject);
        this.mID = pID;
    }

    public SpriteMenuItem(int pID, ITextureRegion pTextureRegion, ISpriteVertexBufferObject pVertexBufferObject, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pTextureRegion, pVertexBufferObject, pShaderProgram);
        this.mID = pID;
    }

    public SpriteMenuItem(int pID, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(0.0f, 0.0f, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
        this.mID = pID;
    }

    public SpriteMenuItem(int pID, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, pShaderProgram);
        this.mID = pID;
    }

    public SpriteMenuItem(int pID, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(0.0f, 0.0f, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, pDrawType);
        this.mID = pID;
    }

    public SpriteMenuItem(int pID, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, pDrawType, pShaderProgram);
        this.mID = pID;
    }

    public SpriteMenuItem(int pID, float pWidth, float pHeight, ITextureRegion pTextureRegion, ISpriteVertexBufferObject pSpriteVertexBufferObject) {
        super(0.0f, 0.0f, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject);
        this.mID = pID;
    }

    public SpriteMenuItem(int pID, float pWidth, float pHeight, ITextureRegion pTextureRegion, ISpriteVertexBufferObject pSpriteVertexBufferObject, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject, pShaderProgram);
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
