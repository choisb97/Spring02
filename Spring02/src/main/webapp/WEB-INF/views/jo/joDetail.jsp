<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** JoDetail Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>

<h2>** JoDetail Spring02_MVC2 **</h2>
=> Request MemJo_Jno : ${param.jno}<br>

<hr>

<c:if test="${not empty apple}">
	<table style="text-align: center;">
		<tr height="40">
			<th bgcolor="AliceBlue" style="text-align: center;">J N O</th>
			<td>${apple.jno}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="AliceBlue" style="text-align: center;">Chief</th>
			<td>${apple.chief}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="AliceBlue" style="text-align: center;">Jo_Name</th>
			<td>${apple.jname}</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="AliceBlue" style="text-align: center;">Note</th>
			<td>${apple.note}</td>
		</tr>
	</table>
</c:if>

<hr>

<h3>** ${apple.jno}조 MemberList **</h3>
<table width=100%; style="text-align: center;">
	<tr bgcolor="Thistle" height="30">
		<th>I D</th>
		<th>Password</th>
		<th>Name</th>
		<th>Info</th>
		<th>Birthday</th>
		<th>Jno</th>
		<th>Age</th>
		<th>Point</th>
	</tr>
	
	<c:if test="${not empty banana}">
		<c:forEach var="member" items="${banana}">
			<tr height="30">
				<td>
				<c:if test="${loginID == 'admin'}">
					<a href="mdetail?id=${member.id}">${member.id}</a>
				</c:if>
				
				<c:if test="${loginID != 'admin'}">
					${member.id}
				</c:if>
				</td>
				<td>${member.password}</td>
				<td>${member.name}</td>
				<td>${member.info}</td>
				<td>${member.birthday}</td>
				<td>${member.jno}</td>
				<td>${member.age}</td>
				<td>${member.point}</td>
			</tr>
		</c:forEach>
	</c:if>
</table>

<hr>
<c:if test="${not empty message}">
	${message}<br>
</c:if>

<c:if test="${not empty loginID}"> 
<br>
&nbsp;&nbsp;&nbsp;<a href="jdetail?jCode=U&jno=${apple.jno}">[그룹 수정]</a>
&nbsp; &nbsp; &nbsp; &nbsp;<a href="jdelete?jno=${apple.jno}">[그룹 삭제]</a>
</c:if>
&nbsp; &nbsp;&nbsp;<a href="jlist">[JoList]</a><br>
<br>
&nbsp;&nbsp;&nbsp;<a href="jinsertf">[새 그룹 등록]</a>
&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp; &nbsp; &nbsp;<a href="home">[Home]</a>

</body>
</html>