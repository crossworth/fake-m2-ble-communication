package org.andengine.opengl.font;

import android.content.res.AssetManager;
import android.util.SparseArray;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.umeng.facebook.share.internal.ShareConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.andengine.opengl.font.exception.FontException;
import org.andengine.opengl.font.exception.LetterNotFoundException;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.util.StreamUtils;
import org.andengine.util.TextUtils;
import org.andengine.util.adt.io.in.AssetInputStreamOpener;

public class BitmapFont implements IFont {
    private static final String TAG_CHAR = "char";
    private static final String TAG_CHARS = "chars";
    private static final int TAG_CHARS_ATTRIBUTECOUNT = 1;
    private static final String TAG_CHARS_ATTRIBUTE_COUNT = "count";
    private static final int TAG_CHARS_ATTRIBUTE_COUNT_INDEX = 1;
    private static final int TAG_CHAR_ATTRIBUTECOUNT = 10;
    private static final String TAG_CHAR_ATTRIBUTE_HEIGHT = "height";
    private static final int TAG_CHAR_ATTRIBUTE_HEIGHT_INDEX = 5;
    private static final String TAG_CHAR_ATTRIBUTE_ID = "id";
    private static final int TAG_CHAR_ATTRIBUTE_ID_INDEX = 1;
    private static final String TAG_CHAR_ATTRIBUTE_PAGE = "page";
    private static final int TAG_CHAR_ATTRIBUTE_PAGE_INDEX = 9;
    private static final String TAG_CHAR_ATTRIBUTE_WIDTH = "width";
    private static final int TAG_CHAR_ATTRIBUTE_WIDTH_INDEX = 4;
    private static final String TAG_CHAR_ATTRIBUTE_X = "x";
    private static final String TAG_CHAR_ATTRIBUTE_XADVANCE = "xadvance";
    private static final int TAG_CHAR_ATTRIBUTE_XADVANCE_INDEX = 8;
    private static final String TAG_CHAR_ATTRIBUTE_XOFFSET = "xoffset";
    private static final int TAG_CHAR_ATTRIBUTE_XOFFSET_INDEX = 6;
    private static final int TAG_CHAR_ATTRIBUTE_X_INDEX = 2;
    private static final String TAG_CHAR_ATTRIBUTE_Y = "y";
    private static final String TAG_CHAR_ATTRIBUTE_YOFFSET = "yoffset";
    private static final int TAG_CHAR_ATTRIBUTE_YOFFSET_INDEX = 7;
    private static final int TAG_CHAR_ATTRIBUTE_Y_INDEX = 3;
    private static final String TAG_COMMON = "common";
    private static final int TAG_COMMON_ATTRIBUTECOUNT = 6;
    private static final String TAG_COMMON_ATTRIBUTE_BASE = "base";
    private static final int TAG_COMMON_ATTRIBUTE_BASE_INDEX = 2;
    private static final String TAG_COMMON_ATTRIBUTE_LINEHEIGHT = "lineHeight";
    private static final int TAG_COMMON_ATTRIBUTE_LINEHEIGHT_INDEX = 1;
    private static final String TAG_COMMON_ATTRIBUTE_PACKED = "packed";
    private static final int TAG_COMMON_ATTRIBUTE_PACKED_INDEX = 6;
    private static final String TAG_COMMON_ATTRIBUTE_PAGES = "pages";
    private static final int TAG_COMMON_ATTRIBUTE_PAGES_INDEX = 5;
    private static final String TAG_COMMON_ATTRIBUTE_SCALEHEIGHT = "scaleH";
    private static final int TAG_COMMON_ATTRIBUTE_SCALEHEIGHT_INDEX = 4;
    private static final String TAG_COMMON_ATTRIBUTE_SCALEWIDTH = "scaleW";
    private static final int TAG_COMMON_ATTRIBUTE_SCALEWIDTH_INDEX = 3;
    private static final String TAG_INFO = "info";
    private static final int TAG_INFO_ATTRIBUTECOUNT = 11;
    private static final String TAG_INFO_ATTRIBUTE_ANTIALIASED = "aa";
    private static final int TAG_INFO_ATTRIBUTE_ANTIALIASED_INDEX = 9;
    private static final String TAG_INFO_ATTRIBUTE_BOLD = "bold";
    private static final int TAG_INFO_ATTRIBUTE_BOLD_INDEX = 3;
    private static final String TAG_INFO_ATTRIBUTE_CHARSET = "charset";
    private static final int TAG_INFO_ATTRIBUTE_CHARSET_INDEX = 5;
    private static final String TAG_INFO_ATTRIBUTE_FACE = "face";
    private static final int TAG_INFO_ATTRIBUTE_FACE_INDEX = 1;
    private static final String TAG_INFO_ATTRIBUTE_ITALIC = "italic";
    private static final int TAG_INFO_ATTRIBUTE_ITALIC_INDEX = 4;
    private static final String TAG_INFO_ATTRIBUTE_PADDING = "padding";
    private static final int TAG_INFO_ATTRIBUTE_PADDING_INDEX = 10;
    private static final String TAG_INFO_ATTRIBUTE_SIZE = "size";
    private static final int TAG_INFO_ATTRIBUTE_SIZE_INDEX = 2;
    private static final String TAG_INFO_ATTRIBUTE_SMOOTH = "smooth";
    private static final int TAG_INFO_ATTRIBUTE_SMOOTH_INDEX = 8;
    private static final String TAG_INFO_ATTRIBUTE_SPACING = "spacing";
    private static final int TAG_INFO_ATTRIBUTE_SPACING_INDEX = 11;
    private static final String TAG_INFO_ATTRIBUTE_STRETCHHEIGHT = "stretchH";
    private static final int TAG_INFO_ATTRIBUTE_STRETCHHEIGHT_INDEX = 7;
    private static final String TAG_INFO_ATTRIBUTE_UNICODE = "unicode";
    private static final int TAG_INFO_ATTRIBUTE_UNICODE_INDEX = 6;
    private static final String TAG_KERNING = "kerning";
    private static final String TAG_KERNINGS = "kernings";
    private static final int TAG_KERNINGS_ATTRIBUTECOUNT = 1;
    private static final String TAG_KERNINGS_ATTRIBUTE_COUNT = "count";
    private static final int TAG_KERNINGS_ATTRIBUTE_COUNT_INDEX = 1;
    private static final int TAG_KERNING_ATTRIBUTECOUNT = 3;
    private static final String TAG_KERNING_ATTRIBUTE_AMOUNT = "amount";
    private static final int TAG_KERNING_ATTRIBUTE_AMOUNT_INDEX = 3;
    private static final String TAG_KERNING_ATTRIBUTE_FIRST = "first";
    private static final int TAG_KERNING_ATTRIBUTE_FIRST_INDEX = 1;
    private static final String TAG_KERNING_ATTRIBUTE_SECOND = "second";
    private static final int TAG_KERNING_ATTRIBUTE_SECOND_INDEX = 2;
    private static final String TAG_PAGE = "page";
    private static final int TAG_PAGE_ATTRIBUTECOUNT = 2;
    private static final String TAG_PAGE_ATTRIBUTE_FILE = "file";
    private static final int TAG_PAGE_ATTRIBUTE_FILE_INDEX = 2;
    private static final String TAG_PAGE_ATTRIBUTE_ID = "id";
    private static final int TAG_PAGE_ATTRIBUTE_ID_INDEX = 1;
    private final int mBase;
    private final BitmapFontInfo mBitmapFontInfo;
    private final BitmapFontOptions mBitmapFontOptions;
    private final int mBitmapFontPageCount;
    private final BitmapFontPage[] mBitmapFontPages;
    private final BitmapTextureFormat mBitmapTextureFormat;
    private final SparseArray<Letter> mCharacterToLetterMap;
    private final int mLineHeight;
    private final boolean mPacked;
    private final int mScaleHeight;
    private final int mScaleWidth;
    private final TextureManager mTextureManager;
    private final TextureOptions mTextureOptions;

