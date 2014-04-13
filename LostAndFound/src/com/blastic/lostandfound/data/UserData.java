package com.blastic.lostandfound.data;

import android.content.Context;
import android.content.SharedPreferences;

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

}
