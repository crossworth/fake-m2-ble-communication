package com.zhuoyou.plugin.running.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.BaasHelper.CommentBean;
import com.zhuoyou.plugin.running.baas.BaasHelper.CommentCallback;
import com.zhuoyou.plugin.running.baas.CommentCloud.CommentInfo;
import com.zhuoyou.plugin.running.baas.CommentCloud.CommentResponse;
import com.zhuoyou.plugin.running.baas.CommentZan;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.CircleImageView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CommentActivity extends BaseActivity {
    private static final String DEFAULT_FORMAT_TIME = "yyyyMMddHHmmss";
    private static final String TAG = "commentCX";
    private MyAdapter adapter;
    private CommentBean commentBean;
    private List<CommentInfo> commentInfoList = new ArrayList();
    private EditText etComment;
    private boolean inCommentUpdate = false;
    private PullToRefreshListView lvComment;
    private Handler mHandler = new Handler();
    private ForegroundColorSpan replyColor = new ForegroundColorSpan(Color.parseColor("#949494"));
    private RelativeLayout rlEditText;
    private List<CommentInfo> selectedCommentInfoList = new ArrayList();
    private String topicId;

    class C16961 implements OnRefreshListener2<ListView> {
        C16961() {
        }

        public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            CommentActivity.this.getListToday();
        }

        public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            CommentActivity.this.getListMore();
        }
    }

    class C16972 implements Runnable {
        C16972() {
        }

        public void run() {
            CommentActivity.this.lvComment.setRefreshing(true);
        }
    }

    interface GetListCallBack {
        void getListFail();

        void getListSuccess(List<CommentInfo> list, List<CommentInfo> list2);
    }

    class C16983 implements GetListCallBack {
        C16983() {
        }

        public void getListSuccess(List<CommentInfo> selectlist, List<CommentInfo> list) {
            Log.i(CommentActivity.TAG, "getListSuccess:" + list.size());
            CommentActivity.this.commentInfoList.clear();
            for (CommentInfo info : list) {
                CommentActivity.this.commentInfoList.add(info);
            }
            CommentActivity.this.selectedCommentInfoList.clear();
            for (CommentInfo info2 : selectlist) {
                CommentActivity.this.selectedCommentInfoList.add(info2);
            }
            CommentActivity.this.adapter.notifyDataSetChanged();
            CommentActivity.this.lvComment.onRefreshComplete();
        }

        public void getListFail() {
            CommentActivity.this.lvComment.onRefreshComplete();
        }
    }

    class C16994 implements GetListCallBack {
        C16994() {
        }

        public void getListSuccess(List<CommentInfo> selectlist, List<CommentInfo> list) {
            Collections.sort(list);
            if (list.size() > 0) {
                for (CommentInfo info : list) {
                    if (!CommentActivity.this.commentInfoList.contains(info)) {
                        CommentActivity.this.commentInfoList.add(info);
                    }
                }
                CommentActivity.this.selectedCommentInfoList.clear();
                for (CommentInfo info2 : selectlist) {
                    CommentActivity.this.selectedCommentInfoList.add(info2);
                }
                CommentActivity.this.adapter.notifyDataSetChanged();
            }
            CommentActivity.this.lvComment.onRefreshComplete();
        }

        public void getListFail() {
            CommentActivity.this.lvComment.onRefreshComplete();
        }
    }

    class C17026 implements CommentCallback {

        class C17011 implements Runnable {
            C17011() {
            }

            public void run() {
                CommentActivity.this.getListToday();
            }
        }

        C17026() {
        }

        public void callback(DroiError droiError) {
            if (droiError.isOk()) {
                Tools.makeToast(CommentActivity.this, (int) C1680R.string.comment_update_success);
                CommentActivity.this.mHandler.postDelayed(new C17011(), 1000);
                return;
            }
            Tools.makeToast(CommentActivity.this, (int) C1680R.string.comment_update_fail);
        }
    }

    private class MyAdapter extends BaseAdapter {
        private List<CommentInfo> list = new ArrayList();
        private List<CommentInfo> selectList = new ArrayList();

        class ViewHolder {
            TextView commentAdd;
            TextView commentTime;
            TextView content;
            TextView reply;
            TextView userName;
            CircleImageView userPhoto;
            TextView zan;

            ViewHolder() {
            }
        }

        MyAdapter(List<CommentInfo> list, List<CommentInfo> selectList) {
            this.list = list;
            this.selectList = selectList;
        }

        public int getCount() {
            if (haveSelect()) {
                return (this.list.size() + this.selectList.size()) + 2;
            }
            return this.list.size() + 1;
        }

        private boolean haveSelect() {
            return this.selectList.size() > 0;
        }

        public Object getItem(int position) {
            return this.list.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            CommentInfo info;
            ViewHolder holder;
            if (haveSelect()) {
                if (position == 0) {
                    v = View.inflate(CommentActivity.this, C1680R.layout.item_comment_title, null);
                    ((TextView) v.findViewById(C1680R.id.tv_comment_title)).setText(C1680R.string.comment_selected);
                    return v;
                } else if (position == this.selectList.size() + 1) {
                    v = View.inflate(CommentActivity.this, C1680R.layout.item_comment_title, null);
                    ((TextView) v.findViewById(C1680R.id.tv_comment_title)).setText(C1680R.string.comment_newest);
                    return v;
                } else if (position <= this.selectList.size()) {
                    info = (CommentInfo) this.selectList.get(position - 1);
                } else {
                    info = (CommentInfo) this.list.get((position - this.selectList.size()) - 2);
                }
            } else if (position == 0) {
                v = View.inflate(CommentActivity.this, C1680R.layout.item_comment_title, null);
                ((TextView) v.findViewById(C1680R.id.tv_comment_title)).setText(C1680R.string.comment_newest);
                return v;
            } else {
                info = (CommentInfo) this.list.get(position - 1);
            }
            if (convertView == null || !(convertView.getTag() instanceof ViewHolder)) {
                holder = new ViewHolder();
                v = View.inflate(CommentActivity.this, C1680R.layout.item_comment, null);
                holder.userPhoto = (CircleImageView) v.findViewById(C1680R.id.cv_user_photo);
                holder.userName = (TextView) v.findViewById(C1680R.id.tv_user_name);
                holder.content = (TextView) v.findViewById(C1680R.id.tv_comment_content);
                holder.commentTime = (TextView) v.findViewById(C1680R.id.tv_comment_time);
                holder.reply = (TextView) v.findViewById(C1680R.id.tv_reply);
                holder.zan = (TextView) v.findViewById(C1680R.id.tv_zan);
                holder.commentAdd = (TextView) v.findViewById(C1680R.id.tv_comment_add);
                convertView = v;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.userPhoto.setImageResource(C1680R.drawable.default_photo);
            if (!TextUtils.isEmpty(info.headUrl)) {
                Tools.displayImage(holder.userPhoto, info.headUrl, false);
            }
            holder.userName.setText(TextUtils.isEmpty(info.user.getNickName()) ? info.user.getUserId() : info.user.getNickName());
            if (TextUtils.isEmpty(info.commentAdd) || info.toUser == null) {
                holder.content.setText(info.comment);
                holder.commentAdd.setVisibility(8);
            } else {
                String userId;
                String commentReply = CommentActivity.this.getResources().getString(C1680R.string.comment_button_reply) + " " + (TextUtils.isEmpty(info.toUser.getNickName()) ? info.toUser.getUserId() : info.toUser.getNickName()) + ": ";
                SpannableStringBuilder commentAdd = new SpannableStringBuilder(commentReply + info.comment);
                commentAdd.setSpan(CommentActivity.this.replyColor, 0, commentReply.length(), 33);
                holder.content.setText(commentAdd);
                holder.commentAdd.setVisibility(0);
                TextView textView = holder.commentAdd;
                StringBuilder stringBuilder = new StringBuilder();
                if (TextUtils.isEmpty(info.toUser.getNickName())) {
                    userId = info.toUser.getUserId();
                } else {
                    userId = info.toUser.getNickName();
                }
                textView.setText(stringBuilder.append(userId).append(": ").append(info.commentAdd).toString());
            }
            if (info.zanSum > 0) {
                holder.zan.setText(info.zanSum + "");
            } else {
                holder.zan.setText("0");
            }
            long secound = CommentActivity.this.getSecound(info.date);
            if (secound <= 300) {
                holder.commentTime.setText(CommentActivity.this.getString(C1680R.string.comment_time_just_now));
            } else if (secound <= 3600) {
                long minute = secound / 60;
                holder.commentTime.setText(String.format(CommentActivity.this.getString(C1680R.string.comment_time_near_time), new Object[]{Long.valueOf(minute)}));
            } else {
                holder.commentTime.setText(Tools.formatTimeByTimeZone(info.date, TimeZone.getDefault().getRawOffset()));
            }
            if (info.zanBean != null) {
                holder.zan.setSelected(true);
            }
            holder.zan.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (!holder.zan.isSelected()) {
                        CommentZan zan = new CommentZan();
                        User user = (User) DroiUser.getCurrentUser(User.class);
                        zan.setUser(user);
                        zan.setDate(Tools.getCurrentTimeByZeroZone());
                        zan.setAccount(user.getUserId());
                        zan.setToUser(info.user);
                        zan.setToAccount(info.user.getUserId());
                        zan.setIfZan(true);
                        zan.setToComment(info.commentId);
                        zan.saveInBackground(null);
                        holder.zan.setSelected(true);
                        holder.zan.setText((info.zanSum + 1) + "");
                    }
                }
            });
            holder.reply.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    CommentActivity.this.commentBean.toUser = info.user;
                    CommentActivity.this.commentBean.ifReply = true;
                    CommentActivity.this.commentBean.commentAdd = info.comment;
                    CommentActivity.this.showInput();
                }
            });
            return convertView;
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_comment);
        this.topicId = getIntent().getStringExtra("topicId");
        initView();
        initData();
    }

    private void initView() {
        this.etComment = (EditText) findViewById(C1680R.id.et_comment);
        this.rlEditText = (RelativeLayout) findViewById(C1680R.id.rl_edit_text);
        this.lvComment = (PullToRefreshListView) findViewById(C1680R.id.lv_comment_list);
        ILoadingLayout loadingLayout = this.lvComment.getLoadingLayoutProxy(true, false);
        loadingLayout.setPullLabel(getResources().getString(C1680R.string.comment_pull_text));
        loadingLayout.setRefreshingLabel(getResources().getString(C1680R.string.comment_pull_refreshing_text));
        this.lvComment.setOnRefreshListener(new C16961());
    }

    private void initData() {
        this.commentBean = new CommentBean(this.topicId);
        this.adapter = new MyAdapter(this.commentInfoList, this.selectedCommentInfoList);
        this.lvComment.setAdapter(this.adapter);
        this.lvComment.postDelayed(new C16972(), 500);
    }

    public void getListToday() {
        getListDate(new C16983(), Tools.getCurrentTimeByZeroZone());
    }

    public void getListMore() {
        if (this.commentInfoList.size() == 0) {
            getListToday();
        } else {
            getListDate(new C16994(), ((CommentInfo) this.commentInfoList.get(this.commentInfoList.size() - 1)).date);
        }
    }

    public void getListDate(final GetListCallBack callback, String date) {
        BaasHelper.getCommentList(new DroiCallback<CommentResponse>() {
            public void result(CommentResponse response, DroiError droiError) {
                if (droiError.isOk()) {
                    callback.getListSuccess(response.selectedCommentInfoList, response.commentInfoList);
                } else {
                    callback.getListFail();
                }
            }
        }, this.topicId, date);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.tv_scroll_comment:
                showInput();
                return;
            case C1680R.id.tv_express_comment:
                if (!this.inCommentUpdate) {
                    String comment = this.etComment.getText().toString();
                    if (TextUtils.isEmpty(comment)) {
                        Tools.makeToast((Context) this, (int) C1680R.string.comment_empty);
                        return;
                    }
                    this.commentBean.comment = comment;
                    BaasHelper.expressComment(this.commentBean, new C17026());
                    hideInput();
                    return;
                }
                return;
            case C1680R.id.ll_cancel_layout:
                hideInput();
                return;
            default:
                return;
        }
    }

    private long getSecound(String date) {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        df.setTimeZone(TimeZone.getDefault());
        try {
            Date tmp = df.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tmp);
            calendar.add(10, TimeZone.getDefault().getRawOffset() / 3600000);
            Date getDate = calendar.getTime();
            Long time = Long.valueOf(today.getTime() - getDate.getTime());
            Log.i(TAG, "today:" + today.toString() + "\n" + "date:" + getDate.toString() + "\noffset:" + TimeZone.getDefault().getRawOffset() + " second:" + (time.longValue() / 1000));
            return time.longValue() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void hideInput() {
        this.rlEditText.setVisibility(8);
        this.etComment.clearFocus();
        this.etComment.setText("");
        this.commentBean.clear();
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void showInput() {
        this.rlEditText.setVisibility(0);
        this.etComment.requestFocus();
        if (this.commentBean.ifReply) {
            this.etComment.setHint(getString(C1680R.string.comment_button_reply) + " " + (TextUtils.isEmpty(this.commentBean.toUser.getNickName()) ? this.commentBean.toUser.getUserId() : this.commentBean.toUser.getNickName()) + ":");
        } else {
            this.etComment.setHint(C1680R.string.comment_please_express);
        }
        ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.etComment, 0);
    }
}
