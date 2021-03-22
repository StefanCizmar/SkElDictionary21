package samples.dictionary.UI;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import kiwi.employeedirectory.R;
import samples.dictionary.DatabaseHelper;
import samples.dictionary.Dictionary;


public class Game extends Activity implements View.OnClickListener {

    private boolean Theme;
    private boolean Theme2;
    private boolean fontSize1;
    private boolean fontSize2;
    private boolean fontSize3;
    private boolean fontSize4;
    private boolean fontSize5;
    private SQLiteDatabase db;
    private Cursor cursor;
    final ArrayList<Integer> numbers = new ArrayList<>();
    String someString;
    SharedPreferences prefs;
    int allWords;
    int tags;
    int score = 0;
    int leftCard = 0;
    int rightCard = 0;
    int firstCard = 0;
    int secondCard = 0;
    int success = 0;
    int hits = 0;

    private final Integer[] randomNumbers = new Integer[10];
    private final Integer[] randomPo = new Integer[10];
    private TextView tv1;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn10;
    private Button btn11;
    private Button btn12;
    private Button btn13;
    private Button btn14;
    private Button btn15;
    private Button btn16;
    private Button btn17;
    private Button btn18;
    private Button btn19;
    private Button btn20;
    private TableLayout tl;

    private ImageView ivCheck;

    int pos_theme;

    boolean speech = true;
    private String bttext;
    Locale loc;
    private TextToSpeech mTts;

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
        setContentView(R.layout.pexeso);

        tl = findViewById(R.id.tableLayout1);
        tv1 = findViewById(R.id.tvFavorites);
        if(Theme) {
            tl.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

            tv1.setTextColor(Color.parseColor("#FF121212"));
        }
        if(Theme2) {
            tl.setBackgroundColor(Color.parseColor("#FF121212"));
            tv1.setTextColor(Color.parseColor("#FFFFFFFF"));

        }


        setTitle(getString(R.string.choose_theme));
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tv1 = findViewById(R.id.tvFavorites);
        tv1.setText("0, 0%");

        Intent mIntent = getIntent();
        pos_theme = mIntent.getIntExtra("intTheme", 0);
        //if(pos_theme == -1) finish();

        pos_theme = pos_theme + 2;
        Log.e("Load dict theme", "" + pos_theme);
        Log.e("Create", "create");

        /**
         * 1. column **********************************************
         */

        btn1 = findViewById(R.id.btnSpeech);
        btn1.setOnClickListener(this);
        btn1.setTransformationMethod(null);

        btn2 = findViewById(R.id.button3);
        btn2.setOnClickListener(this);
        btn2.setTransformationMethod(null);

        btn3 = findViewById(R.id.button5);
        btn3.setOnClickListener(this);
        btn3.setTransformationMethod(null);

        btn4 = findViewById(R.id.button7);
        btn4.setOnClickListener(this);
        btn4.setTransformationMethod(null);

        btn5 = findViewById(R.id.button9);
        btn5.setOnClickListener(this);
        btn5.setTransformationMethod(null);

        btn6 = findViewById(R.id.button11);
        btn6.setOnClickListener(this);
        btn6.setTransformationMethod(null);

        btn7 = findViewById(R.id.button13);
        btn7.setOnClickListener(this);
        btn7.setTransformationMethod(null);

        btn8 = findViewById(R.id.button15);
        btn8.setOnClickListener(this);
        btn8.setTransformationMethod(null);

        btn9 = findViewById(R.id.button17);
        btn9.setOnClickListener(this);
        btn9.setTransformationMethod(null);

        btn10 = findViewById(R.id.button19);
        btn10.setOnClickListener(this);
        btn10.setTransformationMethod(null);

        /** 2. column ***********************************************/

        btn11 = findViewById(R.id.button2);
        btn11.setOnClickListener(this);
        btn11.setTransformationMethod(null);

        btn12 = findViewById(R.id.button4);
        btn12.setOnClickListener(this);
        btn12.setTransformationMethod(null);

        btn13 = findViewById(R.id.button6);
        btn13.setOnClickListener(this);
        btn13.setTransformationMethod(null);

        btn14 = findViewById(R.id.button8);
        btn14.setOnClickListener(this);
        btn14.setTransformationMethod(null);

        btn15 = findViewById(R.id.button10);
        btn15.setOnClickListener(this);
        btn15.setTransformationMethod(null);

