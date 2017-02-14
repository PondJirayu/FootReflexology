package jirayu.pond.footreflexology.manager;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.Locale;

/**
 * Created by lp700 on 14/2/2560.
 */

public class TextToSpeechManager extends UtteranceProgressListener implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {

    public static TextToSpeechManager textToSpeechManager;

    public static TextToSpeechManager getInstance(Context context) {
        if (textToSpeechManager == null) {
            textToSpeechManager = new TextToSpeechManager(context);
        }
        return textToSpeechManager;
    }

    private Context context;
    private TextToSpeech textToSpeech;
    private Locale locale = Locale.getDefault();
    private String enginePackageName;
    private String message;
    private boolean isRunning;
    private int speakCount;

    public TextToSpeechManager(Context context) {
        this.context = context;
    }

    public void speak(String message) {
        this.message = message;

        if (textToSpeechManager == null || !isRunning) {
            speakCount = 0;

            if (enginePackageName != null && !enginePackageName.isEmpty()) {
                textToSpeech = new TextToSpeech(context, this, enginePackageName);
            } else {
                textToSpeech = new TextToSpeech(context, this);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                textToSpeech.setOnUtteranceProgressListener(this);
            } else {
                textToSpeech.setOnUtteranceCompletedListener(this);
            }

            isRunning = true;
        } else {
            startSpeak();
        }
    }

    public TextToSpeechManager setEngine(String packageName) {
        enginePackageName = packageName;
        return this;
    }

    public TextToSpeechManager setLocale(Locale local) {
        this.locale = local;
        return this;
    }

    private void startSpeak() {
        speakCount++;

        if (locale != null) {
            textToSpeech.setLanguage(locale);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, "");
        } else {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void clear() {
        speakCount--;

        if (speakCount == 0) {
            textToSpeech.shutdown();
            isRunning = false;
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            startSpeak();
        }
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {
        clear();
    }

    @Override
    public void onStart(String utteranceId) {

    }

    @Override
    public void onDone(String utteranceId) {
        clear();
    }

    @Override
    public void onError(String utteranceId) {
        clear();
    }
}
