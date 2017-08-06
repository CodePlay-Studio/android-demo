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

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import my.com.codeplay.android_demo.R;
import my.com.codeplay.android_demo.data.Dummy;

public class RecyclerViewActivity extends Activity {
    // set the number of columns if RecyclerView is used to display in grid order.
    private static final int COLUMNS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        // set to true to optimize performance if changes in content do not change the layout size
        mRecyclerView.setHasFixedSize(true);
        /*
         * provide a LinearLayoutLayoutManager to align items in single order vertically or horizontally.
         * To lay items in a grid, use GridLayoutManager instead. By default, each item occupies 1 span.
         * You can change it by providing a custom GridLayoutManager.SpanSizeLookup instance via
         * setSpanSizeLookup(SpanSizeLookup). Example:
         *
         * GridLayoutManager mLayoutManager = new GridLayoutManager(this, COLUMNS);
         */
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            ViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.image);
                textView  = (TextView)  view.findViewById(R.id.text);
            }
        }

        @Override
        public int getItemCount() {
            return Dummy.NAMES.length;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
            View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_cardview, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.imageView.setImageResource(Dummy.DRAWABLES[position]);
            viewHolder.textView.setText(Dummy.NAMES[position]);
        }
    }
}
