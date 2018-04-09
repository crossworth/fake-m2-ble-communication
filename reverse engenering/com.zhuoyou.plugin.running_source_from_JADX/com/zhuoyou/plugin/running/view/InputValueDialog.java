package com.zhuoyou.plugin.running.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseDialog;
import com.zhuoyou.plugin.running.tools.DisplayUtils;

public class InputValueDialog extends BaseDialog {
    private MyActionBar actionBar;
    private boolean allowEmpty;
    private EditText etValue;
    private Context mContext;
    private OnClickListener onClickListener = new C19421();
    private OnOkClickListener onOkClickListener;

    public interface OnOkClickListener {
        void onClick(String str);
    }

    class C19421 implements OnClickListener {
        C19421() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case C1680R.id.actionbar_back_title:
                    InputValueDialog.this.dialog.dismiss();
                    return;
                case C1680R.id.actionbar_right_title:
                    String value = InputValueDialog.this.etValue.getText().toString();
                    if (!InputValueDialog.this.allowEmpty && TextUtils.isEmpty(value.trim())) {
                        InputValueDialog.this.etValue.setError(InputValueDialog.this.mContext.getString(C1680R.string.string_input_not_null));
                        return;
                    } else if (InputValueDialog.this.onOkClickListener != null) {
                        InputValueDialog.this.onOkClickListener.onClick(value);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public InputValueDialog(Context context) {
        this.mContext = context;
        this.dialog = new Dialog(context, C1680R.style.FullScreenDialog);
        this.dialog.setContentView(C1680R.layout.layout_edit_dialog);
        Window window = this.dialog.getWindow();
        window.setSoftInputMode(4);
        LayoutParams lp = window.getAttributes();
        lp.width = DisplayUtils.getScreenMetrics(context).x;
        lp.height = DisplayUtils.getScreenMetrics(context).y;
        window.setAttributes(lp);
        initView();
    }

    private void initView() {
        this.actionBar = (MyActionBar) this.dialog.findViewById(C1680R.id.actionbar);
        this.actionBar.setOnRightTitleClickListener(this.onClickListener);
        this.actionBar.setOnBackTitleClickListener(this.onClickListener);
        this.etValue = (EditText) this.dialog.findViewById(C1680R.id.et_value);
    }

    public void setOnOkClickListener(OnOkClickListener listener) {
        this.onOkClickListener = listener;
    }

    public void setTitle(String titleStr) {
        this.actionBar.setBackTitle(titleStr);
    }

    public void setTitle(int resId) {
        this.actionBar.setBackTitle(resId);
    }

    public EditText getEditText() {
        return this.etValue;
    }

    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }
}
