package com.ncs.green;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import criTest.PageMaker;
import criTest.SearchCriteria;
import service.MemberService;
import vo.MemberVO;

/*
 < ** Bean 생성하는 @ >
  Java : @Component
  Spring 세분화 됨
  => @Controller,  @Service,  @Repository 
*/

/*
 < ** Spring의 redirect >
  => mv.addObject에 보관한 값들을 쿼리스트링의 parameter로 붙여 전달해줌 
     그러므로 전달하려는 값들을 mv.addObject로 처리하면 편리.
     단, 브라우저의 주소창에 보여짐.

 < ** RedirectAttributes >
  => Redirect 할 때 parameter를 쉽게 전달할 수 있도록 지원하며,
     addAttribute, addFlashAttribute, getFlashAttribute 등의 메서드가 제공됨.
  => addAttribute
       - url에 쿼리스트링으로 parameter가 붙어 전달됨. 
       - 그렇기 때문에 전달된 페이지에서 parameter가 노출됨.
  => addFlashAttribute
       - Redirect 동작이 수행되기 전에 Session에 값이 저장되고 전달 후 소멸된다.
       - Session을 선언해서 집어넣고 사용 후 지워주는 수고를 덜어주고,
       - url에 쿼리스트링으로 붙지 않기 때문에 깨끗하고 f5(새로고침)에 영향을 주지않음.  
       - 주의사항 
         받는쪽 매핑메서드의 매개변수로 parameter를 전달받는 VO가 정의되어 있으면
         이 VO 생성과 관련된 500 발생하므로 주의한다.
         ( Test : JoController 의 jupdate 성공시 redirect:jdetail )
         단, VO로 받지 않는 경우에는 url에 붙여 전달하면서 addFlashAttribute 사용 가능 함        

  => getFlashAttribute
       - insert 성공 후 redirect:jlist에서 Test (JoController, 결과는 null)
       - 컨트롤러에서 addFlashAttribute 가 session에 보관한 값을 꺼내는 것은 좀 더 확인이 필요함 

 < ** redirect로 한글 parameter 전달 시 한글깨짐 >
  => 한글깨짐이 발생하는 경우 사용함.
  => url parameter로 전달되는 한글 값을 위한 encoding
       - String message = URLEncoder.encode("~~ member가 없네용 ~~", "UTF-8");
         mv.setViewName("redirect:mlist?message=" + message);  
*/

// ===========================================================================================================================

/*
 ***** JSON 제이슨, (JavaScript Object Notation) **********
 => 자바스크립트의 객체 표기법으로, 데이터를 전달할 때 사용하는 표준형식.
  속성(key)과 값(value)이 하나의 쌍을 이룸
      
 ** JAVA의 Data 객체 -> JSON 변환하기
 1) GSON
 : 자바 객체의 직렬화 / 역직렬화를 도와주는 라이브러리 (구글에서 만듦)
 즉, JAVA객체 -> JSON 또는 JSON -> JAVA객체
      
 2) @ResponseBody (매핑 메서드에 적용)
 : 메서드의 리턴값이 View를 통해 출력되지 않고 HTTP Response Body에 직접 쓰여지게 됨.
 이때 쓰여지기 전, 리턴되는 데이터 타입에 따라 종류별 MessageConverter에서 변환이 이뤄진다.
 MappingJacksonHttpMessageConverter를 사용하면 request, response를 JSON으로 변환
 view (~.jsp)가 아닌 Data 자체를 전달하기 위한 용도
 @JsonIgnore : VO에 적용하면 변환에서 제외

 3) jsonView
 => Spring에서 MappingJackson2JsonView를 사용해서
 ModelAndView를 json 형식으로 반환해 준다.
 => 방법
 -> pom dependency추가
 -> 설정파일 xml에 bean 등록 
  ( 안하면 /WEB-INF/views/jsonView.jsp를 찾게되고  없으니 404 발생 )
 -> return할 ModelAndView 생성 시 View_Name을 "jsonView"로 설정

==========================================================================================
*/

@Controller
public class MemberController {
	
		// ** 전역변수 활용
		@Autowired
		MemberService service;
		
