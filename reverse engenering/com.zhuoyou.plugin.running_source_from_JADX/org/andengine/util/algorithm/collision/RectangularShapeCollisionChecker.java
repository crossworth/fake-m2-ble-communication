package org.andengine.util.algorithm.collision;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.util.adt.transformation.Transformation;
import org.andengine.util.math.MathUtils;

public class RectangularShapeCollisionChecker extends ShapeCollisionChecker {
    public static final int RECTANGULARSHAPE_VERTEX_COUNT = 4;
    private static final float[] VERTICES_COLLISION_TMP_A = new float[8];
    private static final float[] VERTICES_COLLISION_TMP_B = new float[8];
    private static final float[] VERTICES_CONTAINS_TMP = new float[8];

    public static boolean checkContains(float pLocalX, float pLocalY, float pLocalWidth, float pLocalHeight, Transformation pLocalToSceneTransformation, float pX, float pY) {
        fillVertices(pLocalX, pLocalY, pLocalWidth, pLocalHeight, pLocalToSceneTransformation, VERTICES_CONTAINS_TMP);
        return ShapeCollisionChecker.checkContains(VERTICES_CONTAINS_TMP, 4, pX, pY);
    }

    public static boolean checkContains(Entity pEntity, float pLocalWidth, float pLocalHeight, float pX, float pY) {
        fillVertices(pEntity.getX(), pEntity.getY(), pLocalWidth, pLocalHeight, pEntity.getLocalToSceneTransformation(), VERTICES_CONTAINS_TMP);
        return ShapeCollisionChecker.checkContains(VERTICES_CONTAINS_TMP, 4, pX, pY);
    }

    public static boolean checkContains(RectangularShape pRectangularShape, float pX, float pY) {
        fillVertices(pRectangularShape, VERTICES_CONTAINS_TMP);
        return ShapeCollisionChecker.checkContains(VERTICES_CONTAINS_TMP, 4, pX, pY);
    }

    public static boolean isVisible(Camera pCamera, RectangularShape pRectangularShape) {
        fillVertices(pCamera, VERTICES_COLLISION_TMP_A);
        fillVertices(pRectangularShape, VERTICES_COLLISION_TMP_B);
        return ShapeCollisionChecker.checkCollision(VERTICES_COLLISION_TMP_A, 4, VERTICES_COLLISION_TMP_B, 4);
    }

    public static boolean isVisible(Camera pCamera, float pX, float pY, float pWidth, float pHeight, Transformation pLocalToSceneTransformation) {
        fillVertices(pCamera, VERTICES_COLLISION_TMP_A);
        fillVertices(pX, pY, pWidth, pHeight, pLocalToSceneTransformation, VERTICES_COLLISION_TMP_B);
        return ShapeCollisionChecker.checkCollision(VERTICES_COLLISION_TMP_A, 4, VERTICES_COLLISION_TMP_B, 4);
    }

    public static boolean isVisible(Camera pCamera, Line pLine) {
        fillVertices(pCamera, VERTICES_COLLISION_TMP_A);
        LineCollisionChecker.fillVertices(pLine, VERTICES_COLLISION_TMP_B);
        return ShapeCollisionChecker.checkCollision(VERTICES_COLLISION_TMP_A, 4, VERTICES_COLLISION_TMP_B, 2);
    }

    public static boolean checkCollision(RectangularShape pRectangularShapeA, RectangularShape pRectangularShapeB) {
        fillVertices(pRectangularShapeA, VERTICES_COLLISION_TMP_A);
        fillVertices(pRectangularShapeB, VERTICES_COLLISION_TMP_B);
        return ShapeCollisionChecker.checkCollision(VERTICES_COLLISION_TMP_A, 4, VERTICES_COLLISION_TMP_B, 4);
    }

    public static boolean checkCollision(RectangularShape pRectangularShape, Line pLine) {
        fillVertices(pRectangularShape, VERTICES_COLLISION_TMP_A);
        LineCollisionChecker.fillVertices(pLine, VERTICES_COLLISION_TMP_B);
        return ShapeCollisionChecker.checkCollision(VERTICES_COLLISION_TMP_A, 4, VERTICES_COLLISION_TMP_B, 2);
    }

    public static void fillVertices(RectangularShape pRectangularShape, float[] pVertices) {
        fillVertices(0.0f, 0.0f, pRectangularShape.getWidth(), pRectangularShape.getHeight(), pRectangularShape.getLocalToSceneTransformation(), pVertices);
    }

    public static void fillVertices(float pLocalX, float pLocalY, float pLocalWidth, float pLocalHeight, Transformation pLocalToSceneTransformation, float[] pVertices) {
        float localXMin = pLocalX;
        float localXMax = pLocalX + pLocalWidth;
        float localYMin = pLocalY;
        float localYMax = pLocalY + pLocalHeight;
        pVertices[0] = localXMin;
        pVertices[1] = localYMin;
        pVertices[2] = localXMax;
        pVertices[3] = localYMin;
        pVertices[4] = localXMax;
        pVertices[5] = localYMax;
        pVertices[6] = localXMin;
        pVertices[7] = localYMax;
        pLocalToSceneTransformation.transform(pVertices);
    }

    private static void fillVertices(Camera pCamera, float[] pVertices) {
        pVertices[0] = pCamera.getXMin();
        pVertices[1] = pCamera.getYMin();
        pVertices[2] = pCamera.getXMax();
        pVertices[3] = pCamera.getYMin();
        pVertices[4] = pCamera.getXMax();
        pVertices[5] = pCamera.getYMax();
        pVertices[6] = pCamera.getXMin();
        pVertices[7] = pCamera.getYMax();
        MathUtils.rotateAroundCenter(pVertices, pCamera.getRotation(), pCamera.getCenterX(), pCamera.getCenterY());
    }
}
