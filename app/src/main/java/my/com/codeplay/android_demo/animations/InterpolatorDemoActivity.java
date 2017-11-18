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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.Spinner;

import my.com.codeplay.android_demo.R;

/**
 * Created by Tham on 17/11/2017.
 */

public class InterpolatorDemoActivity extends AppCompatActivity
        implements Animation.AnimationListener{
    private Spinner sInterpolators;
    private View vObj1, vObj2;
    private Animation anim1, anim2;
    private boolean isAnimating;

    public void animate(View v) {
        if (isAnimating)
            return;

        int interpolator = sInterpolators.getSelectedItemPosition();
        switch (interpolator) {
            case 0:
                anim1.setInterpolator(new LinearInterpolator());
                break;
            case 1:
                anim1.setInterpolator(new AccelerateInterpolator());
                break;
            case 2:
                anim1.setInterpolator(new DecelerateInterpolator());
                break;
            case 3:
                anim1.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            case 4:
                anim1.setInterpolator(new AnticipateInterpolator());
                break;
            case 5:
                anim1.setInterpolator(new AnticipateOvershootInterpolator());
                break;
            case 6:
                anim1.setInterpolator(new OvershootInterpolator());
                break;
            case 7:
                anim1.setInterpolator(new BounceInterpolator());
                break;
            case 8:
                anim2.setInterpolator(new CycleInterpolator(2f));
                break;
        }

        if (interpolator==8) {
            vObj2.startAnimation(anim2);
        } else {
            vObj1.startAnimation(anim1);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolators_demo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sInterpolators = (Spinner) findViewById(R.id.interpolators);
        sInterpolators.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==8) {
                    vObj1.clearAnimation();
                    vObj1.setVisibility(View.INVISIBLE);
                    vObj2.setVisibility(View.VISIBLE);
                } else {
                    vObj1.setVisibility(View.VISIBLE);
                    vObj2.clearAnimation();
                    vObj2.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        vObj1 = findViewById(R.id.object1);
        vObj2 = findViewById(R.id.object2);

        anim1 = AnimationUtils.loadAnimation(this, R.anim.translate_y_1);
        anim1.setAnimationListener(this);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.translate_y_2);
        anim2.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        isAnimating = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        isAnimating = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
