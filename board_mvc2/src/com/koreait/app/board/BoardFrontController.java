package com.koreait.app.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.ActionForward;
import com.koreait.app.user.CheckIdOkAction;
import com.koreait.app.user.UserJoinOkAction;
import com.koreait.app.user.UserLoginOkAction;

public class BoardFrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath(); 
		String command = requestURI.substring(contextPath.length());
		ActionForward forward = null; 
		
		switch(command) { 
		case "/board/BoardList.bo":
			try {
				forward = new BoardListAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("BoardList : " + e);
				//로그인 하면 BoardList.bo 띄워주기 위해 이동함.=> 따라서 이 액션에서는 무조건 jsp파일 써줘야함.그래야지 뷰를 띄울 수 있기 때문에
			}
			break;
		case "/board/BoardView.bo":
			try {
				forward = new BoardViewAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("BoardView :" +e);
			}
			break;
		case "/board/BoardWrite.bo":
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/app/board/boardwrite.jsp");
			break;
		case "/board/BoardWriteOk.bo":
			try {
				forward = new BoardWriteOkAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("boardWriteOk :" + e);
			}
			break;
		case "/board/BoardModify.bo":
			try {
				forward = new BoardModifyAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("BoardModify :"+ e);
			}
			break;
		case "/board/BoardModifyOk.bo" :
			try {
				forward = new BoardModifyOkAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("BoardModifyOk :" +e);
			}
			break;
		case "/board/FileDownload.bo":
			try {
				forward = new FileDownloadAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("FileDownload :" +e);
			}
		}
		
		//일괄처리
		
		if(forward != null) { 
			if(forward.isRedirect()) { 
				resp.sendRedirect(forward.getPath());
			}
			else {
				RequestDispatcher disp = req.getRequestDispatcher(forward.getPath()); 
				disp.forward(req, resp);
			}
		}
		
		
	}
	
}
