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

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * A started service only stops itself when it calls stopSelf() or stopService() is
 * explicitly called by other components.
 *
 * A bound service runs only as long as another application components is bound to it,
 * and it is destroyed when all the components unbind.
 *
 * A service's main thread is the same thread where UI operations take place for Activities running
 * in the same process.
 */
public class MediaPlayerService extends Service {
    public static final String EXTRA_SONG_ID = "android_demo.media_player.extra.SONG_ID";
    private MediaPlayer player;

    /**
     * The System calls onCreate() when the service is first created.
     * If the service is already running, this method is not called.
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * onStart() method will be called on Android version pre 2.0, on version 2.0 and above
     * it is deprecated with onStartCommand().
     */
    @Override
    public void onStart(Intent intent, int startid) {
        play(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        play(intent);

        //return super.onStartCommand(intent, flags, startId);
        /**
         * return START_STICKY if this service wants to continue running until it is explicitly stopped.
         * System will try to re-create the service if its' process is killed while it is started
         * (after returning from onStartCommand()) but don't retain the delivered intent. If there are
         * not any pending start commands to be delivered to the service, it will be re-create will a
         * null intent object. Example use case: a service performing background music playback.
         *
         * return START_NOT_STICKY if the service is killed while it is started (after returning from onStartCommand())
         * and there are no new start intents to deliver to it, System would not re-create it until
         * a future explicit start command call. An example of such a service would be one that pulls
         * for data from a server: it could schedule an alarm to poll every N minutes by having the
         * alarm start its service. When its onStartCommand(Intent, int, int) is called from the alarm,
         * it schedules a new alarm for N minutes later, and spawns a thread to do its networking.
         * If its process is killed while doing that check, the service will not be restarted until
         * the alarm goes off.
         */
        return START_STICKY;
        //return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (player!=null) {
            if (player.isPlaying())
                player.stop();
            player = null;
        }
    }

    private void play(Intent intent) {
        if (intent!=null) {
            int songId = intent.getIntExtra(EXTRA_SONG_ID, 0);
            if (songId!=0) {
                player = MediaPlayer.create(this, songId);
                player.setLooping(false);
                player.start();
            }
        }
    }

    /**
     * There are two recommended mechanisms for implementing interaction between client
     * components and a bound service.
     *
     * 1. In the event that the bound service is local and private to the same application
     * as the client component (in other words it runs within the same process and is not
     * available to components in other applications), the recommended mechanism is to create
     * a subclass of the Binder class and extend it to provide an interface to the service.
     * An instance of this Binder object is then returned by the onBind() method and subsequently
     * used by the client component to directly access methods and data held within the service.
     *
     * 2. In situations where the bound service is not local to the application
     * (in other words, it is running in a different process from the client component),
     * interaction is best achieved using a Messenger/Handler implementation.
     */
    class MyLocalBinder extends Binder {
        MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }
    private IBinder localBinder = new MyLocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        play(intent);
        return localBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (player!=null) {
            if (player.isPlaying())
                player.stop();
            player = null;
        }
        return super.onUnbind(intent);
    }

    public void playSong() {
        if (player==null || player.isPlaying())
            return;

        player.start();
    }

    public int getSongDuration() {
        if (player!=null) {
            return player.getDuration();
        }
        return 0;
    }

    public int getSongPosition() {
        if (player!=null && player.isPlaying()) {
            return player.getCurrentPosition();
        }
        return 0;
    }
}
