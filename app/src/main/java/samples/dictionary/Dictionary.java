package samples.dictionary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Locale;

import kiwi.employeedirectory.R;
import samples.dictionary.UI.AboutApp;
import samples.dictionary.UI.AboutDict;
import samples.dictionary.UI.DictionaryDetails;
import samples.dictionary.UI.Grammar;
import samples.dictionary.UI.Settings;
import samples.dictionary.UI.ThemeGrid;

@SuppressLint("NewApi")
public class Dictionary extends ListActivity implements OnClickListener, OnInitListener {

    public static final String GREEK = "(α|β|γ|δ|ε|ζ|ε|θ|ι|κ|λ|μ|ν|χ|ο|π|ρ|σ|τ|υ|φ|χ|ψ|ω)";

    public static TextToSpeech mTts;
    public static EditText searchText;
    public static ToggleButton tb;
    public static boolean fontSize1;
    public static boolean fontSize2;
    public static boolean fontSize3;
    public static boolean fontSize4;
    public static boolean fontSize5;
    public static boolean Theme;
    public static boolean Theme2;
    public static boolean speech;
    public static boolean show_art;
    public static boolean type_speech;

    public static boolean type_language;


    public static boolean favorites = false;
    static boolean show_type;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    protected Context context;
    protected SQLiteDatabase db;
    protected Cursor cursor;
    protected Cursor cursor2;
    protected Cursor cursor3;

    protected ListAdapter adapter;
    Locale loc;
    View Context;
    boolean no_accent;
    boolean searchtype1;
    boolean searchtype2;
    boolean speech_type;
    //boolean direction Greek;
    boolean country;
    int allWords;
    int svkWords;
    int greekWords;
    int DbVer;
    AlertDialog alertDialogChose;
    SharedPreferences prefs;
    private Menu menu;
    private LinearLayout layout; //main layout
    private Button btn_del;
    private Button btn_del_fav;
    public static boolean darkMode;

    private ArrayList<String> sId = new ArrayList<String>();        // id
    private ArrayList<String> sWord = new ArrayList<String>();      // word
    private ArrayList<String> sArth = new ArrayList<String>();      // arth
    private ArrayList<String> sDef = new ArrayList<String>();       // definition
    private ArrayList<String> sArth2 = new ArrayList<String>();     // arth2
    private ArrayList<String> sTrsp = new ArrayList<String>();      // trsp
    private ArrayList<String> sDef2 = new ArrayList<String>();      // definition 2

    private ArrayList<String> sDefSk = new ArrayList<String>();      // definition sk
    private ArrayList<String> sDefEl = new ArrayList<String>();      // definition el

    private ArrayList<String> sRem = new ArrayList<String>();       // remark

    private ArrayList<String> sRemSk = new ArrayList<String>();       // remark sk
    private ArrayList<String> sRemEl = new ArrayList<String>();       // remark el


    private ArrayList<String> sFav = new ArrayList<String>();       // favorite
    private ArrayList<String> sDec = new ArrayList<String>();       // declension
    private AlertDialog.Builder build;

