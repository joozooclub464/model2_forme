package com.koreait.app.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.user.dao.UserDAO;
import com.koreait.app.user.dao.UserDTO;

public class UserLoginOkAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	UserDAO udao = new UserDAO();
	String userid = req.getParameter("userid");
	String userpw = req.getParameter("userpw");
	//내장객체 사용해서 세션 만들어줌(새롭게 만들면 x,기존에 있는것 받아와야함->세션도 쿠키처럼 request에 있음) 
	HttpSession session = req.getSession();
	
	ActionForward forward = null;
	UserDTO loginUser = udao.login(userid, userpw);
	if(loginUser != null) {
		//메인뷰로 보냄(게시판 리스트로 보낼거) -> 시스템에 변화가 없으므로 포워드로 보냄
		session.setAttribute("loginUser", loginUser); //세션세팅
		forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/board/BoardList.bo"); //새로운 프론트컨트롤러를 만들고 거기는 게시판에 관련된 프론트 컨트롤러 역할을 함 
	} else {
		forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/user/UserLogin.us?login=false");		
	}
	return forward;
	}
}
