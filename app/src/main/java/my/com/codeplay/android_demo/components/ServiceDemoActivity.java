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

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import my.com.codeplay.android_demo.R;

public class ServiceDemoActivity extends AppCompatActivity {
    private TextView tvTime;
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            tvTime.post(new Runnable() {
                @Override
                public void run() {
                    if (isBound && playerService!=null) {
                        int playback = playerService.getSongPosition();
                        if (playback>0) {
                            int time = playerService.getSongDuration() - playback;
                            if (time>0) {
                                long seconds = (TimeUnit.MILLISECONDS.toSeconds(time)%60);
                                if (seconds>9)
                                    tvTime.setText(TimeUnit.MILLISECONDS.toMinutes(time) + ":" + seconds);
                                else
                                    tvTime.setText(TimeUnit.MILLISECONDS.toMinutes(time) + ":0" + seconds);
                            }
                        }
                        tvTime.postDelayed(run, interval);
                    }
                }
            });
        }
    };
    private long interval = 1000;
    /**
     * A ServiceConnection subclass is needed to successfully bind to a service
     * and receive the IBinder object returned by the service's onBind() method.
     */
    private ServiceConnection mpServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaPlayerService.MyLocalBinder binder =
                    (MediaPlayerService.MyLocalBinder) service;
            playerService = binder.getService();
            isBound = true;

            tvTime.post(run);
        }

        /*
		 * onServiceDisconnected is not supposed to be raised when you unbind your service,
		 * so don't rely on it. It is supposed to inform you in case the connection between
		 * your Service and ServiceConnection is dropped.
		 */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            playerService = null;
            isBound = false;
        }
    };
    private MediaPlayerService playerService;
    private boolean isBound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        tvTime = findViewById(R.id.text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindPlayer();
    }

    public void onMediaPlayerButtonClick(View v) {
        switch (v.getId()) {
            case R.id.button_play:
                Intent mediaPlayerIntent = new Intent(this,
                        MediaPlayerService.class);
                mediaPlayerIntent.putExtra(MediaPlayerService.EXTRA_SONG_ID, R.raw.mungkin_nanti);
                //startService(mediaPlayerIntent);

                if (isBound) {
                    playerService.playSong();
                } else {
                    bindService(mediaPlayerIntent,
                            mpServiceConnection, Context.BIND_AUTO_CREATE);
                }
                break;
            case R.id.button_stop:
                //stopService(new Intent(this, MediaPlayerService.class));

                unbindPlayer();
                break;
        }
    }

    private void unbindPlayer() {
        if (isBound) {
            unbindService(mpServiceConnection);
            isBound = false;
            playerService = null;
        }
    }
}
