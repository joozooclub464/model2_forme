package com.koreait.app.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.ActionForward;
import com.koreait.app.user.dao.UserDAO;
import com.koreait.app.user.dao.UserDTO;

public class UserJoinOkAction  {
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception{ //db처리를 위한 메소드
		//db처리를 하기 위해 execute가 밖에서 받아와야할것 
		// -> joinview에서 입력한 input들을 request.getParameter로 꺼냈음.
		// 따라서 execute()는 필수적으로 HttpServletRequest 라는 객체를 매개변수로 가지고 있어야 함
		// 그리고 응답도 해줘야함 -> HttpServletResponse 라는 응답 객체도 매개변수로 가지고 있음.
		
		//다시설명
		//request는 요청이 날아오면 FrontController를 들려서 거기에 요청과 응답할수있는 객체가 담겨있음(doProcess에)
		//걔들을 그대로 넘겨받아야(어디에? UserJoinOkAction에 있는 execute()가) 
		//그래야 담겨온 데이터를 꺼내서 처리할 수 있고 응답도 그 정보를 가지고 할 수 있음.
		//따라서 FrontController에서는 UserJoinOKAction.execute()를 호출하면서 받아온 req와 resp를 그대로 넘겨줌.
		//----------------------------------------------------------------------------------------------
		
		//입력받은 데이터 꺼내려면 인코딩부터해야함.(FrontController에서 해야 한번만 할 수 있음)
		//그리고 데이터 꺼내기
		UserDTO newUser = new UserDTO();
		UserDAO udao = new UserDAO();
		newUser.setUserid(req.getParameter("userid"));
		newUser.setUserpw(req.getParameter("userpw"));
		newUser.setUsername(req.getParameter("username"));
		newUser.setUsergender(req.getParameter("usergender"));
		newUser.setZipcode(req.getParameter("zipcode"));
		newUser.setAddr(req.getParameter("addr"));
		newUser.setAddrdetail(req.getParameter("addrdetail"));
		newUser.setAddretc(req.getParameter("addretc"));
		
		newUser.setHobbystr(req.getParameterValues("userhobby"));
		//newUser라는 객체는 userhobby라는 배열도 세팅이 되어있고 hobbystr이라는 문자열도 세팅되어있음.
		//dao 통해서 db에 넣을거(mybatis)
		//이때 user.xml에서 join할때 hobbystr 꺼내서 씀 -> getter는 hobbystr만 잘 만들어져 있으면 됨 ==> db에 넣는것까진 해결됨
		//로그인때 컬럼명과 같으면 문자열 꺼내와서 세팅.userhobby컬럼에 있는걸 꺼내와서 userhobby필드에 세팅할건데
		//컬럼은 문자열이고 세팅해야할 필드는 배열임.
		//-> 일단 문자열 타입으로 매개변수를 받고(mybatis에서 꺼낼 때 문자열 타입으로 건네줄거니까)	-> 그걸 꺼내서 hobbystr에 집어넣으면 끝
		
		//UserFromtController에서 보낸 path와 어떤 방식인지(redirect/forward)를 알아야 하기 때문에 아래 액션포워드 설정
		ActionForward forward = null;
		if(udao.join(newUser)) {
			//성공했을떄 
			//로그인 뷰 페이지로 이동해야해 + 이동방식
			//ActionForward가 있으면 그 forward에다 생성자를 통해서 만들어줌
			forward = new ActionForward();
			//어떤 방식으로 이동할 것인지(redirect / forward)
			forward.setRedirect(true); //회원가입이므로 리다이렉트임 //그 다음에 path나 이런것들을 세팅해줌
			//어떤 페이지로 이동할 것인지(forward : 앞의 root가 남아있음 / redirect : 재요청이기 때문에  cp 연결) 
			forward.setPath(req.getContextPath()+"/user/UserLogin.us?userid="+req.getParameter("userid"));//가입이 완료되면 여기로 옴->frontcontroller들러서 뷰단 띄워야 함.
							//새로운 재요청이 일어나기 때문에 루트경로를 다시 넣어줘야 함.
							//${cp}는 자바에서는 el문이 먹히지 않기 때문에 req.getContextPath()로 루트경로 넣어줌
		} //실패했을때는 else에 별도로 넣어주지 않고  return forward 해줌 -> 리턴타입 ActionForward로 바꿔주기   
		return forward; //frontcontroller의 "new UserJoinOkAction().execute(req, resp);"이부분으로 날아감
			//이 리턴은 UserFrontController의 newUserJoinOkAction.execute(req,resp)로 날아감 
		
		
	}
}
