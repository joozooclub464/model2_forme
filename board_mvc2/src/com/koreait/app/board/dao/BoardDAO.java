package com.koreait.app.board.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.koreait.mybatis.SqlMapConfig;

public class BoardDAO {
	SqlSession sqlsession;
	
	public BoardDAO() {
		sqlsession = SqlMapConfig.getFactory().openSession(true); //true로 설정시 오토커밋 
	
	}
	
	public int getBoardCnt() {
		return sqlsession.selectOne("Board.getBoardCnt");

	}

	public List<BoardDTO> getBoardList(int startRow, int endRow) {
		
		HashMap<String , Integer> datas = new HashMap<String, Integer>();
		datas.put("startRow", startRow);
		datas.put("endRow", endRow);
		
//		쿼리문의 수행 결과가 여러 행일 경우 selectOne으로 검색해오면 맨 윗줄로 객체 한개 만들어서 리턴
//		selectList로 검색해 오면 그 모든 줄들로 객체를 만들고 그것들이 담겨있는 List를 리턴
		List<BoardDTO> result = sqlsession.selectList("Board.getBoardList", datas); //행 하나하나마다 result 타입으로 만들어서 돌려줌
		
		return result;
	}

	public BoardDTO getDetail(int boardnum) {
		return sqlsession.selectOne("Board.getDetail",boardnum);
		
	}

	public void updateReadCount(int boardnum) {
		//조회수는 가끔 하나씩 올라가지 않더라고 운영에 지장이 없으므로 if문 없이 void로..
		sqlsession.update("Board.updateReadCount",boardnum);
		
		
	}

	public boolean insertBoard(BoardDTO board) {
		return 1 == sqlsession.insert("Board.insertBoard",board); //성공하면 성공한 행 수가 리턴됨 성공: 1
	}

	public int getLastNum(String userid) {
		return sqlsession.selectOne("Board.getLastNum",userid);
	}

	public boolean updateBoard(BoardDTO board) {
		return 1 == sqlsession.update("Board.updateBoard",board);
	}
}
