package com.ncs.green;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;
import service.JoService;
import service.MemberService;
import vo.JoVO;
import vo.MemberVO;
import vo.RestVO;

/*
 < ** REST API >
 => REST 방식의 기본 사항은 서버에서 순수 데이터만을 전송한다는 점.
 => 그래서 @Controller가 아닌 @RestController 사용
 
 ** @Controller와 @RestController 차이점
 => @Controller
 	- String Return : View Name으로 처리 됨
 	
 => @RestController
 	- REST 컨트롤러임을 명시하며, 모든 매핑 메서드의 리턴타입이 기존과는 다르게 처리함을 의미
 	- String Return : 순수한 Data로 처리 됨 
 	
 => produces 속성
  	- 해당 메서드 결과물의 MIME Type을 의미 (UI Content-Type에 표시 됨)
	- 위처럼 문자열로 직접 지정할 수도 있고, 메서드 내의 MediaType 클래스를 이용할 수도 있음
	- 필수 속성은 아님
*/

@RestController
@RequestMapping("/rest")
@Log4j
public class RTestController {
	
	//@Autowired
	JoService service;
	
	//@Autowired
	MemberService mservice;

	// ** RestController의 다양한 Return Type
	// 1) Text Return
	@GetMapping(value = "/getText", produces = "text/plain; charset = UTF-8")
	// => produces 속성
	//    - 해당 메서드 결과물의 MIME Type을 의미 (UI Content-Type에 표시 됨)
	//	  - 위처럼 문자열로 직접 지정할 수도 있고, 메서드 내의 MediaType 클래스를 이용할 수도 있음
	//	  - 필수 속성은 아님
	public String getText() {
		
		log.info("** MIME Type : " + MediaType.TEXT_PLAIN_VALUE);
		
		return "@@ 안녕하세요~ REST API !!";

	} // getText
	
