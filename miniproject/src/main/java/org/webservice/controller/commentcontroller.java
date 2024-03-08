package org.webservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.webservice.domain.boardsearch;
import org.webservice.domain.comment;
import org.webservice.domain.commentpage;
import org.webservice.service_1.boardservice;
import org.webservice.service_1.commentservice;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@AllArgsConstructor
@RequestMapping("/comment/*")
@Log4j
public class commentcontroller {
	
	
	boardservice bservice;
	
	commentservice cservice;
	
	//댓글 INSERT를 수행한다.
	@PreAuthorize("authenticated()")
	@PostMapping("/insertcomment")	
	public String insertcomment(comment cmt, RedirectAttributes rttr) {
		cservice.registercomment(cmt);
		rttr.addFlashAttribute("result", "success");
		return "redirect:/board/readBoard?bno="+cmt.getBno();
	}

	//댓글 DELETE를 수행한다.
	//@PreAuthorize("principal.username == #board.writer")
	@PostMapping("/deletecomment")
	@ResponseBody
	public Map<String,String> deletecomment(@RequestParam Long rno) {
	    Map<String, String> response = new HashMap<>();
		if(cservice.deletecomment(rno)==1) {
			response.put("result", "success");
		}else {
			response.put("result", "failure");
		}
		return response;
		
	}
	
	//댓글 READ를 수행한다.
	@GetMapping("/readComment")
	@ResponseBody
	public Map<String,Object> readcomment(@RequestParam Long rno){
		comment cmt=cservice.getcomment(rno);
		Map<String,Object> resultMap=new HashMap<>();
		resultMap.put("comment", cmt);
		
		return resultMap;
	}
	
	//댓글 UPDATE를 수행한다.
	@PostMapping("/updatecomment")
	public String updatecomment(comment cmt, RedirectAttributes rttr) {
		if(cservice.updatecomment(cmt)==1) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/readBoard?bno="+cmt.getBno();
	}
	
	//댓글 list를 읽어온다.
	@GetMapping("/readcommentlist")
	@ResponseBody
	public Map<String,Object> readcommentlist(@RequestParam Long bno, @RequestParam int pagenum) {
		boardsearch search=new boardsearch(pagenum,10);
		List<comment> cmtlist=cservice.getcmtlist(search, bno);
		commentpage cmtpage=new commentpage(cmtlist.size(), cmtlist);
		int cmtcnt=cservice.gettotalcommentcnt(bno);
		
		Map<String,Object> resultMap=new HashMap<>();
		resultMap.put("cmtpage", cmtpage);
		resultMap.put("cmtcnt",cmtcnt);
		return resultMap;
	}
	
}
