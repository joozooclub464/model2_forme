package com.koreait.app.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.ActionForward;

public class UserFrontController extends HttpServlet{ //HttpServlet 상속시켜주고

	//기본으로 써둬야 하는 시리얼버전아이디
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//UserFrontController의 목적은 
		//어떤 것이든 user에 관련된것이면 여기로 와서 switch문 혹은 if문으로 흐름 나누어주는것(길 나누기)
		//User라는 곳에 올 때 때에 따라서 get으로 올 수도 있고 post로 올 수도 있음. 따라서 전송방식에 따라 get,post 둘 다 호출될 수 있고,
		//그렇게 되면 길 나누는 코드를 get에도 써야하고 post에도 써야 함 ->똑같은 코드가 두번 쓰임.
		// ==>doGet,doPost에서 doProcess를 호출하고  doProcess에 길 나누는 코드를 넣기
		
		// doPost안에서 호출하기전에 인코딩 해주면 인코딩을 한번만 할 수 있음
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		doProcess(req,resp);
		
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//길 나누는 코드
		String requestURI = req.getRequestURI(); //board_mvc2/user/UserJoin.us
		String contextPath = req.getContextPath(); //board_mvc2
		String command = requestURI.substring(contextPath.length());// /user/UserJoin.us //substring : 문자열 잘라내는 코드(contextPath의 길이만큼 잘라냄)
		ActionForward forward = null; //프론트컨트롤러에서 join.us에 들어왔던 login.us에 들어왔던 다 모르겠고 ActionForward 타입의 forward를 만들어 둠
		
		switch(command) { //command로 가르면 모듈이 어떻게 설정되어 있더라도 잘 돌아감
		case "/user/UserJoin.us": //requestURI에 contextPath도 포함되어 있는것이기 때문에 거기서 contextPath를 잘라내면 "/user/UserJoin.us"이 나온다
			forward = new ActionForward(); //join.us에 들어왔으면 forward에다가 새로운 객체  만들고
			forward.setPath("/app/user/joinview.jsp"); //setPath로 "/app/user/joinview.jsp" 야  //(joinviewjsp.servlet 실행해 와 같은 뜻임)
			forward.setRedirect(false); //setRedirect가 true면 redirect방식이고 false면 forward방식
			break;
		case "/user/UserLogin.us":
			forward = new ActionForward();
			forward.setPath("/app/user/loginview.jsp");
			forward.setRedirect(false); //forward방식으로 이동해야하는 정보를 갖고 있음(setRedirect가 false)
			break;
		case "/user/UserJoinOk.us":
			try { //UserJoinOkAction 에서 throws했기 때문에(어떤 에러가 발생할지 몰라서) 여기서 try-catch 로 잡아주면 됨
				new UserJoinOkAction().execute(req, resp);
			} catch (Exception e) {
				System.out.println("UserJoinOk : " + e);
			} //따라서 FrontController에서는 UserJoinOKAction.execute()를 호출하면서 받아온 req와 resp를 그대로 넘겨줌.
			break;
		case "/user/CheckIdOk.us":
			try {
				new CheckIdOkAction().execute(req,resp); //null이기 때문에 페이지 이동은 없음.
			} catch (Exception e) {
				System.out.println("CheckIdOk : " + e);
			}
			break;
		case "/user/UserLoginOk.us":
			//단순 페이지 이동이 아니라 어떤 처리가 있으면 무조건 Action 만들어야함.
			try {
				forward = new UserLoginOkAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("UserLoginOk : " +e);
			}
			break;
		case "/user/UserLogoutOk.us":
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/user/UserLogin.us");
			req.getSession().removeAttribute("loginUser");
			break;
		}
		
		//일괄처리
		//어디인지, 어떤 방식인지는 몰라도 그냥 forward 객체에 담겨있는 정보대로 페이지를 이동시키는 코드
		if(forward != null) { //혹시 forward가 null이 아니라면 즉 정상적으로 무언가가 실행이 된다면 ->정보들이 담겨있다.
			if(forward.isRedirect()) { //그 forward의 리다이렉트가 혹시 true니?
				resp.sendRedirect(forward.getPath()); //리다이렉트로 보내야함
			}
			else { //그 forward의 리다이렉트가 혹시 false 니?
				RequestDispatcher disp = req.getRequestDispatcher(forward.getPath()); //포워드로 보내야함
				disp.forward(req, resp);
			}
		}
		
		
	}
	
	
	
	
	
}
