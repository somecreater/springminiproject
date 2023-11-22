package org.webservice.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class boardpage {
	  private String boardname;
	  private int startPage;
	  private int endPage;
	  private boolean prev, next;

	  private int total;
	  private boardsearch srh;
	  
	  public boardpage(boardsearch srh,int total) {
		  this.srh=srh;
		  this.total=total;
		  this.endPage = (int) (Math.ceil(srh.getPageNum() / 10.0)) * 10;

		    this.startPage = this.endPage - 9;

		    int realEnd = (int) (Math.ceil((total * 1.0) / srh.getAmount()));

		    if (realEnd < this.endPage) {
		      this.endPage = realEnd;
		    }

		    this.prev = this.startPage > 1;

		    this.next = this.endPage < realEnd;
		    this.boardname=srh.getBoardname();
		  
	  }
}
