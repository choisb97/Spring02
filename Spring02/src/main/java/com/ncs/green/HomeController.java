package com.ncs.green;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
 < ** 스프링MVC : 스프링 DispatcherServlet 사용 >
 => Ano 기반
 
 < ** @Component(bean 생성 @)의 세분화
 => 스프칭 프레임워크에서는 클래스들을 기능별로 분류하기 위해 @을 추가 함.
 => @Controller : 사용자 요청을 제어하는 Controller 클래스
 				  DispatcherServlet이 해당 객체를 Controller 객체로 인식하게 해줌.
 => @Service : 비즈니스 로직을 담당하는 Service 클래스
 => @Repository : DB 연동을 담당하는 DAO 클래스
 				  DB 연동 과정에서 발생하는 예외를 변환해주는 기능 추가
 
 < ** @Controller 사용 함 >
 => implements Controller를 대신 함.
 => 아래와 관련된 import 삭제해야 함.
 	public class LoginController implements Controller {
 => 메서드명, 매개변수, 리던값에 자유로워짐.
 	-> 메서드명은 handleRequest 사용 안 해도 됨.
 	-> 매개변수 다양한 정의 가능(메서드 내에서 생성할 필요 없어짐)
 	-> 리턴값은 ModelAneView 또는 String 가능 함.
 	
 => 요청 별 Controller를 한 클래스 내에 메서드로 구현할 수 있게 됨.
 => 요청과 메서드 연결은 @RequestMapping으로 
 
 
---------------------------------------------------------------------------------------------

 < ** @RequestMapping >
 => DefaultAnnotationHandlerMapping에서 컨트롤러를 선택할 때 대표적으로 사용하는 애노테이션
 => DefaultAnnotationHandlerMapping은 클래스와 메서드에 붙은 @RequestMapping 애노테이션 정보를 결합해 최종 매핑정보를 생성한다
 => 기본적인 결합 방법은 클래스 레벨의 @RequestMapping을 기준으로 삼고, 메스드 레벨의 @Request으로 세분화하는 방식으로 사용된다.
 
 < ** @RequestMapping 특징 >
 => url 당 하나의 컨트롤러에 매핑되던 다른 핸들러 매핑과 달리 메서드 단위까지 세분화하여 적용할 수 있으며,
    url 뿐 아니라 파라미터, 헤더 등 더욱 넓은 범위를 적용할 수 있다.
 => 요청과 매핑메서드 1:1 mapping
 => value="/mlist"
    : 이때 호출되는 메서드명과 동일하면 value 생략 가능 그러나 value 생략 시 404(확인 필요)
    : 해당 메서드 내에서 mv.setViewName("....");을 생략하면
      요청명을 viewName으로 인식. 즉, mv.setViewName("mlist")으로 처리 함.
      
 < ** @RequestMapping 속성 >
 => value : URL 패턴 (와일드카드 * 사용 가능)
    @RequestMapping(value="/post")
    @RequestMapping(value="/post.*")
    @RequestMapping(value={"/post", "/P"}) : 다중매핑 가능
*/
 // + @RequestMapping(value="/post/**/comment")

/*
 => method
 	@RequestMapping(value="/post", method=RequestMethod.GET)
 	-> url이 /post인 요청 중 GET 메서드인 경우 호출됨.
 	@RequestMapping(value="/post", method=RequestMethod.POST)
 	-> url이 /post인 요청 중 POST 메서드인 경우 호출됨.
 	   GET, POST, PUT, DELETE, OPTIONS, TRACE 총 7개의 HTTP 메서드가 정의되어 있음.
 	   (이들은 아래 @GetMapping... 등으로 좀 더 간편하게 사용 가능)
 
 => params : 요청 파라미터와 값으로도 구분 가능 함.
 	@RequestMapping(value="/post", params="useYn=Y")
 	-> /post?useYn=Y 일 경우 호출됨
 	@RequestMapping(value="/post", params="useYn!=Y")
 	-> not equal도 가능
 	@RequestMapping(value="/post", parmas="useYn")
 	-> 값에 상관없이 파라미터에 useYn이 있을 경우 호출됨
 	@RequestMapping(value="/post", params="!useYn")
 	-> 파라미터에 useYn이 없어야 호출됨
 	
--------------------------------------------------------------------------------------------------------------

 < ** @RequestMapping @GetMapping @PostMapping 차이 >
 => @GetMapping
 	-> @RequestMapping(method = RequestMethod.GET ...) 동일효과
 => @PostMapping
 	-> @RequestMapping(method = RequestMethod.POST ...) 동일효과
 => @PutMapping(), @DeleteMapping() 모두 가능
 
 => 특징
 	-> @RequestMapping은 클래스레벨 적용 가능하지만, 나머지 둘은 메서드 레벨 적용만 가능
 	-> 여러 개의 어노테이션을 조합하여 새로운 어노테이션을 생성한 것으로 내부에 @RequestMapping이 존재함.
 	   ctrl + click으로 소스 확인해보면
 	   @Target(ElementType.METHOD)
 	   @Retention(RetentionPolicy.RUNTIME)
 	   @Documented
 	   @RequestMapping(method = RequestMethod.GET)
 	   
---------------------------------------------------------------------------------------------------------------

 < ** 매핑메서드의 매개변수 >
 => 매개변수로 정의하면 메서드 내에서 생성할 필요 없음
 => request.getParameter 값 VO에 담기 -> 자동화 됨.
 	-> vo를 매핑 메서드의 매개변수로 선언하면 자동으로 대입됨.
 	-> 단, form의 input Tag의 name과 vo의 변수명(setter로 찾음)이 동일한 경우만 자동 대입됨.
 	(컬럼명의 두 번째 알파벳을 대문자로 하면 혼동이 발생하여 못 찾을 수 있으므로 사용하지 않는다)
 	
 => Parameter처리 다른 방법 : @RequestParam
 	publicModelAndView plogin(ModelAndView mv, @RequestParam("id")String id, @RequestParam("pw")String pw) {....
 	
 	-> String id = request.getParameter("id")와 동일, 그러나 매개변수로 VO를 사용하는 것이 가장 간편
 	
-----------------------------------------------------------------------------------------------------------------

 < ** Model & ModelAndView >
 => Model(interface)
 	-> controller처리 후 데이터(Model)을 담아서 반환
 	-> 구현 클래스 : ModelMap
 	-> 아래 home 메서드처럼 ModelAndView보다 심플한 코드 작성 가능하므로 많이 사용됨.
 	   mv.setViewName("~~~~") 하지 않고, viewName을 return
 	   
 => ModelAndView(class)
 	-> controller 처리 후 데이터(Model)와 viewName을 담아서 반환
 	-> Object -> ModelAneView

----------------------------------------------------------------------------------------------------------------

 **** Logger & Locale **************************************
 
 ** Logger : 현재 위치상태를 보여줘서 에러 위치를 잡을 수 있게 해줄 수 있는 코드
 => Log4J의 핵심 구성 요소로 6개의 로그 레벨을 가지고 있으며, 출력하고자 하는 로그 메시지를 Appender(log4j.xml 참고)에게 전달한다.
 
 => 활용하려면 pom.xml에 dependency(log4j, slf4j)추가 (되어있음)
 => Controller에는 아래의 코드를 넣어주고,
 => 확인이 필요한 위치에서 원하는 메시지 출력, Sysout은 (I/O 발생으로) 성능 저하 유발
 	현재 클래스 내에서만 사용 가능하도록 logger 인스턴스 선언 & 생성
 
 => ** Locale : (사건 등의 현장), 다국어 지원 설정을 지원하는 클래스
 => locale 값을 받아서 현재 설정된 언어를 알려줌 -> 한글 메시지 출력 가능
 => jsp의 언어 설정을 받아 해당 언어에 맞게 자동으로 message가 출력되도록 할 때 사용
 	logger.info("Welcome home! 로그 메시지 Test -> locale is {}.", locale);
 => {} : 일종의 출력 포맷으로 우측 ',' 뒷편 변수의 값이 표시됨.
 
 => src/main/resources/log4j.xml의 <logger> 태그에 패키지명을 동일하게 지정해야 함
 => 패턴 적용 가능
 	https://blog.naver.com/deersoul6662/222024554482
 	https://to-dy.tistory.com/20 (종합)
 	
 ** Log4J : Log for Java(Apache Open Source Log Library)의 준말
 => 자바기반 로깅 유틸리티로 디버깅용 도구로 주로 사용되고 있다.
 	로그를 남기는 가장 쉬운 방법은 System.out.println("로그 메시지")이지만
 	프로그램 개발 완료 후 불필요한 구문은 삭제해야 하며 성능 저하 요인이 됨.
 	Log4J 라이브러리는 멀티스레드 환경에서도 성능에 전혀 영향을 미치지 않으면서
 	안전하게 로그를 기록할 수 있는 환경을 제공함.
 	
 => 로깅레벨 단계
 	TRACE > DEBUG > INFO > WARN > ERROR > FATAL(치명적인)
 	
 	TRACE : Debug보다 좀 더 상세한 정보를 나타냄
 	DEBUG : 애플리케이션의 내부 실행 상황을 추적하기 위한 상세 정보 출력
 			(Mybatis의 SQL Log 확인 가능)
 	INFO : 상태 변경과 같은 주요 실행 정보 메시지를 나타냄
 	WARN : 잠재적인 위험(에러)를 안고 있는 상태일 때 출력 (경고성 메시지)
 	ERROR : 오류가 발생했지만 애플리케이션은 계속 실행할 수 있을 때 출력
 	FATAL : 애플리케이션을 중지해야 할 심각한 에러가 발생했을 때 출력
 
 => log4j.xml의 root Tag에서 출력 level 조정
 => <root><priority value 값>
 	-> 이 값을 warn(default), error, debug, trace
 	   변경하면서 Spring이 출력하는 Console Message를 비교해본다.
 	-> 차이점
 	   : info, warn, error -> INFO, WARN, ERROR
 	   : debug -> 위에 DEBUG 추가
 	   : trace -> 위에 TRACE 추가
 => 실제는 DEBUG, WARN이 주로 이용됨.
*/
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		logger.info("\nWelcome home! The client locale is {}.", locale); // {} : 변수 자리 - locale : 현지의 언어 정보 -> {}에 출력 됨
		
		
		// ** Logger Message Test
		// 1) {} 활용
	/*	logger.info("** Test 1) 안녕하세요 ~~");
		logger.info("** Test 2) 안녕하세요 ~~ {}!!", "홍길동");
		
		// 2) 변수 활용
		String name = "김길동";
		int age = 20;
		
		logger.info("** Test 3) 이름 : " + name + ", 나이 : " + age);
		logger.info("** Test 4) 이름 : {}, 나이 : {}", name, age);
		
		// ** log4j.xml의 로깅레벨 조정 Test **
		// => root Tag에서 출력 level 조정 (system 오류 level 조정)
		//    <root> <priority value 값>
		// => <logger name="com.ncs.green">에서 출력 level 조정
		//    <level value="DEBUG" />
		// => 이 두 곳의 값을 warn(default), error, debug, trace
		
		// => DEBUG Level에서 Mybatis SQL구문
		// DEBUG: mapperInterface.MemberMapper.insert - ==>  Preparing: insert into member values (?, ?, ?, ?, ?, ?, ?, ?, ?) 
		// DEBUG: mapperInterface.MemberMapper.insert - ==> Parameters: CHOI(String), $2a$10$R0reH5Q9w17TY91DCniKHOnlgf7Q5E2SRjEpkuPNjZA02PACTzRMm(String), 
		// 초이(String), debugTest(String), 2022-11-01(String), 9(Integer), 26(Integer), 123.4(Double), resources/uploadImage/basicman2.jpg(String)
		
		logger.warn("** 로깅레벨 Test warn, name => " + name) ;
		logger.error("** 로깅레벨 Test error, name => " + name);
		logger.debug("** 로깅레벨 Test debug, name => " + name);
		logger.trace("** 로깅레벨 Test trace, name => " + name);
	*/	
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
		
	} // home
	
