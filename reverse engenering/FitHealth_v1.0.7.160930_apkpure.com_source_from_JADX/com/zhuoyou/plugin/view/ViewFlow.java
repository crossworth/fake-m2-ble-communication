package com.zhuoyou.plugin.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Scroller;
import com.zhuoyou.plugin.running.C1400R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class ViewFlow extends AdapterView<Adapter> {
    private static final int INVALID_SCREEN = -1;
    private static final int SNAP_VELOCITY = 100;
    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_SCROLLING = 1;
    private Handler handler;
    private Adapter mAdapter;
    private int mCurrentAdapterIndex;
    private int mCurrentBufferIndex;
    private int mCurrentScreen;
    private AdapterDataSetObserver mDataSetObserver;
    private boolean mFirstLayout;
    private FlowIndicator mIndicator;
    private float mLastMotionX;
    private int mLastOrientation;
    private int mLastScrollDirection;
    private LinkedList<View> mLoadedViews;
    private int mMaximumVelocity;
    private int mNextScreen;
    private Scroller mScroller;
    private int mSideBuffer;
    private int mTouchSlop;
    private int mTouchState;
    private VelocityTracker mVelocityTracker;
    private ViewSwitchListener mViewSwitchListener;
    private OnGlobalLayoutListener orientationChangeListener;
    private long timeSpan;

    class C14421 implements OnGlobalLayoutListener {
        C14421() {
        }

        public void onGlobalLayout() {
            ViewFlow.this.getViewTreeObserver().removeGlobalOnLayoutListener(ViewFlow.this.orientationChangeListener);
            ViewFlow.this.setSelection(ViewFlow.this.mCurrentAdapterIndex);
        }
    }

    class C14432 extends Handler {
        C14432() {
        }

        public void handleMessage(Message msg) {
            ViewFlow.this.snapToScreen((ViewFlow.this.mCurrentScreen + 1) % ViewFlow.this.getChildCount());
            sendMessageDelayed(ViewFlow.this.handler.obtainMessage(0), ViewFlow.this.timeSpan);
        }
    }

    class AdapterDataSetObserver extends DataSetObserver {
        AdapterDataSetObserver() {
        }

        public void onChanged() {
            View v = ViewFlow.this.getChildAt(ViewFlow.this.mCurrentBufferIndex);
            if (v != null) {
                for (int index = 0; index < ViewFlow.this.mAdapter.getCount(); index++) {
                    if (v.equals(ViewFlow.this.mAdapter.getItem(index))) {
                        ViewFlow.this.mCurrentAdapterIndex = index;
                        break;
                    }
                }
            }
            ViewFlow.this.resetFocus();
        }

        public void onInvalidated() {
        }
    }

    public interface ViewSwitchListener {
        void onSwitched(View view, int i);
    }

    public ViewFlow(Context context) {
        super(context);
        this.mSideBuffer = 2;
        this.mTouchState = 0;
        this.mNextScreen = -1;
        this.mFirstLayout = true;
        this.mLastOrientation = -1;
        this.timeSpan = 3000;
        this.orientationChangeListener = new C14421();
        this.mSideBuffer = 3;
        init();
    }

    public ViewFlow(Context context, int sideBuffer) {
        super(context);
        this.mSideBuffer = 2;
        this.mTouchState = 0;
        this.mNextScreen = -1;
        this.mFirstLayout = true;
        this.mLastOrientation = -1;
        this.timeSpan = 3000;
        this.orientationChangeListener = new C14421();
        this.mSideBuffer = sideBuffer;
        init();
    }

    @SuppressLint({"Recycle"})
    public ViewFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSideBuffer = 2;
        this.mTouchState = 0;
        this.mNextScreen = -1;
        this.mFirstLayout = true;
        this.mLastOrientation = -1;
        this.timeSpan = 3000;
        this.orientationChangeListener = new C14421();
        this.mSideBuffer = context.obtainStyledAttributes(attrs, C1400R.styleable.ViewFlow).getInt(0, 3);
        init();
    }

    private void init() {
        this.mLoadedViews = new LinkedList();
        this.mScroller = new Scroller(getContext());
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    public void startAutoFlowTimer() {
        this.handler = new C14432();
        this.handler.sendMessageDelayed(this.handler.obtainMessage(0), this.timeSpan);
    }

    public void stopAutoFlowTimer() {
        if (this.handler != null) {
            this.handler.removeMessages(0);
        }
        this.handler = null;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation != this.mLastOrientation) {
            this.mLastOrientation = newConfig.orientation;
            getViewTreeObserver().addOnGlobalLayoutListener(this.orientationChangeListener);
        }
    }

    public int getViewsCount() {
        return this.mSideBuffer;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (MeasureSpec.getMode(widthMeasureSpec) != 1073741824 && !isInEditMode()) {
            throw new IllegalStateException("ViewFlow can only be used in EXACTLY mode.");
        } else if (MeasureSpec.getMode(heightMeasureSpec) == 1073741824 || isInEditMode()) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
            }
            if (this.mFirstLayout) {
                this.mScroller.startScroll(0, 0, this.mCurrentScreen * width, 0, 0);
                this.mFirstLayout = false;
            }
        } else {
            throw new IllegalStateException("ViewFlow can only be used in EXACTLY mode.");
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                int childWidth = child.getMeasuredWidth();
                child.layout(childLeft, 0, childLeft + childWidth, child.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int i = 1;
        if (getChildCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        int action = ev.getAction();
        float x = ev.getX();
        int deltaX;
        switch (action) {
            case 0:
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                this.mLastMotionX = x;
                if (this.mScroller.isFinished()) {
                    i = 0;
                }
                this.mTouchState = i;
                if (this.handler == null) {
                    return false;
                }
                this.handler.removeMessages(0);
                return false;
            case 1:
                if (this.mTouchState == 1) {
                    deltaX = (int) (this.mLastMotionX - x);
                    if (deltaX > 30) {
                        snapToScreen(this.mCurrentScreen + 1);
                    } else if (deltaX < 30) {
                        snapToScreen(this.mCurrentScreen - 1);
                    }
                }
                this.mTouchState = 0;
                if (this.handler == null) {
                    return false;
                }
                this.handler.sendMessageDelayed(this.handler.obtainMessage(0), this.timeSpan);
                return false;
            case 2:
                boolean xMoved;
                if (((int) Math.abs(x - this.mLastMotionX)) > this.mTouchSlop) {
                    xMoved = true;
                } else {
                    xMoved = false;
                }
                if (xMoved) {
                    this.mTouchState = 1;
                }
                if (this.mTouchState != 1) {
                    return false;
                }
                deltaX = (int) (this.mLastMotionX - x);
                this.mLastMotionX = x;
                int scrollX = getScrollX();
                if (deltaX < 0) {
                    if (scrollX > 0) {
                        scrollBy(Math.max(-scrollX, deltaX), 0);
                    }
                } else if (deltaX > 0) {
                    int availableToScroll = (getChildAt(getChildCount() - 1).getRight() - scrollX) - getWidth();
                    if (availableToScroll > 0) {
                        scrollBy(Math.min(availableToScroll, deltaX), 0);
                    }
                }
                return true;
            case 3:
                this.mTouchState = 0;
                return false;
            default:
                return false;
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (getChildCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        int action = ev.getAction();
        float x = ev.getX();
        switch (action) {
            case 0:
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                this.mLastMotionX = x;
                this.mTouchState = this.mScroller.isFinished() ? 0 : 1;
                if (this.handler != null) {
                    this.handler.removeMessages(0);
                    break;
                }
                break;
            case 1:
                if (this.mTouchState == 1) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    int velocityX = (int) velocityTracker.getXVelocity();
                    if (velocityX > 100 && this.mCurrentScreen > 0) {
                        snapToScreen(this.mCurrentScreen - 1);
                    } else if (velocityX >= -100 || this.mCurrentScreen >= getChildCount() - 1) {
                        snapToDestination();
                    } else {
                        snapToScreen(this.mCurrentScreen + 1);
                    }
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                }
                this.mTouchState = 0;
                if (this.handler != null) {
                    this.handler.sendMessageDelayed(this.handler.obtainMessage(0), this.timeSpan);
                    break;
                }
                break;
            case 2:
                if (((int) Math.abs(x - this.mLastMotionX)) > this.mTouchSlop) {
                    this.mTouchState = 1;
                }
                if (this.mTouchState == 1) {
                    int deltaX = (int) (this.mLastMotionX - x);
                    this.mLastMotionX = x;
                    int scrollX = getScrollX();
                    if (deltaX < 0) {
                        if (scrollX > 0) {
                            scrollBy(Math.max(-scrollX, deltaX), 0);
                        }
                    } else if (deltaX > 0) {
                        int availableToScroll = (getChildAt(getChildCount() - 1).getRight() - scrollX) - getWidth();
                        if (availableToScroll > 0) {
                            scrollBy(Math.min(availableToScroll, deltaX), 0);
                        }
                    }
                    return true;
                }
                break;
            case 3:
                snapToDestination();
                this.mTouchState = 0;
                break;
        }
        return true;
    }

    protected void onScrollChanged(int h, int v, int oldh, int oldv) {
        super.onScrollChanged(h, v, oldh, oldv);
        if (this.mIndicator != null) {
            this.mIndicator.onScrolled(h + ((this.mCurrentAdapterIndex - this.mCurrentBufferIndex) * getWidth()), v, oldh, oldv);
        }
    }

    private void snapToDestination() {
        int screenWidth = getWidth();
        snapToScreen((getScrollX() + (screenWidth / 2)) / screenWidth);
    }

    private void snapToScreen(int whichScreen) {
        this.mLastScrollDirection = whichScreen - this.mCurrentScreen;
        if (this.mScroller.isFinished()) {
            whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
            this.mNextScreen = whichScreen;
            int delta = (whichScreen * getWidth()) - getScrollX();
            this.mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
            invalidate();
        }
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            postInvalidate();
        } else if (this.mNextScreen != -1) {
            this.mCurrentScreen = Math.max(0, Math.min(this.mNextScreen, getChildCount() - 1));
            this.mNextScreen = -1;
            postViewSwitched(this.mLastScrollDirection);
        }
    }

    private void setVisibleView(int indexInBuffer, boolean uiThread) {
        this.mCurrentScreen = Math.max(0, Math.min(indexInBuffer, getChildCount() - 1));
        int dx = (this.mCurrentScreen * getWidth()) - this.mScroller.getCurrX();
        this.mScroller.startScroll(this.mScroller.getCurrX(), this.mScroller.getCurrY(), dx, 0, 0);
        if (dx == 0) {
            onScrollChanged(this.mScroller.getCurrX() + dx, this.mScroller.getCurrY(), this.mScroller.getCurrX() + dx, this.mScroller.getCurrY());
        }
        if (uiThread) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public void setOnViewSwitchListener(ViewSwitchListener l) {
        this.mViewSwitchListener = l;
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(Adapter adapter) {
        setAdapter(adapter, 0);
    }

    public void setAdapter(Adapter adapter, int initialPosition) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        this.mAdapter = adapter;
        if (this.mAdapter != null) {
            this.mDataSetObserver = new AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
        }
        if (this.mAdapter != null && this.mAdapter.getCount() != 0) {
            setSelection(initialPosition);
        }
    }

    public View getSelectedView() {
        return this.mCurrentBufferIndex < this.mLoadedViews.size() ? (View) this.mLoadedViews.get(this.mCurrentBufferIndex) : null;
    }

    public int getSelectedItemPosition() {
        return this.mCurrentAdapterIndex;
    }

    public void setFlowIndicator(FlowIndicator flowIndicator) {
        this.mIndicator = flowIndicator;
        this.mIndicator.setViewFlow(this);
    }

    public void setSelection(int position) {
        this.mNextScreen = -1;
        this.mScroller.forceFinished(true);
        if (this.mAdapter != null) {
            position = Math.min(Math.max(position, 0), this.mAdapter.getCount() - 1);
            ArrayList<View> recycleViews = new ArrayList();
            while (!this.mLoadedViews.isEmpty()) {
                View recycleView = (View) this.mLoadedViews.remove();
                recycleViews.add(recycleView);
                detachViewFromParent(recycleView);
            }
            View currentView = makeAndAddView(position, true, recycleViews.isEmpty() ? null : (View) recycleViews.remove(0));
            this.mLoadedViews.addLast(currentView);
            for (int offset = 1; this.mSideBuffer - offset >= 0; offset++) {
                int leftIndex = position - offset;
                int rightIndex = position + offset;
                if (leftIndex >= 0) {
                    this.mLoadedViews.addFirst(makeAndAddView(leftIndex, false, recycleViews.isEmpty() ? null : (View) recycleViews.remove(0)));
                }
                if (rightIndex < this.mAdapter.getCount()) {
                    View view;
                    LinkedList linkedList = this.mLoadedViews;
                    if (recycleViews.isEmpty()) {
                        view = null;
                    } else {
                        view = (View) recycleViews.remove(0);
                    }
                    linkedList.addLast(makeAndAddView(rightIndex, true, view));
                }
            }
            this.mCurrentBufferIndex = this.mLoadedViews.indexOf(currentView);
            this.mCurrentAdapterIndex = position;
            Iterator it = recycleViews.iterator();
            while (it.hasNext()) {
                removeDetachedView((View) it.next(), false);
            }
            requestLayout();
            setVisibleView(this.mCurrentBufferIndex, false);
            if (this.mIndicator != null) {
                this.mIndicator.onSwitched((View) this.mLoadedViews.get(this.mCurrentBufferIndex), this.mCurrentAdapterIndex);
            }
            if (this.mViewSwitchListener != null) {
                this.mViewSwitchListener.onSwitched((View) this.mLoadedViews.get(this.mCurrentBufferIndex), this.mCurrentAdapterIndex);
            }
        }
    }

    private void resetFocus() {
        this.mLoadedViews.clear();
        removeAllViewsInLayout();
        for (int i = Math.max(0, this.mCurrentAdapterIndex - this.mSideBuffer); i < Math.min(this.mAdapter.getCount(), (this.mCurrentAdapterIndex + this.mSideBuffer) + 1); i++) {
            this.mLoadedViews.addLast(makeAndAddView(i, true, null));
            if (i == this.mCurrentAdapterIndex) {
                this.mCurrentBufferIndex = this.mLoadedViews.size() - 1;
            }
        }
        requestLayout();
    }

    private void postViewSwitched(int direction) {
        if (direction != 0) {
            View recycleView;
            int newBufferIndex;
            if (direction > 0) {
                this.mCurrentAdapterIndex++;
                this.mCurrentBufferIndex++;
                recycleView = null;
                if (this.mCurrentAdapterIndex > this.mSideBuffer) {
                    recycleView = (View) this.mLoadedViews.removeFirst();
                    detachViewFromParent(recycleView);
                    this.mCurrentBufferIndex--;
                }
                newBufferIndex = this.mCurrentAdapterIndex + this.mSideBuffer;
                if (newBufferIndex < this.mAdapter.getCount()) {
                    this.mLoadedViews.addLast(makeAndAddView(newBufferIndex, true, recycleView));
                }
            } else {
                this.mCurrentAdapterIndex--;
                this.mCurrentBufferIndex--;
                recycleView = null;
                if ((this.mAdapter.getCount() - 1) - this.mCurrentAdapterIndex > this.mSideBuffer) {
                    recycleView = (View) this.mLoadedViews.removeLast();
                    detachViewFromParent(recycleView);
                }
                newBufferIndex = this.mCurrentAdapterIndex - this.mSideBuffer;
                if (newBufferIndex > -1) {
                    this.mLoadedViews.addFirst(makeAndAddView(newBufferIndex, false, recycleView));
                    this.mCurrentBufferIndex++;
                }
            }
            requestLayout();
            setVisibleView(this.mCurrentBufferIndex, true);
            if (this.mIndicator != null) {
                this.mIndicator.onSwitched((View) this.mLoadedViews.get(this.mCurrentBufferIndex), this.mCurrentAdapterIndex);
            }
            if (this.mViewSwitchListener != null) {
                this.mViewSwitchListener.onSwitched((View) this.mLoadedViews.get(this.mCurrentBufferIndex), this.mCurrentAdapterIndex);
            }
        }
    }

    private View setupChild(View child, boolean addToEnd, boolean recycle) {
        int i = -1;
        LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new AbsListView.LayoutParams(-1, -2, 0);
        }
        if (recycle) {
            if (!addToEnd) {
                i = 0;
            }
            attachViewToParent(child, i, p);
        } else {
            if (!addToEnd) {
                i = 0;
            }
            addViewInLayout(child, i, p, true);
        }
        return child;
    }

    private View makeAndAddView(int position, boolean addToEnd, View convertView) {
        return setupChild(this.mAdapter.getView(position, convertView, this), addToEnd, convertView != null);
    }

    public void setTimeSpan(long timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setmSideBuffer(int mSideBuffer) {
        this.mSideBuffer = mSideBuffer;
    }
}
