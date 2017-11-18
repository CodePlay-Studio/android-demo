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

package my.com.codeplay.android_demo.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import my.com.codeplay.android_demo.ItemListAdapter;
import my.com.codeplay.android_demo.R;
import my.com.codeplay.android_demo.objects.ListItem;

/**
 * Created by Tham on 29/10/2017.
 */

public class TextActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        final List<ListItem> itemList = new ArrayList<>();
        itemList.add(new ListItem(0, R.string.text_switcher, R.string.text_switcher_desc, TextSwitcherDemoActivity.class, 0));
        itemList.add(new ListItem(0, R.string.text_selection, R.string.text_selection_desc, TextSelectionDemoActivity.class, 0));

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(TextActivity.this,
                        itemList.get(position).getTargetComponent()));
            }
        });
        listView.setAdapter(new ItemListAdapter(this, itemList));
    }
}
