package com.zhuoyou.plugin.running.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.droi.btlib.connection.MapConstants;
import com.droi.greendao.bean.SportBean;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiCondition.Type;
import com.droi.sdk.core.DroiQuery.Builder;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.FishGameActivity;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.FishGameScore;
import com.zhuoyou.plugin.running.baas.Rank;
import com.zhuoyou.plugin.running.baas.Rank.RankInfo;
import com.zhuoyou.plugin.running.baas.Rank.Response;
import com.zhuoyou.plugin.running.baas.RankZan;
import com.zhuoyou.plugin.running.baas.UploadCallBack;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseFragment;
import com.zhuoyou.plugin.running.bean.EventGetRank;
import com.zhuoyou.plugin.running.bean.EventUserFetch;
import com.zhuoyou.plugin.running.database.SportHelper;
import com.zhuoyou.plugin.running.tools.AnimUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyActionBar;
import com.zhuoyou.plugin.running.view.ProgressWheel;
import com.zhuoyou.plugin.running.view.UserCardDialog;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;

public class GroupFragment extends BaseFragment implements OnClickListener {
    private static final long getInterval = 900000;
    private BaseAdapter adapter = new C18877();
    private boolean checkGameInfo = true;
    private ImageView cvFirstImg;
    private ImageView cvUserImg;
    private ImageView imgFirstBack;
    private boolean isLoadAll;
    private boolean isLoading;
    private ImageView ivGameCancel;
    private ImageView ivGameStart;
    private long lastGetTime = 0;
    private ListView mListView;
    private ProgressWheel progressWheel;
    private List<RankInfo> rankList = new ArrayList();
    private SwipeRefreshLayout swipe;
    private TextView tvFirstName;
    private TextView tvFirstSignature;
    private TextView tvLoading;
    private TextView tvTitle;
    private TextView tvUserName;
    private TextView tvUserRank;
    private TextView tvUserStep;
    private TextView tvUserZan;
    private User user = ((User) DroiUser.getCurrentUser(User.class));
    private int userZan;

    class C18791 implements OnRefreshListener {
        C18791() {
        }

        public void onRefresh() {
            GroupFragment.this.getRanklist(true, true);
        }
    }

    class C18802 implements OnClickListener {
        C18802() {
        }

        public void onClick(View v) {
            new UserCardDialog(GroupFragment.this.getContext(), GroupFragment.this.user, GroupFragment.this.userZan).show();
        }
    }

    class C18813 implements OnClickListener {
        C18813() {
        }

        public void onClick(View v) {
            GroupFragment.this.getMoreRankList();
        }
    }

