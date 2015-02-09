package com.parkgaram.restfulsample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.parkgaram.lib.restful.Method;
import com.parkgaram.lib.restful.StringBodyRest;
import com.parkgaram.lib.restful.StringBodyRest.Factory;
import com.parkgaram.lib.restful.client.RestRequestUtil;

public class MainActivity extends Activity {

	//Change Uri
	private String sampleUri = "https://what.you.make.rest-api";
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new RESTfulTask().execute((Void) null);
			}
		});
	}

	class RESTfulTask extends AsyncTask<Void, Void, HttpResponse> {

		@Override
		protected HttpResponse doInBackground(Void... params) {
			Factory factory = 
					Factory
					.create()
					.setMethod(Method.GET)
//					.setBody(body)
//					.setHeader(header)
					.setUri(sampleUri);
			
			StringBodyRest rest = factory.done();
			try {
				return RestRequestUtil.request(rest);
			} catch (Exception e) {
				Log.i("TEST", e.getLocalizedMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(HttpResponse result) {
			try {
				String ret = inputStreamToString(result.getEntity()
						.getContent());
				Toast.makeText(getApplicationContext(), ret, 0).show();
			} catch (IllegalStateException | IOException e) {
				Toast.makeText(getApplicationContext(),
						e.getLocalizedMessage(), 0).show();
			}
			super.onPostExecute(result);
		}
	}

	static public String inputStreamToString(InputStream is) throws IOException {

		BufferedReader rd = new BufferedReader(new InputStreamReader(is,Charset.forName("utf-8")));

		StringBuilder builder = new StringBuilder();

		String line = new String();

		while ((line = rd.readLine()) != null) {
			builder.append(line);
		}

		String res = builder.toString();

		return res;
	}
}
