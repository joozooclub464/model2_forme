package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import mybatisjava.Professor;

public interface ProfessorMapper {
	@Select("select count(*) from professor")
	int count();
	@Select("select * from professor")
	List<Professor> list();
	@Select("select * from professor where deptno = #{value}")
	List<Professor> selectdeptno(int depnto);
	@Select("select * from professor where name like '${name}%'"
			+ "and position = #{position}")
	List<Professor> selectnameposition(Map map);

//ProfessorMapper.xml을 위와 같이 바꿈.
	

	@Select({"<script>",
			"select * from professor",
			"<trim prefix = 'where' prefixOverrides = 'AND || OR'>",
				"<if test = 'deptno != null'> and deptno = #{deptno} </if>",
				"<if test = 'position != null'> and position = #{position} </if>",
				"<if test = 'profno != null'> and profno = #{profno} </if>",
				"<if test = 'datas != null'> and deptno in"
					+"<foreach collection = 'datas' item = 'd'  " 
					+" separator = ',' open = '(' close = ')'> #{d} </foreach>"
				+ "</if>",
			"</trim>",
			"</script>"})
	List<Professor> select(Map<String,Object>map);
}