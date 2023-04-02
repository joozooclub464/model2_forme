package com.koreait.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.koreait.mybatis.SqlMapConfig;

public class JSONDAO {
	//여기에서만 mybatis를 쓰는것임.
	SqlSessionFactory factory = SqlMapConfig.getFactory();
	SqlSession sqlsession;
	
	public JSONDAO() {
		sqlsession = factory.openSession(true);
	}
	public int doSomething() {
		return (Integer)sqlsession.selectOne("Test.ds");
	}
}
