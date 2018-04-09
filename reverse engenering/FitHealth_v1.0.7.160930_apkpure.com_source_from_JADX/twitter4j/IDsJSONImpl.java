package twitter4j;

import java.util.Arrays;
import twitter4j.conf.Configuration;

final class IDsJSONImpl extends TwitterResponseImpl implements IDs {
    private static final long serialVersionUID = 6999637496007165672L;
    private long[] ids;
    private long nextCursor = -1;
    private long previousCursor = -1;

    IDsJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        String json = res.asString();
        init(json);
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    IDsJSONImpl(String json) throws TwitterException {
        init(json);
    }

    private void init(String jsonStr) throws TwitterException {
        JSONObject json;
        JSONArray idList;
        try {
            int i;
            if (jsonStr.startsWith("{")) {
                json = new JSONObject(jsonStr);
                idList = json.getJSONArray("ids");
                this.ids = new long[idList.length()];
                for (i = 0; i < idList.length(); i++) {
                    this.ids[i] = Long.parseLong(idList.getString(i));
                }
                this.previousCursor = ParseUtil.getLong("previous_cursor", json);
                this.nextCursor = ParseUtil.getLong("next_cursor", json);
                return;
            }
            idList = new JSONArray(jsonStr);
            this.ids = new long[idList.length()];
            for (i = 0; i < idList.length(); i++) {
                this.ids[i] = Long.parseLong(idList.getString(i));
            }
        } catch (Throwable nfe) {
            throw new TwitterException("Twitter API returned malformed response: " + idList, nfe);
        } catch (Throwable nfe2) {
            throw new TwitterException("Twitter API returned malformed response: " + json, nfe2);
        } catch (Exception jsone) {
            throw new TwitterException(jsone);
        }
    }

    public long[] getIDs() {
        return this.ids;
    }

    public boolean hasPrevious() {
        return 0 != this.previousCursor;
    }

    public long getPreviousCursor() {
        return this.previousCursor;
    }

    public boolean hasNext() {
        return 0 != this.nextCursor;
    }

    public long getNextCursor() {
        return this.nextCursor;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IDs)) {
            return false;
        }
        if (Arrays.equals(this.ids, ((IDs) o).getIDs())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.ids != null ? Arrays.hashCode(this.ids) : 0;
    }

    public String toString() {
        return "IDsJSONImpl{ids=" + Arrays.toString(this.ids) + ", previousCursor=" + this.previousCursor + ", nextCursor=" + this.nextCursor + '}';
    }
}
