package samples.dictionary;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RemoteViews;

import java.util.Random;

import kiwi.employeedirectory.R;

public class WidgetDictionaryProvider extends AppWidgetProvider {

    protected SQLiteDatabase db;
    protected Cursor cursor;
    int allWords;
    int randomInt;
    String someString;
    String someStringSk;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            /**
             * Initialization
             */
            db = (new DatabaseHelper(context)).getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE nofrases = '1'", null);
            allWords = cursor.getCount();
            //Log.e("All words: " , "" + allWords);

            Random randomGenerator = new Random();
            randomInt = randomGenerator.nextInt(allWords - 1);

            cursor.moveToPosition(randomInt);
            someString = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("definition")));
            someStringSk = String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("word")));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_dictionary);

            cleanStr(someString);

            remoteViews.setTextViewText(R.id.tvSk, someString);
            remoteViews.setTextViewText(R.id.tvE, someStringSk);

            Intent intent = new Intent(context, WidgetDictionaryProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.Main_Layout, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private String cleanStr(String someString2) {
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
        someString = someString.replaceAll("\\s/", "");

        return someString;
    }

}
