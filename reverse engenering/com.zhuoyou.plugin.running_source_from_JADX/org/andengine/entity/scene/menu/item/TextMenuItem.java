package org.andengine.entity.scene.menu.item;

import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.text.vbo.ITextVertexBufferObject;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class TextMenuItem extends Text implements IMenuItem {
    private final int mID;

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(0.0f, 0.0f, pFont, pText, pVertexBufferObjectManager);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pFont, pText, pVertexBufferObjectManager, pShaderProgram);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(0.0f, 0.0f, pFont, pText, pVertexBufferObjectManager, pDrawType);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pFont, pText, pVertexBufferObjectManager, pDrawType, pShaderProgram);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(0.0f, 0.0f, pFont, pText, pTextOptions, pVertexBufferObjectManager);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pFont, pText, pTextOptions, pVertexBufferObjectManager, pShaderProgram);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(0.0f, 0.0f, pFont, pText, pTextOptions, pVertexBufferObjectManager, pDrawType);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(0.0f, 0.0f, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pFont, pText, pTextOptions, pVertexBufferObjectManager, pDrawType, pShaderProgram);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager, pShaderProgram);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(0.0f, 0.0f, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager, pDrawType);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager, pDrawType, pShaderProgram);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(0.0f, 0.0f, pFont, pText, pCharactersMaximum, pTextOptions, pVertexBufferObjectManager);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(0.0f, 0.0f, pFont, pText, pCharactersMaximum, pTextOptions, pVertexBufferObjectManager, pDrawType);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pFont, pText, pCharactersMaximum, pTextOptions, pVertexBufferObjectManager, pDrawType, pShaderProgram);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, ITextVertexBufferObject pTextVertexBufferObject) {
        super(0.0f, 0.0f, pFont, pText, pCharactersMaximum, pTextOptions, pTextVertexBufferObject);
        this.mID = pID;
    }

    public TextMenuItem(int pID, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, ITextVertexBufferObject pTextVertexBufferObject, ShaderProgram pShaderProgram) {
        super(0.0f, 0.0f, pFont, pText, pCharactersMaximum, pTextOptions, pTextVertexBufferObject, pShaderProgram);
        this.mID = pID;
    }

    public int getID() {
        return this.mID;
    }

    public void onSelected() {
    }

    public void onUnselected() {
    }
}
