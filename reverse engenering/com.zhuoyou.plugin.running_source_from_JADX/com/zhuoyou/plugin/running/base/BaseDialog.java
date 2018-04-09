package com.zhuoyou.plugin.running.base;

import android.app.Dialog;
import android.widget.TextView;

public class BaseDialog {
    protected Dialog dialog;
    protected TextView title;

    public Dialog getDialog() {
        return this.dialog;
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        this.dialog.setCanceledOnTouchOutside(cancel);
    }

    public void setCancelable(boolean cancel) {
        this.dialog.setCancelable(cancel);
    }

    public void setTitle(String titleStr) {
        if (this.title != null) {
            this.title.setText(titleStr);
            this.title.setVisibility(0);
        }
    }

    public void setTitle(int resId) {
        if (this.title != null) {
            this.title.setText(resId);
            this.title.setVisibility(0);
        }
    }

    public void show() {
        this.dialog.show();
    }

    public void dismiss() {
        this.dialog.dismiss();
    }

    public boolean isShowing() {
        return this.dialog.isShowing();
    }
}
