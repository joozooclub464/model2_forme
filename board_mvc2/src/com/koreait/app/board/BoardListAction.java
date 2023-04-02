package com.koreait.app.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;

public class BoardListAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		BoardDAO bdao = new BoardDAO();
//		리스트에서 보여달라고 요청한 페이지
		String temp = req.getParameter("page"); //처음 들어오면 temp가 있을수도 있고 없을수도 있음 
//		처음으로 리스트에 들어오는 중이라면 요청하고 있는 page번호가 없으므로 그때는 무조건 1번 페이지를 띄워줘야 한다.
		int page = temp == null?1:Integer.parseInt("temp"); //temp가 null인지 확인 
		
//		한 페이지에 보여줄 게시글의 개수
		//null일때는 1페이지고, null이 아니면 그걸 int로 형변환 해주면 그게 페이지가 됨.
		int pageSize = 10; //하나의 페이지에 10개씩 보여줄것.
		
//		전체 게시글의 개수
		int totalCnt = bdao.getBoardCnt();
		
//		보여줘야 되는 마지막 게시글의 rownum
		int endRow = page*pageSize;
		
//		보여줘야 하는 첫번째 게시글의 rownum
		int startRow = endRow-pageSize+1;
		
//		아래쪽 페이징 처리의 보여지는 첫 번째 페이지 번호
		int startPage = ((page-1)/pageSize)*pageSize+1 ;
//		아래쪽 페이징 처리의 보여져야 하는 마지막 페이지 번호를 연산으로 구한것
		int endPage = startPage + pageSize-1;
//		전체 개수를 기반으로 가장 마지막 페이지 번호
		int totalPage = (totalCnt-1)/pageSize + 1;

//		가장 마지막 페이지 번호보다 연산으로 구해진 endPage가 더 큰 경우도 있다.(허구의 페이지 번호)
//		그때는 endPage를 가장 마지막 페이지 번호로 바꿔준다.
		endPage = endPage>totalPage?totalPage:endPage;
		
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("totalCnt", totalCnt);
		req.setAttribute("page", page);
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		req.setAttribute("boardList", bdao.getBoardList(startRow,endRow));
		
		
		
		ActionForward forward = new ActionForward();
		//해당 페이지에 띄워주어야 할 Board 객체들을 들고가기 때문에 
		//request가 초기화되면 안된다. 무조건  forward 방식!!!
		forward.setRedirect(false);
		forward.setPath("/app/board/boardlist.jsp"); //jsp로 뷰를 띄우는 이유(로그인 후 BoardList.bo를 띄워주기 위해 이동. 무조건 띄우기 때문에.)
		return null;
	}
}
