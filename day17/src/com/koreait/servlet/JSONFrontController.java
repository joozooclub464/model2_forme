package com.koreait.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.ActionForward;

//frontController에서는 길을 나눠주기만 하는것
//@WebServlet("*.jd") //request-uri가 "*.jd"임.
public class JSONFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		doProcess(req, resp);
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws Exception  {
		//front-controller의 역할을 여기서 하는것과 다름없음.
		String requestURI = req.getRequestURI(); //req로 요청한 uri좀 보여줘//localhost:9090/day17/json/GetJSON.jd
		String contextPath = req.getContextPath();//루트경로를 잘라냄 //localhost:9090/day17
		String command = requestURI.substring(contextPath.length());//잘라낸 루트경로만큼 앞에서 잘라낸 후 뒤에 경로만 command에 담았음 // /json/GetJSON.jd
		ActionForward forward = null; //아래 스위치문 하나하나에서 페이지 이동시켜주는 코드를 쓰려면 너무 길어지니까 수행결과를 forward객체에 받아버림
									  //그러면 어떤 케이스에 들어왔던 상관없이 맨 마지막에 forward에 담겨있는대로 이동하면 됨
		
		switch(command) {
		case "/json/GetJSON.jd":
			//어떤 요청이 들어오던 하는 일은 다르지만 다른 클래스로 이동해서 그 안에 있는 메소드 수행 이 틀은 똑같다.
			try {
				forward = new GetJSONAction().execute(req,resp);//수행된 결과인ActionForward타입의 객체가 이 자리에 리턴됨
			} catch (Exception e) {
				System.out.println("GetJSON :" +e);
			} //객체 만든것.
			break;
		case "":
			
			break;

		}
		
		//따라서 forward에 무언가 담겨있다면 정상적으로 Action에 execute를 쓰고 그 처리결과를 forward로 받아왔다는 뜻
		if(forward != null) {
			if(forward.isRedirect()) {
				//Redirect
				resp.sendRedirect(forward.getPath()); //목적지는 forward 객체에 담겨있으니 forward에서 path를 getPath로 꺼내서 그쪽으로 이동
			}else {
				//Forward
				RequestDispatcher disp = req.getRequestDispatcher(forward.getPath());
				disp.forward(req, resp);
				
			}
		}
		
	}
	
}