		//ModelAndView mv = new ModelAndView();
		// => 전역변수로 정의하면 message가 사라지지 않고 계속 남아있게 됨.
		//  -> request.setAttribute는 자동삭제 되지만 ModelAndView는 일반 객체이므로 전역으로 정의하면 addObject 값이 남아있음
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 전역 선언
		// ** PasswordEncoder interface 구현 클래스
		// => Pbkdf2PasswordEncoder, BCryptPasswordEncoder, 
		//    SCryptPasswordEncoder, StandardPasswordEncoder,
		//    NoOpPasswordEncoder
		// => 대표적인 BCryptPasswordEncoder root-context.xml 또는
		//    servlet-context.xml 에 bean 설정 후 @Autowired 가능
		
	//-----------------------------------------------------------------------------------------------------
			
		// ** Member_CheckList
		// => UI의 checkbox를 이용하여 mapper에 반복문 적용하기
		// @RequestMapping(value = "/mchecklist", method=RequestMethod.POST) // method post로 작성한 경우 post 방식'만' 적용 가능
		@RequestMapping(value = "/mchecklist") // method 없는 경우 get 방식이나 post 방식 모두 가능
		public ModelAndView mchecklist(ModelAndView mv, MemberVO vo) {
			
			// 1) Check_Box 처리
			// String[] check = request.getParameterValues("check");
			// => vo에 배열 Type의 check 컬럼을 추가하면 편리
			
			// 2) Service 실행
			// => 선택하지 않은 경우 : selectList()
			// => 선택을 한 경우 : 조건별 검색 checkList(vo) -> 추가
			// => 조건 선택여부 확인 : 배열 check의 길이,  null
			// => 배열 Type의 경우 선택하지 않으면 check = null이므로 길이 비교 필요 없음.
			
			// ** view 화면에 요청 조건이 동일하게 표시되도록 하기 위해
			// => 요청 조건 재전달하기
			mv.addObject("check", vo.getCheck());
			
			if (vo.getCheck() != null && vo.getCheck().length > 0) { // null인지, 0보다 큰지 확인!
				// => 조건별 검색
				mv.addObject("banana", service.checkList(vo));
				
			} else
				mv.addObject("banana", service.selectList()); // 아니면 selectList return
	
			mv.setViewName("/member/mCheckList");
	
			return mv;
	
		} // mchecklist
			
	//-----------------------------------------------------------------------------------------------------
		
		// ** Ajax Json Member Delete : 관리자의 회원 삭제
		@RequestMapping(value = "/axmdelete", method = RequestMethod.POST)
		public ModelAndView axmdelete(HttpServletRequest request, ModelAndView mv, MemberVO vo) {
			
			// 1. 요청분석
			String id = "null";
			HttpSession session = request.getSession(false);
			// => 메서드내에서 session을 사용 가능하도록 정의
			//    삭제 성공 후 session 무효화하기 위함
			
			if (session != null && ((String)session.getAttribute("loginID")).equals("admin")) {
	        	
	        	// 2. Service 처리
				if (service.delete(vo) > 0) {
					mv.addObject("code", "200");
					
				} else {
					mv.addObject("code", "201");
				}
	        	
	        } else {
	        	// ** session 정보가 없어 삭제가 불가능한 경우
	        	mv.addObject("code", "202");
	        }
			
			// 3. 결과 처리(Java 객체 -> JSON)
			mv.setViewName("jsonView");
			return mv;

		} // axmdelete
		
	//-----------------------------------------------------------------------------------------------------
	
		// ** Image(File) Download
		// => 전달받은 파일패스와 이름으로 File 객체를 만들어 뷰로 전달
		@RequestMapping(value = "/dnload")
		public ModelAndView dnload(HttpServletRequest request, ModelAndView mv, @RequestParam("dnfile") String dnfile) {
			// @RequestParam("dnfile") String dnfile와 동일 기능 : String dnfile = request.getParameter("dnfile");
			
			// 1) 파일 path 확인
			// => 요청 Parameter를 확인
			// String dnfile = request.getParameter("dnfile");
			String realPath = request.getRealPath("/"); // deprecated Method
			String fileName = dnfile.substring(dnfile.lastIndexOf("/") + 1);
			// dnfile => resources\\uploadImage\\aaa.gif
			
			// => 개발 중인지, 배포 했는지에 따라 결정
			// => 해당 File 찾기
			if (realPath.contains(".eclipse.")) // eclipse 개발환경 (배포 전)
				realPath = "D:\\MTest\\myWork\\Spring02\\src\\main\\webapp\\resources\\uploadImage\\" + fileName;
			else // 톰캣 서버에 배포 후 : 서버 내에서의 위치
				realPath += "resources\\uploadImage\\" + fileName;
			
			// 2) 해당 파일 객체화
			File file = new File(realPath);
			mv.addObject("downloadFile", file);
			
			// 3) response 처리 (reponse의 body에 담아줌)
			// => Java File 객체 -> File 정보를 response에 전달
			mv.setViewName("downloadView");
			
			return mv;

		} // dnload
		
