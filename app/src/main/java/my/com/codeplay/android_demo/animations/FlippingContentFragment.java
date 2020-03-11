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

package my.com.codeplay.android_demo.animations;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import my.com.codeplay.android_demo.R;

/**
 * Created by Tham on 18/11/2017.
 */

public class FlippingContentFragment extends Fragment {
    private static final String KEY_IMG = "banner";
    private static final String KEY_TXT = "desc";
    private ImageView imageView;
    private int imgId;

    public static FlippingContentFragment newInstance(int imgId, int txtId) {
        FlippingContentFragment fragment = new FlippingContentFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_IMG, imgId);
        args.putInt(KEY_TXT, txtId);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flipping_content, container, false);

        imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);

        imgId = getArguments().getInt(KEY_IMG);
        imageView.setImageResource(imgId);
        textView.setText(getArguments().getInt(KEY_TXT));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        switch (imgId) {
            case R.drawable.banner_ad:
                imageView.setBackgroundColor(ContextCompat
                        .getColor(getActivity(), R.color.colorAccent));
                break;
            case R.drawable.banner_pad:
                imageView.setBackgroundColor(ContextCompat
                        .getColor(getActivity(), R.color.colorPrimaryDark));
                break;
        }
    }
}
