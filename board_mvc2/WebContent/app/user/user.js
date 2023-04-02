function sample6_execDaumPostcode() {
	new daum.Postcode(
			{
				oncomplete : function(data) {
					// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

					// 각 주소의 노출 규칙에 따라 주소를 조합한다.
					// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
					var addr = ''; // 주소 변수
					var extraAddr = ''; // 참고항목 변수

					// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
					if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을
															// 경우
						addr = data.roadAddress;
					} else { // 사용자가 지번 주소를 선택했을 경우(J)
						addr = data.jibunAddress;
					}

					// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
					if (data.userSelectedType === 'R') {
						// 법정동명이 있을 경우 추가한다. (법정리는 제외)
						// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
						if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
							extraAddr += data.bname;
						}
						// 건물명이 있고, 공동주택일 경우 추가한다.
						if (data.buildingName !== '' && data.apartment === 'Y') {
							extraAddr += (extraAddr !== '' ? ', '
									+ data.buildingName : data.buildingName);
						}
						// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
						if (extraAddr !== '') {
							extraAddr = ' (' + extraAddr + ')';
						}
						// 조합된 참고항목을 해당 필드에 넣는다.
						document.getElementById("sample6_extraAddress").value = extraAddr;

					} else {
						document.getElementById("sample6_extraAddress").value = '';
					}

					// 우편번호와 주소 정보를 해당 필드에 넣는다.
					document.getElementById('sample6_postcode').value = data.zonecode;
					document.getElementById("sample6_address").value = addr;
					// 커서를 상세주소 필드로 이동한다.
					document.getElementById("sample6_detailAddress").focus();
				}
			}).open();
}
function sendit(){
	let joinForm = document.joinForm;
	let idTag = joinForm.userid;
	let dupTag = document.getElementById("result");
	if(idTag.value == ""){
		alert("아이디를 입력하세요!");
		idTag.focus();
		return false;
	}
	if(idTag.value.length < 5 || idTag.value.length > 12){
		alert("아이디는 5자 이상 12자 이하로 입력해주세요!");
		idTag.focus();
		return false;
	}
	if(dupTag.innerHTML == ""){
		alert("아이디 중복검사를 진행해주세요!");
		idTag.focus();
		return false;
	}
	if(dupTag.innerHTML != "사용할 수 있는 아이디입니다!"){
		alert("중복된 아이디가 있습니다!");
		idTag.focus();
		return false;
	}
	let pwTag = joinForm.userpw;
	let pw_reTag = joinForm.userpw_re;
	if(pwTag.value == ""){
		alert("비밀번호를 입력해주세요!");
		pwTag.focus();
		return false;
	}
	//정규식 - https://developer.mozilla.org/ko/docs/Web/JavaScript/Guide/Regular_Expressions
	let reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,}$/
	if(!reg.test(pwTag.value)){
		alert("비밀번호는 8자 이상, 숫자, 대문자, 소문자, 특수문자를 모두 포함해야 합니다!");
		pwTag.focus();
		return false;
	}else if(/(\w)\1\1\1/.test(pwTag.value)){
		alert("같은 문자를 4번 이상 사용하실 수 없습니다!")
		pwTag.focus();
		return false;
	}else if(pwTag.value.search(/\s/) != -1){
		alert("비밀번호는 공백을 포함할 수 없습니다!");
		pwTag.focus()
		return false;
	}
	if(pw_reTag.value == ""){
		alert("비밀번호 확인을 해주세요!");
		pw_reTag.focus();
		return false;
	}
	if(pwTag.value != pw_reTag.value){
		alert("비밀번호 확인을 다시 해주세요!");
		pwTag.focus();
		return false;
	}
	let nameTag = joinForm.username;
	if(nameTag.value == ""){
		alert("이름을 입력해주세요!");
		nameTag.focus();
		return false;
	}
	let zipcodeTag = joinForm.zipcode;
	if(zipcodeTag.value == ""){
		alert("주소를 입력해주세요!");
		sample6_execDaumPostcode();
		return false;
	}
	let addrdetailTag = joinForm.addrdetail;
	if(addrdetailTag.value == ""){
		alert("주소를 마저 입력해주세요!");
		addrdetailTag.focus();
		return false;
	}
	//hobby 체크박스 배열들
    let hobbies = document.getElementsByName("userhobby");
    let check = false;
    //하나씩 꺼내오며 .checked 프로퍼티 확인했을 때 true이면 체크했다는 뜻
    for(let hobby of hobbies){
    	if(hobby.checked){
			check = true;
			break;
    	}
    }
    if(!check){
    	alert("취미를 하나 이상 선택하세요!");
    	return false;
    }
    return true;
}
function checkId(){
	let userid = document.joinForm.userid.value;
	if(userid.length < 5 || userid.length>12){
		alert("아이디는 5자 이상 12자 이하로 입력해주세요!");
		idTag.focus();
		return false;
	}
	let resultTag = document.getElementById("result");
	let xhr = new XMLHttpRequest();
	//통신할 곳과의 연결 통로 열기
	xhr.open("GET",cp+"/user/CheckIdOk.us?userid="+userid,true);
	//joinview.jsp에서 자바스크립트 변수로 cp 에 pageContext 만들어두면 여기서 바로 cp만 연결해서 쓸 수 있음
	
	//xhr의 상태가 변할 때 호출될 함수 미리 설정
	xhr.onreadystatechange = function(){
		if(xhr.readyState == XMLHttpRequest.DONE){
			if(xhr.status == 200 || xhr.status == 201){
				let txt = xhr.responseText;
				//문자열.trim() : 문자열의 양쪽에 있는 공백 제거
				txt = txt.trim();
				
				if(txt == "O"){
					//중복체크 통과
					resultTag.innerHTML = "사용할 수 있는 아이디입니다!";
				}else{
					//중복체크 실패
					resultTag.innerHTML = "이미 존재하는 아이디입니다!";
				}
			}
			
		}
	}
	//통신하기
	xhr.send();
}



