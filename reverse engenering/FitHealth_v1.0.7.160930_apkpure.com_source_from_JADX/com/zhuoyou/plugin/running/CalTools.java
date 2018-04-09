package com.zhuoyou.plugin.running;

import android.content.Context;
import com.fithealth.running.R;
import com.tencent.open.yyb.TitleBar;
import java.util.ArrayList;
import java.util.Random;
import p031u.aly.C1507j;

public class CalTools {
    private static final int[] mCal = new int[]{210, 6, 300, 8, 174, C1507j.f3829b, 400, 600};
    private static ArrayList<FoodItem> mFood_list;
    private static final int[] mFoods = new int[]{R.string.rice, R.string.chocolate, R.string.ice_cream, R.string.fat, R.string.cake, R.string.milk_tea, R.string.hamburger, R.string.large_fries};

    private static void init(Context mContext) {
        mFood_list = new ArrayList();
        for (int i = 0; i < mFoods.length; i++) {
            FoodItem mItem = new FoodItem();
            mItem.setName(mContext.getString(mFoods[i]));
            mItem.setCal(mCal[i]);
            mFood_list.add(mItem);
        }
    }

    public static String getResultFromCal(Context mContext, int cal) {
        String result = "";
        init(mContext);
        int index = new Random().nextInt(mFood_list.size());
        if (index < 0 || index >= mFood_list.size()) {
            return result;
        }
        float count = ((float) Math.round((((float) cal) / ((float) ((FoodItem) mFood_list.get(index)).getCal())) * TitleBar.SHAREBTN_RIGHT_MARGIN)) / TitleBar.SHAREBTN_RIGHT_MARGIN;
        if ((count * TitleBar.SHAREBTN_RIGHT_MARGIN) % TitleBar.SHAREBTN_RIGHT_MARGIN == 0.0f) {
            return ((int) count) + " " + ((FoodItem) mFood_list.get(index)).getName();
        }
        return count + " " + ((FoodItem) mFood_list.get(index)).getName();
    }
}
