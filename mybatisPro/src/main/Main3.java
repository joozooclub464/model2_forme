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
	private final static String NS = "student2."; //NS�� ����ϱ� �빮��. student �ڿ� '.' ������ ����.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SqlSessionFactory sqlmap = Main1.initMybatis();
		SqlSession session = sqlmap.openSession(); 
		
		System.out.println("�л� ��ü ���ڵ� ��ȸ�ϱ�");
		List<Student> list = session.selectList(NS+"select1");
		for(Student s : list) System.out.println(s);
		
		map.clear();
		map.put("grade",1);
		System.out.println("1�г� �л� ���ڵ� ��ȸ�ϱ�");
		list = session.selectList(NS+"select4",map);
		for(Student s : list) System.out.println(s);
	
		map.clear();
		map.put("height",180);
		System.out.println("1�л� �� Ű�� 180 �̻��� �л� ���ڵ� ��ȸ�ϱ�");
		list = session.selectList(NS+"select4",map);
		for(Student s : list) System.out.println(s);
		
		map.clear();
		map.put("grade",2);
		map.put("height",180);
		System.out.println("2�г� �л� �� Ű�� 180 �̻��� �л� ���ڵ�");
		list = session.selectList(NS+"select4",map);
		for(Student s : list) System.out.println(s);
		
		
		System.out.println("101, 201 �а��� ���� �л��� ���ڵ� ��ȸ�ϱ�");
		List<Integer> mlist = Arrays.asList(101,201);
		map.clear();
		map.put("column", "deptno1");
		map.put("datas", mlist);
		list = session.selectList(NS+"select5",map);
		for(Student s : list) System.out.println(s);
		
		System.out.println("�����԰� 70, 72, 82�� �л��� ���ڵ� ��ȸ�ϱ�");
		mlist = Arrays.asList(70,72,82);
		map.clear();
		map.put("column", "weight");
		map.put("datas", mlist);
		list = session.selectList(NS+"select5",map);
		for(Student s : list) System.out.println(s);
				
		
		
		
	}

}
