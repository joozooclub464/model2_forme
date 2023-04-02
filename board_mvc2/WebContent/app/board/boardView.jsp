<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
	<c:if test="${not empty write}">
		<script>
			alert("게시글 작성 완료!");
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
				<td><h3><a href="${cp}/board/BoardList.bo">MVC 게시판</a></h3></td>
			</tr>
			
		</table>

		<form method="post" name="boardForm" action="${cp}/board/BoardWriteOk.bo" >	
			<!-- 게시글 작성하는 인풋들 배치 테이블 -->
			<table border="1" style="border-collapse:collapse;">
				<tr height="30px">
					<th align="center" width="150px">
						제 목
					</th>
					<td>
						<input name="boardtitle" size="50" maxlength="100" readonly value="${board.boardtitle}">
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
						<textarea name="boardcontents" style="width:700px;height:250px;resize:none;" readonly>${board.boardcontents}</textarea>					
					</td>
				</tr>
				<tr>
					<th>파일 첨부1</th>
					<td>
						<input type="file" name='file1'>
						<input type="button" value="첨부삭제">
					</td>
				</tr>
				<tr>
					<th>파일 첨부2</th>
					<td>
						<input type="file" name="file2">
						<input type="button" value="첨부삭제">
					</td>
				</tr>
				<c:choose>
					<c:when test="${files != null and files.size() > 0}">
						<c:forEach var="i" begin ="0" end="${files.size()-1}">
							<tr>
								<th>첨부파일${i+1}</th>
								<td>
									<a href="${cp}/board/FileDownLoad.bo?filename=${files[i].systemname}">${files[i].orgname}</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="2" style="text-align:center;font-size:20;">첨부 파일이 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
			<br>
			<table class="btn_area">
				<tr align="right" valign="middle">
					<td>
						<c:if test="${board.userid == loginUser.userid}">
							<a href="${cp}/board/BoardModify.bo?boardnum=${board.boardnum}">수정</a>&nbsp;&nbsp;
							<a href="#">삭제</a>&nbsp;&nbsp;
						</c:if>
						<a href="${cp}/board/boardList.bo?page=${param.page==null?1:param.page}">목록</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
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

</script>
</html>