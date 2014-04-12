package com.blastic.lostandfound.location;

public class GeolocationUtils {
	
	private double degToRad(double deg){
        return (deg * Math.PI / 180.0);
    }

    private double radToDeg(double rad){
        return (rad / Math.PI * 180.0);
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(degToRad(lat1)) * Math.sin(degToRad(lat2)) + Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) * Math.cos(degToRad(theta));
        dist = Math.abs(Math.round(radToDeg(Math.acos(dist)) * 60 * 1.1515 * 1.609344 * 1000));
        return dist;
    }
     
    public void getReportsInRound(){
    	getDistance(0, 0, 0, 0);
    }

}
