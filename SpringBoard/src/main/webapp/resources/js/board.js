'use strict'

// 지금은 사용 X, 혹시나 나중에 비속어가 있는지 체크하는 용도로 사용.
function chk() {
	var frm = document.querySelector('#frm');	// 없어도 실행 되는데, 확실하게 해주기 위함!!
	
	if(chkEmptyEle(frm.title, '제목') || chkEmptyEle(frm.ctnt, '내용')){
		return false;
	}
}

// 삭제 버튼 클릭 -> fetch 사용(비동기 요청)
// then : 응답이 올 때까지 마냥 기다리는 게 아니라 일단 실행해놓고, 서버가 응답할 때까지 다른 일을 할 수 있게끔 하게 하는 함수. then을 쓰면 then 뒤에 오는 것들은 서버 응답이 끝나면 실행이 된다.
function clkDel(i_board, typ){
	if(confirm('삭제 하시겠습니까?')){
		fetch(`/board/del/${i_board}`,{
			method: 'delete'
		}).then(function(res){	
			return res.json();	// 꼭 적어줘야 한다!!
		}).then(function(myJson){
			console.log(myJson);
			
			if(myJson.result === 1){	// 삭제 완료
				location.href = `/board/list?typ=${typ}`;
			}else{	// 삭제 실패
				alert("삭제 실패하였습니다.");
			}
		});
	}
}

// 댓글 삭제 버튼 클릭
function clkCmtDel(i_cmt, i_board){
	if(confirm('삭제 하시겠습니까?')){
		location.href = `cmt/del?i_cmt=${i_cmt}&i_board=${i_board}`;
	}
}

// 댓글에서 수정버튼 클릭 > 수정 FORM 나타나게 처리
function clkCmtMod(i_cmt){
	/*console.log('i_cmt : ' + i_cmt);	// 연결이 잘 되었는지 체크하는 것이 좋다!!*/
	var trForm = document.querySelector('#mod_' + i_cmt);
	// bDetail.jsp에서 63번째 줄에 class 엘리먼트만 유일하게 접근할 때, classList로 접근!!
	trForm.classList.remove('cmd_mod_form');
	
	console.log(trForm);
}

// 댓글에서 닫기버튼 클릭
function clkCmtClose(i_cmt){
	var trForm = document.querySelector('#mod_' + i_cmt);
	trForm.classList.add('cmd_mod_form');	// push도 가능.
	
	console.log(trForm);
}

// 좋아요 버튼 클릭 -> Ajax 사용
// 자바스크립트에서 함수도 객체로 보기(즉, 주소값을 갖고 있다!!)
function toggleFavorite (i_board){
	/*console.log('toggleFavorite called');*/
	var fc = document.querySelector('#favoriteContainer');
	// 1: 좋아요	0: 안 좋아요
	// 임의로 받아오는 것은 getAttribute()로 받아와야 한다.
	var state = fc.getAttribute('is_favorite');	// 문자열
	console.log(state);
	// 자동으로 정수로 변환
	var state = 1 - state;	// 1 -> 0	0 -> 1
	
	// get방식 통신 방법
	axios.get('/board/ajaxFavorite.korea',{
		params:{
			'state': state,	// Key : Value
			'i_board': i_board
		}
	}).then(function (res){	// 통신 성공,	then을 쓸 수 있는 건 promise 객체여서 쓸 수 있다.
		console.log(res);
		
		if(res.data.result == 1){	// res에 있는 data객체에 접근 후, result 값 가져오기.
			var iconClass = state == 1 ? 'fas' : 'far';
			fc.innerHTML = `<i class="${iconClass} fa-thumbs-up"></i>`;	// 기존에 있던 태그는 모두 지우고, 바꿔치기.
			fc.setAttribute('is_favorite', state)	// 반대 값이 들어간다.
		}else{
			alert('에러가 발생하였습니다.')
		}
	}).catch(function(err){	// 통신 실패
		console.err('에러 발생: ' + err)
	});
	
	/*console.log(fc.getAttribute('is_favorite'));*/
}

// 모달창 열기 닫기
function openCloseCmtModal(state){
	var modalWrapElem = document.querySelector('.modal_wrap')
	var blackBgElem = document.querySelector('.black_bg')

	modalWrapElem.style.display = state
	blackBgElem.style.display = state
}

// 댓글 수정
function modCmt(i_cmt, ctnt){
	openCloseCmtModal('block')	// 화면에 나타나게 한다.
	
	var cmtCtntElem = document.querySelector('.modal_wrap #cmtCtnt')
	var cmtModBtn = document.querySelector('.modal_wrap #cmtModBtn')
	
	cmtCtntElem.value = ctnt	// 값이 빈 칸이였는데, ctnt값으로 완전히 교체가 된다.
	cmtModBtn.onclick = ajax	// 수정 버튼 눌렀을 때, 실행.
	
	function ajax(){
		var param = {
			i_cmt,
			ctnt: cmtCtntElem.value
		}
		
		fetch('/board/updCmt', {
			method: 'put',	// 데이터를 보내줄테니, 서버에 존재하는 정보를 update방식으로 처리.
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(param)	// 객체를 문자열(JSON)로 바꿔준다.
		}).then(res => res.json())	// "arrow function" : 문자를 최대한 축약해준다. 한 줄로 적으면 자동으로 return이 들어간다.
		.then((myJson) => {
			openCloseCmtModal('none')
			
			switch(myJson.result){
				case 0:
					alert('댓글 수정에 실패하셨습니다.')
				return
				case 1:
					cmtObj.getCmtList()	// 리스트 가져오기
				return
			}
		})
		
		/*
		.then(function(res) {
			return res.json()	// 응답 객체(= res)의 json() 메서드를 호출하면 응답 데이터를 객체 형태로 반환.
		}).then((myJson) =>{
			switch(myJson.result){
			case 1:
				openCloseCmtModal('none')
				cmtObj.getCmtList()
			return
			case 0:
				alert('댓글 수정 실패')
			return
			}
		})
		*/
	}
}

