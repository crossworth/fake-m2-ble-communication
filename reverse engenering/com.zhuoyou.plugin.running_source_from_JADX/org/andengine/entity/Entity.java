package org.andengine.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.IUpdateHandler.IUpdateHandlerMatcher;
import org.andengine.engine.handler.UpdateHandlerList;
import org.andengine.entity.modifier.EntityModifierList;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierMatcher;
import org.andengine.opengl.util.GLState;
import org.andengine.util.IDisposable.AlreadyDisposedException;
import org.andengine.util.IMatcher;
import org.andengine.util.adt.list.SmartList;
import org.andengine.util.adt.transformation.Transformation;
import org.andengine.util.call.ParameterCallable;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

public class Entity implements IEntity {
    private static final int CHILDREN_CAPACITY_DEFAULT = 4;
    private static final int ENTITYMODIFIERS_CAPACITY_DEFAULT = 4;
    private static final ParameterCallable<IEntity> PARAMETERCALLABLE_DETACHCHILD = new C20481();
    private static final int UPDATEHANDLERS_CAPACITY_DEFAULT = 4;
    private static final float[] VERTICES_LOCAL_TO_SCENE_TMP = new float[2];
    private static final float[] VERTICES_SCENE_TO_LOCAL_TMP = new float[2];
    protected SmartList<IEntity> mChildren;
    protected boolean mChildrenIgnoreUpdate;
    protected boolean mChildrenSortPending;
    protected boolean mChildrenVisible;
    protected Color mColor;
    protected boolean mCullingEnabled;
    protected boolean mDisposed;
    private EntityModifierList mEntityModifiers;
    protected boolean mIgnoreUpdate;
    private Transformation mLocalToParentTransformation;
    private boolean mLocalToParentTransformationDirty;
    private Transformation mLocalToSceneTransformation;
    private IEntity mParent;
    private Transformation mParentToLocalTransformation;
    private boolean mParentToLocalTransformationDirty;
    protected float mRotation;
    protected float mRotationCenterX;
    protected float mRotationCenterY;
    protected float mScaleCenterX;
    protected float mScaleCenterY;
    protected float mScaleX;
    protected float mScaleY;
    private Transformation mSceneToLocalTransformation;
    protected float mSkewCenterX;
    protected float mSkewCenterY;
    protected float mSkewX;
    protected float mSkewY;
    protected int mTag;
    private UpdateHandlerList mUpdateHandlers;
    private Object mUserData;
    protected boolean mVisible;
    protected float mX;
    protected float mY;
    protected int mZIndex;

    static class C20481 implements ParameterCallable<IEntity> {
        C20481() {
        }

        public void call(IEntity pEntity) {
            pEntity.setParent(null);
            pEntity.onDetached();
        }
    }

    public Entity() {
        this(0.0f, 0.0f);
    }

    public Entity(float pX, float pY) {
        this.mVisible = true;
        this.mChildrenVisible = true;
        this.mTag = Integer.MIN_VALUE;
        this.mZIndex = 0;
        this.mColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mRotation = 0.0f;
        this.mRotationCenterX = 0.0f;
        this.mRotationCenterY = 0.0f;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mScaleCenterX = 0.0f;
        this.mScaleCenterY = 0.0f;
        this.mSkewX = 0.0f;
        this.mSkewY = 0.0f;
        this.mSkewCenterX = 0.0f;
        this.mSkewCenterY = 0.0f;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
        this.mX = pX;
        this.mY = pY;
    }

    protected void onUpdateColor() {
    }

