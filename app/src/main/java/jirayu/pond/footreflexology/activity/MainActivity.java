package jirayu.pond.footreflexology.activity;

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
import android.widget.ImageView;

import java.util.Random;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.adapter.ViewPagerAdapter;
import jirayu.pond.footreflexology.fragment.LeftFootFragment;
import jirayu.pond.footreflexology.fragment.RightFootFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TabLayout tabLayout;
    NavigationView navigationView;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

//    ImageView imageView;
//    int[] image_list = new int[4];
//    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_main); // inflate

        initToolbar();
        initInstances();

        // Place Fragment here
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initInstances() {
        // findViewById here
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("เท้าขวา"));
        tabLayout.addTab(tabLayout.newTab().setText("เท้าซ้าย"));
        tabLayout.addTab(tabLayout.newTab().setText("ด้านอื่น"));
        tabLayout.addTab(tabLayout.newTab().setText("คำสำคัญ"));

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        imageView = (ImageView) findViewById(R.id.imageView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setTitle("หน้าหลัก");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        // Random Background DrawerMenu
//        image_list[0] = R.drawable.stock1;
//        image_list[1] = R.drawable.stock2;
//        image_list[2] = R.drawable.stock3;
//        image_list[3] = R.drawable.stock4;
//        Random random = new Random();
//        n = random.nextInt(4);
//        imageView.setImageResource(image_list[n]);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menu_main
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle click Hamburger Icon
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle click Options Menu
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_LogOut:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle Drawer Menu
        return false;
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

}