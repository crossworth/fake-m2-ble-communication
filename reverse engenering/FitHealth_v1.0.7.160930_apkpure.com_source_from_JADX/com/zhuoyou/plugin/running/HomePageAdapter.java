package com.zhuoyou.plugin.running;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePageAdapter extends FragmentStatePagerAdapter {
    private List<RunningItem> mRunningDates = new ArrayList();
    public SparseArray<Fragment> registeredFragments = new SparseArray();
    private Map<String, Integer> steps = null;
    private Map<String, String> weight = null;

    public void notifyDataSetChanged(List<RunningItem> lists, Map<String, String> wei, Map<String, Integer> step) {
        this.mRunningDates = lists;
        this.weight = wei;
        this.steps = step;
        super.notifyDataSetChanged();
    }

    public HomePageAdapter(FragmentManager fm, List<RunningItem> lists, Map<String, String> wei, Map<String, Integer> step) {
        super(fm);
        if (lists == null) {
            this.mRunningDates = new ArrayList();
        } else {
            this.mRunningDates = lists;
        }
        this.weight = wei;
        this.steps = step;
    }

    public int getCount() {
        return this.mRunningDates.size();
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        this.registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        Fragment localFragment = (Fragment) super.instantiateItem(container, position);
        this.registeredFragments.put(position, localFragment);
        return localFragment;
    }

    public Fragment getItem(int arg0) {
        return HomePageItemFragment.newInstance((RunningItem) this.mRunningDates.get(arg0), this.weight, this.steps);
    }

    public Parcelable saveState() {
        return null;
    }
}
