package samples.dictionary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kiwi.employeedirectory.R;

public class STTDialog extends Dialog {

    private static final String TAG = "RecognitionListener";
    public Activity c;
    public Dialog d;
    public ImageView mic_amp;
    public ImageButton micBtn;
    public TextView resultTv, dbTV;
    private String local = null;
    private SpeechRecognizer speech = null;

    public STTDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public STTDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.speech_dialiog_kids);

        micBtn = findViewById(R.id.mic_icon);
        mic_amp = findViewById(R.id.speech_amplitude);
        resultTv = findViewById(R.id.txt_dia);
        dbTV = findViewById(R.id.dbTv);

        speech = SpeechRecognizer.createSpeechRecognizer(this.c);
        speech.setRecognitionListener(new listener());

        if (!Dictionary.tb.isChecked()) {
            local = "sk-SK";
            dbTV.setText(R.string.speech_slovak);
        }
        if (Dictionary.tb.isChecked()) {
            local = "el-GR";
            dbTV.setText(R.string.speech_greek);

        }

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, local);
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, local);
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, local);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 15000);
        speech.startListening(intent);

        micBtn.setOnClickListener(new View.OnClickListener() {
            private Context c;

            @Override
            public void onClick(View v) {

                if(speech == null) {
                    speech = SpeechRecognizer.createSpeechRecognizer(this.c);
                    speech.setRecognitionListener(new listener());
                }
                Intent intent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, local);
                //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
                intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, local);
                intent2.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, local);
                intent2.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 15000);

                speech.startListening(intent2);
                resultTv.setText(R.string.speech_prompt);
            }
        });

    }

    public void dismiss(){
        speech.stopListening();
        try{
            speech.destroy();
        }
        catch (Exception e)
        {
            Log.e(TAG,"Exception:" + e.toString());
        }
        super.dismiss();
    }

    /**
     * listener inner class
     */

    public class listener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle arg0) {
            // TODO Auto-generated method stub
            Log.i(TAG, "onReadyForSpeech");
            //dbTV.setText("Ready");
        }

        @Override
        public void onBeginningOfSpeech() {
            // TODO Auto-generated method stub
            Log.i(TAG, "onBeginningOfSpeech");
            micBtn.setBackgroundResource(R.drawable.mba);
        }

        @Override
        public void onRmsChanged(float rms) {
            // TODO Auto-generated method stub

			if (rms >= -2.12f && rms < 0.0f) 	    mic_amp.setBackgroundResource(R.drawable.amplitude1);	// 0
        	if (rms >= 0.0f && rms < 2.0f) 	        mic_amp.setBackgroundResource(R.drawable.amplitude2);
        	if (rms >= 2.0f && rms < 5.0f) 		    mic_amp.setBackgroundResource(R.drawable.amplitude3);
        	if (rms >= 5.0f && rms < 7.0f) 		    mic_amp.setBackgroundResource(R.drawable.amplitude4);
        	if (rms >= 10.0f && rms < 12.0f) 		mic_amp.setBackgroundResource(R.drawable.amplitude5);
        	if (rms >= 12.0f && rms < 14.0f) 		mic_amp.setBackgroundResource(R.drawable.amplitude6);
        	if (rms >= 14.0f && rms < 16.0f) 		mic_amp.setBackgroundResource(R.drawable.amplitude7);
        	if (rms >= 7.0f && rms < 8.0f) 		    mic_amp.setBackgroundResource(R.drawable.amplitude8);
        	if (rms >= 16.0f && rms < 18.0f) 		mic_amp.setBackgroundResource(R.drawable.amplitude9);
        	if  (rms >= 18.0f && rms < 20.0f) 	    mic_amp.setBackgroundResource(R.drawable.amplitude10);
        	//dbTV.setText("" + rms);*/

        }

        @Override
        public void onBufferReceived(byte[] arg0) {
            // TODO Auto-generated method stub
            //mic_amp.setBackgroundResource(R.drawable.amplitude5);
        }

        @Override
        public void onEndOfSpeech() {
            // TODO Auto-generated method stub
            micBtn.setBackgroundResource(R.drawable.mbb);
            speech.stopListening();
        }

        @Override
        public void onResults(Bundle bundle) {
            // TODO Auto-generated method stub
            ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            //displaying the first match
            if (matches != null)
                resultTv.setText(matches.get(0));
            Dictionary.searchText.setText(matches.get(0));
            Log.i(TAG, "Success: " + matches);

            speech.stopListening();
            try{
                speech.destroy();
            }
            catch (Exception e)
            {
                Log.e(TAG,"Exception:"+e.toString());
            }

            STTDialog.this.dismiss();
        }

        @Override
        public void onPartialResults(Bundle arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onEvent(int arg0, Bundle arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onError(int errorCode) {
            // TODO Auto-generated method stub
            Log.i(TAG, "Error: " + errorCode);
            //Toast.makeText(c,"" + errorCode,Toast.LENGTH_SHORT).show();
            //
            String message = "nothing";
            if (errorCode == SpeechRecognizer.ERROR_AUDIO) message = "audio";
            else if (errorCode == SpeechRecognizer.ERROR_CLIENT)
                resultTv.setText(R.string.error_client);
            else if (errorCode == SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS)
                message = "insufficient permissions";
            else if (errorCode == SpeechRecognizer.ERROR_NETWORK) message = "network";
            else if (errorCode == SpeechRecognizer.ERROR_NETWORK_TIMEOUT)
                resultTv.setText(R.string.network_timeout);
            else if (errorCode == SpeechRecognizer.ERROR_NO_MATCH)
                resultTv.setText(R.string.error_nomatch);
            else if (errorCode == SpeechRecognizer.ERROR_RECOGNIZER_BUSY)
                resultTv.setText(R.string.recognizer_busy);
            else if (errorCode == SpeechRecognizer.ERROR_SERVER)
                resultTv.setText(R.string.error_server);
            else if (errorCode == SpeechRecognizer.ERROR_SPEECH_TIMEOUT)
                resultTv.setText(R.string.speech_timeout);
            Log.i(TAG, "Error2: " + message);
            mic_amp.setBackgroundResource(R.drawable.amplitude1);

        }

    }

}
