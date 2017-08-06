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

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import my.com.codeplay.android_demo.R;

public class AnimationsActivity extends AppCompatActivity implements Animation.AnimationListener {
    private ImageView ivBird;
    private ImageButton ibStart;

    private AnimationDrawable flyingloop;
    private Animation animLeftToRight, animFlyAround, animFadeIn, animFadeOut;
    private boolean isFlying;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // set the below window flag to request shared element transition between activities.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);

        animLeftToRight = AnimationUtils.loadAnimation(this, R.anim.bird_fly_left_to_right);
        animLeftToRight.setAnimationListener(this);

        animFlyAround = AnimationUtils.loadAnimation(this, R.anim.bird_fly_around);
        animFlyAround.setAnimationListener(this);

        animFadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        ivBird = (ImageView) findViewById(R.id.bird);
        ibStart = (ImageButton) findViewById(R.id.button_start);
        ibStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFlying) {
                    isFlying = true;

                    ivBird.startAnimation(animFlyAround);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (flyingloop!=null) {
            if (flyingloop.isRunning())
                flyingloop.stop();
            flyingloop = null;
        }
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();

        if (!isFlying) {
            isFlying = true;

            ivBird.startAnimation(animLeftToRight);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        flyingloop = (AnimationDrawable) ivBird.getBackground();
        if (flyingloop!=null)
            flyingloop.start();
        if (!ivBird.isShown())
            ivBird.setVisibility(View.VISIBLE);
        if (ibStart.isShown()) {
            ibStart.startAnimation(animFadeOut);
            ibStart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        ivBird.setVisibility(View.INVISIBLE);
        ibStart.startAnimation(animFadeIn);
        ibStart.setVisibility(View.VISIBLE);
        if (flyingloop!=null) {
            flyingloop.stop();
            flyingloop = null;
        }
        isFlying = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
