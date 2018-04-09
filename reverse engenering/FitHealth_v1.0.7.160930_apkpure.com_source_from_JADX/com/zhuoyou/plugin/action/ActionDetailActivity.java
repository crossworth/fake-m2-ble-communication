package com.zhuoyou.plugin.action;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyou.plugin.cloud.GetDataFromNet;
import com.zhuoyou.plugin.cloud.NetMsgCode;
import com.zhuoyou.plugin.cloud.NetUtils;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.util.HashMap;
import java.util.List;

public class ActionDetailActivity extends FragmentActivity {
    static final int NUM_ITEMS = 4;
    private String ActionTitle = null;
    private String actionId = "";
    private ActionInfo actionInfo;
    private String action_flag = "0";
    private RelativeLayout back;
    private int bmpWidth;
    private int currIndex;
    private ImageView cursor;
    private TextView join_action;
    private List<ActionPannelItemInfo> listPannelItem;
    private List<ActionRankingItemInfo> listRank;
    private Context mContext = RunningApp.getInstance().getApplicationContext();
    MyAdapter mPageAdaptor;
    private ViewPager mPager;
    private CacheTool mcachetool;
    private Handler mhandler = new C10863();
    private TextView mtitle;
    private ActionRankingItemInfo myRank = new ActionRankingItemInfo();
    private int offset;
    private String openId;
    HashMap<String, String> params = new HashMap();
    private int result = -1;
    private TextView view1;
    private TextView view2;
    private TextView view3;
    private TextView view4;

    class C10841 implements OnClickListener {
        C10841() {
        }

        public void onClick(View v) {
            ActionDetailActivity.this.finish();
        }
    }

    class C10852 implements OnClickListener {
        C10852() {
        }

        public void onClick(View v) {
            if (!ActionDetailActivity.this.action_flag.equals("2") && ActionDetailActivity.this.actionInfo.GetIsJoin() != 1) {
                if (NetUtils.getAPNType(ActionDetailActivity.this.mContext) == -1) {
                    Toast.makeText(ActionDetailActivity.this.mContext, R.string.check_network, 0).show();
                } else if (ActionDetailActivity.this.openId == null || ActionDetailActivity.this.openId.equals("")) {
                    Toast.makeText(ActionDetailActivity.this.mContext, R.string.login_before_lookup, 0).show();
                } else {
                    new GetDataFromNet(NetMsgCode.ACTION_JOIN, ActionDetailActivity.this.mhandler, ActionDetailActivity.this.params, ActionDetailActivity.this.mContext).execute(new Object[]{NetMsgCode.URL});
                }
            }
        }
    }

