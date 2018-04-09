package org.andengine.util.adt.io.in;

import android.content.res.Resources;
import java.io.IOException;
import java.io.InputStream;

public class ResourceInputStreamOpener implements IInputStreamOpener {
    private final int mResourceID;
    private final Resources mResources;

    public ResourceInputStreamOpener(Resources pResources, int pResourceID) {
        this.mResources = pResources;
        this.mResourceID = pResourceID;
    }

    public InputStream open() throws IOException {
        return this.mResources.openRawResource(this.mResourceID);
    }
}
