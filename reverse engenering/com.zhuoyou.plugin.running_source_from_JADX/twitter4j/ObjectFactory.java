package twitter4j;

import java.io.Serializable;

interface ObjectFactory extends Serializable {
    Status createStatus(HttpResponse httpResponse) throws TwitterException;

    User createUser(HttpResponse httpResponse) throws TwitterException;
}
