package org.andengine.entity.sprite.vbo;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class HighPerformanceDiamondSpriteVertexBufferObject extends HighPerformanceSpriteVertexBufferObject implements IDiamondSpriteVertexBufferObject {
    public HighPerformanceDiamondSpriteVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public void onUpdateVertices(Sprite pSprite) {
        float[] bufferData = this.mBufferData;
        float x2 = pSprite.getWidth();
        float y2 = pSprite.getHeight();
        float xCenter = (0.0f + x2) * 0.5f;
        float yCenter = (0.0f + y2) * 0.5f;
        bufferData[0] = 0.0f;
        bufferData[1] = yCenter;
        bufferData[5] = xCenter;
        bufferData[6] = y2;
        bufferData[10] = xCenter;
        bufferData[11] = 0.0f;
        bufferData[15] = x2;
        bufferData[16] = yCenter;
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
        float uCenter = (u + u2) * 0.5f;
        float vCenter = (v + v2) * 0.5f;
        if (textureRegion.isRotated()) {
            bufferData[3] = uCenter;
            bufferData[4] = v;
            bufferData[8] = u;
            bufferData[9] = vCenter;
            bufferData[13] = u2;
            bufferData[14] = vCenter;
            bufferData[18] = uCenter;
            bufferData[19] = v2;
        } else {
            bufferData[3] = u;
            bufferData[4] = vCenter;
            bufferData[8] = uCenter;
            bufferData[9] = v2;
            bufferData[13] = uCenter;
            bufferData[14] = v;
            bufferData[18] = u2;
            bufferData[19] = vCenter;
        }
        setDirtyOnHardware();
    }
}
