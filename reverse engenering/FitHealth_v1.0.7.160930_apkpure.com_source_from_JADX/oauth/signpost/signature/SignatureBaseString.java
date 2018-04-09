package oauth.signpost.signature;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import oauth.signpost.OAuth;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import org.apache.http.HttpHost;

public class SignatureBaseString {
    private HttpRequest request;
    private HttpParameters requestParameters;

    public SignatureBaseString(HttpRequest request, HttpParameters requestParameters) {
        this.request = request;
        this.requestParameters = requestParameters;
    }

    public String generate() throws OAuthMessageSignerException {
        try {
            return this.request.getMethod() + '&' + OAuth.percentEncode(normalizeRequestUrl()) + '&' + OAuth.percentEncode(normalizeRequestParameters());
        } catch (Exception e) {
            throw new OAuthMessageSignerException(e);
        }
    }

    public String normalizeRequestUrl() throws URISyntaxException {
        boolean dropPort;
        URI uri = new URI(this.request.getRequestUrl());
        String scheme = uri.getScheme().toLowerCase();
        String authority = uri.getAuthority().toLowerCase();
        if ((scheme.equals(HttpHost.DEFAULT_SCHEME_NAME) && uri.getPort() == 80) || (scheme.equals("https") && uri.getPort() == 443)) {
            dropPort = true;
        } else {
            dropPort = false;
        }
        if (dropPort) {
            int index = authority.lastIndexOf(":");
            if (index >= 0) {
                authority = authority.substring(0, index);
            }
        }
        String path = uri.getRawPath();
        if (path == null || path.length() <= 0) {
            path = "/";
        }
        return scheme + "://" + authority + path;
    }

    public String normalizeRequestParameters() throws IOException {
        if (this.requestParameters == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String param : this.requestParameters.keySet()) {
            if (!(OAuth.OAUTH_SIGNATURE.equals(param) || "realm".equals(param))) {
                if (i > 0) {
                    sb.append("&");
                }
                sb.append(this.requestParameters.getAsQueryString(param));
            }
            i++;
        }
        return sb.toString();
    }
}