        btn16 = findViewById(R.id.button12);
        btn16.setOnClickListener(this);
        btn16.setTransformationMethod(null);

        btn17 = findViewById(R.id.button14);
        btn17.setOnClickListener(this);
        btn17.setTransformationMethod(null);

        btn18 = findViewById(R.id.button16);
        btn18.setOnClickListener(this);
        btn18.setTransformationMethod(null);

        btn19 = findViewById(R.id.button18);
        btn19.setOnClickListener(this);
        btn19.setTransformationMethod(null);

        btn20 = findViewById(R.id.button20);
        btn20.setOnClickListener(this);
        btn20.setTransformationMethod(null);

        ivCheck = findViewById(R.id.ivCheck);
        ivCheck.setVisibility(View.INVISIBLE);

        if (fontSize1) {
            btn1.setTextSize(14);
            btn2.setTextSize(14);
            btn3.setTextSize(14);
            btn4.setTextSize(14);
            btn5.setTextSize(14);
            btn6.setTextSize(14);
            btn7.setTextSize(14);
            btn8.setTextSize(14);
            btn9.setTextSize(14);
            btn10.setTextSize(14);
            btn11.setTextSize(14);
            btn12.setTextSize(14);
            btn13.setTextSize(14);
            btn14.setTextSize(14);
            btn15.setTextSize(14);
            btn16.setTextSize(14);
            btn17.setTextSize(14);
            btn18.setTextSize(14);
            btn19.setTextSize(14);
            btn20.setTextSize(14);

        }
        if (fontSize2) {
            btn1.setTextSize(15);
            btn2.setTextSize(15);
            btn3.setTextSize(15);
            btn4.setTextSize(15);
            btn5.setTextSize(15);
            btn6.setTextSize(15);
            btn7.setTextSize(15);
            btn8.setTextSize(15);
            btn9.setTextSize(15);
            btn10.setTextSize(15);
            btn11.setTextSize(15);
            btn12.setTextSize(15);
            btn13.setTextSize(15);
            btn14.setTextSize(15);
            btn15.setTextSize(15);
            btn16.setTextSize(15);
            btn17.setTextSize(15);
            btn18.setTextSize(15);
            btn19.setTextSize(15);
            btn20.setTextSize(15);
        }
        if (fontSize3) {
            btn1.setTextSize(16);
            btn2.setTextSize(16);
            btn3.setTextSize(16);
            btn4.setTextSize(16);
            btn5.setTextSize(16);
            btn6.setTextSize(16);
            btn7.setTextSize(16);
            btn8.setTextSize(16);
            btn9.setTextSize(16);
            btn10.setTextSize(16);
            btn11.setTextSize(16);
            btn12.setTextSize(16);
            btn13.setTextSize(16);
            btn14.setTextSize(16);
            btn15.setTextSize(16);
            btn16.setTextSize(16);
            btn17.setTextSize(16);
            btn18.setTextSize(16);
            btn19.setTextSize(16);
            btn20.setTextSize(16);
        }
        if (fontSize4) {
            btn1.setTextSize(17);
            btn2.setTextSize(17);
            btn3.setTextSize(17);
            btn4.setTextSize(17);
            btn5.setTextSize(17);
            btn6.setTextSize(17);
            btn7.setTextSize(17);
            btn8.setTextSize(17);
            btn9.setTextSize(17);
            btn10.setTextSize(17);
            btn11.setTextSize(17);
            btn12.setTextSize(17);
            btn13.setTextSize(17);
            btn14.setTextSize(17);
            btn15.setTextSize(17);
            btn16.setTextSize(17);
            btn17.setTextSize(17);
            btn18.setTextSize(17);
            btn19.setTextSize(17);
            btn20.setTextSize(17);
        }
        if (fontSize5) {
            btn1.setTextSize(18);
            btn2.setTextSize(18);
            btn3.setTextSize(18);
            btn4.setTextSize(18);
            btn5.setTextSize(18);
            btn6.setTextSize(18);
            btn7.setTextSize(18);
            btn8.setTextSize(18);
            btn9.setTextSize(18);
            btn10.setTextSize(18);
            btn11.setTextSize(18);
            btn12.setTextSize(18);
            btn13.setTextSize(18);
            btn14.setTextSize(18);
            btn15.setTextSize(18);
            btn16.setTextSize(18);
            btn17.setTextSize(18);
            btn18.setTextSize(18);
            btn19.setTextSize(18);
            btn20.setTextSize(18);
        }


