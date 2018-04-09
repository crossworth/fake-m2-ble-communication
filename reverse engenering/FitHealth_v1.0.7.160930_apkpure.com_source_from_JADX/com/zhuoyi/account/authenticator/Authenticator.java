package com.zhuoyi.account.authenticator;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

class Authenticator extends AbstractAccountAuthenticator {
    private final Context mContext;

    public Authenticator(Context context) {
        super(context);
        this.mContext = context;
    }

    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) {
        Intent intent = new Intent(this.mContext, AuthenticatorActivity.class);
        intent.putExtra("accountAuthenticatorResponse", response);
        Bundle bundle = new Bundle();
        bundle.putParcelable("intent", intent);
        return bundle;
    }

    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) {
        return null;
    }

    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        throw new UnsupportedOperationException();
    }

    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle loginOptions) throws NetworkErrorException {
        if (authTokenType.equals("com.zhuoyou.account.android.samplesync")) {
            String password = AccountManager.get(this.mContext).getPassword(account);
            if (password != null) {
                String authToken = NetworkUtilities.authenticate(account.name, password);
                if (!TextUtils.isEmpty(authToken)) {
                    Bundle result = new Bundle();
                    result.putString("authAccount", account.name);
                    result.putString("accountType", "account_type");
                    result.putString(NetworkUtilities.PARAM_AUTH_TOKEN, authToken);
                    return result;
                }
            }
            Intent intent = new Intent(this.mContext, AuthenticatorActivity.class);
            intent.putExtra("username", account.name);
            intent.putExtra(AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE, authTokenType);
            intent.putExtra("accountAuthenticatorResponse", response);
            Bundle bundle = new Bundle();
            bundle.putParcelable("intent", intent);
            return bundle;
        }
        result = new Bundle();
        result.putString("errorMessage", "invalid authTokenType");
        return result;
    }

    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) {
        Bundle result = new Bundle();
        result.putBoolean("booleanResult", false);
        return result;
    }

    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle loginOptions) {
        return null;
    }
}
