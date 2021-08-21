package samples.dictionary.UI;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import kiwi.employeedirectory.R;

public class AboutApp extends Activity implements OnClickListener {

    public boolean Theme;
    public boolean Theme2;
    private LinearLayout layout;
    private TextView appvers;
    private TextView info;
    private TextView copyright;
    private ImageView logo;

    private Button okButton;
    private Button sendBtn;
    private Button donate;
    private Button more;
    /**
     * strings for send email
     */
    private String recipient = "stefan.cizmar.pp@centrum.sk";
    private String subject = "Dictionary";
    private String body = "Sample text.";
    private SharedPreferences prefs;
    private int verCode = 0;
    private String verName;
    private PackageInfo manager;

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        setContentView(R.layout.aboutprog);

        setTitle(getString(R.string.about));

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        layout = findViewById(R.id.LinearLayoutApp);
        appvers = findViewById(R.id.textInfoDic);
        info = findViewById(R.id.textInfo);
        copyright = findViewById(R.id.textCopyright);
        logo = findViewById(R.id.imageLogo);

        if (Build.VERSION.SDK_INT < 21) {
            logo.setVisibility(View.GONE);
        }

        /**
         * buttons
         */
        okButton = findViewById(R.id.buttonOk2);
        sendBtn = findViewById(R.id.email_button);
        donate = findViewById(R.id.donate_button);
        more = findViewById(R.id.more_button);

        sendBtn.setTransformationMethod(null);

        more.setVisibility(View.GONE);

        okButton.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
        donate.setOnClickListener(this);
        more.setOnClickListener(this);

        okButton.setTextColor(Color.parseColor("#FFFFFFFF"));
        sendBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
        donate.setTextColor(Color.parseColor("#FFFFFFFF"));

        /**
         * set theme
         */
        if (Theme2) {
            layout.setBackgroundColor(Color.parseColor("#FF121212"));
            appvers.setTextColor(Color.parseColor("#2196f3"));
            info.setTextColor(Color.parseColor("#FFFFFFFF"));
            copyright.setTextColor(Color.parseColor("#FFFFFFFF"));
            }
            else {
            layout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            appvers.setTextColor(Color.parseColor("#2196f3"));
            info.setTextColor(Color.parseColor("#FF121212"));
            copyright.setTextColor(Color.parseColor("#FF121212"));
            }

        /**
         * get version code
         */
        try {
            manager = getPackageManager().getPackageInfo(getPackageName(), 0);
            } catch (NameNotFoundException e) {
            e.printStackTrace();
            }

        getVersionCode(manager);
        String appver = getString(R.string.app_name) + " v. " + verName;
        appvers.setText(appver);

    }

    public void onClick(View v) {
        // Parameter v stands for the view that was clicked.
        // getId() returns this view's identifier.
        if
        (v.getId() == R.id.buttonOk2) {
            finish();
            }
        if
        (v.getId() == R.id.donate_button) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=stefan%2ecizmar%2epp%40centrum%2esk&lc=SK&item_name=Mobile%20apps&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHosted")));
            Log.e("Load paypal", "paypal");
            }
        if
        (v.getId() == R.id.email_button) {
            sendEmail();
            }
        if
        (v.getId() == R.id.more_button) {
            Log.e("Load more", "more");

            Intent i = new Intent(getApplicationContext(), AboutMore.class);
            startActivity(i);
        }
    }

    /**
     * get version
     */
    public void getVersionCode(PackageInfo manager) {
        verCode = manager.versionCode;
        verName = manager.versionName;
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
        if (item.getItemId() == android.R.id.home) {
            // app icon in action bar clicked; go home
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * send email procedure
     */
    protected void sendEmail() {

        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        // prompts email clients only
        email.setType("message/rfc822");

        // EXTRA_EMAIL must field !!!
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, body);

        try {
            // the user can choose the email client
            startActivity(Intent.createChooser(email, "Choose an email client from..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AboutApp.this, "No email client installed.", Toast.LENGTH_LONG).show();
        }
    }
}
