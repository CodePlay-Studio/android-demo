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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import my.com.codeplay.android_demo.R;

/*
 * If an app would not actually want a full-blown web browser, the user won't need to interact with
 * the web page beyond reading it, and the web page won't need to interact with the user, it is
 * better to invoke the Browser application with a URL Intent rather than show it with a WebView.
 */
public class WebViewDemoActivity extends AppCompatActivity {
	// set the default URL to the website from assets directory
	private static final String DEFAULT_WEBSITE_FROM_ASSETS = "file:///android_asset/Home.html";
	private static final String JAVASCRIPT_HTML_FROM_ASSETS = "file:///android_asset/Javascript Demo/index.html";
    private static final String HTTP_PROTOCOL = "http://";
	private EditText etUrl;
	private WebView webView;

	@SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // display the progress in the activity actionbar, like the browser app does in below than API 21.
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_PROGRESS); // deprecated from API 21
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        etUrl = (EditText) findViewById(R.id.edittext);
        etUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_GO) {
                    String url = etUrl.getText().toString().trim();
                    if (TextUtils.isEmpty(url)) {
                        showSnackbar("Please enter a valid URL.");
                    } else if (url.equalsIgnoreCase("javascript")) {
                        webView.loadUrl(JAVASCRIPT_HTML_FROM_ASSETS);
                    } else {
                        if (!url.startsWith(HTTP_PROTOCOL)) {
                            etUrl.setText(url = HTTP_PROTOCOL + url);
                        }
                        webView.loadUrl(url);
                    }
                    return true;
                }
                return false;
            }
        });
        webView = (WebView) findViewById(R.id.webview);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setProgressBarVisibility(true);
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    setProgress(newProgress * 100);

                    if(newProgress == 100) {
                        setProgressBarVisibility(false);
                    }
                }
            });
        }
        /*
         * the below line of code set a WebView client, so the loadUrl method will open the URLs
         * inside any web pages within the same browser in the Activity of this app instead of other
         * browser apps.
         */
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    showSnackbar(error.getDescription());
                } else {
                    showSnackbar("Error: failed to load the URL.");
                }
            }
        });
        // enable JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new DeviceInfo(), "DeviceInfo");

        /* Example to load from an HTML string
        String htmlString = "<html><body>Load from <b>HTML</b> string</body></html>";
        webview.loadData(htmlString, "text/html", null);
        // */
        
        Intent intent = this.getIntent();
        Uri uri = intent.getData();
        if (uri != null)
            webView.loadUrl(uri.toString());
        else
            webView.loadUrl(DEFAULT_WEBSITE_FROM_ASSETS);
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView!=null && webView.canGoBack())
                webView.goBack();
			else
				finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showSnackbar(CharSequence msg) {
        Snackbar snackbar = Snackbar.make(etUrl, msg, Snackbar.LENGTH_LONG);
        // custom the message text color, otherwise it is followed the theme text color.
        ((TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text))
                .setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    // For accessing device info via Javascript Interface
    public class DeviceInfo {
        @JavascriptInterface
	    public String get() throws JSONException {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("model", Build.MODEL);
            jsonObject.put("manufacturer", Build.MANUFACTURER);
            return jsonObject.toString();
        }
    }
}

