package twitter4j;

import java.io.Serializable;
import p031u.aly.au;
import twitter4j.conf.Configuration;

class AccountSettingsJSONImpl extends TwitterResponseImpl implements AccountSettings, Serializable {
    private static final long serialVersionUID = 603189815663175766L;
    private final boolean ALWAYS_USE_HTTPS;
    private final boolean DISCOVERABLE_BY_EMAIL;
    private final boolean GEO_ENABLED;
    private final String LANGUAGE;
    private final String SCREEN_NAME;
    private final String SLEEP_END_TIME;
    private final String SLEEP_START_TIME;
    private final boolean SLEEP_TIME_ENABLED;
    private final TimeZone TIMEZONE;
    private final Location[] TREND_LOCATION;

    private AccountSettingsJSONImpl(HttpResponse res, JSONObject json) throws TwitterException {
        super(res);
        try {
            JSONObject sleepTime = json.getJSONObject("sleep_time");
            this.SLEEP_TIME_ENABLED = ParseUtil.getBoolean("enabled", sleepTime);
            this.SLEEP_START_TIME = sleepTime.getString("start_time");
            this.SLEEP_END_TIME = sleepTime.getString("end_time");
            if (json.isNull("trend_location")) {
                this.TREND_LOCATION = new Location[0];
            } else {
                JSONArray locations = json.getJSONArray("trend_location");
                this.TREND_LOCATION = new Location[locations.length()];
                for (int i = 0; i < locations.length(); i++) {
                    this.TREND_LOCATION[i] = new LocationJSONImpl(locations.getJSONObject(i));
                }
            }
            this.GEO_ENABLED = ParseUtil.getBoolean("geo_enabled", json);
            this.LANGUAGE = json.getString(au.f3551F);
            this.ALWAYS_USE_HTTPS = ParseUtil.getBoolean("always_use_https", json);
            this.DISCOVERABLE_BY_EMAIL = ParseUtil.getBoolean("discoverable_by_email", json);
            if (json.isNull("time_zone")) {
                this.TIMEZONE = null;
            } else {
                this.TIMEZONE = new TimeZoneJSONImpl(json.getJSONObject("time_zone"));
            }
            this.SCREEN_NAME = json.getString("screen_name");
        } catch (Exception e) {
            throw new TwitterException(e);
        }
    }

    AccountSettingsJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        this(res, res.asJSONObject());
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, res.asJSONObject());
        }
    }

    AccountSettingsJSONImpl(JSONObject json) throws TwitterException {
        this(null, json);
    }

    public boolean isSleepTimeEnabled() {
        return this.SLEEP_TIME_ENABLED;
    }

    public String getSleepStartTime() {
        return this.SLEEP_START_TIME;
    }

    public String getSleepEndTime() {
        return this.SLEEP_END_TIME;
    }

    public Location[] getTrendLocations() {
        return this.TREND_LOCATION;
    }

    public boolean isGeoEnabled() {
        return this.GEO_ENABLED;
    }

    public boolean isDiscoverableByEmail() {
        return this.DISCOVERABLE_BY_EMAIL;
    }

    public boolean isAlwaysUseHttps() {
        return this.ALWAYS_USE_HTTPS;
    }

    public String getScreenName() {
        return this.SCREEN_NAME;
    }

    public String getLanguage() {
        return this.LANGUAGE;
    }

    public TimeZone getTimeZone() {
        return this.TIMEZONE;
    }
}
