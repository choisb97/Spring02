<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Member Criteria_Page List Spring02_MVC2 **</title>
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
				self.location = "mcrilist" + "${pageMaker.makeQuery(1)}" 
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

<h2>** Member Criteria_Page List Spring02_MVC2 **</h2>
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
		<option value="i" ${pageMaker.cri.searchType == 'i' ? 'selected' : '' }>ID</option>
		<option value="a" ${pageMaker.cri.searchType == 'a' ? 'selected' : '' }>Name</option>
		<option value="f" ${pageMaker.cri.searchType == 'f' ? 'selected' : '' }>Info</option>
		<option value="j" ${pageMaker.cri.searchType == 'j' ? 'selected' : '' }>Jno</option>
		<option value="b" ${pageMaker.cri.searchType == 'b' ? 'selected' : '' }>BirthDay</option>
		<option value="in" ${pageMaker.cri.searchType == 'in' ? 'selected' : '' }>ID or Name</option>
	</select>
	
	<input type="text" name="keyword" id="keyword" value="${pageMaker.cri.keyword}">
	<button id="searchBtn">Search</button>
</div>
<br>
<hr>
   
<table width=100% style="text-align: center;">
	<tr bgcolor="hotpink" height="30" style="text-align: center;">
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

<!-- ** Criteria_Page ** -->
<div align="center">
	<!-- First, Prev -->
	<c:choose>
		<c:when test="${pageMaker.prev && pageMaker.spageNo > 1}">
			<!-- ** New Version02_searchQuery 사용 ** -->
			<a href="mcrilist${pageMaker.searchQuery(1)}">FIRST</a>&nbsp;
			<a href="mcrilist${pageMaker.searchQuery(pageMaker.spageNo-1)}">&lt;</a>&nbsp;&nbsp;
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
			<!-- ** New Version02_searchQuery 사용 ** -->
			<a href="mcrilist${pageMaker.searchQuery(i)}">${i}</a>&nbsp;
		</c:if>
	</c:forEach>


	<!-- Next, Last -->
	<c:choose>
		<c:when test="${pageMaker.next && pageMaker.epageNo > 0}">
			<!-- ** New Version02_searchQuery 사용 ** -->
			<a href="mcrilist${pageMaker.searchQuery(pageMaker.epageNo+1)}">&nbsp;&nbsp;&gt;</a>
			<a href="mcrilist${pageMaker.searchQuery(pageMaker.lastPageNo)}">&nbsp;LAST</a>
		</c:when>
		
		<c:otherwise>
			<font color="DimGray">&nbsp;&gt;&nbsp;&nbsp;LAST</font>
		</c:otherwise>
	</c:choose>

</div>
<br>
&nbsp;&nbsp;&nbsp;<a href="mlist">[MemberList]</a><br>
<br>


</body>
</html>