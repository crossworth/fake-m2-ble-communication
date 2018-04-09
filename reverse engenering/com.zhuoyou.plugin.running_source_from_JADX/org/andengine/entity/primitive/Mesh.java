package org.andengine.entity.primitive;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.vbo.HighPerformanceMeshVertexBufferObject;
import org.andengine.entity.primitive.vbo.IMeshVertexBufferObject;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.shape.Shape;
import org.andengine.opengl.shader.PositionColorShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributesBuilder;
import org.andengine.util.exception.MethodNotSupportedException;

public class Mesh extends Shape {
    public static final int COLOR_INDEX = 2;
    public static final VertexBufferObjectAttributes VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT = new VertexBufferObjectAttributesBuilder(2).add(0, ShaderProgramConstants.ATTRIBUTE_POSITION, 2, 5126, false).add(1, ShaderProgramConstants.ATTRIBUTE_COLOR, 4, 5121, true).build();
    public static final int VERTEX_INDEX_X = 0;
    public static final int VERTEX_INDEX_Y = 1;
    public static final int VERTEX_SIZE = 3;
    private int mDrawMode;
    protected final IMeshVertexBufferObject mMeshVertexBufferObject;
    private int mVertexCountToDraw;

    public Mesh(float pX, float pY, float[] pBufferData, int pVertexCount, DrawMode pDrawMode, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX, pY, pBufferData, pVertexCount, pDrawMode, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public Mesh(float pX, float pY, float[] pBufferData, int pVertexCount, DrawMode pDrawMode, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX, pY, pVertexCount, pDrawMode, new HighPerformanceMeshVertexBufferObject(pVertexBufferObjectManager, pBufferData, pVertexCount, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));
    }

    public Mesh(float pX, float pY, int pVertexCount, DrawMode pDrawMode, IMeshVertexBufferObject pMeshVertexBufferObject) {
        super(pX, pY, PositionColorShaderProgram.getInstance());
        this.mDrawMode = pDrawMode.getDrawMode();
        this.mMeshVertexBufferObject = pMeshVertexBufferObject;
        this.mVertexCountToDraw = pVertexCount;
        this.mMeshVertexBufferObject.setDirtyOnHardware();
        setBlendingEnabled(true);
    }

    public float[] getBufferData() {
        return this.mMeshVertexBufferObject.getBufferData();
    }

    public void setVertexCountToDraw(int pVertexCountToDraw) {
        this.mVertexCountToDraw = pVertexCountToDraw;
    }

    public void setDrawMode(DrawMode pDrawMode) {
        this.mDrawMode = pDrawMode.mDrawMode;
    }

    public IMeshVertexBufferObject getVertexBufferObject() {
        return this.mMeshVertexBufferObject;
    }

    protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        this.mMeshVertexBufferObject.bind(pGLState, this.mShaderProgram);
    }

    protected void draw(GLState pGLState, Camera pCamera) {
        this.mMeshVertexBufferObject.draw(this.mDrawMode, this.mVertexCountToDraw);
    }

    protected void postDraw(GLState pGLState, Camera pCamera) {
        this.mMeshVertexBufferObject.unbind(pGLState, this.mShaderProgram);
        super.postDraw(pGLState, pCamera);
    }

    protected void onUpdateColor() {
        this.mMeshVertexBufferObject.onUpdateColor(this);
    }

    protected void onUpdateVertices() {
        this.mMeshVertexBufferObject.onUpdateVertices(this);
    }

    @Deprecated
    public boolean contains(float pX, float pY) {
        throw new MethodNotSupportedException();
    }

    public boolean collidesWith(IShape pOtherShape) {
        if (!(pOtherShape instanceof Line) && (pOtherShape instanceof RectangularShape)) {
        }
        return false;
    }
}
