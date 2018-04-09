package org.andengine.entity.primitive;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.vbo.HighPerformanceLineVertexBufferObject;
import org.andengine.entity.primitive.vbo.ILineVertexBufferObject;
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
import org.andengine.util.algorithm.collision.LineCollisionChecker;
import org.andengine.util.algorithm.collision.RectangularShapeCollisionChecker;
import org.andengine.util.exception.MethodNotSupportedException;

public class Line extends Shape {
    public static final int COLOR_INDEX = 2;
    public static final int LINE_SIZE = 6;
    public static final float LINE_WIDTH_DEFAULT = 1.0f;
    public static final VertexBufferObjectAttributes VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT = new VertexBufferObjectAttributesBuilder(2).add(0, ShaderProgramConstants.ATTRIBUTE_POSITION, 2, 5126, false).add(1, ShaderProgramConstants.ATTRIBUTE_COLOR, 4, 5121, true).build();
    public static final int VERTEX_INDEX_X = 0;
    public static final int VERTEX_INDEX_Y = 1;
    public static final int VERTEX_SIZE = 3;
    public static final int VERTICES_PER_LINE = 2;
    protected final ILineVertexBufferObject mLineVertexBufferObject;
    protected float mLineWidth;
    protected float mX2;
    protected float mY2;

    public Line(float pX1, float pY1, float pX2, float pY2, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX1, pY1, pX2, pY2, 1.0f, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public Line(float pX1, float pY1, float pX2, float pY2, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX1, pY1, pX2, pY2, 1.0f, pVertexBufferObjectManager, pDrawType);
    }

    public Line(float pX1, float pY1, float pX2, float pY2, float pLineWidth, VertexBufferObjectManager pVertexBufferObjectManager) {
        this(pX1, pY1, pX2, pY2, pLineWidth, pVertexBufferObjectManager, DrawType.STATIC);
    }

    public Line(float pX1, float pY1, float pX2, float pY2, float pLineWidth, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        this(pX1, pY1, pX2, pY2, pLineWidth, (ILineVertexBufferObject) new HighPerformanceLineVertexBufferObject(pVertexBufferObjectManager, 6, pDrawType, true, VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));
    }

    public Line(float pX1, float pY1, float pX2, float pY2, float pLineWidth, ILineVertexBufferObject pLineVertexBufferObject) {
        super(pX1, pY1, PositionColorShaderProgram.getInstance());
        this.mX2 = pX2;
        this.mY2 = pY2;
        this.mLineWidth = pLineWidth;
        this.mLineVertexBufferObject = pLineVertexBufferObject;
        onUpdateVertices();
        onUpdateColor();
        float centerY = (this.mY2 - this.mY) * 0.5f;
        this.mRotationCenterX = (this.mX2 - this.mX) * 0.5f;
        this.mRotationCenterY = centerY;
        this.mScaleCenterX = this.mRotationCenterX;
        this.mScaleCenterY = this.mRotationCenterY;
        setBlendingEnabled(true);
    }

    @Deprecated
    public float getX() {
        return super.getX();
    }

    @Deprecated
    public float getY() {
        return super.getY();
    }

    public float getX1() {
        return super.getX();
    }

    public float getY1() {
        return super.getY();
    }

    public float getX2() {
        return this.mX2;
    }

    public float getY2() {
        return this.mY2;
    }

    public float getLineWidth() {
        return this.mLineWidth;
    }

    public void setLineWidth(float pLineWidth) {
        this.mLineWidth = pLineWidth;
    }

    @Deprecated
    public void setX(float pX) {
        float dX = this.mX - pX;
        super.setX(pX);
        this.mX2 += dX;
    }

    @Deprecated
    public void setY(float pY) {
        float dY = this.mY - pY;
        super.setY(pY);
        this.mY2 += dY;
    }

    @Deprecated
    public void setPosition(float pX, float pY) {
        float dX = this.mX - pX;
        float dY = this.mY - pY;
        super.setPosition(pX, pY);
        this.mX2 += dX;
        this.mY2 += dY;
    }

    public void setPosition(float pX1, float pY1, float pX2, float pY2) {
        this.mX2 = pX2;
        this.mY2 = pY2;
        super.setPosition(pX1, pY1);
        onUpdateVertices();
    }

    public ILineVertexBufferObject getVertexBufferObject() {
        return this.mLineVertexBufferObject;
    }

    public boolean isCulled(Camera pCamera) {
        return pCamera.isLineVisible(this);
    }

    protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        pGLState.lineWidth(this.mLineWidth);
        this.mLineVertexBufferObject.bind(pGLState, this.mShaderProgram);
    }

    protected void draw(GLState pGLState, Camera pCamera) {
        this.mLineVertexBufferObject.draw(1, 2);
    }

    protected void postDraw(GLState pGLState, Camera pCamera) {
        this.mLineVertexBufferObject.unbind(pGLState, this.mShaderProgram);
        super.postDraw(pGLState, pCamera);
    }

    protected void onUpdateColor() {
        this.mLineVertexBufferObject.onUpdateColor(this);
    }

    protected void onUpdateVertices() {
        this.mLineVertexBufferObject.onUpdateVertices(this);
    }

    public float[] getSceneCenterCoordinates() {
        throw new MethodNotSupportedException();
    }

    public float[] getSceneCenterCoordinates(float[] pReuse) {
        throw new MethodNotSupportedException();
    }

    @Deprecated
    public boolean contains(float pX, float pY) {
        throw new MethodNotSupportedException();
    }

    public boolean collidesWith(IShape pOtherShape) {
        if (pOtherShape instanceof Line) {
            Line otherLine = (Line) pOtherShape;
            return LineCollisionChecker.checkLineCollision(this.mX, this.mY, this.mX2, this.mY2, otherLine.mX, otherLine.mY, otherLine.mX2, otherLine.mY2);
        } else if (pOtherShape instanceof RectangularShape) {
            return RectangularShapeCollisionChecker.checkCollision((RectangularShape) pOtherShape, this);
        } else {
            return false;
        }
    }
}
