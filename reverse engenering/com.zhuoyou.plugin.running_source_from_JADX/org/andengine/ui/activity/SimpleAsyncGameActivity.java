package org.andengine.ui.activity;

import org.andengine.entity.scene.Scene;
import org.andengine.ui.IGameInterface.OnCreateResourcesCallback;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;
import org.andengine.util.ActivityUtils;
import org.andengine.util.call.Callback;
import org.andengine.util.progress.IProgressListener;
import org.andengine.util.progress.ProgressCallable;

public abstract class SimpleAsyncGameActivity extends BaseGameActivity {
    public abstract void onCreateResourcesAsync(IProgressListener iProgressListener) throws Exception;

    public abstract Scene onCreateSceneAsync(IProgressListener iProgressListener) throws Exception;

    public abstract void onPopulateSceneAsync(Scene scene, IProgressListener iProgressListener) throws Exception;

    public void onCreateResources(final OnCreateResourcesCallback pOnCreateResourcesCallback) {
        runOnUiThread(new Runnable() {

            class C20881 implements ProgressCallable<Void> {
                C20881() {
                }

                public Void call(IProgressListener pProgressListener) throws Exception {
                    SimpleAsyncGameActivity.this.onCreateResourcesAsync(pProgressListener);
                    pProgressListener.onProgressChanged(100);
                    pOnCreateResourcesCallback.onCreateResourcesFinished();
                    return null;
                }
            }

            class C20892 implements Callback<Void> {
                C20892() {
                }

                public void onCallback(Void pCallbackValue) {
                }
            }

            public void run() {
                ActivityUtils.doProgressAsync(SimpleAsyncGameActivity.this, (CharSequence) "Loading Resources...", 17301581, new C20881(), new C20892());
            }
        });
    }

    public void onCreateScene(final OnCreateSceneCallback pOnCreateSceneCallback) {
        runOnUiThread(new Runnable() {

            class C20911 implements ProgressCallable<Void> {
                C20911() {
                }

                public Void call(IProgressListener pProgressListener) throws Exception {
                    Scene scene = SimpleAsyncGameActivity.this.onCreateSceneAsync(pProgressListener);
                    pProgressListener.onProgressChanged(100);
                    pOnCreateSceneCallback.onCreateSceneFinished(scene);
                    return null;
                }
            }

            class C20922 implements Callback<Void> {
                C20922() {
                }

                public void onCallback(Void pCallbackValue) {
                }
            }

            public void run() {
                ActivityUtils.doProgressAsync(SimpleAsyncGameActivity.this, (CharSequence) "Loading Scene...", 17301581, new C20911(), new C20922());
            }
        });
    }

    public void onPopulateScene(final Scene pScene, final OnPopulateSceneCallback pOnPopulateSceneCallback) {
        runOnUiThread(new Runnable() {

            class C20941 implements ProgressCallable<Void> {
                C20941() {
                }

                public Void call(IProgressListener pProgressListener) throws Exception {
                    SimpleAsyncGameActivity.this.onPopulateSceneAsync(pScene, pProgressListener);
                    pProgressListener.onProgressChanged(100);
                    pOnPopulateSceneCallback.onPopulateSceneFinished();
                    return null;
                }
            }

            class C20952 implements Callback<Void> {
                C20952() {
                }

                public void onCallback(Void pCallbackValue) {
                }
            }

            public void run() {
                ActivityUtils.doProgressAsync(SimpleAsyncGameActivity.this, (CharSequence) "Populating Scene...", 17301581, new C20941(), new C20952());
            }
        });
    }
}
