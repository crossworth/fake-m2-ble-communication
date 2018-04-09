package com.zhuoyou.plugin.add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.fithealth.running.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PicSelectorPopupWindow extends PopupWindow {
    private SimpleAdapter adapter;
    private List<Map<String, Object>> list = new ArrayList();
    private TextView tv_cancle;
    private TextView tv_chose_camera;
    private TextView tv_take_photo;
    private View view;

    class C11311 implements OnClickListener {
        C11311() {
        }

        public void onClick(View v) {
            PicSelectorPopupWindow.this.dismiss();
        }
    }

    public PicSelectorPopupWindow(Context context, OnClickListener listener) {
        super(context);
        this.view = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.popupwindow_demo, null);
        this.tv_take_photo = (TextView) this.view.findViewById(R.id.tv_take_photo);
        this.tv_chose_camera = (TextView) this.view.findViewById(R.id.tv_chose_pic);
        this.tv_cancle = (TextView) this.view.findViewById(R.id.tv_cancle);
        this.tv_take_photo.setOnClickListener(listener);
        this.tv_chose_camera.setOnClickListener(listener);
        this.tv_cancle.setOnClickListener(new C11311());
        setContentView(this.view);
        setWidth(-1);
        setHeight(-2);
        setFocusable(true);
    }
}
