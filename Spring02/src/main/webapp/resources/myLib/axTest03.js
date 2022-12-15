
/*
 ** Ajax Board Test
 => jsonView 활용
*/

// Test 1. 타이틀 클릭하면 하단(resultArea2)에 글 내용 출력하기
// => bdetail 이용하여 response Page 출력

function jsBDetail1(seq) {

	$.ajax({
		type: 'Get',
		url: 'bdetail',
		data: {
			seq: seq
		},
		success: function(resultPage) {
			$('#resultArea2').html(resultPage);
		},
		error: function() {
			$('#resultArea2').html('** 서버 오류 ! 잠시 후 다시 시도해주세요');
		}

	}); // ajax

} // jsBDetail1

//====================================================================================================

//  Test 2. 타이틀 클릭하면 글 목록의 아래(span)에 글 내용 출력하기
// => Toggle 방식으로 없을 때 클릭하면 표시되고, 있을 때 클릭하면 사라지도록
// => 새로운 글을 클릭하면 이전 글의 컨텐츠는 사라짐
// => jsonView로 content 값만 전달받음.

// => 이벤트 객체 전달 Test (이벤트 리스너 함수의 첫 번째 매개변수에 전달)
// => 이벤트 객체의 property
//    - type : 발생된 이벤트 종류
//    - target : 이벤트를 발생시킨 객체
//    - currentTarget : 현재 이벤트 리스너를 실행하고 있는 DOM 객체
//    - defaultPrevented : true / false 이벤트의 기본 동작이 취소되었는지 나타냄.

// => 이벤트 객체의 메서드
//    - preventDefault() : 이벤트의 기본 동작을 취소시킴.
      
/*
function jsBDetail2(seq, count) {

	$('#resultArea2').html('');

	if ($('#' + count).html() == '') {
		// => 글 내용이 없을 때만 ajax 실행

		$.ajax({
			type: 'Get',
			url: 'jsbdetail?seq=' + seq, // data 필요 없어짐.
			success: function(resultData) {

				// 이전에 출력되었던 글은 지우고, 새 글만 출력
				$('.content').html('');
				$('#' + count).html(resultData.content);

			},
			error: function() {
				$('#resultArea2').html('** 서버 오류 ! 잠시 후 다시 시도해주세요');
			}

		}); // ajax

	} else
		$('#' + count).html(''); // 출력된 글이 있을 때는 clear

} // jsBDetail2
*/

// ** Event 객체 전달
function jsBDetail2(e, seq, count) {

	console.log('** e.type = ' + e.type);
	console.log('** e.target = ' + e.target);

	$('#resultArea2').html('');

	if ($('#' + count).html() == '') { // 글 내용이 없을 때만 ajax 실행

		$.ajax({
			type: 'Get',
			url: 'jsbdetail?seq=' + seq, // data 필요 없어짐.
			success: function(resultData) {
				// 이전에 출력되었던 글은 지우고, 새 글만 출력
				$('.content').html('');
				$('#' + count).html(resultData.content);
			},
			error: function() {
				$('#resultArea2').html('** 서버 오류 ! 잠시 후 다시 시도해주세요');
			}

		}); // ajax

	} else
		$('#' + count).html(''); // 출력된 글이 있을 때는 clear

} // jsBDetail2

//====================================================================================================

/*
  Test 3. seq에 마우스 오버 시 별도의 DIV에 글 내용 표시되도록 하기
  => JQ : id, class, this
  => hover(f1, f2) => hover는 익명함수 2개 사용
  => div를 표시 / 사라짐 처리
     - css의 display 속성 활용
	 - JQ 메서드 show(), hide() 활용
  => div 출력 위치 : 마우스 포인터 위치
  => 마우스 포인터 위치 인식
     - event 객체(이벤트 핸들러 첫 번째 매개변수)가 제공
     - e.pageX, e.pageY : 전체 Page 기준
     - e.clientX, e.clientY : 보여지는 화면 기준 -> page Scroll 시 불편함

*/

$(function() {

	// 3.1) css의 display 속성 활용
	$('.qqq111').hover(function(e) { // 3.2) Test 위해 qqq -> qqq111 변경
	
		// (1) 마우스 포인터 위치 인식
		let mleft = e.pageX + 15; // + number 하면 보기 편할 수 있음
		let mtop = e.pageY + 10;
		
		console.log(`** e.pageX = ${e.pageX}, e.pageY = ${e.pageY}`);
		console.log(`** e.clientX = ${e.clientX}, e.clientY = ${e.clientY}`); // 위치 비교
		
		// (2) seq 확인
		let seq = $(this).attr('id'); // this : ajax하기 전이라 event가 일어난 td를 의미
		
		// (3) ajax
		$.ajax({
			type: 'Get',
			url: 'jsbdetail?seq=' + seq, // data 필요 없어짐.
			success: function(resultData) {
				// 이전에 출력되었던 글은 지우고, 새 글만 div로 출력
				$('.content').html('');
				$('#content').html(resultData.content)
				.css({
					/* div 위치 지정해서 표시 */
					display: 'Block',
					left: mleft,
					top: mtop
				}); // css
			},
			error: function() {
				$('#resultArea2').html('** 서버 오류 ! 잠시 후 다시 시도해주세요');
			}

		}); // ajax
		
		// e.preventDefault(); // 아래와 동일
		return false; // Event Propagation(전달) 방지
	
	}, function() {
	
		// ** mouse Out
		$('.content').html('');
		
		$('#content').css(
			'display', 'none' // 두 개 다 ''안에 속성 작성
		);
	
	}); // qqq_hover
	
	
	// 3.2) JQ 메서드 show(), hide() 활용 ****************************************************
	$('.qqq').hover(function(e) {
	
		// (1) 마우스 포인터 위치 인식
		let mleft = e.pageX + 15; // + number 하면 보기 편할 수 있음
		let mtop = e.pageY + 10;
		
		console.log(`** e.pageX = ${e.pageX}, e.pageY = ${e.pageY}`);
		console.log(`** e.clientX = ${e.clientX}, e.clientY = ${e.clientY}`); // 위치 비교
		
		// (2) seq 확인
		let seq = $(this).attr('id'); // this : ajax하기 전이라 event가 일어난 td를 의미
		
		// (3) ajax
		$.ajax({
			type: 'Get',
			url: 'jsbdetail?seq=' + seq, // data 필요 없어짐.
			success: function(resultData) {
				// 이전에 출력되었던 글은 지우고, 새 글만 div로 출력
				$('.content').html('');
				$('#content').html(resultData.content)
				.css({
					/* div 위치 지정해서 표시 */
					/* display: 'Block', => show()로 처리 */
					left: mleft,
					top: mtop
				}).show();
			},
			error: function() {
				$('#resultArea2').html('** 서버 오류 ! 잠시 후 다시 시도해주세요');
			}

		}); // ajax
		
		// e.preventDefault(); // 아래와 동일
		return false; // Event Propagation(전달) 방지
	
	}, function() {
	
		// ** mouse Out
		$('.content').html('');
		
		/* $('#content').css(
			'display', 'none' => hide()로 처리
		); */
		
		$('#content').hide();
	
	}); // qqq_hover

}); // ready
