package com.zhuoyou.plugin.running.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseDialog;
import com.zhuoyou.plugin.running.tools.DisplayUtils;
import com.zhuoyou.plugin.running.tools.Tools;

public class HintPagerDialog extends BaseDialog {
    private ImageView imgPoint;
    private OnPageChangeListener onPageChangeListener = new C19412();
    private LinearLayout pointLayout;
    private View pointsLayout;
    private ViewPager viewPager;

    class C19401 implements OnClickListener {
        C19401() {
        }

        public void onClick(View v) {
            HintPagerDialog.this.dialog.dismiss();
        }
    }

    class C19412 implements OnPageChangeListener {
        C19412() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            LayoutParams params = (LayoutParams) HintPagerDialog.this.imgPoint.getLayoutParams();
            params.leftMargin = (int) (((((float) position) + positionOffset) * ((float) Tools.dip2px(12.0f))) + ((float) Tools.dip2px(3.0f)));
            HintPagerDialog.this.imgPoint.setLayoutParams(params);
        }

        public void onPageSelected(int position) {
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public HintPagerDialog(Context context) {
        this.dialog = new Dialog(context, C1680R.style.FullScreenDialog);
        this.dialog.setContentView(C1680R.layout.layout_hint_pager_dialog);
        Window window = this.dialog.getWindow();
        window.setGravity(17);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = DisplayUtils.getScreenMetrics(context).x;
        lp.height = DisplayUtils.getScreenMetrics(context).y;
        window.setAttributes(lp);
        initView();
    }

    private void initView() {
        this.viewPager = (ViewPager) this.dialog.findViewById(C1680R.id.view_pager);
        ((ImageView) this.dialog.findViewById(C1680R.id.btn_close)).setOnClickListener(new C19401());
        this.viewPager.addOnPageChangeListener(this.onPageChangeListener);
        this.pointsLayout = this.dialog.findViewById(C1680R.id.points_layout);
        this.pointLayout = (LinearLayout) this.dialog.findViewById(C1680R.id.point_layout);
        this.imgPoint = (ImageView) this.dialog.findViewById(C1680R.id.img_point);
    }

    public void setAdapter(PagerAdapter adapter) {
        this.viewPager.setAdapter(adapter);
        initPoints(adapter.getCount());
        if (adapter.getCount() > 0) {
            this.viewPager.setCurrentItem(0);
        }
    }

    private void initPoints(int count) {
        if (count < 2) {
            this.pointsLayout.setVisibility(8);
            return;
        }
        this.pointLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView img = new ImageView(this.pointLayout.getContext());
            img.setImageResource(C1680R.drawable.hint_page_point2);
            this.pointLayout.addView(img);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
            params.leftMargin = Tools.dip2px(3.0f);
            params.rightMargin = Tools.dip2px(3.0f);
            img.setLayoutParams(params);
        }
        this.pointsLayout.setVisibility(0);
    }
}
