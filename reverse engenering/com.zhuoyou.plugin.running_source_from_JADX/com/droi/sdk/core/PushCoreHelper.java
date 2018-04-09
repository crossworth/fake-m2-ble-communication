package com.droi.sdk.core;

import android.content.Context;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import java.util.concurrent.atomic.AtomicReference;

public class PushCoreHelper {

    final class C08661 extends DroiRunnable {
        final /* synthetic */ AtomicReference val$atomId;
        final /* synthetic */ DroiCallback val$callback;

        C08661(AtomicReference atomicReference, DroiCallback droiCallback) {
            this.val$atomId = atomicReference;
            this.val$callback = droiCallback;
        }

        public void run() {
            DroiError droiError = new DroiError();
            String str = (String) this.val$atomId.get();
            if (str == null) {
                droiError.setCode(DroiError.ERROR);
            }
            if (this.val$callback != null) {
                this.val$callback.result(str, droiError);
            }
        }
    }

    final class C08672 extends DroiRunnable {
        final /* synthetic */ Context val$appContext;
        final /* synthetic */ AtomicReference val$atomId;

        C08672(Context context, AtomicReference atomicReference) {
            this.val$appContext = context;
            this.val$atomId = atomicReference;
        }

        public void run() {
            Core.initialize(this.val$appContext);
            this.val$atomId.set(Core.getDroiDeviceId());
        }
    }

    public static String m2596a() {
        return Core.getDroiAppId();
    }

    public static boolean m2597a(Context context, DroiCallback droiCallback) {
        AtomicReference atomicReference = new AtomicReference();
        return DroiTask.create(new C08672(context, atomicReference)).callback(new C08661(atomicReference, droiCallback), TaskDispatcher.currentTaskDispatcher().name()).runInBackground(TaskDispatcher.getDispatcher("com.droi.sdk.push").name()).booleanValue();
    }
}
