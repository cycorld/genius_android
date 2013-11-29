package net.likelion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class WebviewActivity extends Activity {
	public static final String PREFS_NAME = "TheGeniusPrefs";
	public static final String HOST = "http://genius.doo2.net";
	InputMethodManager mImm;
	private String selectedBtn;
	private String msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


		SharedPreferences code = getSharedPreferences(PREFS_NAME, 0);
		String val = String.valueOf(code.getInt("user_id", 0));
		Log.d("DEBUG", val);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		webSettings.setSupportZoom(false);
		webSettings.setUserAgentString( webSettings.getUserAgentString() + " (XY ClientApp)" );
		webSettings.setAllowFileAccess(true);
		webSettings.setSavePassword(false);
		webSettings.setSupportMultipleWindows(false);
		webSettings.setAppCacheEnabled(true);
		webSettings.setAppCachePath("");
		webSettings.setAppCacheMaxSize(5*1024*1024);

		final ScrollView myScrollView = (ScrollView) findViewById(R.id.scrollView1);
		myScrollView.getParent().requestDisallowInterceptTouchEvent(true);



		String url = HOST + "/mobi	le";
		//String url = "http://m.facebook.com";
		String postData = "id=" + val;
		myWebView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));

		//mImm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		//mImm.showSoftInput(myWebView, 0);

		//((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

		ImageButton btn1 = (ImageButton)findViewById(R.id.button1);
		ImageButton btn2 = (ImageButton)findViewById(R.id.button2);
		ImageButton btn3 = (ImageButton)findViewById(R.id.button3);
		ImageButton btn4 = (ImageButton)findViewById(R.id.button4);
		//Button sendBtn = (Button)findViewById(R.id.sendMsgBtn);
		final EditText msgEdit = (EditText)findViewById(R.id.sendMsg);

		msgEdit.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent envent) {
				// TODO Auto-generated method stub
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					//sendMessage();
					Log.d("DEBUG", "btn1 clicked");
					msg = msgEdit.getText().toString();
					if (msg != "") {
						msgEdit.setText("");
						new SendPost().execute();
					}
					handled = true;
				}
				return handled;
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("절대찬성입니까?")
		.setTitle("확인");
		// Add the buttons
		builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
				msg = "/s_agree";
				new SendPost().execute();
			}
		});
		builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});
		final AlertDialog dialog1 = builder.create();




		builder.setMessage("찬성입니까?").setTitle("확인");
		// Add the buttons
		builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
				msg = "/agree";
				new SendPost().execute();
			}
		});
		builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});
		final AlertDialog dialog2 = builder.create();
		builder.setMessage("반대입니까?").setTitle("확인");
		// Add the buttons
		builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
				msg = "/disagree";
				new SendPost().execute();
			}
		});
		builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});
		final AlertDialog dialog3 = builder.create();
		builder.setMessage("절대반대입니까?").setTitle("확인");
		// Add the buttons
		builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
				msg = "/s_disagree";
				new SendPost().execute();
			}
		});
		builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});
		final AlertDialog dialog4 = builder.create();

		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog1.show();

			}
		});
		btn2.setOnClickListener(new OnClickListener() {    
			@Override
			public void onClick(View arg0) {
				dialog2.show();

			}
		});
		btn3.setOnClickListener(new OnClickListener() {    
			@Override
			public void onClick(View arg0) {
				dialog3.show();

			}
		});
		btn4.setOnClickListener(new OnClickListener() {    
			@Override
			public void onClick(View arg0) {
				dialog4.show();

			}
		});



	}
	private class SendPost extends AsyncTask<Void, Void, String> {
		protected String doInBackground(Void... unused) {
			String content = executeClient();
			return content;
		}

		protected void onPostExecute(String result) {
			// 모두 작업을 마치고 실행할 일 (메소드 등등)
		}

		// 실제 전송하는 부분
		public String executeClient() {
			SharedPreferences code = getSharedPreferences(PREFS_NAME, 0);
			String val = String.valueOf(code.getInt("user_id", 0));
			ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
			post.add(new BasicNameValuePair("send_user_id", val));
			post.add(new BasicNameValuePair("target_user_id", "1"));
			post.add(new BasicNameValuePair("content", msg));
			post.add(new BasicNameValuePair("time_id", "3:3"));


			// 연결 HttpClient 객체 생성
			HttpClient client = new DefaultHttpClient();

			// 객체 연결 설정 부분, 연결 최대시간 등등
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);

			// Post객체 생성
			HttpPost httpPost = new HttpPost(HOST + "/web/push");
			Log.d("DEBUG", "0");

			try {
				Log.d("DEBUG", "1");
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
				httpPost.setEntity(entity);
				Log.d("DEBUG", "2");
				client.execute(httpPost);
				return EntityUtils.getContentCharSet(entity);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}


/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.webview, menu);
		return true;
	}
 */

