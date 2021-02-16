'use strict'

var inputImgElem = document.querySelector('#inputImg')

function upload(){
	if(inputImgElem.files.length === 0){
		alert('이미지를 선택해 주세요')
		retrun
	}
	
	ajax()
	
	function ajax(){
		var formData = new FormData()	// 페이지 전환 없이 폼 데이터를 제출하고 싶을 때, 바로 FormData 객체를 사용.
		
		for(var i = 0; i < inputImgElem.files.length; i++){
			formData.append('imgs', inputImgElem.files[i])
		}
		
		fetch('/user/profileUpload',{
			method: 'POST',
			body: formData
		})
		.then(res => res.json())
		.then(myJson => {
			if(myJson === 1){
				getData()
			}else{
				alert('이미지 업로드를 실패하였습니다.')
			}
		})
	}
}

// 전역 변수
var splide = null
var centerContElem = document.querySelector('.centerCont')

function getData(){
	fetch('/user/profileData')
	.then(res => res.json())
	.then(myJson => {
		proc(myJson)
	})
	
	function proc(myJson){
		const div = document.createElement('div')
		div.classList.add('profileBox')	// 클래스를 필요에 따라 삽입.
		
		let delProfileHTML = ''
		
		const imgBasicSrc = '/res/img/basic_profile.jpg'
		const imgSrc = `/res/img/user/${myJson.i_user}/${myJson.profile_img}`
		
		let imgOption = ''
		
		if(myJson.profile_img){
			imgOption = ` onclick="clkProfile()" class="pointer" `	// 이미지가 있었다면, 아래 imgOption에 들어갈 것이다.
			
			delProfileHTML = `
				<div id="delProfileBtnContainer">
					<button onclick="delProfileImg();">기본 이미지 사용</button>
				</div>
			`
		}
		
		let gender = '여'
		
		if(myJson.gender === 1){
			gender = '남'
		}
		
		div.innerHTML = `
			<div class="circular--landscape circular--size200">
				<img id="profileImg" src="${imgSrc}" ${imgOption} alt="프로필 이미지"
					onerror="this.onerror=null; this.src='${imgBasicSrc}'">
			</div>
			<div>
				<div>아이디 : ${myJson.user_id}</div>
				<div>이름 : ${myJson.nm}</div>
				<div>성별 : ${gender}</div>
				<div>연락처 : ${myJson.ph}</div> 
			</div>
			${delProfileHTML}
		`
		
		centerContElem.innerHTML = ''
		centerContElem.append(div)
	}
}

getData()

// Profile Modal
var modalContainerElem = document.querySelector('.modalContainer')

function clkProfile(){
	openModal()
	getProfileImgList()
}

// 프로필 이미지 리스트 가져오기
function getProfileImgList(){
	fetch('/user/profileImgList')
	.then(res => res.json())
	.then(myJson => {
		profileImgCarouselProc(myJson)
	})
}

// 프로필 이미지 삭제
function delProfileImg({i_img, img}){
	return new Promise(function(resolve){	// 오래 걸리기 때문에, Promise를 리턴 해주어야 한다. 
		fetch(`/user/profileImg?i_img=${i_img}&img=${img}`, {
			method: 'delete'
		})	// 쿼리 스트링으로 날릴려고 ``를 쓴다.
		.then(res => res.json())
		.then(myJson => {
			resolve(myJson)	// 아래의 .then(myJson)으로 이동한다.
		})
	})
}

function profileImgCarouselProc(myJson){
	console.log(myJson)
	
	var splideList = document.querySelector('.splide__list')
	splideList.innerHTML = null
	
	myJson.forEach(function(item){
		const div = document.createElement('div')
		div.classList.add('splide__slide')
		
		const img = document.createElement('img')
		img.src = `/res/img/user/${item.i_user}/${item.img}`
		
		const span = document.createElement('span')
		span.classList.add('pointer')
		span.append('X')
		span.addEventListener('click', function(){
			// console.log(item.i_img)
			delProfileImg(item).then(myJson => {	// 어느 프로필 이미지를 삭제할지 모르는 상태이기 때문에, 함수에 Promise를 리턴하도록 한다.
				if(myJson === 1){
					div.remove()
					splide.refresh()
				}else{
					alert('삭제를 실패하였습니다.')
				}
			})
		})
		
		div.append(img)
		div.append(span)
		splideList.append(div)
	})
	
	if(splide != null){
		splide.destroy(true)
	}
	
	splide = new Splide('.splide').mount()
}

function openModal(){
	modalContainerElem.classList.remove('hide')
}

function closeModal(){
	modalContainerElem.classList.add('hide')
}
