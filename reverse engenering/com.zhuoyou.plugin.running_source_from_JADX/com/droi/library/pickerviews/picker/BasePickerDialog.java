package com.droi.library.pickerviews.picker;

import android.app.Dialog;
import android.content.Context;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.droi.library.pickerviews.C0738R;
import com.droi.library.pickerviews.tools.DisplayUtils;

public class BasePickerDialog<T extends IPickerView> {
    protected TextView btnOK;
    protected Dialog dialog;
    protected T pickerView;
    protected TextView tvTitle;

    protected void setParams(Context context) {
        Window window = this.dialog.getWindow();
        window.setGravity(80);
        window.setWindowAnimations(C0738R.style.BottomDialogShowAnim);
        LayoutParams lp = window.getAttributes();
        lp.width = DisplayUtils.getScreenMetrics(context).x;
        window.setAttributes(lp);
    }

    public T getPickerView() {
        return this.pickerView;
    }

    public Dialog getDialog() {
        return this.dialog;
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        this.dialog.setCanceledOnTouchOutside(cancel);
    }

    public void setCancelable(boolean cancel) {
        this.dialog.setCancelable(cancel);
    }

    public TextView setTitle(int resid) {
        this.tvTitle.setText(resid);
        return this.tvTitle;
    }

    public TextView setTitle(String title) {
        this.tvTitle.setText(title);
        return this.tvTitle;
    }

    public TextView setOKButton(OnClickListener listener) {
        this.btnOK.setVisibility(0);
        this.btnOK.setOnClickListener(listener);
        return this.btnOK;
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
