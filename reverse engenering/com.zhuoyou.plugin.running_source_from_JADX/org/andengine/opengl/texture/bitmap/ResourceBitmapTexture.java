package org.andengine.opengl.texture.bitmap;

import android.content.res.Resources;
import java.io.IOException;
import org.andengine.opengl.texture.ITextureStateListener;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.adt.io.in.ResourceInputStreamOpener;

public class ResourceBitmapTexture extends BitmapTexture {
    public ResourceBitmapTexture(TextureManager pTextureManager, Resources pResources, int pDrawableResourceID) throws IOException {
        super(pTextureManager, new ResourceInputStreamOpener(pResources, pDrawableResourceID));
    }

    public ResourceBitmapTexture(TextureManager pTextureManager, Resources pResources, int pDrawableResourceID, BitmapTextureFormat pBitmapTextureFormat) throws IOException {
        super(pTextureManager, new ResourceInputStreamOpener(pResources, pDrawableResourceID), pBitmapTextureFormat);
    }

    public ResourceBitmapTexture(TextureManager pTextureManager, Resources pResources, int pDrawableResourceID, TextureOptions pTextureOptions) throws IOException {
        super(pTextureManager, new ResourceInputStreamOpener(pResources, pDrawableResourceID), pTextureOptions);
    }

    public ResourceBitmapTexture(TextureManager pTextureManager, Resources pResources, int pDrawableResourceID, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions) throws IOException {
        super(pTextureManager, new ResourceInputStreamOpener(pResources, pDrawableResourceID), pBitmapTextureFormat, pTextureOptions);
    }

    public ResourceBitmapTexture(TextureManager pTextureManager, Resources pResources, int pDrawableResourceID, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, ITextureStateListener pTextureStateListener) throws IOException {
        super(pTextureManager, new ResourceInputStreamOpener(pResources, pDrawableResourceID), pBitmapTextureFormat, pTextureOptions, pTextureStateListener);
    }
}
