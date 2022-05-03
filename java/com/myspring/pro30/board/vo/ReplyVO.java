package com.myspring.pro30.board.vo;

import org.springframework.stereotype.Component;

@Component("ReplyVO")
public class ReplyVO {
	private String comment;
	private String writer;
	private String writeDate;
	private int commentNO;
	private int totalComments2;
	
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getcommentNO() {
		return commentNO;
	}
	public void setcommentNO(int commentNO) {
		this.commentNO = commentNO;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public int gettotalComments2() {
		return totalComments2;
	}
	public void settotalComments2(int totalComments2) {
		this.totalComments2 = totalComments2;
	}
	public void addComment2() {
		totalComments2++;
	}
	public void deleteComment2() {
		totalComments2--;
	}
	
}