	//-----------------------------------------------------------------------------------------------------
	
		// ** Ajax_MemberList
		@RequestMapping(value = "/axmlist")
		public ModelAndView axmlist(ModelAndView mv) {
			
			mv.addObject("banana", service.selectList());

			mv.setViewName("/axTest/axMemberList");
			
			return mv;

		} // axmlist
		
	//-----------------------------------------------------------------------------------------------------
		
		// ** JSON Login
		// => jsonView 적용
		@RequestMapping(value = "/jslogin", method=RequestMethod.POST)
		public ModelAndView jslogin(HttpServletRequest request,	HttpServletResponse response, ModelAndView mv, MemberVO vo) {

			// 1) request 처리
			// => 입력한 Password 보관
			// => response 한글처리 (Ajax 요청 결과로 Data 전송 시 필수)
			String password = request.getParameter("password");
			response.setContentType("text/html; charset=UTF-8");

			// 2) service 처리
			vo = service.selectOne(vo);
			if ( vo != null ) {

				// ID 는 일치 -> Password 확인
				if ( vo.getPassword().equals(password) ) {
					// Login 성공 -> login 정보 session에 보관, home
					request.getSession().setAttribute("loginID", vo.getId());
					request.getSession().setAttribute("loginName", vo.getName());
					mv.addObject("code", "200");
					
				} else {
					// Password 오류
					mv.addObject("message", "** Password 오류, 다시 하세요");
					mv.addObject("code", "201");
				}

			} else {	// ID 오류
				mv.addObject("message", "** ID 오류, 다시 하세요");
				mv.addObject("code", "202");
			} //else

			mv.setViewName("jsonView");
			return mv;
			
		} // jslogin
		
	//-----------------------------------------------------------------------------------------------------
		
		// ** ID 중복 확인
		@RequestMapping(value = "/idDupCheck")
		public ModelAndView idDupCheck(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo) {

			// 1) 입력한 newID 보관
			mv.addObject("newId", vo.getId());

			// 2) service
			vo = service.selectOne(vo);

			// 3) 결과 처리
			if (vo != null) {
				// -> newId 사용 불가능
				mv.addObject("isUse", "F");
			} else {
				// -> newId 사용 가능
				mv.addObject("isUse", "T");
			}
			mv.setViewName("/member/idDupCheck"); // member/idDupCheck => 가능
			return mv;

		} // idDupCheck
		
	//-----------------------------------------------------------------------------------------------------
		
		// ** Criteria Page List
		// => ver02 : SearchCriteria cri
		@RequestMapping(value = "/mcrilist")
		public ModelAndView mcrilist(HttpServletRequest request, HttpServletResponse response, 
										ModelAndView mv, SearchCriteria cri, PageMaker pageMaker) {
			
			// 1) Criteria 처리
			cri.setSnoEno(); // Criteria Class에 값이 저장되어 있음
			
			// 2) 서비스 처리
			// => List 처리
			mv.addObject("banana", service.searchList(cri)); // ver02

			
			// 3) View 처리 => PageMaker
			pageMaker.setCri(cri);
			pageMaker.setTotalRowsCount(service.searchCount(cri)); // ver02 - 조건과 일치하는 Rows 개수
			
			mv.addObject("pageMaker", pageMaker);
			mv.setViewName("/member/mCriList");
			return mv;

		} // mcrilist
		
//-----------------------------------------------------------------------------------------------------------------------

		// ** MemberList
		@RequestMapping(value = "/mlist")
		public ModelAndView mlist(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

			// MemberList
			List<MemberVO> list = new ArrayList<MemberVO>();

			list = service.selectList();
			if (list != null) {
				mv.addObject("banana", list); // request.setAttribute("banana", list)와 동일

			} else {
				mv.addObject("message", "** 출력 자료가 없습니다 **");
				// request.setAttribute("message", "** 출력 자료가 없습니다 **")
			}

			mv.setViewName("/member/memberList");
			return mv;

		} // mlist

//-----------------------------------------------------------------------------------------------------------------------

