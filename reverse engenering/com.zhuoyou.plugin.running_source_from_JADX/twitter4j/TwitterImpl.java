package twitter4j;

import com.umeng.facebook.internal.ServerProtocol;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import twitter4j.api.UsersResources;
import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;

class TwitterImpl extends TwitterBaseImpl implements Twitter {
    private static final ConcurrentHashMap<Configuration, HttpParameter[]> implicitParamsMap = new ConcurrentHashMap();
    private static final ConcurrentHashMap<Configuration, String> implicitParamsStrMap = new ConcurrentHashMap();
    private static final long serialVersionUID = 9170943084096085770L;
    private final HttpParameter[] IMPLICIT_PARAMS;
    private final String IMPLICIT_PARAMS_STR;
    private final HttpParameter INCLUDE_MY_RETWEET;

    TwitterImpl(Configuration conf, Authorization auth) {
        super(conf, auth);
        this.INCLUDE_MY_RETWEET = new HttpParameter("include_my_retweet", conf.isIncludeMyRetweetEnabled());
        if (implicitParamsMap.containsKey(conf)) {
            this.IMPLICIT_PARAMS = (HttpParameter[]) implicitParamsMap.get(conf);
            this.IMPLICIT_PARAMS_STR = (String) implicitParamsStrMap.get(conf);
            return;
        }
        String implicitParamsStr = conf.isIncludeEntitiesEnabled() ? "include_entities=true" : "";
        boolean contributorsEnabled = conf.getContributingTo() != -1;
        if (contributorsEnabled) {
            if (!"".equals(implicitParamsStr)) {
                implicitParamsStr = implicitParamsStr + "?";
            }
            implicitParamsStr = implicitParamsStr + "contributingto=" + conf.getContributingTo();
        }
        List<HttpParameter> params = new ArrayList(3);
        if (conf.isIncludeEntitiesEnabled()) {
            params.add(new HttpParameter("include_entities", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE));
        }
        if (contributorsEnabled) {
            params.add(new HttpParameter("contributingto", conf.getContributingTo()));
        }
        if (conf.isTrimUserEnabled()) {
            params.add(new HttpParameter("trim_user", "1"));
        }
        if (conf.isIncludeExtAltTextEnabled()) {
            params.add(new HttpParameter("include_ext_alt_text", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE));
        }
        HttpParameter[] implicitParams = (HttpParameter[]) params.toArray(new HttpParameter[params.size()]);
        implicitParamsStrMap.putIfAbsent(conf, implicitParamsStr);
        implicitParamsMap.putIfAbsent(conf, implicitParams);
        this.IMPLICIT_PARAMS = implicitParams;
        this.IMPLICIT_PARAMS_STR = implicitParamsStr;
    }

    public Status updateStatus(String status) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + "statuses/update.json", new HttpParameter("status", status)));
    }

    public Status updateStatus(StatusUpdate status) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + (status.isForUpdateWithMedia() ? "statuses/update_with_media.json" : "statuses/update.json"), status.asHttpParameterArray()));
    }

    private void addParameterToList(List<HttpParameter> colors, String paramName, String color) {
        if (color != null) {
            colors.add(new HttpParameter(paramName, color));
        }
    }

    public User showUser(long userId) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "users/show.json?user_id=" + userId));
    }

    public UsersResources users() {
        return this;
    }

    private HttpResponse get(String url) throws TwitterException {
        ensureAuthorizationEnabled();
        if (this.IMPLICIT_PARAMS_STR.length() > 0) {
            if (url.contains("?")) {
                url = url + "&" + this.IMPLICIT_PARAMS_STR;
            } else {
                url = url + "?" + this.IMPLICIT_PARAMS_STR;
            }
        }
        if (!this.conf.isMBeanEnabled()) {
            return this.http.get(url, null, this.auth, this);
        }
        HttpResponse response = null;
        long start = System.currentTimeMillis();
        try {
            response = this.http.get(url, null, this.auth, this);
            return response;
        } finally {
            long currentTimeMillis = System.currentTimeMillis() - start;
        }
    }

    private HttpResponse get(String url, HttpParameter... params) throws TwitterException {
        ensureAuthorizationEnabled();
        if (!this.conf.isMBeanEnabled()) {
            return this.http.get(url, mergeImplicitParams(params), this.auth, this);
        }
        HttpResponse response = null;
        long start = System.currentTimeMillis();
        try {
            response = this.http.get(url, mergeImplicitParams(params), this.auth, this);
            return response;
        } finally {
            long currentTimeMillis = System.currentTimeMillis() - start;
        }
    }

    private HttpResponse post(String url) throws TwitterException {
        ensureAuthorizationEnabled();
        if (!this.conf.isMBeanEnabled()) {
            return this.http.post(url, this.IMPLICIT_PARAMS, this.auth, this);
        }
        HttpResponse response = null;
        long start = System.currentTimeMillis();
        try {
            response = this.http.post(url, this.IMPLICIT_PARAMS, this.auth, this);
            return response;
        } finally {
            long currentTimeMillis = System.currentTimeMillis() - start;
        }
    }

    private HttpResponse post(String url, HttpParameter... params) throws TwitterException {
        ensureAuthorizationEnabled();
        if (!this.conf.isMBeanEnabled()) {
            return this.http.post(url, mergeImplicitParams(params), this.auth, this);
        }
        HttpResponse response = null;
        long start = System.currentTimeMillis();
        try {
            response = this.http.post(url, mergeImplicitParams(params), this.auth, this);
            return response;
        } finally {
            long currentTimeMillis = System.currentTimeMillis() - start;
        }
    }

    private HttpParameter[] mergeParameters(HttpParameter[] params1, HttpParameter[] params2) {
        if (params1 != null && params2 != null) {
            HttpParameter[] params = new HttpParameter[(params1.length + params2.length)];
            System.arraycopy(params1, 0, params, 0, params1.length);
            System.arraycopy(params2, 0, params, params1.length, params2.length);
            return params;
        } else if (params1 == null && params2 == null) {
            return new HttpParameter[0];
        } else {
            if (params1 != null) {
                return params1;
            }
            return params2;
        }
    }

    private HttpParameter[] mergeImplicitParams(HttpParameter... params) {
        return mergeParameters(params, this.IMPLICIT_PARAMS);
    }

    public String toString() {
        return "TwitterImpl{INCLUDE_MY_RETWEET=" + this.INCLUDE_MY_RETWEET + '}';
    }
}