    public Dictionary() {
        fontSize1 = false;
        fontSize2 = false;
        fontSize3 = true;
        fontSize4 = false;
        fontSize5 = false;
        Theme = true;
        Theme2 = false;
        country = false;
        Context = null;
        type_speech = false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUserSettings();

        isAudioRecordPermissionGranted();


        mTts = new TextToSpeech(this, this);

        if (Build.VERSION.SDK_INT >= 21) {
            if (Theme) {
                setTheme(R.style.AppTheme1);
            }

            if (Theme2) {
                setTheme(R.style.AppTheme2);
            }
        }

        setContentView(R.layout.main);

        searchText = findViewById(R.id.searchText);
        searchText.setOnClickListener(this);
        layout = findViewById(R.id.LinearLayout1);

        tb = findViewById(R.id.cTb);
        tb.setOnClickListener(this);

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        country = sharedpreferences.getBoolean("coutnry", false);


        if (country == true) {
            tb.setChecked(true);
        }


        btn_del = findViewById(R.id.btn_del_all);
        btn_del.setOnClickListener(this);

        btn_del_fav = findViewById(R.id.btn_del_fav);
        btn_del_fav.setOnClickListener(this);

        if (Theme) {
            btn_del.setBackgroundResource(R.drawable.ic_action_dell_all);
            btn_del_fav.setBackgroundResource(R.drawable.ic_action_dell_fav);
        }
        if (Theme2) {
            btn_del.setBackgroundResource(R.drawable.ic_action_dell_all2);
            btn_del_fav.setBackgroundResource(R.drawable.ic_action_dell_fav2);
        }

        if(Build.VERSION.SDK_INT >= 29) {
            int currentNightMode = this.getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO: {
                    Log.i("MODE4", " LIGHT");
                    btn_del.setBackgroundResource(R.drawable.ic_action_dell_all2);
                    btn_del_fav.setBackgroundResource(R.drawable.ic_action_dell_fav2);
                    break;
                }
                case Configuration.UI_MODE_NIGHT_YES:
                    //return true;
                {
                    Log.i("MODE4", " DARK");
                    setTheme(R.style.AppTheme2);


                    btn_del.setBackgroundResource(R.drawable.ic_action_dell_all2);
                    btn_del_fav.setBackgroundResource(R.drawable.ic_action_dell_fav2);
                    layout.setBackgroundColor(Color.parseColor("#FF121212"));
                    searchText.setTextColor(Color.parseColor("#FFFFFFFF"));
                    searchText.setBackgroundResource(R.drawable.bottom_line);

                    break;
                }
            }
        }

        db = (new DatabaseHelper(this)).getWritableDatabase();
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setTitle(getString(R.string.app_name));
        getDbVersion();

        /** start searching in start activity  */
        search(Context);

