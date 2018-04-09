package org.andengine.opengl.texture.compressed.pvr;

import android.opengl.GLES20;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.andengine.opengl.texture.ITextureStateListener;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.compressed.pvr.pixelbufferstrategy.GreedyPVRTexturePixelBufferStrategy;
import org.andengine.opengl.texture.compressed.pvr.pixelbufferstrategy.IPVRTexturePixelBufferStrategy;
import org.andengine.opengl.texture.compressed.pvr.pixelbufferstrategy.IPVRTexturePixelBufferStrategy.IPVRTexturePixelBufferStrategyBufferManager;
import org.andengine.opengl.util.GLState;
import org.andengine.util.StreamUtils;
import org.andengine.util.adt.array.ArrayUtils;
import org.andengine.util.adt.io.out.ByteBufferOutputStream;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;

public abstract class PVRTexture extends Texture {
    public static final int FLAG_ALPHA = 32768;
    public static final int FLAG_BUMPMAP = 1024;
    public static final int FLAG_CUBEMAP = 4096;
    public static final int FLAG_FALSEMIPCOL = 8192;
    public static final int FLAG_MIPMAP = 256;
    public static final int FLAG_TILING = 2048;
    public static final int FLAG_TWIDDLE = 512;
    public static final int FLAG_VERTICALFLIP = 65536;
    public static final int FLAG_VOLUME = 16384;
    private final PVRTextureHeader mPVRTextureHeader;
    private final IPVRTexturePixelBufferStrategy mPVRTexturePixelBufferStrategy;

    public enum PVRTextureFormat {
        RGBA_4444(16, false, PixelFormat.RGBA_4444),
        RGBA_5551(17, false, PixelFormat.RGBA_5551),
        RGBA_8888(18, false, PixelFormat.RGBA_8888),
        RGB_565(19, false, PixelFormat.RGB_565),
        I_8(22, false, PixelFormat.I_8),
        AI_88(23, false, PixelFormat.AI_88),
        A_8(27, false, PixelFormat.A_8);
        
        private final boolean mCompressed;
        private final int mID;
        private final PixelFormat mPixelFormat;

        private PVRTextureFormat(int pID, boolean pCompressed, PixelFormat pPixelFormat) {
            this.mID = pID;
            this.mCompressed = pCompressed;
            this.mPixelFormat = pPixelFormat;
        }

        public static PVRTextureFormat fromID(int pID) {
            for (PVRTextureFormat pvrTextureFormat : values()) {
                if (pvrTextureFormat.mID == pID) {
                    return pvrTextureFormat;
                }
            }
            throw new IllegalArgumentException("Unexpected " + PVRTextureFormat.class.getSimpleName() + "-ID: '" + pID + "'.");
        }

        public static PVRTextureFormat fromPixelFormat(PixelFormat pPixelFormat) throws IllegalArgumentException {
            switch (pPixelFormat) {
                case RGBA_8888:
                    return RGBA_8888;
                case RGBA_4444:
                    return RGBA_4444;
                case RGB_565:
                    return RGB_565;
                default:
                    throw new IllegalArgumentException("Unsupported " + PixelFormat.class.getName() + ": '" + pPixelFormat + "'.");
            }
        }

        public int getID() {
            return this.mID;
        }

        public boolean isCompressed() {
            return this.mCompressed;
        }

        public PixelFormat getPixelFormat() {
            return this.mPixelFormat;
        }
    }

    public static class PVRTextureHeader {
        private static final int FORMAT_FLAG_MASK = 255;
        static final byte[] MAGIC_IDENTIFIER = new byte[]{(byte) 80, (byte) 86, (byte) 82, (byte) 33};
        public static final int SIZE = 52;
        private final ByteBuffer mDataByteBuffer;
        private final PVRTextureFormat mPVRTextureFormat;