    public boolean isDisposed() {
        return this.mDisposed;
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public void setVisible(boolean pVisible) {
        this.mVisible = pVisible;
    }

    public boolean isCullingEnabled() {
        return this.mCullingEnabled;
    }

    public void setCullingEnabled(boolean pCullingEnabled) {
        this.mCullingEnabled = pCullingEnabled;
    }

    public boolean isCulled(Camera pCamera) {
        return false;
    }

    public boolean isChildrenVisible() {
        return this.mChildrenVisible;
    }

    public void setChildrenVisible(boolean pChildrenVisible) {
        this.mChildrenVisible = pChildrenVisible;
    }

    public boolean isIgnoreUpdate() {
        return this.mIgnoreUpdate;
    }

    public void setIgnoreUpdate(boolean pIgnoreUpdate) {
        this.mIgnoreUpdate = pIgnoreUpdate;
    }

    public boolean isChildrenIgnoreUpdate() {
        return this.mChildrenIgnoreUpdate;
    }

    public void setChildrenIgnoreUpdate(boolean pChildrenIgnoreUpdate) {
        this.mChildrenIgnoreUpdate = pChildrenIgnoreUpdate;
    }

    public boolean hasParent() {
        return this.mParent != null;
    }

    public IEntity getParent() {
        return this.mParent;
    }

    public void setParent(IEntity pEntity) {
        this.mParent = pEntity;
    }

    public int getTag() {
        return this.mTag;
    }

    public void setTag(int pTag) {
        this.mTag = pTag;
    }

    public int getZIndex() {
        return this.mZIndex;
    }

    public void setZIndex(int pZIndex) {
        this.mZIndex = pZIndex;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public void setX(float pX) {
        this.mX = pX;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setY(float pY) {
        this.mY = pY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setPosition(IEntity pOtherEntity) {
        setPosition(pOtherEntity.getX(), pOtherEntity.getY());
    }

    public void setPosition(float pX, float pY) {
        this.mX = pX;
        this.mY = pY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public float getRotation() {
        return this.mRotation;
    }

    public boolean isRotated() {
        return this.mRotation != 0.0f;
    }

    public void setRotation(float pRotation) {
        this.mRotation = pRotation;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public float getRotationCenterX() {
        return this.mRotationCenterX;
    }

    public float getRotationCenterY() {
        return this.mRotationCenterY;
    }

    public void setRotationCenterX(float pRotationCenterX) {
        this.mRotationCenterX = pRotationCenterX;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setRotationCenterY(float pRotationCenterY) {
        this.mRotationCenterY = pRotationCenterY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setRotationCenter(float pRotationCenterX, float pRotationCenterY) {
        this.mRotationCenterX = pRotationCenterX;
        this.mRotationCenterY = pRotationCenterY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public boolean isScaled() {
        return (this.mScaleX == 1.0f && this.mScaleY == 1.0f) ? false : true;
    }

    public float getScaleX() {
        return this.mScaleX;
    }

    public float getScaleY() {
        return this.mScaleY;
    }

    public void setScaleX(float pScaleX) {
        this.mScaleX = pScaleX;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setScaleY(float pScaleY) {
        this.mScaleY = pScaleY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setScale(float pScale) {
        this.mScaleX = pScale;
        this.mScaleY = pScale;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setScale(float pScaleX, float pScaleY) {
        this.mScaleX = pScaleX;
        this.mScaleY = pScaleY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public float getScaleCenterX() {
        return this.mScaleCenterX;
    }

    public float getScaleCenterY() {
        return this.mScaleCenterY;
    }

    public void setScaleCenterX(float pScaleCenterX) {
        this.mScaleCenterX = pScaleCenterX;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setScaleCenterY(float pScaleCenterY) {
        this.mScaleCenterY = pScaleCenterY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setScaleCenter(float pScaleCenterX, float pScaleCenterY) {
        this.mScaleCenterX = pScaleCenterX;
        this.mScaleCenterY = pScaleCenterY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public boolean isSkewed() {
        return (this.mSkewX == 0.0f && this.mSkewY == 0.0f) ? false : true;
    }

    public float getSkewX() {
        return this.mSkewX;
    }

    public float getSkewY() {
        return this.mSkewY;
    }

    public void setSkewX(float pSkewX) {
        this.mSkewX = pSkewX;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setSkewY(float pSkewY) {
        this.mSkewY = pSkewY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setSkew(float pSkew) {
        this.mSkewX = pSkew;
        this.mSkewY = pSkew;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setSkew(float pSkewX, float pSkewY) {
        this.mSkewX = pSkewX;
        this.mSkewY = pSkewY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public float getSkewCenterX() {
        return this.mSkewCenterX;
    }

    public float getSkewCenterY() {
        return this.mSkewCenterY;
    }

    public void setSkewCenterX(float pSkewCenterX) {
        this.mSkewCenterX = pSkewCenterX;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setSkewCenterY(float pSkewCenterY) {
        this.mSkewCenterY = pSkewCenterY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public void setSkewCenter(float pSkewCenterX, float pSkewCenterY) {
        this.mSkewCenterX = pSkewCenterX;
        this.mSkewCenterY = pSkewCenterY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    public boolean isRotatedOrScaledOrSkewed() {
        return (this.mRotation == 0.0f && this.mScaleX == 1.0f && this.mScaleY == 1.0f && this.mSkewX == 0.0f && this.mSkewY == 0.0f) ? false : true;
    }

    public float getRed() {
        return this.mColor.getRed();
    }

    public float getGreen() {
        return this.mColor.getGreen();
    }

    public float getBlue() {
        return this.mColor.getBlue();
    }

    public float getAlpha() {
        return this.mColor.getAlpha();
    }

    public Color getColor() {
        return this.mColor;
    }

    public void setColor(Color pColor) {
        this.mColor.set(pColor);
        onUpdateColor();
    }

    public void setRed(float pRed) {
        if (this.mColor.setRedChecking(pRed)) {
            onUpdateColor();
        }
    }

    public void setGreen(float pGreen) {
        if (this.mColor.setGreenChecking(pGreen)) {
            onUpdateColor();
        }
    }

    public void setBlue(float pBlue) {
        if (this.mColor.setBlueChecking(pBlue)) {
            onUpdateColor();
        }
    }

    public void setAlpha(float pAlpha) {
        if (this.mColor.setAlphaChecking(pAlpha)) {
            onUpdateColor();
        }
    }

    public void setColor(float pRed, float pGreen, float pBlue) {
        if (this.mColor.setChecking(pRed, pGreen, pBlue)) {
            onUpdateColor();
        }
    }

    public void setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
        if (this.mColor.setChecking(pRed, pGreen, pBlue, pAlpha)) {
            onUpdateColor();
        }
    }

    public int getChildCount() {
        if (this.mChildren == null) {
            return 0;
        }
        return this.mChildren.size();
    }

    public IEntity getChildByTag(int pTag) {
        if (this.mChildren == null) {
            return null;
        }
        for (int i = this.mChildren.size() - 1; i >= 0; i--) {
            IEntity child = (IEntity) this.mChildren.get(i);
            if (child.getTag() == pTag) {
                return child;
            }
        }
        return null;
    }

    public IEntity getChildByIndex(int pIndex) {
        if (this.mChildren == null) {
            return null;
        }
        return (IEntity) this.mChildren.get(pIndex);
    }

    public IEntity getChildByMatcher(IEntityMatcher pEntityMatcher) {
        if (this.mChildren == null) {
            return null;
        }
        return (IEntity) this.mChildren.get(pEntityMatcher);
    }

    public IEntity getFirstChild() {
        if (this.mChildren == null) {
            return null;
        }
        return (IEntity) this.mChildren.get(0);
    }

    public IEntity getLastChild() {
        if (this.mChildren == null) {
            return null;
        }
        return (IEntity) this.mChildren.get(this.mChildren.size() - 1);
    }

    public ArrayList<IEntity> query(IEntityMatcher pEntityMatcher) {
        return (ArrayList) query(pEntityMatcher, new ArrayList());
    }

    public IEntity queryFirst(IEntityMatcher pEntityMatcher) {
        return queryFirstForSubclass(pEntityMatcher);
    }

    public <S extends IEntity> S queryFirstForSubclass(IEntityMatcher pEntityMatcher) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            IEntity child = (IEntity) this.mChildren.get(i);
            if (pEntityMatcher.matches(child)) {
                return child;
            }
            S childQueryFirst = child.queryFirstForSubclass(pEntityMatcher);
            if (childQueryFirst != null) {
                return childQueryFirst;
            }
        }
        return null;
    }

    public <L extends List<IEntity>> L query(IEntityMatcher pEntityMatcher, L pResult) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            IEntity child = (IEntity) this.mChildren.get(i);
            if (pEntityMatcher.matches(child)) {
                pResult.add(child);
            }
            child.query(pEntityMatcher, pResult);
        }
        return pResult;
    }

    public <S extends IEntity> ArrayList<S> queryForSubclass(IEntityMatcher pEntityMatcher) throws ClassCastException {
        return (ArrayList) queryForSubclass(pEntityMatcher, new ArrayList());
    }

    public <L extends List<S>, S extends IEntity> L queryForSubclass(IEntityMatcher pEntityMatcher, L pResult) throws ClassCastException {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            IEntity child = (IEntity) this.mChildren.get(i);
            if (pEntityMatcher.matches(child)) {
                pResult.add(child);
            }
            child.queryForSubclass(pEntityMatcher, pResult);
        }
        return pResult;
    }

    public boolean detachSelf() {
        IEntity parent = this.mParent;
        if (parent != null) {
            return parent.detachChild((IEntity) this);
        }
        return false;
    }

    public void detachChildren() {
        if (this.mChildren != null) {
            this.mChildren.clear(PARAMETERCALLABLE_DETACHCHILD);
        }
    }

    public void attachChild(IEntity pEntity) throws IllegalStateException {
        assertEntityHasNoParent(pEntity);
        if (this.mChildren == null) {
            allocateChildren();
        }
        this.mChildren.add(pEntity);
        pEntity.setParent(this);
        pEntity.onAttached();
    }

    public void sortChildren() {
        sortChildren(true);
    }

    public void sortChildren(boolean pImmediate) {
        if (this.mChildren != null) {
            if (pImmediate) {
                ZIndexSorter.getInstance().sort(this.mChildren);
            } else {
                this.mChildrenSortPending = true;
            }
        }
    }

    public void sortChildren(IEntityComparator pEntityComparator) {
        if (this.mChildren != null) {
            ZIndexSorter.getInstance().sort(this.mChildren, (Comparator) pEntityComparator);
        }
    }

    public boolean detachChild(IEntity pEntity) {
        if (this.mChildren == null) {
            return false;
        }
        return this.mChildren.remove((Object) pEntity, PARAMETERCALLABLE_DETACHCHILD);
    }

    public IEntity detachChild(int pTag) {
        if (this.mChildren == null) {
            return null;
        }
        for (int i = this.mChildren.size() - 1; i >= 0; i--) {
            if (((IEntity) this.mChildren.get(i)).getTag() == pTag) {
                IEntity removed = (IEntity) this.mChildren.remove(i);
                PARAMETERCALLABLE_DETACHCHILD.call(removed);
                return removed;
            }
        }
        return null;
    }

    public IEntity detachChild(IEntityMatcher pEntityMatcher) {
        if (this.mChildren == null) {
            return null;
        }
        return (IEntity) this.mChildren.remove((IMatcher) pEntityMatcher, PARAMETERCALLABLE_DETACHCHILD);
    }

    public boolean detachChildren(IEntityMatcher pEntityMatcher) {
        if (this.mChildren == null) {
            return false;
        }
        return this.mChildren.removeAll(pEntityMatcher, PARAMETERCALLABLE_DETACHCHILD);
    }

    public void callOnChildren(IEntityParameterCallable pEntityParameterCallable) {
        if (this.mChildren != null) {
            this.mChildren.call(pEntityParameterCallable);
        }
    }

    public void callOnChildren(IEntityParameterCallable pEntityParameterCallable, IEntityMatcher pEntityMatcher) {
        if (this.mChildren != null) {
            this.mChildren.call(pEntityMatcher, pEntityParameterCallable);
        }
    }

    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
        if (this.mUpdateHandlers == null) {
            allocateUpdateHandlers();
        }
        this.mUpdateHandlers.add(pUpdateHandler);
    }

    public boolean unregisterUpdateHandler(IUpdateHandler pUpdateHandler) {
        if (this.mUpdateHandlers == null) {
            return false;
        }
        return this.mUpdateHandlers.remove(pUpdateHandler);
    }

    public boolean unregisterUpdateHandlers(IUpdateHandlerMatcher pUpdateHandlerMatcher) {
        if (this.mUpdateHandlers == null) {
            return false;
        }
        return this.mUpdateHandlers.removeAll(pUpdateHandlerMatcher);
    }

    public int getUpdateHandlerCount() {
        if (this.mUpdateHandlers == null) {
            return 0;
        }
        return this.mUpdateHandlers.size();
    }

    public void clearUpdateHandlers() {
        if (this.mUpdateHandlers != null) {
            this.mUpdateHandlers.clear();
        }
    }

    public void registerEntityModifier(IEntityModifier pEntityModifier) {
        if (this.mEntityModifiers == null) {
            allocateEntityModifiers();
        }
        this.mEntityModifiers.add((IModifier) pEntityModifier);
    }

    public boolean unregisterEntityModifier(IEntityModifier pEntityModifier) {
        if (this.mEntityModifiers == null) {
            return false;
        }
        return this.mEntityModifiers.remove(pEntityModifier);
    }

    public boolean unregisterEntityModifiers(IEntityModifierMatcher pEntityModifierMatcher) {
        if (this.mEntityModifiers == null) {
            return false;
        }
        return this.mEntityModifiers.removeAll(pEntityModifierMatcher);
    }

    public int getEntityModifierCount() {
        if (this.mEntityModifiers == null) {
            return 0;
        }
        return this.mEntityModifiers.size();
    }

    public void clearEntityModifiers() {
        if (this.mEntityModifiers != null) {
            this.mEntityModifiers.clear();
        }
    }

    public float[] getSceneCenterCoordinates() {
        return convertLocalToSceneCoordinates(0.0f, 0.0f);
    }

    public float[] getSceneCenterCoordinates(float[] pReuse) {
        return convertLocalToSceneCoordinates(0.0f, 0.0f, pReuse);
    }

    public Transformation getLocalToParentTransformation() {
        if (this.mLocalToParentTransformation == null) {
            this.mLocalToParentTransformation = new Transformation();
        }
        Transformation localToParentTransformation = this.mLocalToParentTransformation;
        if (this.mLocalToParentTransformationDirty) {
            localToParentTransformation.setToIdentity();
            float scaleX = this.mScaleX;
            float scaleY = this.mScaleY;
            if (!(scaleX == 1.0f && scaleY == 1.0f)) {
                float scaleCenterX = this.mScaleCenterX;
                float scaleCenterY = this.mScaleCenterY;
                localToParentTransformation.postTranslate(-scaleCenterX, -scaleCenterY);
                localToParentTransformation.postScale(scaleX, scaleY);
                localToParentTransformation.postTranslate(scaleCenterX, scaleCenterY);
            }
            float skewX = this.mSkewX;
            float skewY = this.mSkewY;
            if (!(skewX == 0.0f && skewY == 0.0f)) {
                float skewCenterX = this.mSkewCenterX;
                float skewCenterY = this.mSkewCenterY;
                localToParentTransformation.postTranslate(-skewCenterX, -skewCenterY);
                localToParentTransformation.postSkew(skewX, skewY);
                localToParentTransformation.postTranslate(skewCenterX, skewCenterY);
            }
            float rotation = this.mRotation;
            if (rotation != 0.0f) {
                float rotationCenterX = this.mRotationCenterX;
                float rotationCenterY = this.mRotationCenterY;
                localToParentTransformation.postTranslate(-rotationCenterX, -rotationCenterY);
                localToParentTransformation.postRotate(rotation);
                localToParentTransformation.postTranslate(rotationCenterX, rotationCenterY);
            }
            localToParentTransformation.postTranslate(this.mX, this.mY);
            this.mLocalToParentTransformationDirty = false;
        }
        return localToParentTransformation;
    }

    public Transformation getParentToLocalTransformation() {
        if (this.mParentToLocalTransformation == null) {
            this.mParentToLocalTransformation = new Transformation();
        }
        Transformation parentToLocalTransformation = this.mParentToLocalTransformation;
        if (this.mParentToLocalTransformationDirty) {
            parentToLocalTransformation.setToIdentity();
            parentToLocalTransformation.postTranslate(-this.mX, -this.mY);
            float rotation = this.mRotation;
            if (rotation != 0.0f) {
                float rotationCenterX = this.mRotationCenterX;
                float rotationCenterY = this.mRotationCenterY;
                parentToLocalTransformation.postTranslate(-rotationCenterX, -rotationCenterY);
                parentToLocalTransformation.postRotate(-rotation);
                parentToLocalTransformation.postTranslate(rotationCenterX, rotationCenterY);
            }
            float skewX = this.mSkewX;
            float skewY = this.mSkewY;
            if (!(skewX == 0.0f && skewY == 0.0f)) {
                float skewCenterX = this.mSkewCenterX;
                float skewCenterY = this.mSkewCenterY;
                parentToLocalTransformation.postTranslate(-skewCenterX, -skewCenterY);
                parentToLocalTransformation.postSkew(-skewX, -skewY);
                parentToLocalTransformation.postTranslate(skewCenterX, skewCenterY);
            }
            float scaleX = this.mScaleX;
            float scaleY = this.mScaleY;
            if (!(scaleX == 1.0f && scaleY == 1.0f)) {
                float scaleCenterX = this.mScaleCenterX;
                float scaleCenterY = this.mScaleCenterY;
                parentToLocalTransformation.postTranslate(-scaleCenterX, -scaleCenterY);
                parentToLocalTransformation.postScale(1.0f / scaleX, 1.0f / scaleY);
                parentToLocalTransformation.postTranslate(scaleCenterX, scaleCenterY);
            }
            this.mParentToLocalTransformationDirty = false;
        }
        return parentToLocalTransformation;
    }

    public Transformation getLocalToSceneTransformation() {
        if (this.mLocalToSceneTransformation == null) {
            this.mLocalToSceneTransformation = new Transformation();
        }
        Transformation localToSceneTransformation = this.mLocalToSceneTransformation;
        localToSceneTransformation.setTo(getLocalToParentTransformation());
        IEntity parent = this.mParent;
        if (parent != null) {
            localToSceneTransformation.postConcat(parent.getLocalToSceneTransformation());
        }
        return localToSceneTransformation;
    }

    public Transformation getSceneToLocalTransformation() {
        if (this.mSceneToLocalTransformation == null) {
            this.mSceneToLocalTransformation = new Transformation();
        }
        Transformation sceneToLocalTransformation = this.mSceneToLocalTransformation;
        sceneToLocalTransformation.setTo(getParentToLocalTransformation());
        IEntity parent = this.mParent;
        if (parent != null) {
            sceneToLocalTransformation.preConcat(parent.getSceneToLocalTransformation());
        }
        return sceneToLocalTransformation;
    }

    public float[] convertLocalToSceneCoordinates(float pX, float pY) {
        return convertLocalToSceneCoordinates(pX, pY, VERTICES_LOCAL_TO_SCENE_TMP);
    }

    public float[] convertLocalToSceneCoordinates(float pX, float pY, float[] pReuse) {
        Transformation localToSceneTransformation = getLocalToSceneTransformation();
        pReuse[0] = pX;
        pReuse[1] = pY;
        localToSceneTransformation.transform(pReuse);
        return pReuse;
    }

    public float[] convertLocalToSceneCoordinates(float[] pCoordinates) {
        return convertLocalToSceneCoordinates(pCoordinates, VERTICES_LOCAL_TO_SCENE_TMP);
    }

    public float[] convertLocalToSceneCoordinates(float[] pCoordinates, float[] pReuse) {
        Transformation localToSceneTransformation = getLocalToSceneTransformation();
        pReuse[0] = pCoordinates[0];
        pReuse[1] = pCoordinates[1];
        localToSceneTransformation.transform(pReuse);
        return pReuse;
    }

    public float[] convertSceneToLocalCoordinates(float pX, float pY) {
        return convertSceneToLocalCoordinates(pX, pY, VERTICES_SCENE_TO_LOCAL_TMP);
    }

    public float[] convertSceneToLocalCoordinates(float pX, float pY, float[] pReuse) {
        pReuse[0] = pX;
        pReuse[1] = pY;
        getSceneToLocalTransformation().transform(pReuse);
        return pReuse;
    }

    public float[] convertSceneToLocalCoordinates(float[] pCoordinates) {
        return convertSceneToLocalCoordinates(pCoordinates, VERTICES_SCENE_TO_LOCAL_TMP);
    }

    public float[] convertSceneToLocalCoordinates(float[] pCoordinates, float[] pReuse) {
        pReuse[0] = pCoordinates[0];
        pReuse[1] = pCoordinates[1];
        getSceneToLocalTransformation().transform(pReuse);
        return pReuse;
    }

    public void onAttached() {
    }

    public void onDetached() {
    }

    public Object getUserData() {
        return this.mUserData;
    }

    public void setUserData(Object pUserData) {
        this.mUserData = pUserData;
    }

    public final void onDraw(GLState pGLState, Camera pCamera) {
        if (!this.mVisible) {
            return;
        }
        if (!this.mCullingEnabled || !isCulled(pCamera)) {
            onManagedDraw(pGLState, pCamera);
        }
    }

    public final void onUpdate(float pSecondsElapsed) {
        if (!this.mIgnoreUpdate) {
            onManagedUpdate(pSecondsElapsed);
        }
    }

    public void reset() {
        this.mVisible = true;
        this.mCullingEnabled = false;
        this.mIgnoreUpdate = false;
        this.mChildrenVisible = true;
        this.mChildrenIgnoreUpdate = false;
        this.mRotation = 0.0f;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mSkewX = 0.0f;
        this.mSkewY = 0.0f;
        this.mColor.reset();
        if (this.mEntityModifiers != null) {
            this.mEntityModifiers.reset();
        }
        if (this.mChildren != null) {
            SmartList<IEntity> entities = this.mChildren;
            for (int i = entities.size() - 1; i >= 0; i--) {
                ((IEntity) entities.get(i)).reset();
            }
        }
    }

    public void dispose() {
        if (this.mDisposed) {
            throw new AlreadyDisposedException();
        }
        this.mDisposed = true;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (!this.mDisposed) {
            dispose();
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        toString(stringBuilder);
        return stringBuilder.toString();
    }

    public void toString(StringBuilder pStringBuilder) {
        pStringBuilder.append(getClass().getSimpleName());
        if (this.mChildren != null && this.mChildren.size() > 0) {
            pStringBuilder.append(" [");
            SmartList<IEntity> entities = this.mChildren;
            for (int i = 0; i < entities.size(); i++) {
                ((IEntity) entities.get(i)).toString(pStringBuilder);
                if (i < entities.size() - 1) {
                    pStringBuilder.append(", ");
                }
            }
            pStringBuilder.append("]");
        }
    }

    protected void preDraw(GLState pGLState, Camera pCamera) {
    }

    protected void draw(GLState pGLState, Camera pCamera) {
    }

    protected void postDraw(GLState pGLState, Camera pCamera) {
    }

    private void allocateEntityModifiers() {
        this.mEntityModifiers = new EntityModifierList(this, 4);
    }

    private void allocateChildren() {
        this.mChildren = new SmartList(4);
    }

    private void allocateUpdateHandlers() {
        this.mUpdateHandlers = new UpdateHandlerList(4);
    }

    protected void onApplyTransformations(GLState pGLState) {
        applyTranslation(pGLState);
        applyRotation(pGLState);
        applySkew(pGLState);
        applyScale(pGLState);
    }

    protected void applyTranslation(GLState pGLState) {
        pGLState.translateModelViewGLMatrixf(this.mX, this.mY, 0.0f);
    }

    protected void applyRotation(GLState pGLState) {
        float rotation = this.mRotation;
        if (rotation != 0.0f) {
            float rotationCenterX = this.mRotationCenterX;
            float rotationCenterY = this.mRotationCenterY;
            pGLState.translateModelViewGLMatrixf(rotationCenterX, rotationCenterY, 0.0f);
            pGLState.rotateModelViewGLMatrixf(rotation, 0.0f, 0.0f, 1.0f);
            pGLState.translateModelViewGLMatrixf(-rotationCenterX, -rotationCenterY, 0.0f);
        }
    }

    protected void applySkew(GLState pGLState) {
        float skewX = this.mSkewX;
        float skewY = this.mSkewY;
        if (skewX != 0.0f || skewY != 0.0f) {
            float skewCenterX = this.mSkewCenterX;
            float skewCenterY = this.mSkewCenterY;
            pGLState.translateModelViewGLMatrixf(skewCenterX, skewCenterY, 0.0f);
            pGLState.skewModelViewGLMatrixf(skewX, skewY);
            pGLState.translateModelViewGLMatrixf(-skewCenterX, -skewCenterY, 0.0f);
        }
    }

    protected void applyScale(GLState pGLState) {
        float scaleX = this.mScaleX;
        float scaleY = this.mScaleY;
        if (scaleX != 1.0f || scaleY != 1.0f) {
            float scaleCenterX = this.mScaleCenterX;
            float scaleCenterY = this.mScaleCenterY;
            pGLState.translateModelViewGLMatrixf(scaleCenterX, scaleCenterY, 0.0f);
            pGLState.scaleModelViewGLMatrixf(scaleX, scaleY, 1);
            pGLState.translateModelViewGLMatrixf(-scaleCenterX, -scaleCenterY, 0.0f);
        }
    }

    protected void onManagedDraw(GLState pGLState, Camera pCamera) {
        pGLState.pushModelViewGLMatrix();
        onApplyTransformations(pGLState);
        SmartList<IEntity> children = this.mChildren;
        if (children == null || !this.mChildrenVisible) {
            preDraw(pGLState, pCamera);
            draw(pGLState, pCamera);
            postDraw(pGLState, pCamera);
        } else {
            if (this.mChildrenSortPending) {
                ZIndexSorter.getInstance().sort(this.mChildren);
                this.mChildrenSortPending = false;
            }
            int childCount = children.size();
            int i = 0;
            while (i < childCount) {
                IEntity child = (IEntity) children.get(i);
                if (child.getZIndex() >= 0) {
                    break;
                }
                child.onDraw(pGLState, pCamera);
                i++;
            }
            preDraw(pGLState, pCamera);
            draw(pGLState, pCamera);
            postDraw(pGLState, pCamera);
            while (i < childCount) {
                ((IEntity) children.get(i)).onDraw(pGLState, pCamera);
                i++;
            }
        }
        pGLState.popModelViewGLMatrix();
    }

    protected void onManagedUpdate(float pSecondsElapsed) {
        if (this.mEntityModifiers != null) {
            this.mEntityModifiers.onUpdate(pSecondsElapsed);
        }
        if (this.mUpdateHandlers != null) {
            this.mUpdateHandlers.onUpdate(pSecondsElapsed);
        }
        if (this.mChildren != null && !this.mChildrenIgnoreUpdate) {
            SmartList<IEntity> entities = this.mChildren;
            int entityCount = entities.size();
            for (int i = 0; i < entityCount; i++) {
                ((IEntity) entities.get(i)).onUpdate(pSecondsElapsed);
            }
        }
    }

    private void assertEntityHasNoParent(IEntity pEntity) throws IllegalStateException {
        if (pEntity.hasParent()) {
            String entityClassName = pEntity.getClass().getSimpleName();
            String currentParentClassName = pEntity.getParent().getClass().getSimpleName();
            throw new IllegalStateException("pEntity '" + entityClassName + "' already has a parent: '" + currentParentClassName + "'. New parent: '" + getClass().getSimpleName() + "'!");
        }
    }
}