    public class BitmapFontInfo {
        private static final int PADDING_BOTTOM_INDEX = 3;
        private static final int PADDING_LEFT_INDEX = 0;
        private static final int PADDING_RIGHT_INDEX = 2;
        private static final int PADDING_TOP_INDEX = 1;
        private static final int SPACING_X_INDEX = 0;
        private static final int SPACING_Y_INDEX = 1;
        private final boolean mAntiAliased;
        private final boolean mBold;
        private final String mCharset;
        private final String mFace;
        private final boolean mItalic;
        private final int mPaddingBottom;
        private final int mPaddingLeft;
        private final int mPaddingRight;
        private final int mPaddingTop;
        private final int mSize;
        private final boolean mSmooth;
        private final int mSpacingX;
        private final int mSpacingY;
        private final int mStretchHeight;
        private final int mUnicode;

        public BitmapFontInfo(String pData) throws FontException {
            if (pData == null) {
                throw new FontException("pData must not be null.");
            }
            String[] infoAttributes = TextUtils.SPLITPATTERN_SPACE.split(pData, 12);
            if (infoAttributes.length - 1 != 11) {
                throw new FontException("Expected: '11' info attributes, found: '" + (infoAttributes.length - 1) + "'.");
            } else if (infoAttributes[0].equals(BitmapFont.TAG_INFO)) {
                this.mFace = BitmapFont.getStringAttribute(infoAttributes, 1, BitmapFont.TAG_INFO_ATTRIBUTE_FACE);
                this.mSize = BitmapFont.getIntAttribute(infoAttributes, 2, BitmapFont.TAG_INFO_ATTRIBUTE_SIZE);
                this.mBold = BitmapFont.getBooleanAttribute(infoAttributes, 3, BitmapFont.TAG_INFO_ATTRIBUTE_BOLD);
                this.mItalic = BitmapFont.getBooleanAttribute(infoAttributes, 4, BitmapFont.TAG_INFO_ATTRIBUTE_ITALIC);
                this.mCharset = BitmapFont.getStringAttribute(infoAttributes, 5, BitmapFont.TAG_INFO_ATTRIBUTE_CHARSET);
                this.mUnicode = BitmapFont.getIntAttribute(infoAttributes, 6, BitmapFont.TAG_INFO_ATTRIBUTE_UNICODE);
                this.mStretchHeight = BitmapFont.getIntAttribute(infoAttributes, 7, BitmapFont.TAG_INFO_ATTRIBUTE_STRETCHHEIGHT);
                this.mSmooth = BitmapFont.getBooleanAttribute(infoAttributes, 8, BitmapFont.TAG_INFO_ATTRIBUTE_SMOOTH);
                this.mAntiAliased = BitmapFont.getBooleanAttribute(infoAttributes, 9, BitmapFont.TAG_INFO_ATTRIBUTE_ANTIALIASED);
                String[] paddings = TextUtils.SPLITPATTERN_COMMA.split(BitmapFont.getAttribute(infoAttributes, 10, BitmapFont.TAG_INFO_ATTRIBUTE_PADDING), 4);
                this.mPaddingLeft = Integer.parseInt(paddings[0]);
                this.mPaddingTop = Integer.parseInt(paddings[1]);
                this.mPaddingRight = Integer.parseInt(paddings[2]);
                this.mPaddingBottom = Integer.parseInt(paddings[3]);
                String[] spacings = TextUtils.SPLITPATTERN_COMMA.split(BitmapFont.getAttribute(infoAttributes, 11, BitmapFont.TAG_INFO_ATTRIBUTE_SPACING), 2);
                this.mSpacingX = Integer.parseInt(spacings[0]);
                this.mSpacingY = Integer.parseInt(spacings[1]);
            } else {
                throw new FontException("Expected: 'info' attributes.");
            }
        }

