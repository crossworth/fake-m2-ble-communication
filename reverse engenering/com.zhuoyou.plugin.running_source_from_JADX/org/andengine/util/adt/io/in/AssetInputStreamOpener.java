package org.andengine.util.adt.io.in;

import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;

public class AssetInputStreamOpener implements IInputStreamOpener {
    private final AssetManager mAssetManager;
    private final String mAssetPath;

    public AssetInputStreamOpener(AssetManager pAssetManager, String pAssetPath) {
        this.mAssetManager = pAssetManager;
        this.mAssetPath = pAssetPath;
    }

    public InputStream open() throws IOException {
        return this.mAssetManager.open(this.mAssetPath);
    }
}
