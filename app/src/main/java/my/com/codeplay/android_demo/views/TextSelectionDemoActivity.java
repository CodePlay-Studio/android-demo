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

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import my.com.codeplay.android_demo.R;

/**
 * Created by Tham on 30/10/2017.
 */

public class TextSelectionDemoActivity extends AppCompatActivity {
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mActionMode = mode;

            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.text_selection, menu);
            // the default selection actions come with Copy, Share, and Select All,
            // may remove these actions by calling removeItem on the Menu with its
            // id, for example see the lines below:
            /*menu.removeItem(android.R.id.copy);*/
            /*menu.removeItem(android.R.id.shareText);*/
            /*menu.removeItem(android.R.id.selectAll);*/
            return true;
        }

        // Called each time the action mode is shown after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated. Return false if
        // nothing is done.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_search:
                    mode.finish();
                    return true;
                case R.id.menu_translate:
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };
    // May save ActionMode as a member variable to make changes to the contextual action bar
    // in response to other events
    private ActionMode mActionMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_selection_demo);

        final TextView textView = (TextView) findViewById(R.id.textview);
        textView.setCustomSelectionActionModeCallback(mActionModeCallback);
    }
}
