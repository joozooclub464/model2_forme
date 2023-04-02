package com.koreait.app.board;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.DefaultBoundedRangeModel;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;
import com.koreait.app.board.dao.BoardDAO;
import com.koreait.app.board.dao.BoardDTO;
import com.koreait.app.board.dao.FileDAO;
import com.koreait.app.board.dao.FileDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardModifyOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//file1, file2	: 실제 파일 데이터
		//filename		: 새롭게 업로드 한 파일의 이름들 혹은 기존의 파일의 이름
		
		String saveFolder = "C:\\web_file";
		BoardDAO bdao = new BoardDAO();
		int size = 5*1024*1024;
		MultipartRequest multi = new MultipartRequest(req, saveFolder,size,"UTF-8",new DefaultFileRenamePolicy());
		BoardDTO board = new BoardDTO();
		
		String boardtitle = multi.getParameter("boardtitle");
		String boardcontents = multi.getParameter("boardcontents");
		int boardnum = Integer.parseInt(multi.getParameter("boardnum"));
		
		board.setBoardtitle(boardtitle);
		board.setBoardcontents(boardcontents);
		board.setBoardnum(boardnum);
		ActionForward forward = new ActionForward();
		forward.setRedirect(true); //insert,delete,update있으므로 포워드는 트루
		
		if(bdao.updateBoard(board)) {
			FileDAO fdao = new FileDAO();
			//방금 수정을 통해 올린 파일의 시스템네임 배열
			String[] filename = {
					multi.getFilesystemName("file1"), 
					multi.getFilesystemName("file2")
			};
			
			//방금 수정을 통해 올린 파일의 원래 이름 배열
			String[] orgname = {
					multi.getOriginalFileName("file1"),
					multi.getOriginalFileName("file2")
			};
			
			//원래 이 글에 등록되어 있던 파일의 정보들
			List<FileDTO> files = fdao.getFiles(boardnum);
			
			//새롭게 이 게시판이 가지고 있어야 하는 파일명들
			String[] newFilenames = multi.getParameterValues("filename");
					//newFilenames는 파일의 개수가 아님.
			
			//이 게시판이 실제로 가지고 있어야 하는 파일의 개수를 세고
			int cnt = 0;
			for(int i=0;i<newFilenames.length;i++) {
				if(newFilenames[i] != null && !newFilenames[i].contentEquals("")) {
					cnt++;
				}
			}
			
			//그 개수만큼 반복하되
			//0개라면 (수정없음, 파일을 올린것이 없음)
			//	DB, 실제 파일 저장되는 경로에 수정 없이 진행
			//1개라면 (기존에 존재하던 하나 수정, 새롭게 하나 추가)
			//	수정한 경우라면 기존파일 삭제 후 추가, 새롭게 추가한다면 그냥 파일만 추가
			//2개라면(기존에 존재하던것 전부 수정, 전부 추가)
			//	수정한 경우라면 기존파일 전부 삭제 후 추가, 파일만 두개 추가
			for(int i=0; i<cnt; i++) {
				if(filename[i] == null) {
					//실제 게시판이 가져야 하는 개수만큼 반복을 하며 날라온 파일데이터를 검사하는것
					//이 if문에 들어왔다면 가져가야하는 개수 안쪽이지만, 날라온 파일데이터는 없다는 뜻이므로
					//수정을 하지 않았다는 뜻					
				} else {
					//무언가 수정이 일어났다는 뜻, i가 0일떄 들어왔다면 file1을 수정한것, i==1 일때면 file2를 수정한것
					if(files.size()>=cnt) {
						//원래 존재하던 FileDTO의 리스트인 files에서 i번째껏을 새롭게 수정해야 하므로 
						//리스트의 i번째에 담겨있던 DTO에 있는 정보로 실제 파일을 객체로 불러오기
						File file = new File(saveFolder,files.get(i).getSystemname());
						//실제 위치에 그 파일이 존재한다면
						if(file.exists()) {
							//해당파일 삭제
							file.delete();
						}
						//실제 파일이 삭제되었기 때문에 DB상에서도 정보를 지워주어야 한다.
						fdao.deleteFileByName(files.get(i).getSystemname());
					}
					//파일정보 DB에 등록
					FileDTO fdto = new FileDTO();
					fdto.setBoardnum(boardnum);//현재의boardnum
					fdto.setSystemname(filename[i]); //새롭게 날린 이름
					fdto.setOrgname(orgname[i]);
					fdao.insertFile(fdto);
				}
			}
			forward.setPath(req.getContextPath() +"/board/BoardView.bo?boardnum="+boardnum);
		} else {
			forward.setPath(req.getContextPath()+"/board/BoardList.bo?update=false");
		}
		return forward;
	}
	
}
