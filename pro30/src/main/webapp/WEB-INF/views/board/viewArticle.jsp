<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%-- <c:set var="article"  value="${articleMap.article}"  /> --%>
<%-- <c:set var="imageFileList"  value="${articleMap.imageFileList}"  /> --%>

<%
request.setCharacterEncoding("UTF-8");
%>

<head>
<meta charset="UTF-8">
<title>글보기</title>
<style>
#tr_file_upload {
 	display: none; 
} 

#tr_btn_modify { 
 	display: none; 
} 

.form-floating {
 	display: flex; 
 	width: -webkit-fill-available; 
} 
img{ 
 	max-width: 100%;
 	max-height: 100%;
}
.img-fluid{
 	height: 400px;
 	width: 400px;
}

.comment-date {
 	color: darkgray;
 	font-size: 11px;
}

.comment-line {
 	text-align: left;
}

.writer-info {
 	text-align: left; 
 	width: 140px; 
}

.write-date {
	font-size: 13px;
 	color: gray;
}

.articletool {
 	position: absolute; 
	right: 0; 
 	bottom: 28px; 
 	font-size: 13px; 
 	line-height: 18px; 
}

.article_header {
 	position: relative; 
 	margin-bottom: 20px; 
 	padding-bottom: 20px; 
 	border-bottom: 1px solid var(- -skinLayoutBorder); 
 	margin-bottom: 0; 
}

.articleTitle {
 	text-align: left; 
 	padding-bottom: 10px; 
}

#i_title {
 	border: 0;
 	width: 100%;
 	background: white; 
 	font-size: 35px; 
}

.chat-icon {
 	margin-right: 6px; 
}

.more_item {
 	display: inline-block; 
 	margin-left: 3px; 
 	position: relative; 
 	vertical-align: top; 
}

.button_comment {
 	text-decoration: none; 
}

.writer-comment {
 	font-weight: 800; 
 	color: #088A85; 
}

.comment-tool {
 	padding-right: 5px; 
}

.comment2 {
 	font-size: 14px; 
 	font-weight: 600; 
}

.comment-font {
 	position: relative; 
 	left: 18px; 
}
.fixed-line{
 	text-align: left; 
 	padding-bottom: 15px; 
}
.fixed_state{
 	font-size: 10px; 
}
</style>
</head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<!-- 아이콘 -->
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>


