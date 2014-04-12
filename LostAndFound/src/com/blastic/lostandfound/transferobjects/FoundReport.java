package com.blastic.lostandfound.transferobjects;

public class FoundReport extends Report{

	private static final long serialVersionUID = 6277726849474247205L;

	private int agePet;
	private String colorPet;
	private String actuallyLocation;

	public FoundReport(String idReport, int petType, String lastlocation,
			String pathPicture, String comments, int agePet, String colorPet,
			String actuallyLocation,boolean isAlert,int numComments, boolean isResolve,String userName) {
		
		super(idReport,Report.CAUSE_FOUND,petType,lastlocation,pathPicture,comments,isAlert,numComments,isResolve,userName);
		
		this.agePet = agePet;
		this.colorPet = colorPet;
		this.actuallyLocation = actuallyLocation;
	}
	
	public int getAgePet() {
		return agePet;
	}

	public String getColorPet() {
		return colorPet;
	}

	public String getActuallyLocation() {
		return actuallyLocation;
	}
}
