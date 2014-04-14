package com.blastic.lostandfound;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.blastic.lostandfound.config.Config;
import com.blastic.lostandfound.data.AppCache;
import com.blastic.lostandfound.data.DataSourceDumy;
import com.blastic.lostandfound.data.UserData;
import com.blastic.lostandfound.gcm.RegisterGCM;
import com.blastic.lostandfound.location.UserLocation;
import com.blastic.lostandfound.location.UserLocation.NoLocationException;

public class SplashScreen extends Activity {

	private Activity activity;
	private UserLocation userLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		initUI();
		initSplash();
		RegisterGCM.registerGCM(this);
	}

	private void initUI() {

		SpannableString spannable = new SpannableString(getString(R.string.app_title));
		spannable.setSpan(new ForegroundColorSpan(Color.rgb(99, 194, 208)), 0, 4, 0);
		spannable.setSpan(new ForegroundColorSpan(Color.rgb(255, 255, 255)), 5, 7, 1);
		spannable.setSpan(new ForegroundColorSpan(Color.rgb(255, 212, 0)), 7,spannable.length(), 2);
		
		final TextView appTitle = (TextView) findViewById(R.id.splash_app_tittle);
		appTitle.setText(spannable, BufferType.SPANNABLE);

	}
	
	private void initSplash(){
		activity = this;
		userLocation=new UserLocation(activity);
		new AsyncTaskLocation().execute();
	}
	
	private class AsyncTaskLocation extends AsyncTask<Void,Void, Location>{
		
		private long initTime;
		private long currentTime;

		@Override
		protected Location doInBackground(Void... params) {
			
			boolean haveLocation=false;
			Location location = null;
			currentTime=initTime=System.currentTimeMillis();
			
			do{
					
				Log.i("TAG","Intentando localizar");
				try {
					location=userLocation.getLocation();
					haveLocation=true;
				} catch (NoLocationException e) {
					haveLocation=false;
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
					}
				}
			}while(!haveLocation && quedaTiempo());
			
			return location;
		}
		
		protected void onPostExecute(final Location result) {
			userLocation.endLocation();
			AppCache.setMyLocation(result);
			
			new AsyncTaskSplash().execute();
		}
		
		private boolean quedaTiempo(){
			currentTime=System.currentTimeMillis();
			return ((currentTime-initTime)<3000)?true:false;	
		}
		
	}

	private class AsyncTaskSplash extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... params) {

			//AllReportsWebService handler = new AllReportsWebService();
			//DataCache.saveCache(handler.getReports());
			DataSourceDumy handler=new DataSourceDumy();
			AppCache.saveCache(handler.getData());

			try {
				if (Config.isDebug())
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
			if (!UserData.isLogged(activity))
				loginScreen = new Intent(getApplicationContext(),LoginScreen.class);
			else
				loginScreen = new Intent(getApplicationContext(),Home.class);

			loginScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			getBaseContext().startActivity(loginScreen);
			activity.finish();
		}
	}

}