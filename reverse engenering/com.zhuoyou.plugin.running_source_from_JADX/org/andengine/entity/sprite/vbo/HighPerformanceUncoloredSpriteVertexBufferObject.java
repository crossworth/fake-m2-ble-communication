package org.andengine.entity.sprite.vbo;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class HighPerformanceUncoloredSpriteVertexBufferObject extends HighPerformanceSpriteVertexBufferObject implements IUncoloredSpriteVertexBufferObject {
    public HighPerformanceUncoloredSpriteVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public void onUpdateVertices(Sprite pSprite) {
        float[] bufferData = this.mBufferData;
        float x2 = pSprite.getWidth();
        float y2 = pSprite.getHeight();
        bufferData[0] = 0.0f;
        bufferData[1] = 0.0f;
        bufferData[4] = 0.0f;
        bufferData[5] = y2;
        bufferData[8] = x2;
        bufferData[9] = 0.0f;
        bufferData[12] = x2;
        bufferData[13] = y2;
        setDirtyOnHardware();
    }

    public void onUpdateTextureCoordinates(Sprite pSprite) {
        float u;
        float u2;
        float v;
        float v2;
        float[] bufferData = this.mBufferData;
        ITextureRegion textureRegion = pSprite.getTextureRegion();
        if (pSprite.isFlippedVertical()) {
            if (pSprite.isFlippedHorizontal()) {
                u = textureRegion.getU2();
                u2 = textureRegion.getU();
                v = textureRegion.getV2();
                v2 = textureRegion.getV();
            } else {
                u = textureRegion.getU();
                u2 = textureRegion.getU2();
                v = textureRegion.getV2();
                v2 = textureRegion.getV();
            }
        } else if (pSprite.isFlippedHorizontal()) {
            u = textureRegion.getU2();
            u2 = textureRegion.getU();
            v = textureRegion.getV();
            v2 = textureRegion.getV2();
        } else {
            u = textureRegion.getU();
            u2 = textureRegion.getU2();
            v = textureRegion.getV();
            v2 = textureRegion.getV2();
        }
        if (textureRegion.isRotated()) {
            bufferData[2] = u2;
            bufferData[3] = v;
            bufferData[6] = u;
            bufferData[7] = v;
            bufferData[10] = u2;
            bufferData[11] = v2;
            bufferData[14] = u;
            bufferData[15] = v2;
        } else {
            bufferData[2] = u;
            bufferData[3] = v;
            bufferData[6] = u;
            bufferData[7] = v2;
            bufferData[10] = u2;
            bufferData[11] = v;
            bufferData[14] = u2;
            bufferData[15] = v2;
        }
        setDirtyOnHardware();
    }
}
