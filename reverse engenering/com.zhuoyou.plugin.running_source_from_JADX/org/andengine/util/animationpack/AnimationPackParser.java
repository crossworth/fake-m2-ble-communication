package org.andengine.util.animationpack;

import android.content.res.AssetManager;
import java.util.ArrayList;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.list.LongArrayList;
import org.andengine.util.animationpack.exception.AnimationPackParseException;
import org.andengine.util.texturepack.TexturePack;
import org.andengine.util.texturepack.TexturePackLibrary;
import org.andengine.util.texturepack.TexturePackLoader;
import org.andengine.util.texturepack.TexturePackTextureRegion;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AnimationPackParser extends DefaultHandler {
    private static final String TAG_ANIMATION = "animation";
    private static final String TAG_ANIMATIONFRAME = "animationframe";
    private static final String TAG_ANIMATIONFRAME_ATTRIBUTE_DURATION = "duration";
    private static final String TAG_ANIMATIONFRAME_ATTRIBUTE_TEXTUREREGION = "textureregion";
    private static final String TAG_ANIMATIONPACK = "animationpack";
    private static final String TAG_ANIMATIONPACK_ATTRIBUTE_VERSION = "version";
    private static final String TAG_ANIMATIONS = "animations";
    private static final String TAG_ANIMATION_ATTRIBUTE_LOOPCOUNT = "loopcount";
    private static final String TAG_ANIMATION_ATTRIBUTE_NAME = "name";
    private static final String TAG_TEXTUREPACK = "texturepack";
    private static final String TAG_TEXTUREPACKS = "texturepacks";
    private static final String TAG_TEXTUREPACK_ATTRIBUTE_FILENAME = "filename";
    private AnimationPack mAnimationPack;
    private AnimationPackTiledTextureRegionLibrary mAnimationPackTiledTextureRegionLibrary;
    private final String mAssetBasePath;
    private final AssetManager mAssetManager;
    private final LongArrayList mCurrentAnimationFrameDurations = new LongArrayList();
    private final ArrayList<TexturePackTextureRegion> mCurrentAnimationFrameTexturePackTextureRegions = new ArrayList();
    private int mCurrentAnimationLoopCount = -1;
    private String mCurrentAnimationName;
    private final TextureManager mTextureManager;
    private TexturePackLibrary mTexturePackLibrary;
    private TexturePackLoader mTexturePackLoader;

    public AnimationPackParser(AssetManager pAssetManager, String pAssetBasePath, TextureManager pTextureManager) {
        this.mAssetManager = pAssetManager;
        this.mAssetBasePath = pAssetBasePath;
        this.mTextureManager = pTextureManager;
    }

    public AnimationPack getAnimationPack() {
        return this.mAnimationPack;
    }

    public void startElement(String pUri, String pLocalName, String pQualifiedName, Attributes pAttributes) throws SAXException {
        if (pLocalName.equals(TAG_ANIMATIONPACK)) {
            int version = SAXUtils.getIntAttributeOrThrow(pAttributes, "version");
            if (version != 1) {
                throw new AnimationPackParseException("Unexpected version: '" + version + "'.");
            }
            this.mTexturePackLoader = new TexturePackLoader(this.mAssetManager, this.mTextureManager);
            this.mTexturePackLibrary = new TexturePackLibrary();
            this.mAnimationPackTiledTextureRegionLibrary = new AnimationPackTiledTextureRegionLibrary();
            this.mAnimationPack = new AnimationPack(this.mTexturePackLibrary, this.mAnimationPackTiledTextureRegionLibrary);
        } else if (!pLocalName.equals(TAG_TEXTUREPACKS)) {
            if (pLocalName.equals(TAG_TEXTUREPACK)) {
                String texturePackName = SAXUtils.getAttributeOrThrow(pAttributes, TAG_TEXTUREPACK_ATTRIBUTE_FILENAME);
                TexturePack texturePack = this.mTexturePackLoader.loadFromAsset(this.mAssetBasePath + texturePackName, this.mAssetBasePath);
                this.mTexturePackLibrary.put(texturePackName, texturePack);
                texturePack.loadTexture();
            } else if (!pLocalName.equals(TAG_ANIMATIONS)) {
                if (pLocalName.equals(TAG_ANIMATION)) {
                    this.mCurrentAnimationName = SAXUtils.getAttributeOrThrow(pAttributes, "name");
                    this.mCurrentAnimationLoopCount = SAXUtils.getIntAttribute(pAttributes, TAG_ANIMATION_ATTRIBUTE_LOOPCOUNT, -1);
                } else if (pLocalName.equals(TAG_ANIMATIONFRAME)) {
                    this.mCurrentAnimationFrameDurations.add((long) SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ANIMATIONFRAME_ATTRIBUTE_DURATION));
                    this.mCurrentAnimationFrameTexturePackTextureRegions.add(this.mTexturePackLibrary.getTexturePackTextureRegion(SAXUtils.getAttributeOrThrow(pAttributes, TAG_ANIMATIONFRAME_ATTRIBUTE_TEXTUREREGION)));
                } else {
                    throw new AnimationPackParseException("Unexpected tag: '" + pLocalName + "'.");
                }
            }
        }
    }

    public void endElement(String pUri, String pLocalName, String pQualifiedName) throws SAXException {
        if (!pLocalName.equals(TAG_ANIMATIONPACK) && !pLocalName.equals(TAG_TEXTUREPACKS) && !pLocalName.equals(TAG_TEXTUREPACK) && !pLocalName.equals(TAG_ANIMATIONS)) {
            if (pLocalName.equals(TAG_ANIMATION)) {
                int currentAnimationFrameFrameCount = this.mCurrentAnimationFrameDurations.size();
                long[] frameDurations = this.mCurrentAnimationFrameDurations.toArray();
                TexturePackTextureRegion[] textureRegions = new TexturePackTextureRegion[currentAnimationFrameFrameCount];
                this.mCurrentAnimationFrameTexturePackTextureRegions.toArray(textureRegions);
                this.mAnimationPackTiledTextureRegionLibrary.put(new AnimationPackTiledTextureRegion(this.mCurrentAnimationName, frameDurations, this.mCurrentAnimationLoopCount, textureRegions[0].getTexture(), textureRegions));
                this.mCurrentAnimationName = null;
                this.mCurrentAnimationLoopCount = -1;
                this.mCurrentAnimationFrameDurations.clear();
                this.mCurrentAnimationFrameTexturePackTextureRegions.clear();
            } else if (!pLocalName.equals(TAG_ANIMATIONFRAME)) {
                throw new AnimationPackParseException("Unexpected end tag: '" + pLocalName + "'.");
            }
        }
    }
}
