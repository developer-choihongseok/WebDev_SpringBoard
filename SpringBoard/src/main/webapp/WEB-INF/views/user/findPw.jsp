<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div>
	<div>
		<div>
			<input type="text" id="findPwUserId" name="user_id" placeholder="아이디를 입력하세요." autofocus="autofocus">
		</div>
		<div>
			<input type="button" value="인증메일 받기" onclick="clkFindPwBtn()">
		</div>
		
		<a href="/user/login">로그인 화면 이동</a>
	</div>
	
	<div id="countDownTime"></div>
</div>
