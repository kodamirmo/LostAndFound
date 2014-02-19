package com.blastic.lostandfound.transferobjects;

import java.io.Serializable;

import android.graphics.Color;

public class Report implements Serializable{
	
	private static final long serialVersionUID = 2330015619136786055L;
	
	public static final int TYPE_DOG=0;
	public static final int TYPE_CAT=1;
	public static final int TYPE_OTHER=2;
	
	public static final int CAUSE_ABUSE=0;
	public static final int CAUSE_ACCIDENT=1;
	public static final int CAUSE_FOUND=2;
	public static final int CAUSE_LOST=3;
	public static final int CAUSE_HOMELESS=4;
	
	public static final int COLOR_ABUSE=Color.parseColor("#FFD400");
	public static final int COLOR_ACCIDENT=Color.parseColor("#F3A2B9");
	public static final int COLOR_FOUND=Color.parseColor("#CDE400");
	public static final int COLOR_LOST=Color.parseColor("#B71C4E");
	public static final int COLOR_HOMELESS=Color.parseColor("#63C2D0");
	
	private String idReport;
	private int petType;
	private String lastlocation;
	private Boolean hasPicture;
	private String pathPicture;
	private String comments;
	
	protected static int typeReport;
	
	////////////////////////////////////////////
	private boolean isAlert;
	private int numComments;
	private boolean isResolve;
	private String userName;
	
	public Report(String idReport, int typeReport, int petType, String lastlocation,String pathPicture,String comments,
				boolean isAlert,int numComments, boolean isResolve,String userName) {
		
		this.idReport = idReport;
		this.typeReport = typeReport;
		this.petType = petType;
		this.lastlocation = lastlocation;
		this.comments= comments;
		this.isAlert=isAlert;
		this.isResolve=isResolve;
		this.numComments=numComments;
		this.userName=userName;
		
		if(pathPicture!=null){
			if(!pathPicture.trim().equals("")){
				this.pathPicture = pathPicture;
				hasPicture=true;
			}
			else	
				hasPicture=false;
		}
		else 	
			hasPicture=false;

	}

	public String getIdReport() {
		return idReport;
	}

	public int getPetType() {
		return petType;
	}

	public String getLastlocation() {
		return lastlocation;
	}

	public Boolean getHasPicture() {
		return hasPicture;
	}

	public String getPathPicture() {
		return pathPicture;
	}

	public String getComments() {
		return comments;
	}
	
	public int getTypeReport() {
		return typeReport;
	}

	public boolean isAlert() {
		return isAlert;
	}

	public int getNumComments() {
		return numComments;
	}

	public boolean isResolve() {
		return isResolve;
	}

	public String getUserName() {
		return userName;
	}
	
	public void changeAlert(){
		isAlert=!isAlert;
	}
	
}
