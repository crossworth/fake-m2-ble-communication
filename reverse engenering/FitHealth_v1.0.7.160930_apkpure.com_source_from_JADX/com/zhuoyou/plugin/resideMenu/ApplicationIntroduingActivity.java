package com.zhuoyou.plugin.resideMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fithealth.running.R;
import java.util.ArrayList;
import java.util.List;

public class ApplicationIntroduingActivity extends Activity {
    private View appView1;
    private View appView2;
    private View appView3;
    private View appView4;
    private TextView tvExperienceRightNow;
    private List<View> viewGroup = new ArrayList();
    private ViewPager viewPager;

    class C13261 implements OnClickListener {
        C13261() {
        }

        public void onClick(View v) {
            ApplicationIntroduingActivity.this.setResult(-1);
            ApplicationIntroduingActivity.this.finish();
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        public List<View> mlist;

        public MyPagerAdapter(List<View> mlist) {
            this.mlist = mlist;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) this.mlist.get(position));
        }

        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
        }

        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView((View) this.mlist.get(position), 0);
            return this.mlist.get(position);
        }

        public void restoreState(Parcelable state, ClassLoader loader) {
            super.restoreState(state, loader);
        }

        public int getCount() {
            return this.mlist.size();
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    @SuppressLint({"InflateParams"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_introducing);
        this.viewPager = (ViewPager) findViewById(R.id.app_introduce_viewpage);
        getLayoutInflater();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        this.appView1 = layoutInflater.inflate(R.layout.app_view1, null);
        this.appView2 = layoutInflater.inflate(R.layout.app_view2, null);
        this.appView3 = layoutInflater.inflate(R.layout.app_view3, null);
        this.appView4 = layoutInflater.inflate(R.layout.app_view4, null);
        this.tvExperienceRightNow = (TextView) this.appView4.findViewById(R.id.experience_rightnow);
        this.viewGroup.add(this.appView1);
        this.viewGroup.add(this.appView2);
        this.viewGroup.add(this.appView3);
        this.viewGroup.add(this.appView4);
        this.viewPager.setAdapter(new MyPagerAdapter(this.viewGroup));
        this.tvExperienceRightNow.setOnClickListener(new C13261());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        return true;
    }
}
