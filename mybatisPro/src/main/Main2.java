package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import mapper.Main1;
import mybatisjava.Student;

public class Main2 {
		private final static String NS="student.";
		private static Map map = new HashMap();
		//세터게터가 있어서 사용 가능.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SqlSessionFactory sqlmap = Main1.initMybatis();
		SqlSession session = sqlmap.openSession(); //쿼리문 붙이는건 sqlsession으로 함.
		System.out.println("Student 테이블에 레코드 추가하기");
		Student st = new Student();
		st.setStudno(1002);
		st.setName("홍길동");
		st.setGrade(1);
		st.setId("hongkd3");
		st.setJumin("1234561234567");
		st.setMajor1(101);
//		int cnt = session.insert(NS+"insert",st);
//		System.out.println("student 레코드 추가:" + cnt);
//		session.commit();
		
		Student dispst = session.selectOne
				(NS+"selectno",st.getStudno());
		System.out.println(dispst);
		
		 dispst = session.selectOne
				(NS+"selectnomap",9514);
		System.out.println(dispst);
		
		/*
		 * 1002번 학생의 학년을 2학년으로. 몸무게 80, 키 175
		 * 지도교수 1001로 수정하기
		 */
		st.setGrade(2);
		st.setWeight(80);
		st.setHeight(175);
		st.setProfno(1001);
		st.setStudno(1002);
		int cnt = session.update(NS+"update",st);
		System.out.println("student 테이블의 레코드 수정:" + cnt);
		session.commit();
		
		
		System.out.println("1002번 학생 삭제하기");
		cnt = session.delete(NS+"delete",1002);
		System.out.println("student 테이블의 레코드 삭제:" + cnt);
		session.commit();
		
		//키가 180 이상인 학생의 정보 출력하기
		System.out.println("키가 180이상인 학생의 정보 출력하기");
		map.clear();
		map.put("col", "weight");
		map.put("value", 80);
		List<Student>list = session.selectList(NS+"select2",map);
		for(Student s : list) System.out.println(s);
	}

}
