package com.blastic.lostandfound.gcm;

import java.io.IOException;

import com.blastic.lostandfound.data.UserData;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

public class RegisterGCM {
	
	private static GoogleCloudMessaging gcm;
	private static final String SENDER_ID="645714650508";
	private static Activity activity;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void  registerGCM(Activity activity){
		
		RegisterGCM.activity=activity;
		
		if(GCMUtils.checkPlayServices(activity)){
			gcm=GoogleCloudMessaging.getInstance(activity);
			
			if(!UserData.isRegisterGCM(activity)){
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
					new AsyncRegisterGCM().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,1);
				else
					new AsyncRegisterGCM().execute(1);
			}else{
				Log.i("TAG", "Ya esta Registrando: " + UserData.getRegistrationId(activity));
			}
		}else{
			Log.i("TAG", "No soporta GCM");
		}
		
	}
	
	private static class AsyncRegisterGCM  extends AsyncTask<Object, Void, Boolean>{
		
		private String registrationID="";

		@Override
		protected Boolean doInBackground(Object... params) {
			Log.i("TAG", "Registrando");	
			try {
				registrationID=gcm.register(SENDER_ID);		
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
					
			return true;
		}
		
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		protected void onPostExecute(Boolean result){
			if(result){
				if(!UserData.isRegisterGCM(activity)){
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
						new AsyncSendRegisterId().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, registrationID);
					else
						new AsyncSendRegisterId().execute(registrationID);
				}	
			}else{
				Log.i("TAG", "error registrando");
			}
		}
	}
	
	private static class AsyncSendRegisterId extends AsyncTask<String, Void, Void>{

		@Override
		protected Void doInBackground(String... params) {

			String id=params[0];
			Log.i("TAG", "Dispositivo resgisrado con ID:  " + id);
			UserData.guardarRegistrationId(activity, id);
			return null;
		}
		
	}

}

//APA91bGg1Z3hXTBmCEecGSpniLHGJ92DjGfqNScQskVNDAg-Pe4xXPLd6Ooggerie4APx65nfkMzlUhpJiDw-0TchAaHmT4j35kfLWr81VsV20gAgI9J0FnRrU27DYLlo_f7Dhu80AMW9DVE5rEuyjQA2H0prBpMrsI3yCUcL1xHf0_9mx22bdc