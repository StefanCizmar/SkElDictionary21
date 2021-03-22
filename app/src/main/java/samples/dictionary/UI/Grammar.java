package samples.dictionary.UI;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import kiwi.employeedirectory.R;

public class Grammar extends Activity {

    private boolean Theme;
    private boolean Theme2;
    private SharedPreferences prefs;
    private WebView browser;

    public Grammar() {
        Theme = true;
        Theme2 = false;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        loadUserSettings();

        if (Build.VERSION.SDK_INT >= 21) {
            if (Theme) {
                setTheme(R.style.AppTheme1);
            }

            if (Theme2) {
                setTheme(R.style.AppTheme2);
            }

        }

        // for Android Q automatic sett to dark mode
        int currentNightMode = this.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
            {
                Log.i("MODE_ABOUT", " LIGHT" );
                Theme = true;
                Theme2 = false;
                break;
            }

            case Configuration.UI_MODE_NIGHT_YES:
            {
                Log.i("MODE_ABOUT", " DARK" );
                Theme = false;
                Theme2 = true;
                break;
            }
        }

        setContentView(R.layout.grammary);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        browser = findViewById(R.id.webViewMore);
        browser.loadUrl("file:///android_res/raw/grammary.html");
        WebSettings webSettings = browser.getSettings();
        webSettings.setDefaultFontSize(12);
        webSettings.setBuiltInZoomControls(true);

    }

    private void loadUserSettings() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String s = prefs.getString("prefTheme", "light");
        if (s.equalsIgnoreCase("light")) {
            Theme = true;
            Theme2 = false;
        }
        if (s.equalsIgnoreCase("black")) {
            Theme = false;
            Theme2 = true;
        }
    }

    /**
     * choice from menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
    /**
         * app icon in action bar clicked; go home
         */
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}