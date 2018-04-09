package org.andengine.util.level;

import android.content.res.AssetManager;
import android.content.res.Resources;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.parsers.SAXParserFactory;
import org.andengine.util.StreamUtils;
import org.andengine.util.debug.Debug;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class LevelLoader {
    private String mAssetBasePath;
    private IEntityLoader mDefaultEntityLoader;
    private final HashMap<String, IEntityLoader> mEntityLoaders;

    public LevelLoader() {
        this("");
    }

    public LevelLoader(String pAssetBasePath) {
        this.mEntityLoaders = new HashMap();
        setAssetBasePath(pAssetBasePath);
    }

    public IEntityLoader getDefaultEntityLoader() {
        return this.mDefaultEntityLoader;
    }

    public void setDefaultEntityLoader(IEntityLoader pDefaultEntityLoader) {
        this.mDefaultEntityLoader = pDefaultEntityLoader;
    }

    public void setAssetBasePath(String pAssetBasePath) {
        if (pAssetBasePath.endsWith("/") || pAssetBasePath.length() == 0) {
            this.mAssetBasePath = pAssetBasePath;
            return;
        }
        throw new IllegalStateException("pAssetBasePath must end with '/' or be lenght zero.");
    }

    public String getAssetBasePath() {
        return this.mAssetBasePath;
    }

    protected void onAfterLoadLevel() {
    }

    protected void onBeforeLoadLevel() {
    }

    public void registerEntityLoader(String pEntityName, IEntityLoader pEntityLoader) {
        this.mEntityLoaders.put(pEntityName, pEntityLoader);
    }

    public void registerEntityLoader(String[] pEntityNames, IEntityLoader pEntityLoader) {
        HashMap<String, IEntityLoader> entityLoaders = this.mEntityLoaders;
        for (int i = pEntityNames.length - 1; i >= 0; i--) {
            entityLoaders.put(pEntityNames[i], pEntityLoader);
        }
    }

    public void loadLevelFromAsset(AssetManager pAssetManager, String pAssetPath) throws IOException {
        loadLevelFromStream(pAssetManager.open(this.mAssetBasePath + pAssetPath));
    }

    public void loadLevelFromResource(Resources pResources, int pRawResourceID) throws IOException {
        loadLevelFromStream(pResources.openRawResource(pRawResourceID));
    }

    public void loadLevelFromStream(InputStream pInputStream) throws IOException {
        try {
            XMLReader xr = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            onBeforeLoadLevel();
            xr.setContentHandler(new LevelParser(this.mDefaultEntityLoader, this.mEntityLoaders));
            xr.parse(new InputSource(new BufferedInputStream(pInputStream)));
            onAfterLoadLevel();
        } catch (Throwable se) {
            Debug.m4592e(se);
        } catch (Throwable pe) {
            Debug.m4592e(pe);
        } finally {
            StreamUtils.close(pInputStream);
        }
    }
}
