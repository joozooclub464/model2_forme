package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import mapper.Main1;
import mybatisjava.Student;

public class Main3 {
	private static Map map = new HashMap();
	private final static String NS = "student2."; //NS는 상수니까 대문자. student 뒤에 '.' 빼먹지 말것.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SqlSessionFactory sqlmap = Main1.initMybatis();
		SqlSession session = sqlmap.openSession(); 
		
		System.out.println("학생 전체 레코드 조회하기");
		List<Student> list = session.selectList(NS+"select1");
		for(Student s : list) System.out.println(s);
		
		map.clear();
		map.put("grade",1);
		System.out.println("1학년 학생 레코드 조회하기");
		list = session.selectList(NS+"select4",map);
		for(Student s : list) System.out.println(s);
	
		map.clear();
		map.put("height",180);
		System.out.println("1학생 중 키가 180 이상인 학생 레코드 조회하기");
		list = session.selectList(NS+"select4",map);
		for(Student s : list) System.out.println(s);
		
		map.clear();
		map.put("grade",2);
		map.put("height",180);
		System.out.println("2학년 학생 중 키가 180 이상인 학생 레코드");
		list = session.selectList(NS+"select4",map);
		for(Student s : list) System.out.println(s);
		
		
		System.out.println("101, 201 학과에 속한 학생의 레코드 조회하기");
		List<Integer> mlist = Arrays.asList(101,201);
		map.clear();
		map.put("column", "deptno1");
		map.put("datas", mlist);
		list = session.selectList(NS+"select5",map);
		for(Student s : list) System.out.println(s);
		
		System.out.println("몸무게가 70, 72, 82인 학생의 레코드 조회하기");
		mlist = Arrays.asList(70,72,82);
		map.clear();
		map.put("column", "weight");
		map.put("datas", mlist);
		list = session.selectList(NS+"select5",map);
		for(Student s : list) System.out.println(s);
				
		
		
		
	}

}
