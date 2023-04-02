<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${param.userid!=null?'환영합니다':'로그인' }</title>
</head>
<style>
	body{
		background-color:rgb(245,246,247);
	}
	#wrap{
		width:600px;
		margin:0 auto;
	}
	#wrap > tr, #wrap > td{
		padding:10px;
	}
	#wrap > tr{
		width:200px;
	}
	#wrap > td{
		width:350px;
	}
	input[type=text], input[type=password]{
		padding:10px;
		width:200px;
		margin-left:20px;
		border:1px solid #ccc;
		outline:none;
	}
	input[type=submit]{
		margin-top:30px;
		padding:10px 20px;
		width:100px;
		border:none;
		background-color:rgb(0,200,80);
		border-radius:5px;
		color:#fff;
		font-weight:bold;
		font-size:18px;
	}
</style>
<body>
	<c:if test="${not empty  param.login }">
		<script>
			alert("로그인 실패! 다시 시도해 주세요...");
		</script>
	</c:if>
	
	<div id="wrap">
		<form name="loginForm" action="${pageContext.request.contextPath}/user/UserLoginOk.us" method="post" onsubmit="sendit();">
			<table>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="userid" placeholder="아이디를 입력하세요"
						value="${param.userid==null?param.userid:''}">
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="password" name="userpw" placeholder="비밀번호를 입력하세요">
					</td>
				</tr>
				<tr>
					<th colspan="2"><input type="submit" value="로그인"></th>
				</tr>
			</table>
		</form>
	</div>
</body>
<script>
	let idTag = document.loginForm.userid;
	let pwTag = document.loginForm.userpw;
	function sendit(){
		if(idTag.value == ""){
			alert("아이디를 입력하세요!");
			idTag.focus();
			return false;
		}
		if(pwTag.value == ""){
			alert("비밀번호를 입력하세요!");
			pwTag.focus();
			return false;
		}
		return true;
	}
</script>
</html>











