package jirayu.pond.footreflexology.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    /************
     * Variables
     ************/

    /************
     * Functions
     ************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_login);

        initFragments(savedInstanceState);
    }

    private void initFragments(Bundle savedInstanceState) {
        // Place Fragment here
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, LoginFragment.newInstance())
                    .commit();
        }
    }

    /****************
     * Listener Zone
     ****************/

    /**************
     * Inner Class
     **************/
}
