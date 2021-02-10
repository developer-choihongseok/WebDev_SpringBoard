<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="centerCont">
	<!-- JS로 처리하는 부분 -->
</div>

<div>
	<input type="file" id="inputImg" multiple accept="image/*">
	<input type="submit" value="업로드" onclick="upload()">
</div>

<script src="/res/js/user/profile.js"></script>

<!-- enctype="multipart/form-data" : <form> 요소가 파일이나 이미지를 서버로 전송할 때 주로 사용.
	request.getParameter()로 받아올 수 없다.
	
	multiple 속성을 주면 이미지 여러 개를 올릴 수 있다. 하지만 방법이 복잡하다..-->