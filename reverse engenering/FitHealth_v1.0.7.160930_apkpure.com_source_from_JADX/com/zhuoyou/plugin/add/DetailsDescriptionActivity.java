package com.zhuoyou.plugin.add;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.view.CircleFlowIndicator;
import com.zhuoyou.plugin.view.ViewFlow;

public class DetailsDescriptionActivity extends Activity {
    public static final int COLOR_CHANGE = Color.parseColor("#4b8339");
    public int[] ids = new int[]{R.drawable.details1, R.drawable.details2, R.drawable.details3, R.drawable.details4};
    private ViewFlow viewFlow;

    class DemoPic extends BaseAdapter {
        private LayoutInflater mInflater;

        class C11251 implements OnClickListener {
            C11251() {
            }

            public void onClick(View arg0) {
                DetailsDescriptionActivity.this.finish();
            }
        }

        public DemoPic() {
            this.mInflater = (LayoutInflater) DetailsDescriptionActivity.this.getSystemService("layout_inflater");
        }

        public int getCount() {
            return DetailsDescriptionActivity.this.ids.length;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = this.mInflater.inflate(R.layout.heartrate_details_imageitem, null);
            }
            ((ImageView) convertView.findViewById(R.id.imgView)).setImageResource(DetailsDescriptionActivity.this.ids[position % DetailsDescriptionActivity.this.ids.length]);
            ((ImageView) convertView.findViewById(R.id.exit)).setOnClickListener(new C11251());
            return convertView;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.details_description);
        this.viewFlow = (ViewFlow) findViewById(R.id.viewflow);
        this.viewFlow.setAdapter(new DemoPic());
        this.viewFlow.setmSideBuffer(this.ids.length);
        CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        indic.setFillColor(getResources().getColor(R.color.color_guild_page2));
        indic.setStrokeColor(getResources().getColor(R.color.color_guild_page1));
        this.viewFlow.setFlowIndicator(indic);
        this.viewFlow.setSelection(0);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    protected void onResume() {
        super.onResume();
    }
}
