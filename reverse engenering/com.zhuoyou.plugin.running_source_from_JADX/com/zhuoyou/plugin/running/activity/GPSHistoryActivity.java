package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.droi.greendao.bean.GpsSportBean;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.database.GpsSportHelper;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import java.util.ArrayList;
import java.util.List;

public class GPSHistoryActivity extends BaseActivity {
    public static final int REQUEST_CODE_DETAIL = 257;
    private BaseAdapter adapter = new C17612();
    private List<GpsSportBean> dataList = new ArrayList();
    private String format;
    private TextView tvHisCount;
    private TextView tvTotalCal;
    private TextView tvTotalDis;
    private ListView viewList;

    class C17601 implements OnItemClickListener {
        C17601() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            GPSHistoryActivity.this.startActivityForResult(new Intent(GPSHistoryActivity.this, GPSDetailActivity.class).putExtra("key_sport_bean_id", ((GpsSportBean) GPSHistoryActivity.this.dataList.get(position)).getId()), 257);
        }
    }

    class C17612 extends BaseAdapter {

        class ViewHolder {
            ImageView imgLocus;
            TextView tvCal;
            TextView tvDistance;
            TextView tvDuration;
            TextView tvTime;

            ViewHolder() {
            }
        }

        C17612() {
        }

        public int getCount() {
            return GPSHistoryActivity.this.dataList.size();
        }

        public Object getItem(int position) {
            return GPSHistoryActivity.this.dataList.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(GPSHistoryActivity.this).inflate(C1680R.layout.item_gps_history_list, parent, false);
                holder.tvDuration = (TextView) convertView.findViewById(C1680R.id.tv_duration);
                holder.tvDistance = (TextView) convertView.findViewById(C1680R.id.tv_distance);
                holder.tvCal = (TextView) convertView.findViewById(C1680R.id.tv_cal);
                holder.imgLocus = (ImageView) convertView.findViewById(C1680R.id.img_locus);
                holder.tvTime = (TextView) convertView.findViewById(C1680R.id.tv_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            GpsSportBean item = (GpsSportBean) GPSHistoryActivity.this.dataList.get(position);
            Tools.displayImage(holder.imgLocus, GpsUtils.getLocusUri(item.getId()), true);
            holder.tvDuration.setText(GPSHistoryActivity.this.getString(C1680R.string.gps_history_duration, new Object[]{GpsUtils.getDuration(item.getDuration())}));
            holder.tvDistance.setText(GPSHistoryActivity.this.getString(C1680R.string.gps_history_distance, new Object[]{GpsUtils.getDisStrNoUnit(item.getDistance())}));
            holder.tvCal.setText(GPSHistoryActivity.this.getString(C1680R.string.gps_history_cal, new Object[]{GpsUtils.getCalStrNoUnit(item.getCal())}));
            holder.tvTime.setText(Tools.formatDate(item.getId(), Tools.DEFAULT_FORMAT_TIME, GPSHistoryActivity.this.format));
            return convertView;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_gps_history);
        this.format = getString(C1680R.string.gps_history_time_format);
        initView();
        initData();
    }

    private void initView() {
        this.viewList = (ListView) findViewById(C1680R.id.history_list);
        this.tvHisCount = (TextView) findViewById(C1680R.id.tv_his_count);
        this.tvTotalDis = (TextView) findViewById(C1680R.id.tv_total_distance);
        this.tvTotalCal = (TextView) findViewById(C1680R.id.tv_total_cal);
        this.viewList.setAdapter(this.adapter);
        this.viewList.setOnItemClickListener(new C17601());
    }

    private void initData() {
        this.dataList = GpsSportHelper.loadAllDesc();
        this.adapter.notifyDataSetChanged();
        this.tvHisCount.setText(getString(C1680R.string.gps_history_count, new Object[]{Integer.valueOf(this.dataList.size())}));
        String[] total = GpsUtils.getTatalData(this.dataList);
        this.tvTotalDis.setText(total[0]);
        this.tvTotalCal.setText(total[1]);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 257:
                if (resultCode == 257) {
                    initData();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
