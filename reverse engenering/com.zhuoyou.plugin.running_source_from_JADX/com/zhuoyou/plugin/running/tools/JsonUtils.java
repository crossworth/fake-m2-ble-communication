package com.zhuoyou.plugin.running.tools;

import com.droi.btlib.connection.MapConstants;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.zhuoyou.plugin.running.bean.ReportBean;
import com.zhuoyou.plugin.running.bean.ReportBean.FirstData;
import com.zhuoyou.plugin.running.bean.ReportBean.Range;
import com.zhuoyou.plugin.running.bean.ReportBean.Record;
import com.zhuoyou.plugin.running.bean.ReportBean.SportData;
import com.zhuoyou.plugin.running.bean.ReportBean.Standard;
import com.zhuoyou.plugin.running.bean.ReportBean.Statistics;
import com.zhuoyou.plugin.running.bean.ReportBean.Step;
import com.zhuoyou.plugin.running.bean.SplashConfig;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static ReportBean getSportReport(String json) {
        try {
            JSONObject object = new JSONObject(json);
            ReportBean bean = new ReportBean();
            bean.firstData = getFirstData(object.optJSONObject("firstData"));
            bean.record = getRecord(object.optJSONObject("record"));
            bean.standard = getStandard(object.optJSONObject("standard"));
            bean.statistics = getStatistics(object.optJSONObject("statistics"));
            bean.steps = getSteps(object.optJSONArray("steps"));
            bean.range = getRanges(object.optJSONObject("range"));
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SportData getSportData(JSONObject object) {
        SportData bean = new SportData();
        bean.account = object.optString("account");
        bean.date = object.optString(MapConstants.DATE);
        bean.stepPhone = object.optInt("stepPhone");
        bean.stepTarget = object.optInt("stepTarget");
        bean.complete = object.optInt("complete");
        bean.distance = (float) object.optDouble("distance");
        bean.cal = (float) object.optDouble("cal");
        return bean;
    }

    public static FirstData getFirstData(JSONObject object) {
        FirstData firstData = new FirstData();
        firstData.data = getSportData(object.optJSONObject("data"));
        return firstData;
    }

    public static Record getRecord(JSONObject object) {
        Record record = new Record();
        record.data = getSportData(object.optJSONObject("data"));
        record.rank = object.optInt("rank");
        record.total = object.optInt("total");
        record.percent = object.optInt("percent");
        return record;
    }

    public static Standard getStandard(JSONObject object) {
        Standard standard = new Standard();
        standard.achieve = object.optInt("achieve");
        standard.total = object.optInt("total");
        return standard;
    }

    public static Statistics getStatistics(JSONObject object) {
        Statistics statistics = new Statistics();
        statistics.total = getSportData(object.optJSONObject("total"));
        statistics.average = getSportData(object.optJSONObject("average"));
        return statistics;
    }

    public static Range getRange(JSONObject object) {
        Range range = new Range();
        range.count = object.optInt(ParamKey.COUNT);
        range.color = object.optInt("color");
        range.label = object.optString("label");
        return range;
    }

    public static List<Range> getRanges(JSONObject object) {
        List<Range> list = new ArrayList();
        JSONArray array = object.optJSONArray("ranges");
        if (array != null && array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                list.add(getRange(array.optJSONObject(i)));
            }
        }
        return list;
    }

    public static Step getStep(JSONObject object) {
        Step step = new Step();
        step.step = object.optInt("step");
        step.date = object.optString(MapConstants.DATE);
        return step;
    }

    public static List<Step> getSteps(JSONArray array) {
        List<Step> list = new ArrayList();
        if (array != null && array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                list.add(getStep(array.optJSONObject(i)));
            }
        }
        return list;
    }

    public static SplashConfig getSplashConfig(String json) {
        try {
            JSONObject object = new JSONObject(json);
            SplashConfig config = new SplashConfig();
            config.imgUrl = object.optString("imgUrl");
            config.url = object.optString("url");
            config.action = object.optInt("action");
            config.showTime = object.optInt("showTime");
            return config;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatJson(String jsonStr) {
        if (jsonStr == null || "".equals(jsonStr)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char current = '\u0000';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            char last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case ',':
                    sb.append(current);
                    if (last == '\\') {
                        break;
                    }
                    sb.append('\n');
                    addIndentBlank(sb, indent);
                    break;
                case '[':
                case '{':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case ']':
                case '}':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                default:
                    sb.append(current);
                    break;
            }
        }
        return sb.toString();
    }

    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}
