package net.likelion;

import org.apache.http.util.EncodingUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebviewActivity extends Activity {
	public static final String PREFS_NAME = "TheGeniusPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		
		SharedPreferences code = getSharedPreferences(PREFS_NAME, 0);
		String val = String.valueOf(code.getInt("user_id", 1));
		Log.d("DEBUG", val);
		
		WebView myWebView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
		String url = "http://genius.doo2.net/mobile";
		String postData = "id=" + val;
		myWebView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));

	}
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.webview, menu);
		return true;
	}
	*/

}
