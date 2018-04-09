package com.weibo.net;

import com.zhuoyi.system.promotion.util.PromConstants;

public class WeiboException extends Exception {
    private static final long serialVersionUID = 475022994858770424L;
    private int statusCode = -1;
    private String statusMessage = "";

    public WeiboException(String msg) {
        super(msg);
    }

    public WeiboException(Exception cause) {
        super(cause);
    }

    public WeiboException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public WeiboException(String msg, Exception cause) {
        super(msg, cause);
    }

    public WeiboException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        String msg = "";
        switch (this.statusCode) {
            case PromConstants.PROM_SEND_SILENT_UPDATE_CODE /*20005*/:
                return "不支持的图片类型,仅仅支持JPG,GIF,PNG";
            case 20006:
                return "图片太大";
            case 20008:
                return "内容为空";
            case 20012:
                return "输入文字太长，请确认不超过140个字符";
            case 20013:
                return "输入文字太长，请确认不超过300个字符";
            case 20016:
                return "发微博太多啦，休息一会儿吧";
            case 20017:
                return "你刚刚已经发送过相似内容了哦，先休息一会吧";
            case 20019:
                return "不要太贪心哦，发一次就够啦";
            case 20023:
                return "很抱歉，此功能暂时无法使用，如需帮助请联系@微博客服 或者致电客服电话400 690 0000";
            case 20032:
                return "微博发布成功。目前服务器数据同步可能会有延迟，请耐心等待1-2分钟。谢谢";
            case 20111:
                return "不能发布相同的微博";
            case 40008:
                return "图片大小错误，上传的图骗不能超过5M";
            case 40012:
                return "内容为空";
            case 40025:
                return "不能发布相同的微博";
            case 40045:
                return "不支持的图片类型,仅仅支持JPG,GIF,PNG";
            case 40090:
                return "发微博太多啦，休息一会儿吧";
            case 40091:
                return "你刚刚已经发送过相似内容了哦，先休息一会吧";
            case 40093:
                return "不要太贪心哦，发一次就够啦";
            case 40098:
                return "微博发布成功。目前服务器数据同步可能会有延迟，请耐心等待1-2分钟。谢谢";
            default:
                return "微博发布失败，请稍候重试";
        }
    }

    public WeiboException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public WeiboException(Throwable throwable) {
        super(throwable);
    }

    public WeiboException(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
