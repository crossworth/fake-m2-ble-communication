package org.andengine.opengl.texture.compressed.pvr;

import java.io.IOException;
import java.util.zip.GZIPInputStream;
import org.andengine.opengl.texture.ITextureStateListener;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.compressed.pvr.PVRTexture.PVRTextureFormat;
import org.andengine.opengl.texture.compressed.pvr.pixelbufferstrategy.IPVRTexturePixelBufferStrategy;

public abstract class PVRGZTexture extends PVRTexture {
    public PVRGZTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat) throws IllegalArgumentException, IOException {
        super(pTextureManager, pPVRTextureFormat);
    }

    public PVRGZTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, IPVRTexturePixelBufferStrategy pPVRTexturePixelBufferStrategy) throws IllegalArgumentException, IOException {
        super(pTextureManager, pPVRTextureFormat, pPVRTexturePixelBufferStrategy);
    }

    public PVRGZTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, ITextureStateListener pTextureStateListener) throws IllegalArgumentException, IOException {
        super(pTextureManager, pPVRTextureFormat, pTextureStateListener);
    }

    public PVRGZTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, IPVRTexturePixelBufferStrategy pPVRTexturePixelBufferStrategy, ITextureStateListener pTextureStateListener) throws IllegalArgumentException, IOException {
        super(pTextureManager, pPVRTextureFormat, pPVRTexturePixelBufferStrategy, pTextureStateListener);
    }

    public PVRGZTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, TextureOptions pTextureOptions) throws IllegalArgumentException, IOException {
        super(pTextureManager, pPVRTextureFormat, pTextureOptions);
    }

    public PVRGZTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, IPVRTexturePixelBufferStrategy pPVRTexturePixelBufferStrategy, TextureOptions pTextureOptions) throws IllegalArgumentException, IOException {
        super(pTextureManager, pPVRTextureFormat, pPVRTexturePixelBufferStrategy, pTextureOptions);
    }

    public PVRGZTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, TextureOptions pTextureOptions, ITextureStateListener pTextureStateListener) throws IllegalArgumentException, IOException {
        super(pTextureManager, pPVRTextureFormat, pTextureOptions, pTextureStateListener);
    }

    public PVRGZTexture(TextureManager pTextureManager, PVRTextureFormat pPVRTextureFormat, IPVRTexturePixelBufferStrategy pPVRTexturePixelBufferStrategy, TextureOptions pTextureOptions, ITextureStateListener pTextureStateListener) throws IllegalArgumentException, IOException {
        super(pTextureManager, pPVRTextureFormat, pPVRTexturePixelBufferStrategy, pTextureOptions, pTextureStateListener);
    }

    public GZIPInputStream getInputStream() throws IOException {
        return new GZIPInputStream(onGetInputStream());
    }
}
