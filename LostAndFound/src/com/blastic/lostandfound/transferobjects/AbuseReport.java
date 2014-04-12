package com.blastic.lostandfound.transferobjects;

public class AbuseReport extends Report{

	private static final long serialVersionUID = 6533248312973019131L;
	
	private String commentAboutSituation; 

	public AbuseReport(String idReport, int petType, String lastlocation,
			String pathPicture, String comments, String commentAboutSituation,
			boolean isAlert,int numComments, boolean isResolve,String userName) {
		
		super(idReport,Report.CAUSE_ABUSE,petType,lastlocation,pathPicture,comments,isAlert,numComments,isResolve,userName);
		
		this.commentAboutSituation = commentAboutSituation;
	}

	public String getCommentAboutSituation() {
		return commentAboutSituation;
	}

}