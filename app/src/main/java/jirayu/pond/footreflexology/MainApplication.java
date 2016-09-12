package jirayu.pond.footreflexology;

import android.app.Application;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by lp700 on 12/9/2559.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize thing(s) here
        Contextor.getInstance().init(getApplicationContext()); // การเอา Application Context มาฝากที่ Contextor
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
