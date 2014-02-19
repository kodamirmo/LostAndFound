package com.blastic.lostandfound.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigData {
	
	public static boolean isLogged(Context context){
		SharedPreferences preferences=context.getSharedPreferences("CONFIG",Context.MODE_PRIVATE);
		return preferences.getBoolean("LOGGED",false);
	}
	
	public static void setLogged(Context context,boolean logged){
		SharedPreferences preferences=context.getSharedPreferences("CONFIG",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putBoolean("LOGGED",logged);
		editor.commit();	
	}

}
