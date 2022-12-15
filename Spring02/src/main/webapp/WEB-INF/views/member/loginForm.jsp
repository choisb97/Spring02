<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** LoginForm Spring02_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
	<script src="resources/myLib/jquery-3.2.1.min.js"></script>
	<script src="resources/myLib/inCheck.js"></script>
	<script src="resources/myLib/axTest01.js"></script>
	<script>
		let iCheck = false;
		let pCheck = false;
		
		$(function() { // $(document).ready 생략
			$('#id').focus();

			$('#id').keydown(function(e) {
				if (e.which == 13) {
					e.preventDefault();
					$('#password').focus();
				}

			}).focusout(function() {
				iCheck = idCheck();
			});

		//-----------------------------------------------------------------------------------------

			$('#password').keydown(function(e) {
				if (e.which == 13) {
					e.preventDefault();
					$('#password2').focus();
				}

			}).focusout(function() {
				pCheck = pwCheck();
			});
		}); // ready
		
		function inCheck() {
			// => 무결성 확인 결과 submit 판단 & 실행
			// => 모든 항목에 오류가 없으면 : submit  -> return true 또는 default
			//    한 곳이라도 오류가 있으면 : submit 취소 -> return false
/*			if (iCheck == false) {
				$('#iMessage').html('ID를(을) 확인하세요');
			}

			if (pCheck == false) {
				$('#pMessage').html('Password를(을) 확인하세요');
			}
			
			if (iCheck && pCheck)
					return true; // submit 진행
			else
				return false; */

			// => Rest API Test 위해 잠시 주석처리 하고 return true   
			return true;
		
		} // inCheck
	</script>
</head>
<body>

<h2>** LoginForm Spring02_MVC2 **</h2>

<form action="login" method="post">
	<table style="width: 100;">
		<tr height="40">
			<th bgcolor="MediumSlateBlue" style="text-align: center;">I D</th>
			<td>
				<input type="text" name="id" id="id">
				<span id="iMessage" class="eMessage"></span>
			</td>
		</tr>
		
		<tr height="40">
			<th bgcolor="MediumSlateBlue" style="text-align: center;">PassWord</th>
			<td>
				<input type="password" name="password" id="password"><br> <!-- 12345! -->
				<span id="pMessage" class="eMessage"></span>
			</td>
		</tr>
		
		<tr>
			<td></td>
			<td><input type="submit" value="Login" onclick="return inCheck()">&nbsp;&nbsp;
			    <input type="reset" value="Reset">&nbsp;&nbsp;
			    <span id="axlogin" class="textLink">[AxLogin]</span>&nbsp;&nbsp;
				<span id="jslogin" class="textLink">[JsLogin]</span>
				<span id="rsdetail" class="textLink">axRSDetail</span>
			</td>
		</tr>
	</table>

</form>
<span id="message"></span><br>
<c:if test="${not empty message}">
<hr>
	${message}<br>
</c:if>
<hr>
&nbsp;&nbsp;<a href="javascript:history.go(-1)">[이전으로]</a>
&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>