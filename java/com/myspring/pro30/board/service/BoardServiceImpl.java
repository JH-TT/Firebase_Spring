package com.myspring.pro30.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro30.board.dao.BoardDAO;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ReplyVO;


@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl  implements BoardService{
	@Autowired
	BoardDAO boardDAO;
	
	public Map listArticles(Map pagingMap) throws Exception{
		Map articlesMap = new HashMap();
		List<ArticleVO> articlesList =  boardDAO.selectAllArticlesList(pagingMap);
		int toArticles = boardDAO.totalArticles();
		articlesMap.put("articlesList", articlesList);
		articlesMap.put("toArticles", toArticles);
		return articlesMap;
	}
	
	public List listcomments(int commentNO, int articleNO) throws Exception{
		Map commentsMap = new HashMap();
		List<ReplyVO> commentsList =  boardDAO.selectAllcommentsList(commentNO, articleNO);
		return commentsList;
	}
	
	// 검색 기능
	@Override
	public Map searchArticles(Map pagingMap, String keyword) throws Exception {
		Map articlesMap = new HashMap();
		List<ArticleVO> articlesList = boardDAO.searchArticlesList(pagingMap, keyword);
		int toArticles = boardDAO.count_search_articles(keyword);
		articlesMap.put("articlesList", articlesList);
		articlesMap.put("toArticles", toArticles);
		
		return articlesMap;
	}
	
	@Override
	public int addNewArticle(ArticleVO article) throws Exception{
		int articleNO = boardDAO.insertNewArticle(article);
		article.setArticleNO(articleNO);
//		boardDAO.insertNewImage(articleMap);
		return articleNO;
	}
	
	@Override
	public ArticleVO viewArticle(int articleNO) throws Exception {
		ArticleVO articleVO = boardDAO.selectArticle(articleNO);
		return articleVO;
	}
	
	@Override
	public List viewComments(int articleNO) throws Exception{
		List<ReplyVO> comments = boardDAO.listComments(articleNO);
		return comments;
	}
	
	
	@Override
	public void modArticle(Map articleMap) throws Exception {
		boardDAO.updateArticle(articleMap);
	}
	
	@Override
	public void removeArticle(int articleNO) throws Exception {
		boardDAO.deleteArticle(articleNO);
	}

	@Override
	public void removeComment(int articleNO, int commentNO) throws Exception {
		boardDAO.deleteComment(articleNO, commentNO);
	}
	
	@Override
	public void removeComment2(int articleNO, int commentNO, int commentNO2) throws Exception {
		boardDAO.deleteComment2(articleNO, commentNO, commentNO2);
	}

	@Override
	public void addNewReply(Map ReplyMap, String articleNO) throws Exception {
		boardDAO.insertNewReply(ReplyMap, articleNO);
	}
	
	@Override
	public void addNewReply2(Map ReplyMap, String articleNO, String commentNO) throws Exception {
		boardDAO.insertNewReply2(ReplyMap, articleNO, commentNO);
	}
	
}
