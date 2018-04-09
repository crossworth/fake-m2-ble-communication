package org.andengine.entity.sprite.vbo;

import java.nio.FloatBuffer;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

public class LowMemoryTiledSpriteVertexBufferObject extends LowMemorySpriteVertexBufferObject implements ITiledSpriteVertexBufferObject {
    public LowMemoryTiledSpriteVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, int pCapacity, DrawType pDrawType, boolean pAutoDispose, VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
    }

    public void onUpdateColor(TiledSprite pTiledSprite) {
        FloatBuffer bufferData = this.mFloatBuffer;
        float packedColor = pTiledSprite.getColor().getABGRPackedFloat();
        int tileCount = pTiledSprite.getTileCount();
        int bufferDataOffset = 0;
        for (int i = 0; i < tileCount; i++) {
            bufferData.put((bufferDataOffset + 0) + 2, packedColor);
            bufferData.put((bufferDataOffset + 5) + 2, packedColor);
            bufferData.put((bufferDataOffset + 10) + 2, packedColor);
            bufferData.put((bufferDataOffset + 15) + 2, packedColor);
            bufferData.put((bufferDataOffset + 20) + 2, packedColor);
            bufferData.put((bufferDataOffset + 25) + 2, packedColor);
            bufferDataOffset += 30;
        }
        setDirtyOnHardware();
    }

    public void onUpdateVertices(TiledSprite pTiledSprite) {
        FloatBuffer bufferData = this.mFloatBuffer;
        float x2 = pTiledSprite.getWidth();
        float y2 = pTiledSprite.getHeight();
        int tileCount = pTiledSprite.getTileCount();
        int bufferDataOffset = 0;
        for (int i = 0; i < tileCount; i++) {
            bufferData.put((bufferDataOffset + 0) + 0, 0.0f);
            bufferData.put((bufferDataOffset + 0) + 1, 0.0f);
            bufferData.put((bufferDataOffset + 5) + 0, 0.0f);
            bufferData.put((bufferDataOffset + 5) + 1, y2);
            bufferData.put((bufferDataOffset + 10) + 0, x2);
            bufferData.put((bufferDataOffset + 10) + 1, 0.0f);
            bufferData.put((bufferDataOffset + 15) + 0, x2);
            bufferData.put((bufferDataOffset + 15) + 1, 0.0f);
            bufferData.put((bufferDataOffset + 20) + 0, 0.0f);
            bufferData.put((bufferDataOffset + 20) + 1, y2);
            bufferData.put((bufferDataOffset + 25) + 0, x2);
            bufferData.put((bufferDataOffset + 25) + 1, y2);
            bufferDataOffset += 30;
        }
        setDirtyOnHardware();
    }

    public void onUpdateTextureCoordinates(TiledSprite pTiledSprite) {
        FloatBuffer bufferData = this.mFloatBuffer;
        ITiledTextureRegion tiledTextureRegion = pTiledSprite.getTiledTextureRegion();
        int tileCount = pTiledSprite.getTileCount();
        int bufferDataOffset = 0;
        for (int i = 0; i < tileCount; i++) {
            float u;
            float u2;
            float v;
            float v2;
            ITextureRegion textureRegion = tiledTextureRegion.getTextureRegion(i);
            if (pTiledSprite.isFlippedVertical()) {
                if (pTiledSprite.isFlippedHorizontal()) {
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
            } else if (pTiledSprite.isFlippedHorizontal()) {
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
                bufferData.put((bufferDataOffset + 0) + 3, u2);
                bufferData.put((bufferDataOffset + 0) + 4, v);
                bufferData.put((bufferDataOffset + 5) + 3, u);
                bufferData.put((bufferDataOffset + 5) + 4, v);
                bufferData.put((bufferDataOffset + 10) + 3, u2);
                bufferData.put((bufferDataOffset + 10) + 4, v2);
                bufferData.put((bufferDataOffset + 15) + 3, u2);
                bufferData.put((bufferDataOffset + 15) + 4, v2);
                bufferData.put((bufferDataOffset + 20) + 3, u);
                bufferData.put((bufferDataOffset + 20) + 4, v);
                bufferData.put((bufferDataOffset + 25) + 3, u);
                bufferData.put((bufferDataOffset + 25) + 4, v2);
            } else {
                bufferData.put((bufferDataOffset + 0) + 3, u);
                bufferData.put((bufferDataOffset + 0) + 4, v);
                bufferData.put((bufferDataOffset + 5) + 3, u);
                bufferData.put((bufferDataOffset + 5) + 4, v2);
                bufferData.put((bufferDataOffset + 10) + 3, u2);
                bufferData.put((bufferDataOffset + 10) + 4, v);
                bufferData.put((bufferDataOffset + 15) + 3, u2);
                bufferData.put((bufferDataOffset + 15) + 4, v);
                bufferData.put((bufferDataOffset + 20) + 3, u);
                bufferData.put((bufferDataOffset + 20) + 4, v2);
                bufferData.put((bufferDataOffset + 25) + 3, u2);
                bufferData.put((bufferDataOffset + 25) + 4, v2);
            }
            bufferDataOffset += 30;
        }
        setDirtyOnHardware();
    }
}
