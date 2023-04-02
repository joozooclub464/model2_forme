<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 수정</title>
<style>
	body{
		background-color:#fff;
	}
	table{
		border:0px;
		width:900px;
	}
	.title h3{
		font-size:1.5em;
		color:rgb(0,200,80);
		text-shadow:0 0 4px deepskyblue;
	}
	a{
		display:inline-block;
		border-radius:3px;
		background-color:rgb(0,200,80);
		color:#fff;
		font-weight: bold;
		text-decoration: none;
	}
	.header_area a{
		width:100px;
		padding:10px;
		text-align:center;
	}
	.header_area span{
		font-weight:bold;
	}
	.btn_area a{
		text-align:center;
		padding:10px;
		width:100px;
	}
	.finput{
		position:absolute;
		left:-9999px;
		width:1px;
		height: 1px;
		padding: 0;
		margin: -1px;
	}
	
</style>
</head>
<body>
	<c:set var="cp" value="${pageContext.request.contextPath}"/>
	<c:if test="${loginUser == null}">
		<script>
			alert("로그인 후 이용하세요!");
			location.replace("${pageContext.request.contextPath}/user/UserLogin.us");
		</script>
	</c:if>
	<div style="margin:0 auto; width:1000px;">
		<!-- 로그아웃 버튼 배치할 테이블 -->
		<table class="header_area">
			<tr align="right" valign="middle">
				<td>
					<span>${loginUser.userid}님 환영합니다.</span>&nbsp;&nbsp;
					<a href="${cp}/user/UserLogoutOk.us">로그아웃</a>
				</td>			
			</tr>
		</table>

		<!-- 타이틀과 글 개수 띄워주는 테이블 -->
		<table class="title">
			<tr align="center" valign="middle">
				<td><h3>MVC 게시판</h3></td>
			</tr>
			
		</table>

		<form method="post" name="boardForm" action="${cp}/board/BoardModifyOk.bo" enctype="multipart/form-data"><!-- enctype="multipart/form-data" : 이 form에 있는 데이터들은 데이터 형태로 날릴거야(텍스트로 바꾸지 않고) -->
			<input type="hidden" value="${board.boardnum}" name="boardnum">	
			<!-- 게시글 작성하는 인풋들 배치 테이블 -->
			<table border="1" style="border-collapse:collapse;">
				<tr height="30px">
					<th align="center" width="150px">
						제 목
					</th>
					<td>
						<input name="boardtitle" size="50" maxlength="100" value="${board.boardtitle}">
					</td>
				</tr>
				<tr height="30px">
					<th align="center" width="150px">
						글쓴이
					</th>
					<td>
						<input name="userid" size="10" maxlength="20" readonly value="${board.userid}"><!-- readonly는 보통 value와 함께 쓰임 -->
					</td>
				</tr>
				<tr height="300px">
					<th align="center" width="150px">
						내 용
					</th>
					<td>
						<textarea name="boardcontents" style="width:700px;height:250px;resize:none;">${board.boardcontents}</textarea>					
					</td>
				</tr>
				<c:choose>
					<c:when test="${files != null and files.size() > 0 }">
						<c:forEach var ="i" begin="0" end="1">
							<tr>
								<th>첨부파일${i+1}</th>
								<td> 
									<c:choose>
										<%-- files : 원래 게시글에 업로드 되어있는 파일 DTO들의 리스트 --%>
										<%--choose안에는 무조건 when과 otherwise만 있어야 하므로 jsp주석을 사용해야함(html주석 쓰면 에러) --%>
										<c:when test="${i<files.size()}">
											<!-- 원래 업로드 되어있던 파일의 정보를 보여주는 쪽 								원래 올라와있던 파일명-->
											<span class="filename" name="filename${i+1}" id="file${i+1}">${files[i].orgname}</span>
											<!-- 새롭게 사용자가 올린 파일의 이름을 자바단으로 보내주기 위한 인풋 / 파일을 새롭게 올리지 않았다면 기존의 파일명이 그대로 날라간다.
												만약 새로운 파일을 업로드했다면, 업로드된 파일명이 value로 날라간다.
											-->
											<input type="hidden" name="filename" value="${files[i].orgname}">
										</c:when>
										<c:otherwise>
											<!-- 단순하게 파일 업로드 할 수 있도록 만들어주는 쪽 -->
											<span class="filename" name="filename${i+1}" id="file${i+1}"></span>
											<!-- 새롭게 업로드된 파일이 있다면, 그 파일의 이름이 날라간다. -->
											<input type="hidden" name="filename"><!-- inputtypehidden : 원래 올라와있던 파일의 이름들.(나중에 자바단으로 보내서 지워달라고 하려고) -->
										</c:otherwise>
									</c:choose>
									<!-- 첨부하기 버튼은 파일이 존재했든 안했든 무조건 보여줘야 하기 때문에 choose문 바깥에서 작성
										라벨을 클릭해서 파일을 업로드 하고자 하면, 아래에 있는 숨겨진 .finput 태그가 클릭되고
										파일 데이터가 그 인풋에 담기게 된다. 폼을 제출하게 되면 그 숨겨진 파일데이터가 자바단으로 날라감.
									 -->
									<label for="fileInput${i+1}">첨부하기</label>
									<input type="file" class="finput" id="fileinput${i+1}" name="file${i+1}">
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="2" style="text-align: center;"></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
			<br>
			<table class="btn_area">
				<tr align="right" valign="middle">
					<td>
						<a href="javascript:sendit()">수정완료</a>&nbsp;&nbsp;<!-- href 안에 자바스크립트 문법을 사용할 수 있음. -->
						<a href="${cp}/board/boardList.bo?page=${param.page == null?1:param.page}">목록</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	const sendit = function() {
		let boardForm = document.boardForm;
		let boardtitle = boardForm.boardtitle;
		if(boardtitle.value == "") {
			alert("게시글 제목을 작성하세요!");
			boardtitle.focus();
			return false;
		}
		let boardcontents = boardForm.boardcontents;
		if(boardcontents.value == "") {
			alert("게시글 내용을 작성하세요!");
			boardcontents.focus();
			return false;
		}
		boardForm.submit(); //자바스크립트를 submit하는 방법은 그 폼 객체를 찾아서 submit해주면 됨
	}
	$(document).ready(function() {
		$('.finput').change(function(e){ //.finput이라는 애가 무언가 바뀌었다면 이 함수 호출해줘
			//변화가 일어난 객체의 이전+이전 +이전요소.
			$(this).prev().prev().prev().text(e.target.filse[0].name); //여기서 e는 클릭됐을때의 이벤트고 그 타겟은 숨겨져 있는 파일임
				//지금 눌린애(this)의 이전,이전 그 내부의 내용에다가 text로 적어줘 
				//뭘로? 이 이벤트의 타겟에 올라가있는 파일중에 0번째꺼 그 파일의 이름으로.
			$(this).prev().prev().val(e.target.files[0].name);
		})
	})
</script>
</html>




