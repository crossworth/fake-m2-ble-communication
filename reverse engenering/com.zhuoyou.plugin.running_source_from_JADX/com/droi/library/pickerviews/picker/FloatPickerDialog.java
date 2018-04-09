package com.droi.library.pickerviews.picker;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import com.droi.library.pickerviews.C0738R;

public class FloatPickerDialog extends BasePickerDialog<FloatPickerView> {
    public FloatPickerDialog(Context context) {
        this.dialog = new Dialog(context, C0738R.style.DefaultDialog);
        this.dialog.setContentView(C0738R.layout.float_picker_dialog);
        setParams(context);
        initView();
    }

    public void initView() {
        this.pickerView = (FloatPickerView) this.dialog.findViewById(C0738R.id.float_picker_view);
        this.btnOK = (TextView) this.dialog.findViewById(C0738R.id.btn_ok);
        this.tvTitle = (TextView) this.dialog.findViewById(C0738R.id.tv_title);
    }
}
