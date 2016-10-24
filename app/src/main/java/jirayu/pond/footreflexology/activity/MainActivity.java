package jirayu.pond.footreflexology.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.adapter.ViewPagerAdapter;
import jirayu.pond.footreflexology.fragment.LeftFootFragment;
import jirayu.pond.footreflexology.fragment.InTheFootFragment;
import jirayu.pond.footreflexology.fragment.OnTheBackFootFragment;
import jirayu.pond.footreflexology.fragment.OutSideFootFragment;
import jirayu.pond.footreflexology.fragment.RightFootFragment;

public class MainActivity extends AppCompatActivity {

    /************
     * Variables
     ************/

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TabLayout tabLayout;
    NavigationView navigationView;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    Intent intent;

    /************
     * Functions
     ************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setContentView(R.layout.activity_main); // inflate

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
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Drawer Menu
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(navigationViewListener);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Set Home Button
        getSupportActionBar().setTitle("หน้าหลัก");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // AddFragment into ViewPager
        viewPagerAdapter.addFragments(RightFootFragment.newInstance(), "เท้าขวา");
        viewPagerAdapter.addFragments(LeftFootFragment.newInstance(), "เท้าซ้าย");
        viewPagerAdapter.addFragments(InTheFootFragment.newInstance(), "ในเท้า");
        viewPagerAdapter.addFragments(OutSideFootFragment.newInstance(), "นอกเท้า");
        viewPagerAdapter.addFragments(OnTheBackFootFragment.newInstance(), "หลังเท้า");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initFragments(Bundle savedInstanceState) {
        // Place Fragment here
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    // Inflate Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        // Handle Click Hamburger Icon
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle Click Options Menu
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Handle Drawer Menu
    NavigationView.OnNavigationItemSelectedListener navigationViewListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // TODO: Handle Drawer Menu here

            // Checking if the item is in checked state or not, if not make it in checked state
            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
            }

            // Closing drawer menu on item click
            drawerLayout.closeDrawers();

            switch (item.getItemId()) {
                case R.id.action_profile:
                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.action_medical_history:
                    intent = new Intent(MainActivity.this, MedicalHistoryActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.action_major_term:
                    intent = new Intent(MainActivity.this, MajorTermActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.action_memo:
                    intent = new Intent(MainActivity.this, MemoActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.action_about:
                    intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return false;
            }
        }
    };

    /***************
     * Inner Class
     **************/

}