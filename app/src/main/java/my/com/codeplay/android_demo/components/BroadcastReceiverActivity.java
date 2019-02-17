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

package my.com.codeplay.android_demo.components;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import my.com.codeplay.android_demo.R;

public class BroadcastReceiverActivity extends AppCompatActivity {
    private MyBroadcastReceiver receiver = new MyBroadcastReceiver() {
        final String TAG = MyBroadcastReceiver.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);

            NetworkInfo info = null;

            final ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = cm.getActiveNetworkInfo();

            /* Check only if the connection is specified to WIFI connection type
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                final Network[] networks = cm.getAllNetworks();
                for (Network network : networks) {
                    info = cm.getNetworkInfo(network);
                    if (info.getType()==ConnectivityManager.TYPE_WIFI)
                        break;
                }
            } else {
                info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            }
            // */

            if (info != null && info.isConnected()) {
                tv.setCompoundDrawables(null, dConnected, null, null);
                tv.setText(R.string.network_connected);
            } else {
                tv.setCompoundDrawables(null, dDisconnected, null, null);
                tv.setText(R.string.network_disconnected);
            }

            // The below code logs intent info return from a broadcast.
            Log.v(TAG, "action: " + intent.getAction());
            Log.v(TAG, "component: " + intent.getComponent());
            Bundle extras = intent.getExtras();
            if (extras != null) {
                for (String key: extras.keySet()) {
                    Log.v(TAG, "key [" + key + "]: " + extras.get(key));
                }
            } else {
                Log.v(TAG, "no extras");
            }
        }
    };
    private TextView tv;
    private Drawable dConnected, dDisconnected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

        tv = (TextView) findViewById(R.id.textview);
        dConnected = getResources().getDrawable(R.drawable.handheld_network_connected);
        dConnected.setBounds(0, 0, dConnected.getMinimumWidth(), dConnected.getMinimumHeight());
        dDisconnected = getResources().getDrawable(R.drawable.handheld_network_disconnected);
        dDisconnected.setBounds(0, 0, dDisconnected.getMinimumWidth(), dDisconnected.getMinimumHeight());
    }

    @Override
    protected void onResume() {
        // Register a broadcast receiver programmatically
        registerReceiver(this.receiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);

        super.onPause();
    }
}
