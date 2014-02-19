package com.blastic.lostandfound;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.blastic.lostandfound.constants.Constants;
import com.blastic.lostandfound.data.DataCache;
import com.blastic.lostandfound.preferences.ConfigData;
import com.blastic.lostandfound.services.AllReportsWebService;
import com.blastic.lostandfound.transferobjects.Report;
import com.blastic.lostandfound.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Typeface;

public class SplashScreen extends Activity {

	private Activity activity;
	private ArrayList<Report> array;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		printHash();

		TextView appTitle = (TextView) findViewById(R.id.splash_app_tittle);

		SpannableString text = new SpannableString("" + appTitle.getText());
		text.setSpan(new ForegroundColorSpan(Color.rgb(99, 194, 208)), 0, 4, 0);
		text.setSpan(new ForegroundColorSpan(Color.rgb(255, 255, 255)), 5, 7, 1);
		text.setSpan(new ForegroundColorSpan(Color.rgb(255, 212, 0)), 7,
				text.length(), 2);
		appTitle.setText(text, BufferType.SPANNABLE);

		TextView appsubTitle = (TextView) findViewById(R.id.splash_app_subtittle);
		appsubTitle.setTypeface(null, Typeface.BOLD);

		activity = this;

		AsyncTaskSplash splash = new AsyncTaskSplash();
		splash.execute();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	private class AsyncTaskSplash extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {

			
			AllReportsWebService handler = new AllReportsWebService();
			DataCache.saveCache(handler.getReports());

			
			try {
				if (Constants.isDebug())
					Thread.sleep(100);
				else
					Thread.sleep(3000);
				/*
				//get report by Id
				ReportsByIdWebService handler2 = new ReportsByIdWebService();
				array = handler2.getReports("52c0cc72ba26191c5c60619e");

				if(array == null){
					Toast.makeText(SplashScreen.this,
							"No se encontró el id", Toast.LENGTH_SHORT)
							.show();
				}
				else{
						Log.i("array1", ""+array.get(0).getIdReport());
						Log.i("array2", ""+array.get(0).getComments());
						Log.i("array3", ""+array.get(0).getLastlocation());
						Log.i("array4", ""+array.get(0).getNumComments());
						Log.i("array5", ""+array.get(0).getPathPicture());
						Log.i("array6", ""+array.get(0).getPetType());
						Log.i("array7", ""+array.get(0).getTypeReport());
						Log.i("array8", ""+array.get(0).getUserName());
						Log.i("array9", ""+array.get(0).getHasPicture());				
				}
				
					
				 //get all reports
				
				
				
		
				//get report by type
				ReportsByTypeWebService handler = new ReportsByTypeWebService();
				handler.getReports("abuse"); 
			
				//get report by type and page size
				ReportsByTypeAndPageWebService handler = new ReportsByTypeAndPageWebService();
				handler.getReports("abuse",2); 
			
				//get reports by page
				ReportsByPageWebService handler = new ReportsByPageWebService();
				handler.getReports(3);
				
				return null; */
				
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
				loginScreen = new Intent(getApplicationContext(),
						LoginScreen.class);
			else
				loginScreen = new Intent(getApplicationContext(), Home.class);

			loginScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			getBaseContext().startActivity(loginScreen);
			activity.finish();
		}

	}

	private void printHash() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.e("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}