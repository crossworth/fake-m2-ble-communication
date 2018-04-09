package org.andengine.opengl.texture.atlas.buildable.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.andengine.opengl.texture.atlas.ITextureAtlas;
import org.andengine.opengl.texture.atlas.buildable.BuildableTextureAtlas.TextureAtlasSourceWithWithLocationCallback;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;

public class BlackPawnTextureAtlasBuilder<T extends ITextureAtlasSource, A extends ITextureAtlas<T>> implements ITextureAtlasBuilder<T, A> {
    private static final Comparator<TextureAtlasSourceWithWithLocationCallback<?>> TEXTURESOURCE_COMPARATOR = new C20721();
    private final int mTextureAtlasBorderSpacing;
    private final int mTextureAtlasSourcePadding;
    private final int mTextureAtlasSourceSpacing;

    static class C20721 implements Comparator<TextureAtlasSourceWithWithLocationCallback<?>> {
        C20721() {
        }

        public int compare(TextureAtlasSourceWithWithLocationCallback<?> pTextureAtlasSourceWithWithLocationCallbackA, TextureAtlasSourceWithWithLocationCallback<?> pTextureAtlasSourceWithWithLocationCallbackB) {
            int deltaWidth = pTextureAtlasSourceWithWithLocationCallbackB.getTextureAtlasSource().getTextureWidth() - pTextureAtlasSourceWithWithLocationCallbackA.getTextureAtlasSource().getTextureWidth();
            return deltaWidth != 0 ? deltaWidth : pTextureAtlasSourceWithWithLocationCallbackB.getTextureAtlasSource().getTextureHeight() - pTextureAtlasSourceWithWithLocationCallbackA.getTextureAtlasSource().getTextureHeight();
        }
    }

    protected static class Node {
        private Node mChildA;
        private Node mChildB;
        private final Rect mRect;
        private ITextureAtlasSource mTextureAtlasSource;

        public Node(int pLeft, int pTop, int pWidth, int pHeight) {
            this(new Rect(pLeft, pTop, pWidth, pHeight));
        }

        public Node(Rect pRect) {
            this.mRect = pRect;
        }

        public Rect getRect() {
            return this.mRect;
        }

        public Node getChildA() {
            return this.mChildA;
        }

        public Node getChildB() {
            return this.mChildB;
        }

        public Node insert(ITextureAtlasSource pTextureAtlasSource, int pTextureWidth, int pTextureHeight, int pTextureAtlasSourceSpacing, int pTextureAtlasSourcePadding) throws IllegalArgumentException {
            if (this.mChildA != null && this.mChildB != null) {
                Node newNode = this.mChildA.insert(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureAtlasSourceSpacing, pTextureAtlasSourcePadding);
                if (newNode != null) {
                    return newNode;
                }
                return this.mChildB.insert(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureAtlasSourceSpacing, pTextureAtlasSourcePadding);
            } else if (this.mTextureAtlasSource != null) {
                return null;
            } else {
                int textureSourceWidth = pTextureAtlasSource.getTextureWidth() + (pTextureAtlasSourcePadding * 2);
                int textureSourceHeight = pTextureAtlasSource.getTextureHeight() + (pTextureAtlasSourcePadding * 2);
                int rectWidth = this.mRect.getWidth();
                int rectHeight = this.mRect.getHeight();
                if (textureSourceWidth > rectWidth || textureSourceHeight > rectHeight) {
                    return null;
                }
                int textureSourceWidthWithSpacing = textureSourceWidth + pTextureAtlasSourceSpacing;
                int textureSourceHeightWithSpacing = textureSourceHeight + pTextureAtlasSourceSpacing;
                int rectLeft = this.mRect.getLeft();
                boolean fitToBottomWithoutSpacing = textureSourceHeight == rectHeight && this.mRect.getTop() + textureSourceHeight == pTextureHeight;
                boolean fitToRightWithoutSpacing = textureSourceWidth == rectWidth && rectLeft + textureSourceWidth == pTextureWidth;
                if (textureSourceWidthWithSpacing == rectWidth) {
                    if (textureSourceHeightWithSpacing == rectHeight) {
                        this.mTextureAtlasSource = pTextureAtlasSource;
                        return this;
                    } else if (fitToBottomWithoutSpacing) {
                        this.mTextureAtlasSource = pTextureAtlasSource;
                        return this;
                    }
                }
                if (fitToRightWithoutSpacing) {
                    if (textureSourceHeightWithSpacing == rectHeight) {
                        this.mTextureAtlasSource = pTextureAtlasSource;
                        return this;
                    } else if (fitToBottomWithoutSpacing) {
                        this.mTextureAtlasSource = pTextureAtlasSource;
                        return this;
                    } else if (textureSourceHeightWithSpacing > rectHeight) {
                        return null;
                    } else {
                        return createChildren(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureAtlasSourceSpacing, pTextureAtlasSourcePadding, rectWidth - textureSourceWidth, rectHeight - textureSourceHeightWithSpacing);
                    }
                } else if (fitToBottomWithoutSpacing) {
                    if (textureSourceWidthWithSpacing == rectWidth) {
                        this.mTextureAtlasSource = pTextureAtlasSource;
                        return this;
                    } else if (textureSourceWidthWithSpacing > rectWidth) {
                        return null;
                    } else {
                        return createChildren(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureAtlasSourceSpacing, pTextureAtlasSourcePadding, rectWidth - textureSourceWidthWithSpacing, rectHeight - textureSourceHeight);
                    }
                } else if (textureSourceWidthWithSpacing > rectWidth || textureSourceHeightWithSpacing > rectHeight) {
                    return null;
                } else {
                    return createChildren(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureAtlasSourceSpacing, pTextureAtlasSourcePadding, rectWidth - textureSourceWidthWithSpacing, rectHeight - textureSourceHeightWithSpacing);
                }
            }
        }

