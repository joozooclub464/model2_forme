package com.koreait.app.board.dao;

public class FileDTO {
	private String systemname;
	private String orgname;
	private int boardnum;
	
	public FileDTO() {

	}
	
	public String getSystemname() {
		return systemname;
	}
	public void setSystemname(String systemname) {
		this.systemname = systemname;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public int getBoardnum() {
		return boardnum;
	}
	public void setBoardnum(int boardnum) {
		this.boardnum = boardnum;
	}
	
	
	
}
