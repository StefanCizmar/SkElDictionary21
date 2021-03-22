package samples.dictionary.UI;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import kiwi.employeedirectory.R;

public class Settings extends PreferenceActivity {

    //public static final String USER_PREFERENCE = "USER_PREFERENCES";
    private boolean Theme;
    private boolean Theme2;
    private String size;
    private boolean searchtype1;
    private boolean searchtype2;

    private boolean speechtype1;
    private boolean speechtype2;

    private boolean langtype1;
    private boolean langtype2;

    private ListPreference listpreference;

    public void onCreate(Bundle bundle)     {
        super.onCreate(bundle);

        loadUserSettings();

    if (Theme)
    {
      getListView().setBackgroundColor(Color.parseColor("#FFFFFFFF"));
      setTheme(R.style.AppThemeLight);
      Resources resources1 = getResources();
      getListView().setDivider(resources1.getDrawable(R.drawable.divider3));
      getListView().setDividerHeight(3);
    }
    if (Theme2)
    {
      getListView().setBackgroundColor(Color.parseColor("#FF121212"));
      setTheme(R.style.AppTheme);
      Resources resources = getResources();
      getListView().setDivider(resources.getDrawable(R.drawable.divider2));
      getListView().setDividerHeight(3);
    }
    if ( Build.VERSION.SDK_INT >= 21) {
        if (Theme)
		    {
		        setTheme(R.style.AppTheme1);
                getListView().setBackgroundColor(Color.parseColor("#FFFFFFFF"));

            }

        if (Theme2)
		    {
		        setTheme(R.style.AppTheme2);
                getListView().setBackgroundColor(Color.parseColor("#FF121212"));

            }
		}

        // for Android Q automatic sett to dark mode
        if(Build.VERSION.SDK_INT >= 29) {
            int currentNightMode = this.getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;

            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                getListView().setBackgroundColor(Color.parseColor("#FF121212"));

                getListView().setDivider(this.getResources().getDrawable(R.drawable.divider2));
                getListView().setDividerHeight(3);
                Theme = false;
                Theme2 = true;
            } else {
                getListView().setBackgroundColor(Color.parseColor("#FFFFFFFF"));

                getListView().setDivider(this.getResources().getDrawable(R.drawable.divider3));
                getListView().setDividerHeight(3);
                Theme = true;
                Theme2 = false;

            }
        }

