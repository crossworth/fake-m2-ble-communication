package com.adroi.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import com.adroi.sdk.p000a.C0277b;
import com.adroi.sdk.p000a.C0278c;
import java.lang.reflect.Method;

public class AppActivity extends Activity {
    private Object f17a;
    private Class<?> f18b;
    private Method[] f19c = null;

    private Method m15a(String str) {
        if (this.f19c == null) {
            return null;
        }
        for (Method method : this.f19c) {
            if (method.getName().equals(str)) {
                method.setAccessible(true);
                return method;
            }
        }
        return null;
    }

    private void m16a(String str, Object... objArr) {
        int i = 0;
        try {
            Object[] objArr2 = new Object[3];
            objArr2[0] = str;
            if (objArr != null) {
                i = objArr.length;
            }
            objArr2[1] = Integer.valueOf(i);
            objArr2[2] = objArr;
            C0278c.m33a(objArr2);
            Method a = m15a(str);
            if (a == null) {
                return;
            }
            if (objArr == null || objArr.length == 0) {
                a.invoke(this.f17a, new Object[0]);
            } else {
                a.invoke(this.f17a, objArr);
            }
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    private boolean m17b(String str, Object... objArr) {
        try {
            Object[] objArr2 = new Object[3];
            objArr2[0] = str;
            objArr2[1] = Integer.valueOf(objArr != null ? objArr.length : 0);
            objArr2[2] = objArr;
            C0278c.m33a(objArr2);
            Method a = m15a(str);
            if (a != null) {
                if (objArr == null || objArr.length == 0) {
                    return ((Boolean) a.invoke(this.f17a, new Object[0])).booleanValue();
                }
                return ((Boolean) a.invoke(this.f17a, objArr)).booleanValue();
            }
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
        return false;
    }

    private Object m18c(String str, Object... objArr) {
        int i = 0;
        try {
            Object[] objArr2 = new Object[3];
            objArr2[0] = str;
            if (objArr != null) {
                i = objArr.length;
            }
            objArr2[1] = Integer.valueOf(i);
            objArr2[2] = objArr;
            C0278c.m33a(objArr2);
            Method a = m15a(str);
            if (a != null) {
                if (objArr == null || objArr.length == 0) {
                    return a.invoke(this.f17a, new Object[0]);
                }
                return a.invoke(this.f17a, objArr);
            }
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
        return null;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (m17b("dispatchKeyEvent", keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (m17b("dispatchTouchEvent", motionEvent)) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        if (m17b("dispatchTrackballEvent", motionEvent)) {
            return true;
        }
        return super.dispatchTrackballEvent(motionEvent);
    }

    protected void finalize() {
        m16a("finalize", new Object[0]);
        super.finalize();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        m16a("onActivityResult", Integer.valueOf(i), Integer.valueOf(i2), intent);
        super.onActivityResult(i, i2, intent);
    }

    protected void onApplyThemeResource(Theme theme, int i, boolean z) {
        m16a("onApplyThemeResource", theme, Integer.valueOf(i), Boolean.valueOf(z));
        super.onApplyThemeResource(theme, i, z);
    }

    protected void onChildTitleChanged(Activity activity, CharSequence charSequence) {
        m16a("onChildTitleChanged", activity, charSequence);
        super.onChildTitleChanged(activity, charSequence);
    }

    public void onConfigurationChanged(Configuration configuration) {
        m16a("onConfigurationChanged", configuration);
        super.onConfigurationChanged(configuration);
    }

    public void onContentChanged() {
        m16a("onContentChanged", new Object[0]);
        super.onContentChanged();
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        if (m17b("onContextItemSelected", menuItem)) {
            return true;
        }
        return super.onContextItemSelected(menuItem);
    }

    public void onContextMenuClosed(Menu menu) {
        m16a("onContextMenuClosed", menu);
        super.onContextMenuClosed(menu);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        C0278c.m30a("appactivity oncreate!!!");
        try {
            String string = getIntent().getExtras().getString("remote_activity");
            C0278c.m30a("onCreate clazz =" + string);
            this.f18b = C0277b.m25a(this, string);
            this.f19c = this.f18b.getDeclaredMethods();
            this.f17a = this.f18b.getConstructor(new Class[]{Activity.class}).newInstance(new Object[]{this});
            C0278c.m33a(string, this.f18b, this.f17a);
        } catch (Throwable e) {
            C0278c.m32a(e);
        }
        m16a("onCreate", bundle);
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        m16a("onCreateContextMenu", contextMenu, view, contextMenuInfo);
    }

    public CharSequence onCreateDescription() {
        CharSequence charSequence = (CharSequence) m18c("onCreateDescription", new Object[0]);
        return charSequence != null ? charSequence : super.onCreateDescription();
    }

    protected Dialog onCreateDialog(int i) {
        Dialog dialog = (Dialog) m18c("onCreateDialog", Integer.valueOf(i));
        return dialog != null ? dialog : super.onCreateDialog(i);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (m17b("onCreateOptionsMenu", menu)) {
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onCreatePanelMenu(int i, Menu menu) {
        if (m17b("onCreatePanelMenu", Integer.valueOf(i), menu)) {
            return true;
        }
        return super.onCreatePanelMenu(i, menu);
    }

    public View onCreatePanelView(int i) {
        View view = (View) m18c("onCreatePanelView", Integer.valueOf(i));
        return view != null ? view : super.onCreatePanelView(i);
    }

    public boolean onCreateThumbnail(Bitmap bitmap, Canvas canvas) {
        if (m17b("onCreateThumbnail", bitmap, canvas)) {
            return true;
        }
        return super.onCreateThumbnail(bitmap, canvas);
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        View view = (View) m18c("onCreateView", str, context, attributeSet);
        return view != null ? view : super.onCreateView(str, context, attributeSet);
    }

    protected void onDestroy() {
        m16a("onDestroy", new Object[0]);
        super.onDestroy();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (m17b("onKeyDown", Integer.valueOf(i), keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        if (m17b("onKeyMultiple", Integer.valueOf(i), Integer.valueOf(i2), keyEvent)) {
            return true;
        }
        return super.onKeyMultiple(i, i2, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (m17b("onKeyUp", Integer.valueOf(i), keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    public void onLowMemory() {
        m16a("onLowMemory", new Object[0]);
        super.onLowMemory();
    }

    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (m17b("onMenuItemSelected", Integer.valueOf(i), menuItem)) {
            return true;
        }
        return super.onMenuItemSelected(i, menuItem);
    }

    public boolean onMenuOpened(int i, Menu menu) {
        if (m17b("onMenuOpened", Integer.valueOf(i), menu)) {
            return true;
        }
        return super.onMenuOpened(i, menu);
    }

    protected void onNewIntent(Intent intent) {
        m16a("onNewIntent", intent);
        super.onNewIntent(intent);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (m17b("onOptionsItemSelected", menuItem)) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onOptionsMenuClosed(Menu menu) {
        m16a("onOptionsMenuClosed", menu);
        super.onOptionsMenuClosed(menu);
    }

    public void onPanelClosed(int i, Menu menu) {
        m16a("onPanelClosed", Integer.valueOf(i), menu);
        super.onPanelClosed(i, menu);
    }

    protected void onPause() {
        m16a("onPause", new Object[0]);
        super.onPause();
    }

    protected void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        m16a("onPostCreate", bundle);
    }

    protected void onPostResume() {
        super.onPostResume();
        m16a("onPostResume", new Object[0]);
    }

    protected void onPrepareDialog(int i, Dialog dialog) {
        super.onPrepareDialog(i, dialog);
        m16a("onPrepareDialog", Integer.valueOf(i), dialog);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (m17b("onPrepareOptionsMenu", menu)) {
            return true;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onPreparePanel(int i, View view, Menu menu) {
        if (m17b("onPreparePanel", Integer.valueOf(i), view, menu)) {
            return true;
        }
        return super.onPreparePanel(i, view, menu);
    }

    protected void onRestart() {
        super.onRestart();
        m16a("onRestart", new Object[0]);
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        m16a("onRestoreInstanceState", bundle);
    }

    protected void onResume() {
        super.onResume();
        m16a("onResume", new Object[0]);
    }

    public Object onRetainNonConfigurationInstance() {
        Object c = m18c("onRetainNonConfigurationInstance", new Object[0]);
        return c != null ? c : super.onRetainNonConfigurationInstance();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        m16a("onSaveInstanceState", bundle);
    }

    public boolean onSearchRequested() {
        if (m17b("onSearchRequested", new Object[0])) {
            return true;
        }
        return super.onSearchRequested();
    }

    protected void onStart() {
        super.onStart();
        m16a("onStart", new Object[0]);
    }

    protected void onStop() {
        m16a("onStop", new Object[0]);
        super.onStop();
    }

    protected void onTitleChanged(CharSequence charSequence, int i) {
        super.onTitleChanged(charSequence, i);
        m16a("onTitleChanged", charSequence, Integer.valueOf(i));
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (m17b("onTouchEvent", motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (m17b("onTrackballEvent", motionEvent)) {
            return true;
        }
        return super.onTrackballEvent(motionEvent);
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        m16a("onUserInteraction", new Object[0]);
    }

    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        m16a("onUserLeaveHint", new Object[0]);
    }

    public void onWindowAttributesChanged(LayoutParams layoutParams) {
        super.onWindowAttributesChanged(layoutParams);
        m16a("onWindowAttributesChanged", layoutParams);
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        m16a("onWindowFocusChanged", Boolean.valueOf(z));
    }
}