// ---------------------------------------------------------------------------------------------------------------------------------------------

	// ** Exception Test
	@GetMapping("/etest")
	public ModelAndView etest(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, RedirectAttributes rttr) {
		
		// ** web.xml
		
		// 1) ArithmeticException
		// => ArithmeticException -> status : 500
		int i = 100 / 10; // 정수 : by zero, Exception 500
		logger.info("** ArithmeticException Test => " +  i);
		
		// => 실수 :  by zero, Exception X
		// => 실수형의 Zero 연산 확인은 if (Double.isInfinite(d)) ~~, if (Double.isNaN(p)) ~~
		double d = 100.0 / 0.0; // Infinity, 무한수
		double p = 100.0 % 0.0; // NaN, Not a Number
		logger.info("** ArithmeticException 실수형 Test 1 => " +  d + ", " + p);
		
		if (Double.isInfinite(d))
			d = 1;
		
		if (Double.isNaN(p))
			p = 0;
		
		//logger.info("** ArithmeticException 실수형 Test 2 => " +  (d * 100 + p)); // NaN -> if 확인하지 않은 경우
		logger.info("** ArithmeticException 실수형 Test 2 => " +  (d * 100 + p));
		
		// 2) NumberFormatException(Java, web.xml) or IllegalArgumentException(Spring)
		String s = "123"; // s = "123a" 비교
		//i += Integer.parseInt(s); // NumberFormatException
		if (Integer.parseInt(s) > 100)
			i = 0;
		
		logger.info("** IllegalArgumentException Test => " +  i);
		
		// 3) NullPointerException
		s = request.getParameter("name"); // name이라는 Parameter가 존재하지 않으면 null return
		
		if (s.equals("홍길동")) 
			s = "Yes";
		
		else
			s = "No";
		
		// => NullPointerException 예방을 위해
		
		if ("홍길동".equals(s)) 
			s = "Yes";
		
		else
			s = "No";
		
		logger.info("** NullPointerException Test => " +  s);
		
		// 4) ArrayIndexOutOfBoundsException
		String[] menu = {"볶음밥", "짜장면", "짬뽕"};
		
		logger.info("** ArrayIndexOutOfBoundsException Test => " +  menu[0]); // test - [3]
		
		// 5) SQL Test
		// => axJoin으로 DuplicateKeyException, SqlException
		//    외에 SqlException 등은 etest, join 등으로 Test
		
		mv.setViewName("redirect:home");
		return mv;
		
	} // etest
	