// 댓글 삭제
function delCmt(i_cmt){
	if(!confirm('삭제하시겠습니까?')){
		return
	}
	
	fetch(`/board/delCmt?i_cmt=${i_cmt}`,{
		method: 'delete'
	}).then(function(res){
		return res.json()
	}).then(function(myJson){
		switch(myJson.result){
			case 1:
				cmtObj.getCmtList()
			return
			case 0:
				alert('댓글 삭제 실패')
			return
		}
	})
}

// cmtObj : 객체
var cmtObj = {
	i_board: 0,
	
	createCmtTable: function(){
		var tableElem = document.createElement('table')
		tableElem.innerHTML = 
		`<tr>
			<th>댓글</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>비고</th>
		</tr>`
		
		return tableElem
	},
	
	// 댓글 리스트 가져오기
	// fetch로 ajax통신 하고 있다.
	getCmtList: function(){
		if(this.i_board === 0){
			return
		}
		
		fetch(`/board/cmtList?i_board=${this.i_board}`)
			.then(function(res){
				return res.json()
		}).then((list) => {
			cmtListElem.innerHTML = ''
			this.proc(list)
		})
	},
		
	proc: function(list) {
		if(list.length == 0){
			return 
		}
			
		var table = this.createCmtTable()
			
		for(var i = 0; i < list.length; i++){
			var recode = this.createRecode(list[i])
			table.append(recode)
		}
		cmtListElem.append(table)
			
		console.log(list)	// 배열 형태.
	},
		
	createRecode: function(item) {
		var etc = ''
		
		if(item.is_mycmt === 1){
			etc = `<button onclick="modCmt(${item.i_cmt}, '${item.ctnt}')">수정</button>
			<button onclick="delCmt(${item.i_cmt})">삭제</button>`
		}
			
		var tr = document.createElement('tr')
		
		tr.innerHTML = `
			<td>${item.ctnt}</td>
			<td>${item.user_nm}</td>
			<td>${item.r_dt}</td>
			<td>${etc}</td>
		`
		return tr
	}
}

// 댓글 리스트
var cmtListElem = document.querySelector('#cmtList')

if(cmtListElem) {
	// 모달창 닫기 버튼
	var modalCloseElem = document.querySelector('.modal_close')
	
	if(modalCloseElem){
		modalCloseElem.addEventListener('click', function(){
		openCloseCmtModal('none')
		})
	}
	
	var i_board = document.querySelector('#i_board').dataset.id
	cmtObj.i_board = i_board
	cmtObj.getCmtList()
}

// 댓글 달기 - ajax 이용
// Standard JS 표준 : == 사용하지 않기, 함수 만들 때 띄우고, 호출할 때 붙이기, ; 빼기
var cmtFrmElem = document.querySelector('#cmtFrm')	// form을 가리킨다.

if(cmtFrmElem){
	// Enter를 눌렀을 때 submit이 안되게 하는 1번째 방법
	/*
	cmtFrmElem.onsubmit = function(){
		return false
	}
	*/
	
	// Enter를 눌렀을 때 submit이 안되게 하는 2번째 방법
	cmtFrmElem.onsubmit = function(e){
		e.preventDefault()
	}
	
	// 115~128번째 줄은 한번만 실행된다!!
	var ctntElem = cmtFrmElem.ctnt	// detail.jsp에서 44번째줄을 뜻한다. 
	var i_board = document.querySelector('#i_board').dataset.id	// dataset : data-에 접근하는 것을 가리킨다.
	var btnElem = cmtFrmElem.btn	// detail.jsp에서 45번째줄을 뜻한다.
	cmtObj.i_board = i_board
	
	// Enter 눌렀을 때 submit 버튼을 누른거랑 똑같은 효과.
	ctntElem.onkeyup = function(e){
		if(e.keyCode === 13){	// 13 : Entity
			ajax()
		}
	}
	
	btnElem.addEventListener('click', ajax)	// 버튼 눌렀을 때 실행시키고 싶은 함수명 : ajax 라는 이름.
	
	function ajax(){
		
		if(ctntElem.value === ''){
			return
		}
		
		var param = {
			i_board: i_board,	// 키 값이랑 value값이 같으면 생략이 가능.
			ctnt: ctntElem.value
		}
		
		console.log(param)
		
		fetch('/board/insCmt', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(param)	// 객체를 문자열(JSON)로 바꿔준다.
		}).then(function(res){	// 성공하면 res에 값이 들어간다.
			return res.json()	// promise를 리턴한다!!
		}).then(function(data){	// myJson : 서버에서 보낸 값.
			proc(data)	// 성공하면 1값을 받을 것이다.
		})
	}
	
	function proc(data){
		switch(data.result){
			case 0:
				alert('댓글 작성 실패하였습니다.')
			return
			case 1:
				ctntElem.value = ''
				cmtObj.getCmtList()
			return
		}
	}
}

