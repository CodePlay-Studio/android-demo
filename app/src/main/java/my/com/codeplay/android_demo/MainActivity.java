package my.com.codeplay.android_demo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import my.com.codeplay.android_demo.animations.AnimationsFragment;
import my.com.codeplay.android_demo.notifications.NotificationsFragment;
import my.com.codeplay.android_demo.sensor.SensorDemoFragment;
import my.com.codeplay.android_demo.viewgroups.ViewGroupsActivity;
import my.com.codeplay.android_demo.views.ViewsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentEventListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    // keep track of the current shown fragment
    private int curNav;

    private boolean isDrawerItemChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set the below window flag to request shared element transition between activities.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.none);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (curNav==0 || curNav==R.id.none || !isDrawerItemChanged)
                    return;

                isDrawerItemChanged = false;

                Fragment fragment = null;
                switch (curNav) {
                    case R.id.views:
                        fragment = ViewsFragment.newInstance();
                        break;
                    case R.id.viewgroups:
                        fragment = DemoListFragment.newInstance(DemoListFragment.TYPE_VIEWGROUPS);
                        break;
                    case R.id.notifications:
                        fragment = NotificationsFragment.newInstance();
                        break;
                    case R.id.animation:
                        fragment = AnimationsFragment.newInstance();
                        break;
                    case R.id.components:
                        fragment = DemoListFragment.newInstance(DemoListFragment.TYPE_COMPONENTS);
                        break;
                    case R.id.multimedia:
                        fragment = DemoListFragment.newInstance(DemoListFragment.TYPE_MULTIMEDIA);
                        break;
                    case R.id.network:
                        fragment = DemoListFragment.newInstance(DemoListFragment.TYPE_NETWORK);
                        break;
                    case R.id.storages:
                        fragment = DemoListFragment.newInstance(DemoListFragment.TYPE_STORAGES);
                        break;
                    case R.id.sensor:
                        fragment = SensorDemoFragment.newInstance(true);
                        break;
                }

                if (fragment!=null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    // clear fragment history
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    // and start a new fragment
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            .replace(R.id.master_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.master_container, TermsOfUseFragment.newInstance())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==curNav) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

        curNav = itemId;
        isDrawerItemChanged = true;

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        // true to display the item as the selected item
        return true;
    }

    @Override
    public void onFragmentButtonClick(View v) {
        switch (v.getId()) {
            case R.id.button_start_bird_anim:
                break;
        }
    }

    @Override
    public void onFragmentListItemClick(Class targetComponent, @LayoutRes int resId) {
        if (targetComponent!=null) {
            Intent intent = new Intent(this, targetComponent);
            if (targetComponent.equals(ViewGroupsActivity.class))
                intent.putExtra(ViewGroupsActivity.EXTRA_LAYOUT_ID, resId);
            startActivity(intent);
        }
    }

    public void clearNavSelection() {
        curNav = 0;
        navigationView.setCheckedItem(R.id.none);
    }
}
