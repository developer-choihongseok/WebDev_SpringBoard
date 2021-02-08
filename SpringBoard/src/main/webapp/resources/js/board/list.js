'use strict'

function getBoardList(page){
	var dataElem = document.querySelector('#data')
	var {typ} = dataElem.dataset	// 여러 개의 값들을 한번에 가지고 온다. var typ = dataElem.dataset.typ
	
	var listFrmElem = document.querySelector('#listFrm')
	var typElem = listFrmElem.typ
	typElem.value = typ
	
	if(page){	// undefined가 아니라면..
		listFrmElem.page.value = page
	}
	
	listFrmElem.submit()
}