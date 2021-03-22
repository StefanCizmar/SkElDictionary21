package samples.dictionary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import kiwi.employeedirectory.R;

public class DictionaryAdapter   extends BaseAdapter  {

	//TextToSpeech mTts;
	Locale loc;

	public static final String GREEK = "(α|β|γ|δ|ε|ζ|ε|θ|ι|κ|" +
    		"λ|μ|ν|χ|ο|π|ρ|σ|τ|υ|φ|χ|ψ|ω)";
    
	private Context mContext;
	private ArrayList<String> fid;		// id
	private ArrayList<String> fword; 	// word
	private ArrayList<String> farth;	// arth
	private ArrayList<String> fdef;		// definition
	private ArrayList<String> farth2;	// arth2
	private ArrayList<String> ftrsp;	// trsp
	private ArrayList<String> fdef2;	// definition 2
	private ArrayList<String> frem;		// remark
	private ArrayList<String> ffav;		// favorite
	private ArrayList<String> fdec;		// declesion
	
	public  DictionaryAdapter(Context c, ArrayList<String> id,ArrayList<String> eword, ArrayList<String> earth, ArrayList<String> edef, 
			ArrayList<String> earth2, ArrayList<String> etrsp, ArrayList<String> edef2, ArrayList<String> erem, ArrayList<String> efav,
			ArrayList<String> edec ) {
		this.mContext = c;

		this.fid = id;
		this.fword = eword;
		this.farth = earth;
		this.fdef = edef;
		this.farth2 = earth2;
		this.ftrsp = etrsp;
		this.fdef2 = edef2;
		this.frem = erem;
		this.ffav = efav;
		this.fdec = edec;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fid.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int pos, View child, ViewGroup parent) {
		// TODO Auto-generated method stub

		//if(mTts == null) mTts = new TextToSpeech(mContext, this);

		Holder mHolder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.dictionary_list_item, parent, false);
			mHolder = new Holder();
			
			mHolder.txt_fword = (TextView) child.findViewById(R.id.wordL);
			mHolder.txt_farth = (TextView) child.findViewById(R.id.arth);				
			mHolder.txt_fdef = (TextView) child.findViewById(R.id.definition);		
			mHolder.txt_fdef2 = (TextView) child.findViewById(R.id.def3);
			mHolder.txt_frem = (TextView) child.findViewById(R.id.remark);
			//mHolder.btnDel = (TextView) child.findViewById(R.id.tvFavorites);
			mHolder.btnSpeech = (ImageView) child.findViewById(R.id.btSpeech);	 // ImageView is O.K.

			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		
		/** implementation 	***********************************************************************************************/
		/** word		*****************************************************************/
    	
        if (Dictionary.tb.isChecked()){
        	//greek

        	// if article and show_art is none 
        	if (farth.get(pos).equalsIgnoreCase("") || (Dictionary.show_art == false) || (Dictionary.favorites == true))
        		mHolder.txt_fword.setText(fword.get(pos));
        	else
        		mHolder.txt_fword.setText(farth.get(pos) + " " + fword.get(pos));
        }
        else
        	//slovak
        	mHolder.txt_fword.setText(fword.get(pos));
        	        
        /** arth		*****************************************************************/
        	// greek
        if (Dictionary.tb.isChecked() || (Dictionary.favorites == true))
        {
        	mHolder.txt_farth.setText("");
        }
        else
        	// slovak
        {
        	if(Dictionary.show_art == false)
        	
        		mHolder.txt_farth.setText("");
        	else {  
        		if(farth.get(pos).equalsIgnoreCase("")) 
        			mHolder.txt_farth.setText("");
        		else 
        			mHolder.txt_farth.setText(farth.get(pos) + " ");
        		}
        }
 

        /** definition	*****************************************************************/

		mHolder.txt_fdef.setText(fdef.get(pos));


		/** definition 2	*****************************************************************/

		if (Dictionary.show_type == true) {

			if (fdef2.get(pos).equals("")) {

				mHolder.txt_fdef2.setText(fdef2.get(pos) + "");
			}
			else {
				mHolder.txt_fdef2.setText(" - " + fdef2.get(pos));
			}

        }
        else
        {
            // no show definition 2
			mHolder.txt_fdef2.setText("");

		}

		/** remark		*****************************************************************/

		if (frem.get(pos).equals("")) {

			mHolder.txt_frem.setText(frem.get(pos) + "");
		}
		else {
			mHolder.txt_frem.setText(" - " + frem.get(pos));
		}

		//mHolder.btnDel.setText(ffav.get(pos));
		
