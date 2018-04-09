package org.andengine.ui;

import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;

public interface IGameInterface {

    public interface OnCreateResourcesCallback {
        void onCreateResourcesFinished();
    }

    public interface OnCreateSceneCallback {
        void onCreateSceneFinished(Scene scene);
    }

    public interface OnPopulateSceneCallback {
        void onPopulateSceneFinished();
    }

    Engine onCreateEngine(EngineOptions engineOptions);

    EngineOptions onCreateEngineOptions();

    void onCreateResources(OnCreateResourcesCallback onCreateResourcesCallback) throws Exception;

    void onCreateScene(OnCreateSceneCallback onCreateSceneCallback) throws Exception;

    void onDestroyResources() throws Exception;

    void onGameCreated();

    void onGameDestroyed();

    void onPauseGame();

    void onPopulateScene(Scene scene, OnPopulateSceneCallback onPopulateSceneCallback) throws Exception;

    void onReloadResources() throws Exception;

    void onResumeGame();
}
