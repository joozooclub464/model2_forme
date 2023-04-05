package mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import mybatisjava.Professor;

/*
 * 1. 교수테이블에 등록된 레코드의 건수를 출력하기,
 * 2. 교수테이블에 등록된 모든 정보를 출력하기
 * 3. 교수중 101번 학과의 교수 정보를 출력하기
 * 4. 교수중 성이 김씨인 시간강사 정보를 출력하기
 */
public class Main5 {
	private final static String NS="professor.";
	private static Map map = new HashMap();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int x = 0;
		SqlSessionFactory sqlmap = Main1.initMybatis();
		SqlSession session = sqlmap.openSession();
		x = (Integer)session.getMapper(ProfessorMapper.class).count();
		
		System.out.println("1.professor 테이블의 레코드 갯수:" + x);
		System.out.println("2.교수테이블에 등록된 모든 정보를 출력하기");
		List<Professor> list = session.getMapper(ProfessorMapper.class).list();
		for(Professor p : list) System.out.println(p);
		
		System.out.println("3. 교수중 101번 학과의 교수 정보를 출력하기");
		list = session.getMapper(ProfessorMapper.class).selectdeptno(101);
		for(Professor p : list) System.out.println(p);
		
		System.out.println("4.교수중 성이 김씨인 시간강사 정보를 출력하기");	
		Map<String,Object> map = new HashMap<>();
		map.put("name", "김");
		map.put("position", "조교수");
		list = session.getMapper(ProfessorMapper.class).selectnameposition(map);
		for(Professor p : list) System.out.println(p);
	}

}
