package org.andengine.opengl.texture.compressed.pvr.pixelbufferstrategy;

import android.opengl.GLES20;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.texture.compressed.pvr.PVRTexture;
import org.andengine.opengl.texture.compressed.pvr.pixelbufferstrategy.IPVRTexturePixelBufferStrategy.IPVRTexturePixelBufferStrategyBufferManager;
import org.andengine.util.StreamUtils;
import org.andengine.util.exception.AndEngineRuntimeException;

public class SmartPVRTexturePixelBufferStrategy implements IPVRTexturePixelBufferStrategy {
    private final int mAllocationSizeMaximum;

    public static class SmartPVRTexturePixelBufferStrategyBufferManager implements IPVRTexturePixelBufferStrategyBufferManager {
        private byte[] mData;
        private final InputStream mInputStream;
        private int mInputStreamPosition;

        public SmartPVRTexturePixelBufferStrategyBufferManager(PVRTexture pPVRTexture) throws IOException {
            this.mInputStream = pPVRTexture.getInputStream();
        }

        public ByteBuffer getPixelBuffer(int pStart, int pByteCount) throws IOException {
            if (pStart < this.mInputStreamPosition) {
                throw new AndEngineRuntimeException("Cannot read data that has been read already. (pStart: '" + pStart + "', this.mInputStreamPosition: '" + this.mInputStreamPosition + "')");
            }
            if (this.mData == null || this.mData.length < pByteCount) {
                this.mData = new byte[pByteCount];
            }
            if (this.mInputStreamPosition < pStart) {
                int bytesToSkip = pStart - this.mInputStreamPosition;
                long skipped = this.mInputStream.skip((long) bytesToSkip);
                this.mInputStreamPosition = (int) (((long) this.mInputStreamPosition) + skipped);
                if (((long) bytesToSkip) != skipped) {
                    throw new AndEngineRuntimeException("Skipped: '" + skipped + "' instead of '" + bytesToSkip + "'.");
                }
            }
            int bytesToRead = (pStart + pByteCount) - this.mInputStreamPosition;
            StreamUtils.streamToBytes(this.mInputStream, bytesToRead, this.mData);
            this.mInputStreamPosition += bytesToRead;
            return ByteBuffer.wrap(this.mData, 0, pByteCount);
        }
    }

    public SmartPVRTexturePixelBufferStrategy(int pAllocationSizeMaximum) {
        this.mAllocationSizeMaximum = pAllocationSizeMaximum;
    }

    public IPVRTexturePixelBufferStrategyBufferManager newPVRTexturePixelBufferStrategyManager(PVRTexture pPVRTexture) throws IOException {
        return new SmartPVRTexturePixelBufferStrategyBufferManager(pPVRTexture);
    }

    public void loadPVRTextureData(IPVRTexturePixelBufferStrategyBufferManager pPVRTexturePixelBufferStrategyManager, int pWidth, int pHeight, int pBytesPerPixel, PixelFormat pPixelFormat, int pLevel, int pCurrentPixelDataOffset, int pCurrentPixelDataSize) throws IOException {
        int glFormat = pPixelFormat.getGLFormat();
        int glType = pPixelFormat.getGLType();
        GLES20.glTexImage2D(3553, pLevel, pPixelFormat.getGLInternalFormat(), pWidth, pHeight, 0, glFormat, glType, null);
        int bytesPerRow = pWidth * pBytesPerPixel;
        int stripeHeight = Math.max(1, this.mAllocationSizeMaximum / bytesPerRow);
        int currentStripePixelDataOffset = pCurrentPixelDataOffset;
        int currentStripeOffsetY = 0;
        while (currentStripeOffsetY < pHeight) {
            int currentStripeHeight = Math.min(pHeight - currentStripeOffsetY, stripeHeight);
            int currentStripePixelDataSize = currentStripeHeight * bytesPerRow;
            GLES20.glTexSubImage2D(3553, pLevel, 0, currentStripeOffsetY, pWidth, currentStripeHeight, glFormat, glType, pPVRTexturePixelBufferStrategyManager.getPixelBuffer(currentStripePixelDataOffset + 52, currentStripePixelDataSize));
            currentStripePixelDataOffset += currentStripePixelDataSize;
            currentStripeOffsetY += currentStripeHeight;
        }
    }
}
