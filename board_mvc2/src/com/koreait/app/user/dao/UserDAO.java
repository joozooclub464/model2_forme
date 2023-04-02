package com.koreait.app.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;

//import com.koreait.dto.UserDTO;
import com.koreait.mybatis.SqlMapConfig;

public class UserDAO {
	SqlSession sqlsession; //우리가 사용할 생수병 받아오기.
	
	public UserDAO() {
		sqlsession = SqlMapConfig.getFactory().openSession(true); //true로 설정시 오토커밋 
	//UserDAO라는 생성자가 호출이 됐다면 바로 sqlsession에 생수병을 찍어서 넣어줘.
	}
	
	
	public boolean join(UserDTO newUser) {
		int result = sqlsession.insert("User.join",newUser);
		return result == 1;
		
	}
	public boolean checkId(String userid) {
		int result = 1;
		result = sqlsession.selectOne("User.checkId",userid); 
		//User 네임스페이스 안에 있는 checkId라는 id의 쿼리문을 쓸게.
		//그리고 받아온 값을 userid로 데이터를 넘겨줄게.
		return result == 0;
	}
	public UserDTO login(String userid,String userpw) {
		UserDTO result = null;
		HashMap<String, String> datas = new HashMap<String, String>();
		
		datas.put("userid", userid);
		datas.put("user_pw", userpw);
		result = sqlsession.selectOne("User.login", datas);
		
		return result ;
	}
}
















