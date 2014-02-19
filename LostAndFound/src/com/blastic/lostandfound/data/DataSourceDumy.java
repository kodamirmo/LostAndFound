package com.blastic.lostandfound.data;

import java.util.ArrayList;

import com.blastic.lostandfound.transferobjects.AbuseReport;
import com.blastic.lostandfound.transferobjects.AccidentReport;
import com.blastic.lostandfound.transferobjects.FoundReport;
import com.blastic.lostandfound.transferobjects.HomelessReport;
import com.blastic.lostandfound.transferobjects.LostReport;
import com.blastic.lostandfound.transferobjects.Report;

public class DataSourceDumy {
	
	private ArrayList<Report> reports;
	
	public DataSourceDumy(){
		reports=new ArrayList<Report>();
	}

	public ArrayList<Report> getData(){
		
		AbuseReport report=new AbuseReport("ABCCD",Report.TYPE_DOG, "  ",null,
				"Este es un comentario acerca de la situacion", "sin comentario", false, 1234, false,"Irving");
		
		AbuseReport report2=new AbuseReport("ABCCD",Report.TYPE_DOG, "  ","c",
				"Este es un comentario acerca de la situacion", "sin comentario", true, 1234, false,"Emmanuel");
		
		AbuseReport report3=new AbuseReport("ABCCD",Report.TYPE_DOG, "  ","",
				"Este es un comentario acerca de la situacion", "sin comentario", false, 1234, true,"Gonzalez");
		
		AccidentReport report4=new AccidentReport("ABCCD", Report.TYPE_CAT, "", null,
				"Este es un comentario acerca de la situacion", false, 234, true, "Pedro Paramo");
		
		FoundReport report5 =new FoundReport("ADSFE", Report.TYPE_OTHER, "", null, 
				"Este es un comentario acerca de la situacion", 2, "rojo", "", false, 0, true, "Laura Bozo");
		
		LostReport report6 =new LostReport("ADASDASF", Report.TYPE_CAT, "", "d", 
				"Este es un comentario acerca de la situacion", "un año", 2,"azul", "gato",false, 456, false, "Irving Emmanuel");
		
		HomelessReport report7 =new HomelessReport("ASDA", Report.TYPE_DOG, "", null,
				"Este es un comentario acerca de la situacion", true, 23, true, "Paulina Rubio");
		
		
		reports.add(report);
		reports.add(report2);
		reports.add(report3);
		reports.add(report4);
		reports.add(report5);
		reports.add(report6);
		reports.add(report7);
		
		return reports;
	}
}
