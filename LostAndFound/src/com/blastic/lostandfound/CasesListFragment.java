package com.blastic.lostandfound;

import java.util.ArrayList;
import java.util.Iterator;

import com.blastic.lostandfound.data.DataCache;
import com.blastic.lostandfound.data.ReportsList;
import com.blastic.lostandfound.R;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CasesListFragment extends Fragment{

	private final int SCREEN_HOME = 0;
	private final int SCREEN_ALERTS = 1;
	private final int SCREEN_REPORTS = 2;
	private final int SCREEN_LOST = 3;
	private final int SCREEN_FOUND = 4;
	private final int SCREEN_ABUSE = 5;
	private final int SCREEN_HOMELESS = 6;
	private final int SCREEN_ACCIDENT = 8;
	private final int SCREEN_RANK = 9;
	private final int SCREEN_DONATE = 10;
	
	private LinearLayout parentLayout;
	private LayoutInflater inflater;
	
	private ReportsList reportList;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cases_list_layout, container, false);
    }
	
	@Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        parentLayout=(LinearLayout)getView().findViewById(R.id.layoutCasesList);
        inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        reportList=new ReportsList(DataCache.getCache(), inflater);

        
        Bundle arguments=getArguments();
        initScreen(arguments);
    }
	
	private void initScreen(Bundle arguments){
		int screenType=arguments.getInt("TYPE");
		
		switch(screenType){
			case SCREEN_HOME:
				initScreenHome();
				break;
			case SCREEN_ALERTS:
				initScreenAlerts();
				break;
			case SCREEN_REPORTS:
				initScreenReports();
				break;
			case SCREEN_LOST:
				initScreenLosts();
				break;
			case SCREEN_FOUND:
				initScreenFound();
				break;
			case SCREEN_ABUSE:
				initScreenAbuse();
				break;
			case SCREEN_HOMELESS:
				initScreenHomeless();
				break;		
			case SCREEN_ACCIDENT:
				initScreenAccident();
				break;
			case SCREEN_RANK:
				initScreenRank();
				break;
			case SCREEN_DONATE:
				initScreenDonate();
		}
	}
	

	private void initScreenHome(){
		ArrayList<View> auxList=reportList.getAllReports();
		Iterator<View> iterator=auxList.iterator();
		
		while(iterator.hasNext()){
			parentLayout.addView(iterator.next());
		}	
	}
	
	private void initScreenAlerts(){
		ArrayList<View> auxList=reportList.getAlertsReports();
		Iterator<View> iterator=auxList.iterator();
		
		while(iterator.hasNext()){
			parentLayout.addView(iterator.next());
		}	
	}
	
	private void initScreenReports(){
		/*ArrayList<View> auxList=reportList.getAllReports();
		Iterator<View> iterator=auxList.iterator();
		
		while(iterator.hasNext()){
			parentLayout.addView(iterator.next());
		}	*/
		//muestra lista con reportes levantados por usuario
		Intent openMyReports = new Intent(getActivity(), ListReportsActivity.class);
		openMyReports.putExtra("myString", "Mis reportes:");
        startActivity(openMyReports);
	}
	
	private void initScreenLosts(){
		ArrayList<View> auxList=reportList.getLostReports();
		Iterator<View> iterator=auxList.iterator();
		
		while(iterator.hasNext()){
			parentLayout.addView(iterator.next());
		}	
	}
	
	private void initScreenAbuse(){
		ArrayList<View> auxList=reportList.getAbuseReports();
		Iterator<View> iterator=auxList.iterator();
		
		while(iterator.hasNext()){
			parentLayout.addView(iterator.next());
		}	
	}

	private void initScreenFound(){
		ArrayList<View> auxList=reportList.getFoundReports();
		Iterator<View> iterator=auxList.iterator();
		
		while(iterator.hasNext()){
			parentLayout.addView(iterator.next());
		}	
	}
	
	private void initScreenHomeless(){
		ArrayList<View> auxList=reportList.getHomelessReports();
		Iterator<View> iterator=auxList.iterator();
		
		while(iterator.hasNext()){
			parentLayout.addView(iterator.next());
		}	
	}
	
	private void initScreenAccident() {
		ArrayList<View> auxList=reportList.getAccidentReports();
		Iterator<View> iterator=auxList.iterator();
		
		while(iterator.hasNext()){
			parentLayout.addView(iterator.next());
		}
		
	}
	
	private void initScreenDonate() {
		/*
		Intent i = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://pawhub.me/donativos.html"));
		startActivity(i);*/
		initScreenHome();
		Toast.makeText(getActivity(), "Esta opción aún no está disponible en el demo", Toast.LENGTH_LONG).show();
	}

	private void initScreenRank() {
		initScreenHome();
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri
				.parse("market://details?id=com.pawhub.lostandfound"));
		startActivity(intent);
	}
	
}
