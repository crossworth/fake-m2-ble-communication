package org.andengine.opengl.texture;

import android.content.res.AssetManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.io.in.AssetInputStreamOpener;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

public class TextureManager {
    private TextureWarmUpVertexBufferObject mTextureWarmUpVertexBufferObject;
    private final ArrayList<ITexture> mTexturesLoaded = new ArrayList();
    private final HashSet<ITexture> mTexturesManaged = new HashSet();
    private final HashMap<String, ITexture> mTexturesMapped = new HashMap();
    private final ArrayList<ITexture> mTexturesToBeLoaded = new ArrayList();
    private final ArrayList<ITexture> mTexturesToBeUnloaded = new ArrayList();

    public synchronized void onCreate() {
        this.mTextureWarmUpVertexBufferObject = new TextureWarmUpVertexBufferObject();
    }

    public synchronized void onReload() {
        HashSet<ITexture> managedTextures = this.mTexturesManaged;
        if (!managedTextures.isEmpty()) {
            Iterator it = managedTextures.iterator();
            while (it.hasNext()) {
                ((ITexture) it.next()).setNotLoadedToHardware();
            }
        }
        if (!this.mTexturesLoaded.isEmpty()) {
            this.mTexturesToBeLoaded.addAll(this.mTexturesLoaded);
            this.mTexturesLoaded.clear();
        }
        if (!this.mTexturesToBeUnloaded.isEmpty()) {
            this.mTexturesManaged.removeAll(this.mTexturesToBeUnloaded);
            this.mTexturesToBeUnloaded.clear();
        }
        this.mTextureWarmUpVertexBufferObject.setNotLoadedToHardware();
    }

    public synchronized void onDestroy() {
        Iterator it = this.mTexturesManaged.iterator();
        while (it.hasNext()) {
            ((ITexture) it.next()).setNotLoadedToHardware();
        }
        this.mTexturesToBeLoaded.clear();
        this.mTexturesLoaded.clear();
        this.mTexturesManaged.clear();
        this.mTexturesMapped.clear();
        this.mTextureWarmUpVertexBufferObject.dispose();
        this.mTextureWarmUpVertexBufferObject = null;
    }

    public synchronized boolean hasMappedTexture(String pID) {
        if (pID == null) {
            throw new IllegalArgumentException("pID must not be null!");
        }
        return this.mTexturesMapped.containsKey(pID);
    }

    public synchronized ITexture getMappedTexture(String pID) {
        if (pID == null) {
            throw new IllegalArgumentException("pID must not be null!");
        }
        return (ITexture) this.mTexturesMapped.get(pID);
    }

    public synchronized void addMappedTexture(String pID, ITexture pTexture) throws IllegalArgumentException {
        if (pID == null) {
            throw new IllegalArgumentException("pID must not be null!");
        } else if (pTexture == null) {
            throw new IllegalArgumentException("pTexture must not be null!");
        } else if (this.mTexturesMapped.containsKey(pID)) {
            throw new IllegalArgumentException("Collision for pID: '" + pID + "'.");
        } else {
            this.mTexturesMapped.put(pID, pTexture);
        }
    }

    public synchronized ITexture removedMappedTexture(String pID) {
        if (pID == null) {
            throw new IllegalArgumentException("pID must not be null!");
        }
        return (ITexture) this.mTexturesMapped.remove(pID);
    }

    public synchronized boolean loadTexture(ITexture pTexture) {
        boolean z;
        if (pTexture == null) {
            throw new IllegalArgumentException("pTexture must not be null!");
        } else if (this.mTexturesManaged.contains(pTexture)) {
            this.mTexturesToBeUnloaded.remove(pTexture);
            z = false;
        } else {
            this.mTexturesManaged.add(pTexture);
            this.mTexturesToBeLoaded.add(pTexture);
            z = true;
        }
        return z;
    }

    public synchronized boolean loadTexture(GLState pGLState, ITexture pTexture) throws IOException {
        boolean z;
        if (pTexture == null) {
            throw new IllegalArgumentException("pTexture must not be null!");
        }
        if (!pTexture.isLoadedToHardware()) {
            pTexture.loadToHardware(pGLState);
        } else if (pTexture.isUpdateOnHardwareNeeded()) {
            pTexture.reloadToHardware(pGLState);
        }
        if (this.mTexturesManaged.contains(pTexture)) {
            this.mTexturesToBeUnloaded.remove(pTexture);
            z = false;
        } else {
            this.mTexturesManaged.add(pTexture);
            this.mTexturesLoaded.add(pTexture);
            z = true;
        }
        return z;
    }

