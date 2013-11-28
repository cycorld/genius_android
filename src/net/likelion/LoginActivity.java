package net.likelion;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity {
	public static final String PREFS_NAME = "TheGeniusPrefs";
	
	private EditText loginCode;
	private Button loginButton;
	private int codeVal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		SharedPreferences code = getSharedPreferences(PREFS_NAME, 0);
		
		Log.d("DEBUG", String.valueOf(code.getInt("user_id", 1)));
		
		if (code.getInt("user_id", 1) != 1) {
			Log.d("DEBUG", "in if");
			Intent webviewActivity = new Intent(getApplicationContext(), WebviewActivity.class);
			startActivity(webviewActivity);
			finish();
		}
		setContentView(R.layout.activity_login);
		
		loginButton = (Button)findViewById(R.id.login_btn);
		loginCode = (EditText)findViewById(R.id.login_code);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}
	public void login(View view) {
		SharedPreferences code = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = code.edit();
		String codeValue = loginCode.getText().toString();
		if (codeValue.length() > 5 ) {
			codeVal = Integer.parseInt(loginCode.getText().toString().substring(0, 5));
		} else {
			codeVal = Integer.parseInt(loginCode.getText().toString());
		}
		editor.putInt("user_id", codeVal);
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
