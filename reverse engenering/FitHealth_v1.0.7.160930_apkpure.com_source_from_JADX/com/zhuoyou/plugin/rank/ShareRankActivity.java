package com.zhuoyou.plugin.rank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI.WXApp;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.weibo.net.AsyncWeiboRunner.RequestListener;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.zhuoyou.plugin.running.BuildConfig;
import com.zhuoyou.plugin.running.CalTools;
import com.zhuoyou.plugin.running.PersonalConfig;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.SharePopupWindow;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.share.AuthorizeActivity;
import com.zhuoyou.plugin.share.ShareTask;
import com.zhuoyou.plugin.share.ShareToWeixin;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.protocol.HTTP;

public class ShareRankActivity extends Activity implements OnClickListener {
    private static final int TIMELINE_SUPPORTED_VERSION = 553779201;
    public static Handler mHandler = new C13245();
    private static int select = 0;
    private View appView1;
    private View appView2;
    private Bitmap bmp = null;
    private int cal = 0;
    private int distance;
    private boolean isQQInstalled = false;
    private boolean isWBInstalled = false;
    private boolean isWXInstalled = false;
    private OnClickListener itemsOnClick = new C13203();
    private ImageView left;
    OnClickListener leftOnclick = new C13192();
    private Context mContext;
    private TextView mData;
    private RankListAdapter mListAdapter = null;
    private ImageView mLogobg;
    private ImageView mMore;
    private TextView mNumTv;
    private Typeface mNumberTP;
    private TextView mPlace;
    private SharePopupWindow mPopupWindow;
    private RequestListener mRequestListener = new C18974();
    private RelativeLayout mScreenshot;
    private TextView mShare_cal;
    private TextView mShare_distance;
    private TextView mShare_food;
    private ImageView mShare_qq;
    private ImageView mShare_quan;
    private TextView mShare_step;
    private ImageView mShare_weixin;
    private ImageView mTargetState;
    private ImageView mTypeIcon;
    private ImageView mUser_logo;
    private TextView mUser_name;
    private ListView moRankList;
    private TextView myData;
    private ImageView myLogobg;
    private ImageView myMore;
    private TextView myNumTv;
    private ImageView myOptimal;
    private ImageView myOptimal2;
    private TextView myPlace;
    private List<RankInfo> myRank = new ArrayList();
    private ScrollView myScreenshot;
    private ImageView myShare_qq;
    private ImageView myShare_quan;
    private ImageView myShare_weixin;
    private ImageView myTypeIcon;
    private ImageView myUser_logo;
    private TextView myUser_name;
    private List<RankInfo> rankList = new ArrayList();
    private ImageView right;
    OnClickListener rightOnclick = new C13181();
    private int type;
    private UMShareListener umShareListener = new C18986();
    private List<View> viewGroup = new ArrayList();
    private ViewPager viewPager;
    private Weibo weibo = Weibo.getInstance();

    class C13181 implements OnClickListener {
        C13181() {
        }

        public void onClick(View v) {
            ShareRankActivity.this.viewPager.setCurrentItem(ShareRankActivity.this.viewPager.getCurrentItem() + 1, true);
        }
    }

    class C13192 implements OnClickListener {
        C13192() {
        }

        public void onClick(View v) {
            ShareRankActivity.this.viewPager.setCurrentItem(ShareRankActivity.this.viewPager.getCurrentItem() - 1, true);
        }
    }

    class C13203 implements OnClickListener {
        C13203() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.share_weibo:
                    if (ShareRankActivity.this.weibo.isSessionValid()) {
                        ShareRankActivity.select = 1;
                        SharePopupWindow.mInstance.getWeiboView().setImageResource(R.drawable.share_wb_select);
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClass(ShareRankActivity.this, AuthorizeActivity.class);
                    ShareRankActivity.this.startActivity(intent);
                    return;
                case R.id.share:
                    if (ShareRankActivity.select <= 0) {
                        Toast.makeText(ShareRankActivity.this.mContext, R.string.select_platform, 0).show();
                        return;
                    } else if (SharePopupWindow.mInstance != null) {
                        ShareRankActivity.this.share2weibo(SharePopupWindow.mInstance.getShareContent(), Tools.getScreenShot(SharePopupWindow.mInstance.getShareFileName()));
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    static class C13245 extends Handler {
        C13245() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ShareRankActivity.select = 1;
                    return;
                default:
                    return;
            }
        }
    }