<script type="text/javascript">
     function backToList(obj){
	    obj.action="${contextPath}/board/listArticles.do";
	    obj.submit();
     }
 
	 function fn_enable(obj){
		 document.getElementById("i_title").disabled=false;
		 document.getElementById("exampleFormControlTextarea1").disabled=false;
		 document.getElementById("i_imageFileName").disabled=false;
		 document.getElementById("tr_btn_modify").style.display="block";
		 var tr_fileupload = document.getElementById("tr_file_upload");
		 if(tr_fileupload != null){
			 tr_fileupload.style.display="block";
		 }
		 document.getElementById("tr_btn").style.display="none";
	 }
	 
	 function fn_modify_article(obj){
		 obj.action="${contextPath}/board/modArticle.do";
		 obj.submit();
	 }
	 
	 function fn_remove_article(url,articleNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var articleNOInput = document.createElement("input");
	     articleNOInput.setAttribute("type","hidden");
	     articleNOInput.setAttribute("name","articleNO");
	     articleNOInput.setAttribute("value", articleNO);
		 
	     form.appendChild(articleNOInput);
	     document.body.appendChild(form);
	     form.submit();
	 
	 }
	 
	 function fn_remove_comment(url, commentNO, articleNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
		 var commentNOInput = document.createElement("input");
		 commentNOInput.setAttribute("type", "hidden");
		 commentNOInput.setAttribute("name", "commentNO");
		 commentNOInput.setAttribute("value", commentNO);
		 
		 var articleNOInput = document.createElement("input");
	     articleNOInput.setAttribute("type","hidden");
	     articleNOInput.setAttribute("name","articleNO");
	     articleNOInput.setAttribute("value", articleNO);
		 
		 form.appendChild(commentNOInput);
		 form.appendChild(articleNOInput);
		 document.body.appendChild(form);
		 form.submit();
	 }
	 
	 function fn_remove_comment2(articleNO, commentNO, commentNO2){
		 var form = document.createElement("form");
		 var url = "${contextPath}/board/removeComment2.do";
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
		 var commentNOInput = document.createElement("input");
		 commentNOInput.setAttribute("type", "hidden");
		 commentNOInput.setAttribute("name", "articleNO");
		 commentNOInput.setAttribute("value", articleNO);
		 
		 var commentNOInput2 = document.createElement("input");
		 commentNOInput2.setAttribute("type", "hidden");
		 commentNOInput2.setAttribute("name", "commentNO");
		 commentNOInput2.setAttribute("value", commentNO);
		 
		 var commentNOInput3 = document.createElement("input");
		 commentNOInput3.setAttribute("type", "hidden");
		 commentNOInput3.setAttribute("name", "commentNO2");
		 commentNOInput3.setAttribute("value", commentNO2);
		 
		 form.appendChild(commentNOInput);
		 form.appendChild(commentNOInput2);
		 form.appendChild(commentNOInput3);
		 document.body.appendChild(form);
		 form.submit();
	 }
	 
	 function fn_reply(isLogOn, articleNO, articleID, memberID){
		 if(isLogOn != '' && isLogOn != 'false'){
			 var form = document.createElement("form");
			 var _comment = $('#floatingTextarea2').val();
			 if(_comment == ""){
				 alert('댓글을 입력해 주세요.');
			 }else{
				 form.setAttribute("method", "post");
				 form.setAttribute("action", "${contextPath }/board/comment.do?articleNO=" + articleNO);
				 
				 var wrtie_comment = document.createElement("input");
				 wrtie_comment.setAttribute("type", "text");
				 wrtie_comment.setAttribute("name", "comment");
				 wrtie_comment.setAttribute("value", _comment);
				 if(articleID != memberID){				
					 var message_id = document.createElement("input");
					 message_id.setAttribute("tyep", "hidden");
					 message_id.setAttribute("name", "message_id");
					 message_id.setAttribute("value", articleID);
					 
					 var senduser = document.createElement("input");
					 senduser.setAttribute("tyep", "hidden");
					 senduser.setAttribute("name", "send_id");
					 senduser.setAttribute("value", memberID);
					 
					 form.appendChild(message_id);
					 form.appendChild(senduser);
				 }
				 form.appendChild(wrtie_comment);
				 document.body.appendChild(form);
				 form.submit();
			 }
		 }else{
			 alert('로그인 후 글쓰기가 가능합니다');
			 location.href = "${contextPath}/member/loginForm.do?action=/board/viewArticle.do?articleNO=" + articleNO;
		 }
		 
	 }
	 
	 function readURL(input) {
	     if (input.files && input.files[0]) {
	         var reader = new FileReader();
	         reader.onload = function (e) {
	             $('#preview').attr('src', e.target.result);
	         }
	         reader.readAsDataURL(input.files[0]);
	     }
	 } 	 
	 
	 function fn_fix_comment(articleNO, commentNO, commentNO2){
		 var form = document.createElement("form");
		 var url = "${contextPath}/board/fixComment.do";
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
		 var commentNOInput = document.createElement("input");
		 commentNOInput.setAttribute("type", "hidden");
		 commentNOInput.setAttribute("name", "articleNO");
		 commentNOInput.setAttribute("value", articleNO);
		 
		 var commentNOInput2 = document.createElement("input");
		 commentNOInput2.setAttribute("type", "hidden");
		 commentNOInput2.setAttribute("name", "commentNO");
		 commentNOInput2.setAttribute("value", commentNO);
		 
		 var commentNOInput3 = document.createElement("input");
		 commentNOInput3.setAttribute("type", "hidden");
		 commentNOInput3.setAttribute("name", "commentNO2");
		 commentNOInput3.setAttribute("value", commentNO2);
		 
		 form.appendChild(commentNOInput);
		 form.appendChild(commentNOInput2);
		 form.appendChild(commentNOInput3);
		 document.body.appendChild(form);
		 form.submit();
	 }
	 
	 // 대댓글 input박스 생성
	 function fn_addcomment2(isLogOn, commentNO, articleNO, userid, articleid, commentid, del){
		 var newDiv = document.createElement('div');
		 var comment2 = document.createElement('input');
		 var send_comment = document.createElement('input');
		 var newDiv2 = document.createElement('div');
		 var newDiv3 = document.createElement('div');
		 var newDiv4 = document.createElement('div');
		 var newDiv5 = document.createElement('div');
		 var comment_label = document.createElement('label');
		 var cancel = document.createElement('input');
		 
		 var state = $('#write-comment' + commentNO).attr("class");
		 
		 if(state == 'close'){
			 $('#write-comment' + commentNO).attr("class", "open");
			 newDiv.setAttribute('class', 'container');
			 newDiv.setAttribute('id', 'CM' + commentNO);
			 newDiv.setAttribute('style', 'padding-top: 13px;');
			 
			 newDiv2.setAttribute('class', 'input-group mb-3');
			 
			 newDiv3.setAttribute('class', 'form-floating');
			 
			 newDiv4.setAttribute('class', 'btn-group-vertical');
			 
			 newDiv5.setAttribute('id', 'commentlist' + commentNO);
			 newDiv5.setAttribute('style', 'padding-top: 5px;');
			 
			 comment2.setAttribute('type', 'text');
			 comment2.setAttribute('name', 'comment2');
			 comment2.setAttribute('class', 'form-control');
			 comment2.setAttribute('placeholder', "Leave a comment here");
			 comment2.setAttribute('id', 'cc' + commentNO);
			 comment2.setAttribute('style', 'height: 90px;');
			 
			 comment_label.setAttribute('for', 'cc' + commentNO);
			 comment_label.append(userid);
			 
			 send_comment.setAttribute('class', 'btn btn-outline-secondary');
			 send_comment.setAttribute('id', 'button-addon2');
			 send_comment.setAttribute('type', 'button');
			 send_comment.setAttribute('value', "확인");
			 send_comment.onclick=function(){
				 var form = document.createElement("form");
				 var _comment = $('#cc' + commentNO).val();
				 if(_comment == ""){
					 alert('댓글을 입력해 주세요.');
				 }else{
					 form.setAttribute("method", "post");
					 if(commentid != userid){
						 form.setAttribute("action", "${contextPath }/board/comment2.do?articleNO=" + articleNO + "&commentNO=" + commentNO + "&commentid=" + commentid);
					 } else{
						 form.setAttribute("action", "${contextPath }/board/comment2.do?articleNO=" + articleNO + "&commentNO=" + commentNO);
					 }
					 
					 
					 var wrtie_comment = document.createElement("input");
					 wrtie_comment.setAttribute("type", "text");
					 wrtie_comment.setAttribute("name", "comment");
					 wrtie_comment.setAttribute("value", _comment);
					 
					 form.appendChild(wrtie_comment);
					 document.body.appendChild(form);
					 form.submit();
				 }
			 }
			 
			 cancel.setAttribute('class', 'btn btn-outline-secondary');
			 cancel.setAttribute('id', 'button-addon2');
			 cancel.setAttribute('type', 'button');
			 cancel.setAttribute('value', "취소");
			 cancel.onclick=function(){				 
				 var addedcomment = document.getElementById('CM' + commentNO);
				 var commentlist = document.getElementById("commentlist" + commentNO);
				 document.getElementById(commentNO).removeChild(addedcomment);
				 document.getElementById(commentNO).removeChild(commentlist);
				 $('#write-comment' + commentNO).attr("class", "close");
			 }
			 
			 newDiv4.append(send_comment);
			 newDiv4.append(cancel);
			 
			 newDiv3.append(comment2);
			 newDiv3.append(comment_label);
			 newDiv3.append(newDiv4);
			 
			 newDiv2.append(newDiv3);
			 
			 newDiv.append(newDiv2);
			 if(isLogOn == 'true' && del == 0){
			 	$('#'+commentNO).append(newDiv);
			 }
			 $('#'+commentNO).append(newDiv5);
			 $.ajax({
				 type: "post",
				 async: true,
				 url: "${contextPath}/board/commentlist",
				 data: {
					 commentNO : commentNO,
					 articleNO : articleNO
				 },
				 success: function(data, textStatus){
					 var commentinfo = "";					 
					 $(data).each(function(){
						 commentinfo += "<div class='fw-bold'>";
						 commentinfo += "<span class='writer'> └ ";
						 if(articleid == this.writer){
							 commentinfo += "	<svg svg width='15' height='15' xmlns='http://www.w3.org/2000/svg'>";
							 commentinfo += "		<image href='${contextPath }/resources/svg/user-check.svg' width='15' height='15' />";
							 commentinfo += "	</svg>";
						 }
						 commentinfo += this.writer + "</span> <span class='comment-date'>" + this.writeDate + "</span> <span class='btn-group' style='float: right;' >";
						 if(this.writer == userid || articleid == userid){
							 commentinfo += "		<div class='comment-tool'>";
							 commentinfo += "			<a type='button' id='dropdownMenuButton1' data-bs-toggle='dropdown' aria-expanded='false'>";
							 commentinfo += "				<svg width='16' height='16' xmlns='http://www.w3.org/2000/svg'>";
							 commentinfo += "					<image href='${contextPath}/resources/svg/9104221_kebab_kebab menu_menu_navigation_icon.svg' width='16' height='16' />";
							 commentinfo += "				</svg>";
							 commentinfo += "			</a>";
							 commentinfo += "			<ul class='dropdown-menu' aria-labelledby='dropdownMenuButton'>";
							 if(this.writer == userid){
								 commentinfo += "				<li><input class='dropdown-item' type='button' value='삭제하기' onClick='fn_remove_comment2("+ articleNO + ", " + commentNO + ", " + this.commentNO + ")'></li>";							 
							 }
							 if(articleid == userid){
								 commentinfo += "				<li><input class='dropdown-item' type='button' value='고정하기' onClick='fn_fix_comment("+ articleNO + ", " + commentNO + ", " + this.commentNO + ")'></li>"
							 }
							 commentinfo += "			</ul>";
							 commentinfo += "		</div>";
						 }
						 commentinfo += "</span>";
						 commentinfo += "</div>"
						 if(articleid == this.writer){
							 commentinfo += "<div class='writer-comment' style='position: relative; left: 19px;'>" + this.comment + "</div>";
						 }else{
							 commentinfo += "<div class='comment-font'>" + this.comment + "</div>"; 
						 }						 					
					 });
					 $("#commentlist" + commentNO).html(commentinfo);
				 },
				 error: function(data, textStatus){					 
				 },
				 complete: function(data, textStatus){
					 
				 }
			 });
		 }else{
			 $('#write-comment' + commentNO).attr("class", "close");
			 var commentlist = document.getElementById("commentlist" + commentNO);			 
			 var addedcomment = document.getElementById('CM' + commentNO);
			 if(addedcomment != null){
			 	document.getElementById(commentNO).removeChild(addedcomment);
			 }
			 document.getElementById(commentNO).removeChild(commentlist);
		 }
	 }
 </script>