    class C10863 extends Handler {
        C10863() {
        }

        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    switch (msg.arg1) {
                        case NetMsgCode.ACTION_GET_MSG /*100012*/:
                            if (ActionDetailActivity.this.mcachetool != null) {
                                ActionDetailActivity.this.mcachetool.SaveMsgList((List) msg.obj);
                                break;
                            }
                            break;
                        case NetMsgCode.ACTION_JOIN /*100013*/:
                            if (ActionDetailActivity.this.mcachetool != null) {
                                if (((Integer) msg.obj).intValue() != 0) {
                                    Toast.makeText(ActionDetailActivity.this.mContext, R.string.running_action_apply, 1).show();
                                    ActionDetailActivity.this.join_action.setText(R.string.running_action_join);
                                    break;
                                }
                                ActionDetailActivity.this.join_action.setText(R.string.running_action_join_in);
                                break;
                            }
                            break;
                        case NetMsgCode.ACTION_GET_IDINFO /*100014*/:
                            if (ActionDetailActivity.this.mcachetool != null) {
                                ActionDetailActivity.this.mcachetool.SaveActionInfo((ActionInfo) msg.obj);
                                ActionDetailActivity.this.actionInfo = (ActionInfo) msg.obj;
                                ActionDetailActivity.this.mPageAdaptor.notifyDataSetChanged(ActionDetailActivity.this.actionInfo);
                                Log.d("zzb", "action in join flag =" + ActionDetailActivity.this.actionInfo.GetIsJoin());
                                int result = ActionDetailActivity.this.actionInfo.GetIsJoin();
                                if (!ActionDetailActivity.this.action_flag.equals("2")) {
                                    if (result != 1) {
                                        ActionDetailActivity.this.join_action.setText(R.string.running_action_join);
                                        break;
                                    } else {
                                        ActionDetailActivity.this.join_action.setText(R.string.running_action_join_in);
                                        break;
                                    }
                                }
                                ActionDetailActivity.this.join_action.setText(R.string.ended);
                                break;
                            }
                            break;
                        default:
                            break;
                    }
                case 404:
                    Toast.makeText(ActionDetailActivity.this.getApplicationContext(), R.string.network_link_failure, 0).show();
                    break;
            }
            super.dispatchMessage(msg);
        }
    }

    public class txListener implements OnClickListener {
        private int index = 0;

        public txListener(int i) {
            this.index = i;
        }

        public void onClick(View v) {
            ActionDetailActivity.this.mPager.setCurrentItem(this.index);
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {
        private int one = ((ActionDetailActivity.this.offset * 2) + ActionDetailActivity.this.bmpWidth);

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageSelected(int arg0) {
            Animation animation = new TranslateAnimation((float) (ActionDetailActivity.this.currIndex * this.one), (float) (this.one * arg0), 0.0f, 0.0f);
            ActionDetailActivity.this.currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(200);
            ActionDetailActivity.this.cursor.startAnimation(animation);
        }
    }

    public class MyAdapter extends FragmentStatePagerAdapter {
        private ActionInfo mactioninfo;
        public SparseArray<Fragment> registeredFragments = new SparseArray();

        public void notifyDataSetChanged(ActionInfo actioninfo) {
            this.mactioninfo = actioninfo;
            super.notifyDataSetChanged();
        }

        public MyAdapter(FragmentManager fm, ActionInfo actioninfo) {
            super(fm);
            this.mactioninfo = actioninfo;
        }

        public int getCount() {
            return 4;
        }

        public Fragment getItem(int position) {
            Fragment mfragment;
            Bundle bundle;
            switch (position) {
                case 0:
                    mfragment = new ActionRuleFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("actioninfo", this.mactioninfo);
                    bundle.putInt("position", position);
                    mfragment.setArguments(bundle);
                    return mfragment;
                case 1:
                    mfragment = new ActionRuleFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("actioninfo", this.mactioninfo);
                    bundle.putInt("position", position);
                    mfragment.setArguments(bundle);
                    return mfragment;
                case 2:
                    mfragment = new ActionRankFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("actioninfo", this.mactioninfo);
                    mfragment.setArguments(bundle);
                    return mfragment;
                case 3:
                    mfragment = new ActionNoticeFragment();
                    bundle = new Bundle();
                    bundle.putSerializable("actioninfo", this.mactioninfo);
                    bundle.putInt("position", position - 1);
                    bundle.putString(Tools.SP_SPP_FLAG_KEY_FLAG, ActionDetailActivity.this.action_flag);
                    mfragment.setArguments(bundle);
                    return mfragment;
                default:
                    return null;
            }
        }

        public int getItemPosition(Object object) {
            return -2;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            Fragment localFragment = (Fragment) super.instantiateItem(container, position);
            this.registeredFragments.put(position, localFragment);
            return localFragment;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("position Destory" + position);
            this.registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_action);
        this.mcachetool = ((RunningApp) getApplication()).GetCacheTool();
        Intent intent = getIntent();
        this.actionId = intent.getStringExtra("id");
        this.ActionTitle = intent.getStringExtra("action_title");
        this.action_flag = intent.getStringExtra("action_flag");
        this.openId = Tools.getOpenId(this);
        initView();
        initImage();
        initViewPager();
    }

    protected void onResume() {
        super.onResume();
        this.openId = Tools.getOpenId(this);
        if (NetUtils.getAPNType(this.mContext) == -1) {
            Toast.makeText(this.mContext, R.string.check_network, 0).show();
            return;
        }
        this.params.put("actId", this.actionId);
        this.params.put("openId", this.openId);
        this.params.put("lcd", GetLcdInfo());
        new GetDataFromNet(NetMsgCode.ACTION_GET_IDINFO, this.mhandler, this.params, this.mContext).execute(new Object[]{NetMsgCode.URL});
    }

    public void initView() {
        this.join_action = (TextView) findViewById(R.id.join_action);
        this.mtitle = (TextView) findViewById(R.id.title);
        if (!(this.ActionTitle == null || this.ActionTitle.equals(""))) {
            this.mtitle.setText(this.ActionTitle);
        }
        this.view1 = (TextView) findViewById(R.id.intro);
        this.view2 = (TextView) findViewById(R.id.rule);
        this.view3 = (TextView) findViewById(R.id.rank);
        this.view4 = (TextView) findViewById(R.id.notice);
        this.view1.setOnClickListener(new txListener(0));
        this.view2.setOnClickListener(new txListener(1));
        this.view3.setOnClickListener(new txListener(2));
        this.view4.setOnClickListener(new txListener(3));
        this.cursor = (ImageView) findViewById(R.id.cursor);
        this.back = (RelativeLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new C10841());
        this.join_action.setOnClickListener(new C10852());
    }

    public void initImage() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        this.bmpWidth = screenW / 4;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_line);
        this.cursor.setImageBitmap(BitMapTools.zoomImg(bitmap, this.bmpWidth, bitmap.getHeight()));
        this.offset = ((screenW / 4) - this.bmpWidth) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate((float) this.offset, 0.0f);
        this.cursor.setImageMatrix(matrix);
    }

    public void initViewPager() {
        this.mPager = (ViewPager) findViewById(R.id.viewPager);
        this.mPageAdaptor = new MyAdapter(getSupportFragmentManager(), this.actionInfo);
        this.mPager.setAdapter(this.mPageAdaptor);
        this.mPager.setCurrentItem(0);
        this.mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    public String GetLcdInfo() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth + "x" + dm.heightPixels;
    }
}
