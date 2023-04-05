package mapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import mybatisjava.Professor;

public class Main7ProfessorDynamicInter {
	private final static String NS="professor.";
	private static Map map = new HashMap();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SqlSessionFactory sqlmap = Main1.initMybatis();
		SqlSession session = sqlmap.openSession();
		
		System.out.println("1.모든 교수 정보 조회하기");
		List<Professor> list = session.getMapper(ProfessorMapper.class).select(null);
		for(Professor s : list) System.out.println(s);
		
		System.out.println("2. 101학과 교수 정보 조회하기");
		Map<String, Object> map = new HashMap<>();
		map.put("deptno", 101);
		list = session.getMapper(ProfessorMapper.class).select(map);
		for(Professor s : list) System.out.println(s);
		
		System.out.println("3. 교수 번호가 1001번인 교수 정보 조회하기");
		map.clear();
		map.put("profno", 1001);
		list = session.getMapper(ProfessorMapper.class).select(map);
		for(Professor s : list) System.out.println(s);
		
		System.out.println("4. 정교수 정보 조회하기");
		map.clear();
		map.put("position", "정교수");
		list = session.getMapper(ProfessorMapper.class).select(map);
		for(Professor s : list) System.out.println(s);
		
		System.out.println("5. 101학과의 정교수 정보 조회하기");
		map.clear();
		map.put("deptno", 101);
		map.put("position", "정교수");
		list = session.getMapper(ProfessorMapper.class).select(map);
		for(Professor s : list) System.out.println(s);
		
		System.out.println("6. 101, 201학과의 교수 정보 조회하기");
		map.clear();
		map.put("datas", Arrays.asList(101,201));
		list = session.getMapper(ProfessorMapper.class).select(map);
		for(Professor s : list) System.out.println(s);
		
		System.out.println("7. 101,201 학과 조 교수 정보 조회하기");
		map.clear();
		map.put("datas", Arrays.asList(101,201));
		map.put("position", "조교수");
		list = session.getMapper(ProfessorMapper.class).select(map);
		for(Professor s : list) System.out.println(s);
	
		
		
	}

}
