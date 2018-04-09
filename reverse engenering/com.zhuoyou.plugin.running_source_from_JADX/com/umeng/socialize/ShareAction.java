package com.umeng.socialize;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.shareboard.ShareBoard;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareAction {
    private Activity activity;
    private ShareBoardlistener boardlistener = null;
    private List<ShareContent> contentlist = new ArrayList();
    private ShareBoardlistener defaultmulshareboardlistener = new C15692();
    private ShareBoardlistener defaultshareboardlistener = new C15681();
    private List<SHARE_MEDIA> displaylist = null;
    private int gravity = 80;
    private List<UMShareListener> listenerlist = new ArrayList();
    private String mFrom = null;
    private UMShareListener mListener = null;
    private SHARE_MEDIA mPlatform = null;
    private ShareContent mShareContent = new ShareContent();
    private List<SnsPlatform> platformlist = new ArrayList();
    private View showatView = null;

    class C15681 implements ShareBoardlistener {
        C15681() {
        }

        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            ShareAction.this.setPlatform(share_media);
            ShareAction.this.share();
        }
    }

    class C15692 implements ShareBoardlistener {
        C15692() {
        }

        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            int index = ShareAction.this.displaylist.indexOf(share_media);
            int contentsize = ShareAction.this.contentlist.size();
            if (contentsize != 0) {
                ShareContent s;
                if (index < contentsize) {
                    s = (ShareContent) ShareAction.this.contentlist.get(index);
                } else {
                    s = (ShareContent) ShareAction.this.contentlist.get(contentsize - 1);
                }
                ShareAction.this.mShareContent = s;
            }
            int listenersize = ShareAction.this.listenerlist.size();
            if (listenersize != 0) {
                if (index < listenersize) {
                    ShareAction.this.mListener = (UMShareListener) ShareAction.this.listenerlist.get(index);
                } else {
                    ShareAction.this.mListener = (UMShareListener) ShareAction.this.listenerlist.get(listenersize - 1);
                }
            }
            ShareAction.this.setPlatform(share_media);
            ShareAction.this.share();
        }
    }

    public ShareAction(Activity activity) {
        if (activity != null) {
            this.activity = (Activity) new WeakReference(activity).get();
        }
    }

    public ShareContent getShareContent() {
        return this.mShareContent;
    }

    public String getFrom() {
        return this.mFrom;
    }

    public SHARE_MEDIA getPlatform() {
        return this.mPlatform;
    }

    public ShareAction setPlatform(SHARE_MEDIA platform) {
        this.mPlatform = platform;
        return this;
    }

    public ShareAction setCallback(UMShareListener listener) {
        this.mListener = listener;
        return this;
    }

    public ShareAction setShareboardclickCallback(ShareBoardlistener listener) {
        this.boardlistener = listener;
        return this;
    }

    public ShareAction setShareContent(ShareContent shareContent) {
        this.mShareContent = shareContent;
        return this;
    }

    public ShareAction setDisplayList(SHARE_MEDIA... list) {
        this.displaylist = Arrays.asList(list);
        this.platformlist.clear();
        for (SHARE_MEDIA temp : this.displaylist) {
            this.platformlist.add(temp.toSnsPlatform());
        }
        return this;
    }

    public ShareAction setListenerList(UMShareListener... list) {
        this.listenerlist = Arrays.asList(list);
        return this;
    }

    public ShareAction setContentList(ShareContent... list) {
        if (list == null || Arrays.asList(list).size() == 0) {
            ShareContent content = new ShareContent();
            content.mText = "友盟分享";
            this.contentlist.add(content);
        } else {
            this.contentlist = Arrays.asList(list);
        }
        return this;
    }

    public ShareAction addButton(String showword, String Keyword, String icon, String Grayicon) {
        this.platformlist.add(SHARE_MEDIA.createSnsPlatform(showword, Keyword, icon, Grayicon, 0));
        return this;
    }

    public ShareAction withText(String text) {
        this.mShareContent.mText = text;
        return this;
    }

    public ShareAction withTitle(String title) {
        this.mShareContent.mTitle = title;
        return this;
    }

    public ShareAction withTargetUrl(String url) {
        this.mShareContent.mTargetUrl = url;
        return this;
    }

    public ShareAction withFile(File file) {
        this.mShareContent.file = file;
        return this;
    }

    public ShareAction withApp(File file) {
        this.mShareContent.app = file;
        return this;
    }

    public ShareAction withMedia(UMImage image) {
        this.mShareContent.mMedia = image;
        return this;
    }

    public ShareAction withMedia(UMEmoji image) {
        this.mShareContent.mMedia = image;
        return this;
    }

    public ShareAction withFollow(String follow) {
        this.mShareContent.mFollow = follow;
        return this;
    }

    public ShareAction withExtra(UMImage mExtra) {
        this.mShareContent.mExtra = mExtra;
        return this;
    }

    public ShareAction withMedia(UMusic music) {
        this.mShareContent.mMedia = music;
        return this;
    }

    public ShareAction withMedia(UMVideo video) {
        this.mShareContent.mMedia = video;
        return this;
    }

    public ShareAction withShareBoardDirection(View view, int gravity) {
        this.gravity = gravity;
        this.showatView = view;
        return this;
    }

    public void share() {
        UMShareAPI.get(this.activity).doShare(this.activity, this, this.mListener);
    }

    public void open() {
        ShareBoard mShareBoard;
        if (this.platformlist.size() != 0) {
            Map<String, Object> map = new HashMap();
            map.put("listener", this.mListener);
            map.put("content", this.mShareContent);
            try {
                mShareBoard = new ShareBoard(this.activity, this.platformlist);
                if (this.boardlistener == null) {
                    mShareBoard.setShareBoardlistener(this.defaultmulshareboardlistener);
                } else {
                    mShareBoard.setShareBoardlistener(this.boardlistener);
                }
                mShareBoard.setFocusable(true);
                mShareBoard.setBackgroundDrawable(new BitmapDrawable());
                if (this.showatView == null) {
                    this.showatView = this.activity.getWindow().getDecorView();
                }
                mShareBoard.showAtLocation(this.showatView, this.gravity, 0, 0);
                return;
            } catch (Exception e) {
                Log.m4548e("");
                return;
            }
        }
        this.platformlist.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        this.platformlist.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        this.platformlist.add(SHARE_MEDIA.SINA.toSnsPlatform());
        this.platformlist.add(SHARE_MEDIA.QQ.toSnsPlatform());
        map = new HashMap();
        map.put("listener", this.mListener);
        map.put("content", this.mShareContent);
        mShareBoard = new ShareBoard(this.activity, this.platformlist);
        if (this.contentlist.size() == 0) {
            if (this.boardlistener == null) {
                mShareBoard.setShareBoardlistener(this.defaultshareboardlistener);
            } else {
                mShareBoard.setShareBoardlistener(this.boardlistener);
            }
        } else if (this.boardlistener == null) {
            mShareBoard.setShareBoardlistener(this.defaultmulshareboardlistener);
        } else {
            mShareBoard.setShareBoardlistener(this.boardlistener);
        }
        mShareBoard.setFocusable(true);
        mShareBoard.setBackgroundDrawable(new BitmapDrawable());
        if (this.showatView == null) {
            this.showatView = this.activity.getWindow().getDecorView();
        }
        mShareBoard.showAtLocation(this.showatView, 80, 0, 0);
    }

    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) {
            return null;
        }
        try {
            v.getLocationOnScreen(loc_int);
            Rect location = new Rect();
            location.left = loc_int[0];
            location.top = loc_int[1];
            location.right = location.left + v.getWidth();
            location.bottom = location.top + v.getHeight();
            return location;
        } catch (NullPointerException e) {
            return null;
        }
    }
}
