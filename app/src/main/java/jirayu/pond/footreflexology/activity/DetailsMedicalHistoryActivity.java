package jirayu.pond.footreflexology.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemDao;
import jirayu.pond.footreflexology.fragment.DetailsMedicalHistoryFragment;
import jirayu.pond.footreflexology.manager.DataMemberManager;

public class DetailsMedicalHistoryActivity extends AppCompatActivity {

    /************
     * Variables
     ************/

    Toolbar toolbar;
    String diseaseName;

    /************
     * Functions
     ************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_medical_history);

        Intent intent = getIntent(); // เปิดซองจดหมาย (Intent)
        diseaseName = intent.getStringExtra("diseaseName"); // หยิบของออกมา (result)

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
        getSupportActionBar().setTitle("รายละเอียดประวัติการรักษา");
        getSupportActionBar().setSubtitle(
                "ของ" +
                DataMemberManager.getInstance().getMemberItemDao().getFirstName() + " "
                + DataMemberManager.getInstance().getMemberItemDao().getLastName()
        );
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initFragments(Bundle savedInstanceState) {
        // Place Fragment here
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, DetailsMedicalHistoryFragment.newInstance(diseaseName))
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

    // Handle Click Option Menu and Home Button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // Handle Up Button
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
