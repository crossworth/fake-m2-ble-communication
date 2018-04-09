package org.andengine.util.modifier;

import org.andengine.util.adt.list.SmartList;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.modifier.IModifier.IModifierListener;

public abstract class BaseModifier<T> implements IModifier<T> {
    private boolean mAutoUnregisterWhenFinished = true;
    protected boolean mFinished;
    private final SmartList<IModifierListener<T>> mModifierListeners = new SmartList(2);

    public abstract IModifier<T> deepCopy() throws DeepCopyNotSupportedException;

    public BaseModifier(IModifierListener<T> pModifierListener) {
        addModifierListener(pModifierListener);
    }

    public boolean isFinished() {
        return this.mFinished;
    }

    public final boolean isAutoUnregisterWhenFinished() {
        return this.mAutoUnregisterWhenFinished;
    }

    public final void setAutoUnregisterWhenFinished(boolean pAutoUnregisterWhenFinished) {
        this.mAutoUnregisterWhenFinished = pAutoUnregisterWhenFinished;
    }

    public void addModifierListener(IModifierListener<T> pModifierListener) {
        if (pModifierListener != null) {
            this.mModifierListeners.add(pModifierListener);
        }
    }

    public boolean removeModifierListener(IModifierListener<T> pModifierListener) {
        if (pModifierListener == null) {
            return false;
        }
        return this.mModifierListeners.remove(pModifierListener);
    }

    protected void onModifierStarted(T pItem) {
        SmartList<IModifierListener<T>> modifierListeners = this.mModifierListeners;
        for (int i = modifierListeners.size() - 1; i >= 0; i--) {
            ((IModifierListener) modifierListeners.get(i)).onModifierStarted(this, pItem);
        }
    }

    protected void onModifierFinished(T pItem) {
        SmartList<IModifierListener<T>> modifierListeners = this.mModifierListeners;
        for (int i = modifierListeners.size() - 1; i >= 0; i--) {
            ((IModifierListener) modifierListeners.get(i)).onModifierFinished(this, pItem);
        }
    }

    protected static final <T> void assertNoNullModifier(IModifier<T> pModifier) {
        if (pModifier == null) {
            throw new IllegalArgumentException("Illegal 'null' " + IModifier.class.getSimpleName() + " detected!");
        }
    }

    protected static final <T> void assertNoNullModifier(IModifier<T>... pModifiers) {
        int modifierCount = pModifiers.length;
        for (int i = 0; i < modifierCount; i++) {
            if (pModifiers[i] == null) {
                throw new IllegalArgumentException("Illegal 'null' " + IModifier.class.getSimpleName() + " detected at position: '" + i + "'!");
            }
        }
    }
}