<body>
	<form name="frmArticle" method="post" action="${contextPath}" enctype="multipart/form-data">
		<input type="hidden" name="articleNO" value="${article.articleNO}" /> <input type="hidden" name="id" value="${article.id}" />
		<div class="article_header">
			<div class="articleTitle">
				<input type="text" value="${article.title }" name="title" id="i_title" disabled />
			</div>
			<div class="writer-info">
				<div class="profile_area">
					<div>${article.id }</div>
					<div class="write-date">${article.writeDate }</div>
				</div>
				<div class="articletool">
					<a role="button" class="button_comment" onClick="document.location='#CM'"> <svg width="17" height="17" class="chat-icon" xmlns="http://www.w3.org/2000/svg">
							<image href="${contextPath }/resources/svg/rocketdotchat.svg" width="17" height="17" />
						</svg> 댓글 <strong>${article.totalComments }</strong>
					</a>
					<div class="more_item">
						<a type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false"> <svg width="16" height="16" xmlns="http://www.w3.org/2000/svg">
								<image href="${contextPath}/resources/svg/9104221_kebab_kebab menu_menu_navigation_icon.svg" width="16" height="16" />
							</svg>
						</a>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
							<c:if test="${member.id == article.id }">
								<li><input type="button" class="dropdown-item" value="수정하기" onClick="fn_enable(this.form)"></li>
								<li><input type="button" class="dropdown-item" value="삭제하기" onClick="fn_remove_article('${contextPath}/board/removeArticle.do', ${article.articleNO})"></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="article-container">
			<div class="input-group">
				<td style="padding: 0;"><textarea name="content" class="form-control" id="exampleFormControlTextarea1" disabled />${article.content }</textarea></td>
			</div>
		</div>
		<table class="table" align="center">
			<c:choose>
				<c:when test="${not empty article.imageFileName && article.imageFileName!='null' }">
					<tr>
						<td><input type="hidden" name="originalFileName" value="${article.imageFileName }" /> <img
							src="${contextPath}/download.do?articleNO=${article.articleNO}&imageFileName=${article.imageFileName}" id="preview" class="btn btn-primary" data-bs-toggle="modal"
							data-bs-target="#staticBackdrop" style="background: white; width: 300px;" /><br></td>
						<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="staticBackdropLabel">사진확대</h5>
										<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
									</div>
									<div class="modal-body">
										<img src="${contextPath}/download.do?articleNO=${article.articleNO}&imageFileName=${article.imageFileName}" id="preview" class="img-fluid" />
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
									</div>
								</div>
							</div>
						</div>
					</tr>
					<tr>
						<td>
							<input name="imageFileName" class="form-control" type="file" id="i_imageFileName" disabled onchange="readURL(this);">
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr id="tr_file_upload">
						<td width="150" align="center" bgcolor="#FF9933" rowspan="2">이미지</td>
						<td><input type="hidden" name="originalFileName" value="${article.imageFileName }" /></td>
					</tr>
					<tr>
						<td><img id="preview" class="img-thumbnail" /><br> 
							<input name="imageFileName" class="form-control" type="file" id="i_imageFileName" disabled onchange="readURL(this);">
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr id="tr_btn_modify" align="center">
				<td colspan="2">
					<div class="btn-group" role="group" aria-label="Basic outlined example">
						<input type=button class="btn btn-outline-primary" value="수정반영하기" onClick="fn_modify_article(frmArticle)">
						<input type=button class="btn btn-outline-primary" value="취소" onClick="backToList(frmArticle)">
					</div>
				</td>
			</tr>

			<tr id="tr_btn">
				<td colspan="2" align="center">
					<div class="btn-group" role="group" aria-label="Basic outlined example">
						<input type=button class="btn btn-outline-primary" value="리스트로 돌아가기" onClick="backToList(this.form)">
					</div>
				</td>
			</tr>
		</table>
	</form>
	<div class="container" id="CM" style="padding-top: 13px;">
		<div class="input-group mb-3">
			<div class="form-floating">
				<input type="text" name="comment" class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 100px"></input> 
				<label for="floatingTextarea2">Comments</label> 
				<input class="btn btn-outline-secondary" id="button-addon2" type=button value="답글쓰기" onClick="fn_reply('${isLogOn }', ${article.articleNO}, '${article.id }', '${member.id }')">
			</div>
		</div>
		<c:if test="${fixed_comment != null }">
			<div class="fixed-line" style="width: 100%;">
				<div class="ms-2 me-auto">
					<div>
						<ion-icon name="star-outline"></ion-icon>
						<span class="fixed_state">고정됨</span>
					</div>
					<div class="fw-bold">
						<c:if test="${article.id == fixed_comment.writer}">
							<a> <svg svg width="15" height="15" xmlns="http://www.w3.org/2000/svg">
									<image href="${contextPath }/resources/svg/user-check.svg" width="15" height="15" />
								</svg>
							</a>
						</c:if>
						<span class="writer">${fixed_comment.writer }</span> <span class="comment-date">${fixed_comment.writeDate }</span> <span class="btn-group" style="position: absolute;"> 
						</span>
					</div>
					<c:choose>
						<c:when test="${article.id == fixed_comment.writer}">
							<span class="writer-comment">${fixed_comment.comment }</span>
						</c:when>
						<c:otherwise>
							${fixed_comment.comment }
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:if>
		<div style="padding-bottom: 10px;">
			<c:forEach var="comment" items="${comments }">
				<li class="list-group-item d-flex justify-content-between align-items-start">
					<div class="comment-line" style="width: 100%;">
						<div class="ms-2 me-auto">
							<div class="fw-bold">
								<c:if test="${comment.isdeleted == 0 }">
									<c:if test="${article.id == comment.writer}">
										<a> <svg svg width="15" height="15" xmlns="http://www.w3.org/2000/svg">
												<image href="${contextPath }/resources/svg/user-check.svg" width="15" height="15" />
											</svg>
										</a>
									</c:if>
									<span class="writer">${comment.writer }</span> <span class="comment-date">${comment.writeDate }</span> <span class="btn-group" style="position: absolute;"> 
										<div class="comment-tool">									
											<c:if test="${member.id == comment.writer || member.id == article.id}">
												<a type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false"> <svg width="16" height="16" xmlns="http://www.w3.org/2000/svg">
														<image href="${contextPath}/resources/svg/9104221_kebab_kebab menu_menu_navigation_icon.svg" width="16" height="16" />
													</svg>
												</a>
												<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
													<c:if test="${member.id == comment.writer }">
														<li><input class="dropdown-item" type="button" value="삭제하기" onClick="fn_remove_comment('${contextPath}/board/removeComment.do', ${comment.commentNO }, ${article.articleNO })"></li>
													</c:if>
													<c:if test="${member.id == article.id }">
														<li><input class="dropdown-item" type="button" value="고정하기" onClick="fn_fix_comment(${article.articleNO }, ${comment.commentNO }, 0)"></li>
													</c:if>
												</ul>
											</c:if>
										</div>
									</span>
								</c:if>
							</div>
							<c:choose>
								<c:when test="${comment.isdeleted == 1 }">
									삭제된 댓글입니다.
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${article.id == comment.writer}">
											<span class="writer-comment">${comment.comment }</span>
										</c:when>
										<c:otherwise>
											${comment.comment }
										</c:otherwise>
									</c:choose>
								</c:otherwise>								
							</c:choose>
							<div class="comment2" id="${comment.commentNO }">
								<a id="write-comment${comment.commentNO }" class="close" type="button" onClick="fn_addcomment2('${isLogOn }', ${comment.commentNO}, ${article.articleNO }, '${member.id }', '${article.id }', '${comment.writer }', '${comment.isdeleted }')">답글(${comment.totalComments2 })</a>
							</div>
						</div>
					</div>
				</li>
				<a></a>
			</c:forEach>
		</div>
	</div>
</body>
</html>