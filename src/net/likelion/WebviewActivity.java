package net.likelion;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

public class WebviewActivity extends Activity {
	public static final String PREFS_NAME = "TheGeniusPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		
		SharedPreferences code = getSharedPreferences(PREFS_NAME, 0);
		String val = String.valueOf(code.getInt("user_id", 1));
		Log.d("DEBUG", val);

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
