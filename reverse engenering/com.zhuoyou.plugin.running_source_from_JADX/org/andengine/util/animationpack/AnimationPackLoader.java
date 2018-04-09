package org.andengine.util.animationpack;

import android.content.res.AssetManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.util.StreamUtils;
import org.andengine.util.animationpack.exception.AnimationPackParseException;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class AnimationPackLoader {
    private final AssetManager mAssetManager;
    private final TextureManager mTextureManager;

    public AnimationPackLoader(AssetManager pAssetManager, TextureManager pTextureManager) {
        this.mAssetManager = pAssetManager;
        this.mTextureManager = pTextureManager;
    }

    public AnimationPack loadFromAsset(String pAssetPath, String pAssetBasePath) throws AnimationPackParseException {
        try {
            return load(this.mAssetManager.open(pAssetPath), pAssetBasePath);
        } catch (IOException e) {
            throw new AnimationPackParseException("Could not load " + getClass().getSimpleName() + " data from asset: " + pAssetPath, e);
        }
    }

    public AnimationPack load(InputStream pInputStream, String pAssetBasePath) throws AnimationPackParseException {
        try {
            XMLReader xr = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            AnimationPackParser animationPackParser = new AnimationPackParser(this.mAssetManager, pAssetBasePath, this.mTextureManager);
            xr.setContentHandler(animationPackParser);
            xr.parse(new InputSource(new BufferedInputStream(pInputStream)));
            AnimationPack animationPack = animationPackParser.getAnimationPack();
            StreamUtils.close(pInputStream);
            return animationPack;
        } catch (Exception e) {
            throw new AnimationPackParseException(e);
        } catch (ParserConfigurationException e2) {
            StreamUtils.close(pInputStream);
            return null;
        } catch (Exception e3) {
            throw new AnimationPackParseException(e3);
        } catch (Throwable th) {
            StreamUtils.close(pInputStream);
        }
    }
}
