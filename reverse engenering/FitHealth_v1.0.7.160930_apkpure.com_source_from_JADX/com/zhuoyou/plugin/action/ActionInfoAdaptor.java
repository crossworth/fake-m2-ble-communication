package com.zhuoyou.plugin.action;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.fithealth.running.R;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.rank.AsyncImageLoader;
import com.zhuoyou.plugin.rank.AsyncImageLoader.ImageCallback;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ActionInfoAdaptor extends BaseAdapter {
    private List<ActParagraph> mActParagraphlist;
    public AsyncImageLoader mAsyncImageLoader;
    private ListView mListView;
    private Context mcontext;

    static final class ViewHolder {
        public TextView Pannel_ind;
        public ImageView Pannel_pre;

        ViewHolder() {
        }
    }

    class C18591 implements ImageCallback {
        C18591() {
        }

        public void imageLoaded(Drawable imageDrawable, String imageUrl) {
            ImageView imageViewByTag = (ImageView) ActionInfoAdaptor.this.mListView.findViewWithTag(imageUrl);
            if (imageViewByTag == null) {
                return;
            }
            if (imageDrawable != null) {
                imageViewByTag.setImageDrawable(imageDrawable);
            } else {
                imageViewByTag.setImageResource(R.drawable.action_2);
            }
        }
    }

    public ActionInfoAdaptor(Context context, ActionPannelItemInfo mm, ListView listview) {
        this.mcontext = context;
        if (mm != null) {
            this.mActParagraphlist = mm.GetActParagraphList();
        }
        this.mListView = listview;
        this.mAsyncImageLoader = new AsyncImageLoader();
    }

    public int getCount() {
        if (this.mActParagraphlist != null) {
            return this.mActParagraphlist.size();
        }
        return 0;
    }

    public Object getItem(int position) {
        if (this.mActParagraphlist != null) {
            return this.mActParagraphlist.get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mcontext).inflate(R.layout.actioninfo_item, null);
            viewHolder = new ViewHolder();
            viewHolder.Pannel_pre = (ImageView) convertView.findViewById(R.id.introduce_img);
            viewHolder.Pannel_ind = (TextView) convertView.findViewById(R.id.introduce);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ActParagraph mm = (ActParagraph) this.mActParagraphlist.get(position);
        if (viewHolder.Pannel_pre != null) {
            String url = mm.GetImgUrl();
            try {
                url = url.substring(0, url.lastIndexOf("/") + 1) + URLEncoder.encode(url.substring(url.lastIndexOf("/") + 1), "UTF-8").replace(SocializeConstants.OP_DIVIDER_PLUS, "%20");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            viewHolder.Pannel_pre.setTag(url);
            Drawable drawable = this.mAsyncImageLoader.loadDrawable(url, new C18591());
            if (drawable == null) {
                viewHolder.Pannel_pre.setImageResource(R.drawable.action_2);
            } else {
                viewHolder.Pannel_pre.setImageDrawable(drawable);
            }
        }
        String title = mm.GetContent();
        if (title == null || title.equals("")) {
            viewHolder.Pannel_ind.setVisibility(8);
        } else {
            viewHolder.Pannel_ind.setText(mm.GetContent());
        }
        return convertView;
    }
}
