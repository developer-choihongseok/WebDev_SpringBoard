<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@splidejs/splide@latest/dist/css/splide.min.css">
<link rel="stylesheet" href="/res/css/user/profile.css">

<div class="centerCont">
	<!-- JS로 처리하는 부분 -->
</div>

<div>
	<input type="file" id="inputImg" multiple accept="image/*">	<!-- image/* : 모든 타입의 이미지 파일이 허용됨. -->
	<input type="submit" value="업로드" onclick="upload()">
</div>

<div class="modalContainer hide">
	<div class="modalContent">
		<span class="pointer" onclick="closeModal()">닫기</span>
		<div class="splide">
			<div class="splide__track">
				<div class="splide__list">
					
				</div>
			</div>
		</div>
	</div>
</div>

<!-- SPLIDE lib 이용 -->
<script src="https://cdn.jsdelivr.net/npm/@splidejs/splide@latest/dist/js/splide.min.js"></script>
<script src="/res/js/user/profile.js"></script>
