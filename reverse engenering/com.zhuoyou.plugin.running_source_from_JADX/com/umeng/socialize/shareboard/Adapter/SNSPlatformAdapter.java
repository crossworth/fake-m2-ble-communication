package com.umeng.socialize.shareboard.Adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.umeng.facebook.share.internal.ShareConstants;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.shareboard.ShareBoard;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.shareboard.wigets.ActionFrameAdapter;
import java.util.ArrayList;
import java.util.List;

public class SNSPlatformAdapter extends ActionFrameAdapter {
    private Context mContext;
    private List<SnsPlatform> platforms = new ArrayList();
    private ShareBoard shareBoard;

    public SNSPlatformAdapter(Context context, List<SnsPlatform> platforms, ShareBoard shareBoard) {
        this.platforms = platforms;
        this.mContext = context;
        this.shareBoard = shareBoard;
    }

    private void initShareBoardItem(View snsItemView, SnsPlatform snsPlatform) {
        ((ImageView) snsItemView.findViewById(ResContainer.getResourceId(this.mContext, ShareConstants.WEB_DIALOG_PARAM_ID, "umeng_socialize_shareboard_image"))).setImageResource(ResContainer.getResourceId(this.mContext, "drawable", snsPlatform.mIcon));
        ((TextView) snsItemView.findViewById(ResContainer.getResourceId(this.mContext, ShareConstants.WEB_DIALOG_PARAM_ID, "umeng_socialize_shareboard_pltform_name"))).setText(ResContainer.getString(this.mContext, snsPlatform.mShowWord));
    }

    private void shareClick(SnsPlatform snsPlatform, SHARE_MEDIA platform) {
        if (snsPlatform != null && this.shareBoard.getShareBoardlistener() != null) {
            this.shareBoard.getShareBoardlistener().onclick(snsPlatform, platform);
        }
    }

    public Object getItem(int position) {
        return this.platforms == null ? null : (SnsPlatform) this.platforms.get(position);
    }

    public int getCount() {
        return this.platforms == null ? 0 : this.platforms.size();
    }

    public View getView(int position, ViewGroup parent) {
        final SnsPlatform snsPlatform = (SnsPlatform) this.platforms.get(position);
        final View snsItemView = View.inflate(this.mContext, ResContainer.getResourceId(this.mContext, "layout", "umeng_socialize_shareboard_item"), null);
        initShareBoardItem(snsItemView, snsPlatform);
        snsItemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SNSPlatformAdapter.this.shareBoard.dismiss();
                SNSPlatformAdapter.this.shareClick(snsPlatform, snsPlatform.mPlatform);
            }
        });
        snsItemView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0) {
                    snsItemView.setBackgroundColor(-3355444);
                } else if (event.getAction() == 1) {
                    snsItemView.setBackgroundColor(-1);
                }
                return false;
            }
        });
        snsItemView.setFocusable(true);
        return snsItemView;
    }
}
