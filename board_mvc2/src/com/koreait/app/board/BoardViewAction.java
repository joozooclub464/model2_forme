package com.koreait.app.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.dao.BoardDTO;
import com.koreait.app.board.dao.FileDAO;
import com.koreait.app.user.dao.UserDTO;


public class BoardViewAction implements Action{
	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		BoardDAO bdao = new BoardDAO();
		int boardnum = Integer.parseInt(req.getParameter("boardnum"));
		
		HttpSession session = req.getSession();
		ActionForward forward = new ActionForward();
		if(session.getAttribute("loginUser") == null) { //세션이 만료되어 로그인이 풀렸을 경우 처리
			//로그아웃이 풀렸다는 뜻.
			forward.setRedirect(false);
			forward.setPath("/user/UserLogin.us");  
		}
		String userid = ((UserDTO)session.getAttribute("loginUser")).getUserid();
		
		BoardDTO board = bdao.getDetail(boardnum);
		if(board.getUserid().equals(userid)) {
			bdao.updateReadCount(boardnum); //지금 상태로는 조회수는 수정이 됐는데 board객체는 여전히 이전 조회수를 가지고 있음.
			board.setReadcount(board.getReadcount()+1); //따라서 조회수 1 추가
		}
		
		
		FileDAO fdao = new FileDAO();
		req.setAttribute("board", board);
		req.setAttribute("files", fdao.getFiles(boardnum));
		forward.setRedirect(false);
		forward.setPath("/app/board/boardview.jsp");
		
		return forward;
	}
	
	
}
