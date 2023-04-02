package com.koreait.action;

public class ActionForward {
	//여기에 두가지의 정보를 가지고 있는 필드를 줌
	private boolean isRedirect; //리다이렉트인지
	private String path; //이동할 경로
	
	
	
	public ActionForward() {
		// 기본생성자 하나 만들어두고
	}
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
	
}
