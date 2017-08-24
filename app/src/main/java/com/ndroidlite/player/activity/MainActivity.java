package com.ndroidlite.player.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.gun0912.tedpermission.PermissionListener;
import com.ndroidlite.player.R;
import com.ndroidlite.player.fragments.FavouriteFragment;
import com.ndroidlite.player.fragments.FilesFragment;
import com.ndroidlite.player.fragments.LibraryFragment;
import com.ndroidlite.player.helper.Functions;
import com.ndroidlite.player.intro.AppIntroActivity;
import com.ndroidlite.player.utils.PreferenceUtil;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final int APP_INTRO_REQUEST = 100;
    private static final int LIBRARY = 0;
    private static final int FILES = 1;
    private static final int FAV = 2;
    private MainActivity.MainActivityFragmentCallbacks currentFragment;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        clickListener();

        if (savedInstanceState == null) {
            setMusicChooser(PreferenceUtil.getInstance(this).getLastMusicChooser());
        } else {
            restoreCurrentFragment();
        }

        if (!checkShowIntro()) {
            checkShowChangelog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_INTRO_REQUEST) {
            //Set permission
            askPermission();
        }
    }

    private void askPermission() {
        Functions.setPermission(this, new String[]{

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                }
                , new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        finish();
                    }
                });
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_equalizer:
                //  NavigationUtil.openEqualizer(getActivity());
                return true;
            case R.id.action_shuffle_all:
                // MusicPlayerRemote.openAndShuffleQueue(SongLoader.getAllSongs(getActivity()), true);
                return true;
            case R.id.group_grid_size:

                return true;
            case R.id.action_search:
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clickListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_library:
                        setMusicChooser(LIBRARY);
                        break;

                    case R.id.nav_file:
                        setMusicChooser(FILES);
                        break;

                    case R.id.nav_my_favorite:
                        setMusicChooser(FAV);
                        break;

                    case R.id.nav_settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;

                    case R.id.nav_about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                }
                return true;
            }
        });

    }

    private void setMusicChooser(int key) {
        PreferenceUtil.getInstance(this).setLastMusicChooser(key);
        switch (key) {
            case LIBRARY:
                navigationView.setCheckedItem(R.id.nav_library);
                setFragment(LibraryFragment.newInstance());
                break;
            case FILES:
                navigationView.setCheckedItem(R.id.nav_file);
                setFragment(FilesFragment.newInstance());
                break;
            case FAV:
                navigationView.setCheckedItem(R.id.nav_my_favorite);
                setFragment(FavouriteFragment.newInstance());
                break;
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

        // Set action bar title
        setTitle(getTitle());
        // Close the navigation drawer
         mDrawerLayout.closeDrawers();
    }

    private void restoreCurrentFragment() {
        currentFragment = (MainActivity.MainActivityFragmentCallbacks) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    private boolean checkShowIntro() {
        if (!PreferenceUtil.getInstance(this).introShown()) {
            PreferenceUtil.getInstance(this).setIntroShown();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivityForResult(new Intent(MainActivity.this, AppIntroActivity.class), APP_INTRO_REQUEST);
                }
            }, 50);
            return true;
        }
        return false;
    }

    private boolean checkShowChangelog() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int currentVersion = pInfo.versionCode;
            if (currentVersion != PreferenceUtil.getInstance(this).getLastChangelogVersion()) {
                //ChangelogDialog.create().show(getSupportFragmentManager(), "CHANGE_LOG_DIALOG");
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    private interface MainActivityFragmentCallbacks {
        boolean handleBackPress();
    }
}
