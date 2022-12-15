<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** JoList Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>

<h2>** JoList Spring02_MVC2 **</h2>
<hr>
	<c:if test="${not empty message}">
		${message}<br>
	</c:if>

<table width=100%; style="text-align: center;">
	<tr bgcolor="MediumSlateBlue" height="30">
		<th>JNo.</th>
		<th>Chief</th>
		<th>C_Name</th>
		<th>Jo_Name</th>
		<th>Note</th>
	</tr>
	
	<c:if test="${not empty banana}">
		<c:forEach var="memjo" items="${banana}">
			<tr height="30">
				<td>
				<c:if test="${loginID == 'admin'}">
					<a href="jdetail?jno=${memjo.jno}">${memjo.jno}</a>
				</c:if>
				
				<c:if test="${loginID != 'admin'}">
					${memjo.jno}
				</c:if>
				</td>
				
				<td>
				<c:if test="${loginID == 'admin'}">
					<a href="mdetail?id=${memjo.chief}">${memjo.chief}</a>
				</c:if>
				
				<c:if test="${loginID != 'admin'}">
					${memjo.chief}
				</c:if>
				</td>
				
				<td>${memjo.name}</td>
				<td>${memjo.jname}</td>
				<td>${memjo.note}</td>
			</tr>
		</c:forEach>
	</c:if>
</table>

<hr>

<c:if test="${not empty loginID}">
	&nbsp;&nbsp;&nbsp;<a href="jinsertf">[새 그룹 등록]</a><br>
</c:if>
<br>
&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp;&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>