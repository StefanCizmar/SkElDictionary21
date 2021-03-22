package samples.dictionary;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

import kiwi.employeedirectory.R;
import samples.dictionary.UI.ThemeGrid;


public class ThemeGridAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] gridViewString;
    private final int[] gridViewImageId;
    private SharedPreferences prefs;
    private boolean Theme;
    private boolean Theme2;

    public ThemeGridAdapter(Context context, String[] gridViewString, int[] gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
    }

    @Override
    public int getCount() {
        return gridViewString.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        loadUserSettings();


        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = new View(mContext);
            convertView = inflater.inflate(R.layout.theme_grid_view_items, null);
            TextView textViewAndroid = (TextView) convertView.findViewById(R.id.themeText);
            ImageView imageViewAndroid = (ImageView) convertView.findViewById(R.id.themeImage);
            textViewAndroid.setText(gridViewString[i]);
            imageViewAndroid.setImageResource(gridViewImageId[i]);



            /** set theme */
            if (ThemeGrid.isTheme) {
                textViewAndroid.setTextColor(Color.parseColor("#FFFFFFFF"));
            } else {
                textViewAndroid.setTextColor(Color.parseColor("#FF000000"));
            }

        } else {
            convertView = (View) convertView;
        }

        return convertView;
    }


    /**
     * load user setting
     */
    private void loadUserSettings() {
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
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

}
