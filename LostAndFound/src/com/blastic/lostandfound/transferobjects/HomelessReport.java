package com.blastic.lostandfound.transferobjects;

public class HomelessReport extends Report{

	private static final long serialVersionUID = 4093595522325763809L;

	public HomelessReport(String idReport, int petType, String lastlocation,
			String pathPicture,String comments,
			boolean isAlert,int numComments, boolean isResolve,String userName) {
		
		super(idReport,Report.CAUSE_HOMELESS,petType,lastlocation,pathPicture,comments,isAlert,numComments,isResolve,userName);
	}

}
