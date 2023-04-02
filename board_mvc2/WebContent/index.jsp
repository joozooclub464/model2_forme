<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>최종 예제 게시판</title>
</head>
<body>
	<c:set var="cp" value="${pageContet.request.contextPath} "/>
	<!-- contextPath : 최상위 경로(루트경로)
		jsp파일에서 뭔가를 연결하거나 요청하거나 그럴 때에는 항상 앞에 (현재 접속하고있는)루트경로를 붙여줘야함. => 서버를 통해서 접속중이기 때문 
	-->
	<h2>최종 예제 게시판</h2>
	<a href="${cp}/user/UserJoin.us">회원가입</a><br>  <!-- join.us가 요청 명이고 그 요청의 성격을(user) 앞에 써줌 -->
	<a href="${cp}/user/UserLogin.us">로그인</a>
	<!-- 
		jsp에서 절대경로를 이용하는 이유   
		: 내가 웹페이지에서 보고 있는 화면이 이 jsp파일이 아님.(be jsp는 서블릿을 통해서 파일이 변환되고 그 서블릿을 통해 정적인 html파일이 출력되는 형태이기 때문)
		  즉 이 파일과 똑같이 생긴 html파일인것이기 때문에 상대경로를 쓰게 되면 문제가 생김(내 경로는 localhost:9090/이기 때문)
		  -> 절대경로 사용
	-->
	
</body>
</html>