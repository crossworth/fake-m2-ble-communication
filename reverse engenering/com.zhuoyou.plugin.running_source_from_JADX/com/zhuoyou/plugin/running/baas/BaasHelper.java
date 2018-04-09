package com.zhuoyou.plugin.running.baas;

import android.os.Handler;
import android.util.Log;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.droi.btlib.connection.MapConstants;
import com.droi.greendao.bean.GpsPointBean;
import com.droi.greendao.bean.HeartBean;
import com.droi.greendao.bean.SleepBean;
import com.droi.greendao.bean.SportBean;
import com.droi.greendao.bean.WeightBean;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCloud;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiCondition.Type;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiPermission;
import com.droi.sdk.core.DroiQuery.Builder;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;
import com.tencent.healthsdk.QQHealthCallback;
import com.tencent.healthsdk.QQHealthManager;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.baas.CommentCloud.CommentRequest;
import com.zhuoyou.plugin.running.baas.CommentCloud.CommentResponse;
import com.zhuoyou.plugin.running.baas.Rank.Request;
import com.zhuoyou.plugin.running.baas.Rank.Response;
import com.zhuoyou.plugin.running.bean.EventGetHeart;
import com.zhuoyou.plugin.running.bean.EventGetRank;
import com.zhuoyou.plugin.running.bean.EventGetSleep;
import com.zhuoyou.plugin.running.bean.EventGetStatis;
import com.zhuoyou.plugin.running.bean.EventGetWeight;
import com.zhuoyou.plugin.running.bean.EventStep;
import com.zhuoyou.plugin.running.database.GpsPointHelper;
import com.zhuoyou.plugin.running.database.HeartHelper;
import com.zhuoyou.plugin.running.database.SleepHelper;
import com.zhuoyou.plugin.running.database.SportHelper;
import com.zhuoyou.plugin.running.database.WeightHelper;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

public class BaasHelper {
    public static final int QQ_SUCCESSFULL = 0;
    public static String WECHART_URL = "http://weixin.droi.com/health/phone/index.php/SendWechat/send";
    private static final Handler mHandler = new Handler();

    public interface CommentCallback {
        void callback(DroiError droiError);
    }

    public interface QQCallback {
        void callback(boolean z, int i, JSONObject jSONObject);
    }

    static class C18682 implements DroiQueryCallback<SportBean> {
        C18682() {
        }

        public void result(List<SportBean> list, DroiError droiError) {
            if (droiError.isOk() && list.size() > 0) {
                for (SportBean bean : list) {
                    bean.setSync(1);
                    SportHelper.getBeanDao().insertOrReplace(bean);
                }
                EventBus.getDefault().post(new EventGetStatis());
                EventBus.getDefault().post(new EventStep(false));
            }
        }
    }

    static class C18693 implements UploadCallBack<SportBean> {
        C18693() {
        }

        public void result(List<SportBean> list, DroiError error) {
            if (error.isOk()) {
                for (SportBean bean : list) {
                    bean.setSync(1);
                    SportHelper.getBeanDao().insertOrReplace(bean);
                }
                Log.i("yuanzi", "EventBus post uploadSportInBackground");
                EventBus.getDefault().post(new EventGetRank());
                BaasHelper.uploadWechartStep(DroiUser.getCurrentUser().getUserId(), SportHelper.getTodaySport().getStepPhone());
            }
        }
    }

    static class C18704 implements QQCallback {
        C18704() {
        }

        public void callback(boolean success, int code, JSONObject obj) {
            if (success) {
                SPUtils.setUploadQQTime(System.currentTimeMillis(), DroiUser.getCurrentUser().getUserId());
                Log.i("chenxin", "auto update qq health success");
            } else if (code == -73) {
                Tools.makeToast((int) C1680R.string.qq_pw_check_fail);
                SPUtils.clearQQInfo(DroiUser.getCurrentUser().getUserId());
            }
        }
    }

    static class C18715 implements Listener<String> {
        C18715() {
        }

        public void onResponse(String s) {
            Log.i("wechart", "response:" + s);
        }
    }

    static class C18726 implements ErrorListener {
        C18726() {
        }

        public void onErrorResponse(VolleyError volleyError) {
        }
    }

