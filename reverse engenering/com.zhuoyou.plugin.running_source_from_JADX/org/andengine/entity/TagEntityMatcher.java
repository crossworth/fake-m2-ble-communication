package org.andengine.entity;

public class TagEntityMatcher implements IEntityMatcher {
    private int mTag;

    public TagEntityMatcher(int pTag) {
        this.mTag = pTag;
    }

    public int getTag() {
        return this.mTag;
    }

    public void setTag(int pTag) {
        this.mTag = pTag;
    }

    public boolean matches(IEntity pEntity) {
        return this.mTag == pEntity.getTag();
    }
}
