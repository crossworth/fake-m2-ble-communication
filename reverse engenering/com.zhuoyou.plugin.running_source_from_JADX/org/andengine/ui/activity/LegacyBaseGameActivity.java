package org.andengine.ui.activity;

import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.IGameInterface.OnCreateResourcesCallback;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;

@Deprecated
public abstract class LegacyBaseGameActivity extends BaseGameActivity {
    protected abstract Scene onLoadComplete();

    protected abstract Engine onLoadEngine();

    protected abstract void onLoadResources();

    protected abstract Scene onLoadScene();

    protected abstract void onUnloadResources();

    public final EngineOptions onCreateEngineOptions() {
        return null;
    }

    public final Engine onCreateEngine(EngineOptions pEngineOptions) {
        return onLoadEngine();
    }

    public final void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
        onLoadResources();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    public final void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        pOnCreateSceneCallback.onCreateSceneFinished(onLoadScene());
    }

    public final void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    public final void onDestroyResources() throws Exception {
        super.onDestroyResources();
        onUnloadResources();
    }

    public synchronized void onGameCreated() {
        super.onGameCreated();
        onLoadComplete();
    }
}
