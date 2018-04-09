package com.umeng.socialize.shareboard.wigets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.utils.Log;
import java.lang.reflect.Array;

public class UMActionFrame extends ViewGroup {
    private static final int ABANDON = 3;
    private static final int COVER = 2;
    private static final int OCCUPIED = 1;
    private ActionFrameAdapter mAdapter;
    private int mColumn = 4;
    private Context mContext = null;
    private int mCoverColor = -1;
    private int mDividerColor = 0;
    private int mDividerSize = 2;
    private int mHeight;
    private int[][] mOccupied = ((int[][]) null);
    private int mRow = 0;
    private int mWidth;

    public UMActionFrame(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mDividerColor = context.getResources().getColor(ResContainer.get(context).color("umeng_socialize_grid_divider_line"));
        this.mContext = context;
    }

    public UMActionFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDividerColor = context.getResources().getColor(ResContainer.get(context).color("umeng_socialize_grid_divider_line"));
        this.mContext = context;
    }

    public UMActionFrame(Context context) {
        super(context);
        this.mDividerColor = context.getResources().getColor(ResContainer.get(context).color("umeng_socialize_grid_divider_line"));
        this.mContext = context;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mAdapter != null) {
            Context context = getContext();
            calculateOccupied(this.mAdapter.getCount());
            removeAllViews();
            int matrixX = this.mOccupied.length;
            int matrixY = this.mOccupied[0].length;
            int stepX = (this.mWidth - ((matrixX - 1) * this.mDividerSize)) / matrixX;
            int stepY = (this.mHeight - ((matrixY - 1) * this.mDividerSize)) / matrixY;
            int dividerLenX = 0;
            int dividerLenY = 0;
            int childPos = 0;
            int i = 0;
            while (i < matrixY) {
                int top;
                View dividerView;
                int j = 0;
                int childPos2 = childPos;
                while (j < matrixX) {
                    if (this.mOccupied[j][i] == 1) {
                        childPos = childPos2 + 1;
                        View view = this.mAdapter.getView(childPos2, this);
                        LayoutParams params = view.getLayoutParams();
                        if (params == null) {
                            view.setLayoutParams(new LayoutParams(stepX, stepY));
                        } else {
                            params.height = stepY;
                            params.width = stepX;
                        }
                        boolean isLastX = j == matrixX + -1;
                        int left = (j * stepX) + dividerLenX;
                        int right = left + stepX;
                        top = (i * stepY) + dividerLenY;
                        int bottom = top + stepY;
                        addView(view);
                        measureChild(view, stepX, stepY);
                        view.layout(left, top, right, bottom);
                        if (!isLastX && this.mOccupied[j + 1][i] == 2) {
                            View coverView = new View(context);
                            coverView.setBackgroundColor(this.mCoverColor);
                            addView(coverView);
                            coverView.layout(left + stepX, top, r, bottom);
                        }
                        dividerView = new View(context);
                        if (isLastX) {
                            dividerView.setBackgroundColor(this.mCoverColor);
                            dividerLenX = 0;
                        } else {
                            dividerView.setBackgroundColor(this.mDividerColor);
                            dividerLenX += this.mDividerSize;
                        }
                        addView(dividerView);
                        dividerView.layout(left + stepX, top, this.mDividerSize + right, bottom);
                    } else {
                        childPos = childPos2;
                    }
                    j++;
                    childPos2 = childPos;
                }
                boolean bb = i > 0 ? this.mOccupied[0][i + -1] == 1 : false;
                if (bb) {
                    dividerView = new View(context);
                    dividerView.setBackgroundColor(this.mDividerColor);
                    addView(dividerView);
                    top = dividerLenY + (i * stepY);
                    dividerView.layout(l, top - this.mDividerSize, r, top);
                }
                dividerLenX = 0;
                dividerLenY += this.mDividerSize;
                i++;
                childPos = childPos2;
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    public void calculateOccupied(int cell) {
        int available;
        int cover = 0;
        int x = this.mOccupied.length;
        int y = this.mOccupied[0].length;
        int mDimen = x * y;
        if (cell > mDimen) {
            available = mDimen;
        } else {
            available = cell;
        }
        int remNum = available % x;
        if (remNum > 0) {
            cover = x - remNum;
        }
        int abandon = (mDimen - available) - cover;
        int startCover = abandon + available;
        int pos = 0;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (pos >= abandon && pos < startCover) {
                    this.mOccupied[j][i] = 1;
                } else if (pos >= startCover) {
                    this.mOccupied[j][i] = 2;
                } else {
                    this.mOccupied[j][i] = 3;
                }
                pos++;
            }
        }
    }

    public ActionFrameAdapter getAdapter() {
        return this.mAdapter;
    }

    private void initRowAndColumnWithConfig() {
        if (this.mContext == null || this.mAdapter == null) {
            this.mOccupied = (int[][]) Array.newInstance(Integer.TYPE, new int[]{4, 2});
            return;
        }
        if (this.mContext.getResources().getConfiguration().orientation == 2) {
            this.mColumn = 6;
        }
        int totalPlatforms = this.mAdapter.getCount();
        this.mRow = this.mAdapter.getCount() / this.mColumn;
        if (totalPlatforms % this.mColumn > 0) {
            this.mRow++;
        }
        Log.m4546d("", "###### row = " + this.mRow + ", column = " + this.mColumn);
        this.mOccupied = (int[][]) Array.newInstance(Integer.TYPE, new int[]{this.mColumn, this.mRow});
    }

    public void setAdapter(ActionFrameAdapter mAdapter) {
        this.mAdapter = mAdapter;
        initRowAndColumnWithConfig();
        requestLayout();
    }

    public void setDividerColor(int dividerColor) {
        this.mDividerColor = dividerColor;
    }

    public void setCoverColor(int coverColor) {
        this.mCoverColor = coverColor;
    }

    public void setDividerSize(int dividerSize) {
        this.mDividerSize = dividerSize;
    }

    public int getAdapterHeight(int width) {
        return (this.mRow * ((width - ((this.mColumn - 1) * this.mDividerSize)) / this.mColumn)) + ((this.mRow - 1) * this.mDividerSize);
    }
}
