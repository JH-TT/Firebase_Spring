<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"
 %>
 <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- 제이쿼리 -->
<script src="http://code.jquery.com/jquery-latest.js"></script>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
#container {
	width: 100%;
	margin: 0px auto;
	text-align: center;
	border: 0px solid #bcbcbc;
}

.container-md {
	padding-bottom: 20px;
}

#footer {
	min-height: 100px;
	position: relative;
	width: 100%;
	background: #3586ff;
	padding: 20px 50px;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
}
#footer .social_icon,
#footer .menu
{
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
	margin: 10px 0;
	flex-wrap: wrap;
}
#footer .social_icon li,
#footer .menu li
{
	list-style: none;
	
}
#footer .social_icon li a
{
	font-size: 2em;
	color: #fff;
	margin: 0 10px;
	display: inline-block;
	transition: 0.5s;
}
#footer .social_icon li a:hover,
#footer .menu li a:hover
{
	transform: translateY(-10px);
}

#footer .menu li a
{
	font-size: 1.2em;
	color: #fff;
	margin: 0 10px;
	display: inline-block;
	transition: 0.5s;
	text-decoration: none;
	opacity: 0.75;
}
#footer .menu li a:hover
{
	opacity: 1;
}

#footer p
{
	color: #fff;
	text-align: center;
	margin-top: 15px;
	margin-bottom: 10px;
	font-size: 1.1em;
}
</style>
<title><tiles:insertAttribute name="title" /></title>
</head>
<body>
	<div id="container">
		<div id="header">
			<tiles:insertAttribute name="header" />
		</div>
		<div id="sidebar-left">
			<tiles:insertAttribute name="side" />
		</div>
		<div class="container">
			<tiles:insertAttribute name="body" />
		</div>
		<div id="footer">
			<tiles:insertAttribute name="footer" />
		</div>		
	</div>
	<div class="go-top">
		<a href="#header"><button type="button" class="btn btn-outline-primary">TOP</button></a>
	</div>
</body>
</html>