package com.zhuoyou.plugin.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyou.plugin.action.PullToRefreshBase.OnRefreshListener;
import com.zhuoyou.plugin.cloud.GetDataFromNet;
import com.zhuoyou.plugin.cloud.NetMsgCode;
import com.zhuoyou.plugin.cloud.NetUtils;
import com.zhuoyou.plugin.running.Main;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ActionActivity extends Activity implements OnItemClickListener {
    private boolean Is_From_Welcome = false;
    private LinearLayout action_container;
    private int leastId = -1;
    private ActionAdaptor mAdapter;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private ListView mListView;
    private PullToRefreshListView mPullListView;
    private CacheTool mcachetool;
    private Handler mhandler = new C10833();
    private List<ActionListItemInfo> tmp_list;

    class C10821 implements OnClickListener {
        C10821() {
        }

        public void onClick(View v) {
            if (ActionActivity.this.Is_From_Welcome) {
                ActionActivity.this.startActivity(new Intent(ActionActivity.this, Main.class));
            }
            ActionActivity.this.finish();
        }
    }

    class C10833 extends Handler {
        C10833() {
        }

        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    switch (msg.arg1) {
                        case NetMsgCode.ACTION_GET_MSG /*100012*/:
                            if (ActionActivity.this.mcachetool != null) {
                                ActionActivity.this.mcachetool.SaveMsgList(msg.obj);
                                break;
                            }
                            break;
                        case NetMsgCode.ACTION_JOIN /*100013*/:
                            break;
                        case NetMsgCode.ACTION_GET_IDINFO /*100014*/:
                            if (ActionActivity.this.mcachetool != null) {
                                ActionActivity.this.mcachetool.SaveActionInfo((List) msg.obj);
                                break;
                            }
                            break;
                        case NetMsgCode.ACTION_GET_NEXTPAGE /*100015*/:
                            ActionActivity.this.tmp_list = (List) msg.obj;
                            if (ActionActivity.this.tmp_list.size() == 0) {
                                Toast.makeText(ActionActivity.this, R.string.act_text, 0).show();
                                ActionActivity.this.mPullListView.setScrollLoadEnabled(false);
                            } else {
                                ActionActivity.this.mPullListView.setScrollLoadEnabled(true);
                            }
                            ActionActivity.this.mAdapter.AddListitem(ActionActivity.this.tmp_list);
                            ActionActivity.this.mAdapter.notifyDataSetChanged();
                            ActionActivity.this.mPullListView.onPullDownRefreshComplete();
                            ActionActivity.this.mPullListView.onPullUpRefreshComplete();
                            ActionActivity.this.mPullListView.setHasMoreData(true);
                            if (ActionActivity.this.mcachetool != null) {
                                ActionActivity.this.mcachetool.SaveActionListItem(ActionActivity.this.tmp_list);
                                break;
                            }
                            break;
                        case NetMsgCode.ACTION_GET_REFRESHPAGE /*100016*/:
                            ActionActivity.this.tmp_list = (List) msg.obj;
                            ActionActivity.this.mAdapter.SetMyListItem(ActionActivity.this.tmp_list);
                            ActionActivity.this.mAdapter.notifyDataSetChanged();
                            ActionActivity.this.mPullListView.onPullDownRefreshComplete();
                            ActionActivity.this.mPullListView.onPullUpRefreshComplete();
                            ActionActivity.this.mPullListView.setHasMoreData(true);
                            if (ActionActivity.this.mcachetool != null) {
                                ActionActivity.this.mcachetool.ClearListItem();
                                ActionActivity.this.mcachetool.SaveActionListItem(ActionActivity.this.tmp_list);
                            }
                            ActionActivity.this.setLastUpdateTime();
                            break;
                        default:
                            break;
                    }
                case 404:
                    ActionActivity.this.mPullListView.onPullDownRefreshComplete();
                    ActionActivity.this.mPullListView.onPullUpRefreshComplete();
                    ActionActivity.this.mPullListView.setHasMoreData(true);
                    break;
            }
            super.dispatchMessage(msg);
        }
    }

    class C18572 implements OnRefreshListener<ListView> {
        C18572() {
        }

        public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            ActionActivity.this.StartRefreshTask(0);
        }

        public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            ActionActivity.this.StartRefreshTask(1);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_layout);
        this.Is_From_Welcome = getIntent().getBooleanExtra("is_from_welcome", false);
        this.mcachetool = ((RunningApp) getApplication()).GetCacheTool();
        InitView();
    }

    private void InitView() {
        ((TextView) findViewById(R.id.title)).setText(R.string.running_event);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C10821());
        this.action_container = (LinearLayout) findViewById(R.id.action_container);
        this.mPullListView = new PullToRefreshListView(this);
        this.action_container.addView(this.mPullListView, new LayoutParams(-1, -1));
        this.mPullListView.setPullLoadEnabled(false);
        this.mPullListView.setScrollLoadEnabled(true);
        this.mListView = (ListView) this.mPullListView.getRefreshableView();
        this.mListView.setOverScrollMode(2);
        this.mListView.setDividerHeight(0);
        this.mAdapter = new ActionAdaptor(this, this.mListView, this.mcachetool);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnItemClickListener(this);
        this.mPullListView.setOnRefreshListener(new C18572());
        setLastUpdateTime();
        this.mPullListView.doPullRefreshing(true, 500);
    }

    public void StartRefreshTask(int type) {
        int msg_code = 0;
        if (NetUtils.getAPNType(this) == -1) {
            this.mPullListView.onPullDownRefreshComplete();
            this.mPullListView.onPullUpRefreshComplete();
            Toast.makeText(this, R.string.check_network, 0).show();
            this.mPullListView.setPullRefreshEnabled(false);
            this.mPullListView.setScrollLoadEnabled(false);
            return;
        }
        HashMap<String, String> params = new HashMap();
        switch (type) {
            case 0:
                params.put("type", "0");
                params.put("actId", "0");
                msg_code = NetMsgCode.ACTION_GET_REFRESHPAGE;
                break;
            case 1:
                params.put("type", "1");
                if (this.mcachetool != null) {
                    int id = this.mcachetool.GetLocalLittleIdofActionitem();
                    params.put("actId", String.valueOf(id));
                    this.leastId = id;
                } else {
                    params.put("actId", "14");
                }
                msg_code = NetMsgCode.ACTION_GET_NEXTPAGE;
                break;
        }
        params.put("lcd", GetLcdInfo());
        new GetDataFromNet(msg_code, this.mhandler, params, this).execute(new Object[]{NetMsgCode.URL});
    }

    private void setLastUpdateTime() {
        this.mPullListView.setLastUpdatedLabel(formatDateTime(System.currentTimeMillis()));
    }

    private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }
        return this.mDateFormat.format(new Date(time));
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent mintent = new Intent(this, ActionDetailActivity.class);
        int index = (int) this.mAdapter.getItemId(position);
        if (index != -1) {
            String title = ((ActionListItemInfo) this.mAdapter.getItem(position)).GetActivtyTitle();
            String startTime = ((ActionListItemInfo) this.mAdapter.getItem(position)).GetActivtyStartTime();
            String endTime = ((ActionListItemInfo) this.mAdapter.getItem(position)).GetActivtyEndTime();
            String curTime = ((ActionListItemInfo) this.mAdapter.getItem(position)).GetActivtyCurTime();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String state = null;
            try {
                Date sta_date = sdf1.parse(startTime);
                Date cur_date = sdf1.parse(curTime);
                Date end_date = sdf1.parse(endTime);
                long st = sta_date.getTime();
                long ct = cur_date.getTime();
                long et = end_date.getTime();
                if (ct < st) {
                    state = "0";
                } else if (ct < et) {
                    state = "1";
                } else if (ct > et) {
                    state = "2";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mintent.putExtra("id", String.valueOf(index));
            mintent.putExtra("action_title", title);
            mintent.putExtra("action_flag", state);
            startActivity(mintent);
        }
    }

    public void GetLocalfile() {
        File cache_path = getCacheDir();
        File file_path = getFilesDir();
    }

    protected void onPause() {
        super.onPause();
        Tools.setActState(this, false);
        if (Main.mHandler != null) {
            Message msg = new Message();
            msg.what = 5;
            Main.mHandler.sendMessage(msg);
        }
    }

    public void onBackPressed() {
        if (this.Is_From_Welcome) {
            startActivity(new Intent(this, Main.class));
        }
        finish();
    }

    public String GetLcdInfo() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth + "x" + dm.heightPixels;
    }
}
