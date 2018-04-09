package twitter4j;

import java.io.Serializable;

public interface ExtendedMediaEntity extends MediaEntity {

    public interface Variant extends Serializable {
        int getBitrate();

        String getContentType();

        String getUrl();
    }

    int getVideoAspectRatioHeight();

    int getVideoAspectRatioWidth();

    long getVideoDurationMillis();

    Variant[] getVideoVariants();
}
