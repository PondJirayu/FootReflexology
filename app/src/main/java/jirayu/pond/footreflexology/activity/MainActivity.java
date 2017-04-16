package jirayu.pond.footreflexology.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;

import jirayu.pond.footreflexology.R;
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
    Intent intent;
    MenuItem menuItem;
    SearchView searchView;

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
        getSupportActionBar().setTitle("หน้าแรก");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle ViewPager
        viewPager.setAdapter(fragmentStatePagerAdapter);
        // ViewPagerIndicator
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
        // Handle SearchView (First Step)
        menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(searchViewListener);
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
            case R.id.action_logout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Handle Drawer Menu
    NavigationView.OnNavigationItemSelectedListener navigationViewListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

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
                case R.id.action_about:
                    intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return false;
            }
        }
    };

    // Handle SearchView (Second Step)
    SearchView.OnQueryTextListener searchViewListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Intent intent = new Intent(MainActivity.this, QueryResponseActivity.class);
            intent.putExtra("query", query); // แนบชื่อโรคที่ค้นหาไปกับ intent
            startActivity(intent);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    // Handle ViewPager
    FragmentStatePagerAdapter fragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return RightFootFragment.newInstance();
                case 1:
                    return LeftFootFragment.newInstance();
                case 2:
                    return InTheFootFragment.newInstance();
                case 3:
                    return OutSideFootFragment.newInstance();
                case 4:
                    return OnTheBackFootFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "เท้าขวา";
                case 1:
                    return "เท้าซ้าย";
                case 2:
                    return "ในเท้า";
                case 3:
                    return "นอกเท้า";
                case 4:
                    return "หลังเท้า";
                default:
                    return null;
            }
        }
    };

    /***************
     * Inner Class
     **************/

}