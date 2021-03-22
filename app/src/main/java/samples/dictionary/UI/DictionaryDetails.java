package samples.dictionary.UI;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kiwi.employeedirectory.R;

public class DictionaryDetails extends ListActivity {
    public boolean Theme;
    public boolean Theme2;
    public boolean fontSize1;
    public boolean fontSize2;
    public boolean fontSize3;
    public boolean fontSize4;
    public boolean fontSize5;
    public boolean country;

    protected LinearLayout layout;
    protected TextView definition2Text;
    protected TextView definitionText;
    protected TextView wordNameText;
    protected String dictId;
    protected String dictWord;
    protected String dictArth;
    protected String dictDef;
    protected String dictArth2;
    protected String dictTrsp;
    protected String dictDecl;
    protected String dictDef2;
    protected WebView webViewO;
    protected SharedPreferences prefs;

    public DictionaryDetails() {
        Theme = true;
        Theme2 = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        if(Build.VERSION.SDK_INT >= 29) {
            int currentNightMode = this.getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO: {
                    Log.i("MODE_ABOUT", " LIGHT");
                    Theme = true;
                    Theme2 = false;
                    break;
                }

                case Configuration.UI_MODE_NIGHT_YES: {
                    Log.i("MODE_ABOUT", " DARK");
                    Theme = false;
                    Theme2 = true;
                    break;
                }
            }
        }
        setContentView(R.layout.dictionary_details);

        setTitle(getString(R.string.detail));
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        dictId = getIntent().getStringExtra("DICT_ID");
        dictWord = getIntent().getStringExtra("DICT_WORD");
        dictArth = getIntent().getStringExtra("DICT_ARTH");
        dictDef = getIntent().getStringExtra("DICT_DEFINITION");
        dictArth2 = getIntent().getStringExtra("DICT_ARTH2");
        dictTrsp = getIntent().getStringExtra("DICT_TRSP");
        dictDef2 = getIntent().getStringExtra("DICT_DEFINITION2");
        dictDecl = getIntent().getStringExtra("DICT_DECLENSION");
        country = getIntent().getBooleanExtra("COUNTRY", true);
        layout = findViewById(R.id.LinearLayoutDetail);

        wordNameText = findViewById(R.id.wordDetail);
        if (!country) //slovak
            wordNameText.setText(dictWord);
        else //greek
            wordNameText.setText(dictArth + " " + dictWord);
        if (dictArth.equalsIgnoreCase(""))
            wordNameText.setText(dictWord);

        definitionText = findViewById(R.id.definitionDetail);
        if (!country) {
            if (dictArth.equalsIgnoreCase(""))
                definitionText.setText(dictArth + "" + dictDef);
            else
                definitionText.setText(dictArth + " " + dictDef);
        } else
            definitionText.setText(dictDef);

        definition2Text = findViewById(R.id.def2);
        definition2Text.setText(dictDef2);


        if (fontSize1) {
            wordNameText.setTextSize(16);
            definitionText.setTextSize(14);
            definition2Text.setTextSize(14);
        }
        if (fontSize2) {
            wordNameText.setTextSize(18);
            definitionText.setTextSize(16);
            definition2Text.setTextSize(16);
        }
        if (fontSize3) {
            wordNameText.setTextSize(20);
            definitionText.setTextSize(16);
            definition2Text.setTextSize(16);
        }
        if (fontSize4) {
            wordNameText.setTextSize(22);
            definitionText.setTextSize(18);
            definition2Text.setTextSize(18);
        }
        if (fontSize5) {
            wordNameText.setTextSize(24);
            definitionText.setTextSize(22);
            definition2Text.setTextSize(22);
        }

        if (Theme2) {
            layout.setBackgroundColor(Color.parseColor("#FF121212"));
            wordNameText.setTextColor(Color.parseColor("#2196f3"));

            definitionText.setTextColor(Color.parseColor("#FFFFFFFF"));
            definition2Text.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            layout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            wordNameText.setTextColor(Color.parseColor("#2196f3"));

            definitionText.setTextColor(Color.parseColor("#FF121212"));
            definition2Text.setTextColor(Color.parseColor("#FF121212"));
        }

        webViewO = findViewById(R.id.webViewAbout);
        webViewO.loadUrl(dictDecl);
        webViewO.getSettings().setBuiltInZoomControls(true);
        webViewO.getSettings().setDefaultFontSize(12);
        if (country)
            webViewO.setVisibility(android.view.View.INVISIBLE);
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
        String s1 = prefs.getString("prefFontSize", "medium_font");

        if (s1.equalsIgnoreCase("huge_font")) {
            fontSize1 = false;
            fontSize2 = false;
            fontSize3 = false;
            fontSize4 = false;
            fontSize5 = true;
        }
        if (s1.equalsIgnoreCase("large_font")) {
            fontSize1 = false;
            fontSize2 = false;
            fontSize3 = false;
            fontSize4 = true;
            fontSize5 = false;
        }
        if (s1.equalsIgnoreCase("medium_font")) {
            fontSize1 = false;
            fontSize2 = false;
            fontSize3 = true;
            fontSize4 = false;
            fontSize5 = false;
        }
        if (s1.equalsIgnoreCase("small_font")) {
            fontSize1 = false;
            fontSize2 = true;
            fontSize3 = false;
            fontSize4 = false;
            fontSize5 = false;
        }
        if (s1.equalsIgnoreCase("tiny_font")) {
            fontSize1 = true;
            fontSize2 = false;
            fontSize3 = false;
            fontSize4 = false;
            fontSize5 = false;
        }
    }

    /**
     * choice from menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}