        public String getFace() {
            return this.mFace;
        }

        public int getSize() {
            return this.mSize;
        }

        public boolean isBold() {
            return this.mBold;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public String getCharset() {
            return this.mCharset;
        }

        public int getUnicode() {
            return this.mUnicode;
        }

        public int getStretchHeight() {
            return this.mStretchHeight;
        }

        public boolean isSmooth() {
            return this.mSmooth;
        }

        public boolean isAntiAliased() {
            return this.mAntiAliased;
        }

        public int getPaddingLeft() {
            return this.mPaddingLeft;
        }

        public int getPaddingTop() {
            return this.mPaddingTop;
        }

        public int getPaddingRight() {
            return this.mPaddingRight;
        }

        public int getPaddingBottom() {
            return this.mPaddingBottom;
        }

        public int getSpacingX() {
            return this.mSpacingX;
        }

        public int getSpacingY() {
            return this.mSpacingY;
        }
    }

    public static class BitmapFontOptions {
        public static final BitmapFontOptions DEFAULT = new BitmapFontOptions(0, 0);
        private final int mTextureOffsetX;
        private final int mTextureOffsetY;

        public BitmapFontOptions(int pTextureOffsetX, int pTextureOffsetY) {
            this.mTextureOffsetX = pTextureOffsetX;
            this.mTextureOffsetY = pTextureOffsetY;
        }

