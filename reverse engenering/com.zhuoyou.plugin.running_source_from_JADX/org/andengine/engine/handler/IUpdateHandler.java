package org.andengine.engine.handler;

import org.andengine.util.IMatcher;

public interface IUpdateHandler {

    public interface IUpdateHandlerMatcher extends IMatcher<IUpdateHandler> {
    }

    void onUpdate(float f);

    void reset();
}
