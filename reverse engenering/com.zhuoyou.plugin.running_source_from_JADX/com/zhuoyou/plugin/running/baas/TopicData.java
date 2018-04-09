package com.zhuoyou.plugin.running.baas;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

public class TopicData extends DroiObject {
    @DroiExpose
    public String date;
    @DroiExpose
    public String topicContent;
    @DroiExpose
    public String topicId;
}
