package mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import mybatisjava.Professor;

/*
 * 1. �������̺� ��ϵ� ���ڵ��� �Ǽ��� ����ϱ�,
 * 2. �������̺� ��ϵ� ��� ������ ����ϱ�
 * 3. ������ 101�� �а��� ���� ������ ����ϱ�
 * 4. ������ ���� �达�� �ð����� ������ ����ϱ�
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
		
		System.out.println("1.professor ���̺��� ���ڵ� ����:" + x);
		System.out.println("2.�������̺� ��ϵ� ��� ������ ����ϱ�");
		List<Professor> list = session.getMapper(ProfessorMapper.class).list();
		for(Professor p : list) System.out.println(p);
		
		System.out.println("3. ������ 101�� �а��� ���� ������ ����ϱ�");
		list = session.getMapper(ProfessorMapper.class).selectdeptno(101);
		for(Professor p : list) System.out.println(p);
		
		System.out.println("4.������ ���� �达�� �ð����� ������ ����ϱ�");	
		Map<String,Object> map = new HashMap<>();
		map.put("name", "��");
		map.put("position", "������");
		list = session.getMapper(ProfessorMapper.class).selectnameposition(map);
		for(Professor p : list) System.out.println(p);
	}

}
