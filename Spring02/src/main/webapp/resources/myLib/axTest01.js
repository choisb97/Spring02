/*
 ** 이클립스 자바스크립트 파일 내용이 흑백으로 나올 때 - 컬러로 고치기
 => 다음에..
*/

/*
 ** Ajax_Test01
 => MousePointer, axLogin, jsLogin, axJoin
 => axJoin : form의 input Data 처리
*/

$(function() {
	// ** MousePointer
	// => ~.hover(f1, f2);
	$('.textLink').hover(function() {
		$(this).css({
			fontSize : '1.2em',
			fontWeight : 'Bold',
			color : 'DimGray',
			cursor : 'pointer'
		}); // css
		
	}, function() {
		$(this).css({
			fontSize : '',
			fontWeight : 'Bold',
			color : 'Gray',
			cursor : 'default'
		}); // css	
	}); // hover
	
//----------------------------------------------------------------------------------------	
	
	// ** AjaxLogin
	
	// => axloginf
	$('#axloginf').click(function() {
		$.ajax({
			type : 'get',
			url : 'loginf',
			success : function(resultPage) {
				$('#resultArea1').html(resultPage);
			},
			error : function() {
				$('#resultArea1').html('Ajax Login 요청 Error !!');
			}
		}); // ajax
	}); // axloginf_click

//----------------------------------------------------------------------------------------	

	// ** AjaxLogin
	// => Ajax 처리의 경우
	//    로그인 성공 or 실패는 모두 컨트롤러의 정상적인 처리 결과이므로
	//    success function에서 처리 함
	//    로그인 성공 or 실패에 따른 다른 처리를 하려면,
	//    로그인 성공 or 실페를 success function에서 구별할 수 있어야 함.
	//   => ver01 : webPage를 response로 전달받음
	//       성공 : 현재 화면을 새로고침
	//       실패 : 현재의 loginForm을 그냥 두면 됨.
	//       그러므로 response Page가 필요하지 않음
	//       로그인 성공 / 실패 결과만 알면 됨.
	
	//   => ver02 : 결괏값 Data를 response로 전달받음 
	//       서버로부터 결괏값을 전달받을 필요성
	//       서버의 결과는 Java의 Data -> JS가 이를 이용해서 코딩
	//       그러므로 Java의 Data를 JS가 인식 가능한 Data형식(JSON 포맷)으로 변환해야 함.

	// => axlogin : ver01
	$('#axlogin').click(function() {
		$.ajax({
			type : 'Post',
			url : 'login',
			data : {
				id : $('#id').val(),
				password : $('#password').val()
			},
			success : function() {
				// => ver01
             	//    resultPage를 사용하면
            	//    실패 시 로그인폼 출력은 가능 하지만,
             	//    성공 시 home 화면을 resultArea1에 출력하게 됨
             	//$('#resultArea1').html(resultPage);
	          
	            // => resultArea는 clear, 현재 Page는 새로고침
	            //    (그러나 실패 시 로그인폼 출력이 어려움)
	            $('#resultArea1').html('');
	            $('#resultArea2').html('');
	            location.reload();
			},
			error : errorMessage
			// => function 작성 후 호출하기 (코드 아래에 function 있음)
            //    익명함수가 이 위치에 있어야하므로 함수명만 사용해서 함수의 코드가 호출 되도록 함.
            //    즉 errorMessage()처럼 함수를 호출하는 것이 아님
			
		}); // ajax
	}); // axlogin_click
	
//----------------------------------------------------------------------------------------	
	
	// ** JSONLogin
	
	// => jslogin : ver02
	$('#jslogin').click(function() {
		$.ajax({
			type : 'Post',
			url : 'jslogin',
			data : {
				id : $('#id').val(),
				password : $('#password').val()
			},
			success : function(resultData) {
				// ** JSON 처리
				// => 컨트롤러의 처리 결과를 전달받아 성공 / 실패 구분 가능
				if (resultData.code == 200) { // type 상관 없음
					$('#resultArea1').html('');
					location.reload();
					
				} else {
					$('#message').html(resultData.message);
					$('#id').focus();
				};
				
				$('#resultArea2').html('');
			},
			error : errorMessage

		}); // ajax
	}); // jslogin_click
	
//----------------------------------------------------------------------------------------	
	
	// ** AjaxJoin
	
	// => axjoinf
	$('#axjoinf').click(function() {
		$.ajax({
			type : 'get',
			url : 'joinf',
			success : function(resultPage) {
				$('#resultArea1').html(resultPage);
				$('#resultArea2').html('');
			},
			error : errorMessage
		}); // ajax
	}); // axjoinf_click
	
//----------------------------------------------------------------------------------------	

	// => axjoin
	/*
	  ** Ajax에서 input Data (Value) 처리방법 **
	  1) Form의 serialize() *
	  => input Tag의 name과 id가 같아야 함.   
	  => 직렬화 : multipart 타입은 전송 안 됨. 
	             처리하지 못 하는 값(예-> file Type)은 스스로 제외시킴 
	  => 제외컬럼 지정하기
	     var formData = $('#myForm : not(#rid)').serialize();
	     rid만 제외시키는 경우 (보류 : 적용 안 됨)
	
	  2) 객체화   
	  => 특정 변수(객체형)에 담기      
	  => 특별한 자료형(fileType : UpLoadFilef) 적용 안 됨.   
	
	  3) FormData 객체 활용
	  => 모든 자료형의 특성에 맞게 적용 가능하여 이미지 등의 file 업로드가 가능한 폼데이터 처리 객체
	  => IE10부터 부분적으로 지원되며, 크롬이나 사파리, 파이어폭스 같은 최신 브라우저에서는 문제 없이 동작
	  => 3.1) append 메서드 또는 3.2) 생성자 매개변수 이용
	
	  ** 관련속성 **
	  => form Tag 
	       enctype 속성 : 'multipart/form-data'  
	       method : 'Post''
	  => ajax 속성
	       method : 'Post','
	       enctype : 'multipart/form-data', // form Tag에서 지정하므로 생략 가능
	       processData : false, // false로 선언 시 formData를 string으로 변환하지 않음
	       contentType : false, // false로 선언 시 content-type 헤더가 multipart/form-data로 전송되게 함
	*/
	
	$('#axjoin').click(function() {
	
		// ** 실습
		// 1) Form의 serialize() : Image(file) 전송 X
		// => fileUpload가 없는 경우 가장 많이 쓰임
		
		// let formData = $('#myForm').serialize();
		// $.ajax({ ~~~, data : formData, ~~~ }); => data 처리 간편해짐, Image(file) 전송 X
		
		
		// 2) 객체화 : Image(file) 전송 X
	/*	formData = {
			id : $('#id').val(),
			password : $('#password').val(),
			name : $('#name').val(),
			info : $('#info').val(),
			birthday : $('#birthday').val(),
			jno : $('#jno').val(),
			age : $('#age').val(),
			point : $('#point').val()
		} */
					
		
		// 3) FormData 객체 활용
		// 3.1) append 메서드 : 제한적으로 Image(file) 전송 가능
		// ~~.append(id, $('#id').val()); 형태로 쓰임
		/*
		let formData = new FormData();
		
		formData.append(id, $('#id').val());
		formData.append(password, $('#password').val());
		formData.append(name, $('#name').val());
		formData.append(info, $('#info').val());
		formData.append(birthday, $('#birthday').val());
		formData.append(jno, $('#jno').val());
		formData.append(age, $('#age').val());
		formData.append(point, $('#point').val());
		
		
		=> Image 처리
		   Ajax의 FormData는 이미지를 선택하지 않으면 append 시 오류 발생하기 때문에 이를 확인 후 append하도록 함.
		   이 때 append를 하지 않으면 서버의 vo.uploadfilef에는 null 값이 전달 됨.
		
		if ($('#uploadfilef')[0].files[0] != null)
		// $('#uploadfilef')[0] => [0] : 아래의 코드처럼 JS의 node(노드)로 인식.
		// if (document.getElementById('uploadfilef').files[0] != null) // 위와 동일
			formData.append('uploadfilef', $('#uploadfilef')[0].files[0]);
		*/	
			
		// 3.2) append all (all append라고 부르기도 함) : 모든 data(file) 전송 가능
		// => 생성자의 매개변수로 form(JS의 node로 인식된 Form이어야 함) 사용
		
		// ** JS 적용
		// let formData = new FormData(document.getElementById('myForm')); // OK
		
		// ** JQ 적용
		// let formData = new FormData($('#myForm')); // Error
		// => 그냥 JQ 사용 시 Error : JS 방식과 같은 myForm을 말하지만 Type이 다름.
		
		let formData = new FormData($('#myForm')[0]); // OK : [0]을 JS의 node로 인식하기 때문
		
		$.ajax({
			type : 'Post',
			url : 'join',
			// => FormData 객체로 fileUpload 시 enctype, processData, contentType 추가
			//    단, enctype : 'multipart/form-data'는 생략 가능 (form Tag에 적용했기 때문)
			processData : false,
			contentType : false,
			data : formData,
			success : function(resultPage) {
				$('#resultArea1').html(resultPage);
				$('#resultArea2').html('');
			},
			error : errorMessage
			//error : function() {
				//$('#resultArea1').html('Ajax Join 요청 Error !!');
			
		}); // ajax
	}); // axjoin_click
	
}); // ready


// ** Error 처리 function **
function errorMessage(message) {

	$('#resultArea2').html('Ajax 요청 Error => ' + message);
	
} // errorMessage
