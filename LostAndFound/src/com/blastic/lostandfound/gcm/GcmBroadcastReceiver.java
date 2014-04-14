package com.blastic.lostandfound.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("TAG","Se recibio el mensaje desde el server");
		ComponentName component=new ComponentName(
				context.getPackageName(),
				GcmIntentService.class.getName());
		
		intent.setComponent(component);
		
		startWakefulService(context,intent);
		setResultCode(Activity.RESULT_OK);
	}

}
