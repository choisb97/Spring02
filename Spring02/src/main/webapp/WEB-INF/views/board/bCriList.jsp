<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Board Criteria_Page List Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
	<script src="resources/myLib/jquery-3.2.1.min.js"></script>
	<script>
		/* 
		 < ** JS에서 함수사용 >
		 => 최상위 객체 window는 생략 가능
		    - window.document.write(".......");
		 => JQuery 호출
		    - window.jQuery('Selector(선택자)_id, class, tag').click(function(){....})
		 	- 늘 사용해야되는 JQuery 대신 $ 기호를 사용 -> $('#searchBtn').click(function() {....})
		 => ready 이벤트
		 	- script 구문이 Body보다 위에 위치하면 Tag 인식 불가능.
		 	- Body가 Tag들을 모두 load한 후 실행되도록 이벤트 적용.
		 	- JS : window.onload
		 	- JQuery : ready -> $(선택자-document).ready(function() {....});
		 					 -> 이 때, "(document).ready"는 생략 가능.
		 */
		
		// ** JQuery 사용 -> (document).ready 생략됨.
		// * Body 아래에 적는 경우 $(function() {..})부분 없이 사용 가능.
		$(function() {
			
			// 1) SearchType이 '전체'이면 keyword 클리어
			$('#searchType').change(function() {
				if ($(this).val() == 'n')
					$('#keyword').val('');
			}); // change
			
			// 2) 검색 후 요청 처리
			// => 검색조건 입력 후 첫 Page 요청
			//    이 때는 서버에 searchType, keyword가 전달되기 이전이므로 makeQuery 메서드 사용.
			// => self.location="bcrilist?currPage=???" : 해당 요청을 서버로 전달
			$('#searchBtn').click(function() {
				self.location = "bcrilist" + "${pageMaker.makeQuery(1)}" 
					+ "&searchType=" + $('#searchType').val()
					+ "&keyword=" + $('#keyword').val()
			}) // click
			/*
			< ** self.location >
      		 (1) location 객체 직접사용 Test : url로 이동, 히스토리에 기록됨
      		 (2) location 객체의 메서드
     		  => href, replace('...'), reload() */
		}); // Ready
	</script>
</head>
<body>

<h2>** Board Criteria_Page List Spring02_MVC2 **</h2>
	<c:if test="${not empty message}">
		${message}<br>
	</c:if>
<hr>
<!-- 검색했을 때 검색창에 검색내용이나 타입이 변경되지 않게..? -->
<div id="searchBar">
	<select name="searchType" id="searchType">
	<!-- <option value="n" selected> 을 조건 (cri.searchType의 값)에 따라 작성하기 위한 삼항식 
         => value="n" : ~~~~.cri.searchType==null ? 'selected':''  첫화면 출력시 초깃값으로 selected
      -->
		<option value="n" ${pageMaker.cri.searchType == null ? 'selected' : '' }>전체(All)</option>
		<option value="t" ${pageMaker.cri.searchType == 't' ? 'selected' : '' }>Title</option>
		<option value="c" ${pageMaker.cri.searchType == 'c' ? 'selected' : '' }>Content</option>
		<option value="i" ${pageMaker.cri.searchType == 'i' ? 'selected' : '' }>ID</option>
		<option value="r" ${pageMaker.cri.searchType == 'r' ? 'selected' : '' }>RegDate</option>
		<option value="tc" ${pageMaker.cri.searchType == 'tc' ? 'selected' : '' }>Title or Content</option>
		<option value="tci" ${pageMaker.cri.searchType == 'tci' ? 'selected' : '' }>Title or Content or Writer</option>
	</select>
	
	<input type="text" name="keyword" id="keyword" value="${pageMaker.cri.keyword}">
	<button id="searchBtn">Search</button>
</div>
<br>
<hr>
   
<table width=100%>
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

<!-- ** Criteria_Page ** -->
<div align="center">
	<!-- First, Prev -->
	<c:choose>
		<c:when test="${pageMaker.prev && pageMaker.spageNo > 1}">
			<%-- ** Old Version **
			<a href="bcrilist?currPage=1&rowsPerPage=5">FIRST</a>&nbsp;
			<a href="bcrilist?currPage=${pageMaker.spageNo-1}&rowsPerPage=5">&lt;</a>&nbsp;&nbsp; --%>
			
			<!-- ** New Version01_makeQuery 사용 ** -->
		<%-- <a href="bcrilist${pageMaker.makeQuery(1)}">FIRST</a>&nbsp;
			<a href="bcrilist${pageMaker.makeQuery(pageMaker.spageNo-1)}">&lt;</a>&nbsp;&nbsp; --%>
			
			<!-- ** New Version02_searchQuery 사용 ** -->
			<a href="bcrilist${pageMaker.searchQuery(1)}">FIRST</a>&nbsp;
			<a href="bcrilist${pageMaker.searchQuery(pageMaker.spageNo-1)}">&lt;</a>&nbsp;&nbsp;
		</c:when>
		
		<c:otherwise>
			<font color="DimGray">FIRST&nbsp;&lt;&nbsp;&nbsp;</font>
		</c:otherwise>
	</c:choose>


	<!-- Display PageNo -->
	<c:forEach var="i" begin="${pageMaker.spageNo}" end="${pageMaker.epageNo}">
		<c:if test="${i == pageMaker.cri.currPage}">
			<font size="5" color="DarkOrange">${i}</font>&nbsp;
		</c:if>
		
		<c:if test="${i != pageMaker.cri.currPage}">
			<%-- ** Old Version ** 
			<a href="bcrilist?currPage=${i}&rowsPerPage=5">${i}</a>&nbsp; --%>
			
			<!-- ** New Version01_makeQuery 사용 ** -->
			<%-- <a href="bcrilist${pageMaker.makeQuery(i)}">${i}</a>&nbsp; --%>
			
			<!-- ** New Version02_searchQuery 사용 ** -->
			<a href="bcrilist${pageMaker.searchQuery(i)}">${i}</a>&nbsp;
		</c:if>
	</c:forEach>


	<!-- Next, Last -->
	<c:choose>
		<c:when test="${pageMaker.next && pageMaker.epageNo > 0}">
			<%-- ** Old Version **
			<a href="bcrilist?currPage=${pageMaker.epageNo+1}&rowsPerPage=5">&nbsp;&nbsp;&gt;</a>
			<a href="bcrilist?currPage=${pageMaker.lastPageNo}&rowsPerPage=5">&nbsp;LAST</a> --%>
			
			<!-- ** New Version01_makeQuery 사용 ** -->
		<%-- <a href="bcrilist${pageMaker.makeQuery(pageMaker.epageNo+1)}">&nbsp;&nbsp;&gt;</a>
			<a href="bcrilist${pageMaker.makeQuery(pageMaker.lastPageNo)}">&nbsp;LAST</a> --%>
			
			<!-- ** New Version02_searchQuery 사용 ** -->
			<a href="bcrilist${pageMaker.searchQuery(pageMaker.epageNo+1)}">&nbsp;&nbsp;&gt;</a>
			<a href="bcrilist${pageMaker.searchQuery(pageMaker.lastPageNo)}">&nbsp;LAST</a>
		</c:when>
		
		<c:otherwise>
			<font color="DimGray">&nbsp;&gt;&nbsp;&nbsp;LAST</font>
		</c:otherwise>
	</c:choose>

</div>
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