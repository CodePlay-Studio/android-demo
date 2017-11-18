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

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import my.com.codeplay.android_demo.R;

/**
 * Created by Tham on 18/11/2017.
 */

public class FlippingActivity extends AppCompatActivity {
    private Button btnFlip;
    private boolean flipBack;

    public void flip(View v) {
        if (flipBack) {
            flipBack = false;

            btnFlip.setBackgroundResource(R.drawable.button_rectangle_primarydark);
            btnFlip.setText(R.string.post_android_development);

            getFragmentManager().popBackStack();
            return;
        }

        flipBack = true;

        btnFlip.setBackgroundResource(R.drawable.button_rectangle_accent);
        btnFlip.setText(R.string.android_development);

        getFragmentManager().beginTransaction()
                // Replace the default fragment animations with animator resources
                // representing rotations when switching to the second fragment, as
                // well as animator resources representing rotations when flipping
                // back to the default fragment (e.g. when the system Back button is pressed).
                //
                // Note that Property animation is only work with android.app.Fragment
                // but not with android.support.v4.app.Fragment. For the latter, use View
                // animation instead.
                .setCustomAnimations(
                        R.animator.flip_right_in,
                        R.animator.flip_right_out,
                        R.animator.flip_left_in,
                        R.animator.flip_left_out)
                .replace(R.id.container_1, FlippingContentFragment.newInstance(
                        R.drawable.banner_pad, R.string.post_android_development_desc))
                // Add this transaction to the back stack, allowing users to press
                // Back to get to the front of the card.
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flipping_demo);

        btnFlip = (Button) findViewById(R.id.button_flip);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_1, FlippingContentFragment.newInstance(
                R.drawable.banner_ad, R.string.android_development_desc));
        if (findViewById(R.id.container_2) != null)
            transaction.replace(R.id.container_2, FlippingContentFragment.newInstance(
                    R.drawable.banner_pad, R.string.post_android_development_desc));
        transaction.commit();
    }
}
