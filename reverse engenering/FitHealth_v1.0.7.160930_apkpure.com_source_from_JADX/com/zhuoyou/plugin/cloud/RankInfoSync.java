package com.zhuoyou.plugin.cloud;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.zhuoyou.plugin.rank.RankInfo;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.selfupdate.TerminalInfo;
import java.util.HashMap;
import java.util.List;

public class RankInfoSync {
    private boolean is_debug = true;
    private Context mContext;
    private TerminalInfo phoneInfo;
    HashMap<String, List<RankInfo>> resRankList = new HashMap();

    public RankInfoSync(Context con) {
        this.mContext = con;
        this.phoneInfo = TerminalInfo.generateTerminalInfo(this.mContext.getApplicationContext());
    }

    public void getNetRankInfo(Handler handler) {
        String openId = Tools.getOpenId(this.mContext);
        HashMap<String, String> params = new HashMap();
        params.put("imsi", this.phoneInfo.getImsi());
        params.put("account", openId);
        new GetDataFromNet(103001, handler, params, this.mContext).execute(new Object[]{NetMsgCode.URL});
        if (this.is_debug) {
            Log.d("txhlog", "getNetRankInfo");
        }
    }

    public void getTodayRankInfo(Handler handler) {
        String openId = Tools.getOpenId(this.mContext);
        HashMap<String, String> params = new HashMap();
        params.put("imsi", this.phoneInfo.getImsi());
        params.put("account", openId);
        new GetDataFromNet(NetMsgCode.getTodayRankInfo, handler, params, this.mContext).execute(new Object[]{NetMsgCode.URL});
        if (this.is_debug) {
            Log.d("txhlog", "getTodayRankInfo");
        }
    }
}
