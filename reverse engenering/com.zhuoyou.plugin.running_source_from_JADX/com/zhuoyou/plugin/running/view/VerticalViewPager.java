package com.zhuoyou.plugin.running.view;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.v4.media.TransportMediator;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.droi.btlib.service.BtManagerService;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import lecho.lib.hellocharts.model.PieChartData;
import org.andengine.util.adt.DataConstants;

public class VerticalViewPager extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<ItemInfo> COMPARATOR = new C19601();
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    private static final int[] LAYOUT_ATTRS = new int[]{16842931};
    private static final int MAX_SETTLE_DURATION = 1000;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private static final Interpolator sInterpolator = new C19612();
    private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();
    private int mActivePointerId = -1;
    private PagerAdapter mAdapter;
    private OnAdapterChangeListener mAdapterChangeListener;
    private EdgeEffectCompat mBottomEdge;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    private int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable = new C19623();
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout = true;
    private float mFirstOffset = -3.4028235E38f;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mIgnoreGutter;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems = new ArrayList();
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset = AutoScrollHelper.NO_MAX;
    private int mLeftPageBounds;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets = false;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit = 1;
    private OnPageChangeListener mOnPageChangeListener;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mRestoredCurItem = -1;
    private int mRightPageBounds;
    private int mScrollState = 0;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private Method mSetChildrenDrawingOrderEnabled;
    private final ItemInfo mTempItem = new ItemInfo();
    private final Rect mTempRect = new Rect();
    private EdgeEffectCompat mTopEdge;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    static class C19601 implements Comparator<ItemInfo> {
        C19601() {
        }

        public int compare(ItemInfo lhs, ItemInfo rhs) {
            return lhs.position - rhs.position;
        }
    }

    static class C19612 implements Interpolator {
        C19612() {
        }

        public float getInterpolation(float t) {
            t -= 1.0f;
            return ((((t * t) * t) * t) * t) + 1.0f;
        }
    }

    class C19623 implements Runnable {
        C19623() {
        }

        public void run() {
            VerticalViewPager.this.setScrollState(0);
            VerticalViewPager.this.populate();
        }
    }

    interface Decor {
    }

    static class ItemInfo {
        float heightFactor;
        Object object;
        float offset;
        int position;
        boolean scrolling;

        ItemInfo() {
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        float heightFactor = 0.0f;
        public boolean isDecor;
        boolean needsMeasure;
        int position;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, VerticalViewPager.LAYOUT_ATTRS);
            this.gravity = a.getInteger(0, 48);
            a.recycle();
        }
    }

    class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        MyAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(ViewPager.class.getName());
            AccessibilityRecordCompat recordCompat = AccessibilityRecordCompat.obtain();
            recordCompat.setScrollable(canScroll());
            if (event.getEventType() == 4096 && VerticalViewPager.this.mAdapter != null) {
                recordCompat.setItemCount(VerticalViewPager.this.mAdapter.getCount());
                recordCompat.setFromIndex(VerticalViewPager.this.mCurItem);
                recordCompat.setToIndex(VerticalViewPager.this.mCurItem);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setClassName(ViewPager.class.getName());
            info.setScrollable(canScroll());
            if (VerticalViewPager.this.internalCanScrollVertically(1)) {
                info.addAction(4096);
            }
            if (VerticalViewPager.this.internalCanScrollVertically(-1)) {
                info.addAction(8192);
            }
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            if (super.performAccessibilityAction(host, action, args)) {
                return true;
            }
            switch (action) {
                case 4096:
                    if (!VerticalViewPager.this.internalCanScrollVertically(1)) {
                        return false;
                    }
                    VerticalViewPager.this.setCurrentItem(VerticalViewPager.this.mCurItem + 1);
                    return true;
                case 8192:
                    if (!VerticalViewPager.this.internalCanScrollVertically(-1)) {
                        return false;
                    }
                    VerticalViewPager.this.setCurrentItem(VerticalViewPager.this.mCurItem - 1);
                    return true;
                default:
                    return false;
            }
        }

        private boolean canScroll() {
            return VerticalViewPager.this.mAdapter != null && VerticalViewPager.this.mAdapter.getCount() > 1;
        }
    }

    interface OnAdapterChangeListener {
        void onAdapterChanged(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2);
    }

    private class PagerObserver extends DataSetObserver {
        private PagerObserver() {
        }

        public void onChanged() {
            VerticalViewPager.this.dataSetChanged();
        }

        public void onInvalidated() {
            VerticalViewPager.this.dataSetChanged();
        }
    }

    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new C19631());
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        static class C19631 implements ParcelableCompatCreatorCallbacks<SavedState> {
            C19631() {
            }

            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.position);
            out.writeParcelable(this.adapterState, flags);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
        }

        SavedState(Parcel in, ClassLoader loader) {
            super(in);
            if (loader == null) {
                loader = getClass().getClassLoader();
            }
            this.position = in.readInt();
            this.adapterState = in.readParcelable(loader);
            this.loader = loader;
        }
    }

    static class ViewPositionComparator implements Comparator<View> {
        ViewPositionComparator() {
        }

        public int compare(View lhs, View rhs) {
            LayoutParams llp = (LayoutParams) lhs.getLayoutParams();
            LayoutParams rlp = (LayoutParams) rhs.getLayoutParams();
            if (llp.isDecor != rlp.isDecor) {
                return llp.isDecor ? 1 : -1;
            } else {
                return llp.position - rlp.position;
            }
        }
    }

    public VerticalViewPager(Context context) {
        super(context);
        initViewPager();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewPager();
    }

    void initViewPager() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        float density = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        this.mMinimumVelocity = (int) (400.0f * density);
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.mTopEdge = new EdgeEffectCompat(context);
        this.mBottomEdge = new EdgeEffectCompat(context);
        this.mFlingDistance = (int) (25.0f * density);
        this.mCloseEnough = (int) (2.0f * density);
        this.mDefaultGutterSize = (int) (16.0f * density);
        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
    }

    protected void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        super.onDetachedFromWindow();
    }

    private void setScrollState(int newState) {
        if (this.mScrollState != newState) {
            this.mScrollState = newState;
            if (this.mPageTransformer != null) {
                enableLayers(newState != 0);
            }
            if (this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageScrollStateChanged(newState);
            }
        }
    }

    public void setAdapter(PagerAdapter adapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
            this.mAdapter.startUpdate((ViewGroup) this);
            for (int i = 0; i < this.mItems.size(); i++) {
                ItemInfo ii = (ItemInfo) this.mItems.get(i);
                this.mAdapter.destroyItem((ViewGroup) this, ii.position, ii.object);
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            this.mItems.clear();
            removeNonDecorViews();
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        PagerAdapter oldAdapter = this.mAdapter;
        this.mAdapter = adapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean wasFirstLayout = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (wasFirstLayout) {
                requestLayout();
            } else {
                populate();
            }
        }
        if (this.mAdapterChangeListener != null && oldAdapter != adapter) {
            this.mAdapterChangeListener.onAdapterChanged(oldAdapter, adapter);
        }
    }

    private void removeNonDecorViews() {
        int i = 0;
        while (i < getChildCount()) {
            if (!((LayoutParams) getChildAt(i).getLayoutParams()).isDecor) {
                removeViewAt(i);
                i--;
            }
            i++;
        }
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    void setOnAdapterChangeListener(OnAdapterChangeListener listener) {
        this.mAdapterChangeListener = listener;
    }

    private int getClientHeight() {
        return (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
    }

    public void setCurrentItem(int item) {
        boolean z;
        this.mPopulatePending = false;
        if (this.mFirstLayout) {
            z = false;
        } else {
            z = true;
        }
        setCurrentItemInternal(item, z, false);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        this.mPopulatePending = false;
        setCurrentItemInternal(item, smoothScroll, false);
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
        setCurrentItemInternal(item, smoothScroll, always, 0);
    }

    void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
        boolean dispatchSelected = true;
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (always || this.mCurItem != item || this.mItems.size() == 0) {
            if (item < 0) {
                item = 0;
            } else if (item >= this.mAdapter.getCount()) {
                item = this.mAdapter.getCount() - 1;
            }
            int pageLimit = this.mOffscreenPageLimit;
            if (item > this.mCurItem + pageLimit || item < this.mCurItem - pageLimit) {
                for (int i = 0; i < this.mItems.size(); i++) {
                    ((ItemInfo) this.mItems.get(i)).scrolling = true;
                }
            }
            if (this.mCurItem == item) {
                dispatchSelected = false;
            }
            if (this.mFirstLayout) {
                this.mCurItem = item;
                if (dispatchSelected && this.mOnPageChangeListener != null) {
                    this.mOnPageChangeListener.onPageSelected(item);
                }
                if (dispatchSelected && this.mInternalPageChangeListener != null) {
                    this.mInternalPageChangeListener.onPageSelected(item);
                }
                requestLayout();
                return;
            }
            populate(item);
            scrollToItem(item, smoothScroll, velocity, dispatchSelected);
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    private void scrollToItem(int item, boolean smoothScroll, int velocity, boolean dispatchSelected) {
        ItemInfo curInfo = infoForPosition(item);
        int destY = 0;
        if (curInfo != null) {
            destY = (int) (((float) getClientHeight()) * Math.max(this.mFirstOffset, Math.min(curInfo.offset, this.mLastOffset)));
        }
        if (smoothScroll) {
            smoothScrollTo(0, destY, velocity);
            if (dispatchSelected && this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(item);
            }
            if (dispatchSelected && this.mInternalPageChangeListener != null) {
                this.mInternalPageChangeListener.onPageSelected(item);
                return;
            }
            return;
        }
        if (dispatchSelected && this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(item);
        }
        if (dispatchSelected && this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(item);
        }
        completeScroll(false);
        scrollTo(0, destY);
        pageScrolled(destY);
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }

    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
        int i = 1;
        if (VERSION.SDK_INT >= 11) {
            boolean z;
            boolean hasTransformer = transformer != null;
            if (this.mPageTransformer != null) {
                z = true;
            } else {
                z = false;
            }
            boolean needsPopulate = hasTransformer != z;
            this.mPageTransformer = transformer;
            setChildrenDrawingOrderEnabledCompat(hasTransformer);
            if (hasTransformer) {
                if (reverseDrawingOrder) {
                    i = 2;
                }
                this.mDrawingOrder = i;
            } else {
                this.mDrawingOrder = 0;
            }
            if (needsPopulate) {
                populate();
            }
        }
    }

    void setChildrenDrawingOrderEnabledCompat(boolean enable) {
        if (VERSION.SDK_INT >= 7) {
            if (this.mSetChildrenDrawingOrderEnabled == null) {
                try {
                    this.mSetChildrenDrawingOrderEnabled = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[]{Boolean.TYPE});
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "Can't find setChildrenDrawingOrderEnabled", e);
                }
            }
            try {
                this.mSetChildrenDrawingOrderEnabled.invoke(this, new Object[]{Boolean.valueOf(enable)});
            } catch (Exception e2) {
                Log.e(TAG, "Error changing children drawing order", e2);
            }
        }
    }

    protected int getChildDrawingOrder(int childCount, int i) {
        int index;
        if (this.mDrawingOrder == 2) {
            index = (childCount - 1) - i;
        } else {
            index = i;
        }
        return ((LayoutParams) ((View) this.mDrawingOrderedChildren.get(index)).getLayoutParams()).childIndex;
    }

    OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener listener) {
        OnPageChangeListener oldListener = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = listener;
        return oldListener;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public void setOffscreenPageLimit(int limit) {
        if (limit < 1) {
            Log.w(TAG, "Requested offscreen page limit " + limit + " too small; defaulting to " + 1);
            limit = 1;
        }
        if (limit != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = limit;
            populate();
        }
    }

    public void setPageMargin(int marginPixels) {
        int oldMargin = this.mPageMargin;
        this.mPageMargin = marginPixels;
        int height = getHeight();
        recomputeScrollPosition(height, height, marginPixels, oldMargin);
        requestLayout();
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    public void setPageMarginDrawable(Drawable d) {
        this.mMarginDrawable = d;
        if (d != null) {
            refreshDrawableState();
        }
        setWillNotDraw(d == null);
        invalidate();
    }

    public void setPageMarginDrawable(int resId) {
        setPageMarginDrawable(getContext().getResources().getDrawable(resId));
    }

    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.mMarginDrawable;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable d = this.mMarginDrawable;
        if (d != null && d.isStateful()) {
            d.setState(getDrawableState());
        }
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((double) ((float) (((double) (f - 0.5f)) * 0.4712389167638204d)));
    }

    void smoothScrollTo(int x, int y) {
        smoothScrollTo(x, y, 0);
    }

    void smoothScrollTo(int x, int y, int velocity) {
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        int sx = getScrollX();
        int sy = getScrollY();
        int dx = x - sx;
        int dy = y - sy;
        if (dx == 0 && dy == 0) {
            completeScroll(false);
            populate();
            setScrollState(0);
            return;
        }
        int duration;
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int height = getClientHeight();
        int halfHeight = height / 2;
        float distance = ((float) halfHeight) + (((float) halfHeight) * distanceInfluenceForSnapDuration(Math.min(1.0f, (1.0f * ((float) Math.abs(dx))) / ((float) height))));
        velocity = Math.abs(velocity);
        if (velocity > 0) {
            duration = Math.round(1000.0f * Math.abs(distance / ((float) velocity))) * 4;
        } else {
            duration = (int) ((1.0f + (((float) Math.abs(dx)) / (((float) this.mPageMargin) + (((float) height) * this.mAdapter.getPageWidth(this.mCurItem))))) * 300.0f);
        }
        this.mScroller.startScroll(sx, sy, dx, dy, Math.min(duration, 1000));
        ViewCompat.postInvalidateOnAnimation(this);
    }

    ItemInfo addNewItem(int position, int index) {
        ItemInfo ii = new ItemInfo();
        ii.position = position;
        ii.object = this.mAdapter.instantiateItem((ViewGroup) this, position);
        ii.heightFactor = this.mAdapter.getPageWidth(position);
        if (index < 0 || index >= this.mItems.size()) {
            this.mItems.add(ii);
        } else {
            this.mItems.add(index, ii);
        }
        return ii;
    }

    void dataSetChanged() {
        boolean needPopulate;
        int adapterCount = this.mAdapter.getCount();
        this.mExpectedAdapterCount = adapterCount;
        if (this.mItems.size() >= (this.mOffscreenPageLimit * 2) + 1 || this.mItems.size() >= adapterCount) {
            needPopulate = false;
        } else {
            needPopulate = true;
        }
        int newCurrItem = this.mCurItem;
        boolean isUpdating = false;
        int i = 0;
        while (i < this.mItems.size()) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            int newPos = this.mAdapter.getItemPosition(ii.object);
            if (newPos != -1) {
                if (newPos == -2) {
                    this.mItems.remove(i);
                    i--;
                    if (!isUpdating) {
                        this.mAdapter.startUpdate((ViewGroup) this);
                        isUpdating = true;
                    }
                    this.mAdapter.destroyItem((ViewGroup) this, ii.position, ii.object);
                    needPopulate = true;
                    if (this.mCurItem == ii.position) {
                        newCurrItem = Math.max(0, Math.min(this.mCurItem, adapterCount - 1));
                        needPopulate = true;
                    }
                } else if (ii.position != newPos) {
                    if (ii.position == this.mCurItem) {
                        newCurrItem = newPos;
                    }
                    ii.position = newPos;
                    needPopulate = true;
                }
            }
            i++;
        }
        if (isUpdating) {
            this.mAdapter.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (needPopulate) {
            int childCount = getChildCount();
            for (i = 0; i < childCount; i++) {
                LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                if (!lp.isDecor) {
                    lp.heightFactor = 0.0f;
                }
            }
            setCurrentItemInternal(newCurrItem, false, true);
            requestLayout();
        }
    }

    void populate() {
        populate(this.mCurItem);
    }

    void populate(int newCurrentItem) {
        ItemInfo oldCurInfo = null;
        int focusDirection = 2;
        if (this.mCurItem != newCurrentItem) {
            focusDirection = this.mCurItem < newCurrentItem ? TransportMediator.KEYCODE_MEDIA_RECORD : 33;
            oldCurInfo = infoForPosition(this.mCurItem);
            this.mCurItem = newCurrentItem;
        }
        if (this.mAdapter == null) {
            sortChildDrawingOrder();
        } else if (this.mPopulatePending) {
            sortChildDrawingOrder();
        } else if (getWindowToken() != null) {
            this.mAdapter.startUpdate((ViewGroup) this);
            int pageLimit = this.mOffscreenPageLimit;
            int startPos = Math.max(0, this.mCurItem - pageLimit);
            int N = this.mAdapter.getCount();
            int endPos = Math.min(N - 1, this.mCurItem + pageLimit);
            if (N != this.mExpectedAdapterCount) {
                String resName;
                try {
                    resName = getResources().getResourceName(getId());
                } catch (NotFoundException e) {
                    resName = Integer.toHexString(getId());
                }
                throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + N + " Pager id: " + resName + " Pager class: " + getClass() + " Problematic adapter: " + this.mAdapter.getClass());
            }
            ItemInfo ii;
            float extraHeightTop;
            int itemIndex;
            int clientHeight;
            float topHeightNeeded;
            int pos;
            float extraHeightBottom;
            float bottomHeightNeeded;
            int childCount;
            int i;
            View child;
            LayoutParams lp;
            View currentFocused;
            ItemInfo curItem = null;
            int curIndex = 0;
            while (curIndex < this.mItems.size()) {
                ii = (ItemInfo) this.mItems.get(curIndex);
                if (ii.position >= this.mCurItem) {
                    if (ii.position == this.mCurItem) {
                        curItem = ii;
                    }
                    if (curItem == null && N > 0) {
                        curItem = addNewItem(this.mCurItem, curIndex);
                    }
                    if (curItem != null) {
                        extraHeightTop = 0.0f;
                        itemIndex = curIndex - 1;
                        ii = itemIndex < 0 ? (ItemInfo) this.mItems.get(itemIndex) : null;
                        clientHeight = getClientHeight();
                        if (clientHeight > 0) {
                            topHeightNeeded = 0.0f;
                        } else {
                            topHeightNeeded = (2.0f - curItem.heightFactor) + (((float) getPaddingLeft()) / ((float) clientHeight));
                        }
                        pos = this.mCurItem - 1;
                        while (pos >= 0) {
                            if (extraHeightTop >= topHeightNeeded || pos >= startPos) {
                                if (ii == null && pos == ii.position) {
                                    extraHeightTop += ii.heightFactor;
                                    itemIndex--;
                                    ii = itemIndex >= 0 ? (ItemInfo) this.mItems.get(itemIndex) : null;
                                } else {
                                    extraHeightTop += addNewItem(pos, itemIndex + 1).heightFactor;
                                    curIndex++;
                                    ii = itemIndex < 0 ? (ItemInfo) this.mItems.get(itemIndex) : null;
                                }
                            } else if (ii == null) {
                                break;
                            } else {
                                if (pos == ii.position && !ii.scrolling) {
                                    this.mItems.remove(itemIndex);
                                    this.mAdapter.destroyItem((ViewGroup) this, pos, ii.object);
                                    itemIndex--;
                                    curIndex--;
                                    if (itemIndex >= 0) {
                                        ii = (ItemInfo) this.mItems.get(itemIndex);
                                    } else {
                                        ii = null;
                                    }
                                }
                            }
                            pos--;
                        }
                        extraHeightBottom = curItem.heightFactor;
                        itemIndex = curIndex + 1;
                        if (extraHeightBottom < 2.0f) {
                            ii = itemIndex >= this.mItems.size() ? (ItemInfo) this.mItems.get(itemIndex) : null;
                            if (clientHeight > 0) {
                                bottomHeightNeeded = 0.0f;
                            } else {
                                bottomHeightNeeded = (((float) getPaddingRight()) / ((float) clientHeight)) + 2.0f;
                            }
                            pos = this.mCurItem + 1;
                            while (pos < N) {
                                if (extraHeightBottom >= bottomHeightNeeded || pos <= endPos) {
                                    if (ii == null && pos == ii.position) {
                                        extraHeightBottom += ii.heightFactor;
                                        itemIndex++;
                                        ii = itemIndex < this.mItems.size() ? (ItemInfo) this.mItems.get(itemIndex) : null;
                                    } else {
                                        itemIndex++;
                                        extraHeightBottom += addNewItem(pos, itemIndex).heightFactor;
                                        ii = itemIndex >= this.mItems.size() ? (ItemInfo) this.mItems.get(itemIndex) : null;
                                    }
                                } else if (ii == null) {
                                    break;
                                } else {
                                    if (pos == ii.position && !ii.scrolling) {
                                        this.mItems.remove(itemIndex);
                                        this.mAdapter.destroyItem((ViewGroup) this, pos, ii.object);
                                        if (itemIndex < this.mItems.size()) {
                                            ii = (ItemInfo) this.mItems.get(itemIndex);
                                        } else {
                                            ii = null;
                                        }
                                    }
                                }
                                pos++;
                            }
                        }
                        calculatePageOffsets(curItem, curIndex, oldCurInfo);
                    }
                    this.mAdapter.setPrimaryItem((ViewGroup) this, this.mCurItem, curItem == null ? curItem.object : null);
                    this.mAdapter.finishUpdate((ViewGroup) this);
                    childCount = getChildCount();
                    for (i = 0; i < childCount; i++) {
                        child = getChildAt(i);
                        lp = (LayoutParams) child.getLayoutParams();
                        lp.childIndex = i;
                        if (!lp.isDecor && lp.heightFactor == 0.0f) {
                            ii = infoForChild(child);
                            if (ii != null) {
                                lp.heightFactor = ii.heightFactor;
                                lp.position = ii.position;
                            }
                        }
                    }
                    sortChildDrawingOrder();
                    if (hasFocus()) {
                        currentFocused = findFocus();
                        ii = currentFocused == null ? infoForAnyChild(currentFocused) : null;
                        if (ii != null || ii.position != this.mCurItem) {
                            while (i < getChildCount()) {
                                child = getChildAt(i);
                                ii = infoForChild(child);
                                if (ii != null || ii.position != this.mCurItem || !child.requestFocus(focusDirection)) {
                                } else {
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
                curIndex++;
            }
            curItem = addNewItem(this.mCurItem, curIndex);
            if (curItem != null) {
                extraHeightTop = 0.0f;
                itemIndex = curIndex - 1;
                if (itemIndex < 0) {
                }
                clientHeight = getClientHeight();
                if (clientHeight > 0) {
                    topHeightNeeded = (2.0f - curItem.heightFactor) + (((float) getPaddingLeft()) / ((float) clientHeight));
                } else {
                    topHeightNeeded = 0.0f;
                }
                pos = this.mCurItem - 1;
                while (pos >= 0) {
                    if (extraHeightTop >= topHeightNeeded) {
                    }
                    if (ii == null) {
                    }
                    extraHeightTop += addNewItem(pos, itemIndex + 1).heightFactor;
                    curIndex++;
                    if (itemIndex < 0) {
                    }
                    pos--;
                }
                extraHeightBottom = curItem.heightFactor;
                itemIndex = curIndex + 1;
                if (extraHeightBottom < 2.0f) {
                    if (itemIndex >= this.mItems.size()) {
                    }
                    if (clientHeight > 0) {
                        bottomHeightNeeded = (((float) getPaddingRight()) / ((float) clientHeight)) + 2.0f;
                    } else {
                        bottomHeightNeeded = 0.0f;
                    }
                    pos = this.mCurItem + 1;
                    while (pos < N) {
                        if (extraHeightBottom >= bottomHeightNeeded) {
                        }
                        if (ii == null) {
                        }
                        itemIndex++;
                        extraHeightBottom += addNewItem(pos, itemIndex).heightFactor;
                        if (itemIndex >= this.mItems.size()) {
                        }
                        pos++;
                    }
                }
                calculatePageOffsets(curItem, curIndex, oldCurInfo);
            }
            if (curItem == null) {
            }
            this.mAdapter.setPrimaryItem((ViewGroup) this, this.mCurItem, curItem == null ? curItem.object : null);
            this.mAdapter.finishUpdate((ViewGroup) this);
            childCount = getChildCount();
            for (i = 0; i < childCount; i++) {
                child = getChildAt(i);
                lp = (LayoutParams) child.getLayoutParams();
                lp.childIndex = i;
                ii = infoForChild(child);
                if (ii != null) {
                    lp.heightFactor = ii.heightFactor;
                    lp.position = ii.position;
                }
            }
            sortChildDrawingOrder();
            if (hasFocus()) {
                currentFocused = findFocus();
                if (currentFocused == null) {
                }
                if (ii != null) {
                }
                for (i = 0; i < getChildCount(); i++) {
                    child = getChildAt(i);
                    ii = infoForChild(child);
                    if (ii != null) {
                    }
                }
            }
        }
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = new ArrayList();
            } else {
                this.mDrawingOrderedChildren.clear();
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.mDrawingOrderedChildren.add(getChildAt(i));
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    private void calculatePageOffsets(ItemInfo curItem, int curIndex, ItemInfo oldCurInfo) {
        float offset;
        int pos;
        ItemInfo ii;
        int N = this.mAdapter.getCount();
        int height = getClientHeight();
        float marginOffset = height > 0 ? ((float) this.mPageMargin) / ((float) height) : 0.0f;
        if (oldCurInfo != null) {
            int oldCurPosition = oldCurInfo.position;
            int itemIndex;
            if (oldCurPosition < curItem.position) {
                itemIndex = 0;
                offset = (oldCurInfo.offset + oldCurInfo.heightFactor) + marginOffset;
                pos = oldCurPosition + 1;
                while (pos <= curItem.position && itemIndex < this.mItems.size()) {
                    ii = (ItemInfo) this.mItems.get(itemIndex);
                    while (pos > ii.position && itemIndex < this.mItems.size() - 1) {
                        itemIndex++;
                        ii = (ItemInfo) this.mItems.get(itemIndex);
                    }
                    while (pos < ii.position) {
                        offset += this.mAdapter.getPageWidth(pos) + marginOffset;
                        pos++;
                    }
                    ii.offset = offset;
                    offset += ii.heightFactor + marginOffset;
                    pos++;
                }
            } else if (oldCurPosition > curItem.position) {
                itemIndex = this.mItems.size() - 1;
                offset = oldCurInfo.offset;
                pos = oldCurPosition - 1;
                while (pos >= curItem.position && itemIndex >= 0) {
                    ii = (ItemInfo) this.mItems.get(itemIndex);
                    while (pos < ii.position && itemIndex > 0) {
                        itemIndex--;
                        ii = (ItemInfo) this.mItems.get(itemIndex);
                    }
                    while (pos > ii.position) {
                        offset -= this.mAdapter.getPageWidth(pos) + marginOffset;
                        pos--;
                    }
                    offset -= ii.heightFactor + marginOffset;
                    ii.offset = offset;
                    pos--;
                }
            }
        }
        int itemCount = this.mItems.size();
        offset = curItem.offset;
        pos = curItem.position - 1;
        this.mFirstOffset = curItem.position == 0 ? curItem.offset : -3.4028235E38f;
        this.mLastOffset = curItem.position == N + -1 ? (curItem.offset + curItem.heightFactor) - 1.0f : AutoScrollHelper.NO_MAX;
        int i = curIndex - 1;
        while (i >= 0) {
            ii = (ItemInfo) this.mItems.get(i);
            while (pos > ii.position) {
                offset -= this.mAdapter.getPageWidth(pos) + marginOffset;
                pos--;
            }
            offset -= ii.heightFactor + marginOffset;
            ii.offset = offset;
            if (ii.position == 0) {
                this.mFirstOffset = offset;
            }
            i--;
            pos--;
        }
        offset = (curItem.offset + curItem.heightFactor) + marginOffset;
        pos = curItem.position + 1;
        i = curIndex + 1;
        while (i < itemCount) {
            ii = (ItemInfo) this.mItems.get(i);
            while (pos < ii.position) {
                offset += this.mAdapter.getPageWidth(pos) + marginOffset;
                pos++;
            }
            if (ii.position == N - 1) {
                this.mLastOffset = (ii.heightFactor + offset) - 1.0f;
            }
            ii.offset = offset;
            offset += ii.heightFactor + marginOffset;
            i++;
            pos++;
        }
        this.mNeedCalculatePageOffsets = false;
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.position = this.mCurItem;
        if (this.mAdapter != null) {
            ss.adapterState = this.mAdapter.saveState();
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            if (this.mAdapter != null) {
                this.mAdapter.restoreState(ss.adapterState, ss.loader);
                setCurrentItemInternal(ss.position, false, true);
                return;
            }
            this.mRestoredCurItem = ss.position;
            this.mRestoredAdapterState = ss.adapterState;
            this.mRestoredClassLoader = ss.loader;
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        if (!checkLayoutParams(params)) {
            params = generateLayoutParams(params);
        }
        LayoutParams lp = (LayoutParams) params;
        lp.isDecor |= child instanceof Decor;
        if (!this.mInLayout) {
            super.addView(child, index, params);
        } else if (lp == null || !lp.isDecor) {
            lp.needsMeasure = true;
            addViewInLayout(child, index, params);
        } else {
            throw new IllegalStateException("Cannot add pager decor view during layout");
        }
    }

    public void removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    ItemInfo infoForChild(View child) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(child, ii.object)) {
                return ii;
            }
        }
        return null;
    }

    ItemInfo infoForAnyChild(View child) {
        while (true) {
            View parent = child.getParent();
            if (parent == this) {
                return infoForChild(child);
            }
            if (parent != null && (parent instanceof View)) {
                child = parent;
            }
        }
        return null;
    }

    ItemInfo infoForPosition(int position) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            if (ii.position == position) {
                return ii;
            }
        }
        return null;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int measuredHeight = getMeasuredHeight();
        this.mGutterSize = Math.min(measuredHeight / 10, this.mDefaultGutterSize);
        int childWidthSize = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        int childHeightSize = (measuredHeight - getPaddingTop()) - getPaddingBottom();
        int size = getChildCount();
        for (i = 0; i < size; i++) {
            LayoutParams lp;
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                lp = (LayoutParams) child.getLayoutParams();
                if (lp != null && lp.isDecor) {
                    int hgrav = lp.gravity & 7;
                    int vgrav = lp.gravity & BtManagerService.CLASSIC_CMD_GET_STEP;
                    int widthMode = Integer.MIN_VALUE;
                    int heightMode = Integer.MIN_VALUE;
                    boolean consumeVertical = vgrav == 48 || vgrav == 80;
                    boolean consumeHorizontal = hgrav == 3 || hgrav == 5;
                    if (consumeVertical) {
                        widthMode = DataConstants.BYTES_PER_GIGABYTE;
                    } else if (consumeHorizontal) {
                        heightMode = DataConstants.BYTES_PER_GIGABYTE;
                    }
                    int widthSize = childWidthSize;
                    int heightSize = childHeightSize;
                    if (lp.width != -2) {
                        widthMode = DataConstants.BYTES_PER_GIGABYTE;
                        if (lp.width != -1) {
                            widthSize = lp.width;
                        }
                    }
                    if (lp.height != -2) {
                        heightMode = DataConstants.BYTES_PER_GIGABYTE;
                        if (lp.height != -1) {
                            heightSize = lp.height;
                        }
                    }
                    child.measure(MeasureSpec.makeMeasureSpec(widthSize, widthMode), MeasureSpec.makeMeasureSpec(heightSize, heightMode));
                    if (consumeVertical) {
                        childHeightSize -= child.getMeasuredHeight();
                    } else if (consumeHorizontal) {
                        childWidthSize -= child.getMeasuredWidth();
                    }
                }
            }
        }
        this.mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, DataConstants.BYTES_PER_GIGABYTE);
        this.mChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, DataConstants.BYTES_PER_GIGABYTE);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        size = getChildCount();
        for (i = 0; i < size; i++) {
            child = getChildAt(i);
            if (child.getVisibility() != 8) {
                lp = (LayoutParams) child.getLayoutParams();
                if (lp == null || !lp.isDecor) {
                    child.measure(this.mChildWidthMeasureSpec, MeasureSpec.makeMeasureSpec((int) (((float) childHeightSize) * lp.heightFactor), DataConstants.BYTES_PER_GIGABYTE));
                }
            }
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h != oldh) {
            recomputeScrollPosition(h, oldh, this.mPageMargin, this.mPageMargin);
        }
    }

    private void recomputeScrollPosition(int height, int oldHeight, int margin, int oldMargin) {
        if (oldHeight <= 0 || this.mItems.isEmpty()) {
            ItemInfo ii = infoForPosition(this.mCurItem);
            int scrollPos = (int) (((float) ((height - getPaddingTop()) - getPaddingBottom())) * (ii != null ? Math.min(ii.offset, this.mLastOffset) : 0.0f));
            if (scrollPos != getScrollY()) {
                completeScroll(false);
                scrollTo(getScrollX(), scrollPos);
                return;
            }
            return;
        }
        int newOffsetPixels = (int) (((float) (((height - getPaddingTop()) - getPaddingBottom()) + margin)) * (((float) getScrollY()) / ((float) (((oldHeight - getPaddingTop()) - getPaddingBottom()) + oldMargin))));
        scrollTo(getScrollX(), newOffsetPixels);
        if (!this.mScroller.isFinished()) {
            this.mScroller.startScroll(0, newOffsetPixels, 0, (int) (infoForPosition(this.mCurItem).offset * ((float) height)), this.mScroller.getDuration() - this.mScroller.timePassed());
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int i;
        int childLeft;
        int childTop;
        int count = getChildCount();
        int width = r - l;
        int height = b - t;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollY = getScrollY();
        int decorCount = 0;
        for (i = 0; i < count; i++) {
            LayoutParams lp;
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                lp = (LayoutParams) child.getLayoutParams();
                if (lp.isDecor) {
                    int vgrav = lp.gravity & BtManagerService.CLASSIC_CMD_GET_STEP;
                    switch (lp.gravity & 7) {
                        case 1:
                            childLeft = Math.max((width - child.getMeasuredWidth()) / 2, paddingLeft);
                            break;
                        case 3:
                            childLeft = paddingLeft;
                            paddingLeft += child.getMeasuredWidth();
                            break;
                        case 5:
                            childLeft = (width - paddingRight) - child.getMeasuredWidth();
                            paddingRight += child.getMeasuredWidth();
                            break;
                        default:
                            childLeft = paddingLeft;
                            break;
                    }
                    switch (vgrav) {
                        case 16:
                            childTop = Math.max((height - child.getMeasuredHeight()) / 2, paddingTop);
                            break;
                        case 48:
                            childTop = paddingTop;
                            paddingTop += child.getMeasuredHeight();
                            break;
                        case 80:
                            childTop = (height - paddingBottom) - child.getMeasuredHeight();
                            paddingBottom += child.getMeasuredHeight();
                            break;
                        default:
                            childTop = paddingTop;
                            break;
                    }
                    childTop += scrollY;
                    child.layout(childLeft, childTop, child.getMeasuredWidth() + childLeft, child.getMeasuredHeight() + childTop);
                    decorCount++;
                }
            }
        }
        int childHeight = (height - paddingTop) - paddingBottom;
        for (i = 0; i < count; i++) {
            child = getChildAt(i);
            if (child.getVisibility() != 8) {
                lp = (LayoutParams) child.getLayoutParams();
                if (!lp.isDecor) {
                    ItemInfo ii = infoForChild(child);
                    if (ii != null) {
                        childLeft = paddingLeft;
                        childTop = paddingTop + ((int) (((float) childHeight) * ii.offset));
                        if (lp.needsMeasure) {
                            lp.needsMeasure = false;
                            child.measure(MeasureSpec.makeMeasureSpec((width - paddingLeft) - paddingRight, DataConstants.BYTES_PER_GIGABYTE), MeasureSpec.makeMeasureSpec((int) (((float) childHeight) * lp.heightFactor), DataConstants.BYTES_PER_GIGABYTE));
                        }
                        child.layout(childLeft, childTop, child.getMeasuredWidth() + childLeft, child.getMeasuredHeight() + childTop);
                    }
                }
            }
        }
        this.mLeftPageBounds = paddingLeft;
        this.mRightPageBounds = width - paddingRight;
        this.mDecorChildCount = decorCount;
        if (this.mFirstLayout) {
            scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    public void computeScroll() {
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll(true);
            return;
        }
        int oldX = getScrollX();
        int oldY = getScrollY();
        int x = this.mScroller.getCurrX();
        int y = this.mScroller.getCurrY();
        if (!(oldX == x && oldY == y)) {
            scrollTo(x, y);
            if (!pageScrolled(y)) {
                this.mScroller.abortAnimation();
                scrollTo(x, 0);
            }
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private boolean pageScrolled(int ypos) {
        if (this.mItems.size() == 0) {
            this.mCalledSuper = false;
            onPageScrolled(0, 0.0f, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        ItemInfo ii = infoForCurrentScrollPosition();
        int height = getClientHeight();
        int heightWithMargin = height + this.mPageMargin;
        float marginOffset = ((float) this.mPageMargin) / ((float) height);
        int currentPage = ii.position;
        float pageOffset = ((((float) ypos) / ((float) height)) - ii.offset) / (ii.heightFactor + marginOffset);
        int offsetPixels = (int) (((float) heightWithMargin) * pageOffset);
        this.mCalledSuper = false;
        onPageScrolled(currentPage, pageOffset, offsetPixels);
        if (this.mCalledSuper) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        int scrollY;
        int childCount;
        int i;
        View child;
        if (this.mDecorChildCount > 0) {
            scrollY = getScrollY();
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            int height = getHeight();
            childCount = getChildCount();
            for (i = 0; i < childCount; i++) {
                child = getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.isDecor) {
                    int childTop;
                    switch (lp.gravity & BtManagerService.CLASSIC_CMD_GET_STEP) {
                        case 16:
                            childTop = Math.max((height - child.getMeasuredHeight()) / 2, paddingTop);
                            break;
                        case 48:
                            childTop = paddingTop;
                            paddingTop += child.getHeight();
                            break;
                        case 80:
                            childTop = (height - paddingBottom) - child.getMeasuredHeight();
                            paddingBottom += child.getMeasuredHeight();
                            break;
                        default:
                            childTop = paddingTop;
                            break;
                    }
                    int childOffset = (childTop + scrollY) - child.getTop();
                    if (childOffset != 0) {
                        child.offsetTopAndBottom(childOffset);
                    }
                }
            }
        }
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(position, offset, offsetPixels);
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(position, offset, offsetPixels);
        }
        if (this.mPageTransformer != null) {
            scrollY = getScrollY();
            childCount = getChildCount();
            for (i = 0; i < childCount; i++) {
                child = getChildAt(i);
                if (!((LayoutParams) child.getLayoutParams()).isDecor) {
                    this.mPageTransformer.transformPage(child, ((float) (child.getTop() - scrollY)) / ((float) getClientHeight()));
                }
            }
        }
        this.mCalledSuper = true;
    }

    private void completeScroll(boolean postEvents) {
        boolean needPopulate;
        if (this.mScrollState == 2) {
            needPopulate = true;
        } else {
            needPopulate = false;
        }
        if (needPopulate) {
            setScrollingCacheEnabled(false);
            this.mScroller.abortAnimation();
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = this.mScroller.getCurrX();
            int y = this.mScroller.getCurrY();
            if (!(oldX == x && oldY == y)) {
                scrollTo(x, y);
            }
        }
        this.mPopulatePending = false;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            if (ii.scrolling) {
                needPopulate = true;
                ii.scrolling = false;
            }
        }
        if (!needPopulate) {
            return;
        }
        if (postEvents) {
            ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
        } else {
            this.mEndScrollRunnable.run();
        }
    }

    private boolean isGutterDrag(float y, float dy) {
        return (y < ((float) this.mGutterSize) && dy > 0.0f) || (y > ((float) (getHeight() - this.mGutterSize)) && dy < 0.0f);
    }

    private void enableLayers(boolean enable) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewCompat.setLayerType(getChildAt(i), enable ? 2 : 0, null);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & 255;
        if (action == 3 || action == 1) {
            this.mIsBeingDragged = false;
            this.mIsUnableToDrag = false;
            this.mActivePointerId = -1;
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            return false;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        switch (action) {
            case 0:
                float x = ev.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                x = ev.getY();
                this.mInitialMotionY = x;
                this.mLastMotionY = x;
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                this.mIsUnableToDrag = false;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalY() - this.mScroller.getCurrY()) > this.mCloseEnough) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    populate();
                    this.mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                    break;
                }
                completeScroll(false);
                this.mIsBeingDragged = false;
                break;
                break;
            case 2:
                int activePointerId = this.mActivePointerId;
                if (activePointerId != -1) {
                    int pointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerId);
                    float y = MotionEventCompat.getY(ev, pointerIndex);
                    float dy = y - this.mLastMotionY;
                    float yDiff = Math.abs(dy);
                    float x2 = MotionEventCompat.getX(ev, pointerIndex);
                    float xDiff = Math.abs(x2 - this.mInitialMotionX);
                    if (dy == 0.0f || isGutterDrag(this.mLastMotionY, dy) || !canScroll(this, false, (int) dy, (int) x2, (int) y)) {
                        if (yDiff > ((float) this.mTouchSlop) && 0.5f * yDiff > xDiff) {
                            this.mIsBeingDragged = true;
                            requestParentDisallowInterceptTouchEvent(true);
                            setScrollState(1);
                            this.mLastMotionY = dy > 0.0f ? this.mInitialMotionY + ((float) this.mTouchSlop) : this.mInitialMotionY - ((float) this.mTouchSlop);
                            this.mLastMotionX = x2;
                            setScrollingCacheEnabled(true);
                        } else if (xDiff > ((float) this.mTouchSlop)) {
                            this.mIsUnableToDrag = true;
                        }
                        if (this.mIsBeingDragged && performDrag(y)) {
                            ViewCompat.postInvalidateOnAnimation(this);
                            break;
                        }
                    }
                    this.mLastMotionX = x2;
                    this.mLastMotionY = y;
                    this.mIsUnableToDrag = true;
                    return false;
                }
                break;
            case 6:
                onSecondaryPointerUp(ev);
                break;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mFakeDragging) {
            return true;
        }
        if (ev.getAction() == 0 && ev.getEdgeFlags() != 0) {
            return false;
        }
        if (this.mAdapter == null || this.mAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        boolean needsInvalidate = false;
        float x;
        switch (ev.getAction() & 255) {
            case 0:
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                x = ev.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                x = ev.getY();
                this.mInitialMotionY = x;
                this.mLastMotionY = x;
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                break;
            case 1:
                if (this.mIsBeingDragged) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    int initialVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker, this.mActivePointerId);
                    this.mPopulatePending = true;
                    int height = getClientHeight();
                    int scrollY = getScrollY();
                    ItemInfo ii = infoForCurrentScrollPosition();
                    setCurrentItemInternal(determineTargetPage(ii.position, ((((float) scrollY) / ((float) height)) - ii.offset) / ii.heightFactor, initialVelocity, (int) (MotionEventCompat.getY(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId)) - this.mInitialMotionY)), true, true, initialVelocity);
                    this.mActivePointerId = -1;
                    endDrag();
                    needsInvalidate = this.mTopEdge.onRelease() | this.mBottomEdge.onRelease();
                    break;
                }
                break;
            case 2:
                if (!this.mIsBeingDragged) {
                    int pointerIndex = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
                    float y = MotionEventCompat.getY(ev, pointerIndex);
                    float yDiff = Math.abs(y - this.mLastMotionY);
                    float x2 = MotionEventCompat.getX(ev, pointerIndex);
                    float xDiff = Math.abs(x2 - this.mLastMotionX);
                    if (yDiff > ((float) this.mTouchSlop) && yDiff > xDiff) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        if (y - this.mInitialMotionY > 0.0f) {
                            x = this.mInitialMotionY + ((float) this.mTouchSlop);
                        } else {
                            x = this.mInitialMotionY - ((float) this.mTouchSlop);
                        }
                        this.mLastMotionY = x;
                        this.mLastMotionX = x2;
                        setScrollState(1);
                        setScrollingCacheEnabled(true);
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                if (this.mIsBeingDragged) {
                    needsInvalidate = false | performDrag(MotionEventCompat.getY(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId)));
                    break;
                }
                break;
            case 3:
                if (this.mIsBeingDragged) {
                    scrollToItem(this.mCurItem, true, 0, false);
                    this.mActivePointerId = -1;
                    endDrag();
                    needsInvalidate = this.mTopEdge.onRelease() | this.mBottomEdge.onRelease();
                    break;
                }
                break;
            case 5:
                int index = MotionEventCompat.getActionIndex(ev);
                this.mLastMotionY = MotionEventCompat.getY(ev, index);
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            case 6:
                onSecondaryPointerUp(ev);
                this.mLastMotionY = MotionEventCompat.getY(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId));
                break;
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        return true;
    }

    private void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    private boolean performDrag(float y) {
        boolean needsInvalidate = false;
        float deltaY = this.mLastMotionY - y;
        this.mLastMotionY = y;
        float scrollY = ((float) getScrollY()) + deltaY;
        int height = getClientHeight();
        float topBound = ((float) height) * this.mFirstOffset;
        float bottomBound = ((float) height) * this.mLastOffset;
        boolean topAbsolute = true;
        boolean bottomAbsolute = true;
        ItemInfo firstItem = (ItemInfo) this.mItems.get(0);
        ItemInfo lastItem = (ItemInfo) this.mItems.get(this.mItems.size() - 1);
        if (firstItem.position != 0) {
            topAbsolute = false;
            topBound = firstItem.offset * ((float) height);
        }
        if (lastItem.position != this.mAdapter.getCount() - 1) {
            bottomAbsolute = false;
            bottomBound = lastItem.offset * ((float) height);
        }
        if (scrollY < topBound) {
            if (topAbsolute) {
                needsInvalidate = this.mTopEdge.onPull(Math.abs(topBound - scrollY) / ((float) height));
            }
            scrollY = topBound;
        } else if (scrollY > bottomBound) {
            if (bottomAbsolute) {
                needsInvalidate = this.mBottomEdge.onPull(Math.abs(scrollY - bottomBound) / ((float) height));
            }
            scrollY = bottomBound;
        }
        this.mLastMotionX += scrollY - ((float) ((int) scrollY));
        scrollTo(getScrollX(), (int) scrollY);
        pageScrolled((int) scrollY);
        return needsInvalidate;
    }

    private ItemInfo infoForCurrentScrollPosition() {
        float scrollOffset;
        float marginOffset = 0.0f;
        int height = getClientHeight();
        if (height > 0) {
            scrollOffset = ((float) getScrollY()) / ((float) height);
        } else {
            scrollOffset = 0.0f;
        }
        if (height > 0) {
            marginOffset = ((float) this.mPageMargin) / ((float) height);
        }
        int lastPos = -1;
        float lastOffset = 0.0f;
        float lastHeight = 0.0f;
        boolean first = true;
        ItemInfo lastItem = null;
        int i = 0;
        while (i < this.mItems.size()) {
            ItemInfo ii = (ItemInfo) this.mItems.get(i);
            if (!(first || ii.position == lastPos + 1)) {
                ii = this.mTempItem;
                ii.offset = (lastOffset + lastHeight) + marginOffset;
                ii.position = lastPos + 1;
                ii.heightFactor = this.mAdapter.getPageWidth(ii.position);
                i--;
            }
            float offset = ii.offset;
            float topBound = offset;
            float bottomBound = (ii.heightFactor + offset) + marginOffset;
            if (!first && scrollOffset < topBound) {
                return lastItem;
            }
            if (scrollOffset < bottomBound || i == this.mItems.size() - 1) {
                return ii;
            }
            first = false;
            lastPos = ii.position;
            lastOffset = offset;
            lastHeight = ii.heightFactor;
            lastItem = ii;
            i++;
        }
        return lastItem;
    }

    private int determineTargetPage(int currentPage, float pageOffset, int velocity, int deltaY) {
        int targetPage;
        if (Math.abs(deltaY) <= this.mFlingDistance || Math.abs(velocity) <= this.mMinimumVelocity) {
            targetPage = (int) ((((float) currentPage) + pageOffset) + (currentPage >= this.mCurItem ? 0.4f : PieChartData.DEFAULT_CENTER_CIRCLE_SCALE));
        } else {
            targetPage = velocity > 0 ? currentPage : currentPage + 1;
        }
        if (this.mItems.size() <= 0) {
            return targetPage;
        }
        return Math.max(((ItemInfo) this.mItems.get(0)).position, Math.min(targetPage, ((ItemInfo) this.mItems.get(this.mItems.size() - 1)).position));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        boolean needsInvalidate = false;
        int overScrollMode = ViewCompat.getOverScrollMode(this);
        if (overScrollMode == 0 || (overScrollMode == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
            int restoreCount;
            int height;
            int width;
            if (!this.mTopEdge.isFinished()) {
                restoreCount = canvas.save();
                height = getHeight();
                width = (getWidth() - getPaddingLeft()) - getPaddingRight();
                canvas.translate((float) getPaddingLeft(), this.mFirstOffset * ((float) height));
                this.mTopEdge.setSize(width, height);
                needsInvalidate = false | this.mTopEdge.draw(canvas);
                canvas.restoreToCount(restoreCount);
            }
            if (!this.mBottomEdge.isFinished()) {
                restoreCount = canvas.save();
                height = getHeight();
                width = (getWidth() - getPaddingLeft()) - getPaddingRight();
                canvas.rotate(180.0f);
                canvas.translate((float) ((-width) - getPaddingLeft()), (-(this.mLastOffset + 1.0f)) * ((float) height));
                this.mBottomEdge.setSize(width, height);
                needsInvalidate |= this.mBottomEdge.draw(canvas);
                canvas.restoreToCount(restoreCount);
            }
        } else {
            this.mTopEdge.finish();
            this.mBottomEdge.finish();
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int scrollY = getScrollY();
            int height = getHeight();
            float marginOffset = ((float) this.mPageMargin) / ((float) height);
            int itemIndex = 0;
            ItemInfo ii = (ItemInfo) this.mItems.get(0);
            float offset = ii.offset;
            int itemCount = this.mItems.size();
            int firstPos = ii.position;
            int lastPos = ((ItemInfo) this.mItems.get(itemCount - 1)).position;
            int pos = firstPos;
            while (pos < lastPos) {
                float drawAt;
                while (pos > ii.position && itemIndex < itemCount) {
                    itemIndex++;
                    ii = (ItemInfo) this.mItems.get(itemIndex);
                }
                if (pos == ii.position) {
                    drawAt = (ii.offset + ii.heightFactor) * ((float) height);
                    offset = (ii.offset + ii.heightFactor) + marginOffset;
                } else {
                    float heightFactor = this.mAdapter.getPageWidth(pos);
                    drawAt = (offset + heightFactor) * ((float) height);
                    offset += heightFactor + marginOffset;
                }
                if (((float) this.mPageMargin) + drawAt > ((float) scrollY)) {
                    this.mMarginDrawable.setBounds(this.mLeftPageBounds, (int) drawAt, this.mRightPageBounds, (int) ((((float) this.mPageMargin) + drawAt) + 0.5f));
                    this.mMarginDrawable.draw(canvas);
                }
                if (drawAt <= ((float) (scrollY + height))) {
                    pos++;
                } else {
                    return;
                }
            }
        }
    }

    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        setScrollState(1);
        this.mLastMotionY = 0.0f;
        this.mInitialMotionY = 0.0f;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
        long time = SystemClock.uptimeMillis();
        MotionEvent ev = MotionEvent.obtain(time, time, 0, 0.0f, 0.0f, 0);
        this.mVelocityTracker.addMovement(ev);
        ev.recycle();
        this.mFakeDragBeginTime = time;
        return true;
    }

    public void endFakeDrag() {
        if (this.mFakeDragging) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
            int initialVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker, this.mActivePointerId);
            this.mPopulatePending = true;
            int height = getClientHeight();
            int scrollY = getScrollY();
            ItemInfo ii = infoForCurrentScrollPosition();
            setCurrentItemInternal(determineTargetPage(ii.position, ((((float) scrollY) / ((float) height)) - ii.offset) / ii.heightFactor, initialVelocity, (int) (this.mLastMotionY - this.mInitialMotionY)), true, true, initialVelocity);
            endDrag();
            this.mFakeDragging = false;
            return;
        }
        throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
    }

    public void fakeDragBy(float yOffset) {
        if (this.mFakeDragging) {
            this.mLastMotionY += yOffset;
            float scrollY = ((float) getScrollY()) - yOffset;
            int height = getClientHeight();
            float topBound = ((float) height) * this.mFirstOffset;
            float bottomBound = ((float) height) * this.mLastOffset;
            ItemInfo firstItem = (ItemInfo) this.mItems.get(0);
            ItemInfo lastItem = (ItemInfo) this.mItems.get(this.mItems.size() - 1);
            if (firstItem.position != 0) {
                topBound = firstItem.offset * ((float) height);
            }
            if (lastItem.position != this.mAdapter.getCount() - 1) {
                bottomBound = lastItem.offset * ((float) height);
            }
            if (scrollY < topBound) {
                scrollY = topBound;
            } else if (scrollY > bottomBound) {
                scrollY = bottomBound;
            }
            this.mLastMotionY += scrollY - ((float) ((int) scrollY));
            scrollTo(getScrollX(), (int) scrollY);
            pageScrolled((int) scrollY);
            MotionEvent ev = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, 0.0f, this.mLastMotionY, 0);
            this.mVelocityTracker.addMovement(ev);
            ev.recycle();
            return;
        }
        throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = MotionEventCompat.getActionIndex(ev);
        if (MotionEventCompat.getPointerId(ev, pointerIndex) == this.mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.mLastMotionY = MotionEventCompat.getY(ev, newPointerIndex);
            this.mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void setScrollingCacheEnabled(boolean enabled) {
        if (this.mScrollingCacheEnabled != enabled) {
            this.mScrollingCacheEnabled = enabled;
        }
    }

    public boolean internalCanScrollVertically(int direction) {
        boolean z = true;
        if (this.mAdapter == null) {
            return false;
        }
        int height = getClientHeight();
        int scrollY = getScrollY();
        if (direction < 0) {
            if (scrollY <= ((int) (((float) height) * this.mFirstOffset))) {
                z = false;
            }
            return z;
        } else if (direction <= 0) {
            return false;
        } else {
            if (scrollY >= ((int) (((float) height) * this.mLastOffset))) {
                z = false;
            }
            return z;
        }
    }

    protected boolean canScroll(View v, boolean checkV, int dy, int x, int y) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            for (int i = group.getChildCount() - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                if (y + scrollY >= child.getTop() && y + scrollY < child.getBottom() && x + scrollX >= child.getLeft() && x + scrollX < child.getRight()) {
                    if (canScroll(child, true, dy, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
                        return true;
                    }
                }
            }
        }
        return checkV && ViewCompat.canScrollVertically(v, -dy);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event) || executeKeyEvent(event);
    }

    public boolean executeKeyEvent(KeyEvent event) {
        if (event.getAction() != 0) {
            return false;
        }
        switch (event.getKeyCode()) {
            case 21:
                return arrowScroll(17);
            case 22:
                return arrowScroll(66);
            case 61:
                if (VERSION.SDK_INT < 11) {
                    return false;
                }
                if (KeyEventCompat.hasNoModifiers(event)) {
                    return arrowScroll(2);
                }
                if (KeyEventCompat.hasModifiers(event, 1)) {
                    return arrowScroll(1);
                }
                return false;
            default:
                return false;
        }
    }

    public boolean arrowScroll(int direction) {
        View currentFocused = findFocus();
        if (currentFocused == this) {
            currentFocused = null;
        } else if (currentFocused != null) {
            boolean isChild = false;
            for (VerticalViewPager parent = currentFocused.getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
                if (parent == this) {
                    isChild = true;
                    break;
                }
            }
            if (!isChild) {
                StringBuilder sb = new StringBuilder();
                sb.append(currentFocused.getClass().getSimpleName());
                for (ViewParent parent2 = currentFocused.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                    sb.append(" => ").append(parent2.getClass().getSimpleName());
                }
                Log.e(TAG, "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
                currentFocused = null;
            }
        }
        boolean handled = false;
        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
        if (nextFocused == null || nextFocused == currentFocused) {
            if (direction == 33 || direction == 1) {
                handled = pageUp();
            } else if (direction == TransportMediator.KEYCODE_MEDIA_RECORD || direction == 2) {
                handled = pageDown();
            }
        } else if (direction == 33) {
            handled = (currentFocused == null || getChildRectInPagerCoordinates(this.mTempRect, nextFocused).top < getChildRectInPagerCoordinates(this.mTempRect, currentFocused).top) ? nextFocused.requestFocus() : pageUp();
        } else if (direction == TransportMediator.KEYCODE_MEDIA_RECORD) {
            handled = (currentFocused == null || getChildRectInPagerCoordinates(this.mTempRect, nextFocused).bottom > getChildRectInPagerCoordinates(this.mTempRect, currentFocused).bottom) ? nextFocused.requestFocus() : pageDown();
        }
        if (handled) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
        }
        return handled;
    }

    private Rect getChildRectInPagerCoordinates(Rect outRect, View child) {
        if (outRect == null) {
            outRect = new Rect();
        }
        if (child == null) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.left = child.getLeft();
            outRect.right = child.getRight();
            outRect.top = child.getTop();
            outRect.bottom = child.getBottom();
            ViewGroup parent = child.getParent();
            while ((parent instanceof ViewGroup) && parent != this) {
                ViewGroup group = parent;
                outRect.left += group.getLeft();
                outRect.right += group.getRight();
                outRect.top += group.getTop();
                outRect.bottom += group.getBottom();
                parent = group.getParent();
            }
        }
        return outRect;
    }

    boolean pageUp() {
        if (this.mCurItem <= 0) {
            return false;
        }
        setCurrentItem(this.mCurItem - 1, true);
        return true;
    }

    boolean pageDown() {
        if (this.mAdapter == null || this.mCurItem >= this.mAdapter.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        int focusableCount = views.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() == 0) {
                    ItemInfo ii = infoForChild(child);
                    if (ii != null && ii.position == this.mCurItem) {
                        child.addFocusables(views, direction, focusableMode);
                    }
                }
            }
        }
        if ((descendantFocusability == 262144 && focusableCount != views.size()) || !isFocusable()) {
            return;
        }
        if (((focusableMode & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && views != null) {
            views.add(this);
        }
    }

    public void addTouchables(ArrayList<View> views) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                ItemInfo ii = infoForChild(child);
                if (ii != null && ii.position == this.mCurItem) {
                    child.addTouchables(views);
                }
            }
        }
    }

    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        int index;
        int increment;
        int end;
        int count = getChildCount();
        if ((direction & 2) != 0) {
            index = 0;
            increment = 1;
            end = count;
        } else {
            index = count - 1;
            increment = -1;
            end = -1;
        }
        for (int i = index; i != end; i += increment) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                ItemInfo ii = infoForChild(child);
                if (ii != null && ii.position == this.mCurItem && child.requestFocus(direction, previouslyFocusedRect)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(event);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                ItemInfo ii = infoForChild(child);
                if (ii != null && ii.position == this.mCurItem && child.dispatchPopulateAccessibilityEvent(event)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return generateDefaultLayoutParams();
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return (p instanceof LayoutParams) && super.checkLayoutParams(p);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }
}