        /**    .... Initialization....    **/

        /**
         * DB query: pos_theme = position + 2
         *  2 - numbers,...
         *
         */

        int pos =1;
        db = (new DatabaseHelper(this)).getWritableDatabase();


        if(pos_theme == 2) pos = 2;     // numbers
        if(pos_theme == 3) pos = 3;     // family
        if(pos_theme == 4) pos = 5;     // traveling
        if(pos_theme == 5) pos = 7;     // restaurant
        if(pos_theme == 6) pos = 4;     // market
        if(pos_theme == 7) pos = 6;     // body
        if(pos_theme == 8) pos = 8;     // health
        if(pos_theme == 9) pos = 9;     // animals
        if(pos_theme == 10) pos = 11;     // plants
        if(pos_theme == 11) pos = 12;     // sports
        if(pos_theme == 12) pos = 20;     // chemistry
        if(pos_theme == 13) pos = 13;     // physics
        if(pos_theme == 14) pos = 10;     // school
        if(pos_theme == 15) pos = 14;     // housing
        if(pos_theme == 16) pos = 15;     // transporting
        if(pos_theme == 17) pos = 17;     // church
        if(pos_theme == 18) pos = 22;     // food
        if(pos_theme == 19) pos = 16;     // everyday
        if(pos_theme == 20) pos = 19;     // pronouns


        //all
        if(pos_theme == 21) {
            cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE nofrases >= " +1, null);
            allWords = cursor.getCount();
        }
        else {
            cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE nofrases = " + pos, null);
            allWords = cursor.getCount();
        }
        Resources res = getResources();
        String[] themeItems = res.getStringArray(R.array.dict_theme);
        String themeN = themeItems[pos_theme -2];

        setTitle(themeN);

        Log.e("All words: ", "" + allWords);

        generateRN();        // generate random numbers
        generateRP();        // generate random positions
        generateWords();    // generate words

