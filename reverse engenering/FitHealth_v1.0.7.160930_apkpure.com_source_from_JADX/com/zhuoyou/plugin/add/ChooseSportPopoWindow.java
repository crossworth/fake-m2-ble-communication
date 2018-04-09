package com.zhuoyou.plugin.add;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;

public class ChooseSportPopoWindow extends PopupWindow {
    private String choiced = null;
    private int[] imgData = new int[]{R.drawable.paobu, R.drawable.qixing, R.drawable.zuqiu, R.drawable.lanqiu, R.drawable.pashan, R.drawable.yumaoqiu, R.drawable.palouti, R.drawable.tiaowu, R.drawable.buxing, R.drawable.youyong, R.drawable.tiaosheng, R.drawable.paiqiu, R.drawable.pingpang, R.drawable.jianshencao, R.drawable.lunhua, R.drawable.quanji, R.drawable.wangqiu, R.drawable.gaoerfu, R.drawable.bangqiu, R.drawable.huaxue, R.drawable.danbanhuaxue, R.drawable.yujia, R.drawable.huachuan, R.drawable.baolingqiu, R.drawable.feibiao, R.drawable.tiaoshui, R.drawable.menqiu, R.drawable.chonglang, R.drawable.biqiu, R.drawable.ganlanqiu, R.drawable.jijian, R.drawable.banqiu, R.drawable.binghu, R.drawable.feipan};
    private ListView listView;
    private Context mContext;
    private MyAdapter page_one_Adapter;
    private TextView tv_ok;
    private View view;
    private String[] wordsData;

    class C11201 implements OnClickListener {
        C11201() {
        }

        public void onClick(View v) {
            ChooseSportPopoWindow.this.dismiss();
        }
    }

    private class MyAdapter extends BaseAdapter {
        private int[] imgData;
        private String[] wordsData;

        public MyAdapter(String[] wordsData, int[] imgData) {
            this.wordsData = wordsData;
            this.imgData = imgData;
        }

        public int getCount() {
            return this.wordsData.length;
        }

        public Object getItem(int position) {
            return this.wordsData[position];
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(ChooseSportPopoWindow.this.mContext).inflate(R.layout.list_item_choose_sport, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.sport_name);
            RelativeLayout relative = (RelativeLayout) convertView.findViewById(R.id.listview_item);
            ((ImageView) convertView.findViewById(R.id.image_icon)).setImageResource(this.imgData[position]);
            tv.setText(this.wordsData[position]);
            ImageView isSelected = (ImageView) convertView.findViewById(R.id.sport_select);
            if (ChooseSportPopoWindow.this.choiced.equals(this.wordsData[position])) {
                Log.d("txhlog", "choiced:" + ChooseSportPopoWindow.this.choiced);
                isSelected.setVisibility(0);
            } else {
                isSelected.setVisibility(8);
            }
            isSelected.setTag(this.wordsData[position]);
            relative.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ImageView pre_isSelected_tag = null;
                    if (!(ChooseSportPopoWindow.this.choiced == null || ChooseSportPopoWindow.this.choiced == MyAdapter.this.wordsData[position])) {
                        pre_isSelected_tag = (ImageView) ChooseSportPopoWindow.this.listView.findViewWithTag(ChooseSportPopoWindow.this.choiced);
                    }
                    ImageView isSelected_tag = (ImageView) ChooseSportPopoWindow.this.listView.findViewWithTag(MyAdapter.this.wordsData[position]);
                    ChooseSportPopoWindow.this.choiced = MyAdapter.this.wordsData[position];
                    if (pre_isSelected_tag != null) {
                        pre_isSelected_tag.setVisibility(8);
                    }
                    if (isSelected_tag != null) {
                        isSelected_tag.setVisibility(0);
                    }
                }
            });
            return convertView;
        }
    }

    public ChooseSportPopoWindow(Context context, String selete) {
        this.mContext = context;
        this.choiced = selete;
        this.view = LayoutInflater.from(context).inflate(R.layout.viewpage_demo, null);
        this.listView = (ListView) this.view.findViewById(R.id.sport_select_listview);
        this.wordsData = this.mContext.getResources().getStringArray(R.array.page_sport);
        this.page_one_Adapter = new MyAdapter(this.wordsData, this.imgData);
        this.listView.setAdapter(this.page_one_Adapter);
        this.tv_ok = (TextView) this.view.findViewById(R.id.tv_ok);
        this.tv_ok.setOnClickListener(new C11201());
        setContentView(this.view);
        setWidth(-1);
        setHeight(-2);
        setFocusable(true);
        setBackgroundDrawable(new PaintDrawable());
        setOutsideTouchable(true);
    }

    public String getSport() {
        return this.choiced;
    }
}
