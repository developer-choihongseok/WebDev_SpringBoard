<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- functions : 문자열을 처리하는 함수 제공 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="data" data-typ="${param.typ == null ? 1 : param.typ}">
	<c:if test="${sessionScope.loginUser != null }">
		<div class="boardWrite">
			<a href="/board/reg?typ=${param.typ == null ? 1 : param.typ} ">
				<button>글쓰기</button>
			</a>
		</div>
	</c:if>
	
	<div>
		<span id="searchWindow">
			<select id="searchType">
				<option value="1" ${param.searchType == 1 ? 'selected' : '' }>제목</option>
				<option value="2" ${param.searchType == 2 ? 'selected' : '' }>내용</option>
				<option value="3" ${param.searchType == 3 ? 'selected' : '' }>제목+내용</option>
				<option value="4" ${param.searchType == 4 ? 'selected' : '' }>작성자</option>
			</select>
			<input type="search" id="searchText" value="${param.searchText }">
			<input type="submit" value="검색" onclick="getBoardList()">
		</span>
				
		<form id="listFrm" action="/board/list" method="get">
			<input type="hidden" name="typ">
			<input type="hidden" name="searchType" value="0">
			<input type="hidden" name="searchText">
			<input type="hidden" name="page" value="1">
			<select name="recordCntPerPage" onchange="getBoardList()">
				<c:forEach begin="5" end="40" step="5" var="p">
					<!-- <option value="5" ${requestScope.data.recordCntPerPage == 5 ? 'selected' : ''}>5개</option>  -->
					<option value="${p }" ${requestScope.data.recordCntPerPage == pageScope.p ? 'selected' : '' }>${p }개</option>							
				</c:forEach>
			</select>
		</form>
	</div>
	
	<c:choose>
		<c:when test="${fn:length(requestScope.data.list) == 0 }">	<!-- requestScope.data : BoardParentDomain -->
			<div>게시글이 없습니다.</div>
		</c:when>
		<c:otherwise>
			<table border="1">
				<thead>
					<tr>
						<th width="70">번호</th>
						<th width="500">제목</th>
						<th width="100">조회수</th>
						<th width="180">작성일</th>
						<th width="130">작성자</th>
						<th width="100">좋아요</th>
					</tr>
				</thead>
				<!-- list는 BoardController의 list 메서드에서 사용 -->
				<c:forEach items="${requestScope.data.list }" var="item">
					<tr class="pointer" onclick="clkArticle(${item.i_board })">
						<td align="center">${item.seq }</td>
						<td align="center">${fn:length(item.title) > 12 ? fn:substring(item.title, 0, 11) += '...' : item.title }</td>
						<td align="center">${item.hits }</td>
						<td align="center">${item.r_dt }</td>
						<td class="profile-td" align="center">
							<c:if test="${item.profile_img == null }">
								<div class="circular--landscape circular--size40">
									<img id="profileImg" src="/res/img/basic_profile.jpg">
								</div>
							</c:if>
							<c:if test="${item.profile_img != null }">
								<div class="circular--landscape circular--size40">
									<img id="profileImg" src="/res/img/${item.i_user }/${item.profile_img}">
								</div>
							</c:if>
							<span class="profile-td-nm">${item.writer_nm }</span>
						</td>
						<td align="center">${item.favorite_cnt }</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	
	<!-- 페이징 -->
	<div class="pageContainer">
		<c:if test="${requestScope.data.startPage > 1}">
			<span class="page" onclick="getBoardList(1)">1</span>
			<span>...</span>
		</c:if>
		
		<%-- 1부터 ${pageCnt }까지 자연수를 순차적으로 출력함. --%>
		<c:forEach begin="${requestScope.data.startPage }" end="${requestScope.data.endPage }" var="i">
			<span class="page ${requestScope.data.page == i ? 'selected' : ''}" onclick="getBoardList(${i})">${i }</span>
				<%-- <a href="list?typ=${typ }&page=${i }">${i }</a> --%>
		</c:forEach>
		
		<c:if test="${requestScope.data.endPage < requestScope.data.maxPageNum }">
			<span>...</span>
			<span class="page" onclick="getBoardList(${requestScope.data.maxPageNum})">${requestScope.data.maxPageNum }</span>
		</c:if>
	</div>
</div>

<script src="/res/js/board/list.js"></script>	<!-- 자동으로 구조를 잡아줄려고 만들어 두었다. -->