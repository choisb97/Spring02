<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Board List Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>

<h2>** Board List Spring02_MVC2 **</h2>
	<c:if test="${not empty message}">
		${message}<br>
	</c:if>
<hr>

<table width=100%;>
	<tr bgcolor="LemonChiffon" height="30" style="text-align: center;">
		<th>Seq</th>
		<th>Title</th>
		<th>I  D</th>
		<th>RegDate</th>
		<th>조회수</th>
	</tr>
	
	<c:if test="${not empty banana}">
		<c:forEach var="board" items="${banana}">
			<tr height="30">
				<td style="text-align: center;">${board.seq}</td>
				
				<td>
					<!-- 답글 등록 후 indent에 따른 들여쓰기
							=> 답글인 경우에만 적용 -->
					<c:if test="${board.indent > 0}"> <!-- indent가 0보다 크면 = 댓글이면 -->
						<c:forEach begin="1" end="${board.indent}">
							<span>&nbsp;&nbsp;</span>
						</c:forEach>
						<span style="color:Crimson">re.</span>
					</c:if>
				
					<!-- 로그인을 한 경우에만 title을 클릭하면 content를 볼 수 있도록 함 
							=> bDetail 실행 -->
					<c:if test="${not empty loginID}"> <!-- loginID가 있을 때(로그인을 한 경우) -->
						<a href="bdetail?seq=${board.seq}">${board.title}</a>
					</c:if>
					
					<c:if test="${empty loginID}"> <!-- loginID가 없을 때 -->
						${board.title}
					</c:if>
				</td>
				
				<td style="text-align: center;">
					<c:if test="${loginID == 'admin'}"> <!-- 로그인 ID가 admin일 때 -->
						<a href="mdetail?id=${board.id}">${board.id}</a>
					</c:if>
					
					<c:if test="${loginID != 'admin'}"> <!-- 로그인 ID가 admin이 아닐 때 -->
						${board.id}
					</c:if>
				</td>
				
				<td style="text-align: center;">${board.regdate}</td>
				<td style="text-align: center;">${board.cnt}</td>
			</tr>
		</c:forEach>
	</c:if>
</table>

<hr>

<c:if test="${not empty loginID}">
	&nbsp;&nbsp;&nbsp;<a href="binsertf">[새 게시글 등록하기]</a><br>
</c:if>
<br>
&nbsp;&nbsp;&nbsp;<a href="blist">[BoardList]</a><br>
<br>
&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp;&nbsp;&nbsp;<a href="home">[Home]</a><br>

</body>
</html>