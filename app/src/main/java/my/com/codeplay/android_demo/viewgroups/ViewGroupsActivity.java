/*
 * Copyright 2017 (C) CodePlay Studio. All rights reserved.
 *
 * All source code within this app is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package my.com.codeplay.android_demo.viewgroups;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.widget.ImageView;
import android.widget.SlidingDrawer;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import my.com.codeplay.android_demo.R;

public class ViewGroupsActivity extends AppCompatActivity {
    public static final String EXTRA_LAYOUT_ID = "viewgroupsactivity.extra.LAYOUT_ID";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int layoutId = getIntent().getIntExtra(EXTRA_LAYOUT_ID, 0);
        int labelId = 0;
        switch (layoutId) {
            case R.layout.activity_framelayout:
                labelId = R.string.framelayout;
                break;
            case R.layout.activity_relativelayout:
                labelId = R.string.relativelayout;
                break;
            case R.layout.activity_linearlayout:
                labelId = R.string.linearlayout;
                break;
            case R.layout.activity_scrollview:
                labelId = R.string.scrollview;
                break;
            case R.layout.activity_tablelayout:
                labelId = R.string.tablelayout;
                break;
            case R.layout.activity_gridlayout:
                labelId = R.string.gridlayout;
                break;
            case R.layout.activity_coordinatorlayout:
                setTheme(R.style.AppTheme_NoActionBar);
                labelId = R.string.coordinatorlayout;
                break;
            case R.layout.activity_sliding_drawer:
                labelId = R.string.slidingdrawer;
                break;
        }

        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        if (getSupportActionBar()!=null && labelId!=0)
            getSupportActionBar().setTitle(labelId);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        if (appBarLayout!=null) {
            final CollapsingToolbarLayout collapsingToolbarLayout
                    = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                        collapsingToolbarLayout.setTitle(getString(R.string.coordinatorlayout));
                    } else {
                        collapsingToolbarLayout.setTitle("");
                    }
                }
            });
        }

        SlidingDrawer slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
        if (slidingDrawer!=null) {
            final ImageView ivHandler = (ImageView) findViewById(R.id.handle);
            slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
                @Override
                public void onDrawerOpened() {
                    ivHandler.setImageResource(R.drawable.ic_arrow_down_black_48dp);
                }
            });
            slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
                @Override
                public void onDrawerClosed() {
                    ivHandler.setImageResource(R.drawable.ic_arrow_up_black_48dp);
                }
            });
        }
    }
}
