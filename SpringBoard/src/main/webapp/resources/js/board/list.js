'use strict'

// 전역 변수
var dataElem = document.querySelector('#data')
var listFrmElem = document.querySelector('#listFrm')
var typElem = listFrmElem.typ
var searchTypeElem = document.querySelector('#searchType')
var searchTextElem = document.querySelector('#searchText')

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