		// ** Detail
		@RequestMapping(value = "/mdetail")
		//public ModelAndView mdetail(HttpServletRequest request, HttpServletResponse response) {
		public ModelAndView mdetail(HttpServletRequest request, HttpServletResponse response, MemberVO vo, ModelAndView mv) {
		// => Mapping 메서드
		//    : 매개변수로 지정된 객체에 request_ParameterName과 일치하는 컬럼(setter)이 존재하면 자동으로 set
		//      request.getParameter("id") 필요없게 됨. -> type 변환도 알아서 척척
			
			// MemberDetail
			//MemberVO vo = new MemberVO(); // 매개변수로 직접 넣으면 따로 생성하지 않아도 됨
			
			
			// 1. 요청분석
			// => 요청 구분 (요청에 따라 id 가져오기)
			// - 로그인 후 내 정보 보기 : session.getAttribute ~
			// - 관리자가 memberList에서 선택 : getParameter...
			HttpSession session = request.getSession(false);
			
			if ( vo.getId() == null || vo.getId().length() < 1 ) {
		        // => parameter id의 값이 없으면 session에서 가져온다. 
		         
				if ( session != null && session.getAttribute("loginID") != null ) {
					vo.setId((String)session.getAttribute("loginID"));
		            
		        } else {
					request.setAttribute("message", "=> 출력 ID 없음, 로그인 후 이용 부탁드립니다.");
					mv.setViewName("home");
					
					return mv;
		         }
					
			 } // if_loginID

			String uri = "/member/memberDetail";
			
			
			// 2. Service 처리
			// => Service에서 selectOne
				vo = service.selectOne(vo);
				
				if (vo != null) { // 해당 자료가 있는 경우
					mv.addObject("apple", vo);
					
					// ** Update(내 정보 수정) 요청이면 updateForm.jsp로
					// => PasswordEncoder 사용 후에는 session에 보관해 놓은 raw_password를 수정할 수 있도록 vo에 set 해줌.
					if ("U".equals(request.getParameter("jCode"))) { // 찾은 자료에 "U"가 jCode와 같다면
						uri = "/member/updateForm"; // updateForm으로 이동
						vo.setPassword((String)session.getAttribute("loginPW"));
					}
					
				} else { // 없는 ID
					mv.addObject("message", "** " + request.getParameter("id") + "님의 자료는 존재하지 않습니다 **");
				}	

				mv.setViewName(uri);
				return mv;
				
		} // mdetail

//-----------------------------------------------------------------------------------------------------------------------

		// ** Login & Logout
		@RequestMapping(value = "/loginf")
		public ModelAndView loginf(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

			mv.setViewName("/member/loginForm");

			return mv;

		} // loginf

//-----------------------------------------------------------------------------------------------------------------------

		@RequestMapping(value = "/login")
		public ModelAndView login(HttpServletRequest request,	HttpServletResponse response, ModelAndView mv, MemberVO vo) {

			// 1) request 처리
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String uri = "/member/loginForm";

			// 2) service 처리
			vo.setId(id);
			vo = service.selectOne(vo);
			
			if ( vo != null ) {

				// ID 는 일치 -> Password 확인
				// => password 암호화 이전
				// if ( vo.getPassword().equals(password) ) {
				
				// => password 암호화 이후
				if (passwordEncoder.matches(password, vo.getPassword())) { // (rawData, digest) => 비교 후 true이면 보관
					// Login 성공 -> login 정보 session에 보관, home
					request.getSession().setAttribute("loginID", id);
					request.getSession().setAttribute("loginName", vo.getName());
					// getSession()은 ModelAndView 적용 X

					// ** BCryptPasswordEncoder로 암호화되면 복호화가 불가능 함.
		            // => password 수정을 별도로 처리해야 함. 
		            // => 그러나 기존의 update Code를 활용하여 updateForm.jsp에서 수정을 위해
		            //    User가 입력한 raw_password를 보관함. 
		            // => 이 session에 보관한 값은 detail에서 "U" 요청 시 사용 함.
					request.getSession().setAttribute("loginPW", password);

					uri = "home" ;
					
				} else {
					// Password 오류
					mv.addObject("message", "** Password 오류,  다시 하세요");
				} // if_passwordEncoder

			} else {	// ID 오류
				mv.addObject("message", "** ID 오류,  다시 하세요");
			} // else

			mv.setViewName(uri);
			return mv;
			
		} // login

//-----------------------------------------------------------------------------------------------------------------------