        public int getTextureOffsetX() {
            return this.mTextureOffsetX;
        }

        public int getTextureOffsetY() {
            return this.mTextureOffsetY;
        }
    }

    public class BitmapFontPage {
        private int mID;
        private final ITexture mTexture;

        public BitmapFontPage(AssetManager pAssetManager, String pAssetBasePath, String pData) throws IOException {
            String[] pageAttributes = TextUtils.SPLITPATTERN_SPACE.split(pData, 3);
            if (pageAttributes.length - 1 != 2) {
                throw new FontException("Expected: '2' page attributes, found: '" + (pageAttributes.length - 1) + "'.");
            } else if (pageAttributes[0].equals(ParamKey.PAGE)) {
                this.mID = BitmapFont.getIntAttribute(pageAttributes, 1, ShareConstants.WEB_DIALOG_PARAM_ID);
                this.mTexture = new BitmapTexture(BitmapFont.this.mTextureManager, new AssetInputStreamOpener(pAssetManager, pAssetBasePath + BitmapFont.getStringAttribute(pageAttributes, 2, BitmapFont.TAG_PAGE_ATTRIBUTE_FILE)), BitmapFont.this.mBitmapTextureFormat, BitmapFont.this.mTextureOptions);
            } else {
                throw new FontException("Expected: 'page' attributes.");
            }
        }

        public int getID() {
            return this.mID;
        }

        public ITexture getTexture() {
            return this.mTexture;
        }
    }

    public BitmapFont(TextureManager pTextureManager, AssetManager pAssetManager, String pAssetPath) {
        this(pTextureManager, pAssetManager, pAssetPath, BitmapTextureFormat.RGBA_8888, TextureOptions.DEFAULT, BitmapFontOptions.DEFAULT);
    }

    public BitmapFont(TextureManager pTextureManager, AssetManager pAssetManager, String pAssetPath, BitmapTextureFormat pBitmapTextureFormat) {
        this(pTextureManager, pAssetManager, pAssetPath, pBitmapTextureFormat, TextureOptions.DEFAULT, BitmapFontOptions.DEFAULT);
    }

    public BitmapFont(TextureManager pTextureManager, AssetManager pAssetManager, String pAssetPath, TextureOptions pTextureOptions) {
        this(pTextureManager, pAssetManager, pAssetPath, BitmapTextureFormat.RGBA_8888, pTextureOptions, BitmapFontOptions.DEFAULT);
    }

