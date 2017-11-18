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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import my.com.codeplay.android_demo.R;

/**
 * Created by Tham on 18/11/2017.
 */

public class TextSwitchingActivity extends AppCompatActivity {
    private static final long INTERVAL = 2000;
    private static final long ANIM_DURATION = INTERVAL / 2;
    private TextView tvText;
    private String[] texts;
    private int counter;
    private float halfScreenWidth;
    private boolean isFadeIn;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (tvText != null && texts !=null && texts.length>1) {
                if (isFadeIn) {
                    if (counter == texts.length-1)
                        counter = 0;
                    else
                        counter++;

                    tvText.animate().alpha(1).x(0f).setDuration(ANIM_DURATION);
                    tvText.setText(texts[counter]);
                    tvText.postDelayed(runnable, INTERVAL);
                } else {
                    Log.d("check", "halfScreenWidth=" + halfScreenWidth);
                    tvText.animate().alpha(0).x(halfScreenWidth).setDuration(ANIM_DURATION);
                    tvText.postDelayed(runnable, INTERVAL / 2);
                }
            }

            isFadeIn = !isFadeIn;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_switcher_demo);

        halfScreenWidth = getScreenWidth() / 2f;

        texts = getResources().getStringArray(R.array.text_switcher_texts);

        tvText = (TextView) findViewById(R.id.textview);
        tvText.setVisibility(View.VISIBLE);
        tvText.setText(texts[counter = 0]);
    }

    @Override
    protected void onStart() {
        super.onStart();

        tvText.postDelayed(runnable, INTERVAL / 2);
    }

    @Override
    protected void onStop() {
        tvText.removeCallbacks(runnable);

        super.onStop();
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