		@RequestMapping(value = "/logout")
		public ModelAndView logout(HttpServletRequest request,	HttpServletResponse response, ModelAndView mv) {

			// ** session 인스턴스 정의 후 삭제하기
			// => 매개변수: 없거나, true, false
			// => false : session 이 없을때 null 을 return;
			HttpSession session = request.getSession(false);

			if (session != null)
				session.invalidate();

			mv.addObject("message", "** 로그아웃 되었습니다");
			mv.setViewName("home");

			return mv;

		} // logout
		
//-----------------------------------------------------------------------------------------------------------------------

		// ** Join : 회원가입
		@RequestMapping(value = "/joinf")
		public ModelAndView joinf(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

			mv.setViewName("/member/joinForm");
			return mv;

		} // joinf
		
//-----------------------------------------------------------------------------------------------------------------------

		@RequestMapping(value = "/join", method = RequestMethod.POST)
		// => 매핑네임과 method가 일치하는 요청만 처리함
		public ModelAndView join(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo) throws IOException {
			// 1. 요청분석
			// => Service 준비
			// => request, 한글처리
			// => Spring : 한글(web.xml, Filter), request(매핑메서드의 매개변수 선언으로 처리)
			// => Join 성공 : 로그인유도 loginForm
			//         실패 : joinForm 
			String uri = "/member/loginForm";
			
			/*
			   ** MultipartFile ***********************
		       => MultipartFile 타입의 uploadfilef의 정보에서 
		          upload된 image 파일과 파일명을 get 처리,
		       => upload된 image 파일은 서버의 정해진 폴더(물리적 위치)에 저장 하고, -> file1
		       => 이 위치에 대한 정보를 table에 저장 (vo의 UploadFile에 set) -> file2
		       ** image 파일명 중복시 : 나중 이미지로 update 됨. 
		    */
			
		      // ** Image 물리적 위치에 저장
		      // 1) 현재 웹 어플리케이션의 실행 위치 확인 : 
		      // => eslipse 개발환경 (배포 전)
		      //    D:\MTest\MyWork\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\Spring01\
		      // => 톰캣서버에 배포 후 : 서버 내에서의 위치가 됨
		      //    D:\MTest\IDESet\apache-tomcat-9.0.41\webapps\Spring01\
			
		      String realPath = request.getRealPath("/"); // deprecated Method
		      System.out.println("** realPath = " + realPath);
		      
		      
		      // 2) 위의 값을 이용해서 실제 저장위치 확인 
		      // => 개발 중인지, 배포했는지에 따라 결정
		      if ( realPath.contains(".eclipse.") )  // eslipse 개발환경 (배포 전)
		         realPath = "D:\\MTest\\myWork\\Spring02\\src\\main\\webapp\\resources\\uploadImage\\";
		      else  // 톰캣서버에 배포 후 : 서버 내에서의 위치
		         realPath += "resources\\uploadImage\\";
		      
		      // ** 폴더 만들기 (File 클래스활용)
		      // => 위의 저장경로에 폴더가 없는 경우 (uploadImage가 없는 경우) 만들어 준다
		      File f1 = new File(realPath);
		      if (!f1.exists()) 
		    	  f1.mkdir();
		      // => realPath 디렉터리가 존재하는지 검사 (uploadImage 폴더 존재 확인)
		      //    존재하지 않으면 디렉토리 생성
		      
		      // ** 기본 이미지 지정하기 
		      String file1, file2 = "resources/uploadImage/basicman2.jpg"; 
		      
		      // ** MultipartFile
		      // => 업로드한 파일에 대한 모든 정보를 가지고 있으며 이의 처리를 위한 메서드를 제공한다.
		      //    -> String getOriginalFilename(), 
		      //    -> void transferTo(File destFile),
		      //    -> boolean isEmpty()
		      
		      MultipartFile uploadfilef = vo.getUploadfilef(); // file의 내용 및 파일명 등 전송된 정보들이 들어있음.
		      if (uploadfilef != null && !uploadfilef.isEmpty()) {
		         
		         // ** Image를 선택 함 -> Image 저장 (경로_realPath + 파일명)
		         // 1) 물리적 저장경로에 Image 저장
		         file1 = realPath + uploadfilef.getOriginalFilename(); // 경로완성
		         uploadfilef.transferTo(new File(file1)); // Image 저장
		         
		         // 2) Table 저장 준비
		         file2 = "resources/uploadImage/" + uploadfilef.getOriginalFilename();
		      }
		      // ** 완성된 경로 vo에 set
		      vo.setUploadfile(file2);
		      
		      // *** PasswordEncoder (암호화 적용하기)
		      // => BCryptPasswordEncoder 적용
		      //    encode(rawData) -> digest 생성 & vo에 set
		      vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		      
		      
			// 2. Service 처리
			// *** Transaction_AOP Test
			/*
			  1. dependency 확인 => AspectJ , AspectJ Weaver 2.
			  servlet-context.xml AOP 설정 3. Rollback Test 3.1) Aop xml 적용전 =>
			  insert1 은 입력되고, insert2 에서 500_Dupl..Key 오류 발생 3.2) Aop xml 적용후
			  => insert2 에서 오류발생시 모두 Rollback 되어 insert1, insert2 모두 입력 안됨
			 */
			// 3.1) Transaction 적용전 : 동일자료 2번 insert
			// => 첫번째는 입력완료 되고, 두번째자료 입력시 Key중복 오류발생 (500 발생)
			// 3.2) Transaction 적용후 : 동일자료 2번 insert
			// => 첫번째는 입력완료 되고, 두번째 자료입력시 Key중복 오류발생 하지만,
			// rollback 되어 둘다 입력 안됨
			// service.insert(vo) ;
		      
			if (service.insert(vo) > 0) {
				mv.addObject("message", "=> 회원가입 성공! 로그인 후 이용해주세요 *^^*");
				
			} else {
				mv.addObject("message", "=> 회원가입 실패, 다시 가입 부탁드립니다.");
				uri = "/member/joinForm";
			}
			
			// 3. 결과(View -> Forward) 처리

			mv.setViewName(uri);
			return mv;

		} // join
		
//-----------------------------------------------------------------------------------------------------------------------

