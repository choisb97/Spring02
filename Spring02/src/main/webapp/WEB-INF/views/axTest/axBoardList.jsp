<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Ajax_BoardList Spring02_Mybatis **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
	<script src="resources/myLib/axTest01.js"></script>
	<script src="resources/myLib/axTest03.js"></script>
</head>
<body>

<!--  
 ** 반복문에 이벤트 적용
 	=> 매개변수 처리에 varStatus 활용, ajax, json Test
 	=> Login 여부에 무관하게 처리 함.
 	
 	Test 1. 타이틀 클릭하면 하단(resultArea2)에 글 내용 출력하기 -> a_Tag, JS, jsBDetail1()
 	
 	Test 2. 타이틀 클릭하면, 글목록의 아래(span result)에 글 내용 출력하기 -> a_Tag, JS, jsBDetail2( , )
 	
 	Test 3. seq에 마우스 오버 시 별도의 DIV에 글 내용 표시 되도록 하기
 			-> jQuery : id, class, this
 			-> seq의 <td>에 마우스오버 이벤트
 			-> content를 표시할 div (table 바깥에) : 표시 / 사라짐 
 			-> 마우스 포인터의 위치를 이용해서 div의 표시위치 지정
-->

<h2>** Ajax_BoardList Spring02_Mybatis **</h2>
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
		<c:forEach var="board" items="${banana}" varStatus="vs">
			<tr height="30">
			
				<!-- Test 3. seq에 마우스 오버 시 DIV에 글 내용 표시 되도록 하기 -->
				<td style="text-align: center;" id="${board.seq}" class="qqq textLink">${board.seq}</td>
				
				<td>
					<!-- 답글 등록 후 indent에 따른 들여쓰기
							=> 답글인 경우에만 적용 -->
					<c:if test="${board.indent > 0}"> <!-- indent가 0보다 크면 = 댓글이면 -->
						<c:forEach begin="1" end="${board.indent}">
							<span>&nbsp;&nbsp;</span>
						</c:forEach>
						<span style="color:Crimson">re.</span>
					</c:if>
					<!-- Test 1. 타이틀 클릭하면 하단(resultArea2)에 글 내용 출력하기 -> a_Tag, JS, jsBDetail1(  ) -->
					<%-- <a href="#resultArea2" onclick="jsBDetail1(${board.seq})">${board.title}</a> --%>
					
					<!-- Test 2. 타이틀 클릭하면, 글목록의 아래(span result)에 글 내용 출력하기 -> a_Tag, JS, jsBDetail2( , ) 
						 => <tr> </tr> 추가 후 <span> 내용 표시
						 => 이 <span>의 id 속성 값으로 반복문의 index 또는 count 이용하기
						 => scroll 정지 : "javascript:;", "javascript:void(0);" 동일 효과
						 => Toggle 방식으로 없을 때 클릭하면 표시되고, 있을 때 클릭하면 사라짐
						 => 새로운 글을 클릭하면 이전 글의 컨텐츠는 사라짐
						 => 만약 출력할 content의 내용이 없으면 아무것도 나타나지 않는다(공백의 span은 표시 X)
						
						 ** function에 이벤트 객체 전달
						 => 이벤트 핸들러의 첫 번째 매개변수에 event라는 이름으로 전달 함.
					-->
 					
 					<%-- <a href="#javascript:;" onclick="jsBDetail2(${board.seq}, ${vs.count})">${board.title}</a> --%>
 					
 					<!-- Event 적용 -->
 					<a href="#javascript:;" onclick="jsBDetail2(event, ${board.seq}, ${vs.count})">${board.title}</a>
					
				</td>
				
				<td style="text-align: center;">${board.id}</td>
				<td style="text-align: center;">${board.regdate}</td>
				<td style="text-align: center;">${board.cnt}</td>
			</tr>
			
			<tr>
				<td></td>
				<td colspan="4">
					<span class="content" id="${vs.count}"></span>
											<!-- index : 0부터 / count : 1부터  -->
				</td>
			</tr>
			
		</c:forEach>
	</c:if>
</table>

<div id="content">

</div>

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