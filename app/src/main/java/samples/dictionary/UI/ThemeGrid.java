package samples.dictionary.UI;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import kiwi.employeedirectory.R;
import samples.dictionary.ThemeGridAdapter;

public class ThemeGrid  extends Activity {
    private GridView androidGridView;
    private SharedPreferences prefs;
    private boolean Theme;
    private boolean Theme2;
    private LinearLayout layout;
    private static Resources res;

    private String[] gridViewString;
    private int theme_pos;

    public static  boolean isTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    // for Android Q use resources from res/ drawable-night
        if(Build.VERSION.SDK_INT >= 29) {
            int currentNightMode = this.getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;

            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                Theme = false;
                Theme2 = true;
                isTheme = true;

            } else {
                Theme = true;
                Theme2 = false;
                isTheme = false;

            }
        }
    setContentView(R.layout.themegrid);

    setTitle(getString(R.string.choose_theme));

    ActionBar actionBar = getActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    layout = findViewById(R.id.GridLayout);
    androidGridView= findViewById(R.id.ThemeGrid);

    /** set theme */
    if (Theme2) {
        layout.setBackgroundColor(Color.parseColor("#FF121212"));
        androidGridView.setBackgroundColor(Color.parseColor("#FF121212"));
        isTheme = true;

    } else {
        layout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        androidGridView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        isTheme = false;

    }
    gridViewString = getResources().getStringArray(R.array.dict_theme);

    int[] gridViewImageId = {
            R.drawable.th_numbers,
            R.drawable.th_family,
            R.drawable.th_traveling,
            R.drawable.th_restaurant,
            R.drawable.th_market,

            R.drawable.th_body,
            R.drawable.th_health,
            R.drawable.th_animals,
            R.drawable.th_plants,
            R.drawable.th_sports,

            R.drawable.th_chemistry,
            R.drawable.th_physics,
            R.drawable.th_school,
            R.drawable.th_house,
            R.drawable.th_transport,

            R.drawable.th_church,
            R.drawable.th_food,
            R.drawable.th_everyday,
            R.drawable.th_pronouns,
            R.drawable.th_dictionary,

    };

    ThemeGridAdapter adapterViewAndroid = new ThemeGridAdapter(ThemeGrid.this, gridViewString, gridViewImageId);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

@Override
public void onItemClick(AdapterView<?> parent, View view,
        int i, long id) {
        //Toast.makeText(ThemeGrid.this, "GridView Item: " + gridViewString[+i] + i, Toast.LENGTH_LONG).show();
        theme_pos = i;

        startActivityForResult(new Intent(ThemeGrid.this, Game.class), 1);

        Intent myIntent = new Intent(ThemeGrid.this, Game.class);
        myIntent.putExtra("intTheme", theme_pos);
        startActivity(myIntent);

        }
        });

        }

    /**
     * load user setting
     */

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// app icon in action bar clicked; go home
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
