<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="articlesList" value="${articlesMap.articlesList }" />
<c:set var="section" value="${articlesMap.section }" />
<c:set var="pageNum" value="${articlesMap.pageNum }" />
<c:set var="totArticles" value="${articlesMap.toArticles }" />
<c:set var="title" value="${articlesMap.title }" />

<%
  request.setCharacterEncoding("UTF-8");
%>  
<!DOCTYPE html>
<html>
<head>
 <style>
   .cls1 {text-decoration:none;}
   .cls2{text-align:center; font-size:30px;}
   .pagination{justify-content: center; padding-top: 10px;}
   .articlenav{font-size: 14px;}
  </style>
  <meta charset="UTF-8">
  <title>글목록창</title>
</head>
<script>
	function fn_articleForm(isLogOn,articleForm,loginForm){
	  if(isLogOn != '' && isLogOn != 'false'){
	    location.href=articleForm;
	  }else{
	    alert("로그인 후 글쓰기가 가능합니다.")
	    location.href=loginForm+'?action=/board/articleForm.do';
	  }
	}
</script>
<body>
<table align="center" width="80%" class="table table-striped" >
  <tr class="articlenav">
     <td >글번호</td>
     <td >작성자</td>              
     <td >제목</td>
     <td >작성일</td>
  </tr>
<c:choose>
  <c:when test="${articlesList ==null }" >
    <tr  height="10">
      <td colspan="4">
         <p align="center">
            <b><span style="font-size:9pt;">등록된 글이 없습니다.</span></b>
        </p>
      </td>  
    </tr>
  </c:when>
  <c:when test="${articlesList !=null }" >
    <c:forEach  var="article" items="${articlesList }" varStatus="articleNum" >
     <tr align="center">
	<td width="5%">${article.articleNO}</td>
	<td width="10%">${article.id }</td>
	<td align='left'  width="35%">
	  <span style="padding-right:30px"></span>
	   <c:choose>
	      <c:when test='${article.level > 1 }'>  
	         <c:forEach begin="1" end="${article.level }" step="1">
	              <span style="padding-left:20px"></span>    
	         </c:forEach>
	         <span style="font-size:12px;">[답변]</span>
                   <a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
	          </c:when>
	          <c:otherwise>
	          	<c:choose>
		            <c:when test="${article.totalComments > 0 }">
		          		<a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title } [${article.totalComments }]</a>
		          	</c:when>
		            <c:otherwise>
		            	<a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>
		            </c:otherwise>
	            </c:choose>
	          </c:otherwise>
	        </c:choose>
	  </td>
	  <td  width="10%">${article.writeDate}</td> 
	</tr>
    </c:forEach>
     </c:when>
    </c:choose>
</table>

	<ul class="pagination">
		<c:if test="${totArticles != null }">					
			<c:choose>
				<c:when test="${section >1 && page==1 }">
					<li class="page-item"><a class="page-link" href="${contextPath }/board/searchArticles.do?section=${section-1}&pageNum=${(section-1)*10 +1 }&title=${title }">&nbsp; Previous </a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item disabled">
						<span class="page-link">Previous</span>
					</li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${totArticles >100 }">
					<!-- 글 개수가 100 초과인경우 -->
					<c:forEach var="page" begin="1" end="10" step="1">						
						<li class="page-item"><a class="page-link" href="${contextPath }/board/searchArticles.do?section=${section}&pageNum=${page}&title=${title }">${(section-1)*10 +page } </a></li>						
					</c:forEach>
				</c:when>
				<c:when test="${totArticles ==100 }">
					<!--등록된 글 개수가 100개인경우  -->
					<c:forEach var="page" begin="1" end="10" step="1">
						<li class="page-item"><a class="page-link" href="#">${page } </a></li>						
					</c:forEach>
				</c:when>

				<c:when test="${totArticles< 100 }">
					<!--등록된 글 개수가 100개 미만인 경우  -->
					<c:choose>
						<c:when test="${totArticles % 10 == 0 }">
							<c:forEach var="page" begin="1" end="${totArticles/10}" step="1">
								<c:choose>
									<c:when test="${page==pageNum }">
										<li class="page-item active" aria-current="page"><a class="page-link" href="${contextPath }/board/searchArticles.do?section=${section}&pageNum=${page}&title=${title}">${page } </a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="${contextPath }/board/searchArticles.do?section=${section}&pageNum=${page}&title=${title}">${page } </a></li>
									</c:otherwise>									
								</c:choose>
							</c:forEach>							
						</c:when>
						<c:otherwise>
							<c:forEach var="page" begin="1" end="${totArticles/10 +1}" step="1">
								<c:choose>
									<c:when test="${page==pageNum }">
										<li class="page-item active" aria-current="page"><a class="page-link" href="${contextPath }/board/searchArticles.do?section=${section}&pageNum=${page}&title=${title}">${page } </a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="${contextPath }/board/searchArticles.do?section=${section}&pageNum=${page}&title=${title}">${page } </a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${totArticles > 100 }">					
					<li class="page-item"><a class="page-link" href="${contextPath }/board/searchArticles.do?section=${section+1}&pageNum=${section*10+1}&title=${title}">&nbsp; Next</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item disabled"><span class="page-link">Next</span></li>
				</c:otherwise>
			</c:choose>
		</c:if>
	</ul>

	<!-- 	<nav aria-label="..."> -->
<!--   <ul class="pagination"> -->
<!--     <li class="page-item disabled"> -->
<!--       <span class="page-link">Previous</span> -->
<!--     </li> -->
<!--     <li class="page-item"><a class="page-link" href="#">1</a></li> -->
<!--     <li class="page-item active" aria-current="page"> -->
<!--       <span class="page-link">2</span> -->
<!--     </li> -->
<%--     <li class="page-item"><a class="page-link" href="${contextPath }/board/listArticles.do?section=1&pageNum=2">3</a></li> --%>
<!--     <li class="page-item"><a class="page-link" href="#">4</a></li> -->
<!--     <li class="page-item"><a class="page-link" href="#">5</a></li> -->
<!--     <li class="page-item"><a class="page-link" href="#">6</a></li> -->
<!--     <li class="page-item"> -->
<!--       <a class="page-link" href="#">Next</a> -->
<!--     </li> -->
<!--   </ul> -->
<!-- </nav> -->
<!-- <a  class="cls1"  href="#"><p class="cls2">글쓰기</p></a> -->
<a  class="cls1"  href="javascript:fn_articleForm('${isLogOn}','${contextPath}/board/articleForm.do', 
                                                    '${contextPath}/member/loginForm.do')"><p class="cls2">글쓰기</p></a>
</body>
</html>