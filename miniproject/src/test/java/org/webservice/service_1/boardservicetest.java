package org.webservice.service_1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.webservice.domain.attachfile;
import org.webservice.domain.auth;
import org.webservice.domain.banuser;
import org.webservice.domain.board;
import org.webservice.domain.boardlist;
import org.webservice.domain.boardsearch;
import org.webservice.mapper.boardmapper;
import org.webservice.mapper.membermapper;
import org.webservice.persis.DataSourceTests;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class boardservicetest {
//board 서비스와 file 서비스를 테스트 하는 코드입니다
	
	@Setter(onMethod_= {@Autowired})
	public boardmapper bmapper;

	//@Test
	public void testmemberinfo() {
		log.info(bmapper.select_boardaouth("main"));
	}
	
	//@Test
	@DisplayName("게시판 생성 테스트")
	public void testregisterboard() {
		boardlist brdlist=new boardlist();
		List<String> mgrlist=new ArrayList<String>(); 
		mgrlist.add("master");
		
		brdlist.setBoardname("second");
		brdlist.setBoardsubject("this is second board");
		brdlist.setReguserid("master");
		brdlist.setManageridlist(mgrlist);
		bmapper.createboard(brdlist);
	}
	
	//@Test
	@DisplayName("게시판 삭제 테스트")
	public void testdeleteaouthboard() {
		bmapper.board_delete("main");
	}
	
	
	//@Test
	@DisplayName("권한양도 테스트")
	public void testaouth() {
		auth ath=new auth();
		ath.setUserid("master");
		ath.setAuth("second");
		bmapper.aouthboard(ath);
	}
	
	//@Test
	@DisplayName("권한삭제 테스트")
	public void testdeleteaouth() {
		auth ath=new auth();
		ath.setUserid(bmapper.select_boardaouth("second"));
		ath.setAuth("second");
		bmapper.deleteaouthboard(ath);
	}
	
	//@Test
	@DisplayName("권한조회 테스트")
	public void testselectaouth() {
		String boarna="main";
		String id=bmapper.select_boardaouth(boarna);
		log.info(id);
	}
	
	//@Test
	@DisplayName("게시물 입력 테스트")
	public void testinsertboard() {
		
		board brd=new board();
		List<attachfile> filelist=new ArrayList<attachfile>();
		brd.setBoardname("second");
		brd.setTitle("test second board");
		brd.setContent("이것은 테스트 용입니다");
		brd.setWriter("user00");
		brd.setAttachlist(filelist);
		bmapper.insertboard(brd);
	}
	
	//@Test
	@DisplayName("게시물 수정 테스트")
	public void testupdateboard() {
		board brd=bmapper.readboard(7L);
		brd.setTitle("수정하는 중인 타이틀입니다");
		brd.setContent("수정되었습니다.");
		brd.setWriter("user00");
		bmapper.updateboard(brd);
		
	}
	
	//@Test
	@DisplayName("게시물 삭제 테스트")
	public void testdeleteboard() {
		
		bmapper.deleteboard(6L);
	}
	
	//@Test
	@DisplayName("유저 차단 테스트")
	public void testuserban() {
		banuser ban=new banuser();
		LocalDate currentDate = LocalDate.now();
		LocalDate endDate=currentDate.plusDays(7);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
		String startformattedDate=currentDate.format(formatter);
		String endformattedDate=endDate.format(formatter);
		
		ban.setUserid("user00");
		ban.setBanreason("this is test");
		ban.setStartdate(startformattedDate);
		ban.setEnddate(endformattedDate);
		bmapper.banusers(ban);
	}
	
	//@Test
	@DisplayName("유처 차단 조회 테스트")
	public void testselectban() {
		log.info(bmapper.banselect("user00"));
	}
	
	//@Test
	@DisplayName("유저 차단 해제 테스트")
	public void testuserbanrelease() {
		String id="user00";
		
		bmapper.banuserrealease(id);
	}
}