		// ** Update : 내 정보 수정하기
		@RequestMapping(value = "/mupdate", method = RequestMethod.POST)
		public ModelAndView mupdate(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo) throws IOException {
			
			// 1. 요청분석
			// => Update 성공 : 내 정보 표시 -> memberDetail.jsp
			//           실패 : 재수정 요구 -> updateForm.jsp
			String uri = "/member/memberDetail";

			mv.addObject("apple", vo);
			// => Update 성공/실패 모두 출력 시 필요하므로
			// => Image 추가 후 Update 성공하면 uploadfile의 값이 변경될 수 있으므로
			//    성공 후에는 재처리해야 함. (uploadImage 폴더 우클릭 - refresh)
			
			// ** Image Update 추가
			
			// 1) Image 물리적 위치에 저장
			String realPath = request.getRealPath("/"); // deprecated Method

			// 1-1) 위의 값을 이용해서 실제 저장위치 확인
			// => 개발 중인지, 배포했는지에 따라 결정
			if (realPath.contains(".eclipse.")) // eslipse 개발환경 (배포 전)
				realPath = "D:\\MTest\\myWork\\Spring02\\src\\main\\webapp\\resources\\uploadImage\\";
			else // 톰캣서버에 배포 후 : 서버 내에서의 위치
				realPath += "resources\\uploadImage\\";

			// 1-2) 폴더 만들기 (File 클래스활용)
			// => 위의 저장경로에 폴더가 없는 경우 (uploadImage가 없는 경우) 만들어 준다
			File f1 = new File(realPath);
			if (!f1.exists())
				f1.mkdir();

			// 2) 기본 이미지 지정하기
			String file1, file2 = "resources/uploadImage/basicman2.jpg";

			// 3) MultipartFile : file은 저장, 저장된 경로는 vo에 set
			// => 새 파일선택 했으면 : uploadfilef 처리
			// => 새 파일선택 안 했으면 : uploadfilef 처리 없이 uploadfile 사용
			MultipartFile uploadfilef = vo.getUploadfilef(); // file의 내용 및 파일명 등 전송된 내용들
			if (uploadfilef != null && !uploadfilef.isEmpty()) {

				// ** 새 Image 파일을 선택 함 -> Image 저장 (경로_realPath + 파일명)
				// 3-1) 물리적 저장경로에 Image 저장
				file1 = realPath + uploadfilef.getOriginalFilename(); // 경로완성
				uploadfilef.transferTo(new File(file1)); // Image 저장

				// 3-2) Table 저장 준비
				file2 = "resources/uploadImage/" + uploadfilef.getOriginalFilename();
				vo.setUploadfile(file2);
			}
			// ** new_Image를 선택하지 않은 경우
			// => form에서 전송되어 vo에 담겨진 uploadfile 값을 사용하면 됨.
			
			
			// *** PasswordEncoder (암호화 적용하기)
			// => update에 적용하기 전
			//   - login : loginPW session에 보관
			//   - detail : updateForm에 raw_password가 출력되도록 수정 함. 
			// => BCryptPasswordEncoder 적용
			// encode(rawData) -> digest 생성 & vo에 set
			vo.setPassword(passwordEncoder.encode(vo.getPassword()));
			
			
			// 2. Service 처리
			if(service.update(vo) > 0) {
				mv.addObject("message", "=> 정보 수정이 성공적으로 완료되었습니다. *^^*");
				mv.addObject("apple",vo); // 수정된 vo를 보관
				
			} else {
				mv.addObject("message", "=> 정보 수정 실패, 다시 수정 부탁드립니다.");
				uri = "/member/updateForm";
			}
			
			// 3. 결과(ModelAndView) 전달
			mv.setViewName(uri);
			return mv;

		} // mupdate
		
//-----------------------------------------------------------------------------------------------------------------------

