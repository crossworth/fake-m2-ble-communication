package com.droi.library.pickerviews.lib;

import com.tencent.open.yyb.TitleBar;
import java.util.TimerTask;

final class InertiaTimerTask extends TimerTask {
    float f2257a = 2.14748365E9f;
    final WheelView loopView;
    final float velocityY;

    InertiaTimerTask(WheelView loopview, float velocityY) {
        this.loopView = loopview;
        this.velocityY = velocityY;
    }

    public final void run() {
        if (this.f2257a == 2.14748365E9f) {
            if (Math.abs(this.velocityY) <= 2000.0f) {
                this.f2257a = this.velocityY;
            } else if (this.velocityY > 0.0f) {
                this.f2257a = 2000.0f;
            } else {
                this.f2257a = -2000.0f;
            }
        }
        if (Math.abs(this.f2257a) < 0.0f || Math.abs(this.f2257a) > TitleBar.BACKBTN_LEFT_MARGIN) {
            int i = (int) ((this.f2257a * TitleBar.SHAREBTN_RIGHT_MARGIN) / 1000.0f);
            this.loopView.totalScrollY -= i;
            if (!this.loopView.isLoop) {
                float itemHeight = this.loopView.itemHeight;
                float top = ((float) (-this.loopView.initPosition)) * itemHeight;
                float bottom = ((float) ((this.loopView.getItemsCount() - 1) - this.loopView.initPosition)) * itemHeight;
                if (((double) this.loopView.totalScrollY) - (((double) itemHeight) * 0.3d) < ((double) top)) {
                    top = (float) (this.loopView.totalScrollY + i);
                } else if (((double) this.loopView.totalScrollY) + (((double) itemHeight) * 0.3d) > ((double) bottom)) {
                    bottom = (float) (this.loopView.totalScrollY + i);
                }
                if (((float) this.loopView.totalScrollY) <= top) {
                    this.f2257a = 40.0f;
                    this.loopView.totalScrollY = (int) top;
                } else if (((float) this.loopView.totalScrollY) >= bottom) {
                    this.loopView.totalScrollY = (int) bottom;
                    this.f2257a = -40.0f;
                }
            }
            if (this.f2257a < 0.0f) {
                this.f2257a += TitleBar.BACKBTN_LEFT_MARGIN;
            } else {
                this.f2257a -= TitleBar.BACKBTN_LEFT_MARGIN;
            }
            this.loopView.handler.sendEmptyMessage(1000);
            return;
        }
        this.loopView.cancelFuture();
        this.loopView.handler.sendEmptyMessage(2000);
    }
}
