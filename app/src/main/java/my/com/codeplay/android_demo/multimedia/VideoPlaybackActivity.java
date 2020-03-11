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
package my.com.codeplay.android_demo.multimedia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import my.com.codeplay.android_demo.R;

@SuppressLint("SdCardPath")
@SuppressWarnings("unused")
public class VideoPlaybackActivity extends Activity {
	private static final String RAW_PATH
			= "android.resource://my.com.codeplay.android_demo/raw/google_holiday_doodle_2014";
	// example of source path in a SDCard
	private static final String SRC_PATH = "/sdcard/filename.3gp";
	// example of source from a streaming server
	private static final String URI_PATH = "rtsp://www.channel.com/filename.3gp";
	private VideoView videoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_playback);

		Uri uri = Uri.parse(RAW_PATH); // Uri.parse(URI_PATH);
		if (uri!=null) {
			videoView = findViewById(R.id.VideoView);
			videoView.setVideoURI(uri);
			//videoView.setVideoPath(SRC_PATH);

			videoView.setMediaController(new MediaController(this));
			videoView.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					videoView.seekTo(0);
				}

			});
			videoView.requestFocus();
			videoView.start();
		}
	}
}
