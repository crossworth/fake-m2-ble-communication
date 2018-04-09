package com.umeng.socialize.shareboard;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.shareboard.Adapter.SNSPlatformAdapter;
import com.umeng.socialize.shareboard.wigets.ActionFrameAdapter;
import com.umeng.socialize.utils.ShareBoardlistener;
import java.util.ArrayList;
import java.util.List;

public class ShareBoard extends PopupWindow {
    private final ResContainer f5004R;
    private ActionFrameAdapter mAdapter;
    private UMActionBoard mBoardView = null;
    private Context mContext = null;
    private OnPlatformClickListener mListener;
    private List<SnsPlatform> platforms = new ArrayList();
    private ShareBoardlistener shareBoardlistener;

    class C16681 implements OnClickListener {
        C16681() {
        }

        public void onClick(View v) {
            ShareBoard.this.dismiss();
        }
    }

    interface OnPlatformClickListener {
        void onClick(SHARE_MEDIA share_media);
    }

    public ShareBoard(Context context, List<SnsPlatform> platforms) {
        super(context);
        setWindowLayoutMode(-1, -1);
        this.f5004R = ResContainer.get(context);
        this.mContext = context;
        this.mBoardView = initContainer(context);
        setContentView(this.mBoardView);
        this.platforms = platforms;
        this.mAdapter = new SNSPlatformAdapter(this.mContext, platforms, this);
        this.mBoardView.activateFrame(this.mAdapter);
        setAnimationStyle(this.f5004R.style("umeng_socialize_shareboard_animation"));
        setFocusable(true);
    }

    public ShareBoardlistener getShareBoardlistener() {
        return this.shareBoardlistener;
    }

    public void setShareBoardlistener(ShareBoardlistener shareBoardlistener) {
        this.shareBoardlistener = shareBoardlistener;
    }

    private UMActionBoard initContainer(Context context) {
        UMActionBoard umActionBoard = new UMActionBoard(context);
        umActionBoard.setLayoutParams(new LayoutParams(-1, -1));
        umActionBoard.setFitsSystemWindows(true);
        umActionBoard.setFrameOutsideListener(new C16681());
        return umActionBoard;
    }
}
