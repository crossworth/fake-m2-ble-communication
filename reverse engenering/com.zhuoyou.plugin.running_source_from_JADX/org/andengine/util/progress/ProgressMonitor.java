package org.andengine.util.progress;

import java.util.ArrayList;
import java.util.HashMap;
import org.andengine.util.math.MathUtils;

public class ProgressMonitor implements IProgressListener {
    private final HashMap<ProgressMonitor, IProgressListener> mChildProgressMonitorToProgressListenerMap = new HashMap();
    private final ArrayList<IProgressListener> mProgressListeners = new ArrayList();

    public ProgressMonitor(IProgressListener pProgressListener) {
        this.mProgressListeners.add(pProgressListener);
    }

    public void onProgressChanged(int pProgress) {
        int progressListenerCount = this.mProgressListeners.size();
        for (int i = 0; i < progressListenerCount; i++) {
            ((IProgressListener) this.mProgressListeners.get(i)).onProgressChanged(pProgress);
        }
    }

    public void registerChildProgressMonitor(ProgressMonitor pChildProgressMonitor, final int pChildProgressMonitorRangeFrom, final int pChildProgressMonitorRangeTo) {
        IProgressListener childProgressMonitorListener = new IProgressListener() {
            public void onProgressChanged(int pProgress) {
                ProgressMonitor.this.onProgressChanged(MathUtils.mix(pChildProgressMonitorRangeFrom, pChildProgressMonitorRangeTo, ((float) pProgress) / 100.0f));
            }
        };
        pChildProgressMonitor.addProgressListener(childProgressMonitorListener);
        this.mChildProgressMonitorToProgressListenerMap.put(pChildProgressMonitor, childProgressMonitorListener);
    }

    public void unregisterChildProgressMonitor(ProgressMonitor pChildProgressMonitor) {
        pChildProgressMonitor.removeProgressListener((IProgressListener) this.mChildProgressMonitorToProgressListenerMap.get(pChildProgressMonitor));
    }

    private void addProgressListener(IProgressListener pProgressListener) {
        this.mProgressListeners.add(pProgressListener);
    }

    private void removeProgressListener(IProgressListener pProgressListener) {
        this.mProgressListeners.add(pProgressListener);
    }
}
