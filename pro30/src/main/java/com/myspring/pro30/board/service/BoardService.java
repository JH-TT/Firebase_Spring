package com.myspring.pro30.board.service;

import java.util.List;
import java.util.Map;

import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ReplyVO;

public interface BoardService {
	public Map listArticles(Map pagingMap) throws Exception;
	public List listcomments(int commentNO, int articleNO) throws Exception;
	public List listmessages(String id) throws Exception;
	public int addNewArticle(ArticleVO article) throws Exception;
	public ArticleVO viewArticle(int articleNO) throws Exception;
	public List viewComments(int articleNO) throws Exception;
	public ReplyVO viewFixed(int articleNO) throws Exception;
	public void modArticle(Map articleMap) throws Exception;
	public void removeArticle(int articleNO) throws Exception;
	public void removeComment(int articleNO, int commentNO) throws Exception;
	public void removeComment2(int articleNO, int commentNO, int commentNO2) throws Exception;
	public int fixComment(int articleNO, int commentNO, int commentNO2) throws Exception;
	public Map searchArticles(Map pagingMap, String keyword) throws Exception;
	public void addNewReply(Map ReplyMap, Map MessageMap, String articleNO) throws Exception;
	public void addNewReply2(Map ReplyMap, Map MessageMap, String articleNO, String commentNO) throws Exception;
}
