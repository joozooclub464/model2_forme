package com.koreait.app.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.dao.BoardDTO;
import com.koreait.app.board.dao.FileDAO;

public class BoardModifyAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		BoardDAO bdao = new BoardDAO();
		FileDAO fdao = new FileDAO();
		
		int boardnum = Integer.parseInt(req.getParameter("boardnum"));
		BoardDTO board = bdao.getDetail(boardnum);
		req.setAttribute("board", board);
		req.setAttribute("files", fdao.getFiles(boardnum));
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false); //request.setAttribute가 보이면 무조건 false(세팅 열심히 해두고 지울 일 없으면...)
		forward.setPath("/app/board/boardmodify.jsp");
		
		return forward;
	}
	
	
}
