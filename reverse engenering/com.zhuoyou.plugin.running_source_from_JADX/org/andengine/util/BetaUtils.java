package org.andengine.util;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.GregorianCalendar;
import org.andengine.util.preferences.SimplePreferences;

public class BetaUtils {
    private static final String PREFERENCES_BETAUTILS_ID = "preferences.betautils.lastuse";

    public static boolean finishWhenExpired(Activity pActivity, GregorianCalendar pExpirationDate, int pTitleResourceID, int pMessageResourceID) {
        return finishWhenExpired(pActivity, pExpirationDate, pTitleResourceID, pMessageResourceID, null, null);
    }

    public static boolean finishWhenExpired(final Activity pActivity, GregorianCalendar pExpirationDate, int pTitleResourceID, int pMessageResourceID, final Intent pOkIntent, final Intent pCancelIntent) {
        SharedPreferences spref = SimplePreferences.getInstance(pActivity);
        long lastuse = Math.max(System.currentTimeMillis(), spref.getLong(PREFERENCES_BETAUTILS_ID, -1));
        spref.edit().putLong(PREFERENCES_BETAUTILS_ID, lastuse).commit();
        GregorianCalendar lastuseDate = new GregorianCalendar();
        lastuseDate.setTimeInMillis(lastuse);
        if (!lastuseDate.after(pExpirationDate)) {
            return false;
        }
        Builder alertDialogBuilder = new Builder(pActivity).setTitle(pTitleResourceID).setIcon(17301543).setMessage(pMessageResourceID);
        alertDialogBuilder.setPositiveButton(17039370, new OnClickListener() {
            public void onClick(DialogInterface pDialog, int pWhich) {
                if (pOkIntent != null) {
                    pActivity.startActivity(pOkIntent);
                }
                pActivity.finish();
            }
        });
        alertDialogBuilder.setNegativeButton(17039360, new OnClickListener() {
            public void onClick(DialogInterface pDialog, int pWhich) {
                if (pCancelIntent != null) {
                    pActivity.startActivity(pCancelIntent);
                }
                pActivity.finish();
            }
        }).create().show();
        return true;
    }
}
