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

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import my.com.codeplay.android_demo.R;
import my.com.codeplay.android_demo.viewgroups.ViewHolder;

public class MyCursorAdapter extends CursorAdapter {
    private int nameColIndex, imageColIndex;

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    /**
     * CursorAdapter has an implementation of getView() which inspects the supplied View to recycle
     * and delegate to newView() and bindView(). If the View is null, getView() calls newView() and
     * then bindView(). If it is not null, getView() just calls bindView().
     *
     * In such a way as enforces the row recycling pattern. Hence, you do not need to do anything
     * special with a CursorAdapter for row recycling if you are overriding newView() and bindView().
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        nameColIndex = cursor.getColumnIndexOrThrow(DatabaseProvider.COL_NAME);
        imageColIndex = cursor.getColumnIndexOrThrow(DatabaseProvider.COL_IMAGE);

        View view = LayoutInflater.from(context).inflate(
                R.layout.item_cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = (TextView) view.findViewById(R.id.text);
        viewHolder.image = (ImageView) view.findViewById(R.id.image);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        nameColIndex = cursor.getColumnIndexOrThrow(DatabaseProvider.COL_NAME);
        imageColIndex = cursor.getColumnIndexOrThrow(DatabaseProvider.COL_IMAGE);

        viewHolder.text.setText(cursor.getString(nameColIndex));
        viewHolder.image.setImageResource(cursor.getInt(imageColIndex));
    }
}
