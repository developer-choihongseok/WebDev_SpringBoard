<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
  
<h1>${data == null ? '글 등록' : '글 수정' }</h1>	

<form action="/board/${param.i_board == null ? 'reg' : 'mod'}" method="post" id="frm">
	<!-- param은 쿼리스트링에 있는 값. -->
	<input type="hidden" name="i_board" value="${param.i_board == null ? 0 : param.i_board}">
	<input type="hidden" name="typ" value="${param.typ }">
	
	<div>
		제목: <input type="text" name="title" value="${data.title }" required="required" autofocus="autofocus">
	</div>
	<div>
		내용: <textarea name="ctnt" rows="5" cols="50" required="required">${data.ctnt }</textarea>
	</div>
	<div>
		<input type="submit" value="${data == null ? '글 등록' : '글 수정' }">
	</div>
</form>