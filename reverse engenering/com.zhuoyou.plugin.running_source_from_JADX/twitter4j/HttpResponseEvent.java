package twitter4j;

public final class HttpResponseEvent {
    private final HttpRequest request;
    private final HttpResponse response;
    private final TwitterException twitterException;

    HttpResponseEvent(HttpRequest request, HttpResponse response, TwitterException te) {
        this.request = request;
        this.response = response;
        this.twitterException = te;
    }

    public HttpRequest getRequest() {
        return this.request;
    }

    public HttpResponse getResponse() {
        return this.response;
    }

    public TwitterException getTwitterException() {
        return this.twitterException;
    }

    public boolean isAuthenticated() {
        return this.request.getAuthorization().isEnabled();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpResponseEvent that = (HttpResponseEvent) o;
        if (this.request == null ? that.request != null : !this.request.equals(that.request)) {
            return false;
        }
        if (this.response != null) {
            if (this.response.equals(that.response)) {
                return true;
            }
        } else if (that.response == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i = 0;
        if (this.request != null) {
            result = this.request.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.response != null) {
            i = this.response.hashCode();
        }
        return i2 + i;
    }

    public String toString() {
        return "HttpResponseEvent{request=" + this.request + ", response=" + this.response + '}';
    }
}
