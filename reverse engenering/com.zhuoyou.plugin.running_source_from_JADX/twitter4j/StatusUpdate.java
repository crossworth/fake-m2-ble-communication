package twitter4j;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StatusUpdate implements Serializable {
    private static final long serialVersionUID = 7422094739799350035L;
    private boolean displayCoordinates = true;
    private long inReplyToStatusId = -1;
    private GeoLocation location = null;
    private transient InputStream mediaBody;
    private File mediaFile;
    private long[] mediaIds;
    private String mediaName;
    private String placeId = null;
    private boolean possiblySensitive;
    private final String status;

    public StatusUpdate(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public long getInReplyToStatusId() {
        return this.inReplyToStatusId;
    }

    public void setInReplyToStatusId(long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public StatusUpdate inReplyToStatusId(long inReplyToStatusId) {
        setInReplyToStatusId(inReplyToStatusId);
        return this;
    }

    public GeoLocation getLocation() {
        return this.location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public StatusUpdate location(GeoLocation location) {
        setLocation(location);
        return this;
    }

    public String getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public StatusUpdate placeId(String placeId) {
        setPlaceId(placeId);
        return this;
    }

    public boolean isDisplayCoordinates() {
        return this.displayCoordinates;
    }

    public void setDisplayCoordinates(boolean displayCoordinates) {
        this.displayCoordinates = displayCoordinates;
    }

    public StatusUpdate displayCoordinates(boolean displayCoordinates) {
        setDisplayCoordinates(displayCoordinates);
        return this;
    }

    public void setMedia(File file) {
        this.mediaFile = file;
    }

    public StatusUpdate media(File file) {
        setMedia(file);
        return this;
    }

    public void setMedia(String name, InputStream body) {
        this.mediaName = name;
        this.mediaBody = body;
    }

    public void setMediaIds(long... mediaIds) {
        this.mediaIds = mediaIds;
    }

    boolean isForUpdateWithMedia() {
        return (this.mediaFile == null && this.mediaName == null) ? false : true;
    }

    public StatusUpdate media(String name, InputStream body) {
        setMedia(name, body);
        return this;
    }

    public void setPossiblySensitive(boolean possiblySensitive) {
        this.possiblySensitive = possiblySensitive;
    }

    public StatusUpdate possiblySensitive(boolean possiblySensitive) {
        setPossiblySensitive(possiblySensitive);
        return this;
    }

    public boolean isPossiblySensitive() {
        return this.possiblySensitive;
    }

    HttpParameter[] asHttpParameterArray() {
        List params = new ArrayList();
        appendParameter("status", this.status, params);
        if (-1 != this.inReplyToStatusId) {
            appendParameter("in_reply_to_status_id", this.inReplyToStatusId, params);
        }
        if (this.location != null) {
            appendParameter("lat", this.location.getLatitude(), params);
            appendParameter("long", this.location.getLongitude(), params);
        }
        appendParameter("place_id", this.placeId, params);
        if (!this.displayCoordinates) {
            appendParameter("display_coordinates", "false", params);
        }
        if (this.mediaFile != null) {
            params.add(new HttpParameter("media[]", this.mediaFile));
            params.add(new HttpParameter("possibly_sensitive", this.possiblySensitive));
        } else if (this.mediaName != null && this.mediaBody != null) {
            params.add(new HttpParameter("media[]", this.mediaName, this.mediaBody));
            params.add(new HttpParameter("possibly_sensitive", this.possiblySensitive));
        } else if (this.mediaIds != null && this.mediaIds.length >= 1) {
            params.add(new HttpParameter("media_ids", StringUtil.join(this.mediaIds)));
        }
        return (HttpParameter[]) params.toArray(new HttpParameter[params.size()]);
    }

    private void appendParameter(String name, String value, List<HttpParameter> params) {
        if (value != null) {
            params.add(new HttpParameter(name, value));
        }
    }

    private void appendParameter(String name, double value, List<HttpParameter> params) {
        params.add(new HttpParameter(name, String.valueOf(value)));
    }

    private void appendParameter(String name, long value, List<HttpParameter> params) {
        params.add(new HttpParameter(name, String.valueOf(value)));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StatusUpdate that = (StatusUpdate) o;
        if (this.displayCoordinates != that.displayCoordinates) {
            return false;
        }
        if (this.inReplyToStatusId != that.inReplyToStatusId) {
            return false;
        }
        if (this.possiblySensitive != that.possiblySensitive) {
            return false;
        }
        if (this.location == null ? that.location != null : !this.location.equals(that.location)) {
            return false;
        }
        if (this.mediaBody == null ? that.mediaBody != null : !this.mediaBody.equals(that.mediaBody)) {
            return false;
        }
        if (this.mediaFile == null ? that.mediaFile != null : !this.mediaFile.equals(that.mediaFile)) {
            return false;
        }
        if (this.mediaName == null ? that.mediaName != null : !this.mediaName.equals(that.mediaName)) {
            return false;
        }
        if (this.mediaIds == null ? that.mediaIds != null : !Arrays.equals(this.mediaIds, that.mediaIds)) {
            return false;
        }
        if (this.placeId == null ? that.placeId != null : !this.placeId.equals(that.placeId)) {
            return false;
        }
        if (this.status != null) {
            if (this.status.equals(that.status)) {
                return true;
            }
        } else if (that.status == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 1;
        int i2 = 0;
        if (this.status != null) {
            result = this.status.hashCode();
        } else {
            result = 0;
        }
        int i3 = ((result * 31) + ((int) (this.inReplyToStatusId ^ (this.inReplyToStatusId >>> 32)))) * 31;
        if (this.location != null) {
            hashCode = this.location.hashCode();
        } else {
            hashCode = 0;
        }
        i3 = (i3 + hashCode) * 31;
        if (this.placeId != null) {
            hashCode = this.placeId.hashCode();
        } else {
            hashCode = 0;
        }
        i3 = (i3 + hashCode) * 31;
        if (this.displayCoordinates) {
            hashCode = 1;
        } else {
            hashCode = 0;
        }
        hashCode = (i3 + hashCode) * 31;
        if (!this.possiblySensitive) {
            i = 0;
        }
        i = (hashCode + i) * 31;
        if (this.mediaName != null) {
            hashCode = this.mediaName.hashCode();
        } else {
            hashCode = 0;
        }
        i = (i + hashCode) * 31;
        if (this.mediaBody != null) {
            hashCode = this.mediaBody.hashCode();
        } else {
            hashCode = 0;
        }
        i = (i + hashCode) * 31;
        if (this.mediaFile != null) {
            hashCode = this.mediaFile.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i + hashCode) * 31;
        if (this.mediaIds != null) {
            i2 = StringUtil.join(this.mediaIds).hashCode();
        }
        return hashCode + i2;
    }

    public String toString() {
        return "StatusUpdate{status='" + this.status + '\'' + ", inReplyToStatusId=" + this.inReplyToStatusId + ", location=" + this.location + ", placeId='" + this.placeId + '\'' + ", displayCoordinates=" + this.displayCoordinates + ", possiblySensitive=" + this.possiblySensitive + ", mediaName='" + this.mediaName + '\'' + ", mediaBody=" + this.mediaBody + ", mediaFile=" + this.mediaFile + ", mediaIds=" + this.mediaIds + '}';
    }
}
