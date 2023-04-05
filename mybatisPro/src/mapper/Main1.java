package mapper;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import mybatisjava.Member;



public class Main1 {

	public static void main(String[] args) {
		SqlSessionFactory sqlMap = initMybatis();
		
		SqlSession session = sqlMap.openSession(); //sql사용
		//select count(*) from member;
		int x = session.selectOne("member.count");
		System.out.println("member 테이블의 레코드 갯수:" + x);
		
		//select * from member
		List<Member> list = session.selectList("member.list");
		for(Member m : list) {
			System.out.println(m);	
		}
		
		System.out.println("member 테이블의 admin 정보 ==========");
		Member mem = session.selectOne("member.selectid","admin");
		System.out.println(mem);
		
		//문제//
		System.out.println("member 테이블에서 이름이 '자'이름 가진 정보 =======");
		list = session.selectList("member.selectname","%자%");
		for(Member m : list) System.out.println(m);
		
		System.out.println("member 테이블에서 이름이 '바'이름 가진 정보 ======");
		list = session.selectList("member.selectname2","바");
		for(Member m : list) System.out.println(m);
		
		System.out.println("이름정보와 성별로 조회하기");
		System.out.println("member 테이블에서 이름이 '바' 이름 가진 여자 정보 ========");
		Map<String,Object> map = new HashMap<>();
		map.put("name", "%바%");
		map.put("gender", 2);
		list = session.selectList("member.selectnamegender",map);
		for(Member m : list) System.out.println(m);
		
		
		
		
	}
	

	
	public static SqlSessionFactory initMybatis() {
		SqlSessionFactory sqlMap = null;
		Reader reader = null;
		
		try {
			reader = Resources.getResourceAsReader("mapper/mybatis-config.xml");
			sqlMap = new SqlSessionFactoryBuilder().build(reader);
			System.out.println("ok config");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return sqlMap;
		
	}
}
