package com.zhuoyou.plugin.mainFrame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.umeng.analytics.MobclickAgent;
import com.zhuoyou.plugin.custom.TitlePageIndicator;
import com.zhuoyou.plugin.custom.ViewPagerScroller;
import com.zhuoyou.plugin.rank.RankInfo;
import com.zhuoyou.plugin.rank.RankListAdapter;
import com.zhuoyou.plugin.rank.ShareRankActivity;
import com.zhuoyou.plugin.running.Main;
import com.zhuoyou.plugin.running.Main.MyOnTouchListener;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RankFragment extends Fragment implements OnClickListener {
    private static final String TAG = "RankFragment";
    private static float event_x = 0.0f;
    private static float event_y = 0.0f;
    public static Handler handler = null;
    private List<RankInfo> accountHighestData = new ArrayList();
    private List<RankInfo> accountMouthData = new ArrayList();
    private List<RankInfo> accountServenData = new ArrayList();
    private Bitmap bmp = null;
    private int currIndex;
    private boolean flag = false;
    private List<RankInfo> highestStepList = new ArrayList();
    private boolean isListViewFirstItemVisible;
    private boolean isLogin;
    private float last_margin = 0.0f;
    private ListView list0;
    private ListView list1;
    private ListView list2;
    private ListView list3;
    private ListView list4;
    private ImageView mImgRankTheme;
    private TitlePageIndicator mIndicator;
    private OnScrollListener mListViewScrollListener;
    private Typeface mNumberTP;
    private List<String> mPageTitleContent;
    private int mRankThemeBgHeight = 0;
    private View mRootView;
    private View mSubFrame;
    private View mView0;
    private View mView1;
    private View mView2;
    private View mView3;
    private View mView4;
    private List<RankInfo> mouthDalysList = new ArrayList();
    private ImageView myIcon;
    private TextView myName;
    MyOnTouchListener myOnTouchListener;
    private TextView myRank;
    private ImageView myRankBg;
    private RelativeLayout myRankLayout;
    private TextView mySteps;
    private LinearLayout noLogin;
    private LinearLayout noRankLayout;
    private RelativeLayout progress;
    private RankListAdapter rAdapterDay;
    private RankListAdapter rAdapterMouth;
    private RankListAdapter rAdapterWeek;
    private RankListPageAdatper rankListPA;
    private LayoutParams realHeaderLayoutParams;
    private List<RankInfo> sevenDalysList = new ArrayList();
    private ProgressBar titleProgress;
    private ImageView titleShare;
    private float top_margin = 0.0f;
    private ViewPager viewPager;

    class C13072 implements OnScrollListener {
        int displayItemSize = 0;
        int itemHeight = 0;
        int viewHeight = 0;

        C13072() {
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case 0:
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                    }
                    if (view.getFirstVisiblePosition() != 0) {
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int i;
            if (this.itemHeight == 0 && view.getChildAt(0) != null) {
                this.itemHeight = view.getChildAt(0).getHeight();
            }
            int i2 = this.viewHeight == 0 ? 1 : 0;
            if (view.getHeight() > this.viewHeight) {
                i = 1;
            } else {
                i = 0;
            }
            if ((i2 | i) != 0) {
                this.viewHeight = view.getHeight();
            }
            if (!(this.viewHeight == 0 || this.itemHeight == 0)) {
                this.displayItemSize = this.viewHeight / this.itemHeight;
            }
            if (firstVisibleItem != 0) {
                RankFragment.this.isListViewFirstItemVisible = false;
            } else if (view.getChildAt(0) == null) {
            } else {
                if (view.getChildAt(0).getY() <= -10.0f || visibleItemCount >= this.displayItemSize + 2) {
                    RankFragment.this.isListViewFirstItemVisible = false;
                } else {
                    RankFragment.this.isListViewFirstItemVisible = true;
                }
            }
        }
    }

    class C18921 implements MyOnTouchListener {
        C18921() {
        }

        public boolean onTouch(MotionEvent ev) {
            RankFragment.this.mRankThemeBgHeight = RankFragment.this.mImgRankTheme.getHeight();
            switch (ev.getAction()) {
                case 0:
                    RankFragment.event_y = ev.getY();
                    RankFragment.event_x = ev.getX();
                    RankFragment.this.realHeaderLayoutParams = (LayoutParams) RankFragment.this.mSubFrame.getLayoutParams();
                    RankFragment.this.top_margin = (float) RankFragment.this.realHeaderLayoutParams.topMargin;
                    RankFragment.this.last_margin = RankFragment.this.top_margin;
                    break;
                case 2:
                    float currentX = ev.getX();
                    float currentY = ev.getY();
                    float deltayX = currentX - RankFragment.event_x;
                    float deltayY = currentY - RankFragment.event_y;
                    RankFragment.event_x = currentX;
                    RankFragment.event_y = currentY;
                    if (Math.abs(deltayY) > Math.abs(deltayX)) {
                        if (deltayY > 0.0f) {
                            if (!RankFragment.this.flag && !RankFragment.this.isListViewFirstItemVisible) {
                                return true;
                            }
                            if (RankFragment.this.top_margin + deltayY < 0.0f) {
                                RankFragment.this.top_margin = RankFragment.this.top_margin + deltayY;
                                RankFragment.this.flag = true;
                            } else {
                                RankFragment.this.top_margin = 0.0f;
                                RankFragment.this.flag = false;
                            }
                        } else if (RankFragment.this.top_margin + deltayY > ((float) (RankFragment.this.mRankThemeBgHeight * -1))) {
                            RankFragment.this.top_margin = RankFragment.this.top_margin + deltayY;
                            RankFragment.this.flag = true;
                        } else {
                            RankFragment.this.top_margin = (float) (RankFragment.this.mRankThemeBgHeight * -1);
                            RankFragment.this.flag = false;
                        }
                        if (Math.abs(RankFragment.this.last_margin - RankFragment.this.top_margin) > 1.0f) {
                            RankFragment.this.realHeaderLayoutParams.topMargin = (int) RankFragment.this.top_margin;
                            RankFragment.this.mSubFrame.setLayoutParams(RankFragment.this.realHeaderLayoutParams);
                            RankFragment.this.last_margin = RankFragment.this.top_margin;
                        }
                    }
                    if (Math.abs(deltayX) > Math.abs(deltayY)) {
                        return true;
                    }
                    if (RankFragment.this.flag) {
                        return false;
                    }
                    break;
            }
            return true;
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {
        int pageIndex;
        int positions;

        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 0 && this.positions != this.pageIndex) {
                RankFragment.this.viewPager.setCurrentItem(this.pageIndex, false);
            }
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            this.pageIndex = position;
            this.positions = position;
            if (this.positions == 1) {
                this.pageIndex = 1;
            } else if (this.positions == 1) {
                this.pageIndex = 1;
            }
            RankFragment.this.setShowType(this.pageIndex);
            RankFragment.this.currIndex = this.pageIndex;
        }
    }

    class RankListPageAdatper extends PagerAdapter {
        private List<View> mViews;

        public RankListPageAdatper(List<View> rankViews) {
            this.mViews = rankViews;
        }

        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) this.mViews.get(position % this.mViews.size()));
        }

        public int getCount() {
            if (this.mViews != null) {
                return this.mViews.size();
            }
            return 0;
        }

        public CharSequence getPageTitle(int position) {
            return (CharSequence) RankFragment.this.mPageTitleContent.get(position % RankFragment.this.mPageTitleContent.size());
        }

        public Object instantiateItem(View container, int position) {
            Log.d("yangyang", "position =" + position);
            ((ViewPager) container).addView((View) this.mViews.get(position), 0);
            return this.mViews.get(position);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public void refresh(List<View> rankViews) {
            this.mViews.clear();
            this.mViews = rankViews;
            super.notifyDataSetChanged();
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.rank_fragment, container, false);
        InitListener();
        initView();
        this.highestStepList = ((Main) getActivity()).GetRankDate(3);
        this.accountServenData = ((Main) getActivity()).GetRankDate(4);
        this.accountMouthData = ((Main) getActivity()).GetRankDate(5);
        this.accountHighestData = ((Main) getActivity()).GetRankDate(6);
        Log.i("hph", "highestStepList:" + this.highestStepList.size() + " mouthDalysList:" + this.mouthDalysList.size() + " highestStepList:" + this.highestStepList.size() + " accountServenData:" + this.accountServenData + " accountMouthData:" + this.accountMouthData.size() + " accountHighestData:" + this.accountHighestData.size());
        if (this.highestStepList.size() >= 0 && this.accountHighestData.size() >= 0) {
            if (this.progress.getVisibility() == 0) {
                this.progress.setVisibility(8);
            }
            if (this.titleProgress.getVisibility() == 0) {
                this.titleProgress.setVisibility(8);
            }
            setInitAdapter();
            setShowType(this.currIndex);
        }
        return this.mRootView;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    public void onDestroyView() {
        super.onDestroyView();
        ((Main) getActivity()).unregisterMyOnTouchListener(this.myOnTouchListener);
        this.rAdapterDay = null;
        this.rAdapterWeek = null;
        this.rAdapterMouth = null;
        this.rankListPA = null;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    void initView() {
        Log.d("yangyang", "initView");
        this.titleShare = (ImageView) this.mRootView.findViewById(R.id.title_share);
        this.titleShare.setOnClickListener(this);
        this.mImgRankTheme = (ImageView) this.mRootView.findViewById(R.id.rank_theme_bg);
        this.noLogin = (LinearLayout) this.mRootView.findViewById(R.id.nologin);
        this.mSubFrame = this.mRootView.findViewById(R.id.container);
        this.realHeaderLayoutParams = (LayoutParams) this.mSubFrame.getLayoutParams();
        this.top_margin = (float) this.realHeaderLayoutParams.topMargin;
        this.viewPager = (ViewPager) this.mRootView.findViewById(R.id.vPager);
        initViewPagerScroll(this.viewPager);
        this.mIndicator = (TitlePageIndicator) this.mRootView.findViewById(R.id.indicator);
        this.noRankLayout = (LinearLayout) this.mRootView.findViewById(R.id.noranklayout);
        this.myRankLayout = (RelativeLayout) this.mRootView.findViewById(R.id.mylayout);
        this.myRankLayout.setVisibility(8);
        this.myRank = (TextView) this.mRootView.findViewById(R.id.my_rank);
        this.myRankBg = (ImageView) this.mRootView.findViewById(R.id.my_rank_bg);
        this.myIcon = (ImageView) this.mRootView.findViewById(R.id.my_icon);
        this.myName = (TextView) this.mRootView.findViewById(R.id.my_name);
        this.mySteps = (TextView) this.mRootView.findViewById(R.id.step);
        this.mNumberTP = RunningApp.getCustomNumberFont();
        this.progress = (RelativeLayout) this.mRootView.findViewById(R.id.progress_bar);
        this.titleProgress = (ProgressBar) this.mRootView.findViewById(R.id.title_progress);
        this.mPageTitleContent = new ArrayList();
        String dayTitle = getContext().getResources().getString(R.string.dayRank);
        String weekTitle = getContext().getResources().getString(R.string.dayRank);
        String mouthTitle = getContext().getResources().getString(R.string.mouthRank);
        this.mPageTitleContent.add(mouthTitle);
        this.mPageTitleContent.add(dayTitle);
        this.mPageTitleContent.add(weekTitle);
        this.mPageTitleContent.add(mouthTitle);
        this.mPageTitleContent.add(dayTitle);
        this.mView0 = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.motion_rank_viewpager, null);
        this.mView1 = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.motion_rank_viewpager, null);
        this.mView2 = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.motion_rank_viewpager, null);
        this.mView3 = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.motion_rank_viewpager, null);
        this.mView4 = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.motion_rank_viewpager, null);
        this.isLogin = Tools.getLogin(getContext());
        if (this.isLogin) {
            this.noLogin.setVisibility(8);
            this.mSubFrame.setVisibility(0);
            return;
        }
        this.noLogin.setVisibility(0);
        this.mSubFrame.setVisibility(8);
    }

    private void InitListener() {
        this.myOnTouchListener = new C18921();
        this.mListViewScrollListener = new C13072();
        ((Main) getActivity()).registerMyOnTouchListener(this.myOnTouchListener);
    }

    private void setInitAdapter() {
        Log.d("yangyang", "setInitAdapter ");
        if (this.rAdapterDay == null) {
            this.rAdapterDay = new RankListAdapter(getContext());
        }
        this.rAdapterDay.refreshListInfo(this.highestStepList);
        if (this.rAdapterWeek == null) {
            this.rAdapterWeek = new RankListAdapter(getContext());
        }
        if (this.rAdapterMouth == null) {
            this.rAdapterMouth = new RankListAdapter(getContext());
        }
        this.list0 = (ListView) this.mView0.findViewById(R.id.moRankList);
        this.list0.setOnScrollListener(this.mListViewScrollListener);
        this.list0.setDivider(null);
        this.list0.setAdapter(this.rAdapterMouth);
        this.list1 = (ListView) this.mView1.findViewById(R.id.moRankList);
        this.list1.setOnScrollListener(this.mListViewScrollListener);
        this.list1.setDivider(null);
        this.list1.setAdapter(this.rAdapterDay);
        this.list2 = (ListView) this.mView2.findViewById(R.id.moRankList);
        this.list2.setOnScrollListener(this.mListViewScrollListener);
        this.list2.setDivider(null);
        this.list2.setAdapter(this.rAdapterWeek);
        this.list3 = (ListView) this.mView3.findViewById(R.id.moRankList);
        this.list3.setOnScrollListener(this.mListViewScrollListener);
        this.list3.setDivider(null);
        this.list3.setAdapter(this.rAdapterMouth);
        this.list4 = (ListView) this.mView4.findViewById(R.id.moRankList);
        this.list4.setOnScrollListener(this.mListViewScrollListener);
        this.list4.setDivider(null);
        this.list4.setAdapter(this.rAdapterDay);
        List<View> mAllRankViews = new ArrayList();
        mAllRankViews.add(this.mView1);
        this.currIndex = 1;
        if (this.rankListPA == null) {
            this.rankListPA = new RankListPageAdatper(mAllRankViews);
        } else {
            this.rankListPA.refresh(mAllRankViews);
        }
        this.viewPager.setAdapter(this.rankListPA);
        this.viewPager.setCurrentItem(this.currIndex);
        this.mIndicator.setViewPager(this.viewPager);
        this.mIndicator.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void setShowType(int position) {
        List<RankInfo> myRank = null;
        if (position == 1) {
            myRank = this.accountHighestData;
        }
        myRankInfo(myRank);
    }

    void myRankInfo(List<RankInfo> mList) {
        String openid = Tools.getOpenId(getContext());
        if (mList == null || mList.size() <= 0 || !((RankInfo) mList.get(0)).getAccountId().equals(openid)) {
            this.myRankLayout.setVisibility(8);
            this.noRankLayout.setVisibility(0);
            return;
        }
        this.myRankLayout.setVisibility(0);
        this.noRankLayout.setVisibility(8);
        RankInfo info = (RankInfo) mList.get(0);
        String rank = info.getRank() + "";
        String name = info.getName();
        String steps = info.getmSteps();
        int headId = info.getmImgId();
        if (headId == 10000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/custom");
            this.myIcon.setImageBitmap(this.bmp);
        } else if (headId == 1000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/logo");
            this.myIcon.setImageBitmap(this.bmp);
        } else {
            this.myIcon.setImageResource(Tools.selectByIndex(headId));
        }
        if (info.getRank() == 1) {
            this.myRankBg.setVisibility(0);
            this.myRank.setTextSize(38.0f);
            this.mySteps.setTextSize(38.0f);
            this.myRank.setTextColor(-1164520);
            this.mySteps.setTextColor(-1164520);
        }
        this.myRank.setTypeface(this.mNumberTP);
        this.mySteps.setTypeface(this.mNumberTP);
        this.myRank.setText(rank);
        this.myName.setText(name);
        this.mySteps.setText(steps);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_share:
                Intent intent = new Intent(getActivity(), ShareRankActivity.class);
                switch (this.currIndex) {
                    case 1:
                        intent.putExtra("type", 3);
                        if (this.highestStepList != null) {
                            intent.putExtra("RankList", this.highestStepList.toArray());
                        }
                        if (this.accountHighestData != null) {
                            intent.putExtra("MyRank", this.accountHighestData.toArray());
                            break;
                        }
                        break;
                }
                getActivity().startActivity(intent);
                return;
            default:
                return;
        }
    }

    private void initViewPagerScroll(ViewPager mViewPager) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(mViewPager, new ViewPagerScroller(mViewPager.getContext()));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e2) {
        } catch (IllegalAccessException e3) {
        }
    }

    public void UpdateRankView(List<RankInfo> msevenDalysList, List<RankInfo> mmouthDalysList, List<RankInfo> mhighestStepList, List<RankInfo> maccountServenData, List<RankInfo> maccountMouthData, List<RankInfo> maccountHighestData) {
        if (this.progress.getVisibility() == 0) {
            this.progress.setVisibility(8);
        }
        if (this.titleProgress.getVisibility() == 0) {
            this.titleProgress.setVisibility(8);
        }
        this.sevenDalysList = msevenDalysList;
        this.mouthDalysList = mmouthDalysList;
        this.highestStepList = mhighestStepList;
        this.accountServenData = maccountServenData;
        this.accountMouthData = maccountMouthData;
        this.accountHighestData = maccountHighestData;
        setInitAdapter();
        setShowType(this.currIndex);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }
}
