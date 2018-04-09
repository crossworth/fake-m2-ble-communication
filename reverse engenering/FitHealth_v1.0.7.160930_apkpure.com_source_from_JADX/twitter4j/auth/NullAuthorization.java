package twitter4j.auth;

import java.io.ObjectStreamException;
import java.io.Serializable;
import twitter4j.HttpRequest;

public class NullAuthorization implements Authorization, Serializable {
    private static final NullAuthorization SINGLETON = new NullAuthorization();
    private static final long serialVersionUID = -7704668493278727510L;

    private NullAuthorization() {
    }

    public static NullAuthorization getInstance() {
        return SINGLETON;
    }

    public String getAuthorizationHeader(HttpRequest req) {
        return null;
    }

    public boolean isEnabled() {
        return false;
    }

    public boolean equals(Object o) {
        return SINGLETON == o;
    }

    public String toString() {
        return "NullAuthentication{SINGLETON}";
    }

    private Object readResolve() throws ObjectStreamException {
        return SINGLETON;
    }
}
