package twitter4j;

import java.io.Serializable;

public interface RateLimitStatus extends Serializable {
    int getSecondsUntilReset();
}
