<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h2>회원가입</h2>

<div>
	<div>
		<form id="joinfrm" action="/user/join" method="post" onsubmit="return joinChk();">
			<div>
				<input type="text" name="user_id" placeholder="아이디 입력" required="required" autofocus="autofocus">
			</div>
			<div>
				<input type="password" name="user_pw" placeholder="비밀번호 입력" required="required">
			</div>
			<div>
				<input type="password" name="user_pw_chk" placeholder="비밀번호 확인" required="required">
			</div>
			<div>
				<input type="text" name="nm" placeholder="이름 입력" required="required">
			</div>
			<div>
				<input type="email" name="email" placeholder="이메일 입력" required="required">
			</div>
			<div>
				성별 :
				<label>
					여성<input type="radio" name="gender" value="0" checked="checked">
				</label>
				<label>
					남성<input type="radio" name="gender" value="1">
				</label>
			</div>
			<div>
				<input type="text" name="ph" placeholder="휴대폰 번호 입력">
			</div>
			<div>
				<input type="submit" value="회원가입">
			</div>
		</form>
	</div>
</div>
