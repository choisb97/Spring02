<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** MemberList Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>

<h2>** MemberList Spring02_MVC2 **</h2>
<hr>
	<c:if test="${not empty message}">
		${message}<br>
	</c:if>

<table width=100%; style="text-align: center;">
	<tr bgcolor="hotpink" height="30">
		<th>I D</th>
		<th>Password</th>
		<th>Name</th>
		<th>Info</th>
		<th>Birthday</th>
		<th>Jno</th>
		<th>Age</th>
		<th>Point</th>
		<th>Image</th>
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
				<td><fmt:formatNumber value="${member.point}" pattern="#,###.##" /></td>
				<td><img src="${member.uploadfile}" width="50" height="60"></td>
			</tr>
		</c:forEach>
	</c:if>

</table>
<hr>
&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp;&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>