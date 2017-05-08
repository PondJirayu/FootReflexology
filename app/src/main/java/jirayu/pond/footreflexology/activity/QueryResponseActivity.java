package jirayu.pond.footreflexology.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.fragment.QueryResponseFragment;

public class QueryResponseActivity extends AppCompatActivity {

    /************
     * Variables
     ************/

    Toolbar toolbar;
    String query;

    /************
     * Functions
     ************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // เปิดซองจดหมาย (intent)
        Intent intent = getIntent();
        query = intent.getStringExtra("query"); // เอาชื่อโรคที่ค้นหาเก็บในตัวแปร query

        setContentView(R.layout.activity_query_response); // inflate

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

        // Set HomeButton
        getSupportActionBar().setTitle("ข้อมูลโรค" + query);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initFragments(Bundle savedInstanceState) {
        // Place Fragment here
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, QueryResponseFragment.newInstance(query))
                    .commit();
        }
    }

    /****************
     * Listener Zone
     ****************/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle click Option Menu
        switch (item.getItemId()) {
            case android.R.id.home: // Handle on BackPress
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***************
     * Inner Class
     **************/
}
