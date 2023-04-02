package com.koreait.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

//config.xml 설정파일을 읽고 Factory를 만들어내는 역할
//아래는 싱글톤으로 만든것
public class SqlMapConfig { 
	private static SqlSessionFactory factory;
	
//	클래스 초기화 블럭, static블럭 (클래스가 처음 로딩될 때 한번만 수행)
	static {
		try {
			String resource = "./com/koreait/mybatis/config.xml"; //참고할 문서 파일이 어디에 있는지 알려줌
			Reader reader = Resources.getResourceAsReader(resource); //리더타입의 리더라는 객체를 만들게. Resources.getResourceAsReader에 우리가 사용하는 설계도 파일(resource)를 넘겨줌 
//					  [		    	건축가		   ]  +  [ 공학자   ]
			factory = new SqlSessionFactoryBuilder().build(reader); 
		} catch (IOException ioe) {
			System.out.println("초기화 문제 발생: " + ioe); //19번째줄에서 잘못될 수 있기 때문에 try-catch예외처리 해줌
		}

	}

	public static SqlSessionFactory getFactory() {
		return factory;
	}
	
}
