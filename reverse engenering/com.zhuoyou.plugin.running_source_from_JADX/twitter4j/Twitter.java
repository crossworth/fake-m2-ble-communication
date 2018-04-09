package twitter4j;

import java.io.Serializable;
import twitter4j.api.TweetsResources;
import twitter4j.api.UsersResources;
import twitter4j.auth.OAuth2Support;
import twitter4j.auth.OAuthSupport;

public interface Twitter extends Serializable, OAuthSupport, OAuth2Support, TwitterBase, TweetsResources, UsersResources {
    UsersResources users();
}
