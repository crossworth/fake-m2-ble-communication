package okhttp3;

import java.io.IOException;

public interface Authenticator {
    public static final Authenticator NONE = new C19851();

    static class C19851 implements Authenticator {
        C19851() {
        }

        public Request authenticate(Route route, Response response) {
            return null;
        }
    }

    Request authenticate(Route route, Response response) throws IOException;
}