		// ** Delete : 회원탈퇴
		@RequestMapping(value = "/mdelete")
		public ModelAndView mdelete(HttpServletRequest request, HttpServletResponse response, 
									ModelAndView mv, MemberVO vo, RedirectAttributes rttr) {
			
			// 1. 요청분석
			// => Delete 성공 : session 무효화, home.jsp
			//           실패 : message, home.jsp
			// => 삭제 대상 확인 : 본인 loginID or 관리자가 삭제하는 경우(request.getParameter..)
			
			String id = "null";
			HttpSession session = request.getSession(false);
			// => 메서드내에서 session을 사용 가능하도록 정의
			//    삭제 성공 후 session 무효화하기 위함
			
			if ( session != null && session.getAttribute("loginID") != null ) {
	        	
				// ** 삭제 가능한 경우
				id = (String)session.getAttribute("loginID");
	        	// => 본인 탈퇴 : loginID 이용한 삭제
	        	//   - 그러나 id는 관리자가 아니어야 함.
	        	//   - 관리자 작업인 경우 : 이미 vo에 삭제할 ID가 set 되어있음
	        	//   - 관리자 작업이 아닌 경우 : session에서 get한 ID를 vo에 set
	        	if (!"admin".equals(id))
	        		vo.setId(id);
	        	
	        	// 2. Service 처리
				if (service.delete(vo) > 0) {
					rttr.addFlashAttribute("message", " 탈퇴 성공, 그 동안 이용해주셔서 감사합니다.");
					
					if (! "admin".equals(id))
					session.invalidate();
					// 본인이 탈퇴하는 경우에만 session 무효화
					
				} else {
					rttr.addFlashAttribute("message", " 탈퇴 실패, 다시 시도해주시고 계속 실패할 시 고객센터로 연락 부탁드립니다.");
				} // Service
	        	
	        } else {
	        	// ** session 정보가 없어 삭제가 불가능한 경우
	        	// => session is null
	        	rttr.addFlashAttribute("message", "=> session 정보 없음, 로그인 후 이용 부탁드립니다.");
	        } // session 확인_if_else
			
			
			// 3. 결과(ModelAndView) 전달
			// => 성공 & 실패
			//   - redirect:home이 바람직 (단, message 처리 주의)
			//   - 웹브라우저 주소창의 주소가 home이 표시되도록
			mv.setViewName("redirect:home");
			return mv;

		} // mdelete

} // class
