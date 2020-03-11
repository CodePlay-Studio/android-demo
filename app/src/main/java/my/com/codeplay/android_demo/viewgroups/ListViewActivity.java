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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import my.com.codeplay.android_demo.R;
import my.com.codeplay.android_demo.data.Dummy;

/*
 * A ListView requests views on demand from interface {@link android.widget.ListAdapter}. The classes
 * implemented this interface include: BaseAdapter, ArrayAdapter<T>, CursorAdapter, SimplerAdapter,
 * SimpleCursorAdapter, etc.
 *
 * Standard row layout resources from android.R to use in a ListView:
 * simple_list_item_1, simple_list_item_2, and two_line_list_item.
 */
public class ListViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ListView listView = findViewById(android.R.id.list);
        listView.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Dummy.NAMES.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /* Without ViewHolder pattern * /
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_listview, parent, false);

            ImageView image = convertView.findViewById(R.id.image);
            TextView text  = convertView.findViewById(R.id.text);

            image.setImageResource(Dummy.DRAWABLES[position]);
            text.setText(Dummy.NAMES[position]);
            // */

            /* With ViewHolder pattern */
            ViewHolder holder;

            if (convertView==null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_listview, parent, false);

                holder = new ViewHolder();
                holder.image = convertView.findViewById(R.id.image);
                holder.text  = convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.image.setImageResource(Dummy.DRAWABLES[position]);
            holder.text.setText(Dummy.NAMES[position]);
            // */
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
