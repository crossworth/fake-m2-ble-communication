package com.zhuoyou.plugin.running.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.tools.Fonts;

public class VitalCapView extends RelativeLayout {
    private ArcView arcValue;
    private TextView tvValue;

    public VitalCapView(Context context) {
        super(context);
        init();
    }

    public VitalCapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VitalCapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(C1680R.layout.layout_vital_cap_view, this, true);
        this.arcValue = (ArcView) rootView.findViewById(C1680R.id.arc_value);
        this.tvValue = (TextView) rootView.findViewById(C1680R.id.tv_value);
        this.tvValue.setTypeface(Fonts.number);
        this.tvValue.setText("0");
    }

    public void setValue(int value) {
        this.arcValue.draw(8000, value);
        this.tvValue.setText(String.valueOf(value));
    }

    public void setOnClickListener(OnClickListener l) {
        this.arcValue.setOnClickListener(l);
    }
}
