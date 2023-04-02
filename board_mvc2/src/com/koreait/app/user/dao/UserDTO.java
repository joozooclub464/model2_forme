package com.koreait.app.user.dao;

public class UserDTO {
	private String userid;
	private String userpw;
	private String username;
	private String usergender;
	private String zipcode;
	private String addr;
	private String addrdetail;
	private String addretc;
	private String[] userhobby;
	private String hobbystr;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsergender() {
		return usergender;
	}
	public void setUsergender(String usergender) {
		this.usergender = usergender;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddrdetail() {
		return addrdetail;
	}
	public void setAddrdetail(String addrdetail) {
		this.addrdetail = addrdetail;
	}
	public String getAddretc() {
		return addretc;
	}
	public void setAddretc(String addretc) {
		this.addretc = addretc;
	}
	
	
	public String[] getUserhobby() {
		return userhobby;
	}
	public void setUserhobby(String hobbystr) { //mybatis에서 꺼낼때 문자열 타입으로 꺼낼거니까 String으로 받아야 함ㅌ
		this.userhobby = hobbystr.split(",");
		this.hobbystr = hobbystr; //두개의 필드를 운영하므로 둘 다 넣어줘야함
		
	}
	
	public String getHobbystr() {
		return hobbystr;
	}
	public void setHobbystr(String[] userhobby) {
		this.userhobby = userhobby;
		String hobbystr = "";
		if(userhobby.length>0) {
			hobbystr = userhobby[0];
			for(int i=0;i<userhobby.length;i++) {
				hobbystr+=","+userhobby[i]; //여기에 누적연결
			}
			
		}
		this.hobbystr = hobbystr;
	}
//DB : 문자열
//입력받은건 : 배열
	
	
	
}
