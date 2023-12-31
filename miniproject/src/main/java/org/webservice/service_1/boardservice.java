package org.webservice.service_1;

import java.util.List;

import org.webservice.domain.attachfile;
import org.webservice.domain.board;
import org.webservice.domain.boardsearch;

public interface boardservice {
	
	
	//차단 및 차단 해제
	public boolean userban(String userid, String reason,int periods);
	public boolean userbanrelease(String userid);
	
	//게시판 생성 및 삭제
	public void board_register(String boardname, String reguserid, String boardsub);
	public boolean board_delete(String boardname);
	
	//관리자 양도
	public boolean board_aouth(String boardname,String userid);
	
	//게시판의 게시글 관련 서비스
	public board readboard(Long bno);
	public void insertboard(board bd);
	public boolean deleteboard(Long bno);
	public boolean updateboard(board bd);
	public int getlisttotal(boardsearch search);
	public List<board> getList(boardsearch search);
	public List<attachfile> getfilelist(Long bno);
	public void deletefilelist(Long bno);
}
