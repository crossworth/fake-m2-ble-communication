package com.droi.library.pickerviews.picker;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import com.droi.library.pickerviews.C0545R;

public class AddressPickerDialog extends BasePickerDialog<AddressPickerView> {
    public AddressPickerDialog(Context context) {
        this.dialog = new Dialog(context, C0545R.style.DefaultDialog);
        this.dialog.setContentView(C0545R.layout.address_picker_dialog);
        setParams(context);
        initView();
    }

    public void initView() {
        this.pickerView = (AddressPickerView) this.dialog.findViewById(C0545R.id.address_picker_view);
        this.btnOK = (TextView) this.dialog.findViewById(C0545R.id.btn_ok);
        this.tvTitle = (TextView) this.dialog.findViewById(C0545R.id.tv_title);
    }
}
