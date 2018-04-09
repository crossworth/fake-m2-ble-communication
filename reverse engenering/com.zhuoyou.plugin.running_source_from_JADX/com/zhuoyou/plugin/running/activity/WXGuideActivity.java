package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.WXGuide;
import com.zhuoyou.plugin.running.tools.StatusBarUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import java.util.ArrayList;
import java.util.LinkedList;

public class WXGuideActivity extends BaseActivity {
    private PagerAdapter adapter = new C18431();
    private ImageView imgPoint;
    private OnPageChangeListener onPageChangeListener = new C18442();
    private ArrayList<WXGuide> pageList = new ArrayList();
    private ViewPager viewPager;

    class C18431 extends PagerAdapter {
        private LinkedList<View> mViewCache = new LinkedList();

        class ViewHolder {
            ImageView img;
            TextView tvHint;

            ViewHolder() {
            }
        }

        C18431() {
        }

        public int getCount() {
            return WXGuideActivity.this.pageList.size();
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            View contentView = (View) object;
            container.removeView(contentView);
            this.mViewCache.add(contentView);
        }

        public Object instantiateItem(ViewGroup container, int position) {
            View convertView;
            ViewHolder holder;
            if (this.mViewCache.size() == 0) {
                convertView = LayoutInflater.from(WXGuideActivity.this).inflate(C1680R.layout.item_weixin_guide_pager, container, false);
                holder = new ViewHolder();
                holder.img = (ImageView) convertView.findViewById(C1680R.id.img_page);
                holder.tvHint = (TextView) convertView.findViewById(C1680R.id.tv_hint);
                convertView.setTag(holder);
            } else {
                convertView = (View) this.mViewCache.removeFirst();
                holder = (ViewHolder) convertView.getTag();
            }
            container.addView(convertView);
            WXGuide item = (WXGuide) WXGuideActivity.this.pageList.get(position);
            holder.img.setImageResource(item.getImg());
            holder.tvHint.setText(item.getHint());
            return convertView;
        }
    }

    class C18442 implements OnPageChangeListener {
        C18442() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            LayoutParams params = (LayoutParams) WXGuideActivity.this.imgPoint.getLayoutParams();
            params.leftMargin = (int) ((((float) position) + positionOffset) * ((float) Tools.dip2px(22.0f)));
            WXGuideActivity.this.imgPoint.setLayoutParams(params);
        }

        public void onPageSelected(int position) {
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.transparencyBar(this, true);
        setContentView((int) C1680R.layout.activity_weixin_guide);
        this.viewPager = (ViewPager) findViewById(C1680R.id.view_pager);
        this.imgPoint = (ImageView) findViewById(C1680R.id.img_point);
        this.viewPager.addOnPageChangeListener(this.onPageChangeListener);
        initPageList();
        this.viewPager.setAdapter(this.adapter);
    }

    private void initPageList() {
        this.pageList.add(new WXGuide(C1680R.drawable.wx_guide_page1, C1680R.string.wx_guide_page_hint1));
        this.pageList.add(new WXGuide(C1680R.drawable.wx_guide_page2, C1680R.string.wx_guide_page_hint2));
        this.pageList.add(new WXGuide(C1680R.drawable.wx_guide_page3, C1680R.string.wx_guide_page_hint3));
        this.pageList.add(new WXGuide(C1680R.drawable.wx_guide_page4, C1680R.string.wx_guide_page_hint4));
    }
}
