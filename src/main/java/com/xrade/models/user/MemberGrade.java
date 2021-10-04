package com.xrade.models.user;

public enum MemberGrade {
	Beginner("Beginner");
	
	public final String label;
	private MemberGrade(String grade){
		this.label = grade;
	}

}
