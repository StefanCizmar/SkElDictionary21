package samples.dictionary.UI;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import kiwi.employeedirectory.R;

public class AboutDict extends Activity {

    private boolean Theme;
    private boolean Theme2;
    private int swords;
    private int gWords;
    private String sw;
    private String ew;
    private String dbv;

    private LinearLayout layout;
    private TextView slovakWords;
    private TextView greekWords;
    private TextView dbVersion;
    private TextView dictInfo;

    private SharedPreferences prefs;

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
        setContentView(R.layout.aboutdict);

        setTitle(getString(R.string.aboutDict));

        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        slovakWords = findViewById(R.id.textSlovakwords);
        greekWords = findViewById(R.id.textGreekwords);
        dbVersion = findViewById(R.id.tvDbVersion);


        /**
         * get count slovak words
         */
        swords = getIntent().getIntExtra("SVK_W", 0);
        sw = getString(R.string.slovakwords) + " " + swords;
        slovakWords.setText(sw);

        /**
         * get count Greek words
         */
        gWords = getIntent().getIntExtra("ELL_W", 0);
        ew = getString(R.string.greekwords) + " " + gWords;
        greekWords.setText(ew);

        dbv = "" + getIntent().getIntExtra("DBV", 0);
        dbVersion.setText(getString(R.string.dbver) + dbv);

        layout = findViewById(R.id.LinearLayoutdict);
        dictInfo = findViewById(R.id.textInfoDic);

        /**
         * set theme
         */
        if (Theme2) {
            layout.setBackgroundColor(Color.parseColor("#FF121212"));
            slovakWords.setTextColor(Color.parseColor("#FFFFFFFF"));
            greekWords.setTextColor(Color.parseColor("#FFFFFFFF"));
            dbVersion.setTextColor(Color.parseColor("#FFFFFFFF"));
            dictInfo.setTextColor(Color.parseColor("#FFFFFFFF"));
            dictInfo.setBackgroundResource(R.drawable.black_box);
        } else {
            layout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            slovakWords.setTextColor(Color.parseColor("#FF121212"));
            greekWords.setTextColor(Color.parseColor("#FF121212"));
            dbVersion.setTextColor(Color.parseColor("#FF121212"));
            dictInfo.setTextColor(Color.parseColor("#FF121212"));
            dictInfo.setBackgroundResource(R.drawable.filled_box);
        }

        Button okButton = findViewById(R.id.buttonOK);
        okButton.setTextColor(Color.parseColor("#FFFFFFFF"));
        okButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadUserSettings() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String s = prefs.getString("prefTheme", "light");
        if (s.equalsIgnoreCase("light")) {
            Theme = true;
            Theme2 = false;
            Log.e("Theme", "light");
        }
        if (s.equalsIgnoreCase("black")) {
            Theme = false;
            Theme2 = true;
            Log.e("Theme", "black");
        }
    }

    /**
     * choice from menu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // app icon in action bar clicked; go home
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}