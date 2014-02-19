package com.blastic.lostandfound.transferobjects;

public class AccidentReport extends Report{

	private static final long serialVersionUID = 3072573095922276081L;

	public AccidentReport(String idReport, int petType, String lastlocation,
			String pathPicture,String comments,boolean isAlert,int numComments, boolean isResolve,String userName) {
		
		super(idReport, typeReport, petType, lastlocation, pathPicture, comments,isAlert,numComments,isResolve,userName);

		super.typeReport=CAUSE_ACCIDENT;
	}

}
