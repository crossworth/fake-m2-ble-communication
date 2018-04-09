package org.andengine.entity.sprite.vbo;

import java.nio.FloatBuffer;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class LowMemoryDiamondSpriteVertexBufferObject extends LowMemorySpriteVertexBufferObject implements IDiamondSpriteVertexBufferObject {
    public LowMemoryDiamondSpriteVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public void onUpdateVertices(Sprite pSprite) {
        FloatBuffer bufferData = this.mFloatBuffer;
        float x2 = pSprite.getWidth();
        float y2 = pSprite.getHeight();
        float xCenter = (0.0f + x2) * 0.5f;
        float yCenter = (0.0f + y2) * 0.5f;
        bufferData.put(0, 0.0f);
        bufferData.put(1, yCenter);
        bufferData.put(5, xCenter);
        bufferData.put(6, y2);
        bufferData.put(10, xCenter);
        bufferData.put(11, 0.0f);
        bufferData.put(15, x2);
        bufferData.put(16, yCenter);
        setDirtyOnHardware();
    }

    public void onUpdateTextureCoordinates(Sprite pSprite) {
        float u;
        float u2;
        float v;
        float v2;
        FloatBuffer bufferData = this.mFloatBuffer;
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
            bufferData.put(3, uCenter);
            bufferData.put(4, v);
            bufferData.put(8, u);
            bufferData.put(9, vCenter);
            bufferData.put(13, u2);
            bufferData.put(14, vCenter);
            bufferData.put(18, uCenter);
            bufferData.put(19, v2);
        } else {
            bufferData.put(3, u);
            bufferData.put(4, vCenter);
            bufferData.put(8, uCenter);
            bufferData.put(9, v2);
            bufferData.put(13, uCenter);
            bufferData.put(14, v);
            bufferData.put(18, u2);
            bufferData.put(19, vCenter);
        }
        setDirtyOnHardware();
    }
}
