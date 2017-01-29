package jirayu.pond.footreflexology.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.fragment.AddMedicalHistoryFragment;
import jirayu.pond.footreflexology.fragment.EditMedicalHistoryFragment;
import jirayu.pond.footreflexology.fragment.MedicalHistoryFragment;

public class MedicalHistoryActivity extends AppCompatActivity {

    /************
     * Variables
     ************/

    Toolbar toolbar;

    /************
     * Functions
     ************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history); // inflate

        initToolbar();
        initInstances();
        initFragments(savedInstanceState);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initInstances() {
        // findViewById here

        // Set Home Button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initFragments(Bundle savedInstanceState) {
        // Place Fragment here
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MedicalHistoryFragment.newInstance())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /****************
     * Listener Zone
     ****************/

    // Handle Click Options Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    R.anim.from_right, R.anim.to_left,
                                    R.anim.from_left, R.anim.to_right
                            )
                            .replace(R.id.contentContainer,
                                    AddMedicalHistoryFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                return true;
            case R.id.action_edit_medical:
                  getSupportFragmentManager().beginTransaction()
                          .setCustomAnimations(
                                  R.anim.from_right, R.anim.to_left,
                                  R.anim.from_left, R.anim.to_right
                          )
                          .replace(R.id.contentContainer,
                                  EditMedicalHistoryFragment.newInstance())
                          .addToBackStack(null)
                          .commit();
                return true;
            case android.R.id.home: // Handle on BackPress
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**************
     * Inner Class
     **************/
}
