package com.myspring.pro30.board.vo;

import org.springframework.stereotype.Component;

@Component("MessageVO")
public class MessageVO {
	String send_id;
	String receive_id;
	String content;
	String alertdate;
	int MessageNO;
	int totalMessages;
	int articleNO;
	
	public int getMessageNO() {
		return MessageNO;
	}
	public void setMessageNO(int messageNO) {
		MessageNO = messageNO;
	}
	public int getArticleNO() {
		return articleNO;
	}
	public void setArticleNO(int articleNO) {
		this.articleNO = articleNO;
	}
	
	public String getSend_id() {
		return send_id;
	}
	public void setSend_id(String send_id) {
		this.send_id = send_id;
	}
	public String getReceive_id() {
		return receive_id;
	}
	public void setReceive_id(String receive_id) {
		this.receive_id = receive_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTotalMessages() {
		return totalMessages;
	}
	public void setTotalMessages(int totalMessages) {
		this.totalMessages = totalMessages;
	}
	
	public String getAlertdate() {
		return alertdate;
	}
	public void setAlertdate(String alertdate) {
		this.alertdate = alertdate;
	}
	public void addMessage() {
		this.totalMessages++;
	}
	public void deleteMessage() {
		this.totalMessages--;
	}
}
