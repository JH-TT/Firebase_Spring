package com.myspring.pro30.board.vo;

import org.springframework.stereotype.Component;

@Component("ReplyVO")
public class ReplyVO {
	private String content;
	private String id;
	private String  writeDate;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	
	
}
