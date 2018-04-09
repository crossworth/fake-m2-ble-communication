package org.andengine.util.modifier;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.util.adt.list.SmartList;

public class ModifierList<T> extends SmartList<IModifier<T>> implements IUpdateHandler {
    private static final long serialVersionUID = 1610345592534873475L;
    private final T mTarget;

    public ModifierList(T pTarget) {
        this.mTarget = pTarget;
    }

    public ModifierList(T pTarget, int pCapacity) {
        super(pCapacity);
        this.mTarget = pTarget;
    }

    public T getTarget() {
        return this.mTarget;
    }

    public boolean add(IModifier<T> pModifier) {
        if (pModifier != null) {
            return super.add(pModifier);
        }
        throw new IllegalArgumentException("Supplied " + IModifier.class.getSimpleName() + " must not be null.");
    }

    public void onUpdate(float pSecondsElapsed) {
        int modifierCount = size();
        if (modifierCount > 0) {
            for (int i = modifierCount - 1; i >= 0; i--) {
                IModifier<T> modifier = (IModifier) get(i);
                modifier.onUpdate(pSecondsElapsed, this.mTarget);
                if (modifier.isFinished() && modifier.isAutoUnregisterWhenFinished()) {
                    remove(i);
                }
            }
        }
    }

    public void reset() {
        for (int i = size() - 1; i >= 0; i--) {
            ((IModifier) get(i)).reset();
        }
    }
}
