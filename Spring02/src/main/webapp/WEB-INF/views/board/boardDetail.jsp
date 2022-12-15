<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Board Detail Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>

<h2>** Board Detail Spring02_MVC2 **</h2>
=> Request Member_ID : ${param.id}<br>

<c:if test="${not empty message}">
	${message}<br>
</c:if>
<hr>

<c:if test="${not empty apple}">
	<table>
		<tr height="40">
			<th bgcolor="DarkKhaki" style="text-align: center;">Seq</th>
			<td>${apple.seq}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="DarkKhaki" style="text-align: center;">I  D</th>
			<td>${apple.id}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="DarkKhaki" style="text-align: center;">Title</th>
			<td>${apple.title}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="DarkKhaki" style="text-align: center;">Content</th>
			<td> <textarea rows="5" cols="50" readonly>${apple.content}</textarea></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="DarkKhaki" style="text-align: center;">RegDate</th>
			<td>${apple.regdate}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="DarkKhaki" style="text-align: center;">조회수</th>
			<td>${apple.cnt}</td>
		</tr>
	</table>
</c:if>

<hr>
<c:if test="${loginID == apple.id || loginID == 'admin'}">
	&nbsp;&nbsp;&nbsp;<a href="bdetail?jCode=U&seq=${apple.seq}">[게시글 수정]</a>
	&nbsp;&nbsp;&nbsp;<a href="bdelete?seq=${apple.seq}&root=${apple.root}">[게시글 삭제]</a>
											<!-- root 추가 : 삭제 시 원글 삭제 or 댓글 삭제 확인을 위함 -->
</c:if>

<c:if test="${not empty loginID}"> <!-- 로그인ID가 비어있지 않다면 = 로그인을 했다면 -->
	&nbsp;&nbsp;&nbsp;<a href="rinsertf?root=${apple.root}&step=${apple.step}&indent=${apple.indent}">[댓글 달기]</a><br>
</c:if>
<br>
&nbsp;&nbsp;&nbsp;<a href="blist">[BoardList]</a>
&nbsp; &nbsp; &nbsp; &nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>