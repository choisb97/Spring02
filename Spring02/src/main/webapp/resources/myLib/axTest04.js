/*
 ** Ajax_REST API Test **
 => axRSLogin
*/

$(function() {

	// ** ajax REST 방식 MemberDetail
	// => Response로 JSON Type Data를 받는다. 
	// => loginForm의 password를 jno 값으로 활용   
	// => 컨트롤러에서는 @PathVariable, ResponseEntity 적용 
	//    @PathVariable 적용을 위해 Get방식, jno를 int로 처리함.
	
	$('#rsdetail').click(function() {
	
		let id = $('#id').val();
		let jno = parseInt($('#password').val()); // Integer Type으로 형변환
		
		console.log(`** id = ${id}, jno = ${jno}`);
		
		$.ajax({
			type : "Get",
			url : `rest/rsdetail1/${id}/${jno}.json`,
			// => Response로 JSON Type Data를 요청 함.
			//    .json 없으면 XML 형식으로 전달 됨.
			
			// => rsdetail1 : ResponseEntity 적용 안 함.
			// => rsdetail2 : ResponseEntity 적용 함.
			success : function(resultData) {
				// ** JSON 처리
				// => 컨트롤러의 처리 결과를 JSON Type으로 전달 받음
				console.log(`** success, id => ${resultData.id}`);
				
				let resultHtml =
				`<table border="1" align="center" width="80%">
					<caption><h3>** Ajax 결과 **</h3></caption>
					<tr><th>I D</th><td>${resultData.id}</td></tr>   
					<tr><th>Name</th><td>${resultData.name}</td></tr>
					<tr><th>Info</th><td>${resultData.info}</td></tr>
					<tr><th>Birthday</th><td>${resultData.birthday}</td></tr>
					<tr><th>Jno</th><td>${resultData.jno}</td></tr>
					<tr><th>Age</th><td>${resultData.age}</td></tr>
					<tr><th>Point</th><td>${resultData.point}</td></tr>
					<tr><th>Image</th><td><img src=${resultData.uploadfile} width=80 height=100></td><tr>
				</table>`;
				
				$('#resultArea1').html(resultHtml);
				$('#resultArea2').html(''); // clear
			},
			error : function() {
				$('#resultArea2').html('!! Ajax 요청 Error : 자료 없음 !!');
			}
		}); // ajax
		
	}); // rsdetail_click
	
}); // ready