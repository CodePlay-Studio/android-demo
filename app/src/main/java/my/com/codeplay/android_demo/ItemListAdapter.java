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

package my.com.codeplay.android_demo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import my.com.codeplay.android_demo.objects.ListItem;

/**
 * Created by Tham on 29/10/2017.
 */

public class ItemListAdapter extends ArrayAdapter {

    public ItemListAdapter(@NonNull Context context, @NonNull List items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_demo, parent, false);

            holder = new ViewHolder();
            holder.ivThumbnail = (ImageView) convertView.findViewById(R.id.image);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            holder.tvShortDesc = (TextView) convertView.findViewById(R.id.short_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListItem item = (ListItem) getItem(position);
        if (item != null) {
            holder.ivThumbnail.setImageResource(item.getThumbnailId());
            holder.tvTitle.setText(item.getTitleId());
            if (item.getShortDescId() > 0)
                holder.tvShortDesc.setText(item.getShortDescId());
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView ivThumbnail;
        TextView tvTitle;
        TextView tvShortDesc;
    }
}
