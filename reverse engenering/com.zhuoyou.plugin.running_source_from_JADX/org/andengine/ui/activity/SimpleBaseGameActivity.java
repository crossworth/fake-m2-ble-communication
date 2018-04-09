package org.andengine.ui.activity;

import org.andengine.entity.scene.Scene;
import org.andengine.ui.IGameInterface.OnCreateResourcesCallback;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;

public abstract class SimpleBaseGameActivity extends BaseGameActivity {
    protected abstract void onCreateResources();

    protected abstract Scene onCreateScene();

    public final void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
        onCreateResources();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    public final void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        pOnCreateSceneCallback.onCreateSceneFinished(onCreateScene());
    }

    public final void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
}
