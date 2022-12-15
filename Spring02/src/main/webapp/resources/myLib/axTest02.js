/*
 < ** AjaxTest 02 >
 => 반복문에 이벤트 적용하기
 => axmlist : id별로 board 조회, 관리자기능 (delete 버튼), Image(File)Download
 => axblist : 상세 글 보기
*/

$(function() {

	// ** Ajax_MemberList

	$('#axmlist').click(function() {
		$.ajax({
			type: 'Get',
			url: 'axmlist',
			success: function(resultPage) {
				$('#resultArea1').html(resultPage);
			},
			error: function() {
				//$('#resultArea1').html('');
				$('#resultArea1').html('** 서버 오류 ! 잠시 후 다시 시도해주세요');
			}

		}); // ajax

	}); // axmlist_click

	//=======================================================================================

	// ** Ajax_BoardList

	$('#axblist').click(function() {
		$.ajax({
			type: 'Get',
			url: 'axblist',
			success: function(resultPage) {
				$('#resultArea1').html(resultPage);
			},
			error: function() {
				//$('#resultArea1').html('');
				$('#resultArea1').html('** 서버 오류 ! 잠시 후 다시 시도해주세요');
			}

		}); // ajax

	}); // axblist_click

	//=======================================================================================

	// ** 반복문에 이벤트 적용하기 2) JQ
	// => id별로 board 조회 (요청 id 인식)
	$('.ccc').click(function() {

		// 1) 요청 id 인식
		let id = $(this).html(); // JS : innerHTML, innerTEXT
		// $(this).text()

		console.log('** id = ' + id);

		// 2) ajax 처리
		$.ajax({
			type: 'Get',
			url: 'aidblist',
			data: {
				keyword: id
				// keyword (SearchCriteria의 keyword -> controller에서 SearchCriteria cri로 처리하기 위함) : id (let id = $(this).html();)
			},
			success: function(resultPage) {
				$('#resultArea2').html(resultPage);
			},
			error: function() {
				$('#resultArea2').html('** 서버 오류 ! 잠시 후 다시 시도해주세요');
			}

		}); // ajax

	}); // ccc_click

	//=======================================================================================

	// ** id별 Delete 기능
	// => Response는 jsonView로 처리
	// => Delete 성공 / 실패 후 표시
	$('.ddd').click(function() {

		// 1) 요청 id 인식
		// => 이벤트가 일어난 Tag(axMemberList.jsp)의 id 속성 값으로 보관
		let id = $(this).attr('id'); // id 속성 값
		//id = '11111'; // Error Test Data

		console.log('** id = ' + id);
		$('#resultArea2').html('');

		// 2) ajax 처리
		$.ajax({
			type: 'Post',
			url: 'axmdelete', // Ajax Member Delete
			data: {
				id: id
				// id (Member) : id (let id = $(this).attr('id');) -> id 속성값을 가진 id
			},
			success: function(resultData) {
				// ** jsonView 처리 결과
				// 성공 : span의 컨텐츠를 Deleted, click_evnet를 off
				if (resultData.code == 200) { // type 상관 없음
					alert('삭제 성공했습니다!');
					/*
					$(this).html('Deleted');
					=> ajax 처리 단계이므로 이미 this는 변경되어 click 시 this가 아님.
					   그러므로 this로는 불가능, id 속성으로는 가능.
					*/

					$('#' + id).html('Deleted')
						.css({
							color: 'Red',
							fontWeight: 'Bold'
						}).off();
					// 이벤트 제거(적용 됨), removeClass는 적용 안 됨.

				} else {
					alert('삭제 실패하였습니다.');
					$('#resultArea2').html('');
				};
			},
			error: function() {
				$('#resultArea2').html('** 서버 오류 ! 잠시 후 다시 시도해주세요');
			}

		}); // ajax

	}); // ddd_click	

}); // ready

//=======================================================================================

// ** 반복문에 이벤트 적용하기 1) JS
// => id별로 board 조회
// test 1) JS function
function aidBList(id) {
	$.ajax({
		type: 'Get',
		url: 'aidblist',
		data: {
			keyword: id
			// keyword (SearchCriteria의 keyword -> controller에서 SearchCriteria cri로 처리하기 위함) : (aidBList) id
		},
		success: function(resultPage) {
			$('#resultArea2').html(resultPage);
		},
		error: function() {
			//$('#resultArea1').html('');
			$('#resultArea2').html('** 서버 오류 ! 잠시 후 다시 시도해주세요');
		}

	}); // ajax

} // aidBList

//=======================================================================================

// => Delete
function amDelete(id) {

	$.ajax({
		type: 'Get',
		url: 'axmdelete',
		data: {
			id: id
		},
		success: function(resultData) {
			// Delete 결과 처리

			if (resultData.code == '200') {
				alert('삭제가 성공적으로 처리되었습니다. ~~ ');
				// span의 컨텐츠를 Deleted, click_event를 off

				/* 
				 $(this).html('Deleted'); 
				  ajax 처리 단계이므로 이미 this는 변경되어 click 시의 this 가 아님 
				  => 그러므로 id 활용 
				  */

				$('#' + id).html('Deleted') // $('#admin')
					.css({
						color: 'Gray',
						fontgWeight: 'bold'
					}).off().attr('onclick', null);
				// JQuery의 이벤트제거이므로 여기서 off()는 적용 안 됨
				// => 그러므로 이 경우에는 onclick 속성의 값을 삭제 해야 함. 
			} else if (resultData.code == '201') {
				$('#resultArea2').html('');
				alert('~~ Delete Sql 오류 !! 잠시후 다시 하세요 ~~');

			} else {
				$('#resultArea2').html('');
				alert('~~ 관리자 로그인 정보 없음!!  다시 하세요 ~~');
			} //else
		}, // seccess
		error: function() {
			alert('~~ 서버오류 !! 잠시후 다시 하세요 ~~');
		}

	}); //ajax

} //amDelete 


