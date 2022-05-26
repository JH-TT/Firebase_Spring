<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  /> 
<%
  request.setCharacterEncoding("UTF-8");
%> 

<head>
<meta charset="UTF-8">
<title>글쓰기창</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
   function readURL(input) {
      if (input.files && input.files[0]) {
	      var reader = new FileReader();
	      reader.onload = function (e) {
	        $('#preview').attr('src', e.target.result);
          }
         reader.readAsDataURL(input.files[0]);
      }
  }
  function postarticle(obj){
	  var _title = $("#formGroupExampleInput2").val();
	  var _content = $("#exampleFormControlTextarea1").val();
	  
	  if(_title == '' || _content == ''){
		  alert('제목 혹은 내용을 작성해 주세요.');
		  return
	  }else{
		  obj.submit();
	  }
  }
  
  function backToList(obj){
    obj.action="${contextPath}/board/listArticles.do";
    obj.submit();
  }
  
  var cnt=1;
  function fn_addFile(){
	  $("#d_file").append("<br>"+"<input type='file' name='file"+cnt+"' />");
	  cnt++;
  }  

</script>
<style>
	#article_form
	{
		display: flex;
		justify-content:center;
	}
</style>
 <title>글쓰기창</title>
</head>
<body>
	<h1 style="text-align: center">글쓰기</h1>
	<form id="article_form" name="articleForm" method="post" action="${contextPath}/board/addNewArticle.do" enctype="multipart/form-data">
		<table border="0" align="center">
			<tr>
				<td colspan=2 align="left"><input type="text" size="20" maxlength="100" value="${member.name }" readonly /></td>
			</tr>
			<tr>
				<td colspan="2"><input class="form-control" id="formGroupExampleInput2" type="text" size="67" maxlength="500" name="title" placeholder="제목" /></td>
			</tr>
			<tr>
				<!-- 				<td align="right" valign="top"><br>글내용: </td> -->
				<td colspan=2><textarea class="form-control" id="exampleFormControlTextarea1" name="content" rows="10" cols="65" maxlength="4000" placeholder="내용"></textarea></td>
			</tr>
			<tr>
				<!-- 				<td> -->
				<!-- 					<div class="input-group"> -->
				<!-- 						<input type="file" name="imageFileName" class="form-control" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Upload" onchange="readURL(this);"> -->
				<!-- 						<button class="btn btn-outline-secondary" type="button" id="inputGroupFileAddon04">Button</button> -->
				<!-- 					</div> -->
				<!-- 				</td> -->
				<div class="mb-3">
					<td><input name="imageFileName" class="form-control" type="file" id="formFile" onchange="readURL(this);"></td>					
				</div>
				<td><img class="img-thumbnail" id="preview" src="#" width=200 height=200 /></td>


				<!-- 				<td align="right">이미지파일 첨부</td> -->
				<!-- 				<td align="left"><input type="button" value="파일 추가" onClick="fn_addFile()" /></td> -->
			</tr>
			<tr>
				<td colspan="4"><div id="d_file"></div></td>
			</tr>
			<div class="btn-group" role="group" aria-label="Basic outlined example">
				<tr>
					<td align="right"></td>
					<td colspan="2">					
						<input type=button class="btn btn-outline-primary" value="글쓰기" onClick="postarticle(this.form)" />
						<input type=button class="btn btn-outline-primary" value="목록보기" onClick="backToList(this.form)" />					
					</td>
				</tr>
			</div>
		</table>
	</form>
</body>
</html>
