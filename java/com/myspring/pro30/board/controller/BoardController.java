package com.myspring.pro30.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro30.board.vo.ReplyVO;


public interface BoardController {
	
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public List<ReplyVO> commentlist(@RequestParam("commentNO") int commentNO,
							@RequestParam("articleNO") int articleNO,
							HttpServletRequest request, HttpServletResponse response) throws Exception;	
	
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest,HttpServletResponse response) throws Exception;
	
	public ResponseEntity addNewReply(@RequestParam("articleNO") int articleNO,
									@RequestParam("comment") String comment,
									HttpServletRequest request,HttpServletResponse response) throws Exception;	
	public ResponseEntity addNewReply2(@RequestParam("articleNO") int articleNO,
			@RequestParam("comment") String comment,
			@RequestParam("commentNO") int commentNO,
			HttpServletRequest request,HttpServletResponse response) throws Exception;	
	
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
			                        HttpServletRequest request, HttpServletResponse response) throws Exception;
	//public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest,  HttpServletResponse response) throws Exception;
	public ResponseEntity  removeArticle(@RequestParam("articleNO") int articleNO,
                              		HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity  removeComment(@RequestParam("commentNO") int commentNO,
			@RequestParam("articleNO") int articleNO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity  removeComment2(@RequestParam("commentNO") int commentNO,
			@RequestParam("commentNO2") int commentNO2,
			@RequestParam("articleNO") int articleNO,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView SearchArticles(@RequestParam("title") String title,
									HttpServletRequest request, HttpServletResponse response) throws Exception;
}