	// 2) 사용자 정의 객체
	// 2.1) 객체 Return 1. : produces 지정한 경우
	@GetMapping(value = "/getVO1", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	// => produces : JSON과 XML 방식의 데이터를 생성할 수 있도록 설정
	// => 요청 url의 확장자에 따라 다른 타입으로 서비스
	//	  - Test 1) 브라우저에서 /rest/getVO1 호출 -> 위 둘 중 XML 전송(Default)
	//	  - Test 2) 브라우저에서 /rest/getVO2.json 호출 -> JSON 전송
	public RestVO getVO1() {
		
		return new RestVO(55, "snow", "Christmas조", "Merry 크리스마스");

	} // getVO1
	
	// 2.2) 객체 Return 2. : produces 지정하지 않은 경우
	@GetMapping(value = "/getVO2")
	public RestVO getVO2() {
		
		return new RestVO(222, "rain", "Christmas조", "Merry 크리스마스");

	} // getVO2
	
	// 3) Collection Return
	// 3.1) Map
	// => XML로 Return하는 경우 Key값 주의 (변수명 규칙)
	//    UI(브라우저)에서 Tag명이 되므로 반드시 문자로 한다.
	//    (첫 글자 숫자, 기호(특수문자) 모두 안 됨 주의!(변수명 정하듯) 단, JSON Type은 무관함)
	//    222, 3rd, -Second 등 / 한글 허용
	@GetMapping(value = "/getMap")
	public Map<String, RestVO> getMap() {
		
		Map<String, RestVO> map = new HashMap<>();
		
		// ** Map은 순서 유지 X
		map.put("First", new RestVO(111, "Choi", "GameZone", "Happy"));
		map.put("Second", new RestVO(222, "ggumsb", "GameZone", "Happy"));
		map.put("Third", new RestVO(333, "Project", "GameZone", "Happy"));
		
/*		map.put("-First", new RestVO(111, "Choi", "GameZone", "Happy"));
		map.put("222", new RestVO(222, "ggumsb", "GameZone", "Happy"));
		map.put("3rd", new RestVO(333, "Project", "GameZone", "Happy"));  */ // 오류

		return map;
		
	} // getMap

	// 3.2) List
	@GetMapping(value = "/getList")
	public List<JoVO> getList() {
		
		List<JoVO> list = service.selectList();
		System.out.println(list);
		
		return list;
		
	} // getList
	
	/*
	  ** ResponseEntity Test
	   => Status (200, 404 등 응답 상태 코드), Headers, Body 등을 함께 전송할 수 있음.
	   => 아래 inCheck() 메서드는 "jno", "chief"를 반드시 Parameter로 전달받아야 하며,
	      만약 하나라도 전달받지 못 하면 "400-잘못된 요청" 오류 발생
	      전달된 jno값의 조건에 의하여 502(BAD_GATEWAY) 또는 200(OK) 상태코드와 데이터를 전송할 수 있다.
	      요청 User가 이 응답 결과의 정상/비정상 여부를 알 수 있도록 해준다.
	   => 200 Test : http://localhost:8080/green/rest/incheck?jno=11&chief=가나다라
	   => 502 Test : http://localhost:8080/green/rest/incheck?jno=5&chief=가나다라
	   
	  * JSON으로 확인하는 경우
	   => http://localhost:8080/green/rest/inCheck.json?jno=11&chief=가나다라
	   											- 요청명에 .json 붙이기
	*/
	// 4) ResponseEntity
	@GetMapping(value = "/inCheck", params = {"jno", "chief"})
	public ResponseEntity<RestVO> inCheck(Integer jno, String chief) {
		
		// 4.1) 준비
		// => ResponseEntity 정의 & Parameter 처리(Service 처리)
		ResponseEntity<RestVO> result = null;
		RestVO vo = new RestVO(jno, chief, "조이름", "** ResponseEntity Test 중이다. **");
		
		// 4.2) 조건 확인(Service 결과 확인)
		if(jno < 10 || jno > 99) { // 오류 상황
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo); // 502 에러
			
		} else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo); // 정상
		}
		
		return result;
		
	} // inCheck
	
	// 5) @PathVariable
	// => URL 경로의 일부를 파라미터로 사용할 때 이용.
	// => 요청 URI 매핑에서 템플릿 변수를 설정하고 이를 매핑메서드 매개변수의 값으로 할당시켜 줌.
	@GetMapping(value = "/product/{category}/{productId}")
	public String[] product(@PathVariable("category") String category, @PathVariable("productId") String productId) {
		
		return new String[] {"** Category : " + category, "Product_ID : " + productId};
		
	} // product
	
	// 6) @RequeestBody
	// => JSON 형식으로 전달된 Data를 컨트롤러에서 사용자 정의 객체(VO)로 변환할 때 사용
	// => 요청 url : http://localhost:8080/green/rest/convert
	// => Payload : {"jno":33, "chief":"victory", "jname":"삼삼오오", "note":"RequestBody Test 중" }
	@PostMapping("/convert")
	public RestVO convert(@RequestBody RestVO vo) {
		
		log.info("** JSON to RestVO => " + vo);
		vo.setJno(vo.getJno() * 100);
		
		return vo;
		
	} // convert
	
	// 7) axRSDetail Test 1
	// ** 준비
	// => rsDetail(id, jno) 추가 : Service, ServiceImpl, MapperInterface, Mapper.xml
	// => UI : loginForm을 이용(수정 필요_메뉴 추가, inCheck 정지), Ajax 처리(axTest04.js)
	
	// => id와 jno를 이용한 Member selectOne
	// => mapper에 두 개의 Parameter(id, jno) 전달
	//    mapper Interface에서 @Param 적용
	// => @PathVariable : 기본 자료형은 사용할 수 없음.
	// => 요청명 : rsdetail1/teacher/9
	@GetMapping("/rsdetail1/{id}/{jno}")
	public MemberVO rsdetail1(@PathVariable("id") String id, @PathVariable("jno") Integer jno) { 
																				// int X -> 실행 시 오류
		log.info("** rsdetail1 id => " + id);
		log.info("** rsdetail1 jno => " + jno);
		
		return mservice.rsDetail(id, jno);
		
	} // rsdetail1
	
	// 8) axRSDetail Test 2
	// => ReponseEntity 적용
	@GetMapping("/rsdetail2/{id}/{jno}")
	public ResponseEntity<MemberVO> rsdetail2(@PathVariable("id") String id, @PathVariable("jno") Integer jno) { 
																								// int X -> 실행 시 오류
		ResponseEntity<MemberVO> result = null;
		MemberVO vo = mservice.rsDetail(id, jno);
		
		if (vo != null) { // success
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
			
		} else { // error
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}
		
		return result;
		
	} // rsdetail2
	
} // class