        /** start searching if is searchText changed */
        searchText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable editable) {
            }
            public void beforeTextChanged(CharSequence charsequence, int i, int j, int k) {
            }
            public void onTextChanged(CharSequence charsequence, int i, int j, int k) {
                search(Context);
            }
        });

        ListView listview1 = getListView();
        listview1.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if (favorites == false) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.FAVORITES, "*");
                    String ID;
                    ID = sId.get(arg2);
                    db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.ID + "=" + ID, null);

                    Log.i("position", ID);
                    Toast.makeText(getApplicationContext(), R.string.addto_favorites, Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        listview1.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if (!tb.isChecked()) {
                    Intent intent = new Intent(getApplicationContext(), DictionaryDetails.class);
                    //Cursor cursor = (Cursor) adapter.getItem(position);
                    intent.putExtra("DICT_ID", sId.get(arg2));
                    intent.putExtra("DICT_WORD", sWord.get(arg2));
                    intent.putExtra("DICT_ARTH", sArth.get(arg2));
                    intent.putExtra("DICT_DEFINITION", sDef.get(arg2));
                    intent.putExtra("DICT_ARTH2", sArth2.get(arg2));
                    intent.putExtra("DICT_TRSP", sTrsp.get(arg2));
                    intent.putExtra("DICT_DEFINITION2", sDef2.get(arg2));
                    intent.putExtra("DICT_DECLENSION", sDec.get(arg2));
                    intent.putExtra("COUNTRY", country);

                    if (!sDec.get(arg2).equalsIgnoreCase("")) startActivity(intent);
                }
                Log.i("position", "" + arg2);
            }
        });

        //checkPermission();
    }

    private boolean isAudioRecordPermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                Log.i("PERMISSION","Permission is granted");
                //Toast.makeText(CalendarActivity.this, "Permission is granted",  Toast.LENGTH_SHORT).show();
                return true;
            } else {

                Log.i("PERMISSION","Permission is revoked");
                //Toast.makeText(CalendarActivity.this, "Permission is revoked",  Toast.LENGTH_SHORT).show();

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 2);
                return false;
            }
        }
	        else {
	            //permission is automatically granted on sdk<23 upon installation
                Log.i("PERMISSION","Permission is granted");
                //Toast.makeText(CalendarActivity.this, "Permission is granted",  Toast.LENGTH_SHORT).show();
                return true;
        }

    }



    private void getDbVersion() {

        DbVer = db.getVersion();
        Log.i("DBV", "" + DbVer);
    }

    /**
     * end on create section
     **/

    protected void onResume() {
        boolean flag = Theme2;
        boolean flag6 = Theme;

        boolean flag1 = fontSize1;
        boolean flag2 = fontSize2;
        boolean flag3 = fontSize3;
        boolean flag4 = fontSize4;
        boolean flag5 = fontSize5;

        boolean flag7 = show_art;
        boolean flag8 = speech;
        boolean flag9 = speech;


        loadUserSettings();
        if (Theme) {
            btn_del.setBackgroundResource(R.drawable.ic_action_dell_all);
            btn_del_fav.setBackgroundResource(R.drawable.ic_action_dell_fav);
        } else {
            btn_del.setBackgroundResource(R.drawable.ic_action_dell_all2);
            btn_del_fav.setBackgroundResource(R.drawable.ic_action_dell_fav2);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            if (Theme) setTheme(R.style.AppTheme1);

            if (Theme2) setTheme(R.style.AppTheme2);
        }
        // for Android Q use resources from es/ drawable-night
        if(Build.VERSION.SDK_INT >= 29) {
            int currentNightMode = this.getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO: {
                    Log.i("MODE6", " LIGHT");
                    getListView().setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    getListView().setDivider(this.getResources().getDrawable(R.drawable.divider3));
                    getListView().setDividerHeight(3);

                    btn_del.setBackgroundResource(R.drawable.ic_action_dell_all);
                    btn_del_fav.setBackgroundResource(R.drawable.ic_action_dell_fav);
                    break;
                }
                case Configuration.UI_MODE_NIGHT_YES: {
                    Log.i("MODE6", " DARK");
                    setTheme(R.style.AppTheme2);

                    getListView().setDivider(this.getResources().getDrawable(R.drawable.divider2));
                    getListView().setDividerHeight(3);
                    btn_del.setBackgroundResource(R.drawable.ic_action_dell_all2);
                    btn_del_fav.setBackgroundResource(R.drawable.ic_action_dell_fav2);
                    getListView().setBackgroundColor(Color.parseColor("#FF121212"));
                    layout.setBackgroundColor(Color.parseColor("#FF121212"));
                    searchText.setTextColor(Color.parseColor("#FFFFFFFF"));
                    searchText.setBackgroundResource(R.drawable.bottom_line);
                    searchText.setHintTextColor(Color.parseColor("#FF777777"));

                    break;
                }
            }
        }


        /** for change theme */
        if (flag != Theme2) search(Context);

        if (flag6 != Theme) search(Context);

        /** for change font size */
        if (flag1 != fontSize1) search(Context);

        if (flag2 != fontSize2) search(Context);

        if (flag3 != fontSize3) search(Context);

        if (flag4 != fontSize4) search(Context);

        if (flag5 != fontSize5) search(Context);

        /** for change show arth */
        if (flag7 != show_art) search(Context);

        /** for change speech */
        if (flag8 != speech) search(Context);
        if (flag9 != type_speech) search(Context);

        if (tb.isChecked()) {
            country = true;
            search(Context);
        }

        /** for TTS */
        if (mTts != null) {

            mTts.stop();
            mTts.shutdown();
        }
        mTts = new TextToSpeech(this, this);
        super.onResume();

    }

    /**
     * %" + searchText.getText().toString() + "%" || is the concatenation operation in SQLite
     */

    public void search(View view) {

        loadUserSettings();
        ListView listview1 = getListView();
        Resources resources1 = getResources();

        if (Theme)  //white
        {
            listview1.setDivider(resources1.getDrawable(R.drawable.divider3));
            listview1.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            layout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            searchText.setTextColor(Color.parseColor("#FF000000"));
            searchText.setBackgroundResource(R.drawable.bottom_line);
            searchText.setHintTextColor(Color.parseColor("#FF777777"));

        }
        if (Theme2) //black
        {
            listview1.setDivider(resources1.getDrawable(R.drawable.divider2));
            listview1.setBackgroundColor(Color.parseColor("#FF121212"));
            layout.setBackgroundColor(Color.parseColor("#FF121212"));
            searchText.setTextColor(Color.parseColor("#FFFFFFFF"));
            searchText.setBackgroundResource(R.drawable.bottom_line);
            searchText.setHintTextColor(Color.parseColor("#FF777777"));

        }
        /** for Android Q use resources from res/ drawable-night */
        if(Build.VERSION.SDK_INT >= 29) {
            int currentNightMode = this.getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;

            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                searchText.setHintTextColor(Color.parseColor("#FF777777"));

                layout.setBackgroundColor(Color.parseColor("#FF121212"));
                searchText.setTextColor(Color.parseColor("#FFFFFFFF"));
                searchText.setBackgroundResource(R.drawable.bottom_line);
                listview1.setDivider(this.getDrawable(R.drawable.divider2));
                listview1.setBackgroundColor(Color.parseColor("#FF121212"));

                darkMode = true;
            } else {
                searchText.setHintTextColor(Color.parseColor("#FF777777"));
                listview1.setDivider(this.getDrawable(R.drawable.divider3));
                listview1.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

                layout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                searchText.setTextColor(Color.parseColor("#FF000000"));
                searchText.setBackgroundResource(R.drawable.bottom_line);
                darkMode = false;

            }
        }


        listview1.setDividerHeight(3);

