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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import my.com.codeplay.android_demo.R;

public class TabLayoutActivity extends AppCompatActivity {
    private static final int TAB_SIZE = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        PagerContentAdapter adapter = new PagerContentAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

    }

    /**
     * The {@link androidx.viewpager.widget.PagerAdapter} provides fragments representing each object
     * in a collection. We use a {@link androidx.fragment.app.FragmentPagerAdapter} derivative,
     * which is best navigating between sibling screens representing a fixed, small number of pages.
     *
     * Use a {@link androidx.fragment.app.FragmentStatePagerAdapter} if paging across a collection
     * of objects for which the number of pages is undetermined. It destroys and re-create fragments
     * as needed, saving and restoring their state in the process as user navigates to other pages,
     * minimizing memory usage to conserve memory and is a best practice when allowing navigation
     * between objects in a potentially large collection.
     */
    private class PagerContentAdapter extends FragmentPagerAdapter {
        public PagerContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return TAB_SIZE;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return TabContentFragment.newInstance();
                case 1: return TabContentFragment.newInstance();
                case 2: return TabContentFragment.newInstance();
                default: return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return super.getPageTitle(position);
            return "TAB " + (position + 1);
        }
    }
}
