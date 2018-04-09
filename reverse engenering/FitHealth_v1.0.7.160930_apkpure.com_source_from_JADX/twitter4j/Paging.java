package twitter4j;

import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Paging implements Serializable {
    static final String COUNT = "count";
    private static final HttpParameter[] NULL_PARAMETER_ARRAY = new HttpParameter[0];
    private static final List<HttpParameter> NULL_PARAMETER_LIST = new ArrayList(0);
    static final String PER_PAGE = "per_page";
    static final char[] f3499S = new char[]{'s'};
    static final char[] SMCP = new char[]{'s', 'm', 'c', 'p'};
    private static final long serialVersionUID = -7226113618341047983L;
    private int count;
    private long maxId;
    private int page;
    private long sinceId;

    List<HttpParameter> asPostParameterList() {
        return asPostParameterList(SMCP, "count");
    }

    HttpParameter[] asPostParameterArray() {
        List<HttpParameter> list = asPostParameterList(SMCP, "count");
        if (list.size() == 0) {
            return NULL_PARAMETER_ARRAY;
        }
        return (HttpParameter[]) list.toArray(new HttpParameter[list.size()]);
    }

    List<HttpParameter> asPostParameterList(char[] supportedParams) {
        return asPostParameterList(supportedParams, "count");
    }

    List<HttpParameter> asPostParameterList(char[] supportedParams, String perPageParamName) {
        List<HttpParameter> pagingParams = new ArrayList(supportedParams.length);
        addPostParameter(supportedParams, 's', pagingParams, "since_id", getSinceId());
        addPostParameter(supportedParams, 'm', pagingParams, "max_id", getMaxId());
        addPostParameter(supportedParams, 'c', pagingParams, perPageParamName, (long) getCount());
        addPostParameter(supportedParams, 'p', pagingParams, ParamKey.PAGE, (long) getPage());
        if (pagingParams.size() == 0) {
            return NULL_PARAMETER_LIST;
        }
        return pagingParams;
    }

    HttpParameter[] asPostParameterArray(char[] supportedParams, String perPageParamName) {
        List<HttpParameter> pagingParams = new ArrayList(supportedParams.length);
        addPostParameter(supportedParams, 's', pagingParams, "since_id", getSinceId());
        addPostParameter(supportedParams, 'm', pagingParams, "max_id", getMaxId());
        addPostParameter(supportedParams, 'c', pagingParams, perPageParamName, (long) getCount());
        addPostParameter(supportedParams, 'p', pagingParams, ParamKey.PAGE, (long) getPage());
        if (pagingParams.size() == 0) {
            return NULL_PARAMETER_ARRAY;
        }
        return (HttpParameter[]) pagingParams.toArray(new HttpParameter[pagingParams.size()]);
    }

    private void addPostParameter(char[] supportedParams, char paramKey, List<HttpParameter> pagingParams, String paramName, long paramValue) {
        boolean supported = false;
        for (char supportedParam : supportedParams) {
            if (supportedParam == paramKey) {
                supported = true;
                break;
            }
        }
        if (!supported && -1 != paramValue) {
            throw new IllegalStateException("Paging parameter [" + paramName + "] is not supported with this operation.");
        } else if (-1 != paramValue) {
            pagingParams.add(new HttpParameter(paramName, String.valueOf(paramValue)));
        }
    }

    public Paging() {
        this.page = -1;
        this.count = -1;
        this.sinceId = -1;
        this.maxId = -1;
    }

    public Paging(int page) {
        this.page = -1;
        this.count = -1;
        this.sinceId = -1;
        this.maxId = -1;
        setPage(page);
    }

    public Paging(long sinceId) {
        this.page = -1;
        this.count = -1;
        this.sinceId = -1;
        this.maxId = -1;
        setSinceId(sinceId);
    }

    public Paging(int page, int count) {
        this(page);
        setCount(count);
    }

    public Paging(int page, long sinceId) {
        this(page);
        setSinceId(sinceId);
    }

    public Paging(int page, int count, long sinceId) {
        this(page, count);
        setSinceId(sinceId);
    }

    public Paging(int page, int count, long sinceId, long maxId) {
        this(page, count, sinceId);
        setMaxId(maxId);
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        if (page < 1) {
            throw new IllegalArgumentException("page should be positive integer. passed:" + page);
        }
        this.page = page;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count should be positive integer. passed:" + count);
        }
        this.count = count;
    }

    public Paging count(int count) {
        setCount(count);
        return this;
    }

    public long getSinceId() {
        return this.sinceId;
    }

    public void setSinceId(long sinceId) {
        if (sinceId < 1) {
            throw new IllegalArgumentException("since_id should be positive integer. passed:" + sinceId);
        }
        this.sinceId = sinceId;
    }

    public Paging sinceId(long sinceId) {
        setSinceId(sinceId);
        return this;
    }

    public long getMaxId() {
        return this.maxId;
    }

    public void setMaxId(long maxId) {
        if (maxId < 1) {
            throw new IllegalArgumentException("max_id should be positive integer. passed:" + maxId);
        }
        this.maxId = maxId;
    }

    public Paging maxId(long maxId) {
        setMaxId(maxId);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paging)) {
            return false;
        }
        Paging paging = (Paging) o;
        if (this.count != paging.count) {
            return false;
        }
        if (this.maxId != paging.maxId) {
            return false;
        }
        if (this.page != paging.page) {
            return false;
        }
        if (this.sinceId != paging.sinceId) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((this.page * 31) + this.count) * 31) + ((int) (this.sinceId ^ (this.sinceId >>> 32)))) * 31) + ((int) (this.maxId ^ (this.maxId >>> 32)));
    }

    public String toString() {
        return "Paging{page=" + this.page + ", count=" + this.count + ", sinceId=" + this.sinceId + ", maxId=" + this.maxId + '}';
    }
}
