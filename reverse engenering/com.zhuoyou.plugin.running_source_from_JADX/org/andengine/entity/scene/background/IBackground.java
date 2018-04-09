package org.andengine.entity.scene.background;

import org.andengine.engine.handler.IDrawHandler;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

public interface IBackground extends IDrawHandler, IUpdateHandler {
    void clearBackgroundModifiers();

    boolean isColorEnabled();

    void registerBackgroundModifier(IModifier<IBackground> iModifier);

    void setColor(float f, float f2, float f3);

    void setColor(float f, float f2, float f3, float f4);

    void setColor(Color color);

    void setColorEnabled(boolean z);

    boolean unregisterBackgroundModifier(IModifier<IBackground> iModifier);
}
