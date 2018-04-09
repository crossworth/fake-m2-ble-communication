package com.zhuoyou.plugin.running;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class SleepPageAdapter extends FragmentStatePagerAdapter {
    private List<String> mDates = new ArrayList();
    public SparseArray<Fragment> registeredFragments = new SparseArray();

    public void notifyDataSetChanged(List<String> lists) {
        Log.d("3333", "notifyDataSetChanged 1111");
        this.mDates = lists;
        super.notifyDataSetChanged();
    }

    public SleepPageAdapter(FragmentManager fm, List<String> lists) {
        super(fm);
        this.mDates = lists;
    }

    public int getCount() {
        return this.mDates.size();
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
        return SleepPageItemFragment.newInstance((String) this.mDates.get(arg0));
    }

    public Parcelable saveState() {
        return null;
    }
}
