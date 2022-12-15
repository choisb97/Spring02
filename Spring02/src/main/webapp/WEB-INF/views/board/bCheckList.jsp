<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Board Check List Spring02_Mybatis **</title>
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

<h2>** Board Check List Spring02_Mybatis **</h2>
	<c:if test="${not empty message}">
		${message}<br>
	</c:if>
	
<!-- Board checkList에 SrearchCri 적용하여 Paging 기능 추가 -->
<div id="searchBar">
<!-- 
	검색 후에도 조건을 유지하고 취소 버튼 클릭 시 조건만 clear 되도록 function checkClear() 추가
	(reset 버튼은 기본적으로 새로고침과 동일하게 처리되어 ${check}로 전달된 조건이 계속 적용되기 때문
-->
	<form action="bchecklist" method="get">
		<b>ID : </b>
		<c:set var="ckPrint" value="false" />
		<c:forEach var="id" items="${pageMaker.cri.check}">
			<c:if test="${id == 'admin'}">
				<input type="checkbox" name="check" value="admin" checked class="clear">관리자 &nbsp;
				<c:set var="ckPrint" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${not ckPrint}">
			<input type="checkbox" name="check" value="admin">관리자 &nbsp;
		</c:if>
		
		<c:set var="ckPrint" value="false" />
		<c:forEach var="id" items="${pageMaker.cri.check}">
			<c:if test="${id == 'snowman'}">
				<input type="checkbox" name="check" value="snowman" checked class="clear">우영우 &nbsp;
				<c:set var="ckPrint" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${not ckPrint}">
			<input type="checkbox" name="check" value="snowman">우영우 &nbsp;
		</c:if>
		
		<c:set var="ckPrint" value="false" />
		<c:forEach var="id" items="${pageMaker.cri.check}">
			<c:if test="${id == 'ggumsb'}">
				<input type="checkbox" name="check" value="ggumsb" checked class="clear">미래해적왕 &nbsp;
				<c:set var="ckPrint" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${not ckPrint}">
			<input type="checkbox" name="check" value="ggumsb">미래해적왕 &nbsp;
		</c:if>
		
		<c:set var="ckPrint" value="false" />
		<c:forEach var="id" items="${pageMaker.cri.check}">
			<c:if test="${id == 'brownie'}">
				<input type="checkbox" name="check" value="brownie" checked class="clear">브부장 &nbsp;
				<c:set var="ckPrint" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${not ckPrint}">
			<input type="checkbox" name="check" value="brownie">브부장 &nbsp;
		</c:if>
		
		<c:set var="ckPrint" value="false" />
		<c:forEach var="id" items="${pageMaker.cri.check}">
			<c:if test="${id == 'CHOI'}">
				<input type="checkbox" name="check" value="CHOI" checked class="clear">초이 &nbsp;
				<c:set var="ckPrint" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${not ckPrint}">
			<input type="checkbox" name="check" value="CHOI">초이 &nbsp;
		</c:if>
		
		<input type="submit" value="Search"> &nbsp;
		<input type="reset" value="Cancel" onclick="checkClear()">
	</form>
</div>
<br>
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
		<tr height="50">
			<td colspan="4"></td>
			<td>* 검색결과 : ${pageMaker.totalRowsCount} 건</td>
		</tr>
	</c:if>
	
	<c:if test="${empty banana}">
		<tr height="30">
			<td colspan="5">** 출력 자료가 한 건도 없습니다 **</td>
		</tr>
	</c:if>
</table>
<hr>

<!-- SearchCri_Page -->
<div align="center">
	<!-- First, Prev -->
	<c:choose>
		<c:when test="${pageMaker.prev && pageMaker.spageNo>1}">
			<a href="bchecklist${pageMaker.searchQuery(1)}">FP</a>&nbsp;
			<a href="bchecklist${pageMaker.searchQuery(pageMaker.spageNo-1)}">&lt;</a>&nbsp;&nbsp; 
		</c:when>
		
		<c:otherwise>
			<font color="Gray">FF&nbsp;&lt;&nbsp;&nbsp;</font>   
		</c:otherwise>
	</c:choose>	
	<!-- Displag PageNo -->
	<c:forEach  var="i" begin="${pageMaker.spageNo}" end="${pageMaker.epageNo}">
		<c:if test="${i==pageMaker.cri.currPage}">
			<font size="5" color="Orange">${i}</font>&nbsp;
		</c:if>
		
		<c:if test="${i!=pageMaker.cri.currPage}">
			<a href="bchecklist${pageMaker.searchQuery(i)}">${i}</a>&nbsp;
		</c:if>
	</c:forEach>
	<!-- Next, Last -->
	<c:choose>
		<c:when test="${pageMaker.next && pageMaker.epageNo>0}">
			<!-- New_ver01_Cri -->
			<a href="bchecklist${pageMaker.searchQuery(pageMaker.epageNo+1)}">&nbsp;&gt;</a>  
			<a href="bchecklist${pageMaker.searchQuery(pageMaker.lastPageNo)}">&nbsp;LP</a> 
		</c:when>
		
		<c:otherwise>
			<font color="Gray">&nbsp;&gt;&nbsp;LP</font>   
		</c:otherwise>
	</c:choose>

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