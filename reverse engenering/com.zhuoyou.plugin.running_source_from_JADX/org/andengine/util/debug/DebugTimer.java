package org.andengine.util.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import org.andengine.util.debug.Debug.DebugLevel;

public class DebugTimer {
    private static final int INDENT_SPACES = SPLIT_STRING.length();
    private static final String SPLIT_STRING = "  Split: ";
    private final DebugLevel mDebugLevel;
    private final Stack<DebugTime> mDebugTimes;

    public class DebugTime {
        private ArrayList<DebugTime> mChildren;
        private long mEndTime;
        private final String mLabel;
        private DebugTime mLastSplit;
        private final boolean mSplit;
        private final long mStartTime;

        public DebugTime(DebugTimer this$0, long pStartTime, String pLabel) {
            this(pStartTime, pLabel, false);
        }

        protected DebugTime(long pStartTime, String pLabel, boolean pSplit) {
            this.mStartTime = pStartTime;
            this.mLabel = pLabel;
            this.mSplit = pSplit;
        }

        public void begin(DebugTime pDebugTime) {
            ensureChildrenAllocated();
            this.mChildren.add(pDebugTime);
        }

        public void split(String pLabel) {
            DebugTime split;
            long now = System.currentTimeMillis();
            if (this.mLastSplit == null) {
                split = new DebugTime(this.mStartTime, pLabel, true);
            } else {
                split = new DebugTime(this.mLastSplit.mEndTime, pLabel, true);
            }
            split.end(now);
            ensureChildrenAllocated();
            this.mChildren.add(split);
            this.mLastSplit = split;
        }

        public void end(long pEndTime) {
            this.mEndTime = pEndTime;
        }

        public void dump(int pIndent) {
            dump(pIndent, "");
        }

        public void dump(int pIndent, String pPostfix) {
            if (this.mSplit) {
                char[] indent = new char[((pIndent - 1) * DebugTimer.INDENT_SPACES)];
                Arrays.fill(indent, ' ');
                Debug.log(DebugTimer.this.mDebugLevel, new String(indent) + DebugTimer.SPLIT_STRING + "'" + this.mLabel + "'" + " @( " + (this.mEndTime - this.mStartTime) + "ms )" + pPostfix);
                return;
            }
            indent = new char[(DebugTimer.INDENT_SPACES * pIndent)];
            Arrays.fill(indent, ' ');
            if (this.mChildren == null) {
                Debug.log(DebugTimer.this.mDebugLevel, new String(indent) + "'" + this.mLabel + "' @( " + (this.mEndTime - this.mStartTime) + "ms )" + pPostfix);
                return;
            }
            ArrayList<DebugTime> children = this.mChildren;
            int childCount = children.size();
            Debug.log(DebugTimer.this.mDebugLevel, new String(indent) + "'" + this.mLabel + "' {");
            for (int i = 0; i < childCount - 1; i++) {
                ((DebugTime) children.get(i)).dump(pIndent + 1, ",");
            }
            ((DebugTime) children.get(childCount - 1)).dump(pIndent + 1);
            Debug.log(DebugTimer.this.mDebugLevel, new String(indent) + "}@( " + (this.mEndTime - this.mStartTime) + "ms )" + pPostfix);
        }

        private void ensureChildrenAllocated() {
            if (this.mChildren == null) {
                this.mChildren = new ArrayList();
            }
        }
    }

    public DebugTimer(String pLabel) {
        this(DebugLevel.DEBUG, pLabel);
    }

    public DebugTimer(DebugLevel pDebugLevel, String pLabel) {
        this.mDebugTimes = new Stack();
        this.mDebugLevel = pDebugLevel;
        init(pLabel);
    }

    private void init(String pLabel) {
        this.mDebugTimes.add(new DebugTime(this, System.currentTimeMillis(), pLabel));
    }

    public void begin(String pLabel) {
        DebugTime debugTime = new DebugTime(this, System.currentTimeMillis(), pLabel);
        ((DebugTime) this.mDebugTimes.peek()).begin(debugTime);
        this.mDebugTimes.add(debugTime);
    }

    public void split(String pLabel) {
        ((DebugTime) this.mDebugTimes.peek()).split(pLabel);
    }

    public void end() {
        long now = System.currentTimeMillis();
        if (this.mDebugTimes.size() == 1) {
            throw new IllegalStateException("Cannot end the root of this " + getClass().getSimpleName());
        }
        ((DebugTime) this.mDebugTimes.pop()).end(now);
    }

    public void dump() {
        dump(false);
    }

    public void dump(boolean pClear) {
        long now = System.currentTimeMillis();
        if (this.mDebugTimes.size() > 1) {
            Debug.m4601w(getClass().getSimpleName() + " not all ended!");
        }
        DebugTime root = (DebugTime) this.mDebugTimes.firstElement();
        root.end(now);
        root.dump(0);
        if (pClear) {
            clear();
        }
    }

    public void clear() {
        DebugTime root = (DebugTime) this.mDebugTimes.firstElement();
        this.mDebugTimes.clear();
        init(root.mLabel);
    }
}
