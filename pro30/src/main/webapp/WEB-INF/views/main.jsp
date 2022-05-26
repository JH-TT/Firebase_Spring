<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:set var="contextPath"  value="${pageContext.request.contextPath}" /> 
<%
  request.setCharacterEncoding("UTF-8");
%>    


<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Swiper demo</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1" />
<!-- Link Swiper's CSS -->
<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />

<!-- Demo styles -->
<style>
@import url('https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700');
.swiper {
	width: 100%;
	padding-top: 50px;
	padding-bottom: 50px;
}

.swiper-slide {
	background-position: center;
	background-size: cover;
	width: 400px;
	height: 500px;
}

.swiper-slide img {
	display: block;
	width: 100%;
}

body {
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	aling-items: center;	
	font-family: 'Poppins', sans-serif;
}
.card
{
	position: relative;
	background: #fff;
	width: 400px;
	height: 500px;
	margin: 0 auto;
}
.card .content a
{
	display: inline-block;
	margin: 10px 0 0;
	padding: 10px 20px;
	text-decoration: none;
	border: 2px solid #262626;
	color: #262626;
	font-weight: 600;
}
.card .content
{
	widows: 400px;
	padding: 30px;
	box-sizing: border-box;
}
.card .sliderText img
{
	position: relative;
	width: 100%;
	height: 250px;
	display: flex;
	justify-content: center;
	align-items: center;
	background: #000;
}
.card .sliderText h3
{
	color: #fff;
	font-size: 3em;
}
</style>
</head>

<body>
    <!-- Swiper -->
	<div class="swiper mySwiper">
		<div class="swiper-wrapper">
			<div class="swiper-slide">
				<div class="card">
					<div class="sliderText">
						<img src="${contextPath}/resources/image/jongho.jpg" />
					</div>
					<div class="content">
						<h2>이종호</h2>
						<p>커뮤니티 제작</p>
						<p>데이터베이스 관리</p>
					</div>
				</div>
			</div>
			<div class="swiper-slide">
				<div class="card">
					<div class="sliderText">
						<h3>엄기산</h3>
					</div>
					<div class="content">
						<p>조장</p>
						<p>어플 제작</p>
						<p>데이터베이스 관리</p>
					</div>
				</div>
			</div>
			<div class="swiper-slide">
				<div class="card">
					<div class="sliderText">
						<img src="${contextPath}/resources/image/suhwan.jpg" />
					</div>
					<div class="content">
						<h2>한수환</h2>
						<p>라즈베리 파이</p>
						<p>빨래통 제작</p>
					</div>
				</div>
			</div>
			<div class="swiper-slide">
				<div class="card">
					<div class="sliderText">
						<img src="${contextPath}/resources/image/kijung.jpg" />
					</div>
					<div class="content">
						<h2>김기정</h2>
						<p>라즈베리 파이</p>
						<p>빨래통 제작</p>
					</div>
				</div>
			</div>
		</div>
		<div class="swiper-pagination"></div>
	</div>

	<!-- Swiper JS -->
	<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>

	<!-- Initialize Swiper -->
	<script>
      var swiper = new Swiper(".mySwiper", {
        effect: "coverflow",
        grabCursor: true,
        centeredSlides: true,
        slidesPerView: "auto",
        coverflowEffect: {
          rotate: 30,
          stretch: 0,
          depth: 500,
          modifier: 1,
          slideShadows: true,
        },
        pagination: {
          el: ".swiper-pagination",
        },
      });
    </script>
</body>
</html>
