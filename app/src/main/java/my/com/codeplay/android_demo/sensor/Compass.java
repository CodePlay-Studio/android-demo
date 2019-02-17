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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import my.com.codeplay.android_demo.R;

public class Compass extends View {
    private static final String TAG = Compass.class.getSimpleName();
	private Paint myPaint;
	private float angle;
	private int width;
	private int height;
    private float strokeSize;

	public Compass(Context context, AttributeSet attrs) {
		super(context, attrs);

		myPaint = new Paint();
        strokeSize = getResources().getDimension(R.dimen.stroke);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "width=" + w + " and height=" + h);
        width = w;
		height = h;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		myPaint.setColor(Color.BLACK);
		myPaint.setStrokeWidth(strokeSize);

		canvas.translate(width-100, height/2);
		canvas.rotate(angle);
		//canvas.drawColor(Color.GRAY);
		canvas.drawLine(0, -50, 0, 50, myPaint);
		myPaint.setTextSize(60);
		canvas.drawText("N", -20, -55, myPaint);			
	}
	
	
	public void setAngle(float direction) {
		angle = direction;
		invalidate();
	}
	
}