        public PVRTextureHeader(byte[] pData) {
            this.mDataByteBuffer = ByteBuffer.wrap(pData);
            this.mDataByteBuffer.rewind();
            this.mDataByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            if (ArrayUtils.equals(pData, 44, MAGIC_IDENTIFIER, 0, MAGIC_IDENTIFIER.length)) {
                this.mPVRTextureFormat = PVRTextureFormat.fromID(getFlags() & 255);
                return;
            }
            throw new IllegalArgumentException("Invalid " + getClass().getSimpleName() + "!");
        }

        public PVRTextureFormat getPVRTextureFormat() {
            return this.mPVRTextureFormat;
        }

        public int headerLength() {
            return this.mDataByteBuffer.getInt(0);
        }

        public int getHeight() {
            return this.mDataByteBuffer.getInt(4);
        }

        public int getWidth() {
            return this.mDataByteBuffer.getInt(8);
        }

        public int getNumMipmaps() {
            return this.mDataByteBuffer.getInt(12);
        }

        public int getFlags() {
            return this.mDataByteBuffer.getInt(16);
        }

        public int getDataLength() {
            return this.mDataByteBuffer.getInt(20);
        }

        public int getBitsPerPixel() {
            return this.mDataByteBuffer.getInt(24);
        }

        public int getBitmaskRed() {
            return this.mDataByteBuffer.getInt(28);
        }

        public int getBitmaskGreen() {
            return this.mDataByteBuffer.getInt(32);
        }

        public int getBitmaskBlue() {
            return this.mDataByteBuffer.getInt(36);
        }

        public int getBitmaskAlpha() {
            return this.mDataByteBuffer.getInt(40);
        }

        public boolean hasAlpha() {
            return getBitmaskAlpha() != 0;
        }

        public int getPVRTag() {
            return this.mDataByteBuffer.getInt(44);
        }

        public int numSurfs() {
            return this.mDataByteBuffer.getInt(48);
        }
    }

    protected abstract InputStream onGetInputStream() throws IOException;

