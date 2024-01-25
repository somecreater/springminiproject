package org.webservice.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.webservice.domain.attachfile;
import org.webservice.domain.board;
import org.webservice.domain.boardlist;
import org.webservice.domain.boardpage;
import org.webservice.domain.boardsearch;
import org.webservice.service_1.boardservice;
import org.webservice.service_1.commentservice;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@AllArgsConstructor
@Log4j
public class boardcontroller {

	public boardservice bservice;
	
	@GetMapping("/readBoard")
	public void readBoard(@RequestParam("bno") Long bno,@ModelAttribute("search") boardsearch search, Model model) {
		
		board board=bservice.readBoard(bno);
		
		model.addAttribute("board", board);
		model.addAttribute("search",search);
	}
	
	@GetMapping("/listboard")
	public void listboard(boardsearch search, Model model) {
		
		List<board> boardList=bservice.getList(search);
		
		boardpage page=new boardpage(search, bservice.getlisttotal(search));
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("page", page);
		
	}
	
	//@PreAuthorize("authenticated()")
	@PostMapping("/saveBoard")
	public String saveBoard(board brd, boardsearch search, RedirectAttributes rttr) {
		bservice.insertboard(brd);
		rttr.addFlashAttribute("result","success");
		return "redirect:/board/listboard"+search.getListLink();
	}	
	//@PreAuthorize("authenticated()")
	@GetMapping("/createBoard")
	public void createBoard(boardsearch search, Model model) {
		List<String> boardname=bservice.select_boardlist();
		model.addAttribute("boardlist", boardname);
		model.addAttribute("searchcondition", "type: "+search.getType()+", keyword: "+search.getKeyword());
	}
	
	@PostMapping("/uploadTest")
	public void uploadTest(MultipartFile[] uploadFile, Model model) {
		for(MultipartFile multipartFile:uploadFile) {
			log.info(multipartFile.getOriginalFilename());
			log.info(multipartFile.getSize());
			log.info(multipartFile.getName());
		}
	}
	
	//@PreAuthorize("authenticated()")
	@PostMapping("createBoardlistaction")
	public String createBoardlistaction(boardlist brdli, boardsearch search, RedirectAttributes rttr) {
		
		List<String> ex_brdlist=bservice.select_boardlist();
		
		for(String brdlist:ex_brdlist) {			
			if(brdli.getBoardname().equals(brdlist)) {
				rttr.addAttribute("result", "error");
				return "redirect:/board/listboard"+search.getListLink();
			}
		}
		bservice.board_register(brdli.getBoardname(), brdli.getReguserid(), brdli.getBoardsubject());
		rttr.addAttribute("result", "success");
		return "redirect:/board/listboard"+search.getListLink();
	}
	//@PreAuthorize("authenticated()")
	@GetMapping("createBoardlist")
	public void createBoardlist(Model model) {
		
	}
	
	//@PreAuthorize("principal.username == #board.writer")
	@PostMapping("/updatesaveBoard")
	public String updatesaveBoard(board brd, boardsearch search,RedirectAttributes rttr) {
		if(bservice.updateboard(brd))
			rttr.addFlashAttribute("result", "success");
		
		return "redirect:/board/listboard"+search.getListLink();
	}
	
	//@PreAuthorize("principal.username == #board.writer")
	@GetMapping("/updateBoard")
	public void updateBoard(Long bno, boardsearch search, Model model){
		board brd=bservice.readBoard(bno);
		List<String> boardname=bservice.select_boardlist();
		model.addAttribute("boardlist", boardname);
		model.addAttribute("board", brd);
	}
	
	//@PreAuthorize("principal.username == #board.writer")
	@PostMapping("/removeBoard")
	public String removeBoard(@RequestParam final Long bno, boardsearch search, RedirectAttributes rttr, String writer) {
		
		if(bservice.deleteboard(bno)) {
			//파일 직접 삭제 함수
			Filedelete(bservice.getfilelist(bno));
			bservice.deletefilelist(bno);
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/listboard"+search.getListLink();
	}
	
	private void Filedelete(List<attachfile> filelist) {
		String firstfilelink="D:\\server\\temp\\";
		Path fpath,sumfpath;
		if(filelist==null||filelist.size()==0) {
			return;
		}
		
		for(attachfile rmfile:filelist) {
			fpath=Paths.get(firstfilelink+rmfile.getUploadPath()+"\\"+rmfile.getUuid()+"_"+rmfile.getFileName());
			try {
				Files.deleteIfExists(fpath);
			} catch (IOException e) {
				log.info("삭제하려는 파일이 없거나 실패하였습니다.");
			}
			if(rmfile.isImage()) {
				sumfpath=Paths.get(firstfilelink+rmfile.getUploadPath()+"\\"+"thum_"+rmfile.getUuid()+"_"+rmfile.getFileName());
				try {
					Files.delete(sumfpath);
				} catch (IOException e) {
					log.info("삭제하려는 썸네일이 없거나 실패하였습니다.");
				}
				
			}
		}
	}

	private void Fileupload(attachfile file) {
		String firstfilelink="D:\\server\\temp";
		Calendar celendar=Calendar.getInstance();
		String year=Integer.toString(celendar.get(Calendar.YEAR));
		String month=Integer.toString(celendar.get(Calendar.MONTH)+1);
		String day=Integer.toString(celendar.get(Calendar.DATE));
		String hour = Integer.toString(celendar.get(Calendar.HOUR)); 
		String min = Integer.toString(celendar.get(Calendar.MINUTE));
		String sec = Integer.toString(celendar.get(Calendar.SECOND));
		
		Path fpath;
		
		
	}
	
}
