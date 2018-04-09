package org.andengine.opengl.texture.compressed.pvr.pixelbufferstrategy;

import android.opengl.GLES20;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.texture.compressed.pvr.PVRTexture;
import org.andengine.opengl.texture.compressed.pvr.pixelbufferstrategy.IPVRTexturePixelBufferStrategy.IPVRTexturePixelBufferStrategyBufferManager;

public class GreedyPVRTexturePixelBufferStrategy implements IPVRTexturePixelBufferStrategy {

    public static class GreedyPVRTexturePixelBufferStrategyBufferManager implements IPVRTexturePixelBufferStrategyBufferManager {
        private final ByteBuffer mByteBuffer;

        public GreedyPVRTexturePixelBufferStrategyBufferManager(PVRTexture pPVRTexture) throws IOException {
            this.mByteBuffer = pPVRTexture.getPVRTextureBuffer();
        }

        public ByteBuffer getPixelBuffer(int pStart, int pByteCount) {
            this.mByteBuffer.position(pStart);
            this.mByteBuffer.limit(pStart + pByteCount);
            return this.mByteBuffer.slice();
        }
    }

    public IPVRTexturePixelBufferStrategyBufferManager newPVRTexturePixelBufferStrategyManager(PVRTexture pPVRTexture) throws IOException {
        return new GreedyPVRTexturePixelBufferStrategyBufferManager(pPVRTexture);
    }

    public void loadPVRTextureData(IPVRTexturePixelBufferStrategyBufferManager pPVRTexturePixelBufferStrategyManager, int pWidth, int pHeight, int pBytesPerPixel, PixelFormat pPixelFormat, int pLevel, int pCurrentPixelDataOffset, int pCurrentPixelDataSize) throws IOException {
        int i = pLevel;
        int i2 = pWidth;
        int i3 = pHeight;
        GLES20.glTexImage2D(3553, i, pPixelFormat.getGLInternalFormat(), i2, i3, 0, pPixelFormat.getGLFormat(), pPixelFormat.getGLType(), pPVRTexturePixelBufferStrategyManager.getPixelBuffer(pCurrentPixelDataOffset + 52, pCurrentPixelDataSize));
    }
}
