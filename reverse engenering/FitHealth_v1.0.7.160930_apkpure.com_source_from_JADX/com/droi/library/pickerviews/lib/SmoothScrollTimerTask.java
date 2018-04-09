package com.droi.library.pickerviews.lib;

import java.util.TimerTask;

final class SmoothScrollTimerTask extends TimerTask {
    final WheelView loopView;
    int offset;
    int realOffset = 0;
    int realTotalOffset = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

    SmoothScrollTimerTask(WheelView loopview, int offset) {
        this.loopView = loopview;
        this.offset = offset;
    }

    public final void run() {
        if (this.realTotalOffset == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
            this.realTotalOffset = this.offset;
        }
        this.realOffset = (int) (((float) this.realTotalOffset) * 0.1f);
        if (this.realOffset == 0) {
            if (this.realTotalOffset < 0) {
                this.realOffset = -1;
            } else {
                this.realOffset = 1;
            }
        }
        if (Math.abs(this.realTotalOffset) <= 1) {
            this.loopView.cancelFuture();
            this.loopView.handler.sendEmptyMessage(3000);
            return;
        }
        this.loopView.totalScrollY += this.realOffset;
        if (!this.loopView.isLoop) {
            float itemHeight = this.loopView.itemHeight;
            float bottom = ((float) ((this.loopView.getItemsCount() - 1) - this.loopView.initPosition)) * itemHeight;
            if (((float) this.loopView.totalScrollY) <= ((float) (-this.loopView.initPosition)) * itemHeight || ((float) this.loopView.totalScrollY) >= bottom) {
                this.loopView.totalScrollY -= this.realOffset;
                this.loopView.cancelFuture();
                this.loopView.handler.sendEmptyMessage(3000);
                return;
            }
        }
        this.loopView.handler.sendEmptyMessage(1000);
        this.realTotalOffset -= this.realOffset;
    }
}
