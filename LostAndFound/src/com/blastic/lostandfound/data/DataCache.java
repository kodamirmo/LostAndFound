package com.blastic.lostandfound.data;

import java.util.ArrayList;

import com.blastic.lostandfound.transferobjects.Report;

public class DataCache {
	
	private  static ArrayList<Report> allReports;
	
	public static void saveCache(ArrayList<Report> allReports){
		DataCache.allReports=allReports;
	}
	
	public static ArrayList<Report> getCache(){
		return DataCache.allReports;
	}
}