/**    set search method   	***********************************************************************************************************************************/

/**    search Greek section 	*************************************************************************************************************/
        if (favorites == false) {
            searchText.setVisibility(View.VISIBLE);
            btn_del.setVisibility(View.VISIBLE);
            tb.setVisibility(View.VISIBLE);
            btn_del_fav.setVisibility(View.GONE);

            if (country) {
                setTitle(R.string.el_sk_directory);

                if (searchtype1 == true) {

                    if (no_accent == false) {
                        cursor = db.rawQuery("SELECT * FROM employee WHERE country = 0 AND word LIKE ?",
                                new String[]{searchText.getText().toString() + "%"});
                    } else {
                        cursor = db.rawQuery("SELECT * FROM employee WHERE country = 0 AND " +
                                        "replace(replace(replace(replace(replace(replace(replace(replace(replace(replace( lower(word)" +
                                        ", 'ά','α'), 'έ','ε'), 'ί','ι'), 'ϊ','ι'), 'ή','η'), 'ό','ο'), 'ύ','υ') ,'ώ','ω'),'ϋ','υ'),'ΐ','ι') LIKE ?",
                                new String[]{searchText.getText().toString() + "%"});
                    }
                } else {
                    if (no_accent == false) {
                        cursor = db.rawQuery("SELECT * FROM employee WHERE country = 0 AND word LIKE ?",
                                new String[]{"%" + searchText.getText().toString() + "%"});
                    } else {
                        cursor = db.rawQuery("SELECT * FROM employee WHERE country = 0 AND " +
                                        "replace(replace(replace(replace(replace(replace(replace(replace(replace(replace( lower(word)" +
                                        ", 'ά','α'), 'έ','ε'), 'ί','ι'), 'ϊ','ι'), 'ή','η'), 'ό','ο'), 'ύ','υ') ,'ώ','ω'),'ϋ','υ'),'ΐ','ι') LIKE ?",
                                new String[]{"%" + searchText.getText().toString() + "%"});
                    }
                }

                /** new adapter */
                sId.clear();
                sWord.clear();
                sArth.clear();
                sDef.clear();
                sArth2.clear();
                sTrsp.clear();
                sDef2.clear();
                sRem.clear();
                sFav.clear();
                sDec.clear();

                if (cursor.moveToFirst()) {
                    do {
                        sId.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID)));
                        sWord.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WORD)));
                        sArth.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ARTH)));
                        sDef.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEFINITION)));
                        sArth2.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ARTH2)));
                        sTrsp.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRSP)));

                        //sDef2.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEFINITION2)));

                        if(type_language == false) sDef2.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEFINITIONSK)));
                        if(type_language == true)  sDef2.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEFINITIONEL)));


                        //sRem.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REMARK)));
                        if(type_language == false) sRem.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REMARKSK)));
                        if(type_language == true) sRem.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REMARKEL)));

                        sFav.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FAVORITES)));
                        sDec.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DECLENSION)));


                    } while (cursor.moveToNext());

                    DictionaryAdapter dictadpt = new DictionaryAdapter(Dictionary.this, sId, sWord, sArth, sDef, sArth2, sTrsp, sDef2, sRem, sFav, sDec);
                    listview1.setAdapter(dictadpt);
                    listview1.requestLayout();
                    cursor.close();
                }

            } else

