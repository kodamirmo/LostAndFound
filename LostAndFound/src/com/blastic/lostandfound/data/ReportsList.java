package com.blastic.lostandfound.data;

import java.util.ArrayList;
import java.util.Iterator;

import android.view.LayoutInflater;
import android.view.View;

import com.blastic.lostandfound.transferobjects.Report;
import com.blastic.lostandfound.views.ReportView;

public class ReportsList {
	
	private ArrayList<Report> allReports;
	private LayoutInflater inflater;
	
	public ReportsList(ArrayList<Report> allReports,LayoutInflater inflater) {
		this.allReports=allReports;
		this.inflater=inflater;
	}
	
	public ArrayList<View> getAllReports(){
		ArrayList<View> auxList=new ArrayList<View>();
		
		Iterator<Report> iterator=allReports.iterator();
		
		while(iterator.hasNext()){
			Report report=iterator.next();
			auxList.add(new ReportView(report, inflater).getReportChart());
		}
		
		return auxList;
	}
	
	public ArrayList<View> getAlertsReports(){
		ArrayList<View> auxList=new ArrayList<View>();
		
		Iterator<Report> iterator=allReports.iterator();
		
		while(iterator.hasNext()){
			Report report=iterator.next();
			if(report.isAlert())
				auxList.add(new ReportView(report, inflater).getReportChart());
		}
		return auxList;
	}

	public ArrayList<View> getLostReports(){
		return getReports(Report.CAUSE_LOST);
	}
	
	public ArrayList<View> getFoundReports(){
		return getReports(Report.CAUSE_FOUND);
	}
	
	public ArrayList<View> getHomelessReports(){
		return getReports(Report.CAUSE_HOMELESS);
	}
	
	public ArrayList<View> getAbuseReports(){
		return getReports(Report.CAUSE_ABUSE);
	}
	
	public ArrayList<View> getAccidentReports(){
		return getReports(Report.CAUSE_ACCIDENT); 
	}
	
	private ArrayList<View> getReports(int type){
		ArrayList<View> auxList=new ArrayList<View>();
		
		Iterator<Report> iterator=allReports.iterator();
		
		while(iterator.hasNext()){
			Report report=iterator.next();
			if(report.getTypeReport()==type)
				auxList.add(new ReportView(report, inflater).getReportChart());
		}
		return auxList;
	}

}
