<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** MemberDetail Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>

<h2>** MemberDetail Spring02_MVC2 **</h2>
=> Request Member_ID : ${param.id}<br>
<hr>

<c:if test="${not empty apple}">
	<table style="text-align: center;">
		<tr height="40">
			<th bgcolor="MediumPurple" style="text-align: center;">I D</th>
			<td>${apple.id}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumPurple" style="text-align: center;">Password</th>
			<td>${apple.password}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumPurple" style="text-align: center;">Name</th>
			<td>${apple.name}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumPurple" style="text-align: center;">Info</th>
			<td>${apple.info}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumPurple" style="text-align: center;">Birthday</th>
			<td>${apple.birthday}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumPurple" style="text-align: center;">Jno</th>
			<td>${apple.jno}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumPurple" style="text-align: center;">Age</th>
			<td>${apple.age}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumPurple" style="text-align: center;">Point</th>
			<td>${apple.point}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumPurple" style="text-align: center;">Image</th>
			<td><img src="${apple.uploadfile}" width="80" height="100"></td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty message}">
	${message}<br>
</c:if>

<br>
&nbsp;&nbsp;&nbsp;<a href="mdetail?jCode=U&id=${apple.id}">[내 정보 수정]</a>
&nbsp;&nbsp;&nbsp;<a href="mdelete?id=${apple.id}">[회원 탈퇴]</a>
&nbsp;&nbsp;&nbsp;<a href="jlist">[JoList]</a><br>
<br>
&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp; &nbsp; &nbsp; &nbsp;<a href="home">[Home]</a><br>
<br>
&nbsp;&nbsp;&nbsp;<a href="logout">[Logout]</a>

</body>
</html>