    static class C18737 implements UploadCallBack<SleepBean> {
        C18737() {
        }

        public void result(List<SleepBean> list, DroiError error) {
            if (error.isOk()) {
                for (SleepBean bean : list) {
                    bean.setSync(1);
                    SleepHelper.getBeanDao().insertOrReplace(bean);
                }
            }
        }
    }

    static class C18748 implements DroiQueryCallback<SleepBean> {
        C18748() {
        }

        public void result(List<SleepBean> list, DroiError droiError) {
            if (droiError.isOk() && list.size() > 0) {
                for (SleepBean bean : list) {
                    bean.setSync(1);
                    SleepHelper.getBeanDao().insertOrReplace(bean);
                }
                EventBus.getDefault().post(new EventGetSleep());
            }
        }
    }

    static class C18759 implements UploadCallBack<WeightBean> {
        C18759() {
        }

        public void result(List<WeightBean> list, DroiError error) {
            if (error.isOk()) {
                for (WeightBean bean : list) {
                    bean.setSync(1);
                    WeightHelper.getBeanDao().insertOrReplace(bean);
                }
            }
        }
    }

    public static class CommentBean {
        public String comment;
        public String commentAdd;
        public boolean ifReply = false;
        public User toUser;
        private String topic;

        public CommentBean(String topic) {
            this.topic = topic;
        }

        public void clear() {
            this.ifReply = false;
            this.toUser = null;
            this.commentAdd = null;
            this.comment = null;
        }
    }

    public static <T extends BaseObject> void saveOrUpdateListInBackground(final List<T> dataList, DroiCondition cond, final UploadCallBack<T> callBack) {
        if (dataList != null && dataList.size() > 0) {
            Builder.newBuilder().query(((BaseObject) dataList.get(0)).getClass()).where(cond).build().runQueryInBackground(new DroiQueryCallback<T>() {
                private List<T> saveList;

                class C18631 implements DroiCallback<Boolean> {
                    C18631() {
                    }

                    public void result(Boolean aBoolean, DroiError droiError) {
                        if (callBack != null) {
                            callBack.result(C18671.this.saveList, droiError);
                        }
                    }
                }

                public void result(List<T> list, DroiError droiError) {
                    if (droiError.isOk()) {
                        if (list.size() > 0) {
                            Log.i("yuanzi", "list size > 0");
                            this.saveList = BaasHelper.getSaveList(list, dataList);
                        } else {
                            Log.i("yuanzi", "list size <= 0");
                            this.saveList = dataList;
                        }
                        DroiObject.saveAllInBackground(this.saveList, new C18631());
                    } else if (callBack != null) {
                        callBack.result(new ArrayList(), droiError);
                    }
                }
            });
        } else if (callBack != null) {
            callBack.result(new ArrayList(), new DroiError(DroiError.ERROR, "Error"));
        }
    }

    public static <T extends BaseObject> DroiError saveOrUpdateList(List<T> dataList, DroiCondition cond) {
        DroiError error = new DroiError();
        if (dataList == null || dataList.size() <= 0) {
            return error;
        }
        List<T> queryList = Builder.newBuilder().query(((BaseObject) dataList.get(0)).getClass()).where(cond).build().runQuery(error);
        if (!error.isOk()) {
            return error;
        }
        if (queryList.size() > 0) {
            return DroiObject.saveAll(getSaveList(queryList, dataList));
        }
        return DroiObject.saveAll(dataList);
    }

    private static <T extends BaseObject> List<T> getSaveList(List<T> baasList, List<T> localList) {
        List<T> saveList = new ArrayList();
        for (T dataItem : localList) {
            boolean hasAdd = false;
            for (T listItem : baasList) {
                if (listItem.ifequals(dataItem)) {
                    listItem.copy(dataItem);
                    saveList.add(listItem);
                    hasAdd = true;
                }
            }
            if (!hasAdd) {
                saveList.add(dataItem);
            }
        }
        return saveList;
    }

    public static void getSportInBackground() {
        getSportInBackground(new C18682());
    }

