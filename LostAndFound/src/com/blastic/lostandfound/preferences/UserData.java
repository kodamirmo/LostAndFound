package com.blastic.lostandfound.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
	
	public static boolean isGuest(Context context){
		return !ConfigData.isLogged(context);
	}
	
	public static void setUsername(Context context,String username){
		SharedPreferences preferences=context.getSharedPreferences("USER_DATA",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putString("USERNAME",username);
		editor.commit();	
	}

}
