<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Board Update Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>

<h2>** Board Update Spring02_MVC2 **</h2>
=> Request Member_ID : ${param.id}<br>
<hr>
<form action="bupdate" method="post">
	<table>
		<tr height="40">
			<th bgcolor="DarkSeaGreen" style="text-align: center;">Seq</th>
			<td><input type="text" name="seq" value="${apple.seq}" size="25" readonly></td>
						<!-- 서버에서 필요한 정보 -->
		</tr>
		
		<tr height="40">
			<th bgcolor="DarkSeaGreen" style="text-align: center;">I  D</th>
			<td><input type="text" name="id" value="${apple.id}" size="25" readonly></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="DarkSeaGreen" style="text-align: center;">Title</th>
			<td><input type="text" name="title" value="${apple.title}" size="25"></td>
						<!-- 서버에서 필요한 정보 -->
		</tr>
		
		<tr height="40">
			<th bgcolor="DarkSeaGreen" style="text-align: center;">Content</th>
			<td> <textarea rows="5" cols="50" name="content">${apple.content}</textarea></td>
							<!-- 서버에서 필요한 정보 -->
		</tr>
		
		<tr height="40">
			<th bgcolor="DarkSeaGreen" style="text-align: center;">RegDate</th>
			<td><input type="text" name="regdate" value="${apple.regdate}" readonly></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="DarkSeaGreen" style="text-align: center;">조회수</th>
			<td><input type="text" name="cnt" value="${apple.cnt}" size="25" readonly></td>
		</tr>
		
		<tr>
			<td></td>
			<td>
				<input type="submit" value="글 수정">&nbsp;&nbsp;&nbsp;
				<input type="reset" value="취소">			
			</td>
		</tr>
	</table>
</form>

<c:if test="${not empty message}">
	${message}<br>
</c:if>
<hr>
<c:if test="${loginID == apple.id || loginID == 'admin'}">
	&nbsp;&nbsp;&nbsp;<a href="bdelete?seq=${apple.seq}&root=${apple.root}">[게시글 삭제]</a><br>
</c:if>

<br>
&nbsp;&nbsp;&nbsp;<a href="blist">[BoardList]</a><br>
<br>

&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>