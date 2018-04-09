package twitter4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import twitter4j.auth.Authorization;

public final class HttpRequest implements Serializable {
    private static final HttpParameter[] NULL_PARAMETERS = new HttpParameter[0];
    private static final long serialVersionUID = 3365496352032493020L;
    private final Authorization authorization;
    private final RequestMethod method;
    private final HttpParameter[] parameters;
    private final Map<String, String> requestHeaders;
    private final String url;

    public HttpRequest(RequestMethod method, String url, HttpParameter[] parameters, Authorization authorization, Map<String, String> requestHeaders) {
        this.method = method;
        if (method == RequestMethod.POST || parameters == null || parameters.length == 0) {
            this.url = url;
            this.parameters = parameters;
        } else {
            this.url = url + "?" + HttpParameter.encodeParameters(parameters);
            this.parameters = NULL_PARAMETERS;
        }
        this.authorization = authorization;
        this.requestHeaders = requestHeaders;
    }

    public RequestMethod getMethod() {
        return this.method;
    }

    public HttpParameter[] getParameters() {
        return this.parameters;
    }

    public String getURL() {
        return this.url;
    }

    public Authorization getAuthorization() {
        return this.authorization;
    }

    public Map<String, String> getRequestHeaders() {
        return this.requestHeaders;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpRequest that = (HttpRequest) o;
        if (this.authorization == null ? that.authorization != null : !this.authorization.equals(that.authorization)) {
            return false;
        }
        if (!Arrays.equals(this.parameters, that.parameters)) {
            return false;
        }
        if (this.requestHeaders == null ? that.requestHeaders != null : !this.requestHeaders.equals(that.requestHeaders)) {
            return false;
        }
        if (this.method == null ? that.method != null : !this.method.equals(that.method)) {
            return false;
        }
        if (this.url != null) {
            if (this.url.equals(that.url)) {
                return true;
            }
        } else if (that.url == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 0;
        if (this.method != null) {
            result = this.method.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.url != null) {
            hashCode = this.url.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.parameters != null) {
            hashCode = Arrays.hashCode(this.parameters);
        } else {
            hashCode = 0;
        }
        i2 = (i2 + hashCode) * 31;
        if (this.authorization != null) {
            hashCode = this.authorization.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (this.requestHeaders != null) {
            i = this.requestHeaders.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        Object obj;
        StringBuilder append = new StringBuilder().append("HttpRequest{requestMethod=").append(this.method).append(", url='").append(this.url).append('\'').append(", postParams=");
        if (this.parameters == null) {
            obj = null;
        } else {
            obj = Arrays.asList(this.parameters);
        }
        return append.append(obj).append(", authentication=").append(this.authorization).append(", requestHeaders=").append(this.requestHeaders).append('}').toString();
    }
}
