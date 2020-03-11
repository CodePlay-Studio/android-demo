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

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import my.com.codeplay.android_demo.R;

public class GridViewActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {
    private List<ResolveInfo> appsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        loadApps();

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(new AppsAdapter());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ResolveInfo appInfo = appsList.get(position);
        ActivityInfo activityInfo = appInfo.activityInfo;

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.setComponent(new ComponentName(
                activityInfo.applicationInfo.packageName, activityInfo.name));
        startActivity(intent);
    }

    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        appsList = getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    private class AppsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (appsList==null || appsList.isEmpty())
                return 0;
            return appsList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView==null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_gridview, parent, false);

                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.icon);
                holder.text  = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ResolveInfo appInfo = appsList.get(position);
            // create and decoding a bitmap is heavy, the more efficient way is to load and cache each
            // app's icon on a worker thread.
            holder.image.setImageDrawable(appInfo.activityInfo.loadIcon(getPackageManager()));
            holder.text.setText(appInfo.activityInfo.loadLabel(getPackageManager()));

            return convertView;
        }
    }


}
