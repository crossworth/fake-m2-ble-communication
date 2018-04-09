package org.andengine.entity.sprite.batch;

import java.util.ArrayList;
import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.vbo.ISpriteBatchVertexBufferObject;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.list.SmartList;

public class SpriteGroup extends DynamicSpriteBatch {
    public SpriteGroup(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pTexture, pCapacity, pVertexBufferObjectManager);
        setChildrenVisible(false);
    }

    public SpriteGroup(float pX, float pY, ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTexture, pCapacity, pVertexBufferObjectManager);
        setChildrenVisible(false);
    }

    public SpriteGroup(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(pTexture, pCapacity, pVertexBufferObjectManager, pDrawType);
        setChildrenVisible(false);
    }

    public SpriteGroup(float pX, float pY, ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(pX, pY, pTexture, pCapacity, pVertexBufferObjectManager, pDrawType);
        setChildrenVisible(false);
    }

    public SpriteGroup(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(pTexture, pCapacity, pVertexBufferObjectManager, pShaderProgram);
        setChildrenVisible(false);
    }

    public SpriteGroup(float pX, float pY, ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(pX, pY, pTexture, pCapacity, pVertexBufferObjectManager, pShaderProgram);
        setChildrenVisible(false);
    }

    public SpriteGroup(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(pTexture, pCapacity, pVertexBufferObjectManager, pDrawType, pShaderProgram);
        setChildrenVisible(false);
    }

    public SpriteGroup(float pX, float pY, ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(pX, pY, pTexture, pCapacity, pVertexBufferObjectManager, pDrawType, pShaderProgram);
        setChildrenVisible(false);
    }

    public SpriteGroup(ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject) {
        super(pTexture, pCapacity, pSpriteBatchVertexBufferObject);
        setChildrenVisible(false);
    }

    public SpriteGroup(float pX, float pY, ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject) {
        super(pX, pY, pTexture, pCapacity, pSpriteBatchVertexBufferObject);
        setChildrenVisible(false);
    }

    public SpriteGroup(ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pTexture, pCapacity, pSpriteBatchVertexBufferObject, pShaderProgram);
        setChildrenVisible(false);
    }

    public SpriteGroup(float pX, float pY, ITexture pTexture, int pCapacity, ISpriteBatchVertexBufferObject pSpriteBatchVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pX, pY, pTexture, pCapacity, pSpriteBatchVertexBufferObject, pShaderProgram);
        setChildrenVisible(false);
    }

    @Deprecated
    public void attachChild(IEntity pEntity) throws IllegalArgumentException {
        if (pEntity instanceof Sprite) {
            attachChild((Sprite) pEntity);
            return;
        }
        throw new IllegalArgumentException("A " + SpriteGroup.class.getSimpleName() + " can only handle children of type Sprite or subclasses of Sprite, like TiledSprite or AnimatedSprite.");
    }

    public void attachChild(Sprite pSprite) {
        assertCapacity();
        assertTexture(pSprite.getTextureRegion());
        super.attachChild(pSprite);
    }

    public void attachChildren(ArrayList<? extends Sprite> pSprites) {
        int baseSpriteCount = pSprites.size();
        for (int i = 0; i < baseSpriteCount; i++) {
            attachChild((Sprite) pSprites.get(i));
        }
    }

    protected boolean onUpdateSpriteBatch() {
        SmartList<IEntity> children = this.mChildren;
        if (children == null) {
            return false;
        }
        int childCount = children.size();
        for (int i = 0; i < childCount; i++) {
            drawWithoutChecks((Sprite) children.get(i));
        }
        return true;
    }

    private void assertCapacity() {
        if (getChildCount() >= this.mCapacity) {
            throw new IllegalStateException("This " + SpriteGroup.class.getSimpleName() + " has already reached its capacity (" + this.mCapacity + ") !");
        }
    }
}