		/** SPEECH TO TEXT ***********************************************************************************************/
		mHolder.btnSpeech.setOnClickListener(new View.OnClickListener() {
			   @SuppressLint("NewApi")
			@Override
			   public void onClick(View v) {
			      // TODO Auto-generated method stub
				   //Log.i("CLICK", "" + fdef.get(pos));
			
				   if(Dictionary.speech) {
					   
					   if (!Dictionary.tb.isChecked()) {
						   loc = new Locale("el");
						   Dictionary.mTts.setLanguage(loc);
					   }
					   if (Dictionary.tb.isChecked()) {
						   loc = new Locale("sk");
						   Dictionary.mTts.setLanguage(loc);
   		            	}
					   
					   String  text = null;
					   //Log.e("word", fdef.get(pos));
   		        
					   // test if definition letters is greek

					   // new version ******************************************************************************************************
					   
					   if(!Dictionary.type_speech){
						   loc = new Locale("sk");
						   Dictionary.mTts.setLanguage(loc);
						   if (!Dictionary.tb.isChecked()) {
							   text = fword.get(pos);
						   }
						   else text = fdef.get(pos);
						   Log.e("matches", "sk");
					   }
					   else {
						   loc = new Locale("el");
						   Dictionary.mTts.setLanguage(loc);
						   if (!Dictionary.tb.isChecked()) {
							   text = fdef.get(pos);
						   }
						   else text = fword.get(pos);
						   Log.e("matches", "el");
					   }
					   
					   //split text
					   String[] parts = text.split("-");
					   String text2 = parts[0]; //
					   if(text2 == null) text2 = text;
					   //else text2 = text;
   		        
					   if(text2 == null || "".equals(text2))
					   {
						   text2 = "Content not available";
   		            
						   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							   Dictionary.mTts.speak(text2,TextToSpeech.QUEUE_FLUSH,null,null);
						   } else {
							   Dictionary.mTts.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
						   }
					   }else
   		        	
						   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							   Dictionary.mTts.speak(text2,TextToSpeech.QUEUE_FLUSH,null,null);
						   } else {
							   Dictionary.mTts.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
						   }			   			   
				   }
			   }
			});
			
		/** UI SETTINGS THEMES, FONT SIZES	***************************************************************************************/
		  	if (Dictionary.fontSize1)
	        {
		  		mHolder.txt_fword.setTextSize(14);
		  		mHolder.txt_farth.setTextSize(14);
		  		mHolder.txt_fdef.setTextSize(14);
		  		mHolder.txt_fdef2.setTextSize(12);
		  		mHolder.txt_frem.setTextSize(12);

	        }
	        if (Dictionary.fontSize2)
	        {
	        	mHolder.txt_fword.setTextSize(16);
	        	mHolder.txt_farth.setTextSize(16);
	        	mHolder.txt_fdef.setTextSize(16);
	        	mHolder.txt_fdef2.setTextSize(14);
	        	mHolder.txt_frem.setTextSize(14);

	        }
	        if (Dictionary.fontSize3)
	        {
	        	mHolder.txt_fword.setTextSize(18);
	        	mHolder.txt_farth.setTextSize(18);
	        	mHolder.txt_fdef.setTextSize(18);
	        	mHolder.txt_fdef2.setTextSize(16);
	        	mHolder.txt_frem.setTextSize(16);

	        }
	        if (Dictionary.fontSize4)
	        {
	        	mHolder.txt_fword.setTextSize(20);
	        	mHolder.txt_farth.setTextSize(20);
	        	mHolder.txt_fdef.setTextSize(20);
	        	mHolder.txt_fdef2.setTextSize(18);
	        	mHolder.txt_frem.setTextSize(18);

	        }
	        if (Dictionary.fontSize5)
	        {
	        	mHolder.txt_fword.setTextSize(22);
	        	mHolder.txt_farth.setTextSize(22);
	        	mHolder.txt_fdef.setTextSize(22);
	        	mHolder.txt_fdef2.setTextSize(20);
	        	mHolder.txt_frem.setTextSize(20);

	        }

	        if (Dictionary.Theme)
            {
	        	mHolder.txt_fword.setTextColor(Color.parseColor("#2196f3"));
	        	mHolder.txt_farth.setTextColor(Color.parseColor("#FF000000"));
	        	mHolder.txt_fdef.setTextColor(Color.parseColor("#FF000000"));
	        	mHolder.txt_fdef2.setTextColor(Color.parseColor("#FF00AA00"));
	        	mHolder.txt_frem.setTextColor(Color.parseColor("#FF00AA00"));
            	}
            if (Dictionary.Theme2)             	
            {                       
            	mHolder.txt_fword.setTextColor(Color.parseColor("#2196f3"));
            	mHolder.txt_farth.setTextColor(Color.parseColor("#FFFFFFFF"));
	        	mHolder.txt_fdef.setTextColor(Color.parseColor("#FFFFFFFF"));
	        	mHolder.txt_fdef2.setTextColor(Color.parseColor("#FF00FF00"));
	        	mHolder.txt_frem.setTextColor(Color.parseColor("#FF00FF00"));
            	}


		if(Build.VERSION.SDK_INT >= 29) {
			if (Dictionary.darkMode) {
				Log.i("MODE4", " DARK");

				mHolder.txt_fword.setTextColor(Color.parseColor("#2196f3"));
				mHolder.txt_farth.setTextColor(Color.parseColor("#FFFFFFFF"));
				mHolder.txt_fdef.setTextColor(Color.parseColor("#FFFFFFFF"));
				mHolder.txt_fdef2.setTextColor(Color.parseColor("#FF00FF00"));
				mHolder.txt_frem.setTextColor(Color.parseColor("#FF00FF00"));

			} else {
				mHolder.txt_fword.setTextColor(Color.parseColor("#2196f3"));
				mHolder.txt_farth.setTextColor(Color.parseColor("#FF000000"));
				mHolder.txt_fdef.setTextColor(Color.parseColor("#FF000000"));
				mHolder.txt_fdef2.setTextColor(Color.parseColor("#FF00AA00"));
				mHolder.txt_frem.setTextColor(Color.parseColor("#FF00AA00"));
			}
		}

		if(!fdec.get(pos).equalsIgnoreCase("")) mHolder.txt_fword.setTextColor(Color.parseColor("#ffaa00ff"));
		if(!Dictionary.speech) mHolder.btnSpeech.setVisibility(View.GONE);


		return child;
	}
	
	public class Holder {
		
		TextView txt_fword;
		TextView txt_farth;
		TextView txt_fdef;
		TextView txt_fdef2;
		TextView txt_frem;	
		//TextView btnDel;
		ImageView btnSpeech;

	}
	
	
	
}
