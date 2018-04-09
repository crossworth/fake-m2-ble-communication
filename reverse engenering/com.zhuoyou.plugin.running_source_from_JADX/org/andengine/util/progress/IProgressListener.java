package org.andengine.util.progress;

public interface IProgressListener {
    public static final int PROGRESS_MAX = 100;
    public static final int PROGRESS_MIN = 0;

    void onProgressChanged(int i);
}
