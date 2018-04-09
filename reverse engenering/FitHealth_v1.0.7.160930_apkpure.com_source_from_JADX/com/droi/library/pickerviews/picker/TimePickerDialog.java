package com.droi.library.pickerviews.picker;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import com.droi.library.pickerviews.C0545R;

public class TimePickerDialog extends BasePickerDialog<TimePickerView> {
    public TimePickerDialog(Context context) {
        this.dialog = new Dialog(context, C0545R.style.DefaultDialog);
        this.dialog.setContentView(C0545R.layout.time_picker_dialog);
        setParams(context);
        initView();
    }

    public void initView() {
        this.pickerView = (TimePickerView) this.dialog.findViewById(C0545R.id.time_picker_view);
        this.btnOK = (TextView) this.dialog.findViewById(C0545R.id.btn_ok);
        this.tvTitle = (TextView) this.dialog.findViewById(C0545R.id.tv_title);
    }
}