    public static void getSportInBackground(DroiQueryCallback<SportBean> callback) {
        Builder.newBuilder().query(SportBean.class).orderBy(MapConstants.DATE, Boolean.valueOf(false)).where(DroiCondition.cond("account", Type.EQ, DroiUser.getCurrentUser().getUserId())).limit(100).build().runQueryInBackground(callback);
    }

    public static void uploadSportInBackground() {
        uploadSportInBackground(new C18693());
    }

    public static void uploadSportInBackground(UploadCallBack<SportBean> callBack) {
        List<SportBean> list = SportHelper.getUploadList();
        if (list == null || list.size() <= 0) {
            callBack.result(new ArrayList(), new DroiError());
            return;
        }
        saveOrUpdateListInBackground(list, DroiCondition.cond(MapConstants.DATE, Type.GT_OR_EQ, ((SportBean) list.get(0)).getDate()).and(DroiCondition.cond("account", Type.EQ, DroiUser.getCurrentUser().getUserId())), callBack);
        if (Tools.isQQLogin()) {
            updateQQHealthStep(new C18704());
        }
    }

    public static void uploadWechartStep(String acount, int step) {
        String url = WECHART_URL + "?" + "accountId=" + acount + "&" + "jibuNuber=" + step;
        Log.i("wechart", "url:" + url);
        TheApp.getRequestQueue().add(new StringRequest(url, new C18715(), new C18726()));
    }

    public static void uploadSleepInBackground() {
        List<SleepBean> list = SleepHelper.getUploadList();
        if (list != null && list.size() > 0) {
            saveOrUpdateListInBackground(list, DroiCondition.cond("startTime", Type.GT_OR_EQ, ((SleepBean) list.get(0)).getStartTime()).and(DroiCondition.cond("account", Type.EQ, DroiUser.getCurrentUser().getUserId())), new C18737());
        }
    }

    public static void getSleepInBackground() {
        getSleepInBackground(new C18748());
    }

    public static void getSleepInBackground(DroiQueryCallback<SleepBean> callback) {
        Builder.newBuilder().query(SleepBean.class).orderBy("startTime", Boolean.valueOf(false)).where(DroiCondition.cond("account", Type.EQ, DroiUser.getCurrentUser().getUserId())).limit(100).build().runQueryInBackground(callback);
    }

    public static void uploadWeightInBackground() {
        List<WeightBean> list = WeightHelper.getUploadList();
        if (list != null && list.size() > 0) {
            saveOrUpdateListInBackground(list, DroiCondition.cond(MapConstants.DATE, Type.GT_OR_EQ, ((WeightBean) list.get(0)).getDate()).and(DroiCondition.cond("account", Type.EQ, DroiUser.getCurrentUser().getUserId())), new C18759());
        }
    }

    public static void getWeightInBackground() {
        Builder.newBuilder().query(WeightBean.class).orderBy(MapConstants.DATE, Boolean.valueOf(false)).where(DroiCondition.cond("account", Type.EQ, DroiUser.getCurrentUser().getUserId())).limit(7).build().runQueryInBackground(new DroiQueryCallback<WeightBean>() {
            public void result(List<WeightBean> list, DroiError droiError) {
                if (droiError.isOk() && list.size() > 0) {
                    for (WeightBean bean : list) {
                        bean.setSync(1);
                        WeightHelper.getBeanDao().insertOrReplace(bean);
                    }
                    EventBus.getDefault().post(new EventGetWeight());
                }
            }
        });
    }

    public static void deleteWeightInBackground(WeightBean bean, DroiCallback<Boolean> callback) {
        bean.deleteInBackground(DroiCondition.cond(MapConstants.DATE, Type.GT_OR_EQ, bean.getDate()).and(DroiCondition.cond("account", Type.EQ, bean.getAccount())), callback);
    }

    public static void uploadHeartInBackground() {
        List<HeartBean> list = HeartHelper.getUploadList();
        if (list != null && list.size() > 0) {
            saveOrUpdateListInBackground(list, DroiCondition.cond(MapConstants.DATE, Type.GT_OR_EQ, ((HeartBean) list.get(0)).getDate()).and(DroiCondition.cond("account", Type.EQ, DroiUser.getCurrentUser().getUserId())), new UploadCallBack<HeartBean>() {
                public void result(List<HeartBean> list, DroiError error) {
                    if (error.isOk()) {
                        for (HeartBean bean : list) {
                            bean.setSync(1);
                            HeartHelper.getBeanDao().insertOrReplace(bean);
                        }
                    }
                }
            });
        }
    }

