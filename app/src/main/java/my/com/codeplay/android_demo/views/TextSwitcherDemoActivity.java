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

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import my.com.codeplay.android_demo.R;

/**
 * Created by Tham on 11/11/2017.
 */

public class TextSwitcherDemoActivity extends AppCompatActivity {
    private static final long INTERVAL = 2000;
    private static final long ANIM_DURATION = INTERVAL / 2;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (tsText != null && texts !=null && texts.length>1) {
                if (counter == texts.length-1)
                    counter = 0;
                else
                    counter++;

                tsText.setText(texts[counter]);
                tsText.postDelayed(runnable, INTERVAL);
            }
        }
    };
    private TextSwitcher tsText;
    private String[] texts;
    private int counter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_switcher_demo);

        texts = getResources().getStringArray(R.array.text_switcher_texts);

        tsText = (TextSwitcher) findViewById(R.id.text_switcher);
        tsText.setVisibility(View.VISIBLE);
        tsText.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(TextSwitcherDemoActivity.this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setTextAppearance(
                            android.support.v7.appcompat.R.style.TextAppearance_AppCompat_Headline);
                } else {
                    textView.setTextAppearance(TextSwitcherDemoActivity.this,
                            android.support.v7.appcompat.R.style.TextAppearance_AppCompat_Headline);
                }
                textView.setTextColor(ContextCompat.getColor(
                        TextSwitcherDemoActivity.this, android.R.color.primary_text_dark));
                textView.setText(texts[counter = 0]);
                return textView;
            }
        });
        Animation animIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        animIn.setDuration(ANIM_DURATION);
        Animation animOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        animOut.setDuration(ANIM_DURATION);
        tsText.setInAnimation(animIn);
        tsText.setOutAnimation(animOut);
    }

    @Override
    protected void onResume() {
        super.onResume();

        tsText.postDelayed(runnable, INTERVAL / 2);
    }

    @Override
    protected void onStop() {
        tsText.removeCallbacks(runnable);

        super.onStop();
    }
}
