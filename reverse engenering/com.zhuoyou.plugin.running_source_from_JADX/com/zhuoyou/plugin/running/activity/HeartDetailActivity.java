package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.droi.greendao.bean.HeartBean;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.umeng.socialize.UMShareAPI;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.EventGetHeart;
import com.zhuoyou.plugin.running.bean.HeartType;
import com.zhuoyou.plugin.running.database.HeartHelper;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.FullShareDialog;
import com.zhuoyou.plugin.running.view.HintPagerDialog;
import com.zhuoyou.plugin.running.view.MyActionBar;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import org.greenrobot.eventbus.EventBus;

public class HeartDetailActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final float ARG1 = 0.6f;
    private static final float ARG2 = 0.7f;
    private static final float ARG3 = 0.85f;
    private static final int YEAR = Calendar.getInstance().get(1);
    private PagerAdapter adapter = new C17834();
    private HeartBean bean;
    private String format;
    private TextView tvCount;
    private TextView tvCount1;
    private TextView tvCount2;
    private TextView tvCount3;
    private TextView tvOxygen;
    private TextView tvSportType;
    private TextView tvTime;
    private ArrayList<HeartType> typeList = new ArrayList();
    private User user = ((User) DroiUser.getCurrentUser(User.class));

    class C17791 implements OnClickListener {
        C17791() {
        }

        public void onClick(View v) {
            HeartDetailActivity.this.showShareDialog();
        }
    }

    class C17812 implements MyAlertDialog.OnClickListener {

        class C17801 implements DroiCallback<Boolean> {
            C17801() {
            }

            public void result(Boolean aBoolean, DroiError droiError) {
                if (droiError.isOk()) {
                    HeartDetailActivity.this.deleteLocal();
                    EventBus.getDefault().post(new EventGetHeart());
                    return;
                }
                Tools.makeToast(Tools.getDroiError(droiError));
            }
        }

        C17812() {
        }

        public void onClick(int witch) {
            Tools.makeToast((int) C1680R.string.string_delete_sync);
            BaasHelper.deleteHeartInBackground(HeartDetailActivity.this.bean, new C17801());
        }
    }

    class C17823 implements MyAlertDialog.OnClickListener {
        C17823() {
        }

        public void onClick(int witch) {
            HeartDetailActivity.this.deleteLocal();
        }
    }

    class C17834 extends PagerAdapter {
        private LinkedList<View> mViewCache = new LinkedList();

        class ViewHolder {
            ImageView imgIcon;
            TextView tvTitle;
            TextView tvValue;

            ViewHolder() {
            }
        }

        C17834() {
        }

        public int getCount() {
            return HeartDetailActivity.this.typeList.size();
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
                convertView = LayoutInflater.from(HeartDetailActivity.this).inflate(C1680R.layout.layout_heart_type_pager, container, false);
                holder = new ViewHolder();
                holder.imgIcon = (ImageView) convertView.findViewById(C1680R.id.img_heart_type);
                holder.tvTitle = (TextView) convertView.findViewById(C1680R.id.tv_type);
                holder.tvValue = (TextView) convertView.findViewById(C1680R.id.tv_value);
                convertView.setTag(holder);
            } else {
                convertView = (View) this.mViewCache.removeFirst();
                holder = (ViewHolder) convertView.getTag();
            }
            container.addView(convertView);
            HeartType item = (HeartType) HeartDetailActivity.this.typeList.get(position);
            holder.imgIcon.setImageResource(item.getIcon());
            holder.tvTitle.setText(item.getTitle());
            holder.tvValue.setText(item.getValue());
            return convertView;
        }
    }

    static {
        boolean z;
        if (HeartDetailActivity.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_heart_detail);
        this.bean = (HeartBean) HeartHelper.getBeanDao().load(getIntent().getStringExtra(HeartAddActivity.KEY_CLICK_ITEM));
        this.format = getString(C1680R.string.heart_time_format);
        initView();
        initData();
        initTypeList();
    }

    private void initView() {
        this.tvTime = (TextView) findViewById(C1680R.id.tv_time);
        this.tvCount = (TextView) findViewById(C1680R.id.tv_heart_count);
        this.tvOxygen = (TextView) findViewById(C1680R.id.heart_oxygen);
        this.tvSportType = (TextView) findViewById(C1680R.id.tv_sport_type);
        this.tvCount1 = (TextView) findViewById(C1680R.id.tv_count1);
        this.tvCount2 = (TextView) findViewById(C1680R.id.tv_count2);
        this.tvCount3 = (TextView) findViewById(C1680R.id.tv_count3);
        MyActionBar actionBar = (MyActionBar) findViewById(C1680R.id.action_bar);
        if ($assertionsDisabled || actionBar != null) {
            actionBar.setOnRightButtonClickListener(new C17791());
            return;
        }
        throw new AssertionError();
    }

    private void initData() {
        String type;
        this.tvCount.setText(String.valueOf(this.bean.getCount()));
        this.tvTime.setText(Tools.formatDate(this.bean.getDate(), Tools.DEFAULT_FORMAT_TIME, this.format));
        this.tvOxygen.setText(getString(C1680R.string.heart_oxygen_value, new Object[]{Tools.getHeartOxygen(this.bean.getCount()) + "%"}));
        int arg = 220 - (YEAR - Tools.parseDefDate(this.user.getBirth()).get(1));
        int arg1 = (int) (((float) arg) * 0.6f);
        int arg2 = (int) (((float) arg) * ARG2);
        int arg3 = (int) (((float) arg) * ARG3);
        this.tvCount1.setText(String.valueOf(arg1));
        this.tvCount2.setText(String.valueOf(arg2));
        this.tvCount3.setText(String.valueOf(arg3));
        if (this.bean.getCount() < arg1) {
            type = getString(C1680R.string.heart_detail_sport_type1);
        } else if (this.bean.getCount() <= arg2) {
            type = getString(C1680R.string.heart_detail_sport_type2);
        } else if (this.bean.getCount() <= arg3) {
            type = getString(C1680R.string.heart_detail_sport_type3);
        } else {
            type = getString(C1680R.string.heart_detail_sport_type4);
        }
        this.tvSportType.setText(getString(C1680R.string.heart_detail_sport_type, new Object[]{type}));
    }

    private void initTypeList() {
        HeartType type = new HeartType();
        type.setIcon(C1680R.drawable.heart_type1);
        type.setTitle(getString(C1680R.string.heart_type_title1));
        type.setValue(getString(C1680R.string.heart_type_value1));
        this.typeList.add(type);
        type = new HeartType();
        type.setIcon(C1680R.drawable.heart_type2);
        type.setTitle(getString(C1680R.string.heart_type_title2));
        type.setValue(getString(C1680R.string.heart_type_value2));
        this.typeList.add(type);
        type = new HeartType();
        type.setIcon(C1680R.drawable.heart_type3);
        type.setTitle(getString(C1680R.string.heart_type_title3));
        type.setValue(getString(C1680R.string.heart_type_value3));
        this.typeList.add(type);
        type = new HeartType();
        type.setIcon(C1680R.drawable.heart_type4);
        type.setTitle(getString(C1680R.string.heart_type_title4));
        type.setValue(getString(C1680R.string.heart_type_value4));
        this.typeList.add(type);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_delete:
                if (this.bean.getSync() == 1) {
                    showSyncDeleteDialog();
                    return;
                } else {
                    showLocalDeleteDialog();
                    return;
                }
            case C1680R.id.btn_heart_type:
                showHeartTypeDialog();
                return;
            default:
                return;
        }
    }

    private void showSyncDeleteDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.string_delete_data_sync);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C17812());
        dialog.show();
    }

    private void showLocalDeleteDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.string_delete_data);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C17823());
        dialog.show();
    }

    private void deleteLocal() {
        HeartHelper.getBeanDao().deleteByKey(this.bean.getDate());
        setResult(-1);
        finish();
        Tools.makeToast((int) C1680R.string.string_delete_complete);
    }

    private void showHeartTypeDialog() {
        HintPagerDialog dialog = new HintPagerDialog(this);
        dialog.setAdapter(this.adapter);
        dialog.show();
    }

    private void showShareDialog() {
        FullShareDialog dialog = new FullShareDialog(this);
        View convertView = getLayoutInflater().inflate(C1680R.layout.layout_share_heart, null);
        TextView date = (TextView) convertView.findViewById(C1680R.id.tv_date);
        ((TextView) convertView.findViewById(C1680R.id.tv_heart_count)).setText(String.valueOf(this.bean.getCount()));
        date.setText(Tools.formatDate(this.bean.getDate(), Tools.DEFAULT_FORMAT_TIME, "yyyy.MM.dd"));
        dialog.setContentView(convertView);
        dialog.setMessage(getString(C1680R.string.share_heart_message, new Object[]{Integer.valueOf(this.bean.getCount())}) + getString(C1680R.string.share_suffix));
        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
