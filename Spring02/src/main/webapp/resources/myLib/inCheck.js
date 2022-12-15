/*
 < ** 입력값의 무결성 확인 >
  ** member 무결성 확인사항
  ID : 길이(4이상), 영문자,숫자로만 구성
  Password : 길이(4이상), 영문,숫자,특수문자로 구성, 특수문자는 반드시 1개 이상 포함할 것
  Name : 길이(2이상), 영문 또는 한글로 만 입력
  Info : 길이로 입력 여부만 확인
  Jno : select 를 이용 (X)
  BirthDay : 입력 여부 확인  ( length == 10 )
  Age : 정수의 범위  ( 숫자이면서, '.'이 없어야함 )
  Point : 구간 (100 ~ 10000)

 < ** 작성 규칙 >
	=> JavaScript function으로 정의 하고 
	   결과를 true or false로 return
 */

function idCheck() {

	let id = $('#id').val();

	if (id.length < 4 || id.length > 10) {
		$('#iMessage').html('ID 길이는 4 ~ 10자리입니다.');
		$('#id').focus();
		return false;

	} else if (id.replace(/[a-z.0-9]/gi, '').length > 0) {
		$('#iMessage').html('ID는 영문자, 숫자로만 입력하세요.');
		$('#id').focus();
		return false;
		
	} else {
		$('#iMessage').html('');
		return true;
	}

} // idCheck

//--------------------------------------------------------------------------------------

function pwCheck() {

	let password = $('#password').val();

	if (password.length < 4) {
		$('#pMessage').html('Password 길이는 4 이상 입니다.');
		$('#password').focus();
		return false;

	} else if (password.replace(/[!-*.@]/gi, '').length >= password.length) {
		$('#pMessage').html('Password에는 특수문자가 반드시 포함되어야 합니다.');
		$('#password').focus();
		return false;

	} else if (password.replace(/[a-z.0-9.!-*.@]/gi, '').length > 0) {
		$('#pMessage').html('Password는 영문자, 숫자, 특수문자로만 입력하세요.');
		$('#password').focus();
		return false;
		
	} else {
		$('#pMessage').html('');
		return true;
	}

} // pwCheck

//--------------------------------------------------------------------------------------

function pw2Check() {

	let password = $('#password').val();
	let password2 = $('#password2').val();

	if (password != password2) {
		$('#ppMessage').html('Password가 다릅니다.');
		$('#password2').focus();
		return false;
		
	} else {
		$('#ppMessage').html('');
		return true;
	}

} // pw2Check

//--------------------------------------------------------------------------------------

function nmCheck() {

	let name = $('#name').val();
			
	if (name.length < 2) {
		$('#nMessage').html('Name 길이는 2 이상 입니다.');
		$('#name').focus();
		return false;

	} else if (name.replace(/[a-z.가-힣]/gi, '').length > 0) {
		$('#nMessage').html('Name은 영문, 한글만 입력 가능합니다.');
		$('#name').focus();
		return false;
		
	} else {
		$('#nMessage').html('');
		return true;
	}

} // nmCheck

//--------------------------------------------------------------------------------------

function foCheck() {

	let info = $('#info').val();
			
	if (info.length < 1) {
		$('#fMessage').html('Info를 입력 하세요.');
		$('#info').focus();
		return false;
		
	} else {
		$('#fMessage').html('');
		return true;
	}

} // foCheck

//--------------------------------------------------------------------------------------

function bdCheck() {

	let birthday = $('#birthday').val();
			
	if (birthday.length != 10) {
		$('#bMessage').html('생년월일을 정확하게 입력 하세요. (yyyy-mm-dd)');
		$('#birthday').focus();
		return false;
		
	} else {
		$('#bMessage').html('');
		return true;
	}

} // bdCheck

//--------------------------------------------------------------------------------------

function agCheck() {

	let age = $('#age').val();

	if ($.isNumeric(age) == false || age.replace(/[.]/g, '').length < age.length) {
      $('#aMessage').html('정숫값을 정확하게 입력하세요.');
      $('#age').focus();
      return false;
      
   } else {
      $('#aMessage').html('');
      return true;
   }

} // agCheck

//--------------------------------------------------------------------------------------

function ptCheck() {

	let point = $('#point').val();
	
	if ($.isNumeric(point) == false) {
      $('#poMessage').html('point 값을 숫자로 정확하게 입력하세요. (100 ~ 10000)');
      $('#point').focus();
      return false;
      
   } else if (parseFloat(point) < 100 || parseFloat(point) > 10000) {
      $('#poMessage').html('point의 범위를 벗어납니다. (100 ~ 10000)');
      $('#point').focus();
      return false;
      
   } else {
      $('#poMessage').html('');
      return true;
   }

} // ptCheck
