package jirayu.pond.footreflexology.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.fragment.ShowDetailsFragment;

public class ShowDetailsActivity extends AppCompatActivity {

    /************
     * Variables
     ************/

    Toolbar toolbar;
    String result;  // ตัวแปร Result เก็บชื่ออวัยวะ

    /************
     * Functions
     ************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        Intent intent = getIntent(); // เปิดซองจดหมาย (Intent)
        result = intent.getStringExtra("result");  // หยิบของออกมา (result)

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
        getSupportActionBar().setTitle(result);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initFragments(Bundle savedInstanceState) {
        // Place Fragment here
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, ShowDetailsFragment.newInstance(result))
                    .commit();
        }
    }

    // Inflate Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_details, menu);
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle Click Options Menu
        switch (item.getItemId()) {
            case android.R.id.home:
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

