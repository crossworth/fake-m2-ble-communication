package com.droi.library.pickerviews.picker;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import com.droi.library.pickerviews.C0545R;

public class FloatPickerDialog extends BasePickerDialog<FloatPickerView> {
    public FloatPickerDialog(Context context) {
        this.dialog = new Dialog(context, C0545R.style.DefaultDialog);
        this.dialog.setContentView(C0545R.layout.float_picker_dialog);
        setParams(context);
        initView();
    }

    public void initView() {
        this.pickerView = (FloatPickerView) this.dialog.findViewById(C0545R.id.float_picker_view);
        this.btnOK = (TextView) this.dialog.findViewById(C0545R.id.btn_ok);
        this.tvTitle = (TextView) this.dialog.findViewById(C0545R.id.tv_title);
    }
}
