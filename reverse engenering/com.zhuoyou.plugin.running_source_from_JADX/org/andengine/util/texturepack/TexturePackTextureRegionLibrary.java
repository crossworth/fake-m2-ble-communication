package org.andengine.util.texturepack;

import android.util.SparseArray;
import java.util.HashMap;

public class TexturePackTextureRegionLibrary {
    private final SparseArray<TexturePackTextureRegion> mIDMapping;
    private final HashMap<String, TexturePackTextureRegion> mSourceMapping;

    public TexturePackTextureRegionLibrary(int pInitialCapacity) {
        this.mIDMapping = new SparseArray(pInitialCapacity);
        this.mSourceMapping = new HashMap(pInitialCapacity);
    }

    public SparseArray<TexturePackTextureRegion> getIDMapping() {
        return this.mIDMapping;
    }

    public HashMap<String, TexturePackTextureRegion> getSourceMapping() {
        return this.mSourceMapping;
    }

    public void put(TexturePackTextureRegion pTexturePackTextureRegion) {
        throwOnCollision(pTexturePackTextureRegion);
        this.mIDMapping.put(pTexturePackTextureRegion.getID(), pTexturePackTextureRegion);
        this.mSourceMapping.put(pTexturePackTextureRegion.getSource(), pTexturePackTextureRegion);
    }

    public void remove(int pID) {
        this.mIDMapping.remove(pID);
    }

    public TexturePackTextureRegion get(int pID) {
        return (TexturePackTextureRegion) this.mIDMapping.get(pID);
    }

    public TexturePackTextureRegion get(String pSource) {
        return (TexturePackTextureRegion) this.mSourceMapping.get(pSource);
    }

    public TexturePackTextureRegion get(String pSource, boolean pStripExtension) {
        if (!pStripExtension) {
            return get(pSource);
        }
        int indexOfExtension = pSource.lastIndexOf(46);
        if (indexOfExtension == -1) {
            return get(pSource);
        }
        return (TexturePackTextureRegion) this.mSourceMapping.get(pSource.substring(0, indexOfExtension));
    }

    private void throwOnCollision(TexturePackTextureRegion pTexturePackTextureRegion) throws IllegalArgumentException {
        if (this.mIDMapping.get(pTexturePackTextureRegion.getID()) != null) {
            throw new IllegalArgumentException("Collision with ID: '" + pTexturePackTextureRegion.getID() + "'.");
        } else if (this.mSourceMapping.get(pTexturePackTextureRegion.getSource()) != null) {
            throw new IllegalArgumentException("Collision with Source: '" + pTexturePackTextureRegion.getSource() + "'.");
        }
    }
}
