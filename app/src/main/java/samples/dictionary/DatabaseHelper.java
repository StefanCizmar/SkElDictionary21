package samples.dictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kiwi.employeedirectory.R;


public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "employee";
	public static final String TABLE_NAME="employee";
	
    public static final String ID = "_id";
    public static final String WORD = "word";
    public static final String ARTH = "arth";
    public static final String DEFINITION = "definition";
    public static final String ARTH2 = "arth2";
    public static final String TRSP = "trsp";

    public static final String REMARK = "remark";

	public static final String REMARKSK = "remarkSk";
	public static final String REMARKEL = "remarkEl";

	//public static final String DEFINITION2 = "definition2";

	public static final String DEFINITIONSK = "definitionSk";
	public static final String DEFINITIONEL = "definitionEl";

	public static final String FAVORITES = "favorites";
    //public static final String NOFRASES = "nofrases";
    public static final String DECLENSION = "declension";


	protected Context context;

	public DatabaseHelper(Context context) {
		
		super(context, DATABASE_NAME, null, 16); // number  is version db
		this.context = context;
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
	String s;
		
		//Toast.makeText(context, R.string.start_create_database, Toast.LENGTH_LONG).show();
		
		try {
	        
	        Log.d("Helper", "Create database.");
			InputStream in = context.getResources().openRawResource(R.raw.sql07122020);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(in, null);
			NodeList statements = doc.getElementsByTagName("statement");
			for (int i=0; i<statements.getLength(); i++) {
				s = statements.item(i).getChildNodes().item(0).getNodeValue();
				db.execSQL(s);
				
			}
		} catch (Throwable t) {

		        //Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
		        Log.d("Helper", t.toString());
		        
			}
		
		
		/***********************************************************************/
		Log.d("Helper", "Database done.");
		Toast.makeText(context, R.string.create_database_done, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS employee");
		onCreate(db);
	}
	
	
	Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    /****************************************************************************************************/
    
    
}