    public synchronized boolean unloadTexture(ITexture pTexture) {
        boolean z;
        if (pTexture == null) {
            throw new IllegalArgumentException("pTexture must not be null!");
        } else if (this.mTexturesManaged.contains(pTexture)) {
            if (this.mTexturesLoaded.contains(pTexture)) {
                this.mTexturesToBeUnloaded.add(pTexture);
            } else if (this.mTexturesToBeLoaded.remove(pTexture)) {
                this.mTexturesManaged.remove(pTexture);
            }
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public synchronized boolean unloadTexture(GLState pGLState, ITexture pTexture) {
        boolean z;
        if (pTexture == null) {
            throw new IllegalArgumentException("pTexture must not be null!");
        }
        if (pTexture.isLoadedToHardware()) {
            pTexture.unloadFromHardware(pGLState);
        }
        if (this.mTexturesManaged.contains(pTexture)) {
            this.mTexturesLoaded.remove(pTexture);
            this.mTexturesToBeLoaded.remove(pTexture);
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public synchronized void updateTextures(GLState pGLState) {
        int i;
        HashSet<ITexture> texturesManaged = this.mTexturesManaged;
        ArrayList<ITexture> texturesLoaded = this.mTexturesLoaded;
        ArrayList<ITexture> texturesToBeLoaded = this.mTexturesToBeLoaded;
        ArrayList<ITexture> texturesToBeUnloaded = this.mTexturesToBeUnloaded;
        for (i = texturesLoaded.size() - 1; i >= 0; i--) {
            ITexture textureToBeReloaded = (ITexture) texturesLoaded.get(i);
            if (textureToBeReloaded.isUpdateOnHardwareNeeded()) {
                try {
                    textureToBeReloaded.reloadToHardware(pGLState);
                } catch (Throwable e) {
                    Debug.m4592e(e);
                }
            }
        }
        int texturesToBeLoadedCount = texturesToBeLoaded.size();
        if (texturesToBeLoadedCount > 0) {
            for (i = texturesToBeLoadedCount - 1; i >= 0; i--) {
                ITexture textureToBeLoaded = (ITexture) texturesToBeLoaded.remove(i);
                if (!textureToBeLoaded.isLoadedToHardware()) {
                    try {
                        textureToBeLoaded.loadToHardware(pGLState);
                        this.mTextureWarmUpVertexBufferObject.warmup(pGLState, textureToBeLoaded);
                    } catch (Throwable e2) {
                        Debug.m4592e(e2);
                    }
                }
                texturesLoaded.add(textureToBeLoaded);
            }
        }
        int texturesToBeUnloadedCount = texturesToBeUnloaded.size();
        if (texturesToBeUnloadedCount > 0) {
            for (i = texturesToBeUnloadedCount - 1; i >= 0; i--) {
                ITexture textureToBeUnloaded = (ITexture) texturesToBeUnloaded.remove(i);
                if (textureToBeUnloaded.isLoadedToHardware()) {
                    textureToBeUnloaded.unloadFromHardware(pGLState);
                }
                texturesLoaded.remove(textureToBeUnloaded);
                texturesManaged.remove(textureToBeUnloaded);
            }
        }
        if (texturesToBeLoadedCount > 0 || texturesToBeUnloadedCount > 0) {
            System.gc();
        }
    }

    public synchronized ITexture getTexture(String pID, AssetManager pAssetManager, String pAssetPath) throws IOException {
        return getTexture(pID, pAssetManager, pAssetPath, TextureOptions.DEFAULT);
    }

    public synchronized ITexture getTexture(String pID, AssetManager pAssetManager, String pAssetPath, TextureOptions pTextureOptions) throws IOException {
        ITexture mappedTexture;
        if (hasMappedTexture(pID)) {
            mappedTexture = getMappedTexture(pID);
        } else {
            mappedTexture = new BitmapTexture(this, new AssetInputStreamOpener(pAssetManager, pAssetPath), pTextureOptions);
            loadTexture(mappedTexture);
            addMappedTexture(pID, mappedTexture);
        }
        return mappedTexture;
    }

    public synchronized ITexture getTexture(String pID, IInputStreamOpener pInputStreamOpener) throws IOException {
        return getTexture(pID, pInputStreamOpener, TextureOptions.DEFAULT);
    }

    public synchronized ITexture getTexture(String pID, IInputStreamOpener pInputStreamOpener, TextureOptions pTextureOptions) throws IOException {
        return getTexture(pID, pInputStreamOpener, BitmapTextureFormat.RGBA_8888, pTextureOptions);
    }

    public synchronized ITexture getTexture(String pID, IInputStreamOpener pInputStreamOpener, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions) throws IOException {
        return getTexture(pID, pInputStreamOpener, pBitmapTextureFormat, pTextureOptions, true);
    }

    public synchronized ITexture getTexture(String pID, IInputStreamOpener pInputStreamOpener, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, boolean pLoadToHardware) throws IOException {
        ITexture mappedTexture;
        if (hasMappedTexture(pID)) {
            mappedTexture = getMappedTexture(pID);
        } else {
            mappedTexture = new BitmapTexture(this, pInputStreamOpener, pBitmapTextureFormat, pTextureOptions);
            if (pLoadToHardware) {
                loadTexture(mappedTexture);
            }
            addMappedTexture(pID, mappedTexture);
        }
        return mappedTexture;
    }
}
