<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="result" value="${param.result }" />
<%
   request.setCharacterEncoding("UTF-8");
%>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인창</title>
<c:choose>
	<c:when test="${result=='loginFailed' }">
		<script>
			window.onload = function() {
				alert("아이디나 비밀번호가 틀립니다.다시 로그인 하세요!");
			}
		</script>
	</c:when>
</c:choose>
</head>

<body>
	<form name="frmLogin" method="post" action="${contextPath}/member/login.do" style="padding-bottom: 10px;">
		<div class="row mb-3">
			<label for="inputEmail3" class="col-sm-2 col-form-label">ID</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="inputEmail3" name="id" required="required">
			</div>
		</div>
		<div class="row mb-3">
			<label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="inputPassword3">
			</div>
		</div>
			<button type="submit" class="btn btn-outline-primary">로그인</button>
			<button type="reset" class="btn btn-outline-primary">다시입력</button>
			<a class="btn btn-outline-primary" href="${contextPath}/member/memberForm.do">회원가입</a>
	</form>
</body>
</html>
