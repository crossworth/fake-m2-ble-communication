package com.zhuoyou.plugin.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseSport extends Activity {
    private int[] imgData = new int[]{R.drawable.paobu, R.drawable.qixing, R.drawable.zuqiu, R.drawable.lanqiu, R.drawable.pashan, R.drawable.yumaoqiu, R.drawable.palouti, R.drawable.tiaowu, R.drawable.buxing, R.drawable.youyong, R.drawable.tiaosheng, R.drawable.paiqiu, R.drawable.pingpang, R.drawable.jianshencao, R.drawable.lunhua, R.drawable.quanji, R.drawable.wangqiu, R.drawable.gaoerfu, R.drawable.bangqiu, R.drawable.huaxue, R.drawable.danbanhuaxue, R.drawable.yujia, R.drawable.huachuan, R.drawable.baolingqiu, R.drawable.feibiao, R.drawable.tiaoshui, R.drawable.menqiu, R.drawable.chonglang, R.drawable.biqiu, R.drawable.ganlanqiu, R.drawable.jijian, R.drawable.banqiu, R.drawable.binghu, R.drawable.feipan};
    private String likeSportIndex = null;
    private ListView listView;
    private MyAdapter page_one_Adapter;
    private ArrayList<String> selected;
    private String[] sportArray = new String[100];
    private Map<String, Integer> state;
    private String[] wordsData;

    class C12931 implements OnClickListener {
        C12931() {
        }

        public void onClick(View v) {
            ChooseSport.this.finish();
        }
    }

    private class MyAdapter extends BaseAdapter {
        private int[] imgData;
        private Map<String, Integer> state;
        private String[] wordsData;

        private class ViewCache {
            ImageView im;
            ImageView isSelected;
            RelativeLayout relative;
            TextView tv;

            private ViewCache() {
            }
        }

        public MyAdapter(String[] wordsData, int[] imgData, Map<String, Integer> state) {
            this.wordsData = wordsData;
            this.imgData = imgData;
            this.state = state;
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
            ViewCache holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ChooseSport.this).inflate(R.layout.list_item_choose_sport, parent, false);
                holder = new ViewCache();
                holder.im = (ImageView) convertView.findViewById(R.id.image_icon);
                holder.tv = (TextView) convertView.findViewById(R.id.sport_name);
                holder.isSelected = (ImageView) convertView.findViewById(R.id.sport_select);
                holder.relative = (RelativeLayout) convertView.findViewById(R.id.listview_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewCache) convertView.getTag();
            }
            holder.im.setImageResource(this.imgData[position]);
            holder.tv.setText(this.wordsData[position]);
            if (this.state.get(String.valueOf(position)) == null) {
                holder.isSelected.setVisibility(8);
            } else {
                holder.isSelected.setVisibility(0);
            }
            holder.relative.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (holder.isSelected.getVisibility() == 0) {
                        holder.isSelected.setVisibility(8);
                        for (int i = 0; i < ChooseSport.this.selected.size(); i++) {
                            if (MyAdapter.this.wordsData[position].equals(ChooseSport.this.selected.get(i))) {
                                ChooseSport.this.selected.remove(i);
                                MyAdapter.this.state.remove(position + "");
                            }
                        }
                    } else if (holder.isSelected.getVisibility() != 8) {
                    } else {
                        if (ChooseSport.this.selected.size() < 4) {
                            holder.isSelected.setVisibility(0);
                            ChooseSport.this.selected.add(MyAdapter.this.wordsData[position]);
                            MyAdapter.this.state.put(position + "", Integer.valueOf(position));
                            return;
                        }
                        Toast.makeText(ChooseSport.this, R.string.like_sport_limite, 0).show();
                    }
                }
            });
            return convertView;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_sport);
        ((TextView) findViewById(R.id.title)).setText(R.string.chose_sport);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C12931());
        this.likeSportIndex = getIntent().getStringExtra("sportlike");
        this.wordsData = getResources().getStringArray(R.array.page_sport);
        this.sportArray = getResources().getStringArray(R.array.whole_sport_type);
        this.selected = new ArrayList();
        this.state = new HashMap();
        if (!this.likeSportIndex.equals("")) {
            String[] likeIndex = this.likeSportIndex.split(SeparatorConstants.SEPARATOR_ADS_ID);
            if (likeIndex.length > 0) {
                for (String parseInt : likeIndex) {
                    for (int j = 0; j < this.wordsData.length; j++) {
                        String sport = this.wordsData[j];
                        if (sport.equals(this.sportArray[Integer.parseInt(parseInt)]) && !this.selected.contains(sport)) {
                            this.selected.add(this.wordsData[j]);
                            this.state.put(j + "", Integer.valueOf(j));
                        }
                    }
                }
            }
        }
        this.listView = (ListView) findViewById(R.id.sport_select_listview);
        this.page_one_Adapter = new MyAdapter(this.wordsData, this.imgData, this.state);
        this.listView.setAdapter(this.page_one_Adapter);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                return;
            default:
                return;
        }
    }

    public void finish() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("sports", this.selected);
        setResult(-1, intent);
        super.finish();
    }
}