        /** Text to speech init **/
        mTts =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {

                    loc = new Locale("sk");
                    mTts.setLanguage(loc);

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.supportLanguage), Toast.LENGTH_LONG).show();
                    Log.i("TTS2", "Language is supported");
                }
                else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.initilizationFailed), Toast.LENGTH_LONG).show();
                    Log.i("TTS", "TTS Initialization Failed");
                }
            }
        });
    }

    private void generateRN() {
        /**
         *  Generating 10 random integers in range selected words.
         */

        //note a single Random object is reused here
        Random randomGenerator = new Random();
        for (int idx = 0; idx <= 9; ++idx) {
            int randomInt = randomGenerator.nextInt(allWords - 1);
            randomNumbers[idx] = randomInt + 1;
            //Log.e("Generated: " , ""+randomInt);
            Log.e("Generated numbers: ", "" + randomNumbers[idx]);

        }
    }

    private void generateRP() {
        // TODO generate random unique numbers
        numbers.clear();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        // Log.e("Generated Positions: " , ""+numbers);
        //
        randomPo[0] = numbers.get(0);
        randomPo[1] = numbers.get(1);
        randomPo[2] = numbers.get(2);
        randomPo[3] = numbers.get(3);
        randomPo[4] = numbers.get(4);
        randomPo[5] = numbers.get(5);
        randomPo[6] = numbers.get(6);
        randomPo[7] = numbers.get(7);
        randomPo[8] = numbers.get(8);
        randomPo[9] = numbers.get(9);
        Log.e("Generated Position1: ", "" + randomPo[0]);
        Log.e("Generated Position2: ", "" + randomPo[1]);
        Log.e("Generated Position3: ", "" + randomPo[2]);
        Log.e("Generated Position4: ", "" + randomPo[3]);
        Log.e("Generated Position5: ", "" + randomPo[4]);
        Log.e("Generated Position6: ", "" + randomPo[5]);
        Log.e("Generated Position7: ", "" + randomPo[6]);
        Log.e("Generated Position8: ", "" + randomPo[7]);
        Log.e("Generated Position9: ", "" + randomPo[8]);
        Log.e("Generated Position10: ", "" + randomPo[9]);

    }

    @SuppressLint("ResourceType")
    private void generateWords() {
        // TODO Auto-generated method stub

        cursor.moveToPosition(randomNumbers[0]);
        someString = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("definition")));
        cleanStr(someString);
        btn1.setText(someString);
        btn1.setId(1);
        btn1.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[randomPo[0]]);
        btn11.setText(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("word"))));
        btn11.setId(11);
        btn11.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[1]);
        someString = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("definition")));
        cleanStr(someString);
        btn2.setText(someString);
        btn2.setId(2);
        btn2.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[randomPo[1]]);
        btn12.setText(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("word"))));
        btn12.setId(12);
        btn12.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[2]);
        someString = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("definition")));
        cleanStr(someString);
        btn3.setText(someString);
        btn3.setId(3);
        btn3.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[randomPo[2]]);
        btn13.setText(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("word"))));
        btn13.setId(13);
        btn13.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[3]);
        someString = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("definition")));
        cleanStr(someString);
        btn4.setText(someString);
        btn4.setId(4);
        btn4.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[randomPo[3]]);
        btn14.setText(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("word"))));
        btn14.setId(14);
        btn14.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[4]);
        someString = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("definition")));
        cleanStr(someString);
        btn5.setText(someString);
        btn5.setId(5);
        btn5.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[randomPo[4]]);
        btn15.setText(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("word"))));
        btn15.setId(15);
        btn15.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[5]);
        someString = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("definition")));
        cleanStr(someString);
        btn6.setText(someString);
        btn6.setId(6);
        btn6.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[randomPo[5]]);
        btn16.setText(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("word"))));
        btn16.setId(16);
        btn16.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[6]);
        someString = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("definition")));
        cleanStr(someString);
        btn7.setText(someString);
        btn7.setId(7);
        btn7.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[randomPo[6]]);
        btn17.setText(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("word"))));
        btn17.setId(17);
        btn17.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[7]);
        someString = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("definition")));
        cleanStr(someString);
        btn8.setText(someString);
        btn8.setId(8);
        btn8.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[randomPo[7]]);
        btn18.setText(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("word"))));
        btn18.setId(18);
        btn18.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[8]);
        someString = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("definition")));
        cleanStr(someString);
        btn9.setText(someString);
        btn9.setId(9);
        btn9.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[randomPo[8]]);
        btn19.setText(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("word"))));
        btn19.setId(19);
        btn19.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[9]);
        someString = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("definition")));
        cleanStr(someString);
        btn10.setText(someString);
        btn10.setId(10);
        btn10.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        cursor.moveToPosition(randomNumbers[randomPo[9]]);
        btn20.setText(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("word"))));
        btn20.setId(20);
        btn20.setBackgroundColor(getResources().getColor(R.color.theme_blue));

    }

    private void cleanStr(String someString2) {
        // TODO del suffix

        someString = someString.replaceAll("\\s-ικο", "");
        someString = someString.replaceAll("\\s-εία", "");
        someString = someString.replaceAll("\\s-ιά", "");
        someString = someString.replaceAll("\\s-ής", "");
        someString = someString.replaceAll("\\s-ές", "");
        someString = someString.replaceAll("\\s-ης", "");
        someString = someString.replaceAll("\\s-ες", "");
        someString = someString.replaceAll("\\s-ή", " ");
        someString = someString.replaceAll("\\s-ό", "");
        someString = someString.replaceAll("\\s-η", " ");
        someString = someString.replaceAll("\\s-ο", "");

        someString = someString.replaceAll("\\s-ύ", "");
        someString = someString.replaceAll("\\s-α", "");
        someString = someString.replaceAll("\\s-ά", "");
        someString = someString.replaceAll("\\s-ι", "");
        someString = someString.replaceAll("\\s-ί", "");

        //someString = someString.replaceAll("\\sς", "");

        someString = someString.replaceAll("\\s/", "");

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        tags = v.getId();
        Log.e("Tag is: ", "" + tags);

        /**
         * add speech to click
         * **/
        bttext = ( (TextView) v ).getText().toString();

        if(speech) speech();



        /** end **/
        hits = hits + 1;
        Log.e("hits: ", "" + hits);
        if ((hits % 2) != 0) {
            firstCard = tags;
            Log.e("firstCard: ", "" + tags);
        }
        if ((hits % 2) == 0) {
            secondCard = tags;
            Log.e("secondCard ", "" + tags);
        }

        getPair(tags);
        //if (tags >= 6){
        //v.setBackgroundColor(Color.parseColor("#FF0055aa"));
        //}
    }

    private void speech() {
        speech = true;

        Log.i("TEXT", bttext);

        if(tags > 10 ) loc = new Locale("sk");
        if(tags < 11 )  loc = new Locale("el");
        mTts.setLanguage(loc);

        if(!Dictionary.type_speech && tags > 10){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mTts.speak(bttext, TextToSpeech.QUEUE_FLUSH,null,null);
            } else {
                mTts.speak(bttext, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        if(Dictionary.type_speech && tags < 11) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mTts.speak(bttext, TextToSpeech.QUEUE_FLUSH,null,null);
            } else {
                mTts.speak(bttext, TextToSpeech.QUEUE_FLUSH, null);
            }
        }


    }

    private Integer getPair(int tags2) {
        // TODO Auto-generated method stub

        if (tags < 11) {

            leftCard = tags;
            Log.e("leftCard is: ", "" + tags);
            redrawCard();
        }
        if (tags > 10) {

            rightCard = tags;
            Log.e("rightCard is: ", "" + tags);
            redrawCard();
        }

        return tags;
    }

    private void redrawCard() {
        // TODO Auto-generated method stub

        int p1 = 0;
        /** cleaning */
        if (btn1.isEnabled()) btn1.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn2.isEnabled()) btn2.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn3.isEnabled()) btn3.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn4.isEnabled()) btn4.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn5.isEnabled()) btn5.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn6.isEnabled()) btn6.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn7.isEnabled()) btn7.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn8.isEnabled()) btn8.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn9.isEnabled()) btn9.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn10.isEnabled()) btn10.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn11.isEnabled()) btn11.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn12.isEnabled()) btn12.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn13.isEnabled()) btn13.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn14.isEnabled()) btn14.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn15.isEnabled()) btn15.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn16.isEnabled()) btn16.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn17.isEnabled()) btn17.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn18.isEnabled()) btn18.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn19.isEnabled()) btn19.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        if (btn20.isEnabled()) btn20.setBackgroundColor(getResources().getColor(R.color.theme_blue));

        /** mark left cards */
        if (leftCard == 1) {
            if (btn1.isEnabled()) btn1.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (leftCard == 2) {
            if (btn2.isEnabled()) btn2.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (leftCard == 3) {
            if (btn3.isEnabled()) btn3.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (leftCard == 4) {
            if (btn4.isEnabled()) btn4.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (leftCard == 5) {
            if (btn5.isEnabled()) btn5.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (leftCard == 6) {
            if (btn6.isEnabled()) btn6.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (leftCard == 7) {
            if (btn7.isEnabled()) btn7.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (leftCard == 8) {
            if (btn8.isEnabled()) btn8.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (leftCard == 9) {
            if (btn9.isEnabled()) btn9.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (leftCard == 10) {
            if (btn10.isEnabled()) btn10.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        /** mark right cards */

        if (rightCard == 11) {
            if (btn11.isEnabled()) btn11.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (rightCard == 12) {
            if (btn12.isEnabled()) btn12.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (rightCard == 13) {
            if (btn13.isEnabled()) btn13.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (rightCard == 14) {
            if (btn14.isEnabled()) btn14.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (rightCard == 15) {
            if (btn15.isEnabled()) btn15.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (rightCard == 16) {
            if (btn16.isEnabled()) btn16.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (rightCard == 17) {
            if (btn17.isEnabled()) btn17.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (rightCard == 18) {
            if (btn18.isEnabled()) btn18.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (rightCard == 19) {
            if (btn19.isEnabled()) btn19.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        if (rightCard == 20) {
            if (btn20.isEnabled()) btn20.setBackgroundColor(Color.parseColor("#FF0055aa"));
        }
        /** cleaning */
        if (leftCard == 0 && rightCard == 0) {
            btn1.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn2.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn3.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn4.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn5.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn6.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn7.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn8.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn9.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn10.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn11.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn12.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn13.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn14.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn15.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn16.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn17.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn18.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn19.setBackgroundColor(getResources().getColor(R.color.theme_blue));
            btn20.setBackgroundColor(getResources().getColor(R.color.theme_blue));
        }

        /** if match	*/
        if (rightCard == 11) p1 = randomPo[0] + 1;
        if (rightCard == 12) p1 = randomPo[1] + 1;
        if (rightCard == 13) p1 = randomPo[2] + 1;
        if (rightCard == 14) p1 = randomPo[3] + 1;
        if (rightCard == 15) p1 = randomPo[4] + 1;
        if (rightCard == 16) p1 = randomPo[5] + 1;
        if (rightCard == 17) p1 = randomPo[6] + 1;
        if (rightCard == 18) p1 = randomPo[7] + 1;
        if (rightCard == 19) p1 = randomPo[8] + 1;
        if (rightCard == 20) p1 = randomPo[9] + 1;

        if (leftCard == p1) {

            if (leftCard == 1) {
                btn1.setBackgroundColor(Color.parseColor("#FF000000"));
                btn1.setEnabled(false);
            }
            if (leftCard == 2) {
                btn2.setBackgroundColor(Color.parseColor("#FF000000"));
                btn2.setEnabled(false);
            }
            if (leftCard == 3) {
                btn3.setBackgroundColor(Color.parseColor("#FF000000"));
                btn3.setEnabled(false);
            }
            if (leftCard == 4) {
                btn4.setBackgroundColor(Color.parseColor("#FF000000"));
                btn4.setEnabled(false);
            }
            if (leftCard == 5) {
                btn5.setBackgroundColor(Color.parseColor("#FF000000"));
                btn5.setEnabled(false);
            }

            if (leftCard == 6) {
                btn6.setBackgroundColor(Color.parseColor("#FF000000"));
                btn6.setEnabled(false);
            }
            if (leftCard == 7) {
                btn7.setBackgroundColor(Color.parseColor("#FF000000"));
                btn7.setEnabled(false);
            }
            if (leftCard == 8) {
                btn8.setBackgroundColor(Color.parseColor("#FF000000"));
                btn8.setEnabled(false);
            }
            if (leftCard == 9) {
                btn9.setBackgroundColor(Color.parseColor("#FF000000"));
                btn9.setEnabled(false);
            }
            if (leftCard == 10) {
                btn10.setBackgroundColor(Color.parseColor("#FF000000"));
                btn10.setEnabled(false);
            }
            if (rightCard == 11) {
                btn11.setBackgroundColor(Color.parseColor("#FF000000"));
                btn11.setEnabled(false);
            }
            if (rightCard == 12) {
                btn12.setBackgroundColor(Color.parseColor("#FF000000"));
                btn12.setEnabled(false);
            }
            if (rightCard == 13) {
                btn13.setBackgroundColor(Color.parseColor("#FF000000"));
                btn13.setEnabled(false);
            }
            if (rightCard == 14) {
                btn14.setBackgroundColor(Color.parseColor("#FF000000"));
                btn14.setEnabled(false);
            }
            if (rightCard == 15) {
                btn15.setBackgroundColor(Color.parseColor("#FF000000"));
                btn15.setEnabled(false);
            }

            if (rightCard == 16) {
                btn16.setBackgroundColor(Color.parseColor("#FF000000"));
                btn16.setEnabled(false);
            }
            if (rightCard == 17) {
                btn17.setBackgroundColor(Color.parseColor("#FF000000"));
                btn17.setEnabled(false);
            }
            if (rightCard == 18) {
                btn18.setBackgroundColor(Color.parseColor("#FF000000"));
                btn18.setEnabled(false);
            }
            if (rightCard == 19) {
                btn19.setBackgroundColor(Color.parseColor("#FF000000"));
                btn19.setEnabled(false);
            }
            if (rightCard == 20) {
                btn20.setBackgroundColor(Color.parseColor("#FF000000"));
                btn20.setEnabled(false);
            }
            /** add score if success*/
            score = score + 10;
            success = (score * 20) / hits;
            tv1.setText("" + score + ", " + success + "%");
            ivCheck.setVisibility(View.VISIBLE);
            ivCheck.setImageResource(R.drawable.ok);
            if(score == 100) {
                ivCheck.setVisibility(View.INVISIBLE);
                displayDialog();
            }


        } else {
            if ((hits % 2) == 0) {

                if (btn1.isEnabled())
                    btn1.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn2.isEnabled())
                    btn2.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn3.isEnabled())
                    btn3.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn4.isEnabled())
                    btn4.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn5.isEnabled())
                    btn5.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn6.isEnabled())
                    btn6.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn7.isEnabled())
                    btn7.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn8.isEnabled())
                    btn8.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn9.isEnabled())
                    btn9.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn10.isEnabled())
                    btn10.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn11.isEnabled())
                    btn11.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn12.isEnabled())
                    btn12.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn13.isEnabled())
                    btn13.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn14.isEnabled())
                    btn14.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn15.isEnabled())
                    btn15.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn16.isEnabled())
                    btn16.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn17.isEnabled())
                    btn17.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn18.isEnabled())
                    btn18.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn19.isEnabled())
                    btn19.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                if (btn20.isEnabled())
                    btn20.setBackgroundColor(getResources().getColor(R.color.theme_blue));

                if (firstCard <= 10 && secondCard <= 10) {
                    if (btn1.isEnabled())
                        btn1.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn2.isEnabled())
                        btn2.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn3.isEnabled())
                        btn3.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn4.isEnabled())
                        btn4.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn5.isEnabled())
                        btn5.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn6.isEnabled())
                        btn6.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn7.isEnabled())
                        btn7.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn8.isEnabled())
                        btn8.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn9.isEnabled())
                        btn9.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn10.isEnabled())
                        btn10.setBackgroundColor(getResources().getColor(R.color.theme_blue));

                    hits = hits - 2;

                }

                if (firstCard >= 11 && secondCard  >= 11) {
                    if (btn11.isEnabled())
                        btn11.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn12.isEnabled())
                        btn12.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn13.isEnabled())
                        btn13.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn14.isEnabled())
                        btn14.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn15.isEnabled())
                        btn15.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn16.isEnabled())
                        btn16.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn17.isEnabled())
                        btn17.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn18.isEnabled())
                        btn18.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn19.isEnabled())
                        btn19.setBackgroundColor(getResources().getColor(R.color.theme_blue));
                    if (btn20.isEnabled())
                        btn20.setBackgroundColor(getResources().getColor(R.color.theme_blue));

                    hits = hits - 2;


                }
                leftCard = 0;
                rightCard = 0;
                ivCheck.setVisibility(View.VISIBLE);

                ivCheck.setImageResource(R.drawable.notok);

                //if (hits != 0) success = (score * 20) / hits;
                //tv1.setText("" + score + ", " + success + "%");
            }

        }

    }

    private void displayDialog() {
        new AlertDialog.Builder(Game.this)
                .setTitle(R.string.end_test)
                .setMessage(getString(R.string.success)  + " " + success + "%")
                .setPositiveButton(R.string.refresh, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        tv1.setText("0, 0%");
                        generateRN();
                        generateRP();
                        generateWords();
                        enableButtons();
                        leftCard = 0;
                        rightCard = 0;
                        score = 0;
                        hits = 0;
                        success = 0;
                        ivCheck.setVisibility(View.INVISIBLE);

                        dialog.cancel();
                    }
                }).show();

    }


    private void enableButtons() {
        // TODO Auto-generated method stub
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);
        btn9.setEnabled(true);
        btn10.setEnabled(true);
        btn11.setEnabled(true);
        btn12.setEnabled(true);
        btn13.setEnabled(true);
        btn14.setEnabled(true);
        btn15.setEnabled(true);
        btn16.setEnabled(true);
        btn17.setEnabled(true);
        btn18.setEnabled(true);
        btn19.setEnabled(true);
        btn20.setEnabled(true);
    }

    /**
     * set theme
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
     * create menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pexeso_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {

                //return to Theme grid activity
                Intent homeIntent = new Intent(this, ThemeGrid.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                }
            case R.id.action_refresh: {
                tv1.setText("0, 0%");
                generateRN();
                generateRP();
                generateWords();
                enableButtons();
                leftCard = 0;
                rightCard = 0;
                score = 0;
                hits = 0;
                success = 0;
                ivCheck.setVisibility(View.INVISIBLE);

                return true;
                }
            case R.id.action_sound: {
                if(speech== true) {
                    speech = false;
                    item.setIcon(getResources().getDrawable(R.drawable.ic_action_soundoff));
                }
                else {
                    speech = true;
                    item.setIcon(getResources().getDrawable(R.drawable.ic_action_sound));

                }

            }
                default:
                return super.onOptionsItemSelected(item);

        }
    }


}
