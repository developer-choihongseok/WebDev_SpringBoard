// 기본 이미지 사용(프로필 이미지 삭제)
function delProfileImg(){
	axios.get('/user/delProfileImg.korea').then(function(res){
		// 하드 코딩
		var basicProfileImg = '/res/img/basic_profile.jpg';
		
		if(res != null && res.status == 200){
			if(res.data.result == 1){	// 프로필 이미지 삭제가 완료됨!
				var img = document.querySelector('#profileImg');
				var container = document.querySelector('#delProfileBtnContainer');
				
				img.src = basicProfileImg;
				container.remove();	// 기본 이미지 사용 버튼 삭제.
			}
		}
	}).catch(function(err){
		console.err('err 발생: ' + err);
	})
}

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
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(param)
		}).then(res => res.json())
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
		}
	}
}


