package com.droi.library.pickerviews.picker;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import com.droi.library.pickerviews.C0545R;

public class NumberPickerDialog extends BasePickerDialog<NumberPickerView> {
    public NumberPickerDialog(Context context) {
        this.dialog = new Dialog(context, C0545R.style.DefaultDialog);
        this.dialog.setContentView(C0545R.layout.number_picker_dialog);
        setParams(context);
        initView();
    }

    public void initView() {
        this.pickerView = (NumberPickerView) this.dialog.findViewById(C0545R.id.number_picker_view);
        this.btnOK = (TextView) this.dialog.findViewById(C0545R.id.btn_ok);
        this.tvTitle = (TextView) this.dialog.findViewById(C0545R.id.tv_title);
    }
}