    public static void getHeartInBackground() {
        Builder.newBuilder().query(HeartBean.class).orderBy(MapConstants.DATE, Boolean.valueOf(false)).where(DroiCondition.cond("account", Type.EQ, DroiUser.getCurrentUser().getUserId())).limit(20).build().runQueryInBackground(new DroiQueryCallback<HeartBean>() {
            public void result(List<HeartBean> list, DroiError droiError) {
                if (droiError.isOk() && list.size() > 0) {
                    Log.i("zhuqichao", "list.size()=" + list.size());
                    for (HeartBean bean : list) {
                        bean.setSync(1);
                        HeartHelper.getBeanDao().insertOrReplace(bean);
                    }
                    EventBus.getDefault().post(new EventGetHeart());
                }
            }
        });
    }

    public static void deleteHeartInBackground(HeartBean bean, DroiCallback<Boolean> callback) {
        bean.deleteInBackground(DroiCondition.cond(MapConstants.DATE, Type.GT_OR_EQ, bean.getDate()).and(DroiCondition.cond("account", Type.EQ, bean.getAccount())), callback);
    }

    public static void uploadGpsPointsInBackground(String sportid) {
        final List<GpsPointBean> list = GpsPointHelper.getUploadList(sportid);
        DroiObject.saveAllInBackground(list, new DroiCallback<Boolean>() {
            public void result(Boolean aBoolean, DroiError droiError) {
                if (droiError.isOk()) {
                    for (GpsPointBean bean : list) {
                        bean.setSync(1);
                        GpsPointHelper.getBeanDao().insertOrReplace(bean);
                    }
                }
            }
        });
    }

    public static void uploadGpsPointsInBackground() {
        final List<GpsPointBean> list = GpsPointHelper.getUploadList();
        DroiObject.saveAllInBackground(list, new DroiCallback<Boolean>() {
            public void result(Boolean aBoolean, DroiError droiError) {
                if (droiError.isOk()) {
                    for (GpsPointBean bean : list) {
                        bean.setSync(1);
                        GpsPointHelper.getBeanDao().insertOrReplace(bean);
                    }
                }
            }
        });
    }

    public static RankZan getZan(User toUser) {
        RankZan zan = new RankZan();
        DroiPermission permission = new DroiPermission();
        permission.setPublicReadPermission(true);
        permission.setPublicWritePermission(true);
        zan.setPermission(permission);
        zan.setAccount(DroiUser.getCurrentUser().getUserId());
        zan.setUser((User) DroiUser.getCurrentUser(User.class));
        zan.setToAccount(toUser.getUserId());
        zan.setToUser(toUser);
        zan.setDate(Tools.getToday());
        return zan;
    }

    private static DroiCondition getZanCondition(RankZan zan) {
        return DroiCondition.cond(MapConstants.DATE, Type.GT_OR_EQ, zan.getDate()).and(DroiCondition.cond("account", Type.EQ, zan.getAccount())).and(DroiCondition.cond("toAccount", Type.EQ, zan.getToAccount()));
    }

    public static void saveZan(RankZan zan, DroiCallback<Boolean> callback) {
        zan.saveOrUpdateInBackground(getZanCondition(zan), callback);
    }

    public static void deleteZan(RankZan zan, DroiCallback<Boolean> callback) {
        zan.deleteInBackground(getZanCondition(zan), callback);
    }

    public static void getRankList(int min, DroiCallback<Response> callback) {
        Request request = new Request();
        request.account = DroiUser.getCurrentUser().getUserId();
        request.min = min;
        DroiCloud.callCloudServiceInBackground("rank_zan.lua", request, callback, Response.class);
    }

    public static void deleteGpsSport(String id, DroiCallback<DeleteGpsSport.Response> callback) {
        DeleteGpsSport.Request request = new DeleteGpsSport.Request();
        request.account = DroiUser.getCurrentUser().getUserId();
        request.sportid = id;
        DroiCloud.callCloudServiceInBackground("delete_gps_sport_by_id.lua", request, callback, DeleteGpsSport.Response.class);
    }