    class C18824 implements OnItemClickListener {
        C18824() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            RankInfo rank = (RankInfo) GroupFragment.this.rankList.get(position - 1);
            if (rank.user != null) {
                new UserCardDialog(GroupFragment.this.getContext(), rank.user, rank.zanSum).show();
            }
        }
    }

    class C18835 implements OnScrollListener {
        C18835() {
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case 0:
                    if (view.getLastVisiblePosition() != view.getCount() - 1) {
                        GroupFragment.this.progressWheel.setVisibility(8);
                        return;
                    } else if (!GroupFragment.this.isLoadAll) {
                        GroupFragment.this.getMoreRankList();
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    class C18846 implements DroiQueryCallback<FishGameScore> {
        C18846() {
        }

        public void result(List<FishGameScore> list, DroiError droiError) {
            GroupFragment.this.checkGameInfo = true;
            Intent gameIntent = new Intent(GroupFragment.this.getContext(), FishGameActivity.class);
            if (!droiError.isOk() || list.size() <= 0) {
                FishGameScore score = new FishGameScore();
                score.setAccount(DroiUser.getCurrentUser().getUserId());
                score.setTime(3);
                score.setDate(Tools.getToday());
                gameIntent.putExtra("fishScore", score);
                GroupFragment.this.startActivity(gameIntent);
                Tools.makeToast(GroupFragment.this.getContext(), GroupFragment.this.getString(C1680R.string.game_toast_num, Integer.valueOf(3)));
            } else if (((FishGameScore) list.get(0)).getTime() > 0) {
                gameIntent.putExtra("fishScore", (Parcelable) list.get(0));
                GroupFragment.this.startActivity(gameIntent);
                Tools.makeToast(GroupFragment.this.getContext(), GroupFragment.this.getString(C1680R.string.game_toast_num, Integer.valueOf(((FishGameScore) list.get(0)).getTime())));
            } else {
                Tools.makeToast(GroupFragment.this.getContext(), GroupFragment.this.getString(C1680R.string.game_num_use_end));
            }
        }
    }

    class C18877 extends BaseAdapter {

        class ViewHolder {
            ImageView imgPhoto;
            TextView tvName;
            TextView tvRank;
            TextView tvStep;
            TextView tvZan;

            ViewHolder() {
            }
        }

        class ZanClickListener implements OnClickListener {
            private RankInfo item;
            private boolean setting = false;
            private TextView tvZan;

            class C18851 implements DroiCallback<Boolean> {
                C18851() {
                }

                public void result(Boolean aBoolean, DroiError droiError) {
                    ZanClickListener.this.setting = false;
                    Log.i("zhuqichao", "error=" + droiError + ", code = " + droiError.getCode());
                    if (droiError.isOk()) {
                        RankInfo access$900 = ZanClickListener.this.item;
                        access$900.zanSum++;
                        return;
                    }
                    ZanClickListener.this.item.rankZan = null;
                    ZanClickListener.this.tvZan.setSelected(false);
                    TextView access$1000 = ZanClickListener.this.tvZan;
                    RankInfo access$9002 = ZanClickListener.this.item;
                    int i = access$9002.zanNum - 1;
                    access$9002.zanNum = i;
                    access$1000.setText(String.valueOf(i));
                    Tools.makeToast((int) C1680R.string.group_rank_zan_fail);
                }
            }

            ZanClickListener(int position, TextView tvZan) {
                this.tvZan = tvZan;
                this.item = (RankInfo) GroupFragment.this.rankList.get(position);
            }

            public void onClick(View v) {
                if (GroupFragment.this.user.getUserId().equals(this.item.user.getUserId())) {
                    Tools.makeToast((int) C1680R.string.group_cont_zan_self);
                } else if (!this.setting) {
                    this.tvZan.setSelected(!this.tvZan.isSelected());
                    RankZan zan;
                    TextView textView;
                    RankInfo rankInfo;
                    int i;
                    if (this.tvZan.isSelected()) {
                        zan = BaasHelper.getZan(this.item.user);
                        textView = this.tvZan;
                        rankInfo = this.item;
                        i = rankInfo.zanNum + 1;
                        rankInfo.zanNum = i;
                        textView.setText(String.valueOf(i));
                        AnimUtils.playZanButtonAnim(this.tvZan);
                        this.setting = true;
                        this.item.rankZan = zan;
                        BaasHelper.saveZan(zan, new C18851());
                    } else if (this.item.rankZan != null) {
                        textView = this.tvZan;
                        rankInfo = this.item;
                        i = rankInfo.zanNum - 1;
                        rankInfo.zanNum = i;
                        textView.setText(String.valueOf(i));
                        this.setting = true;
                        zan = this.item.rankZan;
                        this.item.rankZan = null;
                        zan.deleteInBackground(new DroiCallback<Boolean>() {
                            public void result(Boolean aBoolean, DroiError droiError) {
                                ZanClickListener.this.setting = false;
                                if (droiError.isOk()) {
                                    RankInfo access$900 = ZanClickListener.this.item;
                                    access$900.zanSum--;
                                    return;
                                }
                                ZanClickListener.this.item.rankZan = zan;
                                ZanClickListener.this.tvZan.setSelected(true);
                                TextView access$1000 = ZanClickListener.this.tvZan;
                                RankInfo access$9002 = ZanClickListener.this.item;
                                int i = access$9002.zanNum + 1;
                                access$9002.zanNum = i;
                                access$1000.setText(String.valueOf(i));
                                Tools.makeToast((int) C1680R.string.group_rank_zan_del_fail);
                            }
                        });
                    }
                }
            }
        }

        C18877() {
        }

        public int getCount() {
            return GroupFragment.this.rankList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            boolean z;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(GroupFragment.this.getContext()).inflate(C1680R.layout.item_group_ranking_list, parent, false);
                holder.imgPhoto = (ImageView) convertView.findViewById(C1680R.id.img_user_photo);
                holder.tvRank = (TextView) convertView.findViewById(C1680R.id.tv_rank);
                holder.tvName = (TextView) convertView.findViewById(C1680R.id.tv_user_name);
                holder.tvStep = (TextView) convertView.findViewById(C1680R.id.tv_step_count);
                holder.tvZan = (TextView) convertView.findViewById(C1680R.id.tv_zan);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            RankInfo item = (RankInfo) GroupFragment.this.rankList.get(position);
            holder.tvRank.setText(String.valueOf(position + 1));
            if (item.user != null) {
                if (item.user.getUserId().contains("-")) {
                    item.user.setNickName(item.user.getUserId().split("-")[0]);
                }
                holder.tvName.setText(TextUtils.isEmpty(item.user.getNickName()) ? item.user.getUserId() : item.user.getNickName());
            } else {
                holder.tvName.setText("");
            }
            holder.tvStep.setText(String.valueOf(item.step));
            holder.imgPhoto.setImageResource(C1680R.drawable.default_photo);
            if (!TextUtils.isEmpty(item.headUrl)) {
                Tools.displayImage(holder.imgPhoto, item.headUrl, false);
            }
            holder.tvZan.setText(String.valueOf(item.zanNum));
            TextView textView = holder.tvZan;
            if (item.rankZan != null) {
                z = true;
            } else {
                z = false;
            }
            textView.setSelected(z);
            holder.tvZan.setOnClickListener(new ZanClickListener(position, holder.tvZan));
            return convertView;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1680R.layout.fragment_group, container, false);
    }

    protected void initDate() {
    }

    protected void initView(View view) {
        this.swipe = (SwipeRefreshLayout) view.findViewById(C1680R.id.swipe_refresh_layout);
        this.swipe.setColorSchemeResources(C1680R.color.background_color_main);
        this.swipe.setOnRefreshListener(new C18791());
        this.mListView = (ListView) view.findViewById(C1680R.id.rank_list);
        View header = getActivity().getLayoutInflater().inflate(C1680R.layout.layout_rank_list_header, null);
        header.setOnClickListener(new C18802());
        View footer = getActivity().getLayoutInflater().inflate(C1680R.layout.layout_rank_list_footer, null);
        footer.setOnClickListener(new C18813());
        this.mListView.addHeaderView(header, null, false);
        this.mListView.addFooterView(footer, null, false);
        this.tvUserRank = (TextView) header.findViewById(C1680R.id.tv_rank);
        this.tvUserName = (TextView) header.findViewById(C1680R.id.tv_user_name);
        this.cvUserImg = (ImageView) header.findViewById(C1680R.id.img_user_photo);
        this.tvUserStep = (TextView) header.findViewById(C1680R.id.tv_step_count);
        this.tvUserZan = (TextView) header.findViewById(C1680R.id.tv_zan);
        this.progressWheel = (ProgressWheel) footer.findViewById(C1680R.id.progress_wheel);
        this.tvLoading = (TextView) footer.findViewById(C1680R.id.btn_load_more);
        this.tvFirstName = (TextView) view.findViewById(C1680R.id.tv_usrname);
        this.cvFirstImg = (ImageView) view.findViewById(C1680R.id.img_user_photo);
        this.imgFirstBack = (ImageView) view.findViewById(C1680R.id.img_user_back);
        this.tvFirstSignature = (TextView) view.findViewById(C1680R.id.tv_signature);
        this.tvTitle = ((MyActionBar) view.findViewById(C1680R.id.action_bar)).getTitle();
        this.mListView.setOnItemClickListener(new C18824());
        this.mListView.setOnScrollListener(new C18835());
        initDate();
        initViewDate();
        getRanklist(true, false);
        this.ivGameStart = (ImageView) view.findViewById(C1680R.id.iv_start_game);
        this.ivGameStart.setOnClickListener(this);
        this.ivGameCancel = (ImageView) view.findViewById(C1680R.id.iv_game_cancle);
        this.ivGameCancel.setOnClickListener(this);
    }

    protected void initViewDate() {
        this.mListView.setAdapter(this.adapter);
        showUserData();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.iv_start_game:
                if (this.checkGameInfo) {
                    this.checkGameInfo = false;
                    Builder.newBuilder().query(FishGameScore.class).where(DroiCondition.cond(MapConstants.DATE, Type.EQ, Tools.getToday()).and(DroiCondition.cond("account", Type.EQ, DroiUser.getCurrentUser().getUserId()))).build().runQueryInBackground(new C18846());
                    return;
                }
                Tools.makeToast(getContext(), getString(C1680R.string.game_qurey_info));
                return;
            case C1680R.id.iv_game_cancle:
                this.ivGameStart.setVisibility(8);
                this.ivGameCancel.setVisibility(8);
                return;
            default:
                return;
        }
    }

    protected void onVisible() {
        if (System.currentTimeMillis() - this.lastGetTime > getInterval) {
            getRanklist(true, false);
        }
    }

    private void showUserData() {
        Tools.displayFace(this.cvUserImg, this.user.getHead());
        this.tvUserName.setText(TextUtils.isEmpty(this.user.getNickName()) ? this.user.getUserId() : this.user.getNickName());
    }

    private void getRanklist(final boolean isRefresh, final boolean isDragDown) {
        if (!this.isLoading) {
            this.lastGetTime = System.currentTimeMillis();
            this.isLoading = true;
            if (!isDragDown) {
                this.tvLoading.setText(C1680R.string.group_rank_loading);
                this.progressWheel.setVisibility(0);
            }
            BaasHelper.uploadSportInBackground(new UploadCallBack<SportBean>() {
                public void result(List<SportBean> list, DroiError error) {
                    if (error.isOk()) {
                        for (SportBean bean : list) {
                            bean.setSync(1);
                            SportHelper.getBeanDao().insertOrReplace(bean);
                        }
                        BaasHelper.uploadWechartStep(DroiUser.getCurrentUser().getUserId(), SportHelper.getTodaySport().getStepPhone());
                    }
                    GroupFragment.this.doGetRank(isRefresh, isDragDown, Rank.MIN);
                }
            });
        }
    }

    private void getMoreRankList() {
        if (!this.isLoading) {
            this.isLoading = true;
            this.tvLoading.setText(C1680R.string.group_rank_loading);
            this.progressWheel.setVisibility(0);
            if (this.rankList.size() > 0) {
                doGetRank(false, false, ((RankInfo) this.rankList.get(this.rankList.size() - 1)).step);
            } else {
                doGetRank(true, false, Rank.MIN);
            }
        }
    }

    private void doGetRank(boolean isRefresh, boolean isDragDown, int min) {
        final long time = System.currentTimeMillis();
        final boolean z = isRefresh;
        final boolean z2 = isDragDown;
        BaasHelper.getRankList(min, new DroiCallback<Response>() {
            public void result(Response response, DroiError droiError) {
                if (droiError.isOk()) {
                    boolean z;
                    GroupFragment.this.progressWheel.setVisibility(8);
                    if (response.rankList.size() <= 0) {
                        GroupFragment.this.isLoadAll = true;
                        GroupFragment.this.tvLoading.setText(C1680R.string.group_rank_loaded_all);
                    } else {
                        GroupFragment.this.isLoadAll = false;
                        GroupFragment.this.tvLoading.setText(C1680R.string.group_rank_loading);
                    }
                    if (z) {
                        GroupFragment.this.rankList = response.rankList;
                        GroupFragment.this.adapter.notifyDataSetInvalidated();
                    } else {
                        GroupFragment.this.rankList.addAll(response.rankList);
                        GroupFragment.this.adapter.notifyDataSetChanged();
                    }
                    GroupFragment.this.tvUserRank.setText(response.mUser.rank == -1 ? "-" : GroupFragment.this.getString(C1680R.string.group_rank_count, Integer.valueOf(response.mUser.rank)));
                    GroupFragment.this.tvUserStep.setText(response.mUser.step == -1 ? "-" : response.mUser.step + "");
                    GroupFragment.this.userZan = response.mUser.zanSum;
                    GroupFragment.this.tvUserZan.setText(String.valueOf(response.mUser.zanNum));
                    TextView access$1600 = GroupFragment.this.tvUserZan;
                    if (response.mUser.zanNum > 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    access$1600.setSelected(z);
                    if (GroupFragment.this.rankList == null || GroupFragment.this.rankList.size() <= 0 || ((RankInfo) GroupFragment.this.rankList.get(0)).user == null) {
                        GroupFragment.this.tvFirstName.setText("");
                        GroupFragment.this.tvFirstSignature.setText("");
                        GroupFragment.this.cvFirstImg.setImageResource(C1680R.drawable.default_photo);
                        GroupFragment.this.imgFirstBack.setImageResource(C1680R.color.background_color_main);
                    } else {
                        User first = ((RankInfo) GroupFragment.this.rankList.get(0)).user;
                        String occupy = GroupFragment.this.getResources().getString(C1680R.string.group_be_top);
                        access$1600 = GroupFragment.this.tvFirstName;
                        Object[] objArr = new Object[1];
                        objArr[0] = TextUtils.isEmpty(first.getNickName()) ? first.getUserId() : first.getNickName();
                        access$1600.setText(String.format(occupy, objArr));
                        GroupFragment.this.tvFirstSignature.setText(first.getSignature());
                        Tools.displayFace(GroupFragment.this.cvFirstImg, first.getHead());
                        Tools.displayBack(GroupFragment.this.imgFirstBack, first.getBack());
                        if (first.getBack() != null) {
                            GroupFragment.this.tvTitle.setShadowLayer(15.0f, 0.0f, 0.0f, GroupFragment.this.getResources().getColor(C1680R.color.text_shadow_color));
                        }
                    }
                    GroupFragment.this.onGetRankFinish(true, z2, droiError.getCode());
                } else {
                    GroupFragment.this.onGetRankFinish(false, z2, droiError.getCode());
                    Log.i("zhuqichao", "error=" + droiError + ", code=" + droiError.getCode());
                }
                GroupFragment.this.isLoading = false;
                Log.i("zhuqichao", "获取排名用时：" + (System.currentTimeMillis() - time));
            }
        });
    }

    private void onGetRankFinish(boolean isSuccess, boolean isDragDown, int errorCode) {
        this.swipe.setRefreshing(false);
        if (isDragDown) {
            Tools.makeToast(isSuccess ? C1680R.string.group_get_rank_success : C1680R.string.group_get_rank_fail);
        }
        if (!isSuccess) {
            this.lastGetTime = 0;
            this.tvLoading.setText(getString(C1680R.string.group_rank_loaded_fail, Integer.valueOf(errorCode)));
            this.progressWheel.setVisibility(8);
        }
    }

    @Subscribe
    public void onEventMainThread(EventGetRank event) {
        Log.i("yuanzi", "onEventMainThread");
        getRanklist(true, false);
    }

    @Subscribe
    public void onEventMainThread(EventUserFetch event) {
        showUserData();
    }
}