// ---------------------------------------------------------------------------------------------------------------------------------------------
	
	// ** AjaxTest Main_Form
	// => Get 방식만 허용
	//   -> @RequestMapping(value = "axTestForm", method = RequestMethod.GET)
	//   -> @GetMapping("/axTestForm")
	//@RequestMapping(value = "axTestForm")
	@GetMapping("/axTestForm")
	public ModelAndView axTestForm(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		
		mv.setViewName("/axTest/axTestForm");
		
		return mv;
		
	} // axTestForm
	
//----------------------------------------------------------------------------------------------------------------
	
	// ** BCryptPasswordEncoder Test
	// => 계층도 : PasswordEncoder(Interface) -> BCryptPasswordEncoder 구현 클래스
	// => import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	// => import org.springframework.security.crypto.password.PasswordEncoder;
	@RequestMapping(value = "bcrypt")
	public ModelAndView bcrypt(ModelAndView mv) {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String password = "12345!"; // rawData(원본 Data)
		
		// 1) encode(rawData)
		//    String digest1,2,3 = passwordEncoder.encode("password");
		//    모두 동일 rawData 적용 후 모두 다른 digest가 생성됨을 Test
		String digest1 = passwordEncoder.encode(password);
		String digest2 = passwordEncoder.encode(password);
		String digest3 = passwordEncoder.encode(password);

		String digest4 = passwordEncoder.encode("1234567#");
		String digest5 = passwordEncoder.encode("abcd789%");
		
		System.out.println("** digest1 = " + digest1);
		System.out.println("** digest2 = " + digest2);
		System.out.println("** digest3 = " + digest3);
		System.out.println("** digest4 = " + digest4);
		System.out.println("** digest5 = " + digest5);
		
		// 2) matches(rawData, digest) : true or false
		System.out.println("** digest1 matches = " + passwordEncoder.matches(password, digest1));
		System.out.println("** digest2 matches = " + passwordEncoder.matches(password, digest2));
		System.out.println("** digest3 matches = " + passwordEncoder.matches(password, digest3));
		System.out.println("** digest4 matches = " + passwordEncoder.matches("1234567#", digest4));
		System.out.println("** digest5 matches = " + passwordEncoder.matches("abcd789%", digest5));
		
		System.out.println("** 교차 matches = " + passwordEncoder.matches(password, digest5)); // false
		
		mv.setViewName("redirect:home");
		
		return mv;
		
	} // bcrypt
	
