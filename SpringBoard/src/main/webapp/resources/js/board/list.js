'use strict'

// 전역 변수
var dataElem = document.querySelector('#data')
var listFrmElem = document.querySelector('#listFrm')
var typElem = listFrmElem.typ
var searchTypeElem = document.querySelector('#searchType')
var searchTextElem = document.querySelector('#searchText')

// 글 번호 클릭 시, 해당 url로 이동
function clkArticle(i_board, searchType, searchText) {
	var url = `/board/detail?i_board=${i_board}&searchType=${searchType}&searchText=${searchText}`; 
	location.href = url;	// 주소값 이동
}

// 검색 테스트에서 Enter 치면 검색되게 처리
function doSearch(e){
	if(e.keyCode === 13){
		getBoardList()
	}
}

function getBoardList(page){	// page : undefined
	var searchTextValue = searchTextElem.value	// 무언가 적혀 있는지를 확인.
	
	if(searchTextValue != ''){
		var searchTypeValue = searchTypeElem.value
		
		listFrmElem.searchType.value = searchTypeValue
		listFrmElem.searchText.value = searchTextValue
	}
	
	var {typ} = dataElem.dataset	// 여러 개의 값들을 한번에 가지고 온다. var typ = dataElem.dataset.typ
	
	typElem.value = typ
	
	if(page){	// undefined가 아니라면..
		listFrmElem.page.value = page
	}
	
	listFrmElem.submit()
}