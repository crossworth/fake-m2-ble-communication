package com.sina.weibo.sdk.statistic;

import java.util.Map;

class EventLog extends PageLog {
    private String mEvent_id;
    private Map<String, String> mExtend;

    public EventLog(String pageName, String eventId, Map<String, String> extend) {
        super(pageName);
        this.mEvent_id = eventId;
        this.mExtend = extend;
    }

    public String getEvent_id() {
        return this.mEvent_id;
    }

    public Map<String, String> getExtend() {
        return this.mExtend;
    }
}
