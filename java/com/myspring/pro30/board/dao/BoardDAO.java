package com.myspring.pro30.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ReplyVO;


public interface BoardDAO {
	public List selectAllArticlesList(Map pagingMap) throws Exception;
	public List selectAllcommentsList(int commentNO, int articleNO) throws Exception;
	public int insertNewArticle(ArticleVO article) throws Exception;
	public void insertNewImage(Map articleMap) throws DataAccessException;
	public List searchArticlesList(Map pagingMap, String keyword) throws Exception;
	
	public ArticleVO selectArticle(int articleNO) throws Exception;
	public List listComments(int articleNO) throws Exception;
	public void updateArticle(Map articleMap) throws DataAccessException;
	public void deleteArticle(int articleNO) throws DataAccessException;
	public void deleteComment(int articleNO, int commentNO) throws DataAccessException;
	public void deleteComment2(int articleNO, int commentNO, int commentNO2) throws DataAccessException;
	public List selectImageFileList(int articleNO) throws DataAccessException;
	public int totalArticles() throws Exception;
	public int totalComment(String articleNO) throws Exception;
	public int count_search_articles(String title) throws Exception;
	public void insertNewReply(Map ReplyMap, String articleNO) throws Exception;
	public void insertNewReply2(Map ReplyMap, String articleNO, String commentNO) throws Exception;
}
