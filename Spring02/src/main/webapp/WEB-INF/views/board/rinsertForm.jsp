<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Reply Insert Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>

<h2>** Reply Insert Spring02_MVC2 **</h2>

<form action="rinsert" method="post">

	<table>
		<tr height="40">
			<th bgcolor="LightSlateGray" style="text-align: center;">I D</th>
			<td><input type="text" name="id" value="${loginID}" size="20" readonly></td>
		</tr>

		<tr height="40">
			<th bgcolor="LightSlateGray" style="text-align: center;">Title</th>
			<td><input type="text" name="title" placeholder="반드시 입력하세요" size="20"></td>
		</tr>

		<tr height="40">
			<th bgcolor="LightSlateGray" style="text-align: center;">Content</th>
			<td><textarea rows="5" cols="50" name="content"></textarea></td>
		</tr>
		
		<tr height="40"> <!-- 댓글 등록 시 필요한 부모글의 root, step, indent 전달 위함
											-> 매핑메서드의 자로 정의된 vo와 param 모두 가능 -->
			<td></td>
			<td>                                 <!-- 필요하지만 보이지 않게 하기 위해 hidden으로 숨기기 -->
				<input type="text" name="root" value="${param.root}" hidden>
				<input type="text" name="step" value="${param.step}" hidden>
				<input type="text" name="indent" value="${param.indent}" hidden>
				<!-- 
				<input type="text" name="root" value="${boardVO.root}" hidden>
				<input type="text" name="step" value="${boardVO.step}" hidden>
				<input type="text" name="indent" value="${boardVO.indent}" hidden>
										-> 매핑메서드의 자로 정의된 vo와 param 모두 가능
				 -->
			</td>
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

&nbsp;&nbsp;&nbsp;<a href="blist">[BoardList]</a><br>
<br>
&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp;&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>