/**    search Slovak section 	*************************************************************************************************************/
            {
                setTitle(R.string.sk_el_directory);



                if (searchtype1 == true) {

                    if (no_accent == false) {
                        cursor2 = db.rawQuery("SELECT * FROM employee  WHERE " +
                                        "country = 1 AND word LIKE ?" ,
                                new String[]{searchText.getText().toString() + "%"});
                    } else {
                        cursor2 = db.rawQuery("SELECT * FROM employee WHERE " +
                                        "country = 1 AND " +
                                        "replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace" +
                                        "(replace(replace(replace( lower(word)" +
                                        ", 'á','a'), 'č','c'), 'ď','d'), 'é','e'), 'ť','t'), 'í','i'), 'ó','o') ,'š','s') ,'ô','o'),'ú','u'), 'ž','z')," +
                                        " 'ľ','l'), 'ň','n'), " +
                                        "'ý','y'), 'ĺ','l'), 'ŕ','r'), 'ä','a') LIKE ?",
                                new String[]{searchText.getText().toString() + "%"});
                    }
                } else {
                    if (no_accent == false) {
                        cursor2 = db.rawQuery("SELECT * FROM employee WHERE" +
                                        " country = 1 AND word LIKE ?",
                                new String[]{"%" + searchText.getText().toString() + "%"});
                    } else {
                        cursor2 = db.rawQuery("SELECT * FROM employee WHERE" +
                                        " country = 1 AND " +
                                        "replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace" +
                                        "(replace(replace(replace( lower(word)" +
                                        ", 'á','a'), 'č','c'), 'ď','d'), 'é','e'), 'ť','t'), 'í','i'), 'ó','o') ,'š','s') ,'ô','o'),'ú','u'), 'ž','z')," +
                                        " 'ľ','l'), 'ň','n'), " +
                                        "'ý','y'), 'ĺ','l'), 'ŕ','r'), 'ä','a') LIKE ?",
                                new String[]{"%" + searchText.getText().toString() + "%"});
                    }
                }

                /** new adapter */
                sId.clear();
                sWord.clear();
                sArth.clear();
                sDef.clear();
                sArth2.clear();
                sTrsp.clear();
                sDef2.clear();
                sRem.clear();
                sFav.clear();
                sDec.clear();

                if (cursor2.moveToFirst()) {
                    do {
                        sId.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ID)));
                        sWord.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.WORD)));
                        sArth.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ARTH)));
                        sDef.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.DEFINITION)));
                        sArth2.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.ARTH2)));
                        sTrsp.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.TRSP)));
                        //sDef2.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEFINITION2)));
                        if(type_language == false) sDef2.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.DEFINITIONSK)));
                        if(type_language == true) sDef2.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.DEFINITIONEL)));

                        //sRem.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REMARK)));
                        if(type_language == false) sRem.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.REMARKSK)));
                        if(type_language == true) sRem.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.REMARKEL)));

                        sFav.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.FAVORITES)));
                        sDec.add(cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.DECLENSION)));


                    } while (cursor2.moveToNext());

                    DictionaryAdapter dictadpt = new DictionaryAdapter(Dictionary.this, sId, sWord, sArth, sDef, sArth2, sTrsp, sDef2, sRem, sFav, sDec);
                    listview1.setAdapter(dictadpt);
                    listview1.requestLayout();

                    cursor2.close();
                }

            }
        } else {

            /** show favorites *********************************************/

            cursor3 = db.rawQuery("SELECT * FROM employee WHERE " +
                            " favorites = '*' AND word LIKE ?",
                    new String[]{"" + "%"});

            /** new adapter */
            sId.clear();
            sWord.clear();
            sArth.clear();
            sDef.clear();
            sArth2.clear();
            sTrsp.clear();
            sDef2.clear();
            sRem.clear();
            sFav.clear();
            sDec.clear();

            if (cursor3.moveToFirst()) {
                do {
                    sId.add(cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.ID)));
                    sWord.add(cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.WORD)));
                    sArth.add(cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.ARTH)));
                    sDef.add(cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.DEFINITION)));
                    sArth2.add(cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.ARTH2)));
                    sTrsp.add(cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.TRSP)));
                    //sDef2.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEFINITION2)));
                    if(type_language == false) sDef2.add(cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.DEFINITIONSK)));
                    else sDef2.add(cursor.getString(cursor3.getColumnIndex(DatabaseHelper.DEFINITIONEL)));

                    //sRem.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REMARK)));
                    if(type_language == false) sRem.add(cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.REMARKSK)));
                    if(type_language == true) sRem.add(cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.REMARKEL)));

                    sFav.add(cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.FAVORITES)));
                    sDec.add(cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.DECLENSION)));


                } while (cursor3.moveToNext());

                DictionaryAdapter dictadpt = new DictionaryAdapter(Dictionary.this, sId, sWord, sArth, sDef, sArth2, sTrsp, sDef2, sRem, sFav, sDec);
                listview1.setAdapter(dictadpt);
                listview1.requestLayout();

                cursor3.close();
            }

            searchText.setVisibility(View.GONE);
            btn_del.setVisibility(View.GONE);
            tb.setVisibility(View.GONE);
            btn_del_fav.setVisibility(View.VISIBLE);

        }

        /** end searching...  ************************************************/

    }

    /**
     * create menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        String locale = Locale.getDefault().getLanguage();
        if (!locale.equalsIgnoreCase("sk")) menu.findItem(R.id.action_grammary).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * choice from menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.speech) {

            if (isAudioRecordPermissionGranted()) {
                /** new STT dialog */
                if (favorites == false) {
                    STTDialog sttdialog = new STTDialog(Dictionary.this);
                    sttdialog.show();
                }

                return true;
            }
            else {

            }
        }

        if (id == R.id.favorites) {
            if (favorites == false) {
                favorites = true;
                item.setIcon(getResources().getDrawable(R.drawable.ic_action_favorites));
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_nospeech));
            } else {
                favorites = false;
                item.setIcon(getResources().getDrawable(R.drawable.ic_action_favorites2));
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_speech));

            }

            search(Context);

            return true;
        }

        if (id == R.id.action_setting) {
            startActivityForResult(new Intent(this, Settings.class), 1);
            return true;
        }

        /**
        if (id == R.id.action_pexeso) {
            startActivityForResult(new Intent(this, Game.class), 1);
            return true;
        }*/

        if (id == R.id.action_themes) {
            startActivityForResult(new Intent(this, ThemeGrid.class), 1);
            return true;
        }

        if (id == R.id.action_grammary) {
            startActivityForResult(new Intent(this, Grammar.class), 1);
            return true;
        }
        if (id == R.id.action_about) {
            startActivityForResult(new Intent(this, AboutApp.class), 1);
            return true;
        }
        if (id == R.id.action_aboutdict) {
            getCount();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Showing google speech input dialog not use
     */

    private void promptSpeechInput() {
        // TODO Auto-generated method stub
        String local = null;
        if (!tb.isChecked()) {
            local = "sk-SK";

        }
        if (tb.isChecked()) {
            local = "el-GR";

        }


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, local);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, local);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.noSupportLanguage), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchText.setText(result.get(0));
                }
                break;
            }

        }
    }

    private void getCount() {

        Intent intent2 = new Intent(this, AboutDict.class);

        cursor = db.rawQuery("SELECT * FROM employee", null);
        allWords = cursor.getCount();

        cursor = db.rawQuery("SELECT * FROM employee WHERE country = 1", null);
        svkWords = cursor.getCount();
        greekWords = allWords - svkWords;

        int dbv = db.getVersion();

        intent2.putExtra("SVK_W", svkWords);
        intent2.putExtra("ELL_W", greekWords);
        intent2.putExtra("DBV", dbv);

        startActivity(intent2);
    }

    private void loadUserSettings() {
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String s = sharedpreferences.getString("prefTheme", "light");
        if (s.equalsIgnoreCase("light")) {
            Theme = true;
            Theme2 = false;
            Log.i("Theme", "light");
        }
        if (s.equalsIgnoreCase("black")) {
            Theme = false;
            Theme2 = true;
            Log.i("Theme", "black");
        }

        String s1 = sharedpreferences.getString("prefFontSize", "medium_font");

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

        String s3 = sharedpreferences.getString("prefSearchType", "end");
        if (s3.equalsIgnoreCase("end")) {
            searchtype1 = true;
            searchtype2 = false;
            Log.i("searchtype", "end");
        }
        if (s3.equalsIgnoreCase("any")) {
            searchtype1 = false;
            searchtype2 = true;
            Log.i("searchtype", "any");
        }

        show_art = sharedpreferences.getBoolean("prefArticles", false);
        no_accent = sharedpreferences.getBoolean("prefAccent", false);
        speech = sharedpreferences.getBoolean("prefSpeech", false);
        show_type = sharedpreferences.getBoolean("prefClass", false);

        String a = sharedpreferences.getString("prefSpeechType", "slovak");
        if (a.equalsIgnoreCase("slovak")) {
            type_speech = false;
        }
        if (a.equalsIgnoreCase("greek")) {
            type_speech = true;
        }

        String l = sharedpreferences.getString("prefLanguage", "slovak");
        if (l.equalsIgnoreCase("slovak")) {
            type_language = false;
        }
        if (l.equalsIgnoreCase("greek")) {
            type_language = true;
        }
    }

    @Override
    public void onClick(View v) {
        /** for delete favorites */
        if (v == btn_del_fav) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE favorites = '*'", null);

            if (cursor.getCount() > 0) {

                build = new AlertDialog.Builder(Dictionary.this);
                build.setTitle(getResources().getString(R.string.dell_favorites) + "?");
                //build.setMessage(getResources().getString(R.string.dell_favorites) + "?");

                build.setPositiveButton(getResources().getString(R.string.yes),
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {

                                //Toast.makeText(
                                //		getApplicationContext(),getResources().getString(R.string.is_del_all), Toast.LENGTH_SHORT).show();


                                // delete - update all row in event column, favorites
                                String strSQL = "";
                                strSQL = "UPDATE " + DatabaseHelper.TABLE_NAME + " SET " + DatabaseHelper.FAVORITES + " = " + "0";
                                db.execSQL(strSQL);

                                search(Context);


                            }
                        });

                build.setNegativeButton(getResources().getString(R.string.no),
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = build.create();
                alert.show();
            }
        }

        /** for del all in edit text */
        if (v == btn_del) {
            searchText.setText("");
            search(Context);
        }

        /** tb */
        if (v == tb) {
            Log.d("Helper", "click to button4");
            if (tb.isChecked()) {
                country = true;
                setTitle(R.string.el_sk_directory);
                search(Context);
                searchText.setText("");
            } else {
                country = false;
                setTitle(R.string.sk_el_directory);
                search(Context);
                searchText.setText("");
            }
        }
    }

    @Override
    public void onDestroy() {

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = sharedpreferences.edit();

        editor.putBoolean("coutnry", country);  // Saving country
        editor.commit(); // commit

        Log.d("country", String.valueOf(country));

        /** Close the Text to Speech Library */
        if (mTts != null) {

            mTts.stop();
            mTts.shutdown();
            Log.d("DICTIONARY", "TTS Destroyed");
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub

        if (mTts != null) {

            mTts.stop();
            mTts.shutdown();
        }
        super.onPause();
    }

    public void onInit(int status) {
        // TODO Auto-generated method stub

        /** TTS is successfully initialized */
        if (status == TextToSpeech.SUCCESS) {

            /** Setting speech language */
            Locale loc = null;
            if (!tb.isChecked()) {
                loc = new Locale("el-GR");
            }
            if (tb.isChecked()) {
                loc = new Locale("sk-SK");
            }
            int result = mTts.setLanguage(loc);

            /** If your device doesn't support language you set above */
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                //Toast.makeText(getApplicationContext(), getResources().getString(R.string.noSupportLanguage), Toast.LENGTH_LONG).show();
                Log.i("TTS", "Language is not supported");
            } else {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.supportLanguage), Toast.LENGTH_LONG).show();
                Log.i("TTS2", "Language is supported");
            }

            /** TTS is not initialized properly */
        } else {
            Toast.makeText(this, getResources().getString(R.string.initilizationFailed), Toast.LENGTH_LONG).show();
            Log.i("TTS", "TTS Initialization Failed");
        }
    }


}