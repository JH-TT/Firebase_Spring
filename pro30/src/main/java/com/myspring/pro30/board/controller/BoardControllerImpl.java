package com.myspring.pro30.board.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro30.board.service.BoardService;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.MessageVO;
import com.myspring.pro30.board.vo.ReplyVO;
import com.myspring.pro30.member.vo.MemberVO;


@Controller("boardController")
public class BoardControllerImpl  implements BoardController{
	private static final String ARTICLE_IMAGE_REPO = "C:\\board\\article_image";
	@Autowired
	private BoardService boardService;
	@Autowired
	private ArticleVO articleVO;
	@Autowired
	private ReplyVO replyVO;
	@Autowired
	private MessageVO messageVO;
	
	@Override
	@RequestMapping(value= "/board/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		String _section = request.getParameter("section");
		String _pageNum = request.getParameter("pageNum");
		int section = Integer.parseInt(((_section == null) ? "1" : _section));
		int pageNum = Integer.parseInt(((_section == null) ? "1" : _pageNum));
		
		Map<String, Integer> pagingMap = new HashMap<String, Integer>();
		pagingMap.put("section", section);
		pagingMap.put("pageNum", pageNum);
		
		Map articlesMap = boardService.listArticles(pagingMap);
		articlesMap.put("section", section);
		articlesMap.put("pageNum", pageNum);
		request.setAttribute("articlesMap", articlesMap);
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("articlesMap", articlesMap);
		return mav;
	}
	
