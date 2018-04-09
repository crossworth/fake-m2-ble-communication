package umeng_bolts;

import android.net.Uri;

public interface AppLinkResolver {
    Task<AppLink> getAppLinkFromUrlInBackground(Uri uri);
}
