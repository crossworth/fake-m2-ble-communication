package twitter4j;

import com.tyd.aidlservice.internal.Constants;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import twitter4j.auth.Authorization;

public abstract class HttpClientBase implements HttpClient, Serializable {
    private static final long serialVersionUID = -8016974810651763053L;
    protected final HttpClientConfiguration CONF;
    private final Map<String, String> requestHeaders = new HashMap();

    abstract HttpResponse handleRequest(HttpRequest httpRequest) throws TwitterException;

    public HttpClientBase(HttpClientConfiguration conf) {
        this.CONF = conf;
        this.requestHeaders.put("X-Twitter-Client-Version", Version.getVersion());
        this.requestHeaders.put("X-Twitter-Client-URL", "http://twitter4j.org/en/twitter4j-" + Version.getVersion() + ".xml");
        this.requestHeaders.put("X-Twitter-Client", "Twitter4J");
        this.requestHeaders.put("User-Agent", "twitter4j http://twitter4j.org/ /" + Version.getVersion());
        if (conf.isGZIPEnabled()) {
            this.requestHeaders.put("Accept-Encoding", Constants.GZIP_TAG);
        }
    }

    protected boolean isProxyConfigured() {
        return (this.CONF.getHttpProxyHost() == null || this.CONF.getHttpProxyHost().equals("")) ? false : true;
    }

    public void write(DataOutputStream out, String outStr) throws IOException {
        out.writeBytes(outStr);
    }

    public Map<String, String> getRequestHeaders() {
        return this.requestHeaders;
    }

    public void addDefaultRequestHeader(String name, String value) {
        this.requestHeaders.put(name, value);
    }

    public final HttpResponse request(HttpRequest req) throws TwitterException {
        return handleRequest(req);
    }

    public final HttpResponse request(HttpRequest req, HttpResponseListener listener) throws TwitterException {
        try {
            HttpResponse res = handleRequest(req);
            if (listener != null) {
                listener.httpResponseReceived(new HttpResponseEvent(req, res, null));
            }
            return res;
        } catch (TwitterException te) {
            if (listener != null) {
                listener.httpResponseReceived(new HttpResponseEvent(req, null, te));
            }
            throw te;
        }
    }

    public HttpResponse get(String url, HttpParameter[] parameters, Authorization authorization, HttpResponseListener listener) throws TwitterException {
        return request(new HttpRequest(RequestMethod.GET, url, parameters, authorization, this.requestHeaders), listener);
    }

    public HttpResponse get(String url) throws TwitterException {
        return request(new HttpRequest(RequestMethod.GET, url, null, null, this.requestHeaders));
    }

    public HttpResponse post(String url, HttpParameter[] parameters, Authorization authorization, HttpResponseListener listener) throws TwitterException {
        return request(new HttpRequest(RequestMethod.POST, url, parameters, authorization, this.requestHeaders), listener);
    }

    public HttpResponse post(String url) throws TwitterException {
        return request(new HttpRequest(RequestMethod.POST, url, null, null, this.requestHeaders));
    }

    public HttpResponse delete(String url, HttpParameter[] parameters, Authorization authorization, HttpResponseListener listener) throws TwitterException {
        return request(new HttpRequest(RequestMethod.DELETE, url, parameters, authorization, this.requestHeaders), listener);
    }

    public HttpResponse delete(String url) throws TwitterException {
        return request(new HttpRequest(RequestMethod.DELETE, url, null, null, this.requestHeaders));
    }

    public HttpResponse head(String url) throws TwitterException {
        return request(new HttpRequest(RequestMethod.HEAD, url, null, null, this.requestHeaders));
    }

    public HttpResponse put(String url, HttpParameter[] parameters, Authorization authorization, HttpResponseListener listener) throws TwitterException {
        return request(new HttpRequest(RequestMethod.PUT, url, parameters, authorization, this.requestHeaders), listener);
    }

    public HttpResponse put(String url) throws TwitterException {
        return request(new HttpRequest(RequestMethod.PUT, url, null, null, this.requestHeaders));
    }
}
