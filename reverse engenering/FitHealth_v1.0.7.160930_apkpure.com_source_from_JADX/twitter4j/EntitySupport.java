package twitter4j;

public interface EntitySupport {
    ExtendedMediaEntity[] getExtendedMediaEntities();

    HashtagEntity[] getHashtagEntities();

    MediaEntity[] getMediaEntities();

    SymbolEntity[] getSymbolEntities();

    URLEntity[] getURLEntities();

    UserMentionEntity[] getUserMentionEntities();
}
