<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

	<!-- 제이쿼리 -->
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	
	<!-- Bootstrap CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/custom.css?after">
		
	<script type="text/javascript">
		function fn_messages(id){
			var state = $('#messagebox').attr("class");
			newDiv = document.createElement("div");
			if(state == 'close'){
				$('#messagebox').attr("class", "open");
				newDiv.setAttribute('id', 'Message');
				$('#alertmessage').append(newDiv);
				$.ajax({
					type: "post",
					 async: true,
					 url: "${contextPath}/board/messagelist",
					 data: {
						 id : id,
					 },
					 success: function(data, textStatus){
						 var messageinfo = "";
						 messageinfo += "<div class='list-group'>";
						 $(data).each(function(){
							 messageinfo += "<a href='http://localhost:8090/pro30/board/viewArticle.do?articleNO=" + this.articleNO + "' class='list-group-item list-group-item-action'>";
							 messageinfo += "	<div class='d-flex w-100 justify-content-between'>";
							 messageinfo += "		<h6 class='mb-1'>" + this.content + "</h6>";
							 messageinfo += "		<small>" + this.alertdate +"</small>";
							 messageinfo += "	</div>";
							 messageinfo += "</a>";
						 });
						 messageinfo += "</div>";
						 $("#Message").html(messageinfo);
					 },
					 error: function(data, textStatus){
						 
					 },
					 complete: function(data, textStatus){
						 
					 }
				});				
			}
			else{
				$('#messagebox').attr("class", "close");
				const messagelist = document.getElementById("Message");
				messagelist.remove();
			}
		}
	</script>
	
	<style>
		@import url('https://fonts.googleapis.com/css?family=Poppins:400,500,600,700,800,900&display=swap');
		*
		{
			margin: 0;
			padding: 0;
			font-family: 'Poppins', sans-serif;
		}
		.navbar-brand
		{
			position: relative;
			margin: 0;
			font-weight: 900;
			color: #fff;
			z-index: 1;
			overflow: hidden;
		}
		.navbar-brand:before
		{
			content: '';
			position: absolute;
			left: 120%;
			width: 120%;
			height: 100%;
			background: linear-gradient(90deg, transparent 0%, #f8f9fa 5%, #f8f9fa 100%);
			animation: animate 3.5s linear forwards;
			animation-iteration-count: infinite;
			animation-direction: alternate;
		}		
		
		@keyframes animate
		{
			0%
			{
				left: 110%
			}
			100%
			{
				left: -20%
			}
		}
		
		.Wrapper{padding-top : 10px;}
		
		@media(max-width: 500px){
			#offcanvasNavbar{
				width: 65%;
			}
		}
	</style>
	<title>헤더</title>	
</head>
<body>
	<nav class="navbar navbar-light bg-light fixed-top" style="position: relative;">
		<div class="container-fluid">
			<a class="navbar-brand" href="${contextPath }/main.do">Learndry</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
				<div class="offcanvas-header">
					<c:choose>
						<c:when test="${isLogOn == true  && member!= null}">
							<h5 class="offcanvas-title" id="offcanvasNavbarLabel">환영합니다. ${member.name }님!</h5>
						</c:when>
						<c:otherwise>
							<div class="login_false">
								<a href="${pageContext.request.contextPath}/member/loginForm.do"><h5>로그인</h5></a>
							</div>
						</c:otherwise>
					</c:choose>							
					<button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
				</div>
				<div class="offcanvas-body">
					<ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
						<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextPath }/main.do">Home</a></li>
						<c:choose>
							<c:when test="${isLogOn == true  && member!= null}">
								<li class="nav-item"><a class="nav-link" href="${contextPath}/member/logout.do">로그아웃</a></li>
							</c:when>
						</c:choose>
						<c:if test="${isLogOn == true && member != null }">
							<div id="alertmessage">
								<a class="nav-link dropdown-toggle" type="button" onClick="fn_messages('${member.id }')" id="navbarScrollingDropdown"> 알림 </a>
								<span id="messagebox" class="close"></span>
							</div>
						</c:if>
					</ul>
				</div>
			</div>
		</div>		
	</nav>

	<div class="container-md" style="padding-top: 60px;">
		<form class="d-flex" action="${pageContext.request.contextPath}/board/searchArticles.do">
			<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="title">
			<button class="btn btn-outline-success" type="submit">Search</button>
		</form>
	</div>

	<div class="Wrapper">
		
		<p class="little_title">커뮤니티</p>		
		<ul class="nav justify-content-center">
			<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/board/listArticles.do">게시판</a></li>
		</ul>
	</div>	
</body>
</html>