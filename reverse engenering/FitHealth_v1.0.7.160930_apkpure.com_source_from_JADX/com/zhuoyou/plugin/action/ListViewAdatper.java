package com.zhuoyou.plugin.action;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.rank.AsyncImageLoader;
import com.zhuoyou.plugin.rank.AsyncImageLoader.ImageCallback;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.util.List;

public class ListViewAdatper extends BaseAdapter {
    private AsyncImageLoader mAsyncImageLoader = new AsyncImageLoader();
    private Context mContext;
    private List<ActionRankingItemInfo> mList;
    private ListView mListView;
    private Typeface mNumberTP = RunningApp.getCustomNumberFont();

    static class ViewCache {
        TextView mAccount;
        ImageView mIcon;
        TextView mName;
        TextView mRank;
        ImageView mRank_bg;
        TextView mStep;

        ViewCache() {
        }
    }

    class C18621 implements ImageCallback {
        C18621() {
        }

        public void imageLoaded(Drawable imageDrawable, String imageUrl) {
            ImageView imageViewByTag = (ImageView) ListViewAdatper.this.mListView.findViewWithTag(imageUrl);
            if (imageViewByTag == null) {
                return;
            }
            if (imageDrawable != null) {
                imageViewByTag.setImageDrawable(imageDrawable);
            } else {
                imageViewByTag.setImageResource(R.drawable.logo_default);
            }
        }
    }

    public ListViewAdatper(Context context, List<ActionRankingItemInfo> list, ListView listview) {
        this.mContext = context;
        this.mList = list;
        this.mListView = listview;
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int position) {
        return this.mList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewCache holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.rank_list_item, parent, false);
            holder = new ViewCache();
            holder.mRank_bg = (ImageView) convertView.findViewById(R.id.rank_bg);
            holder.mRank = (TextView) convertView.findViewById(R.id.rank);
            holder.mIcon = (ImageView) convertView.findViewById(R.id.icon);
            holder.mName = (TextView) convertView.findViewById(R.id.name);
            holder.mStep = (TextView) convertView.findViewById(R.id.step);
            convertView.setTag(holder);
        } else {
            holder = (ViewCache) convertView.getTag();
        }
        holder.mRank.setTypeface(this.mNumberTP);
        holder.mStep.setTypeface(this.mNumberTP);
        if (position == 0) {
            holder.mRank_bg.setVisibility(0);
            holder.mRank_bg.setBackgroundResource(R.drawable.rank_first);
            holder.mRank.setTextSize(38.0f);
            holder.mStep.setTextSize(38.0f);
            holder.mRank.setTextColor(-1164520);
            holder.mStep.setTextColor(-1164520);
        } else if (position == 1) {
            holder.mRank_bg.setVisibility(8);
            holder.mRank.setTextSize(33.0f);
            holder.mStep.setTextSize(33.0f);
            holder.mRank.setTextColor(-618202);
            holder.mStep.setTextColor(-618202);
        } else if (position == 2) {
            holder.mRank_bg.setVisibility(8);
            holder.mRank.setTextSize(28.0f);
            holder.mStep.setTextSize(28.0f);
            holder.mRank.setTextColor(-1914590);
            holder.mStep.setTextColor(-1914590);
        } else {
            holder.mRank_bg.setVisibility(8);
            holder.mRank.setTextSize(23.0f);
            holder.mStep.setTextSize(23.0f);
            holder.mRank.setTextColor(-6844017);
            holder.mStep.setTextColor(-6844017);
        }
        holder.mRank.setText(((ActionRankingItemInfo) this.mList.get(position)).GetCount() + "");
        int headId = ((ActionRankingItemInfo) this.mList.get(position)).GetHeadImgId();
        String headUrl = ((ActionRankingItemInfo) this.mList.get(position)).GetHeadImgUrl();
        if (headId == 1000 || headId == 10000) {
            holder.mIcon.setTag(headUrl);
            Drawable drawable = this.mAsyncImageLoader.loadDrawable(headUrl, new C18621());
            if (drawable == null) {
                holder.mIcon.setImageResource(R.drawable.logo_default);
            } else {
                holder.mIcon.setImageDrawable(drawable);
            }
        } else {
            holder.mIcon.setImageResource(Tools.selectByIndex(headId));
        }
        holder.mName.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        holder.mName.setText(((ActionRankingItemInfo) this.mList.get(position)).GetName() + "");
        holder.mStep.setText(((ActionRankingItemInfo) this.mList.get(position)).GetSteps() + "");
        return convertView;
    }
}
