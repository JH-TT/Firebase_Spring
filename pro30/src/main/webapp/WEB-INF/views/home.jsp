<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}" /> 

<html>
<head>
	<title>LEARNdry Home</title>

	<style>
		section {
			height: 950px;
			background: url(${contextPath}/resources/image/cleaning.jpg);
		}
		
		body {
			margin: 0;
		}
		
		section h1 {
			font-size: 20em;
			font-family: sans-serif;
			position: absolute;
			left: 160px;
			top: 150px;
			color: white;
			cursor: default;
			animation: slide 1s ease-out;			
		}
		@keyframes slide{
			from{
				left: -400px;
				opacity:0;
			}
			to{
				left: 160px;
				opacity:1;'
			}
		}			
	</style>
<!-- 제이쿼리 -->
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	
	<!-- Bootstrap CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
		crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</head>
<body>
	<section>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<a class="navbar-brand" href="#">      </a>
				<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarNav">
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextPath }/main.do">Main</a></li>
						<li class="nav-item"><a class="nav-link" href="#">Features</a></li>
						<li class="nav-item"><a class="nav-link" href="#">Pricing</a></li>
						<li class="nav-item"><a class="nav-link disabled">Disabled</a></li>
					</ul>
				</div>
			</div>
		</nav>
		<h1>LEARNdry!</h1>
	</section>	
</body>
</html>
