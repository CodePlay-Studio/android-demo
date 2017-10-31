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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import my.com.codeplay.android_demo.R;

/**
 * Created by Tham on 05/08/2017.
 */

public class ViewsFragment extends Fragment {
    private TextView textView;
    private EditText editText;
    private Chronometer chronometer;

    public static ViewsFragment newInstance() {
        ViewsFragment fragment = new ViewsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_views, container, false);

        textView = (TextView) view.findViewById(R.id.textview);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(textView.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(getActivity(), TextActivity.class));
            }
        };
        spannableStringBuilder.setSpan(clickableSpan, 10, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableStringBuilder);
        editText = (EditText) view.findViewById(R.id.edittext);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_DONE) {
                    editText.clearFocus();
                    toggle(getActivity());
                }
                return false;
            }
        });

        chronometer = (Chronometer) view.findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        // isCountDown(), isTheFinalCountDown() and setCountDown() methods is only available in API 24.
        //chronometer.setCountDown(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        chronometer.start();
    }

    @Override
    public void onStop() {
        super.onStop();

        chronometer.stop();
    }

    private static void toggle(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
        } else {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // show
        }
    }
}
