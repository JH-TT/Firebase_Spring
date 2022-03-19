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
import com.myspring.pro30.board.vo.ImageVO;


@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl  implements BoardService{
	@Autowired
	BoardDAO boardDAO;
	
//	public List<ArticleVO> listArticles() throws Exception{
//		List<ArticleVO> articlesList =  boardDAO.selectAllArticlesList();
//        return articlesList;
//	}
	public Map listArticles(Map pagingMap) throws Exception{
		Map articlesMap = new HashMap();
		List<ArticleVO> articlesList =  boardDAO.selectAllArticlesList(pagingMap);
		int toArticles = boardDAO.selectNewArticleNO();
		articlesMap.put("articlesList", articlesList);
		articlesMap.put("toArticles", toArticles);
		return articlesMap;
	}
	
	// 검색 기능
//	@Override
//	public List<ArticleVO> searchArticles(String title) throws Exception {
//		List<ArticleVO> articlesList = boardDAO.searchArticlesList(title);
//		return articlesList;
//	}
	
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
	
	// 다중 이미지 추가하기
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		int articleNO = boardDAO.insertNewArticle(articleMap);
		articleMap.put("articleNO", articleNO);
//		boardDAO.insertNewImage(articleMap);
		return articleNO;
	}
	
	
	 //���� �̹��� �߰��ϱ�
	/*
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		int articleNO = boardDAO.insertNewArticle(articleMap);
		articleMap.put("articleNO", articleNO);
		boardDAO.insertNewImage(articleMap);
		return articleNO;
	}
	*/
	/*
	//���� ���� ���̱�
	@Override
	public Map viewArticle(int articleNO) throws Exception {
		Map articleMap = new HashMap();
		ArticleVO articleVO = boardDAO.selectArticle(articleNO);
		List<ImageVO> imageFileList = boardDAO.selectImageFileList(articleNO);
		articleMap.put("article", articleVO);
		articleMap.put("imageFileList", imageFileList);
		return articleMap;
	}
   */
	
	
	 //���� ���� ���̱�
	@Override
	public ArticleVO viewArticle(int articleNO) throws Exception {
		ArticleVO articleVO = boardDAO.selectArticle(articleNO);
		return articleVO;
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
	public void addNewReply(Map ReplyMap, String articleNO) throws Exception {
		boardDAO.insertNewReply(ReplyMap, articleNO);
	}
	
}