    public static void getFishRankList(DroiCallback<FishRank.Response> paramDroiCallback) {
        FishRank.Request localRequest = new FishRank.Request();
        localRequest.account = DroiUser.getCurrentUser().getUserId();
        DroiCloud.callCloudServiceInBackground("fish_rank.lua", localRequest, paramDroiCallback, FishRank.Response.class);
    }

    public static void getSportsReportTotal(DroiCallback<SportsReport.Response> callback) {
        SportsReport.Request request = new SportsReport.Request();
        request.type = 0;
        request.account = DroiUser.getCurrentUser().getUserId();
        DroiCloud.callCloudServiceInBackground("sports_report.lua", request, callback, SportsReport.Response.class);
    }

    public static void callCloudAPI(int code, HashMap<String, Object> params, DroiCallback<CloudAPI.Response> callback) {
        CloudAPI.Request request = new CloudAPI.Request();
        request.code = code;
        if (params != null) {
            request.param = new JSONObject(params).toString();
        }
        DroiCloud.callCloudServiceInBackground(CloudCode.LUA_NAME, request, callback, CloudAPI.Response.class);
    }

    public static void getServerTime(DroiCallback<CloudAPI.Response> callback) {
        callCloudAPI(CloudCode.CODE_GET_SERVER_TIME, null, callback);
    }

    public static void getSplashConfig(DroiCallback<CloudAPI.Response> callback) {
        callCloudAPI(CloudCode.CODE_GET_SPLASH_CONFIG, null, callback);
    }

    public static void getCommentList(DroiCallback<CommentResponse> callback, String topicId, String date) {
        CommentRequest request = new CommentRequest();
        request.topicId = topicId;
        request.date = date;
        request.accountId = DroiUser.getCurrentUser().getUserId();
        DroiCloud.callCloudServiceInBackground("comment_list.lua", request, callback, CommentResponse.class);
    }

    public static void updateQQHealthStep(QQCallback callback) {
        updateQQHealthStep(callback, true);
    }

    public static void updateQQHealthStep(final QQCallback callback, final boolean retrans) {
        QQHealthManager.getInstance().uploadHealthData(TheApp.getContext(), new QQHealthCallback() {
            public String getHealthData() {
                String healthDate = SportHelper.getQQHealthData();
                Log.i("chenxinX", "数据准备：" + healthDate);
                return healthDate;
            }

            public void onComplete(final JSONObject jsonObject) {
                Log.i("chenxinX", "数据上传完成：" + jsonObject);
                final int code = Tools.dealQQjson(jsonObject);
                if (code == 0) {
                    BaasHelper.mHandler.post(new Runnable() {
                        public void run() {
                            SPUtils.setUploadQQTime(System.currentTimeMillis(), DroiUser.getCurrentUser().getUserId());
                            callback.callback(true, code, jsonObject);
                        }
                    });
                } else if (code == -73) {
                    BaasHelper.mHandler.post(new Runnable() {
                        public void run() {
                            callback.callback(false, code, jsonObject);
                        }
                    });
                } else {
                    BaasHelper.mHandler.post(new Runnable() {
                        public void run() {
                            if (retrans) {
                                BaasHelper.updateQQHealthStep(callback, false);
                            } else {
                                callback.callback(false, code, jsonObject);
                            }
                        }
                    });
                }
            }
        });
    }

    public static void expressComment(CommentBean comment, final CommentCallback callback) {
        CommentData commentData = new CommentData();
        commentData.setDate(Tools.getCurrentTimeByZeroZone());
        commentData.setComment(comment.comment);
        commentData.setUser((User) DroiUser.getCurrentUser(User.class));
        commentData.setTopicId(comment.topic);
        DroiPermission permission = new DroiPermission();
        permission.setPublicReadPermission(true);
        commentData.setPermission(permission);
        if (comment.ifReply) {
            commentData.setToUser(comment.toUser);
            commentData.setCommentAdd(comment.commentAdd);
        }
        commentData.saveInBackground(new DroiCallback<Boolean>() {
            public void result(Boolean aBoolean, DroiError droiError) {
                callback.callback(droiError);
            }
        });
    }
}
