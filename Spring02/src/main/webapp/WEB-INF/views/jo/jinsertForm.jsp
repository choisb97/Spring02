<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Jo Insert Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>

<h2>** Jo Insert Spring02_MVC2 **</h2>
<form action="jinsert" method="post">
	<table width: 100%;">
		<tr height="40">
			<th bgcolor="LavenderBlush" style="text-align: center;">J N O</th>
			<td><input type="text" name="jno" id="jno" size="25" placeholder="Jno은(는) 숫자 5글자 이내"></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="LavenderBlush" style="text-align: center;">Chief</th>
			<td><input type="text" name="chief" id="chief" size="25" placeholder="영문 5글자 이상 입력"></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="LavenderBlush" style="text-align: center;">Jo_Name</th>
			<td><input type="text" name="jname" id="jname" size="25"></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="LavenderBlush" style="text-align: center;">Note</th>
			<td><input type="text" name="note" id="note" size="25"></td>
		</tr>
		
		<tr height="40">
			<td></td>
			<td>
				<input type="submit" value="등록">&nbsp;&nbsp;
				<input type="reset" value="취소">
			</td>
		</tr>
	</table>
</form>
<hr>


<c:if test="${not empty message}">
<hr>
	${message}<br>
</c:if>
<br>
&nbsp;&nbsp;&nbsp;<a href="jlist">[JoList]</a><br>
<br>
&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp;&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>