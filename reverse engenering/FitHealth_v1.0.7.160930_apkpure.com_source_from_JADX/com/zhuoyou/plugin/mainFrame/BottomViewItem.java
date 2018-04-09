package com.zhuoyou.plugin.mainFrame;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fithealth.running.R;

public class BottomViewItem {
    public static BottomViewItem instance;
    public ImageView[] images = new ImageView[this.viewNum];
    public int[] images_id = new int[]{R.id.home_image, R.id.order_image, R.id.finder_image, R.id.mine_image};
    public int[] images_selected = new int[]{R.drawable.home_press, R.drawable.order_press, R.drawable.finder_press, R.drawable.mine_press};
    public int[] images_unselected = new int[]{R.drawable.home_normal, R.drawable.order_normal, R.drawable.finder_normal, R.drawable.mine_normal};
    public LinearLayout[] linears = new LinearLayout[this.viewNum];
    public int[] linears_id = new int[]{R.id.home_layout, R.id.order_layout, R.id.finder_layout, R.id.mine_layout};
    public TextView[] texts = new TextView[this.viewNum];
    public int[] texts_id = new int[]{R.id.home_text, R.id.order_text, R.id.finder_text, R.id.mine_text};
    public int viewNum = 4;

    public static BottomViewItem getInstance() {
        if (instance == null) {
            instance = new BottomViewItem();
        }
        return instance;
    }
}