    public BitmapFont(TextureManager pTextureManager, AssetManager pAssetManager, String pAssetPath, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions) {
        this(pTextureManager, pAssetManager, pAssetPath, pBitmapTextureFormat, pTextureOptions, BitmapFontOptions.DEFAULT);
    }

    public BitmapFont(TextureManager pTextureManager, AssetManager pAssetManager, String pAssetPath, BitmapTextureFormat pBitmapTextureFormat, TextureOptions pTextureOptions, BitmapFontOptions pBitmapFontOptions) {
        this.mCharacterToLetterMap = new SparseArray();
        this.mTextureManager = pTextureManager;
        this.mBitmapTextureFormat = pBitmapTextureFormat;
        this.mTextureOptions = pTextureOptions;
        this.mBitmapFontOptions = pBitmapFontOptions;
        InputStream in = null;
        try {
            String assetBasePath;
            in = pAssetManager.open(pAssetPath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in), 8192);
            if (pAssetPath.indexOf(47) == -1) {
                assetBasePath = "";
            } else {
                assetBasePath = pAssetPath.substring(0, pAssetPath.lastIndexOf(47) + 1);
            }
            this.mBitmapFontInfo = new BitmapFontInfo(bufferedReader.readLine());
            String common = bufferedReader.readLine();
            if (common == null || !common.startsWith(TAG_COMMON)) {
                throw new FontException("Expected: 'common' attributes.");
            }
            String[] commonAttributes = TextUtils.SPLITPATTERN_SPACE.split(common, 7);
            if (commonAttributes.length - 1 != 6) {
                throw new FontException("Expected: '6' common attributes, found: '" + (commonAttributes.length - 1) + "'.");
            } else if (commonAttributes[0].equals(TAG_COMMON)) {
                this.mLineHeight = getIntAttribute(commonAttributes, 1, TAG_COMMON_ATTRIBUTE_LINEHEIGHT);
                this.mBase = getIntAttribute(commonAttributes, 2, TAG_COMMON_ATTRIBUTE_BASE);
                this.mScaleWidth = getIntAttribute(commonAttributes, 3, TAG_COMMON_ATTRIBUTE_SCALEWIDTH);
                this.mScaleHeight = getIntAttribute(commonAttributes, 4, TAG_COMMON_ATTRIBUTE_SCALEHEIGHT);
                this.mBitmapFontPageCount = getIntAttribute(commonAttributes, 5, TAG_COMMON_ATTRIBUTE_PAGES);
                this.mPacked = getBooleanAttribute(commonAttributes, 6, TAG_COMMON_ATTRIBUTE_PACKED);
                if (this.mBitmapFontPageCount != 1) {
                    throw new FontException("Only a single page is supported.");
                }
                this.mBitmapFontPages = new BitmapFontPage[this.mBitmapFontPageCount];
                if (this.mPacked) {
                    throw new FontException("Packed is not supported.");
                }
                for (int i = 0; i < this.mBitmapFontPageCount; i++) {
                    this.mBitmapFontPages[i] = new BitmapFontPage(pAssetManager, assetBasePath, bufferedReader.readLine());
                }
                String chars = bufferedReader.readLine();
                if (chars == null || !chars.startsWith(TAG_CHARS)) {
                    throw new FontException("Expected: 'chars' attributes.");
                }
                String[] charsAttributes = TextUtils.SPLITPATTERN_SPACE.split(chars, 2);
                if (charsAttributes.length - 1 != 1) {
                    throw new FontException("Expected: '1' chars attributes, found: '" + (charsAttributes.length - 1) + "'.");
                } else if (charsAttributes[0].equals(TAG_CHARS)) {
                    parseCharacters(getIntAttribute(charsAttributes, 1, ParamKey.COUNT), bufferedReader);
                    String kernings = bufferedReader.readLine();
                    if (kernings != null && kernings.startsWith(TAG_KERNINGS)) {
                        String[] kerningsAttributes = TextUtils.SPLITPATTERN_SPACE.split(kernings, 2);
                        if (kerningsAttributes.length - 1 != 1) {
                            throw new FontException("Expected: '1' kernings attributes, found: '" + (kerningsAttributes.length - 1) + "'.");
                        } else if (kerningsAttributes[0].equals(TAG_KERNINGS)) {
                            parseKernings(getIntAttribute(kerningsAttributes, 1, ParamKey.COUNT), bufferedReader);
                        } else {
                            throw new FontException("Expected: 'kernings' attributes.");
                        }
                    }
                    StreamUtils.close(in);
                } else {
                    throw new FontException("Expected: 'chars' attributes.");
                }
            } else {
                throw new FontException("Expected: 'common' attributes.");
            }
        } catch (IOException e) {
            throw new FontException("Failed loading BitmapFont. AssetPath: " + pAssetPath, e);
        } catch (Throwable th) {
            StreamUtils.close(in);
        }
    }

