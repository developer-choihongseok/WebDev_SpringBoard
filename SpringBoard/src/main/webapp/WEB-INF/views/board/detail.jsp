<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- functions : 문자열을 처리하는 함수 제공 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div>
	<a href="/board/list?typ=${requestScope.data.typ }">게시판으로 돌아가기</a>
	
	<c:if test="${data.i_user == loginUser.i_user }">
		<button onclick="clkDel(${requestScope.data.i_board}, ${requestScope.data.typ });">삭제</button>
		<a href="/board/mod?typ=${requestScope.data.typ }&i_board=${requestScope.data.i_board }">
			<button>수정</button>
		</a>
	</c:if>
	
	<div style="margin-top: 20px;">
		<div>번호 : ${requestScope.data.seq }</div>
		<div>조회수 : ${requestScope.data.hits }</div>
		<div>
			<span class="profile-td-nm">작성자: </span>
			<c:if test="${requestScope.data.profile_img == null }">
				<div class="circular--landscape circular--size40">
					<img id="profileImg" src="/res/img/basic_profile.jpg">
				</div>
			</c:if>
			<c:if test="${requestScope.data.profile_img != null }">
				<div class="circular--landscape circular--size40">
					<img id="profileImg" src="/res/img/${requestScope.data.i_user }/${requestScope.data.profile_img}">
				</div>
			</c:if>
			${requestScope.data.writer_nm }
		</div>
		<div>제목 : ${requestScope.data.title }</div>
		<div>작성일 : ${requestScope.data.r_dt }</div>
		<div>내용: ${requestScope.data.ctnt }</div>
	</div>
	
	<div style="margin-top: 20px;">
		<span id="i_board" data-id="${requestScope.data.i_board }"></span>
		
		<c:if test="${loginUser != null }">
			<%-- 댓글 쓰는 부분 --%>
			<div>
				<form id="cmtFrm">
					댓글: <input type="text" name="ctnt">
					<input type="button" name="btn" value="댓글쓰기"><br><br>
				</form>
			</div>
		</c:if>

		<strong>댓글 목록</strong><br>
				
		<div id="cmtList"></div>

	</div>
	
	<c:if test="${loginUser != null }">
		<div id="favoriteContainer" is_favorite="${data.is_favorite}" onclick="toggleFavorite(${data.i_board });">
			<%-- ${data.is_favorite} --%>
			<c:choose>
				<c:when test="${data.is_favorite == 1 }">
					<i class="fas fa-thumbs-up"></i>
				</c:when>
				<c:otherwise>
					<i class="far fa-thumbs-up"></i>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div class="black_bg"></div>
		<div class="modal_wrap">
	    	<div class="modal_close">
	    		<a href="#">close</a>
	    	</div>
	    	<div>
	       		 <h3>댓글 수정</h3>
	       		 <input type="text" id="cmtCtnt">
	       		 <input type="button" value="수정" id="cmtModBtn">
	    	</div>
		</div>
	</c:if>

</div>

