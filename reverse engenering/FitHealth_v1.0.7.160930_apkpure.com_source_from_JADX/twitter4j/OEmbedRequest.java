package twitter4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class OEmbedRequest implements Serializable {
    private static final long serialVersionUID = 7454130135274547901L;
    private Align align = Align.NONE;
    private boolean hideMedia = true;
    private boolean hideThread = true;
    private String lang;
    private int maxWidth;
    private boolean omitScript = false;
    private String[] related = new String[0];
    private final long statusId;
    private final String url;

    public enum Align {
        LEFT,
        CENTER,
        RIGHT,
        NONE
    }

    public OEmbedRequest(long statusId, String url) {
        this.statusId = statusId;
        this.url = url;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public OEmbedRequest MaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public void setHideMedia(boolean hideMedia) {
        this.hideMedia = hideMedia;
    }

    public OEmbedRequest HideMedia(boolean hideMedia) {
        this.hideMedia = hideMedia;
        return this;
    }

    public void setHideThread(boolean hideThread) {
        this.hideThread = hideThread;
    }

    public OEmbedRequest HideThread(boolean hideThread) {
        this.hideThread = hideThread;
        return this;
    }

    public void setOmitScript(boolean omitScript) {
        this.omitScript = omitScript;
    }

    public OEmbedRequest omitScript(boolean omitScript) {
        this.omitScript = omitScript;
        return this;
    }

    public void setAlign(Align align) {
        this.align = align;
    }

    public OEmbedRequest align(Align align) {
        this.align = align;
        return this;
    }

    public void setRelated(String[] related) {
        this.related = related;
    }

    public OEmbedRequest related(String[] related) {
        this.related = related;
        return this;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public OEmbedRequest lang(String lang) {
        this.lang = lang;
        return this;
    }

    HttpParameter[] asHttpParameterArray() {
        List params = new ArrayList(12);
        appendParameter("id", this.statusId, params);
        appendParameter("url", this.url, params);
        appendParameter("maxwidth", (long) this.maxWidth, params);
        params.add(new HttpParameter("hide_media", this.hideMedia));
        params.add(new HttpParameter("hide_thread", this.hideThread));
        params.add(new HttpParameter("omit_script", this.omitScript));
        params.add(new HttpParameter("align", this.align.name().toLowerCase()));
        if (this.related.length > 0) {
            appendParameter("related", StringUtil.join(this.related), params);
        }
        appendParameter("lang", this.lang, params);
        return (HttpParameter[]) params.toArray(new HttpParameter[params.size()]);
    }

    private void appendParameter(String name, String value, List<HttpParameter> params) {
        if (value != null) {
            params.add(new HttpParameter(name, value));
        }
    }

    private void appendParameter(String name, long value, List<HttpParameter> params) {
        if (0 <= value) {
            params.add(new HttpParameter(name, String.valueOf(value)));
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OEmbedRequest that = (OEmbedRequest) o;
        if (this.hideMedia != that.hideMedia) {
            return false;
        }
        if (this.hideThread != that.hideThread) {
            return false;
        }
        if (this.maxWidth != that.maxWidth) {
            return false;
        }
        if (this.omitScript != that.omitScript) {
            return false;
        }
        if (this.statusId != that.statusId) {
            return false;
        }
        if (this.align != that.align) {
            return false;
        }
        if (this.lang == null ? that.lang != null : !this.lang.equals(that.lang)) {
            return false;
        }
        if (!Arrays.equals(this.related, that.related)) {
            return false;
        }
        if (this.url != null) {
            if (this.url.equals(that.url)) {
                return true;
            }
        } else if (that.url == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i;
        int i2 = 1;
        int i3 = 0;
        int hashCode = ((((((int) (this.statusId ^ (this.statusId >>> 32))) * 31) + (this.url != null ? this.url.hashCode() : 0)) * 31) + this.maxWidth) * 31;
        if (this.hideMedia) {
            i = 1;
        } else {
            i = 0;
        }
        hashCode = (hashCode + i) * 31;
        if (this.hideThread) {
            i = 1;
        } else {
            i = 0;
        }
        i = (hashCode + i) * 31;
        if (!this.omitScript) {
            i2 = 0;
        }
        i2 = (i + i2) * 31;
        if (this.align != null) {
            i = this.align.hashCode();
        } else {
            i = 0;
        }
        i2 = (i2 + i) * 31;
        if (this.related != null) {
            i = Arrays.hashCode(this.related);
        } else {
            i = 0;
        }
        i = (i2 + i) * 31;
        if (this.lang != null) {
            i3 = this.lang.hashCode();
        }
        return i + i3;
    }

    public String toString() {
        Object obj;
        StringBuilder append = new StringBuilder().append("OEmbedRequest{statusId=").append(this.statusId).append(", url='").append(this.url).append('\'').append(", maxWidth=").append(this.maxWidth).append(", hideMedia=").append(this.hideMedia).append(", hideThread=").append(this.hideThread).append(", omitScript=").append(this.omitScript).append(", align=").append(this.align).append(", related=");
        if (this.related == null) {
            obj = null;
        } else {
            obj = Arrays.asList(this.related);
        }
        return append.append(obj).append(", lang='").append(this.lang).append('\'').append('}').toString();
    }
}
