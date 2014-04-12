package com.blastic.lostandfound.data;

import java.util.ArrayList;
import java.util.Iterator;

import android.location.Location;

import com.blastic.lostandfound.transferobjects.Report;

public class AppCache {

	public static final int TYPE_ALERTS = 100;
	public static final int TYPE_MY_REPORTS = 200;

	private static ArrayList<Report> allReports;
	private static Location myLocation;

	// /////////// MY LOCATION /////////////////////
	public static Location getMyLocation() {
		return myLocation;
	}

	public static void setMyLocation(Location myLocation) {
		AppCache.myLocation = myLocation;
	}
	///////////////////////////////////////////////////////////////

	///////////// REPORTS /////////////////////

	public static void saveCache(ArrayList<Report> allReports) {
		AppCache.allReports = allReports;
	}

	public static ArrayList<Report> getAllReports() {
		return AppCache.allReports;
	}

	public static ArrayList<Report> getReports(int type) {
		if (type == TYPE_ALERTS)
			return getMyAltertReports();
		else if (type == TYPE_MY_REPORTS)
			return getMyReports();
		else
			return getReportsByType(type);
	}

	private static ArrayList<Report> getReportsByType(int type) {
		ArrayList<Report> auxList = new ArrayList<Report>();

		Iterator<Report> iterator = allReports.iterator();

		while (iterator.hasNext()) {
			Report report = iterator.next();
			if (report.getTypeReport() == type)
				auxList.add(report);
		}
		return auxList;
	}

	private static ArrayList<Report> getMyReports() {
		ArrayList<Report> auxList = new ArrayList<Report>();

		Iterator<Report> iterator = allReports.iterator();

		while (iterator.hasNext()) {
			Report report = iterator.next();
			auxList.add(report);
		}
		return auxList;
	}

	private static ArrayList<Report> getMyAltertReports() {
		ArrayList<Report> auxList = new ArrayList<Report>();

		Iterator<Report> iterator = allReports.iterator();

		while (iterator.hasNext()) {
			Report report = iterator.next();
			auxList.add(report);
		}
		return auxList;
	}

}
