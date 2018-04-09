package com.zhuoyou.plugin.action;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.fithealth.running.R;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.rank.AsyncImageLoader;
import com.zhuoyou.plugin.rank.AsyncImageLoader.ImageCallback;
import com.zhuoyou.plugin.running.Tools;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ActionNoticeFragment extends Fragment {
    private int ActionPannelPosition;
    private String action_flag;
    private ActionInfo actioninfo;
    private ActionInfoAdaptor baseadaptor;
    private ImageView image;
    private ActionPannelItemInfo mActionPannelItemInfo;
    AsyncImageLoader mAsyncImageLoader;
    private ListView mList;
    private View mView;
    private TextView mtitle;
    private List<ActionPannelItemInfo> pannellist;
    private ActionImageView url_img;

    class C18601 implements ImageCallback {
        C18601() {
        }

        public void imageLoaded(Drawable imageDrawable, String imageUrl) {
            if (ActionNoticeFragment.this.url_img == null) {
                return;
            }
            if (imageDrawable != null) {
                ActionNoticeFragment.this.url_img.setVisibility(0);
                ActionNoticeFragment.this.url_img.setImageDrawable(imageDrawable);
                return;
            }
            ActionNoticeFragment.this.url_img.setVisibility(8);
        }
    }

    public ActionNoticeFragment() {
        this.action_flag = null;
        this.ActionPannelPosition = 0;
        this.actioninfo = (ActionInfo) getArguments().get("actioninfo");
        this.action_flag = (String) getArguments().get(Tools.SP_SPP_FLAG_KEY_FLAG);
        if (this.actioninfo != null) {
            this.pannellist = this.actioninfo.getPannel();
        }
        this.ActionPannelPosition = ((Integer) getArguments().get("position")).intValue();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.notice_fragment, container, false);
        this.mtitle = (TextView) this.mView.findViewById(R.id.mtitle);
        this.image = (ImageView) this.mView.findViewById(R.id.my_net_img);
        if (this.action_flag.equals("0")) {
            this.mtitle.setText(R.string.action_notice_title);
            this.image.setVisibility(8);
        } else {
            this.mtitle.setVisibility(8);
            this.image.setVisibility(0);
        }
        InitContent();
        return this.mView;
    }

    public void InitContent() {
        if (this.pannellist != null && this.pannellist.size() > this.ActionPannelPosition) {
            this.mActionPannelItemInfo = (ActionPannelItemInfo) this.pannellist.get(this.ActionPannelPosition);
            List<ActParagraph> mActParagraph = this.mActionPannelItemInfo.GetActParagraphList();
            if (mActParagraph != null && mActParagraph.size() > 0) {
                for (int i = 0; i < mActParagraph.size(); i++) {
                    ActParagraph actParagraph = (ActParagraph) mActParagraph.get(i);
                }
            }
        }
        this.mAsyncImageLoader = new AsyncImageLoader();
        this.url_img = (ActionImageView) this.mView.findViewById(R.id.my_net_img);
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        if (this.mActionPannelItemInfo != null) {
            List<ActParagraph> mTmp = this.mActionPannelItemInfo.GetActParagraphList();
            if (mTmp != null && mTmp.size() > 0) {
                ActParagraph mm = (ActParagraph) mTmp.get(0);
                if (mm != null) {
                    String url = mm.GetImgUrl();
                    if (url != null && !url.equals("")) {
                        try {
                            url = url.substring(0, url.lastIndexOf("/") + 1) + URLEncoder.encode(url.substring(url.lastIndexOf("/") + 1), "UTF-8").replace(SocializeConstants.OP_DIVIDER_PLUS, "%20");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Drawable drawable = this.mAsyncImageLoader.loadDrawable(url, new C18601());
                        if (drawable == null) {
                            this.url_img.setVisibility(8);
                            return;
                        }
                        this.url_img.setVisibility(0);
                        this.url_img.setImageDrawable(drawable);
                    }
                }
            }
        }
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
