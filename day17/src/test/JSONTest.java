package test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class JSONTest {

	public static void main(String[] args) {
		//날아온 json을 꺼내오면 text임.(text기반이기 때문)
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
		
		//위의 객체를 json형태의 텍스트로 바꿀것.
		String data = out.toJSONString();
		System.out.println(data);
			//문자열로 날라왔다면
			//자바에서는 제이슨으로 써야 함 => 파서
		
		JSONParser p = JSONParser(); //jsonsimple.parser로 임포트
		try {
			JSONObject json = (JSONObject)p.parse(data);
			JSONArray in_result = (JSONArray)json.get("search_word"); //배열(업캐스팅객체)
			for(int i=0; i<in_result.size(); i++) {
				JSONObject j = (JSONObject)in_result.get(i);
				System.out.println(i+1+"번째 내부 json");
				System.out.println("rank :"+(String)j.get("rank"));
				System.out.println("name: "+(String)j.get("name"));
			}
		} catch (ParseException e) {
			System.out.println("JSON 파싱 실패");
		}
		
		
		
		
		
	}
}
