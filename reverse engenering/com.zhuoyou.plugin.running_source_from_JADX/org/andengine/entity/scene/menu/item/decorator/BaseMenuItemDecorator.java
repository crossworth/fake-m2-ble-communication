package org.andengine.entity.scene.menu.item.decorator;

import java.util.ArrayList;
import java.util.List;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.IUpdateHandler.IUpdateHandlerMatcher;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityComparator;
import org.andengine.entity.IEntityMatcher;
import org.andengine.entity.IEntityParameterCallable;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierMatcher;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.shape.IShape;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.IVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.transformation.Transformation;
import org.andengine.util.color.Color;

public abstract class BaseMenuItemDecorator implements IMenuItem {
    protected final IMenuItem mMenuItem;

    protected abstract void onMenuItemReset(IMenuItem iMenuItem);

    protected abstract void onMenuItemSelected(IMenuItem iMenuItem);

    protected abstract void onMenuItemUnselected(IMenuItem iMenuItem);

    public BaseMenuItemDecorator(IMenuItem pMenuItem) {
        this.mMenuItem = pMenuItem;
    }

    public int getID() {
        return this.mMenuItem.getID();
    }

    public VertexBufferObjectManager getVertexBufferObjectManager() {
        return this.mMenuItem.getVertexBufferObjectManager();
    }

    public IVertexBufferObject getVertexBufferObject() {
        return this.mMenuItem.getVertexBufferObject();
    }

    public final void onSelected() {
        this.mMenuItem.onSelected();
        onMenuItemSelected(this.mMenuItem);
    }

    public final void onUnselected() {
        this.mMenuItem.onUnselected();
        onMenuItemUnselected(this.mMenuItem);
    }

    public float getX() {
        return this.mMenuItem.getX();
    }

    public float getY() {
        return this.mMenuItem.getY();
    }

    public void setX(float pX) {
        this.mMenuItem.setX(pX);
    }

    public void setY(float pY) {
        this.mMenuItem.setY(pY);
    }

    public void setPosition(IEntity pOtherEntity) {
        this.mMenuItem.setPosition(pOtherEntity);
    }

    public void setPosition(float pX, float pY) {
        this.mMenuItem.setPosition(pX, pY);
    }

    public float getWidth() {
        return this.mMenuItem.getWidth();
    }

    public float getWidthScaled() {
        return this.mMenuItem.getWidthScaled();
    }

    public float getHeight() {
        return this.mMenuItem.getHeight();
    }

    public float getHeightScaled() {
        return this.mMenuItem.getHeightScaled();
    }

    public void setWidth(float pWidth) {
        this.mMenuItem.setWidth(pWidth);
    }

    public void setHeight(float pHeight) {
        this.mMenuItem.setHeight(pHeight);
    }

    public void setSize(float pWidth, float pHeight) {
        this.mMenuItem.setSize(pWidth, pHeight);
    }

    public float getRed() {
        return this.mMenuItem.getRed();
    }

    public float getGreen() {
        return this.mMenuItem.getGreen();
    }

    public float getBlue() {
        return this.mMenuItem.getBlue();
    }

    public float getAlpha() {
        return this.mMenuItem.getAlpha();
    }

    public void setRed(float pRed) {
        this.mMenuItem.setRed(pRed);
    }

    public void setGreen(float pGreen) {
        this.mMenuItem.setGreen(pGreen);
    }

    public void setBlue(float pBlue) {
        this.mMenuItem.setBlue(pBlue);
    }

    public void setAlpha(float pAlpha) {
        this.mMenuItem.setAlpha(pAlpha);
    }

    public Color getColor() {
        return this.mMenuItem.getColor();
    }

    public void setColor(Color pColor) {
        this.mMenuItem.setColor(pColor);
    }

    public void setColor(float pRed, float pGreen, float pBlue) {
        this.mMenuItem.setColor(pRed, pGreen, pBlue);
    }

