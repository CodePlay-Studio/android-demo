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
package my.com.codeplay.android_demo.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import my.com.codeplay.android_demo.R;

import static android.content.Context.SENSOR_SERVICE;

public class SensorDemoFragment extends Fragment implements SensorEventListener {
    private static final String TYPE_SENSOR = "TYPE_SENSOR";
	private TextView output;
	private ImageView ivCompass;
	private Compass compass;
	private SensorManager sensorManager;
	private Sensor sensor;
	private boolean isSensing;
	
	// For Orientation
	private Sensor gravity, accelerometer, magnetometer;
	private final float[] mGravity = new float[3];
	private final float[] mGeomagnetic = new float[3];
	private boolean isOrientation;

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];
    private float currentDegree = 0f;
	// */

    public static SensorDemoFragment newInstance(boolean isOrientation) {
        SensorDemoFragment fragment = new SensorDemoFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean(TYPE_SENSOR, isOrientation);
        fragment.setArguments(bundle);

        return fragment;
    }

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sensor, container, false);

		output = (TextView) view.findViewById(R.id.output);
		ivCompass = (ImageView) view.findViewById(R.id.compass);
		compass = (Compass) view.findViewById(R.id.compass_custom);

		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
	}

	@Override
	public void onResume() {
		super.onResume();

        isOrientation = getArguments().getBoolean(TYPE_SENSOR);
        if (!isOrientation) {
            /*
             *  Uncomment one of the following line to test the sensor of:
             *  Accelerometer, Compass, Gyroscope, or Orientation
             */
            //sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            //sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            //sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION); // Deprecated since Android 2.2
        } else {
            // For orientation

            /*
             * these is the low-level implementation directly from source which includes
             * heavy measuring inaccuracies. For example, if the device suffers any linear
             * acceleration, or if there are any magnetic interferences, the measured values
             * are getting noisy.
             */
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

            /*
             * By (low-pass) filtered the accelerometer values to isolate the gravity component
             * from the accelerometer because they are not very exact, we can improve the stability
             * of the measurement.
             */
            gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

            /*
             * To even improve the measurement, we can use TYPE_ROTATION_VECTOR. It uses the
             * accelerometer, gyroscope and magnetometer if they are available for measuring
             * the devices orientation information. This way the data are stable and we have
             * a great response time.
             *
             * The technique to create software driven sensors, receiving data by getting the
             * input of several sensors is called sensor-fusion. The measured data from sensor
             * -fusion sensors is in many ways better than it would be possible when these sources
             * were used individually. Better can mean, more accurate, more complete or more reliable.
             */
            //Sensor fusionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            // */
        }

		if (sensor != null) {
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
			isSensing = true;
		}
		// This part of code is for orientation ==============================================
		else if ((accelerometer!=null || gravity!=null) && magnetometer!=null) {
			ivCompass.setVisibility(View.VISIBLE);
            compass.setVisibility(View.VISIBLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                sensorManager.registerListener(this, gravity!=null? gravity : accelerometer,
                        SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
                sensorManager.registerListener(this, magnetometer,
                        SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
            } else {
                sensorManager.registerListener(this, gravity!=null? gravity : accelerometer,
                        SensorManager.SENSOR_DELAY_UI);
                sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
            }
			isSensing = true;
		}
		// ====================================================================================
		else {
			Snackbar.make(output, R.string.sensor_not_available, Snackbar.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		if(isSensing) {
			sensorManager.unregisterListener(this);
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Not in use
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER ||
                event.sensor.getType()==Sensor.TYPE_GRAVITY) {
            //mGravity = event.values;
            System.arraycopy(event.values, 0, mGravity, 0, mGravity.length);
        }

		if (event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD) {
            //mGeomagnetic = event.values;
            System.arraycopy(event.values, 0, mGeomagnetic, 0, mGeomagnetic.length);
        }

		// Print orientation result =============================================================
		if (isOrientation) {
			/* Deprecated processing
            if (mGravity!=null && mGeomagnetic!=null) {
				float R[] = new float[9];
			    float I[] = new float[9];
			    boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
			    if (success) {
			        float orientation[] = new float[3];
			        SensorManager.getOrientation(R, orientation);
			        printResults(orientation);
					setCompassDirection(orientation[0]);
			        
			        mGravity = mGeomagnetic = null;
			    }
			}
			// */

            // Update rotation matrix, which is needed to update orientation angles.
            SensorManager.getRotationMatrix(mRotationMatrix, null,
                    mGravity, mGeomagnetic);
            // "mRotationMatrix" now has up-to-date information.
            SensorManager.getOrientation(mRotationMatrix, mOrientationAngles);
            printResults(mOrientationAngles);

            /*
             * Azimuth value depends on the device’s alignment. If the device is not holding flat (∓45° deviation),
             * use remapCoordinateSystem() in a useful way to get correct results.
             */

            // angle in degree [0 - 360] degree
            double azimuth = (Math.toDegrees(mOrientationAngles[0]) + 360) % 360;
            setCompassDirection(azimuth);
			compass.setAngle((float) -azimuth);
		}
		// ======================================================================================
		else {
			printResults(event.values);
		}
	}
	
	private void printResults(float[] values) {
		int length = values.length;

		StringBuilder builder = new StringBuilder();
		builder.append("\nSensor: ");
		builder.append(sensor==null? "Orientation" : sensor.getName());
		builder.append("\nType: ");
		builder.append(sensor==null? "TYPE_ORIENTATION" : sensor.getType());
		builder.append("\nValues:\n");
		for (int i = 0; i < length; i++) {
			builder.append("   [");
			builder.append(i);
			builder.append("] = ");
			builder.append(values[i]);
			builder.append("\n");
		}
		output.setText(builder);
	}

	private void setCompassDirection(double value) {
		// get the angle around the z-axis rotated
		float degree = Math.round(value);

		// create a rotation animation (reverse turn degree degrees)
		RotateAnimation rotate = new RotateAnimation(
				currentDegree,
				-degree,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		// set the rotate animation duration takes
		rotate.setDuration(210);
		// set the animation after the end of the reservation status
		rotate.setFillAfter(true);

		// Start the animation
		ivCompass.startAnimation(rotate);
		currentDegree = -degree;
	}
}