//----------------------------------------------------------------------------------------------------------------
	
	// **** KakaoMap Test ****************************************************************************************
	
	@RequestMapping(value = "greensn")
	public ModelAndView greensn(ModelAndView mv) {
		
		mv.setViewName("kakaoMapJsp/map01_greenSN");
		
		return mv;
		
	} // greensn
	
	@RequestMapping(value = "greenall")
	public ModelAndView greenall(ModelAndView mv) {
		
		mv.setViewName("kakaoMapJsp/map02_greenAll");
		
		return mv;
		
	} // greenall
	
	@RequestMapping(value = "jeju")
	public ModelAndView jeju(ModelAndView mv) {
		
		mv.setViewName("kakaoMapJsp/map03_jeju");
		
		return mv;
		
	} // jeju
	
	@RequestMapping(value = "gps")
	public ModelAndView gps(ModelAndView mv) {
		
		mv.setViewName("kakaoMapJsp/map04_GPS");
		
		return mv;
		
	} // gps

//----------------------------------------------------------------------------------------------------------------

	// ** WebSocket Echo Test
	@RequestMapping(value = "echo")
	public ModelAndView echo(ModelAndView mv) {
		
		mv.setViewName("axTest/wsEcho");
		
		return mv;
		
	} // echo
	
	
	// ** WebSocket Chat Test
	@RequestMapping(value = "chat")
	public ModelAndView chat(ModelAndView mv) {
		
		mv.setViewName("axTest/wsChat");
		
		return mv;
		
	} // chat
	
	
} // class
