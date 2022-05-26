package com.myspring.pro30.board.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("articleVO")
public class ArticleVO {
	private int  level;
	private int articleNO;
	private int parentNO;
	private int totalComments;
	private int fixedComment; 
	private String title;
	private String content;
	private String imageFileName;
	private String originalFileName;
	private String id;
	private String  writeDate;
	private ReplyVO reply;
	
	public ArticleVO() {
		System.out.println("ArticleVO 생성");
	}
	
	// 댓글관련 start
	public int gettotalComments() {
		return totalComments;
	}
	
	public void settotalComments(int totalComments) {
		this.totalComments = totalComments;
	}
	
	public void addComment() {
		totalComments++;
	}
	
	public void deleteComment() {
		totalComments--;
	}
	// 댓글관련 end
	

	public int getArticleNO() {
		return articleNO;
	}

	public void setArticleNO(int articleNO) {
		this.articleNO = articleNO;
	}

	public int getParentNO() {
		return parentNO;
	}

	public void setParentNO(int parentNO) {
		this.parentNO = parentNO;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageFileName() {
		try {
			if (imageFileName != null && imageFileName.length() != 0) {
				imageFileName = URLDecoder.decode(imageFileName, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		try {
			if(imageFileName!= null && imageFileName.length()!=0) {
				this.imageFileName = URLEncoder.encode(imageFileName,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String getOriginalFileName() {
		try {
			if (imageFileName != null && imageFileName.length() != 0) {
				imageFileName = URLDecoder.decode(imageFileName, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return imageFileName;
	}
	
	public void setOriginalFileName(String originalFileName) {
		try {
			if(imageFileName!= null && imageFileName.length()!=0) {
				this.imageFileName = URLEncoder.encode(imageFileName,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public int getFixedComment() {
		return fixedComment;
	}

	public void setFixedComment(int fixedComment) {
		this.fixedComment = fixedComment;
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

	public void setReply(ReplyVO reply) {
		this.reply = reply;
	}
	
	public ReplyVO getReply() {
		return reply;
	}
	
}
