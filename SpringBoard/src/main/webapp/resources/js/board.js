'use strict'

// 글 번호 클릭 시, 해당 url로 이동
function clkArticle(i_board) {
	var url = `/board/detail?i_board=${i_board}`; 
	location.href = url;	// 주소값 이동
}

// 지금은 사용 X, 혹시나 나중에 비속어가 있는지 체크하는 용도로 사용.
function chk() {
	var frm = document.querySelector('#frm');	// 없어도 실행 되는데, 확실하게 해주기 위함!!
	
	if(chkEmptyEle(frm.title, '제목') || chkEmptyEle(frm.ctnt, '내용')){
		return false;
	}
}

// 삭제 버튼 클릭 -> fetch 사용(비동기 요청)
function clkDel(i_board, typ){
	if(confirm('삭제 하시겠습니까?')){
		fetch(`/board/del/${i_board}`,{
			method: 'delete'	// get방식이랑 비슷함, put이 post방식이랑 비슷하다.
		})
		.then(function(res){
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

var cmtObj = {
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
		getCmtList: function(i_board){
			fetch(`/board/cmtList?i_board=${i_board}`)
				.then(function(res){
					return res.json()
			})
			.then((list) => {
				this.proc(list)
			})
		},
		
		createRecode: function(item) {
		
		},
		
		proc: function(list) {
			var table = this.createCmtTable()

			console.log(list)
		}
}

var cmtListElem = document.querySelector('#cmtList')

if(cmtListElem) {
	var i_board = document.querySelector('#i_board').dataset.id
	cmtObj.getCmtList(i_board)
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
			return
		}
	}
}
















