package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.fithealth.running.R;

public class SharePopupWindow extends PopupWindow {
    public static SharePopupWindow mInstance;
    private String mFileName;
    private TextView mShare;
    private EditText mShare_edit = ((EditText) this.mView.findViewById(R.id.share_edit));
    private ImageView mShare_weibo = ((ImageView) this.mView.findViewById(R.id.share_weibo));
    private View mView;

    class C14121 implements OnTouchListener {
        C14121() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            int height = SharePopupWindow.this.mView.findViewById(R.id.share_text).getTop();
            int y = (int) event.getY();
            if (event.getAction() == 1 && y < height) {
                SharePopupWindow.this.dismiss();
            }
            return true;
        }
    }

    public SharePopupWindow(Activity context, OnClickListener itemsOnClick, String fileName) {
        super(context);
        mInstance = this;
        this.mFileName = fileName;
        this.mView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.share_popwindow_layout, null);
        this.mShare_weibo.setOnClickListener(itemsOnClick);
        this.mShare = (TextView) this.mView.findViewById(R.id.share);
        this.mShare.setOnClickListener(itemsOnClick);
        setContentView(this.mView);
        setWidth(-1);
        setHeight(-2);
        setFocusable(true);
        setAnimationStyle(R.style.AnimBottom);
        setBackgroundDrawable(new ColorDrawable(0));
        this.mView.setOnTouchListener(new C14121());
    }

    public String getShareContent() {
        if (this.mShare_edit != null) {
            return this.mShare_edit.getText().toString();
        }
        return null;
    }

    public ImageView getWeiboView() {
        return this.mShare_weibo;
    }

    public String getShareFileName() {
        return this.mFileName;
    }
}