	@Override
	@RequestMapping(value="/board/messagelist", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List Messagelist(@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<MessageVO> messagesList = boardService.listmessages(id);		
		return messagesList;
	}
	
	@Override
	@RequestMapping(value="/board/commentlist", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<ReplyVO> commentlist(@RequestParam("commentNO") int commentNO,
							@RequestParam("articleNO") int articleNO,
							HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ReplyVO> commentsList = boardService.listcomments(commentNO, articleNO);		
		
		return commentsList;
	}
	
	
	
	
	//?????? ??????
	@Override
	@RequestMapping(value="/board/comment.do", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity addNewReply(@RequestParam("articleNO") int articleNO,
									@RequestParam("comment") String comment,
									@RequestParam(value = "message_id", required=false) String message_id,
									@RequestParam(value = "send_id", required=false) String send_id,
									 HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		Map<String,Object> ReplyMap = new HashMap<String, Object>();
		Map<String,Object> MessageMap = new HashMap<String, Object>();		
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");		
		String writer = memberVO.getId();

		ReplyMap.put("comment", comment);
		ReplyMap.put("writer", writer);
		ReplyMap.put("totalComments2", 0);
		ReplyMap.put("isdeleted", 0);
		ReplyMap.put("fixed", 0);
		
		String content;
		content = send_id + "?????? ????????? ???????????????.";	
//			System.out.println(content);
		MessageMap.put("send_id", send_id);
		MessageMap.put("receive_id", message_id);
		MessageMap.put("content", content);
		MessageMap.put("articleNO", articleNO);
				
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	    try {
			boardService.addNewReply(ReplyMap, MessageMap, String.valueOf(articleNO));
			message = "<script>";
			message += " alert('????????? ?????????????????????.');";
			message += " location.href='" + request.getContextPath() + "/board/viewArticle.do?articleNO="
					+ articleNO + "';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    }catch(Exception e) {
	      message = "<script>";
		  message += " alert('????????? ?????????????????? ????????? ?????? ????????? ?????????.');";
		  message += " location.href='"+ request.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
		  message +=" </script>";
	      resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    }
		
		return resEnt;
	}
	
	// ?????????
	@Override
	@RequestMapping(value="/board/comment2.do", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity addNewReply2(@RequestParam("articleNO") int articleNO,
										@RequestParam("comment") String comment,
										@RequestParam("commentNO") int commentNO,
										@RequestParam(value = "commentid", required=false) String commentid,
										HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		Map<String,Object> ReplyMap = new HashMap<String, Object>();
		Map<String,Object> MessageMap = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String writer = memberVO.getId();
//		System.out.println(comment);
//		System.out.println(writer);
//		System.out.println(articleNO);
		ReplyMap.put("comment", comment);
		ReplyMap.put("writer", writer);
		ReplyMap.put("totalComments2", 0);
		ReplyMap.put("isdeleted", 0);
		
		String content;
		content = writer + "?????? ???????????? ???????????????.";	
		MessageMap.put("send_id", writer);
		MessageMap.put("receive_id", commentid);
		MessageMap.put("content", content);
		MessageMap.put("articleNO", articleNO);
		
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			boardService.addNewReply2(ReplyMap, MessageMap, String.valueOf(articleNO), String.valueOf(commentNO));
			message = "<script>";
			message += " alert('????????? ?????????????????????.');";
			message += " location.href='" + request.getContextPath() + "/board/viewArticle.do?articleNO="
					+ articleNO + "';";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e) {
			message = "<script>";
			message += " alert('????????? ?????????????????? ????????? ?????? ????????? ?????????.');";
			message += " location.href='"+ request.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
			message +=" </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}
		
		return resEnt;
	}
	
	
	// ?????? ??????
	@Override
	@RequestMapping(value="/board/searchArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView SearchArticles(@RequestParam("title") String title, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		String _section = request.getParameter("section");
		String _pageNum = request.getParameter("pageNum");
		int section = Integer.parseInt(((_section == null) ? "1" : _section));
		int pageNum = Integer.parseInt(((_section == null) ? "1" : _pageNum));
		
		Map<String, Integer> pagingMap = new HashMap<String, Integer>();
		pagingMap.put("section", section);
		pagingMap.put("pageNum", pageNum);
		
		Map articlesMap = boardService.searchArticles(pagingMap, title);
		articlesMap.put("section", section);
		articlesMap.put("pageNum", pageNum);
		articlesMap.put("title", title);
		request.setAttribute("articlesMap", articlesMap);
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("articlesMap", articlesMap);
		return mav;
	}
	
	//?????? ????????? ??? ????????????
	@Override
	@RequestMapping(value="/board/addNewArticle.do" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest, 
	HttpServletResponse response) throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		
		Map<String,Object> articleMap = new HashMap<String, Object>();
		ArticleVO article = new ArticleVO();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			if(name.equals("title")) {
				article.setTitle(value);				
			}
			else if(name.equals("content")) {
				article.setContent(value);
			}
			
//			articleMap.put(name,value);
		}
		
		String imageFileName= upload(multipartRequest);
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String id = memberVO.getId();
		String parentNO = (String)session.getAttribute("parentNO");
		if(parentNO == null) {
			article.setParentNO(0);
		} else {
			article.setParentNO(Integer.valueOf(parentNO));
		}
		article.setId(id);
		article.setImageFileName(imageFileName);
		article.settotalComments(0);
//		articleMap.put("parentNO", (parentNO == null ? 0 : parentNO));
//		articleMap.put("id", id);
//		articleMap.put("imageFileName", imageFileName);
//		articleMap.put("totalcomments", 0);
		
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
//			int articleNO = boardService.addNewArticle(articleMap);
			int articleNO = boardService.addNewArticle(article);
			if(imageFileName!=null && imageFileName.length()!=0) {
				File srcFile = new 
				File(ARTICLE_IMAGE_REPO+ "\\" + "temp"+ "\\" + imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
				FileUtils.moveFileToDirectory(srcFile, destDir,true);
			}
	
			message = "<script>";
			message += " alert('????????? ?????????????????????');";
			message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do'; ";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e) {
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			srcFile.delete();
			
			message = " <script>";
			message +=" alert('????????? ?????????????????? ?????? ??? ?????? ????????? ?????????.');";
			message +=" location.href='"+multipartRequest.getContextPath()+"/board/articleForm.do'; ";
			message +=" </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	//????????? ????????? ????????????
	@RequestMapping(value="/board/viewArticle.do" ,method = RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		articleVO=boardService.viewArticle(articleNO);
		ReplyVO fixed_comment = boardService.viewFixed(articleNO);
		List<ReplyVO> replyVO = boardService.viewComments(articleNO);	
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("article", articleVO);
		mav.addObject("comments", replyVO);
		mav.addObject("fixed_comment", fixed_comment);
		return mav;
	}
	
  //??? ??? ????????? ?????? ??????
  @RequestMapping(value="/board/modArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest,  
    HttpServletResponse response) throws Exception{
    multipartRequest.setCharacterEncoding("utf-8");
	Map<String,Object> articleMap = new HashMap<String, Object>();
	Enumeration enu=multipartRequest.getParameterNames();
	while(enu.hasMoreElements()){
		String name=(String)enu.nextElement();
		String value=multipartRequest.getParameter(name);
		articleMap.put(name,value);
	}
	
	String imageFileName= upload(multipartRequest);
	articleMap.put("imageFileName", imageFileName);
	String articleNO=(String)articleMap.get("articleNO");
	articleMap.put("articleNO", Integer.valueOf(articleNO));
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
    try {
       boardService.modArticle(articleMap);
       if(imageFileName!=null && imageFileName.length()!=0) {
         File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
         File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
         FileUtils.moveFileToDirectory(srcFile, destDir, true);
         
         String originalFileName = (String)articleMap.get("originalFileName");
         File oldFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+originalFileName);
         oldFile.delete();
       }
       message = "<script>";
	   message += " alert('??????????????????.');";
	   message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
	   message +=" </script>";
       resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
    }catch(Exception e) {
      File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
      srcFile.delete();
      message = "<script>";
	  message += " alert('????????? ?????????????????? ????????? ?????? ????????? ?????????.');";
	  message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
	  message +=" </script>";
      resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
    }
    return resEnt;
  }
  
  @Override
  @RequestMapping(value="/board/removeArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity  removeArticle(@RequestParam("articleNO") int articleNO,
                              HttpServletRequest request, HttpServletResponse response) throws Exception{
	response.setContentType("text/html; charset=UTF-8");
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	try {
		boardService.removeArticle(articleNO);
		File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
		FileUtils.deleteDirectory(destDir);
		
		message = "<script>";
		message += " alert('?????? ?????? ?????????????????????');";
		message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	       
	}catch(Exception e) {
		message = "<script>";
		message += " alert('????????? ????????? ??????????????????.?????? ????????? ?????????.');";
		message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    e.printStackTrace();
	}
	return resEnt;
  }  

  @Override
  @RequestMapping(value="/board/removeComment.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity  removeComment(@RequestParam("commentNO") int commentNO,
		  							   @RequestParam("articleNO") int articleNO,
		  							   HttpServletRequest request, HttpServletResponse response) throws Exception{
	  response.setContentType("text/html; charset=UTF-8");
	  String message;
	  ResponseEntity resEnt=null;
	  HttpHeaders responseHeaders = new HttpHeaders();
	  responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	  try {
		  boardService.removeComment(articleNO, commentNO);
		  message = "<script>";
		  message += " alert('?????? ????????? ?????????????????????');";
		  message += " location.href='"+request.getContextPath()+"/board/viewArticle.do?articleNO="+ articleNO + "#CM';";		  
		  message +=" </script>";
		  resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		  
	  }catch(Exception e) {
		  message = "<script>";
		  message += " alert('????????? ????????? ??????????????????.?????? ????????? ?????????.');";
		  message += " location.href='"+request.getContextPath()+"/board/viewArticle.do?articleNO="+ articleNO + "';";
		  message +=" </script>";
		  resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		  e.printStackTrace();
	  }
	  return resEnt;
  }  
  
  @Override
  @RequestMapping(value="/board/removeComment2.do", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity removeComment2(@RequestParam("commentNO") int commentNO,
			   							@RequestParam("commentNO2") int commentNO2,
			   							@RequestParam("articleNO") int articleNO,
			   							HttpServletRequest request, HttpServletResponse response) throws Exception{
	  response.setContentType("text/html; charset=UTF-8");
	  String message;
	  ResponseEntity resEnt=null;
	  HttpHeaders responseHeaders = new HttpHeaders();
	  responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	  try {
		  boardService.removeComment2(articleNO, commentNO, commentNO2);
		  message = "<script>";
		  message += "alert('??????');";
		  message += "location.href='" + request.getContextPath() + "/board/viewArticle.do?articleNO=" + articleNO + "';";
		  message += "</script>";
		  resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	  } catch(Exception e) {
		  e.printStackTrace();
	  }
	  
	  return resEnt;
  }
  
  @Override
  @RequestMapping(value="/board/fixComment.do", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity fixComment(@RequestParam("commentNO") int commentNO,
		  @RequestParam("commentNO2") int commentNO2,
		  @RequestParam("articleNO") int articleNO,
		  HttpServletRequest request, HttpServletResponse response) throws Exception{
	  response.setContentType("text/html; charset=UTF-8");
	  String message;
	  int fix_usable;
	  ResponseEntity resEnt=null;
	  HttpHeaders responseHeaders = new HttpHeaders();
	  responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	  try {
		  fix_usable = boardService.fixComment(articleNO, commentNO, commentNO2);
		  // 1?????? ?????? ??????
		  if(fix_usable == 1) {
			  message = "<script>";
			  message += "alert('????????????');";
			  message += "location.href='" + request.getContextPath() + "/board/viewArticle.do?articleNO=" + articleNO + "';";
			  message += "</script>";
		  }else {
			  message = "<script>";
			  message += "alert('?????? ??????????????? ????????????.');";
			  message += "</script>";
		  }
		  resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	  } catch(Exception e) {
		  e.printStackTrace();
	  }
	  
	  return resEnt;
  }

	@RequestMapping(value = "/board/*Form.do", method = {RequestMethod.GET, RequestMethod.POST})
	private ModelAndView form(@RequestParam(value="parentNO", required=false) String parentNO,
								HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");		
		if(viewName.equals("/board/replyForm")) {
			HttpSession session = request.getSession();
			if(parentNO != null) {
				session.setAttribute("parentNO", parentNO); // ?????? ????????? ???, ?????? ?????? ?????? ??? ?????????????????? ????????? ??????
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	//?????? ????????? ???????????????
	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		String imageFileName= null;
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while(fileNames.hasNext()){
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			imageFileName=mFile.getOriginalFilename();
			File file = new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+"\\" + fileName);
			if(mFile.getSize()!=0){ //File Null Check
				if(!file.exists()){ //??????????? ?????????????????? ???????????????????????? ???????????? ?????????
					file.getParentFile().mkdirs();  //??????????? ???????????????? ???????????????????????? ????????????
					mFile.transferTo(new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+ "\\"+imageFileName)); //?????????? ??????????????? multipartFile?????? ???????????? ?????????????? ????????????
				}
			}
			
		}
		return imageFileName;
	}
	
}