    public BitmapFontInfo getBitmapFontInfo() {
        return this.mBitmapFontInfo;
    }

    public int getBase() {
        return this.mBase;
    }

    public int getScaleWidth() {
        return this.mScaleWidth;
    }

    public int getScaleHeight() {
        return this.mScaleHeight;
    }

    public int getBitmapFontPageCount() {
        return this.mBitmapFontPageCount;
    }

    public BitmapFontPage[] getBitmapFontPages() {
        return this.mBitmapFontPages;
    }

    public BitmapFontPage getBitmapFontPage(int pIndex) {
        return this.mBitmapFontPages[pIndex];
    }

    public boolean isPacked() {
        return this.mPacked;
    }

    public ITexture getTexture() {
        return this.mBitmapFontPages[0].getTexture();
    }

    public void load() {
        loadTextures();
    }

    public void unload() {
        unloadTextures();
    }

    public float getLineHeight() {
        return (float) this.mLineHeight;
    }

    public Letter getLetter(char pChar) throws LetterNotFoundException {
        Letter letter = (Letter) this.mCharacterToLetterMap.get(pChar);
        if (letter != null) {
            return letter;
        }
        throw new LetterNotFoundException("Letter '" + pChar + "' not found.");
    }

    public void loadTextures() {
        for (BitmapFontPage texture : this.mBitmapFontPages) {
            texture.getTexture().load();
        }
    }

    public void unloadTextures() {
        for (BitmapFontPage texture : this.mBitmapFontPages) {
            texture.getTexture().unload();
        }
    }

    private void parseCharacters(int pCharacterCount, BufferedReader pBufferedReader) throws IOException {
        int i = pCharacterCount - 1;
        while (i >= 0) {
            String[] charAttributes = TextUtils.SPLITPATTERN_SPACES.split(pBufferedReader.readLine(), 11);
            if (charAttributes.length - 1 != 10) {
                throw new FontException("Expected: '10' char attributes, found: '" + (charAttributes.length - 1) + "'.");
            } else if (charAttributes[0].equals(TAG_CHAR)) {
                char id = getCharAttribute(charAttributes, 1, ShareConstants.WEB_DIALOG_PARAM_ID);
                int x = this.mBitmapFontOptions.mTextureOffsetX + getIntAttribute(charAttributes, 2, TAG_CHAR_ATTRIBUTE_X);
                int y = this.mBitmapFontOptions.mTextureOffsetY + getIntAttribute(charAttributes, 3, TAG_CHAR_ATTRIBUTE_Y);
                int width = getIntAttribute(charAttributes, 4, "width");
                int height = getIntAttribute(charAttributes, 5, "height");
                int xOffset = getIntAttribute(charAttributes, 6, TAG_CHAR_ATTRIBUTE_XOFFSET);
                int yOffset = getIntAttribute(charAttributes, 7, TAG_CHAR_ATTRIBUTE_YOFFSET);
                int xAdvance = getIntAttribute(charAttributes, 8, TAG_CHAR_ATTRIBUTE_XADVANCE);
                ITexture bitmapFontPageTexture = this.mBitmapFontPages[getIntAttribute(charAttributes, 9, ParamKey.PAGE)].getTexture();
                float textureWidth = (float) bitmapFontPageTexture.getWidth();
                float textureHeight = (float) bitmapFontPageTexture.getHeight();
                float u = ((float) x) / textureWidth;
                float v = ((float) y) / textureHeight;
                float u2 = ((float) (x + width)) / textureWidth;
                float v2 = ((float) (y + height)) / textureHeight;
                this.mCharacterToLetterMap.put(id, new Letter(id, x, y, width, height, (float) xOffset, (float) yOffset, (float) xAdvance, u, v, u2, v2));
                i--;
            } else {
                throw new FontException("Expected: 'char' attributes.");
            }
        }
    }

