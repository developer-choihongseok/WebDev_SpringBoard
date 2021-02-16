'use strict'

// 비밀번호 변경 전, 확인
function chkPw(){
	var frm = document.querySelector('#frm');
	
	if(frm.current_pw.value == ''){
		alert('기존 비밀번호를 작성해 주세요.');
		frm.current_pw.focus();
		return false;
	}else if(frm.user_pw.value == ''){
		alert('변경할 비밀번호를 작성해 주세요.');
		frm.user_pw.focus();
		return false;
	} else if(frm.user_pw.value != frm.chk_user_pw.value){
		alert('변경/확인 비밀번호를 확인해 주세요.');
		frm.user_pw.focus();
		return false;
	}
	return true;
}

// 인증 메일 받기
function clkFindPwBtn(){
	var user_id = document.querySelector('#findPwUserId').value

	ajax()
	
	function ajax(){
		fetch(`/user/findPwProc?user_id=${user_id}`)
		.then(res => res.json())
		.then(res => {
			if(res.result === 1){
				// 타이머 시작!
				startTimer()
			}else{
				alert('인증 메일 발송을 실패하였습니다.')
			}
		})
	}
	
	// 타이머 작동
	function startTimer(){
		var countDownTimeElem = document.querySelector('#countDownTime')
		var totalSec = 300
		
		var interval = setInterval(function(){	// 1초마다 안에 내용이 실행된다.
			var min = parseInt(totalSec / 60)
			var sec = totalSec - (min * 60)
			var result = `남은시간 : ${create2Seat(min)}:${create2Seat(sec)}`
			
			countDownTimeElem.innerText = result
			
			if(totalSec == 0){
				clearInterval(interval)
			}
			totalSec--
		}, 1000)
	}
	
	// 무조건 2자리 숫자 만들기
	// ex) var.substr(0(인덱스),1(길이)) : 0번째 인덱스 자리부터 1 자리 숫자를 가지고 온다.
	// ex) var.substring(4,6) : 둘 다 인덱스 값이다.
	function create2Seat(p){
		var val = '0' + p
		return val.substr(val.length-2, 2)
		
		// return p.length === 1 ? `0${p}`: `${p}`
	}
}

// 비밀번호 찾기(변경)
var findPwAuthFrmElem = document.querySelector('#findPwAuthFrm')	// findPwAuthFrmElem : <form></form> 태그를 가리킨다.

// . : 제일 먼저 속성값을 찾고 난 뒤 자식요소를 찾는다.
// findPwAuth.jsp 페이지를 찾았을 때, undefined가 되지 않고, findPwAuthFrm 값이 true가 된다.
if(findPwAuthFrmElem){
	var btnSendElem = findPwAuthFrmElem.btnSend
	var userPwElem = findPwAuthFrmElem.user_pw	// 속성 값이 있으면, 속성 값을 가지고 온다.
	var userChkPwElem = findPwAuthFrmElem.chk_user_pw
	
	var userIdVal = findPwAuthFrmElem.user_id.value	// 밑에 자식이 없고, 속성 중에 value라는 값이 있어서 값이 복사되서 넘어온다.
	var cdVal = findPwAuthFrmElem.cd.value	// 밑에 자식이 없고, 속성 중에 value라는 값이 있어서 값이 복사되서 넘어온다.
//	var user_PwValue = findPwAuthFrm.user_pw.value -> 빈 값을 사용하겠다는 의미가 된다.
	
	btnSendElem.addEventListener('click', function(){
		if(userPwElem.value !== userChkPwElem.value){
			alert('비밀번호를 확인해주세요.')
			return
		}
		ajax()
	})
	
	function ajax(){	// ajax는 버튼 누르는 순간 실행이 된다!!, 호출 될 당시의 값을 가지고 와야 하기 때문에, 현재 값이 들어온다.
		var param = {
			user_id: userIdVal,
			cd: cdVal,
			user_pw: userPwElem.value	// 외부에 선언되면 무조건 빈 값이 되므로, 안쪽에 집어넣음.
		}
		
		fetch('/user/findPwAuth',{
			method: 'POST',	// 데이터를 보내줄테니, 서버의 DB에 저장을 요청.
			headers: {
				'Content-Type': 'application/json'	// JSON 형태의 데이터를 주고 싶을 때 무조건 작성.
			},
			body: JSON.stringify(param)	// 자바스크립트 객체(= param)를 JSON 문자열로 변환.
		}).then(res => res.json())	// JSON.parse()의 역할을 한다 : JSON 문자열을 JS 객체로 변환. json() : JSON을 이용해서 텍스트를 JS 객체로 parse한 결과를 담은 Promise 객체를 다시 반환한다.
		.then(myJson => {
			proc(myJson)
		})
	}
	
	function proc(res){
		switch(res.result){
			case 0:
				alert('비밀번호 변경에 실패하였습니다.')
			return
			case 1:
				alert('비밀번호를 변경하였습니다.')
				location.href='/user/login'
			return
			case 2:
				alert('인증시간이 초과하였습니다.')
				location.href='/user/login'
			return
		}
	}
}
