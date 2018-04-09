package org.andengine.util.texturepack;

import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.PixelFormat;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.compressed.pvr.PVRCCZTexture;
import org.andengine.opengl.texture.compressed.pvr.PVRGZTexture;
import org.andengine.opengl.texture.compressed.pvr.PVRTexture;
import org.andengine.opengl.texture.compressed.pvr.PVRTexture.PVRTextureFormat;
import org.andengine.opengl.texture.compressed.pvr.pixelbufferstrategy.SmartPVRTexturePixelBufferStrategy;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.texturepack.exception.TexturePackParseException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TexturePackParser extends DefaultHandler {
    private static final String TAG_TEXTURE = "texture";
    private static final String TAG_TEXTUREREGION = "textureregion";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_HEIGHT = "height";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_ID = "id";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_ROTATED = "rotated";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_SOURCE = "src";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_SOURCE_HEIGHT = "srcheight";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_SOURCE_WIDTH = "srcwidth";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_SOURCE_X = "srcx";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_SOURCE_Y = "srcy";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_TRIMMED = "trimmed";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_WIDTH = "width";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_X = "x";
    private static final String TAG_TEXTUREREGION_ATTRIBUTE_Y = "y";
    private static final String TAG_TEXTURE_ATTRIBUTE_FILE = "file";
    private static final String TAG_TEXTURE_ATTRIBUTE_MAGFILTER = "magfilter";
    private static final String TAG_TEXTURE_ATTRIBUTE_MAGFILTER_VALUE_LINEAR = "linear";
    private static final String TAG_TEXTURE_ATTRIBUTE_MAGFILTER_VALUE_NEAREST = "nearest";
    private static final String TAG_TEXTURE_ATTRIBUTE_MINFILTER = "minfilter";
    private static final String TAG_TEXTURE_ATTRIBUTE_MINFILTER_VALUE_LINEAR = "linear";
    private static final String TAG_TEXTURE_ATTRIBUTE_MINFILTER_VALUE_LINEAR_MIPMAP_LINEAR = "linear_mipmap_linear";
    private static final String TAG_TEXTURE_ATTRIBUTE_MINFILTER_VALUE_LINEAR_MIPMAP_NEAREST = "linear_mipmap_nearest";
    private static final String TAG_TEXTURE_ATTRIBUTE_MINFILTER_VALUE_NEAREST = "nearest";
    private static final String TAG_TEXTURE_ATTRIBUTE_MINFILTER_VALUE_NEAREST_MIPMAP_LINEAR = "nearest_mipmap_linear";
    private static final String TAG_TEXTURE_ATTRIBUTE_MINFILTER_VALUE_NEAREST_MIPMAP_NEAREST = "nearest_mipmap_nearest";
    private static final String TAG_TEXTURE_ATTRIBUTE_PIXELFORMAT = "pixelformat";
    private static final String TAG_TEXTURE_ATTRIBUTE_PREMULTIPLYALPHA = "premultiplyalpha";
    private static final String TAG_TEXTURE_ATTRIBUTE_TYPE = "type";
    private static final String TAG_TEXTURE_ATTRIBUTE_TYPE_VALUE_BITMAP = "bitmap";
    private static final String TAG_TEXTURE_ATTRIBUTE_TYPE_VALUE_PVR = "pvr";
    private static final String TAG_TEXTURE_ATTRIBUTE_TYPE_VALUE_PVRCCZ = "pvrccz";
    private static final String TAG_TEXTURE_ATTRIBUTE_TYPE_VALUE_PVRGZ = "pvrgz";
    private static final String TAG_TEXTURE_ATTRIBUTE_VERSION = "version";
    private static final String TAG_TEXTURE_ATTRIBUTE_WRAPS = "wraps";
    private static final String TAG_TEXTURE_ATTRIBUTE_WRAPT = "wrapt";
    private static final String TAG_TEXTURE_ATTRIBUTE_WRAP_VALUE_CLAMP = "clamp";
    private static final String TAG_TEXTURE_ATTRIBUTE_WRAP_VALUE_CLAMP_TO_EDGE = "clamp_to_edge";
    private static final String TAG_TEXTURE_ATTRIBUTE_WRAP_VALUE_REPEAT = "repeat";
    private final String mAssetBasePath;
    private final AssetManager mAssetManager;
    private ITexture mTexture;
    private final TextureManager mTextureManager;
    private TexturePack mTexturePack;
    private TexturePackTextureRegionLibrary mTextureRegionLibrary;
    private int mVersion;

    public TexturePackParser(AssetManager pAssetManager, String pAssetBasePath, TextureManager pTextureManager) {
        this.mAssetManager = pAssetManager;
        this.mAssetBasePath = pAssetBasePath;
        this.mTextureManager = pTextureManager;
    }

    public TexturePack getTexturePack() {
        return this.mTexturePack;
    }

    public void startElement(String pUri, String pLocalName, String pQualifiedName, Attributes pAttributes) throws SAXException {
        if (pLocalName.equals(TAG_TEXTURE)) {
            this.mVersion = SAXUtils.getIntAttributeOrThrow(pAttributes, "version");
            this.mTexture = parseTexture(pAttributes);
            this.mTextureRegionLibrary = new TexturePackTextureRegionLibrary(10);
            this.mTexturePack = new TexturePack(this.mTexture, this.mTextureRegionLibrary);
            return;
        }
        if (pLocalName.equals(TAG_TEXTUREREGION)) {
            int id = SAXUtils.getIntAttributeOrThrow(pAttributes, "id");
            int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TEXTUREREGION_ATTRIBUTE_X);
            int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TEXTUREREGION_ATTRIBUTE_Y);
            int width = SAXUtils.getIntAttributeOrThrow(pAttributes, "width");
            int height = SAXUtils.getIntAttributeOrThrow(pAttributes, "height");
            String source = SAXUtils.getAttributeOrThrow(pAttributes, TAG_TEXTUREREGION_ATTRIBUTE_SOURCE);
            boolean trimmed = SAXUtils.getBooleanAttributeOrThrow(pAttributes, TAG_TEXTUREREGION_ATTRIBUTE_TRIMMED);
            boolean rotated = SAXUtils.getBooleanAttributeOrThrow(pAttributes, TAG_TEXTUREREGION_ATTRIBUTE_ROTATED);
            int sourceX = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TEXTUREREGION_ATTRIBUTE_SOURCE_X);
            int sourceY = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TEXTUREREGION_ATTRIBUTE_SOURCE_Y);
            int sourceWidth = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TEXTUREREGION_ATTRIBUTE_SOURCE_WIDTH);
            int sourceHeight = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TEXTUREREGION_ATTRIBUTE_SOURCE_HEIGHT);
            this.mTextureRegionLibrary.put(new TexturePackTextureRegion(this.mTexture, x, y, width, height, id, source, rotated, trimmed, sourceX, sourceY, sourceWidth, sourceHeight));
            return;
        }
        throw new TexturePackParseException("Unexpected tag: '" + pLocalName + "'.");
    }

    protected InputStream onGetInputStream(String pFilename) throws IOException {
        return this.mAssetManager.open(this.mAssetBasePath + pFilename);
    }

    private ITexture parseTexture(Attributes pAttributes) throws TexturePackParseException {
        final String file = SAXUtils.getAttributeOrThrow(pAttributes, TAG_TEXTURE_ATTRIBUTE_FILE);
        if (this.mTextureManager.hasMappedTexture(file)) {
            return this.mTextureManager.getMappedTexture(file);
        }
        ITexture texture;
        String type = SAXUtils.getAttributeOrThrow(pAttributes, "type");
        PixelFormat pixelFormat = parsePixelFormat(pAttributes);
        TextureOptions textureOptions = parseTextureOptions(pAttributes);
        if (type.equals(TAG_TEXTURE_ATTRIBUTE_TYPE_VALUE_BITMAP)) {
            try {
                texture = new BitmapTexture(this.mTextureManager, new IInputStreamOpener() {
                    public InputStream open() throws IOException {
                        return TexturePackParser.this.onGetInputStream(file);
                    }
                }, BitmapTextureFormat.fromPixelFormat(pixelFormat), textureOptions);
            } catch (Exception e) {
                throw new TexturePackParseException(e);
            }
        } else if (type.equals(TAG_TEXTURE_ATTRIBUTE_TYPE_VALUE_PVR)) {
            try {
                texture = new PVRTexture(this.mTextureManager, PVRTextureFormat.fromPixelFormat(pixelFormat), new SmartPVRTexturePixelBufferStrategy(131072), textureOptions) {
                    protected InputStream onGetInputStream() throws IOException {
                        return TexturePackParser.this.onGetInputStream(file);
                    }
                };
            } catch (Exception e2) {
                throw new TexturePackParseException(e2);
            }
        } else if (type.equals(TAG_TEXTURE_ATTRIBUTE_TYPE_VALUE_PVRGZ)) {
            try {
                texture = new PVRGZTexture(this.mTextureManager, PVRTextureFormat.fromPixelFormat(pixelFormat), new SmartPVRTexturePixelBufferStrategy(131072), textureOptions) {
                    protected InputStream onGetInputStream() throws IOException {
                        return TexturePackParser.this.onGetInputStream(file);
                    }
                };
            } catch (Exception e22) {
                throw new TexturePackParseException(e22);
            }
        } else if (type.equals(TAG_TEXTURE_ATTRIBUTE_TYPE_VALUE_PVRCCZ)) {
            try {
                texture = new PVRCCZTexture(this.mTextureManager, PVRTextureFormat.fromPixelFormat(pixelFormat), new SmartPVRTexturePixelBufferStrategy(131072), textureOptions) {
                    protected InputStream onGetInputStream() throws IOException {
                        return TexturePackParser.this.onGetInputStream(file);
                    }
                };
            } catch (Exception e222) {
                throw new TexturePackParseException(e222);
            }
        } else {
            throw new TexturePackParseException(new IllegalArgumentException("Unsupported pTextureFormat: '" + type + "'."));
        }
        this.mTextureManager.addMappedTexture(file, texture);
        return texture;
    }

    private static PixelFormat parsePixelFormat(Attributes pAttributes) {
        return PixelFormat.valueOf(SAXUtils.getAttributeOrThrow(pAttributes, TAG_TEXTURE_ATTRIBUTE_PIXELFORMAT));
    }

    private TextureOptions parseTextureOptions(Attributes pAttributes) {
        return new TextureOptions(parseMinFilter(pAttributes), parseMagFilter(pAttributes), parseWrapT(pAttributes), parseWrapS(pAttributes), parsePremultiplyalpha(pAttributes));
    }

    private static int parseMinFilter(Attributes pAttributes) {
        String minFilter = SAXUtils.getAttributeOrThrow(pAttributes, TAG_TEXTURE_ATTRIBUTE_MINFILTER);
        if (minFilter.equals("nearest")) {
            return 9728;
        }
        if (minFilter.equals("linear")) {
            return 9729;
        }
        if (minFilter.equals(TAG_TEXTURE_ATTRIBUTE_MINFILTER_VALUE_LINEAR_MIPMAP_LINEAR)) {
            return 9987;
        }
        if (minFilter.equals(TAG_TEXTURE_ATTRIBUTE_MINFILTER_VALUE_LINEAR_MIPMAP_NEAREST)) {
            return 9985;
        }
        if (minFilter.equals(TAG_TEXTURE_ATTRIBUTE_MINFILTER_VALUE_NEAREST_MIPMAP_LINEAR)) {
            return 9986;
        }
        if (minFilter.equals(TAG_TEXTURE_ATTRIBUTE_MINFILTER_VALUE_NEAREST_MIPMAP_NEAREST)) {
            return 9984;
        }
        throw new IllegalArgumentException("Unexpected minfilter attribute: '" + minFilter + "'.");
    }

    private static int parseMagFilter(Attributes pAttributes) {
        String magFilter = SAXUtils.getAttributeOrThrow(pAttributes, TAG_TEXTURE_ATTRIBUTE_MAGFILTER);
        if (magFilter.equals("nearest")) {
            return 9728;
        }
        if (magFilter.equals("linear")) {
            return 9729;
        }
        throw new IllegalArgumentException("Unexpected magfilter attribute: '" + magFilter + "'.");
    }

    private int parseWrapT(Attributes pAttributes) {
        return parseWrap(pAttributes, TAG_TEXTURE_ATTRIBUTE_WRAPT);
    }

    private int parseWrapS(Attributes pAttributes) {
        return parseWrap(pAttributes, TAG_TEXTURE_ATTRIBUTE_WRAPS);
    }

    private int parseWrap(Attributes pAttributes, String pWrapAttributeName) {
        String wrapAttribute = SAXUtils.getAttributeOrThrow(pAttributes, pWrapAttributeName);
        if ((this.mVersion == 1 && wrapAttribute.equals(TAG_TEXTURE_ATTRIBUTE_WRAP_VALUE_CLAMP)) || wrapAttribute.equals(TAG_TEXTURE_ATTRIBUTE_WRAP_VALUE_CLAMP_TO_EDGE)) {
            return 33071;
        }
        if (wrapAttribute.equals(TAG_TEXTURE_ATTRIBUTE_WRAP_VALUE_REPEAT)) {
            return 10497;
        }
        throw new IllegalArgumentException("Unexpected " + pWrapAttributeName + " attribute: '" + wrapAttribute + "'.");
    }

    private static boolean parsePremultiplyalpha(Attributes pAttributes) {
        return SAXUtils.getBooleanAttributeOrThrow(pAttributes, TAG_TEXTURE_ATTRIBUTE_PREMULTIPLYALPHA);
    }
}
