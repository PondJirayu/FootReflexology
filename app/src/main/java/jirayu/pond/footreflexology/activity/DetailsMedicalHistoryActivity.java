package jirayu.pond.footreflexology.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    String diseaseName, firstName, lastName;
    int medicalHistoryId;
    SharedPreferences sharedPreferences;

    /************
     * Functions
     ************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_medical_history);

        Intent intent = getIntent(); // เปิดซองจดหมาย (Intent)
        diseaseName = intent.getStringExtra("diseaseName"); // หยิบของออกมา (result)
        medicalHistoryId = intent.getIntExtra("medicalHistoryId", -1);

        initToolbar();
        initSharedPreferences();
        initInstances();
        initFragments(savedInstanceState);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initSharedPreferences() {
        sharedPreferences = DetailsMedicalHistoryActivity.this.getSharedPreferences("loginMember",
                Context.MODE_PRIVATE);
        firstName = sharedPreferences.getString("firstname", null);
        lastName = sharedPreferences.getString("lastname", null);
    }

    private void initInstances() {
        // findViewById here

        // Set Home Button
        getSupportActionBar().setTitle("รายละเอียดประวัติการรักษา");
        getSupportActionBar().setSubtitle("ของ"
                + firstName
                + " "
                + lastName);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initFragments(Bundle savedInstanceState) {
        // Place Fragment here
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, DetailsMedicalHistoryFragment.newInstance(diseaseName, medicalHistoryId))
                    .commit();
        }
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
