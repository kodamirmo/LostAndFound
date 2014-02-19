package com.blastic.lostandfound.transferobjects;

public class LostReport extends Report{

	private static final long serialVersionUID = 6658076717693829388L;
	
	private String lostDate;
	private int agePet;
	private String colorPet;
	private String namePet;
		 
	public LostReport(String idReport, int petType, String lastlocation,
			String pathPicture, String comments, String lostDate, int agePet,
			String colorPet, String namePet,
			boolean isAlert,int numComments, boolean isResolve,String userName) {
		
		super(idReport, typeReport, petType, lastlocation, pathPicture, comments,isAlert,numComments,isResolve,userName);
		
		this.lostDate = lostDate;
		this.agePet = agePet;
		this.colorPet = colorPet; 
		this.namePet = namePet;
		super.typeReport=CAUSE_LOST;
	}

	public String getLostDate() {
		return lostDate;
	}


	public int getAgePet() {
		return agePet;
	}


	public String getColorPet() {
		return colorPet;
	}


	public String getNamePet() {
		return namePet;
	}

}
