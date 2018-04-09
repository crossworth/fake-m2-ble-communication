package org.andengine.entity;

import java.util.ArrayList;
import java.util.List;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IDrawHandler;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.IUpdateHandler.IUpdateHandlerMatcher;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierMatcher;
import org.andengine.util.IDisposable;
import org.andengine.util.adt.transformation.Transformation;
import org.andengine.util.color.Color;

public interface IEntity extends IDrawHandler, IUpdateHandler, IDisposable {
    public static final int TAG_INVALID = Integer.MIN_VALUE;

    void attachChild(IEntity iEntity);

    void callOnChildren(IEntityParameterCallable iEntityParameterCallable);

    void callOnChildren(IEntityParameterCallable iEntityParameterCallable, IEntityMatcher iEntityMatcher);

    void clearEntityModifiers();

    void clearUpdateHandlers();

    float[] convertLocalToSceneCoordinates(float f, float f2);

    float[] convertLocalToSceneCoordinates(float f, float f2, float[] fArr);

    float[] convertLocalToSceneCoordinates(float[] fArr);

    float[] convertLocalToSceneCoordinates(float[] fArr, float[] fArr2);

    float[] convertSceneToLocalCoordinates(float f, float f2);

    float[] convertSceneToLocalCoordinates(float f, float f2, float[] fArr);

    float[] convertSceneToLocalCoordinates(float[] fArr);

    float[] convertSceneToLocalCoordinates(float[] fArr, float[] fArr2);

    IEntity detachChild(int i);

    IEntity detachChild(IEntityMatcher iEntityMatcher);

    boolean detachChild(IEntity iEntity);

    void detachChildren();

    boolean detachChildren(IEntityMatcher iEntityMatcher);

    boolean detachSelf();

    float getAlpha();

    float getBlue();

    IEntity getChildByIndex(int i);

    IEntity getChildByMatcher(IEntityMatcher iEntityMatcher);

    IEntity getChildByTag(int i);

    int getChildCount();

    Color getColor();

    int getEntityModifierCount();

    IEntity getFirstChild();

    float getGreen();

    IEntity getLastChild();

    Transformation getLocalToParentTransformation();

    Transformation getLocalToSceneTransformation();

    IEntity getParent();

    Transformation getParentToLocalTransformation();

    float getRed();

    float getRotation();

    float getRotationCenterX();

    float getRotationCenterY();

    float getScaleCenterX();

    float getScaleCenterY();

    float getScaleX();

    float getScaleY();

    float[] getSceneCenterCoordinates();

    float[] getSceneCenterCoordinates(float[] fArr);

    Transformation getSceneToLocalTransformation();

    float getSkewCenterX();

    float getSkewCenterY();

    float getSkewX();

    float getSkewY();

    int getTag();

    int getUpdateHandlerCount();

    Object getUserData();

    float getX();

    float getY();

    int getZIndex();

    boolean hasParent();

    boolean isChildrenIgnoreUpdate();

    boolean isChildrenVisible();

    boolean isCulled(Camera camera);

    boolean isCullingEnabled();

    boolean isIgnoreUpdate();

    boolean isRotated();

    boolean isRotatedOrScaledOrSkewed();

    boolean isScaled();

    boolean isSkewed();

    boolean isVisible();

    void onAttached();

    void onDetached();

    ArrayList<IEntity> query(IEntityMatcher iEntityMatcher);

    <L extends List<IEntity>> L query(IEntityMatcher iEntityMatcher, L l);

    IEntity queryFirst(IEntityMatcher iEntityMatcher);

    <S extends IEntity> S queryFirstForSubclass(IEntityMatcher iEntityMatcher);

    <S extends IEntity> ArrayList<S> queryForSubclass(IEntityMatcher iEntityMatcher) throws ClassCastException;

    <L extends List<S>, S extends IEntity> L queryForSubclass(IEntityMatcher iEntityMatcher, L l) throws ClassCastException;

    void registerEntityModifier(IEntityModifier iEntityModifier);

    void registerUpdateHandler(IUpdateHandler iUpdateHandler);

    void setAlpha(float f);

    void setBlue(float f);

    void setChildrenIgnoreUpdate(boolean z);

    void setChildrenVisible(boolean z);

    void setColor(float f, float f2, float f3);

    void setColor(float f, float f2, float f3, float f4);

    void setColor(Color color);

    void setCullingEnabled(boolean z);

    void setGreen(float f);

    void setIgnoreUpdate(boolean z);

    void setParent(IEntity iEntity);

    void setPosition(float f, float f2);

    void setPosition(IEntity iEntity);

    void setRed(float f);

    void setRotation(float f);

    void setRotationCenter(float f, float f2);

    void setRotationCenterX(float f);

    void setRotationCenterY(float f);

    void setScale(float f);

    void setScale(float f, float f2);

    void setScaleCenter(float f, float f2);

    void setScaleCenterX(float f);

    void setScaleCenterY(float f);

    void setScaleX(float f);

    void setScaleY(float f);

    void setSkew(float f);

    void setSkew(float f, float f2);

    void setSkewCenter(float f, float f2);

    void setSkewCenterX(float f);

    void setSkewCenterY(float f);

    void setSkewX(float f);

    void setSkewY(float f);

    void setTag(int i);

    void setUserData(Object obj);

    void setVisible(boolean z);

    void setX(float f);

    void setY(float f);

    void setZIndex(int i);

    void sortChildren();

    void sortChildren(IEntityComparator iEntityComparator);

    void sortChildren(boolean z);

    void toString(StringBuilder stringBuilder);

    boolean unregisterEntityModifier(IEntityModifier iEntityModifier);

    boolean unregisterEntityModifiers(IEntityModifierMatcher iEntityModifierMatcher);

    boolean unregisterUpdateHandler(IUpdateHandler iUpdateHandler);

    boolean unregisterUpdateHandlers(IUpdateHandlerMatcher iUpdateHandlerMatcher);
}
