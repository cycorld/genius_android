package net.likelion;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity {
	public static final String PREFS_NAME = "TheGeniusPrefs";
	
	private EditText loginCode;
	private Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		SharedPreferences code = getSharedPreferences(PREFS_NAME, 0);
		if (code.getInt("user_id", 1) != 1) {
			Intent webviewActivity = new Intent(getApplicationContext(), WebviewActivity.class);
			startActivity(webviewActivity);
			finish();
		}
		
		loginButton = (Button)findViewById(R.id.login_btn);
		loginCode = (EditText)findViewById(R.id.login_code);
		
		
		
		
		
	}
	public void login(View view) {
		SharedPreferences code = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = code.edit();
		editor.putInt("user_id", 3);
		editor.commit();
		
		
		Intent webviewActivity = new Intent(getApplicationContext(), WebviewActivity.class);
		startActivity(webviewActivity);
		finish();
	}

/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
*/
}
