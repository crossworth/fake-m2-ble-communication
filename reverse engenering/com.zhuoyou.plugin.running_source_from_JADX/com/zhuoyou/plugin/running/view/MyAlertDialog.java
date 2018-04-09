package com.zhuoyou.plugin.running.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseDialog;
import com.zhuoyou.plugin.running.tools.DisplayUtils;

public class MyAlertDialog extends BaseDialog {
    public static final int LEFT_BUTTON = 1;
    public static final int MIDDLE_BUTTON = 2;
    public static final int RIGHT_BUTTON = 3;
    private Button leftButton;
    private TextView message;
    private Button middleButton;
    private Button rightButton;

    public interface OnClickListener {
        void onClick(int i);
    }

    public MyAlertDialog(Context context) {
        this.dialog = new Dialog(context, C1680R.style.DefaultDialog);
        this.dialog.setContentView(C1680R.layout.layout_alert_dialog);
        Window window = this.dialog.getWindow();
        LayoutParams lp = window.getAttributes();
        lp.width = DisplayUtils.getScreenMetrics(context).x;
        window.setAttributes(lp);
        iniView();
    }

    public void setContentView(View view) {
        this.dialog.setContentView(view);
        iniView();
    }

    private void iniView() {
        this.title = (TextView) this.dialog.findViewById(C1680R.id.dialog_title);
        this.message = (TextView) this.dialog.findViewById(C1680R.id.dialog_message);
        this.leftButton = (Button) this.dialog.findViewById(C1680R.id.button_left);
        this.middleButton = (Button) this.dialog.findViewById(C1680R.id.button_middle);
        this.rightButton = (Button) this.dialog.findViewById(C1680R.id.button_right);
    }

    public void setLeftButton(String text, final OnClickListener listener) {
        this.leftButton.setText(text);
        this.leftButton.setVisibility(0);
        this.leftButton.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                MyAlertDialog.this.dialog.dismiss();
                if (listener != null) {
                    listener.onClick(1);
                }
            }
        });
    }

    public void setLeftButton(int resid, final OnClickListener listener) {
        this.leftButton.setText(resid);
        this.leftButton.setVisibility(0);
        this.leftButton.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                MyAlertDialog.this.dialog.dismiss();
                if (listener != null) {
                    listener.onClick(1);
                }
            }
        });
    }

    public void setMiddleButton(String text, final OnClickListener listener) {
        this.middleButton.setText(text);
        this.middleButton.setVisibility(0);
        this.middleButton.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                MyAlertDialog.this.dialog.dismiss();
                if (listener != null) {
                    listener.onClick(2);
                }
            }
        });
    }

    public void setMiddleButton(int resid, final OnClickListener listener) {
        this.middleButton.setText(resid);
        this.middleButton.setVisibility(0);
        this.middleButton.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                MyAlertDialog.this.dialog.dismiss();
                if (listener != null) {
                    listener.onClick(2);
                }
            }
        });
    }

    public void setRightButton(String text, final OnClickListener listener) {
        this.rightButton.setText(text);
        this.rightButton.setVisibility(0);
        this.rightButton.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                MyAlertDialog.this.dialog.dismiss();
                if (listener != null) {
                    listener.onClick(3);
                }
            }
        });
    }

    public void setRightButton(int resid, final OnClickListener listener) {
        this.rightButton.setText(resid);
        this.rightButton.setVisibility(0);
        this.rightButton.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                MyAlertDialog.this.dialog.dismiss();
                if (listener != null) {
                    listener.onClick(3);
                }
            }
        });
    }

    public void setMessage(String messageStr) {
        this.message.setText(messageStr);
    }

    public void setMessage(int resId) {
        this.message.setText(resId);
    }

    public void setDismissListener(OnDismissListener listener) {
        this.dialog.setOnDismissListener(listener);
    }
}
