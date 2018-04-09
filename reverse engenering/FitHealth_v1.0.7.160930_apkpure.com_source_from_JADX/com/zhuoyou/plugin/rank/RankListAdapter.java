package com.zhuoyou.plugin.rank;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.rank.AsyncImageLoader.ImageCallback;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.util.List;

public class RankListAdapter extends BaseAdapter {
    private AsyncImageLoader mAsyncImageLoader = new AsyncImageLoader();
    private Context mContext;
    private List<RankInfo> mList;
    private ListView mListView;
    private Typeface mNumberTP = RunningApp.getCustomNumberFont();

    static class ViewCache {
        ImageView mIcon;
        TextView mName;
        TextView mRank;
        ImageView mRank_bg;
        TextView mStep;

        ViewCache() {
        }
    }

    class C18961 implements ImageCallback {
        C18961() {
        }

        public void imageLoaded(Drawable imageDrawable, String imageUrl) {
            ImageView imageViewByTag = (ImageView) RankListAdapter.this.mListView.findViewWithTag(imageUrl);
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

    public RankListAdapter(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        if (this.mList != null) {
            return this.mList.size();
        }
        return 0;
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
        this.mListView = (ListView) parent;
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
            holder.mRank_bg.setVisibility(0);
            holder.mRank.setTextSize(33.0f);
            holder.mStep.setTextSize(33.0f);
            holder.mRank.setTextColor(-618202);
            holder.mStep.setTextColor(-618202);
        } else if (position == 2) {
            holder.mRank_bg.setVisibility(0);
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
        holder.mRank.setText(((RankInfo) this.mList.get(position)).getRank() + "");
        int headId = ((RankInfo) this.mList.get(position)).getmImgId();
        String headUrl = ((RankInfo) this.mList.get(position)).getHeadUrl();
        if (headId == 1000 || headId == 10000) {
            holder.mIcon.setTag(headUrl);
            Drawable drawable = this.mAsyncImageLoader.loadDrawable(headUrl, new C18961());
            if (drawable == null) {
                holder.mIcon.setImageResource(R.drawable.logo_default);
            } else {
                holder.mIcon.setImageDrawable(drawable);
            }
        } else {
            holder.mIcon.setImageResource(Tools.selectByIndex(headId));
        }
        holder.mName.setText(((RankInfo) this.mList.get(position)).getName());
        holder.mStep.setText(((RankInfo) this.mList.get(position)).getmSteps());
        if (((RankInfo) this.mList.get(position)).getmSteps().equals("0")) {
            convertView.setVisibility(8);
        } else if (((RankInfo) this.mList.get(position)).getmSteps().length() > 8) {
            holder.mStep.setText(((RankInfo) this.mList.get(position)).getmSteps().substring(0, ((RankInfo) this.mList.get(position)).getmSteps().length() - 3));
        } else {
            convertView.setVisibility(0);
        }
        return convertView;
    }

    public void refreshListInfo(List<RankInfo> list) {
        this.mList = list;
        super.notifyDataSetChanged();
    }
}
