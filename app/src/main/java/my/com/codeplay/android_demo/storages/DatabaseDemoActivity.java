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

package my.com.codeplay.android_demo.storages;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import my.com.codeplay.android_demo.R;

public class DatabaseDemoActivity extends AppCompatActivity {
    private ListView listView;
    private MyCursorAdapter myCursorAdapter;
    private DatabaseProvider database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listView = (ListView) findViewById(android.R.id.list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        database = new DatabaseProvider(this);
        cursor = database.query();

        myCursorAdapter = new MyCursorAdapter(this, cursor);
        listView.setAdapter(myCursorAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (myCursorAdapter!=null) {
            myCursorAdapter.changeCursor(null);
            myCursorAdapter = null;
        }

        if (cursor!=null && !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }

        if (database!=null) {
            database.close();
            database = null;
        }
    }
}
