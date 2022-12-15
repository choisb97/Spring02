<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** MemberUpdate Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
	<script src="resources/myLib/jquery-3.2.1.min.js"></script>
</head>
<body>

<h2>** MemberUpdate Spring02_MVC2 **</h2>

<form action="mupdate" method="post" enctype="multipart/form-data">
	<table width: 100%;">
		<tr height="40">
			<th bgcolor="LightCoral" style="text-align: center;">I D</th>
			<td><input type="text" name="id" id="id" value="${apple.id}" size="25" readonly></td>
		</tr>
		
																			<!-- ** input Tag 입력 막기
																				=> disabled : 서버로 전송 안 됨
																				=> readonly : 서버로 전송 됨 -->
		<tr height="40">
			<th bgcolor="LightCoral" style="text-align: center;">Password</th>
			<td><input type="password" name="password" id="password" value="${apple.password}" size="25" placeholder="특수문자 반드시 포함" value="12345!"></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="LightCoral" style="text-align: center;">Name</th>
			<td><input type="text" name="name" id="name" value="${apple.name}" size="25"></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="LightCoral" style="text-align: center;">Info</th>
			<td><input type="text" name="info" id="info" value="${apple.info}" size="25"></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="LightCoral" style="text-align: center;">Birthday</th>
			<td><input type="date" name="birthday" id="birthday" value="${apple.birthday}"></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="LightCoral" style="text-align: center;">Jno</th>
			<td>
				<select name="jno" id="jno">
				<c:choose>
					<c:when test="${apple.jno == 1}">
						<option value="1" selected>1 : unique</option>
						<option value="2">2 : 천지창조</option>
						<option value="3">3 : 3조</option>
						<option value="4">4 : 4조</option>
						<option value="5">5 : do가자</option>
						<option value="6">6 : 김고정</option>
						<option value="9">9 : 관리자</option>
					</c:when>
					
					<c:when test="${apple.jno == 2}">
						<option value="1">1 : unique</option>
						<option value="2" selected>2 : 천지창조</option>
						<option value="3">3 : 3조</option>
						<option value="4">4 : 4조</option>
						<option value="5">5 : do가자</option>
						<option value="6">6 : 김고정</option>
						<option value="9">9 : 관리자</option>
					</c:when>
					
					<c:when test="${apple.jno == 3}">
						<option value="1">1 : unique</option>
						<option value="2">2 : 천지창조</option>
						<option value="3" selected>3 : 3조</option>
						<option value="4">4 : 4조</option>
						<option value="5">5 : do가자</option>
						<option value="6">6 : 김고정</option>
						<option value="9">9 : 관리자</option>
					</c:when>
					
					<c:when test="${apple.jno == 4}">
						<option value="1">1 : unique</option>
						<option value="2">2 : 천지창조</option>
						<option value="3">3 : 3조</option>
						<option value="4" selected>4 : 4조</option>
						<option value="5">5 : do가자</option>
						<option value="6">6 : 김고정</option>
						<option value="9">9 : 관리자</option>
					</c:when>
					
					<c:when test="${apple.jno == 5}">
						<option value="1">1 : unique</option>
						<option value="2">2 : 천지창조</option>
						<option value="3">3 : 3조</option>
						<option value="4">4 : 4조</option>
						<option value="5" selected>5 : do가자</option>
						<option value="6">6 : 김고정</option>
						<option value="9">9 : 관리자</option>
					</c:when>
					
					<c:when test="${apple.jno == 6}">
						<option value="1">1 : unique</option>
						<option value="2">2 : 천지창조</option>
						<option value="3">3 : 3조</option>
						<option value="4">4 : 4조</option>
						<option value="5">5 : do가자</option>
						<option value="6" selected>6 : 김고정</option>
						<option value="9">9 : 관리자</option>
					</c:when>
					
					<c:otherwise>
					 	<option value="1">1 : unique</option>
						<option value="2">2 : 천지창조</option>
						<option value="3">3 : 3조</option>
						<option value="4">4 : 4조</option>
						<option value="5">5 : do가자</option>
						<option value="6">6 : 김고정</option>
						<option value="9" selected>9 : 관리자</option>
					</c:otherwise>
				</c:choose>
				</select>
			</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="LightCoral" style="text-align: center;">Age</th>
			<td><input type="text" name="age" id="age" value="${apple.age}" placeholder="정수 입력"></td>
		</tr>
		
		<tr height="40">
			<th bgcolor="LightCoral" style="text-align: center;">Point</th>
			<td><input type="text" name="point" id="point" value="${apple.point}" placeholder="실수 입력"></td>
		</tr>
		
		<!-- Image Update 추가 
			=> tr Tag 추가, form Tag 속성(method="POST", enctype="multipart/form-data") 확인 -->
		<tr height="40">
			<th bgcolor="LightCoral" style="text-align: center;">Image</th>
			<td>
				<img src="${apple.uploadfile}" class="select_img" width="80" height="100">
				<input type="hidden" name="uploadfile" value="${apple.uploadfile}"><br>
				<input type="file" name="uploadfilef" id="uploadfilef">
				<script>
					$('#uploadfilef').change(function() {
						
						if(this.files && this.files[0]) {
							var reader = new FileReader;
							reader.readAsDataURL(this.files[0]);
							
							reader.onload = function(e) {
								$(".select_img").attr("src", e.target.result).width(100).height(100);
							} // onload_function
							
						} // if
	
					}); // change
				</script>
			</td>
		</tr>
		
		<tr height="40">
			<td></td>
			<td>
				<input type="submit" value="수정">&nbsp;&nbsp;
				<input type="reset" value="취소">
			</td>
		</tr>
	</table>
</form>

<c:if test="${not empty message}">
	<hr>
	${message}<br>
</c:if>

&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp; &nbsp; &nbsp;<a href="home">[Home]</a>

</body>
</html>