<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** ID 중복 확인 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
	<script src="resources/myLib/jquery-3.2.1.min.js"></script>
	<script src="resources/myLib/inCheck.js"></script>
	<script>
		// ** idOK() : 사용자가 입력한 ID를 사용 가능하도록 해주고, 현재(this)창은 close
		// 1) this 창의 ID를 부모창의 ID로
		// 2) 부모창의 ID 중복확인 버튼을 disable & submit은 enable
		// 3) 부모창의 ID는 수정불가(ReadOnly), password에 focus
		// 4) 현재(this)창은 close
	
		function idOK() {
			// 1)
			//opener.document.getElementById('id').value='${newId}';
			opener.$('#id').val('${newId}'); // 위와 동일
			// => <script>에서 EL은 문자열 Type 내부에서 사용 가능(문자열 없이 그냥 EL 사용 불가)
			
			// 2)
			// ** JS ver.
			//opener.document.getElementById('submit').disabled = ''; // '' -> enable로 변경
			//opener.document.getElementById('idDup').disabled = 'disabled'; // 'disabled' -> disable로 변경
			
		// ---------------------------------------------------------------------------------------------------------
			
			// ** JQ ver.
			// => attr, prop 비교
			// => attr()는 HTML의 속성(Attribute), 기능, 입력된 값을 취급
			// => prop()은 JavaScript DOM 객체의 프로퍼티(Property), 실제 값, property가 가지는 본연의 값
			//opener.$('#submit').attr('disabled', ''); // 적용 안 됨.
			opener.$('#submit').attr('disabled', false); // 적용 됨.
			opener.$('#idDup').attr('disabled', 'disabled');

			//opener.$('#submit').prop('disabled', false); // boolean type으로 작성해야 됨
			//opener.$('#idDup').prop('disabled', true);
			
			// 3)
			// => id가 확정되었으므로 수정 불가 -> readonly
			// => attr, prop 비교
			//opener.$('#id').attr('readonly', 'readonly');
			opener.$('#id').prop('readonly', true);
			opener.$('#password').focus();

			// 4)
			window.close();
			// close();, self.close();, this.close(); -> 확인 필요
			
		} // idOK
	</script>
	
	<style>
		body {
	      /* background-color: LightYellow; */
	      font-family: 맑은고딕;
	   }
	   
	   /* #wrap {
	      margin-left: 0;
	      text-align: center;
	   } */
	   
	   h3 { 
	   	color: navy;
	   }
	</style>
</head>
<body>

<div id="Wrap">
	<h3>** ID 중복 확인**</h3>
	<form action="idDupCheck" method="get">
		User_ID : 
		<input type="text" name="id" id="id">&nbsp;
		<input type="submit" value="ID 중복확인" onclick="return idCheck()">
		<!-- inCheck.js의 idCheck()의 결과에 따라 submit 결정 -->
		<span id="iMessage" class="eMessage"></span>
	</form>
	<br><br>
	<hr>
	<!-- ** 서버의 확인 결과에 따른 처리 영역
		=> isUse : 'T' 가능 / 'F' 불가능 -->
	<div id="msgBlock">
		<c:if test="${isUse == 'T'}">
			${newId}는 사용 가능합니다.&nbsp;&nbsp;
			<input type="button" value="id_OK" onclick="idOK()">
			<!-- 사용자가 입력한 ID를 사용 가능하도록 해주고, 현재(this)창은 close -->
		</c:if>
		
		<c:if test="${isUse == 'F'}">
			${newId}는 사용 불가능합니다. (사용중이거나 탈퇴한 ID입니다)<br>
			다시 입력하세요.
			<!-- 부모창(joinForm, opener)에 남아있는 사용자가 입력한 id는 지워주고,
				현재(this)창에서는 id에 focus를 주고 재입력 유도 -> script 필요 -->
			<script>
				$('#id').focus();
				opener.document.getElementById('id').value='';
			</script>
		</c:if>
	</div>
</div>

</body>
</html>