    public PVRTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat) throws IllegalArgumentException, IOException {
        this(pTextureManager, pPVRTextureFormat, new GreedyPVRTexturePixelBufferStrategy(), TextureOptions.DEFAULT, null);
    }

    public PVRTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, IPVRTexturePixelBufferStrategy pPVRTexturePixelBufferStrategy) throws IllegalArgumentException, IOException {
        this(pTextureManager, pPVRTextureFormat, pPVRTexturePixelBufferStrategy, TextureOptions.DEFAULT, null);
    }

    public PVRTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, ITextureStateListener pTextureStateListener) throws IllegalArgumentException, IOException {
        this(pTextureManager, pPVRTextureFormat, new GreedyPVRTexturePixelBufferStrategy(), TextureOptions.DEFAULT, pTextureStateListener);
    }

    public PVRTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, IPVRTexturePixelBufferStrategy pPVRTexturePixelBufferStrategy, ITextureStateListener pTextureStateListener) throws IllegalArgumentException, IOException {
        this(pTextureManager, pPVRTextureFormat, pPVRTexturePixelBufferStrategy, TextureOptions.DEFAULT, pTextureStateListener);
    }

    public PVRTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, TextureOptions pTextureOptions) throws IllegalArgumentException, IOException {
        this(pTextureManager, pPVRTextureFormat, new GreedyPVRTexturePixelBufferStrategy(), pTextureOptions, null);
    }

    public PVRTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, IPVRTexturePixelBufferStrategy pPVRTexturePixelBufferStrategy, TextureOptions pTextureOptions) throws IllegalArgumentException, IOException {
        this(pTextureManager, pPVRTextureFormat, pPVRTexturePixelBufferStrategy, pTextureOptions, null);
    }

    public PVRTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, TextureOptions pTextureOptions, ITextureStateListener pTextureStateListener) throws IllegalArgumentException, IOException {
        this(pTextureManager, pPVRTextureFormat, new GreedyPVRTexturePixelBufferStrategy(), pTextureOptions, pTextureStateListener);
    }

    public PVRTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, IPVRTexturePixelBufferStrategy pPVRTexturePixelBufferStrategy, TextureOptions pTextureOptions, ITextureStateListener pTextureStateListener) throws IllegalArgumentException, IOException {
        super(pTextureManager, pPVRTextureFormat.getPixelFormat(), pTextureOptions, pTextureStateListener);
        this.mPVRTexturePixelBufferStrategy = pPVRTexturePixelBufferStrategy;
        InputStream inputStream = null;
        try {
            inputStream = getInputStream();
            this.mPVRTextureHeader = new PVRTextureHeader(StreamUtils.streamToBytes(inputStream, 52));
            if (this.mPVRTextureHeader.getPVRTextureFormat().getPixelFormat() != pPVRTextureFormat.getPixelFormat()) {
                throw new IllegalArgumentException("Other PVRTextureFormat: '" + this.mPVRTextureHeader.getPVRTextureFormat().getPixelFormat() + "' found than expected: '" + pPVRTextureFormat.getPixelFormat() + "'.");
            } else if (this.mPVRTextureHeader.getPVRTextureFormat().isCompressed()) {
                throw new IllegalArgumentException("Invalid PVRTextureFormat: '" + this.mPVRTextureHeader.getPVRTextureFormat() + "'.");
            } else {
                if (hasMipMaps()) {
                    switch (pTextureOptions.mMinFilter) {
                    }
                }
                this.mUpdateOnHardwareNeeded = true;
            }
        } finally {
            StreamUtils.close(inputStream);
        }
    }

    public int getWidth() {
        return this.mPVRTextureHeader.getWidth();
    }

    public int getHeight() {
        return this.mPVRTextureHeader.getHeight();
    }

    public boolean hasMipMaps() {
        return this.mPVRTextureHeader.getNumMipmaps() > 0;
    }

    public PVRTextureHeader getPVRTextureHeader() {
        return this.mPVRTextureHeader;
    }

    public InputStream getInputStream() throws IOException {
        return onGetInputStream();
    }

    protected void writeTextureToHardware(GLState pGLState) throws IOException {
        IPVRTexturePixelBufferStrategyBufferManager pvrTextureLoadStrategyManager = this.mPVRTexturePixelBufferStrategy.newPVRTexturePixelBufferStrategyManager(this);
        int width = getWidth();
        int height = getHeight();
        int dataLength = this.mPVRTextureHeader.getDataLength();
        int bytesPerPixel = this.mPVRTextureHeader.getBitsPerPixel() / 8;
        GLES20.glPixelStorei(3317, 1);
        int currentLevel = 0;
        int currentPixelDataOffset = 0;
        while (currentPixelDataOffset < dataLength) {
            if (currentLevel > 0 && !(width == height && MathUtils.nextPowerOfTwo(width) == width)) {
                Debug.m4601w("Mipmap level '" + currentLevel + "' is not squared. Width: '" + width + "', height: '" + height + "'. Texture won't render correctly.");
            }
            int currentPixelDataSize = (height * width) * bytesPerPixel;
            this.mPVRTexturePixelBufferStrategy.loadPVRTextureData(pvrTextureLoadStrategyManager, width, height, bytesPerPixel, this.mPixelFormat, currentLevel, currentPixelDataOffset, currentPixelDataSize);
            currentPixelDataOffset += currentPixelDataSize;
            width = Math.max(width / 2, 1);
            height = Math.max(height / 2, 1);
            currentLevel++;
        }
        GLES20.glPixelStorei(3317, 4);
    }

    public ByteBuffer getPVRTextureBuffer() throws IOException {
        InputStream inputStream = getInputStream();
        try {
            OutputStream os = new ByteBufferOutputStream(1024, 524288);
            StreamUtils.copy(inputStream, os);
            ByteBuffer toByteBuffer = os.toByteBuffer();
            return toByteBuffer;
        } finally {
            StreamUtils.close(inputStream);
        }
    }
}
