<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>로그인</h2>

<div>
	<div>
		<form action="/user/login" method="post">
			<div>
				<input type="text" name="user_id" placeholder="아이디를 입력하세요." autofocus="autofocus">
			</div>
			<div>
				<input type="password" name="user_pw" placeholder="비밀번호를 입력하세요.">
			</div>
			<div>
				<input type="submit" value="로그인">
			</div>
		</form>
			
		<div style="color:red;">${msg }</div>
		
		<a href="/user/join">회원가입</a>
		<a href="/user/findPw">비밀번호 찾기</a>
	</div>
</div>

<div>
	<c:forEach items="${list }" var="item">
		<div>${item.i_user }, ${item.nm }</div>
	</c:forEach>
</div>
