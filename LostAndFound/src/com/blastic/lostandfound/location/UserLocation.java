package com.blastic.lostandfound.location;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class UserLocation implements LocationListener{
	
	private LocationManager locationManager;
	private Location currentLocation;
	
	public UserLocation(Context context){
		locationManager =(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		
		Log.i("TAG", "Init location");
		
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
		    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 250, 0, this); 
        }else{
		    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 250, 0, this);
        }
			
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.i("TAG", "Change location");
		currentLocation=location;
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
	
	public Location getLocation() throws NoLocationException{
		if(currentLocation==null)
			throw new NoLocationException("No location found");
		
		Log.d("TAG_LOCATION","Long: " +  currentLocation.getLongitude() + " Lat:" + currentLocation.getLatitude());
		return currentLocation;
	}
	
	public void endLocation(){
		locationManager.removeUpdates(this);
	}
	
	public class NoLocationException extends Exception {
	
		private static final long serialVersionUID = 1L;

		public NoLocationException() { 
			super(); 
		}
		  
		public NoLocationException(String message) { 
			super(message); 
		}	 
	}

}
