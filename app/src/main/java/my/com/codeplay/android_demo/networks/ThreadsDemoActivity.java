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
package my.com.codeplay.android_demo.networks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import my.com.codeplay.android_demo.R;

public class ThreadsDemoActivity extends AppCompatActivity {
	private ProgressBar progressBar;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressBar.setProgress(msg.arg1);
			handler.postDelayed(run, 500);
		}
	};
	private Runnable run = new Runnable() {
		@Override
		public void run() {
			index += 10;

			Message msg = handler.obtainMessage();
			msg.arg1 = index;
			if (index<=100) {
				handler.sendMessage(msg);
			} else {
				isLoading = false;
				isAsyncTask = true;
			}
		}
	};
	private int index;
	private boolean isLoading, isAsyncTask;

	public void onClick(View v) {
		if (isLoading) return;

		switch(v.getId()) {
			case R.id.button_ui_thread:
				progressBar.setProgress(index = 0);
				isLoading = true;

				while (index<100) {
					index += 10;
					progressBar.setProgress(index);

					/*
					 * the below code stimulates a task which takes 0.5sec
					 */
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				isLoading = false;
				break;
			case R.id.button_worker_thread:
				isLoading = true;

				if (isAsyncTask) {
					// Using AsyncTask
					new MyAsyncTask().execute();

					show("Worker Thread with Asynctask demo.");
				} else {
					progressBar.setProgress(index = 0);

					//Using Handler
					handler.post(run);

					show("Worker Thread with Message Handler demo.");
				}
				break;
			case R.id.button_reset:
				if (!isLoading)
					progressBar.setProgress(index = 0);
				break;
		}
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        isLoading = false;
        isAsyncTask = false;
    }

    private class MyAsyncTask extends AsyncTask<Void, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressBar.setProgress(index = 0);
		}

		@Override
		protected String doInBackground(Void... arg0) {
			while (index<100) {
				index += 10;
				this.publishProgress(index); //progressBar.setProgress(index);

				/*
				 * the below code stimulates a task which takes 0.5sec
				 */
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);

			progressBar.setProgress(values[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			isLoading = false;
			isAsyncTask = false;
		}
    }

    private void show(String msg) {
		Snackbar snackbar = Snackbar.make(progressBar, msg, Snackbar.LENGTH_LONG);
		((TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text))
				.setTextColor(ContextCompat.getColor(this, android.R.color.white));
		snackbar.show();
	}
}
