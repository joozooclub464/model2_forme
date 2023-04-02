<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
</head>
<body>
	<c:set var="cp" value="${pageContext.request.contextPath}"/>
	<c:if test="${loginUser == null}">
		<script>
			alert("로그인 후 이용하세요!");
			location.replace("${pageContext.request.contextPath}/user/UserLogin.us");
		</script>
	</c:if>
	<c:if test="${not empty param.write }">
		<script>
			alert("게시글 작성 실패!");
		</script>
	</c:if>
	<c:set var="boardList" value="${requestScope.boardList}"/>
	<c:set var="page" value="${requestScope.page}"/>
	<c:set var="startPage" value="${requestScope.startPage}"/>
	<c:set var="endPage" value="${requestScope.endPage}"/>
	<c:set var="totalCnt" value="${requestScope.totalCnt}"/>
	<c:set var="totalPage" value="${requestScope.totalPage}"/>
	
	<div style="text-align:center;">
		<!-- 로그아웃 버튼 배치할 테이블 -->
		<table style="border:0px;width:900px;">
			<tr align="right" valign="middle">
				<td>
					${loginUser.userid}님 환영합니다. &nbsp;&nbsp;
					<a href="${cp}/user/UserLogoutOk.us">로그아웃</a>
				</td>			
			</tr>
		</table>

		<!-- 타이틀과 글 개수 띄워주는 테이블 -->
		<table style="width:900px;border:0px;">
			<tr align="center" valign="middle">
				<td><h3>MVC 게시판</h3></td>
			</tr>
			<tr align="right" valign="middle">
				<td>글 개수 : ${totalCnt}</td>
			</tr>
		</table>

		<!-- 게시글 리스트 띄우는 테이블 -->
		<table border="1" style="border-collapse:collapse; border-spacing:0; width:900px;">
			<tr align="center" valign="middle">
				<th width="8%">번호</th>
				<th width="50%">제목</th>
				<th width="15%">작성자</th>
				<th width="17%">날짜</th>
				<th width="10%">조회수</th>
			</tr>
			<c:choose>
				<c:when test="${boardList.size()>0 and boardList != null}">
					<c:forEach var="${board}" items="${boardList}"> <!-- 게시글이 없을 수도 있기 때문에 무조건 띄우는것이 아님 -->
						<tr class="board" id="board${board.boardnum }">
							<td>${board.boardnu}</td>
							<td><a href="${cp}/board/BoardView.bo?board.boardnum=${board.boardnum}&page=${page}">${board.boardtitle}</a></td>
							<td>${board.userid}</td>
							<td>${board.regdate}</td>
							<td>${board.readcount}</td>
						</tr>
		
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="5" style="text-align: center; font-size: 32px;">등록된 게시글이 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table><br>

		<!-- 페이징 처리하는 테이블 -->
		<table style="border:0px;width:900px">
			<tr align="center" valign="middle">
				<td>	
					<c:if test="${page>1}">
						<a href="${cp}/board/BoardList.bo?page=${page-1}">[${i}]</a>[&lt;]
					</c:if>
					<c:forEach begin="${startPage}" end="${endPage}" step="1" var="i">
						<c:choose>
							<c:when test="${page == i}">
								[${i}]
							</c:when>
							<c:otherwise>
								<a href="${cp}/board/BoardList.bo?page=${i}">[${i}]</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${page<totalPage}">
						<a href="${cp}/board/BoardList.bo?page=${i}">[${page+1}]</a>[&gt;]					
					</c:if>
				</td>
			</tr>
		</table> 
		<!-- 글쓰기 버튼 배치하는 테이블 -->
		<table style="border:0px; width:900px;">
			<tr align="right" valign="middle">
				<td><a class="write" href="${cp}/board/BoardWrite.bo?page=${param.page}">[글쓰기]</a></td>
			</tr>
		</table>
	</div>
</body>
</html>