        addPreferencesFromResource(R.xml.user_setting);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.theme_blue)));
        actionBar.setIcon( new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        if(Theme) {
            getListView().setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            setTheme(R.style.AppTheme1);
            Resources resources1 = getResources();
            getListView().setDivider(resources1.getDrawable(R.drawable.divider3));
            getListView().setDividerHeight(3);
        }
        else {
            getListView().setBackgroundColor(Color.parseColor("#FF121212"));
            setTheme(R.style.AppTheme2);
            Resources resources = getResources();
            getListView().setDivider(resources.getDrawable(R.drawable.divider2));
            getListView().setDividerHeight(3);
        }

        /**
         * get value of theme
         * */

        listpreference = (ListPreference)findPreference("prefTheme");
        if(Build.VERSION.SDK_INT >= 29) {
            getPreferenceScreen().findPreference("prefTheme").setEnabled(false);
        }
        if (listpreference.getValue() == null)
        {
            listpreference.setValueIndex(0);
        }

        listpreference.setSummary(listpreference.getValue());
        if (Theme ) listpreference.setSummary(R.string.themelight);
        else listpreference.setSummary(R.string.themeblack);

        listpreference.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object obj)
            {
                preference.setSummary(obj.toString());
            	recreate(); // redraw after change theme
                return true;
            }
        });

        /**
         * get value of speech type
         * */

        ListPreference listpreference3 = (ListPreference)findPreference("prefSpeechType");
        if (listpreference3.getValue() == null)
        {
            listpreference3.setValueIndex(0);
        }

        listpreference3.setSummary(listpreference3.getValue());
        if (speechtype1 ) listpreference3.setSummary(R.string.slovak);
        else listpreference3.setSummary(R.string.greek);

        listpreference3.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object obj)
            {
                preference.setSummary(obj.toString());
            	recreate(); // redraw after change theme

                return true;
            }
        });

        /**
         * get value of language type
         * */

        ListPreference listpreference4 = (ListPreference)findPreference("prefLanguage");
        if (listpreference4.getValue() == null)
        {
            listpreference4.setValueIndex(0);
        }

        listpreference4.setSummary(listpreference4.getValue());
        if (langtype1 ) listpreference4.setSummary(R.string.slo);
        else listpreference4.setSummary(R.string.gre);

        listpreference4.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object obj)
            {
                preference.setSummary(obj.toString());
                recreate(); // redraw after change theme

                return true;
            }

        });

        /**
         * get value of font size
         * */

        final ListPreference listpreference1 = (ListPreference)findPreference("prefFontSize");
        if (listpreference1.getValue() == null)
        {
            listpreference1.setValueIndex(2);
        }

        listpreference1.setSummary(listpreference1.getValue());
        if (listpreference1.getSummary().equals("huge_font")) listpreference1.setSummary(R.string.huge_font);
        if (listpreference1.getSummary().equals("large_font")) listpreference1.setSummary(R.string.large_font);
        if (listpreference1.getSummary().equals("medium_font")) listpreference1.setSummary(R.string.medium_font);
        if (listpreference1.getSummary().equals("small_font")) listpreference1.setSummary(R.string.small_font);
        if (listpreference1.getSummary().equals("tiny_font")) listpreference1.setSummary(R.string.tiny_font);
        listpreference1.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object obj)
            {
                preference.setSummary(obj.toString());
                if (preference.getSummary().equals("huge_font")) listpreference1.setSummary(R.string.huge_font);
                if (preference.getSummary().equals("large_font")) listpreference1.setSummary(R.string.large_font);
                if (preference.getSummary().equals("medium_font")) listpreference1.setSummary(R.string.medium_font);
                if (preference.getSummary().equals("small_font")) listpreference1.setSummary(R.string.small_font);
                if (preference.getSummary().equals("tiny_font")) listpreference1.setSummary(R.string.tiny_font);

                return true;
            }

        });

        /**
         * get value of search type
         * */

        final ListPreference listpreference2 = (ListPreference)findPreference("prefSearchType");
        if (listpreference2.getValue() == null)
        {
            listpreference2.setValueIndex(0);
        }

        listpreference2.setSummary(listpreference2.getValue());
        if (searchtype1 ) listpreference2.setSummary(R.string.endSubstring);
        else listpreference2.setSummary(R.string.anySubstring);

        listpreference2.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference2, Object obj)
            {

                preference2.setSummary(obj.toString());
                if (preference2.getSummary().equals("end")) listpreference2.setSummary(R.string.endSubstring);
                else listpreference2.setSummary(R.string.anySubstring);

                return true;
            }

        });

        /**
         * get value of checkBox accent
         * */

        CheckBoxPreference box_accent=(CheckBoxPreference)findPreference("prefAccent");

        if (box_accent.isChecked())
        {
        	box_accent.setDefaultValue(false);
        }

        /**
         * get value of checkBox articles
         * */

        CheckBoxPreference box=(CheckBoxPreference)findPreference("prefArticles");

        if (box.isChecked())
        {
        	box.setDefaultValue(false);
        }

    }

private void loadUserSettings()
    {
        /** theme */
        String s = PreferenceManager.getDefaultSharedPreferences(this).getString("prefTheme", "light");
        if (s.equalsIgnoreCase("light"))
        {
            Theme = true;
            Theme2 = false;
            Log.i("SETTING", "LIGHT");
        }
        if (s.equalsIgnoreCase("black"))
        {
            Theme = false;
            Theme2 = true;
            Log.i("SETTING", "BLACK");

        }

        /** font size */
        String f_size = PreferenceManager.getDefaultSharedPreferences(this).getString("prefFontSize", getString(R.string.medium_font));

        if (f_size.equalsIgnoreCase("huge_font")) size = f_size;

        if (f_size.equalsIgnoreCase("large_font")) size = f_size;

        if (f_size.equalsIgnoreCase("medium_font")) size = f_size;

        if (f_size.equalsIgnoreCase("small_font"))  size = f_size;

        if (f_size.equalsIgnoreCase("tiny_font")) size = f_size;

        /** search type */
        String s_type = PreferenceManager.getDefaultSharedPreferences(this).getString("prefSearchType", "end");
        if (s_type.equalsIgnoreCase("end"))
        {
        	 searchtype1 = true;
        	 searchtype2 = false;
        }
        if (s_type.equalsIgnoreCase("any"))
        {
        	 searchtype1 = false;
        	 searchtype2 = true;
        }

        /** speech type */
        String sp_type = PreferenceManager.getDefaultSharedPreferences(this).getString("prefSpeechType", "slovak");
        if (sp_type.equalsIgnoreCase("slovak"))
        {
        	speechtype1 = true;
        	speechtype2 = false;
        }
        if (sp_type.equalsIgnoreCase("greek"))
        {
        	speechtype1 = false;
        	speechtype2 = true;
        }

        /** language type */
        String lang_type = PreferenceManager.getDefaultSharedPreferences(this).getString("prefLanguage", "slovak");
        if (lang_type.equalsIgnoreCase("slovak"))
        {
            langtype1 = true;
            langtype2 = false;
        }
        if (lang_type.equalsIgnoreCase("greek"))
        {
            langtype1 = false;
            langtype2 = true;
        }
    }

    /**
     * choice from menu
     * */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
