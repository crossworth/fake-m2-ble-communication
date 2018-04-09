package com.zhuoyou.plugin.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.util.ArrayList;
import java.util.List;

public class ActionRankFragment extends Fragment {
    private Bitmap bmp = null;
    private ListView listView;
    private ActionInfo mActionInfo = ((ActionInfo) getArguments().get("actioninfo"));
    private Context mContext = RunningApp.getInstance().getApplicationContext();
    private Typeface mNumberTP;
    private View mView;
    private ImageView myHead;
    private TextView myName;
    private ActionRankingItemInfo myRank = new ActionRankingItemInfo();
    private TextView myRank_count;
    private TextView myStep;
    private TextView noRank;
    private List<ActionRankingItemInfo> rank = new ArrayList();

    public ActionRankFragment() {
        if (this.mActionInfo != null) {
            this.rank = this.mActionInfo.getRankList();
            this.myRank = this.mActionInfo.getMyRankInfo();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.action_rank_fragment, container, false);
        initView();
        return this.mView;
    }

    public void initView() {
        this.noRank = (TextView) this.mView.findViewById(R.id.noRecordRank);
        this.myRank_count = (TextView) this.mView.findViewById(R.id.my_rank);
        this.myStep = (TextView) this.mView.findViewById(R.id.my_step);
        this.myName = (TextView) this.mView.findViewById(R.id.my_name);
        this.myHead = (ImageView) this.mView.findViewById(R.id.my_icon);
        Log.i("zoujian", this.myRank + "myRank");
        if (this.myRank == null || this.myRank.GetSteps() == 0) {
            this.noRank.setVisibility(0);
            this.myRank_count.setVisibility(8);
            this.myStep.setVisibility(8);
            this.myName.setVisibility(8);
            this.myHead.setVisibility(8);
        } else {
            this.mNumberTP = RunningApp.getCustomNumberFont();
            this.noRank.setVisibility(8);
            this.myRank_count.setVisibility(0);
            this.myStep.setVisibility(0);
            this.myName.setVisibility(0);
            this.myHead.setVisibility(0);
            this.myRank_count.setText(this.myRank.GetCount() + "");
            this.myStep.setText(this.myRank.GetSteps() + "");
            this.myName.setText(this.myRank.GetName() + "");
            int headIndex = Tools.getHead(this.mContext);
            if (headIndex == 10000) {
                this.bmp = Tools.convertFileToBitmap("/Running/download/custom");
                this.myHead.setImageBitmap(this.bmp);
            } else if (headIndex == 1000) {
                this.bmp = Tools.convertFileToBitmap("/Running/download/logo");
                this.myHead.setImageBitmap(this.bmp);
            } else {
                this.myHead.setImageResource(Tools.selectByIndex(headIndex));
            }
            this.myRank_count.setTypeface(this.mNumberTP);
            this.myStep.setTypeface(this.mNumberTP);
            this.myRank_count.setTextSize(23.0f);
            this.myStep.setTextSize(23.0f);
            this.myRank_count.setTextColor(-6844017);
            this.myStep.setTextColor(-6844017);
        }
        if (this.rank != null && this.rank.size() > 0) {
            this.listView = (ListView) this.mView.findViewById(R.id.action_rank_listview);
            this.listView.setAdapter(new ListViewAdatper(this.mContext, this.rank, this.listView));
        }
    }
}
