<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Member Check List Spring02_Mybatis **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
	<script src="resources/myLib/jquery-3.2.1.min.js"></script>
	<script>
	
		function checkClear() {
			$('.clear').attr('checked', false);
			return false; // reset의 기본 onclick 취소
		} // checkClear
		
	</script>
</head>
<body>

<h2>** Member Check List Spring02_Mybatis **</h2>
<hr>
	<c:if test="${not empty message}">
		${message}<br>
	</c:if>
	
<div id="searchBar">
<!-- 
	검색 후에도 조건을 유지하고 취소 버튼 클릭 시 조건만 clear 되도록 function checkClear() 추가
	(reset 버튼은 기본적으로 새로고침과 동일하게 처리되어 ${check}로 전달된 조건이 계속 적용되기 때문
-->
	<form action="mchecklist" method="post">
		<b>JO_Number : </b>
		<c:set var="ckPrint" value="false" />
		<c:forEach var="jno" items="${check}">
			<c:if test="${jno == 1}">
				<input type="checkbox" name="check" value="1" checked class="clear">1조 &nbsp;
				<c:set var="ckPrint" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${not ckPrint}">
			<input type="checkbox" name="check" value="1">1조 &nbsp;
		</c:if>
		
		<c:set var="ckPrint" value="false" />
		<c:forEach var="jno" items="${check}">
			<c:if test="${jno == 2}">
				<input type="checkbox" name="check" value="2" checked class="clear">2조 &nbsp;
				<c:set var="ckPrint" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${not ckPrint}">
			<input type="checkbox" name="check" value="2">2조 &nbsp;
		</c:if>
		
		<c:set var="ckPrint" value="false" />
		<c:forEach var="jno" items="${check}">
			<c:if test="${jno == 3}">
				<input type="checkbox" name="check" value="3" checked class="clear">3조 &nbsp;
				<c:set var="ckPrint" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${not ckPrint}">
			<input type="checkbox" name="check" value="3">3조 &nbsp;
		</c:if>
		
		<c:set var="ckPrint" value="false" />
		<c:forEach var="jno" items="${check}">
			<c:if test="${jno == 4}">
				<input type="checkbox" name="check" value="4" checked class="clear">4조 &nbsp;
				<c:set var="ckPrint" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${not ckPrint}">
			<input type="checkbox" name="check" value="4">4조 &nbsp;
		</c:if>
		
		<c:set var="ckPrint" value="false" />
		<c:forEach var="jno" items="${check}">
			<c:if test="${jno == 5}">
				<input type="checkbox" name="check" value="5" checked class="clear">5조 &nbsp;
				<c:set var="ckPrint" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${not ckPrint}">
			<input type="checkbox" name="check" value="5">5조 &nbsp;
		</c:if>
		
		<c:set var="ckPrint" value="false" />
		<c:forEach var="jno" items="${check}">
			<c:if test="${jno == 6}">
				<input type="checkbox" name="check" value="6" checked class="clear">6조 &nbsp;
				<c:set var="ckPrint" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${not ckPrint}">
			<input type="checkbox" name="check" value="6">6조 &nbsp;
		</c:if>
		
		<input type="submit" value="Search"> &nbsp;
		<input type="reset" value="Cancel" onclick="checkClear()">
	</form>
</div>
<br>
<hr>

<table width=100%; style="text-align: center;">
	<tr bgcolor="CornflowerBlue" height="30">
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
				<td>${member.point}</td>
				<td><img src="${member.uploadfile}" width="50" height="60"></td>
			</tr>
		</c:forEach>
	</c:if>
	
	<c:if test="${empty banana}">
		<tr height="30">
			<td colspan="9">** 출력 자료가 한 건도 없습니다 **</td>
		</tr>
	</c:if>

</table>
<hr>
&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp;&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>