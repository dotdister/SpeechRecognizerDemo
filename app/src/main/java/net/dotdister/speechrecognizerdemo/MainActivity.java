package net.dotdister.speechrecognizerdemo;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.telecom.RemoteConference;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button recognizeOnlineButton = (Button) this.findViewById(R.id.button_recognize_online);

        // fire on click "RECOGNIZE! (ONLINE)" button
        recognizeOnlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // prepare Speech Recognizer
                Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                        getApplicationContext().getPackageName());

                startActivityForResult(recognizerIntent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == REQUEST_CODE) && (resultCode == RESULT_OK)) {
            // retrieve recognized results (words)
            ArrayList<String> recognizedWords =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            // retrieve recognized results (confident score)
            float[] confidentScores =
                    data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

            // make result strings
            StringBuilder result = new StringBuilder();
            String recognizedWord = "Word: " + recognizedWords.get(0);
            String confidentScore = "Score: " + confidentScores[0];

            result.append(recognizedWord);
            result.append(" - ");
            result.append(confidentScore);
            Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
