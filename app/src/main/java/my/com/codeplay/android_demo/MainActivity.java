package my.com.codeplay.android_demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    /*private static final int[] ICONS  = {
            R.drawable.ic_widgets_black_24dp
    };
    private static final int[] LABELS = {
            R.string.widgets,
            R.string.layouts
    };
    private static final int COLUMNS = 2;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.master_container, TermsOfUseFragment.newInstance())
                .commit();

        /*
            GridLayoutManager
                A RecyclerView.LayoutManager implementations that lays out items in a grid.
                By default, each item occupies 1 span. You can change it by providing a custom
                GridLayoutManager.SpanSizeLookup instance via setSpanSizeLookup(SpanSizeLookup).
        */
        /*
            public GridLayoutManager (Context context, int spanCount)
                Creates a vertical GridLayoutManager

            Parameters
                context : Current context, will be used to access resources.
                spanCount : The number of columns in the grid
        */
        // Define a layout for RecyclerView
        /*mLayoutManager = new GridLayoutManager(this, COLUMNS);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter = new DemosAdapter());*/
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
        drawerLayout.closeDrawer(GravityCompat.START);
        // true to display the item as the selected item
        return true;
    }

    /*private class DemosAdapter extends RecyclerView.Adapter<DemosAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_demo, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.ivIcon.setImageResource(ICONS[position]);
            holder.tvLabel.setText(LABELS[position]);
        }

        @Override
        public int getItemCount() {
            return LABELS.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivIcon;
            private TextView tvLabel;

            public ViewHolder(View itemView) {
                super(itemView);

                ivIcon = (ImageView) itemView.findViewById(R.id.icon);
                tvLabel = (TextView) itemView.findViewById(R.id.label);
            }
        }
    }*/
}
