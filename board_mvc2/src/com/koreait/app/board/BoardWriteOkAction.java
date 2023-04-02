package com.koreait.app.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.dao.BoardDTO;
import com.koreait.app.board.dao.FileDAO;
import com.koreait.app.board.dao.FileDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardWriteOkAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		BoardDAO bdao = new BoardDAO();
		
		//파일이 저장될 경로
		String saveFolder = "C:\\web_file";		
		//저장될 파일의 크기
		int size = 1024*1024*5; //->5MB  /1024:1KB 
		//cos
		//form에서 enctype을 multipart/form-data 로 보냈다면 필요한 객체
		MultipartRequest multi = new MultipartRequest(req, saveFolder, size, "UTF-8",new DefaultFileRenamePolicy());		
			//multi라는 객체는 new로 생성하는 순간 이 파일들이 저 "C:\\web_file" 경로에 저장됨
		
		boolean fcheck1 = false;
		boolean fcheck2 = false;
		
		//<input type="file">중에 name이 file1인 태그로 올린 파일이 있다면, 그 파일이 폴더에 저장되어 있는 이름
		String filename1 = multi.getFilesystemName("file1"); //input type의 name으로 찾는것.
		if(filename1 == null) {
			fcheck1 = true; //사용자가 파일이 없는 게시글을 작성한다면 파일 업로드가 제대로 됐는지 체크할 필요가 없기 때문에 true임
		}
		//파일을 올릴 때 사용자가 올렸던 이름(다운로드시에는 이 이름으로 다운로드 되게 해야함)
		String orgname1 = multi.getOriginalFileName("file1");
		
		String filename2 = multi.getFilesystemName("file2"); //input type의 name으로 찾는것.
		if(filename2 == null) {
			fcheck2 = true;
		}
		String orgname2 = multi.getOriginalFileName("file2");
				
		if(!fcheck1) { //만약에 fcheck1가 false라면
			fcheck1 = true; //사용자가 파일을 올렸다는 뜻이니까 db에 넣을것 -> 넣는것 성공하면 true 대입 
		}
		
		
		String boardtitle = multi.getParameter("boardtitle");
		String boardcontents = multi.getParameter("boardcontents");
		String userid = multi.getParameter("userid");
		
		BoardDTO board = new BoardDTO();
		board.setBoardtitle(boardtitle);
		board.setBoardcontents(boardcontents);
		board.setUserid(userid);

		int boardnum = 0; //내림차순으로 검색해서 가장 처음에 뜨는 것이 이 사람이 마지막으로 쓴 게시글
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		if(bdao.insertBoard(board)) {
			boardnum = bdao.getLastNum(userid);
			FileDAO fdao = new FileDAO();
			
			if(!fcheck1) { //file1이 있었어? (있었으면 false로 해줬기 때문에 !이 붙음)
//				file1의 정보를 TEST_FILE 테이블에 꽂아넣기
//				성공했다면 fcheck1 = true;
				FileDTO file = new FileDTO();
				file.setSystemname(filename1);
				file.setOrgname(orgname1);
				file.setBoardnum(boardnum);
				//DTO라는건 어떤 파일의 정보만 갖고있을 뿐이지 실제 파일은 아님
				
				fcheck1 = fdao.insertFile(file); //이 부분 통째로가 db에 추가했는지 실패했는지임.
				//ie) fcheck1이 true라는건 파일이 없었거나, 파일이 있었는데 정상적으로 등록했거나 둘 중에 하나임.
			}
			if(!fcheck2) {
				FileDTO file = new FileDTO();
				file.setSystemname(filename2);
				file.setOrgname(orgname2);
				file.setBoardnum(boardnum);
				//DTO라는건 어떤 파일의 정보만 갖고있을 뿐이지 실제 파일은 아님
				
				fcheck2 = fdao.insertFile(file);
			}
			//fcheck1, fcheck2 가 true 라는 뜻
			//1. 원래 파일이 존재하지 않았음
			//2. 파일이 존재했고, 위의 DB처리까지 완벽하게 성공했음
			//위가 모두 성공했을때 아래의 board쓰기를 해주는것임.
			if(fcheck1 && fcheck2) {
//				req.setAttribute("write", "true"); //redirect true이기 때문에 여기에 세팅해놔도 날아가지 않음.(파라미터로 넣어야함)
				forward.setPath(req.getContextPath()+"/board/BoardView.bo?write=true&boardnum="+boardnum);				
				return forward;
			} //board쓰기는 성공했는데 파일 업로드는 실패하면 여기로 옴
				//board에 썼던 정보를(db에 등록했던 게시글) 지워주는것도 해야함.
				//bdao.deleteBoard(boardnum);				
		}
		//위의 if문에 들어가지 못하면 여기까지 반드시 오게됨.
		//ie 어디로가든 무조건 실패기때문에 forward.setPath 해줘야 함
		forward.setPath(req.getContextPath()+"/board/BoardList.bo?write=false");
		return forward;
	}
	
}