    class C18974 implements RequestListener {

        class C13211 implements Runnable {
            C13211() {
            }

            public void run() {
                Toast.makeText(ShareRankActivity.this, R.string.share_success, 0).show();
            }
        }

        C18974() {
        }

        public void onComplete(String response) {
            ShareRankActivity.this.runOnUiThread(new C13211());
        }

        public void onError(final WeiboException e) {
            ShareRankActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(ShareRankActivity.this, e.getStatusMessage(), 0).show();
                }
            });
        }

        public void onIOException(final IOException e) {
            ShareRankActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(ShareRankActivity.this, e.getMessage(), 0).show();
                }
            });
        }
    }

    class C18986 implements UMShareListener {
        C18986() {
        }

        public void onResult(SHARE_MEDIA platform) {
        }

        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        public void onCancel(SHARE_MEDIA platform) {
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
        setContentView(R.layout.share_rank);
        this.mContext = this;
        Intent intent = getIntent();
        if (intent != null && Tools.getLogin(this.mContext)) {
            this.type = intent.getIntExtra("type", 0);
            Object[] objs = (Object[]) intent.getSerializableExtra("RankList");
            if (objs != null) {
                for (Object obj : objs) {
                    this.rankList.add((RankInfo) obj);
                }
            }
            Object[] objs2 = (Object[]) intent.getSerializableExtra("MyRank");
            if (objs2 != null) {
                for (Object obj2 : objs2) {
                    this.myRank.add((RankInfo) obj2);
                }
            }
        }
        this.viewPager = (ViewPager) findViewById(R.id.share_viewpage);
        getLayoutInflater();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        this.appView1 = layoutInflater.inflate(R.layout.share_rank_list, null);
        this.appView2 = layoutInflater.inflate(R.layout.share_myrank, null);
        this.viewGroup.add(this.appView1);
        this.viewGroup.add(this.appView2);
        this.viewPager.setAdapter(new MyPagerAdapter(this.viewGroup));
        this.mNumberTP = RunningApp.getCustomNumberFont();
        initView();
        initData(this.rankList, this.myRank);
        getShareAppStatus();
    }

    void initData(List<RankInfo> mList, List<RankInfo> list) {
        if (mList != null) {
            this.mListAdapter = new RankListAdapter(this);
            this.mListAdapter.refreshListInfo(mList);
            this.moRankList.setDivider(null);
            this.moRankList.setAdapter(this.mListAdapter);
        }
    }

    private void initView() {
        this.mScreenshot = (RelativeLayout) this.appView1.findViewById(R.id.screenshot);
        this.myScreenshot = (ScrollView) this.appView2.findViewById(R.id.screenshot);
        String str = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        this.mData = (TextView) this.appView1.findViewById(R.id.data);
        this.myData = (TextView) this.appView2.findViewById(R.id.data);
        this.mData.setText(str);
        this.myData.setText(str);
        this.mTypeIcon = (ImageView) this.appView1.findViewById(R.id.typeIcon);
        this.myTypeIcon = (ImageView) this.appView2.findViewById(R.id.typeIcon);
        if (this.type != 0) {
            switch (this.type) {
                case 1:
                    this.mTypeIcon.setImageResource(R.drawable.week_rank_icon);
                    this.myTypeIcon.setImageResource(R.drawable.week_rank_icon);
                    break;
                case 2:
                    this.mTypeIcon.setImageResource(R.drawable.mouth_rank_icon);
                    this.myTypeIcon.setImageResource(R.drawable.mouth_rank_icon);
                    break;
                case 3:
                    this.mTypeIcon.setImageResource(R.drawable.day_rank_icon);
                    this.myTypeIcon.setImageResource(R.drawable.day_rank_icon);
                    break;
            }
        }
        this.mUser_logo = (ImageView) this.appView1.findViewById(R.id.user_logo);
        this.myUser_logo = (ImageView) this.appView2.findViewById(R.id.user_logo);
        int headIndex = Tools.getHead(this);
        if (headIndex == 10000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/custom");
            this.mUser_logo.setImageBitmap(this.bmp);
            this.myUser_logo.setImageBitmap(this.bmp);
        } else if (headIndex == 1000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/logo");
            this.mUser_logo.setImageBitmap(this.bmp);
            this.myUser_logo.setImageBitmap(this.bmp);
        } else {
            this.mUser_logo.setImageResource(Tools.selectByIndex(headIndex));
            this.myUser_logo.setImageResource(Tools.selectByIndex(headIndex));
        }
        this.mUser_name = (TextView) this.appView1.findViewById(R.id.user_name);
        this.myUser_name = (TextView) this.appView2.findViewById(R.id.user_name);
        if (!Tools.getUsrName(this).equals("")) {
            this.mUser_name.setText(Tools.getUsrName(this));
            this.myUser_name.setText(Tools.getUsrName(this));
        } else if (Tools.getLoginName(this).equals("")) {
            this.mUser_name.setText(R.string.username);
            this.myUser_name.setText(R.string.username);
        } else {
            this.mUser_name.setText(Tools.getLoginName(this));
            this.myUser_name.setText(Tools.getLoginName(this));
        }
        this.mPlace = (TextView) this.appView1.findViewById(R.id.place);
        this.myPlace = (TextView) this.appView2.findViewById(R.id.place);
        this.mNumTv = (TextView) this.appView1.findViewById(R.id.num_tv);
        this.myNumTv = (TextView) this.appView2.findViewById(R.id.num_tv);
        this.mPlace.setTypeface(this.mNumberTP);
        this.myPlace.setTypeface(this.mNumberTP);
        int place = 0;
        int steps = 0;
        if (this.myRank.size() > 0) {
            place = ((RankInfo) this.myRank.get(0)).getRank();
            steps = Integer.parseInt(((RankInfo) this.myRank.get(0)).getmSteps());
            this.mPlace.setText(place + "");
            this.myPlace.setText(place + "");
        } else {
            this.mPlace.setText("");
            this.myPlace.setText("");
            this.mNumTv.setText(R.string.no_place);
            this.myNumTv.setText(R.string.no_place);
        }
        this.mTargetState = (ImageView) this.appView2.findViewById(R.id.target_state);
        this.mLogobg = (ImageView) this.appView1.findViewById(R.id.user_logo_bg);
        this.myLogobg = (ImageView) this.appView2.findViewById(R.id.user_logo_bg);
        this.myOptimal = (ImageView) this.appView2.findViewById(R.id.optimal_icon);
        this.myOptimal2 = (ImageView) this.appView2.findViewById(R.id.optimal_icon2);
        switch (place) {
            case 1:
                this.mLogobg.setImageResource(R.drawable.head_decoration_top1);
                this.myLogobg.setImageResource(R.drawable.head_decoration_top1);
                this.mTargetState.setImageResource(R.drawable.share_rank_top);
                this.myOptimal.setVisibility(0);
                this.myOptimal2.setVisibility(0);
                break;
            case 2:
                this.mLogobg.setImageResource(R.drawable.head_decoration_top2);
                this.myLogobg.setImageResource(R.drawable.head_decoration_top2);
                this.mTargetState.setImageResource(R.drawable.share_rank_top);
                this.myOptimal.setVisibility(0);
                this.myOptimal2.setVisibility(0);
                break;
            case 3:
                this.mLogobg.setImageResource(R.drawable.head_decoration_top3);
                this.myLogobg.setImageResource(R.drawable.head_decoration_top3);
                this.mTargetState.setImageResource(R.drawable.share_rank_top);
                this.myOptimal.setVisibility(0);
                this.myOptimal2.setVisibility(0);
                break;
            default:
                this.mLogobg.setImageResource(R.drawable.head_decoration);
                this.myLogobg.setImageResource(R.drawable.head_decoration);
                this.mTargetState.setImageResource(R.drawable.share_rank_back);
                this.myOptimal.setVisibility(8);
                this.myOptimal2.setVisibility(8);
                break;
        }
        this.right = (ImageView) this.appView1.findViewById(R.id.next);
        this.right.setOnClickListener(this.rightOnclick);
        this.left = (ImageView) this.appView2.findViewById(R.id.left);
        this.left.setOnClickListener(this.leftOnclick);
        this.moRankList = (ListView) this.appView1.findViewById(R.id.moListRank);
        this.mShare_step = (TextView) this.appView2.findViewById(R.id.share_step);
        this.mShare_step.setText(steps + "");
        this.mShare_step.setTypeface(this.mNumberTP);
        PersonalConfig config = Tools.getPersonalConfig();
        this.distance = Tools.calcDistance(steps, config.getHeight());
        this.mShare_distance = (TextView) this.appView2.findViewById(R.id.share_distance);
        this.mShare_distance.setText(getResources().getString(R.string.walk) + " " + String.valueOf(getKilometer(this.distance)) + " " + getResources().getString(R.string.kilometre));
        this.mShare_cal = (TextView) this.appView2.findViewById(R.id.share_cal);
        this.cal = Tools.calcCalories(this.distance, config.getWeightNum());
        this.mShare_cal.setText(this.cal + "");
        this.mShare_cal.setTypeface(this.mNumberTP);
        this.mShare_food = (TextView) this.appView2.findViewById(R.id.share_food);
        this.mShare_food.setText("≈" + CalTools.getResultFromCal(this.mContext, this.cal));
        this.mShare_weixin = (ImageView) this.appView1.findViewById(R.id.share_weixin);
        this.mShare_weixin.setOnClickListener(this);
        this.mShare_quan = (ImageView) this.appView1.findViewById(R.id.share_quan);
        this.mShare_quan.setOnClickListener(this);
        this.mShare_qq = (ImageView) this.appView1.findViewById(R.id.share_qq);
        this.mShare_qq.setOnClickListener(this);
        this.mMore = (ImageView) this.appView1.findViewById(R.id.share_more);
        this.mMore.setOnClickListener(this);
        this.myShare_weixin = (ImageView) this.appView2.findViewById(R.id.share_my_weixin);
        this.myShare_weixin.setOnClickListener(this);
        this.myShare_quan = (ImageView) this.appView2.findViewById(R.id.share_my_quan);
        this.myShare_quan.setOnClickListener(this);
        this.myShare_qq = (ImageView) this.appView2.findViewById(R.id.share_my_qq);
        this.myShare_qq.setOnClickListener(this);
        this.myMore = (ImageView) this.appView2.findViewById(R.id.share_my_more);
        this.myMore.setOnClickListener(this);
    }

    private float getKilometer(int meter) {
        return new BigDecimal((double) ((float) (meter / 1000))).setScale(2, 4).floatValue();
    }

    private void getScreenShot(View mScreenshot, String fileName) {
        int h = mScreenshot.getHeight();
        if (mScreenshot instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) mScreenshot;
            h = 0;
            for (int i = 0; i < scrollView.getChildCount(); i++) {
                h += scrollView.getChildAt(i).getHeight();
            }
        }
        Bitmap bmp = Bitmap.createBitmap(mScreenshot.getWidth(), h, Config.RGB_565);
        mScreenshot.draw(new Canvas(bmp));
        Tools.saveBitmapToFile(bmp, fileName);
        bmp.recycle();
    }

    public void onClick(View v) {
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())) + ".jpg";
        int item = this.viewPager.getCurrentItem();
        Uri uri;
        Intent intent;
        switch (v.getId()) {
            case R.id.share_weixin:
                if (item == 0) {
                    getScreenShot(this.mScreenshot, fileName);
                } else if (item == 1) {
                    getScreenShot(this.myScreenshot, fileName);
                }
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.FACEBOOK).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
                    return;
                } else if (!this.isWXInstalled) {
                    Toast.makeText(this.mContext, R.string.install_weixin, 0).show();
                    return;
                } else if (ShareToWeixin.api.isWXAppSupportAPI()) {
                    ShareToWeixin.SharetoWX(this.mContext, false, fileName);
                    return;
                } else {
                    Toast.makeText(this.mContext, R.string.weixin_no_support, 0).show();
                    return;
                }
            case R.id.share_quan:
                if (item == 0) {
                    getScreenShot(this.mScreenshot, fileName);
                } else if (item == 1) {
                    getScreenShot(this.myScreenshot, fileName);
                }
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    try {
                        getPackageManager().getPackageInfo("com.twitter.android", 0);
                        uri = Uri.fromFile(new File(Tools.getScreenShot(fileName)));
                        intent = new Intent("android.intent.action.SEND");
                        intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
                        intent.setType("image/jpeg");
                        intent.putExtra("android.intent.extra.STREAM", uri);
                        startActivity(intent);
                        return;
                    } catch (Exception e) {
                        Toast.makeText(this, "TPlease install twitter client", 1).show();
                        e.printStackTrace();
                        return;
                    }
                } else if (!this.isWXInstalled) {
                    Toast.makeText(this.mContext, R.string.install_weixin, 0).show();
                    return;
                } else if (ShareToWeixin.api.getWXAppSupportAPI() >= 553779201) {
                    ShareToWeixin.SharetoWX(this.mContext, true, fileName);
                    return;
                } else {
                    Toast.makeText(this.mContext, R.string.weixin_no_support_quan, 0).show();
                    return;
                }
            case R.id.share_qq:
                if (item == 0) {
                    getScreenShot(this.mScreenshot, fileName);
                } else if (item == 1) {
                    getScreenShot(this.myScreenshot, fileName);
                }
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.WHATSAPP).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
                    return;
                } else if (this.isQQInstalled) {
                    shareToQQ(fileName);
                    return;
                } else {
                    Toast.makeText(this.mContext, R.string.install_qq, 0).show();
                    return;
                }
            case R.id.share_more:
                if (item == 0) {
                    getScreenShot(this.mScreenshot, fileName);
                } else if (item == 1) {
                    getScreenShot(this.myScreenshot, fileName);
                }
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.INSTAGRAM).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
                    break;
                }
                this.mPopupWindow = new SharePopupWindow(this, this.itemsOnClick, fileName);
                this.mPopupWindow.setInputMethodMode(1);
                this.mPopupWindow.setSoftInputMode(16);
                this.mPopupWindow.showAtLocation(findViewById(R.id.main), 81, 0, 0);
                return;
            case R.id.img_back:
                finish();
                return;
            case R.id.share_my_weixin:
                break;
            case R.id.share_my_quan:
                if (item == 0) {
                    getScreenShot(this.mScreenshot, fileName);
                } else if (item == 1) {
                    getScreenShot(this.myScreenshot, fileName);
                }
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    try {
                        getPackageManager().getPackageInfo("com.twitter.android", 0);
                        uri = Uri.fromFile(new File(Tools.getScreenShot(fileName)));
                        intent = new Intent("android.intent.action.SEND");
                        intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
                        intent.setType("image/jpeg");
                        intent.putExtra("android.intent.extra.STREAM", uri);
                        startActivity(intent);
                        return;
                    } catch (Exception e2) {
                        Toast.makeText(this, "Please install twitter client", 1).show();
                        e2.printStackTrace();
                        return;
                    }
                } else if (!this.isWXInstalled) {
                    Toast.makeText(this.mContext, R.string.install_weixin, 0).show();
                    return;
                } else if (ShareToWeixin.api.getWXAppSupportAPI() >= 553779201) {
                    ShareToWeixin.SharetoWX(this.mContext, true, fileName);
                    return;
                } else {
                    Toast.makeText(this.mContext, R.string.weixin_no_support_quan, 0).show();
                    return;
                }
            case R.id.share_my_qq:
                if (item == 0) {
                    getScreenShot(this.mScreenshot, fileName);
                } else if (item == 1) {
                    getScreenShot(this.myScreenshot, fileName);
                }
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.WHATSAPP).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
                    return;
                } else if (this.isQQInstalled) {
                    shareToQQ(fileName);
                    return;
                } else {
                    Toast.makeText(this.mContext, R.string.install_qq, 0).show();
                    return;
                }
            case R.id.share_my_more:
                if (item == 0) {
                    getScreenShot(this.mScreenshot, fileName);
                } else if (item == 1) {
                    getScreenShot(this.myScreenshot, fileName);
                }
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.INSTAGRAM).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
                    return;
                }
                this.mPopupWindow = new SharePopupWindow(this, this.itemsOnClick, fileName);
                this.mPopupWindow.setInputMethodMode(1);
                this.mPopupWindow.setSoftInputMode(16);
                this.mPopupWindow.showAtLocation(findViewById(R.id.main), 81, 0, 0);
                return;
            default:
                return;
        }
        if (item == 0) {
            getScreenShot(this.mScreenshot, fileName);
        } else if (item == 1) {
            getScreenShot(this.myScreenshot, fileName);
        }
        if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
            new ShareAction(this).setPlatform(SHARE_MEDIA.FACEBOOK).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
        } else if (!this.isWXInstalled) {
            Toast.makeText(this.mContext, R.string.install_weixin, 0).show();
        } else if (ShareToWeixin.api.isWXAppSupportAPI()) {
            ShareToWeixin.SharetoWX(this.mContext, false, fileName);
        } else {
            Toast.makeText(this.mContext, R.string.weixin_no_support, 0).show();
        }
    }

    private void getShareAppStatus() {
        PackageManager pm = getPackageManager();
        Intent filterIntent = new Intent("android.intent.action.SEND", null);
        filterIntent.addCategory("android.intent.category.DEFAULT");
        filterIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        List<ResolveInfo> resolveInfos = new ArrayList();
        resolveInfos.addAll(pm.queryIntentActivities(filterIntent, 0));
        for (int i = 0; i < resolveInfos.size(); i++) {
            String mPackageName = ((ResolveInfo) resolveInfos.get(i)).activityInfo.packageName;
            if (mPackageName.equals(WXApp.WXAPP_PACKAGE_NAME)) {
                this.isWXInstalled = true;
            }
            if (mPackageName.equals("com.sina.weibo")) {
                this.isWBInstalled = true;
            }
            if (mPackageName.equals(Constants.MOBILEQQ_PACKAGE_NAME)) {
                this.isQQInstalled = true;
            }
        }
    }

    private void shareToQQ(String fileName) {
        ComponentName cp = new ComponentName(Constants.MOBILEQQ_PACKAGE_NAME, "com.tencent.mobileqq.activity.JumpActivity");
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setComponent(cp);
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(Tools.getScreenShot(fileName))));
        startActivity(intent);
    }

    private void share2weibo(String content, String picpath) {
        new Thread(new ShareTask(this, picpath, content, this.mRequestListener)).start();
        if (this.mPopupWindow.isShowing()) {
            this.mPopupWindow.dismiss();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.bmp != null) {
            this.bmp.recycle();
            this.bmp = null;
            System.gc();
        }
    }
}
