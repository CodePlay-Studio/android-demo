package my.com.codeplay.android_demo;

import android.content.Intent;
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

import my.com.codeplay.android_demo.notifications.NotificationsFragment;
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
                        fragment = ViewGroupsListFragment.newInstance();
                        break;
                    case R.id.notifications:
                        fragment = NotificationsFragment.newInstance();
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
                .add(R.id.master_container, TermsOfUseFragment.newInstance())
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
