package com.baidu.mapapi.cloud;

import com.sina.weibo.sdk.component.WidgetRequestParam;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class BaseCloudSearchInfo extends BaseSearchInfo {
    public String filter;
    public int pageIndex;
    public int pageSize = 10;
    public String f935q;
    public String sortby;
    public String tags;

    String mo1757a() {
        StringBuilder stringBuilder = new StringBuilder();
        if (super.mo1757a() == null) {
            return null;
        }
        stringBuilder.append(super.mo1757a());
        if (!(this.f935q == null || this.f935q.equals("") || this.f935q.length() > 45)) {
            stringBuilder.append("&");
            stringBuilder.append(WidgetRequestParam.REQ_PARAM_COMMENT_TOPIC);
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(this.f935q, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (!(this.tags == null || this.tags.equals("") || this.tags.length() > 45)) {
            stringBuilder.append("&");
            stringBuilder.append("tags");
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(this.tags, "UTF-8"));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        if (!(this.sortby == null || this.sortby.equals(""))) {
            stringBuilder.append("&");
            stringBuilder.append("sortby");
            stringBuilder.append("=");
            stringBuilder.append(this.sortby);
        }
        if (!(this.filter == null || this.filter.equals(""))) {
            stringBuilder.append("&");
            stringBuilder.append("filter");
            stringBuilder.append("=");
            stringBuilder.append(this.filter);
        }
        if (this.pageIndex >= 0) {
            stringBuilder.append("&");
            stringBuilder.append("page_index");
            stringBuilder.append("=");
            stringBuilder.append(this.pageIndex);
        }
        if (this.pageSize >= 0 && this.pageSize <= 50) {
            stringBuilder.append("&");
            stringBuilder.append("page_size");
            stringBuilder.append("=");
            stringBuilder.append(this.pageSize);
        }
        return stringBuilder.toString();
    }
}
