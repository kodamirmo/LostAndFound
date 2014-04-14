package com.blastic.lostandfound.gcm;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.widget.Toast;

public class GCMUtils {
	
	public static boolean checkPlayServices(Activity activity){
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
		
		if( resultCode != ConnectionResult.SUCCESS){
			if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
				GooglePlayServicesUtil.getErrorDialog(resultCode, activity, 1234);
			}else{
				Toast.makeText(activity, "No soporta GooglePS", Toast.LENGTH_LONG).show();
			}
			
			return false;
		}
		
		return true;
	}

}
