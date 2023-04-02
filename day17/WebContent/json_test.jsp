<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1">
		<tr>
			<td><h2>실시간 검색 순위</h2></td>
			<td><div id="ranking">실시간 검색어</div></td>
		</tr>
	</table>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	let obj = null; //응답된 텍스트를 여기에 넣음
	let word = new Array();
	let ranking = document.getElementById("ranking"); 
	
	//1. ------------------바닐라 자바스크립트 Ajax------------------
	//서버와 소통하기 위한 뒷길
	let xhr = new XMLHttpRequest();
	//xhr.readyState : 길의 상태를 보여주는 전광판
	//on : readystate(전광판의 상태)가 change(바뀔)할 때
	xhr.onreadystatechange = function() {
		//서버 응답 완료
		if(xhr.readyState == 4) {
			//응답 성공
			if(xhr.status == 200){
				//xhr.responseText : 응답된 메세지, 텍스트 내용
				obj = JSON.parse(xhr.responseText); //이제 여기서  obj안에는 search_word라는 키값이 들어감.
				for(let i=0; i<obj.search_word.length; i++) {
					word[i] = obj.search_word[i].name;	//내부에 있는 json들을 하나씩 꺼내와서 word배열에 하나씩 넣어줌.				
				}
			}
				
		}
	} //여기까지 함수 등록만 해준것.
	
	//어느 곳으로 길을 열것인지
	//xhr.open("GET","data.json",true);	//길이 열려 있습니다
	xhr.open("GET","${pageContext.request.contextPath}/json/GetJSON.jd");
	//열려 있는 길로 요청 전송
	xhr.send();							//이 길로 전송중입니다
										//와 같은 식으로 계속해서 전광판 내용이 바뀜
	//----------------------------------------------------------------------
	//2.------------------------jQuery Ajax---------------------------------
	/* $.ajax({
		url:'data.json',
		type:'get', //전송방식
		//data:보낼데이터("키":"값")
		//dataType:보내는데이터의타입 //text인지 json인지 적어주는것
		success:function(data) {
			//여기서는 responseText가 data일것.
			obj = JSON.parse(data);
			for(let i=0; i<obj.search_word.length;i++) {
				word[i] = obj.search_word[i].name;
			}
		},
		error:function(err) {
			alert("Ajax 실패! " + err)
		}
	}) */
	
	
	//----------------------------------------------------------------------
	let i=0;
	//setInterval(함수,밀리초) : 밀리초마다 넘겨준 함수 호출 
	let interval = setInterval(function() {
		if(i == obj.search_word.length) {
			i=0; //마지막까지 했다는 뜻이니 i를 돌려줌.
		}
		ranking.innerHTML = "<b>" + word[i] + "</b>";
		i++;
	},2000)
	
</script>
</html>