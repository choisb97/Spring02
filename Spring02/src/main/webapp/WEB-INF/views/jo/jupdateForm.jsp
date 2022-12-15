<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Jo Update Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>

<h2>** Jo Update Spring02_MVC2 **</h2>
<hr>
<form action="jupdate" method="post">
	<table>
		<tr height="40">
			<th bgcolor="MediumAquaMarine" style="text-align: center;">J N O</th>
			<td><input type="text" name="jno" value="${apple.jno}" size="25" readonly></td>
						<!-- 서버에서 필요한 정보 -->
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumAquaMarine" style="text-align: center;">Chief</th>
			<td><input type="text" name="chief" value="${apple.chief}" size="25"></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumAquaMarine" style="text-align: center;">Jo_Name</th>
			<td><input type="text" name="jname" value="${apple.jname}" size="25"></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumAquaMarine" style="text-align: center;">Note</th>
			<td><input type="text" name="note" value="${apple.note}"></td>
		</tr>

		<tr>
			<td></td>
			<td>
				<input type="submit" value="수정">&nbsp;&nbsp;&nbsp;
				<input type="reset" value="취소">			
			</td>
		</tr>
	</table>
</form>

<c:if test="${not empty message}">
	${message}<br>
</c:if>

<br>
&nbsp;&nbsp;&nbsp;<a href="jlist">[JoList]</a><br>
<br>

&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>