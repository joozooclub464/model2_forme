package com.koreait.servlet;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.dao.JSONDAO;

public class GetJSONAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		JSONDAO jdao = new JSONDAO();
		jdao.doSomething();//호출해서 db관련된건 하고 그 외의 로직적인부분 하고.
		
		int result = jdao.doSomething();
		if(result == 5) {
			//JSON 요청
			// localhost:9090/day17/json/GetJSON.jd url을 입력해서 결과 확인.(뷰단 안만들었음)
			JSONArray jarr = new JSONArray();
			String[] rank = {"1","2","3","4","5"};
			String[] name = {"Ajax","웹개발","HTML","CSS","Spring"};
			JSONObject[] in = new JSONObject[5];
			for(int i=0; i < in.length; i++) {
				JSONObject j = new JSONObject();
				j.put("rank", rank[i]);
				j.put("name", name[i]);	
				jarr.add(j);
			}
			JSONObject out = new JSONObject();
			out.put("search_word", jarr);
			//JSON을 만들 때에는 항상 안쪽부터 만들어야함.
			//여기서는 안에꺼 다 만들었으면 array에 담아주고 array에 담았으면 바깥쪽 제이슨 만들면서 key랑 array객체 넣어주고
		
			//response에 대한 인코딩 
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=UTF-8");//응답하는 형태
			//out 객체를 응답해줘야지(response로) json_test.jsp의 xhr.responstText로 받아가지고 파싱해서 쓸 수 있음
			PrintWriter writer = resp.getWriter();
			writer.print(out); //응답
		
		}
		
		
//		ActionForward forward = new ActionForward();
//		forward.setPath(path);//만약 성공했으면 setPath를 이용하여 뭔가 잘못됐다는 뜻으로 써주는것이고
//		forward.setRedirect(isRedirect);//아니라면 성공했다는 뜻으로 써주는것
//		return forward; //세팅되어있는 forward 리턴
		
		return null; //우리는 여기서 json을 쓸것이기 때문에 위의 코드 사용 안함.
	}
	
	
	
	
}
