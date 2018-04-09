package org.andengine.ui.dialog;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import org.andengine.util.call.Callback;
import org.andengine.util.debug.Debug;

public abstract class GenericInputDialogBuilder<T> {
    protected final Context mContext;
    private final String mDefaultText;
    private final int mErrorResID;
    protected final int mIconResID;
    protected final int mMessageResID;
    protected final OnCancelListener mOnCancelListener;
    protected final Callback<T> mSuccessCallback;
    protected final int mTitleResID;

    class C20971 implements OnClickListener {
        C20971() {
        }

        public void onClick(DialogInterface pDialog, int pWhich) {
            GenericInputDialogBuilder.this.mOnCancelListener.onCancel(pDialog);
            pDialog.dismiss();
        }
    }

    protected abstract T generateResult(String str);

    public GenericInputDialogBuilder(Context pContext, int pTitleResID, int pMessageResID, int pErrorResID, int pIconResID, Callback<T> pSuccessCallback, OnCancelListener pOnCancelListener) {
        this(pContext, pTitleResID, pMessageResID, pErrorResID, pIconResID, "", pSuccessCallback, pOnCancelListener);
    }

    public GenericInputDialogBuilder(Context pContext, int pTitleResID, int pMessageResID, int pErrorResID, int pIconResID, String pDefaultText, Callback<T> pSuccessCallback, OnCancelListener pOnCancelListener) {
        this.mContext = pContext;
        this.mTitleResID = pTitleResID;
        this.mMessageResID = pMessageResID;
        this.mErrorResID = pErrorResID;
        this.mIconResID = pIconResID;
        this.mDefaultText = pDefaultText;
        this.mSuccessCallback = pSuccessCallback;
        this.mOnCancelListener = pOnCancelListener;
    }

    public Dialog create() {
        final EditText etInput = new EditText(this.mContext);
        etInput.setText(this.mDefaultText);
        Builder ab = new Builder(this.mContext);
        if (this.mTitleResID != 0) {
            ab.setTitle(this.mTitleResID);
        }
        if (this.mMessageResID != 0) {
            ab.setMessage(this.mMessageResID);
        }
        if (this.mIconResID != 0) {
            ab.setIcon(this.mIconResID);
        }
        setView(ab, etInput);
        ab.setOnCancelListener(this.mOnCancelListener).setPositiveButton(17039370, new OnClickListener() {
            public void onClick(DialogInterface pDialog, int pWhich) {
                try {
                    GenericInputDialogBuilder.this.mSuccessCallback.onCallback(GenericInputDialogBuilder.this.generateResult(etInput.getText().toString()));
                    pDialog.dismiss();
                } catch (Throwable e) {
                    Debug.m4591e("Error in GenericInputDialogBuilder.generateResult()", e);
                    Toast.makeText(GenericInputDialogBuilder.this.mContext, GenericInputDialogBuilder.this.mErrorResID, 0).show();
                }
            }
        }).setNegativeButton(17039360, new C20971());
        return ab.create();
    }

    protected void setView(Builder pBuilder, EditText pInputEditText) {
        pBuilder.setView(pInputEditText);
    }
}