        private Node createChildren(ITextureAtlasSource pTextureAtlasSource, int pTextureWidth, int pTextureHeight, int pTextureAtlasSourceSpacing, int pTextureAtlasSourcePadding, int pDeltaWidth, int pDeltaHeight) {
            Rect rect = this.mRect;
            if (pDeltaWidth >= pDeltaHeight) {
                this.mChildA = new Node(rect.getLeft(), rect.getTop(), (pTextureAtlasSource.getTextureWidth() + pTextureAtlasSourceSpacing) + (pTextureAtlasSourcePadding * 2), rect.getHeight());
                this.mChildB = new Node(rect.getLeft() + ((pTextureAtlasSource.getTextureWidth() + pTextureAtlasSourceSpacing) + (pTextureAtlasSourcePadding * 2)), rect.getTop(), rect.getWidth() - ((pTextureAtlasSource.getTextureWidth() + pTextureAtlasSourceSpacing) + (pTextureAtlasSourcePadding * 2)), rect.getHeight());
            } else {
                this.mChildA = new Node(rect.getLeft(), rect.getTop(), rect.getWidth(), (pTextureAtlasSource.getTextureHeight() + pTextureAtlasSourceSpacing) + (pTextureAtlasSourcePadding * 2));
                this.mChildB = new Node(rect.getLeft(), rect.getTop() + ((pTextureAtlasSource.getTextureHeight() + pTextureAtlasSourceSpacing) + (pTextureAtlasSourcePadding * 2)), rect.getWidth(), rect.getHeight() - ((pTextureAtlasSource.getTextureHeight() + pTextureAtlasSourceSpacing) + (pTextureAtlasSourcePadding * 2)));
            }
            return this.mChildA.insert(pTextureAtlasSource, pTextureWidth, pTextureHeight, pTextureAtlasSourceSpacing, pTextureAtlasSourcePadding);
        }
    }

    protected static class Rect {
        private final int mHeight;
        private final int mLeft;
        private final int mTop;
        private final int mWidth;

        public Rect(int pLeft, int pTop, int pWidth, int pHeight) {
            this.mLeft = pLeft;
            this.mTop = pTop;
            this.mWidth = pWidth;
            this.mHeight = pHeight;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getLeft() {
            return this.mLeft;
        }

        public int getTop() {
            return this.mTop;
        }

        public int getRight() {
            return this.mLeft + this.mWidth;
        }

        public int getBottom() {
            return this.mTop + this.mHeight;
        }

        public String toString() {
            return "@: " + this.mLeft + "/" + this.mTop + " * " + this.mWidth + "x" + this.mHeight;
        }
    }

    public BlackPawnTextureAtlasBuilder(int pTextureAtlasBorderSpacing, int pTextureAtlasSourceSpacing, int pTextureAtlasSourcePadding) {
        this.mTextureAtlasBorderSpacing = pTextureAtlasBorderSpacing;
        this.mTextureAtlasSourceSpacing = pTextureAtlasSourceSpacing;
        this.mTextureAtlasSourcePadding = pTextureAtlasSourcePadding;
    }

    public void build(A pTextureAtlas, ArrayList<TextureAtlasSourceWithWithLocationCallback<T>> pTextureAtlasSourcesWithLocationCallback) throws TextureAtlasBuilderException {
        Collections.sort(pTextureAtlasSourcesWithLocationCallback, TEXTURESOURCE_COMPARATOR);
        int rootWidth = pTextureAtlas.getWidth() - (this.mTextureAtlasBorderSpacing * 2);
        int rootHeight = pTextureAtlas.getHeight() - (this.mTextureAtlasBorderSpacing * 2);
        Node root = new Node(new Rect(0, 0, rootWidth, rootHeight));
        int textureSourceCount = pTextureAtlasSourcesWithLocationCallback.size();
        for (int i = 0; i < textureSourceCount; i++) {
            TextureAtlasSourceWithWithLocationCallback<T> textureSourceWithLocationCallback = (TextureAtlasSourceWithWithLocationCallback) pTextureAtlasSourcesWithLocationCallback.get(i);
            T textureAtlasSource = textureSourceWithLocationCallback.getTextureAtlasSource();
            Node inserted = root.insert(textureAtlasSource, rootWidth, rootHeight, this.mTextureAtlasSourceSpacing, this.mTextureAtlasSourcePadding);
            if (inserted == null) {
                throw new TextureAtlasBuilderException("Could not build: '" + textureAtlasSource.toString() + "' into: '" + pTextureAtlas.getClass().getSimpleName() + "'.");
            }
            int textureAtlasSourceLeft = (inserted.mRect.mLeft + this.mTextureAtlasBorderSpacing) + this.mTextureAtlasSourcePadding;
            int textureAtlasSourceTop = (inserted.mRect.mTop + this.mTextureAtlasBorderSpacing) + this.mTextureAtlasSourcePadding;
            if (this.mTextureAtlasSourcePadding == 0) {
                pTextureAtlas.addTextureAtlasSource(textureAtlasSource, textureAtlasSourceLeft, textureAtlasSourceTop);
            } else {
                pTextureAtlas.addTextureAtlasSource(textureAtlasSource, textureAtlasSourceLeft, textureAtlasSourceTop, this.mTextureAtlasSourcePadding);
            }
            textureSourceWithLocationCallback.getCallback().onCallback(textureAtlasSource);
        }
    }
}
