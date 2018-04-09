package com.zhuoyou.plugin.action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.fithealth.running.R;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.info.GoodsAddressActivity;
import com.zhuoyou.plugin.rank.AsyncImageLoader;
import com.zhuoyou.plugin.rank.AsyncImageLoader.ImageCallback;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ActionRuleFragment extends Fragment {
    private int ActionPannelPosition = 0;
    private ActionInfo actioninfo = ((ActionInfo) getArguments().get("actioninfo"));
    private ImageView image_hide;
    private ActionPannelItemInfo mActionPannelItemInfo;
    AsyncImageLoader mAsyncImageLoader;
    private Context mContext = RunningApp.getInstance().getApplicationContext();
    private View mView;
    private ScrollView mscroll;
    private List<ActionPannelItemInfo> pannellist;
    private TextView show_text;
    private RelativeLayout show_warn;
    private ActionImageView url_img;

    class C10891 implements OnClickListener {
        C10891() {
        }

        public void onClick(View v) {
            ActionRuleFragment.this.show_warn.setVisibility(8);
        }
    }

    class C10902 implements OnClickListener {
        C10902() {
        }

        public void onClick(View v) {
            ActionRuleFragment.this.startActivity(new Intent(ActionRuleFragment.this.mContext, GoodsAddressActivity.class));
        }
    }

    class C18613 implements ImageCallback {
        C18613() {
        }

        public void imageLoaded(Drawable imageDrawable, String imageUrl) {
            if (ActionRuleFragment.this.url_img == null) {
                return;
            }
            if (imageDrawable != null) {
                ActionRuleFragment.this.url_img.setVisibility(0);
                ActionRuleFragment.this.url_img.setImageDrawable(imageDrawable);
                return;
            }
            ActionRuleFragment.this.url_img.setVisibility(8);
        }
    }

    public ActionRuleFragment() {
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
        this.mView = inflater.inflate(R.layout.rule_fragment, container, false);
        this.mAsyncImageLoader = new AsyncImageLoader();
        this.mscroll = (ScrollView) this.mView.findViewById(R.id.myscroll);
        initView();
        this.image_hide.setOnClickListener(new C10891());
        this.show_text.setOnClickListener(new C10902());
        InitContent();
        this.url_img = (ActionImageView) this.mView.findViewById(R.id.my_net_img);
        return this.mView;
    }

    public void onResume() {
        super.onResume();
        String phone = Tools.getConsigneePhone(this.mContext);
        String name = Tools.getConsigneeName(this.mContext);
        String address = Tools.getConsigneeAddress(this.mContext);
        if (phone == null || phone.equals("") || name == null || name.equals("") || address == null || address.equals("")) {
            this.show_warn.setVisibility(0);
        } else {
            this.show_warn.setVisibility(8);
        }
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
                        Drawable drawable = this.mAsyncImageLoader.loadDrawable(url, new C18613());
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

    public void initView() {
        this.show_warn = (RelativeLayout) this.mView.findViewById(R.id.rule_perfect_personal_data);
        this.show_text = (TextView) this.mView.findViewById(R.id.onclik_personal_data);
        this.image_hide = (ImageView) this.mView.findViewById(R.id.hide_lay);
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
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
