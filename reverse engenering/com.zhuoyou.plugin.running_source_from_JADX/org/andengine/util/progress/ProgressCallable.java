package org.andengine.util.progress;

public interface ProgressCallable<T> {
    T call(IProgressListener iProgressListener) throws Exception;
}
