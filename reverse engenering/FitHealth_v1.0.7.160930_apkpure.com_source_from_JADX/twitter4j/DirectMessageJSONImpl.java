package twitter4j;

import com.umeng.socialize.editorpage.ShareActivity;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import twitter4j.conf.Configuration;

final class DirectMessageJSONImpl extends TwitterResponseImpl implements DirectMessage, Serializable {
    private static final long serialVersionUID = 7092906238192790921L;
    private Date createdAt;
    private ExtendedMediaEntity[] extendedMediaEntities;
    private HashtagEntity[] hashtagEntities;
    private long id;
    private MediaEntity[] mediaEntities;
    private User recipient;
    private long recipientId;
    private String recipientScreenName;
    private User sender;
    private long senderId;
    private String senderScreenName;
    private SymbolEntity[] symbolEntities;
    private String text;
    private URLEntity[] urlEntities;
    private UserMentionEntity[] userMentionEntities;

    DirectMessageJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        JSONObject json = res.asJSONObject();
        init(json);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    DirectMessageJSONImpl(JSONObject json) throws TwitterException {
        init(json);
    }

    private void init(JSONObject json) throws TwitterException {
        this.id = ParseUtil.getLong("id", json);
        this.senderId = ParseUtil.getLong("sender_id", json);
        this.recipientId = ParseUtil.getLong("recipient_id", json);
        this.createdAt = ParseUtil.getDate(SessionColumns.CREATED_AT, json);
        this.senderScreenName = ParseUtil.getUnescapedString("sender_screen_name", json);
        this.recipientScreenName = ParseUtil.getUnescapedString("recipient_screen_name", json);
        try {
            this.sender = new UserJSONImpl(json.getJSONObject(MessageObj.SENDER));
            this.recipient = new UserJSONImpl(json.getJSONObject("recipient"));
            if (!json.isNull("entities")) {
                int len;
                int i;
                JSONObject entities = json.getJSONObject("entities");
                if (!entities.isNull("user_mentions")) {
                    JSONArray userMentionsArray = entities.getJSONArray("user_mentions");
                    len = userMentionsArray.length();
                    this.userMentionEntities = new UserMentionEntity[len];
                    for (i = 0; i < len; i++) {
                        this.userMentionEntities[i] = new UserMentionEntityJSONImpl(userMentionsArray.getJSONObject(i));
                    }
                }
                if (!entities.isNull("urls")) {
                    JSONArray urlsArray = entities.getJSONArray("urls");
                    len = urlsArray.length();
                    this.urlEntities = new URLEntity[len];
                    for (i = 0; i < len; i++) {
                        this.urlEntities[i] = new URLEntityJSONImpl(urlsArray.getJSONObject(i));
                    }
                }
                if (!entities.isNull("hashtags")) {
                    JSONArray hashtagsArray = entities.getJSONArray("hashtags");
                    len = hashtagsArray.length();
                    this.hashtagEntities = new HashtagEntity[len];
                    for (i = 0; i < len; i++) {
                        this.hashtagEntities[i] = new HashtagEntityJSONImpl(hashtagsArray.getJSONObject(i));
                    }
                }
                if (!entities.isNull("symbols")) {
                    JSONArray symbolsArray = entities.getJSONArray("symbols");
                    len = symbolsArray.length();
                    this.symbolEntities = new SymbolEntity[len];
                    for (i = 0; i < len; i++) {
                        this.symbolEntities[i] = new HashtagEntityJSONImpl(symbolsArray.getJSONObject(i));
                    }
                }
                if (!entities.isNull(ShareActivity.KEY_PLATFORM)) {
                    JSONArray mediaArray = entities.getJSONArray(ShareActivity.KEY_PLATFORM);
                    len = mediaArray.length();
                    this.mediaEntities = new MediaEntity[len];
                    for (i = 0; i < len; i++) {
                        this.mediaEntities[i] = new MediaEntityJSONImpl(mediaArray.getJSONObject(i));
                    }
                }
            }
            this.userMentionEntities = this.userMentionEntities == null ? new UserMentionEntity[0] : this.userMentionEntities;
            this.urlEntities = this.urlEntities == null ? new URLEntity[0] : this.urlEntities;
            this.hashtagEntities = this.hashtagEntities == null ? new HashtagEntity[0] : this.hashtagEntities;
            this.symbolEntities = this.symbolEntities == null ? new SymbolEntity[0] : this.symbolEntities;
            this.mediaEntities = this.mediaEntities == null ? new MediaEntity[0] : this.mediaEntities;
            this.extendedMediaEntities = this.extendedMediaEntities == null ? new ExtendedMediaEntity[0] : this.extendedMediaEntities;
            this.text = HTMLEntity.unescapeAndSlideEntityIncdices(json.getString(MessageObj.SUBTYPE_NOTI), this.userMentionEntities, this.urlEntities, this.hashtagEntities, this.mediaEntities);
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public long getSenderId() {
        return this.senderId;
    }

    public long getRecipientId() {
        return this.recipientId;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public String getSenderScreenName() {
        return this.senderScreenName;
    }

    public String getRecipientScreenName() {
        return this.recipientScreenName;
    }

    public User getSender() {
        return this.sender;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public UserMentionEntity[] getUserMentionEntities() {
        return this.userMentionEntities;
    }

    public URLEntity[] getURLEntities() {
        return this.urlEntities;
    }

    public HashtagEntity[] getHashtagEntities() {
        return this.hashtagEntities;
    }

    public MediaEntity[] getMediaEntities() {
        return this.mediaEntities;
    }

    public ExtendedMediaEntity[] getExtendedMediaEntities() {
        return this.extendedMediaEntities;
    }

    public SymbolEntity[] getSymbolEntities() {
        return this.symbolEntities;
    }

    static ResponseList<DirectMessage> createDirectMessageList(HttpResponse res, Configuration conf) throws TwitterException {
        try {
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            JSONArray list = res.asJSONArray();
            int size = list.length();
            ResponseList<DirectMessage> directMessages = new ResponseListImpl(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                DirectMessage directMessage = new DirectMessageJSONImpl(json);
                directMessages.add(directMessage);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(directMessage, json);
                }
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(directMessages, list);
            }
            return directMessages;
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public int hashCode() {
        return (int) this.id;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if ((obj instanceof DirectMessage) && ((DirectMessage) obj).getId() == this.id) {
            return true;
        }
        return false;
    }

    public String toString() {
        Object obj;
        Object obj2 = null;
        StringBuilder append = new StringBuilder().append("DirectMessageJSONImpl{id=").append(this.id).append(", text='").append(this.text).append('\'').append(", sender_id=").append(this.senderId).append(", recipient_id=").append(this.recipientId).append(", created_at=").append(this.createdAt).append(", userMentionEntities=");
        if (this.userMentionEntities == null) {
            obj = null;
        } else {
            obj = Arrays.asList(this.userMentionEntities);
        }
        append = append.append(obj).append(", urlEntities=");
        if (this.urlEntities == null) {
            obj = null;
        } else {
            obj = Arrays.asList(this.urlEntities);
        }
        append = append.append(obj).append(", hashtagEntities=");
        if (this.hashtagEntities == null) {
            obj = null;
        } else {
            obj = Arrays.asList(this.hashtagEntities);
        }
        append = append.append(obj).append(", sender_screen_name='").append(this.senderScreenName).append('\'').append(", recipient_screen_name='").append(this.recipientScreenName).append('\'').append(", sender=").append(this.sender).append(", recipient=").append(this.recipient).append(", userMentionEntities=");
        if (this.userMentionEntities == null) {
            obj = null;
        } else {
            obj = Arrays.asList(this.userMentionEntities);
        }
        append = append.append(obj).append(", urlEntities=");
        if (this.urlEntities == null) {
            obj = null;
        } else {
            obj = Arrays.asList(this.urlEntities);
        }
        StringBuilder append2 = append.append(obj).append(", hashtagEntities=");
        if (this.hashtagEntities != null) {
            obj2 = Arrays.asList(this.hashtagEntities);
        }
        return append2.append(obj2).append('}').toString();
    }
}
