<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Board Insert Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>

<h2>** Board Insert Spring02_MVC2 **</h2>

<form action="binsert" method="post">

	<table>
		<tr height="40">
			<th bgcolor="YellowGreen" style="text-align: center;">I D</th>
			<td><input type="text" name="id" value="${loginID}" size="20" readonly></td>
		</tr>

		<tr height="40">
			<th bgcolor="YellowGreen" style="text-align: center;">Title</th>
			<td><input type="text" name="title" placeholder="반드시 입력하세요" size="20"></td>
		</tr>

		<tr height="40">
			<th bgcolor="YellowGreen" style="text-align: center;">Content</th>
			<td><textarea rows="5" cols="50" name="content"></textarea></td>
		</tr>
		
		<tr>
			<td></td>
			<td><input type="submit" value="등록">&nbsp;&nbsp;&nbsp;
				<input type="reset" value="취소">
			</td>
		</tr>
	</table>

</form>

<hr>
<c:if test="${not empty message}">
	${message}<br>
</c:if>
<br>
&nbsp;&nbsp;&nbsp;<a href="blist">[BoardList]</a><br>
<br>
&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp;&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>