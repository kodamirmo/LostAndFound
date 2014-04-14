package com.blastic.lostandfound.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserData {
	
	public static boolean isGuest(Context context){
		return !isLogged(context);
	}
	
	public static void setUsername(Context context,String username){
		SharedPreferences preferences=context.getSharedPreferences("USER_DATA",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putString("USERNAME",username);
		editor.commit();	
	}
	
	public static boolean isLogged(Context context){
		SharedPreferences preferences=context.getSharedPreferences("CONFIG",Context.MODE_PRIVATE);
		return preferences.getBoolean("LOGGED",true);
	}
	
	public static void setLogged(Context context,boolean logged){
		SharedPreferences preferences=context.getSharedPreferences("CONFIG",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putBoolean("LOGGED",logged);
		editor.commit();	
	}
	
	private static SharedPreferences getGCMPreferences(Context context){
		return context.getSharedPreferences("GCM_UTILS",Context.MODE_PRIVATE);
	}
	
	public static boolean isRegisterGCM(Context context){
		if(getRegistrationId(context).isEmpty()){
			Log.i("TAG", "No esta registrado este dispositivo");
			return false;
		}
		return true;
	}
	
	public static String getRegistrationId(Context context){
		SharedPreferences preferences=getGCMPreferences(context);
		String registrationID=preferences.getString("REGISTER_ID", "");
		
		return registrationID;
	}
	
	public static void guardarRegistrationId(Context context, String id){
		SharedPreferences.Editor editor=getGCMPreferences(context).edit();
		editor.putString("REGISTER_ID", id);
		editor.commit();
	}

}
