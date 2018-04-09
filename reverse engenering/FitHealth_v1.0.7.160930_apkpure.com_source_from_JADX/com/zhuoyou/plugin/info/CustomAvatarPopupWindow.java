package com.zhuoyou.plugin.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.fithealth.running.R;

public class CustomAvatarPopupWindow extends PopupWindow {

    class C12951 implements OnClickListener {
        C12951() {
        }

        public void onClick(View v) {
            CustomAvatarPopupWindow.this.dismiss();
        }
    }

    public CustomAvatarPopupWindow(Context context, Boolean flag, OnClickListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_avatar_popwindow, null);
        TextView tv_select = (TextView) view.findViewById(R.id.tv_select);
        TextView tv_take_photo = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView tv_chose_camera = (TextView) view.findViewById(R.id.tv_chose_pic);
        TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        if (flag.booleanValue()) {
            tv_select.setOnClickListener(listener);
        } else {
            tv_select.setVisibility(8);
        }
        tv_take_photo.setOnClickListener(listener);
        tv_chose_camera.setOnClickListener(listener);
        tv_cancle.setOnClickListener(new C12951());
        setContentView(view);
        setWidth(-1);
        setHeight(-2);
        setFocusable(true);
    }
}
