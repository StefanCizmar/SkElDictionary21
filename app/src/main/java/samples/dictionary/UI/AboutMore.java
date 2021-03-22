package samples.dictionary.UI;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import kiwi.employeedirectory.R;

public class AboutMore extends Activity {

    WebView MoreBrowser;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.grammary);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        MoreBrowser = findViewById(R.id.webViewMore);
        MoreBrowser.loadUrl("file:///android_res/raw/aboutmore.html");
        WebSettings webSettings = MoreBrowser.getSettings();
        webSettings.setDefaultFontSize(12);
        webSettings.setBuiltInZoomControls(true);

        /** not used!!!
         *
         */
    }

    /**
     * choice from menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // app icon in action bar clicked; go home
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
