<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<%
	request.setCharacterEncoding("UTF-8");
%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function fn_check_register() {
		var _id = $("input[name=id]").val();
		if (_id == '') {
			alert("ID를 입력하세요");
			return;
		}
		$.ajax({
			type : "post",
			async : true,
			url : "${contextPath}/member/overlappedID",
			dataType : "text",
			data : {
				id : _id
			},
			success : function(data, textStatus) {
				if (data == 'usable') {
					alert('사용가능한 아이디 입니다.');
					$('input[name=pwd]').prop("disabled", false);
				} else {
					alert("사용할 수 없는 ID입니다.");
				}
			},
			error : function(data, testStatus) {

			},
			complete : function(data, testStatus) {

			}
		});
	}
	
</script>
<title>회원 가입창</title>
<style>
.text_center {
	text-align: center;
}
#signup{
	width: 90%;
}
</style>
</head>
<body>
<%-- 	<form class="row g-3 needs-validation" method="post" action="${contextPath}/member/addMember.do" novalidate> --%>
<!-- 		<h1 class="text_center">회원 가입창</h1> -->
<!-- 		<table align="center"> -->
<!-- 			<tr> -->
<!-- 				<td width="200"><p align="right">아이디</td> -->
<!-- 				<td> -->
<!-- 					<div class="input-group mb-3"> -->
<!-- 						<input type="text" name="id" class="form-control" placeholder="Recipient's username" aria-label="Recipient's username" aria-describedby="button-addon2"> -->
<!-- 						<button class="btn btn-outline-secondary" type="button" id="button-addon2" onClick="fn_check_register()">중복확인</button> -->
<!-- 					</div> -->
<!-- 				</td> -->
<!-- 			</tr> -->

<!-- 			<tr> -->
<!-- 				<td width="200"><p align="right">비밀번호</td> -->
<!-- 				<td width="400"><input type="password" name="pwd" disabled></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td width="200"><p align="right">이름</td> -->
<!-- 				<td width="400"><p><input type="text" name="name"></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td width="200"><p>&nbsp;</p></td> -->
<!-- 				<td width="400"><input type="submit" value="가입하기"><input type="reset" value="다시입력"></td> -->
<!-- 			</tr> -->
<!-- 		</table> -->
<!-- 	</form> -->
	<form method="post" action="${contextPath}/member/addMember.do" class="was-validated" style="padding-bottom: 10px;">
		<h1 class="text_center">회원 가입창</h1>
		<table id="signup" align="center">
			<tr>
				<td><label for="input_id" class="form-label">ID:</label></td>
				<td>
					<div class="mb-3 mt-3">
<!-- 						<div style="display: flex;"> -->
<!-- 							<input type="text" class="form-control" id="input_id" placeholder="Enter userid" name="id" required> -->
<!-- 							<button class="btn btn-outline-secondary btn-sm" type="button" id="button-addon2" onClick="fn_check_register()">중복확인</button> -->
<!-- 						</div> -->
						<input type="text" class="form-control" id="input_id" placeholder="Enter userid" name="id" required>
						<div class="valid-feedback">Valid.</div>
						<div class="invalid-feedback">Please fill out this field.</div>
					</div>
					<button class="btn btn-outline-secondary btn-sm" type="button" id="button-addon2" onClick="fn_check_register()">중복확인</button>
				</td>
			</tr>
			<tr>
				<td><label for="input_pwd" class="form-label">Password:</label></td>
				<td>				
					<div class="mb-3 mt-3">
						<input type="password" class="form-control" id="input_pwd" placeholder="Enter password" name="pwd" required disabled>
						<div class="valid-feedback">Valid.</div>
						<div class="invalid-feedback">Please fill out this field.</div>
					</div>
				</td>
			</tr>
			<tr>
				<td><label for="input_name" class="form-label">Name:</label></td>
				<td>
					<div class="mb-3 mt-3">
						<input type="text" class="form-control" id="input_name" placeholder="Enter username" name="name" required>
						<div class="valid-feedback">Valid.</div>
						<div class="invalid-feedback">Please fill out this field.</div>
					</div>
				</td>
			</tr>
			<tr>
				<td width="150"><p>&nbsp;</p></td>
				<td>
					<button type="submit" class="btn btn-outline-success">가입하기</button>
					<button type="reset" class="btn btn-outline-secondary">다시입력</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>