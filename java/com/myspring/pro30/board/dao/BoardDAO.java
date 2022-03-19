package com.myspring.pro30.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30.board.vo.ArticleVO;


public interface BoardDAO {
//	public List selectAllArticlesList() throws DataAccessException;
//	public List selectAllArticlesList() throws Exception;
	public List selectAllArticlesList(Map pagingMap) throws Exception;
//	public int insertNewArticle(Map articleMap) throws DataAccessException;
	public int insertNewArticle(Map articleMap) throws Exception;
	public void insertNewImage(Map articleMap) throws DataAccessException;
//	public List searchArticlesList(String keyword) throws DataAccessException;
	public List searchArticlesList(Map pagingMap, String keyword) throws Exception;
	
	public ArticleVO selectArticle(int articleNO) throws Exception;
	public void updateArticle(Map articleMap) throws DataAccessException;
	public void deleteArticle(int articleNO) throws DataAccessException;
	public List selectImageFileList(int articleNO) throws DataAccessException;
	public int selectNewArticleNO() throws Exception;
	public int count_search_articles(String title) throws Exception;
	public void insertNewReply(Map ReplyMap, String articleNO) throws Exception;
}
