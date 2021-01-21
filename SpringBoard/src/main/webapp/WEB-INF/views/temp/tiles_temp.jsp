<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title }</title>
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
<link rel="stylesheet" href="/res/css/common.css?ver=16">
<link rel="stylesheet" href="/res/css/board.css?ver=10">
<!-- js 파일을 배열로 전달 -->
<c:forEach items="${jsList}" var="item">
	<script defer src="/res/js/${item}.js?ver=15"></script>
</c:forEach>
<!-- /를 적으면 절대 경로를 적어줘야 한다! -->
<script defer src="/res/js/common.js"></script>
</head>
<body>
	<div id="container">
		<tiles:insertAttribute name="header"/>
		
		<section>
			<tiles:insertAttribute name="content"/>
		</section>
	</div>
</body>
</html>

<!-- defer는 먼저 페이지 로드 후 연결 시킨다는 것(즉,맨 밑에 놔둔 효과), async는 화면을 안 느려지게 하는 효과 -->

