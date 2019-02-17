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

package my.com.codeplay.android_demo.notifications;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import my.com.codeplay.android_demo.MainActivity;
import my.com.codeplay.android_demo.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationsFragment extends Fragment implements View.OnClickListener {
	private AlertDialog alertDialog;

	public static NotificationsFragment newInstance() {
		NotificationsFragment fragment = new NotificationsFragment();
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notifications, container, false);

		view.findViewById(R.id.button_show_toast).setOnClickListener(this);
		view.findViewById(R.id.button_show_snackbar).setOnClickListener(this);
		view.findViewById(R.id.button_show_alertdialog).setOnClickListener(this);
		view.findViewById(R.id.button_show_listdialog).setOnClickListener(this);
		view.findViewById(R.id.button_show_datepickerdialog).setOnClickListener(this);
		view.findViewById(R.id.button_show_timepickerdialog).setOnClickListener(this);
		view.findViewById(R.id.button_notify).setOnClickListener(this);
		return view;
	}

	@Override
	public void onStop() {
		super.onStop();

		if (alertDialog!=null) {
			if (alertDialog.isShowing())
				alertDialog.dismiss();
			alertDialog = null;
		}
	}

	public void onClick(View view) {
		if (getActivity()==null)
			return;

		switch(view.getId()) {
			case R.id.button_show_toast:
				showToastMsg(getString(R.string.toast_msg));
				break;
			case R.id.button_show_snackbar:
				if (getView()!=null) {
					Snackbar snackbar = Snackbar.make(getView(), R.string.snackbar_msg, Snackbar.LENGTH_LONG);
					// custom the message text color, otherwise it is followed the theme text color.
					((TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text))
							.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
					snackbar.show();
				}
				break;
			case R.id.button_show_alertdialog:
				alertDialog = showAlertDialog();
				alertDialog.show();
				break;
			case R.id.button_show_listdialog:
				alertDialog = showListDialog();
				alertDialog.show();
				break;
			case R.id.button_show_datepickerdialog:
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getFragmentManager(), "datePicker");
				break;
			case R.id.button_show_timepickerdialog:
                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getFragmentManager(), "timePicker");
				break;
			case R.id.button_notify:
				notifyOnStatus();
				break;
		}
	}

	private void showToastMsg(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG)
				.show();
	}
	
	private AlertDialog showAlertDialog() {
		return new AlertDialog.Builder(getActivity())
				.setTitle("Dialog Title")
				.setMessage("Dialog message...")
				.setPositiveButton("Positive", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						showToastMsg("Positive button clicked");
					}
				})
				.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						showToastMsg("Neutral button clicked");
					}
				})
				.setNegativeButton("Negative", null)
				.setCancelable(false)
				.create();
	}
	
	private AlertDialog showListDialog() {
		return new AlertDialog.Builder(getActivity())
				.setTitle("Select an item")
				.setItems(R.array.spinner_items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						showToastMsg("Item " + (which+1) + " selected");
					}
				})
				.create();
		
	}
	
	private void notifyOnStatus() {
		/*
		 * Using NotificationCompat and its Builder in the Support Library to support for
		 * a wide range of platforms.
		 */
		NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
		// At bare minimum, a notification must include a small icon, a title and a detail text.
		builder.setSmallIcon(R.mipmap.ic_launcher)
			.setContentTitle("Notification Title")
			.setContentText("Notification message...");

		/*
		 * Below code show an example to set a custom layout for a notification.
		 * /
		RemoteViews contentView = new RemoteViews(getPackageName(),
			R.layout.notification_custom_layout);
		contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
		contentView.setTextViewText(R.id.text, "text");
		builder.setContent(contentView);
		// */

		/*
		 * Add an action button that shows on expanded notification,
		 * which are only available in Android 4.1 and later.
		 * /
		Intent actionIntent = new Intent(this, Receiver.class);
		// add an action string to uniquely identify an intent
		actionIntent.setAction(action);
		// put if any data to be process when user clicks on the action button
		actionIntent.putExtra("Data", ...);
		// To perform an activity via pending intent, use getActivity(...)
		PendingIntent actionPendingIntent = PendingIntent.getBroadcast(
				this,
				0, // requestCode
				actionIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.addAction(R.drawable.ic_launcher, "Action", actionPendingIntent);
		// */

		/*
		 * lines of code below define the notification's action that takes user 
		 * directly from the notification to an Activity in the app.
		 * 
		 *  The action itself is defined by a PendingIntent containing an Intent 
		 *  that starts an Activity.
		 */
		Intent resultIntent = new Intent(getActivity(), MainActivity.class);
		// To perform a broadcast via pending intent, use getBroadcast(...)
		PendingIntent resultPendingIntent = PendingIntent.getActivity(
				getActivity(),
				0, // requestCode
				resultIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(resultPendingIntent);
		
		// lines of code below issue an notification
		Notification notification = builder.build();
		notification.defaults |= Notification.DEFAULT_SOUND;
		// cancel the notification when user response to it
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// define a unique id for each notification to enable multiple notifications from the same app.
		int notificationId = 1;
		NotificationManager notificationMgr
				= (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
		notificationMgr.notify(notificationId, notification);
	}

	public static class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        }
    }
}
