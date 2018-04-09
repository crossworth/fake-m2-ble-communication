package com.zhuoyou.plugin.cloud;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.open.SocialConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyi.system.promotion.util.PromConstants;
import com.zhuoyou.plugin.action.ActParagraph;
import com.zhuoyou.plugin.action.ActionInfo;
import com.zhuoyou.plugin.action.ActionListItemInfo;
import com.zhuoyou.plugin.action.ActionPannelItemInfo;
import com.zhuoyou.plugin.action.ActionRankingItemInfo;
import com.zhuoyou.plugin.action.ActionWelcomeInfo;
import com.zhuoyou.plugin.action.AppInitForAction;
import com.zhuoyou.plugin.action.MessageInfo;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.firmware.Firmware;
import com.zhuoyou.plugin.info.ImageAsynTask;
import com.zhuoyou.plugin.rank.RankInfo;
import com.zhuoyou.plugin.running.PersonalConfig;
import com.zhuoyou.plugin.running.PersonalGoal;
import com.zhuoyou.plugin.running.Tools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class GetDataFromNet extends AsyncTask<Object, Object, String> {
    final int NO_VALUE = 100000;
    private Context mContext;
    private Handler mHandler = null;
    PersonalConfig mPersonalConfig;
    PersonalGoal mPersonalGoal;
    private OpenUrlGetStyle mUrlGetStyle = null;
    private int msgCode;
    HashMap<String, String> params;
    private String postParams = "";

    public GetDataFromNet(int msgCode, Handler handler, HashMap<String, String> map, Context context) {
        this.mHandler = handler;
        this.mUrlGetStyle = new OpenUrlGetStyle();
        this.mContext = context;
        this.msgCode = msgCode;
        this.params = map;
        this.mPersonalConfig = Tools.getPersonalConfig();
        this.mPersonalGoal = Tools.getPersonalGoal();
    }

    public GetDataFromNet(int msgCode, Handler handler, Context context) {
        this.mHandler = handler;
        this.mUrlGetStyle = new OpenUrlGetStyle();
        this.mContext = context;
        this.msgCode = msgCode;
        this.mPersonalConfig = Tools.getPersonalConfig();
        this.mPersonalGoal = Tools.getPersonalGoal();
    }

    public GetDataFromNet(Handler handler) {
        this.mHandler = handler;
        this.mUrlGetStyle = new OpenUrlGetStyle();
        this.mPersonalConfig = Tools.getPersonalConfig();
        this.mPersonalGoal = Tools.getPersonalGoal();
    }

    protected void onPreExecute() {
        try {
            JSONObject jsObject = new JSONObject();
            jsObject.put("head", buildHeadData());
            jsObject.put("body", buildBodyData());
            this.postParams = jsObject.toString();
        } catch (JSONException e) {
        }
        Log.i("txhlog", "postParams:" + this.postParams);
    }

    protected String doInBackground(Object... params) {
        String result = null;
        String urlHeader = "";
        urlHeader = params[0];
        try {
            OpenUrlGetStyle openUrlGetStyle = this.mUrlGetStyle;
            result = OpenUrlGetStyle.accessNetworkByPost(urlHeader, this.postParams);
        } catch (IOException e) {
        }
        Log.i("txhlog", "result:" + result);
        return result;
    }

    protected void onPostExecute(String result) {
        Message msg = this.mHandler.obtainMessage();
        if (result == null || result.equals("zero") || !getSUCCESS(result)) {
            msg.what = 404;
            this.mHandler.sendMessage(msg);
            return;
        }
        msg.what = 200;
        switch (this.msgCode) {
            case NetMsgCode.APP_RUN_ACTION_INIT /*100011*/:
                AppInitForAction mm = SplitActionInitDate(result);
                msg.arg1 = NetMsgCode.APP_RUN_ACTION_INIT;
                msg.obj = mm;
                break;
            case NetMsgCode.ACTION_GET_MSG /*100012*/:
                List<MessageInfo> msglist = SpiltMsg(result);
                msg.arg1 = NetMsgCode.ACTION_GET_MSG;
                msg.obj = msglist;
                break;
            case NetMsgCode.ACTION_JOIN /*100013*/:
                msg.arg1 = NetMsgCode.ACTION_JOIN;
                msg.obj = Integer.valueOf(0);
                break;
            case NetMsgCode.ACTION_GET_IDINFO /*100014*/:
                Log.d("zzb", "ACTION_GET_IDINFO:result = " + result);
                ActionInfo actioninfo = SpiltActionInfo(result);
                msg.arg1 = NetMsgCode.ACTION_GET_IDINFO;
                msg.obj = actioninfo;
                break;
            case NetMsgCode.ACTION_GET_NEXTPAGE /*100015*/:
            case NetMsgCode.ACTION_GET_REFRESHPAGE /*100016*/:
                List<ActionListItemInfo> mactionlist = SpiltActionListitem(result);
                msg.arg1 = this.msgCode;
                msg.obj = mactionlist;
                break;
            case NetMsgCode.postCustomInfo /*101001*/:
                Log.i("txhlog", "result:NetMsgCode.postCustomInfo");
                msg.arg1 = NetMsgCode.postCustomInfo;
                break;
            case NetMsgCode.getCustomInfo /*102001*/:
                saveCustInfoToSharePrefer(result);
                msg.arg1 = NetMsgCode.getCustomInfo;
                break;
            case 103001:
                HashMap<String, List<RankInfo>> list = SplitRankInfoList(result);
                msg.arg1 = 103001;
                msg.obj = list;
                break;
            case NetMsgCode.FIRMWARE_GET_VERSION /*200101*/:
                msg.obj = getFirmware(result);
                Tools.setFirmwear(false);
                Log.i("hph", "Firmware result=" + result);
                break;
            case NetMsgCode.getTodayRankInfo /*301013*/:
                HashMap<String, List<RankInfo>> todaylist = SplitTodayRankInfoList(result);
                msg.arg1 = NetMsgCode.getTodayRankInfo;
                msg.obj = todaylist;
                break;
        }
        Log.d("txhlog", "hander sendmes =" + msg.arg1);
        this.mHandler.sendMessage(msg);
    }

    private HashMap<String, List<RankInfo>> SplitRankInfoList(String result) {
        HashMap<String, List<RankInfo>> resRankList = new HashMap();
        Log.i("txhlog", "result:" + result);
        if (!TextUtils.isEmpty(result)) {
            try {
                int i;
                RankInfo mRankInfo;
                JSONObject tempJSONObject;
                List<RankInfo> mySportRanking;
                JSONObject jSONObject;
                RankInfo myRankInfo;
                JSONObject body = new JSONObject(new JSONObject(result.trim()).optString("body"));
                String sevenList = body.optString("sevenDaysStepList");
                if (!(sevenList == null || sevenList.equals(""))) {
                    List<RankInfo> mSportList = new ArrayList();
                    JSONArray jasonArray = new JSONArray(sevenList);
                    if (jasonArray != null && jasonArray.length() > 0) {
                        for (i = 0; i < jasonArray.length(); i++) {
                            mRankInfo = new RankInfo();
                            tempJSONObject = jasonArray.getJSONObject(i);
                            mRankInfo.setRank(Integer.valueOf(tempJSONObject.optString("count")).intValue());
                            mRankInfo.setAccountId(tempJSONObject.optString("accountId"));
                            mRankInfo.setmImgId(Integer.valueOf(tempJSONObject.optString("headimgId")).intValue());
                            mRankInfo.setName(tempJSONObject.optString("name"));
                            mRankInfo.setmSteps(tempJSONObject.optString("steps"));
                            mRankInfo.setHeadUrl(tempJSONObject.optString("headImgUrl"));
                            mSportList.add(mRankInfo);
                        }
                        resRankList.put("sevenDaysStepList", mSportList);
                    }
                }
                String mouthList = body.optString("monthStepList");
                if (!(mouthList == null || mouthList.equals(""))) {
                    List<RankInfo> mMouthSportList = new ArrayList();
                    JSONArray mjasonArray = new JSONArray(mouthList);
                    if (mjasonArray != null && mjasonArray.length() > 0) {
                        for (i = 0; i < mjasonArray.length(); i++) {
                            mRankInfo = new RankInfo();
                            tempJSONObject = mjasonArray.getJSONObject(i);
                            mRankInfo.setRank(Integer.valueOf(tempJSONObject.optString("count")).intValue());
                            mRankInfo.setAccountId(tempJSONObject.optString("accountId"));
                            mRankInfo.setmImgId(Integer.valueOf(tempJSONObject.optString("headimgId")).intValue());
                            mRankInfo.setName(tempJSONObject.optString("name"));
                            mRankInfo.setmSteps(tempJSONObject.optString("steps"));
                            mRankInfo.setHeadUrl(tempJSONObject.optString("headImgUrl"));
                            mMouthSportList.add(mRankInfo);
                        }
                        resRankList.put("monthStepList", mMouthSportList);
                    }
                }
                String highestList = body.optString("highestStepList");
                if (!(highestList == null || highestList.equals(""))) {
                    List<RankInfo> mhSportList = new ArrayList();
                    JSONArray hJasonArray = new JSONArray(highestList);
                    if (hJasonArray != null && hJasonArray.length() > 0) {
                        for (i = 0; i < hJasonArray.length(); i++) {
                            mRankInfo = new RankInfo();
                            tempJSONObject = hJasonArray.getJSONObject(i);
                            mRankInfo.setRank(Integer.valueOf(tempJSONObject.optString("count")).intValue());
                            mRankInfo.setAccountId(tempJSONObject.optString("accountId"));
                            mRankInfo.setmImgId(Integer.valueOf(tempJSONObject.optString("headimgId")).intValue());
                            mRankInfo.setName(tempJSONObject.optString("name"));
                            mRankInfo.setmSteps(tempJSONObject.optString("steps"));
                            mRankInfo.setHeadUrl(tempJSONObject.optString("headImgUrl"));
                            mhSportList.add(mRankInfo);
                        }
                        resRankList.put("highestStepList", mhSportList);
                    }
                }
                String sportRanking = body.optString("accountSevenData");
                if (!(sportRanking == null || sportRanking.equals(""))) {
                    mySportRanking = new ArrayList();
                    jSONObject = new JSONObject(sportRanking);
                    myRankInfo = new RankInfo();
                    myRankInfo.setRank(Integer.valueOf(jSONObject.optString("count")).intValue());
                    myRankInfo.setAccountId(jSONObject.optString("accountId"));
                    myRankInfo.setmImgId(Integer.valueOf(jSONObject.optString("headimgId")).intValue());
                    myRankInfo.setName(jSONObject.optString("name"));
                    myRankInfo.setmSteps(jSONObject.optString("steps"));
                    myRankInfo.setHeadUrl(jSONObject.optString("headImgUrl"));
                    mySportRanking.add(myRankInfo);
                    resRankList.put("accountSevenData", mySportRanking);
                }
                String sportRankingM = body.optString("accountMonthData");
                if (!(sportRankingM == null || sportRankingM.equals(""))) {
                    mySportRanking = new ArrayList();
                    jSONObject = new JSONObject(sportRankingM);
                    myRankInfo = new RankInfo();
                    myRankInfo.setRank(Integer.valueOf(jSONObject.optString("count")).intValue());
                    myRankInfo.setAccountId(jSONObject.optString("accountId"));
                    myRankInfo.setmImgId(Integer.valueOf(jSONObject.optString("headimgId")).intValue());
                    myRankInfo.setName(jSONObject.optString("name"));
                    myRankInfo.setmSteps(jSONObject.optString("steps"));
                    myRankInfo.setHeadUrl(jSONObject.optString("headImgUrl"));
                    mySportRanking.add(myRankInfo);
                    resRankList.put("accountMonthData", mySportRanking);
                }
                String hSportRanking = body.optString("accountHighestData");
                if (!(hSportRanking == null || hSportRanking.equals(""))) {
                    List<RankInfo> myhSportRanking = new ArrayList();
                    JSONObject htemObject = new JSONObject(hSportRanking);
                    RankInfo myhRankInfo = new RankInfo();
                    myhRankInfo.setRank(Integer.valueOf(htemObject.optString("count")).intValue());
                    myhRankInfo.setAccountId(htemObject.optString("accountId"));
                    myhRankInfo.setmImgId(Integer.valueOf(htemObject.optString("headimgId")).intValue());
                    myhRankInfo.setName(htemObject.optString("name"));
                    myhRankInfo.setmSteps(htemObject.optString("steps"));
                    myhRankInfo.setHeadUrl(htemObject.optString("headImgUrl"));
                    myhSportRanking.add(myhRankInfo);
                    resRankList.put("accountHighestData", myhSportRanking);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resRankList;
    }

    private HashMap<String, List<RankInfo>> SplitTodayRankInfoList(String result) {
        HashMap<String, List<RankInfo>> resRankList = new HashMap();
        Log.i("txhlog", "result:" + result);
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONObject body = new JSONObject(new JSONObject(result.trim()).optString("body"));
                String sevenList = body.optString("todayStepList");
                if (!(sevenList == null || sevenList.equals(""))) {
                    List<RankInfo> mSportList = new ArrayList();
                    JSONArray jasonArray = new JSONArray(sevenList);
                    if (jasonArray != null && jasonArray.length() > 0) {
                        for (int i = 0; i < jasonArray.length(); i++) {
                            RankInfo mRankInfo = new RankInfo();
                            JSONObject tempJSONObject = jasonArray.getJSONObject(i);
                            mRankInfo.setRank(Integer.valueOf(tempJSONObject.optString("count")).intValue());
                            mRankInfo.setAccountId(tempJSONObject.optString("accountId"));
                            mRankInfo.setmImgId(Integer.valueOf(tempJSONObject.optString("headimgId")).intValue());
                            mRankInfo.setName(tempJSONObject.optString("name"));
                            mRankInfo.setmSteps(tempJSONObject.optString("steps"));
                            mRankInfo.setHeadUrl(tempJSONObject.optString("headImgUrl"));
                            mSportList.add(mRankInfo);
                        }
                        resRankList.put("todayStepList", mSportList);
                    }
                }
                String hSportRanking = body.optString("accountTodayStep");
                if (!(hSportRanking == null || hSportRanking.equals(""))) {
                    List<RankInfo> myhSportRanking = new ArrayList();
                    JSONObject htemObject = new JSONObject(hSportRanking);
                    RankInfo myhRankInfo = new RankInfo();
                    myhRankInfo.setRank(Integer.valueOf(htemObject.optString("count")).intValue());
                    myhRankInfo.setAccountId(htemObject.optString("accountId"));
                    myhRankInfo.setmImgId(Integer.valueOf(htemObject.optString("headimgId")).intValue());
                    myhRankInfo.setName(htemObject.optString("name"));
                    myhRankInfo.setmSteps(htemObject.optString("steps"));
                    myhRankInfo.setHeadUrl(htemObject.optString("headImgUrl"));
                    myhSportRanking.add(myhRankInfo);
                    resRankList.put("accountTodayStep", myhSportRanking);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resRankList;
    }

    private void saveCustInfoToSharePrefer(String result) {
        String josnBody = "";
        String mAccountInfo = "";
        try {
            JSONObject mBody = (JSONObject) new JSONTokener(((JSONObject) new JSONTokener(result).nextValue()).getString("body")).nextValue();
            int mJsonResult = mBody.getInt("result");
            JSONObject mAccoInfo = (JSONObject) new JSONTokener(mBody.getString("accountForm")).nextValue();
            Tools.setInfoResult(this.mContext, mJsonResult);
            if (mJsonResult == 0) {
                int headId = mAccoInfo.optInt("headimgId", 6);
                String headImgUrl = mAccoInfo.optString("headImgUrl", "");
                if (!headImgUrl.equals("") && headImgUrl.startsWith("http:")) {
                    if (headId == 10000) {
                        new ImageAsynTask().execute(new String[]{headImgUrl, "custom"});
                    } else if (headId == 1000) {
                        new ImageAsynTask().execute(new String[]{headImgUrl, "logo"});
                    }
                }
                Tools.setUsrName(this.mContext, mAccoInfo.optString("name", ""));
                Tools.setHead(this.mContext, headId);
                this.mPersonalConfig.setSex(mAccoInfo.optInt(DataBaseContants.CONF_SEX, 0));
                int birth = mAccoInfo.optInt(SocializeProtocolConstants.PROTOCOL_KEY_BIRTHDAY, 1991);
                if (birth < 200) {
                    birth = Calendar.getInstance().get(1) - birth;
                }
                this.mPersonalConfig.setYear(birth);
                Tools.setSignature(this.mContext, mAccoInfo.optString("signature", ""));
                Tools.setLikeSportsIndex(this.mContext, mAccoInfo.optString("favoriteSport", ""));
                this.mPersonalConfig.setWeight(mAccoInfo.optInt(DataBaseContants.CONF_WEIGHT, 75) + "." + mAccoInfo.optString("weightN", "0"));
                this.mPersonalConfig.setHeight(mAccoInfo.optInt(DataBaseContants.CONF_HEIGHT, 180));
                this.mPersonalGoal.mGoalSteps = mAccoInfo.optInt("step", 7000);
                this.mPersonalGoal.mGoalCalories = mAccoInfo.optInt(DataBaseContants.GPS_CALORIE, 200);
                Tools.updatePersonalGoal(this.mPersonalGoal);
                Tools.updatePersonalConfig(this.mPersonalConfig);
                Tools.setPhoneNum(this.mContext, mAccoInfo.optString("phone", ""));
                Tools.setEmail(this.mContext, mAccoInfo.optString("email", ""));
                Tools.setProviceIndex(this.mContext, mAccoInfo.optInt("location", 10000));
                Tools.setCityIndex(this.mContext, mAccoInfo.optInt("county", 10000));
                Tools.saveConsigneeInfo(mAccoInfo.optString("consigneeName", ""), mAccoInfo.optString("consigneePhone", ""), mAccoInfo.optString("consigneeLocation", ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private AppInitForAction SplitActionInitDate(String result) {
        AppInitForAction mappinitforaction = new AppInitForAction();
        try {
            int i;
            JSONObject object;
            JSONObject AppInitObject = new JSONObject(new JSONObject(result).optString("body"));
            String openAppActInfo = AppInitObject.optString("openAppAct");
            if (openAppActInfo != null) {
                if (!openAppActInfo.equals("")) {
                    JSONObject jSONObject = new JSONObject(openAppActInfo);
                    mappinitforaction.SetWelcomeInfo(new ActionWelcomeInfo(jSONObject.optString("imgUrl"), jSONObject.optInt("id")));
                }
            }
            String MsgInfo = AppInitObject.optString("msgs");
            if (!(MsgInfo == null || MsgInfo.equals(""))) {
                JSONArray jSONArray = new JSONArray(MsgInfo);
                for (i = 0; i < jSONArray.length(); i++) {
                    object = jSONArray.getJSONObject(i);
                    mappinitforaction.AddMsgItem(new MessageInfo(object.optInt("noticeId"), object.optString("content"), object.optInt("activityId"), object.optInt("type")));
                }
            }
            String ActsInfo = AppInitObject.optString("acts");
            if (!(ActsInfo == null || ActsInfo.equals(""))) {
                JSONArray ActsList = new JSONArray(ActsInfo);
                for (i = 0; i < ActsList.length(); i++) {
                    object = ActsList.getJSONObject(i);
                    AppInitForAction appInitForAction = mappinitforaction;
                    appInitForAction.AddActionListItem(new ActionListItemInfo(object.optInt("actId"), object.optString("title"), object.optString("startTime"), object.optString("endTime"), object.optString("curTime"), object.optString("num"), object.optInt(Tools.SP_SPP_FLAG_KEY_FLAG), object.optInt("top"), object.optString("imgUrl")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mappinitforaction;
    }

    private List<MessageInfo> SpiltMsg(String result) {
        List<MessageInfo> msgList = new ArrayList();
        try {
            String msglist = new JSONObject(new JSONObject(result).optString("body")).optString("msgs");
            if (!(msglist == null || msglist.equals(""))) {
                JSONArray MsgList = new JSONArray(msglist);
                for (int i = 0; i < MsgList.length(); i++) {
                    JSONObject object = MsgList.getJSONObject(i);
                    msgList.add(new MessageInfo(object.optInt("noticeId"), object.optString("content"), object.optInt("activityId"), object.optInt("type")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msgList;
    }

    private ActionInfo SpiltActionInfo(String result) {
        ActionInfo mActioninfo = new ActionInfo();
        try {
            int i;
            JSONObject object;
            JSONObject MsgObject = new JSONObject(new JSONObject(result).optString("body"));
            String pannels = MsgObject.optString("pannels");
            if (pannels != null) {
                if (!pannels.equals("")) {
                    JSONArray PannelList = new JSONArray(pannels);
                    for (i = 0; i < PannelList.length(); i++) {
                        ActionPannelItemInfo mpannelitem = new ActionPannelItemInfo();
                        object = PannelList.getJSONObject(i);
                        mpannelitem.SetPannelTitle(object.optString("pannelTitle"));
                        String paragraphlist = object.optString("actParagraph");
                        if (paragraphlist != null) {
                            if (!paragraphlist.equals("")) {
                                JSONArray ParagraphList = new JSONArray(paragraphlist);
                                for (int j = 0; j < ParagraphList.length(); j++) {
                                    JSONObject paragraph_object = ParagraphList.getJSONObject(j);
                                    mpannelitem.AddPannelParagraph(new ActParagraph(paragraph_object.optString("description"), paragraph_object.optInt("paragraphNum"), paragraph_object.optString(SocialConstants.PARAM_IMG_URL), paragraph_object.optString("content")));
                                }
                            }
                        }
                        mActioninfo.AddPannel(mpannelitem);
                    }
                }
            }
            String ranklist = MsgObject.optString("ranking");
            if (ranklist != null) {
                if (!ranklist.equals("")) {
                    JSONArray RankList = new JSONArray(ranklist);
                    for (i = 0; i < RankList.length(); i++) {
                        object = RankList.getJSONObject(i);
                        ActionInfo actionInfo = mActioninfo;
                        actionInfo.AddRank(new ActionRankingItemInfo(object.optInt("count"), object.optString("accountId"), object.optInt("steps"), object.optInt("headimgId"), object.optString("name"), object.optString("headImgUrl")));
                    }
                }
            }
            String myranking = MsgObject.optString("myRanking");
            if (myranking != null) {
                if (!myranking.equals("")) {
                    JSONObject MyRanking = new JSONObject(myranking);
                    mActioninfo.SetMyRankInfo(MyRanking.optInt("count"), MyRanking.optString("accountId"), MyRanking.optInt("steps"), MyRanking.optInt("headimgId"), MyRanking.optString("name"), MyRanking.optString("headImgUrl"));
                }
            }
            String is_join = MsgObject.optString(Tools.SP_SPP_FLAG_KEY_FLAG);
            if (is_join != null) {
                if (!is_join.equals("")) {
                    mActioninfo.SetJoinFlag(Integer.valueOf(is_join).intValue());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mActioninfo;
    }

    private List<ActionListItemInfo> SpiltActionListitem(String result) {
        List<ActionListItemInfo> actionitemlist = new ArrayList();
        try {
            String ActionlistItem = new JSONObject(new JSONObject(result).optString("body")).optString("acts");
            if (!(ActionlistItem == null || ActionlistItem.equals(""))) {
                JSONArray jSONArray = new JSONArray(ActionlistItem);
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject object = jSONArray.getJSONObject(i);
                    actionitemlist.add(new ActionListItemInfo(object.optInt("actId"), object.optString("title"), object.optString("startTime"), object.optString("endTime"), object.optString("curTime"), object.optString("num"), object.optInt(Tools.SP_SPP_FLAG_KEY_FLAG), object.optInt("top"), object.optString("imgUrl")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return actionitemlist;
    }

    private Firmware getFirmware(String result) {
        Firmware ware = new Firmware();
        try {
            JSONObject firmObject = new JSONObject(new JSONObject(new JSONObject(result).optString("body")).optString("bluetooth"));
            ware.setName(firmObject.optString(PromConstants.PROM_HTML5_INFO_PACKAGE_NAME));
            ware.setContent(firmObject.optString("content"));
            ware.setCurrentVer(firmObject.optString(PromConstants.PROM_HTML5_INFO_VERSION_CODE));
            ware.setDescription(firmObject.optString("description"));
            ware.setFileUrl(firmObject.optString("fileUrl"));
            ware.setDatFileUrl(firmObject.optString("batFileURL"));
            ware.setTitle(firmObject.optString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ware;
    }

    public String buildHeadData() {
        String result = "";
        UUID uuid = UUID.randomUUID();
        Header header = new Header();
        header.setBasicVer((byte) 1);
        header.setLength(84);
        header.setType((byte) 1);
        header.setReserved((short) 0);
        header.setFirstTransaction(uuid.getMostSignificantBits());
        header.setSecondTransaction(uuid.getLeastSignificantBits());
        header.setMessageCode(this.msgCode);
        return header.toString();
    }

    public String buildBodyData() {
        JSONObject jsonObjBody = new JSONObject();
        try {
            if (this.params != null && this.params.size() > 0) {
                for (String key : this.params.keySet()) {
                    jsonObjBody.put(key, this.params.get(key));
                }
            }
            return jsonObjBody.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean getSUCCESS(String result) {
        int netResult;
        try {
            netResult = new JSONObject(new JSONObject(result).getString("body")).optInt("result", -1);
        } catch (JSONException e) {
            netResult = -1;
        }
        if (netResult == 0) {
            return true;
        }
        return false;
    }
}