    private void parseKernings(int pKerningsCount, BufferedReader pBufferedReader) throws IOException {
        int i = pKerningsCount - 1;
        while (i >= 0) {
            String[] charAttributes = TextUtils.SPLITPATTERN_SPACES.split(pBufferedReader.readLine(), 4);
            if (charAttributes.length - 1 != 3) {
                throw new FontException("Expected: '3' kerning attributes, found: '" + (charAttributes.length - 1) + "'.");
            } else if (charAttributes[0].equals(TAG_KERNING)) {
                int first = getIntAttribute(charAttributes, 1, TAG_KERNING_ATTRIBUTE_FIRST);
                ((Letter) this.mCharacterToLetterMap.get(first)).addKerning(getIntAttribute(charAttributes, 2, TAG_KERNING_ATTRIBUTE_SECOND), getIntAttribute(charAttributes, 3, TAG_KERNING_ATTRIBUTE_AMOUNT));
                i--;
            } else {
                throw new FontException("Expected: 'kerning' attributes.");
            }
        }
    }

    private static boolean getBooleanAttribute(String[] pData, int pPosition, String pAttribute) {
        String data = pData[pPosition];
        int attributeLength = pAttribute.length();
        if (data.startsWith(pAttribute) && data.charAt(attributeLength) == '=') {
            return Integer.parseInt(data.substring(attributeLength + 1)) != 0;
        } else {
            throw new FontException("Expected '" + pAttribute + "' at position '" + pPosition + "', but found: '" + data + "'.");
        }
    }

    private static char getCharAttribute(String[] pData, int pPosition, String pAttribute) {
        return (char) getIntAttribute(pData, pPosition, pAttribute);
    }

    private static int getIntAttribute(String[] pData, int pPosition, String pAttribute) {
        String data = pData[pPosition];
        int attributeLength = pAttribute.length();
        if (data.startsWith(pAttribute) && data.charAt(attributeLength) == '=') {
            return Integer.parseInt(data.substring(attributeLength + 1));
        }
        throw new FontException("Expected '" + pAttribute + "' at position '" + pPosition + "', but found: '" + data + "'.");
    }

    private static String getStringAttribute(String[] pData, int pPosition, String pAttribute) {
        String data = pData[pPosition];
        int attributeLength = pAttribute.length();
        if (data.startsWith(pAttribute) && data.charAt(attributeLength) == '=') {
            return data.substring(attributeLength + 2, data.length() - 1);
        }
        throw new FontException("Expected '" + pAttribute + "' at position '" + pPosition + "', but found: '" + data + "'.");
    }

    private static String getAttribute(String[] pData, int pPosition, String pAttribute) {
        String data = pData[pPosition];
        int attributeLength = pAttribute.length();
        if (data.startsWith(pAttribute)) {
            return data.substring(attributeLength + 1);
        }
        throw new FontException("Expected '" + pAttribute + "' at position '" + pPosition + "', but found: '" + data + "'.");
    }
}
