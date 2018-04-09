package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import com.fithealth.running.R;

public class CalendarGridView extends GridView {
    private Display display;
    private Context mContext;

    public CalendarGridView(Context context) {
        super(context);
        this.mContext = context;
        setGirdView();
    }

    private void setGirdView() {
        this.display = ((Activity) this.mContext).getWindowManager().getDefaultDisplay();
        int i = this.display.getWidth() / 7;
        setLayoutParams(new LayoutParams(-1, -2));
        setNumColumns(7);
        setGravity(16);
        setSelector(new ColorDrawable(0));
        setVerticalSpacing(1);
        setBackgroundColor(getResources().getColor(R.color.cal_bg));
        setPadding((this.display.getWidth() - (i * 7)) / 2, 0, 0, 0);
    }
}
