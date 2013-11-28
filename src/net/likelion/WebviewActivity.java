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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class WebviewActivity extends Activity {
	public static final String PREFS_NAME = "TheGeniusPrefs";
	InputMethodManager mImm;
	private String selectedBtn;
	private String msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);

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





		String url = "http://192.168.10.56:3000/mobile";
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
		Button sendBtn = (Button)findViewById(R.id.sendMsgBtn);
		final EditText msgEdit = (EditText)findViewById(R.id.sendMsg);

		sendBtn.setOnClickListener(new OnClickListener() {    
			@Override
			public void onClick(View arg0) {
				Log.d("DEBUG", "btn1 clicked");
				msg = msgEdit.getText().toString();
				if (msg != "") {
					msgEdit.setText("");
					new SendPost().execute();
				}
			}
		});
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.d("DEBUD", "btn1 clicked");
				msg = "/s_agree";
				new SendPost().execute();
			}
		});
		btn2.setOnClickListener(new OnClickListener() {    
			@Override
			public void onClick(View arg0) {
				Log.d("DEBUD", "btn1 clicked");
				msg = "/agree";
				new SendPost().execute();
			}
		});
		btn3.setOnClickListener(new OnClickListener() {    
			@Override
			public void onClick(View arg0) {
				Log.d("DEBUD", "btn1 clicked");
				msg = "/disagree";
				new SendPost().execute();
			}
		});
		btn4.setOnClickListener(new OnClickListener() {    
			@Override
			public void onClick(View arg0) {
				Log.d("DEBUD", "btn1 clicked");
				msg = "/s_disagree";
				new SendPost().execute();
			}
		});



	}
	private class SendPost extends AsyncTask<Void, Void, String> {
		protected String doInBackground(Void... unused) {
			String content = executeClient();
			return content;
		}

		protected void onPostExecute(String result) {
			// ��� �۾��� ��ġ�� ������ �� (�޼ҵ� ���)
		}

		// ���� �����ϴ� �κ�
		public String executeClient() {
			SharedPreferences code = getSharedPreferences(PREFS_NAME, 0);
			String val = String.valueOf(code.getInt("user_id", 0));
			ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
			post.add(new BasicNameValuePair("send_user_id", val));
			post.add(new BasicNameValuePair("target_user_id", "1"));
			post.add(new BasicNameValuePair("content", msg));
			post.add(new BasicNameValuePair("time_id", "3:3"));
			

			// ���� HttpClient ��ü ����
			HttpClient client = new DefaultHttpClient();

			// ��ü ���� ���� �κ�, ���� �ִ�ð� ���
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);

			// Post��ü ����
			HttpPost httpPost = new HttpPost("http://192.168.10.56:3000/web/push");
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

