package com.zhuoyou.plugin.running.activity;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.baidu.mapapi.map.HeatMap;
import com.droi.btlib.connection.MapConstants;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiCondition.Type;
import com.droi.sdk.core.DroiQuery.Builder;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.ActivityRank;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.Tools;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StepActivityActivity extends BaseActivity {
    private static final double[] GIFT_CASH = new double[]{3.0d, 1.6d, 1.2d, 0.9d, 0.8d, 0.7d, HeatMap.DEFAULT_OPACITY, 0.5d, 0.4d, 0.3d};
    private static final String TAG = "chenxin";
    private List<ActivityRank> activityList = new ArrayList();
    private MyAdapter adapter;
    private MyDialog dialog;
    private boolean ifQueryMyRank = false;
    private ListView rankList;

    class C18261 implements DroiQueryCallback<ActivityRank> {
        C18261() {
        }

        public void result(List<ActivityRank> list, DroiError droiError) {
            Log.i(StepActivityActivity.TAG, "get activity rank:" + list.size());
            if (droiError.isOk()) {
                StepActivityActivity.this.activityList = StepActivityActivity.this.removeEquals(list);
                StepActivityActivity.this.adapter.notifyDataSetChanged();
            }
        }
    }

    class C18272 implements DroiQueryCallback<ActivityRank> {
        C18272() {
        }

        public void result(List<ActivityRank> list, DroiError droiError) {
            StepActivityActivity.this.ifQueryMyRank = false;
            if (!droiError.isOk()) {
                Tools.makeToast(StepActivityActivity.this, (int) C1680R.string.droierror_net_error);
            } else if (list.size() > 0) {
                StepActivityActivity.this.dialog = new MyDialog(StepActivityActivity.this, (ActivityRank) list.get(0));
                StepActivityActivity.this.dialog.show();
            } else {
                StepActivityActivity.this.dialog = new MyDialog(StepActivityActivity.this, null);
                StepActivityActivity.this.dialog.show();
            }
        }
    }

    class MyAdapter extends BaseAdapter {
        MyAdapter() {
        }

        public int getCount() {
            return StepActivityActivity.this.activityList.size() >= 10 ? 10 : StepActivityActivity.this.activityList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = View.inflate(StepActivityActivity.this, C1680R.layout.item_step_activity_rank, null);
            ActivityRank info = (ActivityRank) StepActivityActivity.this.activityList.get(position);
            ((TextView) v.findViewById(C1680R.id.tv_user_name)).setText(TextUtils.isEmpty(info.nickName) ? info.accountId : info.nickName);
            return v;
        }
    }

    class MyDialog extends Dialog implements OnClickListener {
        ClipboardManager myClipboard;
        private ActivityRank rankInfo;
        private TextView tvActivityCode;
        private TextView tvGift;

        public MyDialog(Context context, ActivityRank rankInfo) {
            super(context, C1680R.style.CustomDialog);
            this.rankInfo = rankInfo;
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(C1680R.layout.step_acitivity_dialog);
            setCanceledOnTouchOutside(true);
            if (this.rankInfo == null) {
                findViewById(C1680R.id.bt_cancel).setOnClickListener(this);
                findViewById(C1680R.id.bt_activity_copy).setVisibility(8);
                findViewById(C1680R.id.rl_gift_back).setVisibility(8);
                findViewById(C1680R.id.iv_no_gift).setVisibility(0);
                findViewById(C1680R.id.tv_activity_code).setVisibility(8);
                return;
            }
            initView();
            initData();
        }

        private void initView() {
            findViewById(C1680R.id.bt_cancel).setOnClickListener(this);
            findViewById(C1680R.id.bt_activity_copy).setOnClickListener(this);
            this.tvGift = (TextView) findViewById(C1680R.id.tv_gift);
            this.tvActivityCode = (TextView) findViewById(C1680R.id.tv_activity_code);
            if (this.rankInfo.rank <= 10) {
                this.tvGift.setText("¥" + new DecimalFormat(".0").format(StepActivityActivity.GIFT_CASH[this.rankInfo.rank - 1]));
            } else if (this.rankInfo.rank <= 100) {
                this.tvGift.setVisibility(8);
                findViewById(C1680R.id.iv_gift_yimei).setVisibility(0);
            } else {
                findViewById(C1680R.id.bt_activity_copy).setVisibility(8);
                findViewById(C1680R.id.rl_gift_back).setVisibility(8);
                findViewById(C1680R.id.iv_no_gift).setVisibility(0);
                this.tvActivityCode.setVisibility(8);
            }
        }

        private void initData() {
            this.tvActivityCode.setText(this.rankInfo.activityCode.substring(0, 8));
            this.myClipboard = (ClipboardManager) StepActivityActivity.this.getSystemService("clipboard");
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case C1680R.id.bt_cancel:
                    dismiss();
                    return;
                case C1680R.id.bt_activity_copy:
                    this.myClipboard.setText(this.rankInfo.activityCode.substring(0, 8));
                    Tools.makeToast(StepActivityActivity.this, (int) C1680R.string.step_acitivity_dialog_copy_success);
                    dismiss();
                    return;
                default:
                    return;
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_step_activity);
        initView();
        initData();
    }

    private void initView() {
        this.rankList = (ListView) findViewById(C1680R.id.lv_activity_rank);
        this.rankList.setFocusable(false);
    }

    private void initData() {
        this.adapter = new MyAdapter();
        this.rankList.setAdapter(this.adapter);
        Builder.newBuilder().query(ActivityRank.class).orderBy("rank", Boolean.valueOf(true)).limit(30).where(DroiCondition.cond(MapConstants.DATE, Type.EQ, Tools.getYesterday())).build().runQueryInBackground(new C18261());
    }

    private List<ActivityRank> removeEquals(List<ActivityRank> list) {
        List<ActivityRank> myList = new ArrayList();
        for (ActivityRank rank : list) {
            if (!myList.contains(rank)) {
                myList.add(rank);
            }
        }
        return myList;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.bt_activity_get:
                if (this.ifQueryMyRank) {
                    Tools.makeToast((Context) this, (int) C1680R.string.step_acitivity_in_get_rank);
                    return;
                } else {
                    showMyAcitivty();
                    return;
                }
            default:
                return;
        }
    }

    private void showMyAcitivty() {
        if (this.dialog == null) {
            this.ifQueryMyRank = true;
            Builder.newBuilder().query(ActivityRank.class).limit(1).where(DroiCondition.cond(MapConstants.DATE, Type.EQ, Tools.getYesterday()).and(DroiCondition.cond("accountId", Type.EQ, DroiUser.getCurrentUser().getUserId()))).build().runQueryInBackground(new C18272());
            return;
        }
        this.dialog.show();
    }
}
