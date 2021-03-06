<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>	<%-- functions : 문자열을 처리하는 함수 제공 --%>

<div>
	<%-- <a href="/board/list?typ=${requestScope.data.typ }">게시판으로 돌아가기</a> --%>
	<span class="pointer" onclick="back()">돌아가기</span>
	
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
			작성자: 
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
			<span class="profile-td-nm">
				<c:choose>
					<c:when test="${param.searchType == 4 && param.searchText != '' }">
						${fn:replace(data.writer_nm, param.searchText, '<mark>' += param.searchText += '</mark>')}
					</c:when>
					<c:otherwise>
						${data.writer_nm }
					</c:otherwise>
				</c:choose>
			</span>
		</div>
		<div>
			제목 : 
				<c:choose>
					<c:when test="${(param.searchType == 1 || param.searchType == 3) && param.searchText != '' }">
						${fn:replace(data.title, param.searchText, '<mark>' += param.searchText += '</mark>')}
					</c:when>
					<c:otherwise>
						${data.title }
					</c:otherwise>
				</c:choose>
		</div>
		<div>작성일 : ${requestScope.data.r_dt }</div>
		<div>
			내용:
				<c:choose>
					<c:when test="${(param.searchType == 2 || param.searchType == 3) && param.searchText != '' }">
						${fn:replace(data.ctnt, param.searchText, '<mark>' += param.searchText += '</mark>')}
					</c:when>
					<c:otherwise>
						${data.ctnt }
					</c:otherwise>
				</c:choose>
		</div>
	</div>
	
	<div style="margin-top: 20px;">
		<!-- HTML의 사용자 지정 특성 이름은 data-로 시작한다.  -->
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

