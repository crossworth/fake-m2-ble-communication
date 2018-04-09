package com.zhuoyou.plugin.running.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.droi.btlib.connection.MapConstants;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiCondition.Type;
import com.droi.sdk.core.DroiQuery.Builder;
import com.droi.sdk.core.DroiQueryCallback;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.BaasHelper.CommentBean;
import com.zhuoyou.plugin.running.baas.BaasHelper.CommentCallback;
import com.zhuoyou.plugin.running.baas.CommentData;
import com.zhuoyou.plugin.running.baas.TopicData;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.Tools;
import java.util.List;

public class TopicActivity extends BaseActivity {
    private static final int GET_COMMENT_SUCCESS = 4098;
    private static final int GET_TOPIC_SUCCESS = 4097;
    private CommentBean comment;
    private EditText etComment;
    private Handler mHandler = new C18281();
    private RelativeLayout rlComment;
    private RelativeLayout rlEditText;
    private String topicId;
    private TextView tvCommentCount;
    private WebView wvTopicContent;

    class C18281 extends Handler {
        C18281() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 4097:
                    TopicActivity.this.topicId = (String) msg.obj;
                    TopicActivity.this.comment = new CommentBean(TopicActivity.this.topicId);
                    TopicActivity.this.rlComment.setClickable(true);
                    TopicActivity.this.getTopicList();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    class C18292 implements DroiCallback<Integer> {
        C18292() {
        }

        public void result(Integer integer, DroiError droiError) {
            Message msg = new Message();
            msg.what = 4098;
            TopicActivity.this.mHandler.sendMessage(msg);
            if (droiError.isOk()) {
                TopicActivity.this.tvCommentCount.setText(integer + "");
            }
        }
    }

    class C18303 implements DroiQueryCallback<TopicData> {
        C18303() {
        }

        public void result(List<TopicData> list, DroiError droiError) {
            if (droiError.isOk() && list.size() > 0) {
                TopicData topic = (TopicData) list.get(0);
                TopicActivity.this.wvTopicContent.loadDataWithBaseURL(null, topic.topicContent, "text/html", "utf-8", null);
                Message msg = new Message();
                msg.what = 4097;
                msg.obj = topic.topicId;
                TopicActivity.this.mHandler.sendMessage(msg);
            }
        }
    }

    class C18314 implements CommentCallback {
        C18314() {
        }

        public void callback(DroiError droiError) {
            if (droiError.isOk()) {
                Tools.makeToast(TopicActivity.this, (int) C1680R.string.comment_update_success);
                TopicActivity.this.getTopicList();
                return;
            }
            Tools.makeToast(TopicActivity.this, (int) C1680R.string.comment_update_fail);
        }
    }

    private void getTopicList() {
        Builder.newBuilder().query(CommentData.class).where(DroiCondition.cond("topicId", Type.EQ, this.topicId)).build().countInBackground(new C18292());
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_topic);
        initView();
        initData();
    }

    private void initView() {
        this.wvTopicContent = (WebView) findViewById(C1680R.id.wv_topic_content);
        this.wvTopicContent.getSettings().setJavaScriptEnabled(true);
        this.rlComment = (RelativeLayout) findViewById(C1680R.id.rl_comment_show);
        this.rlComment.setClickable(false);
        this.rlEditText = (RelativeLayout) findViewById(C1680R.id.rl_edit_text);
        this.etComment = (EditText) findViewById(C1680R.id.et_comment);
        this.tvCommentCount = (TextView) findViewById(C1680R.id.tv_comment_count);
    }

    private void initData() {
        Builder.newBuilder().query(TopicData.class).orderBy(MapConstants.DATE, Boolean.valueOf(false)).limit(1).where(DroiCondition.cond(MapConstants.DATE, Type.LT, Tools.getTodayTime())).build().runQueryInBackground(new C18303());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.rl_comment_show:
                Intent startIntent = new Intent(this, CommentActivity.class);
                startIntent.putExtra("topicId", this.topicId);
                startActivity(startIntent);
                return;
            case C1680R.id.tv_scroll_comment:
                showInput();
                return;
            case C1680R.id.tv_express_comment:
                String commentStr = this.etComment.getText().toString();
                if (TextUtils.isEmpty(commentStr)) {
                    Tools.makeToast((Context) this, (int) C1680R.string.comment_empty);
                    return;
                }
                hideInput();
                this.comment.comment = commentStr;
                BaasHelper.expressComment(this.comment, new C18314());
                return;
            case C1680R.id.ll_cancel_layout:
                hideInput();
                return;
            default:
                return;
        }
    }

    private void hideInput() {
        this.rlEditText.setVisibility(8);
        this.etComment.clearFocus();
        this.etComment.setText("");
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void showInput() {
        this.rlEditText.setVisibility(0);
        this.etComment.requestFocus();
        ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.etComment, 0);
    }

    public void onBackPressed() {
        if (this.rlEditText.getVisibility() == 0) {
            Log.i("chenxinX", "onBackPressed");
            hideInput();
            return;
        }
        super.onBackPressed();
    }
}
