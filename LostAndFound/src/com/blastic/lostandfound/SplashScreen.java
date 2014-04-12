package com.blastic.lostandfound;

import com.blastic.lostandfound.constants.Constants;
import com.blastic.lostandfound.data.AppCache;
import com.blastic.lostandfound.data.DataSourceDumy;
import com.blastic.lostandfound.preferences.ConfigData;
import com.blastic.lostandfound.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

public class SplashScreen extends Activity {

	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		initUI();
		activity = this;
		AsyncTaskSplash splash = new AsyncTaskSplash();
		splash.execute();
	}

	private void initUI() {

		SpannableString spannable = new SpannableString(getString(R.string.app_title));
		spannable.setSpan(new ForegroundColorSpan(Color.rgb(99, 194, 208)), 0, 4, 0);
		spannable.setSpan(new ForegroundColorSpan(Color.rgb(255, 255, 255)), 5, 7, 1);
		spannable.setSpan(new ForegroundColorSpan(Color.rgb(255, 212, 0)), 7,spannable.length(), 2);
		
		final TextView appTitle = (TextView) findViewById(R.id.splash_app_tittle);
		appTitle.setText(spannable, BufferType.SPANNABLE);

	}

	private class AsyncTaskSplash extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... params) {

			//AllReportsWebService handler = new AllReportsWebService();
			//DataCache.saveCache(handler.getReports());
			DataSourceDumy handler=new DataSourceDumy();
			AppCache.saveCache(handler.getData());

			try {
				if (Constants.isDebug())
					Thread.sleep(100);
				else
					Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {

			Intent loginScreen;

			// Change condition when login has been implemented
			if (!ConfigData.isLogged(activity))
				loginScreen = new Intent(getApplicationContext(),LoginScreen.class);
			else
				loginScreen = new Intent(getApplicationContext(),Home.class);

			loginScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			getBaseContext().startActivity(loginScreen);
			activity.finish();
		}

	}

}