    public void setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
        this.mMenuItem.setColor(pRed, pGreen, pBlue, pAlpha);
    }

    public boolean isRotated() {
        return this.mMenuItem.isRotated();
    }

    public float getRotation() {
        return this.mMenuItem.getRotation();
    }

    public void setRotation(float pRotation) {
        this.mMenuItem.setRotation(pRotation);
    }

    public float getRotationCenterX() {
        return this.mMenuItem.getRotationCenterX();
    }

    public float getRotationCenterY() {
        return this.mMenuItem.getRotationCenterY();
    }

    public void setRotationCenterX(float pRotationCenterX) {
        this.mMenuItem.setRotationCenterX(pRotationCenterX);
    }

    public void setRotationCenterY(float pRotationCenterY) {
        this.mMenuItem.setRotationCenterY(pRotationCenterY);
    }

    public void setRotationCenter(float pRotationCenterX, float pRotationCenterY) {
        this.mMenuItem.setRotationCenter(pRotationCenterX, pRotationCenterY);
    }

    public boolean isScaled() {
        return this.mMenuItem.isScaled();
    }

    public float getScaleX() {
        return this.mMenuItem.getScaleX();
    }

    public float getScaleY() {
        return this.mMenuItem.getScaleY();
    }

    public void setScale(float pScale) {
        this.mMenuItem.setScale(pScale);
    }

    public void setScale(float pScaleX, float pScaleY) {
        this.mMenuItem.setScale(pScaleX, pScaleY);
    }

    public void setScaleX(float pScaleX) {
        this.mMenuItem.setScaleX(pScaleX);
    }

    public void setScaleY(float pScaleY) {
        this.mMenuItem.setScaleY(pScaleY);
    }

    public float getScaleCenterX() {
        return this.mMenuItem.getScaleCenterX();
    }

    public float getScaleCenterY() {
        return this.mMenuItem.getScaleCenterY();
    }

    public void setScaleCenterX(float pScaleCenterX) {
        this.mMenuItem.setScaleCenterX(pScaleCenterX);
    }

    public void setScaleCenterY(float pScaleCenterY) {
        this.mMenuItem.setScaleCenterY(pScaleCenterY);
    }

    public void setScaleCenter(float pScaleCenterX, float pScaleCenterY) {
        this.mMenuItem.setScaleCenter(pScaleCenterX, pScaleCenterY);
    }

    public boolean isSkewed() {
        return this.mMenuItem.isSkewed();
    }

    public float getSkewX() {
        return this.mMenuItem.getSkewX();
    }

    public float getSkewY() {
        return this.mMenuItem.getSkewY();
    }

    public void setSkew(float pSkew) {
        this.mMenuItem.setSkew(pSkew);
    }

    public void setSkew(float pSkewX, float pSkewY) {
        this.mMenuItem.setSkew(pSkewX, pSkewY);
    }

    public void setSkewX(float pSkewX) {
        this.mMenuItem.setSkewX(pSkewX);
    }

    public void setSkewY(float pSkewY) {
        this.mMenuItem.setSkewY(pSkewY);
    }

    public float getSkewCenterX() {
        return this.mMenuItem.getSkewCenterX();
    }

    public float getSkewCenterY() {
        return this.mMenuItem.getSkewCenterY();
    }

    public void setSkewCenterX(float pSkewCenterX) {
        this.mMenuItem.setSkewCenterX(pSkewCenterX);
    }

    public void setSkewCenterY(float pSkewCenterY) {
        this.mMenuItem.setSkewCenterY(pSkewCenterY);
    }

    public void setSkewCenter(float pSkewCenterX, float pSkewCenterY) {
        this.mMenuItem.setSkewCenter(pSkewCenterX, pSkewCenterY);
    }

    public boolean isRotatedOrScaledOrSkewed() {
        return this.mMenuItem.isRotatedOrScaledOrSkewed();
    }

    public boolean collidesWith(IShape pOtherShape) {
        return this.mMenuItem.collidesWith(pOtherShape);
    }

    public float[] getSceneCenterCoordinates() {
        return this.mMenuItem.getSceneCenterCoordinates();
    }

    public float[] getSceneCenterCoordinates(float[] pReuse) {
        return this.mMenuItem.getSceneCenterCoordinates(pReuse);
    }

    public boolean isCullingEnabled() {
        return this.mMenuItem.isCullingEnabled();
    }

    public void registerEntityModifier(IEntityModifier pEntityModifier) {
        this.mMenuItem.registerEntityModifier(pEntityModifier);
    }

    public boolean unregisterEntityModifier(IEntityModifier pEntityModifier) {
        return this.mMenuItem.unregisterEntityModifier(pEntityModifier);
    }

    public boolean unregisterEntityModifiers(IEntityModifierMatcher pEntityModifierMatcher) {
        return this.mMenuItem.unregisterEntityModifiers(pEntityModifierMatcher);
    }

    public int getEntityModifierCount() {
        return this.mMenuItem.getEntityModifierCount();
    }

    public void clearEntityModifiers() {
        this.mMenuItem.clearEntityModifiers();
    }

    public boolean isBlendingEnabled() {
        return this.mMenuItem.isBlendingEnabled();
    }

    public void setBlendingEnabled(boolean pBlendingEnabled) {
        this.mMenuItem.setBlendingEnabled(pBlendingEnabled);
    }

    public int getBlendFunctionSource() {
        return this.mMenuItem.getBlendFunctionSource();
    }

    public void setBlendFunctionSource(int pBlendFunctionSource) {
        this.mMenuItem.setBlendFunctionSource(pBlendFunctionSource);
    }

    public int getBlendFunctionDestination() {
        return this.mMenuItem.getBlendFunctionDestination();
    }

    public void setBlendFunctionDestination(int pBlendFunctionDestination) {
        this.mMenuItem.setBlendFunctionDestination(pBlendFunctionDestination);
    }

    public void setBlendFunction(int pBlendFunctionSource, int pBlendFunctionDestination) {
        this.mMenuItem.setBlendFunction(pBlendFunctionSource, pBlendFunctionDestination);
    }

    public void setCullingEnabled(boolean pCullingEnabled) {
        this.mMenuItem.setCullingEnabled(pCullingEnabled);
    }

    public int getTag() {
        return this.mMenuItem.getTag();
    }

    public void setTag(int pTag) {
        this.mMenuItem.setTag(pTag);
    }

    public int getZIndex() {
        return this.mMenuItem.getZIndex();
    }

    public void setZIndex(int pZIndex) {
        this.mMenuItem.setZIndex(pZIndex);
    }

    public ShaderProgram getShaderProgram() {
        return this.mMenuItem.getShaderProgram();
    }

    public void setShaderProgram(ShaderProgram pShaderProgram) {
        this.mMenuItem.setShaderProgram(pShaderProgram);
    }

    public void onDraw(GLState pGLState, Camera pCamera) {
        this.mMenuItem.onDraw(pGLState, pCamera);
    }

    public void onUpdate(float pSecondsElapsed) {
        this.mMenuItem.onUpdate(pSecondsElapsed);
    }

    public void reset() {
        this.mMenuItem.reset();
        onMenuItemReset(this.mMenuItem);
    }

    public boolean isDisposed() {
        return this.mMenuItem.isDisposed();
    }

    public void dispose() {
        this.mMenuItem.dispose();
    }

    public boolean contains(float pX, float pY) {
        return this.mMenuItem.contains(pX, pY);
    }

    public float[] convertLocalToSceneCoordinates(float pX, float pY) {
        return this.mMenuItem.convertLocalToSceneCoordinates(pX, pY);
    }

    public float[] convertLocalToSceneCoordinates(float pX, float pY, float[] pReuse) {
        return this.mMenuItem.convertLocalToSceneCoordinates(pX, pY, pReuse);
    }

    public float[] convertLocalToSceneCoordinates(float[] pCoordinates) {
        return this.mMenuItem.convertLocalToSceneCoordinates(pCoordinates);
    }

    public float[] convertLocalToSceneCoordinates(float[] pCoordinates, float[] pReuse) {
        return this.mMenuItem.convertLocalToSceneCoordinates(pCoordinates, pReuse);
    }

    public float[] convertSceneToLocalCoordinates(float pX, float pY) {
        return this.mMenuItem.convertSceneToLocalCoordinates(pX, pY);
    }

    public float[] convertSceneToLocalCoordinates(float pX, float pY, float[] pReuse) {
        return this.mMenuItem.convertSceneToLocalCoordinates(pX, pY, pReuse);
    }

    public float[] convertSceneToLocalCoordinates(float[] pCoordinates) {
        return this.mMenuItem.convertSceneToLocalCoordinates(pCoordinates);
    }

    public float[] convertSceneToLocalCoordinates(float[] pCoordinates, float[] pReuse) {
        return this.mMenuItem.convertSceneToLocalCoordinates(pCoordinates, pReuse);
    }

    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return this.mMenuItem.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }

    public int getChildCount() {
        return this.mMenuItem.getChildCount();
    }

    public void attachChild(IEntity pEntity) {
        this.mMenuItem.attachChild(pEntity);
    }

    public IEntity getFirstChild() {
        return this.mMenuItem.getFirstChild();
    }

    public IEntity getLastChild() {
        return this.mMenuItem.getLastChild();
    }

    public IEntity getChildByTag(int pTag) {
        return this.mMenuItem.getChildByTag(pTag);
    }

    public IEntity getChildByIndex(int pIndex) {
        return this.mMenuItem.getChildByIndex(pIndex);
    }

    public IEntity getChildByMatcher(IEntityMatcher pEntityMatcher) {
        return this.mMenuItem.getChildByMatcher(pEntityMatcher);
    }

    public ArrayList<IEntity> query(IEntityMatcher pEntityMatcher) {
        return this.mMenuItem.query(pEntityMatcher);
    }

    public IEntity queryFirst(IEntityMatcher pEntityMatcher) {
        return this.mMenuItem.queryFirst(pEntityMatcher);
    }

    public <L extends List<IEntity>> L query(IEntityMatcher pEntityMatcher, L pResult) {
        return this.mMenuItem.query(pEntityMatcher, pResult);
    }

    public <S extends IEntity> S queryFirstForSubclass(IEntityMatcher pEntityMatcher) {
        return this.mMenuItem.queryFirstForSubclass(pEntityMatcher);
    }

    public <S extends IEntity> ArrayList<S> queryForSubclass(IEntityMatcher pEntityMatcher) throws ClassCastException {
        return this.mMenuItem.queryForSubclass(pEntityMatcher);
    }

    public <L extends List<S>, S extends IEntity> L queryForSubclass(IEntityMatcher pEntityMatcher, L pResult) throws ClassCastException {
        return this.mMenuItem.queryForSubclass(pEntityMatcher, pResult);
    }

    public void sortChildren() {
        this.mMenuItem.sortChildren();
    }

    public void sortChildren(boolean pImmediate) {
        this.mMenuItem.sortChildren(pImmediate);
    }

    public void sortChildren(IEntityComparator pEntityComparator) {
        this.mMenuItem.sortChildren(pEntityComparator);
    }

    public boolean detachSelf() {
        return this.mMenuItem.detachSelf();
    }

    public boolean detachChild(IEntity pEntity) {
        return this.mMenuItem.detachChild(pEntity);
    }

    public IEntity detachChild(int pTag) {
        return this.mMenuItem.detachChild(pTag);
    }

    public IEntity detachChild(IEntityMatcher pEntityMatcher) {
        return this.mMenuItem.detachChild(pEntityMatcher);
    }

    public boolean detachChildren(IEntityMatcher pEntityMatcher) {
        return this.mMenuItem.detachChildren(pEntityMatcher);
    }

    public void detachChildren() {
        this.mMenuItem.detachChildren();
    }

    public void callOnChildren(IEntityParameterCallable pEntityParameterCallable) {
        this.mMenuItem.callOnChildren(pEntityParameterCallable);
    }

    public void callOnChildren(IEntityParameterCallable pEntityParameterCallable, IEntityMatcher pEntityMatcher) {
        this.mMenuItem.callOnChildren(pEntityParameterCallable, pEntityMatcher);
    }

    public Transformation getLocalToSceneTransformation() {
        return this.mMenuItem.getLocalToSceneTransformation();
    }

    public Transformation getSceneToLocalTransformation() {
        return this.mMenuItem.getSceneToLocalTransformation();
    }

    public Transformation getLocalToParentTransformation() {
        return this.mMenuItem.getLocalToParentTransformation();
    }

    public Transformation getParentToLocalTransformation() {
        return this.mMenuItem.getParentToLocalTransformation();
    }

    public boolean hasParent() {
        return this.mMenuItem.hasParent();
    }

    public IEntity getParent() {
        return this.mMenuItem.getParent();
    }

    public void setParent(IEntity pEntity) {
        this.mMenuItem.setParent(pEntity);
    }

    public boolean isVisible() {
        return this.mMenuItem.isVisible();
    }

    public void setVisible(boolean pVisible) {
        this.mMenuItem.setVisible(pVisible);
    }

    public boolean isCulled(Camera pCamera) {
        return this.mMenuItem.isCulled(pCamera);
    }

    public boolean isChildrenVisible() {
        return this.mMenuItem.isChildrenVisible();
    }

    public void setChildrenVisible(boolean pChildrenVisible) {
        this.mMenuItem.setChildrenVisible(pChildrenVisible);
    }

    public boolean isIgnoreUpdate() {
        return this.mMenuItem.isIgnoreUpdate();
    }

    public void setIgnoreUpdate(boolean pIgnoreUpdate) {
        this.mMenuItem.setIgnoreUpdate(pIgnoreUpdate);
    }

    public boolean isChildrenIgnoreUpdate() {
        return this.mMenuItem.isChildrenIgnoreUpdate();
    }

    public void setChildrenIgnoreUpdate(boolean pChildrenIgnoreUpdate) {
        this.mMenuItem.setChildrenIgnoreUpdate(pChildrenIgnoreUpdate);
    }

    public void setUserData(Object pUserData) {
        this.mMenuItem.setUserData(pUserData);
    }

    public Object getUserData() {
        return this.mMenuItem.getUserData();
    }

    public void onAttached() {
        this.mMenuItem.onAttached();
    }

    public void onDetached() {
        this.mMenuItem.onDetached();
    }

    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
        this.mMenuItem.registerUpdateHandler(pUpdateHandler);
    }

    public boolean unregisterUpdateHandler(IUpdateHandler pUpdateHandler) {
        return this.mMenuItem.unregisterUpdateHandler(pUpdateHandler);
    }

    public int getUpdateHandlerCount() {
        return this.mMenuItem.getUpdateHandlerCount();
    }

    public void clearUpdateHandlers() {
        this.mMenuItem.clearUpdateHandlers();
    }

    public boolean unregisterUpdateHandlers(IUpdateHandlerMatcher pUpdateHandlerMatcher) {
        return this.mMenuItem.unregisterUpdateHandlers(pUpdateHandlerMatcher);
    }

    public void toString(StringBuilder pStringBuilder) {
        this.mMenuItem.toString(pStringBuilder);
    }
}
